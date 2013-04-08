package deposito.actions.niveles;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import util.ErrorsTag;
import xml.config.ConfiguracionArchivoManager;

import common.actions.BaseAction;
import common.navigation.KeysClientsInvocations;
import common.util.ArrayUtils;
import common.util.ListUtils;
import common.util.StringUtils;
import common.vos.KeyValueVO;

import deposito.DepositoConstants;
import deposito.exceptions.TipoElementoDepositoException;
import deposito.forms.NivelesDepositoForm;
import deposito.model.GestorEstructuraDepositoBI;
import deposito.vos.IconoDeposito;
import deposito.vos.TipoElementoVO;

public class GestionNivelesDepositoAction extends BaseAction {

	public void initExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		saveCurrentInvocation(KeysClientsInvocations.DEPOSITO_NIVELES, request);

		List listaNivelesCuadro = getGestorEstructuraDepositoBI(request)
				.getTiposElemento(
						null,
						new String[] { DepositoConstants.ID_TIPO_ELEMENTO_UBICACION });
		if (ListUtils.isEmpty(listaNivelesCuadro)) {
			listaNivelesCuadro = new ArrayList();
		}

		setInTemporalSession(request, DepositoConstants.LISTA_NIVELES_KEY,
				listaNivelesCuadro);

