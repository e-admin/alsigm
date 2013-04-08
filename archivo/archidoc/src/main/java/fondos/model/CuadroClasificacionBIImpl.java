package fondos.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;

import se.usuarios.AppPermissions;
import se.usuarios.ServiceClient;
import util.CollectionUtils;
import xml.config.Busqueda;
import xml.config.ConfiguracionFondos;
import xml.config.ConfiguracionSistemaArchivoFactory;
import auditoria.logger.DataLoggingEvent;
import auditoria.logger.LoggingEvent;

import common.ConfigConstants;
import common.Constants;
import common.Messages;
import common.bi.GestionCuadroClasificacionBI;
import common.bi.GestionDescripcionBI;
import common.bi.GestionSeriesBI;
import common.bi.ServiceBase;
import common.bi.ServiceRepository;
import common.db.DBUtils;
import common.definitions.ArchivoModules;
import common.exceptions.SecurityException;
import common.exceptions.TooManyResultsException;
import common.security.FondosSecurityManager;
import common.util.ArrayUtils;
import common.util.CustomDate;
import common.util.CustomDateRange;
import common.util.DateUtils;
import common.util.ListUtils;
import common.util.StringUtils;
import common.vos.ConsultaConnectBy;
import common.vos.TypeDescVO;

import deposito.db.IUDocEnUiDepositoDbEntity;
import deposito.db.IUInstalacionDepositoDBEntity;
import descripcion.DescripcionUtils;
import descripcion.FormaReemplazo;
import descripcion.db.IFechaDBEntity;
import descripcion.db.INumeroDBEntity;
import descripcion.db.IReferenciaDBEntity;
import descripcion.db.ITextoDBEntity;
import descripcion.model.AuditoriaDescripcion;
import descripcion.model.xml.card.TipoFicha;
import descripcion.model.xml.definition.DefCampoDato;
import descripcion.model.xml.definition.DefFicha;
import descripcion.model.xml.definition.DefFichaFactory;
import descripcion.model.xml.definition.DefTipos;
import descripcion.vos.CampoTextoVO;
import descripcion.vos.ProductorElementoCF;
import descripcion.vos.ValorCampoGenericoVOBase;
import es.archigest.framework.core.exceptions.ArchigestException;
import fondos.db.ICatalogoTablasTemporalesDBEntity;
import fondos.db.IElementoCuadroClasificacionDbEntity;
import fondos.db.IElementoCuadroClasificacionVistaDBEntity;
import fondos.db.INivelCFDBEntity;
import fondos.db.IProductorSerieDbEntity;
import fondos.db.ISerieDbEntity;
import fondos.db.ITablaTemporalDBEntity;
import fondos.db.IUnidadDocumentalDbEntity;
import fondos.db.UpdateCodRefsReturns;
import fondos.exceptions.FondosOperacionNoPermitidaException;
import fondos.vos.BusquedaElementosVO;
import fondos.vos.BusquedaUdocsVO;
import fondos.vos.CatalogoTablaTemporalVO;
import fondos.vos.ElementoCuadroClasificacionVO;
import fondos.vos.INivelCFVO;
import fondos.vos.InteresadoUdocVO;
import fondos.vos.JerarquiaNivelCFVO;
import fondos.vos.TablaTemporalFondosVO;
import gcontrol.db.IArchivoDbEntity;
import gcontrol.db.IUsuarioDBEntity;
import gcontrol.model.NivelAcceso;
import gcontrol.vos.ArchivoVO;

