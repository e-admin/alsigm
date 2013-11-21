package ieci.tecdoc.isicres.rpadmin.struts.acciones.transportes;

import ieci.tecdoc.isicres.rpadmin.struts.acciones.RPAdminWebAction;
import ieci.tecdoc.isicres.rpadmin.struts.forms.TransporteForm;
import ieci.tecdoc.isicres.rpadmin.struts.util.Utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
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
import es.ieci.tecdoc.isicres.admin.beans.Transporte;
import es.ieci.tecdoc.isicres.admin.estructura.adapter.ISicresServicioRPAdminAdapter;
import es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.TipoTransporteIntercambioRegistralManager;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.TipoTransporteIntercambioRegistralVO;

public class GuardarEdicionTransporteAction extends RPAdminWebAction {

	private static final Logger logger = Logger
			.getLogger(GuardarEdicionTransporteAction.class);

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionForward af = null;

		try {
			ISicresServicioRPAdmin oServicio = new ISicresServicioRPAdminAdapter();

			// Guardamos los datos del Transporte desde la opción de guardar de
			// la
			// pagina de Edición si el id viene relleno
			String id = (String) request.getParameter("idTransporte");

			if (id != null && !id.equals("")) {

				TransporteForm transporteForm = (TransporteForm) form;
				Transporte transporte = new Transporte();

				ActionErrors errores = Utils
						.validarDatosTransporte(transporteForm);

				if (errores != null) {
					saveErrors(request, errores);
					af = mapping.findForward("error");
				} else {
					transporteForm.toUpperCase(request);

					BeanUtils.copyProperties(transporte, transporteForm);
					transporte.setId(Integer.parseInt(id));

					// Se obtiene la entidad
					Entidad entidad = new Entidad();
					entidad.setIdentificador(MultiEntityContextHolder
							.getEntity());

					oServicio.editarTransporte(transporte, entidad);
					transporteForm.setIdTipoTransporte(transporte.getId());
					updateTipoTransporteSIR(transporteForm);

					ActionMessages messages = new ActionMessages();
					ActionMessage mesage = new ActionMessage(
							"ieci.tecdoc.sgm.rpadmin.transportes.resultado.editadoOK");
					messages.add("Result: ", mesage);
					saveMessages(request, messages);
					af = mapping.findForward("success");
				}
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

	/**
	 * @param transporteForm
	 */
	private void updateTipoTransporteSIR(TransporteForm transporteForm) {

		TipoTransporteIntercambioRegistralManager manager = es.ieci.tecdoc.isicres.admin.business.spring.AdminIRManagerProvider
				.getInstance().getTipoTransporteIntercambioRegistralManager();
		TipoTransporteIntercambioRegistralVO tipoTransporteIntercambioRegistralVO = new TipoTransporteIntercambioRegistralVO();
		tipoTransporteIntercambioRegistralVO.setCodigoSIR(transporteForm
				.getCodigoIntercambioRegistral());
		tipoTransporteIntercambioRegistralVO.setDescripcion(transporteForm
				.getTransport().toUpperCase());
		tipoTransporteIntercambioRegistralVO.setIdTipoTransporte(transporteForm
				.getIdTipoTransporte());
		TipoTransporteIntercambioRegistralVO transporteSIR = manager
				.getTipoTransporteByIdSicres(transporteForm
						.getIdTipoTransporte());
		/*
		 * Si ya esta creado el tipo de transporte hay que actualizarlo o eliminarlo
		 */
		if (transporteSIR != null) {
			if (StringUtils.isNotEmpty(transporteForm
					.getCodigoIntercambioRegistral())) {
				manager.updateByIdTipoTransporte(tipoTransporteIntercambioRegistralVO);
			}else{
				manager.delete(transporteSIR.getId());
			}
		} else {
			manager.save(tipoTransporteIntercambioRegistralVO);
		}

	}

}
