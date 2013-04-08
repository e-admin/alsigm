package es.ieci.tecdoc.fwktd.sir.ws.service.multiEntity.axis;

import org.apache.axis.AxisFault;
import org.apache.axis.MessageContext;
import org.apache.axis.components.logger.LogFactory;
import org.apache.axis.handlers.http.URLMapper;
import org.apache.axis.transport.http.HTTPConstants;
import org.apache.commons.logging.Log;
import es.ieci.tecdoc.fwktd.sir.ws.service.multiEntity.UrlEndPointMultiEntityParser;

/**
 * Mapeador de EndPoints de axis 1 para configuracion multientidad
 * 
 * Los Endpoints multientidad seran del tipo:
 * 
 * services/idEntidad/serviceName
 * 
 * Los EndPoints no multientidad seran del tipo 
 * 
 *  
 * services/serviceName
 *
 * En el caso de los EndPoints del tipo multientidad se seteará la variable {@link es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource.MultiEntityContextHolder} 
 * 
 * @author Iecisa
 *
 */
public class URLMultiEntityMapper extends URLMapper {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3476585196368920363L;
	private static String SEPARATOR="/";
	protected static Log log =
        LogFactory.getLog(URLMultiEntityMapper.class.getName());

    public void invoke(MessageContext msgContext) throws AxisFault
    {
    	if (log.isDebugEnabled()){
    		log.debug("Enter: URLMultiEntityMapper::invoke");
    	}
        

        /** If there's already a targetService then just return.
         */
        if ( msgContext.getService() == null ) {


        	String servletPathInfo=(String)msgContext.getProperty(HTTPConstants.MC_HTTP_SERVLETPATHINFO);
        	
        	//obtenemos el nombre del  servicio final
        	String path = UrlEndPointMultiEntityParser.getPathTargetService(servletPathInfo);
        	//seteamos la configuración de multientidad
        	UrlEndPointMultiEntityParser.setUpMultiEntity(servletPathInfo);
        	
        	// path may or may not start with a "/". see http://issues.apache.org/jira/browse/AXIS-1372
            if ((path != null) && (path.length() >= 1)) { //rules out the cases of path="", path=null
            	if(path.startsWith(URLMultiEntityMapper.SEPARATOR)){
            		path = path.substring(1); //chop the extra "/"
            	}

                msgContext.setTargetService(path);
            }
        }
        if (log.isDebugEnabled()){
        	log.debug("Exit: URLMultiEntityMapper::invoke");
        }
    }
    
    


}
