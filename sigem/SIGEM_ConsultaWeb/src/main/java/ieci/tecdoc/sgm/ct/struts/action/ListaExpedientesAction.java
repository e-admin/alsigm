package ieci.tecdoc.sgm.ct.struts.action;

import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.consulta.CriterioBusquedaExpedientes;
import ieci.tecdoc.sgm.core.services.consulta.Expediente;
import ieci.tecdoc.sgm.core.services.consulta.Expedientes;
import ieci.tecdoc.sgm.core.services.consulta.ServicioConsultaExpedientes;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.user.web.ConstantesSesionUser;
import ieci.tecdoc.sgm.ct.struts.form.ListaExpedientesForm;
import ieci.tecdoc.sgm.ct.utilities.Misc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class ListaExpedientesAction extends ConsultaWebAction {

	/**
	 * Se sobrescribe el metodo execute de la clase Action
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

		 String cnif = "";
		 String sessionId = "";

		 try{
			 sessionId = request.getParameter(Misc.SESION_ID);
			 if (Misc.isEmpty(sessionId)) {
				 sessionId = (String) session.getAttribute(ConstantesSesionUser.ID_SESION);
			 }

			 cnif = Misc.getCNIFUsuario(request, sessionId);
		 }
		 catch(Exception ex){
			 request.setAttribute(Misc.MENSAJE_ERROR, ex.getMessage());
		 }

		ListaExpedientesForm listaExpedienteForm = (ListaExpedientesForm) form;
		boolean completo = true;

	    try {
			String numExpediente = request.getParameter(Misc.EXPEDIENTE);
			String procedimiento = request.getParameter(Misc.PROCEDIMIENTO);
			String numeroRegistroInicial = request.getParameter(Misc.NUMERO_REGISTRO_INICIAL);
			String fechaRegistroInicialDesdeRequest = request.getParameter(Misc.FECHA_REGISTRO_INICIAL_DESDE);
			String operadorFechaInicial = request.getParameter(Misc.OPERADOR_CONSULTA_FECHA_INICIAL);
			String fechaRegistroInicialHastaRequest = request.getParameter(Misc.FECHA_REGISTRO_INICIAL_HASTA);
			String fechaDesdeRequest = request.getParameter(Misc.FECHA_DESDE);
			String operador = request.getParameter(Misc.OPERADOR_CONSULTA);
			String fechaHastaRequest = request.getParameter(Misc.FECHA_HASTA);
			String estado = request.getParameter(Misc.ESTADO);

			Entidad entidad = Misc.obtenerEntidad(request);
			ServicioConsultaExpedientes oServicio = LocalizadorServicios.getServicioConsultaExpedientes();

	    	Expedientes expedientes = null;
	    	CriterioBusquedaExpedientes oCriterio = new CriterioBusquedaExpedientes();
	    	oCriterio.setNIF(cnif);

	    	if(fechaDesdeRequest== null && fechaRegistroInicialDesdeRequest==null
	    			&& estado==null && numExpediente==null && procedimiento==null && numeroRegistroInicial==null) {

	    		expedientes = oServicio.consultarExpedientesNIF(cnif, entidad);
	    	}
	    	else {
	    		String fechaParseadaDesde = null;
	    		String fechaParseadaHasta = null;
	    		String fechaRegistroInicialParseadaDesde = null;
	    		String fechaRegistroInicialParseadaHasta = null;

	    		try {
	    			fechaParseadaDesde = parsearFecha(fechaDesdeRequest);
	    			fechaParseadaHasta = parsearFecha(fechaHastaRequest);
	    			fechaRegistroInicialParseadaDesde = parsearFecha(fechaRegistroInicialDesdeRequest);
	    			fechaRegistroInicialParseadaHasta = parsearFecha(fechaRegistroInicialHastaRequest);
	    		} catch (Throwable conEx){
	    			 ActionMessages errors = new ActionMessages();
	    			 errors.add("error", new ActionMessage("formatoFechaIncorrecto"));
	    			 return mapping.findForward("Busqueda");
	    		}

	    		session.setAttribute(Misc.EXPEDIENTE, numExpediente);
				session.setAttribute(Misc.PROCEDIMIENTO, procedimiento);
				session.setAttribute(Misc.NUMERO_REGISTRO_INICIAL, numeroRegistroInicial);
				session.setAttribute(Misc.FECHA_REGISTRO_INICIAL_DESDE, fechaRegistroInicialParseadaDesde);
				session.setAttribute(Misc.FECHA_REGISTRO_INICIAL_DESDE_BUSQUEDA, fechaRegistroInicialDesdeRequest);
				session.setAttribute(Misc.OPERADOR_CONSULTA_FECHA_INICIAL, operadorFechaInicial);
				session.setAttribute(Misc.FECHA_REGISTRO_INICIAL_HASTA, fechaRegistroInicialParseadaHasta);
				session.setAttribute(Misc.FECHA_REGISTRO_INICIAL_HASTA_BUSQUEDA, fechaRegistroInicialHastaRequest);
				session.setAttribute(Misc.FECHA_DESDE, fechaParseadaDesde);
				session.setAttribute(Misc.FECHA_DESDE_BUSQUEDA, fechaDesdeRequest);
				session.setAttribute(Misc.OPERADOR_CONSULTA, operador);
				session.setAttribute(Misc.FECHA_HASTA, fechaParseadaHasta);
				session.setAttribute(Misc.FECHA_HASTA_BUSQUEDA, fechaHastaRequest);
				session.setAttribute(Misc.ESTADO, estado);

	    		completo = false;
	    		oCriterio.setFechaDesde(fechaParseadaDesde);
	    		oCriterio.setFechaHasta(fechaParseadaHasta);
	    		oCriterio.setOperadorConsulta(operador);
	    		oCriterio.setFechaRegistroInicialDesde(fechaRegistroInicialParseadaDesde);
	    		oCriterio.setFechaRegistroInicialHasta(fechaRegistroInicialParseadaHasta);
	    		oCriterio.setOperadorConsultaFechaInicial(operadorFechaInicial);
	    		oCriterio.setExpediente(numExpediente);
	    		oCriterio.setNumeroRegistroInicial(numeroRegistroInicial);
	    		oCriterio.setProcedimiento(procedimiento);
	    		oCriterio.setEstado(estado);

	    		expedientes = oServicio.busquedaExpedientes(oCriterio, entidad);
	    	}

	    	listaExpedienteForm.setURLAportacion(oServicio.obtenerURLAportacionExpedientes());
    		listaExpedienteForm.setURLNotificacion(oServicio.obtenerURLNotificacionExpedientes());

    		Expedientes nuevoExpedientes = new Expedientes();

    		for (int a=0; a<expedientes.count(); a++) {

    			Expediente expediente = (Expediente)expedientes.get(a);

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

    			/*boolean existeNotificacion = false;//oServicio.recogerNotificaciones(NIF, expediente.getNumero());
    			if(existeNotificacion){
    				expediente.setNotificacion("S");
    			}*/

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

    			nuevoExpedientes.add(expediente);
    		}

    		listaExpedienteForm.setExpedientes(nuevoExpedientes);
    		request.setAttribute("expedientes", nuevoExpedientes.getExpedientes());

	    }
	    catch (Exception ex){
	    	request.setAttribute(Misc.MENSAJE_ERROR, ex.getMessage());
	    }

	    if (completo) {
	    	return mapping.findForward("Success");
	    } else {
	    	return mapping.findForward("Success_Search");
	    }
	 }

	 private String parsearFecha (String fecha) {

		 String fechaParseada = null;
		 if(fecha != null && !fecha.equals("")){
			 try {
			 String[] porcionesFecha = fecha.split("-");
			 fechaParseada = porcionesFecha[2] + "-" + porcionesFecha[1] + "-" + porcionesFecha[0];
			 } catch (java.lang.IndexOutOfBoundsException ex) {
				 throw ex;
			 }
		 }
		 return fechaParseada;
	 }

}