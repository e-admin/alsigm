package deposito.actions.reubicacion;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import se.usuarios.ServiceClient;
import solicitudes.prestamos.vos.DetallePrestamoVO;
import util.ErrorsTag;

import common.Constants;
import common.MotivoEliminacionUnidadInstalacion;
import common.actions.BaseAction;
import common.bi.ServiceRepository;
import common.exceptions.ActionNotAllowedException;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;
import common.util.ActionForwardUtils;
import common.util.ArrayUtils;
import common.util.ListUtils;
import common.util.StringUtils;

import deposito.DepositoConstants;
import deposito.MarcaUtilUI;
import deposito.actions.ErrorKeys;
import deposito.actions.asignable.ElementoAsignablePO;
import deposito.actions.hueco.HuecoPO;
import deposito.actions.hueco.UdocEnUIPO;
import deposito.actions.hueco.UdocEnUIToPO;
import deposito.forms.ReubicacionUnidadesDocumentalesForm;
import deposito.model.DepositoException;
import deposito.model.GestorEstructuraDepositoBI;
import deposito.vos.DepositoVO;
import deposito.vos.ElementoAsignableVO;
import deposito.vos.FormatoHuecoVO;
import deposito.vos.HuecoID;
import deposito.vos.HuecoVO;
import deposito.vos.UDocEnUiDepositoVO;
import deposito.vos.UInsDepositoVO;

public class ReubicacionUnidadesDocumentales extends BaseAction {

	public void initReubicarUDocsExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ClientInvocation cli = getInvocationStack(request)
				.getLastClientInvocation();
		cli.setAsReturnPoint(true);

		// borrado del form
		ReubicacionUnidadesDocumentalesForm moverUdocsForm = (ReubicacionUnidadesDocumentalesForm) form;
		String idUnidadInstalacionOrigen = moverUdocsForm
				.getIdUinstalacionOrigen();

		GestorEstructuraDepositoBI serviceEstructura = getGestorEstructuraDepositoBI(request);
		UInsDepositoVO uinsDepositoVO = serviceEstructura
				.getUinsEnDeposito(idUnidadInstalacionOrigen);

		if (MarcaUtilUI.isUnidadInstalacionBloqueada(uinsDepositoVO
				.getMarcasBloqueo())) {
			ActionErrors errors = new ActionErrors();
			errors.add(
					ErrorKeys.ERROR_UINSTALACION_BLOQUEADA_NO_COMPACTAR,
					new ActionError(
							ErrorKeys.ERROR_UINSTALACION_BLOQUEADA_NO_COMPACTAR));
			ErrorsTag.saveErrors(request, errors);
			setReturnActionFordward(request,
					mappings.findForward("listado_udocs"));
			return;
		}

		moverUdocsForm.resetInInit();

