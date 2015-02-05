package ieci.tecdoc.isicres.rpadmin.struts.acciones.libros;

import ieci.tecdoc.isicres.rpadmin.struts.acciones.RPAdminWebAction;
import ieci.tecdoc.isicres.rpadmin.struts.forms.PermisosSicresForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource.MultiEntityContextHolder;
import es.ieci.tecdoc.isicres.admin.beans.Entidad;
import es.ieci.tecdoc.isicres.admin.beans.Libro;
import es.ieci.tecdoc.isicres.admin.beans.PermisosSicres;
import es.ieci.tecdoc.isicres.admin.estructura.adapter.ISicresServicioRPAdminAdapter;
import es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin;

public class ListadoAsociacionAction extends RPAdminWebAction {

	private static final Logger logger = Logger.getLogger(ListadoAsociacionAction.class);
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		ActionForward af = null;
		ISicresServicioRPAdmin oServicio = new ISicresServicioRPAdminAdapter();

		String nombre = (String)request.getParameter("nombre") != null ? (String)request.getParameter("nombre"):(String)request.getAttribute("nombre");

		String idLibroE = (String)request.getParameter("idLibroE") != null ? (String)request.getParameter("idLibroE"):(String)request.getAttribute("idLibroE");
		String idLibroS = (String)request.getParameter("idLibroS") != null ? (String)request.getParameter("idLibroS"):(String)request.getAttribute("idLibroS");
		String idEstado = (String)request.getParameter("idEstado") != null ? (String)request.getParameter("idEstado"):(String)request.getAttribute("idEstado");

		int idLibro = -1;
		int tipoLibro = -1;

		if( idLibroE != null && !idLibroE.equals("")) {
			tipoLibro = Libro.LIBRO_ENTRADA;
			idLibro = Integer.parseInt(idLibroE);
		} else {
			tipoLibro = Libro.LIBRO_SALIDA;
			idLibro = Integer.parseInt(idLibroS);
		}

		Entidad entidad = new Entidad();
		entidad.setIdentificador(MultiEntityContextHolder.getEntity());

		PermisosSicres permisosSicres = oServicio.obtenerPermisosOficinasLibro(idLibro, entidad);

		PermisosSicresForm permisosSicresForm = (PermisosSicresForm)form;
		BeanUtils.copyProperties(permisosSicresForm, permisosSicres);

		request.setAttribute("permisosSicres", permisosSicres);
		request.setAttribute("tipoLibro", String.valueOf(tipoLibro));
		request.setAttribute("idLibro", String.valueOf(idLibro));
		request.setAttribute("estado", idEstado);
		request.setAttribute("nombre", nombre);

		// Si venimos de la acción de desaociar una oficina a un libro
		String accion = (String)request.getAttribute("accion");

		af = mapping.findForward("success");

		if( accion != null) {
			if( accion.equals("ELIMINAR") ||
				accion.equals("ASOCIAR_OFICINAS") ||
				accion.equals("ASOCIAR_USUARIOS") ) {

				ActionErrors errores = request.getAttribute("errors") != null ? (ActionErrors)request.getAttribute("errors"): null;
				if(errores != null && !errores.isEmpty()) {
					saveErrors(request, errores);
				}

				ActionMessages messages = request.getAttribute("messages") != null ? (ActionMessages)request.getAttribute("messages"): null;
				if(messages != null && !messages.isEmpty()) {
					saveMessages(request, messages);
				}
			}
			af = mapping.findForward("success");
		}

		return af;
	}

}
