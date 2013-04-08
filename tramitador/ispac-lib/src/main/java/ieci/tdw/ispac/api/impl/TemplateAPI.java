package ieci.tdw.ispac.api.impl;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.ITemplateAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACNullObject;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.cat.CTTemplate;
import ieci.tdw.ispac.ispaclib.dao.cat.TemplateDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.gendoc.parser.IDocumentParserConnector;
import ieci.tdw.ispac.ispaclib.gendoc.parser.DocumentParserConnectorFactory;
import ieci.tdw.ispac.ispaclib.templates.TemplateMgr;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.log4j.Logger;

public class TemplateAPI implements ITemplateAPI {
	
	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(TemplateAPI.class);
	
	private TemplateMgr mTemplateMgr;
	private ClientContext context;
	/**
	 * Constructor
	 * @param context Contexto del cliente
	 */
	public TemplateAPI(ClientContext context)
	throws ISPACException
	{
		mTemplateMgr = new TemplateMgr(context);
		this.context = context;
	}

	/**
	 * Crea una nueva plantilla.
	 * @param idTpDoc identificador del tipo de documento
	 * @param name nombre de la plantilla
	 * @param expresion expresion de la plantilla
	 * @param idPcd identificador de procedimiento
	 * @param fichero para asociar a la plantilla
	 * @return el objeto plantilla
	 * @throws ISPACException
	 * @throws IOException 
	 * @throws FileNotFoundException
	 */
	public CTTemplate newTemplate (int idTpDoc, String name, String code, int idPcd, String expresion, InputStream fichero)
	throws ISPACException, FileNotFoundException, IOException {
		return newTemplate(idTpDoc, name, code, idPcd, expresion, fichero, null);
	}

	/* (non-Javadoc)
	 * @see ieci.tdw.ispac.api.ITemplateAPI#newTemplate(int, java.lang.String, java.lang.String, int, java.lang.String, java.io.InputStream, java.lang.String)
	 */
	public CTTemplate newTemplate(int idTpDoc, String name, String code,
			int idPcd, String expresion, InputStream fichero, String mimeType)
			throws ISPACException, FileNotFoundException, IOException {
		return mTemplateMgr.newTemplate(idTpDoc, name, code, idPcd, expresion, fichero, mimeType);
	}

	/**
	 * Crea una plantilla nueva asociandole un documento
	 * @param idTpDoc
	 * @param name
	 * @param code
	 * @param expresion
	 * @param procId
	 * @param fichero
	 * @return
	 * @throws ISPACException
	 */
	public CTTemplate createTemplateProc(int idTpDoc, String name, String code, String expresion, int procId, InputStream fichero)
			throws ISPACException, FileNotFoundException, IOException {
		return createTemplateProc(idTpDoc, name, code, expresion, procId, fichero, null);
	}

	/**
	 * @see ieci.tdw.ispac.api.ITemplateAPI#createTemplateProc(int, java.lang.String, java.lang.String, java.lang.String, int, java.io.InputStream, java.lang.String)
	 */
	public CTTemplate createTemplateProc(int idTpDoc, String name, String code,
			String expresion, int procId, InputStream fichero, String mimeType)
			throws ISPACException, FileNotFoundException, IOException {
		
		CTTemplate template = null;
		DbCnt cnt = null;
		
		try {
			cnt = context.getConnection();
			cnt.openTX();
			if (fichero == null) {
				template = mTemplateMgr.newTemplate(cnt, idTpDoc, name, code, procId, expresion);
			} else {
				template = mTemplateMgr.newTemplate(cnt, idTpDoc, name, code, procId, expresion, fichero, mimeType);
			}

			IItem item = context.getAPI().getCatalogAPI().createCTEntity(context.getConnection(),ICatalogAPI.ENTITY_P_PLANTPROC);
			item.set("ID_PCD", procId);
			item.set("ID_P_PLANTDOC", template.getTemplate());
			item.store(context);
			cnt.closeTX(true);
		} catch (Exception e) {
			logger.error("Error al crear la plantilla", e);
			cnt.closeTX(false);
			throw new ISPACException(e.getMessage());
		} finally {
			cnt.closeConnection();
		}
		
		return template;
	}

	/**
	 * Elimina la plantilla genérica.
	 * @param templateId Identificador de la plantilla.
	 * @throws ISPACException si ocurre algún error.
	 */
	public void deleteTemplate(int templateId) throws ISPACException {
		DbCnt cnt = context.getConnection();
		try{
			cnt.openTX();
			TemplateDAO.delete(cnt, templateId);
			cnt.closeTX(true);
		}catch (Exception e) {
			cnt.closeTX(false);
			throw new ISPACException(e.getMessage());
		}finally{
			cnt.closeConnection();
		}

	}

	public void deleteTemplateProc(int id_p_plantdoc) throws ISPACException {
		CTTemplate template = null;
		DbCnt cnt = null;
		try{
			cnt = context.getConnection();
			cnt.openTX();

				String query = "DELETE FROM SPAC_P_PLANTILLAS WHERE ID_P_PLANTDOC="+id_p_plantdoc;
			 	cnt.directExec(query);	

			 	//elimino de la tabla de plantillas
				template = getTemplate(id_p_plantdoc);
				
				mTemplateMgr.deleteTemplate( template);
				
			cnt.closeTX(true);
 				
		}catch (Exception e) {
			cnt.closeTX(false);
			throw new ISPACException(e.getMessage());
		}finally{
			cnt.closeConnection();
		}

	}
	
