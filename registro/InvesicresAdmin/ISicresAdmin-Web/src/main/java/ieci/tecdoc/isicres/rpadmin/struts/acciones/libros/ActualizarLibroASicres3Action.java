package ieci.tecdoc.isicres.rpadmin.struts.acciones.libros;

import ieci.tecdoc.isicres.rpadmin.struts.acciones.RPAdminWebAction;
import ieci.tecdoc.isicres.rpadmin.struts.acciones.business.Constantes;
import ieci.tecdoc.isicres.rpadmin.struts.forms.LibroForm;

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
import es.ieci.tecdoc.isicres.admin.core.beans.definicion.DefinicionLibroSicres3Utils;
import es.ieci.tecdoc.isicres.admin.estructura.adapter.ISicresServicioRPAdminAdapter;
import es.ieci.tecdoc.isicres.admin.exception.ISicresRPAdminException;
import es.ieci.tecdoc.isicres.admin.rpadmin.manager.ISicresRPAdminLibroManager;
import es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin;

public class ActualizarLibroASicres3Action extends RPAdminWebAction {

	private static final Logger logger = Logger.getLogger(ActualizarLibroASicres3Action.class);

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		ISicresServicioRPAdmin oServicio = new ISicresServicioRPAdminAdapter();
		ActionForward af = mapping.findForward("success");
		ActionErrors errores = new ActionErrors();
		ActionMessages messages = new ActionMessages();

		// Obtener el idLibroSeleccionado
		LibroForm libroForm = (LibroForm)form;
		Integer idLibro = Integer.valueOf(libroForm.getId());
		Entidad entidad = new Entidad();
		entidad.setIdentificador(MultiEntityContextHolder.getEntity());
		boolean actualizableASicres3 = DefinicionLibroSicres3Utils.isSicres3Enabled()
					&& !ISicresRPAdminLibroManager.isLibroSicres3(idLibro, entidad.getIdentificador());

		try {

			if (actualizableASicres3) {
				oServicio.actualizarLibroASicres3(idLibro.intValue(), entidad);
				ActionMessage mesage = new ActionMessage("ieci.tecdoc.sgm.rpadmin.libros.resultado.actualizadoASicres3OK");
				messages.add("Result: ", mesage);
				saveMessages(request, messages);
				libroForm.setActualizableASicres3(Boolean.FALSE);
			} else {
				ActionError error = new ActionError("ieci.tecdoc.sgm.rpadmin.libros.resultado.noPermitidoActASicres3");
				errores.add("Error interno", error);
				saveErrors(request, errores);
				af = mapping.findForward("error");
			}

		} catch (ISicresRPAdminException e) {
			logger.error("Error en la aplicación", e);
			ActionError error = new ActionError("ieci.tecdoc.sgm.rpadmin.error.mensaje", e.getMessage());
			errores.add("Error interno", error);
			saveErrors(request, errores);
			af = mapping.findForward("error");
		}

		ActionForward edicion = new ActionForward();
		edicion.setName(af.getName());
		edicion.setPath(af.getPath()+"?idLibro=" + idLibro +"&cambios="+Constantes.UNCHECKED_VALUE);
		edicion.setRedirect(false);

		return edicion;
	}
}