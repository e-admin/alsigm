package es.ieci.tecdoc.fwktd.sir.ws.service.multiEntity.cxf;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.cxf.Bus;
import org.apache.cxf.common.classloader.ClassLoaderUtils;
import org.apache.cxf.common.classloader.ClassLoaderUtils.ClassLoaderHolder;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.resource.ResourceManager;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.apache.cxf.transport.http.DestinationRegistry;
import org.apache.cxf.transport.servlet.ServletController;
import org.apache.cxf.transports.http.QueryHandler;
import org.apache.cxf.transports.http.QueryHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ieci.tecdoc.fwktd.sir.ws.service.multiEntity.UrlEndPointMultiEntityParser;

/**
 * Basada en implementacion Cxf 2.5.2 {@link org.apache.cxf.transport.servlet.ServletController}
 * 
 * Las modificaciones realizadas se basan en la llamada a {@link es.ieci.tecdoc.fwktd.sir.ws.service.multiEntity.UrlEndPointMultiEntityParser}
 * @author IECISA
 *
 */
public class ServletControllerCXFMultiEntity extends ServletController {
	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = LoggerFactory.getLogger(ServletControllerCXFMultiEntity.class);

	public ServletControllerCXFMultiEntity(
			DestinationRegistry destinationRegistry, ServletConfig config,
			HttpServlet serviceListGenerator) {
		super(destinationRegistry, config, serviceListGenerator);
	}
	
	
	 public void invoke(HttpServletRequest request, HttpServletResponse res) throws ServletException {
	        try {
	            String serveltPathInfo = request.getPathInfo() == null ? "" : request.getPathInfo();
	            //obtenemos el nombre del  servicio final
	        	String pathInfo = UrlEndPointMultiEntityParser.getPathTargetService(serveltPathInfo);
	        	//seteamos la configuración de multientidad
	        	UrlEndPointMultiEntityParser.setUpMultiEntity(serveltPathInfo);
	        	
	            AbstractHTTPDestination d = destinationRegistry.getDestinationForPath(pathInfo, true);

	            if (d == null) {
	                if (!isHideServiceList && (request.getRequestURI().endsWith(serviceListRelativePath)
	                    || request.getRequestURI().endsWith(serviceListRelativePath + "/")
	                    || StringUtils.isEmpty(pathInfo)
	                    || "/".equals(pathInfo))) {
	                    setBaseURLAttribute(request);
	                    serviceListGenerator.service(request, res);
	                } else {
	                    d = destinationRegistry.checkRestfulRequest(pathInfo);
	                    if (d == null || d.getMessageObserver() == null) {
	                       logger.warn("Can't find the the request for "+ request.getRequestURL() + "'s Observer ");
	                        generateNotFound(request, res);
	                    }  else { // the request should be a restful service request
	                        updateDestination(request, d);
	                        invokeDestination(request, res, d);
	                    }
	                }
	            } else {
	                EndpointInfo ei = d.getEndpointInfo();
	                Bus bus = d.getBus();
	                ClassLoaderHolder orig = null;
	                try {
	                    ResourceManager manager = bus.getExtension(ResourceManager.class);
	                    if (manager != null) {
	                        ClassLoader loader = manager.resolveResource("", ClassLoader.class);
	                        if (loader != null) {
	                            //need to set the context classloader to the loader of the bundle
	                            orig = ClassLoaderUtils.setThreadContextClassloader(loader);
	                        }
	                    }
	                    QueryHandlerRegistry queryHandlerRegistry = bus.getExtension(QueryHandlerRegistry.class);

	                    if (!StringUtils.isEmpty(request.getQueryString()) && queryHandlerRegistry != null) {
	                        
	                        // update the EndPoint Address with request url
	                        if ("GET".equals(request.getMethod())) {
	                            updateDestination(request, d);
	                        }
	                        
	                        String ctxUri = request.getPathInfo();
	                        String baseUri = request.getRequestURL().toString()
	                            + "?" + request.getQueryString();

	                        QueryHandler selectedHandler = 
	                            findQueryHandler(queryHandlerRegistry, ei, ctxUri, baseUri);
	                        
	                        if (selectedHandler != null) {
	                            respondUsingQueryHandler(selectedHandler, res, ei, ctxUri, baseUri);
	                            return;
	                        }
	                    } else {
	                        updateDestination(request, d);
	                    }
	                    invokeDestination(request, res, d);
	                } finally {
	                    if (orig != null) { 
	                        orig.reset();
	                    }
	                }
	                
	            }
	        } catch (IOException e) {
	            throw new ServletException(e);
	        }
	    }

}
