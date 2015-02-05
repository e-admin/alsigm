package ieci.tecdoc.isicres.rpadmin.struts.acciones.libros;

import ieci.tecdoc.isicres.rpadmin.struts.acciones.RPAdminWebAction;
import ieci.tecdoc.isicres.rpadmin.struts.forms.PermisosSicresForm;

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
import es.ieci.tecdoc.isicres.admin.beans.PermisosSicres;
import es.ieci.tecdoc.isicres.admin.estructura.adapter.ISicresServicioRPAdminAdapter;
import es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin;

public class AsociarUsuariosAction extends RPAdminWebAction {

	private static final Logger logger = Logger.getLogger(AsociarUsuariosAction.class);
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		ISicresServicioRPAdmin oServicio = new ISicresServicioRPAdminAdapter();
		String idLibro = (String)request.getParameter("idLibro");
		String accion = (String)request.getParameter("accion");

		//Se obtiene la entidad
		Entidad entidad = new Entidad();
		entidad.setIdentificador(MultiEntityContextHolder.getEntity());

		if(accion == null) {
			int idBook = Integer.parseInt(idLibro);

			//Obtener las oficinas asociadas al libro
			// Se elimina porque lo requiere el ticket 457 de SIGEM por un
			// cambio solicitado por AAF

			//Obtener los permisos de todos los usuarios
			PermisosSicresForm permisosForm = (PermisosSicresForm)form;
			PermisosSicres permisos = new PermisosSicres();
			if (isLdapMethod(entidad.getIdentificador())){
				permisos = oServicio.obtenerPermisosUsuariosLdap(idBook,
						entidad);
			} else {
				permisos = oServicio.obtenerPermisosUsuarios(idBook,
						entidad);
			}
			BeanUtils.copyProperties(permisosForm, permisos);
			request.setAttribute("permisosSicres", permisos);

			LibroBean libro = oServicio.obtenerLibroBean(Integer.parseInt(idLibro), entidad);
			request.setAttribute("libro", libro);
			return mapping.findForward("success");
		} else {
			PermisosSicres permisosSicres = new PermisosSicres();
			PermisosSicresForm permisosForm = (PermisosSicresForm)form;
			BeanUtils.copyProperties(permisosSicres, permisosForm);
			try {
				if(logger.isDebugEnabled())
					logger.debug("Los permisos de usuarios modificados asociadas al libro son: " + permisosSicres.toString());

				oServicio.modificarPermisos(permisosSicres, entidad);

				ActionMessages messages = new ActionMessages();
				ActionMessage mesage = new ActionMessage("ieci.tecdoc.sgm.rpadmin.libros.resultado.asociar.usuarios.guardadoOK");
				messages.add("Result", mesage);
				saveMessages(request, messages);
			} catch (Exception e) {
				logger.error("Error en la aplicación", e);
				ActionErrors errores = new ActionErrors();
				ActionError error = new ActionError("ieci.tecdoc.sgm.rpadmin.error.mensaje", e.getMessage());
				errores.add("Error interno", error);
				saveErrors(request, errores);
			}
		}

		ActionForward af = mapping.findForward("edit");
		ActionForward edicion = new ActionForward();
		edicion.setName(af.getName());
		edicion.setPath(af.getPath()+"?idLibro="+idLibro);
		//edicion.setRedirect(true);
		return edicion;
	}

}
