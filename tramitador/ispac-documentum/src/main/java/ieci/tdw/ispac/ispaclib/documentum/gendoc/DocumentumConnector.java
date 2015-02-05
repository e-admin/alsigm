package ieci.tdw.ispac.ispaclib.documentum.gendoc;

import ieci.tdw.ispac.api.connector.DocumentProperties;
import ieci.tdw.ispac.api.connector.IDocConnector;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACNullObject;
import ieci.tdw.ispac.ispaclib.util.FileTemporaryManager;
import ieci.tdw.ispac.ispaclib.util.ISPACConfiguration;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.XmlTag;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.documentum.fc.client.DfClient;
import com.documentum.fc.client.DfQuery;
import com.documentum.fc.client.IDfClient;
import com.documentum.fc.client.IDfCollection;
import com.documentum.fc.client.IDfQuery;
import com.documentum.fc.client.IDfSession;
import com.documentum.fc.client.IDfSessionManager;
import com.documentum.fc.client.IDfSysObject;
import com.documentum.fc.common.DfException;
import com.documentum.fc.common.DfId;
import com.documentum.fc.common.DfLoginInfo;
import com.documentum.fc.common.DfTime;
import com.documentum.fc.common.IDfAttr;
import com.documentum.fc.common.IDfId;
import com.documentum.fc.common.IDfLoginInfo;
import com.documentum.fc.common.IDfTime;

/**
 * 
 * @author Enrique de Lema
 * 
 * Esta clase tiene como cometido la gestión de documentos en el gestor
 * documental Documentum.
 * 
 * El fichero de configuración de Documentum 'dmcl.ini' tiene la
 * siguienteconfiguración:
 * 
 * [DOCBROKER_PRIMARY] 
 * host = nombre del host o dirección IP 
 * port =1489
 * 
 * [DMAPI_CONFIGURATION] 
 * cache_queries = T 
 * client_locale=es
 * secure_connect_default=try_native_first 
 * client_codepage=UTF-8
 * client_Os_codepage=UTF-8 
 * max_session_count=256 
 * connect_pooling_enabled=T
 * 
 * También es necesario un fichero de propiedades que relaciona los tipos mime
 * de los documentos con los tipos de contenido de Documentum
 * 'documentum.properties'. Este fichero se localiza en el classpath de la
 * aplicación.
 */
public class DocumentumConnector implements IDocConnector
{
	private static Logger log = Logger.getLogger(DocumentumConnector.class);
	
	private static DocumentumConnector mInstance = null;

	protected IDfClient mClient = null; // Configuración local del cliente

	protected IDfSessionManager mSessionManager = null; // Agrupación de conexiones

	//protected IDfSession mSession = null; 
	
	// Documentum

	protected String msDocbase = null; // Nombre de la base de datos

	protected String msCabinet = null; // Repositorio donde se asocian los documentos

	protected String msUser = null; // Usuario administrador

	protected String msPassword = null; // Clave del usuario administrador

	protected FileTemporaryManager mFileTemporaryManager = null; // Gestor de ficheros

	// temporales

	protected String msFileTemporaryPath = null;

	protected Properties mContentType = null;

