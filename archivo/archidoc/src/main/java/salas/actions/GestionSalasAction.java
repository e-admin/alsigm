package salas.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import salas.SalasConsultaConstants;
import salas.exceptions.SalasConsultaException;
import salas.form.SalaForm;
import salas.model.GestionSalasConsultaBI;
import salas.vos.SalaVO;
import util.ErrorsTag;

import common.Constants;
import common.Messages;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;
import common.util.StringUtils;
import common.util.TypeConverter;

public class GestionSalasAction extends SalasConsultaBaseAction {

	public void listadoExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		try {
			ClientInvocation invocation = saveCurrentInvocation(
					KeysClientsInvocations.SALAS_LISTADO_SALAS, request);
			invocation.setAsReturnPoint(true);

			SalaForm salaForm = (SalaForm) form;
			salaForm.reset();

			String idEdificio = request
					.getParameter(SalasConsultaConstants.PARAM_ID_EDIFICIO);
			removeInTemporalSession(request, SalasConsultaConstants.SALA_KEY);
			removeInTemporalSession(request,
					SalasConsultaConstants.LISTA_SALAS_KEY);
			GestionSalasConsultaBI salasBI = getGestionSalasBI(request);
			List listaSalas = salasBI.getSalas(idEdificio);
			setInTemporalSession(request,
					SalasConsultaConstants.LISTA_SALAS_KEY, listaSalas);

			setReturnActionFordward(
					request,
					mappings.findForward(SalasConsultaConstants.FORWARD_LISTADO));
		} catch (SecurityException e) {
			accionSinPermisos(request);
			goLastClientExecuteLogic(mappings, form, request, response);
		}
	}

	public void verExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		try {
			ClientInvocation invocation = saveCurrentInvocation(
					KeysClientsInvocations.SALAS_VER_SALA, request);
			invocation.setAsReturnPoint(true);

			if (Boolean
					.valueOf(
							request.getParameter(SalasConsultaConstants.PARAM_REFRESHVIEW))
					.booleanValue()) {
				request.setAttribute(SalasConsultaConstants.REFRESH_VIEW_KEY,
						Boolean.TRUE);
			}

			String idSala = request
					.getParameter(SalasConsultaConstants.PARAM_ID_SALA);
			GestionSalasConsultaBI salasBI = getGestionSalasBI(request);
			SalaVO salaVO = salasBI.getSalaById(idSala);
			setInTemporalSession(request, SalasConsultaConstants.SALA_KEY,
					salaVO);

			List listaMesas = salasBI.getMesas(idSala);
			if (listaMesas != null)
				salaVO.setNumMesas(listaMesas.size());
			setInTemporalSession(request,
					SalasConsultaConstants.LISTA_MESAS_KEY, listaMesas);

			verNodoTreeView(request, salaVO);

			setReturnActionFordward(request,
					mappings.findForward(SalasConsultaConstants.FORWARD_DATOS));
		} catch (SecurityException e) {
			accionSinPermisos(request);
			goLastClientExecuteLogic(mappings, form, request, response);
		}
	}

	public void nuevoExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		SalaForm salaForm = (SalaForm) form;
		salaForm.reset();

		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.SALAS_CREAR_SALA, request);
		invocation.setAsReturnPoint(true);
		setReturnActionFordward(request,
				mappings.findForward(SalasConsultaConstants.FORWARD_EDICION));
	}

	private void setPermitidoModificarSala(HttpServletRequest request,
			String idSala) {

		if (StringUtils.isNotEmpty(idSala)) {
			GestionSalasConsultaBI salasBI = getGestionSalasBI(request);
			if (salasBI.existenMesasOcupadas(idSala))
				request.setAttribute(
						SalasConsultaConstants.PERMITIR_MODIFICAR_MESA_KEY,
						Boolean.FALSE);
			else
				request.setAttribute(
						SalasConsultaConstants.PERMITIR_MODIFICAR_MESA_KEY,
						Boolean.TRUE);
		}
	}

	public void edicionExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		SalaForm salaForm = (SalaForm) form;
		GestionSalasConsultaBI salasBI = getGestionSalasBI(request);
		String idSala = salaForm.getIdSala();
		if (StringUtils.isNotEmpty(idSala)) {
			SalaVO salaVO = salasBI.getSalaById(idSala);

			List listaMesas = salasBI.getMesas(idSala);
			if (listaMesas != null)
				salaVO.setNumMesas(listaMesas.size());

			salaForm.set(salaVO);

			if (salaVO != null
					&& StringUtils.isNotEmpty(salaVO.getIdEdificio())) {
				setPermitidoModificarSala(request, salaVO.getId());
				saveCurrentInvocation(KeysClientsInvocations.SALAS_EDITAR_SALA,
						request);
				setReturnActionFordward(
						request,
						mappings.findForward(SalasConsultaConstants.FORWARD_EDICION));
			} else {
				if (logger.isDebugEnabled()) {
					logger.debug("No se ha encontrado el objeto Sala para el id:"
							+ idSala);
				}
				accionDatosElementoNoEncontrado(
						request,
						Messages.getString(SalasConsultaConstants.ETIQUETA_OBJETO_SALA));
				goLastClientExecuteLogic(mappings, form, request, response);
			}
		} else {
			if (logger.isDebugEnabled()) {
				logger.debug("No se ha recibido el parámetro idSala");
			}
			accionDatosElementoNoEncontrado(
					request,
					Messages.getString(SalasConsultaConstants.ETIQUETA_OBJETO_SALA));
			goLastClientExecuteLogic(mappings, form, request, response);
		}
	}

	public void guardarExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		GestionSalasConsultaBI salasBI = getGestionSalasBI(request);
		SalaVO salaVO = new SalaVO();
		SalaForm salaForm = (SalaForm) form;
		salaForm.populate(salaVO);
		ActionErrors errors = validateForm(request, form);
		setPermitidoModificarSala(request, salaForm.getIdSala());
		if (errors.isEmpty()) {
			try {
				int numMesas = 0;
				if (StringUtils.isEmpty(salaForm.getIdSala())) {

					// Número de mesas a crear
					if (StringUtils.isNotEmpty(salaForm.getNumeroMesas()))
						numMesas = TypeConverter.toInt(salaForm
								.getNumeroMesas());

					if (numMesas > 0) {
						setInTemporalSession(request,
								SalasConsultaConstants.SALA_KEY, salaVO);
						setReturnActionFordward(
								request,
								getActionRedirectCrearMesas(mappings,
										salaForm.getNumeroMesas()));
					} else {
						salasBI.insertarSala(salaVO, null);
						insertarNodoTreeView(request, salaVO);
						setReturnActionFordward(
								request,
								getActionRedirectVerSala(mappings,
										salaVO.getId(), true));
					}
				} else {
					salasBI.actualizarSala(salaVO);
					actualizarNodoTreeView(request, salaVO);
					setReturnActionFordward(
							request,
							getActionRedirectVerSala(mappings, salaVO.getId(),
									true));
				}
			} catch (SecurityException e) {
				accionSinPermisos(request);
				goLastClientExecuteLogic(mappings, form, request, response);
			} catch (SalasConsultaException e) {
				guardarError(request, e);
				goLastClientExecuteLogic(mappings, form, request, response);
			} catch (Exception e) {
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionError(
						Constants.GLOBAL_ARCHIGEST_EXCEPTION, e.getMessage()));
				ErrorsTag.saveErrors(request, errors);
				logger.error(
						"Se ha producido un error al guardar los datos de la sala: "
								+ salaVO, e);
				setReturnActionFordward(
						request,
						mappings.findForward(SalasConsultaConstants.FORWARD_EDICION));
			}
		} else {
			ErrorsTag.saveErrors(request, errors);
			setReturnActionFordward(
					request,
					mappings.findForward(SalasConsultaConstants.FORWARD_EDICION));
		}
	}

	public void eliminarExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		GestionSalasConsultaBI salasBI = getGestionSalasBI(request);
		String idSala = request
				.getParameter(SalasConsultaConstants.PARAM_ID_SALA);
		try {
			SalaVO salaVO = (SalaVO) getFromTemporalSession(request,
					SalasConsultaConstants.SALA_KEY);
			salaVO.setId(idSala);
			salasBI.eliminarSala(salaVO);

			eliminarNodoTreeView(request);

			setReturnActionFordward(
					request,
					getActionRedirectVerEdificio(mappings,
							salaVO.getIdEdificio(), true));
		} catch (SecurityException e) {
			accionSinPermisos(request);
			goLastClientExecuteLogic(mappings, form, request, response);
		} catch (SalasConsultaException scm) {
			guardarError(request, scm);
			goLastClientExecuteLogic(mappings, form, request, response);
		} catch (Exception e) {
			ActionErrors errors = new ActionErrors();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionError(
					Constants.GLOBAL_ARCHIGEST_EXCEPTION, e.getMessage()));
			ErrorsTag.saveErrors(request, errors);
			setReturnActionFordward(request,
					mappings.findForward(SalasConsultaConstants.FORWARD_DATOS));
			logger.error(
					"Se ha producido un error al eliminar la sala con id: "
							+ idSala, e);
		}
	}

	public void verPadreExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		String idSala = request
				.getParameter(SalasConsultaConstants.PARAM_ID_SALA);
		GestionSalasConsultaBI salasBI = getGestionSalasBI(request);
		SalaVO salaVO = salasBI.getSalaById(idSala);
		if (salaVO != null && StringUtils.isNotEmpty(salaVO.getIdEdificio()))
			setReturnActionFordward(
					request,
					getActionRedirectVerEdificio(mappings,
							salaVO.getIdEdificio(), true));
		else
			goBackExecuteLogic(mappings, form, request, response);
	}

	private ActionErrors validateForm(HttpServletRequest request,
			ActionForm form) {
		ActionErrors errors = new ActionErrors();
		SalaForm salaForm = (SalaForm) form;

		if (GenericValidator.isBlankOrNull(salaForm.getNombre())) {
			errors.add(
					ActionErrors.GLOBAL_ERROR,
					new ActionError(Constants.ERROR_REQUIRED, Messages
							.getString(Constants.ETIQUETA_NOMBRE,
									request.getLocale())));
		}

		GestionSalasConsultaBI salasBI = getGestionSalasBI(request);
		SalaVO salaVO = salasBI.getSalaByNombreAndEdificio(
				salaForm.getNombre(), salaForm.getIdEdificio());
		if ((salaVO != null) && (!salaVO.getId().equals(salaForm.getIdSala()))) {
			errors.add(
					ActionErrors.GLOBAL_ERROR,
					new ActionError(Messages.getString(
							SalasConsultaConstants.ERROR_NOMBRE_SALA_DUPLICADO,
							request.getLocale())));
		}

		errors.add(validateNumMesas(request, salaForm));

		return errors;
	}

	private ActionErrors validateNumMesas(HttpServletRequest request,
			SalaForm salaForm) {
		ActionErrors errors = new ActionErrors();
		if (GenericValidator.isBlankOrNull(salaForm.getNumeroMesas())) {
			errors.add(
					ActionErrors.GLOBAL_ERROR,
					new ActionError(
							Constants.ERROR_REQUIRED,
							Messages.getString(
									SalasConsultaConstants.ETIQUETA_NUMERO_MESAS,
									request.getLocale())));
		} else if (TypeConverter.toInt(salaForm.getNumeroMesas(), -1) < 0)
			errors.add(
					ActionErrors.GLOBAL_ERROR,
					new ActionError(
							Messages.getString(
									SalasConsultaConstants.ERROR_NUMERO_MESAS_INCORRECTO,
									request.getLocale())));
		return errors;
	}
}