package fondos.model;

import ieci.core.guid.GuidManager;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import se.NotAvailableException;
import se.instituciones.GestorOrganismos;
import se.instituciones.GestorOrganismosFactory;
import se.instituciones.InfoOrgano;
import se.instituciones.TipoAtributo;
import se.instituciones.exceptions.GestorOrganismosException;
import se.procedimientos.GestorCatalogo;
import se.procedimientos.GestorCatalogoFactory;
import se.procedimientos.IOrganoProductor;
import se.procedimientos.IProcedimiento;
import se.procedimientos.InfoBProcedimiento;
import se.procedimientos.exceptions.GestorCatalogoException;
import se.usuarios.AppPermissions;
import se.usuarios.ServiceClient;
import transferencias.TransferenciasElectronicasConstants;
import transferencias.db.IDetallePrevisionDBEntity;
import transferencias.db.IPrevisionDBEntity;
import transferencias.db.IRelacionEntregaDBEntity;
import transferencias.electronicas.serie.InformacionSerie;
import transferencias.electronicas.serie.Procedimiento;
import transferencias.electronicas.serie.Productor;
import transferencias.vos.TransferenciaElectronicaInfo;
import valoracion.db.IValoracionDBEntity;
import valoracion.vos.ValoracionSerieVO;
import xml.config.ConfiguracionFondos;
import xml.config.ConfiguracionGeneral;
import xml.config.ConfiguracionSistemaArchivo;
import xml.config.ConfiguracionSistemaArchivoFactory;
import xml.config.ConfiguracionWsTransferencias;
import xml.config.ListaProductores;
import auditoria.logger.LoggingEvent;

import common.ConfigConstants;
import common.Constants;
import common.Messages;
import common.MultiEntityConstants;
import common.bi.GestionControlUsuariosBI;
import common.bi.GestionCuadroClasificacionBI;
import common.bi.GestionDescripcionBI;
import common.bi.GestionDocumentosElectronicosBI;
import common.bi.GestionSeriesBI;
import common.bi.ServiceBase;
import common.bi.ServiceRepository;
import common.db.ConstraintType;
import common.db.FieldConstraint;
import common.exceptions.ActionNotAllowedException;
import common.exceptions.ArchivoModelException;
import common.exceptions.SecurityException;
import common.exceptions.TooManyResultsException;
import common.exceptions.TransferenciaElectronicaException;
import common.exceptions.UncheckedArchivoException;
import common.pagination.PageInfo;
import common.security.FondosSecurityManager;
import common.util.ArrayUtils;
import common.util.DateUtils;
import common.util.ListUtils;

import deposito.db.IUDocEnUiDepositoDbEntity;
import descripcion.db.IDescriptorDBEntity;
import descripcion.db.IFechaDBEntity;
import descripcion.db.IReferenciaDBEntity;
import descripcion.model.EstadoDescriptor;
import descripcion.model.TipoDescriptor;
import descripcion.model.automaticos.ADReglaGenDatosContants;
import descripcion.model.xml.card.TipoFicha;
import descripcion.model.xml.definition.DefCampoDato;
import descripcion.model.xml.definition.DefFicha;
import descripcion.model.xml.definition.DefFichaFactory;
import descripcion.vos.DescriptorVO;
import descripcion.vos.FechaInicialFinalElementoCF;
import descripcion.vos.InfoBFichaVO;
import descripcion.vos.ProductorElementoCF;
import docelectronicos.db.IDocDocumentoCFDBEntity;
import docelectronicos.vos.IRepositorioEcmVO;
import docelectronicos.vos.InfoBFichaClfVO;
import docelectronicos.vos.InfoBFichaUDocRepEcmUDocsSerie;
import es.archigest.framework.core.exceptions.ArchigestModelException;
import fondos.FondosConstants;
import fondos.actions.FondoPO;
import fondos.actions.FondoToPOTransformer;
import fondos.db.IElementoCuadroClasificacionDbEntity;
import fondos.db.IElementoCuadroClasificacionVistaDBEntity;
import fondos.db.IEntidadProductoraDBEntity;
import fondos.db.IFondoDbEntity;
import fondos.db.INivelCFDBEntity;
import fondos.db.IOrganoProductorDbEntity;
import fondos.db.IProductorSerieDbEntity;
import fondos.db.ISerieDbEntity;
import fondos.db.ISolicitudSerieBusquedasDbEntity;
import fondos.db.ISolicitudSerieDbEntity;
import fondos.db.IUnidadDocumentalDbEntity;
import fondos.db.IUnidadDocumentalVistaDBEntity;
import fondos.db.IVolumenSerieDBEntity;
import fondos.db.SerieDBEntityImpl;
import fondos.db.oracle.ElementoCuadroClasificacionDBEntityImpl;
import fondos.exceptions.FondosOperacionNoPermitidaException;
import fondos.exceptions.ProductorProcedimientoNoIncorporadoException;
import fondos.utils.ProductoresUtils;
import fondos.vos.BusquedaSolicitudesSerieVO;
import fondos.vos.BusquedaUdocsSerieVO;
import fondos.vos.ElementoCuadroClasificacionVO;
import fondos.vos.EntidadProductoraVO;
import fondos.vos.FondoVO;
import fondos.vos.IInfoProductorSerie;
import fondos.vos.INivelCFVO;
import fondos.vos.InfoOrganoProductorSerie;
import fondos.vos.InfoProdVigenteHistorico;
import fondos.vos.InfoProductorSerie;
import fondos.vos.InteresadoUdocVO;
import fondos.vos.NivelFichaUDocRepEcmVO;
import fondos.vos.OpcionesDescripcionSerieVO;
import fondos.vos.OrganoProductorVO;
import fondos.vos.ProductorSerieVO;
import fondos.vos.SerieVO;
import fondos.vos.SolicitudSerieVO;
import fondos.vos.VolumenSerieVO;
import gcontrol.db.ICAOrganoDbEntity;
import gcontrol.db.IListaControlAccesoDbEntity;
import gcontrol.db.IPermisosListaDbEntity;
import gcontrol.model.NivelAcceso;
import gcontrol.model.NombreOrganoFormat;
import gcontrol.model.TipoDestinatario;
import gcontrol.model.TipoListaControlAcceso;
import gcontrol.vos.CAOrganoVO;
import gcontrol.vos.ListaAccesoVO;
import gcontrol.vos.PermisosListaVO;
import gcontrol.vos.UsuarioVO;

/**
 * Implementación de los métodos de servicio de gestión de series documentales
 */
public class GestionSeriesBIImpl extends ServiceBase implements GestionSeriesBI {

	/** Logger de la clase */
	Logger logger = Logger.getLogger(GestionSeriesBIImpl.class);

	IElementoCuadroClasificacionDbEntity _elementoCuadroClasificacionDBEntity = null;
	INivelCFDBEntity _nivelDBEntity = null;
	ISolicitudSerieDbEntity _solicitudSerieDBEntity = null;
	ISerieDbEntity _serieDBEntity = null;
	IEntidadProductoraDBEntity _entidadProductoraDBEntity = null;
	IFondoDbEntity _fondoDBEntity = null;
	IOrganoProductorDbEntity _orgProductorDBEntity = null;
	IProductorSerieDbEntity _productorSerieDBEntity = null;
	IDescriptorDBEntity _descriptorDBEntity = null;
	ICAOrganoDbEntity _caOrganoDbEntity = null;
	IListaControlAccesoDbEntity _listaControlAcceso = null;
	IPermisosListaDbEntity _permisosListaDbEntity = null;
	IValoracionDBEntity _valoracionDbEntity = null;
	IVolumenSerieDBEntity _volumenSerieDbEntity = null;
	ISolicitudSerieBusquedasDbEntity _solicitudSerieBusquedasDbEntity = null;
	IDocDocumentoCFDBEntity _docDocumentoCFDBEntity = null;
	IReferenciaDBEntity _referenciaDBDbEntity = null;
	IFechaDBEntity _fechaDbEntity = null;
	IUnidadDocumentalDbEntity _unidadDocumentalDbEntity = null;
	IUDocEnUiDepositoDbEntity _udocEnUiDepositoDbEntity = null;
	IElementoCuadroClasificacionVistaDBEntity _elementClasificacionVistaDBEntity = null;
	IUnidadDocumentalVistaDBEntity _unidadDocumentalVistaDBEntity = null;
	IDetallePrevisionDBEntity _detallePrevisionDBEntity = null;
	IRelacionEntregaDBEntity _relacionEntregaDBEntity = null;

	public GestionSeriesBIImpl(
			IElementoCuadroClasificacionDbEntity elementoCuadroClasificacionDBEntity,
			INivelCFDBEntity nivelDBEntity,
			ISolicitudSerieDbEntity solicitudSerieDBEntity,
			ISerieDbEntity serieDBEntity,
			IEntidadProductoraDBEntity entidadProductoraDBEntity,
			IFondoDbEntity fondoDBEntity,
			IOrganoProductorDbEntity orgProductorDBEntity,
			IProductorSerieDbEntity productorSerieDBEntity,
			IDescriptorDBEntity descriptorDBEntity,
			ICAOrganoDbEntity caOrganoDbEntity,
			IListaControlAccesoDbEntity listaControlAcceso,
			IPermisosListaDbEntity permisosListaDbEntity,
			IValoracionDBEntity valoracionDbEntity,
			IVolumenSerieDBEntity volumenSerieDbEntity,
			ISolicitudSerieBusquedasDbEntity solicitudSerieBusquedasDbEntity,
			IDocDocumentoCFDBEntity docDocumentoCFDBEntity,
			IReferenciaDBEntity referenciaDBEntity,
			IFechaDBEntity fechaDbEntity,
			IUnidadDocumentalDbEntity unidadDocumentalDbEntity,
			IUDocEnUiDepositoDbEntity udocEnUiDepositoDbEntity,
			IElementoCuadroClasificacionVistaDBEntity elementoCuadroClasificacionVistaDBEntity,
			IUnidadDocumentalVistaDBEntity unidadDocumentalVistaDBEntity,
			IDetallePrevisionDBEntity detallePrevisionDBEntity,
			IRelacionEntregaDBEntity relacionEntregaDBEntity

	) {

		super();

		this._elementoCuadroClasificacionDBEntity = elementoCuadroClasificacionDBEntity;
		this._nivelDBEntity = nivelDBEntity;
		this._solicitudSerieDBEntity = solicitudSerieDBEntity;
		this._serieDBEntity = serieDBEntity;
		this._entidadProductoraDBEntity = entidadProductoraDBEntity;
		this._fondoDBEntity = fondoDBEntity;
		this._orgProductorDBEntity = orgProductorDBEntity;
		this._productorSerieDBEntity = productorSerieDBEntity;
		this._descriptorDBEntity = descriptorDBEntity;
		this._caOrganoDbEntity = caOrganoDbEntity;
		this._listaControlAcceso = listaControlAcceso;
		this._permisosListaDbEntity = permisosListaDbEntity;
		this._valoracionDbEntity = valoracionDbEntity;
		this._volumenSerieDbEntity = volumenSerieDbEntity;
		this._solicitudSerieBusquedasDbEntity = solicitudSerieBusquedasDbEntity;
		this._docDocumentoCFDBEntity = docDocumentoCFDBEntity;
		this._referenciaDBDbEntity = referenciaDBEntity;
		this._fechaDbEntity = fechaDbEntity;
		this._unidadDocumentalDbEntity = unidadDocumentalDbEntity;
		this._udocEnUiDepositoDbEntity = udocEnUiDepositoDbEntity;
		this._elementClasificacionVistaDBEntity = elementoCuadroClasificacionVistaDBEntity;
		this._unidadDocumentalVistaDBEntity = unidadDocumentalVistaDBEntity;
		this._detallePrevisionDBEntity = detallePrevisionDBEntity;
		this._relacionEntregaDBEntity = relacionEntregaDBEntity;
	}

	/**
	 * Obtiene una serie del cuadro de clasificación de fondos documentales
	 *
	 * @param idSerie
	 *            Identificador de serie documental
	 * @return Datos de la serie
	 */
	public SerieVO getSerie(String idSerie) {
		return _serieDBEntity.getSerie(idSerie);
	}

	/**
	 * Obtiene la serie documental que ha sido vinculada con el procedimiento
	 * que se indica
	 *
	 * @param procedimiento
	 *            Identificador de procedimiento
	 * @return Datos de la serie vinculada al procedimiento
	 */
	public SerieVO getSerieProcedimiento(String procedimiento) {
		List seriesProcedimiento = _serieDBEntity
				.getSerieXProcedimiento(procedimiento);
		SerieVO serie = null;
		if (seriesProcedimiento != null && seriesProcedimiento.size() > 0)
			serie = (SerieVO) seriesProcedimiento.get(0);
		return serie;
	}

	/**
	 * Incorpora una nueva serie al cuadro de clasificación de fondos
	 * documentales
	 *
	 * @param elementoPadre
	 *            Clasificador de series donde se crea la series
	 * @param infoAltaSerie
	 *            Datos de serie
	 * @return Datos de la serie creada
	 * @throws ActionNotAllowedException
	 *             Caso de que la incorporación de la serie no esté permitida
	 */
	public SerieVO nuevaSerie(ElementoCuadroClasificacionVO elementoPadre,
			SerieVO infoAltaSerie) throws ActionNotAllowedException {

		// auditoria
		LoggingEvent logEvent = AuditFondos.getLogginEventAlta(this);

		// chequeo de seguridad
		checkPermission(FondosSecurityManager.ALTA_DIRECTA_SERIE);

		iniciarTransaccion();

		IElementoCuadroClasificacion parentElement = (IElementoCuadroClasificacion) elementoPadre;
		ElementoCuadroClasificacionVO elementoCF = (ElementoCuadroClasificacionVO) infoAltaSerie;
		elementoCF.setIdFondo(parentElement.getIdFondo());
		elementoCF.setTipo(TipoNivelCF.SERIE.getIdentificador());
		elementoCF.setCodRefFondo(parentElement.getCodRefFondo());

		INivelCFVO nivelElementoPadre = getGestionCuadroClasificacionBI()
				.getNivelCF(elementoPadre.getIdNivel());

		elementoCF.setFinalCodRefPadre(_elementoCuadroClasificacionDBEntity
				.composeFinalCodRefPadre(nivelElementoPadre,
						elementoPadre.getFinalCodRefPadre(),
						elementoPadre.getCodigo()));

		elementoCF.setCodReferencia(_elementoCuadroClasificacionDBEntity
				.composeCodigoReferencia(nivelElementoPadre,
						elementoPadre.getFinalCodRefPadre(),
						elementoPadre.getCodigo(),
						elementoPadre.getCodRefFondo(),
						infoAltaSerie.getCodigo()));

		elementoCF.setEstado(IElementoCuadroClasificacion.NO_VIGENTE);
		elementoCF.setEditClfDocs(Constants.FALSE_STRING);
		elementoCF.setNivelAcceso(NivelAcceso.PUBLICO);

		SerieVO serieVO = (SerieVO) infoAltaSerie;
		serieVO.setEstadoserie(EstadoSerie.NO_VIGENTE);
		serieVO.setUltimoestadoautoriz(EstadoSerie.SOLICITIDA_ALTA);
		serieVO.setTienedescr(Constants.FALSE_STRING);

		// Si no está activada la distinción de mayúsculas/minúsculas, no hacer
		// el toUpper
		if (!ConfigConstants.getInstance().getDistinguirMayusculasMinusculas())
			serieVO.setTitulo(serieVO.getTitulo().toUpperCase());

		getAuthorizationHelper().verificarPermitidoCrear(elementoPadre,
				infoAltaSerie);

		_elementoCuadroClasificacionDBEntity.insertElementoCF(elementoCF);

		SerieVO serieProxy = _serieDBEntity.insertSerie(infoAltaSerie,
				elementoCF.getId());
		INivelCFVO nivelserieProxy = getGestionCuadroClasificacionBI()
				.getNivelCF(serieProxy.getIdNivel());

		// crear la ficha descriptora para la serie
		getServiceRepository().lookupGestionDescripcionBI().createFicha(
				elementoCF.getId(), TipoFicha.FICHA_ELEMENTO_CF);

		parentElement.addChild((IElementoCuadroClasificacion) serieProxy);

		ElementoCuadroClasificacionVO padre = _elementoCuadroClasificacionDBEntity
				.getElementoCuadroClasificacion(infoAltaSerie.getIdPadre());
		Locale locale = getServiceClient().getLocale();
		AuditFondos.addDataLogAlta(locale, logEvent, serieProxy,
				nivelserieProxy.getNombre(), padre.getCodReferencia());

		commit();

		return serieProxy;
	}

	private String createInfoSolicitudAlta(
			ElementoCuadroClasificacionVO elementoSerie, INivelCFVO nivelSerie,
			ElementoCuadroClasificacionVO clasificadorSerie,
			INivelCFVO nivelClasificadorSerie) {
		StringBuffer buff = new StringBuffer(Constants.XML_HEADER);
		ElementoCuadroClasificacionVO fondo = _elementoCuadroClasificacionDBEntity
				.getElementoCuadroClasificacion(elementoSerie.getIdFondo());
		buff.append("<fondo>")
				.append("<codigo>")
				.append(Constants.addCData(fondo.getCodReferencia()))
				.append("</codigo>")
				.append("<denominacion>")
				.append(Constants.addCData(fondo.getTitulo()))
				.append("</denominacion>")
				.append("</fondo>")
				.append("<clasificador_serie>")
				.append("<codigo>")
				.append(Constants.addCData(clasificadorSerie.getCodReferencia()))
				.append("</codigo>").append("<denominacion>")
				.append(Constants.addCData(clasificadorSerie.getTitulo()))
				.append("</denominacion>").append("<nivel_descriptivo>")
				.append(Constants.addCData(nivelClasificadorSerie.getNombre()))
				.append("</nivel_descriptivo>").append("</clasificador_serie>")
				.append("<serie>").append("<codigo>")
				.append(Constants.addCData(elementoSerie.getCodigo()))
				.append("</codigo>").append("<denominacion>")
				.append(Constants.addCData(elementoSerie.getTitulo()))
				.append("</denominacion>").append("<nivel_descriptivo>")
				.append(Constants.addCData(nivelSerie.getNombre()))
				.append("</nivel_descriptivo>").append("</serie>");
		return buff.toString();
	}

	/**
	 * Crea en el sistema una solicitud de creación de una serie documental con
	 * los datos que se indican para que alguien con los privilegios oportunos
	 * pueda aceptar dicha solicitud y que la serie cuya alta se pide sea
	 * incorporada al sistema
	 *
	 * @param elementoPadre
	 *            Clasificador de series donde se solicita el alta
	 * @param infoSerie
	 *            Datos de serie
	 * @param motivoSolicitud
	 *            Explicación del motivo por el que se solicita el alta de la
	 *            serie
	 * @param idUsrSolititante
	 *            Identificador del usuario que solicita el alta de la serie
	 * @return Datos de la solicitud que se genera
	 * @throws ActionNotAllowedException
	 *             Caso de que no esté permitida la creación de lo solictud que
	 *             se pretende generar
	 */
	public SolicitudSerieVO solicitarAltaDeSerie(
			ElementoCuadroClasificacionVO elementoPadre, SerieVO infoSerie,
			String motivoSolicitud, String idUsrSolititante)
			throws ActionNotAllowedException {

		// Auditoria
		LoggingEvent logEvent = AuditFondos.getLogginEventSolicitudAlta(this);

		checkPermission(FondosSecurityManager.SOLICITUD_ALTA_SERIE_ACTION);

		String codigoSerie = (String) infoSerie.getCodigo();

		getAuthorizationHelper().verificarPermitidoSolicitarAlta(elementoPadre,
				codigoSerie);

		iniciarTransaccion();

		ElementoCuadroClasificacionVO elementoCF = new ElementoCuadroClasificacion();
		elementoCF.setCodigo(codigoSerie);
		elementoCF.setTitulo(infoSerie.getTitulo());
		elementoCF.setIdPadre(infoSerie.getIdPadre());
		String idNivelSerie = infoSerie.getIdNivel();

		INivelCFVO nivelSerie = _nivelDBEntity.getNivelCF(idNivelSerie);
		elementoCF.setIdNivel(idNivelSerie);
		elementoCF.setIdFichaDescr(nivelSerie.getIdFichaDescrPref());

		elementoCF.setTipo(TipoNivelCF.SERIE.getIdentificador());

		IElementoCuadroClasificacion parentElement = (IElementoCuadroClasificacion) elementoPadre;

		elementoCF.setIdFondo(parentElement.getIdFondo());

		String codigoReferenciaFondo = parentElement.getCodRefFondo();
		elementoCF.setCodRefFondo(codigoReferenciaFondo);

		INivelCFVO nivelClasificadorSerie = getGestionCuadroClasificacionBI()
				.getNivelCF(parentElement.getIdNivel());
		String finalCodRefPadre = _elementoCuadroClasificacionDBEntity
				.composeFinalCodRefPadre(nivelClasificadorSerie,
						parentElement.getFinalCodRefPadre(),
						parentElement.getCodigo());
		String codigoReferencia = _elementoCuadroClasificacionDBEntity
				.composeCodigoReferencia(nivelClasificadorSerie,
						parentElement.getFinalCodRefPadre(),
						parentElement.getCodigo(),
						parentElement.getCodRefFondo(), codigoSerie);

		elementoCF.setFinalCodRefPadre(finalCodRefPadre);

		elementoCF.setCodReferencia(codigoReferencia);

		// Las series no tienen idArchivo
		// elementoCF.setIdArchivo(elementoPadre.getIdArchivo());
		elementoCF.setTienedescr(Constants.FALSE_STRING);

		// Si no está activada la distinción de mayúsculas/minúsculas, no hacer
		// el toUpper
		if (!ConfigConstants.getInstance().getDistinguirMayusculasMinusculas())
			elementoCF.setTitulo(elementoCF.getTitulo().toUpperCase());

		elementoCF = _elementoCuadroClasificacionDBEntity
				.insertElementoCF(elementoCF);

		SolicitudSerieVO solicitudSerie = nuevaSolicitud(
				elementoCF,
				createInfoSolicitudAlta(elementoCF, nivelSerie, elementoPadre,
						nivelClasificadorSerie), SolicitudSerie.SOLICITUD_ALTA,
				motivoSolicitud, idUsrSolititante);

		_solicitudSerieDBEntity.insertSolicitud(solicitudSerie);

		// Auditoria
		Locale locale = getServiceClient().getLocale();
		AuditFondos.addDataLogSolicitudSerie(locale, logEvent, elementoCF,
				solicitudSerie.getInfo());

		commit();

		return solicitudSerie;

	}

	/**
	 * Obtiene los datos de las series documentales cuyos identificadores se
	 * suministran
	 *
	 * @param serieIDs
	 *            Conjunto de identificadores de series
	 * @return Lista de series documentales {@link SerieVO}
	 */
	public List getSeries(String[] serieIDs) {
		return _serieDBEntity.getSeries(serieIDs);
	}

	/**
	 * Obtiene las series de un fondo documental
	 *
	 * @param idFondo
	 *            Identificador de fondo
	 * @return Lista de series del fondo {@link SerieVO}
	 */
	public List getSeriesFondo(String idFondo) {
		return _serieDBEntity.getSeriesXFondo(idFondo);
	}

	/*
	 * (sin Javadoc)
	 *
	 * @see
	 * common.bi.GestionSeriesBI#getCountSolicitudesGestor(java.lang.String)
	 */
	public int getCountSolicitudesGestor(String iduser) {
		int solicitudesAlta = _solicitudSerieDBEntity.getCountSolicitudes(
				iduser, null, null);
		return solicitudesAlta;
	}

	/*
	 * (sin Javadoc)
	 *
	 * @see common.bi.GestionSeriesBI#getSolicitudesGestor(java.lang.String)
	 */
	public List getSolicitudesGestor(String iduser) {
		List solicitudesAlta = _solicitudSerieDBEntity.getSolicitudes(iduser,
				null, null);
		return solicitudesAlta;
	}

	/**
	 * Obtiene los detalles de una solicitud de autorización de una acción de la
	 * gestión de series
	 *
	 * @return Datos de la solicitud
	 */
	public SolicitudSerieVO getDetalleSolicitud(String idSolicitud) {
		SolicitudSerieVO solicitud = _solicitudSerieDBEntity
				.getSolicitud(idSolicitud);
		return solicitud;
	}

	/**
	 * Obtiene los detalles de una solicitud de autorización de una acción de la
	 * gestión de series
	 *
	 * @return Datos de la solicitud
	 */
	public SolicitudSerieVO abrirSolicitud(String idSolicitud) {
		SolicitudSerieVO solicitud = _solicitudSerieDBEntity
				.getSolicitud(idSolicitud);
		ElementoCuadroClasificacionVO clasificadorSeries = _elementoCuadroClasificacionDBEntity
				.getElementoPadre(solicitud.getIdSerie());
		if (clasificadorSeries != null)
			solicitud.setPuedeSerAutorizada(clasificadorSeries.isVigente());
		else
			solicitud.setPuedeSerAutorizada(false);

		return solicitud;
	}

