package ieci.tecdoc.isicres.rpadmin.struts.acciones.libros;

import ieci.tecdoc.isicres.rpadmin.struts.acciones.RPAdminWebAction;
import ieci.tecdoc.isicres.rpadmin.struts.forms.LibroForm;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
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
import es.ieci.tecdoc.isicres.admin.beans.LibroBean;
import es.ieci.tecdoc.isicres.admin.beans.OficinaBean;
import es.ieci.tecdoc.isicres.admin.beans.PermisoSicres;
import es.ieci.tecdoc.isicres.admin.beans.PermisosSicres;
import es.ieci.tecdoc.isicres.admin.estructura.adapter.ISicresServicioRPAdminAdapter;
import es.ieci.tecdoc.isicres.admin.exception.ISicresRPAdminException;
import es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin;

public class PermisosOficinasAsociadasAction extends RPAdminWebAction {

	private static final Logger logger = Logger.getLogger(PermisosOficinasAsociadasAction.class);
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		ISicresServicioRPAdmin oServicio = new ISicresServicioRPAdminAdapter();
		String accion = (String)request.getParameter("accion");
		LibroForm libroForm = (LibroForm)form;

		Entidad entidad = new Entidad();
		entidad.setIdentificador(MultiEntityContextHolder.getEntity());

		String idLibro = (String)request.getParameter("idLibro");
		LibroBean libro = oServicio.obtenerLibroBean(Integer.parseInt(idLibro), entidad);
		request.setAttribute("libro", libro);

		if(accion == null){
			PermisosSicres permisosSicres = oServicio.obtenerPermisosOficinasLibro(Integer.parseInt(idLibro), entidad);
			BeanUtils.copyProperties(libroForm, permisosSicres);
			request.setAttribute("permisosSicres", permisosSicres);

			return mapping.findForward("success");
		}else{
			try {
				PermisosSicres permisosSicres = new PermisosSicres();
				BeanUtils.copyProperties(permisosSicres, libroForm);

				if(logger.isDebugEnabled())
					logger.debug("La asociación modificada es: " + permisosSicres.toString());

				permisosSicres = cambiarIdPermiso(oServicio, permisosSicres, entidad);
				oServicio.modificarPermisos(permisosSicres, entidad);

				ActionMessages messages = new ActionMessages();
				ActionMessage mesage = new ActionMessage("ieci.tecdoc.sgm.rpadmin.libros.resultado.asociar.oficinas.modificadoOK");
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
		}
	}

	private PermisosSicres cambiarIdPermiso(ISicresServicioRPAdmin oServicio,
			PermisosSicres permisosSicres, Entidad entidad) throws ISicresRPAdminException {
		if (permisosSicres != null && permisosSicres.count() > 0) {
			for (Iterator iterator = permisosSicres.getLista().iterator(); iterator
					.hasNext();) {
				PermisoSicres permiso = (PermisoSicres) iterator.next();

				OficinaBean oficina = oServicio.obtenerOficina(permiso.getId(),
						entidad);

				permiso.setId(oficina.getDeptId());
			}
		}

		return permisosSicres;
	}
}