package deposito.actions.hueco;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import util.CollectionUtils;

import common.Constants;
import common.actions.BaseAction;
import common.bi.ServiceRepository;
import common.exceptions.ActionNotAllowedException;
import common.navigation.KeysClientsInvocations;
import common.util.StringUtils;

import deposito.DepositoConstants;
import deposito.actions.ErrorKeys;
import deposito.actions.asignable.ElementoAsignablePO;
import deposito.forms.GestionEstadoHuecoForm;
import deposito.forms.HuecoForm;
import deposito.model.GestorEstructuraDepositoBI;
import deposito.vos.ElementoAsignableVO;
import deposito.vos.HuecoID;
import deposito.vos.HuecoVO;

public class GestionEstadoHuecosAction extends BaseAction {

	private void prepareSeleccionHuecos(HttpServletRequest request,
			String idElementoAsignable) {

		ServiceRepository services = getServiceRepository(request);
		GestorEstructuraDepositoBI depositoBI = services
				.lookupGestorEstructuraDepositoBI();

		ElementoAsignableVO asignable = depositoBI
				.getElementoAsignable(idElementoAsignable);
		List listaHuecos = depositoBI.getHuecos(idElementoAsignable);
		CollectionUtils.transform(listaHuecos, HuecoToPO.getInstance(services));

		request.setAttribute(DepositoConstants.ELEMENTO_ASIGNABLE_KEY,
				new ElementoAsignablePO(asignable, services));
		request.setAttribute(DepositoConstants.LISTA_HUECOS_KEY, listaHuecos);
		request.setAttribute(
				DepositoConstants.EDITABLE_NUMERACION_KEY,
				new Boolean(depositoBI.isEditableNumeracion(asignable
						.getIddeposito())));
	}

	private boolean hayElementoASeleccionar(HttpServletRequest request,
			String estadoASeleccionar) {
		boolean hayElementoASeleccionar = false;
		List listaHuecos = (List) request
				.getAttribute(DepositoConstants.LISTA_HUECOS_KEY);
		for (int i = 0; !hayElementoASeleccionar && i < listaHuecos.size(); i++)
			hayElementoASeleccionar = estadoASeleccionar
					.equals(((HuecoVO) listaHuecos.get(i)).getEstado());
		return hayElementoASeleccionar;
	}

	public void habilitarHuecosExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		GestionEstadoHuecoForm estadoHuecoForm = (GestionEstadoHuecoForm) form;

		prepareSeleccionHuecos(request, estadoHuecoForm.getIdAsignable());

		boolean hayHuecosInutilizados = hayElementoASeleccionar(request,
				HuecoVO.INUTILIZADO_STATE);

		estadoHuecoForm.setEstadoAEstablecer(HuecoVO.LIBRE_STATE);
		estadoHuecoForm.setEstadoASeleccionar(HuecoVO.INUTILIZADO_STATE);
		estadoHuecoForm.setHayElementoASeleccionar(hayHuecosInutilizados);

		if (!hayHuecosInutilizados)
			obtenerErrores(request, true).add(ActionErrors.GLOBAL_ERROR,
					new ActionError(ErrorKeys.NO_HAY_HUECOS_A_SELECCIONAR));