	/**
	 * Aceptar una solicitud de autorización para la realización de una accion
	 * de gestión de series. La aceptación de la solicitud supone la ejecución
	 * de la acción solicitada
	 *
	 * @param solicitud
	 *            Solicitud que se autoriza
	 * @param idGestor
	 *            Identificador del usuario que autoriza la solicitud
	 * @param infoAutorizacion
	 *            Información adicional suministrada para la autorización de la
	 *            solicitud
	 * @return Datos de la solicitud autorizada
	 * @throws FondosOperacionNoPermitidaException
	 *             Caso de la que autorización de la solicitud no sea posible
	 */
	public SolicitudSerieVO autorizarSolicitud(SolicitudSerieVO solicitud,
			String idGestor, Map infoAutorizacion)
			throws ActionNotAllowedException {
		// obtener el elementoCF creado durante la solicitud
		ElementoCuadroClasificacionVO elementoCF = _elementoCuadroClasificacionDBEntity
				.getElementoCuadroClasificacion(solicitud.getIdSerie());

		ServiceRepository services = ServiceRepository
				.getInstance(getServiceSession());
		GestionDescripcionBI descripcionBI = null;

		// Auditoria
		LoggingEvent logEvent = AuditFondos
				.getLogginEventAutorizarDenegarSolicitudAlta(this);
		Locale locale = getServiceClient().getLocale();
		AuditFondos.addDataLogAutorizarSolicitudSerie(locale, logEvent,
				elementoCF, solicitud.getInfo());

		checkPermission(FondosSecurityManager.GESTION_SOLICITUDES_SERIE_ACTION);

		iniciarTransaccion();
		if (solicitud.isTipoSolicitudAlta()) {
			// /crear la serie en la tabla de series
			SerieVO nuevaSerie = new Serie(elementoCF);
			nuevaSerie.setIdusrgestor((String) infoAutorizacion
					.get(Serie.GESTOR_BEAN_PROPERTY));
			nuevaSerie.setEstadoserie(EstadoSerie.NO_VIGENTE);
			nuevaSerie.setUltimoestadoautoriz(EstadoSerie.SOLICITIDA_ALTA);
			nuevaSerie.setInfoFichaUDocRepEcm((String) infoAutorizacion
					.get(Serie.INFO_FICHA_UDOC_REP_ECM_BEAN_PROPERTY));

			nuevaSerie = _serieDBEntity.insertSerie(nuevaSerie,
					elementoCF.getId());

			elementoCF.setIdFichaDescr((String) infoAutorizacion
					.get(Serie.IDFICHADESCR_BEAN_PROPERTY));
			elementoCF.setIdRepEcm((String) infoAutorizacion
					.get(Serie.REPOSITORIO_ECM_BEAN_PROPERTY));
			elementoCF.setEstado(IElementoCuadroClasificacion.NO_VIGENTE);
			elementoCF.setTienedescr(Constants.TRUE_STRING);
			_elementoCuadroClasificacionDBEntity
					.updateElementoCuadroClasificacion(elementoCF);

			descripcionBI = services.lookupGestionDescripcionBI();
			descripcionBI.createFicha(nuevaSerie.getId(),
					TipoFicha.FICHA_ELEMENTO_CF);
		} else {
			SerieVO serie = getSerie(solicitud.getIdSerie());
			switch (solicitud.getTipo()) {

			case SolicitudSerie.CAMBIOS_EN_IDENTIFICACION:
				String listaControlAccesoSerie = serie.getIdLCA();
				if (!StringUtils.isBlank(listaControlAccesoSerie))
					actualizarAccesoSerie(listaControlAccesoSerie,
							_orgProductorDBEntity
									.getOrganosProductoresSerie(serie.getId()));
				// _serieDBEntity.updateEstadoSerie(solicitud.getIdSerie(),
				// serie.getUltimoestadoautoriz(), serie.getEstadoserie());
				_serieDBEntity.updateEstadoSerie(solicitud.getIdSerie(),
						EstadoSerie.VIGENTE, serie.getEstadoserie());
				setMarcaProductores(solicitud.getIdSerie(),
						FondosConstants.MARCA_VACIO);
				descripcionBI = services.lookupGestionDescripcionBI();
				descripcionBI
						.generarAutomaticos(
								serie.getId(),
								TipoFicha.FICHA_ELEMENTO_CF,
								Collections
										.singletonMap(
												ADReglaGenDatosContants.ACCIONES,
												new int[] { ADReglaGenDatosContants.ACTUALIZAR_PRODUCTORES }));

				_elementoCuadroClasificacionDBEntity.updateEstadoElementoCF(
						solicitud.getIdSerie(),
						IElementoCuadroClasificacion.VIGENTE);
				break;

			case SolicitudSerie.PASO_A_VIGENTE:
				performPasoAVigente(serie);
				break;

			case SolicitudSerie.PASO_A_HISTORICA:
				_serieDBEntity.updateEstadoSerie(solicitud.getIdSerie(),
						EstadoSerie.HISTORICA, serie.getEstadoserie());
			}
		}
		_solicitudSerieDBEntity.saveAutorizacionSolicitud(solicitud.getId(),
				idGestor);

		commit();

		solicitud.setEstado(SolicitudSerie.ESTADO_EJECUTADA);
		solicitud.setIdUsrGestor(idGestor);

		return solicitud;
	}

	public void actualizarAccesoOrganoProductor(String idLCA,
			String idOrganoProductor) {
		PermisosListaVO permisosDeLaLista = new PermisosListaVO();
		permisosDeLaLista.setIdListCA(idLCA);
		permisosDeLaLista.setTipoDest(TipoDestinatario.ORGANO);
		permisosDeLaLista.setIdDest(idOrganoProductor);
		permisosDeLaLista.setPerm(new Integer(
				AppPermissions.CONSULTA_DESCRIPCION_CUADRO_CLASIFICACION)
				.intValue());

		PermisosListaVO permisoVO = _permisosListaDbEntity
				.getPermisosListaVO(permisosDeLaLista);
		if (permisoVO == null)
			_permisosListaDbEntity.insertPermisosListaVO(permisosDeLaLista);

	}

	/**
	 * Actualiza la composición de la lista de control de acceso que restringe
	 * el acceso a la documentación de una serie documental a partir de la
	 * información introducida en el proceso de identificación de la serie de
	 * manera que únicamente los órganos identificados como productores en vigor
	 * de la serie tengan acceso a ella
	 *
	 * @param serie
	 *            Serie para la que se actualiza la información de acceso
	 */
	private void actualizarAccesoSerie(String listaControlAccesoSerie,
			List listaProductoresConAcceso) {
		ServiceRepository services = ServiceRepository
				.getInstance(getServiceSession());
		GestionControlUsuariosBI controlAccesoBI = services
				.lookupGestionControlUsuariosBI();
		List organosConAcceso = controlAccesoBI
				.getOrganosEnListaAcceso(listaControlAccesoSerie);
		CollectionUtils.transform(organosConAcceso, new Transformer() {
			public Object transform(Object obj) {
				return ((CAOrganoVO) obj).getIdOrg();
			}
		});
		// List productoresSerie =
		// _orgProductorDBEntity.getOrganosProductoresSerie(serie.getId());
		List productoresSerie = new ArrayList(listaProductoresConAcceso);
		CollectionUtils.transform(productoresSerie, new Transformer() {
			public Object transform(Object obj) {
				return ((OrganoProductorVO) obj).getIdOrgano();
			}
		});
		if (organosConAcceso == null) {
			organosConAcceso = new ArrayList();
		}

		List productoresSinAcceso = new ArrayList(organosConAcceso);
		productoresSinAcceso.removeAll(productoresSerie);
		if (productoresSinAcceso.size() > 0)
			_permisosListaDbEntity.deletePermisosLista(listaControlAccesoSerie,
					TipoDestinatario.ORGANO, (String[]) productoresSinAcceso
							.toArray(ArrayUtils.EMPTY_STRING_ARRAY));
		List productoresToAdd = new ArrayList(productoresSerie);
		productoresToAdd.removeAll(organosConAcceso);

		for (Iterator i = productoresToAdd.iterator(); i.hasNext();) {
			PermisosListaVO permisosDeLaLista = new PermisosListaVO();
			permisosDeLaLista.setIdListCA(listaControlAccesoSerie);
			permisosDeLaLista.setTipoDest(TipoDestinatario.ORGANO);
			permisosDeLaLista.setIdDest((String) i.next());
			permisosDeLaLista.setPerm(new Integer(
					AppPermissions.CONSULTA_DESCRIPCION_CUADRO_CLASIFICACION)
					.intValue());

			_permisosListaDbEntity.insertPermisosListaVO(permisosDeLaLista);
		}
	}

	/**
	 * Autoriza posibles cambios que se hayan podido producir en la
	 * identificación de una serie documental
	 *
	 * @param idSerie
	 *            Identificador de serie documental
	 * @throws FondosOperacionNoPermitidaException
	 *             Caso de que la autorización de cambios en la identificación
	 *             de la serie no esté permitida
	 */
	public void autorizarCambiosEnIdentificacion(String idSerie)
			throws FondosOperacionNoPermitidaException {
		checkPermission(FondosSecurityManager.SOLICITUD_Y_AUTORIZACION_AUTOMATICA_CAMBIOS_ACTION);

		// Auditoria
		LoggingEvent logEvent = AuditFondos
				.getLogginEventGestionSolicitudesSerie(this);

		SerieVO serie = getSerie(idSerie);

		// Auditoria
		Locale locale = getServiceClient().getLocale();
		AuditFondos.addDataLogAutorizarSolicitudSerie(locale, logEvent, serie,
				"AUTORIZACION DIRECTA");

		getAuthorizationHelper().verificarPermitidoPasoAVigente(serie);
		iniciarTransaccion();
		String listaControlAccesoSerie = serie.getIdLCA();
		if (!StringUtils.isBlank(listaControlAccesoSerie))
			actualizarAccesoSerie(listaControlAccesoSerie,
					_orgProductorDBEntity.getOrganosProductoresSerie(serie
							.getId()));
		_serieDBEntity.updateEstadoSerie(serie.getId(),
				serie.getUltimoestadoautoriz(), serie.getEstadoserie());
		_elementoCuadroClasificacionDBEntity.updateEstadoElementoCF(
				serie.getId(), IElementoCuadroClasificacion.VIGENTE);
		_productorSerieDBEntity.setMarcaProductores(idSerie,
				FondosConstants.MARCA_VACIO);
		commit();
	}

	public void setMarcaProductores(String idSerie, int marca) {
		_productorSerieDBEntity.setMarcaProductores(idSerie, marca);
	}

	/**
	 * Rechaza una solicitud de autorización para la realización de una accion
	 * de gestión de series.
	 *
	 * @param solicitud
	 *            Solicitud que se autoriza
	 * @param motivo
	 *            Motivo por el que se rechaza la solicitud
	 * @param idGestor
	 *            Identificador del usuario que rechaza la solcitud
	 * @throws FondosOperacionNoPermitidaException
	 *             Caso de la que el rechazo de la solicitud no esté permitido
	 */
	public void rechazarSolicitud(SolicitudSerieVO solicitud, String motivo,
			String idGestor) {
		checkPermission(FondosSecurityManager.GESTION_SOLICITUDES_SERIE_ACTION);
		Locale locale = getServiceClient().getLocale();

		LoggingEvent logEvent = null;
		iniciarTransaccion();

		if (solicitud.isTipoSolicitudAlta()) {
			// Auditoria
			logEvent = AuditFondos
					.getLogginEventAutorizarDenegarSolicitudAlta(this);
			ElementoCuadroClasificacionVO elemento = _elementoCuadroClasificacionDBEntity
					.getElementoCuadroClasificacion(solicitud.getIdSerie());
			AuditFondos
					.addDataLogDenegarSolicitudSerie(locale, logEvent,
							elemento, solicitud.getInfo(),
							solicitud.getMotivoRechazo());

			_elementoCuadroClasificacionDBEntity.deleteElementoCF(solicitud
					.getIdSerie());

		} else {
			// Auditoria
			Date fechaEstadoSerie = null;
			switch (solicitud.getTipo()) {
			case SolicitudSerie.PASO_A_HISTORICA:
				if (StringUtils.isNotBlank(solicitud.getInfo())) {
					SAXReader saxReader = new SAXReader();
					try {
						Document infoSolicitud = saxReader
								.read(new StringReader(solicitud.getInfo()));
						fechaEstadoSerie = DateUtils.getTimestamp(infoSolicitud
								.valueOf("fecha_estado"));
					} catch (DocumentException de) {
						throw new UncheckedArchivoException(de);
					}
				}
			}
			logEvent = AuditFondos.getLogginEventGestionSolicitudesSerie(this);
			SerieVO serie = _serieDBEntity.getSerie(solicitud.getIdSerie());

			AuditFondos.addDataLogDenegarSolicitudSerie(locale, logEvent,
					serie, solicitud.getInfo(), solicitud.getMotivoRechazo());
			// _serieDBEntity.updateEstadoSerie(solicitud.getIdSerie(),
			// serie.getUltimoestadoautoriz(), EstadoSerie.VIGENTE);

			// _serieDBEntity.setUltimoEstadoSerie(solicitud.getIdSerie(),
			// EstadoSerie.VIGENTE, fechaEstadoSerie);
			_serieDBEntity.setEstadoSerie(solicitud.getIdSerie(),
					serie.getUltimoestadoautoriz(), EstadoSerie.VIGENTE,
					fechaEstadoSerie);

		}
		_solicitudSerieDBEntity.saveRechazoSolicitud(solicitud.getId(),
				idGestor, motivo);

		commit();

		solicitud.setEstado(SolicitudSerie.ESTADO_DENEGADA);
	}

	private SolicitudSerieVO nuevaSolicitud(
			ElementoCuadroClasificacionVO serie, String xinfo,
			int tipoSolicitud, String motivo, String idSolicitante) {
		SolicitudSerieVO solicitudSerie = new SolicitudSerie(tipoSolicitud);
		solicitudSerie.setIdSerie(serie.getId());
		StringBuffer etiquetaSerie = new StringBuffer(serie.getCodReferencia())
				.append(" - ").append(serie.getTitulo());
		solicitudSerie.setEtiquetaSerie(etiquetaSerie.toString());
		solicitudSerie.setEstado(SolicitudSerie.ESTADO_SOLICITADA);
		solicitudSerie.setMotivoSolicitud(motivo);
		solicitudSerie.setIdUsrSolicitante(idSolicitante);
		Date currentDate = DateUtils.getFechaActual();
		solicitudSerie.setFecha(currentDate);
		solicitudSerie.setFechaEstado(currentDate);
		solicitudSerie.setInfo(xinfo);
		return solicitudSerie;
	}

	/**
	 * Solicita el paso a vigente de una serie documental
	 *
	 * @param idSerie
	 *            Identificador de la serie para la que se realiza la solicitud
	 * @param motivo
	 *            Motivo por el que se realiza la solicitud
	 * @param idSolicitante
	 *            Identificador del usuario que realiza la solicitud
	 * @return Solicitud generada
	 * @throws FondosOperacionNoPermitidaException
	 *             Caso de que la solicitud de paso a vigente de la serie no
	 *             esté permitida
	 */
	public SolicitudSerieVO solicitarPasoAVigente(String idSerie,
			String motivo, String idSolicitante)
			throws FondosOperacionNoPermitidaException {

		// Auditoria
		LoggingEvent logEvent = AuditFondos
				.getLogginEventSolicitudCambiosEstadoOModif(this);
		Locale locale = getServiceClient().getLocale();

		SerieVO serie = _serieDBEntity.getSerie(idSerie);

		checkPermission(FondosSecurityManager.SOLICITUD_CAMBIOS_ESTADO_O_MODIF_ACTION);

		getAuthorizationHelper().verificarPermitidoSolicitarPasoAVigente(serie);

		iniciarTransaccion();

		SolicitudSerieVO solicitudSerie = nuevaSolicitud(serie, null,
				SolicitudSerie.PASO_A_VIGENTE, motivo, idSolicitante);

		// Auditoria
		AuditFondos.addDataLogSolicitudSerie(locale, logEvent, serie,
				solicitudSerie.getInfo());

		_solicitudSerieDBEntity.insertSolicitud(solicitudSerie);

		// Auditoria
		logEvent = AuditFondos.getLogginEventSolicitudCambiosEstadoOModif(this);
		AuditFondos.addDataLogCambiosEstadoSerie(locale, logEvent, serie,
				serie.getEstadoserie(), EstadoSerie.PENDIENTE_VIGENTE);

		_serieDBEntity.updateEstadoSerie(idSerie,
				EstadoSerie.PENDIENTE_VIGENTE, serie.getEstadoserie());

		commit();
		return solicitudSerie;
	}

	private String createInfoSolicitudPasoHistorica(Date fechaEstadoSerie) {
		if (fechaEstadoSerie != null) {
			StringBuffer buffer = new StringBuffer(Constants.XML_HEADER);
			buffer.append("<fecha_estado>")
					.append(DateUtils.formatTimestamp(fechaEstadoSerie))
					.append("</fecha_estado>");
			return buffer.toString();
		} else
			return null;
	}

	public SolicitudSerieVO solicitarPasoAHistorica(String idSerie,
			String motivo, String idSolicitante)
			throws ActionNotAllowedException {

		// Auditoria
		LoggingEvent logEvent = AuditFondos
				.getLogginEventSolicitudCambiosEstadoOModif(this);
		Locale locale = getServiceClient().getLocale();

		SerieVO serie = _serieDBEntity.getSerie(idSerie);

		checkPermission(AppPermissions.GESTOR_SERIE);

		getAuthorizationHelper().verificarPermitidoSolicitarPasoAHistorica(
				serie);

		SolicitudSerieVO solicitudSerie = nuevaSolicitud(serie,
				createInfoSolicitudPasoHistorica(serie.getFechaestado()),
				SolicitudSerie.PASO_A_HISTORICA, motivo, idSolicitante);

		iniciarTransaccion();

		// Auditoria
		AuditFondos.addDataLogSolicitudSerie(locale, logEvent, serie,
				solicitudSerie.getInfo());

		_solicitudSerieDBEntity.insertSolicitud(solicitudSerie);

		// Auditoria
		logEvent = AuditFondos.getLogginEventSolicitudCambiosEstadoOModif(this);
		AuditFondos.addDataLogCambiosEstadoSerie(locale, logEvent, serie,
				serie.getEstadoserie(), EstadoSerie.PENDIENTE_HISTORICA);

		_serieDBEntity.updateEstadoSerie(idSerie,
				EstadoSerie.PENDIENTE_HISTORICA, serie.getEstadoserie());

		commit();
		return solicitudSerie;
	}

	/**
	 * Solicita la autorización de los cambios realizados en la identificación
	 * de una serie documental
	 *
	 * @param idSerie
	 *            Identificador de la serie para la que se realiza la solicitud
	 * @param motivo
	 *            Motivo por el que se realiza la solicitud
	 * @param idSolicitante
	 *            Identificador del usuario que realiza la solicitud
	 * @return Solicitud generada
	 * @throws FondosOperacionNoPermitidaException
	 *             Caso de que la solicitud de autorización de cambios en
	 *             identificación no esté permitida
	 */
	public SolicitudSerieVO solicitarAutorizacionCambios(String idSerie,
			String motivo, String idSolicitante)
			throws FondosOperacionNoPermitidaException {

		// Auditoria
		LoggingEvent logEvent = AuditFondos
				.getLogginEventSolicitudCambiosEstadoOModif(this);
		Locale locale = getServiceClient().getLocale();

		try {
			checkPermission(FondosSecurityManager.SOLICITUD_CAMBIOS_IDENTIFICACION_ACTION);

			SerieVO serie = _serieDBEntity.getSerie(idSerie);

			getAuthorizationHelper()
					.verificarPermitidoSolicitarAutorizacionCambios(serie);

			SolicitudSerieVO solicitudSerie = nuevaSolicitud(serie, null,
					SolicitudSerie.CAMBIOS_EN_IDENTIFICACION, motivo,
					idSolicitante);

			// Auditoria
			AuditFondos.addDataLogSolicitudSerie(locale, logEvent, serie,
					solicitudSerie.getInfo());

			iniciarTransaccion();

			_solicitudSerieDBEntity.insertSolicitud(solicitudSerie);

			_serieDBEntity.updateEstadoSerie(serie.getId(),
					EstadoSerie.PENDIENTE_AUTORIZACION_CAMBIOS,
					serie.getEstadoserie());

			// Auditoria
			logEvent = AuditFondos
					.getLogginEventSolicitudCambiosEstadoOModif(this);
			AuditFondos.addDataLogCambiosEstadoSerie(locale, logEvent, serie,
					serie.getEstadoserie(),
					EstadoSerie.PENDIENTE_AUTORIZACION_CAMBIOS);

			commit();

			serie.setEstadoserie(EstadoSerie.PENDIENTE_AUTORIZACION_CAMBIOS);

			return solicitudSerie;
		} catch (SecurityException e) {
			throw e;
		}
	}

	private boolean tienenMismaLCA(List listaProductores) {
		String listaAcceso = null;
		if (!ListUtils.isEmpty(listaProductores)) {
			for (int i = 0; i < listaProductores.size(); i++) {
				ProductorSerieVO productorSerieVO = (ProductorSerieVO) listaProductores
						.get(i);
				if (!common.util.StringUtils.isEmpty(productorSerieVO
						.getIdLCAPref())) {
					if (listaAcceso == null) {
						listaAcceso = productorSerieVO.getIdLCAPref();
					} else {
						if (!productorSerieVO.getIdLCAPref()
								.equals(listaAcceso)) {
							return false;
						}
					}

				}
			}
		}
		return true;

	}

	private String getFirstLCA(List listaProductores) {

		if (!ListUtils.isEmpty(listaProductores)) {
			for (int i = 0; i < listaProductores.size(); i++) {
				ProductorSerieVO productorSerieVO = (ProductorSerieVO) listaProductores
						.get(i);
				if (!common.util.StringUtils.isEmpty(productorSerieVO
						.getIdLCAPref()))
					return productorSerieVO.getIdLCAPref();
			}
		}
		return null;
	}

	private void performPasoAVigente(SerieVO serie)
			throws ActionNotAllowedException {
		iniciarTransaccion();
		getAuthorizationHelper().verificarPermitidoPasoAVigente(serie);
		String idSerie = serie.getId();

		List listaProductoresVigentes = _productorSerieDBEntity
				.getProductoresVigentesBySerie(idSerie);

		// boolean
		// creadaLCA=crearLCASinProductoresVigentes(listaProductoresVigentes,idSerie);

		if (!ListUtils.isEmpty(listaProductoresVigentes)) {
			for (int i = 0; i < listaProductoresVigentes.size(); i++) {
				ProductorSerieVO productorSerieVO = (ProductorSerieVO) listaProductoresVigentes
						.get(i);

				OrganoProductorVO organoProductorVO = _orgProductorDBEntity
						.getOrgProductorXIdDescr(productorSerieVO
								.getIdProductor());

				if (organoProductorVO != null) {
					crearListaAcceso(organoProductorVO, serie);
				} else {
					crearListaAcceso(productorSerieVO.getNombre(),
							productorSerieVO.getIdProductor(), serie);
				}
			}
		}

		_serieDBEntity.updateEstadoSerie(idSerie, EstadoSerie.VIGENTE,
				EstadoSerie.NO_VIGENTE);
		ServiceRepository services = ServiceRepository
				.getInstance(getServiceSession());
		GestionDescripcionBI descripcionBI = services
				.lookupGestionDescripcionBI();
		descripcionBI
				.generarAutomaticos(
						serie.getId(),
						TipoFicha.FICHA_ELEMENTO_CF,
						Collections
								.singletonMap(
										ADReglaGenDatosContants.ACCIONES,
										new int[] { ADReglaGenDatosContants.ACTUALIZAR_PRODUCTORES }));

		_elementoCuadroClasificacionDBEntity.updateEstadoElementoCF(idSerie,
				IElementoCuadroClasificacion.VIGENTE);
		commit();
	}

	/**
	 * Paso de una serie a estado 'Vigente
	 *
	 * @param idSerie
	 *            Identificador de serie del cuadro de clasificación de fondos
	 *            documentales
	 * @throws ActionNotAllowedException
	 *             Caso de que el paso a vigente de la serie no esté permitido
	 */
	public void serieVigente(String idSerie) throws ActionNotAllowedException {
		iniciarTransaccion();
		SerieVO serie = getSerie(idSerie);
		performPasoAVigente(serie);
		commit();
	}