public class CuadroClasificacionBIImpl extends ServiceBase implements
		GestionCuadroClasificacionBI {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(CuadroClasificacionBIImpl.class);

	IElementoCuadroClasificacionDbEntity _elementoCuadroClasificacionDBEntity = null;
	INivelCFDBEntity _nivelDBEntity = null;
	ITextoDBEntity _textoCortoDbEntity = null;
	ITextoDBEntity _textoLargoDbEntity = null;
	INumeroDBEntity _numeroDbEntity = null;
	IFechaDBEntity _fechaDbEntity = null;
	IReferenciaDBEntity _referenciaDbEntity = null;
	IUsuarioDBEntity _usuariosDbEntity = null;
	IProductorSerieDbEntity _productorSerieDbEntity = null;
	IUnidadDocumentalDbEntity _unidadDocumentalDbEntity = null;
	IUDocEnUiDepositoDbEntity _udocEnUiDepositoDbEntity = null;
	ISerieDbEntity _serieDbEntity = null;
	IArchivoDbEntity _archivoDbEntity = null;
	IUInstalacionDepositoDBEntity _uinstalacionDepositoDbEntity = null;
	ICatalogoTablasTemporalesDBEntity _catalagoTablasTemporalesDBEntity = null;
	ITablaTemporalDBEntity _tablaTemporalDBEntity = null;
	IElementoCuadroClasificacionVistaDBEntity _elementoCuadroClasificacionVistaDBEntity = null;

	private static final String[] PERMISOS_VER_ELEMENTOS_NO_VIGENTES = new String[] {
			AppPermissions.SOLICITUD_ALTA_SERIE, AppPermissions.GESTOR_SERIE,
			AppPermissions.GESTION_SOLICITUDES_SERIE,
			AppPermissions.CREACION_CUADRO_CLASIFICACION,
			AppPermissions.MODIFICACION_CUADRO_CLASIFICACION,
			AppPermissions.ELIMINACION_CUADRO_CLASIFICACION };

	/**
	 * Constructor.
	 *
	 * @param _elementoCuadroClasificacionDBEntity
	 * @param _nivelDBEntity
	 * @param _textoCortoDbEntity
	 * @param _textoLargoDbEntity
	 * @param _numeroDbEntity
	 * @param _fechaDbEntity
	 * @param _referenciaDbEntity
	 */
	public CuadroClasificacionBIImpl(
			IElementoCuadroClasificacionDbEntity _elementoCuadroClasificacionDBEntity,
			INivelCFDBEntity _nivelDBEntity,
			ITextoDBEntity _textoCortoDbEntity,
			ITextoDBEntity _textoLargoDbEntity,
			INumeroDBEntity _numeroDbEntity, IFechaDBEntity _fechaDbEntity,
			IReferenciaDBEntity _referenciaDbEntity,
			IUsuarioDBEntity _usuariosDbEntity,
			IProductorSerieDbEntity productorSerieDbEntity,
			IUnidadDocumentalDbEntity unidadDocumentalDbEntity,
			IUDocEnUiDepositoDbEntity _udocEnUiDepositoDbEntity,
			ISerieDbEntity serieDbEntity, IArchivoDbEntity archivoDbEntity,
			IUInstalacionDepositoDBEntity uinstalacionDepositoDbEntity,
			ICatalogoTablasTemporalesDBEntity catalogoTablasTemporalesDBEntity,
			ITablaTemporalDBEntity tablaTemporalDBEntity) {

		super();

		this._elementoCuadroClasificacionDBEntity = _elementoCuadroClasificacionDBEntity;
		this._nivelDBEntity = _nivelDBEntity;
		this._textoCortoDbEntity = _textoCortoDbEntity;
		this._textoLargoDbEntity = _textoLargoDbEntity;
		this._numeroDbEntity = _numeroDbEntity;
		this._fechaDbEntity = _fechaDbEntity;
		this._referenciaDbEntity = _referenciaDbEntity;
		this._usuariosDbEntity = _usuariosDbEntity;
		this._productorSerieDbEntity = productorSerieDbEntity;
		this._unidadDocumentalDbEntity = unidadDocumentalDbEntity;
		this._udocEnUiDepositoDbEntity = _udocEnUiDepositoDbEntity;
		this._serieDbEntity = serieDbEntity;
		this._archivoDbEntity = archivoDbEntity;
		this._uinstalacionDepositoDbEntity = uinstalacionDepositoDbEntity;
		this._catalagoTablasTemporalesDBEntity = catalogoTablasTemporalesDBEntity;
		this._tablaTemporalDBEntity = tablaTemporalDBEntity;

	}

	public CuadroClasificacion getCuadroClasificacion() {
		GestionCuadroClasificacionBI wraperServiceCuadro = ServiceRepository
				.getInstance(getServiceSession())
				.lookupGestionCuadroClasificacionBI();
		return new CuadroClasificacion(wraperServiceCuadro);

	}

	public ElementoCuadroClasificacionVO getElementoCuadroClasificacion(
			String idElemento) {
		return _elementoCuadroClasificacionDBEntity
				.getElementoCuadroClasificacion(idElemento);
	}

	/**
	 * Obtiene la información del elementod el cuadro de clasificación.
	 *
	 * @param codReferencia
	 *            Código de referencia
	 * @return Un elemento del cuadro por el código de referencia.
	 */
	public ElementoCuadroClasificacionVO getElementoCuadroClasificacionXCodReferencia(
			String codReferencia) {
		return _elementoCuadroClasificacionDBEntity
				.getElementoCuadroClasificacionXCodReferencia(codReferencia);
	}

	public List getHijosElementoCuadroClasificacion(String idElementoCF) {
		return _elementoCuadroClasificacionDBEntity
				.getElementosCuadroClasificacionWithPermissions(
						getServiceClient(), idElementoCF,
						getEstadosVisiblesUsuario());

	}

	public List getHijosElementoCuadroClasificacionWithPermissions(
			String idElementoCF) {
		return _elementoCuadroClasificacionDBEntity
				.getElementosCuadroClasificacionWithPermissions(
						getServiceClient(), idElementoCF,
						getEstadosVisiblesUsuario());

	}

	// teniendo en cuenta permisos de la lista de acceso
	public List getFondosWithPermissions() {
		return _elementoCuadroClasificacionDBEntity.getFondosWithPermissions(
				getServiceClient(), getEstadosVisiblesUsuario());
	}

	public List getHijosElementoCuadroClasificacion(String idElementoCF,
			boolean mostrarTemporales) {
		int[] newEstados = null;

		// Obtener los estados visibles para el usuario
		int[] estados = getEstadosVisiblesUsuario();

		// Si el usuario no tiene estados, es decir, puede ver todos.
		if (estados == null) {
			// Si no se muestran los temporales
			if (!mostrarTemporales) {

				// Se establecen los estados NO VIGENTE, VIGENTE,
				// ESTADO_ELIMINADO
				newEstados = new int[] {
						IElementoCuadroClasificacion.NO_VIGENTE,
						IElementoCuadroClasificacion.VIGENTE,
						IElementoCuadroClasificacion.ESTADO_ELIMINADO };
			}
		} else {
			if (!mostrarTemporales) {
				// Comprobar que no los estados permitidos no esté el temporal
				// para quitarlo.

				int indiceEstadoTemporal = ArrayUtils.indexOf(estados,
						ElementoCuadroClasificacion.TEMPORAL);
				if (indiceEstadoTemporal != -1) {
					newEstados = ArrayUtils.removeElement(estados,
							ElementoCuadroClasificacion.TEMPORAL);
				} else {
					newEstados = estados;
				}
			}
		}

		return _elementoCuadroClasificacionDBEntity
				.getElementosCuadroClasificacionWithPermissions(
						getServiceClient(), idElementoCF, newEstados);
	}

	public List getHijosVigentesElementoCuadroClasificacion(String idElementoCF) {
		int[] estadoVigente = new int[] { IElementoCuadroClasificacion.VIGENTE };
		return _elementoCuadroClasificacionDBEntity
				.getElementosCuadroClasificacion(idElementoCF, estadoVigente);
	}

	public INivelCFVO getNivelCF(String idNivel) {
		return _nivelDBEntity.getNivelCF(idNivel);
	}

	public INivelCFVO getNivelCFById(String idNivel) {
		return _nivelDBEntity.getNivelCFById(idNivel);
	}

	public List getNombresNiveles(List idsElementos) {
		return _elementoCuadroClasificacionDBEntity
				.getNombresNiveles(idsElementos);
	}

	public HashMap getPairsIdCodigo(List idsElementos) {
		return _elementoCuadroClasificacionDBEntity
				.getPairsIdCodigo(idsElementos);
	}

	public HashMap getPairsIdCodRefPadre(List idsElementos) {
		return _elementoCuadroClasificacionDBEntity
				.getPairsIdCodRefPadre(idsElementos);
	}

	public List getNivelesPadre(String idNivel, int tipoNivel) {
		return _nivelDBEntity.getNivelesPadre(idNivel, tipoNivel);
	}

	public List getNivelesByTipo(TipoNivelCF tipoNivel, String nivelPadre) {
		return _nivelDBEntity.getNivelesCF(tipoNivel.getIdentificador(),
				nivelPadre);
	}

	public List getNivelesCFByTipo(int[] tiposHijo, List nivelesHijo) {
		return _nivelDBEntity.getNivelesCFByTipo(tiposHijo, nivelesHijo);
	}

	protected String appendDelimiter(String field, String delimiter) {
		return field != null && !StringUtils.isBlank(field) ? delimiter : "";
	}

	protected String appendField(String field) {

		return field != null && !StringUtils.isBlank(field) ? field : "";

	}

	public void checkCrearClasificadorSerie(
			ElementoCuadroClasificacionVO parent, String codigo)
			throws FondosOperacionNoPermitidaException {
		if (_elementoCuadroClasificacionDBEntity.getElementoCFXCodigo(codigo,
				parent.getId()) != null)
			throw new FondosOperacionNoPermitidaException(
					FondosOperacionNoPermitidaException.XCODIGO_YA_EXISTE);

	}

	public void checkCrearClasificadorFondo(
			ElementoCuadroClasificacionVO parent, String codigo)
			throws FondosOperacionNoPermitidaException {
		if (_elementoCuadroClasificacionDBEntity.getElementoCFXCodigo(codigo,
				parent.getId()) != null)
			throw new FondosOperacionNoPermitidaException(
					FondosOperacionNoPermitidaException.XCODIGO_YA_EXISTE);

	}

	public ElementoCuadroClasificacionVO crearClasificadorSeries(
			ElementoCuadroClasificacionVO parent, String tipoClasificador,
			String codigo, String denominacion, String codOrdenacion)
			throws FondosOperacionNoPermitidaException {
		// Auditoria
		LoggingEvent event = AuditFondos.getLogginEventAlta(this);

		checkPermission(FondosSecurityManager.ALTA_ELEMENTO_ACTION);

		if (StringUtils.isNotEmpty(codigo))
			checkCrearClasificadorSerie(parent, codigo);

		ElementoCuadroClasificacionVO clfSeries = new ElementoCuadroClasificacion();
		clfSeries.setCodigo(codigo);
		clfSeries.setTitulo(denominacion);
		clfSeries.setIdNivel(tipoClasificador);
		clfSeries.setOrdPos(codOrdenacion);
		clfSeries.setIdPadre(parent.getId());
		clfSeries.setIdFondo(parent.getIdFondo());
		clfSeries.setTipo(TipoNivelCF.CLASIFICADOR_SERIE.getIdentificador());
		clfSeries.setEstado(IElementoCuadroClasificacion.NO_VIGENTE);
		clfSeries.setCodRefFondo(parent.getCodRefFondo());

		INivelCFVO nivelParent = getNivelCF(parent.getIdNivel());

		String finalCodRefPadre = _elementoCuadroClasificacionDBEntity
				.composeFinalCodRefPadre(nivelParent,
						parent.getFinalCodRefPadre(), parent.getCodigo());

		clfSeries.setFinalCodRefPadre(finalCodRefPadre);

		String codReferencia = _elementoCuadroClasificacionDBEntity
				.composeCodigoReferencia(nivelParent,
						parent.getFinalCodRefPadre(), parent.getCodigo(),
						parent.getCodRefFondo(), clfSeries.getCodigo());

		clfSeries.setCodReferencia(codReferencia);

		INivelCFVO nivelClfSeries = _nivelDBEntity.getNivelCF(tipoClasificador);
		clfSeries.setIdFichaDescr(nivelClfSeries.getIdFichaDescrPref());
		clfSeries.setTienedescr(Constants.FALSE_STRING);
		clfSeries.setIdRepEcm(nivelClfSeries.getIdRepEcmPref());
		clfSeries.setEditClfDocs(Constants.FALSE_STRING);
		clfSeries.setNivelAcceso(NivelAcceso.PUBLICO);

		// Si no está activada la distinción de mayúsculas/minúsculas, no hacer
		// el toUpper
		if (!ConfigConstants.getInstance().getDistinguirMayusculasMinusculas())
			clfSeries.setTitulo(clfSeries.getTitulo().toUpperCase());

		iniciarTransaccion();

		clfSeries = _elementoCuadroClasificacionDBEntity
				.insertElementoCF(clfSeries);

		((IElementoCuadroClasificacion) parent)
				.addChild((IElementoCuadroClasificacion) clfSeries);

		if (clfSeries.getIdFichaDescr() != null) {
			getServiceRepository().lookupGestionDescripcionBI().createFicha(
					clfSeries.getId(), TipoFicha.FICHA_ELEMENTO_CF);
		}

		// Auditoria
		Locale locale = getServiceClient().getLocale();
		AuditFondos.addDataLogAlta(locale, event, clfSeries,
				nivelClfSeries.getNombre(), parent.getCodReferencia());

		commit();

		return clfSeries;
	}

	public ElementoCuadroClasificacionVO crearClasificadorFondos(
			ElementoCuadroClasificacionVO parent, String tipoClasificador,
			String codigo, String denominacion, String codOrdenacion)
			throws FondosOperacionNoPermitidaException {
		// Auditoria
		LoggingEvent event = AuditFondos.getLogginEventAlta(this);

		checkPermission(FondosSecurityManager.ALTA_ELEMENTO_ACTION);

		// Ahora los clasificadores de fondos y series pueden ser nulos
		// Por lo tanto, cuando es nulo no podemos hacer esa comprobacion ya que
		// si hay nulos en la B.D. fallara.
		if (StringUtils.isNotEmpty(codigo))
			checkCrearClasificadorFondo(parent, codigo);

		ElementoCuadroClasificacionVO clfFondos = new ElementoCuadroClasificacion();
		clfFondos.setCodigo(codigo);
		clfFondos.setTitulo(denominacion);
		clfFondos.setIdNivel(tipoClasificador);
		clfFondos.setIdPadre(parent.getId());
		clfFondos.setTipo(TipoNivelCF.CLASIFICADOR_FONDOS.getIdentificador());
		clfFondos.setCodRefFondo(parent.getCodRefFondo());
		clfFondos.setOrdPos(codOrdenacion);

		// El clasificador de fondo siempre se crea como vigente, ya que no
		// tiene ficha asociada.
		clfFondos.setEstado(IElementoCuadroClasificacion.VIGENTE);

		if (parent.getIdNivel() != null) {
			INivelCFVO nivelParent = getNivelCF(parent.getIdNivel());
			clfFondos.setFinalCodRefPadre(_elementoCuadroClasificacionDBEntity
					.composeFinalCodRefPadre(nivelParent,
							parent.getFinalCodRefPadre(), parent.getCodigo()));

			clfFondos.setCodReferencia(_elementoCuadroClasificacionDBEntity
					.composeCodigoReferencia(nivelParent,
							parent.getFinalCodRefPadre(), parent.getCodigo(),
							parent.getCodRefFondo(), clfFondos.getCodigo()));
		} else {
			clfFondos.setCodReferencia(clfFondos.getCodigo());
		}

		INivelCFVO nivelClfFondos = _nivelDBEntity.getNivelCF(tipoClasificador);
		clfFondos.setIdFichaDescr(nivelClfFondos.getIdFichaDescrPref());
		clfFondos.setTienedescr(Constants.FALSE_STRING);
		clfFondos.setIdRepEcm(nivelClfFondos.getIdRepEcmPref());
		clfFondos.setEditClfDocs(Constants.FALSE_STRING);
		clfFondos.setNivelAcceso(NivelAcceso.PUBLICO);

		// Si no está activada la distinción de mayúsculas/minúsculas, no hacer
		// el toUpper
		if (!ConfigConstants.getInstance().getDistinguirMayusculasMinusculas())
			clfFondos.setTitulo(clfFondos.getTitulo().toUpperCase());

		iniciarTransaccion();

		clfFondos = _elementoCuadroClasificacionDBEntity
				.insertElementoCF(clfFondos);

		if (parent.getId() != null)
			((IElementoCuadroClasificacion) parent)
					.addChild((IElementoCuadroClasificacion) clfFondos);

		if (clfFondos.getIdFichaDescr() != null) {
			getServiceRepository().lookupGestionDescripcionBI().createFicha(
					clfFondos.getId(), TipoFicha.FICHA_ELEMENTO_CF);
		}

		// Auditoria
		Locale locale = getServiceClient().getLocale();
		AuditFondos.addDataLogAlta(locale, event, clfFondos,
				nivelClfFondos.getNombre(), parent.getCodReferencia());

		commit();

		return clfFondos;
	}

	public boolean existHermanosConMismoCodigo(
			ElementoCuadroClasificacionVO elementoCF, String codigo) {
		ElementoCuadroClasificacionVO elementoConMismoCodigo = _elementoCuadroClasificacionDBEntity
				.getElementoCFXCodigo(codigo, elementoCF.getIdPadre());
		boolean codigoDuplicado = false;
		if (elementoConMismoCodigo != null
				&& !elementoConMismoCodigo.getId().equals(elementoCF.getId()))
			codigoDuplicado = true;

		return codigoDuplicado;

	}

	private void checkUpdateClasificadorSeries(
			ElementoCuadroClasificacionVO elementoCF, String tipoClasificador,
			String codigo) throws FondosOperacionNoPermitidaException {
		if (!tipoClasificador.equals(elementoCF.getIdNivel())
				&& _elementoCuadroClasificacionDBEntity
						.countNumChilds(elementoCF.getId()) > 0)
			throw new FondosOperacionNoPermitidaException(
					FondosOperacionNoPermitidaException.XTIPO_CLASIFICADOR_NO_PUEDE_SER_CAMBIADO);
		// compobacion para la actuailzacion
		if (existHermanosConMismoCodigo(elementoCF, codigo))
			throw new FondosOperacionNoPermitidaException(
					FondosOperacionNoPermitidaException.XCODIGO_YA_EXISTE);
	}

	private void checkUpdateClasificadorFondos(
			ElementoCuadroClasificacionVO elementoCF, String tipoClasificador,
			String codigo) throws FondosOperacionNoPermitidaException {
		if (!tipoClasificador.equals(elementoCF.getIdNivel())
				&& _elementoCuadroClasificacionDBEntity
						.countNumChilds(elementoCF.getId()) > 0)
			throw new FondosOperacionNoPermitidaException(
					FondosOperacionNoPermitidaException.XTIPO_CLASIFICADOR_NO_PUEDE_SER_CAMBIADO);
		// compobacion para la actuailzacion
		if (existHermanosConMismoCodigo(elementoCF, codigo))
			throw new FondosOperacionNoPermitidaException(
					FondosOperacionNoPermitidaException.XCODIGO_YA_EXISTE);
	}

	/**
	 * Actualizacion de un clasificador de serie
	 *
	 * @param elementoCF
	 *            Clasificador de series que se desea actualizar
	 * @param tipoClasificador
	 *            Tipo de clasificador
	 * @param codigo
	 *            Codigo para el clasificador
	 * @param denominacion
	 *            Denominación para el clasificador
	 * @throws FondosOperacionNoPermitidaException
	 *             Caso de que la actualización del clasificador no esté
	 *             permitida
	 */
	public void updateClasificadorSeries(
			ElementoCuadroClasificacionVO elementoCF, String tipoClasificador,
			String codigo, String denominacion, String codigoOld)
			throws FondosOperacionNoPermitidaException {

		// Auditoria
		LoggingEvent logEvent = AuditFondos.getLogginEventUpdate(this);

		// chequeo de seguridad
		checkPermission(FondosSecurityManager.MODIFICAR_ELEMENTO_ACTION);

		// compobacion para la actuailzacion
		if (common.util.StringUtils.isNotBlank(codigo))
			checkUpdateClasificadorSeries(elementoCF, tipoClasificador, codigo);

		// Si no está activada la distinción de mayúsculas/minúsculas, no hacer
		// el toUpper
		if (StringUtils.isNotEmpty(denominacion)
				&& !ConfigConstants.getInstance()
						.getDistinguirMayusculasMinusculas())
			denominacion = denominacion.toUpperCase();

		Locale locale = getServiceClient().getLocale();
		iniciarTransaccion();

		DataLoggingEvent dataLogginEvent = AuditFondos.addDataLogUpdateStep1(
				locale, logEvent, elementoCF);

		// esto no habria que hacerlo si se produjo una excepcion

		_elementoCuadroClasificacionDBEntity.updateElementoCF(
				elementoCF.getId(), tipoClasificador, codigo, denominacion,
				elementoCF.getOrdPos());

		elementoCF.setIdNivel(tipoClasificador);
		elementoCF.setCodigo(codigo);
		elementoCF.setTitulo(denominacion);

		// actualizar codigos de referencias, solamente cuando sea necesario
		if (!codigoOld.equals(codigo))
			updateCodRefs(elementoCF);

		// Auditoria
		AuditFondos.addDataLogUpdateStep2(locale, dataLogginEvent, elementoCF);

		commit();
	}

	/**
	 * Actualizacion de un clasificador de serie
	 *
	 * @param elementoCF
	 *            Clasificador de series que se desea actualizar
	 * @param tipoClasificador
	 *            Tipo de clasificador
	 * @param codigo
	 *            Codigo para el clasificador
	 * @param denominacion
	 *            Denominación para el clasificador
	 * @throws FondosOperacionNoPermitidaException
	 *             Caso de que la actualización del clasificador no esté
	 *             permitida
	 */
	public void updateClasificadorFondos(
			ElementoCuadroClasificacionVO elementoCF, String tipoClasificador,
			String codigo, String denominacion, String codigoOld)
			throws FondosOperacionNoPermitidaException {

		// Auditoria
		LoggingEvent logEvent = AuditFondos.getLogginEventUpdate(this);

		// chequeo de seguridad
		checkPermission(FondosSecurityManager.MODIFICAR_ELEMENTO_ACTION);

		// compobacion para la actuailzacion
		if (common.util.StringUtils.isNotBlank(codigo))
			checkUpdateClasificadorFondos(elementoCF, tipoClasificador, codigo);

		// Si no está activada la distinción de mayúsculas/minúsculas, no hacer
		// el toUpper
		if (StringUtils.isNotEmpty(denominacion)
				&& !ConfigConstants.getInstance()
						.getDistinguirMayusculasMinusculas())
			denominacion = denominacion.toUpperCase();

		Locale locale = getServiceClient().getLocale();
		iniciarTransaccion();

		DataLoggingEvent dataLogginEvent = AuditFondos.addDataLogUpdateStep1(
				locale, logEvent, elementoCF);

		// esto no habria que hacerlo si se produjo una excepcion
		_elementoCuadroClasificacionDBEntity.updateElementoCF(
				elementoCF.getId(), tipoClasificador, codigo, denominacion,
				elementoCF.getOrdPos());

		elementoCF.setIdNivel(tipoClasificador);
		elementoCF.setCodigo(codigo);
		elementoCF.setTitulo(denominacion);

		// actualizar codigos de referencias, solamente cuando sea necesario
		if (!codigoOld.equals(codigo))
			updateCodRefs(elementoCF);

		// Auditoria
		AuditFondos.addDataLogUpdateStep2(locale, dataLogginEvent, elementoCF);

		commit();

	}

	public void updateCodRefs(ElementoCuadroClasificacionVO elemento) {

		Locale locale = getServiceClient().getLocale();
		ConfiguracionFondos config = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionFondos();
		String delimiter = config.getDelimitadorCodigoReferencia();

		iniciarTransaccion();

		UpdateCodRefsReturns retUpdating = _elementoCuadroClasificacionDBEntity
				.updateCodRefElementoYHijos(elemento.getId(), delimiter, false);

		// Auditoria
		// audita solo si hay algun cambio -> evitar repeticion de pista de
		// auditoria por el mismo motivo. Ej modificacion CS
		if (elemento.getCodReferencia() != null
				&& !elemento.getCodReferencia().equals(
						retUpdating.getCodReferencia())) {
			// Auditoria (solo se audita el cambio en el padre, sino en
			// movimiento de cosas
			// y modi de fondo se generarian muchos detalles de auditoria)
			LoggingEvent logEvent = AuditFondos.getLogginEventUpdate(this);

			DataLoggingEvent dataLogginEvent = AuditFondos
					.addDataLogUpdateStep1(locale, logEvent, elemento);

			elemento.setCodReferencia(retUpdating.getCodReferencia());
			elemento.setFinalCodRefPadre(retUpdating.getFinalCodRefPadre());

			// Auditoria
			AuditFondos
					.addDataLogUpdateStep2(locale, dataLogginEvent, elemento);
		}

		commit();

	}

	private void checkRemoveElementoCF(ElementoCuadroClasificacionVO elementoCF)
			throws FondosOperacionNoPermitidaException {
		if (elementoCF.isTipoClasificadorSerie()) {
			if (_elementoCuadroClasificacionDBEntity.countNumChilds(elementoCF
					.getId()) > 0)
				throw new FondosOperacionNoPermitidaException(
						FondosOperacionNoPermitidaException.XCL_SERIE_NO_BORRABLE_TIENE_SERIES);

			if (util.CollectionUtils
					.collectionSize(_elementoCuadroClasificacionDBEntity.getElementosCuadroClasificacion(
							elementoCF.getId(),
							new int[] { IElementoCuadroClasificacion.VIGENTE })) > 0)
				throw new FondosOperacionNoPermitidaException(
						FondosOperacionNoPermitidaException.XCL_SERIE_NO_BORRABLE_TIENE_HIJOS_VIGENTES);
		} else if (elementoCF.isTipoClasificadorFondo()) {
			if (_elementoCuadroClasificacionDBEntity.countNumChilds(elementoCF
					.getId()) > 0)
				throw new FondosOperacionNoPermitidaException(
						FondosOperacionNoPermitidaException.XCL_FONDO_NO_BORRABLE_TIENE_HIJOS);
		}
	}

	private void checkCambioEstado(ElementoCuadroClasificacionVO elementoCF,
			boolean isChangeToVigente)
			throws FondosOperacionNoPermitidaException {

		if (!isChangeToVigente) {
			if (hasChildsByState(elementoCF,
					IElementoCuadroClasificacion.VIGENTE))
				throw new FondosOperacionNoPermitidaException(
						FondosOperacionNoPermitidaException.XNO_SE_PUEDE_CAMBIAR_ESTADO_X_TIENE_HIJOS);
		} else {
			if (elementoCF.getIdPadre() != null
					&& _elementoCuadroClasificacionDBEntity
							.getElementoCuadroClasificacion(
									elementoCF.getIdPadre()).isNoVigente())
				throw new FondosOperacionNoPermitidaException(
						FondosOperacionNoPermitidaException.XNO_SE_PUEDE_CAMBIAR_ESTADO_X_PADRE_TIENE_ESTADO_NO_VIGENTE);

			if (!StringUtils.isEmpty(elementoCF.getIdFichaDescr())) {
				ServiceRepository services = ServiceRepository
						.getInstance(getServiceSession());
				DefFicha definicionFicha = DefFichaFactory.getInstance(
						getServiceClient()).getDefFichaById(
						elementoCF.getIdFichaDescr());
				List camposObligatorios = definicionFicha
						.getDatosObligatorios();
				if (camposObligatorios != null) {
					GestionDescripcionBI descripcionBI = services
							.lookupGestionDescripcionBI();
					for (Iterator i = camposObligatorios.iterator(); i
							.hasNext();) {
						DefCampoDato datoFicha = (DefCampoDato) i.next();
						List valoresDato = descripcionBI.getValues(
								TipoFicha.FICHA_ELEMENTO_CF,
								datoFicha.getTipoDato(), elementoCF.getId(),
								datoFicha.getId());
						if (valoresDato == null || valoresDato.size() == 0)
							throw new FondosOperacionNoPermitidaException(
									FondosOperacionNoPermitidaException.CAMPOS_OBLIGATORIOS_DESCRIPCION_SIN_VALOR);
					}
				}
			}
		}

	}

	public void setEstadoVigencia(ElementoCuadroClasificacionVO elementoCF,
			boolean vigente) throws FondosOperacionNoPermitidaException {

		// Auditoria
		LoggingEvent logEvent = AuditFondos.getLogginEventUpdate(this);

		// comprobacion de seguridad
		checkPermission(FondosSecurityManager.MODIFICAR_ELEMENTO_ACTION);

		int proximoEstado = vigente ? IElementoCuadroClasificacion.VIGENTE
				: IElementoCuadroClasificacion.NO_VIGENTE;

		// Auditoria
		Locale locale = getServiceClient().getLocale();
		AuditFondos.addDataLogUpdateState(locale, logEvent, elementoCF,
				elementoCF.getEstado(), proximoEstado);

		checkCambioEstado(elementoCF, vigente);

		iniciarTransaccion();

		_elementoCuadroClasificacionDBEntity.updateEstadoElementoCF(
				elementoCF.getId(), proximoEstado);

		elementoCF.setEstado(proximoEstado);

		commit();

	}

	public void setTieneDescr(String idElementoCF, boolean tieneDescr) {

		iniciarTransaccion();

		String strTieneDescr = DBUtils.getStringValue(tieneDescr);

		_elementoCuadroClasificacionDBEntity.updateTieneDescrElementoCF(
				idElementoCF, strTieneDescr);

		commit();
	}

	public void setEditClfDocs(String idElementoCF, boolean editClfDocs) {

		iniciarTransaccion();

		String strEditClfDocs = DBUtils.getStringValue(editClfDocs);

		_elementoCuadroClasificacionDBEntity.updateEditClfDocsElementoCF(
				idElementoCF, strEditClfDocs);

		commit();
	}

	private boolean hasChildsByState(ElementoCuadroClasificacionVO elementoCF,
			int estado) {
		List hijosVigentes = _elementoCuadroClasificacionDBEntity
				.getElementosCuadroClasificacion(elementoCF.getId(),
						new int[] { estado });
		if (hijosVigentes != null)
			return (hijosVigentes.size() > 0);
		return false;
	}

	/**
	 * Obtiene la lista de niveles de descripción (excepto los clasificadores de
	 * fondos).
	 *
	 * @return Lista de niveles de descripción.
	 */
	public List getNivelesCF() {
		return _nivelDBEntity.getNivelesCF();
	}

	/**
	 * Obtiene la lista de niveles de descripción (incluidos los clasificadores
	 * de fondos).
	 *
	 * @return Lista de niveles de descripción.
	 */
	public List getAllNivelesCF() {
		return _nivelDBEntity.getAllNivelesCF();
	}

	/**
	 * Obtiene la lista de niveles de descripción.
	 *
	 * @param nivelPadre
	 *            Nivel del padre.
	 * @return Lista de niveles de descripción.
	 */
	public List getNivelesCF(String idNivelPadre) {
		return _nivelDBEntity.getNivelesCF(idNivelPadre);
	}

	/**
	 * Obtiene la lista de niveles no serie de descripción.
	 *
	 * @param nivelPadre
	 *            Nivel del padre.
	 * @return Lista de niveles de descripción.
	 */
	public List getNivelesNoSerieCF(String idNivelPadre) {
		return _nivelDBEntity.getNivelesNoSerieCF(idNivelPadre);
	}

	/**
	 * Obtiene la lista de niveles de descripción.
	 *
	 * @param idNivelPadre
	 *            Identificador del nivel del padre.
	 * @param tipoNivel
	 *            Tipo de nivel.
	 * @return Lista de niveles de descripción.
	 */
	/*
	 * Funcionalidad repetida public List getNivelesCF(String idNivelPadre, int
	 * tipoNivel) { return _nivelDBEntity.getNivelesCF(tipoNivel, idNivelPadre);
	 * }
	 */
	/**
	 * Obtiene los tipos de niveles.
	 *
	 * @param idNivelPadre
	 *            Identificador del nivel padre.
	 * @return Tipos de niveles.
	 */
	public Collection getTiposSubniveles(String idNivelPadre) {
		Collection childTypes = new HashSet();

		List subniveles = getNivelesCF(idNivelPadre);
		if (!CollectionUtils.isEmpty(subniveles)) {
			INivelCFVO nivel;
			for (int i = 0; i < subniveles.size(); i++) {
				nivel = (INivelCFVO) subniveles.get(i);
				childTypes.add(nivel.getTipoNivel());
			}
		}

		return childTypes;
	}

	public List getElementosCFXNivelYFondoYCodigoYTitulo(String[] niveles,
			String idFondo, String codigo, String titulo) {
		return _elementoCuadroClasificacionDBEntity
				.getElementosCFXNivelYFondoYCodigoYTitulo(niveles, idFondo,
						codigo, titulo, true);
	}

	public List getElementosCFXNivelYFondoYCodigoYTitulo(String[] niveles,
			String idFondo, String codigo, String titulo,
			boolean conCriterioEstado) {
		return _elementoCuadroClasificacionDBEntity
				.getElementosCFXNivelYFondoYCodigoYTitulo(niveles, idFondo,
						codigo, titulo, conCriterioEstado);
	}

	public List getElementosCuadroClasificacionXNivel(String[] nivelesID,
			String idFondo) {
		return _elementoCuadroClasificacionDBEntity.getElementosCFXNivel(
				nivelesID, idFondo);
	}

	public int getElementosCFByNivel(String idNivel) {
		return _elementoCuadroClasificacionDBEntity
				.getCountElementosCFByNivel(idNivel);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see common.bi.GestionCuadroClasificacionBI#removeElementoCF(fondos.vos.
	 * ElementoCuadroClasificacionVO)
	 */
	public void removeElementoCF(ElementoCuadroClasificacionVO elementoCF)
			throws FondosOperacionNoPermitidaException {
		// auditoria: obtencion de evento de log
		LoggingEvent logginEvent = AuditFondos.getLogginEventRemove(this,
				elementoCF);

		// borrado de elementoCF y datos descriptivos asociados
		String idElementoCF = elementoCF.getId();

		checkPermission(FondosSecurityManager.ELIMINACION_ELEMENTO_ACTION);

		checkRemoveElementoCF(elementoCF);

		// Auditoria
		ElementoCuadroClasificacionVO padre = getElementoCuadroClasificacion(elementoCF
				.getIdPadre());
		String codigoAscendenteJerarquico = null;
		if (padre != null)
			codigoAscendenteJerarquico = padre.getCodReferencia();

		Locale locale = getServiceClient().getLocale();
		AuditFondos.addDataLogRemove(locale, logginEvent, elementoCF,
				codigoAscendenteJerarquico);

		iniciarTransaccion();

		GestionDescripcionBI descripcionBI = getServiceRepository()
				.lookupGestionDescripcionBI();
		descripcionBI.deleteFicha(idElementoCF, TipoFicha.FICHA_ELEMENTO_CF);
		_elementoCuadroClasificacionDBEntity.deleteElementoCF(idElementoCF);

		commit();

	}

	public void checkMoverElemento(
			ElementoCuadroClasificacionVO elementoCuadro,
			ElementoCuadroClasificacionVO nuevoPadre)
			throws FondosOperacionNoPermitidaException {
		if (_elementoCuadroClasificacionDBEntity.hasHermanosConMismoCodigo(
				elementoCuadro.getId(), elementoCuadro.getIdNivel(),
				elementoCuadro.getCodigo(), nuevoPadre.getId()))
			throw new FondosOperacionNoPermitidaException(
					FondosOperacionNoPermitidaException.XNO_SE_PUEDE_MOVER_HAY_HERMANOS_CON_MISMO_CODIGO);
		// TODO REVISAR
		// if
		// (_elementoCuadroClasificacionDBEntity.countNumChilds(elementoCuadro.getId())
		// > 0)
		// throw new FondosOperacionNoPermitidaException(
		// FondosOperacionNoPermitidaException.XNO_SE_PUEDE_MOVER_TIENE_SERIES);
		// if (elementoCuadro.getEstado() ==
		// IElementoCuadroClasificacion.VIGENTE
		// && nuevoPadre.getEstado() == IElementoCuadroClasificacion.NO_VIGENTE)
		// throw new FondosOperacionNoPermitidaException(
		// FondosOperacionNoPermitidaException.XNO_SE_PUEDE_MOVER_NUEVO_PADRE_NO_VIGENTE);
	}

	public void checkMoverElemento(TablaTemporalFondosVO tablaTemporal,
			ElementoCuadroClasificacionVO nuevoPadre)
			throws FondosOperacionNoPermitidaException {
		if (_elementoCuadroClasificacionDBEntity.hasHermanosConMismoCodigo(
				tablaTemporal, nuevoPadre.getId()))
			throw new FondosOperacionNoPermitidaException(
					FondosOperacionNoPermitidaException.XNO_SE_PUEDE_MOVER_HAY_HERMANOS_CON_MISMO_CODIGO);

		// Comprobar si el elmento está incluído en alguna eliminación no
		// finalizada
		if (getGestionEliminacionBI()
				.hasUnidadesDocumentalesEnSeleccionSinFinalizar(tablaTemporal)) {
			throw new FondosOperacionNoPermitidaException(
					FondosOperacionNoPermitidaException.XNO_SE_PUEDE_MOVER_UDOCS_EN_ELIMINACION_NO_FINALIZADA);
		}
	}

	public void moverUnidadesDocumentales(
			TablaTemporalFondosVO tablaTemporalUDocs,
			ElementoCuadroClasificacionVO elementoPadre, String idLCA)
			throws FondosOperacionNoPermitidaException, Exception {
		iniciarTransaccion();

		// Obtenemos el repositorio con la informacion de sesion comun
		ServiceRepository repository = getServiceRepository();
		GestionSeriesBI seriesBI = repository.lookupGestionSeriesBI();


		// Auditoria
		LoggingEvent logEvent = AuditFondos.getLogginEventMove(this);

		// Comprobar permisos
		checkPermission(FondosSecurityManager.MODIFICAR_ELEMENTO_ACTION);
		checkMoverElemento(tablaTemporalUDocs, elementoPadre);

		Locale locale = getServiceClient().getLocale();
		AuditFondos.addDataLogMoveStep1(locale, logEvent, tablaTemporalUDocs);

		// Obtener los codigos de referencia de la serie y del fondo
		String codRefPadre = _elementoCuadroClasificacionDBEntity
				.getCodRefElementoCfFunction(elementoPadre.getId());
		String codRefFondo = _elementoCuadroClasificacionDBEntity
				.getCodRefElementoCfFunction(elementoPadre.getIdFondo());

		// Actualizar el idserie y el idfondo de las unidades documentales
		_unidadDocumentalDbEntity.updateSerieFondoUDocs(elementoPadre,
				tablaTemporalUDocs);

		// Actualizar la identificación de unidades documentales
		_udocEnUiDepositoDbEntity.updateIdentificacion(codRefFondo,
				tablaTemporalUDocs);

		// Actualizar la identificación de unidades de instalación
		_uinstalacionDepositoDbEntity.updateIdentificacionUnidadesInstalacion(
				codRefFondo, tablaTemporalUDocs);

		// Actualizar las unidades en fondos
		_elementoCuadroClasificacionDBEntity
				.updatePadreCodRefUnidadesDocumentales(elementoPadre.getId(),
						elementoPadre.getIdFondo(), codRefFondo, codRefPadre,
						tablaTemporalUDocs);

		// Auditoria
		String codigoNuevoAscendenteJerarquico = null;
		if (elementoPadre != null)
			codigoNuevoAscendenteJerarquico = elementoPadre.getCodReferencia();
		AuditFondos.addDataLogMoveStep2(locale, logEvent, tablaTemporalUDocs,
				codigoNuevoAscendenteJerarquico);

		if (StringUtils.isNotEmpty(idLCA)) {
			_elementoCuadroClasificacionDBEntity.updateControlAcceso(
					tablaTemporalUDocs, 0, idLCA);
		}

		String oldIdSerie = tablaTemporalUDocs.getIdentificador();

		// Actualizar datos de la serie Anterior
		seriesBI.updateVolumenSerieNoTransaccional(oldIdSerie);
		seriesBI.updateFechasExtremas(oldIdSerie);
		seriesBI.updateContenidoSerieNoTransaccional(oldIdSerie);

		// Actualizar datos de la serie nueva
		seriesBI.updateVolumenSerieNoTransaccional(elementoPadre.getId());
		seriesBI.updateContenidoSerieNoTransaccional(elementoPadre.getId());
		seriesBI.updateFechasExtremas(elementoPadre.getId());

		liberarTablaTemporalNoTransaccional(tablaTemporalUDocs);

		commit();
	}

	public void moverElemento(ElementoCuadroClasificacionVO elementoCuadro,
			String idNuevoPadre) throws FondosOperacionNoPermitidaException {

		ElementoCuadroClasificacionVO nuevoPadre = _elementoCuadroClasificacionDBEntity
				.getElementoCuadroClasificacion(idNuevoPadre);

		moverElemento(elementoCuadro, nuevoPadre);
	}

	private void moverElemento(ElementoCuadroClasificacionVO elementoCuadro,
			ElementoCuadroClasificacionVO nuevoPadre)
			throws FondosOperacionNoPermitidaException {

		// Auditoria
		LoggingEvent logEvent = AuditFondos.getLogginEventMove(this);

		// Comprobar permisos
		checkPermission(FondosSecurityManager.MODIFICAR_ELEMENTO_ACTION);
		checkMoverElemento(elementoCuadro, nuevoPadre);

		ConfiguracionFondos config = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionFondos();
		String delimiter = config.getDelimitadorCodigoReferencia();

		iniciarTransaccion();

		// Auditoria
		Locale locale = getServiceClient().getLocale();
		String codigoAscendenteJerarquico = null;

		ElementoCuadroClasificacionVO padre = _elementoCuadroClasificacionDBEntity
				.getElementoPadre(elementoCuadro.getId());
		if (padre != null) {
			codigoAscendenteJerarquico = padre.getCodReferencia();
		}

		DataLoggingEvent dataLogginEvent = AuditFondos.addDataLogMoveStep1(
				locale, logEvent, elementoCuadro, codigoAscendenteJerarquico);

		// Actualizar codigos de referencia.
		elementoCuadro.setIdPadre(nuevoPadre.getId());
		_elementoCuadroClasificacionDBEntity
				.updateElementoCuadroClasificacion(elementoCuadro);
		_elementoCuadroClasificacionDBEntity.updateCodRefElementoYHijos(
				elementoCuadro.getId(), delimiter, true);

		// Auditoria
		String codigoNuevoAscendenteJerarquico = null;
		if (nuevoPadre != null)
			codigoNuevoAscendenteJerarquico = nuevoPadre.getCodReferencia();
		AuditFondos.addDataLogMoveStep2ByCodigo(locale, dataLogginEvent,
				elementoCuadro.getCodigo(), codigoNuevoAscendenteJerarquico);

		commit();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see common.bi.ServiceBase#getLogginEvent(int)
	 */
	public LoggingEvent getLogginEvent(int action) {
		// obtener el evento
		LoggingEvent event = new LoggingEvent(ArchivoModules.FONDOS_MODULE,
				action, getServiceClient(), false);

		// anadirlo al logger
		getLogger().add(event);

		return event;
	}

	public List getAncestors(String idElemento) {
		return _elementoCuadroClasificacionDBEntity.getAncestors(idElemento);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see common.bi.GestionCuadroClasificacionBI#
	 * getElementoCuadroClasificacionXListaControlAcceso(java.lang.String)
	 */
	public List getElementoCuadroClasificacionXListaControlAcceso(
			String idListaControlAcceso) {
		return _elementoCuadroClasificacionDBEntity
				.getElementosCFXLCAcceso(idListaControlAcceso);

	}

	/**
	 * Obtiene los estados visibles del usuario actual.
	 *
	 * @return Lista de estados.
	 */
	private int[] getEstadosVisiblesUsuario() {
		int[] estados = null;
		if (!getServiceClient().hasAnyPermission(
				PERMISOS_VER_ELEMENTOS_NO_VIGENTES))
			estados = new int[] { IElementoCuadroClasificacion.VIGENTE };
		return estados;
	}

	/**
	 * Obtiene los nodos raiz.
	 *
	 * @return Lista de elementos del cuadro.
	 */
	public List getRootNodes() {

		ServiceClient serviceClient = getServiceClient();
		if (!serviceClient.isPersonalArchivo()
				&& ConfigConstants.getInstance()
						.getOcultarClasificadoresFondosUsuariosOficina()) {
			return _elementoCuadroClasificacionDBEntity
					.getFondosWithPermissions(serviceClient,
							getEstadosVisiblesUsuario());
		} else {
			return _elementoCuadroClasificacionDBEntity
					.getElementosCuadroClasificacionWithPermissions(
							serviceClient, null, getEstadosVisiblesUsuario());
		}
	}

	/**
	 * Obtiene los estados de los elementos del cuadro.
	 *
	 * @return Estados de los elementos del cuadro.
	 */
	public List getEstadosElementos() {
		List estados = new ArrayList();
		Locale localeUsuario = getServiceClient().getLocale();
		estados.add(new TypeDescVO(0, Messages.getString(
				Constants.ETIQUETA_ESTADO_ELEMENTO_CF + ".0", localeUsuario)));
		estados.add(new TypeDescVO(1, Messages.getString(
				Constants.ETIQUETA_ESTADO_ELEMENTO_CF + ".1", localeUsuario)));
		estados.add(new TypeDescVO(2, Messages.getString(
				Constants.ETIQUETA_ESTADO_ELEMENTO_CF + ".2", localeUsuario)));
		return estados;
	}

	/**
	 * Busca los elementos del cuadro que cumplan unos criterios concretos.
	 *
	 * @param vo
	 *            Criterios de la búsqueda.
	 * @return Lista de elementos del cuadro @link ElementoCuadroClasificacion
	 * @throws TooManyResultsException
	 *             si el número de resultados es excesivo.
	 */
	public List getElementos(BusquedaElementosVO vo)
			throws TooManyResultsException {
		// Comprobar permisos
		if (!getServiceClient().hasPermission(
				AppPermissions.ADMINISTRACION_DESCRIPCION)) {
			checkPermission(FondosSecurityManager.CONSULTA_ACTION);
		}

		vo.setServiceClient(getServiceClient());

		return _elementoCuadroClasificacionDBEntity.getElementos(vo);
	}

	/**
	 * Obtiene las fechas extremas de un clasificador de series.
	 *
	 * @param idClfSeries
	 *            Identificador del clasificador de series.
	 * @return Fechas extremas.
	 */
	public CustomDateRange getFechasExtremasClasificadorSeries(
			String idClfSeries) {
		return _elementoCuadroClasificacionDBEntity
				.getFechasExtremasClasificadorSeries(idClfSeries);
	}

	/**
	 * Obtiene la lista de productores de un clasificador de series.
	 *
	 * @param idClfSeries
	 *            Identificador del clasificador de series.
	 * @return Lista de productores.
	 */
	public List getProductoresClasificadorSeries(String idClfSeries) {
		return _productorSerieDbEntity
				.getProductoresXIdClasificadorSeries(idClfSeries);
	}

	/**
	 * Obtiene la lista de volúmenes de las series de un clasificador de series.
	 *
	 * @param idClfSeries
	 *            Identificador del clasificador de series.
	 * @return Lista de volúmenes de las series de un clasificador de series.
	 */
	public List getVolumenesYSoportesSeriesClasificadorSeries(String idClfSeries) {
		return _elementoCuadroClasificacionDBEntity
				.getVolumenesSeriesClasificadorSeries(idClfSeries);
	}

	public ElementoCuadroClasificacionVO getElementoPadre(String idElemento) {
		return _elementoCuadroClasificacionDBEntity
				.getElementoPadre(idElemento);
	}

	/**
	 * Actualiza la información de control de acceso de un elemento.
	 *
	 * @param idElemento
	 *            Identificador del elemento.
	 * @param nivelAcceso
	 *            Nivel de acceso del elemento ({@link NivelAcceso}).
	 * @param idListaControlAcceso
	 *            Identificador de la lista de control de acceso.
	 */
	public void updateControlAcceso(String idElemento, int nivelAcceso,
			String idListaControlAcceso) {
		_elementoCuadroClasificacionDBEntity.updateControlAcceso(idElemento,
				nivelAcceso, idListaControlAcceso);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * common.bi.GestionCuadroClasificacionBI#getUnidadesDocumentales(fondos
	 * .vos.BusquedaUdocsVO)
	 */
	public List getUnidadesDocumentales(BusquedaUdocsVO vo)
			throws TooManyResultsException {
		// Comprobar permisos
		// checkPermission(FondosSecurityManager.CONSULTA_ACTION);

		vo.setServiceClient(getServiceClient());

		return _unidadDocumentalDbEntity.findUnidadesDocumentales(vo);
	}

	public List getElementos(String[] ids,
			BusquedaElementosVO busquedaElementosVO, Busqueda busqueda)
			throws TooManyResultsException {
		return _elementoCuadroClasificacionDBEntity
				.getElementosCuadroClasificacion(ids, busquedaElementosVO,
						busqueda);
	}

	public List getElementosYPartes(String[] ids,
			BusquedaElementosVO busquedaElementosVO, Busqueda busqueda)
			throws TooManyResultsException {
		return _elementoCuadroClasificacionDBEntity
				.getElementosCuadroClasificacionYPartes(ids,
						busquedaElementosVO, busqueda);
	}

	public List getElementos(String[] ids) {
		return _elementoCuadroClasificacionDBEntity
				.getElementosCuadroClasificacion(ids);
	}

	public List getElementosCFVOByIds(String[] ids) {
		return _elementoCuadroClasificacionDBEntity.getElementosCFVOByIds(ids);
	}

	public List getIdsElementos(BusquedaElementosVO vo, int numMaxResults,
			Busqueda busqueda) throws TooManyResultsException {
		List idsElementos = new ArrayList();

		// Comprobar permisos
		if (!getServiceClient().hasPermission(
				AppPermissions.ADMINISTRACION_DESCRIPCION)) {
			checkPermission(FondosSecurityManager.CONSULTA_ACTION);
		}

		vo.setServiceClient(getServiceClient());

		// Comprobar los niveles de descripcion seleccionados para ver si son de
		// tipo nivel documental para el bloqueo.
		if (vo.getCamposRellenos() != null
				&& vo.getCamposRellenos()
						.contains(
								CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_NIVELES_DESCRIPCION)
				&& vo.getCamposRellenos().contains(
						CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_BLOQUEO)) {
			List niveles = _nivelDBEntity.getNivelesCFByTipo(
					new int[] { TipoNivelCF.UNIDAD_DOCUMENTAL
							.getIdentificador() }, vo.getNiveles());
			if (niveles != null && niveles.isEmpty()) {
				vo.getCamposRellenos().remove(
						CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_BLOQUEO);
				vo.setBloqueos(null);
			}
		}

		idsElementos = _elementoCuadroClasificacionDBEntity.getIdsElementos(vo,
				numMaxResults, busqueda);

		// Identificadores de los elementos del cuadro de clasificación que
		// cumplen con las condiciones anteriores y además
		// con las de contenido de fichero

		if (busqueda != null
				&& busqueda.getMapEntrada() != null
				&& busqueda
						.getMapEntrada()
						.containsKey(
								CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_CONTENIDO_FICHERO)
				&& idsElementos != null && idsElementos.size() > 0) {
			if (vo.getCamposRellenos().contains(
					CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_CONTENIDO_FICHERO)
					&& StringUtils.isNotEmpty(vo.getContenidoFichero())) {
				idsElementos = getGestionDocumentosElectronicosBI()
						.buscarFicherosXContenido(vo.getContenidoFichero(),
								idsElementos);
			}
		}

		return idsElementos;
	}

	/**
	 * Obtener los ids,signaturas de elementos que cumplan las condiciones de
	 * búsqueda incluyendo uno por cada una de sus partes en caso de que las
	 * tuviesen.
	 *
	 * @param vo
	 * @param numMaxResultados
	 * @param busqueda
	 * @return Una lista de strings donde las posiciones pares son ids de
	 *         elementos y las impares son signaturas
	 * @throws TooManyResultsException
	 */
	public List getIdsElementosYPartes(BusquedaElementosVO vo,
			int numMaxResultados, Busqueda busqueda)
			throws TooManyResultsException {
		// Comprobar permisos
		if (!getServiceClient().hasPermission(
				AppPermissions.ADMINISTRACION_DESCRIPCION)) {
			checkPermission(FondosSecurityManager.CONSULTA_ACTION);
		}

		vo.setServiceClient(getServiceClient());

		return _elementoCuadroClasificacionDBEntity.getIdsElementosYPartes(vo,
				numMaxResultados, busqueda);

	}

	/**
	 * Obtiene la lista de productores de un elemento del cuadro de
	 * clasificación
	 *
	 * @param idElementocf
	 *            Identificador del elemento del cuadro
	 * @param idCampo
	 *            Identificador del campo de productor
	 * @return Lista de productores.
	 */
	public List getProductoresElementoCF(String idElementocf, String idCampo) {
		return _elementoCuadroClasificacionDBEntity.getProductoresElementoCF(
				idElementocf, idCampo);
	}

	/**
	 * Obtiene la lista de rangos de un elemento del cuadro de clasificación
	 *
	 * @param idElementocf
	 *            Identificador del elemento del cuadro
	 * @param idCampoInicial
	 *            Identificador del campo rango inicial
	 * @param idCampoFinal
	 *            Identificador del campo rango final
	 * @return Lista de rangos.
	 */
	public List getRangosElementoCF(String idElementocf, String idCampoInicial,
			String idCampoFinal) {

		return _elementoCuadroClasificacionDBEntity.getRangosElementoCF(
				idElementocf, idCampoInicial, idCampoFinal);
	}

	/*
	 * (sin Javadoc)
	 *
	 * @see
	 * common.bi.GestionCuadroClasificacionBI#existeArchivoEnCuadro(java.lang
	 * .String)
	 */
	public boolean existeArchivoEnCuadro(String idArchivo) {

		List listaElementos = _elementoCuadroClasificacionDBEntity
				.getDistinctIdsArchivo(idArchivo);
		if (ListUtils.isEmpty(listaElementos)) {
			return false;
		} else {
			return true;
		}
	}

	public void updateElementoCFVEA(ElementoCuadroClasificacionVO elemento) {
		_elementoCuadroClasificacionDBEntity.updateElementoCFVEA(elemento);
		String newCodReferencia = _elementoCuadroClasificacionDBEntity
				.getCodRefElementoCfFunction(elemento.getId());
		_elementoCuadroClasificacionDBEntity.updateCodReferenciaCF(
				elemento.getId(), newCodReferencia);
	}

	public void updateElementoCF(ElementoCuadroClasificacionVO elemento) {
		_elementoCuadroClasificacionDBEntity.updateElementoCFVEA(elemento);
	}

	public String[] getNivelesSerie() {
		List nivelesSerie = getNivelesByTipo(TipoNivelCF.SERIE, null);
		if (nivelesSerie == null)
			return null;
		String[] niveles = new String[nivelesSerie.size()];
		int i = 0;
		for (Iterator it = nivelesSerie.iterator(); it.hasNext();) {
			INivelCFVO nivel = ((INivelCFVO) it.next());
			niveles[i] = nivel.getId();
			i++;
		}
		return niveles;
	}

	public boolean isAccesibleWithPermissions(ServiceClient serviceClient,
			String idElemento) {
		return _elementoCuadroClasificacionDBEntity.isAccesibleWithPermissions(
				serviceClient, idElemento);
	}

	public int reemplazoElementosCF(BusquedaElementosVO vo, Busqueda busqueda,
			String nombreCampoCambio, String formaReemplazo)
			throws TooManyResultsException {
		boolean reemplazoAvanzadoSoloCamposFichaVacios = FormaReemplazo
				.isReemplazoValoresNulos(formaReemplazo);

		if (!getServiceClient().hasPermission(
				AppPermissions.ADMINISTRACION_DESCRIPCION)) {
			throw new SecurityException(
					"Se ha intentando realizar una operación no permitida");
		}
		Locale locale = getServiceClient().getLocale();
		vo.setServiceClient(getServiceClient());

		ConsultaConnectBy selectForInStatementInWhere = _elementoCuadroClasificacionDBEntity
				.getSQLPadresEIdsElementos(vo, busqueda);
		String idCampo = vo.getCampoCambio();

		boolean isCampoFecha = false;
		boolean isFechaIni = false;
		if (DescripcionUtils.isCampoDescripcionFechaIni(idCampo)) {
			isCampoFecha = true;
			isFechaIni = true;
		} else if (DescripcionUtils.isCampoDescripcionFechaFin(idCampo)) {
			isCampoFecha = true;
			isFechaIni = false;
		}

		boolean transaccionIniciada = false;
		int filasAfectadas = 0;
		if (isCampoFecha) {
			Date fecha = null;
			CustomDate periodoFecha = new CustomDate(vo.getValor3()[0],
					vo.getValor3()[1], "/", null);
			if (isFechaIni) {
				fecha = periodoFecha.getMinDate();
			} else {
				fecha = periodoFecha.getMaxDate();
			}

			List seriesAfectadas = _elementoCuadroClasificacionDBEntity
					.getSeriesAfectadasFechaIniFin(selectForInStatementInWhere,
							fecha, "" + vo.getCampoCambio(), isFechaIni, false);
			if (seriesAfectadas != null && seriesAfectadas.size() > 0) {
				iniciarTransaccion();
				transaccionIniciada = true;

				AuditoriaDescripcion.auditaReemplazoCampoElemento(locale, this,
						nombreCampoCambio, vo, seriesAfectadas);

				// actualizar antes las fechas de las series afectadas
				filasAfectadas += _elementoCuadroClasificacionDBEntity
						.updateSerieAfectadasFechaIniFin(
								selectForInStatementInWhere, periodoFecha, ""
										+ vo.getCampoCambio(), isFechaIni);
			}
		}

		String condicionValor = _elementoCuadroClasificacionDBEntity
				.getCondicionValorCampo(vo);

		AuditoriaDescripcion.auditaReemplazoCampoElemento(locale, this, vo,
				nombreCampoCambio, busqueda);

		// detectar si se esta usando el reemplazo para inicializar valores
		// nulos.
		boolean soloValoresNulos = reemplazoAvanzadoSoloCamposFichaVacios;
		if (!reemplazoAvanzadoSoloCamposFichaVacios)
			soloValoresNulos = detectarReemplazoSobreValoresVaciosFicha(vo);

		if (FormaReemplazo.isReemplazoValoresParciales(formaReemplazo)) {
			int numMaxResultados = ConfiguracionSistemaArchivoFactory
					.getConfiguracionSistemaArchivo().getConfiguracionGeneral()
					.getNumMaxResultados();
			if (vo.getSelectedIds() != null) {
				filasAfectadas = reemplazarParteValor(vo.getSelectedIds(),
						idCampo, (short) vo.getTipoCampoCambio().intValue(),
						vo.getValorReemplazoParcial(), vo.getValor3()[0]);
			} else {
				List listaIds = _elementoCuadroClasificacionDBEntity
						.getIdsElementos(vo, numMaxResultados, busqueda);

				if (ListUtils.isNotEmpty(listaIds)) {
					filasAfectadas = reemplazarParteValor(
							CollectionUtils.toArray(listaIds), idCampo,
							(short) vo.getTipoCampoCambio().intValue(),
							vo.getValor1()[0], vo.getValor3()[0]);
				}
			}
		} else {
			Integer tipoReferencia = null;
			if (vo.getTipoReferencia() != null
					&& StringUtils.isNotEmpty(vo.getTipoReferencia())
					&& Integer.valueOf(vo.getTipoReferencia()) != null)
				tipoReferencia = new Integer(Integer.parseInt(vo
						.getTipoReferencia()));

			String valorAntiguo = null;
			if (vo.getValor4() != null
					&& StringUtils.isNotEmpty(vo.getValor4())) {
				if (vo.getTipoCampoCambio() != null
						&& DefTipos.TIPO_DATO_FECHA == vo.getTipoCampoCambio()
								.intValue()) {
					CustomDate valorFecha = new CustomDate(
							vo.getFormatoFecha4(), vo.getValor4A(),
							vo.getValor4M(), vo.getValor4D(), vo.getValor4S());
					valorAntiguo = valorFecha.getValue();
				} else
					valorAntiguo = vo.getValor4();
			} else {
				if (vo.getTipoCampoCambio() != null
						&& DefTipos.TIPO_DATO_FECHA == vo.getTipoCampoCambio()
								.intValue()) {
					CustomDate valorFecha = new CustomDate(
							vo.getFormatoFecha1()[0], vo.getValor1A()[0],
							vo.getValor1M()[0], vo.getValor1D()[0],
							vo.getValor1S()[0]);
					valorAntiguo = valorFecha.getValue();
				} else
					valorAntiguo = vo.getValor1()[0];
			}

			if (vo.getSelectedIds() != null) {
				filasAfectadas += _elementoCuadroClasificacionDBEntity
						.updateCampoGenerico(
								Arrays.asList(vo.getSelectedIds()), vo
										.getTipoCampoCambio().intValue(), vo
										.getCampoCambio(), valorAntiguo, vo
										.getValor3()[0], vo.getValor3()[1],
								condicionValor, soloValoresNulos,
								tipoReferencia);
			} else {
				selectForInStatementInWhere = _elementoCuadroClasificacionDBEntity
						.getSQLIdsElementos(vo, busqueda, false);

				filasAfectadas += _elementoCuadroClasificacionDBEntity
						.updateCampoGenerico(selectForInStatementInWhere, vo
								.getTipoCampoCambio().intValue(), vo
								.getCampoCambio(), valorAntiguo,
								vo.getValor3()[0], vo.getValor3()[1],
								condicionValor, soloValoresNulos,
								tipoReferencia);
			}
		}

		if (transaccionIniciada)
			commit();
		return filasAfectadas;
	}

	private int reemplazarParteValor(String[] idsElementos, String idCampo,
			short tipoCampo, String valorAntiguo, String valorNuevo) {
		int numeroElemtentosAfectados = 0;

		if (idCampo.equals(DefTipos.CAMPO_ESPECIAL_ID_TITULO)) {
			for (int i = 0; i < idsElementos.length; i++) {
				String idElementoCF = (String) idsElementos[i];
				reemplazarParteValorTitulo(idElementoCF, valorAntiguo,
						valorNuevo);
				numeroElemtentosAfectados++;
			}
		} else if (idCampo.equals(DefTipos.CAMPO_ESPECIAL_ID_NUMERO_EXPEDIENTE)) {
			for (int i = 0; i < idsElementos.length; i++) {
				String idElementoCF = (String) idsElementos[i];
				reemplazarParteNumeroExpediente(idElementoCF, valorAntiguo,
						valorNuevo);
				numeroElemtentosAfectados++;
			}
		} else {
			switch (tipoCampo) {
			case ValorCampoGenericoVOBase.TIPO_TEXTO_CORTO:
				for (int i = 0; i < idsElementos.length; i++) {
					String idElementoCF = (String) idsElementos[i];
					reemplazarParteValorTextoCorto(idElementoCF, idCampo,
							valorAntiguo, valorNuevo);
					numeroElemtentosAfectados++;
				}
				break;

			case ValorCampoGenericoVOBase.TIPO_TEXTO_LARGO:
				for (int i = 0; i < idsElementos.length; i++) {
					String idElementoCF = (String) idsElementos[i];
					reemplazarParteValorTextoLargo(idElementoCF, idCampo,
							valorAntiguo, valorNuevo);
					numeroElemtentosAfectados++;
				}
				break;

			default:
				throw new ArchigestException(
						"El reemplazo de valores parciales para el idCampo= "
								+ idCampo + " tipoCampo=" + tipoCampo
								+ "  no está permitido");
			}
		}
		return numeroElemtentosAfectados;
	}

	private void reemplazarParteValorTextoCorto(String idElementoCF,
			String idCampo, String valorAntiguo, String valorNuevo) {
		List listaValores = _textoCortoDbEntity.getValues(idElementoCF,
				idCampo, ValorCampoGenericoVOBase.TIPO_ELEMENTO_INDEFINIDO);

		for (Iterator it = listaValores.iterator(); it.hasNext();) {
			CampoTextoVO campoTexto = (CampoTextoVO) it.next();

			String valor = campoTexto.getValor();
			valor = StringUtils.replace(valor, valorAntiguo, valorNuevo);
			campoTexto.setValor(valor);

			_textoCortoDbEntity.updateValue(campoTexto);
		}
	}

	private void reemplazarParteValorTextoLargo(String idElementoCF,
			String idCampo, String valorAntiguo, String valorNuevo) {
		List listaValores = _textoLargoDbEntity.getValues(idElementoCF,
				idCampo, ValorCampoGenericoVOBase.TIPO_ELEMENTO_INDEFINIDO);

		for (Iterator it = listaValores.iterator(); it.hasNext();) {
			CampoTextoVO campoTexto = (CampoTextoVO) it.next();

			String valor = campoTexto.getValor();
			valor = StringUtils.replace(valor, valorAntiguo, valorNuevo);
			campoTexto.setValor(valor);

			_textoLargoDbEntity.updateValue(campoTexto);
		}
	}

	private void reemplazarParteValorTitulo(String idElementoCF,
			String valorAntiguo, String valorNuevo) {
		ElementoCuadroClasificacionVO elemento = _elementoCuadroClasificacionDBEntity
				.getElementoCuadroClasificacion(idElementoCF);

		if (elemento != null) {
			String titulo = elemento.getTitulo();
			titulo = StringUtils.replace(titulo, valorAntiguo, valorNuevo);
			elemento.setTitulo(titulo);
			_elementoCuadroClasificacionDBEntity.updateTitulo(idElementoCF,
					titulo);
		}
	}

	private void reemplazarParteNumeroExpediente(String idElementoCF,
			String valorAntiguo, String valorNuevo) {
		UnidadDocumental unidadDoc = (UnidadDocumental) _unidadDocumentalDbEntity
				.getUnidadDocumental(idElementoCF);

		if (unidadDoc != null) {
			String numExp = unidadDoc.getNumExp();
			numExp = StringUtils.replace(numExp, valorAntiguo, valorNuevo);
			_unidadDocumentalDbEntity.updateNumeroExpediente(idElementoCF,
					numExp);
		}
	}

	private boolean detectarReemplazoSobreValoresVaciosFicha(
			BusquedaElementosVO vo) {
		if (vo == null)
			return false;
		if (StringUtils.isEmpty(vo.getValor1()[0])
				&& (StringUtils.isEmpty(vo.getValor1A()[0])
						&& StringUtils.isEmpty(vo.getValor1D()[0])
						&& StringUtils.isEmpty(vo.getValor1M()[0]) && StringUtils
						.isEmpty(vo.getValor1S()[0]))
				&& (vo.getSelectedIds() == null || vo.getSelectedIds().length == 0)) {
			return true;
		}
		return false;
	}

	public boolean checkUpdateFechasElementos(BusquedaElementosVO vo,
			Busqueda busqueda, boolean isFechaIni) {
		vo.setServiceClient(getServiceClient());
		String idCampo = vo.getCampoCambio();

		Date fecha = DescripcionUtils.getFechaFromRangoIniFin(
				vo.getValor3()[0], vo.getValor3()[1], isFechaIni);

		boolean correcto = true;
		if (vo.getSelectedIds() != null) {
			List idsElementos = Arrays.asList(vo.getSelectedIds());
			correcto = _elementoCuadroClasificacionDBEntity
					.checkUpdateFechaIniFin(idsElementos, fecha, idCampo,
							isFechaIni);
		} else {
			ConsultaConnectBy subqueryIdsElementos = _elementoCuadroClasificacionDBEntity
					.getSQLIdsElementos(vo, busqueda, false);

			correcto = _elementoCuadroClasificacionDBEntity
					.checkUpdateFechaIniFin(subqueryIdsElementos, fecha,
							idCampo, isFechaIni);
		}
		return correcto;
	}

	public boolean checkUpdateFechasSeries(BusquedaElementosVO vo,
			Busqueda busqueda, boolean isFechaIni) {
		vo.setServiceClient(getServiceClient());
		String idCampo = vo.getCampoCambio();

		Date fecha = DescripcionUtils.getFechaFromRangoIniFin(
				vo.getValor3()[0], vo.getValor3()[1], isFechaIni);

		// hay que chequear las fechas de una udoc
		ConsultaConnectBy subqueryIdsUdocs = _elementoCuadroClasificacionDBEntity
				.getSQLPadresEIdsElementos(vo, busqueda);

		return _elementoCuadroClasificacionDBEntity
				.checkUpdateSerieFechaIniFin(subqueryIdsUdocs, fecha, idCampo,
						isFechaIni);
	}

	public List checkUdocsInRangeNewFechasSeries(BusquedaElementosVO vo,
			Busqueda busqueda, boolean isFechaIni) {
		vo.setServiceClient(getServiceClient());
		String idCampo = vo.getCampoCambio();

		Date fecha = DescripcionUtils.getFechaFromRangoIniFin(
				vo.getValor3()[0], vo.getValor3()[1], isFechaIni);

		// hay que chequear las fechas de una udoc
		ConsultaConnectBy subqueryIdsUdocs = _elementoCuadroClasificacionDBEntity
				.getSQLIdsElementos(vo, busqueda, false);

		return _elementoCuadroClasificacionDBEntity
				.checkUdocsInRangeNewFechasSeries(subqueryIdsUdocs, fecha,
						idCampo, isFechaIni);
	}

	public List getSeriesAfectadas(BusquedaElementosVO vo, Busqueda busqueda,
			boolean isFechaIni) {
		vo.setServiceClient(getServiceClient());
		String idCampo = vo.getCampoCambio();

		Date fecha = DescripcionUtils.getFechaFromRangoIniFin(
				vo.getValor3()[0], vo.getValor3()[1], isFechaIni);

		// hay que chequear las fechas de una udoc
		ConsultaConnectBy subqueryIdsUdocs = _elementoCuadroClasificacionDBEntity
				.getSQLPadresEIdsElementos(vo, busqueda);

		return _elementoCuadroClasificacionDBEntity
				.getSeriesAfectadasFechaIniFin(subqueryIdsUdocs, fecha,
						idCampo, isFechaIni, false);
	}

	public int getCountElementosAfectados(
			BusquedaElementosVO busquedaElementosVO, Busqueda busqueda) {
		busquedaElementosVO.setServiceClient(getServiceClient());
		// no tener en cuenta las udocs bloqueadas o en ui bloqueada.
		return _elementoCuadroClasificacionDBEntity.getCountElementosAfectados(
				busquedaElementosVO, busqueda);
	}

	public List getElementosAfectados(BusquedaElementosVO busquedaElementosVO,
			Busqueda busqueda) {
		return _elementoCuadroClasificacionDBEntity.getElementosAfectados(
				busquedaElementosVO, busqueda);
	}

	public boolean isSubtipoCaja(String idNivel) {
		boolean ret = false;

		INivelCFVO nivel = this.getNivelCF(idNivel);

		if (nivel != null
				&& nivel.getSubtipo() == ElementoCuadroClasificacion.SUBTIPO_CAJA)
			ret = true;

		return ret;
	}

	public int getSubtipo(String idNivel) {
		int ret = ElementoCuadroClasificacion.SUBTIPO_UNIDAD_DOCUMENTAL_SIMPLE;

		INivelCFVO nivel = this.getNivelCF(idNivel);

		if (nivel != null)
			ret = nivel.getSubtipo();

		return ret;
	}

	public INivelCFVO getNivelCF(int tipo, int subtipo) {
		return _nivelDBEntity.getNivelCF(tipo, subtipo);
	}

	public List getIdsElementosBloqueados(List ids) {
		return _elementoCuadroClasificacionDBEntity
				.getIdsElementosBloqueados(ids);
	}

	public int getTipoElemento(String idElemento) {
		ElementoCuadroClasificacion elementoCuadro = (ElementoCuadroClasificacion) _elementoCuadroClasificacionDBEntity
				.getElementoCuadroClasificacion(idElemento);
		if (elementoCuadro != null)
			return elementoCuadro.getTipo();
		return -1;
	}

	public boolean checkElementosMismoTipo(String[] ids) {
		return _elementoCuadroClasificacionDBEntity
				.checkElementosMismoTipo(ids);
	}

	public boolean checkElementosDistintoPadre(String[] ids,
			boolean permitidoDistintoPadre) {
		return _elementoCuadroClasificacionDBEntity
				.checkElementosDistintoPadre(ids, permitidoDistintoPadre);
	}

	// metodos para chequear los permisos de acceso de elementos.

	public boolean checkPermitidoConsultaElemento(String idElemento)
			throws Exception {
		String[] permisosAcceso = new String[] {
				AppPermissions.CONSULTA_CUADRO_CLASIFICACION,
				AppPermissions.MODIFICACION_CUADRO_CLASIFICACION };
		return _elementoCuadroClasificacionDBEntity
				.checkPermisosAccesibilidadElementoYPadres(idElemento,
						getServiceClient(), permisosAcceso);
	}

	public boolean checkPermisosElemento(String idElemento, Object[] permisos) {
		return _elementoCuadroClasificacionDBEntity
				.checkAccesibilidadElementoWithPermissions(idElemento,
						getServiceClient(), permisos);
	}

	public List getIdsElementosCampoVacioFicha(List idsElementos,
			int tipoCampo, String idCampo) {
		return _elementoCuadroClasificacionDBEntity
				.getIdsElementosCampoVacioFicha(idsElementos, tipoCampo,
						idCampo);
	}

	public List getNumUdocsLongFormatoMovimiento(String idSerieOrigen,
			String idSerieDestino, List idsUIs,
			String[] aliasCamposDevueltosConsulta) {
		return _elementoCuadroClasificacionDBEntity
				.getNumUdocsLongFormatoMovimiento(idSerieOrigen,
						idSerieDestino, idsUIs, aliasCamposDevueltosConsulta);
	}

	List getNumUdocsLongFormatoMovimiento;

	public INivelCFVO insertarNivelJerarquiaCF(INivelCFVO nivelCFVO,
			final JerarquiaNivelCFVO jerarquiaNivelCFVO) {
		iniciarTransaccion();
		if (nivelCFVO != null) {
			_nivelDBEntity.insertNivel(nivelCFVO);
			if (jerarquiaNivelCFVO != null) {
				jerarquiaNivelCFVO.setIdNivelHijo(nivelCFVO.getId());
				jerarquiaNivelCFVO.setTipoNivelHijo(nivelCFVO.getTipo());
				_nivelDBEntity.insertJerarquiaNivelCF(jerarquiaNivelCFVO);
			}
		}
		if (nivelCFVO == null && jerarquiaNivelCFVO != null)
			_nivelDBEntity.insertJerarquiaNivelCF(jerarquiaNivelCFVO);

		commit();
		return nivelCFVO;
	}

	public void deleteJerarquiaNivelCF(
			final JerarquiaNivelCFVO jerarquiaNivelCFVO) {
		iniciarTransaccion();
		if (jerarquiaNivelCFVO != null)
			_nivelDBEntity.deleteJerarquiaNivelCF(jerarquiaNivelCFVO);

		commit();
	}

	public INivelCFVO actualizarNivelJerarquiaCF(INivelCFVO nivelCFVO,
			final JerarquiaNivelCFVO jerarquiaNivelCFVO,
			boolean cambioInitialYesCheck) {
		iniciarTransaccion();
		if (nivelCFVO != null) {
			_nivelDBEntity.updateNivel(nivelCFVO);
			if (jerarquiaNivelCFVO != null) {
				// Si se cambio de Nivel Inicial cuequeado a deschequeado
				// (borrar entrada en la tabla)
				// Sino insertamos en la tabla dicho cambio para indicar que es
				// NIVEL RAIZ
				if (cambioInitialYesCheck)
					_nivelDBEntity.deleteJerarquiaNivelCF(jerarquiaNivelCFVO);
				else
					_nivelDBEntity.insertJerarquiaNivelCF(jerarquiaNivelCFVO);
			}
		}
		commit();
		return nivelCFVO;
	}

	public void eliminarNivelJerarquiaCF(final INivelCFVO nivelCFVO,
			final JerarquiaNivelCFVO jerarquiaNivelCFVO) {
		iniciarTransaccion();
		if (nivelCFVO != null) {
			_nivelDBEntity.deleteNivelCF(nivelCFVO);
			if (jerarquiaNivelCFVO != null) {
				jerarquiaNivelCFVO.setIdNivelHijo(nivelCFVO.getId());
				jerarquiaNivelCFVO.setTipoNivelHijo(nivelCFVO.getTipo());
				_nivelDBEntity.deleteJerarquiaNivelCF(jerarquiaNivelCFVO);
			}
		}
		if (nivelCFVO == null && jerarquiaNivelCFVO != null)
			_nivelDBEntity.deleteJerarquiaNivelCF(jerarquiaNivelCFVO);
		commit();
	}

	public boolean isNivelInicial(String idNivel) {
		if (StringUtils.isNotEmpty(idNivel))
			return _nivelDBEntity.isNivelInicial(idNivel);
		return false;
	}

	public boolean isNivelHijoNoRaiz(String idNivelHijo) {
		if (StringUtils.isNotEmpty(idNivelHijo))
			return _nivelDBEntity.isNivelHijoNoRaiz(idNivelHijo);
		return false;
	}

	public boolean checkElementosMismoArchivo(String[] ids) {
		if (ArrayUtils.isNotEmpty(ids)) {
			return _elementoCuadroClasificacionDBEntity
					.checkUdocsEnMismoArchivo(ids);
		}

		return false;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see common.bi.GestionCuadroClasificacionBI#getArchivoByIdUdoc(java.lang.String)
	 */
	public ArchivoVO getArchivoByIdElemento(String idElemento) {

		ArchivoVO archivoVO = null;

		IElementoCuadroClasificacion elementoCF = _elementoCuadroClasificacionDBEntity
				.getElementoCuadroClasificacion(idElemento);

		if (elementoCF != null) {
			archivoVO = _archivoDbEntity.getArchivoXId(elementoCF
					.getIdArchivo());
		}

		return archivoVO;
	}

	public int getMarcasBloqueo(String idElementoCF) {
		return _elementoCuadroClasificacionDBEntity
				.getMarcasBloqueoElemento(idElementoCF);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see common.bi.GestionCuadroClasificacionBI#getSeriesHijas(java.lang.String)
	 */
	public List getSeriesHijas(String nivelCFSerie, String idElementoCF) {
		return _elementoCuadroClasificacionDBEntity.getElementosXNivelYPadre(
				nivelCFSerie, idElementoCF);
	}

	public TablaTemporalFondosVO getTablaTemporal(String idUsuario,
			String identificador, String[] ids) throws Exception {

		iniciarTransaccion();

		_catalagoTablasTemporalesDBEntity.bloqueaTabla();

		CatalogoTablaTemporalVO vo = _catalagoTablasTemporalesDBEntity
				.getByEstadoAndUsuario(Constants.TABLA_TEMPORAL_LIBRE_STATE,
						idUsuario);

		if (vo != null) {
			vo.setEstado(Constants.TABLA_TEMPORAL_OCUPADO_STATE);
			vo.setFecha(DateUtils.getFechaActual());
			vo.setIdUsuario(idUsuario);
			_catalagoTablasTemporalesDBEntity.actualizarEstado(vo);
		} else {
			vo = _catalagoTablasTemporalesDBEntity
					.getTablaTemporalFromCaducadas();
			if (vo != null) {
				vo.setEstado(Constants.TABLA_TEMPORAL_OCUPADO_STATE);
				vo.setFecha(DateUtils.getFechaActual());
				vo.setIdUsuario(idUsuario);
				_catalagoTablasTemporalesDBEntity.actualizarEstado(vo);
			} else {
				vo = new CatalogoTablaTemporalVO();
				vo.setEstado(Constants.TABLA_TEMPORAL_OCUPADO_STATE);
				vo.setFecha(DateUtils.getFechaActual());
				vo.setIdUsuario(idUsuario);

				_catalagoTablasTemporalesDBEntity.insertar(vo);
				_tablaTemporalDBEntity.createTable(vo.getId().intValue());
			}

		}

		_tablaTemporalDBEntity.truncateTable(vo.getNombreTabla());

		TablaTemporalFondosVO tablaTemporalVO = new TablaTemporalFondosVO(
				vo.getId(), vo.getNombreTabla(), identificador, idUsuario);

		int i = 0;

		int total = ids.length;

		while (i < total) {
			int startIndexInclusive = i;
			int endIndexExclusive = i + 5000;
			if (endIndexExclusive > total) {
				endIndexExclusive = total;
			}

			String[] idsSubconjunto = (String[]) ArrayUtils.subarray(ids,
					startIndexInclusive, endIndexExclusive);

			_tablaTemporalDBEntity.insertAsSelect(tablaTemporalVO,
					idsSubconjunto);

			i = endIndexExclusive;
		}

		commit();

		tablaTemporalVO.setTotal(total);

		return tablaTemporalVO;

	}

	public void liberarTablaTemporalNoTransaccional(
			TablaTemporalFondosVO tablaTemporalFondosVO) {
		try {
			_tablaTemporalDBEntity.truncateTable(tablaTemporalFondosVO
					.getNombreTabla());
			_catalagoTablasTemporalesDBEntity.reset(tablaTemporalFondosVO
					.getNumeroTablaAsInt());
		} catch (Exception e) {
			logger.error(
					"Se ha producido un error al liberar la tabla temporal "
							+ tablaTemporalFondosVO.getNombreTabla(), e);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see common.bi.GestionCuadroClasificacionBI#getElementos(fondos.vos.TablaTemporalFondosVO)
	 */
	public List getElementos(TablaTemporalFondosVO tablaTemporal) {
		return _elementoCuadroClasificacionDBEntity
				.getElementosCuadroClasificacion(tablaTemporal);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see common.bi.GestionCuadroClasificacionBI#getPairsIdCodigo(fondos.vos.TablaTemporalFondosVO)
	 */
	public HashMap getPairsIdCodigo(TablaTemporalFondosVO tablaTemporalFondosVO) {
		return _tablaTemporalDBEntity.getPairsIdCodigo(tablaTemporalFondosVO);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see common.bi.GestionCuadroClasificacionBI#getInteresadosByIdsElementoCF(java.util.List)
	 */
	public Map getInteresadosByIdsElementoCF(List idsElementosCF) {
		Map mapInteresados = new HashMap();

		List interesados = _referenciaDbEntity
				.getInteresadosByIdsElementosCF(ArrayUtils
						.getArrayIds(idsElementosCF));

		if (ListUtils.isNotEmpty(interesados)) {
			for (Iterator iterator = interesados.iterator(); iterator.hasNext();) {
				InteresadoUdocVO interesado = (InteresadoUdocVO) iterator
						.next();

				if (interesado != null) {
					List listaInteresados = (List) mapInteresados
							.get(interesado.getIdelementocf());
					if (listaInteresados == null) {
						listaInteresados = new ArrayList();
						mapInteresados.put(interesado.getIdelementocf(),
								listaInteresados);
					}
					listaInteresados.add(interesado);
				}
			}
		}
		return mapInteresados;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see common.bi.GestionCuadroClasificacionBI#getProductoresByIdsElementoCF(java.util.List)
	 */
	public Map getProductoresByIdsElementoCF(List idsElementos) {
		Map mapProductores = new HashMap();

		List productores = _referenciaDbEntity
				.getProductoresByIdsElementosCF(ArrayUtils
						.getArrayIds(idsElementos));

		if (ListUtils.isNotEmpty(productores)) {
			for (Iterator iterator = productores.iterator(); iterator.hasNext();) {
				ProductorElementoCF productor = (ProductorElementoCF) iterator
						.next();

				if (productor != null) {
					mapProductores.put(productor.getIdelementocf(), productor);
				}
			}
		}
		return mapProductores;

	}

	public IElementoCuadroClasificacion getElementoCuadroClasificacionConFechas(
			String idElemento) {
		return _elementoCuadroClasificacionDBEntity
				.getElementoCuadroClasificacionConFechas(idElemento);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see common.bi.GestionCuadroClasificacionBI#getNombresNiveles(fondos.vos.TablaTemporalFondosVO)
	 */
	public List getNombresNiveles(TablaTemporalFondosVO tablaTemporalFondosVO) {
		return _elementoCuadroClasificacionDBEntity
				.getNombresNiveles(tablaTemporalFondosVO);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see common.bi.GestionCuadroClasificacionBI#getPairsIdCodRefPadre(fondos.vos.TablaTemporalFondosVO)
	 */
	public HashMap getPairsIdCodRefPadre(
			TablaTemporalFondosVO tablaTemporalFondosVO) {
		return _elementoCuadroClasificacionDBEntity
				.getPairsIdCodRefPadre(tablaTemporalFondosVO);

	}
}