/*
 * invesflow Java - ISPAC
 * Fuente: EntityAppFactory.java
 * Creado el 04-ago-2005 por juanin
 *
 */
package ieci.tdw.ispac.ispaclib.app;

import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.session.OrganizationUser;
import ieci.tdw.ispac.ispaclib.session.OrganizationUserInfo;
import ieci.tdw.ispac.ispaclib.utils.FileUtils;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;

public class EntityAppFactory {
	
	public static final String FORMS_COMMON_DIR = "/forms/common/";
	
	public static final String EXPEDIENT_FORM_INITIAL_PART_JSP = "expedientFormInitial.jsp";
	public static final String EXPEDIENT_FORM_FINAL_PART_JSP = "expedientFormFinal.jsp";
	
	public static final String THIRD_FORM_LIST_INITIAL_PART_JSP = "thirdFormListInitial.jsp";
	public static final String THIRD_FORM_LIST_FINAL_PART_JSP = "thirdFormListFinal.jsp";
	
	public static final String ENTITY_FORM_INITIAL_PART_JSP = "entityFormInitial.jsp";
	public static final String ENTITY_FORM_FINAL_PART_JSP = "entityFormFinal.jsp";
	
	public static final String ENTITY_FORM_LIST_INITIAL_PART_JSP = "entityFormListInitial.jsp";
	public static final String ENTITY_FORM_LIST_FINAL_PART_JSP = "entityFormListFinal.jsp";
	
    static public EntityApp newEntityApp(ClientContext context, String sClassName)
	throws ISPACException
	{
	    // Implementación por defecto
	    if (sClassName.length() == 0)
	    	return new SimpleEntityApp(context);

	    Class cls=null;
	    try
	    {
	        cls = Class.forName(sClassName);
	    }
	    catch (ClassNotFoundException e)
	    {
	        throw new ISPACException(e);
	    }

		if (!EntityApp.class.isAssignableFrom(cls))
			throw new ISPACException( sClassName + " no extiende la clase EntityApp");

		Object[] arguments = new Object[1];
		arguments[0] = context;
		Class[] types = new Class[1];
		types[0] = context.getClass();

		try
	    {
			Constructor object = cls.getConstructor( types);
			return (EntityApp) object.newInstance( arguments);
	    }
	    catch (Exception e)
	    {
	        throw new ISPACException(e);
	    }

	}

    /*
    static public EntityApp createEntityApp(ClientContext context, IApplicationDef application, int entityId, IItem item)
	throws ISPACException
	{
		String sClassName = application.getClassName();
		String sURL = application.getPage();
		String sParameters = application.getParameters();
		String sFormatter = application.getFormatter();
		String sAppName= application.getName();
		int appId= application.getId();

		EntityApp entity = newEntityApp(context,sClassName);

		entity.setAppId(appId);
		entity.setAppName(sAppName);
		entity.setURL(sURL);
		entity.setMainEntity(entityId);
		entity.setParameters(sParameters);
		entity.setFormatter(sFormatter);
        entity.setItem(item);

		// Inicialización de la aplicación.
		entity.initiate();

		return entity;
	}
	*/
    