	/**
	 * Pasa una serie documental a estado 'En Estudio'
	 *
	 * @param idSerie
	 *            Identificador de serie documental
	 * @param gestorSerie
	 *            Identificador del usuario a establecer como gestor de la serie
	 *            y por tanto encargado de realizar el estudio sobre la serie
	 * @throws FondosOperacionNoPermitidaException
	 *             Caso de que el paso de la serie a 'En Estudio' no esté
	 *             permitido
	 */
	public void serieEnEstudio(String idSerie, String gestorSerie)
			throws FondosOperacionNoPermitidaException {

		// Auditoria
		LoggingEvent logEvent = AuditFondos
				.getLogginEventModificacionSerie(this);
		Locale locale = getServiceClient().getLocale();

		checkPermission(FondosSecurityManager.PASO_A_EN_ESTUDIO_ACTION);

		SerieVO serie = _serieDBEntity.getSerie(idSerie);

		// Auditoria
		AuditFondos.addDataLogCambiosEstadoSerie(locale, logEvent, serie,
				serie.getEstadoserie(), EstadoSerie.EN_ESTUDIO);

		getAuthorizationHelper().verificarPermitidoPasoAEstudio(serie);
		iniciarTransaccion();

		_serieDBEntity.updateGestor(idSerie, gestorSerie);
		_serieDBEntity.updateEstadoSerie(idSerie, EstadoSerie.EN_ESTUDIO,
				serie.getEstadoserie());

		GestionControlUsuariosBI userService = getServiceRepository()
				.lookupGestionControlUsuariosBI();
		UsuarioVO userInicial = userService.getUsuario(serie.getIdusrgestor());
		UsuarioVO userFinal = userInicial;
		if (!StringUtils.equals(serie.getIdusrgestor(), gestorSerie))
			userFinal = userService.getUsuario(gestorSerie);
		// Auditoria
		logEvent = AuditFondos.getLogginEventModificacionSerie(this);
		AuditFondos.addDataLogCambiosUsrGestorSerie(locale, logEvent, serie,
				userInicial.getNombre(), userInicial.getId(),
				userFinal.getNombre(), userFinal.getId());

		commit();
	}

	/**
	 * Pasa una serie a estado 'Histórica'
	 *
	 * @param idSerie
	 *            Identificador de serie
	 * @throws ActionNotAllowedException
	 *             Caso de que el paso de la serie a estado 'Histórica' no esté
	 *             permitido
	 */
	public void serieHistorica(String idSerie) throws ActionNotAllowedException {
		checkPermission(AppPermissions.GESTION_SOLICITUDES_SERIE);
		SerieVO serie = _serieDBEntity.getSerie(idSerie);
		getAuthorizationHelper().verificarPermitidoSolicitarPasoAHistorica(
				serie);

		// Auditoria
		Locale locale = getServiceClient().getLocale();
		LoggingEvent logEvent = AuditFondos
				.getLogginEventModificacionSerie(this);
		AuditFondos.addDataLogCambiosEstadoSerie(locale, logEvent, serie,
				serie.getEstadoserie(), EstadoSerie.HISTORICA);
		iniciarTransaccion();
		_serieDBEntity.updateEstadoSerie(idSerie, EstadoSerie.HISTORICA,
				serie.getEstadoserie());
		commit();
	}

	/**
	 * Obtiene las series que tiene al usuario indicado como gestor
	 *
	 * @param idGestor
	 *            Identificador de usuario
	 * @return Lista de series de las que es gestor el usuario que se indica
	 *         {@link SerieVO}
	 */
	public List findSeriesByGestor(String idGestor) {
		return _serieDBEntity.getSeriesXGestor(idGestor, null);
	}

	private List createSearchConditions(String fondoID, String codigo,
			String titulo) {
		List searchConditions = new ArrayList();
		if (StringUtils.isNotEmpty(fondoID))
			searchConditions.add(new FieldConstraint(
					SerieDBEntityImpl.CAMPO_IDFONDO, ConstraintType.EQUAL,
					fondoID));
		if (StringUtils.isNotEmpty(codigo))
			searchConditions
					.add(new FieldConstraint(SerieDBEntityImpl.CODIGO_FIELD,
							ConstraintType.LIKE, codigo));
		if (StringUtils.isNotEmpty(titulo))
			searchConditions.add(new FieldConstraint(
					SerieDBEntityImpl.TITULO_FIELD,
					SerieDBEntityImpl.IDXTITULO_FIELD, ConstraintType.CONTAINS,
					titulo));
		return searchConditions;
	}

	/**
	 * Obtiene las series que verifican las condiciones especificadas:
	 * Pertenecen al fondo indicado y su código y título contienen los patrones
	 * indicados
	 *
	 * @param fondoID
	 *            Identificador de fondo al que deben pertenecer las series.
	 *            Puede ser nulo
	 * @param codigo
	 *            Patrón que debe estar presente en el código de las series a
	 *            devolver
	 * @param titulo
	 *            Patrón que debe estar presente en el título de las series a
	 *            devolver
	 * @return Lista de series que verifican las condiciones {@link SerieVO}
	 */
	public List findSeries(String fondoID, String codigo, String titulo) {
		return _serieDBEntity.findSeries(createSearchConditions(fondoID,
				codigo, titulo));
	}

	public List getSeriesPorEstados(String fondo, String codigo, String titulo,
			int[] estados) {
		return _serieDBEntity.findSeriesInEstados(
				createSearchConditions(fondo, codigo, titulo), estados);
	}

	public List getSeriesActivas(String fondo, String codigo, String titulo) {
		int[] estados = { EstadoSerie.VIGENTE, EstadoSerie.PENDIENTE_HISTORICA,
				EstadoSerie.HISTORICA };
		return _serieDBEntity.findSeriesInEstados(
				createSearchConditions(fondo, codigo, titulo), estados);
	}

	public List findSeriesValorables(String fondoID, String codigo,
			String titulo) {
		int[] estadosSerie = { EstadoSerie.VIGENTE, EstadoSerie.HISTORICA };

		List searchConditions = createSearchConditions(fondoID, codigo, titulo);
		if (getServiceClient() != null)
			searchConditions.add(new FieldConstraint(
					SerieDBEntityImpl.CAMPO_IDUSRGESTOR, ConstraintType.EQUAL,
					getServiceClient().getId()));
		return _serieDBEntity.findSeriesValorables(searchConditions,
				estadosSerie);
	}

	/**
	 * Localiza las series dentro del cuadro de clasificacion que verifican las
	 * condiciones suministradas y cuya serie se encuentra en estado vigente o
	 * historica y tiene una valoración dictaminada
	 *
	 * @param fondoID
	 *            Identificador del fondo al que deben pertenecer las series.
	 *            Puede ser nulo
	 * @param codigo
	 *            Cadena que debe estar contenida en el codigo de la serie
	 * @param titulo
	 *            Cadena que debe estar contenida en la denominación de la serie
	 * @return Lista de series que verifican los criterios {@link SerieVO}
	 */
	public List findSeriesSeleccionables(String fondoID, String codigo,
			String titulo) {
		int[] estadosSerie = { EstadoSerie.VIGENTE, EstadoSerie.HISTORICA };

		List searchConditions = createSearchConditions(fondoID, codigo, titulo);
		// if (getServiceClient() != null)
		// searchConditions.add(new
		// FieldConstraint(SerieDBEntityImpl.CAMPO_IDUSRGESTOR,
		// ConstraintType.EQUAL, getServiceClient().getId()));

		return _serieDBEntity.findSeriesSeleccionables(searchConditions,
				estadosSerie);
	}

	// private void checkGestor(String idUsrGestor) throws
	// FondosOperacionNoPermitidaException{
	// GestionControlUsuariosBI userService =
	// getServiceRepository().lookupGestionControlUsuariosBI();
	// if (!userService.isGestorSerie(idUsrGestor))
	// throw new FondosOperacionNoPermitidaException(
	// FondosOperacionNoPermitidaException.X_EL_USUARIO_SELECCIONADO_NO_ES_GESTOR_DE_SERIE);
	// }

	/**
	 * Asigna a un usuario como gestor de las series especificadas.
	 *
	 * @param idGestor
	 *            Identificador de usuario
	 * @param serieIDs
	 *            Lista de identificadores de serie
	 * @throws FondosOperacionNoPermitidaException
	 *             Caso de que no se puede llevar a cabo la asignacion de alguna
	 *             de las series al gestor indicado
	 */
	public void asignarGestor(String idNewGestor, String[] serieIDs)
			throws FondosOperacionNoPermitidaException {

		checkPermission(FondosSecurityManager.CEDER_CONTROL_ACTION);
		Locale locale = getServiceClient().getLocale();

		int[] estadosValoracion = { ValoracionSerieVO.ESTADO_ABIERTA,
				ValoracionSerieVO.ESTADO_PENDIENTE_DE_VALIDAR,
				ValoracionSerieVO.ESTADO_VALIDADA,
				ValoracionSerieVO.ESTADO_VALIDACION_RECHAZADA,
				ValoracionSerieVO.ESTADO_EVALUADA };
		// Transformer valoracionToID = new Transformer() {
		// public Object transform(Object obj) {
		// return ((ValoracionSerieVO)obj).getId();
		// }
		// };
		GestionControlUsuariosBI usaurioBI = getServiceRepository()
				.lookupGestionControlUsuariosBI();
		UsuarioVO userFinal = usaurioBI.getUsuario(idNewGestor);

		// checkGestor(idNewGestor);
		if (!usaurioBI.userHasPermission(idNewGestor,
				AppPermissions.GESTOR_SERIE))
			throw new FondosOperacionNoPermitidaException(
					FondosOperacionNoPermitidaException.X_EL_USUARIO_SELECCIONADO_NO_ES_GESTOR_DE_SERIE);
		else {
			iniciarTransaccion();
			int nSeries = serieIDs.length;

			for (int i = 0; i < nSeries; i++) {

				SerieVO serie = _serieDBEntity.getSerie(serieIDs[i]);

				// Auditoria
				LoggingEvent logEvent = AuditFondos
						.getLogginEventModificacionSerie(this);

				UsuarioVO userInicial = usaurioBI.getUsuario(serie
						.getIdusrgestor());
				String nombreUsrInicial = null;
				String idUsrInicial = null;
				if (userInicial != null) {
					nombreUsrInicial = userInicial.getNombreCompleto();
					idUsrInicial = userInicial.getId();
				}

				// Auditoria
				AuditFondos.addDataLogCambiosUsrGestorSerie(locale, logEvent,
						serie, nombreUsrInicial, idUsrInicial,
						userFinal.getNombre(), userFinal.getId());

				_serieDBEntity.updateGestor(serieIDs[i], idNewGestor);
				List valoracionesEnElaboracion = _valoracionDbEntity
						.getIDsValoracionSerie(serie.getId(), estadosValoracion);
				// CollectionUtils.transform(valoracionesEnElaboracion,
				// valoracionToID);
				if (valoracionesEnElaboracion.size() > 0)
					_valoracionDbEntity.updateGestorValoracion(
							(String[]) valoracionesEnElaboracion
									.toArray(ArrayUtils.EMPTY_STRING_ARRAY),
							idNewGestor);
			}

			commit();
		}

	}

	/**
	 * Obtiene la lista de entidades identificadas como productores de una serie
	 * documental
	 *
	 * @param idSerie
	 *            Identificador de serie documental
	 * @return Lista de productores de la serie {@link ProductorSerieVO}
	 */
	public List getProductoresSerie(String idSerie) {
		return _productorSerieDBEntity.getProductoresXIdSerie(idSerie, null,
				false);
	}

	/**
	 * Obtiene la lista de entidades identificadas como productores de una serie
	 * documental ordenadas por fecha
	 *
	 * @param idSerie
	 *            Identificador de serie documental
	 * @param orderByDate
	 *            Indica si se ordenan por fecha
	 * @return Lista de productores de la serie {@link ProductorSerieVO}
	 */
	public List getProductoresSerie(String idSerie, boolean orderByDate) {
		return _productorSerieDBEntity.getProductoresXIdSerie(idSerie, null,
				orderByDate);
	}

	public List getProductoresVigentesBySerie(String idSerie) {
		return _productorSerieDBEntity.getProductoresVigentesBySerie(idSerie);
	}

	public ProductorSerieVO getProductorVigenteBySerie(String idSerie,
			String idProductor) {
		return _productorSerieDBEntity.getProductorVigenteBySerie(idSerie,
				idProductor);
	}

	public ProductorSerieVO getProductorHistoricoBySerie(String idSerie,
			String idProductor) {
		return _productorSerieDBEntity.getProductorHistoricoBySerie(idSerie,
				idProductor);
	}

	public List getProductoresHistoricosBySerie(String idSerie) {
		return _productorSerieDBEntity.getProductoresHistoricosBySerie(idSerie);
	}

	public void updateProductorSerie(ProductorSerieVO productorSerieVO) {
		_productorSerieDBEntity.updateOrgProductor(productorSerieVO);
	}

	public CAOrganoVO getCAOrgProductorVOXSistExtGestorYIdOrgsExtGestor(
			final String sistExtGestor, final String idEnSistExt) {
		return _caOrganoDbEntity
				.getCAOrgProductorVOXSistExtGestorYIdOrgsExtGestor(
						sistExtGestor, idEnSistExt);
	}

	public CAOrganoVO getCAOrgProductorVOXSistExtGestorYIdOrgsExtGestor(
			final String sistExtGestor, final String idEnSistExt,
			final Boolean vigente) {
		if (vigente != null) {
			return _caOrganoDbEntity
					.getCAOrgProductorVOXSistExtGestorYIdOrgsExtGestorYVigencia(
							sistExtGestor, idEnSistExt, vigente.booleanValue());
		} else {
			return _caOrganoDbEntity
					.getCAOrgProductorVOXSistExtGestorYIdOrgsExtGestor(
							sistExtGestor, idEnSistExt);
		}
	}

	public void pasarAHistorico(ProductorSerieVO productorSerieVO,
			CAOrganoVO antiguoCAorganoVO, CAOrganoVO nuevoCAorganoVO) {

		productorSerieVO.setFechaFinal(DateUtils.getFechaActual());
		updateProductorSerie(productorSerieVO);

		if (antiguoCAorganoVO != null && nuevoCAorganoVO != null
				&& !antiguoCAorganoVO.equals(nuevoCAorganoVO)) {
			antiguoCAorganoVO.setVigente(Constants.FALSE_STRING);
			_caOrganoDbEntity.updateCAOrgVO(antiguoCAorganoVO);
		}
	}

	public void pasarAHistorico(ProductorSerieVO productorSerieVO) {

		productorSerieVO.setFechaFinal(DateUtils.getFechaActual());
		updateProductorSerie(productorSerieVO);

		OrganoProductorVO organoProductorVO = _orgProductorDBEntity
				.getOrgProductorXIdDescr(productorSerieVO.getIdProductor());
		CAOrganoVO organoVO = _caOrganoDbEntity
				.getCAOrgProductorVOXId(organoProductorVO.getIdOrgano());

		if (organoVO != null) {
			organoVO.setVigente(Constants.FALSE_STRING);
			_caOrganoDbEntity.updateCAOrgVO(organoVO);
		}
	}

	public void pasarAHistoricoProductoresVigentesDespues(
			List listaProductoresAntiguosVigentesDespues,
			String[] productoresAntiguos,
			CAOrganoVO nuevoProductorCAOrganoVODespues) {
		if (listaProductoresAntiguosVigentesDespues != null) {
			for (int j = 0; j < listaProductoresAntiguosVigentesDespues.size(); j++) {
				ProductorSerieVO productorSerieVO = (ProductorSerieVO) listaProductoresAntiguosVigentesDespues
						.get(j);
				for (int h = 0; h < productoresAntiguos.length; h++) {
					if (productorSerieVO.getId().equals(productoresAntiguos[h])) {
						h = productoresAntiguos.length;
					} else if (!productorSerieVO.getId().equals(
							productoresAntiguos[h])
							&& h == productoresAntiguos.length - 1) {
						OrganoProductorVO antiguoOrganoProductorVO = _orgProductorDBEntity
								.getOrgProductorXIdDescr(productorSerieVO
										.getId());

						CAOrganoVO antiguoCAorganoVO = null;
						if (antiguoOrganoProductorVO != null)
							antiguoCAorganoVO = _caOrganoDbEntity
									.getCAOrgProductorVOXId(antiguoOrganoProductorVO
											.getIdOrgano());

						// productorSerieVO=_productorSerieDBEntity.getProductorSerieVO(productorSerieVO.getIdserie(),
						// productorSerieVO.getIdprocedimiento(),
						// productorSerieVO.getIdProductor());
						productorSerieVO = _productorSerieDBEntity
								.getProductorVigenteBySerie(
										productorSerieVO.getIdserie(),
										productorSerieVO.getIdProductor());
						pasarAHistorico(productorSerieVO, antiguoCAorganoVO,
								nuevoProductorCAOrganoVODespues);
					}

				}
			}
		}
	}

	/**
	 * Refresca los datos de la identificación de una serie. La información de
	 * la identificación de la serie que puede haber variado son los productores
	 * del procedimiento vinculado a la serie caso de que en el proceso de
	 * identificación se le haya asociado un procedimiento a la serie.
	 *
	 * @param idSerie
	 *            Identificador de la serie cuya identificación se desea
	 *            actualizar
	 * @throws FondosOperacionNoPermitidaException
	 *             Caso de que el refreco de la identificación de la serie no
	 *             esté permitida
	 */
	public HashMap actualizarIdentificacionSerie(String idSerie)
			throws GestorCatalogoException, GestorOrganismosException,
			NotAvailableException, FondosOperacionNoPermitidaException,
			ArchigestModelException,
			ProductorProcedimientoNoIncorporadoException {
		iniciarTransaccion();
		Locale locale = getServiceClient().getLocale();

		SerieVO serie = getSerie(idSerie);
		IdentificacionSerie identificacionSerie = getIdentificacionSerie(serie);
		EntidadProductoraVO entidadProductoraFondo = identificacionSerie
				.getEntidadProductoraFondo();
		InfoBProcedimiento datosProcedimiento = identificacionSerie
				.getProcedimiento();
		ConfiguracionSistemaArchivo config = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo();
		ConfiguracionGeneral configuracionGeneral = config
				.getConfiguracionGeneral();
		ConfiguracionFondos configuracionFondos = config
				.getConfiguracionFondos();

		// Obtener información de la entidad
		ServiceClient serviceClient = getServiceClient();

		// Obtener la entidad para el usuario conectado
		Properties params = null;

		if ((serviceClient != null)
				&& (StringUtils.isNotEmpty(serviceClient.getEntity()))) {
			params = new Properties();
			params.put(MultiEntityConstants.ENTITY_PARAM,
					serviceClient.getEntity());
		}

		GestorOrganismos gestorOrganizacion = GestorOrganismosFactory
				.getConnectorById(configuracionFondos.getIdSistGestorOrg(),
						params);
		ServiceRepository services = ServiceRepository
				.getInstance(getServiceSession());
		/* GestionControlUsuariosBI controlAccesoBI = */services
				.lookupGestionControlUsuariosBI();

		if (datosProcedimiento != null) {

			GestorCatalogo gestorCatalogo = GestorCatalogoFactory
					.getConnector(params);
			IProcedimiento procedimiento = gestorCatalogo
					.recuperarProcedimiento(datosProcedimiento.getId());

			if (procedimiento == null) {
				commit();
				throw new ArchigestModelException(Messages.getString(
						Constants.NO_EXISTE_PROCEDIMIENTO, locale));
				// throw new
				// ArchigestModelException("No se ha encontrado el procedimiento, no se puede actualizar");
			}
			List productoresProcedimiento = procedimiento
					.getOrganosProductores();
			// List listaProductores = new ArrayList();

			if (productoresProcedimiento == null
					|| productoresProcedimiento.size() == 0) {
				commit();
				throw new ArchigestModelException(Messages.getString(
						Constants.NO_EXISTEN_PRODUCTORES, locale));
				// throw new
				// ArchigestModelException("No hay productores asociados al procedimiento, no se puede actualizar");
			}

			List listaProductoresVigentes = getProductoresVigentesBySerie(serie
					.getId());

			if (ListUtils.isEmpty(listaProductoresVigentes)) {

				// Para sigia. Podría darse el caso de que una serie vigente no
				// tenga productores vigentes.

				String idLCAParaUnUnicoProductor = null;

				if (!ListUtils.isEmpty(productoresProcedimiento)
						&& productoresProcedimiento.size() == 1) {
					List listaProductoresHistoricos = _productorSerieDBEntity
							.getProductoresHistoricosBySerie(serie.getId());
					if (!ListUtils.isEmpty(listaProductoresHistoricos)
							&& tienenMismaLCA(listaProductoresHistoricos)) {
						idLCAParaUnUnicoProductor = getFirstLCA(listaProductoresHistoricos);

						IOrganoProductor productorProcedimiento = (IOrganoProductor) productoresProcedimiento
								.get(0);
						CAOrganoVO nuevoCAorganoVO = _caOrganoDbEntity
								.getCAOrgProductorVOXSistExtGestorYIdOrgsExtGestorYVigencia(
										configuracionFondos
												.getIdSistGestorOrg(),
										productorProcedimiento.getId(), true);

						String idListaDescriptor = configuracionGeneral
								.getListaDescriptoresEntidad(
										TipoProductor.BDORGANIZACION
												.getIdentificador()).getId();
						DescriptorVO descriptor = obtenerDescriptorOrgano(
								nuevoCAorganoVO, idListaDescriptor);

						OrganoProductorVO nuevoOrganoProductorVO = _orgProductorDBEntity
								.getOrgProductorXIdDescr(descriptor.getId());
						if (nuevoOrganoProductorVO == null) {

							crearOrganoComoProductor(
									nuevoCAorganoVO.getIdOrg(),
									descriptor.getId(),
									entidadProductoraFondo.getId());

							nuevoOrganoProductorVO = _orgProductorDBEntity
									.getOrgProductorXIdDescr(descriptor.getId());
						}

						String idDescriptorNuevo = nuevoOrganoProductorVO
								.getId();

						ProductorSerieVO productorSerie = _productorSerieDBEntity
								.getProductorXId(idSerie, descriptor.getId());

						if (productorSerie != null) {
							commit();
							throw new ArchigestModelException(
									Messages.getString(
											Constants.PRODUCTOR_EXISTE_EN_HISTORICOS,
											locale));
						}

						else {
							productorSerie = new ProductorSerieVO();
							productorSerie.setIdserie(serie.getId());
							if (procedimiento != null)
								productorSerie
										.setIdprocedimiento(datosProcedimiento
												.getId());
							productorSerie.setIdProductor(descriptor.getId());
							productorSerie
									.setTipoProductor(ProductorSerieVO.TIPO_ORG_DEP);
							productorSerie.setFechaInicial(DateUtils
									.getFechaActual());
							productorSerie
									.setIdLCAPref(idLCAParaUnUnicoProductor);
							_productorSerieDBEntity
									.insertProductorVGSerieVO(productorSerie);
						}

						if (!StringUtils.isBlank(idLCAParaUnUnicoProductor))
							actualizarAccesoOrganoProductor(
									idLCAParaUnUnicoProductor,
									nuevoCAorganoVO.getIdOrg());
						else {
							OrganoProductorVO organoProductor = _orgProductorDBEntity
									.getOrgProductorXIdDescr(idDescriptorNuevo);
							crearListaAcceso(organoProductor, serie);
						}

					}
				}
				// commit();
				// throw new
				// ArchigestModelException(Messages.getString(Constants.NO_EXISTEN_PRODUCTORES_VIGENTES));
			}

			else if ((productoresProcedimiento != null && productoresProcedimiento
					.size() > 1)
					|| (listaProductoresVigentes != null && listaProductoresVigentes
							.size() > 1)) {
				commit();
				return refrescarVariosProductores(idSerie);

			} else if (productoresProcedimiento != null) // Sustitución 1 a 1
			{
				IOrganoProductor productorProcedimiento = (IOrganoProductor) productoresProcedimiento
						.get(0);
				CAOrganoVO nuevoCAorganoVO = _caOrganoDbEntity
						.getCAOrgProductorVOXSistExtGestorYIdOrgsExtGestorYVigencia(
								configuracionFondos.getIdSistGestorOrg(),
								productorProcedimiento.getId(), true);

				// TODO Multiarchivo - Hecho
				if (nuevoCAorganoVO == null) {
					List productores = new ArrayList();
					InfoOrganoProductorSerie unProductor = null;
					for (Iterator i = productoresProcedimiento.iterator(); i
							.hasNext();) {

						IOrganoProductor organoProductor = (IOrganoProductor) i
								.next();

						if (organoProductor != null) {
							InfoOrgano datosOrgano = gestorOrganizacion
									.recuperarOrgano(
											TipoAtributo.IDENTIFICADOR_ORGANO,
											organoProductor.getId());

							if (datosOrgano == null) {
								datosOrgano = gestorOrganizacion
										.recuperarOrgano(
												TipoAtributo.CODIGO_ORGANO,
												organoProductor.getId());
							}

							if (datosOrgano != null) {
								unProductor = new InfoOrganoProductorSerie(
										ProductorSerieVO.TIPO_ORG_DEP,
										configuracionFondos
												.getIdSistGestorOrg(),
										datosOrgano.getId(),
										NombreOrganoFormat
												.formatearNombreLargo(
														datosOrgano,
														gestorOrganizacion
																.recuperarOrganosAntecesores(
																		datosOrgano
																				.getId(),
																		0)),
										datosOrgano.getCodigo());
								unProductor.setPuedeSerEliminado(false);
								productores.add(unProductor);
							} else {
								logger.error("Organo no encontrado Id="
										+ organoProductor.getId());
							}
						}
					}

					// productores.add(productorProcedimiento);
					throw new ProductorProcedimientoNoIncorporadoException(
							productores);
				}
				/*
				 * nuevoCAorganoVO = controlAccesoBI.crearOrgano(
				 * configuracionFondos.getIdSistGestorOrg(),
				 * gestorOrganizacion.recuperarOrgano
				 * (TipoAtributo.IDENTIFICADOR_ORGANO,
				 * productorProcedimiento.getId()),
				 * gestorOrganizacion.recuperarOrganosAntecesores
				 * (productorProcedimiento.getId(), 0), serie.getIdArchivo());
				 */

				String idListaDescriptor = configuracionGeneral
						.getListaDescriptoresEntidad(
								TipoProductor.BDORGANIZACION.getIdentificador())
						.getId();
				DescriptorVO descriptor = obtenerDescriptorOrgano(
						nuevoCAorganoVO, idListaDescriptor);

				OrganoProductorVO nuevoOrganoProductorVO = _orgProductorDBEntity
						.getOrgProductorXIdDescr(descriptor.getId());
				if (nuevoOrganoProductorVO == null) {
					nuevoOrganoProductorVO = new OrganoProductorVO();
					nuevoOrganoProductorVO.setIdOrgano(nuevoCAorganoVO
							.getIdOrg());
					nuevoOrganoProductorVO.setId(descriptor.getId());
					nuevoOrganoProductorVO
							.setIdEntProdInst(entidadProductoraFondo.getId());
					_orgProductorDBEntity
							.insertOrgProductorVO(nuevoOrganoProductorVO);
					nuevoOrganoProductorVO = _orgProductorDBEntity
							.getOrgProductorXIdDescr(descriptor.getId());
				}

				ProductorSerieVO antiguoProductorSerieVO = (ProductorSerieVO) listaProductoresVigentes
						.get(0);
				int tipoProductorAntiguo = antiguoProductorSerieVO
						.getTipoProductor();
				String idDescriptorAntiguo = antiguoProductorSerieVO
						.getIdProductor();

				// OrganoProductorVO
				// nuevoOrganoProductorVO=_orgProductorDBEntity.getOrgProductorXIdOrgano(nuevoCAorganoVO.getIdOrg());
				// String idDescriptorNuevo=nuevoOrganoProductorVO.getId();
				String idDescriptorNuevo = nuevoOrganoProductorVO.getId();

				if (tipoProductorAntiguo == TipoProductorSerie.ENTIDAD
						|| (idDescriptorAntiguo != null
								&& idDescriptorNuevo != null && !idDescriptorAntiguo
								.equalsIgnoreCase(idDescriptorNuevo)))

				{
					OrganoProductorVO antiguoOrganoProductorVO = _orgProductorDBEntity
							.getOrgProductorXIdDescr(idDescriptorAntiguo);

					CAOrganoVO antiguoCAOrganoVO = null;
					if (antiguoOrganoProductorVO != null) {
						antiguoCAOrganoVO = _caOrganoDbEntity
								.getCAOrgProductorVOXId(antiguoOrganoProductorVO
										.getIdOrgano());
					}
					pasarAHistorico(
							(ProductorSerieVO) listaProductoresVigentes.get(0),
							antiguoCAOrganoVO, nuevoCAorganoVO);

					ProductorSerieVO productorSerie = _productorSerieDBEntity
							.getProductorXId(idSerie, descriptor.getId());
					if (productorSerie == null) {
						productorSerie = new ProductorSerieVO();
						productorSerie.setIdserie(serie.getId());
						if (procedimiento != null)
							productorSerie
									.setIdprocedimiento(datosProcedimiento
											.getId());
						productorSerie.setIdProductor(descriptor.getId());
						productorSerie
								.setTipoProductor(ProductorSerieVO.TIPO_ORG_DEP);
						productorSerie.setFechaInicial(DateUtils
								.getFechaActual());
						productorSerie.setIdLCAPref(antiguoProductorSerieVO
								.getIdLCAPref());
						_productorSerieDBEntity
								.insertProductorVGSerieVO(productorSerie);
					}

					String idLCA = antiguoProductorSerieVO.getIdLCAPref();
					if (!StringUtils.isBlank(idLCA))
						actualizarAccesoOrganoProductor(idLCA,
								nuevoCAorganoVO.getIdOrg());
					else {
						OrganoProductorVO organoProductor = _orgProductorDBEntity
								.getOrgProductorXIdDescr(idDescriptorNuevo);
						crearListaAcceso(organoProductor, serie);
					}
				}
			}

		}
		updateContenidoSerieNoTransaccional(idSerie);
		updateVolumenSerieNoTransaccional(idSerie);

		GestionSeriesBI serieBI = services.lookupGestionSeriesBI();
		Date fechaInicial = null;
		Date fechaFinal = null;
		fechaInicial = getFechaInicialExtremaUdocsSerie(idSerie);
		fechaFinal = getFechaFinalExtremaUdocsSerie(idSerie);
		serieBI.updateFechasExtremas(serie.getId(), fechaInicial, fechaFinal);

		Map paramActualizacionFicha = new HashMap();
		paramActualizacionFicha.put(ADReglaGenDatosContants.ACCIONES,
				new int[] { ADReglaGenDatosContants.ACTUALIZAR_PRODUCTORES,
						ADReglaGenDatosContants.ACTUALIZAR_VOLUMENES_SOPORTES,
						ADReglaGenDatosContants.ACTUALIZAR_FECHAS_EXTREMAS });
		GestionDescripcionBI descripcionBI = services
				.lookupGestionDescripcionBI();
		descripcionBI.generarAutomaticos(serie.getId(),
				TipoFicha.FICHA_ELEMENTO_CF, paramActualizacionFicha);
		commit();
		return null;
	}