	private DocumentumConnector() 
	throws ISPACException
	{
		try
		{
			log.info("DocumentumConnector.DocumentumConnector:  Inicio");
			// Parámetros de la aplicación
			ISPACConfiguration parameters = ISPACConfiguration.getInstance();

			msDocbase = parameters.get(ISPACConfiguration.DCMT_DOCBASE);
			msUser = parameters.get(ISPACConfiguration.DCMT_USER);
			msPassword = parameters.get(ISPACConfiguration.DCMT_PASSWORD);
			msCabinet = parameters.get(ISPACConfiguration.DCMT_CABINET);

			log.debug("DocumentumConnector.DocumentumConnector: Antes new DfLoginInfo()");
			IDfLoginInfo login = new DfLoginInfo();
			// Obtiene la configuración local de Documentum.
			log.debug("DocumentumConnector.DocumentumConnector: Antes new DfClient.getLocalClient()");
			mClient = DfClient.getLocalClient();
			log.debug("DocumentumConnector.DocumentumConnector: Antes new mClient.newSessionManager()");
			mSessionManager = mClient.newSessionManager();

			login.setUser(msUser);
			login.setPassword(msPassword);
			login.setDomain(null);
			log.debug("DocumentumConnector.DocumentumConnector: Antes mSessionManager.setIdentity(msDocbase, login): " +
					"login.user= " + msUser+
					"login.user= " + msPassword);
			mSessionManager.setIdentity(msDocbase, login);
			
			// Obtenemos la sesion
			log.debug("DocumentumConnector.DocumentumConnector: Antes mSessionManager.getSession(msDocbase): " +
					"msDocbase= " + msDocbase);
			//mSession = mSessionManager.getSession(msDocbase);

			// Obtiene el gestor de ficheros temporales
			log.debug("DocumentumConnector.DocumentumConnector: Antes  FileTemporaryManager.getInstance(): ");
			mFileTemporaryManager = FileTemporaryManager.getInstance();
			msFileTemporaryPath = mFileTemporaryManager.getFileTemporaryPath();

			log.debug("DocumentumConnector.DocumentumConnector: Despues  FileTemporaryManager.getInstance(): " +
					"msFileTemporaryPath= " + msFileTemporaryPath);

			getFileMimeType();
			
			log.info("DocumentumConnector.DocumentumConnector: Fin");
		} catch (DfException e)
		{
			throw new ISPACException("Documentum", e);
		}
	}

	private void getFileMimeType() throws ISPACException {
		try {
			// Relaciona los tipos mime de los documentos con los tipos
			// de contenido soportados por documentum.
			// Se localiza en el classpath de la aplicación.
			mContentType = new Properties();

			String strFileName = "documentum.properties";
			URL url = this.getClass().getClassLoader().getResource(strFileName);
			
			log.info("DocumentumConnector:this.getClass().getClassLoader().getResource(strFileName) url= " + url);
			
			if (url == null)
				url = ClassLoader.getSystemResource(strFileName);
			if (url == null)
				throw new NullPointerException();

			String strFolder = new String(url.getPath().getBytes(), 0, url.getPath().lastIndexOf(
					"/") + 1);

			File file = new File(strFolder, strFileName);
			FileInputStream in = new FileInputStream(file);
			mContentType.load(in);
			in.close();
		}			
		catch (IOException e)
		{
			throw new ISPACException(e);
		}		
	}

	public static synchronized DocumentumConnector getInstance() 
	throws ISPACException
	{
		if (mInstance == null)
			mInstance = new DocumentumConnector();
		return mInstance;
	}
	
	public boolean existsDocument(Object session, String sGUID) throws ISPACException
	{
		log.info("DocumentumConnector.existsDocument: Inicio");

		IDfSession mSession= (IDfSession) session; 

		boolean exists = false;

		String strObjectId = sGUID;

		try
		{
			exists = existsObject(mSession, strObjectId);
		} catch (DfException e)
		{
			throw new ISPACException(e);
		} 
		
		log.info("DocumentumConnector.existsDocument: Fin");

		return exists;
	}

	public void getDocument(Object session, String sGUID, OutputStream out) throws ISPACException
	{
		log.info("DocumentumConnector.getDocument: Inicio");

		IDfSession mSession= (IDfSession) session; 

		String strObjectId = sGUID;

		try
		{
			if (!existsObject(mSession, strObjectId))
				throw new ISPACException("No existe el documento");

			int read;
			byte[] buffer = new byte[4096];

			DfId objectId = new DfId(strObjectId);
			IDfSysObject sysObject = (IDfSysObject) mSession.getObject(objectId);
			ByteArrayInputStream in = sysObject.getContent();

			while ((read = in.read(buffer)) != -1)
			{
				out.write(buffer, 0, read);
			}
		} catch (DfException e)
		{
			throw new ISPACException(e);
		} catch (IOException e)
		{
			throw new ISPACException(e);
		}
		
		log.info("DocumentumConnector.getDocument: Fin");
	}

