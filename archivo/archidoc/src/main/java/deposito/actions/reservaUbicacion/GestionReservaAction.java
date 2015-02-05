package deposito.actions.reservaUbicacion;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import se.usuarios.ServiceClient;
import transferencias.TransferenciasConstants;
import transferencias.actions.RelacionEntregaPO;
import transferencias.actions.RelacionToPO;
import transferencias.vos.RelacionEntregaVO;

import common.Constants;
import common.actions.BaseAction;
import common.bi.GestionRelacionesEntregaBI;
import common.bi.ServiceRepository;
import common.exceptions.ActionNotAllowedException;
import common.navigation.KeysClientsInvocations;

import deposito.DepositoConstants;
import deposito.forms.GestionReservaForm;
import deposito.model.GestorEstructuraDepositoBI;
import deposito.model.NoAvailableSpaceException;
import deposito.vos.DepositoVO;
import deposito.vos.ElementoVO;
import deposito.vos.FormatoHuecoVO;

/**
 * Action que maneja las acciones que el usuario invoca en el proceso de gestion
 * de reserva de espacio para relaciones de entrega
 */
public class GestionReservaAction extends BaseAction {

	public void listadorelacionesExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		GestionReservaForm frm = (GestionReservaForm) form;
		GestionRelacionesEntregaBI relacionesService = getGestionRelacionesBI(request);
		try {
			Collection relacionesAReservar = relacionesService
					.getRelacionesAReservar();
			ServiceRepository services = ServiceRepository
					.getInstance(ServiceClient.create(getAppUser(request)));
			CollectionUtils.transform(relacionesAReservar,
					RelacionToPO.getInstance(services));
			if ((relacionesAReservar != null)
					&& (relacionesAReservar.size() > 0)
					&& ((frm.getIdrelacionseleccionada() == null) || (frm
							.getIdrelacionseleccionada().trim().length() == 0))) {
				RelacionEntregaPO re = ((RelacionEntregaPO) relacionesAReservar
						.iterator().next());
				frm.setIdrelacionseleccionada(re.getId());
			}
			request.setAttribute(TransferenciasConstants.LISTA_RELACIONES_KEY,
					relacionesAReservar);
			saveCurrentInvocation(
					KeysClientsInvocations.DEPOSITO_RESERVA_LISTA_RELACIONES,
					request);

			setReturnActionFordward(request,
					mappings.findForward("listado_relaciones"));
		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
			goLastClientExecuteLogic(mappings, form, request, response);
		}
	}

	public void vernavegadorExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionRelacionesEntregaBI relacionesService = services
				.lookupGestionRelacionesBI();
		GestionReservaForm frm = (GestionReservaForm) form;

		RelacionEntregaVO re = relacionesService.getRelacionXIdRelacion(frm
				.getIdrelacionseleccionada());
		setInTemporalSession(request, DepositoConstants.RELACION_KEY,
				RelacionToPO.getInstance(services).transform(re));
		GestorEstructuraDepositoBI serviceDeposito = getGestorEstructuraDepositoBI(request);
		DepositoVO edificio = (DepositoVO) serviceDeposito.getInfoElemento(
				re.getIddeposito(), DepositoVO.ID_TIPO_ELEMENTO_UBICACION);

		ElementoVO elementoEnReserva;
		// if(StringUtils.isBlank(frm.getIdasignabledestino())) {
		elementoEnReserva = serviceDeposito.getInfoElemento(
				re.getIdelmtodreserva(), re.getIdtipoelmtodreserva());
		// }
		/*
		 * else { elementoEnReserva =
		 * serviceDeposito.getInfoElemento(frm.getIdasignabledestino(),
		 * frm.getIdtipoasignabledestino()); }
		 */
		request.setAttribute(DepositoConstants.EDIFICIO_KEY, edificio);
		request.setAttribute(DepositoConstants.DEPOSITO_KEY, elementoEnReserva);
		saveCurrentInvocation(
				KeysClientsInvocations.DEPOSITO_RESERVA_VER_NAVEGADOR, request);
		setReturnActionFordward(request, mappings.findForward("ver_navegador"));
	}

	public void rechazarExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			GestorEstructuraDepositoBI serviceDeposito = getGestorEstructuraDepositoBI(request);
			RelacionEntregaVO relacion = (RelacionEntregaVO) getFromTemporalSession(
					request, DepositoConstants.RELACION_KEY);
			serviceDeposito.rechazarReserva(relacion);
		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
		}
		goBackExecuteLogic(mappings, form, request, response);
	}

	public void liberarHuecosReservaExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		try {
			GestorEstructuraDepositoBI serviceDeposito = getGestorEstructuraDepositoBI(request);
			RelacionEntregaVO relacion = (RelacionEntregaVO) getFromTemporalSession(
					request, DepositoConstants.RELACION_KEY);

			serviceDeposito.liberarReserva(relacion.getId());

		} catch (Exception e) {

		}
		setReturnActionFordward(request, mappings.findForward("ver_navegador"));
	}

	public void informereservaExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		// GestionRelacionesEntregaBI relacionesService =
		// services.lookupGestionRelacionesBI();
		GestorEstructuraDepositoBI serviceDeposito = services
				.lookupGestorEstructuraDepositoBI();
		GestionReservaForm frm = (GestionReservaForm) form;
		if (!StringUtils.isEmpty(frm.getAsignabledestino())) {
			RelacionEntregaPO relacion = (RelacionEntregaPO) getFromTemporalSession(
					request, DepositoConstants.RELACION_KEY);

			DepositoVO edificioVO = (DepositoVO) serviceDeposito
					.getInfoElemento(relacion.getIddeposito(),
							DepositoVO.ID_TIPO_ELEMENTO_UBICACION);
			setInTemporalSession(request, DepositoConstants.DEPOSITO_KEY,
					edificioVO);

			int nUnidadesInstalacion = relacion.getNumUIs();
			FormatoHuecoVO formato = relacion.getFormatoDestino();
			// TODO PASAR LAS COMPROBACIONES AL SERVICIO
			try {
				removeInTemporalSession(request,
						DepositoConstants.LISTA_HUECOS_KEY);
				List huecosAReservar = serviceDeposito.searchNHuecosLibres(
						frm.getIdasignabledestino(),
						frm.getIdtipoasignabledestino(), nUnidadesInstalacion,
						null, null, formato.getId(), true);

				// relacion.setIdenareserva(frm.getIdasignabledestino());
				relacion.setIdelmtodreserva(frm.getIdasignabledestino());
				relacion.setIdtipoelmtodreserva(frm.getIdtipoasignabledestino());
				setInTemporalSession(request,
						DepositoConstants.LISTA_HUECOS_KEY, huecosAReservar);
				saveCurrentInvocation(
						KeysClientsInvocations.DEPOSITO_RESERVA_INFORME,
						request);
				setReturnActionFordward(request,
						mappings.findForward("informe_reserva"));
			} catch (NoAvailableSpaceException e) {
				obtenerErrores(request, true).add(
						ActionErrors.GLOBAL_ERROR,
						new ActionError(
								Constants.ESPACIO_EN_UBICACION_INSUFICIENTE));
				goLastClientExecuteLogic(mappings, form, request, response);
			}
		} else
			goBackExecuteLogic(mappings, form, request, response);
	}

	public void aceptarinformereservaExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		GestorEstructuraDepositoBI serviceDeposito = getGestorEstructuraDepositoBI(request);
		RelacionEntregaVO relacionVO = (RelacionEntregaVO) getFromTemporalSession(
				request, DepositoConstants.RELACION_KEY);
		List huecosAReservar = (List) getFromTemporalSession(request,
				DepositoConstants.LISTA_HUECOS_KEY);
		try {
			serviceDeposito.reservarHuecos(huecosAReservar, relacionVO);
			goReturnPointExecuteLogic(mappings, form, request, response);
		} catch (ActionNotAllowedException e) {
			guardarError(request, e);
			goLastClientExecuteLogic(mappings, form, request, response);
		}
	}
}