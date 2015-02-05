package ieci.tecdoc.sgm.pe.struts.action;
/*
 *  $Id: MostrarLiquidacionPararFirmarAction.java,v 1.2.2.2 2008/03/14 11:24:13 jnogales Exp $
 */
import ieci.tecdoc.sgm.base.base64.Base64Util;
import ieci.tecdoc.sgm.base.miscelanea.Goodies;
import ieci.tecdoc.sgm.core.services.pago.Liquidacion;
import ieci.tecdoc.sgm.pe.struts.FormCreator;
import ieci.tecdoc.sgm.pe.struts.PagoElectronicoManagerHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.axis.utils.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

/**
 * Acción encargada de mostrar una liquidación preparada para ser firmada por el ciudadano.
 */
public class MostrarLiquidacionPararFirmarAction extends PagoElectronicoWebAction {

	private static final Logger logger = Logger.getLogger(MostrarLiquidacionPararFirmarAction.class);
	
	private static final String ERROR_FORWARD = "error";
	private static final String SUCCESS_FORWARD = "success";
	private static final String DOC_PAGO_KEY = "documentoPago";
	private static final String DATOS_PARA_FIRMAR_KEY = "datosParaFirmar";
	private static final String DATOS_FIRMADOS = "datosFirmados";
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
		String cXMLDatos = null;
		byte[] datosAFirmar = null;
		String cDatosAFirmar = null;
		try {
			
			oLiquidacion = PagoElectronicoManagerHelper.obtenerDatosLiquidacion(request, oForm);
//			String fechaCaducidad=(String)oForm.get(PagoElectronicoManagerHelper.CAMPO_FEC_CADUCIDAD_TARJETA_CREDITO);
//			if(StringUtils.isEmpty(fechaCaducidad)) 
//				oForm.set(PagoElectronicoManagerHelper.CAMPO_FEC_CADUCIDAD_TARJETA_CREDITO, oLiquidacion.getVencimiento());
			cXMLDatos = PagoElectronicoManagerHelper.obtenerDocumentoPago(request, oForm);
			
			cDocumentoPago = FormCreator.crearResumenPago(oLiquidacion.getTasa(), cXMLDatos, request);
			datosAFirmar = Goodies.fromStrToUTF8(cXMLDatos);
			cDatosAFirmar = Base64Util.encode(datosAFirmar);
			cDatosAFirmar = cDatosAFirmar.replaceAll("\n", "");
			cDatosAFirmar = cDatosAFirmar.replaceAll("\r", "");			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			request.setAttribute(ERROR_KEY, e.getMessage());	
			return mapping.findForward(ERROR_FORWARD);
		}
		
		request.setAttribute(DOC_PAGO_KEY, cDocumentoPago);
		request.setAttribute(DATOS_PARA_FIRMAR_KEY, cDatosAFirmar);
		request.setAttribute(DATOS_FIRMADOS, cXMLDatos);
		return mapping.findForward(SUCCESS_FORWARD);
	}
}