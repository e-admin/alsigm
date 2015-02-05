package fondos.actions;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import se.usuarios.AppPermissions;
import se.usuarios.AppUser;
import se.usuarios.ServiceClient;
import transferencias.TransferenciasConstants;
import transferencias.model.EstadoREntrega;
import transferencias.vos.RelacionEntregaVO;
import util.ErrorsTag;

import common.Constants;
import common.actions.ActionRedirect;
import common.actions.BusquedaBaseAction;
import common.bi.GestionRelacionesEntregaBI;
import common.bi.GestionSeriesBI;
import common.bi.GestionUnidadDocumentalBI;
import common.bi.ServiceRepository;
import common.exceptions.ActionNotAllowedException;
import common.exceptions.TooManyResultsException;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;
import common.pagination.PageInfo;
import common.util.ArrayUtils;
import common.util.StringUtils;

import deposito.forms.HuecoForm;
import deposito.model.GestorEstructuraDepositoBI;
import deposito.vos.DepositoVO;
import deposito.vos.ElementoAsignableVO;
import deposito.vos.ElementoVO;
import deposito.vos.HuecoID;
import deposito.vos.HuecoVO;
import docelectronicos.DocumentosConstants;
import docelectronicos.TipoObjeto;
import fondos.FondosConstants;
import fondos.forms.UnidadDocumentalForm;
import fondos.vos.BusquedaUdocsSerieVO;
import fondos.vos.SerieVO;
import fondos.vos.UnidadDocumentalVO;
import gcontrol.model.TipoListaControlAcceso;
import gcontrol.vos.ArchivoVO;

public class GestionUnidadDocumental extends BusquedaBaseAction {

	private static final String FORWARD_LISTADO = "lista_udocs_serie";

	public void generateInformeUdocsSerieExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ServiceRepository services = getServiceRepository(request);
		GestionSeriesBI serieBI = services.lookupGestionSeriesBI();
		try {

			// Cargamos la lista de unidades documentales de la serie
			SerieVO serieVO = (SerieVO) getFromTemporalSession(request,
					FondosConstants.SERIE_KEY);

			UnidadDocumentalForm unidadDocumentalForm = (UnidadDocumentalForm) form;
			List listaUdocs = serieBI.getUdocsSerieByProductor(serieVO.getId(),
					null, unidadDocumentalForm.getProductor());

			setListaUnidadesDocumentales(request, listaUdocs);

		} catch (TooManyResultsException e) {
			// Esta excepcion no se va a dar nunca
		}