	private void recalcularVolumenYSoporte(String idSerie) {
		// Actualizar el volumen y el soporte
		_volumenSerieDbEntity.deleteVolumenesByIdSerie(idSerie);

		List listaVolumenesYSoporte = _unidadDocumentalDbEntity
				.getCalculoVolumenYSoporteByIdSerie(idSerie);

		if (ListUtils.isNotEmpty(listaVolumenesYSoporte)) {
			for (Iterator It = listaVolumenesYSoporte.iterator(); It.hasNext();) {
				VolumenSerieVO volumenSerieVO = (VolumenSerieVO) It.next();
				if (volumenSerieVO != null) {
					_volumenSerieDbEntity.insertVolumenSerie(volumenSerieVO);
				}
			}
		}
	}

	private void eliminarProductoresEnComun(List nuevosProductoresAntes,
			List antiguosProductoresVigentesAntes,
			List antiguosProductoresVigentesDespues,
			List nuevosProductoresDespues,
			ConfiguracionGeneral configuracionGeneral) {
		for (int i = 0; i < nuevosProductoresAntes.size(); i++) {
			CAOrganoVO nuevoCAorganoVO = (CAOrganoVO) nuevosProductoresAntes
					.get(i);
			String idListaDescriptor = configuracionGeneral
					.getListaDescriptoresEntidad(
							TipoProductor.BDORGANIZACION.getIdentificador())
					.getId();
			DescriptorVO descriptor = obtenerDescriptorOrgano(nuevoCAorganoVO,
					idListaDescriptor);
			String idNuevoDescriptor = descriptor.getId();
			for (int j = 0; j < antiguosProductoresVigentesAntes.size(); j++) {
				ProductorSerieVO antiguoProductorSerieVO = (ProductorSerieVO) antiguosProductoresVigentesAntes
						.get(j);
				String idAntiguoDescriptor = antiguoProductorSerieVO
						.getIdProductor();
				if (idAntiguoDescriptor != null
						&& idNuevoDescriptor != null
						&& idAntiguoDescriptor
								.equalsIgnoreCase(idNuevoDescriptor)) {
					antiguosProductoresVigentesDespues
							.remove(antiguoProductorSerieVO);
					nuevosProductoresDespues.remove(nuevoCAorganoVO);
					j = antiguosProductoresVigentesAntes.size();
				}
			}

		}
	}

	private List eliminarNodosExceptoPrimero(List listaProductores) {
		// List list=new ArrayList();
		if (!ListUtils.isEmpty(listaProductores)) {
			for (int i = 0; i < listaProductores.size(); i++) {
				ProductorSerieVO productorSerieVO = (ProductorSerieVO) listaProductores
						.get(i);
				productorSerieVO.setNombre(common.util.StringUtils.getToken(
						productorSerieVO.getNombre(), "/", 1));
			}
		}
		return listaProductores;
	}

	private String getNombresProductores(List listaProductores) {

		String result = null;
		if (!ListUtils.isEmpty(listaProductores)) {
			for (int i = 0; i < listaProductores.size(); i++) {
				ProductorSerieVO productorSerieVO = (ProductorSerieVO) listaProductores
						.get(i);
				if (result == null) {
					result = productorSerieVO.getNombre();
				} else {
					result += "$" + productorSerieVO.getNombre();
				}

			}
		}
		return result;
	}

	private HashMap refrescarVariosProductores(String idSerie)
			throws GestorCatalogoException, GestorOrganismosException,
			NotAvailableException, FondosOperacionNoPermitidaException,
			ArchigestModelException,
			ProductorProcedimientoNoIncorporadoException {

		ServiceRepository services = ServiceRepository
				.getInstance(getServiceSession());
		GestionSeriesBI serieBI = services.lookupGestionSeriesBI();

		SerieVO serie = serieBI.getSerie(idSerie);
		IdentificacionSerie identificacionSerie = serieBI
				.getIdentificacionSerie(serie);
		InfoBProcedimiento datosProcedimiento = identificacionSerie
				.getProcedimiento();

		HashMap hashMap = null;

		if (datosProcedimiento != null) {
			// Obtener información de la entidad
			ServiceClient serviceClient = getServiceClient();

			// Obtener la entidad para el usuario conectado
			Properties params = null;

			if ((serviceClient != null)
					&& (StringUtils.isNotEmpty(serviceClient.getEntity()))) {
				params = new Properties();
				params.put(MultiEntityConstants.ENTITY_PARAM,
						serviceClient.getEntity());
			}

			GestorCatalogo gestorCatalogo = GestorCatalogoFactory
					.getConnector(params);
			IProcedimiento procedimiento = gestorCatalogo
					.recuperarProcedimiento(datosProcedimiento.getId());
			ConfiguracionSistemaArchivo config = ConfiguracionSistemaArchivoFactory
					.getConfiguracionSistemaArchivo();
			ConfiguracionGeneral configuracionGeneral = config
					.getConfiguracionGeneral();
			ConfiguracionFondos configuracionFondos = config
					.getConfiguracionFondos();

			GestorOrganismos gestorOrganizacion = GestorOrganismosFactory
					.getConnectorById(configuracionFondos.getIdSistGestorOrg(),
							params);

			List nuevosProductoresAntes = getNuevosProductores(procedimiento,
					configuracionFondos, gestorOrganizacion, serie);
			List antiguosProductoresVigentesAntes = getProductoresVigentesBySerie(serie
					.getId());

			List nuevosProductoresDespues = new ArrayList(
					nuevosProductoresAntes);
			ArrayList antiguosProductoresVigentesDespues = new ArrayList(
					antiguosProductoresVigentesAntes);

			eliminarProductoresEnComun(nuevosProductoresAntes,
					antiguosProductoresVigentesAntes,
					antiguosProductoresVigentesDespues,
					nuevosProductoresDespues, configuracionGeneral);

			String strAntiguosProductoresVigentesDespues = getNombresProductores(antiguosProductoresVigentesDespues);
			List antiguosProductoresVigentesDespuesPrimerNodo = eliminarNodosExceptoPrimero(antiguosProductoresVigentesDespues);

			if (antiguosProductoresVigentesDespues.size() == 0
					|| nuevosProductoresDespues.size() == 0) {
				// no mostramos pantalla para combinar
				// Se dejan los vigentes que habia en un principio
				for (int i = 0; i < nuevosProductoresDespues.size(); i++) {
					CAOrganoVO nuevoProductorDespuesCAOrganoVO = (CAOrganoVO) nuevosProductoresDespues
							.get(i);
					createNuevoProductor(nuevoProductorDespuesCAOrganoVO,
							idSerie);
				}

				if (antiguosProductoresVigentesDespues.size() > 0) {
					Iterator it = antiguosProductoresVigentesDespues.iterator();
					while (it.hasNext()) {
						ProductorSerieVO productorSerieVO = (ProductorSerieVO) it
								.next();
						pasarAHistorico(productorSerieVO);
					}
				}
			} else {
				hashMap = new HashMap();
				hashMap.put(FondosConstants.NUEVOS_PRODUCTORES_DESPUES_KEY,
						nuevosProductoresDespues);
				hashMap.put(
						FondosConstants.ANTIGUOS_PRODUCTORES_VIGENTES_DESPUES_KEY,
						strAntiguosProductoresVigentesDespues);
				hashMap.put(
						FondosConstants.ANTIGUOS_PRODUCTORES_VIGENTES_DESPUES_PRIMER_NODO_KEY,
						antiguosProductoresVigentesDespuesPrimerNodo);
			}
		}
		return hashMap;
	}

	public void sustituirProductor(String idDescriptorProductorAntiguo,
			CAOrganoVO nuevoCAOrganoVO, String idSerie)
			throws GestorCatalogoException, NotAvailableException {

		SerieVO serie = getSerie(idSerie);
		IdentificacionSerie identificacionSerie = getIdentificacionSerie(serie);
		EntidadProductoraVO entidadProductoraFondo = identificacionSerie
				.getEntidadProductoraFondo();
		ConfiguracionSistemaArchivo config = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo();
		ConfiguracionGeneral configuracionGeneral = config
				.getConfiguracionGeneral();
		InfoBProcedimiento datosProcedimiento = identificacionSerie
				.getProcedimiento();

		IProcedimiento procedimiento = null;
		if (datosProcedimiento != null) {
			// Obtener información de la entidad
			ServiceClient serviceClient = getServiceClient();

			// Obtener la entidad para el usuario conectado
			Properties params = null;

			if ((serviceClient != null)
					&& (StringUtils.isNotEmpty(serviceClient.getEntity()))) {
				params = new Properties();
				params.put(MultiEntityConstants.ENTITY_PARAM,
						serviceClient.getEntity());
			}

			GestorCatalogo gestorCatalogo = GestorCatalogoFactory
					.getConnector(params);
			procedimiento = gestorCatalogo
					.recuperarProcedimiento(datosProcedimiento.getId());
		}

		OrganoProductorVO organoProductor = _orgProductorDBEntity
				.getOrgProductorXIdDescr(idDescriptorProductorAntiguo);
		ProductorSerieVO antiguoProductorSerieVO = _productorSerieDBEntity
				.getProductorXId(idSerie, idDescriptorProductorAntiguo);

		// Se pasa a historico el productor actual

		CAOrganoVO antiguoCAorganoVO = _caOrganoDbEntity
				.getCAOrgProductorVOXId(organoProductor.getIdOrgano());
		pasarAHistorico(antiguoProductorSerieVO, antiguoCAorganoVO,
				nuevoCAOrganoVO);

		String idListaDescriptor = configuracionGeneral
				.getListaDescriptoresEntidad(
						TipoProductor.BDORGANIZACION.getIdentificador())
				.getId();
		DescriptorVO descriptor = obtenerDescriptorOrgano(nuevoCAOrganoVO,
				idListaDescriptor);

		organoProductor = _orgProductorDBEntity
				.getOrgProductorXIdDescr(descriptor.getId());
		if (organoProductor == null) {
			crearOrganoComoProductor(nuevoCAOrganoVO.getIdOrg(),
					descriptor.getId(), entidadProductoraFondo.getId());
			organoProductor = _orgProductorDBEntity
					.getOrgProductorXIdDescr(descriptor.getId());

		}
		ProductorSerieVO productorSerie = _productorSerieDBEntity
				.getProductorXId(idSerie, descriptor.getId());
		if (productorSerie == null) {
			productorSerie = new ProductorSerieVO();
			productorSerie.setIdserie(serie.getId());
			if (procedimiento != null)
				productorSerie.setIdprocedimiento(datosProcedimiento.getId());
			productorSerie.setIdProductor(descriptor.getId());
			productorSerie.setTipoProductor(ProductorSerieVO.TIPO_ORG_DEP);
			productorSerie.setFechaInicial(DateUtils.getFechaActual());
			productorSerie.setIdLCAPref(antiguoProductorSerieVO.getIdLCAPref());
			_productorSerieDBEntity.insertProductorVGSerieVO(productorSerie);
		}

		String idLCA = antiguoProductorSerieVO.getIdLCAPref();
		if (!StringUtils.isBlank(idLCA)) {
			actualizarAccesoOrganoProductor(idLCA, nuevoCAOrganoVO.getIdOrg());
			_productorSerieDBEntity.setListaControlAcceso(
					organoProductor.getIdOrgano(), idSerie, idLCA);
		}

	}

	private String calcularNombreUnico(String nombre) {
		ListaAccesoVO listaAccesoVO = _listaControlAcceso
				.getListaControlAccesoByNombre(nombre);
		if (listaAccesoVO == null)
			return nombre;

		List listaPermisos = _permisosListaDbEntity
				.getPermisosXIdLista(listaAccesoVO.getId());
		int contador = 0;
		if (!ListUtils.isEmpty(listaPermisos))
			contador = listaPermisos.size();
		while (listaAccesoVO != null) {
			contador++;
			listaAccesoVO = _listaControlAcceso
					.getListaControlAccesoByNombre(nombre + "(" + contador
							+ ")");
		}
		return nombre + "(" + contador + ")";

	}

	private void crearListaAcceso(String nombreOrganoProductor,
			String idDescriptorOrgano, SerieVO serie) {
		ListaAccesoVO listaAccesoSerie = new ListaAccesoVO();

		StringBuffer descripcionListaAcceso = new StringBuffer("ACL: ")
				.append(serie.getCodigo()).append(" - ")
				.append(nombreOrganoProductor);

		String[] splitNombre = nombreOrganoProductor.split("/");
		String nombreCorto = nombreOrganoProductor;
		if (splitNombre != null
				&& StringUtils.isNotEmpty(splitNombre[splitNombre.length - 1]))
			nombreCorto = splitNombre[0];

		StringBuffer nombreListaAcceso = new StringBuffer("Serie: ")
				.append(serie.getCodigo()).append(" - ").append(nombreCorto);

		listaAccesoSerie.setNombre(calcularNombreUnico(nombreListaAcceso
				.toString()));
		listaAccesoSerie
				.setTipo(TipoListaControlAcceso.ELEMENTO_CUADRO_CLASIFICACION);
		listaAccesoSerie.setDescripcion(descripcionListaAcceso.toString());
		_listaControlAcceso.insertListaAccesoVO(listaAccesoSerie);

		_productorSerieDBEntity.setListaControlAcceso(idDescriptorOrgano,
				serie.getId(), listaAccesoSerie.getId());

	}

	private void crearListaAcceso(OrganoProductorVO organoProductor,
			SerieVO serie) {
		ListaAccesoVO listaAccesoSerie = new ListaAccesoVO();

		StringBuffer descripcionListaAcceso = new StringBuffer("ACL: ")
				.append(serie.getCodigo()).append(" - ")
				.append(organoProductor.getNombre());

		String[] splitNombre = organoProductor.getNombre().split("/");
		String nombreCorto = organoProductor.getNombre();
		if (splitNombre != null
				&& StringUtils.isNotEmpty(splitNombre[splitNombre.length - 1]))
			nombreCorto = splitNombre[0];

		StringBuffer nombreListaAcceso = new StringBuffer("Serie: ")
				.append(serie.getCodigo()).append(" - ").append(nombreCorto);

		listaAccesoSerie.setNombre(calcularNombreUnico(nombreListaAcceso
				.toString()));
		listaAccesoSerie
				.setTipo(TipoListaControlAcceso.ELEMENTO_CUADRO_CLASIFICACION);
		listaAccesoSerie.setDescripcion(descripcionListaAcceso.toString());
		_listaControlAcceso.insertListaAccesoVO(listaAccesoSerie);

		PermisosListaVO permisosDeLaLista = new PermisosListaVO();
		permisosDeLaLista.setIdListCA(listaAccesoSerie.getId());
		permisosDeLaLista.setTipoDest(TipoDestinatario.ORGANO);
		permisosDeLaLista.setIdDest(organoProductor.getIdOrgano());

		permisosDeLaLista.setPerm(new Integer(
				AppPermissions.CONSULTA_DESCRIPCION_CUADRO_CLASIFICACION)
				.intValue());
		_permisosListaDbEntity.insertPermisosListaVO(permisosDeLaLista);

		permisosDeLaLista.setPerm(new Integer(
				AppPermissions.CONSULTA_CUADRO_CLASIFICACION).intValue());
		_permisosListaDbEntity.insertPermisosListaVO(permisosDeLaLista);

		permisosDeLaLista.setPerm(new Integer(
				AppPermissions.CONSULTA_DOCUMENTOS_CUADRO_CLASIFICACION)
				.intValue());
		_permisosListaDbEntity.insertPermisosListaVO(permisosDeLaLista);

		_productorSerieDBEntity.setListaControlAcceso(organoProductor.getId(),
				serie.getId(), listaAccesoSerie.getId());
	}

	public void createNuevoProductor(CAOrganoVO organo, String idSerie)
			throws GestorCatalogoException, NotAvailableException {

		SerieVO serie = getSerie(idSerie);
		IdentificacionSerie identificacionSerie = getIdentificacionSerie(serie);
		EntidadProductoraVO entidadProductoraFondo = identificacionSerie
				.getEntidadProductoraFondo();
		InfoBProcedimiento datosProcedimiento = identificacionSerie
				.getProcedimiento();
		ConfiguracionSistemaArchivo config = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo();
		ConfiguracionGeneral configuracionGeneral = config
				.getConfiguracionGeneral();
		// Obtener información de la entidad
		ServiceClient serviceClient = getServiceClient();

		// Obtener la entidad para el usuario conectado
		Properties params = null;

		if ((serviceClient != null)
				&& (StringUtils.isNotEmpty(serviceClient.getEntity()))) {
			params = new Properties();
			params.put(MultiEntityConstants.ENTITY_PARAM,
					serviceClient.getEntity());
		}
		GestorCatalogo gestorCatalogo = GestorCatalogoFactory
				.getConnector(params);
		IProcedimiento procedimiento = gestorCatalogo
				.recuperarProcedimiento(datosProcedimiento.getId());
		String idListaDescriptor = configuracionGeneral
				.getListaDescriptoresEntidad(
						TipoProductor.BDORGANIZACION.getIdentificador())
				.getId();
		DescriptorVO descriptor = obtenerDescriptorOrgano(organo,
				idListaDescriptor);

		OrganoProductorVO organoProductor = _orgProductorDBEntity
				.getOrgProductorXIdDescr(descriptor.getId());
		if (organoProductor == null) {
			crearOrganoComoProductor(organo.getIdOrg(), descriptor.getId(),
					entidadProductoraFondo.getId());

			organoProductor = _orgProductorDBEntity
					.getOrgProductorXIdDescr(descriptor.getId());
		}
		ProductorSerieVO productorSerie = _productorSerieDBEntity
				.getProductorXId(idSerie, descriptor.getId());
		if (productorSerie == null) {
			productorSerie = new ProductorSerieVO();
			productorSerie.setIdserie(serie.getId());
			if (procedimiento != null)
				productorSerie.setIdprocedimiento(datosProcedimiento.getId());
			productorSerie.setIdProductor(descriptor.getId());
			productorSerie.setTipoProductor(ProductorSerieVO.TIPO_ORG_DEP);
			productorSerie.setFechaInicial(DateUtils.getFechaActual());
			// productorSerie.setIdLCAPref(antiguoProductorSerieVO.getIdLCAPref());
			_productorSerieDBEntity.insertProductorVGSerieVO(productorSerie);
		}

		crearListaAcceso(organoProductor, serie);

		ServiceRepository services = ServiceRepository
				.getInstance(getServiceSession());
		Map paramActualizacionFicha = new HashMap();
		paramActualizacionFicha.put(ADReglaGenDatosContants.ACCIONES,
				new int[] { ADReglaGenDatosContants.ACTUALIZAR_PRODUCTORES });
		GestionDescripcionBI descripcionBI = services
				.lookupGestionDescripcionBI();
		descripcionBI.generarAutomaticos(serie.getId(),
				TipoFicha.FICHA_ELEMENTO_CF, paramActualizacionFicha);

	}