	/**
	 * Crea un nuevo documento.
	 * 
	 * @param in input stream.
	 * @param strPproperties documento XML con las propiedades del documento.
	 * @exception ISPACException.
	 *  
	 */
	public String newDocument(Object session, InputStream in, int length, String sProperties) 
	throws ISPACException
	{
		log.info("DocumentumConnector.newDocument: Inicio");

		IDfSession mSession= (IDfSession) session; 

		IDfSysObject sysObject = null;
		String strObjectId = null;

		// Nombre del fichero intermedio necesario
		// para salvar el documento.
		String strName = null;


		try
		{
			
		    DocumentProperties properties = new DocumentProperties(sProperties);
			
			if (properties.getDocumentId() == 0)
			{
				throw new ISPACException("Falta identificador del documento");
			}
		
			if (properties.getProcedureName() == null)
			{
				throw new ISPACException("Falta el nombre del procedimiento");
			}
		
			if (properties.getDocumentName() == null)
			{
				throw new ISPACException("Falta el nombre del documento");
			}
			// Crea la carpeta (si no existe) con el nombre del
			// procedimiento donde salvar el documento
			
			//Se acondiciona el nombre del expediente que va a ser el nombre de la carpeta contenedora
			String expedientName  = properties.getExpedientName();
			expedientName = StringUtils.replace(expedientName, "/","_");
			setFolder(mSession, expedientName);

			sysObject = (IDfSysObject) mSession.newObject("spac_document");
			sysObject.setSubject("ispac document");
			//sysObject.link("/" + msCabinet + "/" + properties.getProcedureName() );
			//sysObject.link("/" + msCabinet + "/" + properties.getExpedientName() );
			sysObject.link("/" + msCabinet + "/" + expedientName );

			sysObject.setFullText(true);
			sysObject.setObjectName(properties.getDocumentName());
			sysObject.setContentType(getContentType(properties.getMimeType()));

			sysObject.setInt("spac_documentid", properties.getDocumentId());

			if (properties.getDocumentType()!= 0)
			{
				sysObject.setInt("spac_documenttype", properties.getDocumentType());
			}

			sysObject.setString("spac_documentname", properties.getDocumentName());

			if (properties.getProcedureId()!= 0)
			{
				sysObject.setInt("spac_procedureid", properties.getProcedureId());
			}

			sysObject.setString("spac_procedurename", properties.getProcedureName());

			if (properties.getExpedientId() != 0)
			{
				sysObject.setInt("spac_expedientid", properties.getExpedientId());
			}
			if (properties.getExpedientName() != null)
			{
				sysObject.setString("spac_expedientname", properties.getExpedientName());
			}
			if (properties.getUserGUID() != null)
			{
				sysObject.setString("spac_userguid", properties.getUserGUID());
			}
			if (properties.getStageId() != 0)
			{
				sysObject.setInt("spac_stageid", properties.getStageId());
			}
			if (properties.getStageName() != null)
			{
				sysObject.setString("spac_stagename", properties.getStageName());
			}
			if (properties.getTaskId() != 0)
			{
				sysObject.setInt("spac_taskid", properties.getTaskId());
			}
			if (properties.getTaskName() != null)
			{
				sysObject.setString("spac_taskname", properties.getTaskName());
			}
			if (properties.getMimeType() != null)
			{
				sysObject.setString("spac_mimetype", properties.getMimeType());
				log.info("DocumentumConnector.newDocument: (properties.getMimeType() != null). spac_mimetype= " + properties.getMimeType());
				log.info("DocumentumConnector.newDocument: (properties.getMimeType() != null). mContentType.getProperty(" + properties.getMimeType() + ")= " + getContentType(properties.getMimeType()));
			}
			// Salva el contenido del input stream en un fichero intermedio
			strName = mFileTemporaryManager.put(in,length);
			String sFile = msFileTemporaryPath + "/" + strName;
			sysObject.setFile(sFile);
			sysObject.save();

			strObjectId = sysObject.getObjectId().getId();
		} 
		catch (DfException e)
		{
			throw new ISPACException(e);
		} 
		finally
		{
			// Elimina el fichero intermedio
			if (strName != null)
				mFileTemporaryManager.delete(strName);
		}
		log.info("DocumentumConnector.newDocument: Inicio");

		return strObjectId;
	}

