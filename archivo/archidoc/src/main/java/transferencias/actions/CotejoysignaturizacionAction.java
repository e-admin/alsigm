package transferencias.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import se.usuarios.ServiceClient;
import transferencias.Errors;
import transferencias.EstadoCotejo;
import transferencias.ReservaPrevision;
import transferencias.TransferenciasConstants;
import transferencias.forms.CotejoysignaturizacionForm;
import transferencias.model.TipoSignaturacion;
import transferencias.vos.IParteUnidadDocumentalVO;
import transferencias.vos.IUnidadInstalacionVO;
import transferencias.vos.ParteUnidadDocumentalVO;
import transferencias.vos.RelacionEntregaVO;
import transferencias.vos.UDocElectronicaVO;
import transferencias.vos.UnidadInstalacionVO;
import util.CollectionUtils;
import util.ErrorsTag;

import common.ConfigConstants;
import common.Constants;
import common.actions.ActionRedirect;
import common.actions.BaseAction;
import common.bi.GestionFondosBI;
import common.bi.GestionRelacionesEACRBI;
import common.bi.GestionRelacionesEntregaBI;
import common.bi.ServiceRepository;
import common.exceptions.ActionNotAllowedException;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;
import common.security.SecurityManagerBase;
import common.security.SecurityManagerLocator;
import common.security.TransferenciasSecurityManager;
import common.util.ArrayUtils;
import common.util.ListUtils;
import common.util.StringUtils;
import common.view.IVisitedRowVO;

import fondos.vos.FondoVO;

/**
 * Action que maneja las acciones que pueden ser invocadas para completar el
 * cotejo de una relacion de entrega
 */
public class CotejoysignaturizacionAction extends BaseAction {

	/**
	 * Muestra el listado de cajas de la relación de entrega.
	 * 
	 * @param mapping
	 *            {@link ActionMapping}con los mapeos asociado.
	 * @param form
	 *            {@link ActionForm}asociado al action.
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 */
	public void verCajasExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// Enlace en la barra de navegación
		saveCurrentInvocation(
				KeysClientsInvocations.TRANSFERENCIAS_CAJAS_A_COTEJAR, request);

		// Información del formulario
		CotejoysignaturizacionForm frm = (CotejoysignaturizacionForm) form;

		// Servicio de gestión de relaciones de entrega
		GestionRelacionesEntregaBI relacionBI = getGestionRelacionesBI(request);
		GestionRelacionesEACRBI relacionEABI = getGestionRelacionesEACRBI(request);

		// Relación de entrega
		RelacionEntregaVO relacionVO = relacionBI.getRelacionXIdRelacion(frm
				.getCodigoseleccionada());
		RelacionEntregaPO relacionPO = (RelacionEntregaPO) RelacionToPO
				.getInstance(getServiceRepository(request)).transform(
						relacionVO);
		setInTemporalSession(request, TransferenciasConstants.RELACION_KEY,
				relacionPO);

		// Cajas de la relación de entrega
		List cajas = relacionBI.getUnidadesInstalacion(
				frm.getCodigoseleccionada(), relacionVO.getTipotransferencia());
		List udocElectronicas = null;

		if (relacionVO.isEntreArchivos()) {
			if (relacionVO.isRelacionConReencajado()) {
				// Cajas de la relación de entrega
				cajas = relacionEABI.getUIsReencajado(frm
						.getCodigoseleccionada());
			}
			// Unidades documentales eclectrónicas
			udocElectronicas = relacionBI
					.getUDocsElectronicasByIdRelacionEntreArchivos(frm
							.getCodigoseleccionada());
		} else {
			// Unidades documentales eclectrónicas
			udocElectronicas = relacionBI.getUDocsElectronicasByIdRelacion(frm
					.getCodigoseleccionada());
		}

		setInTemporalSession(request, TransferenciasConstants.CAJAS_KEY, cajas);
		setInTemporalSession(request,
				TransferenciasConstants.LISTA_UDOCS_ELECTRONICAS_KEY,
				udocElectronicas);

		// Mostrar botones de finalización de cotejo o signaturar
		showFinalizarCotejoSignaturarButtons(request, relacionPO, cajas,
				udocElectronicas);

