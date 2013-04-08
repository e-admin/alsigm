package valoracion.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;

import se.usuarios.AppPermissions;
import valoracion.ValoracionConstants;
import valoracion.db.INSecValoracionDBEntity;
import valoracion.db.INSecVersionDBEntity;
import valoracion.db.IPlazosValoracionDBEntity;
import valoracion.db.IValoracionDBEntity;
import valoracion.exceptions.ValoracionActionNotAllowedException;
import valoracion.vos.BoletinInfoVO;
import valoracion.vos.BusquedaVO;
import valoracion.vos.PlazoValoracionVO;
import valoracion.vos.SerieSeleccionableVO;
import valoracion.vos.ValoracionSerieVO;
import xml.config.ConfiguracionSistemaArchivoFactory;
import auditoria.ArchivoDetails;
import auditoria.ArchivoErrorCodes;
import auditoria.logger.DataLoggingEvent;
import auditoria.logger.LoggingEvent;

import common.Messages;
import common.bi.GestionDescripcionBI;
import common.bi.GestionValoracionBI;
import common.bi.ServiceBase;
import common.db.DBUtils;
import common.definitions.ArchivoActions;
import common.definitions.ArchivoModules;
import common.definitions.ArchivoObjects;
import common.exceptions.ActionNotAllowedException;
import common.exceptions.TooManyResultsException;
import common.pagination.PageInfo;
import common.security.ActionObject;
import common.security.FondosSecurityManager;
import common.util.DateUtils;

import es.archigest.framework.core.vo.PropertyBean;
import fondos.model.EstadoSerie;
import fondos.vos.SerieVO;

/**
 * Clase de implementacion de los metodos de gestión para el módulo de
 * valoración
 */
