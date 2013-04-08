package ieci.tecdoc.isicres.rpadmin.struts.acciones.libros;

import ieci.tecdoc.isicres.rpadmin.struts.acciones.RPAdminWebAction;
import ieci.tecdoc.isicres.rpadmin.struts.forms.OficinasLibrosForm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import es.ieci.tecdoc.isicres.admin.beans.Oficinas;
import es.ieci.tecdoc.isicres.admin.beans.PermisoSicres;
import es.ieci.tecdoc.isicres.admin.beans.PermisosSicres;
import es.ieci.tecdoc.isicres.admin.estructura.adapter.ISicresServicioRPAdminAdapter;
import es.ieci.tecdoc.isicres.admin.exception.ISicresRPAdminException;
import es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin;

public class AsociarOficinasAction extends RPAdminWebAction {

	private static final Logger logger = Logger.getLogger(AsociarOficinasAction.class);
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		ActionForward af = null;
		ISicresServicioRPAdmin oServicio = new ISicresServicioRPAdminAdapter();
		String idLibro = (String)request.getParameter("idLibro");
		String accion = (String)request.getParameter("accion");
		Entidad entidad = new Entidad();
		entidad.setIdentificador(MultiEntityContextHolder.getEntity());

		if( accion != null && accion.equals("ASOCIAR")) {

			OficinasLibrosForm oficinasLibrosForm = (OficinasLibrosForm)form;
			int[] ids = new int[oficinasLibrosForm.countOficinas()];
			for( int i = 0; i < oficinasLibrosForm.countOficinas(); i++)
				ids[i] = Integer.parseInt(oficinasLibrosForm.getIds(i));

            // Si no se ha asociado ninguna oficina, no mostramos el mensaje de ok
			if( ids != null && ids.length > 0) {
				try {
					if(logger.isDebugEnabled())
						logger.debug("Los ids oficinas modificadas asociadas al libro son: " + ids);

					oServicio.asociarOficinaALibro(Integer.parseInt(idLibro), ids, entidad);

					//Modificar permisos de cada una de las oficinas que asociamos al libro
					PermisosSicres permisosSicres = new PermisosSicres();
					BeanUtils.copyProperties(permisosSicres, oficinasLibrosForm);

					List listaOficinas = null;
					if (permisosSicres.count() > 0){
						listaOficinas = obtenerOficinasSeleccionadas(oServicio,
								ids, entidad);
					}
					permisosSicres = obtenerPermisosOficinasSeleccionadas(ids,permisosSicres, listaOficinas);
					oServicio.modificarPermisos(permisosSicres, entidad);
					ActionMessages messages = new ActionMessages();
					ActionMessage mesage = new ActionMessage("ieci.tecdoc.sgm.rpadmin.libros.resultado.asociar.oficinas.guardadoOK");
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
			af = mapping.findForward("listado");
		} else {
			Oficinas oficinas = oServicio.obtenerOficinasDesasociadasALibro( Integer.parseInt(idLibro), entidad);
			request.setAttribute("oficinas", oficinas);
			LibroBean libro = oServicio.obtenerLibroBean(Integer.parseInt(idLibro), entidad);
			request.setAttribute("libro", libro);

			af = mapping.findForward("success");
		}

		return af;
	}

	private PermisosSicres obtenerPermisosOficinasSeleccionadas(int ids[], PermisosSicres permisosSicres, List listaOficinas){
		PermisosSicres permisos = permisosSicres;
		for (Iterator iterator = permisos.getLista().iterator(); iterator
				.hasNext();) {
			PermisoSicres permiso = (PermisoSicres) iterator.next();

			if (!oficinaSeleccionada(ids, permiso)) {
				iterator.remove();
			} else {
				permiso.setId(cambiarIdPermiso(permiso.getId(), listaOficinas));
			}
		}
		return permisos;
	}

	private boolean oficinaSeleccionada(int ids[], PermisoSicres permiso){
		for(int i=0; i<ids.length;i++){
			if(permiso.getId() == ids[i])
				return true;
		}
		return false;
	}

	private List obtenerOficinasSeleccionadas(ISicresServicioRPAdmin oServicio,
			int[] ids, Entidad entidad) throws ISicresRPAdminException {
		if (ids != null && ids.length > 0) {
			List oficinas = new ArrayList();
			for (int i = 0; i < ids.length; i++) {
				OficinaBean oficina = oServicio.obtenerOficina(ids[i], entidad);
				oficinas.add(oficina);
			}

			return oficinas;
		}
		return null;
	}

	private int cambiarIdPermiso(int id, List listaOficinas) {
		int deptId = 0;

		if (listaOficinas != null && listaOficinas.size() > 0) {
			for (Iterator iterator = listaOficinas.iterator(); iterator
					.hasNext();) {
				OficinaBean oficina = (OficinaBean) iterator.next();

				if (oficina.getId() == id) {
					deptId = oficina.getDeptId();
					break;
				}
			}
		}

		return deptId;
	}
}