	private List getNuevosProductores(IProcedimiento procedimiento,
			ConfiguracionFondos configuracionFondos,
			GestorOrganismos gestorOrganizacion, SerieVO serie)
			throws GestorOrganismosException, NotAvailableException,
			ProductorProcedimientoNoIncorporadoException {

		List nuevosProductoresProcedimiento = new ArrayList();
		List organosNoCreados = new ArrayList();
		if (procedimiento.getOrganosProductores() != null) {
			for (int i = 0; i < procedimiento.getOrganosProductores().size(); i++) {
				// se.procedimientos.archigest.stub.OrganoProductorVO
				// stubOrganoProductorVO=(se.procedimientos.archigest.stub.OrganoProductorVO)procedimiento.getOrganosProductores().get(i);
				IOrganoProductor organoProductor = (IOrganoProductor) procedimiento
						.getOrganosProductores().get(i);
				CAOrganoVO nuevoCAorganoVO = _caOrganoDbEntity
						.getCAOrgProductorVOXSistExtGestorYIdOrgsExtGestorYVigencia(
								configuracionFondos.getIdSistGestorOrg(),
								organoProductor.getId(), true);
				// ServiceRepository services =
				// ServiceRepository.getInstance(getServiceSession());
				// GestionControlUsuariosBI controlAccesoBI =
				// services.lookupGestionControlUsuariosBI();

				if (nuevoCAorganoVO == null) {
					// List productores = new ArrayList();
					InfoOrganoProductorSerie unProductor = null;
					InfoOrgano datosOrgano = gestorOrganizacion
							.recuperarOrgano(TipoAtributo.IDENTIFICADOR_ORGANO,
									organoProductor.getId());
					unProductor = new InfoOrganoProductorSerie(
							ProductorSerieVO.TIPO_ORG_DEP,
							configuracionFondos.getIdSistGestorOrg(),
							datosOrgano.getId(),
							NombreOrganoFormat.formatearNombreLargo(
									datosOrgano, gestorOrganizacion
											.recuperarOrganosAntecesores(
													datosOrgano.getId(), 0)),
							datosOrgano.getCodigo());
					unProductor.setPuedeSerEliminado(false);
					// productores.add(unProductor);

					organosNoCreados.add(unProductor);

					// TODO Multiarchivo - Hecho
					/*
					 * nuevoCAorganoVO=controlAccesoBI.crearOrgano(
					 * configuracionFondos.getIdSistGestorOrg(),
					 * gestorOrganizacion
					 * .recuperarOrgano(TipoAtributo.IDENTIFICADOR_ORGANO,
					 * stubOrganoProductorVO.getId()),
					 * gestorOrganizacion.recuperarOrganosAntecesores
					 * (stubOrganoProductorVO.getId(), 0),
					 * serie.getIdArchivo());
					 */
				}

				nuevosProductoresProcedimiento.add(nuevoCAorganoVO);
			}

			if (organosNoCreados.size() > 0)
				throw new ProductorProcedimientoNoIncorporadoException(
						organosNoCreados);

			return nuevosProductoresProcedimiento;
		} else
			return null;
	}

	/**
	 * Obtiene los datos de identificación de una serie documental
	 *
	 * @param serie
	 *            Serie del cuadro de clasificación de fondos documentales
	 * @return Información que conforma la identificación de la serie documental
	 */
	public IdentificacionSerie getIdentificacionSerie(SerieVO serie) {
		return new IdentificacionSerie(serie, getServiceClient());
	}

	/**
	 * Obtiene los datos de identificación de una serie documental
	 *
	 * @param serie
	 *            Serie del cuadro de clasificación de fondos documentales
	 * @return Información que conforma la identificación de la serie documental
	 */
	public IdentificacionSerie abrirIdentificacionSerie(SerieVO serie)
			throws Exception {
		IdentificacionSerie identificacionSerie = new IdentificacionSerie(
				serie, getServiceClient());
		if (!identificacionSerie.getPermitidaSeleccionProductores()) {
			List productores = identificacionSerie
					.getListaInfoProductoresSerie();
			if (productores == null || productores.size() == 0) {
				EntidadProductoraVO entidadProductora = identificacionSerie
						.getEntidadProductoraFondo();
				InfoProductorSerie productorSerie = new InfoProductorSerie(
						ProductorSerieVO.TIPO_ENTIDAD,
						entidadProductora.getNombre());
				productorSerie.setIdDescriptor(entidadProductora.getId());
				productorSerie.setPuedeSerEliminado(false);
				identificacionSerie.addInfoProductorSerie(productorSerie);
			}
		}
		return identificacionSerie;
	}

	DescriptorVO obtenerDescriptorOrgano(CAOrganoVO organo,
			String idListaDescriptor) {
		DescriptorVO descriptor = _descriptorDBEntity.getDescriptor(
				idListaDescriptor, organo.getNombreLargo(),organo.getSistExtGestor(), organo.getIdOrgSExtGestor());
		if (descriptor == null) {
			descriptor = new DescriptorVO();
			descriptor.setIdDescrSistExt(organo.getIdOrgSExtGestor());
			descriptor.setIdSistExt(organo.getSistExtGestor());
			descriptor.setNombre(organo.getNombreLargo());
			descriptor.setIdLista(idListaDescriptor);
			descriptor.setTimestamp(DateUtils.getFechaActual());
			descriptor.setTipo(TipoDescriptor.ENTIDAD);
			descriptor.setEstado(EstadoDescriptor.VALIDADO);
			descriptor.setTieneDescr(Constants.FALSE_STRING);
			descriptor.setEditClfDocs(Constants.FALSE_STRING);
			_descriptorDBEntity.insertDescriptorVO(descriptor);
		}
		return descriptor;
	}

	/**
	 *
	 * @param procedimiento
	 * @return El tipo de procedimiento [ Procedimiento.AUTOMATICO -
	 *         Procedimiento.NO_AUTOMATICO ]
	 */
	private int getTipoProcedimiento(InfoBProcedimiento procedimiento) {
		return (procedimiento.getCodSistProductor() == null || procedimiento
				.getCodSistProductor().equals("00")) ? IProcedimiento.NO_AUTOMATICO
				: IProcedimiento.AUTOMATICO;
	}

	private String[] getOrganosProductoresABorrar(List listaProductoresSerie) {
		List listaOrganosProductoresABorrar = new ArrayList();
		for (int i = 0; i < listaProductoresSerie.size(); i++) {
			ProductorSerieVO productorSerieVO = (ProductorSerieVO) listaProductoresSerie
					.get(i);
			List newListaProductoresSerie = _productorSerieDBEntity
					.getProductoresSerieXIdProductor(productorSerieVO
							.getIdProductor());
			if (ListUtils.isEmpty(newListaProductoresSerie)) {
				listaOrganosProductoresABorrar.add(productorSerieVO
						.getIdProductor());

			}
		}
		return common.util.StringUtils.toString(listaOrganosProductoresABorrar
				.toArray());

	}

	public void delete(ProductorSerieVO productorSerieVO) {
		_productorSerieDBEntity.delete(productorSerieVO);
	}

	public void delete(String idSerie, String idProductor) {
		_productorSerieDBEntity.delete(idSerie, idProductor);
	}

	public boolean isHistorico(ProductorSerieVO productorSerieVO) {
		return productorSerieVO.getFechaFinal() != null;
	}

	public boolean isVigente(ProductorSerieVO productorSerieVO) {
		return (productorSerieVO.getFechaInicial() != null && productorSerieVO
				.getFechaFinal() == null);
	}

	// private boolean isSustituido(ProductorSerieVO productorSerieVO,List
	// sustitucionVigenteHistorico)
	// {
	//
	// if(!ListUtils.isEmpty(sustitucionVigenteHistorico))
	// {
	// for(int i=0;i<sustitucionVigenteHistorico.size();i++)
	// {
	// InfoProdVigenteHistorico
	// infoProdVigenteHistorico=(InfoProdVigenteHistorico)sustitucionVigenteHistorico.get(i);
	// if(productorSerieVO.equals(infoProdVigenteHistorico.getProductorSerieSustituido()))
	// return true;
	// }
	// }
	// return false;
	// }

	// private boolean isPasadoAHistorico(ProductorSerieVO productorSerieVO,List
	// listaProductoresPasadosAHistoricos)
	// {
	//
	// if(!ListUtils.isEmpty(listaProductoresPasadosAHistoricos))
	// {
	// for(int i=0;i<listaProductoresPasadosAHistoricos.size();i++)
	// {
	// ProductorSerieVO
	// productorSerieVOAHistorico=(ProductorSerieVO)listaProductoresPasadosAHistoricos.get(i);
	// if(productorSerieVO.equals(productorSerieVOAHistorico))
	// return true;
	// }
	// }
	// return false;
	// }

	// private boolean isEliminadoHistorico(ProductorSerieVO
	// productorSerieVO,List listaProductoresHistoricosEliminados)
	// {
	// if(ListUtils.isNotEmpty(listaProductoresHistoricosEliminados))
	// {
	// for(int i=0;i<listaProductoresHistoricosEliminados.size();i++)
	// {
	// ProductorSerieVO
	// productorSerieVOAHistorico=(ProductorSerieVO)listaProductoresHistoricosEliminados.get(i);
	// if(productorSerieVO.equals(productorSerieVOAHistorico))
	// return true;
	// }
	// }
	// return false;
	// }

	public List setMarcas(List list, int marcas) {
		if (!ListUtils.isEmpty(list)) {
			for (int i = 0; i < list.size(); i++) {
				IInfoProductorSerie infoProductorSerie = (IInfoProductorSerie) list
						.get(i);
				infoProductorSerie.setMarcas(marcas);
			}
		}
		return list;
	}

	// private void borrarProductoresVigentesEliminados(List
	// listaProductoresSerieVigentes, List productoresSerie, List
	// sustitucionVigenteHistorico, List listaProductoresPasadosAHistoricos,
	// List listaProductoresEliminados)
	// {
	// boolean borrar=true;
	// if(!ListUtils.isEmpty(listaProductoresSerieVigentes))
	// {
	// for(int i=0;i<listaProductoresSerieVigentes.size();i++)
	// {
	// ProductorSerieVO
	// productorSerieVigenteVO=(ProductorSerieVO)listaProductoresSerieVigentes.get(i);
	//
	// if(!ListUtils.isEmpty(productoresSerie))
	// {
	// for(int j=0;j<productoresSerie.size();j++)
	// {
	// if(productoresSerie.get(j).getClass().equals(InfoProductorSerie.class))
	// {
	// InfoProductorSerie
	// infoProductorSerie=(IInfoProductorSerie)productoresSerie.get(j);
	// if(infoProductorSerie.getIdDescriptor().equalsIgnoreCase(productorSerieVigenteVO.getId()))
	// {
	//
	// borrar=false;
	// break;
	// }
	// }
	// }
	//
	// }
	//
	// if(borrar==true)
	// {
	// if(isSustituido(productorSerieVigenteVO,sustitucionVigenteHistorico))
	// borrar=false;
	// }
	// if(borrar == true){
	// if(isPasadoAHistorico(productorSerieVigenteVO,
	// listaProductoresPasadosAHistoricos))
	// borrar= false;
	// }
	// if(borrar==true)
	// {
	// borrarProductor(productorSerieVigenteVO);
	// }
	//
	// borrar=true;
	//
	// }
	// }
	// }

	/**
	 * Borra de la Tabla los productores elminados.
	 *
	 * @param listaInfoProductoresEliminados
	 */
	private void borrarProductoresEliminados(List listaInfoProductoresEliminados) {
		logger.debug("inicio borrarProductoresEliminados");

		if (ListUtils.isNotEmpty(listaInfoProductoresEliminados)) {
			for (Iterator iterator = listaInfoProductoresEliminados.iterator(); iterator
					.hasNext();) {
				IInfoProductorSerie infoProductorSerie = (IInfoProductorSerie) iterator
						.next();

				logger.debug("InfoProductorSerie:" + infoProductorSerie);

				if (infoProductorSerie != null
						&& infoProductorSerie.getProductorSerieVO() != null) {
					borrarProductor(infoProductorSerie.getProductorSerieVO());
				}
			}
		}

		logger.debug("fin borrarProductoresEliminados");
	}

	private void borrarProductor(ProductorSerieVO productorSerieVigenteVO) {
		logger.debug("inicio borrarProductor:" + productorSerieVigenteVO);

		_productorSerieDBEntity.delete(productorSerieVigenteVO.getIdserie(),
				productorSerieVigenteVO.getIdProductor());

		elminarOrganosProductores(productorSerieVigenteVO.getIdserie(),
				new String[] { productorSerieVigenteVO.getIdProductor() });

		// Se borra el organo de la lista de acceso
		String idLCA = productorSerieVigenteVO.getIdLCAPref();

		CAOrganoVO caOrganoVO = getCAOrgProductorVOXSistExtGestorYIdOrgsExtGestor(
				productorSerieVigenteVO.getIdSistExt(),
				productorSerieVigenteVO.getIdDescrSistExt());
		if (caOrganoVO != null) {
			_permisosListaDbEntity.deletePermisosLista(idLCA,
					TipoDestinatario.ORGANO,
					new String[] { caOrganoVO.getIdOrg() });
		}

		// Comprobar si la lista está vacía para eliminarla
		ListaAccesoVO listaAcceso = _listaControlAcceso
				.getListaAccesoVOXId(idLCA);
		if (listaAcceso != null) {
			if (isListaEliminable(idLCA, productorSerieVigenteVO.getIdserie())) {
				getGestionControlUsusarios()
						.eliminarListaAccesoNoTransaccional(idLCA);
			}
		}

		logger.debug("fin borrarProductor:" + productorSerieVigenteVO);

	}

	private boolean isListaEliminable(String idLCA, String idSerie) {
		boolean eliminable = false;

		if (getGestionControlUsusarios().isListaAccesoSinElementos(idLCA)) {
			GestionCuadroClasificacionBI cuadroService = getServiceRepository()
					.lookupGestionCuadroClasificacionBI();
			List elementosEnLista = cuadroService
					.getElementoCuadroClasificacionXListaControlAcceso(idLCA);

			if (util.CollectionUtils.isEmptyCollection(elementosEnLista)) {
				eliminable = true;
			}
		}

		return eliminable;
	}

	private InfoProdVigenteHistorico getInfoProdVigenteHistorico(
			List sustitucionVigenteHistorico,
			InfoOrganoProductorSerie infoOrganoProductorSerie) {
		if (!ListUtils.isEmpty(sustitucionVigenteHistorico)) {
			for (int i = 0; i < sustitucionVigenteHistorico.size(); i++) {
				InfoProdVigenteHistorico infoProdVigenteHistorico = (InfoProdVigenteHistorico) sustitucionVigenteHistorico
						.get(i);
				if (infoOrganoProductorSerie.equals(infoProdVigenteHistorico
						.getSustituidor())) {
					return infoProdVigenteHistorico;
				}

			}
		}
		return null;
	}

	public void saveIdentificacionSerieSinProcedimientoEnEstudio(SerieVO serie,
			IdentificacionSerie identificacionSerie,
			List listaProductoresHistoricosEnMemoria,
			List sustitucionVigenteHistorico,
			List listaProductoresPasadosAHistoricos,
			List listaProductoresHistoricosEliminados)
			throws FondosOperacionNoPermitidaException,
			GestorOrganismosException, NotAvailableException {
		logger.debug("inicio saveIdentificacionSerieSinProcedimientoEnEstudio");

		List listaProductoresVigentesEnTabla = _productorSerieDBEntity
				.getProductoresVigentesBySerie(serie.getId());

		// List listaProductoresHistoricosEnTabla =
		// _productorSerieDBEntity.getProductoresHistoricosBySerie(serie.getId());
		List productoresSerie = identificacionSerie
				.getInfoProductoresNoEliminados();

		String idLCAParaUnUnicoProductor = null;
		if (ListUtils.isEmpty(listaProductoresVigentesEnTabla)) {
			if (!ListUtils.isEmpty(productoresSerie)
					&& productoresSerie.size() == 1) {
				List listaProductoresHistoricos = _productorSerieDBEntity
						.getProductoresHistoricosBySerie(serie.getId());
				if (!ListUtils.isEmpty(listaProductoresHistoricos)
						&& tienenMismaLCA(listaProductoresHistoricos)) {
					idLCAParaUnUnicoProductor = getFirstLCA(listaProductoresHistoricos);
				}
			}
		}

		LoggingEvent logEvent = AuditFondos.getLogginEventIdentificacion(this);

		getAuthorizationHelper().verificarPermitidoModificarIdentificacion(
				serie);

		if (StringUtils.isNotEmpty(identificacionSerie.getDenominacion())
				&& !ConfigConstants.getInstance()
						.getDistinguirMayusculasMinusculas())
			identificacionSerie.setDenominacion(identificacionSerie
					.getDenominacion().toUpperCase());

		IdentificacionSerie identificacionAnterior = getIdentificacionSerie(serie);
		Locale locale = getServiceClient().getLocale();
		AuditFondos.addDataLogGuardarIdentificacion(locale, logEvent, serie,
				identificacionAnterior, identificacionSerie);

		iniciarTransaccion();

		EntidadProductoraVO entidadProductoraFondo = identificacionSerie
				.getEntidadProductoraFondo();

		_elementoCuadroClasificacionDBEntity.updateTitulo(serie.getId(),
				identificacionSerie.getDenominacion());

		_serieDBEntity.updateIdentificacion(serie.getId(),
				identificacionSerie.toString(), null, -1);

		borrarProductoresEliminados(identificacionSerie
				.getInfoProductoresSerieAEliminar());

		if (productoresSerie != null && productoresSerie.size() > 0) {
			ConfiguracionGeneral config = ConfiguracionSistemaArchivoFactory
					.getConfiguracionSistemaArchivo().getConfiguracionGeneral();
			IInfoProductorSerie unProductor = null;
			for (Iterator i = productoresSerie.iterator(); i.hasNext();) {
				unProductor = (IInfoProductorSerie) i.next();

				if (unProductor.isSinGuardar()
						&& (unProductor.isNuevo() || unProductor
								.isIncorporadoComoHistorico())) {

					DescriptorVO descriptor = null;

					if (unProductor instanceof InfoOrganoProductorSerie) {
						InfoOrganoProductorSerie organoProductorSerie = (InfoOrganoProductorSerie) unProductor;

						CAOrganoVO organo = _caOrganoDbEntity
								.getCAOrgProductorVOXSistExtGestorYIdOrgsExtGestorYVigencia(
										organoProductorSerie
												.getSistemaExterno(),
										organoProductorSerie
												.getIdEnSistemaExterno(), true);

						if(organo == null){
							organo = _caOrganoDbEntity.getCAOrgProductorVOXId(organoProductorSerie.getIdEnSistemaExterno());
						}



						String idListaDescriptor = null;
						if (organoProductorSerie.getTipoProductor() == ProductorSerieVO.TIPO_ENTIDAD)
							idListaDescriptor = config
									.getListaDescriptoresEntidad(
											TipoProductor.INSTITUCION
													.getIdentificador())
									.getId();
						else
							idListaDescriptor = config
									.getListaDescriptoresEntidad(
											TipoProductor.BDORGANIZACION
													.getIdentificador())
									.getId();

						descriptor = obtenerDescriptorOrgano(organo,
								idListaDescriptor);

						OrganoProductorVO organoProductor = _orgProductorDBEntity
								.getOrgProductorXIdDescr(descriptor.getId());
						if (organoProductor == null) {

							crearOrganoComoProductor(organo.getIdOrg(),
									descriptor.getId(),
									entidadProductoraFondo.getId());

							organoProductor = _orgProductorDBEntity
									.getOrgProductorXIdDescr(descriptor.getId());
						}
						ProductorSerieVO productorSerie = new ProductorSerieVO();
						productorSerie.setIdserie(serie.getId());
						productorSerie.setIdProductor(descriptor.getId());
						productorSerie.setTipoProductor(unProductor
								.getTipoProductor());

						int marcas = ProductoresUtils.getMarcaForAction(
								unProductor.getMarcas(),
								ProductoresUtils.ACCION_GUARDAR);

						productorSerie.setMarcas(marcas);
						productorSerie.setFechaInicial(unProductor
								.getFechaInicio());
						productorSerie.setFechaFinal(unProductor.getFechaFin());

						_productorSerieDBEntity
								.insertProductorVGSerieVO(productorSerie);

						if (idLCAParaUnUnicoProductor != null) {
							_productorSerieDBEntity.setListaControlAcceso(
									organoProductor.getId(), serie.getId(),
									idLCAParaUnUnicoProductor);
							actualizarAccesoOrganoProductor(
									idLCAParaUnUnicoProductor,
									organoProductor.getIdOrgano());
						} else {
							InfoProdVigenteHistorico infoProdVigenteHistorico = getInfoProdVigenteHistorico(
									sustitucionVigenteHistorico,
									(InfoOrganoProductorSerie) unProductor);
							if (infoProdVigenteHistorico == null) {
								crearListaAcceso(organoProductor, serie);
							} else {
								ProductorSerieVO productorSustituidoSerieVO = infoProdVigenteHistorico
										.getProductorSerieSustituido();
								String idLCA = productorSustituidoSerieVO
										.getIdLCAPref();
								_productorSerieDBEntity.setListaControlAcceso(
										organoProductor.getId(), serie.getId(),
										idLCA);
								actualizarAccesoOrganoProductor(idLCA,
										organoProductor.getIdOrgano());
							}
						}
					} else {
						descriptor = _descriptorDBEntity
								.getDescriptor(unProductor.getIdDescriptor());

						ProductorSerieVO productorSerie = new ProductorSerieVO();
						productorSerie.setIdserie(serie.getId());
						productorSerie.setIdProductor(descriptor.getId());
						productorSerie.setTipoProductor(unProductor
								.getTipoProductor());

						int marcas = ProductoresUtils.getMarcaForAction(
								unProductor.getMarcas(),
								ProductoresUtils.ACCION_GUARDAR);

						productorSerie.setMarcas(marcas);
						productorSerie.setFechaInicial(unProductor
								.getFechaInicio());
						productorSerie.setFechaFinal(unProductor.getFechaFin());

						_productorSerieDBEntity
								.insertProductorVGSerieVO(productorSerie);

						crearListaAcceso(descriptor.getNombre(),
								descriptor.getId(), serie);
					}
				} else if (unProductor.isModificado()) {
					int marcas = ProductoresUtils.getMarcaForAction(
							unProductor.getMarcas(),
							ProductoresUtils.ACCION_GUARDAR);
					unProductor.setMarcas(marcas);
					updateProductorSerie(unProductor.getProductorSerieVO());
				}
			}
		}
		commit();
	}

	public void crearOrganoComoProductor(String idOrgano, String idDescriptor,
			String idEntidadProductora) {

		OrganoProductorVO organoProductor = new OrganoProductorVO();
		organoProductor.setIdOrgano(idOrgano);
		organoProductor.setId(idDescriptor);
		organoProductor.setIdEntProdInst(idEntidadProductora);

		_orgProductorDBEntity.insertOrgProductorVO(organoProductor);
	}

	public void deletePermisosLista(String idLista, int tipoDestinatarios,
			String[] idDestinatarios) {
		_permisosListaDbEntity.deletePermisosLista(idLista, tipoDestinatarios,
				idDestinatarios);
	}

