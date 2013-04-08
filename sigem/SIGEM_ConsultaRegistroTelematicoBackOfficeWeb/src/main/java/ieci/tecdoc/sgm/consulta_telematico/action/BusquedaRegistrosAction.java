package ieci.tecdoc.sgm.consulta_telematico.action;

import ieci.tecdoc.sgm.consulta_telematico.form.BusquedaRegistrosForm;
import ieci.tecdoc.sgm.consulta_telematico.utils.Defs;
import ieci.tecdoc.sgm.consulta_telematico.utils.Utils;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.catalogo.ServicioCatalogoTramites;
import ieci.tecdoc.sgm.core.services.catalogo.Tramites;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.telematico.RegistroConsulta;
import ieci.tecdoc.sgm.core.services.telematico.Registros;
import ieci.tecdoc.sgm.core.services.telematico.ServicioRegistroTelematico;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 */
public class BusquedaRegistrosAction extends ConsultaRegistroTelematicoWebAction {

	private static final Logger logger = Logger.getLogger(BusquedaRegistrosAction.class);

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		// Información de la sesión del usuario
		HttpSession session = request.getSession();
		String idEntidad = (String) session.getAttribute(Defs.ENTIDAD_ID);

	    try{
	    	BusquedaRegistrosForm busquedaForm = (BusquedaRegistrosForm)form;
	    	if ((form == null) || (busquedaForm.getOperadorFechas() == null)) {

	    		ServicioCatalogoTramites oServicioCT = LocalizadorServicios.getServicioCatalogoTramites();
	    		Tramites tramites = oServicioCT.getProcedures(Utils.obtenerEntidad(idEntidad));
	    		session.setAttribute(Defs.TRAMITES, Utils.obtenerTramites(tramites));

	    		request.setAttribute(Defs.RESULTADO_BUSQUEDA, new ArrayList());

	    		return mapping.findForward(Defs.SUCCESS_FORWARD);
	    	}

	    	Entidad entidad = Utils.obtenerEntidad(idEntidad);
	    	ServicioRegistroTelematico oServicioRT = LocalizadorServicios.getServicioRegistroTelematico();

	    	// Establecer los parámetros de búsqueda
	    	RegistroConsulta registroConsulta = new RegistroConsulta();

	    	registroConsulta.setSenderId(busquedaForm.getCnifUsuario());

	    	registroConsulta.setRegistryNumber(busquedaForm.getNumeroRegistro());
	    	registroConsulta.setOprRegistryNumber(Utils.obtenerOperadorRegistro(busquedaForm.getOperadorNumeroRegistro()));

	    	if (busquedaForm.getOperadorFechas().equals("=")) {

	    		// Día completo desde 00:00:00 hasta 23:59:59
		    	registroConsulta.setFirstRegistryDate(busquedaForm.getFechaDesde());
		    	registroConsulta.setLastRegistryDate(busquedaForm.getFechaDesde());
	    	}
	    	else if (busquedaForm.getOperadorFechas().equals("<")) {

	    		// Al generar la consulta, cuando la última fecha de registro tiene valor y no la primera
	    		// se supone que el operador seleccionado para las fechas es el de "menor que" (RegistroTabla.java)
	    		registroConsulta.setLastRegistryDate(busquedaForm.getFechaDesde());
	    	}
	    	else {
		    	registroConsulta.setFirstRegistryDate(busquedaForm.getFechaDesde());
		    	registroConsulta.setLastRegistryDate(busquedaForm.getFechaHasta());
	    	}
	    	if (busquedaForm.getOperadorFechasEfectivas().equals("=")) {

	    		// Día completo desde 00:00:00 hasta 23:59:59
		    	registroConsulta.setFirstEffectiveDate(busquedaForm.getFechaEfectivaDesde());
		    	registroConsulta.setLastEffectiveDate(busquedaForm.getFechaEfectivaDesde());
	    	}
	    	else if (busquedaForm.getOperadorFechasEfectivas().equals("<")) {

	    		// Al generar la consulta, cuando la última fecha de registro tiene valor y no la primera
	    		// se supone que el operador seleccionado para las fechas es el de "menor que" (RegistroTabla.java)
	    		registroConsulta.setLastEffectiveDate(busquedaForm.getFechaEfectivaDesde());
	    	}
	    	else {
		    	registroConsulta.setFirstEffectiveDate(busquedaForm.getFechaEfectivaDesde());
		    	registroConsulta.setLastEffectiveDate(busquedaForm.getFechaEfectivaHasta());
	    	}
	    	registroConsulta.setTopic(busquedaForm.getAsunto());
	    	//registroConsulta.setStatus(Defs.REGISTRO_OK);

	    	Registros registros = oServicioRT.obtenerRegistrosParaMostrar(registroConsulta, entidad);

	    	busquedaForm.setBuscado("S");
	    	request.setAttribute(Defs.RESULTADO_BUSQUEDA, Utils.obtenerListado(registros, oServicioRT, entidad));

	    	return mapping.findForward(Defs.SUCCESS_FORWARD);
	    }
	    catch(Exception e){
	    	logger.error("Se ha producido un error al realizar la busqueda", e.fillInStackTrace());

	    	session.setAttribute(Defs.TRAMITES, new ArrayList());
    		request.setAttribute(Defs.RESULTADO_BUSQUEDA, new ArrayList());

	    	return mapping.findForward(Defs.FAILURE_FORWARD);
   		}
   	}
}