    /**
     * Obtiene la aplicación solicitada para la entidad debidamente
     * cargada con los datos del registro suministrado.
     * La aplicación permite el intercambio de datos entre
     * la capa de presentación y los objetos de negocio.
     * 
     * @param context Contexto del cliente
     * @param application Aplicación
     * @param numExp Número de expediente
     * @param procedureId Identificador del procedimiento
     * @param entityId Identificador de la entidad
     * @param item Objeto de la entidad
     * @param path Ruta de la aplicación
     * @param urlKey Clave de la entidad en la url
     * @return EntityApp
     * @throws ISPACException Si se produce algún error
     */
    static public EntityApp createEntityApp(ClientContext context, 
    										IApplicationDef application,
    										String numExp,
    										int procedureId,
    										int entityId, 
    										IItem item, 
    										String path,
    										int urlKey) throws ISPACException {
    	
		String sClassName = application.getClassName();
		String sURL = application.getPage();
		String sParameters = application.getParameters();
		String sFormatter = application.getFormatter();
		String sFormatterXML = application.getFormatterXML();
		String sAppName= application.getName();
		int appId= application.getId();
		int version = application.getFrmVersion();
		String frmJsp = application.getFrmJsp();
		String entityName = application.getEntPrincipalNombre();
		
		// Si el formulario no es estático, comprobar si el entorno es de multientidad
		if ((path != null) && (!StringUtils.isEmpty(frmJsp))) {
			
			// Comprobar si el entorno es de multientidad
			OrganizationUserInfo info = OrganizationUser.getOrganizationUserInfo();
			if (info != null){
				String organizationId = info.getOrganizationId();
				if (StringUtils.isNotBlank(organizationId)) {
					sURL = "/" + organizationId + sURL;
				}
			}
		}
				
		EntityApp entityApp = newEntityApp(context, sClassName);
		
		// Configuración de la aplicación
		entityApp.setAppId(appId);
		entityApp.setAppName(sAppName);
		entityApp.setExpedient(numExp);
		entityApp.setMainEntity(entityId);
		entityApp.setMainEntityName(entityName);
		entityApp.setXmlParameters(sParameters);
		entityApp.setFormatter(sFormatter);
		entityApp.setFormatterXML(sFormatterXML);
		entityApp.setPath(path);
		entityApp.setURL(sURL);
		entityApp.setUrlKey(urlKey);
		
		// Establecer la entidad principal y cargar sus recursos
		entityApp.setItem(item);
		entityApp.setLabels(context.getAppLanguage());

		// Inicialización de la aplicación
		entityApp.initiate();
		
		// Procesar la página JSP que presenta el formulario asociado a la aplicación
		processApplicationPageForm(frmJsp, version, path, sURL, entityId, entityApp);

		return entityApp;
	}
    
    /**
     * Procesa la página JSP que presenta el formulario asociado a la aplicación,
     * comprobando si no existe para crearlo y si ya existe comprobando la versión
     * para sustituirlo cuando es una nueva versión.
     * 
     * @param frmJsp Formulario JSP.
     * @param version Número de versión del formulario.
     * @param path Ruta de la aplicación.
     * @param urlPage Ruta del formulario.
     * @param entityId Identificador de la entidad.
     * @param entityApp Aplicación que presenta la entidad.
     * @throws ISPACException Si se produce algún error.
     */
    static private void processApplicationPageForm(String frmJsp, 
    											   int version, 
    											   String path, 
    											   String urlPage,
    											   int entityId,
    											   EntityApp entityApp) throws ISPACException {

    	//comprueba que tiene JSP asociada y su codigo esta en Base de datos
		if ((path != null) &&
			(!StringUtils.isEmpty(frmJsp))) {
				
			File filePath = new File(path);
			File filePage = new File(filePath, urlPage);
				
			String pathPage = filePage.getPath();
			int index = pathPage.lastIndexOf(File.separatorChar);
			
			String pathPageDir = pathPage.substring(0, index);
			File filePathPageDir = new File(pathPageDir);
			
		    String pathPageVersion = pathPage + "." + version;	
		    File filePageVersionInBD = new File(pathPageVersion);
				
			if (!filePage.exists()) {
				
				generateFilePageVersion(path, filePathPageDir, filePage, filePageVersionInBD, frmJsp, entityId, entityApp);
			}
			else if (!filePageVersionInBD.exists()) {
					
				// Nueva versión de la JSP
				// FileUtils.deleteDirContents(filePathPageDir);
				
				// No se elimina el contenido del directorio sino que se renombra el formulario jsp con la versión
				int antVersion = version - 1;
				filePage.renameTo(new File(pathPage + ".v" + antVersion));
				
				// Eliminar el fichero de versión anterior
				while ((antVersion > 0) && (!(new File(pathPage + "." + antVersion).delete()))) {
					antVersion--;
				}
				
				generateFilePageVersion(path, filePathPageDir, filePage, filePageVersionInBD, frmJsp, entityId, entityApp);
			}
		}
    }
    
