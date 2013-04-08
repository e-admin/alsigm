package ieci.tecdoc.sgm.pe.struts.action;
/*
 *  $Id: RealizarPagoAutoliquidacionAction.java,v 1.2.2.1 2008/02/05 13:33:23 jconca Exp $
 */
import ieci.tecdoc.sgm.core.services.pago.Liquidacion;
import ieci.tecdoc.sgm.core.services.pago.Pago;
import ieci.tecdoc.sgm.pe.struts.FormCreator;
import ieci.tecdoc.sgm.pe.struts.PagoElectronicoManagerHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;


/**
 * Acción encargada de realizar el pago de una autoliquidación 
 */
public class RealizarPagoAutoliquidacionAction extends PagoElectronicoWebAction {

	private static final Logger logger = Logger.getLogger(RealizarPagoAutoliquidacionAction.class);
	
	private static final String ERROR_FORWARD = "error";
	private static final String SUCCESS_FORWARD = "success";
	private static final String DOC_PAGO_KEY = "documentoPago";
	private static final String REFERENCIA_KEY = "referencia";
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
		
		DynaValidatorForm oForm = (DynaValidatorForm)form;
		String cDocumentoPago = null;
		Liquidacion oLiquidacion = null;
		Pago oPago = null;
		try {
			oLiquidacion = PagoElectronicoManagerHelper.altaLiquidacionAL3(request, oForm); 
			oPago = PagoElectronicoManagerHelper.obtenerPagoBean(oForm);
			oPago.setAcreditacion(Pago.ACREDITACION_NO_TERCERO_AUTORIZADO);
			oPago.setReferencia(oLiquidacion.getReferencia());
			oPago.setLiquidacion(oLiquidacion);
			oPago = PagoElectronicoManagerHelper.realizarPago(request, oPago);
			String cXMLDatos = PagoElectronicoManagerHelper.obtenerDocumentoPago(request, oPago);
			cDocumentoPago = FormCreator.crearFinalPago(oLiquidacion.getTasa(), cXMLDatos, request);
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			request.setAttribute(ERROR_KEY, e.getMessage());			
			return mapping.findForward(ERROR_FORWARD);
		}
		
		request.setAttribute(DOC_PAGO_KEY, cDocumentoPago);
		request.setAttribute(REFERENCIA_KEY, oLiquidacion.getReferencia());
		return mapping.findForward(SUCCESS_FORWARD);
	}
}