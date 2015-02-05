/*
 * Created on 06-sep-2004
*/
package ieci.tdw.ispac.ispaclib.gendoc;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.context.ExpedientContext;
import ieci.tdw.ispac.ispaclib.dao.entity.EntityDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.entity.DocumentData;
import ieci.tdw.ispac.ispaclib.entity.EntityManager;
import ieci.tdw.ispac.ispaclib.gendoc.parser.IDocumentParserConnector;
import ieci.tdw.ispac.ispaclib.gendoc.parser.DocumentParserConnectorFactory;
import ieci.tdw.ispac.ispaclib.utils.XmlTag;

import org.apache.log4j.Logger;

/**
 * @author juanin
 */
public class DMDocumentManager {
	
	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(DMDocumentManager.class);
		
	private final ClientContext mcontext;
	private final ExpedientContext mexpctx;
	private final String TAG_NAME = "name";
	private final String TAG_VALUE = "value";
	private final String TAG_PROPERTY = "property";

	private final String PROPERTY_NUMEXP = "Nº Expediente";
	private final String PROPERTY_CODIGO_PROCEDIMIENTO = "Código Procedimiento";
	private final String PROPERTY_FASE = "Fase";
	private final String PROPERTY_TRAMITE = "Trámite";
	private final String PROPERTY_TIPO_DOCUMENTO = "Tipo Documento";
	//private final String PROPERTY_FIRMA = "Firma";
	private final String PROPERTY_TIPO_MIME = "Tipo Mime";
	private final String PROPERTY_LONGITUD = "longitud";
	//private final String PROPERTY_FECHA_CREACION = "Fecha Creación";
	
	public DMDocumentManager(ClientContext context,ExpedientContext expctx)
	{
		mcontext=context;
		mexpctx=expctx;
	}

	public void mergeDocument(String sOpenDocURL, String sSaveDocURL,DocumentData data) throws ISPACException {
		
		try {

			// Obtiene el conector con el gestor de plantillas
			IDocumentParserConnector connector = DocumentParserConnectorFactory
					.getTemplateConnector(mcontext);
			
			// Combina el documento
			connector.mergeDocument(sOpenDocURL, sSaveDocURL, data);

		} catch (ISPACException e) {
			logger.error("Error al combinar el documento", e);
			throw e;
		} catch (Exception e) {
			logger.error("Error al combinar el documento", e);
			throw new ISPACException(e);
		}
	}

