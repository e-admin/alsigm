package ieci.tecdoc.isicres.rpadmin.struts.acciones.oficinas;

import ieci.tecdoc.isicres.rpadmin.struts.acciones.RPAdminWebAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource.MultiEntityContextHolder;
import es.ieci.tecdoc.isicres.admin.beans.Entidad;
import es.ieci.tecdoc.isicres.admin.beans.Oficinas;
import es.ieci.tecdoc.isicres.admin.estructura.adapter.ISicresServicioRPAdminAdapter;
import es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin;

public class ListadoOficinasAction extends RPAdminWebAction {

	private static final Logger logger = Logger.getLogger(ListadoOficinasAction.class);
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		ISicresServicioRPAdmin oServicio = new ISicresServicioRPAdminAdapter();
		//Oficinas oficinas = oServicio.obtenerOficinas(SesionHelper.obtenerEntidad(request));
		//Se obtiene la entidad
		Entidad entidad = new Entidad();
		entidad.setIdentificador(MultiEntityContextHolder.getEntity());

		Oficinas oficinas = oServicio.obtenerOficinas(entidad);
		request.setAttribute("oficinas", oficinas);

		// Flg que establece si venimos de la pagina de filtros de los libros
		String fila = (String)request.getParameter("fila");

		if( fila != null && !fila.equals("")) {
			request.setAttribute("size", String.valueOf(oficinas.count()));
			request.setAttribute("fila", fila);
			return mapping.findForward("oficinas");
		} else {
			return mapping.findForward("success");
		}
	}

}
