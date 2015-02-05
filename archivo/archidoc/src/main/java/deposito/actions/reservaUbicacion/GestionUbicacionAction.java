package deposito.actions.reservaUbicacion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
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
import transferencias.ReservaPrevision;
import transferencias.TransferenciasConstants;
import transferencias.actions.RelacionEntregaPO;
import transferencias.actions.RelacionToPO;
import transferencias.vos.RelacionEntregaVO;
import util.ErrorsTag;

import common.ConfigConstants;
import common.Constants;
import common.actions.ActionRedirect;
import common.actions.BaseAction;
import common.bi.GestionRelacionesEntregaBI;
import common.bi.ServiceRepository;
import common.definitions.ArchivoModules;
import common.exceptions.ActionNotAllowedException;
import common.exceptions.ArchivoModelException;
import common.exceptions.SecurityException;
import common.lang.MutableBoolean;
import common.lock.exceptions.LockerException;
import common.lock.model.Locker;
import common.lock.model.LockerObjectTypes;
import common.navigation.KeysClientsInvocations;
import common.util.ListUtils;
import common.view.ErrorKeys;

import deposito.DepositoConstants;
import deposito.actions.EspacioEnDepositoPO;
import deposito.actions.TransformerToEspacioEnDepositoPO;
import deposito.db.ConcurrentModificationException;
import deposito.forms.GestionReservaForm;
import deposito.model.DetalleUbicacion;
import deposito.model.GestorEstructuraDepositoBI;
import deposito.model.NoAvailableSpaceException;
import deposito.vos.DepositoVO;
import deposito.vos.ElementoNoAsignableVO;
import deposito.vos.ElementoVO;
import deposito.vos.OcupacionElementoDeposito;
import fondos.FondosConstants;

public class GestionUbicacionAction extends BaseAction {

