package ieci.tecdoc.sgm.pe.struts.action;
/*
 *  $Id: RealizarPagoAction.java,v 1.2.2.2 2008/03/14 11:24:13 jnogales Exp $
 */
import ieci.tecdoc.sgm.base.base64.Base64Util;
import ieci.tecdoc.sgm.base.miscelanea.Goodies;
import ieci.tecdoc.sgm.core.services.pago.Liquidacion;
import ieci.tecdoc.sgm.core.services.pago.Pago;
import ieci.tecdoc.sgm.pe.ConfiguracionComun;
import ieci.tecdoc.sgm.pe.exception.PagoElectronicoExcepcion;
import ieci.tecdoc.sgm.pe.struts.Constantes;
import ieci.tecdoc.sgm.pe.struts.FormCreator;
import ieci.tecdoc.sgm.pe.struts.PagoElectronicoManagerHelper;
import ieci.tecdoc.sgm.pe.pasarela.ConectorPasarelaPagoFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;


/**
 * Acción encargada de realizar el pago de una liquidación.
 */
public class RealizarPagoAction extends PagoElectronicoWebAction {

	private static final Logger logger = Logger.getLogger(RealizarPagoAction.class);
	
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
			oLiquidacion = PagoElectronicoManagerHelper.obtenerDatosLiquidacion(request, oForm);			
			// Recuperamos los datos que se han firmado
			String cXMLDatosFirmados = (String)oForm.get(PagoElectronicoManagerHelper.CAMPO_FIRMA_SOLICITUD);
			// Recuperamos la firma
			String cFirma = Base64Util.decodeToString((String)oForm.get(PagoElectronicoManagerHelper.CAMPO_FIRMA_SOLICITUD));
			// Creamos el documento de solicitud de pago
			String cSolicitud = PagoElectronicoManagerHelper.obtenerDocumentoSolicitudPago(cXMLDatosFirmados, cFirma);
			oLiquidacion.setSolicitud(Goodies.fromStrToUTF8(cSolicitud));
			PagoElectronicoManagerHelper.actualizarLiquidacion(request, oLiquidacion);
			oPago = PagoElectronicoManagerHelper.realizarPago(request, oForm);
			if(ConfiguracionComun.usarPasarelaPagoExternaConRedireccion()){
				if(oPago.getPeticionPagoPasarelaExternaConRedireccion()==null) 
					throw new PagoElectronicoExcepcion(PagoElectronicoExcepcion.EC_INVOCANDO_SERVICO_PAGO_EXTERNO);
				request.setAttribute(ieci.tecdoc.sgm.pe.Constantes.KEY_REQUEST_PASARELA_REFERENCIA_PETICION_PAGO, oPago.getReferencia());
				request.setAttribute(ieci.tecdoc.sgm.pe.Constantes.KEY_REQUEST_PASARELA_XML_PETICION_PAGO, oPago.getPeticionPagoPasarelaExternaConRedireccion());
				ConectorPasarelaPagoFactory.getConectorPasarelaPago().redireccionarAPasarela(request, response);
			}else{
				String cXMLDatos = PagoElectronicoManagerHelper.obtenerDocumentoPago(request, oPago);
				cDocumentoPago = FormCreator.crearFinalPago(oLiquidacion.getTasa(), cXMLDatos, request);
			}
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			request.setAttribute(Constantes.ERROR_KEY, e.getMessage());			
			return mapping.findForward(Constantes.ERROR_FORWARD);
		}
		
		request.setAttribute(Constantes.DOC_PAGO_KEY, cDocumentoPago);
		request.setAttribute(ieci.tecdoc.sgm.pe.Constantes.REFERENCIA_KEY, (String)oForm.get(PagoElectronicoManagerHelper.CAMPO_REFERENCIA));
		return mapping.findForward(Constantes.SUCCESS_FORWARD);
	}
}