package transferencias.actions;

import fondos.FondosConstants;
import fondos.model.SubtipoNivelCF;
import fondos.model.TipoNivelCF;
import fondos.vos.ElementoCuadroClasificacionVO;
import fondos.vos.INivelCFVO;
import fondos.vos.OrganoProductorVO;
import fondos.vos.SerieVO;
import fondos.vos.UnidadDocumentalVO;
import gcontrol.vos.ArchivoVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import se.usuarios.AppUser;
import se.usuarios.ServiceClient;
import solicitudes.prestamos.vos.RevisionDocumentacionVO;
import transferencias.TransferenciasConstants;
import transferencias.forms.IngresoDirectoForm;
import transferencias.vos.RelacionEntregaVO;
import xml.config.ConfiguracionSistemaArchivoFactory;
import xml.config.ConfiguracionTransferencias;
import auditoria.ArchivoErrorCodes;

import common.Constants;
import common.Messages;
import common.actions.ActionRedirect;
import common.actions.BaseAction;
import common.bi.GestionArchivosBI;
import common.bi.GestionCuadroClasificacionBI;
import common.bi.GestionDescripcionBI;
import common.bi.GestionFondosBI;
import common.bi.GestionPrestamosBI;
import common.bi.GestionRelacionesEntregaBI;
import common.bi.GestionSeriesBI;
import common.bi.GestionUnidadDocumentalBI;
import common.bi.ServiceRepository;
import common.definitions.ArchivoModules;
import common.exceptions.ActionNotAllowedException;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;
import common.util.ListUtils;

import deposito.model.GestorEstructuraDepositoBI;
import deposito.vos.UDocEnUiDepositoVO;
import deposito.vos.UInsDepositoVO;
import descripcion.model.xml.card.TipoFicha;
import descripcion.vos.CampoReferenciaVO;
import descripcion.vos.DescriptorVO;
import descripcion.vos.FichaVO;
import descripcion.vos.ValorCampoGenericoVO;

/**
 * Action que lleva a cabo las diferentes acciones que pueden ser realizadas
 * sobre ingreso directo
 */
public class GestionIngresoDirectoAction extends BaseAction {

	/** Logger de la clase */
	protected final static Logger logger = Logger
			.getLogger(GestionIngresoDirectoAction.class);

