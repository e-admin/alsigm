package ieci.tecdoc.sgm.ct.struts.action;
/*
 *  $Id: DetalleExpedienteAction.java,v 1.7.2.3 2008/05/29 15:08:46 jnogales Exp $
 */
import ieci.tecdoc.sgm.core.exception.SigemException;
import ieci.tecdoc.sgm.core.services.ConstantesServicios;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.consulta.ConsultaExpedientesException;
import ieci.tecdoc.sgm.core.services.consulta.Expediente;
import ieci.tecdoc.sgm.core.services.consulta.FicherosHito;
import ieci.tecdoc.sgm.core.services.consulta.FicherosHitos;
import ieci.tecdoc.sgm.core.services.consulta.HitoExpediente;
import ieci.tecdoc.sgm.core.services.consulta.HitosExpediente;
import ieci.tecdoc.sgm.core.services.consulta.Notificaciones;
import ieci.tecdoc.sgm.core.services.consulta.Pagos;
import ieci.tecdoc.sgm.core.services.consulta.ServicioConsultaExpedientes;
import ieci.tecdoc.sgm.core.services.consulta.Subsanaciones;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.user.web.ConstantesSesionUser;
import ieci.tecdoc.sgm.ct.exception.ConsultaCodigosError;
import ieci.tecdoc.sgm.ct.struts.form.DetalleExpedienteForm;
import ieci.tecdoc.sgm.ct.utilities.Misc;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class DetalleExpedienteAction extends ConsultaWebAction {

	/**
	 * Se sobrescribe el metodo execute de la clase Action
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	 public ActionForward executeAction(
	        ActionMapping mapping,
	        ActionForm form,
	        HttpServletRequest request,
	        HttpServletResponse response) {

		HttpSession session = request.getSession();
		DetalleExpedienteForm detalleExpedienteForm = (DetalleExpedienteForm) form;

	    try{
	    	String sessionId = request.getParameter(Misc.SESION_ID);
			if(Misc.isEmpty(sessionId)) {
				sessionId = (String) session.getAttribute(ConstantesSesionUser.ID_SESION);
			}

			String cnif = Misc.getCNIFUsuario(request, sessionId);
	    	String id = request.getParameter("id");
	    	String reg = request.getParameter("registro");

	    	Entidad entidad = Misc.obtenerEntidad(request);
	    	ServicioConsultaExpedientes oServicio = LocalizadorServicios.getServicioConsultaExpedientes();

	    	// Comprobar que el expediente pertenezca al usuario conectado
	    	Expediente expediente = oServicio.busquedaExpediente(cnif, id, entidad);
	    	if ((expediente == null) || (StringUtils.isBlank(expediente.getNumero()))) {
	    		StringBuffer cCodigo = new StringBuffer(ConstantesServicios.SERVICE_QUERY_EXPS_ERROR_PREFIX);
				cCodigo.append(String.valueOf(ConsultaCodigosError.EC_NUMERO_EXPEDIENTE_INCORRECTO));
	    		throw new ConsultaExpedientesException(Long.valueOf(cCodigo.toString()).longValue());
	    	}

	    	HitoExpediente hitoEstado = oServicio.obtenerHitoEstado(id, entidad);
	    	if (hitoEstado != null)
	    		hitoEstado.setDescripcion(Misc.obtenerMensaje(request, hitoEstado.getDescripcion()));
	    	detalleExpedienteForm.setHitoEstado(hitoEstado);
	    	detalleExpedienteForm.setFicherosEstado(
	    			Misc.modificarMensajesFicherosHito(
	    					request, oServicio.obtenerFicherosHito(hitoEstado.getGuid(), entidad)).getFicheros()
	    			);

	    	//Se establecen los pagos, notificaciones y subsanaciones del hito actual
	    	try{
	    		Subsanaciones subsActual = oServicio.obtenerSubsanacionesHitoActual(id, entidad);
	    		Misc.modificarMensajesSubsanaciones(request, subsActual);
	    		detalleExpedienteForm.setSubsActual(subsActual);
	    	}catch(Exception ex1){
	    		detalleExpedienteForm.setSubsActual(new Subsanaciones());
	    	}
	    	try{
	    		Notificaciones notifsActual = oServicio.obtenerNotificionesHitoActual(id, entidad);
	    		Misc.modificarMensajesNotificaciones(request, notifsActual);
	    		detalleExpedienteForm.setNotifsActual(notifsActual);
	    	}catch(Exception ex1){
	    		detalleExpedienteForm.setNotifsActual(new Notificaciones());
	    	}
	    	try{
	    		Pagos pagosActual = oServicio.obtenerPagosHitoActual(id, entidad);
	    		Misc.modificarMensajesPagos(request, pagosActual);
	    		detalleExpedienteForm.setPagosActual(pagosActual);
	    	}catch(Exception ex1){
	    		detalleExpedienteForm.setPagosActual(new Pagos());
	    	}

	    	HitosExpediente hitos = oServicio.obtenerHistoricoExpediente(id, entidad);
	    	HitosExpediente hitosRev = new HitosExpediente();
	    	for(int i=0; i<hitos.count(); i++) {
	    		if (hitos.get(hitos.count()-i-1) != null)
	    			hitos.get(hitos.count()-i-1).setDescripcion(Misc.obtenerMensaje(request, (hitos.get(hitos.count()-i-1)).getDescripcion()));
	    		hitosRev.add(hitos.get(hitos.count()-i-1));
	    	}
	    	detalleExpedienteForm.setHitos(hitosRev.getHitosExpediente());
	    	detalleExpedienteForm.setFicherosHashtable(obtenerFicherosHitos(request, hitosRev, entidad));

	    	ArrayList subsanaciones = new ArrayList();
	    	for(int i=0; i<hitos.count(); i++){
	    		String idHito = ((HitoExpediente)hitos.get(hitos.count()-i-1)).getGuid();
	    		try{
	    			Subsanaciones subsHist = oServicio.obtenerSubsanacionesHito(idHito, entidad);
	    			Misc.modificarMensajesSubsanaciones(request, subsHist);
	    			subsanaciones.add(subsHist);
	    		}catch(Exception ex2){
	    			subsanaciones.add(new Subsanaciones());
	    		}
	    	}
	    	ArrayList notificaciones = new ArrayList();
	    	for(int i=0; i<hitos.count(); i++){
	    		String idHito = ((HitoExpediente)hitos.get(hitos.count()-i-1)).getGuid();
	    		try{
	    			Notificaciones notifsHist = oServicio.obtenerNotificionesHito(idHito, entidad);
	    			Misc.modificarMensajesNotificaciones(request, notifsHist);
	    			notificaciones.add(notifsHist);
	    		}catch(Exception ex2){
	    			notificaciones.add(new Notificaciones());
	    		}
	    	}
	    	ArrayList pagos = new ArrayList();
	    	for(int i=0; i<hitos.count(); i++){
	    		String idHito = ((HitoExpediente)hitos.get(hitos.count()-i-1)).getGuid();
	    		try{
	    			Pagos pagosHist = oServicio.obtenerPagosHito(idHito, entidad);
	    			Misc.modificarMensajesPagos(request, pagosHist);
	    			pagos.add(pagosHist);
	    		}catch(Exception ex2){
	    			pagos.add(new Pagos());
	    		}
	    	}
	    	detalleExpedienteForm.setSubsHistoricas(subsanaciones);
	    	detalleExpedienteForm.setNotifsHistoricas(notificaciones);
	    	detalleExpedienteForm.setPagosHistoricas(pagos);
	    	detalleExpedienteForm.setURLAportacion(oServicio.obtenerURLAportacionExpedientes());
	    	detalleExpedienteForm.setURLNotificacion(oServicio.obtenerURLNotificacionExpedientes());
	    	detalleExpedienteForm.setURLPago(oServicio.obtenerURLPagoTasas());
	    	detalleExpedienteForm.setNumeroExpediente(id);
	    	detalleExpedienteForm.setNumeroRegistro(reg);

	    	request.setAttribute("busqueda", request.getParameter("busqueda"));
	    } catch (Exception ex){
	    	request.setAttribute(Misc.MENSAJE_ERROR, ex.getMessage());
	    	return mapping.findForward("Failure");
	    }
	    return mapping.findForward("Success");
	 }

	 private Hashtable obtenerFicherosHitos(HttpServletRequest request, HitosExpediente hitos, Entidad entidad) throws SigemException{
			ServicioConsultaExpedientes oServicio = LocalizadorServicios.getServicioConsultaExpedientes();
			FicherosHitos ofichs = oServicio.obtenerFicherosHitos(hitos, entidad);
			Hashtable oRetorno = new Hashtable();
			if((ofichs != null) && (ofichs.getFicherosHitos() != null)){
				Iterator oIterador = ofichs.getFicherosHitos().iterator();
				FicherosHito oFichsHito = null;
				while(oIterador.hasNext()){
					oFichsHito = (FicherosHito)oIterador.next();
					if (oFichsHito.getGuidHito() != null)
						oRetorno.put(oFichsHito.getGuidHito(), Misc.modificarMensajesFicherosHito(request, oFichsHito));
				}
			}
			return oRetorno;
//			Hashtable otable = GestorConsulta.obtenerFicherosHitos(getHitosExpedienteImpl(hitos));
//			Hashtable oRetorno = new Hashtable();
//			Enumeration oKeys = otable.keys();
//			String key = null;
//			while(oKeys.hasMoreElements()){
//				key = (String)oKeys.nextElement();
//				oRetorno.put(key, getFicherosHitoServicio((ieci.tecdoc.sgm.ct.database.datatypes.FicherosHito)otable.get(key)));
//			}
	 }

}
