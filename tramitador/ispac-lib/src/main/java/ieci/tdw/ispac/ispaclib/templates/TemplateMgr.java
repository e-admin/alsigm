package ieci.tdw.ispac.ispaclib.templates;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.dao.cat.CTTemplate;
import ieci.tdw.ispac.ispaclib.dao.cat.TemplateDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.util.ISPACConfiguration;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

public class TemplateMgr
{

	private final IClientContext context;

	private final String TEMPLATE = "ieci/tdw/ispac/ispaclib/templates/doc/empty.doc";
	
	private String msDefaultTemplate;

	/**
	 * Constructor
	 * @param context contexto del cliente
	 */
	public TemplateMgr (IClientContext context)
	throws ISPACException
	{
		this.context = context;
		ISPACConfiguration config = ISPACConfiguration.getInstance();
		msDefaultTemplate = config.get(ISPACConfiguration.DEFAULT_TEMPLATE);
		if (msDefaultTemplate == null)
		{
			msDefaultTemplate = TEMPLATE;
		}
	}

	/**
	 * Crea una nueva plantilla asociandole un tipo de documento, 
	 * sino recibe ningún fichero se crea un documento en blanco por defecto
	 * @param idTpDoc identificador del tipo de documento
	 * @param name nombre de la plantilla
	 * @param expresion expresion de la plantilla
	 * @param fichero que queremos subir como plantilla
	 * @return plantilla
	 * @throws ISPACException
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public CTTemplate newTemplate (int idTpDoc, String name, String code, int idPcd,  String expresion, InputStream fichero)
			throws ISPACException, FileNotFoundException, IOException {
		return newTemplate(idTpDoc, name, code, idPcd, expresion, fichero, null);
	}
	
	public CTTemplate newTemplate(int idTpDoc, String name, String code,
			int idPcd, String expresion, InputStream fichero, String mimetype)
			throws ISPACException, FileNotFoundException, IOException {
		
		DbCnt cnt = null;
		
		try {
			
			cnt = context.getConnection();			
			
			CTTemplate template = new CTTemplate();
			if (fichero == null) {
				template.newTemplate( cnt, idTpDoc, name, code, idPcd, expresion, msDefaultTemplate);
			} else {
				template.newTemplate( cnt, idTpDoc, name, code, idPcd, expresion, fichero, mimetype);
			}
			
			return template;
			
		} finally {
			context.releaseConnection(cnt);
		}
	}

	public CTTemplate newTemplate (DbCnt cnt, int idTpDoc, String name, String code, int idPcd, String expresion)
	throws ISPACException
	{
			CTTemplate template = new CTTemplate();
			template.newTemplate( cnt, idTpDoc, name, code, idPcd, expresion, msDefaultTemplate);
			return template;
	}
	
	
	/**
	 * Crea una nueva plantilla asociandole un documento
	 * @param cnt para la conexión
	 * @param idTpDoc identificador del tipo de documento
	 * @param name nombre de la plantilla
	 * @param code código de la plantilla
	 * @param expresion expresion de la plantilla
	 * @param documento documento asociado a la plantilla
	 * @return plantilla
	 * @throws ISPACException
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public CTTemplate newTemplate (DbCnt cnt, int idTpDoc, String name, String code, int idPcd,  String expresion, InputStream documento)
	throws ISPACException, FileNotFoundException, IOException
	{
		CTTemplate template = new CTTemplate();
		template.newTemplate(cnt, idTpDoc, name, code, idPcd, expresion, documento);
		return template;
	}

	public CTTemplate newTemplate (DbCnt cnt, int idTpDoc, String name, String code, int idPcd,  String expresion, InputStream documento, String mimeType)
	throws ISPACException, FileNotFoundException, IOException
	{
		CTTemplate template = new CTTemplate();		
		template.newTemplate(cnt, idTpDoc, name, code, idPcd, expresion, documento, mimeType);
		return template;
	}

	public IItemCollection getTemplates() throws ISPACException {
		return getTemplates(null);
	}
	
	public IItemCollection getTemplates(String templateName) throws ISPACException {

		HashMap tablemap=new HashMap();
		tablemap.put("CTTEMPLATE", new Integer(ICatalogAPI.ENTITY_CT_TEMPLATE));
		tablemap.put("CTTYPEDOC", new Integer(ICatalogAPI.ENTITY_CT_TYPEDOC));

		String filter = "WHERE CTTYPEDOC.ID=CTTEMPLATE.ID_TPDOC";
		if (StringUtils.isNotBlank(templateName)) {
			filter += " AND CTTEMPLATE.NOMBRE LIKE '%" + DBUtil.replaceQuotes(templateName) + "%'";
		}
		filter += " ORDER BY CTTEMPLATE.NOMBRE, CTTYPEDOC.NOMBRE";

		return context.getAPI().getCatalogAPI().queryCTEntities(tablemap, filter);
	}
	
	/**
	 * Carga una plantilla
	 * 
	 * @param id
	 *            identificador de la plantilla
	 * @return plantilla
	 * @throws ISPACException
	 */
	public CTTemplate getTemplate(int id)
	throws ISPACException
	{
		DbCnt cnt = null;
		try
		{
			cnt = context.getConnection();

			return new CTTemplate(cnt, id);
		}
		finally
		{
			context.releaseConnection(cnt);
		}
	}

