package es.ieci.plusvalias.ws;

import ieci.tecdoc.sgm.core.config.impl.spring.MultiEntityContextHolder;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.transport.context.TransportContext;
import org.springframework.ws.transport.context.TransportContextHolder;
import org.springframework.ws.transport.http.HttpServletConnection;

public class MultientityInterceptor implements EndpointInterceptor, ApplicationContextAware{

	public static final Logger logger = Logger.getLogger(MultientityInterceptor.class);
	
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		
	}

	public boolean handleRequest(MessageContext messageContext, Object endpoint)
			throws Exception {
		
		TransportContext context = TransportContextHolder.getTransportContext();
		HttpServletConnection connection = (HttpServletConnection )context.getConnection();
		HttpServletRequest request = connection.getHttpServletRequest();
		String requestURI = request.getRequestURI();
		int pos=requestURI.lastIndexOf("/");
		String servletNamePath=requestURI.substring(pos+1);
		pos=servletNamePath.lastIndexOf("_");
		String entidadId=servletNamePath.substring(pos+1);
		MultiEntityContextHolder.setEntity(entidadId);
		return true;
	}

	public boolean handleResponse(MessageContext messageContext, Object endpoint) throws Exception {
		return true;
	}

	public boolean handleFault(MessageContext messageContext, Object endpoint) throws Exception {
		return true;
	}

	public void afterCompletion(MessageContext arg0, Object arg1, Exception arg2)
			throws Exception {
		
	}
	
}