		setReturnActionFordward(request, mappings.findForward("init"));
	}

	public void nuevoNivelExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		saveCurrentInvocation(KeysClientsInvocations.DEPOSITO_NIVELES_ALTA,
				request);

		cargarListaPredeterminados(request);

		NivelesDepositoForm formulario = (NivelesDepositoForm) form;

		String idPadre = request.getParameter("idPadre");

		TipoElementoVO tipoElementoPadreVO = getGestorEstructuraDepositoBI(
				request).getTipoElementoSingleton(idPadre);

		setInTemporalSession(request, DepositoConstants.NIVEL_DEPOSITO_KEY,
				new TipoElementoVO());
		setInTemporalSession(request,
				DepositoConstants.NIVEL_PADRE_DEPOSITO_KEY, tipoElementoPadreVO);

		formulario.setIdPadre(idPadre);

		setReturnActionFordward(request, mappings.findForward("alta_nivel"));
	}

	public void verNivelExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		saveCurrentInvocation(KeysClientsInvocations.DEPOSITO_NIVELES_DATOS,
				request);
		verNivelCodeLogic(mappings, form, request, response);
	}

	public void verSubNivelExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		saveCurrentInvocation(KeysClientsInvocations.DEPOSITO_SUBNIVELES_DATOS,
				request);
		verNivelCodeLogic(mappings, form, request, response);
	}

	public void verNivelCodeLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		removeInTemporalSession(request,
				DepositoConstants.LISTA_NIVELES_DEPOSITO_HIJO);
		removeInTemporalSession(request, DepositoConstants.NIVEL_DEPOSITO_KEY);
		removeInTemporalSession(request,
				DepositoConstants.NIVEL_PADRE_DEPOSITO_KEY);
		removeInTemporalSession(request,
				DepositoConstants.LISTA_ELEMENTOS_DEFECTO);

		GestorEstructuraDepositoBI depositoBI = getGestorEstructuraDepositoBI(request);

		String idNivel = request.getParameter("idNivel");
		if (StringUtils.isNotEmpty(idNivel)) {
			TipoElementoVO tipoElementoVO = depositoBI
					.getTipoElementoSingleton(idNivel);

			// Obtener los niveles hijos
			List listaNivelesHijo = depositoBI
					.getTiposElemento(
							idNivel,
							new String[] { DepositoConstants.ID_TIPO_ELEMENTO_UBICACION });
			setInTemporalSession(request,
					DepositoConstants.LISTA_NIVELES_DEPOSITO_HIJO,
					listaNivelesHijo);

			if (ListUtils.isNotEmpty(listaNivelesHijo)) {
				tipoElementoVO.setHasSubniveles(true);
			} else {
				tipoElementoVO.setHasSubniveles(false);
			}

			if (depositoBI.isTipoElementoReferenciado(tipoElementoVO.getId())) {
				tipoElementoVO.setElementoReferenciado(true);
			} else {
				tipoElementoVO.setElementoReferenciado(false);
			}

			setInTemporalSession(request, DepositoConstants.NIVEL_DEPOSITO_KEY,
					tipoElementoVO);
		}

		setReturnActionFordward(request, mappings.findForward("datos_nivel"));
	}

	private void cargarListaPredeterminados(HttpServletRequest request) {
		GestorEstructuraDepositoBI depositoBI = getGestorEstructuraDepositoBI(request);
		List listaElementos = depositoBI.getTiposElemento(null, null);

		List listaElementosDefecto = new ArrayList();

		String[] ids = ArrayUtils.getArrayIds(listaElementos);

		LinkedHashMap iconos = ConfiguracionArchivoManager.getInstance()
				.getIconosDeposito();

		for (Iterator iterator = iconos.keySet().iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			IconoDeposito value = (IconoDeposito) iconos.get(key);

			KeyValueVO keyValueVO = new KeyValueVO(key, value.getIconoDefecto());

			if (StringUtils.isNotEmpty(ids) && !ArrayUtils.contains(ids, key)) {
				listaElementosDefecto.add(keyValueVO);
			}
		}
		setInTemporalSession(request,
				DepositoConstants.LISTA_ELEMENTOS_DEFECTO,
				listaElementosDefecto);
	}

	/**
	 * Carga la página para la edición del nivel del cuadro de clasificacion
	 * seleccionado.
	 * 
	 * @param mappings
	 * @param form
	 * @param request
	 * @param response
	 */
	public void editarNivelExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		saveCurrentInvocation(KeysClientsInvocations.DEPOSITO_NIVELES_EDICION,
				request);
		removeInTemporalSession(request,
				DepositoConstants.NIVEL_PADRE_DEPOSITO_KEY);

		NivelesDepositoForm formulario = (NivelesDepositoForm) form;

		String idTipoElemento = request.getParameter("idNivel");
		if (StringUtils.isNotEmpty(idTipoElemento)) {
			TipoElementoVO tipoElemento = getGestorEstructuraDepositoBI(request)
					.getTipoElementoSingleton(idTipoElemento);

			if (tipoElemento != null) {
				boolean elementoReferenciado = getGestorEstructuraDepositoBI(
						request).isTipoElementoReferenciado(
						tipoElemento.getId());
				tipoElemento.setElementoReferenciado(elementoReferenciado);
				boolean elementoConHijos = getGestorEstructuraDepositoBI(
						request).hasChilds(idTipoElemento);
				tipoElemento.setHasSubniveles(elementoConHijos);

				setInTemporalSession(request,
						DepositoConstants.NIVEL_DEPOSITO_KEY, tipoElemento);
			}

			formulario.set(tipoElemento);
		}
		setReturnActionFordward(request, mappings.findForward("alta_nivel"));
	}

	/**
	 * Carga la página para la edición del nivel del cuadro de clasificacion
	 * seleccionado.
	 * 
	 * @param mappings
	 * @param form
	 * @param request
	 * @param response
	 */
	public void guardarExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		NivelesDepositoForm formulario = (NivelesDepositoForm) form;

		ActionErrors errors = formulario.validate(mappings, request);

		// Comprobar que el elemento no está duplicado
		GestorEstructuraDepositoBI depositoBI = getGestorEstructuraDepositoBI(request);

		TipoElementoVO tipoElementoVO = new TipoElementoVO();
		formulario.populate(tipoElementoVO);

		if (errors.isEmpty()) {
			try {
				if (StringUtils.isNotEmpty(tipoElementoVO.getId())) {
					depositoBI.actualizarTipoElemento(tipoElementoVO);
				} else {
					tipoElementoVO.setId(formulario.getIcono());
					depositoBI.insertarTipoElemento(tipoElementoVO);
				}
				goBackExecuteLogic(mappings, formulario, request, response);
			} catch (TipoElementoDepositoException e) {
				guardarError(request, e);
				setReturnActionFordward(request,
						mappings.findForward("alta_nivel"));
			}
		} else {
			ErrorsTag.saveErrors(request, errors);
			setReturnActionFordward(request, mappings.findForward("alta_nivel"));
		}
	}

	public void eliminarNivelExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		NivelesDepositoForm formulario = (NivelesDepositoForm) form;
		GestorEstructuraDepositoBI depositoBI = getGestorEstructuraDepositoBI(request);

		ActionErrors errors = new ActionErrors();

		if (errors.isEmpty()) {
			try {
				depositoBI.eliminarTipoElemento(formulario.getId());
				setReturnActionFordward(request, mappings.findForward("list"));
			} catch (TipoElementoDepositoException e) {
				guardarError(request, e);
				setReturnActionFordward(request,
						mappings.findForward("datos_nivel"));
			}
		} else {
			ErrorsTag.saveErrors(request, errors);
			setReturnActionFordward(request,
					mappings.findForward("datos_nivel"));
		}
	}

}