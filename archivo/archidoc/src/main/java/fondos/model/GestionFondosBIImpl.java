package fondos.model;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import se.NotAvailableException;
import se.instituciones.GestorOrganismos;
import se.instituciones.GestorOrganismosFactory;
import se.instituciones.InfoOrgano;
import se.instituciones.TipoAtributo;
import se.instituciones.exceptions.GestorOrganismosException;
import se.procedimientos.GestorCatalogo;
import se.procedimientos.GestorCatalogoFactory;
import se.procedimientos.exceptions.GestorCatalogoException;
import se.usuarios.AppPermissions;
import se.usuarios.ServiceClient;
import transferencias.MarcaUdocRelacionConstants;
import transferencias.exceptions.RelacionOperacionNoPermitidaException;
import util.CollectionUtils;
import xml.config.ConfiguracionFondos;
import xml.config.ConfiguracionSistemaArchivo;
import xml.config.ConfiguracionSistemaArchivoFactory;
import xml.config.ListaProductores;
import auditoria.logger.DataLoggingEvent;
import auditoria.logger.IArchivoLogger;
import auditoria.logger.LoggingEvent;

import common.ConfigConstants;
import common.Constants;
import common.MultiEntityConstants;
import common.bi.GestionCuadroClasificacionBI;
import common.bi.GestionDescripcionBI;
import common.bi.GestionFondosBI;
import common.bi.ServiceBase;
import common.bi.ServiceRepository;
import common.db.DBUtils;
import common.exceptions.ActionNotAllowedException;
import common.exceptions.ArchivoModelException;
import common.security.FondosSecurityManager;
import common.util.CustomDateRange;
import common.util.ListUtils;
import common.util.MarcaUtil;

import descripcion.db.ICatalogoListaDescriptoresDBEntity;
import descripcion.db.IDescriptorDBEntity;
import descripcion.model.EstadoDescriptor;
import descripcion.model.TipoDescriptor;
import descripcion.model.automaticos.ADReglaGenDatosContants;
import descripcion.model.xml.card.TipoFicha;
import descripcion.model.xml.definition.DefCampoDato;
import descripcion.model.xml.definition.DefFicha;
import descripcion.model.xml.definition.DefFichaFactory;
import descripcion.vos.DescriptorVO;
import descripcion.vos.ListaDescrVO;
import fondos.db.IElementoCuadroClasificacionDbEntity;
import fondos.db.IEntidadProductoraDBEntity;
import fondos.db.IFondoDbEntity;
import fondos.db.INivelCFDBEntity;
import fondos.db.IOrganoProductorDbEntity;
import fondos.db.ISerieDbEntity;
import fondos.db.IUnidadDocumentalDbEntity;
import fondos.exceptions.FondosOperacionNoPermitidaException;
import fondos.vos.ElementoCuadroClasificacionVO;
import fondos.vos.EntidadProductoraVO;
import fondos.vos.FondoVO;
import fondos.vos.INivelCFVO;
import gcontrol.model.NivelAcceso;

public class GestionFondosBIImpl extends ServiceBase implements GestionFondosBI {
	/** Logger para la auditoría de la aplicación */
	IArchivoLogger sLogger = null;

	/** Logger para el volcado de los mensajes de log del sistema */
	Logger logger = Logger.getLogger(GestionFondosBIImpl.class);

	IFondoDbEntity _fondoDBEntity = null;
	IEntidadProductoraDBEntity _entidadProductoraDBEntity = null;
	IElementoCuadroClasificacionDbEntity _elementoCuadroClasificacionDBEntity = null;
	ISerieDbEntity _serieDBEntity = null;
	IUnidadDocumentalDbEntity _unidadDocumentalDBEntity = null;
	IDescriptorDBEntity _descriptorDbEntity = null;
	IOrganoProductorDbEntity _organoProductorDbEntity = null;
	ICatalogoListaDescriptoresDBEntity _catalogoListaDescriptoresDBEntity = null;
	INivelCFDBEntity _nivelCFDbEntity = null;

	public GestionFondosBIImpl(
			IFondoDbEntity fondoDBEntity,
			IEntidadProductoraDBEntity entidadProductoraDBEntity,
			IElementoCuadroClasificacionDbEntity elementoCuadroClasificacionDBEntity,
			ISerieDbEntity serieDBEntity,
			IDescriptorDBEntity descriptorDBEntity,
			IOrganoProductorDbEntity organoProductorDBEntity,
			ICatalogoListaDescriptoresDBEntity catalogoListaDescriptoresDBEntity,
			INivelCFDBEntity nivelCFDBEntity,
			IUnidadDocumentalDbEntity unidadDocumentalDBEntity) {
		this._fondoDBEntity = fondoDBEntity;
		this._entidadProductoraDBEntity = entidadProductoraDBEntity;
		this._elementoCuadroClasificacionDBEntity = elementoCuadroClasificacionDBEntity;
		this._serieDBEntity = serieDBEntity;
		this._descriptorDbEntity = descriptorDBEntity;
		this._organoProductorDbEntity = organoProductorDBEntity;
		this._catalogoListaDescriptoresDBEntity = catalogoListaDescriptoresDBEntity;
		this._nivelCFDbEntity = nivelCFDBEntity;
		this._unidadDocumentalDBEntity = unidadDocumentalDBEntity;
	}

	public FondoVO getFondoXId(String idFondo) {
		return _fondoDBEntity.getFondoXId(idFondo);
	}

	public List getFondosVigentes() {
		int[] estado = { IElementoCuadroClasificacion.VIGENTE };
		return getFondosXEstados(estado);
	}

	public List getFondosNoVigentes() {
		int[] estado = { IElementoCuadroClasificacion.NO_VIGENTE };
		return getFondosXEstados(estado);
	}

	public List getFondos() {
		return _fondoDBEntity.getFondos();
	}

	public List getFondosXEstados(int[] estados) {
		return _fondoDBEntity.getFondosXEstados(estados);
	}