	public String updateDocument(Object session,String sGUID, InputStream in, int length,String sProperties)
	throws ISPACException
	{
		log.info("DocumentumConnector.setDocument: Inicio");

		IDfSession mSession= (IDfSession) session; 

		String strObjectId = null;

		// Nombre del fichero intermedio necesario
		// para salvar el documento.
		String strName = null;
		strObjectId = sGUID;

		try
		{
		    DocumentProperties properties = new DocumentProperties(sProperties);
			
			if (!existsObject(mSession, strObjectId))
				throw new ISPACException("No existe el documento");

			DfId objectId = new DfId(strObjectId);
			IDfSysObject sysObject = (IDfSysObject) mSession.getObject(objectId);
			
			if (properties.getMimeType().length() != 0) {
				log.info("DocumentumConnector.setDocument: (properties.getMimeType() != null). spac_mimetype= " + properties.getMimeType());
				log.info("DocumentumConnector.setDocument: (properties.getMimeType() != null). mContentType.getProperty(" + properties.getMimeType() + ")= " + getContentType(properties.getMimeType()));
				sysObject.setContentType(getContentType(properties.getMimeType()));
			}

			// Salva el contenido del input stream en un fichero intermedio
			strName = mFileTemporaryManager.put(in,length);
			String sFile = msFileTemporaryPath + "/" + strName;
			sysObject.setFile(sFile);
			sysObject.save();
		} 
		catch (DfException e)
		{
			throw new ISPACException(e);
		} 
		finally
		{
			// Elimina el fichero intermedio
			if (strName != null)
				mFileTemporaryManager.delete(strName);
		}
		log.info("DocumentumConnector.setDocument: Fin");
		return sGUID;
	}

	public String getMimeType(Object session,String sGUID)
	throws ISPACException {
		
		log.info("DocumentumConnector.getMimeType: Inicio");

		IDfSession mSession= (IDfSession) session; 

		String sMimeType = null;

		String strObjectId = sGUID;

		try
		{
			if (!existsObject(mSession, strObjectId))
				throw new ISPACException("No existe el documento");
			
			sMimeType = (String) getObject(mSession,strObjectId).get( "spac_mimetype");
		} 
		catch (DfException e)
		{
			throw new ISPACException(e);
		} 
		
		log.info("DocumentumConnector.getMimeType: Fin");

		return sMimeType;
	}
	
