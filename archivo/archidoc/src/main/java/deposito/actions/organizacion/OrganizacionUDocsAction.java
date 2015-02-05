package deposito.actions.organizacion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import se.usuarios.AppUser;
import se.usuarios.ServiceClient;
import solicitudes.prestamos.vos.DetallePrestamoVO;
import util.ErrorsTag;

import common.Constants;
import common.actions.BaseAction;
import common.bi.GestionCuadroClasificacionBI;
import common.bi.ServiceRepository;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;
import common.util.ActionForwardUtils;
import common.util.ListUtils;
import common.util.StringUtils;

import deposito.DepositoConstants;
import deposito.MarcaUtilUI;
import deposito.actions.hueco.HuecoPO;
import deposito.actions.hueco.UdocEnUIPO;
import deposito.actions.hueco.UdocEnUIToPO;
import deposito.forms.OrganizacionUDocsForm;
import deposito.forms.ReubicacionUnidadesDocumentalesForm;
import deposito.model.GestorEstructuraDepositoBI;
import deposito.vos.HuecoID;
import deposito.vos.HuecoVO;
import deposito.vos.UDocEnUiDepositoVO;
import deposito.vos.UInsDepositoVO;
import fondos.vos.ElementoCuadroClasificacionVO;

public class OrganizacionUDocsAction extends BaseAction {

	private static final int SUBIR = 1;
	private static final int BAJAR = 2;
	private static final int UNIR = 3;
	private static final int DIVIDIR = 4;

	/**
	 * Inicio de la pantalla para Organizar las Unidades Documentales
	 * 
	 * @param mappings
	 * @param form
	 * @param request
	 * @param response
	 */
	public void initOrganizarUDocsExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ClientInvocation cli = getInvocationStack(request)
				.getLastClientInvocation();
		cli.setAsReturnPoint(true);

		// Establecer la variable de Cambios sin guardar a false
		setInTemporalSession(request,
				DepositoConstants.HAY_CAMBIOS_SIN_GUARDAR, Boolean.FALSE);

		OrganizacionUDocsForm formulario = (OrganizacionUDocsForm) form;

		String idUnidadInstalacion = formulario.getIdUinstalacion();

		GestorEstructuraDepositoBI depositoBI = getGestorEstructuraDepositoBI(request);
		UInsDepositoVO uinsDepositoVO = depositoBI
				.getUinsEnDeposito(idUnidadInstalacion);
		ActionErrors errors = new ActionErrors();

		removeInTemporalSession(request, DepositoConstants.LISTA_UDOCS_EN_CAJA);
		List listaUdocs = depositoBI.getUDocsValidadasEnUInstalacion(formulario
				.getIdUinstalacion());
		setListUDocs(request, listaUdocs);

		if (MarcaUtilUI.isUnidadInstalacionBloqueada(uinsDepositoVO
				.getMarcasBloqueo())) {

			errors.add(
					Constants.ERROR_UINSTALACION_BLOQUEADA_NO_ORGANIZAR,
					new ActionError(
							Constants.ERROR_UINSTALACION_BLOQUEADA_NO_ORGANIZAR));
			ErrorsTag.saveErrors(request, errors);
			setReturnActionFordward(request,
					mappings.findForward("listado_udocs"));
			return;
		} else {
			// Comprobar que ninguna de las unidades documentales están en
			// préstamos

			List listaIdsUdocs = new ArrayList();

			Iterator it = listaUdocs.iterator();
			while (it.hasNext()) {
				UDocEnUiDepositoVO udoc = (UDocEnUiDepositoVO) it.next();
				listaIdsUdocs.add(udoc.getIdunidaddoc());
			}

			if (!ListUtils.isEmpty(listaIdsUdocs)) {
				GestorEstructuraDepositoBI depositoService = getGestionDespositoBI(request);
				int[] estados = Constants.ESTADOS_DETALLES_EN_PRESTAMOS;
				List uDocsNoDisponibles = depositoService
						.getUDocsEnPrestamoByEstado(listaIdsUdocs, estados);
				if (!ListUtils.isEmpty(uDocsNoDisponibles)) {
					String uDocsMessage = obtenerMensajeDeUnidadesNoDisponibles(
							listaUdocs, uDocsNoDisponibles);
					errors.add(Constants.ERROR_UDOCS_EN_PRESTAMOS,
							new ActionError(Constants.ERROR_UDOCS_EN_PRESTAMOS,
									uDocsMessage));
					ErrorsTag.saveErrors(request, errors);
					setReturnActionFordward(request,
							mappings.findForward("listado_udocs"));
					return;

				}
			}
		}

