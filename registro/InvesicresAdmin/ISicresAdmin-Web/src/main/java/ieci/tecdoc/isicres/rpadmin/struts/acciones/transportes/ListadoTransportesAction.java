package ieci.tecdoc.isicres.rpadmin.struts.acciones.transportes;

import ieci.tecdoc.isicres.rpadmin.struts.acciones.RPAdminWebAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource.MultiEntityContextHolder;
import es.ieci.tecdoc.isicres.admin.beans.Entidad;
import es.ieci.tecdoc.isicres.admin.beans.Transportes;
import es.ieci.tecdoc.isicres.admin.estructura.adapter.ISicresServicioRPAdminAdapter;
import es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin;

public class ListadoTransportesAction extends RPAdminWebAction {

//	private static final Logger logger = Logger.getLogger(ListadoTransportesAction.class);
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		ISicresServicioRPAdmin oServicio = new ISicresServicioRPAdminAdapter();
		Entidad entidad = new Entidad();
		entidad.setIdentificador(MultiEntityContextHolder.getEntity());
		Transportes transportes = oServicio.obtenerTransportes(entidad);
		request.setAttribute("transportes", transportes);
		return mapping.findForward("success");
	}
}