	public int getDocumentSize(Object session, String sGUID)
	throws ISPACException {
		
		log.info("DocumentumConnector.getDocumentSize: Inicio");

		IDfSession mSession= (IDfSession) session; 
		    
	    int size = 0;
		String strObjectId = sGUID;
		    
			try
			{
				if (!existsObject(mSession, strObjectId))
					throw new ISPACException("No existe el documento");
				
				Object obj= getObject(mSession,strObjectId).get( "r_content_size");

				if (obj instanceof Integer) {
					log.info("Integer");
					size = ((Integer) obj).intValue();
				} else {
					if (obj instanceof Long) {
						log.info("Long");
						size = ((Long) obj).intValue();
					} else {
						if (obj instanceof Number) {
							log.info("Number");
							size = ((Number) obj).intValue();
						} else {
							String valor  = (String) obj;
							log.info("Valor" + valor);
						}
					}
				}
			} 
			catch (DfException e)
			{
				throw new ISPACException(e);
			} 

			log.info("DocumentumConnector.getDocumentSize: Fin");
			
			return size;
	}
	
	
	public void deleteDocument(Object session, String sGUID) throws ISPACException
	{
		log.info("DocumentumConnector.deleteDocument: Inicio");

		IDfSession mSession= (IDfSession) session; 

		String strObjectId = sGUID;

		try
		{
			if (!existsObject(mSession, strObjectId))
				throw new ISPACNullObject("No existe el documento");

			IDfId sysObjectId = new DfId(strObjectId);
			IDfSysObject sysObject = (IDfSysObject) mSession.getObject(sysObjectId);
			sysObject.destroyAllVersions();
		} 
		catch (DfException e)
		{
			throw new ISPACException(e);
		} 

		log.info("DocumentumConnector.deleteDocument: Fin");
	}

	public void createRepository(Object session) throws ISPACException
	{
		log.info("DocumentumConnector.createRepository: Inicio");

		IDfSession mSession= (IDfSession) session; 

		StringBuffer sBuffer = null;
		IDfQuery query = new DfQuery();

		try
		{
			// Crea un nuevo tipo para la gestión de documentos ispac
			sBuffer = new StringBuffer();
			sBuffer.append("create type spac_document");
			sBuffer.append("(");
			sBuffer.append("spac_documentid integer,");
			sBuffer.append("spac_documenttype integer,");
			sBuffer.append("spac_documentname string(100),");
			sBuffer.append("spac_procedureid integer,");
			sBuffer.append("spac_procedurename string(100),");
			sBuffer.append("spac_expedientid integer,");
			sBuffer.append("spac_expedientname string(100),");
			sBuffer.append("spac_userguid string(250),");
			sBuffer.append("spac_stageid integer,");
			sBuffer.append("spac_stagename string(100),");
			sBuffer.append("spac_taskid integer,");
			sBuffer.append("spac_taskname string(100),");
			sBuffer.append("spac_mimetype string(100)");
			sBuffer.append(")");
			sBuffer.append("with supertype dm_document primary key(spac_documentid) PUBLISH");

			query.setDQL(sBuffer.toString());
			query.execute(mSession, DfQuery.DF_QUERY);
		} catch (DfException e)
		{
			throw new ISPACException(e);
		}
		
		log.info("DocumentumConnector.createRepository: Fin");
	}

	protected Map getObject(Object session, String strObjectId) 
	throws ISPACException
	{
		log.info("DocumentumConnector.getObject: Inicio");

		IDfSession mSession= (IDfSession) session; 
		
		StringBuffer sBuffer = new StringBuffer();
		IDfQuery query = new DfQuery();
		IDfCollection collection = null;
		IDfAttr attribute = null;
		String sName = null;
		
		LinkedHashMap attrs = new LinkedHashMap();

		try
		{
			sBuffer.append("select *, r_content_size from spac_document");
			sBuffer.append(" where r_object_id = '" + strObjectId + "'");

			query.setDQL(sBuffer.toString());
			collection = query.execute(mSession, DfQuery.DF_READ_QUERY);
			if (collection.next())
			{
				Object object = null;
				
				for (int i = 0; i < collection.getAttrCount(); i++)
				{
					attribute = collection.getAttr(i);
					sName = attribute.getName();

					switch (attribute.getDataType())
					{
					case IDfAttr.DM_INTEGER:
						object = new Integer(collection.getInt(sName));
						break;
					case IDfAttr.DM_STRING:
						object = collection.getString(sName);
						break;
					case IDfAttr.DM_TIME:
						object = collection.getTime(sName).getDate();
						break;
					case IDfAttr.DM_BOOLEAN:
						object = new Boolean(collection.getBoolean(sName));
						break;
					case IDfAttr.DM_DOUBLE:
						object = new Double(collection.getDouble(sName));
						break;
					}

					attrs.put(sName,object);
				}
				
			}
		} 
		catch (DfException e)
		{
			throw new ISPACException(e);
		} 
		finally
		{
			try
			{	if (collection != null)	collection.close();} 
			catch (DfException e)
			{}
		}
		log.info("DocumentumConnector.getObject: Fin");
		
		return attrs;
	}