	public void deleteTemplateProc(int procId, int id_p_plantdoc)
	throws ISPACException
	{
		CTTemplate template = null;
		DbCnt cnt = null;
		try{
			cnt = context.getConnection();
			cnt.openTX();

				String query = "WHERE ID_PCD =  " + procId + " AND " + "ID_P_PLANTDOC="+id_p_plantdoc;
				IItemCollection items = context.getAPI().getCatalogAPI().queryCTEntities(ICatalogAPI.ENTITY_P_PLANTPROC, query);
				String id = null;
				if (items.next()){
					id = items.value().getString("ID");
				}
				if (id!=null) {
					IItem item=context.getAPI().getCatalogAPI().createCTEntity(ICatalogAPI.ENTITY_P_PLANTPROC);
					item.set("ID",id);
					item.set("ID_PCD",procId);
					item.set("ID_P_PLANTDOC",id_p_plantdoc);
					item.delete(context);
	
					//comprobar : si no hay mas proc con la plantilla la elimino de la lista de plantillas
					query = "WHERE ID_P_PLANTDOC="+id_p_plantdoc;
					items = context.getAPI().getCatalogAPI().queryCTEntities(ICatalogAPI.ENTITY_P_PLANTPROC, query);
					if (!items.next()){
	 					//elimino de la tabla de plantillas
	 					template = getTemplate(id_p_plantdoc);
	 					mTemplateMgr.deleteTemplate( template);
					}
				}
 				cnt.closeTX(true);
 				
		}catch (Exception e) {
			cnt.closeTX(false);
			throw new ISPACException(e.getMessage());
		}finally{
			cnt.closeConnection();
		}
	}

	
	/**
	 * Obtiene las plantillas del catálogo.
	 * @return Lista de plantillas.
	 * @throws ISPACException si ocurre algún error.
	 */
	public IItemCollection getTemplates() throws ISPACException {
		return mTemplateMgr.getTemplates();
	}

	/**
	 * Obtiene las plantillas del catálogo.
	 * @param templateName Nombre de la plantilla.
	 * @return Lista de plantillas.
	 * @throws ISPACException si ocurre algún error.
	 */
	public IItemCollection getTemplates(String templateName) throws ISPACException {
		return mTemplateMgr.getTemplates(templateName);
	}

	/**
	 * Obtiene una plantilla.
	 * @param id identificador de la plantilla
	 * @return la plantilla
	 * @throws ISPACException
	 */
	public CTTemplate getTemplate(int id)
	throws ISPACException
	{
		return mTemplateMgr.getTemplate(id);
	}

	/**
	 * Obtiene una plantilla
	 * @param code Código de la plantilla.
	 * @return Plantilla
	 * @throws ISPACException si ocurre algún error.
	 */
	public TemplateDAO getTemplateByCode(String code) throws ISPACException {
		return mTemplateMgr.getTemplateByCode(code);
	}

	/**
	 * Elimina una plantilla
	 * @param template la plantilla
	 * @throws ISPACException
	 */
	public void deleteTemplate(CTTemplate template)
	throws ISPACException
	{
		mTemplateMgr.deleteTemplate( template);
	}
	
	/**
	 * Asigna el contenido a una plantilla.
	 * @param template plantilla
	 * @param in fuente de los datos del archivo a anexar
	 * @param mimetype mimetype del archivo
	 * @throws ISPACException
	 */
	public void setTemplate(CTTemplate template, InputStream in,int length, String mimetype)
	throws ISPACException
	{
		mTemplateMgr.setTemplate(template, in, length, mimetype);
	}

	/**
	 * Obtiene el contenido de una plantilla
	 * @param template la plantilla
	 * @param out destino donde se vuelca el archivo
	 * @throws ISPACException, TemplateArchiveException
	 */
	public void getTemplate(CTTemplate template, OutputStream out)
	throws ISPACException
	{
		mTemplateMgr.getTemplate(template, out);
	}

	public boolean isProcedureTemplate(int id_p_plantdoc) throws ISPACException {
		try{
			String query = "WHERE ID_P_PLANTDOC="+id_p_plantdoc;
			IItemCollection items = context.getAPI().getCatalogAPI().queryCTEntities(ICatalogAPI.ENTITY_P_PLANTPROC, query);
			if (items.next()){
				return true;
			}
			return false;
 				
		}catch (ISPACNullObject e) {
			return false;
		}
		
	}

	public int countTemplatesProcedure(int id_p_plantdoc) throws ISPACException {
		String query = "WHERE ID_P_PLANTDOC="+id_p_plantdoc;
		int numItems = context.getAPI().getCatalogAPI().countCTEntities(ICatalogAPI.ENTITY_P_PLANTPROC, query);
		return numItems;
	}
	
	/** 
	 * @see ieci.tdw.ispac.api.ITemplateAPI#isMimeTypeSupported(java.lang.String)
	 */
	public boolean isMimeTypeSupported(String mimeType) throws ISPACException {

		boolean isSupported = false;

		try {

			// Obtiene el conector con el gestor de plantillas
			IDocumentParserConnector connector = DocumentParserConnectorFactory
					.getTemplateConnector(context);
			
			// Comprueba si el tipo MIME de la plantilla está soportado
			isSupported = connector.isSupported(mimeType);

		} catch (ISPACException e) {
			logger.error(
					"Error al comprobar si el tipo MIME de la plantilla está soportado",
					e);
			throw e;
		} catch (Exception e) {
			logger.error(
					"Error al comprobar si el tipo MIME de la plantilla está soportado",
					e);
			throw new ISPACException(e);
		}

		return isSupported;
	}
}
