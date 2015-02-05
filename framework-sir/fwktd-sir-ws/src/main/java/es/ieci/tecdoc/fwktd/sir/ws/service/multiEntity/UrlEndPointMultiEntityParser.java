package es.ieci.tecdoc.fwktd.sir.ws.service.multiEntity;

import org.apache.axis.components.logger.LogFactory;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;

import es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource.MultiEntityContextHolder;

/**
 * Parser de EndPoints para configuracion multientidad
 * 
 * Los Endpoints multientidad seran del tipo:
 * 
 * /idEntidad/serviceName
 * 
 * Los EndPoints no multientidad seran del tipo 
 *   
 * /serviceName
 *
 * En el caso de los EndPoints del tipo multientidad se seteará la variable {@link es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource.MultiEntityContextHolder} 
 * 
 * @author Iecisa
 */
public class UrlEndPointMultiEntityParser {
	
	private static String SEPARATOR="/";
	protected static Log log =
        LogFactory.getLog(UrlEndPointMultiEntityParser.class.getName());

	/**
     * Método que configurará el contexto multientidad
     * @param servletPathInfo     Formatos esperados a recibir: 	"/idEntidad/serviceName"  o  	"/serviceName"
     */
    public static void setUpMultiEntity(String servletPathInfo){
    	
    	if (log.isDebugEnabled()){
    		log.debug("Enter: UrlEndPoingMultiEntityParser::setUpMultiEntity:"+servletPathInfo);
    	}
    	
    	//obtenemos la entidad
    	String entity=null;
    	if (servletPathInfo.lastIndexOf(SEPARATOR)!=-1){
    		entity=StringUtils.substringBeforeLast(servletPathInfo, SEPARATOR);
    	}
    	entity=StringUtils.removeStartIgnoreCase(entity, SEPARATOR);
    	
    	//seteamos el contexto multientidad
    	if (StringUtils.isNotEmpty(entity)){
    		MultiEntityContextHolder.setEntity(entity);
    	}
    	
    	
    	if (log.isDebugEnabled()){
    		log.debug("EXIT: UrlEndPoingMultiEntityParser::setUpMultiEntity:"+entity);
    	}
    	
    }
    
    /**
     * Método para obtener el nombre del servicio final a invocar.
     * Formatos esperados a recibir para entorno CON multientidad: 	"/idEntidad/serviceName"  
     * Formatos esperados a recibir para entorno SIN multientidad: 	
     * @param servletPathInfo     Formatos esperados a recibir: 	"/serviceName"
     */
    public static  String getPathTargetService(String servletPathInfo){
    	
    	if (log.isDebugEnabled()){
    		log.debug("Enter: UrlEndPoingMultiEntityParser::getPathTargetService:"+servletPathInfo);
    	}
    	String pathTargetService = StringUtils.substringAfterLast(servletPathInfo, "/");
    	
    	if (log.isDebugEnabled()){
    		log.debug("Exit: UrlEndPoingMultiEntityParser::getPathTargetService:"+pathTargetService);
    	}
    	return pathTargetService;
    }
}
