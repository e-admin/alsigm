package deposito.actions.reubicacion;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import util.CollectionUtils;

import common.MotivoEliminacionUnidadInstalacion;
import common.actions.BaseAction;
import common.bi.ServiceRepository;
import common.exceptions.ActionNotAllowedException;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;
import common.util.ArrayUtils;

import deposito.DepositoConstants;
import deposito.actions.ErrorKeys;
import deposito.actions.asignable.ElementoAsignablePO;
import deposito.actions.hueco.HuecoPO;
import deposito.actions.hueco.HuecoToPO;
import deposito.forms.ReubicacionForm;
import deposito.model.GestorEstructuraDepositoBI;
import deposito.model.NoAvailableSpaceException;
import deposito.vos.DepositoVO;
import deposito.vos.ElementoAsignableVO;
import deposito.vos.ElementoVO;
import deposito.vos.HuecoID;
import deposito.vos.HuecoVO;

public class ReubicacionUnidadesInstalacionAction extends BaseAction {

	public void seleccionUIsExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ServiceRepository services = getServiceRepository(request);

		removeInTemporalSession(request,
				DepositoConstants.REUBICACION_FINALIZADA);

		GestorEstructuraDepositoBI depositoBI = services
				.lookupGestorEstructuraDepositoBI();
		String idElementoAsignable = request.getParameter("idAsignable");

		ElementoAsignableVO asignable = depositoBI
				.getElementoAsignable(idElementoAsignable);
		// List listaHuecos= depositoBI.getHuecos(idElementoAsignable);
		List listaHuecos = depositoBI
				.getHuecosNoBloqueados(idElementoAsignable);
		CollectionUtils.transform(listaHuecos, HuecoToPO.getInstance(services));

		request.setAttribute(DepositoConstants.LISTA_HUECOS_KEY, listaHuecos);

		// Comprobar si hay huecos ocupados
		boolean hayHuecosOcupados = false;
		for (int i = 0; !hayHuecosOcupados && i < listaHuecos.size(); i++)
			hayHuecosOcupados = HuecoVO.OCUPADO_STATE
					.equals(((HuecoVO) listaHuecos.get(i)).getEstado());
		request.setAttribute(DepositoConstants.HAY_HUECOS_OCUPADOS_KEY,
				new Boolean(hayHuecosOcupados));

		if (!hayHuecosOcupados)
			obtenerErrores(request, true).add(ActionErrors.GLOBAL_ERROR,
					new ActionError(ErrorKeys.NO_HAY_HUECOS_A_SELECCIONAR));

