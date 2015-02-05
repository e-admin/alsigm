package ieci.tecdoc.isicres.rpadmin.struts.acciones.asuntos;

import ieci.tecdoc.isicres.rpadmin.struts.acciones.RPAdminWebAction;
import ieci.tecdoc.isicres.rpadmin.struts.acciones.transportes.GuardarNuevoTransporteAction;
import ieci.tecdoc.isicres.rpadmin.struts.forms.AsuntoForm;
import ieci.tecdoc.isicres.rpadmin.struts.util.Utils;

import java.util.List;

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
import org.apache.struts.util.MessageResources;

import es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource.MultiEntityContextHolder;
import es.ieci.tecdoc.isicres.admin.beans.Entidad;
import es.ieci.tecdoc.isicres.admin.beans.TipoAsuntoBean;
import es.ieci.tecdoc.isicres.admin.core.locale.LocaleFilterHelper;
import es.ieci.tecdoc.isicres.admin.estructura.adapter.ISicresServicioRPAdminAdapter;
import es.ieci.tecdoc.isicres.admin.exception.ISicresRPAdminException;
import es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin;

public class GuardarNuevoAsuntoAction extends RPAdminWebAction {
	private static final Logger logger = Logger.getLogger(GuardarNuevoTransporteAction.class);

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionForward af = null;

		AsuntoForm asuntoForm = (AsuntoForm)form;

		try {
		    MessageResources mr = getResources(request);
			ActionErrors errores = Utils.validarDatosAsunto(asuntoForm, mr, LocaleFilterHelper.getCurrentLocale(request));

			if (errores != null) {
				saveErrors(request, errores);
				af = mapping.findForward("error");
			} else {
				ISicresServicioRPAdmin oServicio = new ISicresServicioRPAdminAdapter();

			    List listaOficinas = (List) getListaFromSession(request, KEY_LISTA_OFICINAS_ASUNTOS);
			    List listaDocumentos = (List) getListaFromSession(request, KEY_LISTA_DOCUMENTOS_ASUNTOS);

			    asuntoForm.toUpperCase(request);
			    TipoAsuntoBean nuevoTipoAsunto = asuntoForm.populate(listaOficinas,listaDocumentos);

				Entidad entidad = new Entidad();
				entidad.setIdentificador(MultiEntityContextHolder.getEntity());

			    oServicio.crearTipoAsunto(nuevoTipoAsunto, entidad);

				if( logger.isDebugEnabled())
					logger.debug("El tipo de asunto creado es: " + nuevoTipoAsunto.toString());

				ActionMessages messages = new ActionMessages();
				ActionMessage mesage = new ActionMessage("ieci.tecdoc.sgm.rpadmin.asuntos.resultado.guardadoOK");
				messages.add("Result: ", mesage);
				saveMessages(request, messages);
				af = mapping.findForward("success");
			}
		}
		catch ( ISicresRPAdminException e)
		{
			logger.error("Error en la aplicación", e);
			ActionErrors errores = new ActionErrors();
			ActionError error = null;
			error = new ActionError("errors.detail", e.getMessage());
			errores.add("Error interno", error);
			saveErrors(request, errores);
			af = mapping.findForward("error");
		}

		return af;
	}
}
