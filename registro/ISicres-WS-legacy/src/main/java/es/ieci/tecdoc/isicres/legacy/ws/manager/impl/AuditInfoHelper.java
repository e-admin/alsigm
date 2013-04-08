package es.ieci.tecdoc.isicres.legacy.ws.manager.impl;

import javax.servlet.http.HttpServletRequest;

import org.apache.cxf.message.Message;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.apache.cxf.transport.http.AbstractHTTPDestination;

import es.ieci.tecdoc.fwktd.audit.integration.business.vo.AuditContext;
import es.ieci.tecdoc.fwktd.audit.integration.context.AuditContextHolder;

public class AuditInfoHelper {

	public static void setInfoAuditContextHolder(){
		Message message = PhaseInterceptorChain.getCurrentMessage();
		HttpServletRequest request = (HttpServletRequest)message.get(AbstractHTTPDestination.HTTP_REQUEST);

		AuditContext auditContext = new AuditContext();
		auditContext.setUserHost(request.getRemoteHost());
		auditContext.setUserIP(request.getRemoteAddr());

		AuditContextHolder.setAuditContext(auditContext);
	}
}