	protected boolean existsObject(IDfSession session, String strObjectId) throws DfException
	{
		log.info("DocumentumConnector.existsObject: Inicio");

		int count = 0;
		StringBuffer sBuffer = new StringBuffer();
		IDfQuery query = new DfQuery();
		IDfCollection collection = null;

		try
		{
			sBuffer.append("select count(*) from spac_document");
			sBuffer.append(" where r_object_id = '" + strObjectId + "'");

			query.setDQL(sBuffer.toString());
			collection = query.execute(session, DfQuery.DF_READ_QUERY);
			if (collection.next())
			{
				IDfAttr attribute = collection.getAttr(0);
				count = collection.getInt(attribute.getName());
			}
		} 
		finally
		{
			if (collection != null)
				collection.close();
		}
		log.info("DocumentumConnector.existsObject: Fin");

		return count != 0;
	}

	protected void setFolder(IDfSession session, String strFolder) throws DfException
	{
		log.info("DocumentumConnector.setFolder: Inicio");

		int count = 0;
		StringBuffer sBuffer = new StringBuffer();
		IDfQuery query = new DfQuery();
		IDfCollection collection = null;
		IDfSysObject sysObject = null;

		try {
			sBuffer.append("select count(*) from dm_folder ");
			sBuffer.append("where cabinet('/" + msCabinet + "') ");
			sBuffer.append("and object_name = '");
			sBuffer.append(strFolder);
			sBuffer.append("'");

			query.setDQL(sBuffer.toString());
			collection = query.execute(session, DfQuery.DF_READ_QUERY);
			if (collection.next())
			{
				IDfAttr attribute = collection.getAttr(0);
				count = collection.getInt(attribute.getName());
			}
			// No existe la carpeta
			if (count == 0)
			{
				sysObject = (IDfSysObject) session.newObject("dm_folder");
				sysObject.setObjectName(strFolder);
				sysObject.setSubject("ispac procedure folder");
				sysObject.link("/" + msCabinet);
				sysObject.save();
			}
			
		}finally
		{
			if (collection != null)
				collection.close();
		}
		log.info("DocumentumConnector.setFolder: Fin");
	}
	
	protected String getContentType(String strMimeType)
	{

		return mContentType.getProperty(strMimeType);
	}

	protected String getDate(IDfTime time)
	{

		String strDate = null;

		// Get the local time zone
		java.util.TimeZone tz = java.util.TimeZone.getDefault();
		java.text.DateFormat dateFormatter = new java.text.SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
		dateFormatter.setTimeZone(tz);
		strDate = dateFormatter.format(time.getDate());
		return strDate;
	}
	
	public void closeSession(Object session) throws ISPACException {
		log.info("DocumentumConnector.closeSession: Inicio");

		IDfSession mSession= (IDfSession) session; 
				
		try {
			if (mSession != null) {
				log.info("DocumentumConnector.getSession: mSession == null. Cerrar Sesion: Inicio Id= " + mSession.getSessionId());
				mSessionManager.release(mSession);
				mSession = null;
				log.info("DocumentumConnector.getSession: mSession == null. Cerrar Sesion: Fin");
			}
		} catch (DfException e)
		{
			throw new ISPACException(e);
		} 
		log.info("DocumentumConnector.closeSession: Fin");
	}
	