		saveCurrentInvocation(KeysClientsInvocations.DEPOSITO_HABILITAR_HUECOS,
				request);
		setReturnActionFordward(request,
				mappings.findForward("seleccion_huecos"));
	}

	public void inhabilitarHuecosExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		GestionEstadoHuecoForm estadoHuecoForm = (GestionEstadoHuecoForm) form;

		prepareSeleccionHuecos(request, estadoHuecoForm.getIdAsignable());

		boolean hayHuecosLibres = hayElementoASeleccionar(request,
				HuecoVO.LIBRE_STATE);

		estadoHuecoForm.setEstadoAEstablecer(HuecoVO.INUTILIZADO_STATE);
		estadoHuecoForm.setEstadoASeleccionar(HuecoVO.LIBRE_STATE);
		estadoHuecoForm.setHayElementoASeleccionar(hayHuecosLibres);

		if (!hayHuecosLibres)
			obtenerErrores(request, true).add(ActionErrors.GLOBAL_ERROR,
					new ActionError(ErrorKeys.NO_HAY_HUECOS_A_SELECCIONAR));

		saveCurrentInvocation(
				KeysClientsInvocations.DEPOSITO_INHABILITAR_HUECOS, request);
		setReturnActionFordward(request,
				mappings.findForward("seleccion_huecos"));
	}

	public void setEstadoHuecosExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		GestionEstadoHuecoForm estadoHuecoForm = (GestionEstadoHuecoForm) form;
		ServiceRepository services = getServiceRepository(request);

		GestorEstructuraDepositoBI depositoBI = services
				.lookupGestorEstructuraDepositoBI();
		try {
			depositoBI.setEstadoHuecos(estadoHuecoForm.getIdAsignable(),
					estadoHuecoForm.getNumeroOrdenHueco(),
					estadoHuecoForm.getEstadoAEstablecer(),
					estadoHuecoForm.getEstadoASeleccionar());
			goBackExecuteLogic(mappings, form, request, response);
		} catch (ActionNotAllowedException e) {
			guardarError(request, e);
			goLastClientExecuteLogic(mappings, form, request, response);
		}
	}

	public void editarNumeracionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		GestionEstadoHuecoForm estadoHuecoForm = (GestionEstadoHuecoForm) form;
		prepareSeleccionHuecos(request, estadoHuecoForm.getIdAsignable());
		removeInTemporalSession(request, DepositoConstants.HUECO_KEY);

		boolean hayHuecosLibres = hayElementoASeleccionar(request,
				HuecoVO.LIBRE_STATE);

		estadoHuecoForm.setHayElementoASeleccionar(hayHuecosLibres);
		if (!hayHuecosLibres)
			obtenerErrores(request, true).add(ActionErrors.GLOBAL_ERROR,
					new ActionError(ErrorKeys.NO_HAY_HUECOS_A_SELECCIONAR));

		saveCurrentInvocation(
				KeysClientsInvocations.DEPOSITO_EDITAR_NUMERACION_HUECOS,
				request);
		setReturnActionFordward(request,
				mappings.findForward("seleccion_uis_editar"));
	}

	public void setNumeracionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		GestionEstadoHuecoForm estadoHuecoForm = (GestionEstadoHuecoForm) form;
		ServiceRepository services = getServiceRepository(request);
		GestorEstructuraDepositoBI depositoBI = services
				.lookupGestorEstructuraDepositoBI();

		HuecoID huecoID = new HuecoID(estadoHuecoForm.getIdAsignable(),
				estadoHuecoForm.getNumeroOrdenHueco()[0]);
		HuecoVO huecoVO = depositoBI.getInfoHueco(huecoID);
		HuecoPO huecoSelected = new HuecoPO(huecoVO, services);
		estadoHuecoForm.setIdHueco(huecoID.getIdpadre() + ":"
				+ huecoID.getNumorden());

		String numeracion = Constants.STRING_EMPTY;

		if (huecoVO != null && StringUtils.isNotBlank(huecoVO.getNumeracion())) {
			numeracion = huecoVO.getNumeracion().trim();
		}

		estadoHuecoForm.setNumeracion(numeracion);
		setInTemporalSession(request, DepositoConstants.HUECO_KEY,
				huecoSelected);
		request.setAttribute(DepositoConstants.MOSTRAR_RENUMERACION_KEY,
				new Boolean(StringUtils.isNumeric(numeracion)));

		saveCurrentInvocation(
				KeysClientsInvocations.DEPOSITO_EDITAR_NUMERACION_HUECOS,
				request);

		setReturnActionFordward(request,
				mappings.findForward("editar_numeracion"));
	}

	public void saveNumeracionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		GestionEstadoHuecoForm estadoHuecoForm = (GestionEstadoHuecoForm) form;
		ServiceRepository services = getServiceRepository(request);
		GestorEstructuraDepositoBI depositoBI = services
				.lookupGestorEstructuraDepositoBI();
		String idHueco = estadoHuecoForm.getIdHueco();
		HuecoID huecoID = HuecoForm.getHuecoID(idHueco);
		String nuevaNumeracion = estadoHuecoForm.getNumeracion();
		if (StringUtils.isNotEmpty(nuevaNumeracion)) {
			// Si se pone renumerar se comprueba si se puede hacer y se lleva a
			// cabo
			// sino se actualiza la numeracion del hueco en la base de datos
			boolean renumerar = estadoHuecoForm.isRenumerar();
			try {
				depositoBI.renumerarHuecos(huecoID, nuevaNumeracion.trim(),
						renumerar);
				goBackExecuteLogic(mappings, estadoHuecoForm, request, response);
			} catch (ActionNotAllowedException e) {
				guardarError(request, e);
				setReturnActionFordward(request,
						mappings.findForward("editar_numeracion"));
			}
		} else {
			obtenerErrores(request, true).add(
					ActionErrors.GLOBAL_ERROR,
					new ActionError(
							DepositoConstants.ERROR_EDITAR_NUMERACION_VACIA));
			setReturnActionFordward(request,
					mappings.findForward("editar_numeracion"));
		}
	}
}