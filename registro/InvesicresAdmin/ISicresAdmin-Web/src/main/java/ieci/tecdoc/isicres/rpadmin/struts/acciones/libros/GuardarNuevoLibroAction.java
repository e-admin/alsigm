package ieci.tecdoc.isicres.rpadmin.struts.acciones.libros;

import ieci.tecdoc.isicres.rpadmin.struts.acciones.RPAdminWebAction;
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

public class GuardarNuevoLibroAction extends RPAdminWebAction {

	private static final Logger logger = Logger.getLogger(GuardarNuevoLibroAction.class);
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		ActionForward af = null;
		ISicresServicioRPAdmin oServicio = new ISicresServicioRPAdminAdapter();

		LibroForm libroForm = (LibroForm)form;

		if(libroForm.getAutenticacion() != null)
			libroForm.setAutenticacion(String.valueOf(LibroBean.AUTENTICACION_IMAGENES_SI));
		else
			libroForm.setAutenticacion(String.valueOf(LibroBean.AUTENTICACION_IMAGENES_NO));

		try {
			LibroBean libro = new LibroBean();
			BeanUtils.copyProperties(libro, libroForm);
			Entidad entidad = new Entidad();
			entidad.setIdentificador(MultiEntityContextHolder.getEntity());

			libro.setId(oServicio.crearLibro(libro, entidad));

			// Si se introdujo la lista de volumenes guardamos la asociacion entre el libro y la lista.
			if(libro.getIdLista() > 0)
				oServicio.asociarListaALibro(libro.getId(), libro.getIdLista(), entidad);

			ActionMessages messages = new ActionMessages();
			ActionMessage mesage = new ActionMessage("ieci.tecdoc.sgm.rpadmin.libros.resultado.guardadoOK");
			messages.add("Result: ", mesage);
			saveMessages(request, messages);
			af = mapping.findForward("success");

		} catch (ISicresRPAdminException e) {
			logger.error("Error en la aplicación", e);
			ActionErrors errores = new ActionErrors();
			ActionError error = new ActionError("ieci.tecdoc.sgm.rpadmin.error.mensaje", e.getMessage());
			errores.add("Error interno", error);
			saveErrors(request, errores);
			af = mapping.findForward("error");
		}
		return af;
	}

}