		ActionRedirect ret = new ActionRedirect(
				mappings.findForward("generate_informe_udocs_serie"));
		setReturnActionFordward(request, ret);
	}

	public void verUdocsSerieExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ServiceRepository services = getServiceRepository(request);
		GestionSeriesBI serieBI = services.lookupGestionSeriesBI();
		String idSerie = request.getParameter("idSerie");
		try {
			removeInTemporalSession(request,
					FondosConstants.MOSTRAR_SIN_PRODUCTOR);
			removeInTemporalSession(request,
					FondosConstants.LISTA_PRODUCTORES_KEY);
			// Miramos si hay alguna unidad que no tiene productor para
			// habilitar la opcion
			if (serieBI.getCountProductorUdocsSerie(idSerie) > 0)
				setInTemporalSession(request,
						FondosConstants.MOSTRAR_SIN_PRODUCTOR,
						Constants.TRUE_STRING);

			// Obtenemos los productores de las unidades documentales de la
			// serie
			setInTemporalSession(request,
					FondosConstants.LISTA_PRODUCTORES_KEY,
					serieBI.getProductoresUdocsSerie(idSerie));

			// Cargamos la lista de unidades documentales de la serie

			List listaUdocs = serieBI.getUdocsSerieConFechasExtremas(idSerie,
					new PageInfo(request, "codigo"));

			setListaUnidadesDocumentales(request, listaUdocs);

			// setInTemporalSession(request,
			// FondosConstants.LISTA_UNIDADES_DOCUMENTALES,
			// listaUdocs);
		} catch (TooManyResultsException e) {
			// Esta excepcion no se va a dar nunca
		}

		ClientInvocation invocation = saveCurrentTreeViewInvocationAndRefresh(
				KeysClientsInvocations.CUADRO_CONTENIDO_SERIE, request,
				FondosConstants.CUADRO_CLF_VIEW_NAME);

		invocation.setAsReturnPoint(true);

		setReturnActionFordward(request,
				mappings.findForward("lista_udocs_serie"));
	}

	public void filtrarUdocsExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ServiceRepository services = getServiceRepository(request);
		GestionSeriesBI serieBI = services.lookupGestionSeriesBI();
		try {
			SerieVO serie = (SerieVO) getFromTemporalSession(request,
					FondosConstants.ELEMENTO_CF_KEY);
			// Cargamos la lista de unidades documentales de la serie
			UnidadDocumentalForm udocForm = (UnidadDocumentalForm) form;

			ActionErrors errors = udocForm.validateCamposBusqueda(mappings,
					request);

			if (errors == null || errors.isEmpty()) {
				BusquedaUdocsSerieVO busquedaUdocsSerieVO = udocForm
						.getBusquedaUdocsSerieVO();

				// List listaUdocs =
				// serieBI.getUdocsSerieFromView(serie.getId(), new
				// PageInfo(request, "codigo"), busquedaUdocsSerieVO);
				List listaUdocs = serieBI.getUdocsSerie(serie.getId(),
						new PageInfo(request, "codigo"), busquedaUdocsSerieVO);

				setListaUnidadesDocumentales(request, listaUdocs);
			} else {
				ErrorsTag.saveErrors(request, errors);
			}
		} catch (TooManyResultsException e) {
			logger.error(e);
		}
		setReturnActionFordward(request,
				mappings.findForward("lista_udocs_serie"));
	}

	public void verUnidadDocumentalWithPermissionsExecuteLogic(
			ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		ServiceClient serviceClient = ServiceClient.create(getAppUser(request));
		ServiceRepository services = ServiceRepository
				.getInstance(serviceClient);
		GestionUnidadDocumentalBI udocBI = services
				.lookupGestionUnidadDocumentalBI();
		// GestionCuadroClasificacionBI
		// elementoCFService=services.lookupGestionCuadroClasificacionBI();
		String pUdocID = request.getParameter(Constants.ID);

		if (puedeAccederUsuarioAElemento(request, pUdocID)) {
			UnidadDocumentalVO udoc = udocBI.getUnidadDocumental(pUdocID);
			UnidadDocumentalToPO udocTransformer = new UnidadDocumentalToPO(
					services);

			if (udoc.isSubtipoCaja()) {
				saveCurrentInvocation(
						KeysClientsInvocations.CUADRO_VER_FRACCION_SERIE,
						request);
			} else {
				saveCurrentInvocation(
						KeysClientsInvocations.CUADRO_VER_UNIDAD_DOCUMENTAL,
						request);
			}

			setInTemporalSession(request, FondosConstants.UDOC_KEY,
					udocTransformer.transform(udoc, getAppUser(request)));

			AppUser user = getAppUser(request);

			if (user.hasPermission(AppPermissions.ADMINISTRACION_TOTAL_SISTEMA)
					|| user.belongsToCustodyArchive(udoc.getIdArchivo()))
				setInTemporalSession(request,
						FondosConstants.PERMITIR_VER_SIGNATURAS, new Boolean(
								true));
			else
				setInTemporalSession(request,
						FondosConstants.PERMITIR_VER_SIGNATURAS, new Boolean(
								false));

			setReturnActionFordward(request,
					mappings.findForward("ver_unidadDocumental"));
		} else {
			goBackExecuteLogic(mappings, form, request, response);
		}

	}

	private ActionErrors checkPermisosArchivoHuecoXElemento(
			HttpServletRequest request, ElementoVO elemento) {
		List custodyArchiveList = getServiceRepository(request)
				.getServiceClient().getCustodyArchiveList();
		ServiceClient serviceClient = ServiceClient.create(getAppUser(request));
		ServiceRepository services = ServiceRepository
				.getInstance(serviceClient);
		GestorEstructuraDepositoBI depositoService = services
				.lookupGestorEstructuraDepositoBI();
		ActionErrors errors = null;

		boolean conPermisosEnArchivoHueco = false;
		ArchivoVO archivo = depositoService.getArchivoXIdElemento(elemento);
		for (Iterator it = custodyArchiveList.iterator(); it.hasNext();) {
			if (((String) it.next()).equals(archivo.getId()))
				conPermisosEnArchivoHueco = true;
		}

		if (!conPermisosEnArchivoHueco) {
			errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError(
					FondosConstants.ERROR_UDOC_EN_ARCHIVO_SIN_PERMISOS));
			obtenerErrores(request, true).add(errors);
		}
		return errors;
	}

	public void verHuecoEnDepositoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		String idHueco = request.getParameter("idHueco");

		ClientInvocation invocacionOrigen = getInvocationStack(request)
				.getLastClientInvocation();

		ServiceClient serviceClient = ServiceClient.create(getAppUser(request));
		ServiceRepository services = ServiceRepository
				.getInstance(serviceClient);
		GestorEstructuraDepositoBI depositoService = services
				.lookupGestorEstructuraDepositoBI();
		HuecoID huecoID = HuecoForm.getHuecoID(idHueco);
		HuecoVO huecoVO = depositoService.getInfoHueco(huecoID);
		ElementoAsignableVO asignable = depositoService
				.getElementoAsignable(huecoVO.getIdElemAPadre());

		ActionErrors errors = checkPermisosArchivoHuecoXElemento(request,
				asignable);
		if (errors != null) {
			goLastClientExecuteLogic(mappings, form, request, response);
			return;
		}

		invocacionOrigen
				.setPath("/action/manageVistaCuadroClasificacion?actionToPerform=goHome");

		String tipoPadre = asignable.getIdTipoElemento();

		ActionRedirect ret = new ActionRedirect(
				mappings.findForward("ver_hueco"));
		ret.addParameter("itemID", idHueco, false);
		ret.addParameter("itemPadreTipo", tipoPadre, false);
		ret.addParameter("itemTipo", DepositoVO.ID_TIPO_HUECO, false);
		setReturnActionFordward(request, ret);
	}

	public void verUnidadDocumentalExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ServiceClient serviceClient = ServiceClient.create(getAppUser(request));
		ServiceRepository services = ServiceRepository
				.getInstance(serviceClient);
		GestionUnidadDocumentalBI udocBI = services
				.lookupGestionUnidadDocumentalBI();
		// GestionCuadroClasificacionBI elementoCFService=
		// services.lookupGestionCuadroClasificacionBI();

		String pUdocID = request.getParameter(Constants.ID);
		if (puedeAccederUsuarioAElemento(request, pUdocID)) {
			UnidadDocumentalVO udoc = udocBI.getUnidadDocumental(pUdocID);
			UnidadDocumentalToPO udocTransformer = new UnidadDocumentalToPO(
					services);

			if (udoc.isSubtipoCaja()) {
				ClientInvocation invocation = saveCurrentInvocation(
						KeysClientsInvocations.CUADRO_VER_FRACCION_SERIE,
						request);
				invocation.addParameter("itemID", pUdocID);
			} else {
				ClientInvocation invocation = saveCurrentInvocation(
						KeysClientsInvocations.CUADRO_VER_UNIDAD_DOCUMENTAL,
						request);
				invocation.addParameter("itemID", pUdocID);
			}

			setInTemporalSession(request, FondosConstants.UDOC_KEY,
					udocTransformer.transform(udoc, getAppUser(request)));

			AppUser user = getAppUser(request);

			if (user.hasPermission(AppPermissions.ADMINISTRACION_TOTAL_SISTEMA)
					|| user.belongsToCustodyArchive(udoc.getIdArchivo()))
				setInTemporalSession(request,
						FondosConstants.PERMITIR_VER_SIGNATURAS, new Boolean(
								true));
			else
				setInTemporalSession(request,
						FondosConstants.PERMITIR_VER_SIGNATURAS, new Boolean(
								false));

			setReturnActionFordward(request,
					mappings.findForward("ver_unidadDocumental"));
		} else {
			goBackExecuteLogic(mappings, form, request, response);
		}
	}

	public void editarInfoUdocExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// Guardar el enlace a la pagina
		saveCurrentInvocation(
				KeysClientsInvocations.CUADRO_EDICION_UNIDAD_DOCUMENTAL,
				request);

		// Informacion de la unidad documental
		UnidadDocumentalPO udoc = (UnidadDocumentalPO) getFromTemporalSession(
				request, FondosConstants.UDOC_KEY);
		((UnidadDocumentalForm) form).set(udoc);

		if (!checkPermisosSobreElemento(request, udoc.getId(),
				FondosConstants.PERMISOS_EDICION_ELEMENTO_CUADRO)) {
			popLastInvocation(request);
			goLastClientExecuteLogic(mappings, form, request, response);
			return;
		}

		// Listas de control de acceso
		request.setAttribute(
				FondosConstants.LISTAS_CONTROL_ACCESO_KEY,
				getGestionControlUsuarios(request)
						.getListasControlAccesoByTipo(
								TipoListaControlAcceso.ELEMENTO_CUADRO_CLASIFICACION));

		setReturnActionFordward(request,
				mappings.findForward("editar_unidadDocumental"));
	}

	public void saveInfoUdocExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UnidadDocumentalForm udocForm = (UnidadDocumentalForm) form;

		ActionErrors errors = udocForm.validate(mappings, request);
		if (errors.isEmpty()) {
			getGestionCuadroClasificacionBI(request).updateControlAcceso(
					udocForm.getId(), udocForm.getNivelAcceso(),
					udocForm.getIdLCA());

			RelacionEntregaVO relacion = (RelacionEntregaVO) getFromTemporalSession(
					request, TransferenciasConstants.RELACION_KEY);
			if (relacion != null) {
				ServiceRepository services = ServiceRepository
						.getInstance(ServiceClient.create(getAppUser(request)));
				GestionRelacionesEntregaBI relacionBI = services
						.lookupGestionRelacionesBI();
				if (relacion.isRechazada()) {
					relacion.setEstado(EstadoREntrega.ABIERTA
							.getIdentificador());
					try {
						relacionBI.updateRelacion(relacion);
					} catch (ActionNotAllowedException e) {
						guardarError(request, e);
					}
				}
			}

			goBackExecuteLogic(mappings, form, request, response);
		} else {
			obtenerErrores(request, true).add(errors);

			// Listas de control de acceso
			request.setAttribute(
					FondosConstants.LISTAS_CONTROL_ACCESO_KEY,
					getGestionControlUsuarios(request)
							.getListasControlAccesoByTipo(
									TipoListaControlAcceso.ELEMENTO_CUADRO_CLASIFICACION));

			setReturnActionFordward(request,
					mappings.findForward("editar_unidadDocumental"));
		}
	}

	public void verEnFondosExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ActionRedirect ret = new ActionRedirect(
				mappings.findForward("ver_en_fondos"));
		ret.addParameter("itemID", request.getParameter("unidadDocumental"));
		setReturnActionFordward(request, ret);
	}

	public void cancelarExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		goLastClientExecuteLogic(mappings, form, request, response);
	}

	/**
	 * Muestra la pagina de inicio de los documentos electr�nicos de un
	 * elemento del cuadro.
	 * 
	 * @param mapping
	 *            {@link ActionMapping} con los mapeos asociado.
	 * @param form
	 *            {@link ActionForm} asociado al action.
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 */
	protected void documentosExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("Inicio de documentosExecuteLogic");

		// Leer el identificador del objeto
		String id = request.getParameter(Constants.ID);
		if (logger.isInfoEnabled())
			logger.info("Id Objeto: " + id);

		setInTemporalSession(request, DocumentosConstants.OBJECT_ID_KEY, id);
		setInTemporalSession(request, DocumentosConstants.OBJECT_TYPE_KEY,
				new Integer(TipoObjeto.ELEMENTO_CF));

		// Inicializar los clasificadores por defecto
		getGestionDocumentosElectronicosBI(request)
				.inicializaClasificadoresYRepEcm(id, TipoObjeto.ELEMENTO_CF,
						false);

		setReturnActionFordward(
				request,
				redirectForwardMethod(request, "/clasificador", "method",
						"retrieve&idObjeto=" + id + "&tipoObjeto="
								+ TipoObjeto.ELEMENTO_CF));
	}

	private void setListaUnidadesDocumentales(HttpServletRequest request,
			Collection listaUdocs) {
		CollectionUtils
				.transform(listaUdocs, UnidadDocumentalToPO
						.getInstance(getServiceRepository(request)));
		setInTemporalSession(request,
				FondosConstants.LISTA_UNIDADES_DOCUMENTALES, listaUdocs);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see common.actions.BusquedaBaseAction#getForwardBusqueda()
	 */
	protected String getForwardBusqueda() {
		return FORWARD_LISTADO;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see common.actions.BusquedaBaseAction#getForwardListado()
	 */
	protected String getForwardListado() {
		return FORWARD_LISTADO;
	}

	public void accionNoConservarUdocsExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		logger.debug("Accion no conservar udocs");
		saveCurrentInvocation(
				KeysClientsInvocations.LISTADO_UDOCS_SERIE_NO_CONSERVAR,
				request);

		UnidadDocumentalForm unidadDocumentalForm = (UnidadDocumentalForm) form;

		String[] ids = (String[]) getFromTemporalSession(request,
				FondosConstants.ACCION_ELEMENTOS_KEY);

		if (ArrayUtils.isNotEmpty(ids)) {
			if (logger.isDebugEnabled()) {
				logger.debug("Accion No Conservar ids" + ids.toString());
			}
			unidadDocumentalForm.setIds(ids);
			List listaUdocs = getGestionSeriesBI(request)
					.getUdocsSerieConservadas(ids);
			setListaUnidadesDocumentales(request, listaUdocs);
		}

		setReturnActionFordward(request,
				mappings.findForward("ver_udocs_serie_no_conservar"));
	}

	public void ejecutarAccionNoConservarUdocsExecuteLogic(
			ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		UnidadDocumentalForm unidadDocumentalForm = (UnidadDocumentalForm) form;

		GestionUnidadDocumentalBI unidadDocumentalBI = getGestionUnidadDocumentalBI(request);

		String ids[] = unidadDocumentalForm.getIds();

		unidadDocumentalBI.eliminarMarcaConservadaUdocsSerie(ids);

		goBackExecuteLogic(mappings, form, request, response);
	}

	public void gestionarUdocsConservadasExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		saveCurrentInvocation(
				KeysClientsInvocations.LISTADO_UDOCS_SERIE_NO_CONSERVAR,
				request);

		String idSerie = request.getParameter("idSerie");

		if (!StringUtils.isEmpty(idSerie)) {
			List listaUdocs = getGestionSeriesBI(request)
					.getUdocsSerieConservadasByIdSerie(idSerie);
			setListaUnidadesDocumentales(request, listaUdocs);
		}
		setReturnActionFordward(request,
				mappings.findForward("ver_udocs_serie_no_conservar"));

	}
}