	public EntityDAO createDocumentEntity(DocumentData docdata)
	throws ISPACException
	{
		DbCnt cnt = null;
		try
		{
			cnt=mcontext.getConnection();

			EntityManager entmgr=new EntityManager(mcontext);
			EntityDAO entity=entmgr.createDocument(cnt,mexpctx.getNumExp(),docdata);
			docdata.setDoc(entity.getInt("ID"));
			return entity;
		}
		catch (ISPACException ie)
		{
			logger.error("Error al crear el documento", ie);
			throw new ISPACException("Error en createDocument", ie);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
	}

	public EntityDAO getDocumentEntity(int keyId)
	throws ISPACException
	{
		DbCnt cnt = null;

		try
		{
			cnt = mcontext.getConnection();

			EntityManager manager = new EntityManager(mcontext);
			return manager.getDocument( cnt, keyId);
		}
		catch (ISPACException ie)
		{
			logger.error("Error al obtener el documento", ie);
			throw new ISPACException("Error en getDocumentEntity", ie);
		}
		finally
		{
			mcontext.releaseConnection(cnt);
		}
	}

	public String getProperties( DocumentData document)
	throws ISPACException
	{
		String sXML = null;
		String str;
		String sProperty;
		String sProperties = "";

		if (document.getDocId() != 0)
		{
			str  = XmlTag.newTag(TAG_NAME, "document_id");
			str += XmlTag.newTag(TAG_VALUE, document.getDocId());
			sProperty = XmlTag.newTag(TAG_PROPERTY, str);
			sProperties = sProperty;

			str  = XmlTag.newTag(TAG_NAME, "document_type");
			str += XmlTag.newTag(TAG_VALUE, document.getDocType());
			sProperty = XmlTag.newTag(TAG_PROPERTY, str);
			sProperties += sProperty;

			str  = XmlTag.newTag(TAG_NAME, "document_name");
			str += XmlTag.newTag(TAG_VALUE, "<![CDATA[" + document.getName() + "]]>");
			sProperty = XmlTag.newTag(TAG_PROPERTY, str);
			sProperties += sProperty;
		}

		if (mexpctx.getProcedure() != 0)
		{
			str  = XmlTag.newTag(TAG_NAME, "procedure_id");
			str += XmlTag.newTag(TAG_VALUE, mexpctx.getProcedure());
			sProperty = XmlTag.newTag(TAG_PROPERTY, str);
			sProperties += sProperty;

			str  = XmlTag.newTag(TAG_NAME, "procedure_name");
			str += XmlTag.newTag(TAG_VALUE, "<![CDATA[" + mexpctx.getProcedureName() + "]]>");
			sProperty = XmlTag.newTag(TAG_PROPERTY, str);
			sProperties += sProperty;
		}

		if (mexpctx.getProcess() != 0)
		{
			str  = XmlTag.newTag(TAG_NAME, "expedient_id");
			str += XmlTag.newTag(TAG_VALUE, mexpctx.getProcess());
			sProperty = XmlTag.newTag(TAG_PROPERTY, str);
			sProperties += sProperty;

			str  = XmlTag.newTag(TAG_NAME, "expedient_name");
			str += XmlTag.newTag(TAG_VALUE, "<![CDATA[" + mexpctx.getNumExp() + "]]>");
			sProperty = XmlTag.newTag(TAG_PROPERTY, str);
			sProperties += sProperty;
		}

		if (mcontext.getRespId() != null)
		{
			str  = XmlTag.newTag(TAG_NAME, "user_guid");
			str += XmlTag.newTag(TAG_VALUE, mcontext.getRespId());
			sProperty = XmlTag.newTag(TAG_PROPERTY, str);
			sProperties += sProperty;

		}

		if (document.getAuthor() != null)
		{
			str  = XmlTag.newTag(TAG_NAME, "user_name");
			str += XmlTag.newTag(TAG_VALUE, "<![CDATA[" + document.getAuthor() + "]]>");
			sProperty = XmlTag.newTag(TAG_PROPERTY, str);
			sProperties += sProperty;
		}

		if (mexpctx.getStagePCD() != 0)
		{
			str  = XmlTag.newTag(TAG_NAME, "stage_id");
			str += XmlTag.newTag(TAG_VALUE, mexpctx.getStagePCD());
			sProperty = XmlTag.newTag(TAG_PROPERTY, str);
			sProperties += sProperty;

			str  = XmlTag.newTag(TAG_NAME, "stage_name");
			str += XmlTag.newTag(TAG_VALUE, "<![CDATA[" + mexpctx.getStageName() + "]]>");
			sProperty = XmlTag.newTag(TAG_PROPERTY, str);
			sProperties += sProperty;
		}

		if (mexpctx.getTaskPCD() != 0)
		{
			str  = XmlTag.newTag(TAG_NAME, "task_id");
			str += XmlTag.newTag(TAG_VALUE, mexpctx.getTaskPCD());
			sProperty = XmlTag.newTag(TAG_PROPERTY, str);
			sProperties += sProperty;

			str  = XmlTag.newTag(TAG_NAME, "task_name");
			str += XmlTag.newTag(TAG_VALUE, "<![CDATA[" + mexpctx.getTaskName() + "]]>");
			sProperty = XmlTag.newTag(TAG_PROPERTY, str);
			sProperties += sProperty;
		}

		if (document.getMimeType().length() != 0)
		{
			str  = XmlTag.newTag(TAG_NAME, "mimetype");
			str += XmlTag.newTag(TAG_VALUE, "<![CDATA[" + document.getMimeType() + "]]>");
			sProperty = XmlTag.newTag(TAG_PROPERTY, str);
			sProperties += sProperty;
		}

		sXML = XmlTag.getXmlInstruction("ISO-8859-1")
			 + XmlTag.newTag("doc_properties", sProperties);

		return sXML;
	}
	
	public String getPropertiesDEF(DocumentData document, int length){
        
		String sXML = null;
		String str;
		String sProperty;
		StringBuffer sProperties = null;

		str  = XmlTag.newTag(TAG_NAME, PROPERTY_NUMEXP);
		str += XmlTag.newTag(TAG_VALUE, mexpctx.getNumExp());
		sProperty = XmlTag.newTag(TAG_PROPERTY, str);
		sProperties = new StringBuffer(sProperty);

		str  = XmlTag.newTag(TAG_NAME, PROPERTY_CODIGO_PROCEDIMIENTO);
		str += XmlTag.newTag(TAG_VALUE, mexpctx.getProcedure());
		sProperty = XmlTag.newTag(TAG_PROPERTY, str);
		sProperties.append(sProperty);

		str  = XmlTag.newTag(TAG_NAME, PROPERTY_FASE);
		str += XmlTag.newTag(TAG_VALUE, mexpctx.getStage());
		sProperty = XmlTag.newTag(TAG_PROPERTY, str);
		sProperties.append(sProperty);        
        
		str  = XmlTag.newTag(TAG_NAME, PROPERTY_TRAMITE);
		str += XmlTag.newTag(TAG_VALUE, XmlTag.newCDATA(mexpctx.getTaskName()));
		sProperty = XmlTag.newTag(TAG_PROPERTY, str);
		sProperties.append(sProperty);        

		str  = XmlTag.newTag(TAG_NAME, PROPERTY_TIPO_DOCUMENTO);
		str += XmlTag.newTag(TAG_VALUE, document.getDocType());
		sProperty = XmlTag.newTag(TAG_PROPERTY, str);
		sProperties.append(sProperty);        


//		str  = XmlTag.newTag(TAG_NAME, PROPERTY_FIRMA);
//		str += XmlTag.newTag(TAG_VALUE, "" ));
//		sProperty = XmlTag.newTag(TAG_PROPERTY, str);
//		sProperties.append(sProperty);  		
		
		
		str  = XmlTag.newTag(TAG_NAME, PROPERTY_TIPO_MIME);
		str += XmlTag.newTag(TAG_VALUE, XmlTag.newCDATA(document.getMimeType()));
		sProperty = XmlTag.newTag(TAG_PROPERTY, str);
		sProperties.append(sProperty);        

		str  = XmlTag.newTag(TAG_NAME, PROPERTY_LONGITUD);
		str += XmlTag.newTag(TAG_VALUE, XmlTag.newCDATA(""+length));
		sProperty = XmlTag.newTag(TAG_PROPERTY, str);
		sProperties.append(sProperty);        
		
//		str  = XmlTag.newTag(TAG_NAME, PROPERTY_FECHA_CREACION);
//		str += XmlTag.newTag(TAG_VALUE, XmlTag.newCDATA(mDateFormat.format(fechaCreacion) ));
//		sProperty = XmlTag.newTag(TAG_PROPERTY, str);
//		sProperties.append(sProperty);        

		
		
		sXML = XmlTag.getXmlInstruction("ISO-8859-1") + XmlTag.newTag("doc_properties", sProperties.toString());		
		return sXML;
    }	
	
}
