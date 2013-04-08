package ieci.tecdoc.sgm.pe.struts.action;
/*
 *  $Id: LiquidacionesPendientesAction.java,v 1.3.2.1 2008/02/05 13:33:23 jconca Exp $
 */
import ieci.tecdoc.sgm.core.exception.SigemException;
import ieci.tecdoc.sgm.pe.struts.PagoElectronicoManagerHelper;
import ieci.tecdoc.sgm.pe.struts.cert.UserCertificateUtil;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * Acción encargada de la búsqueda de liquidaciones pendientes.
 *
 */
public class LiquidacionesPendientesAction extends PagoElectronicoWebAction {

	private static final Logger logger = Logger.getLogger(LiquidacionesPendientesAction.class);
	
	private static final String ERROR_FORWARD = "error";
	private static final String SUCCESS_FORWARD = "success";
	private static final String LISTA_PENDIENTES_KEY = "listaPendientes";
	private static final String ERROR_KEY 	=  "mensajeError";
	
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
		
		// obtenemos las liquidaciones pendientes
		List oListaPendientes = null;
		try {
			oListaPendientes = PagoElectronicoManagerHelper
								.obtenerListaLiquidacionesPendientesNIF(request, 
										UserCertificateUtil.getUserData(request).getM_nif());
		} catch (SigemException e) {
			logger.error(e.getMessage(), e);
			request.setAttribute(ERROR_KEY, e.getMessage());	
			return mapping.findForward(ERROR_FORWARD);			
		}
		request.setAttribute(LISTA_PENDIENTES_KEY, oListaPendientes);
		
		
		return mapping.findForward(SUCCESS_FORWARD);
	}
}