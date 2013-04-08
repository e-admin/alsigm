package ieci.tecdoc.isicres.rpadmin.struts.acciones.oficinas;

import ieci.tecdoc.isicres.rpadmin.struts.acciones.RPAdminWebAction;
import ieci.tecdoc.isicres.rpadmin.struts.forms.OficinaForm;

import java.util.Calendar;
import java.util.Date;

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
import es.ieci.tecdoc.isicres.admin.beans.OficinaBean;
import es.ieci.tecdoc.isicres.admin.estructura.adapter.ISicresServicioRPAdminAdapter;
import es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin;

public class GuardarEdicionOficinaAction extends RPAdminWebAction {

	private static final Logger logger = Logger.getLogger(GuardarEdicionOficinaAction.class);

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		ActionForward af = null;

		try {
			ISicresServicioRPAdmin oServicio = new ISicresServicioRPAdminAdapter();

			// Guardamos los datos de la Oficina desde la opción de guardar de la
			// pagina de Edición si el id viene relleno
			String id = (String) request.getParameter("id");

			if (id != null && !id.equals("")) {

				OficinaForm oficinaForm = (OficinaForm) form;
				oficinaForm.toUpperCase(request);

				OficinaBean oficina = new OficinaBean();

				Calendar fechaForm = Calendar.getInstance();
				Calendar fechaAlta = Calendar.getInstance();
				fechaForm.setTime(new Date());

				fechaAlta.setTime(oficinaForm.getFecha());
				if (fechaAlta.getTime().after(fechaForm.getTime())) {
					ActionErrors errores = new ActionErrors();
					ActionError error = new ActionError(
							"ieci.tecdoc.sgm.rpadmin.oficinas.error.fechaAlta");
					errores.add("Error interno", error);
					saveErrors(request, errores);
					return mapping.findForward("error");
				}

				BeanUtils.copyProperties(oficina, oficinaForm);
				oficina.setId(Integer.parseInt(id));

				//Se obtiene la entidad
				Entidad entidad = new Entidad();
				entidad.setIdentificador(MultiEntityContextHolder.getEntity());

				oServicio.editarOficina(oficina, entidad);

				ActionMessages messages = new ActionMessages();
				ActionMessage mesage = new ActionMessage(
						"ieci.tecdoc.sgm.rpadmin.oficinas.resultado.editadoOK");
				messages.add("Result: ", mesage);
				saveMessages(request, messages);
				af = mapping.findForward("success");
			}

		} catch (Exception e) {

			logger.error("Error en la aplicación", e);
			ActionErrors errores = new ActionErrors();
			ActionError error = new ActionError(
					"ieci.tecdoc.sgm.rpadmin.error.mensaje", e.getMessage());
			errores.add("Error interno", error);
			saveErrors(request, errores);
			af = mapping.findForward("error");
		}

		return af;
	}

}