	public String getProperties(Object session, String sGUID) throws ISPACException {
		log.info("DocumentumConnector.getProperties: Inicio");
		IDfSession mSession= (IDfSession) session; 
		String strObjectId = sGUID;
		String sXML = null;
		String sTag;
		String sProperty;
		String name;
		String value;
		StringBuffer buffer = new StringBuffer();
		try
		{
			if (!existsObject(mSession, strObjectId))
				throw new ISPACException("No existe el documento");

			DfId objectId = new DfId(strObjectId);
			IDfSysObject sysObject = (IDfSysObject) mSession.getObject(objectId);

			//TODO Extraer properties de fichero de configuracion
			String[] properties = new String[]{"spac_documentid","spac_documenttype","spac_documentname","spac_procedureid","spac_procedurename","spac_expedientid","spac_expedientname","spac_userguid","spac_stageid","spac_stagename","spac_taskid","spac_taskname","spac_mimetype"}; 
			for (int i = 0; i < properties.length; i++)
			{
				name= properties[i];
				value = sysObject.getString(name);
				sTag  = XmlTag.newTag("name", name);
				sTag += XmlTag.newTag("value", XmlTag.newCDATA(value));
				sProperty = XmlTag.newTag("property", sTag);
				buffer.append(sProperty);
			}
			sXML = XmlTag.getXmlInstruction("ISO-8859-1") + XmlTag.newTag("doc_properties", buffer.toString());
			
		}catch (DfException e) {
			throw new ISPACException(e);
		} catch (ISPACException e) {
			throw e;
		}
		log.info("DocumentumConnector.getProperties: Fin");
		return sXML;
	}

	public String getProperty(Object session, String sGUID, String property) throws ISPACException {
		String value = null;
		try{
			IDfSession mSession= (IDfSession) session; 
			String strObjectId = sGUID;
			DfId objectId = new DfId(strObjectId);
			IDfSysObject sysObject = (IDfSysObject) mSession.getObject(objectId);
			value = sysObject.getString(property);
		} catch (DfException e) {
			throw new ISPACException(e);
		}
		return value;
	}

	public void setProperty(Object session, String sGUID, String name, String value) throws ISPACException {
		try{
			IDfSession mSession= (IDfSession) session; 
			String strObjectId = sGUID;
			DfId objectId = new DfId(strObjectId);
			IDfSysObject sysObject = (IDfSysObject) mSession.getObject(objectId);
			switch (sysObject.getAttrDataType(name))
			{
			case IDfAttr.DM_INTEGER:
				sysObject.setInt(name, new Integer(value).intValue());
				break;
			case IDfAttr.DM_STRING:
				sysObject.setString(name, value);
				break;
			case IDfAttr.DM_TIME:
				//TODO Probar el timo DfTime
				sysObject.setTime(name, new DfTime(value) );
				break;
			case IDfAttr.DM_BOOLEAN:
				sysObject.setBoolean(name, new Boolean(value).booleanValue());
				break;
			case IDfAttr.DM_DOUBLE:
				sysObject.setDouble(name, new Double(value).doubleValue());				
				break;
			}			
			
		} catch (DfException e) {
			throw new ISPACException(e);
		}
		return ;
	}

	public String getRepositoryInfo(Object session, String repId) throws ISPACException {
		// TODO Auto-generated method stub
		
		return null;
	}

	public Object createSession() throws ISPACException {
		log.info("DocumentumConnector.getSession: Inicio");
		IDfSession mSession =null;
		try {
			log.info("DocumentumConnector.getSession: mSession == null. Recoger Sesion: Inicio");
			mSession = mSessionManager.getSession(msDocbase);
			log.info("DocumentumConnector.getSession: mSession == null. Recoger Sesion: Fin. Id= " + mSession.getSessionId());
		} catch (DfException e)
		{
			throw new ISPACException("Documentum", e);
		} 
		log.info("DocumentumConnector.getSession: Fin");
		
		return mSession;
	}

}