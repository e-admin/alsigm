package ieci.tecdoc.sgm.pe.struts.action;
/*
 *  $Id: CargarFormularioAutoLiquidacionAction.java,v 1.3.2.1 2008/02/05 13:33:23 jconca Exp $
 */
import ieci.tecdoc.sgm.core.services.pago.Tasa;
import ieci.tecdoc.sgm.pe.ConfiguracionComun;
import ieci.tecdoc.sgm.pe.struts.Constantes;
import ieci.tecdoc.sgm.pe.struts.FormCreator;
import ieci.tecdoc.sgm.pe.struts.PagoElectronicoManagerHelper;
import ieci.tecdoc.sgm.pe.struts.cert.UserCertificateUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

/**
 * Acción encargada de la carga de un formulario de autoliquidación
 *
 */
public class CargarFormularioAutoLiquidacionAction extends PagoElectronicoWebAction {

	private static final Logger logger = Logger.getLogger(CargarFormularioAutoLiquidacionAction.class);
	
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
		
		DynaValidatorForm oForm = (DynaValidatorForm)form;
		
		String cFormulario = null;
		Tasa oTasa = null;
		try {
			oTasa = PagoElectronicoManagerHelper.obtenerDatosTasa(request, oForm);
			String cXMLDatos = PagoElectronicoManagerHelper.obtenerDocumentoPago(request, oForm);
			cXMLDatos = PagoElectronicoManagerHelper
								.incluirInformacionCertificado(
										UserCertificateUtil.getUserData(request), 
										cXMLDatos);
			cFormulario = FormCreator.crearFormularioLiquidacion(oTasa, cXMLDatos, request);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			request.setAttribute(ERROR_KEY, e.getMessage());
			return mapping.findForward(Constantes.ERROR_FORWARD);		
		}
		request.setAttribute(FORMULARIO_KEY, cFormulario);
		
		if(ConfiguracionComun.usarPasarelaPagoExternaConRedireccion()) return mapping.findForward(Constantes.PASARELA_FORWARD); 
		return mapping.findForward(Constantes.SUCCESS_FORWARD);
	}
}