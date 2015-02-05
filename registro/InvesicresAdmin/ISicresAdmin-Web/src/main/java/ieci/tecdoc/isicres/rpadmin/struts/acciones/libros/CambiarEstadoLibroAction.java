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
import es.ieci.tecdoc.isicres.admin.beans.Libro;
import es.ieci.tecdoc.isicres.admin.estructura.adapter.ISicresServicioRPAdminAdapter;
import es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin;
import es.ieci.tecdoc.isicres.admin.service.admsesion.Sesion;

public class CambiarEstadoLibroAction extends RPAdminWebAction {

	private static final Logger logger = Logger.getLogger(CambiarEstadoLibroAction.class);
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		ActionForward af = null;
		ISicresServicioRPAdmin oServicio = new ISicresServicioRPAdminAdapter();
		String idBook = (String)request.getParameter("idLibro");
		String idEstado = (String)request.getParameter("idEstado");
		//String usuario = ((Sesion)request.getSession(false).getAttribute("datosSesion")).getUsuario();
		Object oUsuario = request.getSession(false).getAttribute("keySesionUsuario");
		String usuario = oUsuario.toString();
		try {

			Entidad entidad = new Entidad();
			entidad.setIdentificador(MultiEntityContextHolder.getEntity());

			oServicio.modificarEstadoLibro(Integer.parseInt(idBook), usuario, Integer.parseInt(idEstado), entidad);

			ActionMessages messages = new ActionMessages();
			ActionMessage mesage = null;
			if( Integer.parseInt(idEstado) == Libro.CERRAR_LIBRO) {
				mesage = new ActionMessage("ieci.tecdoc.sgm.rpadmin.libros.resultado.cerradoOK");
			} else if( Integer.parseInt(idEstado) == Libro.ABRIR_LIBRO ) {
				mesage = new ActionMessage("ieci.tecdoc.sgm.rpadmin.libros.resultado.abiertoOK");
			}

			messages.add("Result: ", mesage);
			saveMessages(request, messages);
			af = mapping.findForward("success");
		} catch ( Exception e) {
			logger.error("Error en la aplicación", e);
			ActionErrors errores = new ActionErrors();
			ActionError error = new ActionError("ieci.tecdoc.sgm.rpadmin.error.mensaje", e.getMessage());
			errores.add("Error interno", error);
			saveErrors(request, errores);
			af = mapping.findForward("success");
		}
		return af;
	}

}