	/**
	 * Comprueba si durante la identificacion de las series del fondo indicado
	 * es posible asociarles un procedimiento. El hecho de que se pueda o no
	 * vincular la serie con un procedimiento vendrá deteminado por la entidad
	 * productora del fondo al que pertenece la serie. Si esa entidad productora
	 * se corresponde con una institución que es gestionada por un sistema
	 * externo de gestión de organización y se dispone de un sistema de gestión
	 * de procedimientos asociado a dicho sistema externo de gestión de
	 * organización la vinculación de un procedimiento con la serie será
	 * posible. Esta comprobación es necesaria para asegurar que los órganos
	 * productores del procedimiento que se seleccione seán órganos
	 * pertenecientes a la misma estructura organizativa que la institución
	 * asignada como entidad productora del fondo documental al que pertenece la
	 * serie.
	 *
	 * @param idFondo
	 *            Identificador del fondo para el que se realiza la comprobación
	 * @return <b>true </b> caso de que la vinculación de procedimiento es
	 *         posible y <b>false </b> cuando no lo es
	 */
	// public boolean isPosibleTenerProcedimiento(String idFondo) {
	// FondoVO fondoVO = getFondoXId(idFondo);
	// EntidadProductoraVO entidadProductora =
	// getEntidadProductoraXIdDescr(fondoVO.getIdEntProductora());
	// boolean returnValue = false;
	// if (entidadProductora.getTipoentidad() ==
	// TipoEntidad.INSTITUCION.getIdentificador()) {
	// SistemaGestorCatalogo gestorCatalago =
	// ConfiguracionSistemaArchivoFactory.getConfiguracionSistemaArchivo().getSistemaGestorCatalogo();
	// returnValue =
	// gestorCatalago.getIdSistGestorOrg().equalsIgnoreCase(entidadProductora.getIdSistExt());
	// }
	// return returnValue;
	// }

	public List getSeriesAceptanTransferencias(String idFondo) {
		int estados[] = new int[] { EstadoSerie.VIGENTE, EstadoSerie.HISTORICA,
				EstadoSerie.PENDIENTE_HISTORICA };
		return _serieDBEntity.getSeriesXFondoYEstados(idFondo, estados);
	}

	// private String appendDelimiter(String field, String delimiter) {
	// return field != null && !StringUtils.isBlank(field) ? delimiter : "";
	// }

	// private String appendField(String field) {
	// return field != null && !StringUtils.isBlank(field) ? field : "";
	// }
	//
	// private String appendDelimiterAndField(String delimiter, String field) {
	// return field != null && !StringUtils.isBlank(field) ? delimiter + field :
	// "";
	// }

	private String generarCodigoReferenciaFondo(FondoVO fondoVO,
			ElementoCuadroClasificacionVO padre) {
		ConfiguracionFondos config = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionFondos();
		String delimiter = config.getDelimitadorCodigoReferencia();
		StringBuffer codigoFondo = new StringBuffer()
				.append(fondoVO.getCodPais()).append(delimiter)
				.append(fondoVO.getCodComunidad()).append(delimiter)
				.append(fondoVO.getCodArchivo()).append(delimiter);

		if (padre != null && StringUtils.isNotEmpty(padre.getCodReferencia())) {
			codigoFondo.append(padre.getCodReferencia()).append(delimiter);
		}

		codigoFondo.append(fondoVO.getCodigo());
		return codigoFondo.toString();
	}

	public EntidadProductoraVO getEntidadProductoraXIdDescr(String id) {
		return _entidadProductoraDBEntity.getEntidadXIddescr(id);
	}

	// public boolean fondoTieneUDocs(String idFondo) {
	// return _unidadDocumentalDBEntity.countUdocsEnFondo(idFondo) > 0;
	// }

	// public boolean fondoTieneSeries(String idFondo) {
	// return _serieDBEntity.countSeriesEnFondo(idFondo) > 0;
	// }

	/**
	 * @param entidadVO
	 * @return
	 */
	protected boolean existEntidadConMismoNombre(EntidadProductoraVO entidadVO) {
		List entidadesConMismoNombre = _entidadProductoraDBEntity
				.getEntidadesXNombre(entidadVO.getNombre());
		for (Iterator itEntidadesConMismoNombre = entidadesConMismoNombre
				.iterator(); itEntidadesConMismoNombre.hasNext();) {
			EntidadProductoraVO entidadConMismoNombre = (EntidadProductoraVO) itEntidadesConMismoNombre
					.next();
			if (!entidadConMismoNombre.getId().equalsIgnoreCase(
					entidadVO.getId()))
				return true;
		}
		return false;
	}

	public void removeFondo(String idFondo)
			throws FondosOperacionNoPermitidaException {
		// obtengo los datos de nuevo en lugar de pasar el VO para asegurarme
		// algo mas de tener datos recientes
		FondoVO fondoVO = getFondoXId(idFondo);
		// Auditoria
		// LoggingEvent logEvent = AuditFondos.getLogginEventRemove(this,
		// fondoVO);
		checkPermission(FondosSecurityManager.ELIMINACION_ELEMENTO_ACTION);
		// comprobacion de que el borrado es posible
		getAuthorizationHelper().verificarPermitidoEliminar(fondoVO);
		ServiceRepository services = ServiceRepository
				.getInstance(getServiceSession());
		iniciarTransaccion();
		GestionCuadroClasificacionBI cuadroService = services
				.lookupGestionCuadroClasificacionBI();
		// borrado de elementos CF y sus datos descriptivos asociados, que
		// tengan como fondo al fondo (el fondo no se borra podria dar un
		// exception si se activa integridad)
		List elementos = _elementoCuadroClasificacionDBEntity
				.getElementosCFXIdFondo(idFondo);
		if (!CollectionUtils.isEmptyCollection(elementos)) {
			for (Iterator itElementos = elementos.iterator(); itElementos
					.hasNext();) {
				ElementoCuadroClasificacionVO element = (ElementoCuadroClasificacionVO) itElementos
						.next();
				if (!element.getId().equals(idFondo)) {
					// no es necesario comprobar si se estan borrando series
					// (en cuyo caso habria que llamar al borrado del servicio
					// de series)
					// ya que solo se puede borrar si no tiene series. SOLO SE
					// BORRARAN CLASIFICADORES
					cuadroService.removeElementoCF(element);
				}
			}
		}
		// borrado del fondo
		_fondoDBEntity.deleteFondo(idFondo);
		cuadroService.removeElementoCF(fondoVO);
		// borrado de la entidad
		eliminarEntidadProductora(fondoVO.getIdEntProductora(), idFondo);

		services.lookupGestionDescripcionBI().deleteFicha(fondoVO.getId(),
				TipoFicha.FICHA_ELEMENTO_CF);
		// Auditoria
		// ElementoCuadroClasificacionVO padre =
		// cuadroService.getElementoCuadroClasificacion(fondoVO.getIdPadre());
		// AuditFondos.addDataLogRemove(logEvent, fondoVO,
		// padre.getCodReferencia());
		commit();
	}