    /**
     * Para la entidad se genera el fichero de la página JSP junto con su fichero de versión.
     * 
     * @param path Ruta de la aplicación
     * @param filePathPageDir Directorio donde se generan los ficheros.
     * @param filePage Fichero de la página JSP.
     * @param filePageVersion Fichero de versión de la página JSP.
     * @param frmJsp Formulario JSP.
     * @param entityId Identificador de la entidad.
     * @param entityApp Aplicación que presenta la entidad.
     * @throws ISPACException Si se produce algún error.
     */
    static private void generateFilePageVersion(String path, 
    											File filePathPageDir, 
    											File filePage, 
    											File filePageVersion, 
    											String frmJsp,
    											int entityId,
    											EntityApp entityApp) throws ISPACException {
    	
    	// Expediente
    	if (entityId == SpacEntities.SPAC_EXPEDIENTES) {
    		
    		generateFilePageVersion(path, filePathPageDir, filePage, filePageVersion, frmJsp,
					EntityAppFactory.EXPEDIENT_FORM_INITIAL_PART_JSP, 
					EntityAppFactory.EXPEDIENT_FORM_FINAL_PART_JSP);
    	}
    	// Entidad con lista
    	else if (entityApp instanceof ListEntityApp) {
    		
    		// Intervinientes
    		if (entityId == SpacEntities.SPAC_DT_INTERVINIENTES) {
    			
        		generateFilePageVersion(path, filePathPageDir, filePage, filePageVersion, frmJsp,
						EntityAppFactory.THIRD_FORM_LIST_INITIAL_PART_JSP, 
						EntityAppFactory.THIRD_FORM_LIST_FINAL_PART_JSP);
    		}
    		else {
    		
	    		generateFilePageVersion(path, filePathPageDir, filePage, filePageVersion, frmJsp,
	    								EntityAppFactory.ENTITY_FORM_LIST_INITIAL_PART_JSP, 
	    								EntityAppFactory.ENTITY_FORM_LIST_FINAL_PART_JSP);
    		}
    	}
    	// Entidad con entidades secundarias agregadas o entidad maestro con detalles
    	else if (entityApp instanceof SecondaryEntityApp || entityApp instanceof MasterDetailEntityApp) {
    		
    		generateFilePageVersion(path, filePathPageDir, filePage, filePageVersion, frmJsp,
    								EntityAppFactory.ENTITY_FORM_INITIAL_PART_JSP, 
    								EntityAppFactory.ENTITY_FORM_FINAL_PART_JSP);
    	}
    }
    
    /**
     * Genera el fichero de la página JSP junto con su fichero de versión.
     * 
     * @param path Ruta de la aplicación
     * @param filePathPageDir Directorio donde se generan los ficheros.
     * @param filePage Fichero de la página JSP.
     * @param filePageVersion Fichero de versión de la página JSP.
     * @param frmJsp Formulario JSP.
     * @param initialPartJsp Nombre de la JSP que es la parte inicial del formulario.
     * @param finalPartJsp Nombre de la JSP que es la parte final del formulario.
     * @throws ISPACException Si se produce algún error.
     */
    static private void generateFilePageVersion(String path, 
    											File filePathPageDir, 
    											File filePage, 
    											File filePageVersion, 
    											String frmJsp,
    											String initialPartJsp,
    											String finalPartJsp) throws ISPACException {
    	
    	FileWriter out = null;
    	
		try {
			
			if (!filePathPageDir.exists()) {
				filePathPageDir.mkdirs();
			}
			
			// Generar la JSP
			filePage.createNewFile();
			out = new FileWriter(filePage);
			
			// Parte inicial del formulario
			File fileInitialPart = new File(path, EntityAppFactory.FORMS_COMMON_DIR + initialPartJsp);
			out.write(FileUtils.retrieveFileAsString(fileInitialPart));
			
			out.write(frmJsp);
			
			// Parte inicial del formulario
			File fileFinalPart = new File(path, EntityAppFactory.FORMS_COMMON_DIR + finalPartJsp);
			out.write(FileUtils.retrieveFileAsString(fileFinalPart));
			
			out.close();
			
			// Generar el fichero de versión
			filePageVersion.createNewFile();
        }
		catch (IOException ioe) {
        	throw new ISPACException(ioe);
        }
        finally {
            try {
                if(out != null) {
                	out.close();
                }
            }
            catch(Exception e) {}
		}
    }
    
}