package ieci.tecdoc.isicres.rpadmin.struts.acciones.unidades;



import ieci.tecdoc.isicres.rpadmin.struts.acciones.RPAdminWebAction;
import ieci.tecdoc.isicres.rpadmin.struts.forms.UnidadForm;
import ieci.tecdoc.isicres.rpadmin.struts.util.Utils;

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
import es.ieci.tecdoc.isicres.admin.beans.OrganizacionBean;
import es.ieci.tecdoc.isicres.admin.estructura.adapter.ISicresServicioRPAdminAdapter;
import es.ieci.tecdoc.isicres.admin.exception.ISicresRPAdminException;
import es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin;

public class GuardarNuevaUnidadRootAction extends RPAdminWebAction {

	private static final Logger logger = Logger.getLogger(GuardarNuevaUnidadRootAction.class);

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		ActionForward af = null;

		try {

			ISicresServicioRPAdmin oServicio = new ISicresServicioRPAdminAdapter();

		    UnidadForm unidadForm = (UnidadForm)form;
		    unidadForm.toUpperCase(request);

		    OrganizacionBean nuevaUnidad = new OrganizacionBean();
			BeanUtils.copyProperties(nuevaUnidad, unidadForm);

			if( logger.isDebugEnabled() ) {
				logger.debug("La unidad creada es: " + nuevaUnidad.toString());
			}

			//Se obtiene la entidad
			Entidad entidad = new Entidad();
			entidad.setIdentificador(MultiEntityContextHolder.getEntity());

			//validamos los datos del IR
			Utils.validateDatosIR(nuevaUnidad);

			int id = oServicio.crearOrganizacion(true, nuevaUnidad, entidad);
			request.setAttribute("nodoRaiz", Boolean.TRUE.toString());

			ActionMessages messages = new ActionMessages();
			ActionMessage mesage = new ActionMessage("ieci.tecdoc.sgm.rpadmin.unidades.resultado.guardadoOK");
			messages.add("Result: ", mesage);
			saveMessages(request, messages);
			af = mapping.findForward("success");

		}
		catch ( ISicresRPAdminException e)
		{
			logger.error("Error en la aplicacion", e);
			ActionErrors errores = new ActionErrors();
			ActionError error = null;
			if( e.getErrorCode() == ISicresRPAdminException.CODIGO_ORGANIZACION_SICRES_EXISTENTE) {
				error = new ActionError("ieci.tecdoc.sgm.rpadmin.error.mensaje.codigo.uid.duplicado");
			} else {
				error = new ActionError("ieci.tecdoc.sgm.rpadmin.error.mensaje", e.getMessage());
			}
			errores.add("Error interno", error);
			saveErrors(request, errores);
			af = mapping.findForward("error");
		}

		return af;
	}

}
