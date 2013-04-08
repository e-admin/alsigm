package es.ieci.plusvalias.ws;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.StringUtils;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.transport.context.TransportContext;
import org.springframework.ws.transport.context.TransportContextHolder;
import org.springframework.ws.transport.http.HttpServletConnection;

import es.ieci.plusvalias.config.Configuration;
import es.ieci.plusvalias.exception.ErrorCode;
import es.ieci.plusvalias.exception.PlusvaliaException;

/**
 * @author Antonio Maria
 * @version 18/03/2008
 */
public class IpValidatorInterceptor implements EndpointInterceptor
{

	public static final Logger logger = Logger.getLogger(IpValidatorInterceptor.class);

	public boolean handleFault(MessageContext messageContext, Object endpoint) throws Exception
	{
		return true;
	}

	public boolean handleRequest(MessageContext messageContext, Object endpoint) throws Exception
	{

		TransportContext context = TransportContextHolder.getTransportContext();
		HttpServletConnection conn = (HttpServletConnection)context.getConnection();
		HttpServletRequest request = conn.getHttpServletRequest();

		String remoteIp = request.getRemoteAddr();

		String ipsAllowed=Configuration.getServicePlusvaliasAlowIPs();
		if (StringUtils.hasText(ipsAllowed) && ipsAllowed.indexOf(remoteIp) == -1)
		{
			throw new PlusvaliaException(ErrorCode.NOT_VALID_IP);
		}
		
		return true;
	}

	public boolean handleResponse(MessageContext messageContext, Object endpoint) throws Exception
	{
		return true;
	}

	public void afterCompletion(MessageContext arg0, Object arg1, Exception arg2)
			throws Exception {
		
	}
}