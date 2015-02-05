package ieci.tdw.ispac.ispaclib.configuration;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.Properties;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.api.item.util.ListCollection;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.item.GenericItem;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.sql.Types;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.struts.util.MessageResources;

/**
 * @author RAULHC
 *
 */
public class ConfigurationMgr {
    
    //--------------------------------------------------------------------------------------------------
    // Variables Globales
    //--------------------------------------------------------------------------------------------------
    
    // Identificador del Trámite de Solicitud de Subsanación en el Catálogo
    public final static String ID_TASK_SOLICITUD_SUBSANACION = "ID_TASK_SOLICITUD_SUBSANACION";
    
    // Estado Administrativo DOCUMENTACION COMPLETA
    public final static String ESTADOADM_DOC_COMPLETA = "ESTADOADM_DOC_COMPLETA";
    
    // Estado Administrativo DOCUMENTACION INCOMPLETA
    public final static String ESTADOADM_DOC_INCOMPLETA = "ESTADOADM_DOC_INCOMPLETA";
    
    // Estado Administrativo INICIAL
    public final static String ESTADOADM_INICIAL = "ESTADO_ADM_INICIAL";
    
    // Identificador de la Fase Archivo en el Catalogo
    public final static String ID_STAGE_ARCHIVO = "ID_STAGE_ARCHIVO";
    
    // Identificador del Procedimiento de Convocatoria Subvenciones
    public final static String ID_PCD_CONVOCATORIA_SUBVENCIONES = "ID_PCD_CONVOCATORIA_SUBVENCIONES";
    
    // Identificador de calendario por defecto
    public final static String DEFAULT_CALENDAR_ID = "DEFAULT_CALENDAR_ID";
    
    // Idiomas soportados (ejemplo: es;gl;eu;ca)
    public final static String LANGUAGES = "LANGUAGES";

    // XML con los componentes web de la bandeja de entrada
    public final static String INBOX_WEB_COMPONENTS = "INBOX_WEB_COMPONENTS";
    
    // Período de recarga de la bandeja de entrada
    public final static String INBOX_RELOAD_PERIOD = "INBOX_RELOAD_PERIOD";
    public final static String DEFAULT_INBOX_RELOAD_PERIOD = "300000"; // 5 minutos
    
	// Flag que indica si se deben archivar automáticamente los registros
	// distribuidos al crear un expediente o al anexar sus documentos a un
	// expediente existente    
    public final static String INTRAY_AUTO_ARCHIVING = "INTRAY_AUTO_ARCHIVING";

    public final static String USE_ODT_TEMPLATES = "USE_ODT_TEMPLATES";
    
    //--------------------------------------------------------------------------------------------------
    
    public static String getVarGlobal(IClientContext cct, String varName) throws ISPACException {
		
    	String valor = "";
		IEntitiesAPI entitiesAPI = cct.getAPI().getEntitiesAPI();

		IItemCollection collection = entitiesAPI.queryEntities(SpacEntities.SPAC_VARS, "WHERE NOMBRE = '"
						+ DBUtil.replaceQuotes(varName) + "'");
		Iterator it = collection.iterator();
		if (it.hasNext()) {
			valor = ((IItem) it.next()).getString("VALOR");
		}

		return valor;
	}

    public static String getVarGlobal(IClientContext cct, String varName, String defaultValue) throws ISPACException {
		
    	String value = getVarGlobal(cct, varName);
    	
		if (StringUtils.isBlank(value)) {
			value = defaultValue;
		}

		return value;
	}

    public static boolean getVarGlobalBoolean(IClientContext cct, String varName, boolean defaultValue) throws ISPACException {
    	String value = getVarGlobal(cct, varName);
    	
    	if (StringUtils.equalsIgnoreCase(value, "si") || StringUtils.equalsIgnoreCase(value, "yes") || StringUtils.equalsIgnoreCase(value, "true")){
    		return true;
    	}else if (StringUtils.equalsIgnoreCase(value, "no") || StringUtils.equalsIgnoreCase(value, "false")){
    		return false;
    	}
    	return defaultValue;
    }
    
    public static String[] getLanguages(IClientContext cct ) throws ISPACException {
    	
		String[] languages = null;

		String varLanguages = getVarGlobal(cct, LANGUAGES);
		if (StringUtils.isEmpty(varLanguages)) {
			languages = new String[] { "es" };
		} else {
			languages = varLanguages.split(";");
		}

		return languages;
	}
    
    public static List getLanguages(IClientContext cct , MessageResources messageResources) throws ISPACException{
    	
    	List listLanguages = new LinkedList(); 
 		// Idiomas soportados
        String[] languages =getLanguages(cct);
    	Properties propertiesLanguages = getPropertiesLanguage();
    	    
    	for (int i=0; i<languages.length; i++ ) {
        	
    		IItem itemLanguage = new GenericItem(propertiesLanguages, "CLAVE");
        	itemLanguage.set("CLAVE", languages[i]);
        	itemLanguage.set("IDIOMA",	messageResources.getMessage(cct.getLocale(), "language."+languages[i]));
        	listLanguages.add(itemLanguage);
        }
        
        // Lista de objetos para la vista
    	IItemCollection collectionLanguagesRequest = new ListCollection(listLanguages);  	
    	return CollectionBean.getBeanList(collectionLanguagesRequest);

    }
    
    private static Properties getPropertiesLanguage() {
    	
        int ordinal = 0;
        Properties properties = new Properties();
        properties.add( new Property(ordinal++, "CLAVE", Types.VARCHAR));
        properties.add( new Property(ordinal++, "IDIOMA", Types.VARCHAR));

        return properties;
    } 
    
}