	/**
	 * Establece la identificación de una serie documental
	 *
	 * @param serie
	 *            Serie cuya identificación se establece
	 * @param identificacionSerie
	 *            Información que integra la identificación de la serie
	 *            documental
	 * @throws FondosOperacionNoPermitidaException
	 *             Caso de que el establecimiento de la identificación de la
	 *             serie no esté permitido
	 */
	public void saveIdentificacionSerie(SerieVO serie,
			IdentificacionSerie identificacionSerie)
			throws FondosOperacionNoPermitidaException,
			GestorOrganismosException, NotAvailableException {
		LoggingEvent logEvent = AuditFondos.getLogginEventIdentificacion(this);

		logger.debug("inicio saveIdentificacionSerie");

		getAuthorizationHelper().verificarPermitidoModificarIdentificacion(
				serie);

		IdentificacionSerie identificacionAnterior = getIdentificacionSerie(serie);
		Locale locale = getServiceClient().getLocale();
		AuditFondos.addDataLogGuardarIdentificacion(locale, logEvent, serie,
				identificacionAnterior, identificacionSerie);

		if (StringUtils.isNotEmpty(identificacionSerie.getDenominacion())
				&& !ConfigConstants.getInstance()
						.getDistinguirMayusculasMinusculas())
			identificacionSerie.setDenominacion(identificacionSerie
					.getDenominacion().toUpperCase());

		iniciarTransaccion();

		EntidadProductoraVO entidadProductoraFondo = identificacionSerie
				.getEntidadProductoraFondo();
		InfoBProcedimiento procedimiento = identificacionSerie
				.getProcedimiento();
		InfoBProcedimiento procedimientoAnterior = identificacionAnterior
				.getProcedimiento();

		if (procedimiento != null) {
			logger.debug("con procedimiento");
			if (procedimientoAnterior == null
					|| !procedimiento.getId().equals(
							procedimientoAnterior.getId())) {
				if (serie.getEstadoserie() != EstadoSerie.NO_VIGENTE)
					throw new ArchivoModelException(
							getClass(),
							"saveIdentificacionSerie",
							"Una vez la serie ha sido pasada a vigente no es posible modificar el procedimiento asociado a la serie");
			}
			_serieDBEntity.updateIdentificacion(serie.getId(),
					identificacionSerie.toString(), procedimiento.getId(),
					getTipoProcedimiento(procedimiento));
		} else {
			logger.debug("sin procedimiento actual");

			if (procedimientoAnterior != null) {
				if (serie.getEstadoserie() != EstadoSerie.NO_VIGENTE) {
					throw new ArchivoModelException(
							getClass(),
							"saveIdentificacionSerie",
							"Una vez la serie ha sido pasada a vigente no es posible modificar el procedimiento asociado a la serie");
				}
			}
			_serieDBEntity.updateIdentificacion(serie.getId(),
					identificacionSerie.toString(), null, -1);
		}
		_elementoCuadroClasificacionDBEntity.updateTitulo(serie.getId(),
				identificacionSerie.getDenominacion());

		List listaProductoresSerie = _productorSerieDBEntity
				.getProductoresXSerie(serie.getId());

		_productorSerieDBEntity.deleteProductoresVigentesSerie(serie.getId());

		String[] organosProductoresABorrar = getOrganosProductoresABorrar(listaProductoresSerie);

		if (organosProductoresABorrar != null
				&& organosProductoresABorrar.length > 0) {
			logger.debug("organosProductoresABorrar"
					+ organosProductoresABorrar);
			elminarOrganosProductores(serie.getId(), organosProductoresABorrar);
		}

		List productoresSerie = identificacionSerie
				.getListaInfoProductoresSerie();
		if (productoresSerie != null && productoresSerie.size() > 0) {
			ConfiguracionGeneral config = ConfiguracionSistemaArchivoFactory
					.getConfiguracionSistemaArchivo().getConfiguracionGeneral();
			IInfoProductorSerie unProductor = null;
			for (Iterator i = productoresSerie.iterator(); i.hasNext();) {
				unProductor = (IInfoProductorSerie) i.next();
				if (unProductor instanceof InfoOrganoProductorSerie) {
					InfoOrganoProductorSerie organoProductorSerie = (InfoOrganoProductorSerie) unProductor;
					CAOrganoVO organo = _caOrganoDbEntity
							.getCAOrgProductorVOXSistExtGestorYIdOrgsExtGestorYVigencia(
									organoProductorSerie.getSistemaExterno(),
									organoProductorSerie
											.getIdEnSistemaExterno(), true);
					String idListaDescriptor = null;
					if (organoProductorSerie.getTipoProductor() == ProductorSerieVO.TIPO_ENTIDAD)
						idListaDescriptor = config.getListaDescriptoresEntidad(
								TipoProductor.INSTITUCION.getIdentificador())
								.getId();
					else
						idListaDescriptor = config
								.getListaDescriptoresEntidad(
										TipoProductor.BDORGANIZACION
												.getIdentificador()).getId();
					DescriptorVO descriptor = obtenerDescriptorOrgano(organo,
							idListaDescriptor);

					OrganoProductorVO organoProductor = _orgProductorDBEntity
							.getOrgProductorXIdDescr(descriptor.getId());
					if (organoProductor == null) {
						crearOrganoComoProductor(organo.getIdOrg(),
								descriptor.getId(),
								entidadProductoraFondo.getId());
						organoProductor = _orgProductorDBEntity
								.getOrgProductorXIdDescr(descriptor.getId());
					}
					ProductorSerieVO productorSerie = new ProductorSerieVO();
					productorSerie.setIdserie(serie.getId());
					if (procedimiento != null)
						productorSerie
								.setIdprocedimiento(procedimiento.getId());
					productorSerie.setIdProductor(descriptor.getId());
					productorSerie.setTipoProductor(unProductor
							.getTipoProductor());
					if (unProductor.getFechaInicio() != null)
						productorSerie.setFechaInicial(unProductor
								.getFechaInicio());
					else
						productorSerie.setFechaInicial(DateUtils
								.getFechaActual());

					_productorSerieDBEntity
							.insertProductorVGSerieVO(productorSerie);
				} else {
					DescriptorVO descriptor = _descriptorDBEntity
							.getDescriptor(unProductor.getIdDescriptor());

					// Comprobar si está dado de alta como organo productor
					OrganoProductorVO organoProductor = _orgProductorDBEntity
							.getOrgProductorXIdDescr(descriptor.getId());

					if (descriptor != null
							&& StringUtils
									.isNotEmpty(descriptor.getIdSistExt())
							&& organoProductor == null) {
						CAOrganoVO organo = _caOrganoDbEntity
								.getCAOrgProductorVOXSistExtGestorYIdOrgsExtGestorYVigencia(
										descriptor.getIdSistExt(),
										descriptor.getIdDescrSistExt(), true);

						crearOrganoComoProductor(organo.getIdOrg(),
								descriptor.getId(),
								entidadProductoraFondo.getId());
						organoProductor = _orgProductorDBEntity
								.getOrgProductorXIdDescr(descriptor.getId());
					}

					ProductorSerieVO productorSerie = new ProductorSerieVO();
					productorSerie.setIdserie(serie.getId());
					if (procedimiento != null)
						productorSerie
								.setIdprocedimiento(procedimiento.getId());
					productorSerie.setIdProductor(descriptor.getId());
					productorSerie.setTipoProductor(unProductor
							.getTipoProductor());
					if (unProductor.getFechaInicio() != null) {
						productorSerie.setFechaInicial(unProductor
								.getFechaInicio());
					} else {
						productorSerie.setFechaInicial(DateUtils
								.getFechaActual());
					}

					_productorSerieDBEntity
							.insertProductorVGSerieVO(productorSerie);
				}
			}
		}

		commit();
		logger.debug("fin saveIdentificacionSerie");
	}

	private void elminarOrganosProductores(String idSerie,
			String[] idsOrganosProductores) {
		logger.debug("Inicio elminarOrganosProductores idSerie:" + idSerie
				+ " - idsOrganosProductores: " + idsOrganosProductores);
		String[] idsABorrar = new String[0];

		for (int i = 0; i < idsOrganosProductores.length; i++) {
			String idOrganoProductor = idsOrganosProductores[i];

			List listaProductores = _productorSerieDBEntity
					.getProductoresSerieXIdProductor(idsOrganosProductores[i]);

			boolean borrar = true;

			if (ListUtils.isNotEmpty(listaProductores)) {
				for (Iterator iterator = listaProductores.iterator(); iterator
						.hasNext();) {
					ProductorSerieVO productorSerieVO = (ProductorSerieVO) iterator
							.next();

					if (productorSerieVO != null
							&& !idSerie.equals(productorSerieVO.getIdserie())) {
						borrar = false;
					}
				}
			}

			if (borrar) {
				idsABorrar = (String[]) ArrayUtils.add(idsABorrar,
						idOrganoProductor);
			}
		}

		if (ArrayUtils.isNotEmpty(idsABorrar)) {
			_orgProductorDBEntity.deleteOrganosProductores(idsABorrar);
		}

		logger.debug("Inicio elminarOrganosProductores idSerie:" + idSerie
				+ " - idsOrganosProductores: " + idsABorrar);
	}

	/**
	 * Obtiene el número de solicitudes que están pendientes de aprobación
	 *
	 * @return Número de solicitudes
	 */
	public int getCountSolicitudesAGestionar() {
		int[] estadosAMostrar = { SolicitudSerie.ESTADO_SOLICITADA };
		return _solicitudSerieDBEntity.getCountSolicitudes(null, null,
				estadosAMostrar);
	}

	/**
	 * Obtiene la lista de solicitudes que están pendientes de aprobación
	 *
	 * @return Lista de solicitudes {@link SolicitudSerieVO}
	 */
	public List getSolicitudesAGestionar() {
		int[] estadosAMostrar = { SolicitudSerie.ESTADO_SOLICITADA };
		return _solicitudSerieDBEntity.getSolicitudes(null, null,
				estadosAMostrar);
	}

	/**
	 * Actualiza el volumen de la serie en las cantidades indicadas
	 *
	 * @param idSerie
	 *            Identificador de la serie
	 * @param incrementNumUdocs
	 *            Cantidad en la que se incrementa el numero de unidades
	 *            documentales de la serie. Puede ser positivo o negativo.
	 * @param incrementNumUIs
	 *            Cantidad en la que se incrementa el numero de unidades de
	 *            instalacion de la serie Puede ser positivo o negativo.
	 * @param incrementVolumen
	 *            Cantidad en la que se incrementa el volumen total de la serie
	 *            Puede ser positivo o negativo.
	 */
	public void updateVolumenSerieNoTransaccional(String idSerie) {
		int numUdocs = _unidadDocumentalDbEntity
				.getCountUDocsByIdSerie(idSerie);
		int numUIs = _udocEnUiDepositoDbEntity
				.getCountUnidadesInstalacionByIdSerie(idSerie);
		double volumen = _udocEnUiDepositoDbEntity.getVolumenSerie(idSerie);

		_serieDBEntity.updateVolumenSerie(idSerie, numUdocs, numUIs, volumen);
	}

	/**
	 * Actualiza el número de unidades documentales que contiene un serie
	 *
	 * @param idSerie
	 *            Identificador de serie del cuadro de clasificación
	 * @param tipoDocumental
	 *            Tipo documental en el que varia el contenido de la serie
	 * @param incremento
	 *            Cantidad en la que varia el numero de unidades del tipo
	 *            documental indicado que contiene la serie
	 */
	public void updateContenidoSerieNoTransaccional(String idSerie) {
		recalcularVolumenYSoporte(idSerie);
	}

	/**
	 * Actualiza las fechas extremas entre las que está comprendida la
	 * documentación contenida en una serie del cuadro de clasificacion
	 *
	 * @param idSerie
	 *            Identificador de serie
	 * @param fechaInicio
	 *            Fecha a establecer como fecha extrema inicial
	 * @param fechaFin
	 *            Fecha a establecer como fecha extrema final
	 */
	public void updateFechasExtremas(String idSerie, Date fechaInicio,
			Date fechaFin) {
		iniciarTransaccion();
		_serieDBEntity.updateFechasExtremas(idSerie, fechaInicio, fechaFin);
		commit();
	}

	/**
	 * Actualiza las fechas extremas entre las que está comprendida la
	 * documentación contenida en una serie del cuadro de clasificacion
	 *
	 * @param idSerie
	 *            Identificador de serie
	 */
	public void updateFechasExtremas(String idSerie) {
		iniciarTransaccion();
		_serieDBEntity.updateFechasExtremas(idSerie);
		commit();
	}

	/*
	 * (sin Javadoc)
	 *
	 * @see
	 * common.bi.GestionSeriesBI#getCountSeriesEnIdentificacion(java.lang.String
	 * )
	 */
	public int getCountSeriesEnIdentificacion(String idGestor) {
		int[] estadosSerie = { EstadoSerie.EN_ESTUDIO, EstadoSerie.NO_VIGENTE };
		int series = _serieDBEntity.getCountSeriesXGestor(idGestor,
				estadosSerie);
		return series;
	}

	/*
	 * (sin Javadoc)
	 *
	 * @see
	 * common.bi.GestionSeriesBI#getSeriesEnIdentificacion(java.lang.String)
	 */
	public List getSeriesEnIdentificacion(String idGestor) {
		int[] estadosSerie = { EstadoSerie.EN_ESTUDIO, EstadoSerie.NO_VIGENTE };
		List series = _serieDBEntity.getSeriesXGestor(idGestor, estadosSerie);
		return series;
	}

	/**
	 * Obtiene la lista de posibles productores que pueden ser incorporados a la
	 * identificación de una serie documental
	 *
	 * @param queryString
	 *            Patrón que debe estar contenido en el nombre de los
	 *            productores
	 * @param serie
	 *            Serie para la que se solicitan los productores
	 * @return Lista de productores {@link InfoProductorSerie}
	 * @throws GestorOrganismosException
	 *             Caso de que los posibles productores de la serie deban ser
	 *             obtenidos de un sistema externo y se produzca un problema en
	 *             la comunicación con ese sistema
	 * @throws NotAvailableException
	 *             Caso de que los posibles productores de la serie deban ser
	 *             obtenidos de un sistema externo y el sistema no implemente la
	 *             funcionalidad necesaria
	 */
	public List findPosiblesProductores(String queryString,
			IdentificacionSerie serie, boolean productorHistorico) throws GestorOrganismosException,
			NotAvailableException {
		final EntidadProductoraVO entidadProductora = serie
				.getEntidadProductoraFondo();
		InfoOrganoProductorSerie productorEntidadProductora = new InfoOrganoProductorSerie(
				ProductorSerieVO.TIPO_ENTIDAD,
				entidadProductora.getIdSistExt(),
				entidadProductora.getIdProductorEnSistGestor(),
				entidadProductora.getNombre(), entidadProductora.getCodigo());
		ConfiguracionGeneral config = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionGeneral();
		List posiblesProductores = null;


		if (StringUtils.isNotBlank(entidadProductora.getIdSistExt())) {

			String idSistExt = entidadProductora.getIdSistExt();
			String idSistExtDestino = entidadProductora.getIdSistExt();

			if(productorHistorico){

				String idSistExtHistoricos = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionFondos().getIdSistGestorOrgHistoricos();

				if(StringUtils.isNotBlank(idSistExtHistoricos)){
					idSistExt = idSistExtHistoricos;
				}
			}

			// Obtener información de la entidad
			ServiceClient serviceClient = getServiceClient();

			// Obtener la entidad para el usuario conectado
			Properties params = null;

			if ((serviceClient != null)
					&& (StringUtils.isNotEmpty(serviceClient.getEntity()))) {
				params = new Properties();
				params.put(MultiEntityConstants.ENTITY_PARAM,
						serviceClient.getEntity());
			}

			final GestorOrganismos gestorOrganismos = GestorOrganismosFactory
					.getConnectorById(idSistExt, params);
			posiblesProductores = gestorOrganismos
					.recuperarOrganos(queryString);
			if (ListUtils.isNotEmpty(posiblesProductores)) {
				posiblesProductores.remove(productorEntidadProductora);

				int numResultados = posiblesProductores.size();
				for (int count = 0; count < numResultados; count++) {
					InfoOrgano organo = (InfoOrgano) posiblesProductores
							.get(count);
					InfoOrganoProductorSerie infoProductorSerie = new InfoOrganoProductorSerie(
							ProductorSerieVO.TIPO_ORG_DEP,
							idSistExt, organo.getId(),
							NombreOrganoFormat.formatearNombreLargo(organo,
									gestorOrganismos
											.recuperarOrganosAntecesores(
													organo.getId(), 0)),
							organo.getCodigo());
					posiblesProductores.set(count, infoProductorSerie);
				}
			}
		} else {
			int tipoEntidadProductores = 0;
			if (entidadProductora.getTipoentidad() == TipoEntidad.INSTITUCION
					.getIdentificador())
				tipoEntidadProductores = TipoProductor.ORGANO
						.getIdentificador();
			else if (entidadProductora.getTipoentidad() == TipoEntidad.FAMILIA
					.getIdentificador())
				tipoEntidadProductores = TipoProductor.PERSONA
						.getIdentificador();
			else
				throw new ArchivoModelException(
						getClass(),
						"findPosiblesProductores",
						"El tipo de entidad del fondo no permite la selección de productores para sus series");

			ListaProductores listaDescriptores = config
					.getListaDescriptoresEntidad(tipoEntidadProductores);

			posiblesProductores = _descriptorDBEntity.findDescriptores(
					queryString, listaDescriptores.getId());
			CollectionUtils.transform(posiblesProductores, new Transformer() {
				public Object transform(Object obj) {
					DescriptorVO descriptorProductor = (DescriptorVO) obj;
					IInfoProductorSerie infoProductorSerie = new InfoProductorSerie(
							ProductorSerieVO.TIPO_ORG_DEP, descriptorProductor
									.getNombre());
					infoProductorSerie.setIdDescriptor(descriptorProductor
							.getId());
					return infoProductorSerie;
				}
			});
		}
		return posiblesProductores;
	}

	/**
	 * Obtiene el número de unidades documentales que contiene una serie
	 * documental
	 *
	 * @param idSerie
	 *            Identificador de serie documental
	 * @return Número de unidades documentales en la serie
	 */
	public int getCountUnidadesDocumentales(String idSerie) {
		return _elementoCuadroClasificacionDBEntity.countNumChilds(idSerie);
	}

	/**
	 * Elimina una serie del cuadro de clasificación de fondos documentales
	 *
	 * @param idSerie
	 *            Identificador de serie
	 * @throws ActionNotAllowedException
	 *             Cuando la eliminación de la serie no está permitida
	 */
	public void eliminarSerie(String idSerie) throws ActionNotAllowedException {
		SerieVO serie = getSerie(idSerie);
		// LoggingEvent logEvent = AuditFondos.getLogginEventRemove(this,
		// serie);


		//Comprobar que no exista ninguna prevision, relación o detalle vincluado a la serie
		if(_relacionEntregaDBEntity.getCountRelacionesBySerie(idSerie) > 0){
			throw new FondosOperacionNoPermitidaException(
			FondosOperacionNoPermitidaException.XNO_SE_PUEDE_ELIMINAR_SERIE_TIENE_RELACIONES);
		}

		if(_detallePrevisionDBEntity.getCountDetallesBySerie(idSerie) > 0){
			throw new FondosOperacionNoPermitidaException(
					FondosOperacionNoPermitidaException.XNO_SE_PUEDE_ELIMINAR_SERIE_TIENE_DETALLES);
		}

		checkPermission(FondosSecurityManager.ELIMINACION_ELEMENTO_ACTION);
		iniciarTransaccion();
		_productorSerieDBEntity.deleteProductoresSerie(idSerie);
		_solicitudSerieDBEntity.eliminarSolicitudesByIdSerie(idSerie);
		_serieDBEntity.removeSerie(idSerie);
		ServiceRepository services = ServiceRepository
				.getInstance(getServiceSession());
		GestionCuadroClasificacionBI cuadroClasificacionBI = services
				.lookupGestionCuadroClasificacionBI();
		cuadroClasificacionBI.removeElementoCF(serie);
		services.lookupGestionDescripcionBI().deleteFicha(idSerie,
				TipoFicha.FICHA_ELEMENTO_CF);
		// String
		// codReferenciaPadre=cuadroClasificacionBI.getElementoCuadroClasificacion(serie.getIdPadre()).getCodReferencia();
		// AuditFondos.addDataLogRemove(logEvent, serie, codReferenciaPadre);
		commit();
	}

	/**
	 * Elimina del sistema las solicitudes de autorización de acciones de
	 * gestión de series cuyos identificadores se suministran
	 *
	 * @param idSolicitud
	 *            Conjunto de identificadores de solicitudes de autorización
	 * @throws ActionNotAllowedException
	 *             Caso de que la eliminación de alguna de las solicitudes no
	 *             esté permitida
	 */
	public void eliminarSolicitudes(String[] idSolicitud)
			throws ActionNotAllowedException {
		iniciarTransaccion();
		_solicitudSerieDBEntity.eliminarSolicitudes(idSolicitud);
		commit();
	}

	/**
	 * Obtiene el volumen de una serie.
	 *
	 * @param idSerie
	 *            Identificador de una serie.
	 * @param tipoDocumental
	 *            Tipo documental.
	 * @return Volumen de una serie.
	 */
	public VolumenSerieVO getVolumenYSoporteSerie(String idSerie,
			String tipoDocumental) {
		return _volumenSerieDbEntity.getVolumenSerie(idSerie, tipoDocumental);
	}

	/**
	 * Obtiene la lista de volúmenes de una serie.
	 *
	 * @param idSerie
	 *            Identificador de una serie.
	 * @return Lista de volúmenes de una serie.
	 */
	public List getVolumenesYSoporteSerie(String idSerie) {
		return _volumenSerieDbEntity.getVolumenesSerie(idSerie);
	}

	/**
	 * Estable valores para las opciones de almacenamiento y visualización de
	 * descripción de una serie del cuadro de clasificación de fondos
	 * documentales
	 *
	 * @param Identificador
	 *            de serie del cuadro de clasificación de fondos documentales
	 * @param fichaDescripcionSerie
	 *            Identificador de la definición de ficha descriptiva a utilizar
	 *            para la descripción de la serie
	 * @param idRepEcmSerie
	 *            Identificador del repositorio ECM en el que se guardarán los
	 *            documentos anexados a la serie
	 * @param fichaDescripcionUdoc
	 *            Identificador de la definición de ficha descriptiva que se
	 *            empleará por defecto para la descripción de las unidades
	 *            documentales de la serie
	 * @param clasificadoresDocumentos
	 *            Platilla a utilizar en la inicialización de la estrutura de
	 *            clasificadores para la organización de los documentos anexados
	 *            a la serie
	 */
	public void setInfoSerie(String idSerie, String fichaDescripcionSerie,
			String idRepEcmSerie, List nivelesFichaUDocRepEcm)
			throws ActionNotAllowedException {
		setInfoSerie(idSerie, fichaDescripcionSerie, idRepEcmSerie,
				nivelesFichaUDocRepEcm, null);
	}

	public void setInfoSerie(String idSerie, String fichaDescripcionSerie,
			String idRepEcmSerie, List nivelesFichaUDocRepEcm, String idLCA)
			throws ActionNotAllowedException {
		checkPermission(FondosSecurityManager.GESTION_SOLICITUDES_SERIE_ACTION);

		SerieVO serie = _serieDBEntity.getSerie(idSerie);

		if (nivelesFichaUDocRepEcm != null) {
			serie.setNivelesFichaUDocRepEcm(nivelesFichaUDocRepEcm);
		}

		iniciarTransaccion();
		_serieDBEntity.updateInfoSerie(idSerie, fichaDescripcionSerie,
				idRepEcmSerie, serie.getInfoFichaUDocRepEcm(), idLCA, 0);

		if (nivelesFichaUDocRepEcm != null && nivelesFichaUDocRepEcm.size() > 0) {
			Iterator it = nivelesFichaUDocRepEcm.iterator();
			while (it.hasNext()) {
				NivelFichaUDocRepEcmVO nivelFichaUDocRepEcm = (NivelFichaUDocRepEcmVO) it
						.next();
				if (nivelFichaUDocRepEcm.isUpdateUDocs()) {
					Map colsToUpdate = new HashMap();
					colsToUpdate
							.put(ElementoCuadroClasificacionDBEntityImpl.REPOSITORIO_ECM_FIELD,
									nivelFichaUDocRepEcm.getIdRepEcmPrefUdoc());
					_elementoCuadroClasificacionDBEntity
							.updateElementosXIdPadreYNivel(serie.getId(),
									nivelFichaUDocRepEcm.getIdNivel(),
									colsToUpdate);
				}
			}
		}
		commit();
	}

	public void setInfoSerie(String idSerie, String fichaDescripcionSerie,
			String idRepEcmSerie, List nivelesFichaUDocRepEcm, String idLCA,
			int nivelAcceso, String codOrdenacion)
			throws ActionNotAllowedException {

		checkPermission(FondosSecurityManager.GESTION_SOLICITUDES_SERIE_ACTION);

		SerieVO serie = _serieDBEntity.getSerie(idSerie);

		if (nivelesFichaUDocRepEcm != null) {
			serie.setNivelesFichaUDocRepEcm(nivelesFichaUDocRepEcm);
		}

		iniciarTransaccion();
		_serieDBEntity.updateInfoSerie(idSerie, fichaDescripcionSerie,
				idRepEcmSerie, serie.getInfoFichaUDocRepEcm(), idLCA,
				nivelAcceso);

		serie.setOrdPos(codOrdenacion);
		_elementoCuadroClasificacionDBEntity.updateElementoCF(serie.getId(),
				serie.getIdNivel(), serie.getCodigo(), serie.getDenominacion(),
				serie.getOrdPos());

		if (nivelesFichaUDocRepEcm != null && nivelesFichaUDocRepEcm.size() > 0) {
			Iterator it = nivelesFichaUDocRepEcm.iterator();
			while (it.hasNext()) {
				NivelFichaUDocRepEcmVO nivelFichaUDocRepEcm = (NivelFichaUDocRepEcmVO) it
						.next();
				if (nivelFichaUDocRepEcm.isUpdateUDocs()) {
					Map colsToUpdate = new HashMap();
					colsToUpdate
							.put(ElementoCuadroClasificacionDBEntityImpl.REPOSITORIO_ECM_FIELD,
									nivelFichaUDocRepEcm.getIdRepEcmPrefUdoc());
					_elementoCuadroClasificacionDBEntity
							.updateElementosXIdPadreYNivel(serie.getId(),
									nivelFichaUDocRepEcm.getIdNivel(),
									colsToUpdate);
				}
			}
		}
		commit();
	}

