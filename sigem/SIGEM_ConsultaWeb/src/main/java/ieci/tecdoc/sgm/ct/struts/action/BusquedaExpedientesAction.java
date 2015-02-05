package ieci.tecdoc.sgm.ct.struts.action;

import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.consulta.CriterioBusquedaExpedientes;
import ieci.tecdoc.sgm.core.services.consulta.Expediente;
import ieci.tecdoc.sgm.core.services.consulta.Expedientes;
import ieci.tecdoc.sgm.core.services.consulta.ServicioConsultaExpedientes;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.user.web.ConstantesSesionUser;
import ieci.tecdoc.sgm.ct.struts.form.BusquedaExpedientesForm;
import ieci.tecdoc.sgm.ct.utilities.Misc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class BusquedaExpedientesAction extends ConsultaWebAction {

	/**
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	 public ActionForward executeAction(ActionMapping mapping,
			 							ActionForm form,
			 							HttpServletRequest request,
			 							HttpServletResponse response) {

		HttpSession session = request.getSession();
		BusquedaExpedientesForm busquedaExpedientesForm = (BusquedaExpedientesForm) form;

	    try {
	    	String sessionId = request.getParameter(Misc.SESION_ID);
			if (Misc.isEmpty(sessionId)) {
				sessionId = (String) session.getAttribute(ConstantesSesionUser.ID_SESION);
			}

	    	String cnif = Misc.getCNIFUsuario(request, sessionId);
	    	busquedaExpedientesForm.setCNIF(cnif);

	    	String rebuscar = request.getParameter("rebuscar");
	    	if ("false".equals(rebuscar)) {

	    		String numExpediente = (String) session.getAttribute(Misc.EXPEDIENTE);
	    		String procedimiento = (String) session.getAttribute(Misc.PROCEDIMIENTO);
	    		String numeroRegistroInicial = (String) session.getAttribute(Misc.NUMERO_REGISTRO_INICIAL);
	    		String fechaDesde = (String) session.getAttribute(Misc.FECHA_DESDE);
	    		String operadorConsulta = (String) session.getAttribute(Misc.OPERADOR_CONSULTA);
	    		String fechaHasta = (String) session.getAttribute(Misc.FECHA_HASTA);
	    		String fechaRegistroInicialDesde = (String) session.getAttribute(Misc.FECHA_REGISTRO_INICIAL_DESDE);
	    		String operadorConsultaFechaInicial = (String) session.getAttribute(Misc.OPERADOR_CONSULTA_FECHA_INICIAL);
	    		String fechaRegistroInicialHasta = (String) session.getAttribute(Misc.FECHA_REGISTRO_INICIAL_HASTA);
	    		String estado = (String) session.getAttribute(Misc.ESTADO);

	    		Entidad entidad = Misc.obtenerEntidad(request);
	    		ServicioConsultaExpedientes oServicio = LocalizadorServicios.getServicioConsultaExpedientes();

	    		CriterioBusquedaExpedientes oCriterio = new CriterioBusquedaExpedientes();
	    		oCriterio.setNIF(cnif);
	    		oCriterio.setEstado(estado);
	    		oCriterio.setFechaDesde(fechaDesde);
	    		oCriterio.setFechaHasta(fechaHasta);
	    		oCriterio.setOperadorConsulta(operadorConsulta);

	    		oCriterio.setExpediente(numExpediente);
	    		oCriterio.setProcedimiento(procedimiento);
	    		oCriterio.setNumeroRegistroInicial(numeroRegistroInicial);
	    		oCriterio.setFechaRegistroInicialDesde(fechaRegistroInicialDesde);
	    		oCriterio.setFechaRegistroInicialHasta(fechaRegistroInicialHasta);
	    		oCriterio.setOperadorConsultaFechaInicial(operadorConsultaFechaInicial);

	    		Expedientes expedientes = oServicio.busquedaExpedientes(oCriterio, entidad);
	    		for (int i=0; i< expedientes.count(); i++){
	    			Expediente expediente = (Expediente)expedientes.get(i);

	    			if (expediente.getEstado().equals(Expediente.COD_ESTADO_EXPEDIENTE_FINALIZADO)) {
	    				expediente.setEstado("cerrado");
	    			} else {
	    				expediente.setEstado("abierto");
	    			}

	    			String proc = expediente.getProcedimiento();
	    			if (proc.length() > 60){
	    				proc = proc.substring(0,57) + "...";
	    				expediente.setProcedimiento(proc);
	    			}

	    			boolean existeNotificacion = oServicio.existenNotificaciones(expediente.getNumero(), entidad);
	    			if(existeNotificacion) {
	    				expediente.setNotificacion("S");
	    			} else {
	    				expediente.setNotificacion("N");
	    			}

	    			boolean existeSubsanacion = oServicio.existenSubsanaciones(expediente.getNumero(), entidad);
	    			if(existeSubsanacion) {
	    				expediente.setAportacion("S");
	    			} else {
	    				expediente.setAportacion("N");
	    			}

	    			boolean existePago = oServicio.existenPagos(expediente.getNumero(), entidad);
	    			if(existePago) {
	    				expediente.setPagos("S");
	    			} else {
	    				expediente.setPagos("N");
	    			}

//	    			expedientes.add(expediente);
//	    			expedientes.set(i, expediente);
	    		}
	    		request.setAttribute("expedientes", expedientes.getExpedientes());
	    		request.setAttribute("buscado", "true");
	    	}
	    	else if (!"true".equals(rebuscar)) {

				// Limpiar el formulario de búsqueda
				session.removeAttribute(Misc.CNIF);
	    		session.removeAttribute(Misc.EXPEDIENTE);
	    		session.removeAttribute(Misc.PROCEDIMIENTO);
	    		session.removeAttribute(Misc.NUMERO_REGISTRO_INICIAL);
	    		session.removeAttribute(Misc.FECHA_DESDE);
	    		session.removeAttribute(Misc.FECHA_DESDE_BUSQUEDA);
	    		session.removeAttribute(Misc.OPERADOR_CONSULTA);
	    		session.removeAttribute(Misc.FECHA_HASTA);
	    		session.removeAttribute(Misc.FECHA_HASTA_BUSQUEDA);
	    		session.removeAttribute(Misc.FECHA_REGISTRO_INICIAL_DESDE);
	    		session.removeAttribute(Misc.FECHA_REGISTRO_INICIAL_DESDE_BUSQUEDA);
	    		session.removeAttribute(Misc.OPERADOR_CONSULTA_FECHA_INICIAL);
	    		session.removeAttribute(Misc.FECHA_REGISTRO_INICIAL_HASTA);
	    		session.removeAttribute(Misc.FECHA_REGISTRO_INICIAL_HASTA_BUSQUEDA);
	    		session.removeAttribute(Misc.ESTADO);

	    		request.setAttribute("buscado", "false");
	    		
	    		request.setAttribute(Misc.EXPEDIENTE, "");
	    		request.setAttribute(Misc.PROCEDIMIENTO, "");
	    		request.setAttribute(Misc.NUMERO_REGISTRO_INICIAL, "");
	    	}
	    	else{
	    		request.setAttribute("buscado", "true");
	    	}

	    	/*Expedientes expedientes = (Expedientes)request.getAttribute("expedientes");
	    	if (expedientes != null)
	    		request.setAttribute("expedientes", expedientes);*/
	    }
	    catch (Exception ex){
	    	request.setAttribute(Misc.MENSAJE_ERROR, ex.getMessage());
	    	return mapping.findForward("Failure");
	    }

	    return mapping.findForward("Success");
	 }

}