	private void eliminarEntidadProductora(String idEntidadProductora,
			String idFondo) {
		// Comprobar que no está siendo utilizada por otro fondo.
		List listaFondos = _fondoDBEntity
				.getFondosXEntidadProductoraExcludeFondo(idEntidadProductora,
						idFondo);

		if (ListUtils.isEmpty(listaFondos)) {
			_entidadProductoraDBEntity.deleteEntidad(idEntidadProductora);
		}
	}

	private void insertarEntidadProductora(EntidadProductoraVO entidadProductora) {
		EntidadProductoraVO entidadProductoraVO = _entidadProductoraDBEntity
				.getEntidadXIddescr(entidadProductora.getId());

		if (entidadProductoraVO == null) {
			_entidadProductoraDBEntity.insertEntidad(entidadProductora);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see common.bi.GestionFondosBI#moverFondo(fondos.vos.FondoVO,
	 *      java.lang.String)
	 */
	public void moverFondo(FondoVO fondo, String idNuevoPadre)
			throws FondosOperacionNoPermitidaException {
		// Auditoria
		Locale locale = getServiceClient().getLocale();
		LoggingEvent logEvent = AuditFondos.getLogginEventUpdate(this);
		DataLoggingEvent dataLogEvent = AuditFondos.addDataLogUpdateStep1(
				locale, logEvent, fondo);
		checkPermission(FondosSecurityManager.MODIFICAR_ELEMENTO_ACTION);
		ElementoCuadroClasificacionVO nuevoPadre = _elementoCuadroClasificacionDBEntity
				.getElementoCuadroClasificacion(idNuevoPadre);

		FondosAuthorizationHelper authorizationHelper = getAuthorizationHelper();
		authorizationHelper.verificarPermitidoMover(fondo, nuevoPadre);

		ConfiguracionFondos config = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionFondos();
		String delimiter = config.getDelimitadorCodigoReferencia();

		iniciarTransaccion();
		fondo.setIdPadre(idNuevoPadre);
		_elementoCuadroClasificacionDBEntity
				.updateElementoCuadroClasificacion(fondo);
		_elementoCuadroClasificacionDBEntity.updateCodRefElementoYHijos(
				fondo.getIdElementoCF(), delimiter, true);

		// Auditoria
		AuditFondos.addDataLogUpdateStep2(locale, dataLogEvent, fondo);
		commit();
	}

	/**
	 * Obtiene el fondo documental al que irá destinada la documentación
	 * transferida por un organo remitente
	 *
	 * @param idOrgano
	 *            Identificador de organo remitente
	 * @return Fondo del cuadro de clasificación de fondos documentales
	 */
	public FondoVO getFondoXOrganoRemitente(String idOrgano, String idArchivo,
			String codArchivo) {
		EntidadProductoraVO entidadProductora = _entidadProductoraDBEntity
				.getEntidadXOrgano(idOrgano);
		FondoVO fondo = null;
		if (entidadProductora != null) {
			// Obtener el archivo
			List fondosEntidadProductora = _fondoDBEntity
					.getFondosXEntidadProductora(entidadProductora.getId(),
							codArchivo);
			if (fondosEntidadProductora != null
					&& fondosEntidadProductora.size() > 0)
				// En realidad una entidad no debe ser productora mas que de un
				// unico fondo
				fondo = (FondoVO) fondosEntidadProductora.get(0);
			else
				logger.info("Ningun fondo definido para la entidad productora "
						+ entidadProductora.getNombre());
		}
		return fondo;
	}

	protected String getIdSistOrg() {
		return ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo()
				.getConfiguracionControlAcceso()
				.findUsuarioByTipo(getServiceClient().getUserType())
				.getIdSistGestorOrg();
	}

	// public List getInstitucionesProductorasParaFondos() throws
	// NotAvailableException,
	// GestorOrganismosException {
	// ConfiguracionSistemaArchivo csa =
	// ConfiguracionSistemaArchivoFactory.getConfiguracionSistemaArchivo();
	// GestorOrganismos connector =
	// GestorOrganismosFactory.getConnectorById(csa.getConfiguracionSGF().getIdSistGestorOrg());
	// return connector.recuperarInstitucionesProductoras();
	// }

	/**
	 * Obtener el fondo de una valoración de serie.
	 *
	 * @param idValoracion
	 *            Identificador de la valoración de serie.
	 * @return Fondo.
	 */
	public FondoVO getFondoXIdValoracion(String idValoracion) {
		return _fondoDBEntity.getFondoXIdValoracion(idValoracion);
	}

	/**
	 * Obtiene las fechas extremas de un fondo.
	 *
	 * @param idFondo
	 *            Identificador del fondo.
	 * @return Fechas extremas.
	 */
	public CustomDateRange getFechasExtremas(String idFondo) {
		return _fondoDBEntity.getFechasExtremas(idFondo);
	}

	public void setEstadoVigencia(FondoVO fondo, boolean vigente)
			throws ActionNotAllowedException {
		// Auditoria
		Locale locale = getServiceClient().getLocale();
		LoggingEvent logEvent = AuditFondos.getLogginEventUpdate(this);

		// comprobacion de seguridad
		checkPermission(FondosSecurityManager.MODIFICAR_ELEMENTO_ACTION);

		int proximoEstado = vigente ? IElementoCuadroClasificacion.VIGENTE
				: IElementoCuadroClasificacion.NO_VIGENTE;

		// Auditoria
		AuditFondos.addDataLogUpdateState(locale, logEvent, fondo,
				fondo.getEstado(), proximoEstado);
		ServiceRepository services = ServiceRepository
				.getInstance(getServiceSession());
		GestionDescripcionBI descripcionBI = services
				.lookupGestionDescripcionBI();

		if (!vigente) {
			List hijosVigentes = _elementoCuadroClasificacionDBEntity
					.getElementosCuadroClasificacion(fondo.getId(),
							new int[] { IElementoCuadroClasificacion.VIGENTE });
			if (hijosVigentes != null && hijosVigentes.size() > 0)
				throw new FondosOperacionNoPermitidaException(
						FondosOperacionNoPermitidaException.XNO_SE_PUEDE_CAMBIAR_ESTADO_X_TIENE_HIJOS);
		} else {
			if (fondo.getIdPadre() != null
					&& _elementoCuadroClasificacionDBEntity
							.getElementoCuadroClasificacion(fondo.getIdPadre())
							.isNoVigente())
				throw new FondosOperacionNoPermitidaException(
						FondosOperacionNoPermitidaException.XNO_SE_PUEDE_CAMBIAR_ESTADO_X_PADRE_TIENE_ESTADO_NO_VIGENTE);
			DefFicha definicionFicha = DefFichaFactory.getInstance(
					getServiceClient())
					.getDefFichaById(fondo.getIdFichaDescr());
			List camposObligatorios = definicionFicha.getDatosObligatorios();
			if (camposObligatorios != null) {
				for (Iterator i = camposObligatorios.iterator(); i.hasNext();) {
					DefCampoDato datoFicha = (DefCampoDato) i.next();
					List valoresDato = descripcionBI.getValues(
							TipoFicha.FICHA_ELEMENTO_CF,
							datoFicha.getTipoDato(), fondo.getId(),
							datoFicha.getId());
					if (valoresDato == null || valoresDato.size() == 0)
						throw new FondosOperacionNoPermitidaException(
								FondosOperacionNoPermitidaException.CAMPOS_OBLIGATORIOS_DESCRIPCION_SIN_VALOR);
				}
			}
		}

		iniciarTransaccion();

		_elementoCuadroClasificacionDBEntity.updateEstadoElementoCF(
				fondo.getId(), proximoEstado);
		Map paramActualizacionFicha = new HashMap();
		paramActualizacionFicha.put(ADReglaGenDatosContants.ACCIONES,
				new int[] { ADReglaGenDatosContants.ACTUALIZAR_PRODUCTORES });
		descripcionBI.generarAutomaticos(fondo.getId(),
				TipoFicha.FICHA_ELEMENTO_CF, paramActualizacionFicha);
		commit();
		fondo.setEstado(proximoEstado);

	}

	private FondoVO updateFondo(FondoVO fondo,
			EntidadProductoraVO entidadProductora)
			throws FondosOperacionNoPermitidaException {
		Locale locale = getServiceClient().getLocale();
		LoggingEvent logEvent = AuditFondos.getLogginEventUpdate(this);
		FondoVO currentInfo = _fondoDBEntity.getFondoXId(fondo.getId());
		DataLoggingEvent data = AuditFondos.addDataLogUpdateStep1(locale,
				logEvent, currentInfo);
		FondosAuthorizationHelper authorizationHelper = getAuthorizationHelper();
		if (!StringUtils.equals(fondo.getCodigo(), currentInfo.getCodigo())) {
			authorizationHelper.verificarPermitidoEstablecerCodigo(currentInfo,
					fondo.getCodigo());
			ElementoCuadroClasificacionVO clasificadorFondos = _elementoCuadroClasificacionDBEntity
					.getElementoCuadroClasificacion(currentInfo.getIdPadre());
			currentInfo.setCodigo(fondo.getCodigo());
			String codigoReferencia = generarCodigoReferenciaFondo(currentInfo,
					clasificadorFondos);
			currentInfo.setCodReferencia(codigoReferencia);
			currentInfo.setCodRefFondo(codigoReferencia);

			// Modificación de código de la entidad productora
			_entidadProductoraDBEntity
					.updateEntidadProductora(entidadProductora);
		}

		if (!StringUtils.equals(fondo.getCodArchivo(),
				currentInfo.getCodArchivo())
				|| !StringUtils.equals(entidadProductora.getId(),
						currentInfo.getIdEntProductora())) {

			authorizationHelper.verificarPermitidoGuardarFondo(fondo,
					entidadProductora);

			if (!StringUtils.equals(entidadProductora.getId(),
					currentInfo.getIdEntProductora())) {
				authorizationHelper
						.verificarPermitidoCambiarEntidadProductora(fondo);
				eliminarEntidadProductora(currentInfo.getIdEntProductora(),
						fondo.getId());
				insertarEntidadProductora(entidadProductora);
				currentInfo.setIdEntProductora(entidadProductora.getId());
			}
		}

		currentInfo.setDenominacion(fondo.getDenominacion());
		currentInfo.setIdArchivo(fondo.getIdArchivo());
		currentInfo.setCodArchivo(fondo.getCodArchivo());
		currentInfo.setOrdPos(fondo.getOrdPos());
		_fondoDBEntity.updateFondo(currentInfo);
		_elementoCuadroClasificacionDBEntity
				.updateElementoCuadroClasificacion(currentInfo);
		AuditFondos.addDataLogUpdateStep2(locale, data, currentInfo);
		return currentInfo;
	}

	private FondoVO crearFondo(FondoVO fondo,
			EntidadProductoraVO entidadProductora)
			throws FondosOperacionNoPermitidaException {
		try {
			LoggingEvent logEvent = AuditFondos.getLogginEventAlta(this);
			checkPermission(FondosSecurityManager.ALTA_ELEMENTO_ACTION);
			getAuthorizationHelper().verificarPermitidoGuardarFondo(fondo,
					entidadProductora);

			// Comprobar que la entidad no existe

			insertarEntidadProductora(entidadProductora);

			fondo.setTipo(ElementoCuadroClasificacion.TIPO_FONDO);
			fondo.setIdEntProductora(entidadProductora.getId());

			ElementoCuadroClasificacionVO clasificadorFondo = _elementoCuadroClasificacionDBEntity
					.getElementoCuadroClasificacion(fondo.getIdPadre());

			ElementoCuadroClasificacion elementoCF = (ElementoCuadroClasificacion) fondo;
			elementoCF.setEstado(IElementoCuadroClasificacion.NO_VIGENTE);
			elementoCF.setTienedescr(Constants.FALSE_STRING);
			INivelCFVO nivelCFVO = _nivelCFDbEntity.getNivelCF(fondo
					.getIdNivel());
			elementoCF.setIdFichaDescr(nivelCFVO.getIdFichaDescrPref());
			// elementoCF.setIdArchivo(clasificadorFondo.getIdArchivo());
			elementoCF.setIdArchivo(fondo.getIdArchivo());
			elementoCF.setNivelAcceso(NivelAcceso.PUBLICO);
			elementoCF.setIdLCA(null);
			elementoCF.setFinalCodRefPadre(null);
			elementoCF.setEditClfDocs(Constants.FALSE_STRING);
			elementoCF.setIdRepEcm(nivelCFVO.getIdRepEcmPref());
			String codigoReferencia = generarCodigoReferenciaFondo(fondo,
					clasificadorFondo);
			elementoCF.setCodReferencia(codigoReferencia);
			elementoCF.setCodRefFondo(codigoReferencia);

			// Si no está activada la distinción de mayúsculas/minúsculas, no
			// hacer el toUpper
			if (!ConfigConstants.getInstance()
					.getDistinguirMayusculasMinusculas())
				fondo.setTitulo(fondo.getTitulo().toUpperCase());

			_elementoCuadroClasificacionDBEntity.insertElementoCF(fondo);
			fondo.setIdElementoCF(fondo.getId());
			int tipoFondo = FondoVO.PRIVADO;
			if (clasificadorFondo != null && StringUtils.isNotEmpty(clasificadorFondo.getCodigo())
					&& clasificadorFondo.getCodigo().startsWith("1")){
				tipoFondo = FondoVO.PUBLICO;
			}

			fondo.setTipofondo(tipoFondo);
			FondoVO nuevoFondo = (FondoVO) _fondoDBEntity.insertFondo(fondo);
			INivelCFVO nivelNuevoFondo = _nivelCFDbEntity.getNivelCF(nuevoFondo
					.getIdNivel());
			ServiceRepository services = ServiceRepository
					.getInstance(getServiceSession());
			GestionDescripcionBI descripcionBI = services
					.lookupGestionDescripcionBI();
			descripcionBI.createFicha(nuevoFondo.getId(),
					TipoFicha.FICHA_ELEMENTO_CF);
			Locale locale = getServiceClient().getLocale();
			String codReferenciaPadre = Constants.STRING_EMPTY;

			if(clasificadorFondo != null){
				codReferenciaPadre = clasificadorFondo.getCodReferencia();
			}

			AuditFondos.addDataLogAlta(locale, logEvent, fondo,
					nivelNuevoFondo.getNombre(),
					codReferenciaPadre);
			return nuevoFondo;
		} catch (FondosOperacionNoPermitidaException ex) {
			throw new FondosOperacionNoPermitidaException(ex.getCodError());
		}
	}

	/**
	 * Incorpora al sistema un fondo cuya entidad productora asociada se obtiene
	 * a partir del descriptor suministrado
	 *
	 * @param fondo
	 *            Datos del fondo a crear
	 * @param tipoEntidad
	 *            Tipo de entidad productora del fondo
	 * @param idDescriptorEntidadProductora
	 *            Identificador del descriptor de la entidad productora del
	 *            fondo
	 * @return Datos del fondo creado
	 */
	public FondoVO guardarFondo(FondoVO fondo, int tipoEntidad,
			String idDescriptorEntidadProductora)
			throws ActionNotAllowedException {
		iniciarTransaccion();
		EntidadProductoraVO entidadProductora = new EntidadProductoraVO();

		entidadProductora.setTipoentidad(tipoEntidad);
		DescriptorVO descriptorEntidadProductora = _descriptorDbEntity
				.getDescriptor(idDescriptorEntidadProductora);

		try {
			BeanUtils.copyProperties(entidadProductora,
					descriptorEntidadProductora);
			entidadProductora.setCodigo(fondo.getCodigo());
		} catch (IllegalAccessException iae) {
			throw new ArchivoModelException(iae, "crearFondo()",
					"Error creando entidad productora");
		} catch (InvocationTargetException ite) {
			throw new ArchivoModelException(ite, "crearFondo()",
					"Error creando entidad productora");
		}
		FondoVO returnValue = fondo;
		if (StringUtils.isNotBlank(fondo.getId()))
			returnValue = updateFondo(fondo, entidadProductora);
		else
			returnValue = crearFondo(fondo, entidadProductora);
		commit();
		return returnValue;
	}

	/**
	 * Incorpora al sistema un fondo cuya entidad productora asociada es una
	 * institución gestionada por el sistema gestor de organización
	 *
	 * @param fondo
	 *            Datos del fondo a crear
	 * @param idInstitucionEnSistemaExterno
	 *            Identificador en el sistema gestor de organización de la
	 *            institución que se debe asociar como entidad productora del
	 *            fondo
	 * @return Datos del fondo creado
	 * @throws GestorOrganismosException
	 * @throws NotAvailableException
	 */
	public FondoVO guardarFondo(FondoVO fondo,
			String idInstitucionEnSistemaExterno)
			throws ActionNotAllowedException, GestorOrganismosException,
			NotAvailableException {
		ConfiguracionSistemaArchivo config = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo();
		ConfiguracionFondos configuracionFondos = config
				.getConfiguracionFondos();
		int tipoEntidadInstitucion = TipoEntidad.INSTITUCION.getIdentificador();
		ListaProductores listaDescriptoresInstitucion = config
				.getConfiguracionGeneral().getListaDescriptoresEntidad(
						tipoEntidadInstitucion);
		iniciarTransaccion();
		DescriptorVO descriptorEntidadProductora = _descriptorDbEntity
				.getDescriptorXIdEnSistemaExterno(
						idInstitucionEnSistemaExterno,
						listaDescriptoresInstitucion.getId());
		// FondoVO nuevoFondo = null;
		if (descriptorEntidadProductora == null) {

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

			GestorOrganismos gestorOrganismos = GestorOrganismosFactory
					.getConnectorById(configuracionFondos.getIdSistGestorOrg(),
							params);
			InfoOrgano institucion = gestorOrganismos.recuperarOrgano(
					TipoAtributo.IDENTIFICADOR_ORGANO,
					idInstitucionEnSistemaExterno);
			descriptorEntidadProductora = new DescriptorVO();
			descriptorEntidadProductora.setNombre(institucion.getNombre());
			descriptorEntidadProductora.setIdLista(listaDescriptoresInstitucion
					.getId());
			descriptorEntidadProductora.setTipo(TipoDescriptor.ENTIDAD);
			descriptorEntidadProductora.setEstado(EstadoDescriptor.VALIDADO);
			descriptorEntidadProductora.setIdSistExt(configuracionFondos
					.getIdSistGestorOrg());
			descriptorEntidadProductora
					.setIdDescrSistExt(idInstitucionEnSistemaExterno);
			descriptorEntidadProductora.setTimestamp(DBUtils.getFechaActual());
			ListaDescrVO listaVO = _catalogoListaDescriptoresDBEntity
					.getListaDescriptora(listaDescriptoresInstitucion.getId());
			if (listaVO != null) {
				descriptorEntidadProductora.setIdFichaDescr(listaVO
						.getIdFichaDescrPref());
				descriptorEntidadProductora.setIdRepEcm(listaVO
						.getIdRepEcmPref());
			}
			descriptorEntidadProductora.setTieneDescr(Constants.FALSE_STRING);
			descriptorEntidadProductora.setNivelAcceso(NivelAcceso.PUBLICO);
			descriptorEntidadProductora.setEditClfDocs(Constants.FALSE_STRING);
			_descriptorDbEntity.insertDescriptorVO(descriptorEntidadProductora);

		}
		EntidadProductoraVO entidadProductora = new EntidadProductoraVO();
		entidadProductora.setTipoentidad(tipoEntidadInstitucion);

		try {
			BeanUtils.copyProperties(entidadProductora,
					descriptorEntidadProductora);
			entidadProductora.setCodigo(fondo.getCodigo());
		} catch (IllegalAccessException iae) {
			throw new ArchivoModelException(iae, "crearFondo()",
					"Error creando entidad productora");
		} catch (InvocationTargetException ite) {
			throw new ArchivoModelException(ite, "crearFondo()",
					"Error creando entidad productora");
		}
		FondoVO returnValue = null;
		if (StringUtils.isNotBlank(fondo.getId()))
			returnValue = updateFondo(fondo, entidadProductora);
		else
			returnValue = crearFondo(fondo, entidadProductora);
		commit();
		return returnValue;
	}

	/**
	 * Obtiene la entidad productra de un fondo documental
	 *
	 * @param idFondo
	 *            Identificador de fondo documental
	 * @return Datos de la entidad productora del fondo
	 */
	public EntidadProductoraVO getEntidadProductoraFondo(String idFondo) {
		FondoVO fondoVO = getFondoXId(idFondo);
		return getEntidadProductoraXIdDescr(fondoVO.getIdEntProductora());
	}

	/**
	 * Obtiene un fondo del cuadro de clasificación de fondos documentales
	 * poniendolo a disposición del usuario que invoca la acción
	 *
	 * @param idFondo
	 *            Identificador del fondo a obtener
	 * @return Datos de fondo
	 */
	public FondoVO abrirFondo(String idFondo) {
		FondoVO fondo = _fondoDBEntity.getFondoXId(idFondo);
		FondosAuthorizationHelper authorizationHelper = getAuthorizationHelper();
		try {
			authorizationHelper.verificarPermitidoEliminar(fondo);
			fondo.setPuedeSerEliminado(true);
		} catch (FondosOperacionNoPermitidaException onpe) {
			fondo.setPuedeSerEliminado(false);
		}
		try {
			authorizationHelper
					.verificarPermitidoCambiarEntidadProductora(fondo);
			fondo.setPermitidoModificarEntidadProductora(true);
		} catch (FondosOperacionNoPermitidaException onpe) {
			fondo.setPermitidoModificarEntidadProductora(false);
		}
		try {
			authorizationHelper.verificarPermitidoModificarCodigo(fondo);
			fondo.setPermitidoModificarCodigo(true);
		} catch (FondosOperacionNoPermitidaException onpe) {
			fondo.setPermitidoModificarCodigo(false);
		}
		try {
			authorizationHelper.verificarPermitidoModificarArchivo(fondo);
			fondo.setPermitidoModificarArchivo(true);
		} catch (FondosOperacionNoPermitidaException onpe) {
			fondo.setPermitidoModificarArchivo(false);
		}
		try {
			authorizationHelper.verificarPermitidoMover(fondo, null);
			fondo.setPuedeSerMovido(true);
		} catch (FondosOperacionNoPermitidaException onpe) {
			fondo.setPuedeSerMovido(false);
		}
		return fondo;
	}

	private FondosAuthorizationHelper getAuthorizationHelper() {
		return new FondosAuthorizationHelper();
	}

	private class FondosAuthorizationHelper {

		void verificarPermitidoGuardarFondo(FondoVO fondo,
				EntidadProductoraVO entidadProductoraVO)
				throws FondosOperacionNoPermitidaException {

			if (existeFondoConMismaEntidadProductoraYMismoArchivo(
					fondo.getId(), fondo.getCodArchivo(),
					entidadProductoraVO.getId())) {
				throw new FondosOperacionNoPermitidaException(
						FondosOperacionNoPermitidaException.XENTIDAD_PRODUCTORA_ASIGNADA);
			}
			if (existeFondoConMismoCodigo(fondo.getId(), fondo.getIdNivel(),
					fondo.getCodigo(), fondo.getIdPadre()))
				throw new FondosOperacionNoPermitidaException(
						FondosOperacionNoPermitidaException.XCODIGO_YA_EXISTE);
		}

		void verificarPermitidoCambiarEntidadProductora(FondoVO fondo)
				throws FondosOperacionNoPermitidaException {

			if (_serieDBEntity.countSeriesEnFondo(fondo.getId()) > 0)
				throw new FondosOperacionNoPermitidaException(
						FondosOperacionNoPermitidaException.NO_SE_PUEDE_CAMBIAR_ENTIDAD_PRODUCTORA_FONDO_CON_SERIES);
		}

		void verificarPermitidoEstablecerCodigo(FondoVO fondo, String codigo)
				throws FondosOperacionNoPermitidaException {
			verificarPermitidoModificarCodigo(fondo);
			if (existeFondoConMismoCodigo(fondo.getId(), fondo.getIdNivel(),
					codigo, fondo.getIdPadre()))
				throw new FondosOperacionNoPermitidaException(
						FondosOperacionNoPermitidaException.XCODIGO_YA_EXISTE);
		}

		void verificarPermitidoModificarCodigo(FondoVO fondo)
				throws FondosOperacionNoPermitidaException {
			if (_elementoCuadroClasificacionDBEntity.countNumChilds(fondo
					.getId()) > 0)
				throw new FondosOperacionNoPermitidaException(
						FondosOperacionNoPermitidaException.NO_SE_PUEDE_CAMBIAR_CODIGO_FONDO_NO_VACIO);
		}

		void verificarPermitidoModificarArchivo(FondoVO fondo)
				throws FondosOperacionNoPermitidaException {
			if (_elementoCuadroClasificacionDBEntity.countNumChilds(fondo
					.getId()) > 0)
				throw new FondosOperacionNoPermitidaException(
						FondosOperacionNoPermitidaException.NO_SE_PUEDE_CAMBIAR_CODIGO_FONDO_NO_VACIO);
		}

		void verificarPermitidoEliminar(FondoVO fondo)
				throws FondosOperacionNoPermitidaException {
			if (_serieDBEntity.countSeriesEnFondo(fondo.getId()) == 0) {
				int estados[] = new int[] { ElementoCuadroClasificacion.VIGENTE };
				List descendientes = _elementoCuadroClasificacionDBEntity
						.getElementosCuadroClasificacion(fondo.getId(), estados);

				if (descendientes != null && descendientes.size() > 0)
					throw new FondosOperacionNoPermitidaException(
							FondosOperacionNoPermitidaException.XFONDO_NO_BORRABLE_TIENE_CLASIFICADORES_SERIE_VIGENTES);
			} else
				throw new FondosOperacionNoPermitidaException(
						FondosOperacionNoPermitidaException.XFONDO_NO_BORRABLE_TIENE_SERIES_ASOCIADAS);
		}

		public void verificarPermitidoMover(FondoVO fondo,
				ElementoCuadroClasificacionVO clasificadorFondos)
				throws FondosOperacionNoPermitidaException {
			if (_unidadDocumentalDBEntity.countUdocsEnFondo(fondo.getId()) > 0)
				throw new FondosOperacionNoPermitidaException(
						FondosOperacionNoPermitidaException.XFONDO_NO_MOVIBLE_XTENER_UDOCS);
			if (clasificadorFondos != null) {
				if (existeFondoConMismoCodigo(fondo.getId(),
						fondo.getIdNivel(), fondo.getCodigo(),
						clasificadorFondos.getId()))
					throw new FondosOperacionNoPermitidaException(
							FondosOperacionNoPermitidaException.XCODIGO_ENTIDAD_YA_EXISTE);

			}
		}

		private boolean existeFondoConMismoCodigo(String idFondo,
				String idNivel, String codigo, String clasificadorFondos) {
			return _elementoCuadroClasificacionDBEntity
					.hasHermanosConMismoCodigo(idFondo, idNivel, codigo,
							clasificadorFondos);
		}

		private boolean existeFondoConMismaEntidadProductoraYMismoArchivo(
				String idFondo, String codArchivo, String idEntidadProductora) {
			FondoVO fondo = _fondoDBEntity
					.getFondosXCodArchivoYEntidadProductora(codArchivo,
							idEntidadProductora);
			if (fondo != null && !fondo.getId().equals(idFondo)) {
				return true;
			}
			return false;
		}
	}

	/**
	 * Obtiene el Repositorio ECM de las series de un fondo.
	 *
	 * @param idFondo
	 *            Identificador de un fondo.
	 * @return Repositorio ECM de las series de un fondo.
	 */
	public List getRepositoriosEcmSeriesFondo(String idFondo) {
		return _fondoDBEntity.getVolumenesSeriesFondo(idFondo);
	}

	/**
	 * Permite obtener los procedimientos para la búsqueda
	 *
	 * @param tipoProcedimiento
	 *            tipo de procedimiento
	 * @param titulo
	 *            título
	 * @return Lista de procedimientos
	 *
	 * @throws GestorCatalogoException
	 * @throws NotAvailableException
	 */
	public List findProcedimientosBusqueda(int tipoProcedimiento, String titulo)
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

		GestorCatalogo gestorCatalogo = GestorCatalogoFactory
				.getConnector(params);
		return gestorCatalogo.recuperarInfoBProcedimientos(tipoProcedimiento,
				titulo);
	}