	public void listadorelacionesExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		GestionReservaForm frm = (GestionReservaForm) form;

		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));

		GestionRelacionesEntregaBI relacionesService = services
				.lookupGestionRelacionesBI();

		Collection relacionesAUbicar = relacionesService
				.getRelacionesAUbicar(getAppUser(request).getIdsArchivosUser());
		if ((relacionesAUbicar != null)
				&& (relacionesAUbicar.size() > 0)
				&& ((frm.getIdrelacionseleccionada() == null) || (frm
						.getIdrelacionseleccionada().trim().length() == 0))) {

			RelacionEntregaVO re = ((RelacionEntregaVO) relacionesAUbicar
					.iterator().next());
			frm.setIdrelacionseleccionada(re.getId());
		}
		CollectionUtils.transform(relacionesAUbicar,
				RelacionToPO.getInstance(services));
		request.setAttribute(TransferenciasConstants.LISTA_RELACIONES_KEY,
				relacionesAUbicar);
		saveCurrentInvocation(
				KeysClientsInvocations.DEPOSITO_UBICACION_LISTA_RELACIONES,
				request);

		setReturnActionFordward(request,
				mappings.findForward("listado_relaciones"));
	}

	public void comprobarreservaExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionRelacionesEntregaBI relacionesService = services
				.lookupGestionRelacionesBI();
		GestionReservaForm frm = (GestionReservaForm) form;
		RelacionEntregaVO relacionVO = relacionesService
				.getRelacionXIdRelacion(frm.getIdrelacionseleccionada());
		RelacionEntregaPO relacionPO = (RelacionEntregaPO) RelacionToPO
				.getInstance(services).transform(relacionVO);

		// comprobar espacio de reserva
		GestorEstructuraDepositoBI serviceDeposito = services
				.lookupGestorEstructuraDepositoBI();
		String idUbicacion = relacionVO.getIddeposito();
		if (StringUtils.isEmpty(idUbicacion)
				&& relacionPO.getIsIngresoDirecto()
				&& relacionPO.isSignaturacionAsociadaAHueco()) {
			// Si se ha llegado aquí y el identificador de ubicación es nulo, es
			// que venimos de un ingreso con cajas asignadas en un
			// archivo con signatura asociada a hueco, por lo que debemos
			// obtener la ubicación de cualquiera de las cajas asignadas
			idUbicacion = relacionesService
					.estableceUbicacionIngresoSignaturacionAsociadaHueco(
							relacionVO.getId(), serviceDeposito, false);
			relacionVO.setIddeposito(idUbicacion);
			relacionPO.setIddeposito(idUbicacion);
		}

		// Guardamos la relación en sesión
		setInTemporalSession(request, DepositoConstants.RELACION_KEY,
				relacionPO);
		setInTemporalSession(request, TransferenciasConstants.RELACION_KEY,
				relacionPO);
		DepositoVO edificioVO = (DepositoVO) serviceDeposito.getInfoElemento(
				idUbicacion, DepositoVO.ID_TIPO_ELEMENTO_UBICACION);
		setInTemporalSession(request, DepositoConstants.DEPOSITO_KEY,
				edificioVO);
		ElementoNoAsignableVO depositoVO = null;
		try {
			// Limpiamos los valores de sesión que pudiesen quedar de una
			// relación seleccionada anteriormente con reserva
			removeInTemporalSession(request,
					DepositoConstants.LISTA_HUECOS_RESERVADOS_KEY);
			removeInTemporalSession(request,
					DepositoConstants.NUM_HUECOS_RESERVADOS_KEY);
			removeInTemporalSession(request,
					DepositoConstants.LISTA_HUECOS_A_LIBERAR_KEY);
			removeInTemporalSession(request,
					DepositoConstants.POSIBLE_UBICAR_RELACION);
			removeInTemporalSession(request,
					DepositoConstants.LISTA_NUEVOS_HUECOS_A_OCUPAR_KEY);
			removeInTemporalSession(request,
					DepositoConstants.DEPOSITOS_EN_UBICACION);

			boolean relacionReservada = (relacionVO.getReservadeposito() == ReservaPrevision.RESERVADA
					.getIdentificador());
			int numeroUnidadesInstalacion = relacionPO.getNumUIs();
			List huecosReservados = null;
			if (relacionReservada
					|| relacionPO
							.isSignaturacionAsociadaHuecoYSignaturaSolicitable()) {
				huecosReservados = serviceDeposito
						.getHuecosReservadosXIdRelacionEntrega(relacionVO
								.getId());
				int numHuecosReservados = 0;
				if (huecosReservados != null)
					numHuecosReservados = huecosReservados.size();
				if (numHuecosReservados >= numeroUnidadesInstalacion) {
					removeInTemporalSession(request,
							DepositoConstants.LISTA_HUECOS_RESERVADOS_KEY);
					removeInTemporalSession(request,
							DepositoConstants.LISTA_HUECOS_A_LIBERAR_KEY);
					// existe espacio suficiente, directamente vamos al informe
					// de ubicacion
					List[] listaHuecosAUbicar = serviceDeposito
							.getHuecosUbicacionConReservaSuficiente(frm
									.getIdrelacionseleccionada());
					setInTemporalSession(request,
							DepositoConstants.LISTA_HUECOS_RESERVADOS_KEY,
							listaHuecosAUbicar[0]);
					setInTemporalSession(request,
							DepositoConstants.LISTA_HUECOS_A_LIBERAR_KEY,
							listaHuecosAUbicar[1]);
					setReturnActionFordward(request,
							mappings.findForward("informe_ubicacion"));
				} else {
					OcupacionElementoDeposito ocupacionDepositoReserva = serviceDeposito
							.getDatosOcupacion(relacionVO.getIdelmtodreserva(),
									relacionVO.getIdtipoelmtodreserva(),
									relacionVO.getIdformatoui());

					if (ocupacionDepositoReserva.getHuecosLibres() < numeroUnidadesInstalacion
							- numHuecosReservados) {

						List depositosUbicacion = new ArrayList(
								serviceDeposito.getHijosElemento(
										edificioVO.getIdElemento(),
										edificioVO.getIdTipoElemento()));
						depositosUbicacion.remove(depositoVO);
						CollectionUtils.transform(depositosUbicacion,
								TransformerToEspacioEnDepositoPO.getInstance(
										services, relacionPO.getIdformatoui()));
						MutableBoolean posibleUbicarRelacion = new MutableBoolean(
								false);
						for (Iterator i = depositosUbicacion.iterator(); i
								.hasNext();) {
							OcupacionElementoDeposito ocupacionDeposito = ((EspacioEnDepositoPO) i
									.next()).getOcupacionDeposito();
							if (ocupacionDeposito.getHuecosLibres() >= numeroUnidadesInstalacion) {
								posibleUbicarRelacion.setValue(true);
								break;
							}
						}
						setInTemporalSession(request,
								DepositoConstants.DEPOSITOS_EN_UBICACION,
								depositosUbicacion);
						setInTemporalSession(request,
								DepositoConstants.POSIBLE_UBICAR_RELACION,
								new Boolean(posibleUbicarRelacion.getValue()));
						setInTemporalSession(request,
								DepositoConstants.NUM_HUECOS_RESERVADOS_KEY,
								new Integer(numHuecosReservados));
						setReturnActionFordward(request,
								mappings.findForward("ver_navegador"));
					} else {
						removeInTemporalSession(request,
								DepositoConstants.LISTA_HUECOS_RESERVADOS_KEY);
						removeInTemporalSession(
								request,
								DepositoConstants.LISTA_NUEVOS_HUECOS_A_OCUPAR_KEY);
						removeInTemporalSession(request,
								DepositoConstants.LISTA_HUECOS_A_LIBERAR_KEY);

						List[] listaHuecosAUbicar = serviceDeposito
								.getHuecosUbicacionSinReservaSuficiente(
										edificioVO.getId(),
										edificioVO.getIdTipoElemento(),
										frm.getIdrelacionseleccionada());

						setInTemporalSession(request,
								DepositoConstants.LISTA_HUECOS_RESERVADOS_KEY,
								listaHuecosAUbicar[0]);
						setInTemporalSession(
								request,
								DepositoConstants.LISTA_NUEVOS_HUECOS_A_OCUPAR_KEY,
								listaHuecosAUbicar[1]);

						// Añadido porque no liberaba bien los huecos
						setInTemporalSession(request,
								DepositoConstants.LISTA_HUECOS_A_LIBERAR_KEY,
								listaHuecosAUbicar[2]);
						setReturnActionFordward(request,
								mappings.findForward("informe_ubicacion"));
					}
				}
			} else {
				// si la relacion cabe en el edificio seleccionado => paso a
				// informe de ubicacion
				List[] listaHuecosUbicacion = serviceDeposito
						.getHuecosUbicacionSinReservaSuficiente(
								relacionVO.getIddeposito(),
								DepositoVO.ID_TIPO_ELEMENTO_UBICACION,
								relacionVO.getId());

				if (listaHuecosUbicacion[1] != null
						&& listaHuecosUbicacion[1].size() >= numeroUnidadesInstalacion) {
					setInTemporalSession(request,
							DepositoConstants.LISTA_NUEVOS_HUECOS_A_OCUPAR_KEY,
							listaHuecosUbicacion[1]);
					ConfigConstants cfgConstants = ConfigConstants
							.getInstance();
					if (cfgConstants.mostrarBotonSeleccionDestinoUbicacion()) {
						setInTemporalSession(
								request,
								DepositoConstants.PERMITIR_SELECCIONAR_ELEMENTO_UBICACION,
								Boolean.TRUE);
					}
					setReturnActionFordward(request,
							mappings.findForward("informe_ubicacion"));
				} else {
					// mostrar mensaje indicando que actualmente no se puede
					// ubicar en el deposito seleccionado
					obtenerErrores(request, true)
							.add(ActionErrors.GLOBAL_ERROR,
									new ActionError(
											Constants.ESPACIO_EN_UBICACION_INSUFICIENTE));
					goBackExecuteLogic(mappings, form, request, response);
				}
			}
			if (relacionVO.getIsIngresoDirecto())
				saveCurrentInvocation(
						KeysClientsInvocations.DEPOSITO_UBICACION_COMPROBAR_RESERVA_INGRESO,
						request);
			else
				saveCurrentInvocation(
						KeysClientsInvocations.DEPOSITO_UBICACION_COMPROBAR_RESERVA,
						request);
		} catch (NoAvailableSpaceException nase) {
			obtenerErrores(request, true)
					.add(ActionErrors.GLOBAL_ERROR,
							new ActionError(
									Constants.ESPACIO_EN_UBICACION_INSUFICIENTE));
			goLastClientExecuteLogic(mappings, form, request, response);
		}
	}

	/**
	 * Muestra el navegador para seleccionar la nueva ubicación de la relación.
	 * 
	 * @param mappings
	 * @param form
	 * @param request
	 * @param response
	 */
	public void cambiarElementoDestinoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		// si la relacion cabe en el edificio seleccionado => paso a informe de
		// ubicacion
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestorEstructuraDepositoBI serviceDeposito = services
				.lookupGestorEstructuraDepositoBI();
		List listaHuecosOcupacion = (List) getFromTemporalSession(request,
				DepositoConstants.LISTA_NUEVOS_HUECOS_A_OCUPAR_KEY);

		if (!ListUtils.isEmpty(listaHuecosOcupacion)) {
			// Establecer el elemento inicial del navegador.
			DetalleUbicacion detalleUbicacion = (DetalleUbicacion) listaHuecosOcupacion
					.get(0);
			String idElemento = detalleUbicacion.getHueco().getIdElemAPadre();

			ElementoVO padreVO = serviceDeposito
					.getElementoAsignable(idElemento);

			idElemento = padreVO.getId() + Constants.DELIMITER_IDS
					+ padreVO.getIdTipoElemento();
			setInTemporalSession(request,
					DepositoConstants.ELEMENTO_ASIGNABLE_DESTINO_KEY,
					idElemento);

			request.setAttribute(
					DepositoConstants.MOSTRAR_NAVEGADOR_CAMBIO_DESTINO_UBICACION,
					Boolean.TRUE);

		}
		setReturnActionFordward(request,
				mappings.findForward("informe_ubicacion"));

	}

	/**
	 * Cancela la operaciónde cabmio de Elemento de Destino
	 * 
	 * @param mappings
	 * @param form
	 * @param request
	 * @param response
	 */
	public void cancelarCambiarElementoDestinoExecuteLogic(
			ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		setReturnActionFordward(request,
				mappings.findForward("informe_ubicacion"));
	}

	/**
	 * Actualizar la ubicación para que obtenga los huecos a partir del elemento
	 * seleccionado
	 * 
	 * @param mappings
	 * @param form
	 * @param request
	 * @param response
	 */
	public void actualizarubicacionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestorEstructuraDepositoBI serviceDeposito = services
				.lookupGestorEstructuraDepositoBI();
		GestionRelacionesEntregaBI relacionesService = services
				.lookupGestionRelacionesBI();

		GestionReservaForm frm = (GestionReservaForm) form;
		RelacionEntregaVO relacionVO = relacionesService
				.getRelacionXIdRelacion(frm.getIdrelacionseleccionada());
		RelacionEntregaPO relacionPO = (RelacionEntregaPO) RelacionToPO
				.getInstance(services).transform(relacionVO);
		List[] listaHuecosUbicacion;
		try {
			listaHuecosUbicacion = serviceDeposito
					.getHuecosUbicacionSinReservaSuficiente(
							frm.getIdasignabledestino(),
							frm.getIdtipoasignabledestino(), relacionVO.getId());

			if (listaHuecosUbicacion[1] != null
					&& listaHuecosUbicacion[1].size() >= relacionPO.getNumUIs()) {
				setInTemporalSession(request,
						DepositoConstants.LISTA_NUEVOS_HUECOS_A_OCUPAR_KEY,
						listaHuecosUbicacion[1]);

				// Establecer el elemento inicial del navegador.
				DetalleUbicacion detalleUbicacion = (DetalleUbicacion) listaHuecosUbicacion[1]
						.get(0);
				String idElemento = detalleUbicacion.getHueco()
						.getIdElemAPadre();
				ElementoVO padreVO = serviceDeposito
						.getElementoAsignable(idElemento);
				idElemento = padreVO.getId() + Constants.DELIMITER_IDS
						+ padreVO.getIdTipoElemento();
				setInTemporalSession(request,
						DepositoConstants.ELEMENTO_ASIGNABLE_DESTINO_KEY,
						idElemento);

				// Establecer el nuevo seleccionado.
				setInTemporalSession(request,
						DepositoConstants.ELEMENTO_ASIGNABLE_DESTINO_KEY,
						frm.getAsignabledestino());

				// Establecer la ubicación
				setInTemporalSession(request, DepositoConstants.EDIFICIO_KEY,
						relacionVO.getIddeposito() + Constants.DELIMITER_IDS
								+ DepositoVO.ID_TIPO_ELEMENTO_UBICACION);

				setReturnActionFordward(request,
						mappings.findForward("informe_ubicacion"));
			}
		} catch (NoAvailableSpaceException e) {
			ActionErrors errors = new ActionErrors();
			errors.add(
					Constants.ERROR_NO_HUECOS_DISPONIBLES_DESDE_POSICION,
					new ActionError(
							Constants.ERROR_NO_HUECOS_DISPONIBLES_DESDE_POSICION));
			saveErrors(request, errors);
		}
		setReturnActionFordward(request,
				mappings.findForward("informe_ubicacion"));
	}

	public void informeubicacionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ActionErrors errors = null;
		GestionReservaForm frm = (GestionReservaForm) form;
		try {
			GestorEstructuraDepositoBI serviceDeposito = getGestorEstructuraDepositoBI(request);

			errors = frm.validateUbicacion();
			if (errors == null) {

				// podria recoger la relacion y datos acerca de los huecos de la
				// session y mandarlos al servicio pero prefieron obtener
				// de nuevo los datos en el servicio por si hubiesen cambiado
				removeInTemporalSession(request,
						DepositoConstants.LISTA_HUECOS_RESERVADOS_KEY);
				removeInTemporalSession(request,
						DepositoConstants.LISTA_NUEVOS_HUECOS_A_OCUPAR_KEY);
				removeInTemporalSession(request,
						DepositoConstants.LISTA_HUECOS_A_LIBERAR_KEY);

				List[] listaHuecosUbicacion = serviceDeposito
						.getHuecosUbicacionSinReservaSuficiente(
								frm.getIdasignabledestino(),
								frm.getIdtipoasignabledestino(),
								frm.getIdrelacionseleccionada());

				setInTemporalSession(request,
						DepositoConstants.LISTA_HUECOS_RESERVADOS_KEY,
						listaHuecosUbicacion[0]);

				setInTemporalSession(request,
						DepositoConstants.LISTA_NUEVOS_HUECOS_A_OCUPAR_KEY,
						listaHuecosUbicacion[1]);

				setInTemporalSession(request,
						DepositoConstants.LISTA_HUECOS_A_LIBERAR_KEY,
						listaHuecosUbicacion[2]);

			}

		} catch (NoAvailableSpaceException e) {
			obtenerErrores(request, true)
					.add(ActionErrors.GLOBAL_ERROR,
							new ActionError(
									Constants.ESPACIO_EN_UBICACION_INSUFICIENTE));
		}

		if (errors != null) {
			setReturnActionFordward(request,
					mappings.findForward("ver_navegador"));
			ErrorsTag.saveErrors(request, errors);

		} else {
			setReturnActionFordward(request,
					mappings.findForward("informe_ubicacion"));
			saveCurrentInvocation(
					KeysClientsInvocations.DEPOSITO_UBICACION_INFORME, request);
		}
	}

	public void aceptarinformeubicacionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		GestionReservaForm frm = (GestionReservaForm) form;
		RelacionEntregaVO relacionVO = (RelacionEntregaVO) getFromTemporalSession(
				request, DepositoConstants.RELACION_KEY);
		List huecosReservadosAOcupar = (List) getFromTemporalSession(request,
				DepositoConstants.LISTA_HUECOS_RESERVADOS_KEY);

		List huecosLibresAOcupar = (List) getFromTemporalSession(request,
				DepositoConstants.LISTA_NUEVOS_HUECOS_A_OCUPAR_KEY);

		List huecosReservadorALiberar = (List) getFromTemporalSession(request,
				DepositoConstants.LISTA_HUECOS_A_LIBERAR_KEY);

		GestorEstructuraDepositoBI serviceDeposito = getGestorEstructuraDepositoBI(request);
		GestionRelacionesEntregaBI serviceRelaciones = getGestionRelacionesBI(request);

		// obtener el tipo y el id del destino
		boolean seleccionoDestino = frm.getIdasignabledestino() != null
				&& frm.getIdtipoasignabledestino() != null;
		String idDestino = null, idTipoDestino = null;
		if (seleccionoDestino) {
			idDestino = frm.getIdasignabledestino();
			idTipoDestino = frm.getIdtipoasignabledestino();
		} else {
			if (relacionVO.getReservadeposito() == ReservaPrevision.RESERVADA
					.getIdentificador()) {
				idDestino = relacionVO.getIdelmtodreserva();
				// ElementoNoAsignableVO noAsignable =
				// serviceDeposito.getNoAsignable(idDestino);
				idTipoDestino = relacionVO.getIdtipoelmtodreserva();
			} else {
				idDestino = relacionVO.getIddeposito();
				idTipoDestino = DepositoVO.ID_TIPO_ELEMENTO_UBICACION;
			}
		}
		try {

			if (relacionVO.getIsIngresoDirecto()) {
				Locker locker = new Locker(getServiceClient(request));

				try {
					// Bloquear la Relación
					locker.bloqueaCualquiera(getServiceClient(request),
							relacionVO.getId(),
							LockerObjectTypes.VALIDACION_RELACION,
							ArchivoModules.TRANSFERENCIAS_MODULE);

					serviceRelaciones.signaturarUbicarValidarIngreso(
							relacionVO, huecosLibresAOcupar, idDestino,
							idTipoDestino, relacionVO.getIdNivelDocumental());
				} catch (ActionNotAllowedException anae) {
					guardarError(request, anae);
				} catch (SecurityException e) {
					obtenerErrores(request, true)
							.add(ActionErrors.GLOBAL_MESSAGE,
									new ActionError(
											FondosConstants.ERROR_VALIDACION_UDOCS_SIN_PERMISOS));

				} finally {
					// Desbloquear la Relación
					locker.desbloquea(getServiceClient(request),
							relacionVO.getId(),
							LockerObjectTypes.VALIDACION_RELACION,
							ArchivoModules.TRANSFERENCIAS_MODULE);
				}

			} else {
				serviceDeposito.ubicarRelacion(relacionVO.getId(),
						huecosReservadosAOcupar, huecosReservadorALiberar,
						huecosLibresAOcupar, idDestino, idTipoDestino);
			}
			popLastInvocation(request);
			ActionRedirect forwardVistaRelacion = new ActionRedirect(
					mappings.findForward("redirect_to_info_relacion"));
			forwardVistaRelacion.setRedirect(true);
			forwardVistaRelacion.addParameter("idrelacionseleccionada",
					relacionVO.getId());
			setReturnActionFordward(request, forwardVistaRelacion);
		} catch (ConcurrentModificationException cme) {
			obtenerErrores(request, true).add(
					ActionErrors.GLOBAL_ERROR,
					new ActionError(ErrorKeys.ESTADO_HUECO_ALTERADO, cme
							.getHueco().getPath()));
			goLastClientExecuteLogic(mappings, form, request, response);
		} catch (ArchivoModelException ame) {
			obtenerErrores(request, true).add(ActionErrors.GLOBAL_ERROR,
					new ActionError(ame.getMessage()));
			goLastClientExecuteLogic(mappings, form, request, response);
		} catch (SecurityException e) {
			obtenerErrores(request, true)
					.add(ActionErrors.GLOBAL_MESSAGE,
							new ActionError(
									FondosConstants.ERROR_VALIDACION_UDOCS_SIN_PERMISOS));
			goLastClientExecuteLogic(mappings, form, request, response);
			goLastClientExecuteLogic(mappings, form, request, response);
		} catch (LockerException e) {
			// Mostrar el error
			obtenerErrores(request, true).add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							ErrorKeys.DESCRIPCION_BLOQUEO_VALIDACION_RELACION,
							new Object[] { e.getLockVO().getUsuario(),
									e.getLockVO().getTs() }));
			setReturnActionFordward(request,
					mappings.findForward("globalerror"));
			return;
		} catch (ActionNotAllowedException e) {
			guardarError(request, e);
			goLastClientExecuteLogic(mappings, form, request, response);
		} catch (Exception e) {
			obtenerErrores(request, true).add(ActionErrors.GLOBAL_ERROR,
					new ActionError(e.getMessage()));
			goLastClientExecuteLogic(mappings, form, request, response);
		}
		// goBackExecuteLogic(mappings, form, request, response);
	}

}