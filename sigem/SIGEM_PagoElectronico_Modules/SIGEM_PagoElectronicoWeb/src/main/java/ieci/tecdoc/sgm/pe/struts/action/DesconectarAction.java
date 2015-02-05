package ieci.tecdoc.sgm.pe.struts.action;
/*
 *  $Id: DesconectarAction.java,v 1.1.2.1 2008/02/05 13:33:23 jconca Exp $
 */
import ieci.tecdoc.sgm.core.user.web.WebAuthenticationHelper;
import ieci.tecdoc.sgm.pe.struts.cert.CertInfo;
import ieci.tecdoc.sgm.pe.struts.cert.UserCertificateUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Acción de entrada a la aplicación de pago
 */
public class DesconectarAction extends PagoElectronicoWebAction {

	private static final Logger logger = Logger.getLogger(DesconectarAction.class);	
	
	/** 
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
			StringBuffer sbUrl = new StringBuffer();
			try {
				// Obtenemos la URL de desconexión
				sbUrl.append(WebAuthenticationHelper.getWebAuthDesconectURL(request));
				
				request.getSession().invalidate();
				response.sendRedirect(sbUrl.toString());
				
				return null;
			} catch (Exception e) {
				logger.error("Error obteniendo datos del usuario.");
				return mapping.findForward(GLOBAL_FORWARD_ERROR);
			}
	}
}