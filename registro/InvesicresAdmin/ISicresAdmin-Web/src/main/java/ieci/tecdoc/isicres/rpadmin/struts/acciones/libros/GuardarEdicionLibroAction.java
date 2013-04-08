package ieci.tecdoc.isicres.rpadmin.struts.acciones.libros;

import ieci.tecdoc.isicres.rpadmin.struts.acciones.RPAdminWebAction;
import ieci.tecdoc.isicres.rpadmin.struts.acciones.business.Constantes;
import ieci.tecdoc.isicres.rpadmin.struts.forms.LibroForm;

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
import es.ieci.tecdoc.isicres.admin.estructura.adapter.ISicresServicioRPAdminAdapter;
import es.ieci.tecdoc.isicres.admin.exception.ISicresRPAdminException;
import es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin;

public class GuardarEdicionLibroAction extends RPAdminWebAction {

	private static final Logger logger = Logger.getLogger(GuardarEdicionLibroAction.class);
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		ISicresServicioRPAdmin oServicio = new ISicresServicioRPAdminAdapter();

		LibroForm libroForm = (LibroForm)form;
		String autenticacion = libroForm.getAutenticacion();
		if(autenticacion.equals(Boolean.TRUE.toString()) || autenticacion.equals(String.valueOf(LibroBean.AUTENTICACION_IMAGENES_SI)))
			libroForm.setAutenticacion(String.valueOf(LibroBean.AUTENTICACION_IMAGENES_SI));
		else
			libroForm.setAutenticacion(String.valueOf(LibroBean.AUTENTICACION_IMAGENES_NO));

		LibroBean libro = new LibroBean();
		BeanUtils.copyProperties(libro, libroForm);
		try {
			//String tipo = (String)request.getParameter("tipo");
			//libro.setTipo(Integer.parseInt(tipo));

			Entidad entidad = new Entidad();
			entidad.setIdentificador(MultiEntityContextHolder.getEntity());

			oServicio.editarLibro(libro, entidad);

			// Guardamos la asociacion entre el libro y la lista de repositorio.
			oServicio.asociarListaALibro(libro.getId(), libro.getIdLista(), entidad);

			ActionMessages messages = new ActionMessages();
			ActionMessage mesage = new ActionMessage("ieci.tecdoc.sgm.rpadmin.libros.resultado.editadoOK");
			messages.add("Result: ", mesage);
			saveMessages(request, messages);
		} catch (ISicresRPAdminException e) {
			logger.error("Error en la aplicación", e);
			ActionErrors errores = new ActionErrors();
			ActionError error = new ActionError("ieci.tecdoc.sgm.rpadmin.error.mensaje", e.getMessage());
			errores.add("Error interno", error);
			saveErrors(request, errores);
		}

		ActionForward af = mapping.findForward("success");
		ActionForward edicion = new ActionForward();
		edicion.setName(af.getName());
		edicion.setPath(af.getPath()+"?idLibro=" + libro.getId()+"&cambios="+Constantes.UNCHECKED_VALUE);
		edicion.setRedirect(false);
		return edicion;
	}
}