	public List getFondosXCodArchivo(String codArchivo) {
		return _fondoDBEntity.getFondosXCodArchivo(codArchivo);
	}

	public void updateInfoAccesoElemento(String idElementoCF, int nivelAcceso,
			String idLCA) {
		_elementoCuadroClasificacionDBEntity.updateControlAcceso(idElementoCF,
				nivelAcceso, idLCA);
	}

	/**
	 * Permite bloquear las unidades documentales seleccionadas
	 *
	 * @param unidadDocumentalIDs
	 *            identificadores del cuadro de las unidades a bloquear
	 * @throws ActionNotAllowedException
	 */
	public void lockUnidadesDocumentales(String[] unidadDocumentalIDs)
			throws ActionNotAllowedException {

		checkPermission(AppPermissions.BLOQUEO_DESBLOQUEO_UNIDADES_DOCUMENTALES);

		// Locale locale = getServiceClient().getLocale();
		iniciarTransaccion();

		int nUdocs = unidadDocumentalIDs.length;
		StringBuffer udocsLocked = new StringBuffer();
		fondos.vos.UnidadDocumentalVO unidadDocumentalVO = null;

		for (int i = 0; i < nUdocs; i++) {
			unidadDocumentalVO = _unidadDocumentalDBEntity
					.getUnidadDocumental(unidadDocumentalIDs[i]);

			if (unidadDocumentalVO.getMarcasBloqueo() > 0) {
				throw new RelacionOperacionNoPermitidaException(
						RelacionOperacionNoPermitidaException.XNO_ES_POSIBLE_BLOQUEAR_UDOC_BLOQUEADAS);
			}

			// Bloquea la Unidad Documental
			int marcas = MarcaUtil
					.setBitActivoInMarca(
							MarcaUdocRelacionConstants.POSICION_BIT_MARCA_BLOQUEADA_TRANSFERENCIA_EA_EXPURGO,
							unidadDocumentalVO.getMarcasBloqueo());
			_unidadDocumentalDBEntity.updateMarcaBloqueo(
					unidadDocumentalVO.getIdElementocf(), marcas);

			udocsLocked.append(unidadDocumentalVO.getNumExp()).append(":")
					.append(unidadDocumentalVO.getTitulo()).append(" - ");
		}

		/*
		 * int action = relacionEntrega.getIsIngresoDirecto() ?
		 * ArchivoActions.FONDOS_MODULE_EDICION_INGRESO_DIRECTO :
		 * ArchivoActions.TRANSFERENCIAS_MODULE_EDICION_RELACION_ENTREGA;
		 * IAuditoriaTransferencias
		 * pistaAuditoria=AuditoriaTransferencias.crearPistaAuditoria
		 * (relacionEntrega, action, this);
		 * pistaAuditoria.addDetalleBasico(locale, relacionEntrega,
		 * TransferenciasConstants
		 * .TRANSFERENCIAS_TIPO_EDICION_BLOQUEO_UNIDAD_DOCUMENTAL);
		 * pistaAuditoria.auditaBloquearUnidadDocumental(locale,
		 * udocsLocked.toString());
		 */
		commit();
	}

