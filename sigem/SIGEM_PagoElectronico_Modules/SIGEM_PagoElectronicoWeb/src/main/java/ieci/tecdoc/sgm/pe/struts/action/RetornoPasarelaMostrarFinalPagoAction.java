package ieci.tecdoc.sgm.pe.struts.action;

import ieci.tecdoc.sgm.core.services.pago.Liquidacion;
import ieci.tecdoc.sgm.pe.Pago;
import ieci.tecdoc.sgm.pe.SistemaPagoElectronicoFactory;
import ieci.tecdoc.sgm.pe.SistemaPasarelaPagoElectronico;
import ieci.tecdoc.sgm.pe.struts.Constantes;
import ieci.tecdoc.sgm.pe.struts.FormCreator;
import ieci.tecdoc.sgm.pe.struts.PagoElectronicoManagerHelper;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class RetornoPasarelaMostrarFinalPagoAction extends Action{
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		String cDocumentoPago=null;
		String referencia=(String)request.getParameter(ieci.tecdoc.sgm.pe.Constantes.REFERENCIA_KEY);
		String idPago=(String)request.getParameter(ieci.tecdoc.sgm.pe.Constantes.ID_PAGO_KEY);
		try{
			Liquidacion oLiquidacion=PagoElectronicoManagerHelper.obtenerDatosLiquidacion(request, referencia);
			SistemaPasarelaPagoElectronico conectorPasarelaPago=(SistemaPasarelaPagoElectronico)SistemaPagoElectronicoFactory.getInstance().obtenerSistemaPagoElectronico();
			String nrc=conectorPasarelaPago.getNRCByIdPago(idPago);
			oLiquidacion.setEstado(Pago.ESTADO_PAGADO);
			oLiquidacion.setNrc(nrc);
			oLiquidacion.setFechaPago(Calendar.getInstance().getTime());
			PagoElectronicoManagerHelper.actualizarLiquidacion(request, oLiquidacion);
			
			String cXMLDatos = PagoElectronicoManagerHelper.obtenerDocumentoPago(request, referencia);
			cDocumentoPago = FormCreator.crearFinalPago(oLiquidacion.getTasa(), cXMLDatos, request);
		}catch (Exception e) {
			Logger.getLogger(this.getClass()).error(e.getMessage(), e);
			request.setAttribute(Constantes.ERROR_KEY, e.getMessage());			
			return mapping.findForward(Constantes.ERROR_FORWARD);
		}
		request.setAttribute(Constantes.DOC_PAGO_KEY, cDocumentoPago);
		request.setAttribute(ieci.tecdoc.sgm.pe.Constantes.REFERENCIA_KEY, referencia);
		return mapping.findForward(Constantes.SUCCESS_FORWARD);
	}
	
	
}