	/**
	 * Cargar una plantilla. 
	 * @param code Código de la plantilla
	 * @return Plantilla
	 * @throws ISPACException si ocurre algún error.
	 */
	public TemplateDAO getTemplateByCode(String code) throws ISPACException {
		TemplateDAO template = null;
		
		if (StringUtils.isNotBlank(code)) {
			DbCnt cnt = null;
			try {
				cnt = context.getConnection();
				template = TemplateDAO.getTemplateByCode(cnt, code);
			} finally {
				context.releaseConnection(cnt);
			}
		}
		
		return template;
	}
	
	/**
	 * Borra la plantilla y todas sus versiones
	 * 
	 * @param template
	 *            plantilla
	 * @throws ISPACException
	 */
	public void deleteTemplate (CTTemplate template)
	throws ISPACException
	{
		DbCnt cnt = null;
		try
		{
			cnt = context.getConnection();

			template.deleteTemplate(cnt);
		}
		finally
		{
			context.releaseConnection(cnt);
		}
	}


	/**
	 * Asocia un archivo a la la plantilla. Si existe un archivo
	 * ya asociado, lo reemplaza
	 * @param template plantilla
	 * @param in fuente de los datos del archivo a anexar
	 * @param mimetype mimetype del archivo
	 * @throws ISPACException
	 */
	public void setTemplate(CTTemplate template, InputStream in,int length, String mimetype)
	throws ISPACException
	{
		DbCnt cnt = null;
		try
		{
			cnt = context.getConnection();

			//si tiene mimeType el documento con el que trabajamos, usamos su mimeType, si no, cogemos el que nos viene 
			if (StringUtils.isNotBlank(template.getMimetype())){
				mimetype = template.getMimetype();		
			}

			template.setTemplate( cnt,in,length,mimetype);
		}
		finally
		{
			context.releaseConnection(cnt);
		}
	}

	/**
	 * Devuelve el archivo anexo a la plantilla. Si el usuario que la pide
	 * es el propietario de un borrador, devuelve el archivo anexo al borrador.
	 * Si no existe borrador o no el usuario no es propietario del borrador,
	 * entonces devuelve el archivo anexo a la version en vigor. Si no existe
	 * versión en vigor, y el borrador no pertenece al usuario, se devuelve
	 * una excepcion de tipo TemplateArchiveException
	 * @param template plantilla
	 * @param out donde volcar el contenido del fichero
	 * @throws ISPACException
	 */
	public void getTemplate(CTTemplate template, OutputStream out)
	throws ISPACException
	{
		DbCnt cnt = null;
		try
		{
			cnt = context.getConnection();

			template.getTemplate( cnt,out);
		}
		finally
		{
			context.releaseConnection(cnt);
		}
	}
}
