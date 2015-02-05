package ieci.tecdoc.sgm.pe.struts.action;
/*
 *  $Id: ReCargarFormularioLiquidacionAction.java,v 1.2.2.1 2008/02/05 13:33:23 jconca Exp $
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
import org.apache.struts.validator.DynaValidatorForm;

/**
 * Acción encargada de cargar un formulario de liquidación. 
 */
public class ReCargarFormularioLiquidacionAction extends PagoElectronicoWebAction {

	private static final Logger logger = Logger.getLogger(ReCargarFormularioLiquidacionAction.class);
	
	private static final String ERROR_FORWARD = "error";
	private static final String SUCCESS_FORWARD = "success";
	private static final String FORMULARIO_KEY = "formulario";
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
		
		String cFormulario = null;
		Liquidacion oLiquidacion = null;
		DynaValidatorForm oForm = (DynaValidatorForm)form;
		try {
			oLiquidacion = PagoElectronicoManagerHelper.obtenerDatosLiquidacion(request, oForm);
			String cXMLDatos = PagoElectronicoManagerHelper.obtenerDocumentoPago(request, oForm);
			cFormulario = FormCreator.crearFormularioLiquidacion(oLiquidacion.getTasa(), cXMLDatos, request);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			request.setAttribute(ERROR_KEY, e.getMessage());			
			return mapping.findForward(ERROR_FORWARD);		
		}
		request.setAttribute(FORMULARIO_KEY, cFormulario);
		
		return mapping.findForward(SUCCESS_FORWARD);
	}
}