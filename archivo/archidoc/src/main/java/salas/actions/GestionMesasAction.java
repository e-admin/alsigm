package salas.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import salas.SalasConsultaConstants;
import salas.exceptions.SalasConsultaException;
import salas.form.MesaForm;
import salas.model.GestionSalasConsultaBI;
import salas.vos.EdificioVO;
import salas.vos.MesaVO;
import salas.vos.SalaVO;
import util.CollectionUtils;
import util.ErrorsTag;

import common.Constants;
import common.Messages;
import common.navigation.KeysClientsInvocations;
import common.util.StringUtils;
import common.util.TypeConverter;

public class GestionMesasAction extends SalasConsultaBaseAction {

	public void nuevoExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		GestionSalasConsultaBI salasBI = getGestionSalasBI(request);
		String idSala = request
				.getParameter(SalasConsultaConstants.PARAM_ID_SALA);
		SalaVO salaVO = salasBI.getSalaById(idSala);
		List listaMesas = salasBI.getMesas(salaVO.getId());
		if (listaMesas != null)
			salaVO.setNumMesas(listaMesas.size());
		setInTemporalSession(request, SalasConsultaConstants.SALA_KEY, salaVO);
		saveCurrentInvocation(KeysClientsInvocations.SALAS_CREAR_MESAS, request);
		setReturnActionFordward(
				request,
				mappings.findForward(SalasConsultaConstants.FORWARD_NUMERO_MESAS_CREAR));
	}

	public void guardarExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		GestionSalasConsultaBI salasBI = getGestionSalasBI(request);
		MesaForm mesaForm = (MesaForm) form;
		SalaVO salaVO = (SalaVO) getFromTemporalSession(request,
				SalasConsultaConstants.SALA_KEY);

		ActionErrors errors = validateNumMesas(request, mesaForm);
		if (errors.isEmpty()) {
			try {
				int codigoInicial = 1;
				if (StringUtils.isNotEmpty(salaVO.getId()))
					codigoInicial = salasBI.getMaxNumOrden(salaVO.getId()) + 1;

				EdificioVO edificioVO = null;
				if (StringUtils.isNotEmpty(salaVO.getIdEdificio()))
					edificioVO = salasBI
							.getEdificioById(salaVO.getIdEdificio());

				int numMesas = 0;
				if (StringUtils.isNotEmpty(mesaForm.getNumeroMesas()))
					numMesas = TypeConverter.toInt(mesaForm.getNumeroMesas());

				List pathMesas = generarPathMesas(mesaForm, salaVO, edificioVO,
						codigoInicial, numMesas);
				List listaMesas = salasBI.getMesas(salaVO.getId());
				if (listaMesas != null)
					salaVO.setNumMesas(listaMesas.size());
				setInTemporalSession(request, SalasConsultaConstants.SALA_KEY,
						salaVO);
				setInTemporalSession(request,
						SalasConsultaConstants.LISTA_PATHS_MESAS_KEY, pathMesas);

				setReturnActionFordward(
						request,
						mappings.findForward(SalasConsultaConstants.FORWARD_CREAR_MESAS));
			} catch (SecurityException e) {
				accionSinPermisos(request);
				goLastClientExecuteLogic(mappings, form, request, response);
			} catch (Exception e) {
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionError(
						Constants.GLOBAL_ARCHIGEST_EXCEPTION, e.getMessage()));
				ErrorsTag.saveErrors(request, errors);
				logger.error(
						"Se ha producido un error al guardar los datos de la mesas en la sala: "
								+ salaVO, e);
				setReturnActionFordward(
						request,
						getActionRedirectVerSala(mappings, salaVO.getId(),
								false));
			}
		} else {
			ErrorsTag.saveErrors(request, errors);
			setReturnActionFordward(
					request,
					mappings.findForward(SalasConsultaConstants.FORWARD_NUMERO_MESAS_CREAR));
		}
	}

	public void finalizarExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		GestionSalasConsultaBI salasBI = getGestionSalasBI(request);
		MesaForm mesaForm = (MesaForm) form;
		SalaVO salaVO = (SalaVO) getFromTemporalSession(request,
				SalasConsultaConstants.SALA_KEY);
		HashMap codigoPos = mesaForm.getValues();
		ActionErrors errors = new ActionErrors();
		try {
			String[] codigosMesa = comprobarCodigos(request, salaVO, codigoPos,
					errors, null);
			if (errors != null && !errors.isEmpty()) {
				ErrorsTag.saveErrors(request, errors);
				setReturnActionFordward(
						request,
						mappings.findForward(SalasConsultaConstants.FORWARD_CREAR_MESAS));
			} else {
				salasBI.insertarSala(salaVO, codigosMesa);
				insertarNodoTreeView(request, salaVO);
				goLastClientExecuteLogic(mappings, mesaForm, request, response);
				setReturnActionFordward(
						request,
						getActionRedirectVerSala(mappings, salaVO.getId(), true));
			}
		} catch (SecurityException e) {
			accionSinPermisos(request);
			setReturnActionFordward(
					request,
					mappings.findForward(SalasConsultaConstants.FORWARD_CREAR_MESAS));
		} catch (SalasConsultaException e) {
			guardarError(request, e);
			setReturnActionFordward(
					request,
					mappings.findForward(SalasConsultaConstants.FORWARD_CREAR_MESAS));
		} catch (Exception e) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionError(
					Constants.GLOBAL_ARCHIGEST_EXCEPTION, e.getMessage()));
			ErrorsTag.saveErrors(request, errors);
			logger.error(
					"Se ha producido un error al guardar los datos de la sala: "
							+ salaVO, e);
			setReturnActionFordward(
					request,
					mappings.findForward(SalasConsultaConstants.FORWARD_CREAR_MESAS));
		}
	}

	public void aceptarModificarMesasExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		try {
			GestionSalasConsultaBI salasBI = getGestionSalasBI(request);
			MesaForm mesaForm = (MesaForm) form;
			SalaVO salaVO = salasBI.getSalaById(mesaForm.getIdSala());
			EdificioVO edificioVO = salasBI.getEdificioById(salaVO
					.getIdEdificio());

			String[] idsMesaSelected = mesaForm.getIdsMesa();
			List listaIds = new ArrayList();
			List pathMesas = new ArrayList();
			int contador = 1;
			if (idsMesaSelected != null && idsMesaSelected.length > 0) {
				List listaMesas = salasBI.getMesasById(idsMesaSelected);
				if (!listaMesas.isEmpty()) {
					for (Iterator iter = listaMesas.iterator(); iter.hasNext();) {
						MesaVO mesaVO = (MesaVO) iter.next();
						String path = edificioVO.getNombre() + Constants.SLASH
								+ salaVO.getNombre();
						String pathMesa = path
								+ Constants.SLASH
								+ SalasConsultaConstants.PATH_NAME_UBICACION_MESA
								+ mesaVO.getNumOrden();
						pathMesas.add(pathMesa);
						listaIds.add(mesaVO.getId());
						mesaForm.getValues().put(String.valueOf(contador++),
								mesaVO.getCodigo());
					}
				}
			}
			List listaMesasSala = salasBI.getMesas(salaVO.getId());
			if (listaMesasSala != null)
				salaVO.setNumMesas(listaMesasSala.size());
			setInTemporalSession(request, SalasConsultaConstants.SALA_KEY,
					salaVO);
			setInTemporalSession(request,
					SalasConsultaConstants.LISTA_PATHS_MESAS_KEY, pathMesas);
			setInTemporalSession(request,
					SalasConsultaConstants.LISTA_IDS_MESA_KEY, listaIds);
			setInTemporalSession(request,
					SalasConsultaConstants.ACTION_MODIFICAR_MESAS_KEY,
					Boolean.TRUE);
			setReturnActionFordward(
					request,
					mappings.findForward(SalasConsultaConstants.FORWARD_CREAR_MESAS));
		} catch (Exception e) {
			ActionErrors errors = new ActionErrors();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionError(
					Constants.GLOBAL_ARCHIGEST_EXCEPTION, e.getMessage()));
			ErrorsTag.saveErrors(request, errors);
			setReturnActionFordward(request,
					mappings.findForward(SalasConsultaConstants.FORWARD_DATOS));
			logger.error(
					"Se ha producido un error al modificar los códigos de mesa.",
					e);
		}
	}

	public void modificarCodigosExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		GestionSalasConsultaBI salasBI = getGestionSalasBI(request);
		MesaForm mesaForm = (MesaForm) form;
		SalaVO salaVO = (SalaVO) getFromTemporalSession(request,
				SalasConsultaConstants.SALA_KEY);
		HashMap codigoPos = mesaForm.getValues();
		ActionErrors errors = new ActionErrors();
		try {
			List listaIds = (List) getFromTemporalSession(request,
					SalasConsultaConstants.LISTA_IDS_MESA_KEY);
			String[] idsMesa = CollectionUtils.toArray(listaIds);
			String[] codigosMesa = comprobarCodigos(request, salaVO, codigoPos,
					errors, idsMesa);
			if (errors != null && !errors.isEmpty()) {
				ErrorsTag.saveErrors(request, errors);
				setReturnActionFordward(
						request,
						mappings.findForward(SalasConsultaConstants.FORWARD_CREAR_MESAS));
			} else {
				salasBI.actualizarCodigosMesa(idsMesa, codigosMesa);
				goReturnPointExecuteLogic(mappings, mesaForm, request, response);
				setReturnActionFordward(
						request,
						getActionRedirectVerSala(mappings, salaVO.getId(), true));
			}
		} catch (Exception e) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionError(
					Constants.GLOBAL_ARCHIGEST_EXCEPTION, e.getMessage()));
			ErrorsTag.saveErrors(request, errors);
			logger.error(
					"Se ha producido un error al actualizar los codigos de las mesas.",
					e);
			setReturnActionFordward(
					request,
					mappings.findForward(SalasConsultaConstants.FORWARD_CREAR_MESAS));
		}
	}

	public void eliminarExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		GestionSalasConsultaBI salasBI = getGestionSalasBI(request);
		try {
			MesaForm mesaForm = (MesaForm) form;
			String[] idsMesa = mesaForm.getIdsMesa();
			salasBI.eliminarMesas(mesaForm.getIdSala(), idsMesa);
			setReturnActionFordward(
					request,
					getActionRedirectVerSala(mappings, mesaForm.getIdSala(),
							false));
		} catch (SecurityException e) {
			accionSinPermisos(request);
			goLastClientExecuteLogic(mappings, form, request, response);
		} catch (Exception e) {
			ActionErrors errors = new ActionErrors();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionError(
					Constants.GLOBAL_ARCHIGEST_EXCEPTION, e.getMessage()));
			ErrorsTag.saveErrors(request, errors);
			setReturnActionFordward(request,
					mappings.findForward(SalasConsultaConstants.FORWARD_DATOS));
			logger.error("Se ha producido un error al eliminar las mesas.", e);
		}
	}

	public void eliminarMesasExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		operacionesMesas(mappings, form, request, response,
				SalasConsultaConstants.ACTION_ELIMINAR_MESAS_KEY);
	}

	public void modificarMesasExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		operacionesMesas(mappings, form, request, response,
				SalasConsultaConstants.ACTION_MODIFICAR_MESAS_KEY);
	}

	private void operacionesMesas(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			String actionMesas) {

		GestionSalasConsultaBI salasBI = getGestionSalasBI(request);
		String idSala = request
				.getParameter(SalasConsultaConstants.PARAM_ID_SALA);
		SalaVO salaVO = salasBI.getSalaById(idSala);
		List listaMesas = salasBI.getMesas(salaVO.getId());
		boolean errores = false;
		if (listaMesas != null) {
			salaVO.setNumMesas(listaMesas.size());

			int numMesasOcupadas = salasBI.getCountMesasOcupadasByIdSala(salaVO
					.getId());
			if (listaMesas.size() > numMesasOcupadas) {
				setInTemporalSession(request, SalasConsultaConstants.SALA_KEY,
						salaVO);
				request.setAttribute(SalasConsultaConstants.LISTA_MESAS_KEY,
						listaMesas);
				request.setAttribute(actionMesas, Boolean.TRUE);
			} else {
				errores = true;
			}
		} else {
			errores = true;
		}

		if (errores) {
			ActionErrors errors = new ActionErrors();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionError(
					SalasConsultaConstants.ERROR_NO_HAY_MESAS_A_MODIFICAR));
			ErrorsTag.saveErrors(request, errors);
			goLastClientExecuteLogic(mappings, form, request, response);
		} else {
			saveCurrentInvocation(KeysClientsInvocations.SALAS_SELECCION_MESAS,
					request);
			setReturnActionFordward(
					request,
					mappings.findForward(SalasConsultaConstants.FORWARD_SELECCION_MESAS));
		}
	}

	private List generarPathMesas(MesaForm mesaForm, SalaVO salaVO,
			EdificioVO edificioVO, int codigoInicial, int numMesas) {
		List pathMesas = new ArrayList();
		int contador = 1;
		for (int i = codigoInicial; i < (codigoInicial + numMesas); i++) {
			String path = edificioVO.getNombre() + Constants.SLASH
					+ salaVO.getNombre();
			String pathMesa = path + Constants.SLASH
					+ SalasConsultaConstants.PATH_NAME_UBICACION_MESA + i;
			pathMesas.add(pathMesa);

			mesaForm.getValues().put(String.valueOf(contador++),
					String.valueOf(i));
		}
		return pathMesas;
	}

	private String[] comprobarCodigos(HttpServletRequest request,
			SalaVO salaVO, HashMap codigoPos, ActionErrors errors,
			String[] idsMesa) {
		GestionSalasConsultaBI salasBI = getGestionSalasBI(request);
		int numOrden = 1;
		List codigosMesaVacios = new ArrayList();
		List codigosMesaDuplicados = new ArrayList();
		String[] codigosMesa = new String[codigoPos.size()];
		for (int i = 0; i < codigoPos.size(); i++) {
			codigosMesa[i] = (String) codigoPos.get(String.valueOf(numOrden++));
			if (StringUtils.isEmpty(codigosMesa[i])) {
				codigosMesaVacios.add(codigosMesa[i]);
			} else if (salaVO.getId() != null) {
				MesaVO mesaVO = salasBI.getMesaBySalaAndCodigo(salaVO.getId(),
						codigosMesa[i]);
				if (ArrayUtils.isEmpty(idsMesa) && mesaVO != null)
					codigosMesaDuplicados.add(codigosMesa[i]);
				if (!ArrayUtils.isEmpty(idsMesa)
						&& (mesaVO != null && !mesaVO.getId()
								.equals(idsMesa[i])))
					codigosMesaDuplicados.add(codigosMesa[i]);
			}
		}

		if (codigosMesaVacios != null && !codigosMesaVacios.isEmpty()) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionError(
					SalasConsultaConstants.ERROR_CODIGO_MESA_VACIO));
		} else if (codigosMesaDuplicados != null
				&& !codigosMesaDuplicados.isEmpty()) {
			String codigosDuplicados = "";
			for (Iterator iter = codigosMesaDuplicados.iterator(); iter
					.hasNext();) {
				codigosDuplicados += (String) iter.next();
				if (iter.hasNext())
					codigosDuplicados += ",";
			}
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionError(
					SalasConsultaConstants.ERROR_CODIGO_MESA_DUPLICADO,
					codigosDuplicados));
		}

		return codigosMesa;
	}

	private ActionErrors validateNumMesas(HttpServletRequest request,
			MesaForm mesaForm) {
		ActionErrors errors = new ActionErrors();
		if (GenericValidator.isBlankOrNull(mesaForm.getNumeroMesas())) {
			errors.add(
					ActionErrors.GLOBAL_ERROR,
					new ActionError(
							Constants.ERROR_REQUIRED,
							Messages.getString(
									SalasConsultaConstants.ETIQUETA_NUMERO_MESAS,
									request.getLocale())));
		} else if (TypeConverter.toInt(mesaForm.getNumeroMesas(), -1) < 0)
			errors.add(
					ActionErrors.GLOBAL_ERROR,
					new ActionError(
							Messages.getString(
									SalasConsultaConstants.ERROR_NUMERO_MESAS_INCORRECTO,
									request.getLocale())));
		return errors;
	}
}