		setReturnActionFordward(request,
				ActionForwardUtils.getActionForward(request, "seleccionUDocs"));

	}

	/**
	 * Método Inicial para reubicar desde las Relaciones entre Archivos.
	 * 
	 * @param mappings
	 * @param form
	 * @param request
	 * @param response
	 */
	public void initReubicarUDocsEAExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		removeInTemporalSession(request,
				DepositoConstants.REUBICACION_FINALIZADA);

		ClientInvocation cli = getInvocationStack(request)
				.getLastClientInvocation();
		cli.setAsReturnPoint(true);

		// borrado del form
		ReubicacionUnidadesDocumentalesForm moverUdocsForm = (ReubicacionUnidadesDocumentalesForm) form;
		String idUnidadInstalacionOrigen = moverUdocsForm
				.getIdUinstalacionOrigen();

		GestorEstructuraDepositoBI serviceEstructura = getGestorEstructuraDepositoBI(request);
		UInsDepositoVO uinsDepositoVO = serviceEstructura
				.getUinsEnDeposito(idUnidadInstalacionOrigen);

		String huecoID = request.getParameter("idHuecoOrigen");
		String[] partes = huecoID.split(Constants.DELIMITER_IDS);
		String pIdAsignable = null;
		if (partes.length == 2) {
			pIdAsignable = partes[0];
		}

		removeInTemporalSession(request, DepositoConstants.LISTA_HUECOS_KEY);
		ElementoAsignableVO asignable = serviceEstructura
				.getElementoAsignable(pIdAsignable);
		List listaHuecos = serviceEstructura.getHuecos2(pIdAsignable);

		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		setInTemporalSession(request, DepositoConstants.ELEMENTO_ASIGNABLE_KEY,
				new ElementoAsignablePO(asignable, services));

		setInTemporalSession(request, DepositoConstants.LISTA_HUECOS_KEY,
				listaHuecos);

		if (MarcaUtilUI.isUnidadInstalacionBloqueada(uinsDepositoVO
				.getMarcasBloqueo())) {
			ActionErrors errors = new ActionErrors();
			errors.add(
					ErrorKeys.ERROR_UINSTALACION_BLOQUEADA_NO_COMPACTAR,
					new ActionError(
							ErrorKeys.ERROR_UINSTALACION_BLOQUEADA_NO_COMPACTAR));
			ErrorsTag.saveErrors(request, errors);
			setReturnActionFordward(request,
					mappings.findForward("listado_udocs"));
			return;
		}

		moverUdocsForm.resetInInit();

		setReturnActionFordward(request,
				ActionForwardUtils.getActionForward(request, "seleccionUDocs"));

	}

	public void seleccionUDocsExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		removeInTemporalSession(request,
				DepositoConstants.REUBICACION_FINALIZADA);

		GestorEstructuraDepositoBI depositoService = getGestionDespositoBI(request);
		ServiceRepository services = getServiceRepository(request);

		ReubicacionUnidadesDocumentalesForm frm = (ReubicacionUnidadesDocumentalesForm) form;
		String idHueco = frm.getIdHuecoOrigen();

		HuecoID huecoID = ReubicacionUnidadesDocumentalesForm
				.getHuecoID(idHueco);
		HuecoVO huecoVO = depositoService.getInfoHueco(huecoID);

		removeInTemporalSession(request,
				DepositoConstants.LISTA_UDOCS_POSIBLES_A_REUBICAR_KEY);
		List udocsHueco = depositoService.getUDocsValidadasEnUInstalacion(frm
				.getIdUinstalacionOrigen());

		HuecoPO huecoEntity = new HuecoPO(huecoVO, services);
		CollectionUtils.transform(udocsHueco,
				UdocEnUIToPO.getInstance(services));
		setInTemporalSession(request,
				DepositoConstants.HUECO_ORIGEN_COMPACTAR_KEY, huecoEntity);
		setInTemporalSession(request,
				DepositoConstants.LISTA_UDOCS_POSIBLES_A_REUBICAR_KEY,
				udocsHueco);

		saveCurrentInvocation(
				KeysClientsInvocations.DEPOSITO_SELECCION_UDOCS_A_REUBICAR,
				request);

		setReturnActionFordward(request,
				mappings.findForward("seleccion_udocs"));
	}

	/**
	 * Obtiene el mensaje informativo de que determinadas unidades documentales
	 * no se pueden compactar
	 * 
	 * @param udocs
	 *            Lista de {@link UdocEnUIPO}
	 * @param idsUdocsNoDisponibles
	 *            , Lista de {@link DetallePrestamoVO}
	 * @return String, mensaje informativo.
	 */
	private String obtenerMensajeDeUnidadesNoDisponibles(List udocs,
			List idsUdocsNoDisponibles) {
		String uDocsMessage = "";
		if (!ListUtils.isEmpty(udocs)
				&& !ListUtils.isEmpty(idsUdocsNoDisponibles)) {
			for (int i = 0; i < udocs.size(); i++) {
				UdocEnUIPO udocEnUIPO = (UdocEnUIPO) udocs.get(i);
				for (int j = 0; j < idsUdocsNoDisponibles.size(); j++) {
					DetallePrestamoVO detallePrestamoVO = (DetallePrestamoVO) idsUdocsNoDisponibles
							.get(j);
					if (udocEnUIPO.getIdunidaddoc().equalsIgnoreCase(
							detallePrestamoVO.getIdudoc())) {
						uDocsMessage += StringUtils.addCharacterAtLeft(
								String.valueOf(i + 1), 3, "0")
								+ "; ";
						break;
					}
				}
			}
		}
		return uDocsMessage;
	}

	public ActionErrors validateSeleccionUDocs(HttpServletRequest request,
			ActionForm form) {
		ActionErrors errors = new ActionErrors();

		/* List udocsTotalesEnUI =(List) */getFromTemporalSession(request,
				DepositoConstants.LISTA_UDOCS_POSIBLES_A_REUBICAR_KEY);
		// int numUdocsTotalesEnUI = 0;
		// if(!util.CollectionUtils.isEmpty(udocsTotalesEnUI))
		// numUdocsTotalesEnUI = udocsTotalesEnUI.size();

		ReubicacionUnidadesDocumentalesForm frm = (ReubicacionUnidadesDocumentalesForm) form;
		if (frm.getIdsUdocsSeleccionadas() == null
				|| frm.getIdsUdocsSeleccionadas().length == 0) {
			errors.add(Constants.ERROR_NOT_SELECTION, new ActionError(
					Constants.ERROR_NOT_SELECTION));
		} else {
			// comprobar posiciones consecutivas por atras
			int[] posUdocsSeleccionadas = frm.getPosUdocsSeleccionadas();

			List totalUdocsHueco = (List) getFromTemporalSession(request,
					DepositoConstants.LISTA_UDOCS_POSIBLES_A_REUBICAR_KEY);
			int[] totalPosUdocsHueco = new int[totalUdocsHueco.size()];
			int index = 0;
			for (Iterator itUdocsHueco = totalUdocsHueco.iterator(); itUdocsHueco
					.hasNext();) {
				UdocEnUIPO hueco = (UdocEnUIPO) itUdocsHueco.next();
				totalPosUdocsHueco[index] = hueco.getPosudocenui();
				index++;
			}

			// comrpobar consecutivas
			int indexTotalPosUdocs = totalPosUdocsHueco.length;
			int posicionCorrecta;
			for (int i = posUdocsSeleccionadas.length - 1; i >= 0; i--) {
				posicionCorrecta = totalPosUdocsHueco[--indexTotalPosUdocs];
				if (posUdocsSeleccionadas[i] != posicionCorrecta) {
					errors.add(
							ErrorKeys.ERROR_SOLO_ES_POSIBLE_SELECCIONAR_ULTIMAS_UDOCS,
							new ActionError(
									ErrorKeys.ERROR_SOLO_ES_POSIBLE_SELECCIONAR_ULTIMAS_UDOCS));
					break;
				}
			}
			if (errors.size() == 0) {
				List listaIdsUdocsSeleccionadas = Arrays.asList(frm
						.getIdsUdocsSeleccionadas());
				// List listaIdsUdocsSeleccionadas=new ArrayList();
				// for(int i=0;i<posUdocsSeleccionadas.length;i++)
				// {
				// UdocEnUIPO
				// udocEnUIPO=(UdocEnUIPO)totalUdocsHueco.get(posUdocsSeleccionadas[i]-1);
				// listaIdsUdocsSeleccionadas.add(udocEnUIPO.getIdunidaddoc());
				// }
				// if(!ListUtils.isEmpty(listaIdsUdocsSeleccionadas))
				// {
				GestorEstructuraDepositoBI depositoService = getGestionDespositoBI(request);
				int[] estados = Constants.ESTADOS_DETALLES_EN_PRESTAMOS;
				List uDocsNoDisponibles = depositoService
						.getUDocsEnPrestamoByEstado(listaIdsUdocsSeleccionadas,
								estados);
				if (!ListUtils.isEmpty(uDocsNoDisponibles)) {
					String uDocsMessage = obtenerMensajeDeUnidadesNoDisponibles(
							totalUdocsHueco, uDocsNoDisponibles);
					errors.add(
							ErrorKeys.ERROR_UDOCS_NO_DISPONIBLES_AL_REUBICAR,
							new ActionError(
									ErrorKeys.ERROR_UDOCS_NO_DISPONIBLES_AL_REUBICAR,
									uDocsMessage));
				}
				// }

			}
		}

		return errors.size() > 0 ? errors : null;
	}

	public ActionErrors validateSeleccionHueco(ActionForm form) {
		ActionErrors errors = new ActionErrors();
		ReubicacionUnidadesDocumentalesForm frm = (ReubicacionUnidadesDocumentalesForm) form;
		if (frm.getNumHuecoDestino() < 0)
			errors.add(Constants.ERROR_NOT_SELECTION, new ActionError(
					Constants.ERROR_NOT_SELECTION));
		return errors.size() > 0 ? errors : null;

	}

	public ActionErrors validateSeleccionAsignable(
			GestorEstructuraDepositoBI depositoService,
			ReubicacionUnidadesDocumentalesForm frm, int numUDocsAReubicar) {
		ActionErrors errors = new ActionErrors();

		if (GenericValidator.isBlankOrNull(frm.getElementoDestino()))
			errors.add(Constants.ERROR_NOT_SELECTION, new ActionError(
					Constants.ERROR_NOT_SELECTION));
		else {
			// comprobar que sea un asignable
			ElementoAsignableVO asignable = depositoService
					.getElementoAsignable(frm.getIdElementoDestino());
			if (asignable == null) {
				errors.add(
						Constants.ERROR_NECESARIO_SELECCIONAR_UN_ASIGNABLE,
						new ActionError(
								Constants.ERROR_NECESARIO_SELECCIONAR_UN_ASIGNABLE));

			} else {
				FormatoHuecoVO formatoHuecoDestino = depositoService
						.getFormatoHueco(asignable.getIdFormato());

				if (formatoHuecoDestino != null && numUDocsAReubicar > 0) {
					if (!formatoHuecoDestino.isMultidoc()
							&& numUDocsAReubicar > 1) {
						errors.add(
								Constants.ERROR_NO_ES_POSIBLE_REUBICAR_VARIAS_UDOCS_EN_FORMATO_NO_MULTIDOCUMENTO,
								new ActionError(
										Constants.ERROR_NO_ES_POSIBLE_REUBICAR_VARIAS_UDOCS_EN_FORMATO_NO_MULTIDOCUMENTO));
					}
				}
			}
		}

		return errors.size() > 0 ? errors : null;
	}

	public void seleccionarAsignableDestinoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		// Añadir los parámetros del formulario al enlace
		ClientInvocation cli = getInvocationStack(request)
				.getLastClientInvocation();
		cli.addParameters(((ReubicacionUnidadesDocumentalesForm) form).getMap());

		ActionErrors errors = validateSeleccionUDocs(request, form);
		if (errors == null) {

			saveCurrentInvocation(
					KeysClientsInvocations.DEPOSITO_SELECCION_ELEMENTO_DESTINO,
					request);

			// a la pagina del navegador
			setReturnActionFordward(request,
					mappings.findForward("seleccion_asignable_destino"));

		} else {
			// Añadir los errores al request
			obtenerErrores(request, true).add(errors);
			goLastClientExecuteLogic(mappings, form, request, response);
		}
	}

	public void seleccionarHuecoDestinoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		// Añadir los parámetros del formulario al enlace
		removeInTemporalSession(request,
				DepositoConstants.REUBICACION_FINALIZADA);

		ClientInvocation cli = getInvocationStack(request)
				.getLastClientInvocation();
		cli.addParameters(((ReubicacionUnidadesDocumentalesForm) form).getMap());

		ServiceRepository services = getServiceRepository(request);
		GestorEstructuraDepositoBI depositoBI = services
				.lookupGestorEstructuraDepositoBI();
		ReubicacionUnidadesDocumentalesForm reubicacionForm = (ReubicacionUnidadesDocumentalesForm) form;

		String[] udocsDestino = reubicacionForm.getIdsUdocsSeleccionadas();
		List udocsAReubicar = (List) getFromTemporalSession(request,
				DepositoConstants.LISTA_UDOCS_POSIBLES_A_REUBICAR_KEY);

		int nUDocs = (udocsDestino == null || udocsDestino.length == 0) ? 0
				: udocsDestino.length;

		// si no se selecciona un asignable el navegador volvera al ultimo
		// asignable seleccionado correctamente
		// (lo recoge de session en la jsp)

		// TODO: Revisar esto
		ActionErrors errors = validateSeleccionAsignable(depositoBI,
				reubicacionForm, nUDocs);
		if (errors == null) {

			// HuecoPO huecoPO = (HuecoPO)getFromTemporalSession(request,
			// DepositoConstants.HUECO_KEY);

			saveCurrentInvocation(
					KeysClientsInvocations.DEPOSITO_SELECCION_HUECO_DESTINO,
					request);

			// preparar vista para el navegador
			ElementoAsignableVO asignable = depositoBI
					.getElementoAsignable(reubicacionForm
							.getIdElementoDestino());

			// lista de huecos que pueden ser seleccionados

			if (!util.CollectionUtils.isEmpty(udocsAReubicar)) {
				String identificacionUdocs = ((UDocEnUiDepositoVO) udocsAReubicar
						.get(0)).getIdentificacion();
				List listaHuecos = depositoBI.getHuecosParaReubicarUdocs(
						identificacionUdocs,
						reubicacionForm.getIdElementoDestino());
				request.setAttribute(DepositoConstants.LISTA_HUECOS_KEY,
						listaHuecos);
			}

			// asignable destino
			setInTemporalSession(request,
					DepositoConstants.ELEMENTO_ASIGNABLE_DESTINO_KEY,
					new ElementoAsignablePO(asignable, services));

			request.setAttribute(
					DepositoConstants.EDITABLE_NUMERACION_KEY,
					new Boolean(depositoBI.isEditableNumeracion(asignable
							.getIddeposito())));

			// a la pagina del navegador
			setReturnActionFordward(request,
					mappings.findForward("seleccion_hueco_destino"));

		} else {
			// Añadir los errores al request
			obtenerErrores(request, true).add(errors);
			goLastClientExecuteLogic(mappings, form, request, response);
		}
	}

	public void informeMovimientoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		// Añadir los parámetros del formulario al enlace
		ClientInvocation cli = getInvocationStack(request)
				.getLastClientInvocation();
		cli.addParameters(((ReubicacionUnidadesDocumentalesForm) form).getMap());

		ReubicacionUnidadesDocumentalesForm reubicacionForm = (ReubicacionUnidadesDocumentalesForm) form;
		GestorEstructuraDepositoBI depositoBI = getGestionDespositoBI(request);

		// obtencion de unidades documentales a reubicar
		List udocsAReubicar = getGestionDespositoBI(request).getUDocsById(
				reubicacionForm.getIdsUdocsSeleccionadas(),
				reubicacionForm.getSignaturasUdocSeleccionadas());

		// obtencion del hueco seleccionado
		ElementoAsignablePO elementoAsignableDestino = (ElementoAsignablePO) getFromTemporalSession(
				request, DepositoConstants.ELEMENTO_ASIGNABLE_DESTINO_KEY);
		HuecoID huecoSeleccionadoID = new HuecoID(
				elementoAsignableDestino.getId(),
				reubicacionForm.getNumHuecoDestino());

		HuecoVO huecoVOdestino = depositoBI.getInfoHueco(huecoSeleccionadoID);

		try {

			// Comprobar si el hueco está libre, en este caso, no se realizará
			// la comprobación.
			if (!HuecoVO.LIBRE_STATE.equals(huecoVOdestino.getEstado())) {

				FormatoHuecoVO formatoHDestino = depositoBI
						.getFormatoHueco(huecoVOdestino.getIdformato());
				if (udocsAReubicar != null && udocsAReubicar.size() > 0) {
					depositoBI.validateHuecoDestinoParaReubicarUdoc(
							huecoSeleccionadoID,
							(UDocEnUiDepositoVO) udocsAReubicar.get(0),
							formatoHDestino);
				}
			}

			ActionErrors errors = validateSeleccionHueco(form);
			if (errors == null) {

				saveCurrentInvocation(
						KeysClientsInvocations.DEPOSITO_ACEPTAR_REUBICACION_UDOCS,
						request);

				// lista de huecos seleccionados
				List listaHuecos = depositoBI
						.getHuecos(new HuecoID[] { huecoSeleccionadoID });
				if (!util.CollectionUtils.isEmpty(listaHuecos)) {
					HuecoPO huecoPO = new HuecoPO((HuecoVO) listaHuecos.get(0),
							getServiceRepository(request));
					setInTemporalSession(request,
							DepositoConstants.HUECO_DESTINO_SELECCIONADO_KEY,
							huecoPO);
				}

				// lista de udocs seleccionadas para reubicar
				CollectionUtils
						.transform(udocsAReubicar, UdocEnUIToPO
								.getInstance(getServiceRepository(request)));
				setInTemporalSession(request,
						DepositoConstants.DEPOSITO_UDOCS_A_REUBICAR,
						udocsAReubicar);

				setReturnActionFordward(request,
						mappings.findForward("informe_reubicacion"));

			} else {
				// Añadir los errores al request
				obtenerErrores(request, true).add(errors);
				goLastClientExecuteLogic(mappings, form, request, response);
			}

		} catch (DepositoException e) {
			guardarError(request, e);
			goLastClientExecuteLogic(mappings, form, request, response);
		}

	}

	public void aceptarMoverExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		// String idUInstalacionDestino = null;
		GestorEstructuraDepositoBI depositoService = getGestionDespositoBI(request);

		List unidadesDocumentales = (List) getFromTemporalSession(request,
				DepositoConstants.DEPOSITO_UDOCS_A_REUBICAR);
		HuecoPO huecoOrigenPO = (HuecoPO) getFromTemporalSession(request,
				DepositoConstants.HUECO_ORIGEN_COMPACTAR_KEY);
		HuecoPO huecoDestinoPO = (HuecoPO) getFromTemporalSession(request,
				DepositoConstants.HUECO_DESTINO_SELECCIONADO_KEY);
		List unidadesDocumentalesDestino = (List) getFromTemporalSession(
				request, DepositoConstants.DEPOSITO_UDOCS_REUBICADAS);
		FormatoHuecoVO formatoHuecoDestino = depositoService
				.getFormatoHueco(huecoDestinoPO.getIdformato());

		DepositoVO ubicacion = getGestorEstructuraDepositoBI(request)
				.getUbicacion(huecoOrigenPO.getIddeposito());
		Boolean reubicacionFinalizada = (Boolean) getFromTemporalSession(
				request, DepositoConstants.REUBICACION_FINALIZADA);

		if (reubicacionFinalizada == null) {
			reubicacionFinalizada = new Boolean(false);
		}

		try {

			// huecoDestinoPO = (HuecoPO)getFromTemporalSession(request,
			// DepositoConstants.HUECO_DESTINO_SELECCIONADO_KEY);

			if (!reubicacionFinalizada.booleanValue()) {

				ReubicacionUnidadesDocumentalesForm frm = (ReubicacionUnidadesDocumentalesForm) form;

				ActionErrors errors = validateSeleccionAsignable(
						depositoService, frm, unidadesDocumentales.size());
				if (errors == null || errors.size() == 0) {

					// if(!reubicacionFinalizada.booleanValue()){
					List listaUDocs = depositoService
							.reubicarUnidadesDocumentales(
									ubicacion.getIdArchivo(),
									unidadesDocumentales,
									huecoDestinoPO.getIduinstalacion(),
									huecoDestinoPO.getHuecoID(),
									formatoHuecoDestino,
									MotivoEliminacionUnidadInstalacion.COMPACTACION_UNIDADES_DOCUMENTALES);

					// setInTemporalSession(request,
					// DepositoConstants.REUBICACION_FINALIZADA, new
					// Boolean(true));

					String[] idsUdocs = new String[0];
					String[] signaturas = new String[0];

					ListIterator it = listaUDocs.listIterator();

					while (it.hasNext()) {
						UDocEnUiDepositoVO udoc = (UDocEnUiDepositoVO) it
								.next();
						idsUdocs = (String[]) ArrayUtils.add(idsUdocs,
								udoc.getIdunidaddoc());
						signaturas = (String[]) ArrayUtils.add(signaturas,
								udoc.getSignaturaudoc());
					}

					// Obtener las unidades documentales destino
					unidadesDocumentalesDestino = depositoService.getUDocsById(
							idsUdocs, signaturas);

					CollectionUtils
							.transform(
									unidadesDocumentalesDestino,
									UdocEnUIToPO
											.getInstance(getServiceRepository(request)));

					// setInTemporalSession(request,
					// DepositoConstants.DEPOSITO_UDOCS_REUBICADAS,
					// unidadesDocumentalesDestino);

					// Leer los datos del hueco Destino
					HuecoVO huecoDestinoVO = depositoService
							.getInfoHueco(huecoDestinoPO.getHuecoID());

					huecoDestinoPO = new HuecoPO(huecoDestinoVO,
							getServiceRepository(request));

					// setInTemporalSession(request,
					// DepositoConstants.HUECO_DESTINO_SELECCIONADO_KEY,
					// huecoPO);

					// TODO: intentar mejorar esto ....

					// ClientInvocation invLast =
					// getInvocationStack(request).getLastClientInvocation();
					ClientInvocation inv = getInvocationStack(request)
							.goToReturnPoint(request);
					getInvocationStack(request).saveClientInvocation(inv,
							request);
					// getInvocationStack(request).saveClientInvocation(invLast,request);

					saveCurrentInvocation(
							KeysClientsInvocations.DEPOSITO_INFORME_REUBICACION_UDOCS,
							request);

					setInTemporalSession(request,
							DepositoConstants.DEPOSITO_UDOCS_A_REUBICAR,
							unidadesDocumentales);
					setInTemporalSession(request,
							DepositoConstants.REUBICACION_FINALIZADA,
							new Boolean(true));
					setInTemporalSession(request,
							DepositoConstants.DEPOSITO_UDOCS_REUBICADAS,
							unidadesDocumentalesDestino);
					setInTemporalSession(request,
							DepositoConstants.HUECO_DESTINO_SELECCIONADO_KEY,
							huecoDestinoPO);
					setInTemporalSession(request,
							DepositoConstants.HUECO_ORIGEN_COMPACTAR_KEY,
							huecoOrigenPO);
					// }

					setReturnActionFordward(request,
							mappings.findForward("informe_reubicacion"));

					// goReturnPointExecuteLogic(mappings, form, request,
					// response);

				} else {
					obtenerErrores(request, true).add(errors);
					goLastClientExecuteLogic(mappings, form, request, response);
				}
			} else {

				saveCurrentInvocation(
						KeysClientsInvocations.DEPOSITO_INFORME_REUBICACION_UDOCS,
						request);

				setInTemporalSession(request,
						DepositoConstants.DEPOSITO_UDOCS_A_REUBICAR,
						unidadesDocumentales);
				setInTemporalSession(request,
						DepositoConstants.DEPOSITO_UDOCS_REUBICADAS,
						unidadesDocumentalesDestino);
				setInTemporalSession(request,
						DepositoConstants.HUECO_DESTINO_SELECCIONADO_KEY,
						huecoDestinoPO);
				setInTemporalSession(request,
						DepositoConstants.HUECO_ORIGEN_COMPACTAR_KEY,
						huecoOrigenPO);

				setReturnActionFordward(request,
						mappings.findForward("informe_reubicacion"));
			}

		} catch (DepositoException e) {
			guardarError(request, e);
			goLastClientExecuteLogic(mappings, form, request, response);
		} catch (ActionNotAllowedException e) {
			guardarError(request, e);
			goLastClientExecuteLogic(mappings, form, request, response);
		} catch (Exception ex) {
			logger.error(ex);

			ActionErrors errors = getErrors(request, true);
			errors.add(Constants.ERROR_COMPACTAR_GENERICO, new ActionError(
					Constants.ERROR_COMPACTAR_GENERICO, ex.getMessage()));

			ErrorsTag.saveErrors(request, errors);
			goLastClientExecuteLogic(mappings, form, request, response);
		}
	}

}