	/**
	 * Permite desbloquear las unidades documentales seleccionadas
	 *
	 * @param unidadDocumentalIDs
	 *            identificadores del cuadro de las unidades a desbloquear
	 * @throws ActionNotAllowedException
	 */
	public void unlockUnidadesDocumentales(String[] unidadDocumentalIDs)
			throws ActionNotAllowedException {

		checkPermission(AppPermissions.BLOQUEO_DESBLOQUEO_UNIDADES_DOCUMENTALES);

		// Locale locale = getServiceClient().getLocale();

		iniciarTransaccion();

		int nUdocs = unidadDocumentalIDs.length;
		StringBuffer udocsUnlocked = new StringBuffer();
		fondos.vos.UnidadDocumentalVO unidadDocumentalVO = null;

		for (int i = 0; i < nUdocs; i++) {
			unidadDocumentalVO = _unidadDocumentalDBEntity
					.getUnidadDocumental(unidadDocumentalIDs[i]);

			if (unidadDocumentalVO.getMarcasBloqueo() == 0) {
				throw new RelacionOperacionNoPermitidaException(
						RelacionOperacionNoPermitidaException.XNO_ES_POSIBLE_DESBLOQUEAR_UDOC_DESBLOQUEADAS);
			}

			// Bloquea la Unidad Documental
			int marcas = MarcaUtil
					.setBitInactivoInMarca(
							MarcaUdocRelacionConstants.POSICION_BIT_MARCA_BLOQUEADA_TRANSFERENCIA_EA_EXPURGO,
							unidadDocumentalVO.getMarcasBloqueo());
			_unidadDocumentalDBEntity.updateMarcaBloqueo(
					unidadDocumentalVO.getId(), marcas);

			udocsUnlocked.append(unidadDocumentalVO.getNumExp()).append(":")
					.append(unidadDocumentalVO.getTitulo()).append(" - ");
		}

		/*
		 * int action = relacionEntrega.getIsIngresoDirecto() ?
		 * ArchivoActions.FONDOS_MODULE_EDICION_INGRESO_DIRECTO :
		 * ArchivoActions.TRANSFERENCIAS_MODULE_EDICION_RELACION_ENTREGA;
		 * IAuditoriaTransferencias
		 * pistaAuditoria=AuditoriaTransferencias.crearPistaAuditoria
		 * (relacionEntrega, action, this);
		 * pistaAuditoria.addDetalleBasico(locale, relacionEntrega,
		 * TransferenciasConstants
		 * .TRANSFERENCIAS_TIPO_EDICION_BLOQUEO_UNIDAD_DOCUMENTAL);
		 * pistaAuditoria.auditaDesbloquearUnidadDocumental(locale,
		 * udocsUnlocked.toString());
		 */
		commit();
	}

	/**
	 * {@inheritDoc}
	 * @see common.bi.GestionFondosBI#getFondoXCodigo(java.lang.String)
	 */
	public FondoVO getFondoXCodigo(String codigoFondo) {
		ElementoCuadroClasificacionVO elemento = _elementoCuadroClasificacionDBEntity.getElementoCFXCodigoYTipo(codigoFondo, String.valueOf(ElementoCuadroClasificacion.TIPO_FONDO));

		if(elemento != null){
			return _fondoDBEntity.getFondoXId(elemento.getId());
		}
		return null;
	}

}