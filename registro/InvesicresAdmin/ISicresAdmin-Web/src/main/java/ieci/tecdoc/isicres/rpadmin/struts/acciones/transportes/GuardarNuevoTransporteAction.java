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
import es.ieci.tecdoc.isicres.admin.exception.ISicresRPAdminException;
import es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.TipoTransporteIntercambioRegistralManager;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.TipoTransporteIntercambioRegistralVO;

public class GuardarNuevoTransporteAction extends RPAdminWebAction {

	private static final Logger logger = Logger
			.getLogger(GuardarNuevoTransporteAction.class);

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionForward af = null;

		try {

			ISicresServicioRPAdmin oServicio = new ISicresServicioRPAdminAdapter();

			TransporteForm transporteForm = (TransporteForm) form;

			ActionErrors errores = Utils.validarDatosTransporte(transporteForm);

			transporteForm.toUpperCase(request);

			Transporte nuevoTransporte = new Transporte();
			BeanUtils.copyProperties(nuevoTransporte, transporteForm);

			if (logger.isDebugEnabled())
				logger.debug("El transporte creado es: "
						+ nuevoTransporte.toString());

			if (errores != null) {
				saveErrors(request, errores);
				af = mapping.findForward("error");
			} else {
				// Se obtiene la entidad
				Entidad entidad = new Entidad();
				entidad.setIdentificador(MultiEntityContextHolder.getEntity());

				oServicio.crearTransporte(nuevoTransporte, entidad);
				transporteForm.setIdTipoTransporte(nuevoTransporte.getId());

				createTipoTransporteSIR(transporteForm);

				ActionMessages messages = new ActionMessages();
				ActionMessage mesage = new ActionMessage(
						"ieci.tecdoc.sgm.rpadmin.transportes.resultado.guardadoOK");
				messages.add("Result: ", mesage);
				saveMessages(request, messages);
				af = mapping.findForward("success");
			}
		} catch (ISicresRPAdminException e) {
			logger.error("Error en la aplicación", e);
			ActionErrors errores = new ActionErrors();
			ActionError error = null;
			error = new ActionError("ieci.tecdoc.sgm.rpadmin.error.mensaje",
					e.getMessage());
			errores.add("Error interno", error);
			saveErrors(request, errores);
			af = mapping.findForward("error");
		}

		return af;
	}

	/**
	 * @param transporteForm
	 */
	private void createTipoTransporteSIR(TransporteForm transporteForm) {
		if (StringUtils.isNotEmpty(transporteForm
				.getCodigoIntercambioRegistral())) {
			TipoTransporteIntercambioRegistralManager manager = es.ieci.tecdoc.isicres.admin.business.spring.AdminIRManagerProvider
					.getInstance()
					.getTipoTransporteIntercambioRegistralManager();
			TipoTransporteIntercambioRegistralVO tipoTransporteIntercambioRegistralVO = new TipoTransporteIntercambioRegistralVO();
			tipoTransporteIntercambioRegistralVO.setCodigoSIR(transporteForm
					.getCodigoIntercambioRegistral());
			tipoTransporteIntercambioRegistralVO.setDescripcion(transporteForm
					.getTransport().toUpperCase());
			tipoTransporteIntercambioRegistralVO
					.setIdTipoTransporte(transporteForm.getIdTipoTransporte());
			manager.save(tipoTransporteIntercambioRegistralVO);
		}
	}

}