		request.setAttribute(
				DepositoConstants.EDITABLE_NUMERACION_KEY,
				new Boolean(depositoBI.isEditableNumeracion(asignable
						.getIddeposito())));

		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.DEPOSITO_REUBICACION_UNIDADES_INSTALACION,
				request);
		invocation.setAsReturnPoint(true);
		setInTemporalSession(request, DepositoConstants.ELEMENTO_ASIGNABLE_KEY,
				new ElementoAsignablePO(asignable, services));

		setReturnActionFordward(request, mappings.findForward("seleccion_uis"));
	}

	public void seleccionarDestinoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ServiceRepository services = getServiceRepository(request);

		GestorEstructuraDepositoBI depositoBI = services
				.lookupGestorEstructuraDepositoBI();
		ElementoAsignableVO elementoAsignable = (ElementoAsignableVO) getFromTemporalSession(
				request, DepositoConstants.ELEMENTO_ASIGNABLE_KEY);
		ReubicacionForm reubicacionForm = (ReubicacionForm) form;
		int[] ordenHuecosAMover = reubicacionForm.getNumeroOrdenHueco();
		if (ordenHuecosAMover != null && ordenHuecosAMover.length > 0) {
			removeInTemporalSession(request,
					DepositoConstants.LISTA_HUECOS_A_MOVER);
			HuecoID[] idHuecosAMover = new HuecoID[ordenHuecosAMover.length];
			for (int i = 0; i < ordenHuecosAMover.length; i++)
				idHuecosAMover[i] = new HuecoID(elementoAsignable.getId(),
						ordenHuecosAMover[i]);
			// List huecosAMover = depositoBI.getHuecos(idHuecosAMover);
			setInTemporalSession(request,
					DepositoConstants.LISTA_HUECOS_A_MOVER, idHuecosAMover);

			Integer numHuecos = new Integer(0);
			if (ArrayUtils.isNotEmpty(idHuecosAMover)) {
				numHuecos = new Integer(idHuecosAMover.length);
			}
			setInTemporalSession(request,
					DepositoConstants.NUM_HUECOS_A_BUSCAR, numHuecos);

			ElementoVO elementoPadre = depositoBI.getElementoPadre(
					elementoAsignable.getId(),
					elementoAsignable.getIdTipoElemento());
			request.setAttribute(DepositoConstants.ELEMENTO_PADRE,
					elementoPadre);
			setReturnActionFordward(request,
					mappings.findForward("seleccion_destino"));
		} else
			seleccionUIsExecuteLogic(mappings, form, request, response);
	}

	public void moverContenidoHuecosExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ReubicacionForm reubicacionForm = (ReubicacionForm) form;

		Boolean reubicacionFinalizada = (Boolean) getFromTemporalSession(
				request, DepositoConstants.REUBICACION_FINALIZADA);

		if (reubicacionFinalizada == null) {
			reubicacionFinalizada = new Boolean(false);
		}

		if (!reubicacionFinalizada.booleanValue()) {
			if (StringUtils.isNotBlank(reubicacionForm.getElementoDestino())) {
				HuecoID[] huecosAMover = (HuecoID[]) getFromTemporalSession(
						request, DepositoConstants.LISTA_HUECOS_A_MOVER);
				ServiceRepository services = getServiceRepository(request);

				GestorEstructuraDepositoBI depositoBI = services
						.lookupGestorEstructuraDepositoBI();
				String[] elementoDestinoID = reubicacionForm
						.getElementoDestino().split(":");
				ElementoVO elementoDestino = depositoBI.getInfoElemento(
						elementoDestinoID[0], elementoDestinoID[1]);

				List listaReubicacion = null;
				try {
					listaReubicacion = depositoBI.reubicarUnidadesInstalacion(
							huecosAMover, elementoDestino);
					setInTemporalSession(request,
							DepositoConstants.REUBICACION_FINALIZADA,
							new Boolean(true));
					cargarDatosInforme(listaReubicacion, request);
					setReturnActionFordward(request,
							mappings.findForward("informe_reubicacion"));

					// goBackExecuteLogic(mappings, form, request, response);
				} catch (NoAvailableSpaceException e) {
					obtenerErrores(request, true).add(
							ActionErrors.GLOBAL_ERROR,
							new ActionError(ErrorKeys.ESPACIO_INSUFICIENTE));
					setReturnActionFordward(request,
							mappings.findForward("seleccion_destino"));

					// goLastClientExecuteLogic(mappings, form, request,
					// response);
				} catch (ActionNotAllowedException e) {
					guardarError(request, e);
					setReturnActionFordward(request,
							mappings.findForward("seleccion_destino"));

					// goLastClientExecuteLogic(mappings, form, request,
					// response);
				}
			} else {
				goLastClientExecuteLogic(mappings, form, request, response);
			}
		} else {
			setReturnActionFordward(request,
					mappings.findForward("informe_reubicacion"));
		}
	}

	private void cargarDatosInforme(List listaReubicacionVO,
			HttpServletRequest request) {

		ServiceRepository services = getServiceRepository(request);

		List listaReubicacionPO = new ArrayList();

		for (int i = 0; i < listaReubicacionVO.size(); i++) {
			ReubicacionUinstVO reubicacionUinstVO = (ReubicacionUinstVO) listaReubicacionVO
					.get(i);

			ReubicacionUinstPO reubicacionUinsPO = new ReubicacionUinstPO(
					reubicacionUinstVO.getHuecoOrigen(),
					reubicacionUinstVO.getHuecoDestino(), services);

			listaReubicacionPO.add(reubicacionUinsPO);
		}

		setInTemporalSession(request, DepositoConstants.LISTA_REUBICACION_KEY,
				listaReubicacionPO);
	}

	public void eliminarCajaVaciaExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		GestorEstructuraDepositoBI depositoService = getGestionDespositoBI(request);

		HuecoPO huecoOrigenPO = (HuecoPO) getFromTemporalSession(request,
				DepositoConstants.HUECO_KEY);
		DepositoVO ubicacion = getGestorEstructuraDepositoBI(request)
				.getUbicacion(huecoOrigenPO.getIddeposito());

		depositoService
				.eliminarCajaVacia(
						ubicacion.getIdArchivo(),
						huecoOrigenPO.getIduinstalacion(),
						MotivoEliminacionUnidadInstalacion.COMPACTACION_UNIDADES_DOCUMENTALES);

		goLastClientExecuteLogic(mappings, form, request, response);
	}
}
