package deposito.actions;

import gcontrol.vos.ArchivoVO;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import util.ErrorsTag;

import common.MotivoEliminacionUnidadInstalacion;
import common.actions.BaseAction;
import common.bi.GestionArchivosBI;
import common.navigation.KeysClientsInvocations;
import common.util.ArrayUtils;
import common.util.ListUtils;

import deposito.DepositoConstants;
import deposito.forms.BusquedaUISEliminadasForm;
import deposito.vos.BusquedaHistUInstDepositoVO;

public class BusquedaUISEliminadasAction extends BaseAction {

	public void nuevaBusquedaUIExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		saveCurrentInvocation(
				KeysClientsInvocations.DEPOSITO_BUSQUEDA_UIS_ELIMINADAS,
				request);

		removeInTemporalSession(request,
				DepositoConstants.LISTA_UIS_ELIMINADAS_KEY);
		removeInTemporalSession(request, DepositoConstants.LISTA_FORMATOS);
		setInTemporalSession(request, DepositoConstants.LISTA_FORMATOS,
				getGestorEstructuraDepositoBI(request).getFormatos());

		BusquedaUISEliminadasForm formulario = (BusquedaUISEliminadasForm) form;

		formulario
				.setMotivos(MotivoEliminacionUnidadInstalacion.MOTIVOS_ELIMINACION);

		// Obtener los posibles archivos receptores
		GestionArchivosBI serviceArchivos = getGestionArchivosBI(request);
		List ltArchivos = serviceArchivos.getArchivosXId(getAppUser(request)
				.getCustodyArchiveList().toArray());
		if (ListUtils.isNotEmpty(ltArchivos)) {
			if (ltArchivos.size() == 1) {
				ArchivoVO archivo = (ArchivoVO) ltArchivos.get(0);
				formulario.setIdArchivo(archivo.getId());
				formulario.setNombreArchivo(archivo.getNombre());

			} else {
				setInTemporalSession(request,
						DepositoConstants.LISTA_ARCHIVOS_USUARIO_KEY,
						ltArchivos);
			}
			setReturnActionFordward(request,
					mappings.findForward("form_busqueda"));
		} else {
			ActionErrors errors = new ActionErrors();
			errors.add(ErrorKeys.ERROR_USUARIO_SIN_ARCHIVOS, new ActionError(
					ErrorKeys.ERROR_USUARIO_SIN_ARCHIVOS));
			ErrorsTag.saveErrors(request, errors);
			goBackExecuteLogic(mappings, formulario, request, response);
		}
	}

	public void buscarUIExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		BusquedaUISEliminadasForm formulario = (BusquedaUISEliminadasForm) form;

		List listaIdsArchivo = getAppUser(request).getCustodyArchiveList();
		BusquedaHistUInstDepositoVO busquedaHistUInstDepositoVO = formulario
				.getBusquedaHistUInstDepositoVO(listaIdsArchivo);

		if (ArrayUtils.isNotEmpty(busquedaHistUInstDepositoVO.getArchivos())) {
			List listaUis = getGestorEstructuraDepositoBI(request)
					.buscarHistoricoUnidadesInstalacionDeposito(
							busquedaHistUInstDepositoVO);
			setInTemporalSession(request,
					DepositoConstants.LISTA_UIS_ELIMINADAS_KEY, listaUis);
		} else {
			ActionErrors errors = new ActionErrors();
			errors.add(ErrorKeys.ERROR_USUARIO_SIN_ARCHIVOS, new ActionError(
					ErrorKeys.ERROR_USUARIO_SIN_ARCHIVOS));
			ErrorsTag.saveErrors(request, errors);
		}

		setReturnActionFordward(request, mappings.findForward("form_busqueda"));
	}
}