	/**
	 * Obtiene la lista de unidades documentales que contiene una serie del
	 * cuadro de clasificación de fondos documentales.
	 *
	 * @param idPadre
	 *            Identificador de serie.
	 * @param pageInfo
	 *            Información de la paginación.
	 * @return Lista de unidades documentales que contiene la serie
	 *         {@link ElementoCuadroClasificacionVO}.
	 * @throws TooManyResultsException
	 *             si el número de resultados excede el máximo.
	 */
	public List getUdocsSerie(String idSerie, PageInfo pageInfo)
			throws TooManyResultsException {
		int[] estados = { IElementoCuadroClasificacion.VIGENTE };
		return _serieDBEntity.getUdocsSerie(idSerie, estados, pageInfo, false);
	}

	public List getUdocsSerieConFechasExtremas(String idSerie, PageInfo pageInfo)
			throws TooManyResultsException {

		List listaUdocs = _serieDBEntity.getUdocsSerieConFechasExtremas(
				getServiceClient(), idSerie, pageInfo, null);

		completarInformacionInteresadosFechasProductores(listaUdocs, true,
				true, true);

		return listaUdocs;

	}

	public List getUdocsSerieByProductor(String idSerie, PageInfo pageInfo,
			String productor) throws TooManyResultsException {
		BusquedaUdocsSerieVO busquedaVO = new BusquedaUdocsSerieVO();
		busquedaVO.setProductor(productor);

		List listaUdocs = _serieDBEntity.getUdocsSerieConFechasExtremas(
				getServiceClient(), idSerie, pageInfo, busquedaVO);

		if (FondosConstants.TODOS_PRODUCTORES.equals(productor))
			completarInformacionInteresadosFechasProductores(listaUdocs, true,
					true, true);
		else
			completarInformacionInteresadosFechasProductores(listaUdocs, true,
					true, false);

		return listaUdocs;
	}

	public int getCountProductorUdocsSerie(String idSerie) {
		return _serieDBEntity.getCountProductoresUdocsSerie(getServiceClient(),
				idSerie);
	}

	public Map getProductoresUdocsSerie(String idSerie)
			throws TooManyResultsException {
		List productores = _serieDBEntity.getProductoresUdocsSerie(
				getServiceClient(), idSerie);
		Map productoresUdocsSerie = new HashMap();
		for (int i = 0; i < productores.size(); i++) {
			ElementoCuadroClasificacion elemento = (ElementoCuadroClasificacion) productores
					.get(i);
			if (StringUtils.isNotEmpty(elemento.getIdobjref())
					&& StringUtils.isNotEmpty(elemento.getNombreProductor()))
				productoresUdocsSerie.put(elemento.getIdobjref(),
						elemento.getNombreCortoProductor());
		}
		return productoresUdocsSerie;
	}

	class SelectorVolumen implements Predicate {
		String idVolumen = null;

		public SelectorVolumen(String idVolumen) {
			this.idVolumen = idVolumen;
		}

		public boolean evaluate(Object obj) {
			return idVolumen.equals(((IRepositorioEcmVO) obj).getId());
		}
	}

	/**
	 * Obtiene las opciones de visualización y almacenamiento definidos para una
	 * serie
	 *
	 * @param serie
	 *            Serie del cuadro de clasificación de fondos documentales
	 * @return Opciones de descripción y almacenamiento establecidas para la
	 *         serie
	 */
	public OpcionesDescripcionSerieVO getOpcionesDescripcionSerie(SerieVO serie) {
		OpcionesDescripcionSerieVO infoDescripcionSerie = new OpcionesDescripcionSerieVO();
		ServiceRepository services = ServiceRepository
				.getInstance(getServiceSession());
		GestionDescripcionBI descripcionBI = services
				.lookupGestionDescripcionBI();
		GestionDocumentosElectronicosBI documentosBI = services
				.lookupGestionDocumentosElectronicosBI();
		GestionCuadroClasificacionBI cuadroBI = services
				.lookupGestionCuadroClasificacionBI();

		// Información de la serie
		List repositoriosEcm = documentosBI.getRepositoriosEcm();

		if (StringUtils.isNotBlank(serie.getIdRepEcm()))
			infoDescripcionSerie
					.setVolumenSerie((IRepositorioEcmVO) CollectionUtils.find(
							repositoriosEcm,
							new SelectorVolumen(serie.getIdRepEcm())));

		if (StringUtils.isNotBlank(serie.getIdFichaDescr()))
			infoDescripcionSerie.setFichaDescripcionSerie(descripcionBI
					.getInfoBFicha(serie.getIdFichaDescr()));

		// Información de las unidades documentales de la serie
		if (StringUtils.isNotBlank(serie.getInfoFichaUDocRepEcm())) {
			if (serie.getNivelesFichaUDocRepEcm() != null
					&& serie.getNivelesFichaUDocRepEcm().size() > 0) {
				List infoUDocs = new ArrayList();
				Iterator it = serie.getNivelesFichaUDocRepEcm().iterator();
				while (it.hasNext()) {
					NivelFichaUDocRepEcmVO nivelFichaUDocRepEcmVO = (NivelFichaUDocRepEcmVO) it
							.next();

					INivelCFVO nivel = cuadroBI
							.getNivelCF(nivelFichaUDocRepEcmVO.getIdNivel());
					InfoBFichaVO infoBFichaUDoc = descripcionBI
							.getInfoBFicha(nivelFichaUDocRepEcmVO
									.getIdFichaDescrPrefUdoc());
					InfoBFichaClfVO infoBFichaClfUDoc = documentosBI
							.getInfoBFicha(nivelFichaUDocRepEcmVO
									.getIdFichaClfDocPrefUdoc());
					SelectorVolumen selectorVolumen = null;
					if (nivelFichaUDocRepEcmVO.getIdRepEcmPrefUdoc() != null)
						selectorVolumen = new SelectorVolumen(
								nivelFichaUDocRepEcmVO.getIdRepEcmPrefUdoc());

					IRepositorioEcmVO repositorioEcmUDoc = (IRepositorioEcmVO) CollectionUtils
							.find(repositoriosEcm, selectorVolumen);

					InfoBFichaUDocRepEcmUDocsSerie infoBFichaUDocRepEcmUDocsSerie = new InfoBFichaUDocRepEcmUDocsSerie();
					infoBFichaUDocRepEcmUDocsSerie.setNivelUDoc(nivel);
					infoBFichaUDocRepEcmUDocsSerie.setFicha(infoBFichaUDoc);
					infoBFichaUDocRepEcmUDocsSerie
							.setFichaClf(infoBFichaClfUDoc);
					infoBFichaUDocRepEcmUDocsSerie
							.setRepositorioEcm(repositorioEcmUDoc);
					infoBFichaUDocRepEcmUDocsSerie
							.setUpdateUDocs(nivelFichaUDocRepEcmVO
									.isUpdateUDocs());

					infoUDocs.add(infoBFichaUDocRepEcmUDocsSerie);
				}

				infoDescripcionSerie
						.setInfoFichaUDocRepEcmUDocsSerie(infoUDocs);
			}
		}

		return infoDescripcionSerie;
	}

	public List findProcedimientos(int tipoProcedimientos, String searchToken)
			throws GestorCatalogoException, NotAvailableException {
		// Obtener información de la entidad
		ServiceClient serviceClient = getServiceClient();

		// Obtener la entidad para el usuario conectado
		Properties params = null;

		if ((serviceClient != null)
				&& (StringUtils.isNotEmpty(serviceClient.getEntity()))) {
			params = new Properties();
			params.put(MultiEntityConstants.ENTITY_PARAM,
					serviceClient.getEntity());
		}
		GestorCatalogo catalogoService = GestorCatalogoFactory
				.getConnector(params);
		List procedimientos = catalogoService.recuperarInfoBProcedimientos(
				tipoProcedimientos, searchToken);

		List procedimientosUsados = _serieDBEntity
				.getProcedimientosIdentificados();
		List procedimientosAEliminar = new ArrayList();
		InfoBProcedimiento unProcedimiento = null;
		for (Iterator i = procedimientos.iterator(); i.hasNext();) {
			unProcedimiento = (InfoBProcedimiento) i.next();
			if (procedimientosUsados.contains(unProcedimiento.getId()))
				procedimientosAEliminar.add(unProcedimiento);
		}
		procedimientos.removeAll(procedimientosAEliminar);
		return procedimientos;
	}

	private SerieAuthorizationHelper getAuthorizationHelper() {
		return new SerieAuthorizationHelper();
	}

	class SerieAuthorizationHelper {

		void verificarPermitidoSolicitarAlta(
				ElementoCuadroClasificacionVO elementoPadre, String codigoSerie)
				throws FondosOperacionNoPermitidaException {
			if (_elementoCuadroClasificacionDBEntity.getElementoCFXCodigo(
					codigoSerie, elementoPadre.getId()) != null)
				throw new FondosOperacionNoPermitidaException(
						FondosOperacionNoPermitidaException.XCODIGO_YA_EXISTE);

		}

		void verificarPermitidoCrear(
				ElementoCuadroClasificacionVO clasificador, SerieVO serie)
				throws FondosOperacionNoPermitidaException {
			if (_elementoCuadroClasificacionDBEntity.getElementoCFXCodigo(
					serie.getCodigo(), clasificador.getId()) != null)
				throw new FondosOperacionNoPermitidaException(
						FondosOperacionNoPermitidaException.XCODIGO_YA_EXISTE);

		}

		void verificarPermitidoPasoAEstudio(SerieVO serie)
				throws FondosOperacionNoPermitidaException {
			if (serie.getEstadoserie() != EstadoSerie.VIGENTE
					&& serie.getEstadoserie() != EstadoSerie.HISTORICA)
				throw new FondosOperacionNoPermitidaException(
						FondosOperacionNoPermitidaException.XLA_SERIE_SE_ENCUENTRA_EN_UN_ESTADO_INCORRECTO_PARA_REALIZAR_ESTA_OPERACION);
		}

		void verificarPermitidoSolicitarPasoAVigente(SerieVO serie)
				throws FondosOperacionNoPermitidaException {
			if (serie.getEstadoserie() != EstadoSerie.NO_VIGENTE) {
				throw new FondosOperacionNoPermitidaException(
						FondosOperacionNoPermitidaException.XLA_SERIE_SE_ENCUENTRA_EN_UN_ESTADO_INCORRECTO_PARA_REALIZAR_ESTA_OPERACION);
			} else if (!serie.getIdusrgestor().equalsIgnoreCase(
					getServiceClient().getId()))
				throw new FondosOperacionNoPermitidaException(
						FondosOperacionNoPermitidaException.XNO_TIENE_PERMISOS_SOBRE_LA_SERIE);
			else {
				ElementoCuadroClasificacionVO clasificadorSeries = _elementoCuadroClasificacionDBEntity
						.getElementoPadre(serie.getId());
				if (!clasificadorSeries.isVigente())
					throw new FondosOperacionNoPermitidaException(
							FondosOperacionNoPermitidaException.XNO_SE_PUEDE_CAMBIAR_ESTADO_X_PADRE_TIENE_ESTADO_NO_VIGENTE);
			}
			verificarIdentificacionSerie(serie);
			verificarDescripcionSerie(serie);
		}

		public void verificarPermitidoSolicitarPasoAHistorica(SerieVO serie)
				throws FondosOperacionNoPermitidaException {
			if (serie.getEstadoserie() == EstadoSerie.VIGENTE) {
				if (!serie.getIdusrgestor().equalsIgnoreCase(
						getServiceClient().getId()))
					throw new FondosOperacionNoPermitidaException(
							FondosOperacionNoPermitidaException.XNO_TIENE_PERMISOS_SOBRE_LA_SERIE);
			} else
				throw new FondosOperacionNoPermitidaException(
						FondosOperacionNoPermitidaException.XLA_SERIE_SE_ENCUENTRA_EN_UN_ESTADO_INCORRECTO_PARA_REALIZAR_ESTA_OPERACION);

		}

		void verificarPermitidoPasoAVigente(SerieVO serie)
				throws FondosOperacionNoPermitidaException {
			if (serie.getEstadoserie() == EstadoSerie.NO_VIGENTE
					|| serie.getEstadoserie() == EstadoSerie.PENDIENTE_VIGENTE
					|| serie.getEstadoserie() == EstadoSerie.PENDIENTE_AUTORIZACION_CAMBIOS
					|| serie.getEstadoserie() == EstadoSerie.EN_ESTUDIO) {
				ElementoCuadroClasificacionVO clasificadorSeries = _elementoCuadroClasificacionDBEntity
						.getElementoPadre(serie.getId());
				if (!clasificadorSeries.isVigente())
					throw new FondosOperacionNoPermitidaException(
							FondosOperacionNoPermitidaException.XNO_SE_PUEDE_CAMBIAR_ESTADO_X_PADRE_TIENE_ESTADO_NO_VIGENTE);
				else {
					verificarIdentificacionSerie(serie);
					verificarDescripcionSerie(serie);
				}

			} else
				throw new FondosOperacionNoPermitidaException(
						FondosOperacionNoPermitidaException.XLA_SERIE_SE_ENCUENTRA_EN_UN_ESTADO_INCORRECTO_PARA_REALIZAR_ESTA_OPERACION);

		}

		void verificarPermitidoSolicitarAutorizacionCambios(SerieVO serie)
				throws FondosOperacionNoPermitidaException {
			if (serie.getEstadoserie() != EstadoSerie.EN_ESTUDIO) {
				throw new FondosOperacionNoPermitidaException(
						FondosOperacionNoPermitidaException.XLA_SERIE_SE_ENCUENTRA_EN_UN_ESTADO_INCORRECTO_PARA_REALIZAR_ESTA_OPERACION);
			} else if (!serie.getIdusrgestor().equalsIgnoreCase(
					getServiceClient().getId()))
				throw new FondosOperacionNoPermitidaException(
						FondosOperacionNoPermitidaException.XNO_TIENE_PERMISOS_SOBRE_LA_SERIE);
		}

		void verificarPermitidoModificarIdentificacion(SerieVO serie)
				throws FondosOperacionNoPermitidaException {
			if (serie.getEstadoserie() == EstadoSerie.EN_ESTUDIO
					|| serie.getEstadoserie() == EstadoSerie.NO_VIGENTE) {
				if (!serie.getIdusrgestor().equalsIgnoreCase(
						getServiceClient().getId()))
					throw new FondosOperacionNoPermitidaException(
							FondosOperacionNoPermitidaException.XNO_TIENE_PERMISOS_SOBRE_LA_SERIE);
			} else
				throw new FondosOperacionNoPermitidaException(
						FondosOperacionNoPermitidaException.XLA_SERIE_SE_ENCUENTRA_EN_UN_ESTADO_INCORRECTO_PARA_REALIZAR_ESTA_OPERACION);
		}

		private void verificarIdentificacionSerie(SerieVO serie)
				throws FondosOperacionNoPermitidaException {
			if (!GenericValidator.isBlankOrNull(serie.getTitulo())) {
				if (!GenericValidator.isBlankOrNull(serie.getIdentificacion())) {
					// IdentificacionSerie identificacion =
					// parseIdentificacion(serie
					// .getIdentificacion());
					IdentificacionSerie identificacion = getIdentificacionSerie(serie);
					if (identificacion != null) {
						if (!GenericValidator.isBlankOrNull(identificacion
								.getDefinicion())) {
							// if
							// (!GenericValidator.isBlankOrNull(identificacion.getTramites()))
							// {
							if (!GenericValidator.isBlankOrNull(identificacion
									.getNormativa())) {
								// comprobar si tiene productores
								List productores = getProductoresSerie(serie
										.getId());
								if (!util.CollectionUtils.isEmpty(productores)) {
									return;
								} else
									throw new FondosOperacionNoPermitidaException(
											FondosOperacionNoPermitidaException.XLA_SERIE_NO_ES_PASABLE_A_VIGENTE_NO_TIENE_PRODUCTORES_ASOCIADOS);
							} else
								throw new FondosOperacionNoPermitidaException(
										FondosOperacionNoPermitidaException.XLA_SERIE_NO_ES_PASABLE_A_VIGENTE_FALTA_NORMATIVA);
							// } else
							// throw new FondosOperacionNoPermitidaException(
							// FondosOperacionNoPermitidaException.XLA_SERIE_NO_ES_PASABLE_A_VIGENTE_FALTA_TRAMITES);
						} else
							throw new FondosOperacionNoPermitidaException(
									FondosOperacionNoPermitidaException.XLA_SERIE_NO_ES_PASABLE_A_VIGENTE_FALTA_DEFINICION);
					} else
						throw new FondosOperacionNoPermitidaException(
								FondosOperacionNoPermitidaException.XLA_SERIE_NO_ES_PASABLE_A_VIGENTE_NO_ESTA_IDENTIFICADA);
				} else
					throw new FondosOperacionNoPermitidaException(
							FondosOperacionNoPermitidaException.XLA_SERIE_NO_ES_PASABLE_A_VIGENTE_NO_ESTA_IDENTIFICADA);
			} else
				throw new FondosOperacionNoPermitidaException(
						FondosOperacionNoPermitidaException.XLA_SERIE_NO_ES_PASABLE_A_VIGENTE_FALTA_TITULO);

		}

		private void verificarDescripcionSerie(SerieVO serie)
				throws FondosOperacionNoPermitidaException {
			ServiceRepository services = ServiceRepository
					.getInstance(getServiceSession());
			DefFicha definicionFicha = DefFichaFactory.getInstance(
					getServiceClient())
					.getDefFichaById(serie.getIdFichaDescr());
			List camposObligatorios = definicionFicha.getDatosObligatorios();
			if (camposObligatorios != null) {
				GestionDescripcionBI descripcionBI = services
						.lookupGestionDescripcionBI();
				for (Iterator i = camposObligatorios.iterator(); i.hasNext();) {
					DefCampoDato datoFicha = (DefCampoDato) i.next();
					List valoresDato = descripcionBI.getValues(
							TipoFicha.FICHA_ELEMENTO_CF,
							datoFicha.getTipoDato(), serie.getId(),
							datoFicha.getId());
					if (valoresDato == null || valoresDato.size() == 0)
						throw new FondosOperacionNoPermitidaException(
								FondosOperacionNoPermitidaException.CAMPOS_OBLIGATORIOS_DESCRIPCION_SIN_VALOR);
				}
			}

		}

		public void verificarPermitidoMover(SerieVO serie,
				ElementoCuadroClasificacionVO clasificadorSeries)
				throws FondosOperacionNoPermitidaException {
			// if (_unidadDocumentalDBEntity.countUdocsEnFondo(fondo.getId()) >
			// 0)
			// throw new FondosOperacionNoPermitidaException(
			// FondosOperacionNoPermitidaException.XFONDO_NO_MOVIBLE_XTENER_UDOCS);
			if (clasificadorSeries != null) {
				verificarPermitidoCrear(clasificadorSeries, serie);
			}
		}

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see common.bi.GestionSeriesBI#getSeriesParaPrevision(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	public List findSeriesParaPrevision(String fondoID, String codigo,
			String titulo) {
		return _serieDBEntity.findSeriesInEstados(
				createSearchConditions(fondoID, codigo, titulo), new int[] {
						EstadoSerie.VIGENTE, EstadoSerie.HISTORICA });
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see common.bi.GestionSeriesBI#busquedaSolicitudesSeries(fondos.vos.
	 * BusquedaSolicitudesSerieVO)
	 */
	public Collection busquedaSolicitudesSeries(
			BusquedaSolicitudesSerieVO busquedaSolicitudSerieVO)
			throws TooManyResultsException {

		return _solicitudSerieBusquedasDbEntity
				.findSolicitudes(busquedaSolicitudSerieVO);

		// return
		// _serieDBEntity.findSeriesInEstados(createSearchConditions(fondoID,
		// codigo, titulo),
		// new int[] { EstadoSerie.VIGENTE, EstadoSerie.HISTORICA });

	}

	public void moverSerie(SerieVO serie, String idNuevoPadre)
			throws FondosOperacionNoPermitidaException {
		// Auditoria
		checkPermission(FondosSecurityManager.MODIFICAR_ELEMENTO_ACTION);
		ElementoCuadroClasificacionVO nuevoPadre = _elementoCuadroClasificacionDBEntity
				.getElementoCuadroClasificacion(idNuevoPadre);

		SerieAuthorizationHelper authorizationHelper = getAuthorizationHelper();
		authorizationHelper.verificarPermitidoMover(serie, nuevoPadre);

		// NivelCFVO nivelElementoPadre = getGestionCuadroClasificacionBI()
		// .getNivelCF(nuevoPadre.getIdNivel());

		// NivelCFVO nivelSerie = getGestionCuadroClasificacionBI()
		// .getNivelCF(serie.getIdNivel());

		// String nuevoCodigoReferenciaSerie
		// =_elementoCuadroClasificacionDBEntity.composeCodigoReferencia(nivelElementoPadre
		// ,nuevoPadre.getFinalCodRefPadre(),nuevoPadre.getCodigo(),
		// nuevoPadre.getCodRefFondo(), serie.getCodigo());

		// serie.setCodReferencia(nuevoCodigoReferenciaSerie);

		// UpdateCodRefsReturns updateCodRefsReturns =
		// _elementoCuadroClasificacionDBEntity
		// .updateCodRefs(serie.getId(), nivelSerie, serie.getCodigo(),
		// _nivelDBEntity
		// .getNivelCF(nuevoPadre.getIdNivel()),
		// nuevoPadre.getFinalCodRefPadre(),
		// nuevoPadre.getCodigo(), nuevoCodigoReferenciaSerie);

		// serie.setFinalCodRefPadre(updateCodRefsReturns.getFinalCodRefPadre());
		// serie.setCodReferencia(nuevoCodigoReferenciaSerie);
		// serie.setIdPadre(idNuevoPadre);

		// _elementoCuadroClasificacionDBEntity.updateElementoCuadroClasificacion(serie);
		// _serieDBEntity.updateSerie(serie);

		GestionCuadroClasificacionBI cuadroBI = ServiceRepository.getInstance(
				getServiceClient()).lookupGestionCuadroClasificacionBI();
		cuadroBI.moverElemento(serie, idNuevoPadre);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * common.bi.GestionSeriesBI#getSeriesDestinoMovimientoUDocs(java.lang.String
	 * , java.lang.String, java.lang.String)
	 */
	public List getSeriesDestinoMovimientoUDocs(String fondo, String codigo,
			String titulo, String idSerieOriginal) {
		List seriesActivas = getSeriesActivas(fondo, codigo, titulo);
		final String idSerieAEliminar = idSerieOriginal;
		CollectionUtils.filter(seriesActivas, new Predicate() {
			public boolean evaluate(Object arg0) {
				return !((SerieVO) arg0).getId().equals(idSerieAEliminar);
			}
		});
		return seriesActivas;
	}

	public OrganoProductorVO getOrgProductorXIdDescr(String idDescr) {

		return _orgProductorDBEntity.getOrgProductorXIdDescr(idDescr);
	}

	public CAOrganoVO getCAOrgProductorVOXId(String idOrgano) {
		return _caOrganoDbEntity.getCAOrgProductorVOXId(idOrgano);
	}

	public ProductorSerieVO getProductorXIdSerieEIdDescriptorOrgano(
			String idSerie, String idOrgano) {
		return _productorSerieDBEntity.getProductorXIdSerieEIdDescriptorOrgano(
				idSerie, idOrgano);
	}

	public List getPermisosXIdListaIdDestino(String idLista, String idDestino) {
		return _permisosListaDbEntity.getPermisosXIdListaIdDestino(idLista,
				idDestino);
	}

	public ListaAccesoVO getListaAcceso(String idListaAcceso) {
		return _listaControlAcceso.getListaAccesoVOXId(idListaAcceso);
	}

	public List getPermisosXIdLista(String idLista) {
		return _permisosListaDbEntity.getPermisosXIdLista(idLista);
	}

	public List getProductoresVigentesOriginalesBySerie(String idSerie) {
		return _productorSerieDBEntity
				.getProductoresVigentesOriginalesBySerie(idSerie);
	}

	public List getProductoresHistoricosBySerieFechaFinalMayorQueFecha(
			String idSerie, Date date) {
		return _productorSerieDBEntity
				.getProductoresHistoricosBySerieFechaFinalMayorQueFecha(
						idSerie, date);
	}

	/*
	 * (sin Javadoc)
	 *
	 * @see
	 * common.bi.GestionSeriesBI#verificarPermitidoModificarIdentificacion(fondos
	 * .vos.SerieVO)
	 */
	public void verificarPermitidoModificarIdentificacion(SerieVO serie)
			throws FondosOperacionNoPermitidaException {
		getAuthorizationHelper().verificarPermitidoModificarIdentificacion(
				serie);
	}

	public void updateByIdSerieIdProductor(ProductorSerieVO productorVO) {
		_productorSerieDBEntity.updateByIdSerieIdProductor(productorVO);
	}

	/**
	 * Método estático para lanzar la actualización de las fechas extremas de
	 * una serie tanto en la tabla ASGFSERIE como en las tabla ADVCFECHA
	 *
	 * @param idSerie
	 * @param fechaInicial
	 * @param fechaFinal
	 * @param seriesBI
	 * @param descripcionBI
	 */