		formulario.resetInInit();

		setReturnActionFordward(request,
				ActionForwardUtils.getActionForward(request, "organizarUDocs"));
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
				UDocEnUiDepositoVO uDocEnUiDeposito = (UDocEnUiDepositoVO) udocs
						.get(i);
				for (int j = 0; j < idsUdocsNoDisponibles.size(); j++) {
					DetallePrestamoVO detallePrestamoVO = (DetallePrestamoVO) idsUdocsNoDisponibles
							.get(j);
					if (uDocEnUiDeposito.getIdunidaddoc().equalsIgnoreCase(
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

	public void organizarUDocsExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		GestorEstructuraDepositoBI depositoService = getGestionDespositoBI(request);
		ServiceRepository services = getServiceRepository(request);

		OrganizacionUDocsForm formulario = (OrganizacionUDocsForm) form;

		List listaUDocs = getListaUDocs(request);

		String idHueco = formulario.getIdHueco();
		HuecoID huecoID = ReubicacionUnidadesDocumentalesForm
				.getHuecoID(idHueco);
		HuecoVO huecoVO = depositoService.getInfoHueco(huecoID);

		HuecoPO huecoEntity = new HuecoPO(huecoVO, services);
		CollectionUtils.transform(listaUDocs,
				UdocEnUIToPO.getInstance(services));
		setInTemporalSession(request, DepositoConstants.HUECO_KEY, huecoEntity);

		saveCurrentInvocation(
				KeysClientsInvocations.DEPOSITO_UDOCS_A_ORGANIZAR, request);

		setReturnActionFordward(request,
				mappings.findForward("organizacion_udocs"));
	}

	/**
	 * Sube las Unidades Documentales especificadas.
	 * 
	 * @param mappings
	 * @param form
	 * @param request
	 * @param response
	 */
	public void subirUDocsExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		OrganizacionUDocsForm formulario = (OrganizacionUDocsForm) form;
		List listaUDocs = getListaUDocs(request);
		ActionErrors errors = comprobacionesBasicas(formulario, listaUDocs,
				SUBIR, request);

		if (errors != null && !errors.isEmpty()) {
			obtenerErrores(request, true).add(errors);
			formulario.setUdocsSeleccionadas(null);
		} else {
			int[] posiciones = formulario.getNuevasPosUdocsSeleccionadas();
			int posSelInicial = posiciones[0];
			int posElementoAnterior = posSelInicial - 1;
			int posSelFinal = posiciones[posiciones.length - 1];

			// Eliminar el elemento anterior de la lista
			UDocEnUiDepositoVO elementoAnterior = (UDocEnUiDepositoVO) listaUDocs
					.get(posElementoAnterior);

			// Eliminar el Elemento de la lista
			listaUDocs.remove(posElementoAnterior);

			// Añadir el elemento en la posición final
			listaUDocs.add(posSelFinal, elementoAnterior);

			// Para recuperar los elementos seleccionados
			for (int i = 0; i < posiciones.length; i++) {
				// Se resta 1 a cada posición
				posiciones[i]--;
			}

			String[] udocsSeleccionadas = formulario.getUdocsSelect(
					formulario.getIdsUdocsSeleccionadas(),
					formulario.getPosUdocsSeleccionadas(),
					formulario.getSignaturasUdocSeleccionadas(), posiciones);

			formulario.setUdocsSeleccionadas(udocsSeleccionadas);

			// Establecer la variable de Cambios sin guardar a true
			setInTemporalSession(request,
					DepositoConstants.HAY_CAMBIOS_SIN_GUARDAR, Boolean.TRUE);
		}

		setReturnActionFordward(request,
				mappings.findForward("organizacion_udocs"));

	}

	/**
	 * Baja las Unidades Documentales especificadas.
	 * 
	 * @param mappings
	 * @param form
	 * @param request
	 * @param response
	 */
	public void bajarUDocsExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		OrganizacionUDocsForm formulario = (OrganizacionUDocsForm) form;
		List listaUDocs = getListaUDocs(request);
		ActionErrors errors = comprobacionesBasicas(formulario, listaUDocs,
				BAJAR, request);

		if (errors != null && !errors.isEmpty()) {
			obtenerErrores(request, true).add(errors);
			formulario.setUdocsSeleccionadas(null);
		} else {
			int[] posiciones = formulario.getNuevasPosUdocsSeleccionadas();
			int posSelInicial = posiciones[0];
			int posSelFinal = posiciones[posiciones.length - 1];
			int posElementoPosterior = posSelFinal + 1;

			// Eliminar el elemento anterior de la lista
			UDocEnUiDepositoVO elementoPosterior = (UDocEnUiDepositoVO) listaUDocs
					.get(posElementoPosterior);

			// Eliminar el Elemento de la lista
			listaUDocs.remove(posElementoPosterior);

			// Añadir el elemento en la posición final
			listaUDocs.add(posSelInicial, elementoPosterior);

			// Para recuperar los elementos seleccionados
			for (int i = 0; i < posiciones.length; i++) {
				// Se suma 1 a cada posición
				posiciones[i]++;
			}

			String[] udocsSeleccionadas = formulario.getUdocsSelect(
					formulario.getIdsUdocsSeleccionadas(),
					formulario.getPosUdocsSeleccionadas(),
					formulario.getSignaturasUdocSeleccionadas(), posiciones);

			formulario.setUdocsSeleccionadas(udocsSeleccionadas);

			// Establecer la variable de Cambios sin guardar a true
			setInTemporalSession(request,
					DepositoConstants.HAY_CAMBIOS_SIN_GUARDAR, Boolean.TRUE);
		}

		setReturnActionFordward(request,
				mappings.findForward("organizacion_udocs"));
	}

