package ieci.tecdoc.sgm.pe.struts.action;
/*
 *  $Id: ConsultaAutoliquidacionAction.java,v 1.2.2.1 2008/02/05 13:33:23 jconca Exp $
 */
import ieci.tecdoc.sgm.core.services.pago.Liquidacion;
import ieci.tecdoc.sgm.pe.struts.FormCreator;
import ieci.tecdoc.sgm.pe.struts.PagoElectronicoManagerHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Acción encargada de la consulta de autoliquidaciones. 
 */
public class ConsultaAutoliquidacionAction extends PagoElectronicoWebAction {

	private static final Logger logger = Logger.getLogger(ConsultaAutoliquidacionAction.class);
	
	private static final String ERROR_FORWARD = "error";
	private static final String SUCCESS_FORWARD = "success";
	private static final String REFEENCIA_KEY = "referencia";
	private static final String DOC_PAGO_KEY = "documentoPago";	
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
		
		String cReferencia = (String)request.getParameter(REFEENCIA_KEY);
		String cDocumentoPago = null;
		Liquidacion oLiquidacion = null;		
		try {
			
			oLiquidacion = PagoElectronicoManagerHelper.obtenerDatosLiquidacion(request, cReferencia);
			String cXMLDatos = PagoElectronicoManagerHelper.obtenerDocumentoPago(request, cReferencia);
			cDocumentoPago = FormCreator.crearConsultaPago(oLiquidacion.getTasa(), cXMLDatos, request);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			request.setAttribute(ERROR_KEY, e.getMessage());	
			return mapping.findForward(ERROR_FORWARD);
		}
		
		request.setAttribute(DOC_PAGO_KEY, cDocumentoPago);
		request.setAttribute(REFEENCIA_KEY, cReferencia);
		return mapping.findForward(SUCCESS_FORWARD);
	}
}