public class GestionValoracionImpl extends ServiceBase implements
		GestionValoracionBI {

	private final static Logger logger = Logger
			.getLogger(GestionValoracionImpl.class);

	private INSecValoracionDBEntity nSecValoracion = null;
	private INSecVersionDBEntity nVersionDBEntity = null;
	private IValoracionDBEntity valoracionDBEntity = null;
	private IPlazosValoracionDBEntity plazosDBEntity = null;

	public GestionValoracionImpl(INSecValoracionDBEntity nSecValoracion,
			IValoracionDBEntity valoracionDBEntity,
			INSecVersionDBEntity nVersionDBEntity,
			IPlazosValoracionDBEntity plazosDBEntity) {
		this.nSecValoracion = nSecValoracion;
		this.valoracionDBEntity = valoracionDBEntity;
		this.nVersionDBEntity = nVersionDBEntity;
		this.plazosDBEntity = plazosDBEntity;
	}

	public List getEstadosValoracion() {
		List estados = new ArrayList();

		Locale locale = getServiceClient().getLocale();
		estados.add(new PropertyBean("" + ValoracionSerieVO.ESTADO_ABIERTA,
				Messages.getString(ValoracionConstants.PREFIX_KEY_VALORACIONES
						+ "1", locale)));
		estados.add(new PropertyBean(""
				+ ValoracionSerieVO.ESTADO_PENDIENTE_DE_VALIDAR, Messages
				.getString(ValoracionConstants.PREFIX_KEY_VALORACIONES + "2",
						locale)));
		estados.add(new PropertyBean("" + ValoracionSerieVO.ESTADO_VALIDADA,
				Messages.getString(ValoracionConstants.PREFIX_KEY_VALORACIONES
						+ "3", locale)));
		estados.add(new PropertyBean(""
				+ ValoracionSerieVO.ESTADO_VALIDACION_RECHAZADA, Messages
				.getString(ValoracionConstants.PREFIX_KEY_VALORACIONES + "4",
						locale)));
		estados.add(new PropertyBean("" + ValoracionSerieVO.ESTADO_EVALUADA,
				Messages.getString(ValoracionConstants.PREFIX_KEY_VALORACIONES
						+ "5", locale)));
		estados.add(new PropertyBean(""
				+ ValoracionSerieVO.ESTADO_EVALUACION_RECHAZADA, Messages
				.getString(ValoracionConstants.PREFIX_KEY_VALORACIONES + "6",
						locale)));
		estados.add(new PropertyBean("" + ValoracionSerieVO.ESTADO_DICTAMINADA,
				Messages.getString(ValoracionConstants.PREFIX_KEY_VALORACIONES
						+ "7", locale)));
		estados.add(new PropertyBean(""
				+ ValoracionSerieVO.ESTADO_DICTAMEN_RECHAZADO, Messages
				.getString(ValoracionConstants.PREFIX_KEY_VALORACIONES + "8",
						locale)));
		estados.add(new PropertyBean(""
				+ ValoracionSerieVO.ESTADO_DICTAMINADA_HISTORICA, Messages
				.getString(ValoracionConstants.PREFIX_KEY_VALORACIONES + "9",
						locale)));

		return estados;
	}

	public List getTiposOrdenacion(int nivel) {
		List tipos = new ArrayList();
		Locale locale = getServiceClient().getLocale();

		switch (nivel) {
		case ValoracionConstants.NIVEL_SERIE_PRIMERO:
			tipos.add(new PropertyBean(""
					+ ValoracionSerieVO.ORDENACION_CRONOLOGICA, Messages
					.getString(ValoracionConstants.LABEL_VALORACION_ORDENACION
							+ "1", locale)));
			tipos.add(new PropertyBean(""
					+ ValoracionSerieVO.ORDENACION_ALFABETICA_ONOMASTICA,
					Messages.getString(
							ValoracionConstants.LABEL_VALORACION_ORDENACION
									+ "2", locale)));
			tipos.add(new PropertyBean(""
					+ ValoracionSerieVO.ORDENACION_ALFABETICA_GEOGRAFICA,
					Messages.getString(
							ValoracionConstants.LABEL_VALORACION_ORDENACION
									+ "3", locale)));
			tipos.add(new PropertyBean(""
					+ ValoracionSerieVO.ORDENACION_NUMERICA, Messages
					.getString(ValoracionConstants.LABEL_VALORACION_ORDENACION
							+ "4", locale)));
			tipos.add(new PropertyBean(""
					+ ValoracionSerieVO.ORDENACION_ALEATORIA, Messages
					.getString(ValoracionConstants.LABEL_VALORACION_ORDENACION
							+ "5", locale)));
			tipos.add(new PropertyBean("" + ValoracionSerieVO.ORDENACION_OTRA,
					Messages.getString(
							ValoracionConstants.LABEL_VALORACION_ORDENACION
									+ "6", locale)));
			break;
		case ValoracionConstants.NIVEL_SERIE_SEGUNDO:
			tipos.add(new PropertyBean(""
					+ ValoracionSerieVO.ORDENACION_CRONOLOGICA, Messages
					.getString(ValoracionConstants.LABEL_VALORACION_ORDENACION
							+ "1", locale)));
			tipos.add(new PropertyBean(""
					+ ValoracionSerieVO.ORDENACION_ALFABETICA_ONOMASTICA,
					Messages.getString(
							ValoracionConstants.LABEL_VALORACION_ORDENACION
									+ "2", locale)));
			tipos.add(new PropertyBean(""
					+ ValoracionSerieVO.ORDENACION_ALFABETICA_GEOGRAFICA,
					Messages.getString(
							ValoracionConstants.LABEL_VALORACION_ORDENACION
									+ "3", locale)));
			tipos.add(new PropertyBean(""
					+ ValoracionSerieVO.ORDENACION_NUMERICA, Messages
					.getString(ValoracionConstants.LABEL_VALORACION_ORDENACION
							+ "4", locale)));
			tipos.add(new PropertyBean(""
					+ ValoracionSerieVO.ORDENACION_ALEATORIA, Messages
					.getString(ValoracionConstants.LABEL_VALORACION_ORDENACION
							+ "5", locale)));
			tipos.add(new PropertyBean("" + ValoracionSerieVO.ORDENACION_OTRA,
					Messages.getString(
							ValoracionConstants.LABEL_VALORACION_ORDENACION
									+ "6", locale)));
			break;
		}

		return tipos;
	}

	public List getTecnicasMuestreo() {
		List tecnicas = new ArrayList();
		Locale locale = getServiceClient().getLocale();

		tecnicas.add(new PropertyBean(""
				+ ValoracionSerieVO.TECNICA_MUESTREO_CRONOLOGICA, Messages
				.getString(
						ValoracionConstants.LABEL_VALORACION_TECNICA_MUESTREO
								+ "1", locale)));
		tecnicas.add(new PropertyBean(""
				+ ValoracionSerieVO.TECNICA_MUESTREO_ALFABETICA, Messages
				.getString(
						ValoracionConstants.LABEL_VALORACION_TECNICA_MUESTREO
								+ "2", locale)));
		tecnicas.add(new PropertyBean(""
				+ ValoracionSerieVO.TECNICA_MUESTREO_CUALITATIVA, Messages
				.getString(
						ValoracionConstants.LABEL_VALORACION_TECNICA_MUESTREO
								+ "3", locale)));

		return tecnicas;
	}

	public List getValoresDictamen() {
		List valores = new ArrayList();
		Locale locale = getServiceClient().getLocale();

		valores.add(new PropertyBean(""
				+ ValoracionSerieVO.VALOR_DICTAMEN_CONSERVACION_TOTAL, Messages
				.getString(ValoracionConstants.PREFIX_KEY_VALORES_DICTAMEN
						+ "1", locale)));
		valores.add(new PropertyBean(""
				+ ValoracionSerieVO.VALOR_DICTAMEN_CONSERVACION_PARCIAL,
				Messages.getString(
						ValoracionConstants.PREFIX_KEY_VALORES_DICTAMEN + "2",
						locale)));
		valores.add(new PropertyBean(""
				+ ValoracionSerieVO.VALOR_DICTAMEN_ELIMINACION_TOTAL, Messages
				.getString(ValoracionConstants.PREFIX_KEY_VALORES_DICTAMEN
						+ "3", locale)));
		return valores;
	}

	public List getEstadosEliminacion() {
		List estados = new ArrayList();
		Locale locale = getServiceClient().getLocale();

		estados.add(new PropertyBean(""
				+ ValoracionConstants.ESTADO_ELIMINACION_ABIERTA, Messages
				.getString(
						ValoracionConstants.LABEL_VALORACION_ELIMINACION_ESTADO
								+ "1", locale)));
		estados.add(new PropertyBean(""
				+ ValoracionConstants.ESTADO_ELIMINACION_PENDIENTE_DE_APROBAR,
				Messages.getString(
						ValoracionConstants.LABEL_VALORACION_ELIMINACION_ESTADO
								+ "2", locale)));
		estados.add(new PropertyBean(""
				+ ValoracionConstants.ESTADO_ELIMINACION_RECHAZADA, Messages
				.getString(
						ValoracionConstants.LABEL_VALORACION_ELIMINACION_ESTADO
								+ "3", locale)));
		estados.add(new PropertyBean(""
				+ ValoracionConstants.ESTADO_ELIMINACION_APROBADA, Messages
				.getString(
						ValoracionConstants.LABEL_VALORACION_ELIMINACION_ESTADO
								+ "4", locale)));
		estados.add(new PropertyBean(""
				+ ValoracionConstants.ESTADO_ELIMINACION_PENDIENTE_DESTRUCCION,
				Messages.getString(
						ValoracionConstants.LABEL_VALORACION_ELIMINACION_ESTADO
								+ "5", locale)));
		estados.add(new PropertyBean(""
				+ ValoracionConstants.ESTADO_ELIMINACION_DESTRUCCION_REALIZADA,
				Messages.getString(
						ValoracionConstants.LABEL_VALORACION_ELIMINACION_ESTADO
								+ "6", locale)));
		estados.add(new PropertyBean(
				""
						+ ValoracionConstants.ESTADO_ELIMINACION_PENDIENTE_FINALIZACION,
				Messages.getString(
						ValoracionConstants.LABEL_VALORACION_ELIMINACION_ESTADO
								+ "7", locale)));
		estados.add(new PropertyBean(""
				+ ValoracionConstants.ESTADO_ELIMINACION_FINALIZADA, Messages
				.getString(
						ValoracionConstants.LABEL_VALORACION_ELIMINACION_ESTADO
								+ "8", locale)));

		return estados;
	}

	/**
	 * Solicita la validacion de una valoración
	 * 
	 * @param idValoracion
	 *            Identificador de valoración
	 * @throws ValoracionActionNotAllowedException
	 *             Caso de que la validación de la valoración no esté permitida
	 */
	public void solicitarValidacionValoracion(String idValoracion)
			throws ValoracionActionNotAllowedException {
		// Creamos el evento de Loggin de auditoria con sus datos asociados
		LoggingEvent event = getLogginEvent(ArchivoActions.FONDOS_MODULE_SOLICITUD_VALIDACION_VALORACION);
		DataLoggingEvent logData = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_VALORACION, idValoracion);
		Locale locale = getServiceClient().getLocale();

		ValoracionSerieVO valoracion = valoracionDBEntity
				.getValoracion(idValoracion);

		// Comprobamos si se puede realizar la validacion
		getConditionChecker().checkOnSolicitarValidacion(valoracion);

		valoracion.setEstado(ValoracionSerieVO.ESTADO_PENDIENTE_DE_VALIDAR);
		valoracion.setFechaEstado(DBUtils.getFechaHoraActual());
		iniciarTransaccion();

		logData.addDetalle(locale, ArchivoDetails.VALORACION_COD_VALORACION,
				valoracion.getTitulo());
		// logData.addDetalle(ArchivoDetails.VALORACION_COD_SERIE,
		// valoracion.getTituloSerie() );
		valoracionDBEntity.updateValoracionSerie(valoracion);
		commit();
	}

	/**
	 * Realiza la autorizacion de una validación de valoración
	 * 
	 * @param id
	 *            Identificador de la valoracion a autorizar su validacio
	 * @throws ValoracionActionNotAllowedException
	 *             Caso de que la autorización de la validación de la valoración
	 *             no esté permitida
	 */
	public void autorizarValidacionValoracion(String id)
			throws ValoracionActionNotAllowedException {
		// Creamos el evento de Loggin de auditoria con sus datos asociados
		LoggingEvent event = getLogginEvent(ArchivoActions.FONDOS_MODULE_AUTORIZACION_VALIDACION_VALORACION);
		DataLoggingEvent logData = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_VALORACION, id);
		Locale locale = getServiceClient().getLocale();

		this.check(FondosSecurityManager.AUTORIZAR_VALIDACION_ACTION);

		ValoracionSerieVO valoracion = valoracionDBEntity.getValoracion(id);
		valoracion.setEstado(ValoracionSerieVO.ESTADO_PENDIENTE_DE_VALIDAR);

		getConditionChecker().checkOnAutorizarValidacion(valoracion);

		iniciarTransaccion();

		int nSecuencia = nSecValoracion.incrementarNumeroSec();

		valoracion.setEstado(ValoracionSerieVO.ESTADO_VALIDADA);
		valoracion.setFechaEstado(DBUtils.getFechaHoraActual());
		valoracion.setNumRegistro(nSecuencia);
		valoracion.setFechaValidacion(DBUtils.getFechaHoraActual());

		logData.addDetalle(locale, ArchivoDetails.VALORACION_COD_VALORACION,
				valoracion.getTitulo());
		// logData.addDetalle(ArchivoDetails.VALORACION_COD_SERIE,
		// valoracion.getTituloSerie() );

		valoracionDBEntity.updateValoracionSerie(valoracion);

		commit();
	}

	/**
	 * Realiza el rechazi de una validación de valoración
	 * 
	 * @param id
	 *            Identificador de la valoracion a rechazar su validación
	 */
	// public void rechazarValidacionValoracion(String id,String motivo) throws
	// ValoracionActionNotAllowedException {
	public void rechazarValidacionValoracion(String id, String motivo,
			SerieVO serieVO) throws ValoracionActionNotAllowedException {
		this.check(FondosSecurityManager.RECHAZAR_VALIDACION_ACTION);

		ValoracionSerieVO valoracion = valoracionDBEntity.getValoracion(id);

		// Creamos el evento de Loggin de auditoria con sus datos asociados
		LoggingEvent event = getLogginEvent(ArchivoActions.FONDOS_MODULE_RECHAZO_VALIDACION_VALORACION);
		DataLoggingEvent logData = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_VALORACION, id);

		getConditionChecker().checkOnRechazarValidacion(valoracion);

		iniciarTransaccion();

		valoracion.setEstado(ValoracionSerieVO.ESTADO_VALIDACION_RECHAZADA);
		valoracion.setFechaEstado(DBUtils.getFechaHoraActual());
		valoracion.setMotivoRechazo(motivo);

		// Nuevo
		ValoracionSerie valoracionSerie = new ValoracionSerie(valoracion,
				serieVO, getServiceClient());
		valoracion.setInfoSerie(valoracionSerie.toString());
		//

		Locale locale = getServiceClient().getLocale();
		logData.addDetalle(locale, ArchivoDetails.VALORACION_COD_VALORACION,
				valoracion.getTitulo());
		// logData.addDetalle(ArchivoDetails.VALORACION_COD_SERIE,
		// valoracion.getTituloSerie());
		logData.addDetalle(locale,
				ArchivoDetails.VALORACION_RECHAZO_VALIDACION, motivo);

		valoracionDBEntity.updateValoracionSerie(valoracion);

		commit();

	}

	/**
	 * Realiza la comprobacion de seguridad para la accion indicada de este
	 * módulo
	 * 
	 * @param action
	 *            Action que se desea comprobar
	 */
	protected void check(ActionObject action) {
		getSecurityManager().check(action, getServiceClient());
	}

	/**
	 * Metodo para la creacion de un evento específico para el modulo de
	 * valoracion
	 */
	public LoggingEvent getLogginEvent(int action) {
		LoggingEvent le = new LoggingEvent(ArchivoModules.FONDOS_MODULE,
				action, getServiceClient(), false);

		// Lo añadimos a la pila
		getLogger().add(le);

		return le;
	}

	public ValoracionSerieVO iniciarValoracionSerie(SerieVO serie)
			throws ActionNotAllowedException {
		LoggingEvent event = getLogginEvent(ArchivoActions.FONDOS_MODULE_ALTA_VALORACION);
		// DataLoggingEvent logData =
		// event.getDataLoggingEvent(ArchivoObjects.OBJECT_LISTA_VALORACIONES,
		// null);
		DataLoggingEvent logData = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_VALORACION, null);

		getConditionChecker().checkOnCreate(serie);
		ValoracionSerieVO valoracion = new ValoracionSerieVO();
		valoracion.setIdSerie(serie.getId());
		valoracion.setFechaEstado(DBUtils.getFechaHoraActual());
		valoracion.setEstado(ValoracionSerieVO.ESTADO_ABIERTA);
		valoracion.setIdUsuarioGestor(serie.getIdusrgestor());

		// heredar la información de la valoración dictaminada de la serie (si
		// existe)
		heredarInfoValoracionDictaminada(serie.getId(), valoracion);

		iniciarTransaccion();

		int nVersion = nVersionDBEntity.incrementarNumeroVersion(serie.getId());
		valoracion.setTitulo(new StringBuffer(serie.getCodigo()).append("v")
				.append(nVersion).toString());
		valoracionDBEntity.insertValoracion(valoracion);

		// cambiar el id de la validacion en los plazos heredados y insertarlos
		// en la BD
		if (valoracion.getListaPlazos() != null) {
			for (Iterator it = valoracion.getListaPlazos().iterator(); it
					.hasNext();) {
				PlazoValoracionVO plazo = (PlazoValoracionVO) it.next();
				plazo.setIdValSerie(valoracion.getId());
				plazosDBEntity.insertPlazoValoracion(plazo);
			}
		}

		Locale locale = getServiceClient().getLocale();
		logData.addDetalle(locale, ArchivoDetails.VALORACION_COD_VALORACION,
				valoracion.getTitulo());
		logData.addDetalle(locale, ArchivoDetails.VALORACION_COD_SERIE,
				serie.getCodigo());
		logData.setIdObject(valoracion.getId());

		commit();

		return valoracion;
	}

	/**
	 * Si la serie de la que se inicia la valoracion. tiene una valoracion
	 * dictaminada La nueva valoracion tiene que heredar determinados campos de
	 * la anterior.
	 */
	private void heredarInfoValoracionDictaminada(String idSerie,
			ValoracionSerieVO valoracion) {
		// ValoracionSerieVO vo=getValoracionDictaminada(idSerie);
		int[] estados = new int[] {
				ValoracionSerieVO.ESTADO_EVALUACION_RECHAZADA,
				ValoracionSerieVO.ESTADO_DICTAMINADA,
				ValoracionSerieVO.ESTADO_DICTAMEN_RECHAZADO };
		ValoracionSerieVO vo = valoracionDBEntity.getValoracionMasReciente(
				valoracion.getIdSerie(), estados);
		if (vo == null)
			return; // no hay val dictaminada -> no hay herencia

		// Obtenemos la lista de plazos de dicha valoracion si es que tiene
		// alguno
		vo.setListaPlazos(plazosDBEntity.getPlazosValoracion(vo.getId()));

		// Copiamos todos los datos de la valoracion mas reciente a la nueva
		// valoracion creada.
		valoracion.setOrdenacionSerie1(vo.getOrdenacionSerie1());
		valoracion.setOrdenacionSerie2(vo.getOrdenacionSerie2());
		valoracion.setOrdenacionSerie1(vo.getOrdenacionSerie1());
		valoracion.setSeriesPrecedentes(vo.getSeriesPrecedentes());
		valoracion.setSeriesRelacionadas(vo.getSeriesRelacionadas());
		valoracion
				.setDocumentosRecopilatorios(vo.getDocumentosRecopilatorios());
		valoracion.setTipoValorAdministrativo(vo.getTipoValorAdministrativo());
		valoracion.setAnosVigenciaAdministrativa(vo
				.getAnosVigenciaAdministrativa());
		valoracion.setValorAdministrativo(vo.getValorAdministrativo());
		valoracion.setTipoValorLegal(vo.getTipoValorLegal());
		valoracion.setAnosVigenciaLegal(vo.getAnosVigenciaLegal());
		valoracion.setValorLegal(vo.getValorLegal());
		valoracion.setTipoValorInformativo(vo.getTipoValorInformativo());
		valoracion.setValorInformativo(vo.getValorInformativo());
		valoracion.setTecnicaMuestreo(vo.getTecnicaMuestreo());
		int nSecuencia = nSecValoracion.incrementarNumeroSec();
		valoracion.setNumRegistro(nSecuencia);
		valoracion.setValorDictamen(vo.getValorDictamen());
		valoracion.setValorDictamenValue(vo.getValorDictamenValue());
		valoracion.setValorCientifico(vo.getValorCientifico());
		valoracion.setTipoValorCientifico(vo.getTipoValorCientifico());
		valoracion.setValorTecnologico(vo.getValorTecnologico());
		valoracion.setTipoValorTecnologico(vo.getTipoValorTecnologico());
		valoracion.setValorCultural(vo.getValorCultural());
		valoracion.setTipoValorCultural(vo.getTipoValorCultural());
		valoracion.setTipoRegimenAcceso(vo.getTipoRegimenAcceso());
		valoracion.setRegimenAcceso(vo.getRegimenAcceso());
		valoracion.setAnosRegimenAcceso(vo.getAnosRegimenAcceso());
		valoracion.setTipoRegimenAccesoTemporal(vo
				.getTipoRegimenAccesoTemporal());
		valoracion.setListaPlazos(vo.getListaPlazos());

	}

	// /**
	// * Crea una nueva valoracion para una serie
	// * @param valoracion Datos de la valoracion a crear
	// * @return Valoracion almancenada con sus ID generado
	// */
	// public ValoracionSerieVO crearValoracion(ValoracionSerieVO valoracion)
	// throws ValoracionActionNotAllowedException {
	//
	// LoggingEvent event =
	// getLogginEvent(ArchivoActions.FONDOS_MODULE_ALTA_VALORACION);
	// GestionSeriesBI gs = ServiceRepository.getInstance( getServiceSession()
	// ).lookupGestionSeriesBI();
	// SerieVO serie = gs.getSerie( valoracion.getIdSerie() );
	//
	// getConditionChecker().checkOnCreate(serie);
	//
	// valoracion.setFechaEstado( DBUtils.getFechaActual() );
	// valoracion.setEstado( ValoracionConstants.ESTADO_ABIERTA );
	// valoracion.setIdUsuarioGestor(serie.getIdusrgestor());
	// valoracionDBEntity.insertValoracion(valoracion);
	//
	// event.getDataLoggingEvent(ArchivoObjects.OBJECT_VALORACION,
	// valoracion.getId());
	//
	// return valoracion;
	// }

	public ValoracionSerieVO actualizarValoracion(ValoracionSerieVO valoracion)
			throws ValoracionActionNotAllowedException {
		LoggingEvent event = getLogginEvent(ArchivoActions.FONDOS_MODULE_ACTUALIZAR_VALORACION);
		DataLoggingEvent logData = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_VALORACION, valoracion.getId());

		getConditionChecker().checkOnActualizar(valoracion);

		// valoracion.setEstado(ValoracionSerieVO.ESTADO_ABIERTA);
		// valoracion.setFechaEstado( DBUtils.getFechaActual() );
		valoracionDBEntity.updateValoracionSerie(valoracion);

		Locale locale = getServiceClient().getLocale();
		logData.addDetalle(locale, ArchivoDetails.VALORACION_COD_VALORACION,
				valoracion.getTitulo());
		// logData.addDetalle(ArchivoDetails.VALORACION_COD_SERIE,
		// valoracion.getTituloSerie());

		return valoracion;
	}

	public void eliminarValoracion(String ids[])
			throws ValoracionActionNotAllowedException {
		LoggingEvent le = getLogginEvent(ArchivoActions.FONDOS_MODULE_BAJA_VALORACION);
		DataLoggingEvent logData = le.getDataLoggingEvent(
				ArchivoObjects.OBJECT_LISTA_VALORACIONES, null);
		Locale locale = getServiceClient().getLocale();

		// comprobar sin son validaciones borrables
		getConditionChecker().checkOnEliminar(ids);

		iniciarTransaccion();
		for (int i = 0; i < ids.length; i++) {
			ValoracionSerieVO valoracion = valoracionDBEntity
					.getValoracion(ids[i]);
			logData.addDetalle(locale,
					ArchivoDetails.VALORACION_COD_VALORACION,
					valoracion.getTitulo());
			logData.addDetalle(locale, ArchivoDetails.VALORACION_COD_SERIE,
					valoracion.getTituloSerie());
			valoracionDBEntity.deleteValoracion(ids[i]);

			// Decrementar contador de valoraciones. No es necesaria ninguna
			// comprobación,
			// porque sólo se puede eliminar una valoración abierta, que siempre
			// será
			// la última versión posible
			nVersionDBEntity.decrementarNumeroVersion(valoracion.getIdSerie());
			plazosDBEntity.deletePlazosValoracion(valoracion.getId());
		}

		commit();
	}

	/**
	 * Obtiene un listado de las valoraciones dadas por su identificador de bd.
	 * 
	 * @param ids
	 *            Lsitado de identificadores de las valoraciones que deseamos
	 *            recuperar.
	 * @return Valoraciones deseadas
	 */
	public List getValoraciones(String[] ids) {
		return valoracionDBEntity.getValoraciones(ids);
	}

	/**
	 * Obtiene un listado de las valoraciones de una serie dada por su
	 * identificador y cuyo estado de valoracion se encuentra en unos de los
	 * pasados, o todas las valoraciones de la series en caso de no venir
	 * definidos los estados.
	 * 
	 * @param codigoSerie
	 *            Serie de la que deseamos obtener las valoraciones.
	 * @param estados
	 *            Listado de los estado que deben tener las valoraciones o null
	 *            si deseamos obtener todas
	 * @return Listado de valoraciones de la serie
	 */
	// public List getValoracionesSerie(String codigoSerie,int[] estados) {
	// return valoracionDBEntity.getValoracionesSerie(codigoSerie, estados);
	// }
	/**
	 * Realiza la autorizacion de una evaluación de valoración
	 * 
	 * @param id
	 *            Identificador de la valoracion a autorizar su evaluación
	 */
	public void autorizarEvaluacionValoracion(String id, Date fechaEvaluacion)
			throws ValoracionActionNotAllowedException {
		this.check(FondosSecurityManager.AUTORIZAR_EVALUACION_ACTION);

		ValoracionSerieVO valoracion = valoracionDBEntity.getValoracion(id);

		// Creamos el evento de Loggin de auditoria con sus datos asociados
		LoggingEvent event = getLogginEvent(ArchivoActions.FONDOS_MODULE_AUTORIZACION_EVALUACION_VALORACION);
		DataLoggingEvent logData = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_VALORACION, id);

		getConditionChecker().checkOnAutorizarEvaluacion(valoracion);

		iniciarTransaccion();

		valoracion.setEstado(ValoracionSerieVO.ESTADO_EVALUADA);
		valoracion.setFechaEstado(DBUtils.getFechaHoraActual());
		valoracion.setFechaEvaluacion(fechaEvaluacion);

		Locale locale = getServiceClient().getLocale();
		logData.addDetalle(locale, ArchivoDetails.VALORACION_COD_VALORACION,
				valoracion.getTitulo());
		// logData.addDetalle(ArchivoDetails.VALORACION_COD_SERIE,
		// valoracion.getTituloSerie());

		valoracionDBEntity.updateValoracionSerie(valoracion);

		commit();
	}

	/**
	 * Realiza el rechazo de una evaluación de valoración
	 * 
	 * @param id
	 *            Identificador de la valoracion a rechazar su evaluación
	 */
	// public void rechazarEvaluacionValoracion(String id,String motivo,Date
	// fechaEvaluacion) throws ValoracionActionNotAllowedException {
	public void rechazarEvaluacionValoracion(String id, String motivo,
			Date fechaEvaluacion, SerieVO serieVO)
			throws ValoracionActionNotAllowedException {
		this.check(FondosSecurityManager.RECHAZAR_VALIDACION_ACTION);

		ValoracionSerieVO valoracion = valoracionDBEntity.getValoracion(id);

		// Creamos el evento de Loggin de auditoria con sus datos asociados
		LoggingEvent event = getLogginEvent(ArchivoActions.FONDOS_MODULE_RECHAZO_EVALUACION_VALORACION);
		DataLoggingEvent logData = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_VALORACION, id);

		getConditionChecker().checkOnRechazarEvaluacion(valoracion);

		iniciarTransaccion();

		valoracion.setEstado(ValoracionSerieVO.ESTADO_EVALUACION_RECHAZADA);
		valoracion.setFechaEstado(DBUtils.getFechaHoraActual());
		valoracion.setFechaEvaluacion(fechaEvaluacion);
		valoracion.setMotivoRechazo(motivo);

		Locale locale = getServiceClient().getLocale();
		logData.addDetalle(locale, ArchivoDetails.VALORACION_COD_VALORACION,
				valoracion.getTitulo());
		// logData.addDetalle(ArchivoDetails.VALORACION_COD_SERIE,
		// valoracion.getTituloSerie());
		logData.addDetalle(locale,
				ArchivoDetails.VALORACION_RECHAZO_EVALUACION, motivo);

		// Nuevo => aquí no porque no se ha terminado el ciclo de vida de la
		// valoración
		// ValoracionSerie valoracionSerie = new ValoracionSerie(valoracion,
		// serieVO, getServiceClient());
		// valoracion.setInfoSerie(valoracionSerie.toString());
		//

		valoracionDBEntity.updateValoracionSerie(valoracion);

		commit();
	}

	/**
	 * Realiza la autorizacion de un dictamen de valoración
	 * 
	 * @param id
	 *            Identificador de la valoracion a autorizar su dictamen
	 * @param boletin
	 *            Informacion del boletin asociado a la valoracion
	 */
	public void autorizarDictamenValoracion(String id, BoletinInfoVO boletin)
			throws ValoracionActionNotAllowedException {
		this.check(FondosSecurityManager.DICTAMINAR_ACTION);

		ValoracionSerieVO valoracion = valoracionDBEntity.getValoracion(id);

		// Creamos el evento de Loggin de auditoria con sus datos asociados
		LoggingEvent event = getLogginEvent(ArchivoActions.FONDOS_MODULE_DICTAMEN_VALORACION);
		DataLoggingEvent logData = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_VALORACION, id);

		getConditionChecker().checkOnAutorizarDictamen(valoracion);

		// valoracion.setEstado( ValoracionSerieVO.ESTADO_DICTAMINADA );
		// valoracion.setFechaEstado( boletin.getFechaDictamen() );
		valoracion.setFechaEstado(DBUtils.getFechaHoraActual());
		valoracion.setFechaDictamen(boletin.getFechaDictamen());
		valoracion.setBoletinDictamen(boletin.getBoletinDictamen());
		valoracion.setFechaBoletinDictamen(boletin.getFechaBoletinDictamen());
		valoracion.setFechaResolucionDictamen(boletin
				.getFechaResolucionDictamen());

		iniciarTransaccion();

		// int[] estados = {ValoracionSerieVO.ESTADO_DICTAMINADA };
		// //Si existe pasamos a histórica la última dictaminada
		// List valoracionesDictaminadas =
		// getValoracionesSerie(valoracion.getIdSerie(), estados );
		// if (valoracionesDictaminadas.size()>0) {
		// ValoracionSerieVO valoracionDictaminada =
		// (ValoracionSerieVO)valoracionesDictaminadas.get(0);
		//
		// valoracionDictaminada.setEstado(
		// ValoracionSerieVO.ESTADO_DICTAMINADA_HISTORICA );
		// valoracionDBEntity.updateValoracionSerie(valoracionDictaminada);
		// }
		// Actualizamos la valoracion
		Locale locale = getServiceClient().getLocale();
		logData.addDetalle(locale, ArchivoDetails.VALORACION_COD_VALORACION,
				valoracion.getTitulo());
		// logData.addDetalle(ArchivoDetails.VALORACION_COD_SERIE,
		// valoracion.getTituloSerie());
		logData.addDetalle(locale, ArchivoDetails.VALORACION_FECHA_DICTAMEN,
				DateUtils.formatDate(boletin.getFechaDictamen()));
		logData.addDetalle(locale, ArchivoDetails.VALORACION_BOLETIN_DICTAMEN,
				boletin.getBoletinDictamen());
		logData.addDetalle(locale,
				ArchivoDetails.VALORACION_FECHA_BOLETIN_DICTAMEN,
				DateUtils.formatDate(boletin.getFechaBoletinDictamen()));
		logData.addDetalle(locale,
				ArchivoDetails.VALORACION_FECHA_RESOLUCION_DICTAMEN,
				DateUtils.formatDate(boletin.getFechaResolucionDictamen()));

		valoracionDBEntity.updateValoracionSerie(valoracion);

		commit();
	}

	/**
	 * Código Anterior public void autorizarDictamenValoracion(String
	 * id,BoletinInfoVO boletin) throws ValoracionActionNotAllowedException {
	 * this.check(FondosSecurityManager.DICTAMINAR_ACTION);
	 * 
	 * ValoracionSerieVO valoracion = valoracionDBEntity.getValoracion(id);
	 * 
	 * //Creamos el evento de Loggin de auditoria con sus datos asociados
	 * LoggingEvent event =
	 * getLogginEvent(ArchivoActions.FONDOS_MODULE_DICTAMEN_VALORACION);
	 * DataLoggingEvent logData =
	 * event.getDataLoggingEvent(ArchivoObjects.OBJECT_VALORACION,id);
	 * 
	 * getConditionChecker().checkOnAutorizarDictamen(valoracion);
	 * 
	 * //valoracion.setEstado( ValoracionSerieVO.ESTADO_DICTAMINADA );
	 * valoracion.setFechaEstado( boletin.getFechaDictamen() );
	 * valoracion.setFechaDictamen( boletin.getFechaDictamen() );
	 * valoracion.setBoletinDictamen( boletin.getBoletinDictamen() );
	 * valoracion.setFechaBoletinDictamen( boletin.getFechaBoletinDictamen() );
	 * valoracion.setFechaResolucionDictamen(
	 * boletin.getFechaResolucionDictamen());
	 * 
	 * iniciarTransaccion();
	 * 
	 * // int[] estados = {ValoracionSerieVO.ESTADO_DICTAMINADA }; // //Si
	 * existe pasamos a histórica la última dictaminada // List
	 * valoracionesDictaminadas = getValoracionesSerie(valoracion.getIdSerie(),
	 * estados ); // if (valoracionesDictaminadas.size()>0) { //
	 * ValoracionSerieVO valoracionDictaminada =
	 * (ValoracionSerieVO)valoracionesDictaminadas.get(0); // //
	 * valoracionDictaminada.setEstado(
	 * ValoracionSerieVO.ESTADO_DICTAMINADA_HISTORICA ); //
	 * valoracionDBEntity.updateValoracionSerie(valoracionDictaminada); // }
	 * //Actualizamos la valoracion
	 * logData.addDetalle(ArchivoDetails.VALORACION_COD_VALORACION ,
	 * valoracion.getTitulo()); //
	 * logData.addDetalle(ArchivoDetails.VALORACION_COD_SERIE,
	 * valoracion.getTituloSerie());
	 * logData.addDetalle(ArchivoDetails.VALORACION_FECHA_DICTAMEN,
	 * DateUtils.formatDate(boletin.getFechaDictamen()));
	 * logData.addDetalle(ArchivoDetails.VALORACION_BOLETIN_DICTAMEN,
	 * boletin.getBoletinDictamen());
	 * logData.addDetalle(ArchivoDetails.VALORACION_FECHA_BOLETIN_DICTAMEN,
	 * DateUtils.formatDate(boletin.getFechaBoletinDictamen()));
	 * logData.addDetalle(ArchivoDetails.VALORACION_FECHA_RESOLUCION_DICTAMEN,
	 * DateUtils.formatDate(boletin.getFechaResolucionDictamen()));
	 * 
	 * valoracionDBEntity.updateValoracionSerie(valoracion);
	 * 
	 * commit(); }
	 */

	public void cerrarDictamenFavorableValoracion(String id, SerieVO serieVO)
			throws ValoracionActionNotAllowedException {
		this.check(FondosSecurityManager.DICTAMINAR_ACTION);

		ValoracionSerieVO valoracion = valoracionDBEntity.getValoracion(id);

		// Creamos el evento de Loggin de auditoria con sus datos asociados
		LoggingEvent event = getLogginEvent(ArchivoActions.FONDOS_MODULE_CERRAR_DICTAMEN_VALORACION);
		DataLoggingEvent logData = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_VALORACION, valoracion.getId());

		getConditionChecker().checkOnCerrarDictamenFavorable(valoracion);

		valoracion.setEstado(ValoracionSerieVO.ESTADO_DICTAMINADA);

		// Nuevo: Rellenar la nueva información que se necesita para almacenar
		// el estado de la serie
		// en el momento de cerrar el dictamen de la serie

		// valoracion.setInfoSerie (new ValoracionSerie(valoracion,serieVO));

		iniciarTransaccion();

		int[] estados = { ValoracionSerieVO.ESTADO_DICTAMINADA };
		// Si existe pasamos a histórica la última dictaminada
		// List valoracionesDictaminadas =
		// getValoracionesSerie(valoracion.getIdSerie(), estados );
		// if (valoracionesDictaminadas.size()>0) {
		// ValoracionSerieVO valoracionDictaminada =
		// (ValoracionSerieVO)valoracionesDictaminadas.get(0);
		//
		// valoracionDictaminada.setEstado(
		// ValoracionSerieVO.ESTADO_DICTAMINADA_HISTORICA );
		// valoracionDBEntity.updateValoracionSerie(valoracionDictaminada);
		// }
		ValoracionSerieVO valoracionDictaminada = valoracionDBEntity
				.getValoracionMasReciente(valoracion.getIdSerie(), estados);
		if (valoracionDictaminada != null) {
			valoracionDictaminada
					.setEstado(ValoracionSerieVO.ESTADO_DICTAMINADA_HISTORICA);
			valoracionDBEntity.updateValoracionSerie(valoracionDictaminada);
		}

		// Actualizamos la valoracion
		Locale locale = getServiceClient().getLocale();
		logData.addDetalle(locale, ArchivoDetails.VALORACION_COD_VALORACION,
				valoracion.getTitulo());
		// logData.addDetalle(ArchivoDetails.VALORACION_COD_SERIE,
		// valoracion.getTituloSerie());

		// Nuevo
		// SerieVO serieVO = valoracionPO.getSerie();
		ValoracionSerie valoracionSerie = new ValoracionSerie(valoracion,
				serieVO, getServiceClient());
		valoracion.setInfoSerie(valoracionSerie.toString());

		valoracionDBEntity.updateValoracionSerie(valoracion);

		commit();
	}

	/**
	 * Código anterior
	 * 
	 * Realiza el cierre del dictamen favorable de la valoración.
	 * 
	 * @param id
	 *            Identificador de la valoracion a dictaminar.
	 */
	/*
	 * public void cerrarDictamenFavorableValoracion(String id) throws
	 * ValoracionActionNotAllowedException {
	 * this.check(FondosSecurityManager.DICTAMINAR_ACTION);
	 * 
	 * ValoracionSerieVO valoracion = valoracionDBEntity.getValoracion(id);
	 * 
	 * //Creamos el evento de Loggin de auditoria con sus datos asociados
	 * LoggingEvent event =
	 * getLogginEvent(ArchivoActions.FONDOS_MODULE_CERRAR_DICTAMEN_VALORACION);
	 * DataLoggingEvent logData =
	 * event.getDataLoggingEvent(ArchivoObjects.OBJECT_VALORACION,id);
	 * 
	 * getConditionChecker().checkOnCerrarDictamenFavorable(valoracion);
	 * 
	 * valoracion.setEstado( ValoracionSerieVO.ESTADO_DICTAMINADA );
	 * 
	 * iniciarTransaccion();
	 * 
	 * int[] estados = {ValoracionSerieVO.ESTADO_DICTAMINADA }; //Si existe
	 * pasamos a histórica la última dictaminada // List
	 * valoracionesDictaminadas = getValoracionesSerie(valoracion.getIdSerie(),
	 * estados ); // if (valoracionesDictaminadas.size()>0) { //
	 * ValoracionSerieVO valoracionDictaminada =
	 * (ValoracionSerieVO)valoracionesDictaminadas.get(0); // //
	 * valoracionDictaminada.setEstado(
	 * ValoracionSerieVO.ESTADO_DICTAMINADA_HISTORICA ); //
	 * valoracionDBEntity.updateValoracionSerie(valoracionDictaminada); // }
	 * ValoracionSerieVO valoracionDictaminada =
	 * valoracionDBEntity.getValoracionMasReciente(valoracion.getIdSerie(),
	 * estados); if (valoracionDictaminada != null) {
	 * valoracionDictaminada.setEstado(
	 * ValoracionSerieVO.ESTADO_DICTAMINADA_HISTORICA );
	 * valoracionDBEntity.updateValoracionSerie(valoracionDictaminada); }
	 * 
	 * //Actualizamos la valoracion
	 * logData.addDetalle(ArchivoDetails.VALORACION_COD_VALORACION ,
	 * valoracion.getTitulo()); //
	 * logData.addDetalle(ArchivoDetails.VALORACION_COD_SERIE,
	 * valoracion.getTituloSerie());
	 * 
	 * valoracionDBEntity.updateValoracionSerie(valoracion);
	 * 
	 * commit(); }
	 */
	/**
	 * Realiza el rechazo de un dictamen de valoración
	 * 
	 * @param id
	 *            Identificador de la valoracion a rechazar su dictamen
	 * @param fechaDictamen
	 *            Fecha del dictamen
	 */
	// public void rechazarDictamenValoracion(String id,String motivo,Date
	// fechaDictamen) throws ValoracionActionNotAllowedException {
	public void rechazarDictamenValoracion(String id, String motivo,
			Date fechaDictamen, SerieVO serieVO)
			throws ValoracionActionNotAllowedException {
		this.check(FondosSecurityManager.DICTAMINAR_ACTION);

		ValoracionSerieVO valoracion = valoracionDBEntity.getValoracion(id);

		// Creamos el evento de Loggin de auditoria con sus datos asociados
		LoggingEvent event = getLogginEvent(ArchivoActions.FONDOS_MODULE_DICTAMEN_VALORACION);
		DataLoggingEvent logData = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_VALORACION, id);

		getConditionChecker().checkOnRechazarDictamen(valoracion);

		iniciarTransaccion();

		valoracion.setEstado(ValoracionSerieVO.ESTADO_DICTAMEN_RECHAZADO);
		valoracion.setFechaEstado(DBUtils.getFechaHoraActual());
		valoracion.setFechaDictamen(fechaDictamen);
		valoracion.setMotivoRechazo(motivo);

		Locale locale = getServiceClient().getLocale();
		logData.addDetalle(locale, ArchivoDetails.VALORACION_COD_VALORACION,
				valoracion.getTitulo());
		logData.addDetalle(locale, ArchivoDetails.VALORACION_FECHA_DICTAMEN,
				DateUtils.formatDate(fechaDictamen));
		logData.addDetalle(locale,
				ArchivoDetails.VALORACION_MOTIVO_RECHAZO_DICTAMEN, motivo);

		valoracionDBEntity.updateValoracionSerie(valoracion);

		commit();
	}

	/**
	 * Obtiene una valoracion dada a partir de su identificador
	 * 
	 * @param idValoracion
	 *            Indetificador de la valoracion en bd
	 * @return Valoracion asociada al identificador
	 */
	public ValoracionSerieVO getValoracion(String idValoracion) {
		ValoracionSerieVO valoracion = valoracionDBEntity
				.getValoracion(idValoracion);
		if (valoracion != null)
			valoracion.setListaPlazos(plazosDBEntity
					.getPlazosValoracion(valoracion.getId()));
		return valoracion;

	}

	/**
	 * Pone una valoración a disposicion del usuario que solicita su apertura de
	 * manera que unicamente dicho usuario puede realizar acciones sobre la
	 * valoración
	 * 
	 * @param idValoracion
	 *            Identificador de valoración
	 * @return Datos de valoración
	 */
	public ValoracionSerieVO abrirValoracion(String idValoracion) {
		ConditionChecker authorizationHelper = getConditionChecker();
		ValoracionSerieVO valoracion = valoracionDBEntity
				.getValoracion(idValoracion);
		authorizationHelper.configureValoracion(valoracion);
		valoracion.setListaPlazos(plazosDBEntity.getPlazosValoracion(valoracion
				.getId()));
		return valoracion;
	}

	/**
	 * Obtiene la valoración dictaminada de una serie
	 * 
	 * @param idSerie
	 *            Identificador de serie
	 * @return Valoracion dictaminada con fecha de dictamen mas reciente
	 */
	public ValoracionSerieVO getValoracionDictaminada(String idSerie) {
		// List valoracionesDictaminadas =
		// valoracionDBEntity.getValoracionesSerie(idSerie, new int[]
		// {ValoracionSerieVO.ESTADO_DICTAMINADA});
		// ValoracionSerieVO valoracion = null;
		// if (valoracionesDictaminadas != null &&
		// valoracionesDictaminadas.size() > 0) {
		// Collections.sort(valoracionesDictaminadas, new Comparator() {
		// public int compare(Object arg0, Object arg1) {
		// int returnValue = -1;
		// if
		// (((ValoracionSerieVO)arg0).getFechaDictamen().after(((ValoracionSerieVO)arg1).getFechaDictamen()))
		// returnValue = 1;
		// return returnValue;
		// }
		// });
		// valoracion = (ValoracionSerieVO)valoracionesDictaminadas.get(0);
		// }
		ValoracionSerieVO valoracion = valoracionDBEntity
				.getValoracionMasReciente(idSerie,
						new int[] { ValoracionSerieVO.ESTADO_DICTAMINADA });
		if (valoracion != null)
			valoracion.setListaPlazos(plazosDBEntity
					.getPlazosValoracion(valoracion.getId()));

		return valoracion;
	}

	/**
	 * Obtiene la valoración actual de una serie
	 * 
	 * @param idSerie
	 *            Identificador de serie
	 * @return Valoración de la serie {@link ValoracionSerieVO}. Si la serie no
	 *         dispone de valoración actual se devuelve null.
	 */
	public ValoracionSerieVO getValoracionEnCurso(String idSerie) {
		List valoraciones = valoracionDBEntity.getValoracionesSerie(idSerie,
				new int[] { ValoracionSerieVO.ESTADO_ABIERTA,
						ValoracionSerieVO.ESTADO_PENDIENTE_DE_VALIDAR,
						ValoracionSerieVO.ESTADO_VALIDADA,
						ValoracionSerieVO.ESTADO_VALIDACION_RECHAZADA,
						ValoracionSerieVO.ESTADO_EVALUADA });

		ValoracionSerieVO valoracion = null;
		if (valoraciones != null && valoraciones.size() > 0)
			valoracion = (ValoracionSerieVO) valoraciones.get(valoraciones
					.size() - 1);

		return valoracion;
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * common.bi.GestionValoracionBI#getCountValoracionesEnElaboracion(java.
	 * lang.String)
	 */
	public int getCountValoracionesEnElaboracion(String idUser) {
		int[] estados = { ValoracionSerieVO.ESTADO_ABIERTA,
				ValoracionSerieVO.ESTADO_VALIDACION_RECHAZADA,
				ValoracionSerieVO.ESTADO_PENDIENTE_DE_VALIDAR };
		return valoracionDBEntity.getCountValoracionesXGestor(idUser, estados);
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * common.bi.GestionValoracionBI#getValoracionesEnElaboracion(java.lang.
	 * String)
	 */
	public List getValoracionesEnElaboracion(String idUser) {
		int[] estados = { ValoracionSerieVO.ESTADO_ABIERTA,
				ValoracionSerieVO.ESTADO_VALIDACION_RECHAZADA,
				ValoracionSerieVO.ESTADO_PENDIENTE_DE_VALIDAR };
		return valoracionDBEntity.getValoracionesXGestor(idUser, estados);
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * common.bi.GestionValoracionBI#getCountValoracionesAGestionar(java.lang
	 * .String)
	 */
	public int getCountValoracionesAGestionar(String idUser) {
		/*
		 * A lo mejor hay que restringir a aquellas series que sean de uno de
		 * los archivos asociados al usuario
		 */
		int[] estados = { ValoracionSerieVO.ESTADO_PENDIENTE_DE_VALIDAR,
				ValoracionSerieVO.ESTADO_EVALUADA,
				ValoracionSerieVO.ESTADO_VALIDADA };
		return valoracionDBEntity.getCountValoraciones(estados);
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * common.bi.GestionValoracionBI#getValoracionesAGestionar(java.lang.String)
	 */
	public List getValoracionesAGestionar(String idUser) {
		/*
		 * A lo mejor hay que restringir a aquellas series que sean de uno de
		 * los archivos asociados al usuario
		 */
		int[] estados = { ValoracionSerieVO.ESTADO_PENDIENTE_DE_VALIDAR,
				ValoracionSerieVO.ESTADO_EVALUADA,
				ValoracionSerieVO.ESTADO_VALIDADA };
		return valoracionDBEntity.getValoraciones(estados);
	}

	/**
	 * Obtiene las valoraciones que cumplen los filtros de busqueda
	 * 
	 * @param busqueda
	 *            Objeto que contiene los elementos del formulario de búsqueda.
	 * @throws TooManyResultsException
	 *             si el número de resultados es excesivo.
	 */
	public List getValoraciones(BusquedaVO busqueda)
			throws TooManyResultsException {
		return valoracionDBEntity.getValoraciones(busqueda);
	}

	private ConditionChecker getConditionChecker() {
		return new ConditionChecker();
	}

	// CLASE INTERNA
	private class ConditionChecker {
		/**
		 * Comprueba si la valoración puede solicitarse su valoración
		 * 
		 * @param valoracion
		 *            Valoració que deseamos comprobar
		 * @throws ValoracionNotAllowedException
		 *             Si el usuario no puede solicitar la validacion
		 */
		public void checkOnSolicitarValidacion(ValoracionSerieVO valoracion)
				throws ValoracionActionNotAllowedException {
			if ((valoracion.getEstado() != ValoracionSerieVO.ESTADO_ABIERTA)
					&& (valoracion.getEstado() != ValoracionSerieVO.ESTADO_VALIDACION_RECHAZADA))
				throw new ValoracionActionNotAllowedException(
						ArchivoErrorCodes.ERROR_VALORACION_NOSOLICITABLE_VALIDACION_XESTADO);

			if (!valoracion.getIdUsuarioGestor().equals(
					getServiceClient().getId()))
				throw new ValoracionActionNotAllowedException(
						ArchivoErrorCodes.ERROR_VALORACION_NOSOLICITABLE_VALIDACION_XUSUARIO);

			if (valoracion.getTipoValorAdministrativo() == 0)
				throw new ValoracionActionNotAllowedException(
						ArchivoErrorCodes.ERROR_VALORACION_NOSOLICITABLE_VALIDACION_XVALORADM);

			if (valoracion.getTipoValorLegal() == 0)
				throw new ValoracionActionNotAllowedException(
						ArchivoErrorCodes.ERROR_VALORACION_NOSOLICITABLE_VALIDACION_XVALORLEGAL);

			if (valoracion.getTipoRegimenAcceso() == 0)
				throw new ValoracionActionNotAllowedException(
						ArchivoErrorCodes.ERROR_VALORACION_NOSOLICITABLE_VALIDACION_XREGACCESO);

			if (valoracion.getValorDictamen() == 0)
				throw new ValoracionActionNotAllowedException(
						ArchivoErrorCodes.ERROR_VALORACION_NOSOLICITABLE_VALIDACION_XVALORDICTAMEN);
		}

		/**
		 * Comprueba si la validación de la valoración se puede autorizar
		 * 
		 * @param valoracion
		 *            Valoració que deseamos autorizar su validación
		 * @throws ValoracionNotAllowedException
		 *             Si no se puede autorizar la validacion
		 */
		public void checkOnAutorizarValidacion(ValoracionSerieVO valoracion)
				throws ValoracionActionNotAllowedException {
			if (valoracion.getEstado() != ValoracionSerieVO.ESTADO_PENDIENTE_DE_VALIDAR)
				throw new ValoracionActionNotAllowedException(
						ArchivoErrorCodes.ERROR_VALORACION_NOAUTORIZABLE_XESTADO);

			if (!getServiceClient().hasPermission(
					AppPermissions.GESTION_VALORACIONES))
				throw new ValoracionActionNotAllowedException(
						ArchivoErrorCodes.ERROR_VALORACION_SOLO_PERMITIDO_A_GESTOR_VALORACIONES);
		}

		/**
		 * Comprueba si la validación de la valoración se puede rechazar
		 * 
		 * @param valoracion
		 *            Valoració que deseamos rechazar su validación
		 * @throws ValoracionNotAllowedException
		 *             Si el usuario no puede solicitar la validacion
		 */
		public void checkOnRechazarValidacion(ValoracionSerieVO valoracion)
				throws ValoracionActionNotAllowedException {
			if (valoracion.getEstado() != ValoracionSerieVO.ESTADO_PENDIENTE_DE_VALIDAR)
				throw new ValoracionActionNotAllowedException(
						ArchivoErrorCodes.ERROR_VALORACION_NORECHAZABLE_XESTADO);

			if (!getServiceClient().hasPermission(
					AppPermissions.GESTION_VALORACIONES))
				throw new ValoracionActionNotAllowedException(
						ArchivoErrorCodes.ERROR_VALORACION_SOLO_PERMITIDO_A_GESTOR_VALORACIONES);
		}

		/**
		 * Comprueba si las valroaciones seleccionadas pueden ser eliminadas
		 * 
		 * @param ids
		 *            Identificadores de las valoraciones a eliminar
		 * @param service
		 *            Servicio de acceso a valoraciones
		 * @throws ValoracionActionNotAllowedException
		 *             Si no se pueden eliminar las valoraciones
		 */
		public void checkOnEliminar(String[] ids)
				throws ValoracionActionNotAllowedException {
			// boolean borrables = true;

			// Obtenemos las valoraciones
			Collection valoraciones = getValoraciones(ids);
			for (Iterator itValoracion = valoraciones.iterator(); itValoracion
					.hasNext();) {
				ValoracionSerieVO valoracionVO = (ValoracionSerieVO) itValoracion
						.next();
				checkOnEliminar(valoracionVO);
				// if ( valoracionVO.getEstado() ==
				// ValoracionConstants.ESTADO_ABIERTA ||
				// valoracionVO.getEstado() ==
				// ValoracionConstants.ESTADO_VALIDACION_RECHAZADA ) {
				// borrables = true;
				// } else
				// borrables = false;
			}
			//
			// if (!borrables)
			// throw new ValoracionActionNotAllowedException(
			// ArchivoErrorCodes.ERROR_VALORACION_NO_BORRABLE);
		}

		public void checkOnEliminar(ValoracionSerieVO valoracion)
				throws ValoracionActionNotAllowedException {
			if (valoracion.getEstado() != ValoracionSerieVO.ESTADO_ABIERTA
					&& valoracion.getEstado() != ValoracionSerieVO.ESTADO_VALIDACION_RECHAZADA)
				throw new ValoracionActionNotAllowedException(
						ArchivoErrorCodes.ERROR_VALORACION_NO_BORRABLE);
			else if (!valoracion.getIdUsuarioGestor().equals(
					getServiceClient().getId()))
				throw new ValoracionActionNotAllowedException(
						ArchivoErrorCodes.ERROR_VALORACION_SOLO_PERMITIDO_A_GESTOR);
		}

		/**
		 * Comprueba si se puede realizar la creacion de una valoracion dada
		 * para una determinada serie. Comprueba: - El estado de la serie (debe
		 * ser vigente o historica) - La serie no debe tener otra valoracion en
		 * estado 1,2,3,4,5
		 * 
		 * @param valoracion
		 *            Valoracion que se desea crear
		 * @param serie
		 *            Serie a la que se refiere la valoración
		 * @throws ValoracionActionNotAllowedException
		 *             Si no se pueder realizar la creación de la valoracion
		 *             para la serie
		 */
		public void checkOnCreate(SerieVO serie)
				throws ValoracionActionNotAllowedException {
			if (serie.getEstadoserie() != EstadoSerie.VIGENTE
					&& serie.getEstadoserie() != EstadoSerie.HISTORICA)
				throw new ValoracionActionNotAllowedException(
						ArchivoErrorCodes.ERROR_VALORACION_NO_CREABLE_XESTADO);

			int[] estados = { ValoracionSerieVO.ESTADO_ABIERTA,
					ValoracionSerieVO.ESTADO_PENDIENTE_DE_VALIDAR,
					ValoracionSerieVO.ESTADO_VALIDADA,
					ValoracionSerieVO.ESTADO_VALIDACION_RECHAZADA,
					ValoracionSerieVO.ESTADO_EVALUADA };
			if (valoracionDBEntity.getNumValoraciones(serie.getId(), estados) > 0)
				throw new ValoracionActionNotAllowedException(
						ArchivoErrorCodes.ERROR_VALORACION_NO_CREABLE_XESTADO_VALORACION);
			// List valoraciones = getValoracionesSerie( serie.getId(), null);
			// for (Iterator itValoracion = valoraciones.iterator();
			// itValoracion.hasNext();) {
			// ValoracionSerieVO valoracionVO = (ValoracionSerieVO)
			// itValoracion.next();
			//
			// if ( valoracionVO.getEstado() == ValoracionSerieVO.ESTADO_ABIERTA
			// ||
			// valoracionVO.getEstado() ==
			// ValoracionSerieVO.ESTADO_PENDIENTE_DE_VALIDAR ||
			// valoracionVO.getEstado() == ValoracionSerieVO.ESTADO_VALIDADA ||
			// valoracionVO.getEstado() ==
			// ValoracionSerieVO.ESTADO_VALIDACION_RECHAZADA ||
			// valoracionVO.getEstado() == ValoracionSerieVO.ESTADO_EVALUADA )
			// throw new ValoracionActionNotAllowedException(
			// ArchivoErrorCodes.ERROR_VALORACION_NO_CREABLE_XESTADO_VALORACION);
			// }

			if (!serie.getIdusrgestor().equalsIgnoreCase(
					getServiceClient().getId()))
				throw new ValoracionActionNotAllowedException(
						ArchivoErrorCodes.ERROR_VALORACION_NO_CREABLE_XUSUARIO);
		}

		/**
		 * Comprueba si se puede editar de la valoración:
		 * 
		 * @param valoracion
		 *            Valoracion que se desea actualizar
		 * @throws ValoracionActionNotAllowedException
		 *             Si no se pueder realizar la actualizcion
		 */
		public void checkOnEditar(ValoracionSerieVO valoracion)
				throws ValoracionActionNotAllowedException {
			if (valoracion.getEstado() != ValoracionSerieVO.ESTADO_ABIERTA
					&& valoracion.getEstado() != ValoracionSerieVO.ESTADO_VALIDACION_RECHAZADA)
				throw new ValoracionActionNotAllowedException(
						ArchivoErrorCodes.ERROR_VALORACION_NO_MODIFICABLE_XESTADO);
			else if (!valoracion.getIdUsuarioGestor().equals(
					getServiceClient().getId()))
				throw new ValoracionActionNotAllowedException(
						ArchivoErrorCodes.ERROR_VALORACION_SOLO_PERMITIDO_A_GESTOR);
		}

		/**
		 * Comprueba si se puede realizar la actualizacion de la valoración:
		 * 
		 * @param valoracion
		 *            Valoracion que se desea actualizar
		 * @throws ValoracionActionNotAllowedException
		 *             Si no se pueder realizar la actualizcion
		 */
		public void checkOnActualizar(ValoracionSerieVO valoracion)
				throws ValoracionActionNotAllowedException {
			if (valoracion.getEstado() != ValoracionSerieVO.ESTADO_ABIERTA
					&& valoracion.getEstado() != ValoracionSerieVO.ESTADO_VALIDACION_RECHAZADA
					&& !((valoracion.getEstado() == ValoracionSerieVO.ESTADO_EVALUADA) && (valoracion
							.getFechaDictamen() != null)))
				throw new ValoracionActionNotAllowedException(
						ArchivoErrorCodes.ERROR_VALORACION_NO_MODIFICABLE_XESTADO);
			else if (!valoracion.getIdUsuarioGestor().equals(
					getServiceClient().getId()))
				throw new ValoracionActionNotAllowedException(
						ArchivoErrorCodes.ERROR_VALORACION_SOLO_PERMITIDO_A_GESTOR);
		}

		/**
		 * Comprueba si la evaluacion de la valoración se puede autorizar
		 * 
		 * @param valoracion
		 *            Valoració que deseamos autorizar su evaluacion
		 * @throws ValoracionNotAllowedException
		 *             Si no se puede autorizar la evaluación
		 */
		public void checkOnAutorizarEvaluacion(ValoracionSerieVO valoracion)
				throws ValoracionActionNotAllowedException {
			if (valoracion.getEstado() != ValoracionSerieVO.ESTADO_VALIDADA)
				throw new ValoracionActionNotAllowedException(
						ArchivoErrorCodes.ERROR_VALORACION_NOEVALUABLE_XESTADO);

			if (!getServiceClient().hasPermission(
					AppPermissions.GESTION_VALORACIONES))
				throw new ValoracionActionNotAllowedException(
						ArchivoErrorCodes.ERROR_VALORACION_SOLO_PERMITIDO_A_GESTOR_VALORACIONES);
		}

		/**
		 * Comprueba si la evaluación de la valoración se puede rechazar
		 * 
		 * @param valoracion
		 *            Valoració que deseamos rechazar su evaluación
		 * @throws ValoracionNotAllowedException
		 *             Si el usuario no puede solicitar la evaluacion
		 */
		public void checkOnRechazarEvaluacion(ValoracionSerieVO valoracion)
				throws ValoracionActionNotAllowedException {
			if (valoracion.getEstado() != ValoracionSerieVO.ESTADO_VALIDADA)
				throw new ValoracionActionNotAllowedException(
						ArchivoErrorCodes.ERROR_VALORACION_NOEVALUABLE_XESTADO);

			if (!getServiceClient().hasPermission(
					AppPermissions.GESTION_VALORACIONES))
				throw new ValoracionActionNotAllowedException(
						ArchivoErrorCodes.ERROR_VALORACION_SOLO_PERMITIDO_A_GESTOR_VALORACIONES);
		}

		/**
		 * Comprueba si el dictamen de la valoración se puede autorizar
		 * 
		 * @param valoracion
		 *            Valoració que deseamos autorizar su dictamen
		 * @throws ValoracionNotAllowedException
		 *             Si no se puede autorizar el dictamen
		 */
		public void checkOnAutorizarDictamen(ValoracionSerieVO valoracion)
				throws ValoracionActionNotAllowedException {
			if ((valoracion.getEstado() != ValoracionSerieVO.ESTADO_EVALUADA)
					|| (valoracion.getFechaDictamen() != null))
				throw new ValoracionActionNotAllowedException(
						ArchivoErrorCodes.ERROR_VALORACION_NODICTAMINABLE_XESTADO);

			if (!getServiceClient().hasPermission(
					AppPermissions.GESTION_VALORACIONES))
				throw new ValoracionActionNotAllowedException(
						ArchivoErrorCodes.ERROR_VALORACION_SOLO_PERMITIDO_A_GESTOR_VALORACIONES);
		}

		/**
		 * Comprueba si el dictamen de la valoración se puede cerrar
		 * favorablemente
		 * 
		 * @param valoracion
		 *            Valoració que deseamos autorizar su dictamen favorable
		 * @throws ValoracionNotAllowedException
		 *             Si no se puede autorizar el dictamen
		 */
		public void checkOnCerrarDictamenFavorable(ValoracionSerieVO valoracion)
				throws ValoracionActionNotAllowedException {
			if ((valoracion.getEstado() != ValoracionSerieVO.ESTADO_EVALUADA)
					|| (valoracion.getFechaDictamen() == null))
				throw new ValoracionActionNotAllowedException(
						ArchivoErrorCodes.ERROR_VALORACION_NODICTAMINABLE_XESTADO);

			if (!getServiceClient().hasPermission(
					AppPermissions.GESTION_VALORACIONES))
				throw new ValoracionActionNotAllowedException(
						ArchivoErrorCodes.ERROR_VALORACION_SOLO_PERMITIDO_A_GESTOR_VALORACIONES);
		}

		/**
		 * Comprueba si el dictamen de la valoración se puede rechazar
		 * 
		 * @param valoracion
		 *            Valoració que deseamos rechazar su dictamen
		 * @throws ValoracionNotAllowedException
		 *             Si el usuario no puede solicitar el dictamen
		 */
		public void checkOnRechazarDictamen(ValoracionSerieVO valoracion)
				throws ValoracionActionNotAllowedException {
			if (valoracion.getEstado() != ValoracionSerieVO.ESTADO_EVALUADA)
				throw new ValoracionActionNotAllowedException(
						ArchivoErrorCodes.ERROR_VALORACION_NODICTAMINABLE_XESTADO);

			if (!getServiceClient().hasPermission(
					AppPermissions.GESTION_VALORACIONES))
				throw new ValoracionActionNotAllowedException(
						ArchivoErrorCodes.ERROR_VALORACION_SOLO_PERMITIDO_A_GESTOR_VALORACIONES);
		}

		/**
		 * Configura las acciones que pueden ser llevadas a cabo sobre una
		 * valoración
		 * 
		 * @param valoracion
		 *            Valoración a configurar
		 */
		public void configureValoracion(ValoracionSerieVO valoracion) {
			/* Verificacion de si la valoración puede ser editada */
			try {
				checkOnEditar(valoracion);
				valoracion.setPuedeSerEditada(true);
			} catch (ValoracionActionNotAllowedException anae) {
				if (logger.isDebugEnabled())
					logger.debug("Configuracion de valoración :: La edicion de la valoracion no está permitida: "
							+ valoracion.getTitulo());
			}
			/* Verificacion de si la valoración puede ser eliminada */
			try {
				checkOnEliminar(valoracion);
				valoracion.setPuedeSerEliminada(true);
			} catch (ValoracionActionNotAllowedException anae) {
				if (logger.isDebugEnabled())
					logger.debug("Configuracion de valoración :: La eliminacion de la valoracion no está permitida: "
							+ valoracion.getTitulo());
			}
			/*
			 * Verificacion de si se puede realizar la solicitud de validación
			 * de la valoración
			 */
			try {
				checkOnSolicitarValidacion(valoracion);
				valoracion.setPermitidaSolicitudValidacion(true);
			} catch (ValoracionActionNotAllowedException anae) {
				if (logger.isDebugEnabled())
					logger.debug("Configuracion de valoración :: La solicitud de validación de la valoracion no está permitida: "
							+ valoracion.getTitulo());
			}
			/* Verificacion de si se puede validar la valoración */
			try {
				checkOnAutorizarValidacion(valoracion);
				valoracion.setPuedeSerValidada(true);
			} catch (ValoracionActionNotAllowedException anae) {
				if (logger.isDebugEnabled())
					logger.debug("Configuracion de valoración :: La validación de la valoracion no está permitida: "
							+ valoracion.getTitulo());
			}
			/*
			 * Verificacion de si se pueden introducir datos de evaluación para
			 * la valoración
			 */
			try {
				checkOnAutorizarEvaluacion(valoracion);
				valoracion.setPuedeSerEvaluada(true);
			} catch (ValoracionActionNotAllowedException anae) {
				if (logger.isDebugEnabled())
					logger.debug("Configuracion de valoración :: La evaluación de la valoracion no está permitida: "
							+ valoracion.getTitulo());
			}
			/*
			 * Verificacion de si se pueden introducir datos de dictaminación
			 * para la valoración
			 */
			try {
				checkOnAutorizarDictamen(valoracion);
				valoracion.setPuedeSerDictaminada(true);
			} catch (ValoracionActionNotAllowedException anae) {
				if (logger.isDebugEnabled())
					logger.debug("Configuracion de valoración :: El dictamen de la valoracion no está permitida: "
							+ valoracion.getTitulo());
			}
			/*
			 * Verificacion de si se pueden introducir datos de dictaminación
			 * para la valoración
			 */
			try {
				checkOnCerrarDictamenFavorable(valoracion);
				valoracion.setDictamenPuedeSerCerrado(true);
			} catch (ValoracionActionNotAllowedException anae) {
				if (logger.isDebugEnabled())
					logger.debug("Configuracion de valoración :: El cierre del dictamen favorable de la valoracion no está permitido: "
							+ valoracion.getTitulo());
			}
		}

	}

	/**
	 * Obtiene la lista de boletines oficiales.
	 * 
	 * @return Lista de boletines oficiales.
	 */
	public List getBoletinesOficiales() {
		String idTblVld = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionFondos()
				.getTablaValidacionBoletinesOficiales();

		GestionDescripcionBI descripcionBI = getServiceRepository()
				.lookupGestionDescripcionBI();

		return descripcionBI.getValoresValidacion(idTblVld);
	}

	/**
	 * Obtiene las series con un número mínimo de unidades documentales
	 * seleccionables.
	 * 
	 * @param idFondo
	 *            Identificador del fondo.
	 * @param minNumUdocs
	 *            Número mínimo de unidades documentales.
	 * @param pageInfo
	 *            Información de la paginación.
	 * @return Series seleccionables ({@link SerieSeleccionableVO}).
	 * @throws TooManyResultsException
	 *             si el número de resultados excede el máximo.
	 */
	public List getSeriesSeleccionables(String idFondo, int minNumUdocs,
			PageInfo pageInfo) throws TooManyResultsException {
		return valoracionDBEntity.getSeriesSeleccionables(idFondo, minNumUdocs,
				pageInfo);
	}

	/**
	 * Obtiene los datos de identificación de una serie documental en el momento
	 * de cierre de la valoración
	 * 
	 * @param serie
	 *            Serie del cuadro de clasificación de fondos documentales
	 * @return Información que conforma la identificación de la serie documental
	 */
	public ValoracionSerie getIdentificacionSerieValoracion(String infoSerie,
			SerieVO serie) {
		return new ValoracionSerie(infoSerie, serie, getServiceClient());
	}

	// METODOS EXCLUSIVOS DE PLAZOS DE VALORACION
	public List getPlazosValoracion(String idValoracion) {
		return plazosDBEntity.getPlazosValoracion(idValoracion);
	}

	public void updateDeletePlazosValoracion(String idValoracion,
			List plazosNuevos) {
		// recorrer la lista de plazos, comprobar
		List plazosValoracionDB = getPlazosValoracion(idValoracion); // ordenada
																		// por
																		// orden
		PlazoValoracionVO plazoNuevoVO = null;
		Iterator itDB = plazosValoracionDB.iterator();
		iniciarTransaccion();
		for (Iterator itPlazosNuevos = plazosNuevos.iterator(); itPlazosNuevos
				.hasNext();) {
			plazoNuevoVO = (PlazoValoracionVO) itPlazosNuevos.next();
			// hay nuevos plazos que aun no estan en la BD
			if (!itDB.hasNext()) {
				plazosDBEntity.insertPlazoValoracion(plazoNuevoVO);
				continue;
			}
			PlazoValoracionVO plazoDB = (PlazoValoracionVO) itDB.next();
			// se modifico algun valor de algun campo.
			if (!plazoDB.equals(plazoNuevoVO)) {
				plazosDBEntity.updatePlazoValoracion(plazoNuevoVO);
			}
		}

		// si la valoracion en la BD tenia más plazos -> eliminarlos a partir
		// del ultimo orden
		if (itDB.hasNext()) {
			plazosDBEntity.deletePlazosValoracionFromOrden(idValoracion,
					plazoNuevoVO == null ? 0 : plazoNuevoVO.getOrden() + 1);
		}
		commit();
	}

	public int getCountPlazosEnValoracion(String idValoracion) {
		return plazosDBEntity.getCountPlazosValoracion(idValoracion);
	}

	public List getValoracionesPorPlazos(String idNivelOrigen,
			String idNivelDestino) {
		return plazosDBEntity.getValoracionesPorPlazos(idNivelOrigen,
				idNivelDestino);
	}

	public List getValoracionesPorIdNivelOrigenDestino(String idNivel) {
		return plazosDBEntity.getValoracionesPorIdNivelOrigenDestino(idNivel);
	}
}