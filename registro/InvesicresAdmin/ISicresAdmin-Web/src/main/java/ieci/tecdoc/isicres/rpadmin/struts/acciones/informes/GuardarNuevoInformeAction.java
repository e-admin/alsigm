package ieci.tecdoc.isicres.rpadmin.struts.acciones.informes;

import ieci.tecdoc.isicres.rpadmin.struts.acciones.RPAdminWebAction;
import ieci.tecdoc.isicres.rpadmin.struts.acciones.keys.InformesKeys;
import ieci.tecdoc.isicres.rpadmin.struts.acciones.transportes.GuardarNuevoTransporteAction;
import ieci.tecdoc.isicres.rpadmin.struts.forms.InformeForm;
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
import es.ieci.tecdoc.isicres.admin.beans.InformeBean;
import es.ieci.tecdoc.isicres.admin.beans.OptionsBean;
import es.ieci.tecdoc.isicres.admin.core.locale.LocaleFilterHelper;
import es.ieci.tecdoc.isicres.admin.estructura.adapter.ISicresServicioRPAdminAdapter;
import es.ieci.tecdoc.isicres.admin.exception.ISicresRPAdminException;
import es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin;

public class GuardarNuevoInformeAction  extends RPAdminWebAction {
	private static final Logger logger = Logger.getLogger(GuardarNuevoTransporteAction.class);

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionForward af = null;

		InformeForm informeForm = (InformeForm)form;
		String path = request.getSession().getServletContext().getRealPath("WEB-INF");

		ISicresServicioRPAdmin oServicio = new ISicresServicioRPAdminAdapter();
		Entidad entidad = new Entidad();
		entidad.setIdentificador(MultiEntityContextHolder.getEntity());

		OptionsBean tiposInformes = oServicio.obtenerTiposInformesCombo(entidad);
		request.setAttribute(InformesKeys.TIPOS_INFORMES, tiposInformes);

		try {
		    MessageResources mr = getResources(request);
			ActionErrors errores = Utils.validarDatosInforme(informeForm, mr, LocaleFilterHelper.getCurrentLocale(request),path);

			if (errores != null) {
				saveErrors(request, errores);
				af = mapping.findForward("error");
			} else {
				List listaOficinas = (List) getListaFromSession(request,InformesKeys.KEY_LISTA_OFICINAS_INFORME);
				List listaPerfiles = (List) getListaFromSession(request,InformesKeys.KEY_LISTA_PERFILES_INFORME);
				List listaLibros = (List) getListaFromSession(request,InformesKeys.KEY_LISTA_LIBROS_INFORME);

			    informeForm.toUpperCase(request);
			    InformeBean nuevoInformeBean = informeForm.populate(listaOficinas, listaLibros, listaPerfiles);

			    oServicio.crearInforme(nuevoInformeBean, entidad);

				if( logger.isDebugEnabled())
					logger.debug("El informe creado es: " + nuevoInformeBean.toString());

				ActionMessages messages = new ActionMessages();
				ActionMessage mesage = new ActionMessage("ieci.tecdoc.sgm.rpadmin.informes.resultado.guardadoOK");
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