		setReturnActionFordward(request, mappings.findForward("cajas_cotejo"));
	}

	/**
	 * Muestra el listado de cajas de la relación de entrega.
	 * 
	 * @param mapping
	 *            {@link ActionMapping}con los mapeos asociado.
	 * @param form
	 *            {@link ActionForm}asociado al action.
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 */
	public void verCajasReencajadoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		// Enlace en la barra de navegación
		saveCurrentInvocation(
				KeysClientsInvocations.TRANSFERENCIAS_CAJAS_A_COTEJAR, request);

		InfoUDocReeacr infoUDocReeacr = (InfoUDocReeacr) getFromTemporalSession(
				request, TransferenciasConstants.INFO_UDOCREEACR);
		RelacionEntregaPO relacionPO = (RelacionEntregaPO) getFromTemporalSession(
				request, TransferenciasConstants.RELACION_KEY);

		if (infoUDocReeacr != null && relacionPO != null) {
			showFinalizarCotejoSignaturarButtons(request, relacionPO,
					infoUDocReeacr.getListaUIs(), null);
		}
		setReturnActionFordward(request,
				mappings.findForward("cajas_cotejo_reencajado"));
	}

	/**
	 * Comprueba si se deben mostrar los botones de finalización de cotejo y
	 * signaturización
	 * 
	 * @param request
	 * @param cajas
	 */
	private void showFinalizarCotejoSignaturarButtons(
			HttpServletRequest request, RelacionEntregaPO relacionPO,
			List cajas, List udocsElectronicas) {
		boolean hayErrorEnCotejo = false;
		boolean hayCajasPendientes = false;
		boolean isSinDocsFisicos = false;

		if (CollectionUtils.isEmpty(cajas)
				&& !CollectionUtils.isEmpty(udocsElectronicas)) {
			isSinDocsFisicos = true;
		}

		if (!CollectionUtils.isEmpty(cajas)) {
			for (int i = 0; i < cajas.size(); i++) {
				IUnidadInstalacionVO ui = (IUnidadInstalacionVO) cajas.get(i);
				if (ui.isPendiente()) {
					hayCajasPendientes = true;
				} else if (ui.isConErrores()) {
					hayErrorEnCotejo = true;
				}
			}
		}

		if (!CollectionUtils.isEmpty(udocsElectronicas)) {
			for (int i = 0; i < udocsElectronicas.size(); i++) {
				UDocElectronicaVO udoc = (UDocElectronicaVO) udocsElectronicas
						.get(i);

				if (udoc.getEstadoCotejo() == EstadoCotejo.PENDIENTE
						.getIdentificador() || udoc.getEstadoCotejo() == 0) {
					hayCajasPendientes = true;
				} else if (udoc.getEstadoCotejo() == EstadoCotejo.ERRORES
						.getIdentificador()) {
					hayErrorEnCotejo = true;
				}
			}
		}

		boolean signaturacionAsociadaAHueco = relacionPO.getArchivoReceptor()
				.getTiposignaturacion() == TipoSignaturacion.SIGNATURACION_ASOCIADA_A_HUECO
				.getIdentificador();

		boolean verBotonSignaturar = false;
		boolean verBotonAceptarCotejo = false;
		boolean verBotonFinalizarCotejo = false;
		boolean verBotonGenerarCartelas = false;
		boolean puedeSerSignaturada = false;

		if (signaturacionAsociadaAHueco) {
			verBotonSignaturar = false;
			verBotonAceptarCotejo = (!hayCajasPendientes && !hayErrorEnCotejo);
			verBotonFinalizarCotejo = (!hayCajasPendientes && hayErrorEnCotejo);
			verBotonGenerarCartelas = false;
			puedeSerSignaturada = true;
		} else {
			verBotonSignaturar = (!hayCajasPendientes && !hayErrorEnCotejo);
			verBotonAceptarCotejo = false;
			verBotonFinalizarCotejo = (!hayCajasPendientes && hayErrorEnCotejo);
			verBotonGenerarCartelas = true;
			puedeSerSignaturada = true;
		}

		if (ListUtils.isEmpty(cajas)) {
			verBotonGenerarCartelas = false;
		}

		// Si aparece el botón Signaturar
		if (verBotonSignaturar) {
			// Si es una relación entre archivos y es signatura única
			if (relacionPO.isEntreArchivos()
					&& !ConfigConstants.getInstance()
							.getSignaturacionPorArchivo() || isSinDocsFisicos) {
				puedeSerSignaturada = false;
				verBotonGenerarCartelas = false;
			}
		}

		if (CollectionUtils.isEmpty(cajas)
				&& CollectionUtils.isEmpty(udocsElectronicas)) {
			verBotonSignaturar = false;
			verBotonAceptarCotejo = false;
			verBotonFinalizarCotejo = false;
			verBotonGenerarCartelas = false;
			puedeSerSignaturada = false;
		}

		request.setAttribute(TransferenciasConstants.VER_BOTON_SIGNATURAR_KEY,
				new Boolean(verBotonSignaturar));

		request.setAttribute(
				TransferenciasConstants.VER_BOTON_ACEPTAR_COTEJO_KEY,
				new Boolean(verBotonAceptarCotejo));

		request.setAttribute(
				TransferenciasConstants.VER_BOTON_FINALIZAR_COTEJO_KEY,
				new Boolean(verBotonFinalizarCotejo));

		request.setAttribute(
				TransferenciasConstants.VER_BOTON_GENERAR_CARTELAS_KEY,
				new Boolean(verBotonGenerarCartelas));

		request.setAttribute(TransferenciasConstants.PUEDE_SER_SIGNATURADA_KEY,
				new Boolean(puedeSerSignaturada));
	}

	/**
	 * Marca las cajas seleccionadas como revisadas.
	 * 
	 * @param mapping
	 *            {@link ActionMapping}con los mapeos asociado.
	 * @param form
	 *            {@link ActionForm}asociado al action.
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 */
	public void marcarCajasExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// Comprobar las cajas seleccionadas
		String[] ids = request.getParameterValues("revisada");
		if (!ArrayUtils.isEmpty(ids)) {
			// Relación de entrega
			RelacionEntregaVO relacionEntrega = (RelacionEntregaVO) getFromTemporalSession(
					request, TransferenciasConstants.RELACION_KEY);

			// Modificar el estado de las unidades de instalación
			getGestionRelacionesBI(request).updateEstadoUnidadesInstalacion(
					ids, EstadoCotejo.REVISADA.getIdentificador(),
					relacionEntrega);
		}

		goLastClientExecuteLogic(mappings, form, request, response);
	}

	/**
	 * Marca las cajas seleccionadas como revisadas.
	 * 
	 * @param mapping
	 *            {@link ActionMapping}con los mapeos asociado.
	 * @param form
	 *            {@link ActionForm}asociado al action.
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 */
	public void marcarCajasConErroresCotejoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// Comprobar las cajas seleccionadas
		String[] ids = request.getParameterValues("revisada");
		if (!ArrayUtils.isEmpty(ids)) {
			// Relación de entrega
			RelacionEntregaVO relacionEntrega = (RelacionEntregaVO) getFromTemporalSession(
					request, TransferenciasConstants.RELACION_KEY);

			// Modificar el estado de las unidades de instalación
			getGestionRelacionesBI(request).updateEstadoUnidadesInstalacion(
					ids, EstadoCotejo.ERRORES.getIdentificador(),
					relacionEntrega);
		}

		goLastClientExecuteLogic(mappings, form, request, response);
	}

	/**
	 * Muestra el contenido de la caja seleccionada.
	 * 
	 * @param mapping
	 *            {@link ActionMapping}con los mapeos asociado.
	 * @param form
	 *            {@link ActionForm}asociado al action.
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 */
	public void verCajaExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// Información del formulario
		CotejoysignaturizacionForm frm = (CotejoysignaturizacionForm) form;

		// Añadir los parámetros del formulario al enlace
		getInvocationStack(request).getLastClientInvocation().addParameters(
				frm.getMap());

		// Enlace en la barra de navegación
		saveCurrentInvocation(
				KeysClientsInvocations.TRANSFERENCIAS_CONTENIDO_CAJA_A_COTEJAR,
				request);

		// Información de la unidad de instalación
		UnidadInstalacionPO2 uInstalacion = (UnidadInstalacionPO2) UnidadInstalacionToPO2
				.getInstance(getServiceRepository(request)).transform(
						getGestionRelacionesBI(request).getUnidadInstalacion(
								frm.getIdCaja()));
		frm.setDevolver(uInstalacion.getDevolucion());
		frm.setNotasCotejo(uInstalacion.getNotasCotejo());

		// Información de la caja
		setInTemporalSession(request, TransferenciasConstants.CAJA_KEY,
				uInstalacion);

		Boolean createNewMapVisitados = (Boolean) getFromTemporalSession(
				request, TransferenciasConstants.CAJA_MAINTAIN_VISITADOS_KEY);
		if (createNewMapVisitados == null) {
			setInTemporalSession(request,
					TransferenciasConstants.CAJA_VISITADOS_KEY, new HashMap());
		} else {
			List ltElements = uInstalacion.getContenido();

			// Restaurar los elementos visitados
			if (!CollectionUtils.isEmpty(ltElements)) {
				Map mapVisitedElements = (Map) getFromTemporalSession(request,
						TransferenciasConstants.CAJA_VISITADOS_KEY);
				if ((ltElements != null) && (!ltElements.isEmpty())) {
					ListIterator it = ltElements.listIterator();
					while (it.hasNext()) {
						ParteUnidadDocumentalVO element = (ParteUnidadDocumentalVO) it
								.next();
						if ((mapVisitedElements != null)
								&& (mapVisitedElements.get(element
										.getIdUnidadDoc()) != null)) {
							element.setRowStyle(IVisitedRowVO.CSS_FILA_CARGADA);
						}
					}
				}
			}
		}

		setReturnActionFordward(request,
				mappings.findForward("contenido_caja_cotejo"));
	}

	/**
	 * Muestra el contenido de la caja seleccionada.
	 * 
	 * @param mapping
	 *            {@link ActionMapping}con los mapeos asociado.
	 * @param form
	 *            {@link ActionForm}asociado al action.
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 */
	public void verContenidoCajaReencajadoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// Información del formulario
		CotejoysignaturizacionForm frm = (CotejoysignaturizacionForm) form;

		// Añadir los parámetros del formulario al enlace
		getInvocationStack(request).getLastClientInvocation().addParameters(
				frm.getMap());

		// Información de la unidad de instalación
		IUnidadInstalacionVO uInstalacion = getGestionRelacionesEACRBI(request)
				.getUIReencajadoById(frm.getIdCaja());

		// Información de la caja
		setInTemporalSession(request,
				TransferenciasConstants.UI_REENCAJADO_KEY, uInstalacion);

		List udocs = getGestionRelacionesEACRBI(request)
				.getUDocsByUIReencajado(frm.getIdCaja());

		setInTemporalSession(request,
				TransferenciasConstants.LISTA_UDOCS_REENCAJADO_KEY, udocs);

		setReturnActionFordward(request,
				mappings.findForward("contenido_caja_reencajado"));
	}

	public void guardarInfoCajaEntreArchivosExecuteLogic(
			ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		List listaCajas = (List) getFromTemporalSession(request,
				TransferenciasConstants.CAJAS_KEY);
		guardarInfoCajaEntreArchivosCodeLogic(mappings, form, request,
				response, listaCajas);

		setReturnActionFordward(request, mappings.findForward("cajas_cotejo"));
	}

	public void guardarInfoCajaEntreArchivosCRExecuteLogic(
			ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		InfoUDocReeacr infoUDocReeacr = (InfoUDocReeacr) getFromTemporalSession(
				request, TransferenciasConstants.INFO_UDOCREEACR);

		guardarInfoCajaEntreArchivosCodeLogic(mappings, form, request,
				response, infoUDocReeacr.getListaUIs());
		setInTemporalSession(request, TransferenciasConstants.INFO_UDOCREEACR,
				infoUDocReeacr);

		setReturnActionFordward(request,
				mappings.findForward("cajas_cotejo_reencajado"));
	}

	private void guardarInfoCajaEntreArchivosCodeLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response, List listaCajas) {

		RelacionEntregaPO relacionPO = (RelacionEntregaPO) getFromTemporalSession(
				request, TransferenciasConstants.RELACION_KEY);

		for (int i = 0; i < listaCajas.size(); i++) {
			IUnidadInstalacionVO caja = (IUnidadInstalacionVO) listaCajas
					.get(i);
			int estadoCotejo = Integer.parseInt(request
					.getParameter("estadocotejounidaddocumental("
							+ caja.getId() + ")"));
			String observaciones = null;
			String devolver = Constants.FALSE_STRING;
			if (EstadoCotejo.isConErrores(estadoCotejo)) {
				observaciones = request.getParameter("observacioneserror("
						+ caja.getId() + ")");
				devolver = request.getParameter("devolver" + caja.getId());
			}
			caja.setEstadoCotejo(estadoCotejo);
			caja.setNotasCotejo(observaciones);
			if (Constants.CHECKED_STRING.equalsIgnoreCase(devolver)) {
				caja.setDevolucion(Constants.TRUE_STRING);
			} else {
				caja.setDevolucion(Constants.FALSE_STRING);
			}
		}
		getGestionRelacionesBI(request).updateUnidadInstalacion(listaCajas,
				relacionPO);
		showFinalizarCotejoSignaturarButtons(request, relacionPO, listaCajas,
				null);

	}

	/**
	 * Guarda la información de la caja.
	 * 
	 * @param mapping
	 *            {@link ActionMapping}con los mapeos asociado.
	 * @param form
	 *            {@link ActionForm}asociado al action.
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 */
	public void guardarInfoCajaExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		CotejoysignaturizacionForm frm = (CotejoysignaturizacionForm) form;

		// Información de la unidad de instalación en sesión
		UnidadInstalacionPO2 caja = (UnidadInstalacionPO2) getFromTemporalSession(
				request, TransferenciasConstants.CAJA_KEY);

		// Guardar la info de la caja
		Map udocsXui = new HashMap();
		udocsXui.put(caja, caja.getContenido());

		if (hayCambiosEnUdocs(udocsXui, frm))
			getGestionRelacionesBI(request).guardarCotejo(
					frm.getCodigoseleccionada(), udocsXui);

		setReturnActionFordward(request,
				mappings.findForward("contenido_caja_cotejo"));

	}

	/**
	 * Cierra la caja.
	 * 
	 * @param mapping
	 *            {@link ActionMapping}con los mapeos asociado.
	 * @param form
	 *            {@link ActionForm}asociado al action.
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 */
	public void closeCajaExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		removeInTemporalSession(request,
				TransferenciasConstants.CAJA_MAINTAIN_VISITADOS_KEY);
		removeInTemporalSession(request,
				TransferenciasConstants.CAJA_VISITADOS_KEY);

		goBackExecuteLogic(mappings, form, request, response);
	}

	/**
	 * Comprueba si hay cambios en la unidad de instalación.
	 * 
	 * @param udocsXui
	 *            Lista de unidades de instalación y sus partes.
	 * @param frm
	 *            Formulario.
	 * @return true si hay cambios.
	 */
	private boolean hayCambiosEnUdocs(Map udocsXui,
			CotejoysignaturizacionForm frm) {
		boolean hayCambios = false;

		UnidadInstalacionVO uInstalacion;
		Collection partesUDocs;
		Iterator itUDocs;
		ParteUnidadDocumentalVO parteUdoc;

		int estadoCotejo;
		String notasCotejo;
		String devolver;

		Iterator itUInstalacion = udocsXui.keySet().iterator();
		while (itUInstalacion.hasNext()) {
			uInstalacion = (UnidadInstalacionVO) itUInstalacion.next();
			partesUDocs = (Collection) udocsXui.get(uInstalacion);

			// Comprobar cambios en las partes de las unidades documentales
			itUDocs = partesUDocs.iterator();
			while (itUDocs.hasNext()) {
				parteUdoc = (ParteUnidadDocumentalVO) itUDocs.next();

				estadoCotejo = Integer.parseInt(frm
						.getEstadocotejounidaddocumental(parteUdoc));
				notasCotejo = frm.getObservacioneserror(parteUdoc);

				if ((estadoCotejo != parteUdoc.getEstadoCotejo())
						|| !StringUtils.equalsNullEmpty(notasCotejo,
								parteUdoc.getNotasCotejo())) {
					hayCambios = true;
					parteUdoc.setFlagChanged(true);
					parteUdoc.setEstadoCotejo(estadoCotejo);
					parteUdoc.setNotasCotejo(notasCotejo);
				}
			}

			// Comprobar cambios en la unidad de instalación
			notasCotejo = frm.getNotasCotejo();
			devolver = frm.getDevolver();

			if (!StringUtils.equalsNullEmpty(notasCotejo,
					uInstalacion.getNotasCotejo())
					|| !StringUtils.equalsNullEmpty(devolver,
							uInstalacion.getDevolucion())) {
				hayCambios = true;
				uInstalacion.setChanged(true);
				uInstalacion.setDevolver(devolver != null ? true : false);
				uInstalacion.setNotasCotejo(notasCotejo);
				/*
				 * el estado de la caja no se actualiza en la vista => ya lo
				 * hace el servicio comprobando las partes!!
				 */
			}
		}

		return hayCambios;
	}

	public void finalizarCotejoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		CotejoysignaturizacionForm frm = (CotejoysignaturizacionForm) form;
		try {
			ServiceRepository services = ServiceRepository
					.getInstance(ServiceClient.create(getAppUser(request)));
			GestionRelacionesEntregaBI serviceRelaciones = services
					.lookupGestionRelacionesBI();
			RelacionEntregaVO relacionEntrega = (RelacionEntregaVO) getFromTemporalSession(
					request, TransferenciasConstants.RELACION_KEY);
			if (relacionEntrega == null) {
				relacionEntrega = serviceRelaciones.getRelacionXIdRelacion(frm
						.getCodigoseleccionada());
			}

			// Comprobar que el usuario tiene permisos para realizar la
			// corrección en archivo.
			if (Constants.TRUE_STRING.equals(frm.getCorreccionenarchivo())) {
				// Comprobar si tiene permisos para establecer el valor a 'S'
				SecurityManagerBase securityManager = SecurityManagerLocator
						.loockupTransferenciasSM();
				securityManager
						.check(TransferenciasSecurityManager.CORRECCION_ERRORES_EN_ARCHIVO,
								ServiceClient.create(getAppUser(request)));
				relacionEntrega.setCorreccionenarchivo(frm
						.getCorreccionenarchivo());
			}

			// Comprobar si la gestión de reserva está pendiente
			if (relacionEntrega.getReservadeposito() != ReservaPrevision.PENDIENTE
					.getIdentificador()) {
				InfoAsignacionUdocs infoAsignacionUdocs = new InfoAsignacionUdocs(
						relacionEntrega, serviceRelaciones);
				serviceRelaciones.finalizarCotejo(relacionEntrega,
						infoAsignacionUdocs.getUdocsXui());
				setInTemporalSession(request,
						TransferenciasConstants.ASIGNACION_UDOC2UI,
						infoAsignacionUdocs);

				ClientInvocation lastClientInvocation = getInvocationStack(
						request).goBackClientInvocation(request);
				setReturnActionFordward(request, new ActionForward(
						lastClientInvocation.getInvocationURI(), true));
			} else {
				obtenerErrores(request, true).add(ActionErrors.GLOBAL_MESSAGE,
						new ActionError(Errors.ERROR_RESERVA_PENDIENTE));
				goLastClientExecuteLogic(mappings, form, request, response);
			}
		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
			goLastClientExecuteLogic(mappings, form, request, response);
		}
	}

	/*
	 * ========================================================================
	 * 
	 * LÓGICA ANTERIOR
	 * 
	 * ========================================================================
	 */

	public void listadocajasExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		CotejoysignaturizacionForm frm = (CotejoysignaturizacionForm) form;
		GestionRelacionesEntregaBI serviceRelaciones = services
				.lookupGestionRelacionesBI();
		RelacionEntregaVO relacionVO = serviceRelaciones
				.getRelacionXIdRelacion(frm.getCodigoseleccionada());
		setInTemporalSession(request, TransferenciasConstants.RELACION_KEY,
				RelacionToPO.getInstance(services).transform(relacionVO));
		setInTemporalSession(request,
				TransferenciasConstants.ASIGNACION_UDOC2UI,
				new InfoAsignacionUdocs(relacionVO, serviceRelaciones));
		preparaDatosParaVista(form, request);
		saveCurrentInvocation(
				KeysClientsInvocations.TRANSFERENCIAS_CAJAS_A_COTEJAR, request);
		setReturnActionFordward(request,
				listadoCajas(mappings, form, request, response));
	}

	String getREQUEST_URI(HttpServletRequest request) {
		StringBuffer ret = new StringBuffer().append(request.getContextPath()
				+ request.getServletPath() + request.getPathInfo());
		if (request.getQueryString() != null)
			ret.append("?" + request.getQueryString());
		return ret.toString();
	}

	public void guardarestadocajasExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		guardarestadocajasCodeLogic(mappings, form, request, response);
		setReturnActionFordward(request,
				listadoCajas(mappings, form, request, response));
	}

	private void preparaDatosParaVista(ActionForm form,
			HttpServletRequest request) {
		CotejoysignaturizacionForm frm = (CotejoysignaturizacionForm) form;
		setInTemporalSession(request, TransferenciasConstants.REQUEST_URI,
				getREQUEST_URI(request));
		setInTemporalSession(request, TransferenciasConstants.CAJAS_ABIERTAS,
				getCajasAbiertasFromFRM(frm));
		setInTemporalSession(request,
				TransferenciasConstants.ULTIMA_CAJA_VISTA,
				frm.getUltimacajavista());
	}

	public void verudocExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		guardarestadocajasCodeLogic(mappings, form, request, response);
		preparaDatosParaVista(form, request);
		// meter en el navegador la URL para volver y mantener el estado q habia
		// antes de la vista
		/* RelacionEntregaPO relacionEntregaPO = (RelacionEntregaPO) */getFromTemporalSession(
				request, TransferenciasConstants.RELACION_KEY);
		// setOnCancelStack(getForwardVolverCotejoConRedirect(form, request),
		// request);
		saveCurrentInvocation(
				KeysClientsInvocations.TRANSFERENCIAS_VER_UNIDAD_DOCUMENTAL,
				request);
		CotejoysignaturizacionForm frm = (CotejoysignaturizacionForm) form;
		ActionForward ret = new ActionForward();
		ret.setPath(frm.getUrlverudoc().substring(
				request.getContextPath().length()));
		ret.setRedirect(true);
		setReturnActionFordward(request, ret);
	}

	public void guardarVerUnidadDocumentalExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		CotejoysignaturizacionForm frm = (CotejoysignaturizacionForm) form;

		// Información de la unidad de instalación en sesión
		UnidadInstalacionPO2 caja = (UnidadInstalacionPO2) getFromTemporalSession(
				request, TransferenciasConstants.CAJA_KEY);

		// Guardar la info de la caja
		Map udocsXui = new HashMap();
		udocsXui.put(caja, caja.getContenido());

		if (hayCambiosEnUdocs(udocsXui, frm))
			getGestionRelacionesBI(request).guardarCotejo(
					frm.getCodigoseleccionada(), udocsXui);

		// Obtener la lista para marcar el seleccionado
		List elements = (List) caja.getContenido();
		Map mapVisitedElements = (Map) getFromTemporalSession(request,
				TransferenciasConstants.CAJA_VISITADOS_KEY);
		if ((elements != null) && (!elements.isEmpty())) {
			ListIterator it = elements.listIterator();
			while (it.hasNext()) {
				ParteUnidadDocumentalVO element = (ParteUnidadDocumentalVO) it
						.next();
				if (element.getIdUnidadDoc().equals(frm.getIdUdoc())) {
					element.setRowStyle(IVisitedRowVO.CSS_FILA_CARGADA);
					if (mapVisitedElements != null)
						mapVisitedElements.put(element.getIdUnidadDoc(),
								element.getIdUnidadDoc());
					break;
				}
			}
		}

		// Guardar una variable para indicar que no se crea un nuevo map de
		// visitados
		setInTemporalSession(request,
				TransferenciasConstants.CAJA_MAINTAIN_VISITADOS_KEY,
				Boolean.TRUE);

		ActionRedirect forwardToViewUdoc = new ActionRedirect(
				mappings.findForward("redirect_to_view_udoc"));
		forwardToViewUdoc.setRedirect(true);
		forwardToViewUdoc.addParameter("udocID", frm.getIdUdoc());
		setReturnActionFordward(request, forwardToViewUdoc);

	}

	public void verUnidadDocumentalExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		CotejoysignaturizacionForm frm = (CotejoysignaturizacionForm) form;

		// Información de la unidad de instalación en sesión
		UnidadInstalacionPO2 caja = (UnidadInstalacionPO2) getFromTemporalSession(
				request, TransferenciasConstants.CAJA_KEY);

		// Obtener la lista para marcar el seleccionado
		List elements = (List) caja.getContenido();
		Map mapVisitedElements = (Map) getFromTemporalSession(request,
				TransferenciasConstants.CAJA_VISITADOS_KEY);
		if ((elements != null) && (!elements.isEmpty())) {
			ListIterator it = elements.listIterator();
			while (it.hasNext()) {
				ParteUnidadDocumentalVO element = (ParteUnidadDocumentalVO) it
						.next();
				if (element.getIdUnidadDoc().equals(frm.getIdUdoc())) {
					element.setRowStyle(IVisitedRowVO.CSS_FILA_CARGADA);
					if (mapVisitedElements != null)
						mapVisitedElements.put(element.getIdUnidadDoc(),
								element.getIdUnidadDoc());
					break;
				}
			}
		}

		// Guardar una variable para indicar que no se crea un nuevo map de
		// visitados
		setInTemporalSession(request,
				TransferenciasConstants.CAJA_MAINTAIN_VISITADOS_KEY,
				Boolean.TRUE);

		ActionRedirect forwardToViewUdoc = new ActionRedirect(
				mappings.findForward("redirect_to_view_udoc"));
		forwardToViewUdoc.setRedirect(true);
		forwardToViewUdoc.addParameter("udocID", frm.getIdUdoc());
		setReturnActionFordward(request, forwardToViewUdoc);

	}

	ActionForward getForwardVolverCotejoConRedirect(ActionForm form,
			HttpServletRequest request) {
		RelacionEntregaPO relacionEntregaPO = (RelacionEntregaPO) getFromTemporalSession(
				request, TransferenciasConstants.RELACION_KEY);
		String urlParaVolverDeLaUdoc = getURLVolverACotejo(request,
				relacionEntregaPO.getId());
		ActionForward volverForw = new ActionForward();
		volverForw.setPath(urlParaVolverDeLaUdoc);
		volverForw.setRedirect(true);
		return volverForw;
	}

	String getURLVolverACotejo(HttpServletRequest request,
			String idRelacionEntrega) {
		return request.getRequestURI().substring(
				request.getContextPath().length())
				+ "?method=volveracotejo&codigoseleccionada="
				+ idRelacionEntrega;
	}

	public void volveracotejoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// preparaDatosParaVista(form,request);
		setReturnActionFordward(request,
				listadoCajas(mappings, form, request, response));
	}

	public void guardarestadocajasCodeLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		CotejoysignaturizacionForm frm = (CotejoysignaturizacionForm) form;
		/* RelacionEntregaVO relacionEntrega = (RelacionEntregaVO) */getFromTemporalSession(
				request, TransferenciasConstants.RELACION_KEY);
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionRelacionesEntregaBI serviceRelaciones = services
				.lookupGestionRelacionesBI();
		InfoAsignacionUdocs infoAsignacionUdocs = (InfoAsignacionUdocs) getFromTemporalSession(
				request, TransferenciasConstants.ASIGNACION_UDOC2UI);
		// actualizar unidadesInstalcion
		boolean huboCambios = updateUnidadesInstalacionInSession(
				infoAsignacionUdocs.getUdocsXui(), frm);
		if (huboCambios) {
			serviceRelaciones.guardarCotejo(frm.getCodigoseleccionada(),
					infoAsignacionUdocs.getUdocsXui());
		}
		preparaDatosParaVista(form, request);
	}

	ArrayList getCajasAbiertasFromFRM(CotejoysignaturizacionForm frm) {
		ArrayList cajas = null;
		String[] cajasAbiertas = frm.getCajasabiertas();
		if (cajasAbiertas != null) {
			cajas = new ArrayList();
			for (int i = 0; i < cajasAbiertas.length; i++) {
				if (cajasAbiertas[i].length() > 0)
					cajas.add(cajasAbiertas[i]);
			}
		}
		return cajas;
	}

	private boolean esParteCambiada(String[] idsPartesCambiadas,
			IParteUnidadDocumentalVO idAComprobar) {
		StringBuffer buff = new StringBuffer(idAComprobar.getIdUnidadDoc())
				.append(":").append(idAComprobar.getNumParteUdoc());
		for (int i = 0; i < idsPartesCambiadas.length; i++) {
			if (idsPartesCambiadas[i].equalsIgnoreCase(buff.toString()))
				return true;
		}
		return false;
	}

	private boolean esUInstalacionCambiada(String[] idsUInstalacionCambiadas,
			String idAUInstalacionComprobar) {
		for (int i = 0; i < idsUInstalacionCambiadas.length; i++) {
			if (idsUInstalacionCambiadas[i]
					.equalsIgnoreCase(idAUInstalacionComprobar.toString()))
				return true;
		}
		return false;
	}

	private boolean updateUnidadesInstalacionInSession(Map udocsXui,
			CotejoysignaturizacionForm frm) {
		// String id = null;
		// String estadoCotejo;
		String[] partesCambiadas = frm.getPartescambiadas();
		String[] uinstalacionCambiadas = frm.getCajascambiadas();
		boolean hayPartesCambiadas = frm.hayPartesCambiadas();
		boolean hayCajasCambiadas = frm.hayUInstalacionCambiadas();
		boolean hayCambios = false;
		hayCambios = (hayPartesCambiadas || hayCajasCambiadas);
		if (hayCambios) {
			// partesUDocsCambiadas = new ArrayList();
			Iterator itUInstalacion = udocsXui.keySet().iterator();
			while (itUInstalacion.hasNext()) {
				UnidadInstalacionVO uInstalacion = (UnidadInstalacionVO) itUInstalacion
						.next();
				if (hayPartesCambiadas) {
					Collection partesUDocs = (Collection) udocsXui
							.get(uInstalacion);
					for (Iterator itUDocs = partesUDocs.iterator(); itUDocs
							.hasNext();) {
						ParteUnidadDocumentalVO parteUdoc = (ParteUnidadDocumentalVO) itUDocs
								.next();
						if (esParteCambiada(partesCambiadas, parteUdoc)) {
							// partesUDocsCambiadas.add(parteUdoc);
							parteUdoc.setFlagChanged(true);
							parteUdoc
									.setEstadoCotejo(Integer.parseInt(frm
											.getEstadocotejounidaddocumental(parteUdoc)));
							parteUdoc.setNotasCotejo(frm
									.getObservacioneserror(parteUdoc));
						}
					}
				}
				// establecer el valor del flag devolver e indicar si la caja a
				// sufrido cambios en este flag
				String numeroCaja = Integer.toString(uInstalacion.getOrden());
				if (esUInstalacionCambiada(uinstalacionCambiadas,
						Integer.toString(uInstalacion.getOrden()))) {
					uInstalacion.setChanged(true);
					boolean devolver = frm
							.getDevolverunidaddocumental(numeroCaja) != null ? true
							: false;
					uInstalacion.setDevolver(devolver);
					uInstalacion.setNotasCotejo(frm
							.getObservacionescaja(numeroCaja));
				}
				/*
				 * el estado de la caja no se actualiza en la vista => ya lo
				 * hace el servicio comprobando las partes!!
				 */
			}
		}
		return hayCambios;
	}

	public void guardarfinalizarcotejoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		CotejoysignaturizacionForm frm = (CotejoysignaturizacionForm) form;
		// String codigoRelacionSeleccionada = frm.getCodigoseleccionada();
		ActionErrors errors = null;
		try {
			errors = frm
					.validate(CotejoysignaturizacionForm.VALIDA_GUARDAR_FINALIZAR_COTEJO);
			if (errors == null)
				codeLogicGuardarFinalizarCotejo(mappings, form, request,
						response);
			else {
				ErrorsTag.saveErrors(request, errors);
				setReturnActionFordward(request,
						listadoCajas(mappings, form, request, response));
			}
		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
			setReturnActionFordward(request,
					listadoCajas(mappings, form, request, response));
		}
	}

	void codeLogicGuardarFinalizarCotejo(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ActionNotAllowedException {
		// CotejoysignaturizacionForm frm = (CotejoysignaturizacionForm) form;
		// String codigoRelacionSeleccionada = frm.getCodigoseleccionada();
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionRelacionesEntregaBI serviceRelaciones = services
				.lookupGestionRelacionesBI();
		RelacionEntregaVO relacionEntrega = (RelacionEntregaVO) getFromTemporalSession(
				request, TransferenciasConstants.RELACION_KEY);
		// serviceRelaciones.finalizarCotejo(codigoRelacionSeleccionada,
		// frm.getDepositoseleccionado(),
		// relacionEntrega.getUnidadesInstalacion());
		// serviceRelaciones.finalizarCotejo(relacionEntrega,
		// frm.getDepositoseleccionado());
		InfoAsignacionUdocs infoAsignacionUdocs = (InfoAsignacionUdocs) getFromTemporalSession(
				request, TransferenciasConstants.ASIGNACION_UDOC2UI);
		serviceRelaciones.finalizarCotejo(relacionEntrega,
				infoAsignacionUdocs.getUdocsXui());
		// actualizar la relacion q aparece en la cabecera!!!!!!!
		((RelacionEntregaVO) getFromTemporalSession(request,
				TransferenciasConstants.RELACION_KEY))
				.setEstado(relacionEntrega.getEstado());
		// muestro un listado de cajas(ya estan en session actualziadas)
		ActionRedirect forwardToViewRelacion = new ActionRedirect(
				mappings.findForward("redirect_to_view_relacion"));
		forwardToViewRelacion.setRedirect(true);
		forwardToViewRelacion.addParameter("idRelacionSeleccionada",
				relacionEntrega.getId());
		setReturnActionFordward(request, forwardToViewRelacion);

		// Antes se redireccionaba al informe de cotejo en HTML. Ahora
		// directamente se envia a ver la relación
		// redirectInformeFinalizarCotejoMethod(mappings, form,request,
		// response));
	}

	/*
	 * private ActionForward redirectInformeFinalizarCotejoMethod(ActionMapping
	 * mappings, ActionForm form, HttpServletRequest request,
	 * HttpServletResponse response) { ActionForward ret = new ActionForward();
	 * ret.setPath("/action" + mappings.getPath() +
	 * "?method=informefinalizarcotejo"); ret.setRedirect(true); return ret; }
	 */

	/*
	 * private ActionForward redirectFinalizarCotejoMethod(ActionMapping
	 * mappings, ActionForm form, HttpServletRequest request,
	 * HttpServletResponse response, String idRelacion) { ActionForward ret =
	 * new ActionForward(); ret.setPath("/action" + mappings.getPath() +
	 * "?method=finalizarcotejo&codigoseleccionada=" + idRelacion);
	 * ret.setRedirect(true); return ret; }
	 */

	/*
	 * public void informefinalizarcotejoExecuteLogic(ActionMapping mappings,
	 * ActionForm form, HttpServletRequest request, HttpServletResponse
	 * response) { // muestro un listado de cajas(ya estan en session
	 * actualziadas) setReturnActionFordward(request,
	 * tileInformeFinalizarCotejo(mappings, form, request, response)); }
	 */

	public void finalizarcotejoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		CotejoysignaturizacionForm frm = (CotejoysignaturizacionForm) form;
		// boolean sePermiteModificarDeposito = false;
		// boolean flagError = false;
		// ActionErrors errors = null;
		try {
			ServiceRepository services = ServiceRepository
					.getInstance(ServiceClient.create(getAppUser(request)));
			GestionRelacionesEntregaBI serviceRelaciones = services
					.lookupGestionRelacionesBI();
			RelacionEntregaVO relacionEntrega = (RelacionEntregaVO) getFromTemporalSession(
					request, TransferenciasConstants.RELACION_KEY);
			if (relacionEntrega == null) {
				// String codigoRelacionSeleccionada =
				// frm.getCodigoseleccionada();
				/* RelacionEntregaVO relacionVO = */serviceRelaciones
						.getRelacionXIdRelacion(frm.getCodigoseleccionada());
			}
			InfoAsignacionUdocs infoAsignacionUdocs = (InfoAsignacionUdocs) getFromTemporalSession(
					request, TransferenciasConstants.ASIGNACION_UDOC2UI);

			// Establecer el modo de corrección
			relacionEntrega
					.setCorreccionenarchivo(frm.getCorreccionenarchivo());

			serviceRelaciones.finalizarCotejo(relacionEntrega,
					infoAsignacionUdocs.getUdocsXui());
			ClientInvocation lastClientInvocation = getInvocationStack(request)
					.goBackClientInvocation(request);
			setReturnActionFordward(request, new ActionForward(
					lastClientInvocation.getInvocationURI(), true));
		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
			setReturnActionFordward(request,
					listadoCajas(mappings, form, request, response));
		}
	}

	public void imprimircartelaExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		CotejoysignaturizacionForm frm = (CotejoysignaturizacionForm) form;
		guardarestadocajasCodeLogic(mappings, form, request, response);
		saveCurrentInvocation(
				KeysClientsInvocations.TRANSFERENCIAS_IMPRESION_CARTELAS,
				request);
		setReturnActionFordward(request, imprimirCartela(mappings));
		RelacionEntregaVO relacionEntrega = (RelacionEntregaVO) getFromTemporalSession(
				request, TransferenciasConstants.RELACION_KEY);
		InfoAsignacionUdocs infoAsignacion = (InfoAsignacionUdocs) getFromTemporalSession(
				request, TransferenciasConstants.ASIGNACION_UDOC2UI);
		List unidadesInstalacion = infoAsignacion.getUnidadesInstalacion();
		UnidadInstalacionVO caja = (UnidadInstalacionVO) unidadesInstalacion
				.get(Integer.parseInt(frm.getNumcajaseleccionada()) - 1);
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionFondosBI serviceFondos = services.lookupGestionFondosBI();
		FondoVO fondoVO = serviceFondos.getFondoXId(relacionEntrega
				.getIdfondodestino());
		request.setAttribute(TransferenciasConstants.CODIGO_FONDO_RELACION,
				fondoVO.getCodigo());
		request.setAttribute(TransferenciasConstants.CAJA_KEY, caja);
	}

	public void verUDocsElectronicasExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		// Información del formulario
		CotejoysignaturizacionForm frm = (CotejoysignaturizacionForm) form;
		ServiceRepository service = ServiceRepository.getInstance(ServiceClient
				.create(getAppUser(request)));
		GestionRelacionesEntregaBI relacionesBI = service
				.lookupGestionRelacionesBI();

		// Añadir los parámetros del formulario al enlace
		getInvocationStack(request).getLastClientInvocation().addParameters(
				frm.getMap());

		// Enlace en la barra de navegación
		saveCurrentInvocation(
				KeysClientsInvocations.TRANSFERENCIAS_UDOC_ELECTRONICAS_A_COTEJAR,
				request);

		RelacionEntregaVO relacionVO = relacionesBI.getRelacionXIdRelacion(frm
				.getCodigoseleccionada());
		setInTemporalSession(request, TransferenciasConstants.RELACION_KEY,
				RelacionToPO.getInstance(getServiceRepository(request))
						.transform(relacionVO));

		List udocsElectronicas = null;

		if (relacionVO.isEntreArchivos()) {
			udocsElectronicas = relacionesBI
					.getUDocsElectronicasByIdRelacionEntreArchivos(frm
							.getCodigoseleccionada());
		} else {
			udocsElectronicas = relacionesBI
					.getUDocsElectronicasByIdRelacion(frm
							.getCodigoseleccionada());
		}

		setInTemporalSession(request,
				TransferenciasConstants.LISTA_UDOCS_ELECTRONICAS_COTEJO_KEY,
				udocsElectronicas);

		setReturnActionFordward(request,
				mappings.findForward("udocs_eletronicas_cotejo"));

	}

	public void guardarCotejoUDocsElectronicasExecuteLogic(
			ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		CotejoysignaturizacionForm frm = (CotejoysignaturizacionForm) form;

		getGestionRelacionesBI(request).guardarCotejoUDocsElectronicas(
				frm.getCodigoseleccionada(),
				frm.getEstadocotejounidaddocumentales(),
				frm.getObservacioneserror());

		goBackExecuteLogic(mappings, form, request, response);

	}

	public void guardarVerUnidadDocumentalElectronicaExecuteLogic(
			ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		CotejoysignaturizacionForm frm = (CotejoysignaturizacionForm) form;

		getGestionRelacionesBI(request).guardarCotejoUDocsElectronicas(
				frm.getCodigoseleccionada(),
				frm.getEstadocotejounidaddocumentales(),
				frm.getObservacioneserror());

		ActionRedirect forwardToViewUdoc = new ActionRedirect(
				mappings.findForward("redirect_to_view_udoc"));
		forwardToViewUdoc.setRedirect(true);
		forwardToViewUdoc.addParameter("udocID", frm.getIdUdoc());
		setReturnActionFordward(request, forwardToViewUdoc);
	}

	public void guardarVerUnidadDocumentalElectronicaEAExecuteLogic(
			ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		CotejoysignaturizacionForm frm = (CotejoysignaturizacionForm) form;

		getGestionRelacionesBI(request).guardarCotejoUDocsElectronicas(
				frm.getCodigoseleccionada(),
				frm.getEstadocotejounidaddocumentales(),
				frm.getObservacioneserror());

		ActionRedirect forwardToViewUdoc = new ActionRedirect(
				mappings.findForward("verUnidadDocumental"));
		forwardToViewUdoc.setRedirect(true);
		forwardToViewUdoc.addParameter("unidadDocumental", frm.getIdUdoc());
		setReturnActionFordward(request, forwardToViewUdoc);
	}

	public void marcarUDocsElectronicaConErroresCotejoExecuteLogic(
			ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		String[] ids = request.getParameterValues("udocElectronicaRevisada");
		if (!ArrayUtils.isEmpty(ids)) {
			// Relación de entrega
			RelacionEntregaVO relacionEntrega = (RelacionEntregaVO) getFromTemporalSession(
					request, TransferenciasConstants.RELACION_KEY);

			// Modificar el estado de las unidades de instalación
			getGestionRelacionesBI(request).updateEstadoUDocsElectronicas(
					relacionEntrega.getId(), ids,
					EstadoCotejo.ERRORES.getIdentificador());
		}

		goLastClientExecuteLogic(mappings, form, request, response);

	}

	public void marcarUDocElectronicaRevisadaExecuteLogic(
			ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		String[] ids = request.getParameterValues("udocElectronicaRevisada");
		if (!ArrayUtils.isEmpty(ids)) {
			// Relación de entrega
			RelacionEntregaVO relacionEntrega = (RelacionEntregaVO) getFromTemporalSession(
					request, TransferenciasConstants.RELACION_KEY);

			// Modificar el estado de las unidades de instalación
			getGestionRelacionesBI(request).updateEstadoUDocsElectronicas(
					relacionEntrega.getId(), ids,
					EstadoCotejo.REVISADA.getIdentificador());
		}

		goLastClientExecuteLogic(mappings, form, request, response);

	}

	public ActionForward imprimirCartela(ActionMapping mappings) {
		return mappings.findForward("imprimircartela");
	}

	public ActionForward listadoCajas(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return mappings.findForward("cotejo");
	}

	public ActionForward finalizarCotejo(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		return mappings.findForward("finalizarcotejo");
	}

	public ActionForward tileInformeFinalizarCotejo(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		return mappings.findForward("informefinalizarcotejo");
	}

}