	/**
	 * Une las partes de una unidad documental
	 * 
	 * @param mappings
	 * @param form
	 * @param request
	 * @param response
	 */
	public void unirPartesUDocExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		OrganizacionUDocsForm formulario = (OrganizacionUDocsForm) form;
		List listaUDocs = getListaUDocs(request);
		ActionErrors errors = comprobacionesBasicas(formulario, listaUDocs,
				UNIR, request);
		if (errors != null && !errors.isEmpty()) {
			obtenerErrores(request, true).add(errors);
			formulario.setUdocsSeleccionadas(null);
		} else {
			// Obtener cual es elemento del cuadro
			String[] idsSeleccionados = formulario.getIdsUdocsSeleccionadas();
			String[] signaturasSeleccionadas = formulario
					.getSignaturasUdocSeleccionadas();
			int[] posicionesSeleccionadas = formulario
					.getNuevasPosUdocsSeleccionadas();
			String codigoElementoCuadro = null;

			ServiceRepository servicio = ServiceRepository
					.getInstance(ServiceClient.create(getAppUser(request)));
			GestionCuadroClasificacionBI cuadroBI = servicio
					.lookupGestionCuadroClasificacionBI();

			ElementoCuadroClasificacionVO elemento = cuadroBI
					.getElementoCuadroClasificacion(idsSeleccionados[0]);

			if (elemento != null) {
				codigoElementoCuadro = elemento.getCodigo();
				List listaUDocsEliminadas = getListaUDocsEliminadas(request);

				// Por defecto el elemento que no se borra es el primero.
				int posicionElementoNoEliminar = posicionesSeleccionadas[0]; // Por
																				// defecto
																				// el
																				// primero
																				// seleccionado

				// Comprobar si en los seleccionados está el elemento del
				// cuadro, y actualizar su posición.
				for (int i = 0; i < idsSeleccionados.length; i++) {
					String signatura = signaturasSeleccionadas[i];
					if (signatura.equals(codigoElementoCuadro)) {
						posicionElementoNoEliminar = posicionesSeleccionadas[i]; // Se
																					// asigna
																					// la
																					// posición
																					// del
																					// elemento
																					// que
																					// está
																					// en
																					// el
																					// cuadro.
						break;
					}
				}

				// Eliminar los elementos de la lista.
				for (int i = posicionesSeleccionadas.length - 1; i >= 0; i--) {
					int posicion = posicionesSeleccionadas[i];
					if (posicionElementoNoEliminar != posicion) {
						UDocEnUiDepositoVO udoc = (UDocEnUiDepositoVO) listaUDocs
								.get(posicion);
						// Añadir el elemento a la lista de Eliminados
						listaUDocsEliminadas.add(udoc);
						listaUDocs.remove(posicion);
					}
				}

				setListUDocs(request, listaUDocs);
				setListUDocsEliminadas(request, listaUDocsEliminadas);

				// Establecer la variable de Cambios sin guardar a true
				setInTemporalSession(request,
						DepositoConstants.HAY_CAMBIOS_SIN_GUARDAR, Boolean.TRUE);

			}
		}
		setReturnActionFordward(request,
				mappings.findForward("organizacion_udocs"));
	}

	/**
	 * Une las partes de una unidad documental
	 * 
	 * @param mappings
	 * @param form
	 * @param request
	 * @param response
	 */
	public void dividirUDocExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		OrganizacionUDocsForm formulario = (OrganizacionUDocsForm) form;
		List listaUDocs = getListaUDocs(request);
		ActionErrors errors = comprobacionesBasicas(formulario, listaUDocs,
				DIVIDIR, request);
		if (errors != null && !errors.isEmpty()) {
			obtenerErrores(request, true).add(errors);
			formulario.setUdocsSeleccionadas(null);
		} else {
			int posicion = formulario.getNuevasPosUdocsSeleccionadas()[0];
			UDocEnUiDepositoVO udoc = (UDocEnUiDepositoVO) listaUDocs
					.get(posicion);
			UDocEnUiDepositoVO udocNueva = new UDocEnUiDepositoVO();
			PropertyUtils.copyProperties(udocNueva, udoc);
			// Insertar la unidad en la siguiente posicion;
			udocNueva.setSignaturaudoc(null);
			udocNueva.setPosudocenui(-1);

			listaUDocs.add(posicion + 1, udocNueva);

			// Establecer la variable de Cambios sin guardar a true
			setInTemporalSession(request,
					DepositoConstants.HAY_CAMBIOS_SIN_GUARDAR, Boolean.TRUE);

		}
		setReturnActionFordward(request,
				mappings.findForward("organizacion_udocs"));

	}

	private void checkBajarUltimo(HttpServletRequest request,
			OrganizacionUDocsForm formulario, int operacion, ActionErrors errors) {
		// si se quiere bajar el ultimo elemento (aunque haya otros
		// seleccionados)
		// mostrar error

		// obtener la lista de unidades/partes actual
		List listaUdocsActual = getListaUDocs(request);

		// obtener el id mayor de las filas seleccionadas
		int indexMayor = -1;
		if (formulario.getUdocsSeleccionadas() != null) {
			for (int i = 0; i < formulario.getUdocsSeleccionadas().length; i++) {
				int index = formulario.getNuevasPosUdocsSeleccionadas()[i];
				if (indexMayor < index)
					indexMayor = index;
			}
		}

		if (operacion == BAJAR && listaUdocsActual.size() - 1 <= indexMayor) {
			errors.add(
					Constants.ERRORS_RELACIONES_BAJAR_LAST_UDOC,
					new ActionError(Constants.ERRORS_RELACIONES_BAJAR_LAST_UDOC));
		}
	}

	/**
	 * Realiza las comprobaciones necesarias antes de subir o bajar elementos
	 * 
	 * @param formulario
	 *            Formulario de la pantalla
	 * @param listaUdocs
	 *            Lista de Unidades Documentales
	 * @param operacion
	 *            Operación a realizar
	 * @return Errores producidos
	 */
	private ActionErrors comprobacionesBasicas(
			OrganizacionUDocsForm formulario, List listaUDocs, int operacion,
			HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();

		checkBajarUltimo(request, formulario, operacion, errors);

		// Comprobar que los elementos son consecutivos
		boolean consecutivos = true;

		// Comprobar que se ha seleccionado algún registro
		if (formulario.getUdocsSeleccionadas() == null
				|| formulario.getUdocsSeleccionadas().length == 0) {
			errors.add(Constants.ERROR_NOT_SELECTION, new ActionError(
					Constants.ERROR_NOT_SELECTION));
		}

		if (operacion == DIVIDIR) {
			// Comprobar que se ha seleccionado solo una unidad
			if (errors.isEmpty()
					&& formulario.getUdocsSeleccionadas().length != 1) {
				errors.add(
						Constants.ERROR_DIVIDIR_SOLO_UNA_UNIDAD,
						new ActionError(Constants.ERROR_DIVIDIR_SOLO_UNA_UNIDAD));
			}
		} else {
			if (errors.isEmpty()) {
				int[] posiciones = formulario.getNuevasPosUdocsSeleccionadas();
				int posicion = posiciones[0];

				for (int i = 0; i < posiciones.length; i++) {
					if (posicion != posiciones[i]) {
						consecutivos = false;
						break;
					}
					posicion++;
				}

				if (!consecutivos) {

					if (operacion != UNIR) {
						errors.add(
								Constants.ERRORS_RELACIONES_MOVER_UDOCS_NO_CONSECUTIVAS,
								new ActionError(
										Constants.ERRORS_RELACIONES_MOVER_UDOCS_NO_CONSECUTIVAS));
					} else {
						errors.add(
								Constants.ERROR_NO_PERMITIR_UNIR_NO_CONSECUTIVAS,
								new ActionError(
										Constants.ERROR_NO_PERMITIR_UNIR_NO_CONSECUTIVAS));
					}
				} else {
					// Si la operación es bajar comprobar que no está
					// seleccionado
					// el último elemento

					switch (operacion) {
					case BAJAR:
						UDocEnUiDepositoVO udocUltima = (UDocEnUiDepositoVO) listaUDocs
								.get(listaUDocs.size() - 1);

						if (isSeleccionado(udocUltima,
								formulario.getUdocsSeleccionadas(),
								formulario.getNuevasPosUdocsSeleccionadas())) {
							errors.add(
									Constants.ERRORS_RELACIONES_BAJAR_LAST_UDOC,
									new ActionError(
											Constants.ERRORS_RELACIONES_BAJAR_LAST_UDOC));
						}
						break;// case BAJAR:

					case SUBIR:
						UDocEnUiDepositoVO udocPrimera = (UDocEnUiDepositoVO) listaUDocs
								.get(0);
						if (isSeleccionado(udocPrimera,
								formulario.getUdocsSeleccionadas(),
								formulario.getNuevasPosUdocsSeleccionadas())) {
							errors.add(
									Constants.ERRORS_RELACIONES_SUBIR_FIRST_UDOC,
									new ActionError(
											Constants.ERRORS_RELACIONES_SUBIR_FIRST_UDOC));
						}
						break;// case SUBIR:

					case UNIR:
						// Comprobar que todos los elementos seleccionados tiene
						// el
						// mismos código de unidad documental
						String[] idsUdocsSel = formulario
								.getIdsUdocsSeleccionadas();

						String idUdoc = idsUdocsSel[0];
						boolean iguales = true;
						if (idsUdocsSel.length > 1) {
							for (int i = 1; i < idsUdocsSel.length; i++) {
								if (!idUdoc.equals(idsUdocsSel[i])) {
									iguales = false;
									break;
								}
							}

							if (!iguales) {
								errors.add(
										Constants.ERROR_LAS_PARTES_NO_SON_IGUALES,
										new ActionError(
												Constants.ERROR_LAS_PARTES_NO_SON_IGUALES));
							}
						} else {
							errors.add(
									Constants.ERROR_DEBE_SELECCIONAR_VARIAS_UDOCS,
									new ActionError(
											Constants.ERROR_DEBE_SELECCIONAR_VARIAS_UDOCS));
						}

						break;// case UNIR:
					}
				}
			}
		}
		return errors;
	}

	/**
	 * Actualiza los datos en las tablas correspondientes.
	 * 
	 * @param mappings
	 * @param form
	 * @param request
	 * @param response
	 */
	public void grabarExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ServiceRepository servicios = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestorEstructuraDepositoBI depositoBI = servicios
				.lookupGestorEstructuraDepositoBI();

		OrganizacionUDocsForm formulario = (OrganizacionUDocsForm) form;

		List listaUDocs = getListaUDocs(request);
		List listaUDocsEliminadas = getListaUDocsEliminadas(request);

		depositoBI.organizarUDocsEnUInst(listaUDocs, listaUDocsEliminadas,
				formulario.getSignaturaui());

		// Establecer la variable de Cambios sin guardar a true
		setInTemporalSession(request,
				DepositoConstants.HAY_CAMBIOS_SIN_GUARDAR, Boolean.TRUE);

		goBackExecuteLogic(mappings, form, request, response);
	}

	public void actualizarCampoDescripcionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		List udocsCaja = (List) getFromTemporalSession(request,
				DepositoConstants.LISTA_UDOCS_EN_CAJA);
		String position = request.getParameter("position");

		AppUser appUser = getAppUser(request);
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(appUser));
		GestorEstructuraDepositoBI depositoBI = services
				.lookupGestorEstructuraDepositoBI();

		UDocEnUiDepositoVO udocEnUi = (UDocEnUiDepositoVO) udocsCaja
				.get(Integer.parseInt(position) - 1);

		udocEnUi.setDescripcion(request.getParameter("valorCampoDescripcion"));
		depositoBI.actualizarDescripcion(udocEnUi);

		goLastClientExecuteLogic(mappings, form, request, response);
	}

	/**
	 * Obtiene las Lista de Unidades de Documentales que está en la sesión.
	 * 
	 * @param request
	 * @return
	 */
	private List getListaUDocs(HttpServletRequest request) {
		List lista = (List) getFromTemporalSession(request,
				DepositoConstants.LISTA_UDOCS_EN_CAJA);
		return lista;
	}

	/**
	 * Coloca la lista en la Sessión.
	 * 
	 * @param request
	 */
	private void setListUDocs(HttpServletRequest request, List listaUDocs) {
		setInTemporalSession(request, DepositoConstants.LISTA_UDOCS_EN_CAJA,
				listaUDocs);
	}

	/**
	 * Obtiene las Lista de Unidades de Documentales que está en la sesión.
	 * 
	 * @param request
	 * @return
	 */
	private List getListaUDocsEliminadas(HttpServletRequest request) {
		List lista = (List) getFromTemporalSession(request,
				DepositoConstants.LISTA_UDOCS_EN_CAJA_ELIMINADAS);

		if (lista == null) {
			return new ArrayList();
		}
		return lista;
	}

	/**
	 * Coloca la lista en la Sessión.
	 * 
	 * @param request
	 */
	private void setListUDocsEliminadas(HttpServletRequest request,
			List listaUDocs) {
		setInTemporalSession(request,
				DepositoConstants.LISTA_UDOCS_EN_CAJA_ELIMINADAS, listaUDocs);
	}

	/**
	 * Comprueba si el elemento es el seleccionado
	 * 
	 * @param udoc
	 *            Unidad Documental
	 * @param codigoElemento
	 *            Código del Elemento compuesto por idunidaddoc:signatura
	 * @return
	 */
	private boolean comprobarElemento(UDocEnUiDepositoVO udoc,
			String codigoElemento, int posicionLista) {

		String codigo = new StringBuffer(udoc.getIdunidaddoc())
				.append(Constants.DELIMITER_IDS).append(udoc.getPosudocenui())
				.append(Constants.DELIMITER_IDS)
				.append(udoc.getSignaturaudoc())
				.append(Constants.DELIMITER_IDS).append(posicionLista)
				.toString();

		if (codigo.equals(codigoElemento)) {
			return true;
		}
		return false;
	}

	/**
	 * Comprueba si está seleccionado una unidad documental
	 * 
	 * @param udoc
	 * @param seleccionados
	 * @return
	 */
	private boolean isSeleccionado(UDocEnUiDepositoVO udoc,
			String[] seleccionados, int[] nuevasPosiciones) {
		for (int i = 0; i < seleccionados.length; i++) {
			if (comprobarElemento(udoc, seleccionados[i], nuevasPosiciones[i])) {
				return true;
			}
		}
		return false;
	}
}
