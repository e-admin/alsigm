package salas.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import salas.EstadoMesa;
import salas.SalasConsultaConstants;
import salas.form.EstadoMesaForm;
import salas.model.GestionSalasConsultaBI;
import salas.vos.MesaVO;
import salas.vos.SalaVO;

import common.actions.BaseAction;
import common.navigation.KeysClientsInvocations;

public class GestionEstadoMesasAction extends BaseAction {

	public void habilitarMesasExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		EstadoMesaForm estadoMesaForm = (EstadoMesaForm) form;
		prepareSeleccionMesas(request, estadoMesaForm.getIdSala());
		boolean hayMesasInutilizadas = hayElementoASeleccionar(request,
				EstadoMesa.INUTILIZADA);

		estadoMesaForm.setEstadoAEstablecer(EstadoMesa.LIBRE);
		estadoMesaForm.setEstadoASeleccionar(EstadoMesa.INUTILIZADA);
		estadoMesaForm.setHayElementoASeleccionar(hayMesasInutilizadas);

		if (!hayMesasInutilizadas) {
			obtenerErrores(request, true)
					.add(ActionErrors.GLOBAL_ERROR,
							new ActionError(
									SalasConsultaConstants.ERROR_NO_HAY_MESAS_A_SELECCIONAR));
			goLastClientExecuteLogic(mappings, estadoMesaForm, request,
					response);
		} else {
			saveCurrentInvocation(KeysClientsInvocations.SALAS_HABILITAR_MESAS,
					request);
			setReturnActionFordward(
					request,
					mappings.findForward(SalasConsultaConstants.FORWARD_SELECCION_ESTADO_MESAS));
		}
	}

	public void inhabilitarMesasExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		EstadoMesaForm estadoMesaForm = (EstadoMesaForm) form;
		prepareSeleccionMesas(request, estadoMesaForm.getIdSala());
		boolean hayMesasLibres = hayElementoASeleccionar(request,
				EstadoMesa.LIBRE);

		estadoMesaForm.setEstadoAEstablecer(EstadoMesa.INUTILIZADA);
		estadoMesaForm.setEstadoASeleccionar(EstadoMesa.LIBRE);
		estadoMesaForm.setHayElementoASeleccionar(hayMesasLibres);

		if (!hayMesasLibres) {
			obtenerErrores(request, true)
					.add(ActionErrors.GLOBAL_ERROR,
							new ActionError(
									SalasConsultaConstants.ERROR_NO_HAY_MESAS_A_SELECCIONAR));
			goLastClientExecuteLogic(mappings, estadoMesaForm, request,
					response);
		} else {
			saveCurrentInvocation(
					KeysClientsInvocations.SALAS_INHABILITAR_MESAS, request);
			setReturnActionFordward(
					request,
					mappings.findForward(SalasConsultaConstants.FORWARD_SELECCION_ESTADO_MESAS));
		}
	}

	public void setEstadoMesasExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		EstadoMesaForm estadoMesaForm = (EstadoMesaForm) form;
		GestionSalasConsultaBI salasBI = getGestionSalasBI(request);
		salasBI.cambiarEstado(estadoMesaForm.getIdsMesa(),
				estadoMesaForm.getEstadoAEstablecer());

		goBackExecuteLogic(mappings, form, request, response);
	}

	private void prepareSeleccionMesas(HttpServletRequest request, String idSala) {

		GestionSalasConsultaBI salasBI = getGestionSalasBI(request);
		SalaVO salaVO = salasBI.getSalaById(idSala);
		List listaMesas = salasBI.getMesas(idSala);

		request.setAttribute(SalasConsultaConstants.SALA_KEY, salaVO);
		request.setAttribute(SalasConsultaConstants.LISTA_MESAS_KEY, listaMesas);
	}

	private boolean hayElementoASeleccionar(HttpServletRequest request,
			String estadoASeleccionar) {

		boolean hayElementoASeleccionar = false;
		List listaMesas = (List) request
				.getAttribute(SalasConsultaConstants.LISTA_MESAS_KEY);
		for (int i = 0; !hayElementoASeleccionar && i < listaMesas.size(); i++) {
			String estado = ((MesaVO) listaMesas.get(i)).getEstado();
			hayElementoASeleccionar = estadoASeleccionar.equals(estado);
		}
		return hayElementoASeleccionar;
	}
}