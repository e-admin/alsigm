package ieci.tecdoc.isicres.rpadmin.struts.acciones.libros;

import ieci.tecdoc.isicres.rpadmin.struts.acciones.RPAdminWebAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource.MultiEntityContextHolder;
import es.ieci.tecdoc.isicres.admin.beans.Entidad;
import es.ieci.tecdoc.isicres.admin.estructura.adapter.ISicresServicioRPAdminAdapter;
import es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin;

public class EliminarAsociacionAction extends RPAdminWebAction {

	private static final Logger logger = Logger.getLogger(EliminarAsociacionAction.class);
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

//		ActionForward af = null;
//		String idEstado = (String)request.getParameter("idEstado");
//		String nombre = (String)request.getParameter("nombre");
		try {

			ISicresServicioRPAdmin oServicio = new ISicresServicioRPAdminAdapter();
			String idOficina = (String)request.getParameter("idOficina");
			String idLibro = (String)request.getParameter("idLibro");
//			String tipo = (String)request.getParameter("tipo");

			Entidad entidad = new Entidad();
			entidad.setIdentificador(MultiEntityContextHolder.getEntity());

			oServicio.desasociarOficinaALibro(Integer.parseInt(idLibro), Integer.parseInt(idOficina), entidad);

//			if( Integer.parseInt(tipo) == Libro.LIBRO_ENTRADA ) {
//				request.setAttribute("idLibroE", idLibro);
//			} else {
//				request.setAttribute("idLibroS", idLibro);
//			}

			ActionMessages messages = new ActionMessages();
			ActionMessage mesage = new ActionMessage("ieci.tecdoc.sgm.rpadmin.libros.eliminar.eliminadoOK");
			messages.add("Result", mesage);
			saveMessages(request, messages);

			ActionForward af = mapping.findForward("edit");
			ActionForward edicion = new ActionForward();
			edicion.setName(af.getName());
			edicion.setPath(af.getPath()+"?idLibro="+idLibro);
			//edicion.setRedirect(true);
			return edicion;
		} catch ( Exception e) {
			logger.error("Error en la aplicación", e);
			ActionErrors errores = new ActionErrors();
			ActionError error = new ActionError("ieci.tecdoc.sgm.rpadmin.error.mensaje", e.getMessage());
			errores.add("Error interno", error);
			saveErrors(request, errores);
			return mapping.findForward("error");
		}
//		request.setAttribute("accion", "ELIMINAR");
//		request.setAttribute("estado", idEstado);
//		request.setAttribute("nombre", nombre);
	}
}