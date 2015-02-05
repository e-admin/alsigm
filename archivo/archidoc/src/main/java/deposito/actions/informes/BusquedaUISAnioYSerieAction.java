/**
 *
 */
package deposito.actions.informes;

import gcontrol.vos.ArchivoVO;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import util.ErrorsTag;

import common.Constants;
import common.actions.BaseAction;
import common.bi.GestionArchivosBI;
import common.exceptions.TooManyResultsException;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;
import common.util.ListUtils;

import deposito.DepositoConstants;
import deposito.actions.ErrorKeys;
import deposito.forms.BusquedaUISAnioYSerieForm;
import deposito.vos.BusquedaUIAnioSerieVO;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class BusquedaUISAnioYSerieAction extends BaseAction {

	public void initBusquedaExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		removeInTemporalSession(request,
				DepositoConstants.LISTA_UIS_ANIO_SERIE_KEY);

		setInTemporalSession(request, DepositoConstants.LISTA_FORMATOS,
				getGestorEstructuraDepositoBI(request).getFormatos());

		BusquedaUISAnioYSerieForm formulario = (BusquedaUISAnioYSerieForm) form;

		formulario.reset(mappings, request);

		// Obtener los posibles archivos receptores
		GestionArchivosBI serviceArchivos = getGestionArchivosBI(request);

		removeInTemporalSession(request, DepositoConstants.LISTA_ARCHIVOS_KEY);
		removeInTemporalSession(request, DepositoConstants.ARCHIVO_DEFAULT);

		List ltArchivos = serviceArchivos.getArchivosXId(getAppUser(request)
				.getCustodyArchiveList().toArray());
		if (ListUtils.isNotEmpty(ltArchivos)) {
			if (ltArchivos.size() == 1) {
				ArchivoVO archivo = (ArchivoVO) ltArchivos.get(0);
				formulario.setIdArchivo(archivo.getId());
				formulario.setNombreArchivo(archivo.getNombre());

				setInTemporalSession(request,
						DepositoConstants.ARCHIVO_DEFAULT, archivo);
			} else {
				setInTemporalSession(request,
						DepositoConstants.LISTA_ARCHIVOS_USUARIO_KEY,
						ltArchivos);
			}

			List listaUbicaciones = getGestorEstructuraDepositoBI(request)
					.getUbicacionesXIdsArchivo(
							getAppUser(request).getIdsArchivosUser());

			setInTemporalSession(request,
					DepositoConstants.LISTA_UBICACIONES_KEY, listaUbicaciones);

			setReturnActionFordward(request,
					mappings.findForward("show_form_busqueda"));

		} else {
			ActionErrors errors = new ActionErrors();
			errors.add(ErrorKeys.ERROR_USUARIO_SIN_ARCHIVOS, new ActionError(
					ErrorKeys.ERROR_USUARIO_SIN_ARCHIVOS));
			ErrorsTag.saveErrors(request, errors);
			goLastClientExecuteLogic(mappings, formulario, request, response);
		}
	}

	public void nuevaBusquedaExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		saveCurrentInvocation(
				KeysClientsInvocations.DEPOSITO_BUSQUEDA_UIS_ANIO_SERIE,
				request);

		setReturnActionFordward(request, mappings.findForward("form_busqueda"));
	}

	public void buscarExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		try {
			BusquedaUISAnioYSerieForm formulario = (BusquedaUISAnioYSerieForm) form;

			ClientInvocation cli = getInvocationStack(request)
					.getLastClientInvocation();
			cli.addParameters(formulario.getMap());

			BusquedaUIAnioSerieVO busquedaUIAnioSerieVO = new BusquedaUIAnioSerieVO();

			ActionErrors errors = formulario.validate(request.getLocale());

			if (errors.isEmpty()) {

				formulario.populate(busquedaUIAnioSerieVO, getAppUser(request));

				List listaUIs = getGestorEstructuraDepositoBI(request)
						.getUnidadesInstalacionPorAnioYSerie(
								busquedaUIAnioSerieVO);

				setInTemporalSession(request,
						DepositoConstants.LISTA_UIS_ANIO_SERIE_KEY, listaUIs);

			} else {
				ErrorsTag.saveErrors(request, errors);
			}

		} catch (TooManyResultsException e) {
			// Añadir los errores al request
			obtenerErrores(request, true).add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(Constants.ERROR_TOO_MANY_RESULTS,
							new Object[] { new Integer(e.getCount()),
									new Integer(e.getMaxNumResults()) }));

		} catch (Exception e) {
			ActionErrors errors = new ActionErrors();
			errors.add(Constants.ERROR_GENERAL_MESSAGE, new ActionError(
					Constants.ERROR_GENERAL_MESSAGE, e));
			ErrorsTag.saveErrors(request, errors);
		}

		setReturnActionFordward(request, mappings.findForward("form_busqueda"));
	}

}