	/**
	 * Método común para cargar los combos al crear o editar un nuevo ingreso
	 * 
	 * @param request
	 *            Petición actual
	 */
	private void loadCombosComunLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ActionNotAllowedException {

		// Obtener el AppUser
		AppUser appUser = getAppUser(request);

		// Obtener el repositorio de servicios
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));

		// Obtener la configuración
		ConfiguracionTransferencias config = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo()
				.getConfiguracionTransferencias();

		// Obtener las formas documentales
		GestionDescripcionBI descripcionBI = services
				.lookupGestionDescripcionBI();
		List formasDocumentales = descripcionBI.getValoresValidacion(config
				.getIdTablaValidacionFormaDocumental());

		// Obtener los formatos
		GestorEstructuraDepositoBI serviceDeposito = services
				.lookupGestorEstructuraDepositoBI();
		List formatosVO = serviceDeposito.getFormatosVigentes();

		// Obtener los posibles archivos receptores
		GestionArchivosBI serviceArchivos = services.lookupGestionArchivosBI();
		List ltArchivos = serviceArchivos.getArchivosXId(appUser
				.getCustodyArchiveList().toArray());
		if (ltArchivos != null && ltArchivos.size() == 1) {
			IngresoDirectoForm frm = (IngresoDirectoForm) form;

			ArchivoVO archivo = (ArchivoVO) ltArchivos.get(0);
			frm.setIdArchivo(archivo.getId());
			frm.setNombreArchivo(archivo.getNombre());
			// ltArchivos = null;
		}

		// Obtener los niveles documentales
		GestionCuadroClasificacionBI cuadroBI = services
				.lookupGestionCuadroClasificacionBI();
		List nivelesDocumentales = cuadroBI.getNivelesByTipo(
				TipoNivelCF.UNIDAD_DOCUMENTAL, null);

		/*
		 * List ltArchivosReceptores = new ArrayList();
		 * 
		 * if ((ltArchivos != null) && (!ltArchivos.isEmpty())) { ArchivoVO
		 * archivo = (ArchivoVO) ltArchivos.get(0); ltArchivosReceptores =
		 * serviceArchivos.getArchivosReceptores(archivo.getId()); }
		 */

		// Obtener los fondos vigentes
		GestionFondosBI serviceFondos = services.lookupGestionFondosBI();
		List ltFondos = serviceFondos.getFondosVigentes();

		// Guardar en sesión las listas necesarias
		setInTemporalSession(request,
				TransferenciasConstants.LISTA_FORMAS_DOCUMENTALES_KEY,
				formasDocumentales);
		setInTemporalSession(request,
				TransferenciasConstants.LISTA_FORMATOS_KEY, formatosVO);
		setInTemporalSession(request,
				TransferenciasConstants.LISTA_CODIGOSARCHIVO_KEY, ltArchivos);
		setInTemporalSession(request,
				TransferenciasConstants.LISTA_CODIGOSFONDO_KEY, ltFondos);
		// Si sólo hay un nivel, dejamos que la aplicación siga funcionando como
		// hasta ahora
		if (nivelesDocumentales != null && nivelesDocumentales.size() > 1)
			setInTemporalSession(request,
					TransferenciasConstants.LISTA_NIVELES_DOCUMENTALES_KEY,
					nivelesDocumentales);
		/*
		 * else cargarFichas(mapping, form, request, response);
		 */

		if (ltFondos == null || ltFondos.size() == 0)
			throw new ActionNotAllowedException(null,
					ArchivoErrorCodes.FONDOS_DESTINO_NO_DEFINIDOS_ALTA,
					ArchivoModules.TRANSFERENCIAS_MODULE);

		if (ltArchivos == null || ltArchivos.size() == 0)
			throw new ActionNotAllowedException(null,
					ArchivoErrorCodes.ARCHIVOS_DESTINO_NO_DEFINIDOS_ALTA,
					ArchivoModules.TRANSFERENCIAS_MODULE);
		else
			ltArchivos = null;
	}

	/*
	 * Método para limpiar los valores de la sesión
	 */
	/*
	 * protected void cleanTemporalSessionValues (HttpServletRequest request) {
	 * 
	 * removeInTemporalSession(request,
	 * TransferenciasConstants.LISTA_FORMAS_DOCUMENTALES_KEY);
	 * removeInTemporalSession(request,
	 * TransferenciasConstants.LISTA_FORMATOS_KEY);
	 * removeInTemporalSession(request,
	 * TransferenciasConstants.LISTA_CODIGOSARCHIVO_KEY);
	 * removeInTemporalSession(request,
	 * TransferenciasConstants.LISTA_CODIGOSFONDO_KEY); }
	 */

	/**
	 * Método para crear un nuevo ingreso directo
	 * 
	 * @param mapping
	 *            Objeto Mapping de struts
	 * @param form
	 *            Formulario de la petición
	 * @param request
	 *            Petición actual
	 * @param response
	 *            Objeto respuesta de la petición
	 */
	protected void nuevoExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		// Crear una invocación
		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.CUADRO_NUEVO_INGRESO_DIRECTO, request);
		invocation.setAsReturnPoint(true);

		IngresoDirectoForm frm = (IngresoDirectoForm) form;
		frm.setPermitidoModificarFormato(true);

		try {
			// Cargar los combos necesarios
			loadCombosComunLogic(mapping, form, request, response);
			setReturnActionFordward(request,
					mapping.findForward("editar_ingreso"));
		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
			// cleanTemporalSessionValues(request);
			setReturnActionFordward(request,
					mapping.findForward("editar_ingreso"));
		}
	}

	/**
	 * Método para crear un nuevo ingreso directo a partir de una unidad
	 * documental.
	 * 
	 * @param mapping
	 *            Objeto Mapping de struts
	 * @param form
	 *            Formulario de la petición
	 * @param request
	 *            Petición actual
	 * @param response
	 *            Objeto respuesta de la petición
	 */
	protected void nuevoDesdeUdocExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		// Crear una invocación
		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.CUADRO_NUEVO_INGRESO_DIRECTO, request);
		invocation.setAsReturnPoint(true);

		IngresoDirectoForm frm = (IngresoDirectoForm) form;
		frm.setPermitidoModificarFormato(true);

		String idRevDoc = request.getParameter(Constants.ID_REV_DOC);
		frm.setIdRevDoc(idRevDoc);

		try {
			// Cargar los combos necesarios
			loadCombosComunLogic(mapping, form, request, response);
			setReturnActionFordward(request,
					mapping.findForward("editar_ingreso"));

			cargarDatosRevisionDocumentacion(mapping, form, request, response,
					idRevDoc);
		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
			// cleanTemporalSessionValues(request);
			setReturnActionFordward(request,
					mapping.findForward("editar_ingreso"));
		}
	}

	/**
	 * Obtiene la unidad documental para la revisión si la hay.
	 * 
	 * @param services
	 * @param idRevDoc
	 *            Identificador de la revisión de la documentación.
	 * @return {@link UnidadDocumentalVO}
	 */
	private void cargarDatosRevisionDocumentacion(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response, String idRevDoc) {

		if (StringUtils.isNotEmpty(idRevDoc)) {
			ServiceRepository services = ServiceRepository
					.getInstance(ServiceClient.create(getAppUser(request)));
			GestionPrestamosBI prestamosBI = services
					.lookupGestionPrestamosBI();
			IngresoDirectoForm frm = (IngresoDirectoForm) form;

			RevisionDocumentacionVO revisionDocumentacionVO = prestamosBI
					.getRevisionDocumentacionById(idRevDoc);

			if (revisionDocumentacionVO != null) {
				String idUdoc = revisionDocumentacionVO.getIdUdoc();

				if (StringUtils.isNotBlank(idUdoc)) {
					GestionCuadroClasificacionBI cuadroBI = services
							.lookupGestionCuadroClasificacionBI();
					ElementoCuadroClasificacionVO udoc = cuadroBI
							.getElementoCuadroClasificacion(idUdoc);

					GestionUnidadDocumentalBI unidadDocumentalBI = services
							.lookupGestionUnidadDocumentalBI();
					UnidadDocumentalVO unidadDocumentalVO = unidadDocumentalBI
							.getUnidadDocumental(udoc.getId());

					// Establecer el código de Archivo
					frm.setIdArchivo(udoc.getIdArchivo());

					// Establecer el fondo de destino
					frm.setIdFondo(udoc.getIdFondo());

					// Establecer la serie destino
					ElementoCuadroClasificacionVO serie = getSerie(services,
							udoc.getIdPadre());
					if (serie != null) {
						frm.setSerie(serie.getId());
						frm.setNombreSerie(serie.getTitulo());
					}

					frm.setIdFormato(getIdFormato(services, udoc.getId()));

					frm.setFormaDocumental(unidadDocumentalVO
							.getTipoDocumental());

					frm.setIdNivelDocumental(udoc.getIdNivel());
					frm.setIdFicha(udoc.getIdFichaDescr());

					// Obtener el Productor
					GestionDescripcionBI descripcionBI = services
							.lookupGestionDescripcionBI();

					String idCampoProductor = ConfiguracionSistemaArchivoFactory
							.getConfiguracionSistemaArchivo()
							.getConfiguracionDescripcion().getProductor();

					List listaProductores = descripcionBI.getValues(
							TipoFicha.FICHA_ELEMENTO_CF,
							ValorCampoGenericoVO.TIPO_REFERENCIA, udoc.getId(),
							idCampoProductor);

					if (!ListUtils.isEmpty(listaProductores)) {
						CampoReferenciaVO campoReferencia = (CampoReferenciaVO) listaProductores
								.get(0);

						if (campoReferencia != null) {
							frm.setIdDescriptorProductor(campoReferencia
									.getIdObjRef());
							frm.setNombreProductor(campoReferencia.getNombre());
						}
					}

					cargarFichas(mapping, frm, request, response);
				}
			}
		}
	}

	/**
	 * Obtiene la serie seleccionada.
	 * 
	 * @param services
	 * @param idSerie
	 *            Identificador de la serie
	 * @return
	 */
	private ElementoCuadroClasificacionVO getSerie(ServiceRepository services,
			String idSerie) {
		GestionCuadroClasificacionBI cuadroBI = services
				.lookupGestionCuadroClasificacionBI();

		ElementoCuadroClasificacionVO serie = cuadroBI
				.getElementoCuadroClasificacion(idSerie);
		return serie;
	}

	private String getIdFormato(ServiceRepository services, String idUdoc) {
		String idFormato = null;
		GestorEstructuraDepositoBI depositoBI = services
				.lookupGestorEstructuraDepositoBI();

		List listaUdocs = depositoBI.getUDocsById(new String[] { idUdoc });
		if (!ListUtils.isEmpty(listaUdocs)) {
			UDocEnUiDepositoVO udoc = (UDocEnUiDepositoVO) listaUdocs.get(0);

			// Obtener la unidad de instalación
			UInsDepositoVO uInsDepositoVO = depositoBI.getUinsEnDeposito(udoc
					.getIduinstalacion());

			if (uInsDepositoVO != null) {
				idFormato = uInsDepositoVO.getIdformato();

			}
		}

		return idFormato;
	}

	public void cargarFichas(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionCuadroClasificacionBI cuadroBI = services
				.lookupGestionCuadroClasificacionBI();
		GestionSeriesBI seriesBI = services.lookupGestionSeriesBI();
		GestionDescripcionBI descripcionBI = services
				.lookupGestionDescripcionBI();
		ArrayList fichas = new ArrayList();
		SerieVO serieVO = null;
		IngresoDirectoForm frm = (IngresoDirectoForm) form;
		boolean necesarioNivel = false;
		String fichaDescripcionUdoc = null, idNivelDocumental = null, idSerieDestino = null;

		idNivelDocumental = frm.getIdNivelDocumental();
		idSerieDestino = frm.getSerie();

		// Si existe más de un nivel documental, la lista de niveles estará en
		// sesión y por tanto será necesario que se haya seleccionado un nivel
		// en el formulario para poder cargar las fichas correspondientes al
		// mismo. Si sólo hay un nivel definido, se coje por defecto el de
		// unidad documental
		// de subtipo simple
		ArrayList listaNiveles = (ArrayList) getFromTemporalSession(request,
				TransferenciasConstants.LISTA_NIVELES_DOCUMENTALES_KEY);
		if (listaNiveles != null && listaNiveles.size() > 1)
			necesarioNivel = true;

		if (StringUtils.isNotEmpty(idSerieDestino)
				&& (!necesarioNivel || StringUtils
						.isNotEmpty(idNivelDocumental))) {

			// Obtenemos la Serie de destino
			serieVO = seriesBI.getSerie(idSerieDestino);

			// Por defecto, se da de alta un nivel de tipo unidad documental y
			// subtipo unidad documental simple
			if (StringUtils.isEmpty(idNivelDocumental))
				idNivelDocumental = cuadroBI.getNivelCF(
						TipoNivelCF.UNIDAD_DOCUMENTAL.getIdentificador(),
						SubtipoNivelCF.UDOCSIMPLE.getIdentificador()).getId();

			// Obtenemos el nivel cuyo identificador es el indicado y a partir
			// del mismo obtenemos el identificador de su ficha preferente
			INivelCFVO nivelUnidadDocumental = cuadroBI
					.getNivelCF(idNivelDocumental);

			String fichaDescripcionPrefUDoc = serieVO
					.getIdFichaDescrPrefUdoc(nivelUnidadDocumental.getId());

			// Si la serie tiene definidos listas de volúmenes y ficha
			// preferente para las u.docs., usar estas, sino, las del nivel
			if (fichaDescripcionPrefUDoc != null)
				fichaDescripcionUdoc = fichaDescripcionPrefUDoc;
			else
				fichaDescripcionUdoc = nivelUnidadDocumental
						.getIdFichaDescrPref();

			FichaVO ficha = null;
			HashMap todasFichas = (HashMap) getFromTemporalSession(request,
					TransferenciasConstants.MAP_TODAS_FICHAS_KEY);
			if (todasFichas == null || todasFichas.isEmpty()) {
				List listaTodasFichas = descripcionBI.getFichas();
				if (listaTodasFichas != null && listaTodasFichas.size() > 0) {
					Iterator it = listaTodasFichas.iterator();
					todasFichas = new HashMap();
					while (it.hasNext()) {
						FichaVO fichaVO = (FichaVO) it.next();
						todasFichas.put(fichaVO.getId(), fichaVO);
					}
				}
				setInTemporalSession(request,
						TransferenciasConstants.MAP_TODAS_FICHAS_KEY,
						todasFichas);
			}

			if (todasFichas != null && !todasFichas.isEmpty()) {
				ficha = (FichaVO) todasFichas.get(fichaDescripcionUdoc);
				fichas.add(ficha);
			}
		}

		setInTemporalSession(request, TransferenciasConstants.LISTA_FICHAS_KEY,
				fichas);
	}

	public void cargarFichasExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		cargarFichas(mapping, form, request, response);
		setReturnActionFordward(request, mapping.findForward("editar_ingreso"));
	}

	/**
	 * Método para editar un nuevo ingreso directo
	 * 
	 * @param mapping
	 *            Objeto Mapping de struts
	 * @param form
	 *            Formulario de la petición
	 * @param request
	 *            Petición actual
	 * @param response
	 *            Objeto respuesta de la petición
	 */
	protected void editarExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		// Salvar la invocación
		saveCurrentInvocation(
				KeysClientsInvocations.CUADRO_EDICION_INGRESO_DIRECTO, request);

		try {
			// Cargar los combos necesarios
			loadCombosComunLogic(mapping, form, request, response);

			// Obtener el id del ingreso
			String idIngreso = request.getParameter("idIngreso");

			// Leer el ingreso
			ServiceRepository services = ServiceRepository
					.getInstance(ServiceClient.create(getAppUser(request)));
			GestionRelacionesEntregaBI relacionesBI = services
					.lookupGestionRelacionesBI();
			RelacionEntregaVO ingresoVO = relacionesBI
					.getRelacionXIdRelacion(idIngreso);
			RelacionEntregaPO ingresoPO = (RelacionEntregaPO) RelacionToPO
					.getInstance(services).transform(ingresoVO);

			// Guardar el ingreso en sesión
			setInTemporalSession(request,
					TransferenciasConstants.INGRESO_DIRECTO_KEY, ingresoPO);

			// Obtener el formulario
			IngresoDirectoForm frm = (IngresoDirectoForm) form;

			// Guardar los datos en el formulario
			frm.set(ingresoVO);

			// Obtener los datos de la serie
			SerieVO serieVO = ingresoPO.getSerie();
			frm.setNombreSerie(serieVO.getCodReferencia() + " "
					+ serieVO.getDenominacion());

			// Obtener los datos del productor
			String idDescriptor = ingresoVO.getIddescrorgproductor();

			if (StringUtils.isNotEmpty(idDescriptor)) {
				OrganoProductorVO organoProductorVO = relacionesBI
						.getOrganoProductor(idDescriptor);
				if (organoProductorVO != null) {
					frm.setIdDescriptorProductor(organoProductorVO.getId());
					frm.setNombreProductor(organoProductorVO.getNombre());
				} else {
					GestionDescripcionBI descripcionBI = services
							.lookupGestionDescripcionBI();
					// Intentar buscarlo en descriptores
					DescriptorVO descriptorVO = descripcionBI
							.getDescriptor(idDescriptor);
					if (descriptorVO != null) {
						frm.setIdDescriptorProductor(descriptorVO.getId());
						frm.setNombreProductor(descriptorVO.getNombre());
					}
				}
			}

			int numUdocRe = relacionesBI.getCountUnidadesDocRe(idIngreso);
			int numUInst = relacionesBI.getCountUnidadesInstalacion(idIngreso);

			if (numUdocRe == 0 && numUInst == 0) {
				frm.setPermitidoModificarFormato(true);
			}

			// Establecemos el nivel documental para el ingreso directo
			/*
			 * String idNivelDocumental = ConfiguracionSistemaArchivoFactory
			 * .getConfiguracionSistemaArchivo() .getConfiguracionGeneral()
			 * .getIdNivelUnidadDocumental();
			 * 
			 * if (StringUtils.isNotEmpty(frm.getIdNivelDocumental()))
			 * idNivelDocumental = frm.getIdNivelDocumental();
			 * 
			 * frm.setIdNivelDocumental(idNivelDocumental);
			 */

			setReturnActionFordward(request,
					mapping.findForward("editar_ingreso"));
		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
			setReturnActionFordward(request,
					mapping.findForward("editar_ingreso"));
		}
	}

	/**
	 * Método para mostrar el buscador de series
	 * 
	 * @param mapping
	 *            Objeto Mapping de struts
	 * @param form
	 *            Formulario de la petición
	 * @param request
	 *            Petición actual
	 * @param response
	 *            Objeto respuesta de la petición
	 */
	protected void verBuscadorSeriesExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		setReturnActionFordward(request, mappings.findForward("editar_ingreso"));
	}

	/**
	 * Método para buscar series en el buscador
	 * 
	 * @param mapping
	 *            Objeto Mapping de struts
	 * @param form
	 *            Formulario de la petición
	 * @param request
	 *            Petición actual
	 * @param response
	 *            Objeto respuesta de la petición
	 */
	protected void buscarSerieExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		// Obtener el formulario
		IngresoDirectoForm frm = (IngresoDirectoForm) form;

		// Obtener el id de fondo destino
		String pFondoID = frm.getIdFondo();

		// Obtener el código de la serie
		String pCodigo = request.getParameter("codigo");

		// Obtener el título de la serie
		String pTitulo = request.getParameter("titulo");

		// Buscar la serie
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionSeriesBI serieBI = services.lookupGestionSeriesBI();
		List series = serieBI.findSeriesParaPrevision(pFondoID, pCodigo,
				pTitulo);
		request.setAttribute(FondosConstants.SERIE_KEY, series);

		// Volver al detalle de la previsión
		setReturnActionFordward(request, mappings.findForward("editar_ingreso"));
	}

	/**
	 * Método para crear un ingreso directo
	 * 
	 * @param mapping
	 *            Objeto Mapping de struts
	 * @param form
	 *            Formulario de la petición
	 * @param request
	 *            Petición actual
	 * @param response
	 *            Objeto respuesta de la petición
	 */
	protected void guardarExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			// Obtener el formulario
			IngresoDirectoForm frm = (IngresoDirectoForm) form;

			// Obtener el servicio de relaciones
			ServiceRepository services = getServiceRepository(request);
			GestionRelacionesEntregaBI serviceRelaciones = services
					.lookupGestionRelacionesBI();
			GestionCuadroClasificacionBI cuadroBI = services
					.lookupGestionCuadroClasificacionBI();

			// Validar los errores
			ActionErrors errors = validarIngresoDirecto(frm, request, cuadroBI);

			if (errors == null) {

				RelacionEntregaVO ingreso = null;

				// Comprobar si es un nuevo ingreso directo o si es una edición
				if (StringUtils.isEmpty(frm.getId())) {

					// Establecemos el nivel documental para el ingreso directo
					/*
					 * String idNivelDocumental =
					 * ConfiguracionSistemaArchivoFactory
					 * .getConfiguracionSistemaArchivo()
					 * .getConfiguracionGeneral() .getIdNivelUnidadDocumental();
					 */
					// Por defecto, se da de alta un nivel de tipo unidad
					// documental y subtipo unidad documental simple
					String idNivelDocumental = cuadroBI.getNivelCF(
							TipoNivelCF.UNIDAD_DOCUMENTAL.getIdentificador(),
							SubtipoNivelCF.UDOCSIMPLE.getIdentificador())
							.getId();
					if (StringUtils.isNotEmpty(frm.getIdNivelDocumental()))
						idNivelDocumental = frm.getIdNivelDocumental();

					// Nuevo ingreso directo
					ingreso = serviceRelaciones.insertIngresoDirecto(
							frm.getIdArchivo(), frm.getIdFondo(),
							frm.getSerie(), frm.getIdFormato(),
							frm.getFormaDocumental(), frm.getObservaciones(),
							frm.getIdDescriptorProductor(), idNivelDocumental,
							frm.getIdFicha(), frm.getIdRevDoc());

					// Establecer el id en el formulario
					frm.setId(ingreso.getId());

					// Eliminar la ultima invocacion del invocation stack
					popLastInvocation(request);

					ActionRedirect redirectAVistaRelacion = new ActionRedirect(
							mapping.findForward("redirect_to_view_relacion"));
					redirectAVistaRelacion.setRedirect(true);
					redirectAVistaRelacion.addParameter(
							"idrelacionseleccionada", ingreso.getId());
					setReturnActionFordward(request, redirectAVistaRelacion);

				} else {

					// Edición de un ingreso directo
					RelacionEntregaVO ingresoVO = (RelacionEntregaVO) getFromTemporalSession(
							request,
							TransferenciasConstants.INGRESO_DIRECTO_KEY);

					frm.populate(ingresoVO);

					serviceRelaciones.updateRelacion(ingresoVO);
					goBackExecuteLogic(mapping, form, request, response);
				}
			} else {
				obtenerErrores(request, true).add(errors);
				setReturnActionFordward(request,
						mapping.findForward("editar_ingreso"));
			}

		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
			setReturnActionFordward(request,
					mapping.findForward("editar_ingreso"));
		}
	}

	private boolean masDeUnNivelDocumental(HttpServletRequest request,
			GestionCuadroClasificacionBI cuadroBI) {
		boolean ret = false;

		Object obj = getFromTemporalSession(request,
				TransferenciasConstants.LISTA_NIVELES_DOCUMENTALES_KEY);
		if (obj == null)
			obj = cuadroBI
					.getNivelesByTipo(TipoNivelCF.UNIDAD_DOCUMENTAL, null);

		if (obj != null) {
			List nivelesDocumentales = (List) obj;
			if (nivelesDocumentales.size() > 1)
				ret = true;
		}

		return ret;
	}

	/**
	 * Valida el alta o edición de un ingreso directo
	 * 
	 * @param form
	 *            Formulario de la petición
	 * @return ActionErrors Errores encontrados
	 */
	private ActionErrors validarIngresoDirecto(IngresoDirectoForm form,
			HttpServletRequest request, GestionCuadroClasificacionBI cuadroBI) {
		ActionErrors errors = new ActionErrors();

		if (StringUtils.isBlank(form.getIdArchivo())) {
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							Constants.ERROR_REQUIRED,
							Messages.getString(
									TransferenciasConstants.LABEL_ARCHIVO_CUSTODIA,
									request.getLocale())));
		}

		if (StringUtils.isBlank(form.getIdFondo())) {
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(Constants.ERROR_REQUIRED, Messages
							.getString(Constants.ETIQUETA_FONDO_DESTINO,
									request.getLocale())));
		}

		if (StringUtils.isBlank(form.getSerie())) {
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(Constants.ERROR_REQUIRED, Messages
							.getString(Constants.ETIQUETA_SERIE_DESTINO,
									request.getLocale())));
		}

		if (StringUtils.isBlank(form.getIdFormato())) {
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							Constants.ERROR_REQUIRED,
							Messages.getString(
									TransferenciasConstants.LABEL_TRANSFERENCIAS_FORMATO,
									request.getLocale())));
		}

		if (StringUtils.isBlank(form.getFormaDocumental())) {
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							Constants.ERROR_REQUIRED,
							Messages.getString(
									TransferenciasConstants.LABEL_TRANSFERENCIAS_SOPORTE_DOCUMENTAL,
									request.getLocale())));
		}

		if (StringUtils.isBlank(form.getIdDescriptorProductor())) {
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							Constants.ERROR_REQUIRED,
							Messages.getString(
									TransferenciasConstants.LABEL_TRANSFERENCIAS_ORG_PROD,
									request.getLocale())));
		}

		/*
		 * if (ConfiguracionSistemaArchivoFactory
		 * .getConfiguracionSistemaArchivo() .getConfiguracionGeneral()
		 * .getIdNivelFraccionSerie() != null)
		 */

		// Si existe más de un nivel documental y no se ha seleccionado ninguno
		// => error
		if (masDeUnNivelDocumental(request, cuadroBI)) {
			if (StringUtils.isBlank(form.getIdNivelDocumental())) {
				errors.add(
						ActionErrors.GLOBAL_ERROR,
						new ActionError(
								Constants.ERROR_REQUIRED,
								Messages.getString(
										TransferenciasConstants.LABEL_TRANSFERENCIAS_NIVEL_DOCUMENTAL,
										request.getLocale())));
			}
		}

		return errors.size() > 0 ? errors : null;
	}

	/**
	 * Permite obtener los posibles productores a partir de la serie
	 * 
	 * @param mapping
	 *            Objeto Mapping de struts
	 * @param form
	 *            Formulario de la petición
	 * @param request
	 *            Petición actual
	 * @param response
	 *            Objeto respuesta de la petición
	 */
	protected void verPosiblesProductoresExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		// Obtener el repositorio de servicios
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));

		// Obtener el formulario
		IngresoDirectoForm frm = (IngresoDirectoForm) form;

		// Obtener la serie
		String idSerie = frm.getSerie();
		GestionSeriesBI seriesBI = services.lookupGestionSeriesBI();
		List posiblesProductores = seriesBI.getProductoresSerie(idSerie, true);
		request.setAttribute(TransferenciasConstants.LISTA_PRODUCTORES,
				posiblesProductores);

		setReturnActionFordward(request, mappings.findForward("editar_ingreso"));
	}

}