	public void actualizarFechasSerie(String idSerie, Date fechaInicial,
			Date fechaFinal, GestionDescripcionBI descripcionBI) {
		// GestionSeriesBI managerSeries = services.lookupGestionSeriesBI();
		// GestionDescripcionBI descripcionBI =
		// services.lookupGestionDescripcionBI();

		updateFechasExtremas(idSerie, fechaInicial, fechaFinal);
		Map paramActualizacionFicha = new HashMap();
		paramActualizacionFicha
				.put(ADReglaGenDatosContants.ACCIONES,
						new int[] { ADReglaGenDatosContants.ACTUALIZAR_FECHAS_EXTREMAS });
		paramActualizacionFicha.put(ADReglaGenDatosContants.FECHA_INICIAL,
				fechaInicial);
		paramActualizacionFicha.put(ADReglaGenDatosContants.FECHA_FINAL,
				fechaFinal);
		descripcionBI.generarAutomaticos(idSerie, TipoFicha.FICHA_ELEMENTO_CF,
				paramActualizacionFicha);
	}

	public List findSeriesParaPrevisionByIdOrgano(String fondoID,
			String codigo, String titulo, String idOrgano) {

		List lista = createSearchConditions(fondoID, codigo, titulo);

		String[] idsSerie = getIdsSeriesOrganoProductor(idOrgano);

		return _serieDBEntity.findSeriesInEstados(lista, new int[] {
				EstadoSerie.VIGENTE, EstadoSerie.HISTORICA }, idsSerie, true);

	}

	/**
	 * Obtiene los procedimientos de los que un determinado organo remitente
	 * puede transferir documentacion al archivo
	 *
	 * @param idOrgano
	 *            Identificador del organo remitente
	 * @throws NotAvailableException
	 *             Funcionalidad no implementada por el sistema de gestion del
	 *             catalogo de procedimientos
	 */
	private String[] getIdsSeriesOrganoProductor(String idOrgano) {
		String idProductor = null;

		String[] idsSerie = new String[0];
		if (idOrgano != null) {
			OrganoProductorVO productor = _orgProductorDBEntity
					.getOrgProductorXIdOrgano(idOrgano);

			if (productor != null) {
				idProductor = productor.getId();

				List listaIds = _productorSerieDBEntity.getIdsSeriesXProductor(
						idProductor, ProductorSerieVO.TIPO_ORG_DEP);

				if (ListUtils.isNotEmpty(listaIds)) {
					for (Iterator iterator = listaIds.iterator(); iterator
							.hasNext();) {
						String idSerie = (String) iterator.next();

						idsSerie = (String[]) ArrayUtils.add(idsSerie, idSerie);
					}
				}

			} else {
				logger.error("Error al obtener el órgano productor asociado al órgano del usuario "
						+ idOrgano);
			}
		}

		return idsSerie;
	}

	public SolicitudSerieVO getSolicitudBySerie(String idSerie) {
		SolicitudSerieVO solicitudVO = _solicitudSerieDBEntity
				.getSolicitudBySerie(idSerie);
		return solicitudVO;
	}

	public Date getFechaInicialExtremaUdocsSerie(String idSerie) {
		return _serieDBEntity.getFechaInicialExtremaUdocsSerie(idSerie);
	}

	public Date getFechaFinalExtremaUdocsSerie(String idSerie) {
		return _serieDBEntity.getFechaFinalExtremaUdocsSerie(idSerie);
	}

	public boolean isProductorConUdocs(String idSerie, String idProductor) {
		int numUdocs = _serieDBEntity.getCountUdocsSerieByProductor(idSerie,
				idProductor);

		if (numUdocs > 0) {
			return true;
		}

		return false;
	}

	public String getGuid() throws Exception {
		return GuidManager.generateGUID(getServiceClient().getEngine());
	}

	public List getInteresadosByIdsElementoCF(String[] idsElementos) {
		return _referenciaDBDbEntity
				.getInteresadosByIdsElementosCF(idsElementos);
	}

	public void completarInformacionInteresadosFechasProductores(
			List listaUnidadesDocumentales, boolean addInteresados,
			boolean addFechas, boolean addProductores) {
		if (ListUtils.isNotEmpty(listaUnidadesDocumentales)) {
			String[] idsElementosCF = new String[0];
			Map listaUdocsMap = new LinkedHashMap();

			for (Iterator iterator = listaUnidadesDocumentales.iterator(); iterator
					.hasNext();) {
				IUnidadDocumental unidadDocumental = (IUnidadDocumental) iterator
						.next();

				if (unidadDocumental != null) {
					idsElementosCF = (String[]) ArrayUtils.add(idsElementosCF,
							unidadDocumental.getId());
					listaUdocsMap.put(unidadDocumental.getId(),
							unidadDocumental);
				}
			}

			if (addInteresados) {
				List listaInteresados = _referenciaDBDbEntity
						.getInteresadosByIdsElementosCF(idsElementosCF);

				if (ListUtils.isNotEmpty(listaInteresados)) {
					for (Iterator iterator = listaInteresados.iterator(); iterator
							.hasNext();) {
						InteresadoUdocVO interesadoUdocVO = (InteresadoUdocVO) iterator
								.next();

						if (interesadoUdocVO != null) {
							String idElementoCF = interesadoUdocVO
									.getIdelementocf();

							UnidadDocumental udoc = (UnidadDocumental) listaUdocsMap
									.get(idElementoCF);

							udoc.addInteresado(interesadoUdocVO);

						}
					}
				}
			}

			if (addFechas) {
				List listaFechas = _fechaDbEntity
						.getFechasByIdsElementosCF(idsElementosCF);

				if (ListUtils.isNotEmpty(listaFechas)) {
					for (Iterator iterator = listaFechas.iterator(); iterator
							.hasNext();) {
						FechaInicialFinalElementoCF fechaVO = (FechaInicialFinalElementoCF) iterator
								.next();

						if (fechaVO != null) {
							String idElementoCF = fechaVO.getIdelementocf();

							UnidadDocumental udoc = (UnidadDocumental) listaUdocsMap
									.get(idElementoCF);
							udoc.setFechaInicial(fechaVO.getFechaInicio());
							udoc.setFechaFinal(fechaVO.getFechaFin());
						}
					}
				}

			}

			if (addProductores) {
				List listaProductores = _referenciaDBDbEntity
						.getProductoresByIdsElementosCF(idsElementosCF);

				if (ListUtils.isNotEmpty(listaProductores)) {
					for (Iterator iterator = listaProductores.iterator(); iterator
							.hasNext();) {
						ProductorElementoCF productorVO = (ProductorElementoCF) iterator
								.next();

						if (productorVO != null) {
							String idElementoCF = productorVO.getIdelementocf();

							IUnidadDocumental udoc = (IUnidadDocumental) listaUdocsMap
									.get(idElementoCF);

							udoc.setNombreProductor(productorVO.getNombre());

						}
					}
				}
			}
		}
	}

	public List getUdocsSerie(String idSerie, PageInfo pageInfo,
			BusquedaUdocsSerieVO busquedaUdocsSerieVO)
			throws TooManyResultsException {
		List listaUdocs = _serieDBEntity.getUdocsSerieConFechasExtremas(
				getServiceClient(), idSerie, pageInfo, busquedaUdocsSerieVO);

		if (FondosConstants.TODOS_PRODUCTORES.equals(busquedaUdocsSerieVO
				.getProductor()))
			completarInformacionInteresadosFechasProductores(listaUdocs, true,
					true, true);
		else
			completarInformacionInteresadosFechasProductores(listaUdocs, true,
					true, false);

		return listaUdocs;
	}

	public List getUdocsSerieFromView(String idSerie, PageInfo pageInfo,
			BusquedaUdocsSerieVO busquedaUdocsSerieVO) {
		List listaUdocs = _unidadDocumentalVistaDBEntity
				.getUnidadesDocumentalesSerie(getServiceClient(), idSerie,
						busquedaUdocsSerieVO);

		if (FondosConstants.TODOS_PRODUCTORES.equals(busquedaUdocsSerieVO
				.getProductor()))
			completarInformacionInteresadosFechasProductores(listaUdocs, true,
					false, true);
		else
			completarInformacionInteresadosFechasProductores(listaUdocs, true,
					false, false);

		return listaUdocs;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see common.bi.GestionSeriesBI#getUdocsSerieByIdsElementos(java.lang.String[])
	 */
	public List getUdocsSerieByIdsElementos(String[] idsElementos) {
		List listaUdocs = _serieDBEntity.getUdocsSerieByIdsElementos(null,
				idsElementos, false);
		completarInformacionInteresadosFechasProductores(listaUdocs, true,
				true, false);

		return listaUdocs;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see common.bi.GestionSeriesBI#getUdocsSerieConservadas(java.lang.String[])
	 */
	public List getUdocsSerieConservadas(String[] idsElementos) {
		List listaUdocs = _serieDBEntity.getUdocsSerieByIdsElementos(null,
				idsElementos, true);
		completarInformacionInteresadosFechasProductores(listaUdocs, true,
				true, false);
		return listaUdocs;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see common.bi.GestionSeriesBI#getUdocsSerieConservadasByIdSerie(java.lang.String)
	 */
	public List getUdocsSerieConservadasByIdSerie(String idSerie) {
		List listaUdocs = _serieDBEntity.getUdocsSerieByIdsElementos(idSerie,
				null, true);
		completarInformacionInteresadosFechasProductores(listaUdocs, true,
				true, false);
		return listaUdocs;
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see common.bi.GestionSeriesBI#establecerSerieTransferenciaElectronica(transferencias.vos.TransferenciaElectronicaInfo,
	 *      boolean)
	 */
	public void establecerSerieTransferenciaElectronica(
			TransferenciaElectronicaInfo info, boolean crearSerie)
			throws TransferenciaElectronicaException {

		if (logger.isDebugEnabled()) {
			logger.debug("Establecer la serie de la tansferencia");
		}

		SerieVO serie = getGestionSeriesBI().getSerieProcedimiento(
				info.getCodigoProcedimiento());

		ElementoCuadroClasificacionVO elementoPadre = null;

		InformacionSerie infoSerie = info.getContenidoUDocXML()
				.getInformacionSerie();

		if (serie == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("No existe la serie asociada al procedimiento "
						+ info.getCodigoProcedimiento());
			}

			if (infoSerie != null) {

				String idPadre = infoSerie.getIdPadre();

				elementoPadre = getGestionCuadroClasificacionBI()
						.getElementoCuadroClasificacion(idPadre);

				if (elementoPadre != null) {

					if (elementoPadre.isVigente()) {

						// Comprobar el tipo
						if (elementoPadre.isTipoFondo()
								|| elementoPadre.isTipoClasificadorSerie()) {

							String idFondo = elementoPadre.getIdFondo();

							if (elementoPadre.isTipoFondo()) {
								idFondo = elementoPadre.getId();
							}

							FondoVO fondo = getGestionFondosBI().getFondoXId(idFondo);

							FondoPO fondoPO = (FondoPO) FondoToPOTransformer
									.getInstance(getServiceRepository())
									.transform(fondo);
							info.setFondo(fondoPO);

							Serie serieVO = getSerieVOFromInfoSerie(info
									.getAppUser().getId(), infoSerie,
									elementoPadre.getId());
							try {
								Procedimiento procedimiento = infoSerie
										.getProcedimiento();

								serie = nuevaSerieVigenteVinculadaAProcedimientoExterno(
										elementoPadre, serieVO, procedimiento);

								gestionarProductorSerie(serie, info);
								info.setInsertarFichaSerie(true);

							} catch (ActionNotAllowedException e) {
								logger.error(e);

								throw new TransferenciaElectronicaException(
										TransferenciasElectronicasConstants.ERROR_SIN_PERMISOS,
										TransferenciasElectronicasConstants.getParametro(TransferenciasElectronicasConstants.PARAMETRO_CREAR_SERIE),
										e);
							} catch (Exception e) {
								logger.error(e);
								throw new TransferenciaElectronicaException(
										TransferenciasElectronicasConstants.ERROR_EXCEPTION,
										TransferenciasElectronicasConstants.getParametro(TransferenciasElectronicasConstants.PARAMETRO_CREAR_SERIE),
										e);
							}

						} else {
							logger.error("El tipo de elemento padre con id "
									+ infoSerie.getIdPadre()
									+ " no es un fondo o clasificador de serie");
							throw new TransferenciaElectronicaException(
									TransferenciasElectronicasConstants.ERROR_ELEMENTO_PADRE_SERIE_INCORRECTO, idPadre);
						}

					} else {
						logger.error("El elemento padre de la serie no está vigente");
						throw new TransferenciaElectronicaException(
								TransferenciasElectronicasConstants.ERROR_ELEMENTO_PADRE_NO_VIGENTE, idPadre);
					}
				} else {
					logger.error("No existe el elemento con id "
							+ infoSerie.getIdPadre());
					throw new TransferenciaElectronicaException(
							TransferenciasElectronicasConstants.ERROR_ELEMENTO_PADRE_INEXSTENTE, idPadre);
				}

			} else {
				throw new TransferenciaElectronicaException(
						TransferenciasElectronicasConstants.ERROR_INFORMACION_REQUERIDA,
						TransferenciasElectronicasConstants.getParametro(TransferenciasElectronicasConstants.PARAMETRO_OBJETO_INFO_TRANSFERENCIA)
					);
			}
		} else {
			gestionarProductorSerie(serie, info);
		}

		info.setSerieVO(serie);
		actualizarProductoresSerie(info);

	}

	/**
	 * Crea una serie para realizar transferencias electrónicas.
	 *
	 * @param elementoPadre
	 * @param infoAltaSerie
	 * @param procedimiento
	 * @return
	 * @throws Exception
	 */
	public SerieVO nuevaSerieVigenteVinculadaAProcedimientoExterno(
			ElementoCuadroClasificacionVO elementoPadre, SerieVO infoAltaSerie,
			IProcedimiento procedimiento) throws Exception {
		// auditoria
		LoggingEvent logEvent = AuditFondos.getLogginEventAlta(this);

		// chequeo de seguridad
		checkPermission(FondosSecurityManager.ALTA_DIRECTA_SERIE);

		SerieVO serie = nuevaSerieNoTransaccional(elementoPadre, infoAltaSerie,
				logEvent, IElementoCuadroClasificacion.VIGENTE,
				EstadoSerie.VIGENTE, EstadoSerie.NO_VIGENTE);

		return serie;

	}

	private SerieVO nuevaSerieNoTransaccional(
			ElementoCuadroClasificacionVO elementoPadre, SerieVO infoAltaSerie,
			LoggingEvent logEvent, int estadoCF, int estadoSerie,
			int estadoAutoriz) throws FondosOperacionNoPermitidaException {

		IElementoCuadroClasificacion parentElement = (IElementoCuadroClasificacion) elementoPadre;
		ElementoCuadroClasificacionVO elementoCF = (ElementoCuadroClasificacionVO) infoAltaSerie;
		elementoCF.setIdFondo(parentElement.getIdFondo());
		elementoCF.setTipo(TipoNivelCF.SERIE.getIdentificador());
		elementoCF.setCodRefFondo(parentElement.getCodRefFondo());

		INivelCFVO nivelElementoPadre = getGestionCuadroClasificacionBI()
				.getNivelCF(elementoPadre.getIdNivel());

		elementoCF.setFinalCodRefPadre(_elementoCuadroClasificacionDBEntity
				.composeFinalCodRefPadre(nivelElementoPadre,
						elementoPadre.getFinalCodRefPadre(),
						elementoPadre.getCodigo()));

		elementoCF.setCodReferencia(_elementoCuadroClasificacionDBEntity
				.composeCodigoReferencia(nivelElementoPadre,
						elementoPadre.getFinalCodRefPadre(),
						elementoPadre.getCodigo(),
						elementoPadre.getCodRefFondo(),
						infoAltaSerie.getCodigo()));

		elementoCF.setEstado(estadoCF);
		elementoCF.setEditClfDocs(Constants.FALSE_STRING);
		elementoCF.setNivelAcceso(NivelAcceso.PUBLICO);

		SerieVO serieVO = (SerieVO) infoAltaSerie;
		serieVO.setEstadoserie(estadoSerie);
		serieVO.setUltimoestadoautoriz(estadoAutoriz);
		serieVO.setTienedescr(Constants.FALSE_STRING);

		// Si no está activada la distinción de mayúsculas/minúsculas, no hacer
		// el toUpper
		if (!ConfigConstants.getInstance().getDistinguirMayusculasMinusculas())
			serieVO.setTitulo(serieVO.getTitulo().toUpperCase());

		getAuthorizationHelper().verificarPermitidoCrear(elementoPadre,
				infoAltaSerie);

		_elementoCuadroClasificacionDBEntity.insertElementoCF(elementoCF);

		SerieVO serieProxy = _serieDBEntity.insertSerie(infoAltaSerie,
				elementoCF.getId());
		INivelCFVO nivelserieProxy = getGestionCuadroClasificacionBI()
				.getNivelCF(serieProxy.getIdNivel());

		// crear la ficha descriptora para la serie
		getServiceRepository().lookupGestionDescripcionBI().createFicha(
				elementoCF.getId(), TipoFicha.FICHA_ELEMENTO_CF);

		parentElement.addChild((IElementoCuadroClasificacion) serieProxy);

		ElementoCuadroClasificacionVO padre = _elementoCuadroClasificacionDBEntity
				.getElementoCuadroClasificacion(infoAltaSerie.getIdPadre());
		Locale locale = getServiceClient().getLocale();
		AuditFondos.addDataLogAlta(locale, logEvent, serieProxy,
				nivelserieProxy.getNombre(), padre.getCodReferencia());

		return serieProxy;

	}

	/**
	 * Comprueba si existen los productores en el sistema y en caso contrario,
	 * los da de alta.
	 *
	 * @param serie
	 * @param info
	 * @param aniadirProductoresInexistentes
	 * @throws TransferenciaElectronicaException
	 */
	private void gestionarProductorSerie(SerieVO serie,
			TransferenciaElectronicaInfo info)
			throws TransferenciaElectronicaException {

		Productor productor = info.getProductor();

		InfoOrgano datosOrgano = productor.getInfoOrgano();

		if (info.getFondo() == null) {
			FondoVO fondoVO = getGestionFondosBI().getFondoXId(
					serie.getIdFondo());

			FondoPO fondoPO = (FondoPO) FondoToPOTransformer.getInstance(
					getServiceRepository()).transform(fondoVO);
			info.setFondo(fondoPO);
		}

		// Comprobar si existe el órgano
		CAOrganoVO organo = getGestionControlUsusarios()
				.getCAOrgProductorVOXSistExtGestorYIdOrgsExtGestor(
						info.getIdSistGestorOrg(), datosOrgano.getId(),
						Constants.TRUE_STRING);

		if (organo == null) {

			logger.info("Se va a proceder a dar de alta el órgano en el sistema de organización");

				try {
					getGestionControlUsusarios().insertProductorCatalogoEnSistema(
							datosOrgano, info.getFondo().getEntidadProductora(),
							info.getFondo().getIdArchivo(),
							info.getGestorOrganismos());
				} catch (GestorOrganismosException e) {
					throw new TransferenciaElectronicaException(e);
				} catch (NotAvailableException e) {
					throw new TransferenciaElectronicaException(e);
				}



			// Comprobar si existe el órgano
			organo = getGestionControlUsusarios()
					.getCAOrgProductorVOXSistExtGestorYIdOrgsExtGestor(
							info.getIdSistGestorOrg(), datosOrgano.getId(),
							Constants.TRUE_STRING);

		}

		info.setOrganoProductorSerie(organo);

		// Obtener el descriptor del órgano
		info.setDescriptorProductorSerieVO(obtenerDescriptorOrgano(organo,
				info.getIdListaDescriptorOrganizacion()));

		if (logger.isDebugEnabled()) {
			logger.debug("Fin procesarOrganoProductor");
		}
	}

	/**
	 * Actualiza la información de los productores de la serie
	 *
	 * @param info
	 */
	private void actualizarProductoresSerie(TransferenciaElectronicaInfo info) {

		String idSerie = info.getSerieVO().getId();

		ProductorSerieVO productorSerie = _productorSerieDBEntity
				.getProductorXId(idSerie, info.getDescriptorProductorSerieVO()
						.getId());

		if (productorSerie == null) {

			productorSerie = new ProductorSerieVO();
			productorSerie.setIdserie(idSerie);
			productorSerie.setIdprocedimiento(info.getCodigoProcedimiento());
			productorSerie.setIdProductor(info.getDescriptorProductorSerieVO()
					.getId());
			productorSerie.setTipoProductor(ProductorSerieVO.TIPO_ORG_DEP);
			productorSerie.setFechaInicial(DateUtils.getFechaActual());
			// productorSerie
			// .setIdLCAPref(idLCAParaUnUnicoProductor);
			_productorSerieDBEntity.insertProductorVGSerieVO(productorSerie);

			String idDescriptor = info.getDescriptorProductorSerieVO().getId();

			OrganoProductorVO organoProductor = _orgProductorDBEntity
			.getOrgProductorXIdDescr(idDescriptor);



			if (organoProductor == null) {
				crearOrganoComoProductor(info.getOrganoProductorSerie().getIdOrg(), idDescriptor,
						info.getFondo().getEntidadProductora().getId());

				organoProductor = _orgProductorDBEntity
						.getOrgProductorXIdDescr(idDescriptor);
			}

			crearListaAcceso(organoProductor, info.getSerieVO());
		}
	}

	private Serie getSerieVOFromInfoSerie(String idUsuario,
			InformacionSerie infoSerie, String idPadre) throws TransferenciaElectronicaException {
		Serie infoAltaSerie = new Serie();

		Procedimiento procedimiento = infoSerie.getProcedimiento();

		infoAltaSerie.setTipoProcedimiento(IProcedimiento.EXTERNO);
		infoAltaSerie.setIdProcedimiento(procedimiento.getCodigo());
		infoAltaSerie.setEstado(EstadoSerie.VIGENTE);

		IdentificacionSerie identificacionSerie = new IdentificacionSerie();
		identificacionSerie.vincularAProcedimientoExterno(procedimiento);

		infoAltaSerie.setIdentificacion(identificacionSerie.toString());

		// Usuario Gestor
		infoAltaSerie.setIdusrgestor(idUsuario);

		infoAltaSerie.setCodigo(infoSerie.getProcedimiento().getCodigo());
		infoAltaSerie.setDenominacion(infoSerie.getProcedimiento()
				.getTituloCorto());
		infoAltaSerie.setIdPadre(idPadre);
		infoAltaSerie.setIdNivel(getConfiguracionWsTransferencias()
				.getIdNivelSerie());
		infoAltaSerie.setIdFichaDescr(getConfiguracionWsTransferencias()
				.getIdFichaSeries());


		//Comprobar que el repositorio Ecm Existe

		if(StringUtils.isNotEmpty(infoSerie.getIdRepEcmSerie())){
			IRepositorioEcmVO repositorioEcmVO = getGestionDocumentosElectronicosBI()
			.getRepositorioEcm(infoSerie.getIdRepEcmSerie());

			if(repositorioEcmVO == null){
				throw new TransferenciaElectronicaException(
						TransferenciasElectronicasConstants.ERROR_REPOSITORIO_NO_CONFIGURADO,
						new Object[] { "idRepEcmSerie", infoSerie.getIdRepEcmSerie()});
			}
		}

		infoAltaSerie.setIdRepEcm(infoSerie.getIdRepEcmSerie());

		NivelFichaUDocRepEcmVO nivelFichaUDocRepEcmVO = new NivelFichaUDocRepEcmVO();
		nivelFichaUDocRepEcmVO.setIdFichaClfDocPrefUdoc(infoSerie.getIdFichaClfDocPrefUdoc());
		nivelFichaUDocRepEcmVO.setIdFichaDescrPrefUdoc(infoSerie
				.getIdFichaUdocs());
		nivelFichaUDocRepEcmVO.setIdNivel(getConfiguracionWsTransferencias()
				.getIdNivelUdoc());



		if(StringUtils.isNotEmpty(infoSerie.getIdRepEcmSerie())){
			IRepositorioEcmVO repositorioEcmVO = getGestionDocumentosElectronicosBI()
			.getRepositorioEcm(infoSerie.getIdRepEcmSerie());

			if(repositorioEcmVO == null){
				throw new TransferenciaElectronicaException(
						TransferenciasElectronicasConstants.ERROR_REPOSITORIO_NO_CONFIGURADO,
						new Object[] { "idRepEcmUdoc", infoSerie.getIdRepEcmUdoc()});
			}
		}


		nivelFichaUDocRepEcmVO.setIdRepEcmPrefUdoc(infoSerie.getIdRepEcmUdoc());

		infoAltaSerie.addNivelFichaUDocRepEcm(nivelFichaUDocRepEcmVO);

		return infoAltaSerie;
	}


	private ConfiguracionWsTransferencias getConfiguracionWsTransferencias() {
		return ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo()
				.getConfiguracionWsTransferencias();
	}

}