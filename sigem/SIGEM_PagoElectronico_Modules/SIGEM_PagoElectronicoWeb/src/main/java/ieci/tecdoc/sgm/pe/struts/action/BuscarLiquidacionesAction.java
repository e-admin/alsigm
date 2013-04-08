package ieci.tecdoc.sgm.pe.struts.action;
/*
 *  $Id: BuscarLiquidacionesAction.java,v 1.3.2.1 2008/02/05 13:33:23 jconca Exp $
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
import org.apache.struts.validator.DynaValidatorForm;

/**
 * Acción encargada de la búsqueda de liquidaciones.
 *
 */
public class BuscarLiquidacionesAction extends PagoElectronicoWebAction {

	private static final Logger logger = Logger.getLogger(BuscarLiquidacionesAction.class);
	
	private static final String SUCCESS_FORWARD =	"success";
	private static final String ERROR_FORWARD =		"error";
	private static final String LISTA_LIQUIDACIONES_KEY =	"liquidaciones";
	
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
				
		DynaValidatorForm buscarLiquidacionesForm = (DynaValidatorForm) form;

		// obtenemos las liquidaciones pendientes
		List oLista = null;
		try {
			oLista = PagoElectronicoManagerHelper
						.buscarLiquidaciones(request, 
								buscarLiquidacionesForm, 
								UserCertificateUtil.getUserData(request));
		} catch (SigemException e) {
			logger.error(e.getMessage(), e);
			request.setAttribute(ERROR_KEY, e.getMessage());			
			return mapping.findForward(ERROR_FORWARD);			
		}
		request.setAttribute(LISTA_LIQUIDACIONES_KEY, oLista);

		
		return mapping.findForward(SUCCESS_FORWARD);
	}
}