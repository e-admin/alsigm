package valoracion.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;

import se.usuarios.AppPermissions;
import solicitudes.db.IDetalleDBEntity;
import valoracion.ValoracionConstants;
import valoracion.db.IEliminacionSerieDBEntity;
import valoracion.db.IEliminacionUDOCConservadaDBEntity;
import valoracion.db.IEliminacionUDOCEliminadaDBEntity;
import valoracion.db.IHistoricoUDOCDBEntity;
import valoracion.db.INSecVersionSelDBEntity;
import valoracion.exceptions.EliminacionActionNotAllowedException;
import valoracion.vos.BusquedaVO;
import valoracion.vos.EliminacionSerieVO;
import valoracion.vos.EliminacionUDocConservadaVO;
import valoracion.vos.HistoricoUDOCVO;
import valoracion.vos.IUnidadDocumentalEliminacionVO;
import valoracion.vos.ResumenUinstEliminacionVO;
import valoracion.vos.UnidadDocumentalEliminacionVO;
import valoracion.vos.UnidadesDocumentalesEliminacionVO;
import valoracion.vos.ValoracionSerieVO;
import auditoria.ArchivoDetails;
import auditoria.ArchivoErrorCodes;
import auditoria.logger.DataLoggingEvent;
import auditoria.logger.LoggingEvent;

import common.bi.GestionEliminacionBI;
import common.bi.GestionValoracionBI;
import common.bi.SerieNoValoradaException;
import common.bi.ServiceBase;
import common.bi.ServiceRepository;
import common.db.DBUtils;
import common.definitions.ArchivoActions;
import common.definitions.ArchivoModules;
import common.definitions.ArchivoObjects;
import common.exceptions.ActionNotAllowedException;
import common.exceptions.TooManyResultsException;
import common.pagination.PageInfo;
import common.security.ActionObject;
import common.security.FondosSecurityManager;
import common.util.ArrayUtils;
import common.util.DateUtils;
import common.util.ListUtils;
import common.vos.IKeyId;

import deposito.db.IUDocEnUiDepositoDbEntity;
import es.archigest.framework.core.vo.PropertyBean;
import fondos.db.IElementoCuadroClasificacionDbEntity;
import fondos.model.ElementoCuadroClasificacion;
import fondos.model.IElementoCuadroClasificacion;
import fondos.vos.SerieVO;
import fondos.vos.TablaTemporalFondosVO;

/**
 * Clase de implementacion de los metodos de gestión para el módulo de
 * eliminación de series
 */
public class GestionEliminacionImpl extends ServiceBase implements
		GestionEliminacionBI {

	private final static Logger logger = Logger
			.getLogger(GestionEliminacionImpl.class);

	private IHistoricoUDOCDBEntity historicoDBEntity = null;
	private IEliminacionSerieDBEntity eliminacionSerieDBEntity = null;
	private IDetalleDBEntity detalleDBEntity = null;
	private IElementoCuadroClasificacionDbEntity elementoCuadroDBEntity = null;
	private INSecVersionSelDBEntity nVersionSelDBEntity = null;
	private IEliminacionUDOCConservadaDBEntity eliminacionUDOCConservadaDBEntity = null;
	private IUDocEnUiDepositoDbEntity uDocEnUiDepositoDbEntity = null;
	private IEliminacionUDOCEliminadaDBEntity eliminacionUDOCEliminadaDBEntity = null;

	public GestionEliminacionImpl(
			IEliminacionSerieDBEntity eliminacionSerieDBEntity,
			IDetalleDBEntity detalleDBEntity,
			IElementoCuadroClasificacionDbEntity elementoCuadroDBEntity,
			IHistoricoUDOCDBEntity historicoDBEntity,
			INSecVersionSelDBEntity nVersionSelDBEntity,
			IEliminacionUDOCConservadaDBEntity eliminacionUDOCConservadaDBEntity,
			IUDocEnUiDepositoDbEntity uDocEnUiDepositoDbEntity,
			IEliminacionUDOCEliminadaDBEntity eliminacionUDOCEliminadaDBEntity) {
		this.eliminacionSerieDBEntity = eliminacionSerieDBEntity;
		this.detalleDBEntity = detalleDBEntity;
		this.elementoCuadroDBEntity = elementoCuadroDBEntity;
		this.historicoDBEntity = historicoDBEntity;
		this.nVersionSelDBEntity = nVersionSelDBEntity;
		this.eliminacionUDOCConservadaDBEntity = eliminacionUDOCConservadaDBEntity;
		this.uDocEnUiDepositoDbEntity = uDocEnUiDepositoDbEntity;
		this.eliminacionUDOCEliminadaDBEntity = eliminacionUDOCEliminadaDBEntity;
	}

	public List getEstadosEliminacion() {
		List estados = new ArrayList();

		estados.add(new PropertyBean(""
				+ ValoracionConstants.ESTADO_ELIMINACION_ABIERTA, "Abierta"));
		estados.add(new PropertyBean(""
				+ ValoracionConstants.ESTADO_ELIMINACION_APROBADA, "Aprobada"));
		estados.add(new PropertyBean(""
				+ ValoracionConstants.ESTADO_ELIMINACION_DESTRUCCION_REALIZADA,
				"Destrucción Realizada"));
		estados.add(new PropertyBean(""
				+ ValoracionConstants.ESTADO_ELIMINACION_FINALIZADA,
				"Finalizada"));
		estados.add(new PropertyBean(""
				+ ValoracionConstants.ESTADO_ELIMINACION_PENDIENTE_DE_APROBAR,
				"Pendiente de Aprobar"));
		estados.add(new PropertyBean(""
				+ ValoracionConstants.ESTADO_ELIMINACION_PENDIENTE_DESTRUCCION,
				"Pendiente de Destrucción"));
		estados.add(new PropertyBean(""
				+ ValoracionConstants.ESTADO_ELIMINACION_RECHAZADA, "Rechazada"));

		return estados;
	}

	/**
	 * Obtiene la eliminacion actual asociada a una valoración de una serie
	 * 
	 * @param idValoracion
	 *            Identificador de la valroacion de la que deseamos obtener la
	 *            eliminacion
	 * @return Datos de la eliminacion
	 */
	public EliminacionSerieVO getEliminacionValoracion(String idValoracion) {
		EliminacionSerieVO eliminacion = null;
		Collection eliminaciones = eliminacionSerieDBEntity
				.getEliminaciones(
						idValoracion,
						new int[] {
								ValoracionConstants.ESTADO_ELIMINACION_ABIERTA,
								ValoracionConstants.ESTADO_ELIMINACION_APROBADA,
								ValoracionConstants.ESTADO_ELIMINACION_DESTRUCCION_REALIZADA,
								ValoracionConstants.ESTADO_ELIMINACION_PENDIENTE_DE_APROBAR,
								ValoracionConstants.ESTADO_ELIMINACION_PENDIENTE_DESTRUCCION });

		// Solo puede haber 1 o ninguna eliminacion en ese estado
		Iterator it = eliminaciones.iterator();
		if (it.hasNext())
			eliminacion = (EliminacionSerieVO) it.next();

		return eliminacion;
	}

	/**
	 * Pone una eliminacion a disposicion del usuario que solicita su apertura
	 * de manera que unicamente dicho usuario puede realizar acciones sobre la
	 * eliminacion
	 * 
	 * @param idEliminacion
	 *            Identificador de eliminacion
	 * @return Datos de eliminacion
	 */
	public EliminacionSerieVO abrirEliminacion(String idEliminacion,
			boolean mostrarResumenUinstalacion) {
		EliminacionSerieVO eliminacion = getEliminacion(idEliminacion);
		getConditionChecker().configureEliminacion(eliminacion);

		if (eliminacion != null && mostrarResumenUinstalacion) {
			addResumenUinstEliminacionVO(eliminacion);
		}

		return eliminacion;
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * common.bi.GestionEliminacionBI#getCountEliminacionesEnElaboracion(java
	 * .lang.String)
	 */
	public int getCountEliminacionesEnElaboracion(String idGestor,
			String[] idsArchivos) {
		if (idsArchivos != null) {

			int[] estados = {
					ValoracionConstants.ESTADO_ELIMINACION_ABIERTA,
					ValoracionConstants.ESTADO_ELIMINACION_APROBADA,
					ValoracionConstants.ESTADO_ELIMINACION_RECHAZADA,
					ValoracionConstants.ESTADO_ELIMINACION_PENDIENTE_DESTRUCCION,
					ValoracionConstants.ESTADO_ELIMINACION_PENDIENTE_DE_APROBAR,
					ValoracionConstants.ESTADO_ELIMINACION_DESTRUCCION_REALIZADA,
					ValoracionConstants.ESTADO_ELIMINACION_PENDIENTE_FINALIZACION
			// ,ValoracionConstants.ESTADO_ELIMINACION_FINALIZADA
			};

			return eliminacionSerieDBEntity.getCountEliminacionesXEstado(
					estados, idsArchivos);
		} else
			return 0;
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * common.bi.GestionEliminacionBI#getEliminacionesEnElaboracion(java.lang
	 * .String)
	 */
	public List getEliminacionesEnElaboracion(String idGestor,
			String[] idsArchivo) {

		if (ArrayUtils.isEmpty(idsArchivo)) {
			return null;
		}

		int[] estados = { ValoracionConstants.ESTADO_ELIMINACION_ABIERTA,
				ValoracionConstants.ESTADO_ELIMINACION_APROBADA,
				ValoracionConstants.ESTADO_ELIMINACION_RECHAZADA,
				ValoracionConstants.ESTADO_ELIMINACION_PENDIENTE_DESTRUCCION,
				ValoracionConstants.ESTADO_ELIMINACION_PENDIENTE_DE_APROBAR,
				ValoracionConstants.ESTADO_ELIMINACION_DESTRUCCION_REALIZADA,
				ValoracionConstants.ESTADO_ELIMINACION_PENDIENTE_FINALIZACION };
		return eliminacionSerieDBEntity.getEliminacionesXEstado(estados,
				idsArchivo);
	}

	public List getEliminacionesValoracion(String idValoracion, int[] estados) {
		return eliminacionSerieDBEntity.getEliminaciones(idValoracion, estados);
	}

	/**
	 * Obtiene una eliminacion de una valoración a partir de su identificador en
	 * la base de datos.
	 * 
	 * @param codigo
	 *            Identificador de la eliminacion en la base de datos
	 * @return Objeto {@link EliminacionSerieVO} con los detalles de la
	 *         eliminacion.
	 */
	public EliminacionSerieVO getEliminacion(String codigo) {
		return eliminacionSerieDBEntity.getEliminacion(codigo);
	}

	/**
	 * Inicia una seleccion de una serie.
	 * 
	 * @param eliminacion
	 *            Información de la eliminación.
	 * @return Selección de la serie.
	 * @throws EliminacionActionNotAllowedException
	 */
	public EliminacionSerieVO iniciarSeleccionSerie(
			EliminacionSerieVO eliminacion)
			throws EliminacionActionNotAllowedException {
		this.check(FondosSecurityManager.CREAR_ELIMINACION_ACTION);

		LoggingEvent event = getLogginEvent(ArchivoActions.FONDOS_MODULE_ALTA_ELIMINACION);
		DataLoggingEvent logData = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_ELIMINACION, null);
		Locale locale = getServiceClient().getLocale();

		SerieVO serie = getGestionSeriesBI().getSerie(eliminacion.getIdSerie());

		getConditionChecker().checkOnCreate(serie);

		eliminacion.setFechaEstado(DBUtils.getFechaActual());
		eliminacion.setEstado(ValoracionConstants.ESTADO_ELIMINACION_ABIERTA);

		iniciarTransaccion();

		int nVersion = nVersionSelDBEntity.incrementarNumeroVersion(serie
				.getId());
		eliminacion.setTitulo(new StringBuffer(serie.getCodigo()).append("s")
				.append(nVersion).toString());

		logData.addDetalle(locale, ArchivoDetails.SELECCION_COD_SELECCION,
				eliminacion.getTitulo());
		logData.addDetalle(locale, ArchivoDetails.SELECCION_COD_SERIE,
				serie.getCodigo());

		eliminacionSerieDBEntity.insertEliminacion(eliminacion);

		logData.setIdObject(eliminacion.getId());
		commit();

		return eliminacion;
	}

	public EliminacionSerieVO actualizarEliminacion(
			EliminacionSerieVO eliminacion)
			throws EliminacionActionNotAllowedException {
		this.check(FondosSecurityManager.ACTUALIZAR_ELIMINACION_ACTION);

		LoggingEvent event = getLogginEvent(ArchivoActions.FONDOS_MODULE_ACTUALIZACION_ELIMINACION);
		DataLoggingEvent logData = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_ELIMINACION, eliminacion.getId());

		getConditionChecker().checkOnUpdate(eliminacion);

		iniciarTransaccion();

		Locale locale = getServiceClient().getLocale();
		logData.addDetalle(locale, ArchivoDetails.SELECCION_COD_SELECCION,
				eliminacion.getTitulo());

		eliminacionSerieDBEntity.updateEliminacion(eliminacion);

		commit();

		return eliminacion;
	}

	/**
	 * Realiza la actualizacion de los criterios de una eliminacion dada
	 * 
	 * @param eliminacion
	 *            Eliminacion que se desea actualizar sus criterios
	 * @return eliminacion con los datos actualizados
	 * @throws EliminacionActionNotAllowedException
	 *             Si no se puede realizar la actualizacion de la eliminacion
	 */
	public EliminacionSerieVO actualizarCriteriosEliminacion(
			EliminacionSerieVO eliminacionNew)
			throws EliminacionActionNotAllowedException {
		EliminacionSerieVO eliminacion = getEliminacion(eliminacionNew.getId());
		eliminacion.setCondicionBusqueda(eliminacionNew.getCondicionBusqueda());

		this.check(FondosSecurityManager.ACTUALIZAR_ELIMINACION_ACTION);

		LoggingEvent event = getLogginEvent(ArchivoActions.FONDOS_MODULE_ACTUALIZACION_ELIMINACION);
		DataLoggingEvent logData = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_ELIMINACION, eliminacion.getId());

		getConditionChecker().checkOnUpdate(eliminacion);

		iniciarTransaccion();

		eliminacion.setFechaEstado(DBUtils.getFechaActual());
		eliminacionSerieDBEntity.updateEliminacion(eliminacion);

		Locale locale = getServiceClient().getLocale();
		logData.addDetalle(locale, ArchivoDetails.SELECCION_COD_SELECCION,
				eliminacion.getTitulo());

		commit();

		return eliminacion;
	}

	public void solicitarAprobacionEliminacion(String idEliminacion,
			Date fechaEjecucion) throws EliminacionActionNotAllowedException {
		this.check(FondosSecurityManager.SOLICITAR_APROBACION_ELIMINACION_ACTION);
		Locale locale = getServiceClient().getLocale();

		EliminacionSerieVO eliminacion = eliminacionSerieDBEntity
				.getEliminacion(idEliminacion);
		LoggingEvent event = getLogginEvent(ArchivoActions.FONDOS_MODULE_SOLICITAR_APROBACION_ELIMINACION);
		DataLoggingEvent logData = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_ELIMINACION, eliminacion.getId());

		getConditionChecker().checkOnSolicitarAprobacion(eliminacion);

		iniciarTransaccion();

		eliminacion
				.setEstado(ValoracionConstants.ESTADO_ELIMINACION_PENDIENTE_DE_APROBAR);
		eliminacion.setFechaEstado(DBUtils.getFechaActual());
		eliminacion.setFechaEjecucion(fechaEjecucion);

		logData.addDetalle(locale, ArchivoDetails.SELECCION_COD_SELECCION,
				eliminacion.getTitulo());
		logData.addDetalle(locale, ArchivoDetails.SELECCION_FECHA_EJECUCION,
				DateUtils.formatDate(fechaEjecucion));

		eliminacionSerieDBEntity.updateEliminacion(eliminacion);

		commit();

	}

	public void aprobarEliminacion(String idEliminacion)
			throws EliminacionActionNotAllowedException {
		this.check(FondosSecurityManager.APROBAR_ELIMINACION_ACTION);
		Locale locale = getServiceClient().getLocale();

		EliminacionSerieVO eliminacion = eliminacionSerieDBEntity
				.getEliminacion(idEliminacion);
		LoggingEvent event = getLogginEvent(ArchivoActions.FONDOS_MODULE_APROBAR_ELIMINACION);
		DataLoggingEvent logData = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_ELIMINACION, eliminacion.getId());

		getConditionChecker().checkOnAprobarEliminacion(eliminacion);

		iniciarTransaccion();

		eliminacion.setEstado(ValoracionConstants.ESTADO_ELIMINACION_APROBADA);
		eliminacion.setFechaEstado(DBUtils.getFechaActual());
		eliminacion.setFechaAprobacion(DBUtils.getFechaActual());

		logData.addDetalle(locale, ArchivoDetails.SELECCION_COD_SELECCION,
				eliminacion.getTitulo());
		logData.addDetalle(locale, ArchivoDetails.SELECCION_FECHA_APROBACION,
				DateUtils.formatDate(eliminacion.getFechaAprobacion()));

		eliminacionSerieDBEntity.updateEliminacion(eliminacion);

		// Insertar las unidades documentales a eliminar en la tabla
		// correspondiente
		UnidadesDocumentalesEliminacionVO unidadesDocumentales = getUnidadesEliminacion(
				eliminacion.getId(), eliminacion.getIdArchivo(), null);

		List udocsAEliminar = unidadesDocumentales.getListaUnidades();

		if (ListUtils.isNotEmpty(udocsAEliminar)) {

			int numero = 1;
			for (Iterator iterator = udocsAEliminar.iterator(); iterator
					.hasNext();) {
				UnidadDocumentalEliminacionVO vo = (UnidadDocumentalEliminacionVO) iterator
						.next();
				vo.setIdEliminacion(idEliminacion);
				vo.setNumero("" + numero);
				numero++;
				eliminacionUDOCEliminadaDBEntity.insertUdocAEliminar(vo);
			}
		}

		commit();

	}

	public void rechazarEliminacion(String idEliminacion, String motivo)
			throws EliminacionActionNotAllowedException {
		this.check(FondosSecurityManager.RECHAZAR_ELIMINACION_ACTION);
		Locale locale = getServiceClient().getLocale();

		EliminacionSerieVO eliminacion = eliminacionSerieDBEntity
				.getEliminacion(idEliminacion);
		LoggingEvent event = getLogginEvent(ArchivoActions.FONDOS_MODULE_RECHAZAR_ELIMINACION);
		DataLoggingEvent logData = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_ELIMINACION, eliminacion.getId());

		getConditionChecker().checkOnRechazarEliminacion(eliminacion);

		iniciarTransaccion();

		eliminacion.setEstado(ValoracionConstants.ESTADO_ELIMINACION_RECHAZADA);
		eliminacion.setMotivoRechazo(motivo);
		eliminacion.setFechaEstado(DBUtils.getFechaActual());

		logData.addDetalle(locale, ArchivoDetails.SELECCION_COD_SELECCION,
				eliminacion.getTitulo());
		logData.addDetalle(locale, ArchivoDetails.SELECCION_MOTIVO_RECHAZO,
				motivo);

		eliminacionSerieDBEntity.updateEliminacion(eliminacion);

		commit();
	}

	/**
	 * Borra una eliminacion existente de la base de datos
	 * 
	 * @param ids
	 *            Listado de los identificadores de la eliminaciones que
	 *            deseamos borrar.
	 * @throws EliminacionActionNotAllowedException
	 *             Si no se pueder realizar el borrado de la eliminación
	 */
	public void eliminarEliminaciones(String[] ids)
			throws EliminacionActionNotAllowedException {
		LoggingEvent event = getLogginEvent(ArchivoActions.FONDOS_MODULE_BAJA_ELIMINACION);
		DataLoggingEvent logData = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_LISTA_ELIMINACIONES, null);
		Locale locale = getServiceClient().getLocale();

		// comprobar sin son validaciones borrables
		getConditionChecker().checkOnEliminar(ids);

		iniciarTransaccion();

		for (int i = 0; i < ids.length; i++) {
			EliminacionSerieVO eliminacion = eliminacionSerieDBEntity
					.getEliminacion(ids[i]);
			logData.addDetalle(locale, ArchivoDetails.SELECCION_COD_SELECCION,
					eliminacion.getTitulo());

			eliminacionSerieDBEntity.deleteEliminacion(ids[i]);
			eliminacionUDOCConservadaDBEntity.deleteUdocsXIdEliminacion(ids[i]);
		}

		commit();
	}

	/**
	 * Obtiene un listado de las eliminaciones dadas por su identificador de bd.
	 * 
	 * @param ids
	 *            Lsitado de identificadores de las eliminaciones que deseamos
	 *            recuperar.
	 * @return Eliminaciones deseadas
	 */
	public List getEliminaciones(String[] ids) {
		return eliminacionSerieDBEntity.getEliminaciones(ids);
	}

	public List ejecutarEliminacion(String id)
			throws EliminacionActionNotAllowedException {
		List udocsADestruir = new ArrayList();
		EliminacionSerieVO eliminacion = this.getEliminacion(id);

		this.check(FondosSecurityManager.EJECUTAR_ELIMINACION_ACTION);

		LoggingEvent event = getLogginEvent(ArchivoActions.FONDOS_MODULE_EJECUTAR_ELIMINACION);
		DataLoggingEvent data = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_ELIMINACION, id);

		getConditionChecker().checkOnEjecutarEliminacion(eliminacion);

		iniciarTransaccion();
		EliminacionSerieVO eliminacionSerie = getEliminacion(id);
		String subqueryIdsUdoc = eliminacionUDOCConservadaDBEntity
				.getSubConsultaIdsUdocsEliminacionConservarXId(id);

		String subqueryIdsUdocEliminar = eliminacionSerieDBEntity
				.getSubConsultaIDsUdocsAEliminar(eliminacionSerie,
						subqueryIdsUdoc);
		elementoCuadroDBEntity.updateEstadoEliminacionElementoCFSubquery(
				subqueryIdsUdocEliminar,
				IElementoCuadroClasificacion.ESTADO_ELIMINADO, id);

		String[] udocsAConservar = eliminacionSerieDBEntity
				.getIDsUdocsAConservar(eliminacion, subqueryIdsUdoc);
		if (ArrayUtils.isNotEmpty(udocsAConservar)) {
			elementoCuadroDBEntity.updateEstadoEliminacionElementoCF(
					udocsAConservar, IElementoCuadroClasificacion.VIGENTE, id);
		}

		eliminacion.setFechaEstado(DBUtils.getFechaActual());
		eliminacion
				.setEstado(ValoracionConstants.ESTADO_ELIMINACION_PENDIENTE_DESTRUCCION);
		eliminacionSerieDBEntity.updateEliminacion(eliminacion);

		Locale locale = getServiceClient().getLocale();
		data.addDetalle(locale, ArchivoDetails.SELECCION_COD_SELECCION,
				eliminacion.getTitulo());

		commit();

		return udocsADestruir;
	}

	public void ejecutarDestruccionFisica(String id, Date fechaEliminacion)
			throws EliminacionActionNotAllowedException {
		EliminacionSerieVO eliminacion = this.getEliminacion(id);
		Locale locale = getServiceClient().getLocale();

		this.check(FondosSecurityManager.EJECUTAR_ELIMINACION_ACTION);

		LoggingEvent event = getLogginEvent(ArchivoActions.FONDOS_MODULE_DESTRUIR_FISICAMENTE);
		DataLoggingEvent logData = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_ELIMINACION, id);

		getConditionChecker().checkOnDestruccionFisica(eliminacion);

		eliminacion
				.setEstado(ValoracionConstants.ESTADO_ELIMINACION_DESTRUCCION_REALIZADA);
		eliminacion.setFechaDestruccion(fechaEliminacion);
		eliminacion.setFechaEstado(fechaEliminacion);
		iniciarTransaccion();

		logData.addDetalle(locale, ArchivoDetails.SELECCION_COD_SELECCION,
				eliminacion.getTitulo());
		logData.addDetalle(locale, ArchivoDetails.SELECCION_FECHA_DESTRUCCION,
				DateUtils.formatDate(fechaEliminacion));

		eliminacionSerieDBEntity.updateEliminacion(eliminacion);

		commit();
	}

	private void addMarcaCajas(List unidades, Map uinstParciales) {
		if (ListUtils.isNotEmpty(unidades) && uinstParciales != null) {
			for (Iterator iterator = unidades.iterator(); iterator.hasNext();) {
				IUnidadDocumentalEliminacionVO unidadDocumentalEliminacionVO = (IUnidadDocumentalEliminacionVO) iterator
						.next();

				if (unidadDocumentalEliminacionVO != null) {
					String idUinstalacion = unidadDocumentalEliminacionVO
							.getIduinstalacion();

					if (uinstParciales.get(idUinstalacion) != null) {
						unidadDocumentalEliminacionVO.setCajaParcial();
					} else {
						unidadDocumentalEliminacionVO.setCajaCompleta();
					}
				}
			}
		}
	}

	/**
	 * Devuelve el listado de unidades documentales que no se verán afectadas
	 * por el proceso de eliminacion
	 * 
	 * @param idEliminacion
	 *            Identificador de la eliminacion.
	 * @return Listado de las unidadesd documentales {@see
	 *         valoracion.vos.UnidadDocumentalEliminacionVO} no afectadas por el
	 *         proceso de eliminacion
	 */
	public Collection getUnidadesConservacion(String idEliminacion,
			String idArchivo) {
		Collection unidades = null;
		try {
			unidades = getUnidadesConservacion(idEliminacion, idArchivo, null);
		} catch (TooManyResultsException e) {
		}

		return unidades;
	}

	/**
	 * Devuelve el listado de unidades documentales procesadas en la
	 * eliminación.
	 * 
	 * @param idEliminacion
	 *            Identificador de la eliminacion.
	 * @return Listado de las unidadesd documentales {@see HistoricoUDOCVO}
	 *         eliminadas.
	 */
	public List getUnidadesEliminadas(String idEliminacion) {
		return historicoDBEntity.getUdocsByIdEliminacion(idEliminacion);
	}

	/**
	 * Devuelve el listado de unidades documentales que se van a eliminar
	 * 
	 * @param idEliminacion
	 *            Identificador de la eliminacion.
	 * @return Listado de las unidadesd documentales UnidadDocu Unidad
	 *         eliminadas.
	 */
	public UnidadesDocumentalesEliminacionVO getUnidadesAEliminar(
			String idEliminacion, boolean addMarcaCajas) {

		List unidades = eliminacionUDOCEliminadaDBEntity
				.getUdocsAEliminarXId(idEliminacion);

		UnidadesDocumentalesEliminacionVO unidadesDocumentales = new UnidadesDocumentalesEliminacionVO(
				unidades);

		if (addMarcaCajas) {
			HashMap mapUinstParciales = getUInstalacionParciales(unidades);
			unidadesDocumentales.setMapUInstParciales(mapUinstParciales);
			addMarcaCajas(unidades, mapUinstParciales);
		}
		return unidadesDocumentales;
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * common.bi.GestionEliminacionBI#getUnidadesEliminacionAConservar(java.
	 * lang.String)
	 */
	public List getUnidadesEliminacionAConservar(String idEliminacion) {
		return eliminacionUDOCConservadaDBEntity
				.getUdocsEliminacionConservarXId(idEliminacion);
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * common.bi.GestionEliminacionBI#insertUnidadesEliminacionAConservar(java
	 * .lang.String, java.lang.String[])
	 */
	public List insertUnidadesEliminacionAConservar(String idEliminacion,
			String[] idsUdoc) {
		// this.check( FondosSecurityManager.FINALIZAR_ELIMINACION_ACTION);

		List ltUnidadesSinFechas = new ArrayList();
		iniciarTransaccion();
		for (int i = 0; i < idsUdoc.length; i++) {
			// ElementoCFVO
			IElementoCuadroClasificacion elemento = elementoCuadroDBEntity
					.getElementoCuadroClasificacionConFechas(idsUdoc[i]);
			if (elemento != null) {
				ElementoCuadroClasificacion udoc = (ElementoCuadroClasificacion) elemento;
				EliminacionUDocConservadaVO eliminacionUDocVO = new EliminacionUDocConservadaVO();
				eliminacionUDocVO.setIdEliminacion(idEliminacion);
				eliminacionUDocVO.setIdUdoc(udoc.getId());
				eliminacionUDocVO.setTituloUdoc(udoc.getTitulo());
				eliminacionUDocVO.setSignaturaUdoc(udoc.getCodigo());
				eliminacionUDocVO.setFechaIniUdoc(udoc.getFechaInicial());
				eliminacionUDocVO.setFechaFinUdoc(udoc.getFechaFinal());
				eliminacionUDOCConservadaDBEntity
						.insertUdocConservar(eliminacionUDocVO);
			} else {
				elemento = elementoCuadroDBEntity
						.getElementoCuadroClasificacion(idsUdoc[i]);
				if (elemento != null) {
					ltUnidadesSinFechas.add(elemento.getCodReferencia());
				}
			}
		}

		commit();

		return ltUnidadesSinFechas;
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * common.bi.GestionEliminacionBI#eliminarUnidadesEliminacionAConservar(
	 * java.lang.String, java.lang.String[])
	 */
	public void eliminarUnidadesEliminacionAConservar(String idEliminacion,
			String[] idsUdoc) {

		iniciarTransaccion();

		eliminacionUDOCConservadaDBEntity.deleteUdocsXIdEliminacion(
				idEliminacion, idsUdoc);
		commit();
	}

	/**
	 * Devuelve el listado de unidades documentales que se verán afectadas por
	 * el proceso de eliminacion
	 * 
	 * @param idEliminacion
	 *            Identificador de la eliminacion.
	 * @param pageInfo
	 *            Información de la paginación.
	 * @return Listado de las unidadesd documentales {@see
	 *         valoracion.vos.UnidadDocumentalEliminacionVO} afectadas por el
	 *         proceso de eliminacion
	 * @throws TooManyResultsException
	 *             si el número de resultados excede el máximo.
	 */
	private UnidadesDocumentalesEliminacionVO getUnidadesEliminacion(
			String idEliminacion, String idArchivo, PageInfo pageInfo) {
		// Obtenemos la eliminacion asociada al id
		EliminacionSerieVO eliminacion = this.getEliminacion(idEliminacion);

		String subqueryIdsUdoc = eliminacionUDOCConservadaDBEntity
				.getSubConsultaIdsUdocsEliminacionConservarXId(idEliminacion);

		List unidades = eliminacionSerieDBEntity.getListaUdocsAEliminar(
				eliminacion, idArchivo, subqueryIdsUdoc, pageInfo);

		return new UnidadesDocumentalesEliminacionVO(unidades);
	}

	public UnidadesDocumentalesEliminacionVO getUnidadesEliminacion(
			EliminacionSerieVO eliminacion, String idArchivo,
			PageInfo pageInfo, boolean addMarcaCajas) {
		if (eliminacion != null) {
			if (eliminacion.isAutorizada()) {
				return getUnidadesAEliminar(eliminacion.getId(), addMarcaCajas);
			} else {
				return getUnidadesEliminacion(eliminacion.getId(), idArchivo,
						pageInfo);
			}
		}
		return null;
	}

	public UnidadesDocumentalesEliminacionVO getUnidadesEliminacion(
			String idEliminacion, String idArchivo, PageInfo pageInfo,
			boolean addMarcaCajas) {
		EliminacionSerieVO eliminacion = getEliminacion(idEliminacion);
		return getUnidadesEliminacion(eliminacion, idArchivo, pageInfo,
				addMarcaCajas);
	}

	/**
	 * Devuelve el listado de unidades documentales relacionadas que se verán
	 * afectadas por el proceso de eliminacion
	 * 
	 * @param idEliminacion
	 *            Identificador de la eliminacion.
	 * @param pageInfo
	 *            Información de la paginación.
	 * @return Listado de las unidadesd documentales {@see
	 *         valoracion.vos.UnidadDocumentalEliminacionVO} afectadas por el
	 *         proceso de eliminacion
	 * @throws TooManyResultsException
	 *             si el número de resultados excede el máximo.
	 */
	public Collection getUnidadesRelEliminacion(String idEliminacion,
			PageInfo pageInfo) throws TooManyResultsException {
		// Obtenemos la eliminacion asociada al id
		EliminacionSerieVO eliminacion = this.getEliminacion(idEliminacion);
		String subqueryIdsUdoc = eliminacionUDOCConservadaDBEntity
				.getSubConsultaIdsUdocsEliminacionConservarXId(idEliminacion);
		return eliminacionSerieDBEntity.getListaUdocsRelAEliminar(eliminacion,
				subqueryIdsUdoc, pageInfo);
	}

	/**
	 * Devuelve el listado de unidades documentales que no se verán afectadas
	 * por el proceso de eliminacion
	 * 
	 * @param idEliminacion
	 *            Identificador de la eliminacion.
	 * @param pageInfo
	 *            Información de la paginación.
	 * @return Listado de las unidadesd documentales {@see
	 *         solicitudes.vos.BusquedaDetalleVO} no afectadas por el proceso de
	 *         eliminacion
	 * @throws TooManyResultsException
	 *             si el número de resultados excede el máximo.
	 */
	public Collection getUnidadesConservacion(String idEliminacion,
			String idArchivo, PageInfo pageInfo) throws TooManyResultsException {

		// Obtenemos la eliminacion asociada al id
		EliminacionSerieVO eliminacion = this.getEliminacion(idEliminacion);

		String subqueryIdsUdoc = eliminacionUDOCConservadaDBEntity
				.getSubConsultaIdsUdocsEliminacionConservarXId(idEliminacion);
		return eliminacionSerieDBEntity.getListaUdocsAConservar(eliminacion,
				idArchivo, subqueryIdsUdoc, pageInfo);
	}

	/**
	 * Realiza la creacion de un historico de una unidad documental que ha sido
	 * eliminada
	 * 
	 * @param historico
	 *            historioco de la unidad documental a guardar en bd
	 */
	public void crearHistoricoUDOC(HistoricoUDOCVO historico) {
		historicoDBEntity.insertHistoricoUdoc(historico);
	}

	/**
	 * Obtiene las eliminaciones que cumplen los filtros de busqueda
	 * 
	 * @param busqueda
	 *            Objeto que contiene los elementos del formulario de búsqueda.
	 * @throws TooManyResultsException
	 *             si el número de resultados es excesivo.
	 */
	public List getEliminaciones(BusquedaVO busqueda)
			throws TooManyResultsException {
		return eliminacionSerieDBEntity.getEliminaciones(busqueda);
	}

	/**
	 * Obtiene la eliminación en curso sobre una serie determinada caso de
	 * existir
	 * 
	 * @param idSerie
	 *            Identificador de serie
	 * @return Datos de eliminación {@link EliminacionSerieVO}
	 */
	public EliminacionSerieVO getEliminacionSerie(String idSerie) {
		int[] estadosEliminacion = {
				ValoracionConstants.ESTADO_ELIMINACION_ABIERTA,
				ValoracionConstants.ESTADO_ELIMINACION_APROBADA,
				ValoracionConstants.ESTADO_ELIMINACION_DESTRUCCION_REALIZADA,
				ValoracionConstants.ESTADO_ELIMINACION_PENDIENTE_DE_APROBAR,
				ValoracionConstants.ESTADO_ELIMINACION_PENDIENTE_DESTRUCCION };
		List eliminaciones = eliminacionSerieDBEntity.getEliminacionesSerie(
				idSerie, estadosEliminacion);
		EliminacionSerieVO eliminacionEnCurso = null;
		if (eliminaciones != null && eliminaciones.size() > 0)
			eliminacionEnCurso = (EliminacionSerieVO) eliminaciones.get(0);
		return eliminacionEnCurso;
	}

	/**
	 * Obtiene las unidades documentales seleccionables de una serie.
	 * 
	 * @param idSerie
	 *            Identificador de la serie.
	 * @param pageInfo
	 *            Información de la paginación.
	 * @return Lista de unidades documentales (
	 *         {@link UnidadDocumentalEliminacionVO}).
	 * @throws TooManyResultsException
	 *             si se excede el número máximo de resultados.
	 */
	public List getUdocsSeleccionables(String idSerie, PageInfo pageInfo)
			throws SerieNoValoradaException, TooManyResultsException {
		ServiceRepository services = ServiceRepository
				.getInstance(getServiceClient());

		GestionValoracionBI valoracionBI = services.lookupGestionValoracionBI();
		ValoracionSerieVO valoracionDictaminada = valoracionBI
				.getValoracionDictaminada(idSerie);
		if (valoracionDictaminada == null)
			throw new SerieNoValoradaException();

		return eliminacionSerieDBEntity.getListaUdocsSeleccionables(idSerie,
				new Date(), valoracionDictaminada.getAnosConservacion(), null,
				pageInfo);
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
	 * eliminacion de series
	 */
	public LoggingEvent getLogginEvent(int action) {
		LoggingEvent le = new LoggingEvent(ArchivoModules.FONDOS_MODULE,
				action, getServiceClient(), false);

		// Lo añadimos a la pila
		getLogger().add(le);

		return le;
	}

	private ConditionChecker getConditionChecker() {
		return new ConditionChecker();
	}

	// CLASE INTERNA
	private class ConditionChecker {

		/**
		 * Configura las acciones que pueden ser realizadas sobre la eliminacion
		 * 
		 * @param eliminacion
		 *            Eliminacion a configurar
		 */
		public void configureEliminacion(EliminacionSerieVO eliminacion) {
			// Verifica si la eliminacion puede ser editada
			try {
				checkOnEditar(eliminacion);
				eliminacion.setPuedeSerEditada(true);
			} catch (ActionNotAllowedException anae) {
				logger.debug("Configuracion de eliminación :: La edicion de la eliminación no está permitida: "
						+ eliminacion.getTitulo());
			}
			// Verifica si la eliminacion puede ser eliminada
			try {
				String ids[] = { eliminacion.getId() };

				checkOnEliminar(ids);
				eliminacion.setPuedeSerEliminada(true);
			} catch (ActionNotAllowedException anae) {
				logger.debug("Configuracion de eliminación :: El borrado de la eliminación no está permitida: "
						+ eliminacion.getTitulo());
			}
			// Verifica si se puede solicitar aprobacion
			try {
				checkOnSolicitarAprobacion(eliminacion);
				eliminacion.setPermitidaSolicitudAprobacionEliminacion(true);
			} catch (ActionNotAllowedException anae) {
				logger.debug("Configuracion de eliminación :: La solicitud de aprobación de la eliminación no está permitida: "
						+ eliminacion.getTitulo());
			}
			// Verifica si se puede aprobar directamente la eliminacion
			try {
				checkOnAprobarDirectamenteEliminacion(eliminacion);
				eliminacion.setPermitidaAprobacionDirecta(true);
			} catch (ActionNotAllowedException anae) {
				logger.debug("Configuracion de eliminación :: La aprobación directa de la eliminación no está permitida: "
						+ eliminacion.getTitulo());
			}
			// Verifica si se puede aprobar la eliminacio
			try {
				checkOnAprobarEliminacion(eliminacion);
				eliminacion.setPermitidaAprobacionEliminacion(true);
			} catch (ActionNotAllowedException anae) {
				logger.debug("Configuracion de eliminación :: La aprobación de la eliminación no está permitida: "
						+ eliminacion.getTitulo());
			}
			// Verifica si se puede ejecutar la eliminacion
			try {
				checkOnEjecutarEliminacion(eliminacion);
				eliminacion.setPermitidaEjecucionEliminacion(true);
			} catch (ActionNotAllowedException anae) {
				logger.debug("Configuracion de eliminación :: La ejecución de la eliminación no está permitida: "
						+ eliminacion.getTitulo());
			}
			// Verifica si se puede realizar destruccion fisica
			try {
				checkOnDestruccionFisica(eliminacion);
				eliminacion.setPermitidaDestruccionFisica(true);
			} catch (ActionNotAllowedException anae) {
				logger.debug("Configuracion de eliminación :: La destrucción física de la eliminación no está permitida: "
						+ eliminacion.getTitulo());
			}
			// Verifica si se puede realizar la finalización
			try {
				checkOnFinalizarEliminacion(eliminacion);
				eliminacion.setPermitidaFinalizacion(true);
			} catch (ActionNotAllowedException anae) {
				logger.debug("Configuracion de eliminación :: La finalización de la eliminación no está permitida: "
						+ eliminacion.getTitulo());
			}
		}

		/**
		 * Comprueba si se puede realizar la creacion de una valoracion dada
		 * para una determinada serie. Comprueba: - La valoracion ebe estar
		 * dictaminada.El estado de la serie (debe ser vigente o historica) - La
		 * serie no debe tener otra valoracion en estado 1,2,3,4,5
		 * 
		 * @param serie
		 *            Serie sobre la que se va a seleccionar
		 * @throws EliminacionActionNotAllowedException
		 *             Si no se pueder realizar la creación de la eliminación
		 *             para la serie
		 */
		public void checkOnCreate(SerieVO serie)
				throws EliminacionActionNotAllowedException {
			GestionValoracionBI gv = ServiceRepository.getInstance(
					getServiceSession()).lookupGestionValoracionBI();
			ValoracionSerieVO valoracion = gv.getValoracionDictaminada(serie
					.getId());

			if (valoracion.getValorDictamen() == ValoracionSerieVO.VALOR_DICTAMEN_CONSERVACION_TOTAL)
				throw new EliminacionActionNotAllowedException(
						ArchivoErrorCodes.ERROR_ELIMINACION_NO_CREABLE_XESTADO);

			List eliminaciones = getEliminacionesValoracion(valoracion.getId(),
					null);
			for (Iterator itEliminacion = eliminaciones.iterator(); itEliminacion
					.hasNext();) {
				EliminacionSerieVO eliminacionSerieVO = (EliminacionSerieVO) itEliminacion
						.next();

				if (eliminacionSerieVO.getEstado() == ValoracionConstants.ESTADO_ELIMINACION_ABIERTA
						|| eliminacionSerieVO.getEstado() == ValoracionConstants.ESTADO_ELIMINACION_APROBADA
						|| eliminacionSerieVO.getEstado() == ValoracionConstants.ESTADO_ELIMINACION_DESTRUCCION_REALIZADA
						|| eliminacionSerieVO.getEstado() == ValoracionConstants.ESTADO_ELIMINACION_PENDIENTE_DE_APROBAR
						|| eliminacionSerieVO.getEstado() == ValoracionConstants.ESTADO_ELIMINACION_PENDIENTE_DESTRUCCION)
					throw new EliminacionActionNotAllowedException(
							ArchivoErrorCodes.ERROR_ELIMINACION_NO_CREABLE_XESTADO_ELIMINACION);
			}
		}

		/**
		 * Comprueba si se puede realizar la actualizacion de una valoracion
		 * dada para una determinada serie. Comprueba:
		 * 
		 * @param eliminacion
		 *            Eliminacion que se desea crear
		 * @throws EliminacionActionNotAllowedException
		 *             Si no se pueder realizar la creación de la eliminación
		 *             para la serie
		 */
		public void checkOnUpdate(EliminacionSerieVO eliminacion)
				throws EliminacionActionNotAllowedException {
		}

		/**
		 * Comprueba si una eliminacion puede ser editada.
		 * 
		 * @param eliminacion
		 *            Eliminacion que se desea comprobar si puede ser editada
		 * @throws EliminacionActionNotAllowedException
		 *             Si no de puede realizar la edicion
		 */
		public void checkOnEditar(EliminacionSerieVO eliminacion)
				throws EliminacionActionNotAllowedException {
			if (eliminacion.getEstado() != ValoracionConstants.ESTADO_ELIMINACION_ABIERTA)
				throw new EliminacionActionNotAllowedException(
						ArchivoErrorCodes.ERROR_ELIMINACION_NO_EDITABLE_XESTADO);
		}

		/**
		 * Comprueba la solicitud de aprobacion de una eliminacion.
		 * 
		 * @param eliminacion
		 *            Eliminacion que se desea comprobar su solicitud de
		 *            eliminacion
		 * @throws EliminacionActionNotAllowedException
		 *             Si no de puede realizar la solicitud
		 */
		public void checkOnSolicitarAprobacion(EliminacionSerieVO eliminacion)
				throws EliminacionActionNotAllowedException {
			if (eliminacion.getEstado() != ValoracionConstants.ESTADO_ELIMINACION_ABIERTA)
				throw new EliminacionActionNotAllowedException(
						ArchivoErrorCodes.ERROR_SOLICITUD_APROBACION_NO_CREABLE_XESTADO);
		}

		/**
		 * Comprueba si se pruebe aprobar la eliminacion.
		 * 
		 * @param eliminacion
		 *            Eliminacion que se desea comprobar si se puede aprobar
		 * @throws EliminacionActionNotAllowedException
		 *             Si no se pueder aprobar la eliminación
		 */
		public void checkOnAprobarEliminacion(EliminacionSerieVO eliminacion)
				throws EliminacionActionNotAllowedException {
			if (!(eliminacion.getEstado() == ValoracionConstants.ESTADO_ELIMINACION_PENDIENTE_DE_APROBAR || (eliminacion
					.getEstado() == ValoracionConstants.ESTADO_ELIMINACION_ABIERTA && (getServiceClient()
					.hasPermission(AppPermissions.GESTION_ELIMINACION_TOTAL) || getServiceClient()
					.hasPermission(AppPermissions.ADMINISTRACION_TOTAL_SISTEMA)))))
				throw new EliminacionActionNotAllowedException(
						ArchivoErrorCodes.ERROR_APROBACION_ELIMINACION_NO_CREABLE_XESTADO);
		}

		/**
		 * Comprueba si se pruebe aprobar directamente la eliminacion.
		 * 
		 * @param eliminacion
		 *            Eliminacion que se desea comprobar si se puede aprobar
		 *            directamente
		 * @throws EliminacionActionNotAllowedException
		 *             Si no se pueder aprobar directamente la eliminación
		 */
		public void checkOnAprobarDirectamenteEliminacion(
				EliminacionSerieVO eliminacion)
				throws EliminacionActionNotAllowedException {
			if (!((getServiceClient().hasPermission(
					AppPermissions.GESTION_ELIMINACION_TOTAL) || getServiceClient()
					.hasPermission(AppPermissions.ADMINISTRACION_TOTAL_SISTEMA))))
				throw new EliminacionActionNotAllowedException(
						ArchivoErrorCodes.ERROR_APROBACION_DIRECTA_ELIMINACION_NO_CREABLE_XUSUARIO);
		}

		/**
		 * Comprueba si se pruebe rechazar la eliminacion.
		 * 
		 * @param eliminacion
		 *            Eliminacion que se desea comprobar si se puede rechazar
		 * @throws EliminacionActionNotAllowedException
		 *             Si no se pueder rechazar la eliminación
		 */
		public void checkOnRechazarEliminacion(EliminacionSerieVO eliminacion)
				throws EliminacionActionNotAllowedException {
			if (!(eliminacion.getEstado() == ValoracionConstants.ESTADO_ELIMINACION_PENDIENTE_DE_APROBAR || (eliminacion
					.getEstado() == ValoracionConstants.ESTADO_ELIMINACION_ABIERTA && (getServiceClient()
					.hasPermission(AppPermissions.GESTION_ELIMINACION_TOTAL) || getServiceClient()
					.hasPermission(AppPermissions.ADMINISTRACION_TOTAL_SISTEMA)))))
				throw new EliminacionActionNotAllowedException(
						ArchivoErrorCodes.ERROR_RECHAZO_ELIMINACION_NO_CREABLE_XESTADO);
		}

		/**
		 * Comprueba si las eliminaciones seleccionadas pueden ser eliminadas
		 * 
		 * @param ids
		 *            Identificadores de las eliminaciones a eliminar
		 * @throws EliminacionActionNotAllowedException
		 *             Si no se pueden eliminar las eliminaciones
		 */
		public void checkOnEliminar(String[] ids)
				throws EliminacionActionNotAllowedException {
			boolean borrables = true;

			// Obtenemos las eliminaciones
			Collection eliminaciones = getEliminaciones(ids);
			for (Iterator itEliminacion = eliminaciones.iterator(); itEliminacion
					.hasNext() && borrables;) {
				EliminacionSerieVO eliminacionVO = (EliminacionSerieVO) itEliminacion
						.next();

				if (eliminacionVO.getEstado() == ValoracionConstants.ESTADO_ELIMINACION_ABIERTA
						|| eliminacionVO.getEstado() == ValoracionConstants.ESTADO_ELIMINACION_RECHAZADA) {
					borrables = true;
				} else
					borrables = false;
			}

			if (!borrables)
				throw new EliminacionActionNotAllowedException(
						ArchivoErrorCodes.ERROR_ELIMINACION_NO_BORRABLE);
		}

		/**
		 * Comprueba si se puede realizar la eliminación física de una
		 * eliminación
		 * 
		 * @param eliminacion
		 *            Eliminacion que se desea comprobar
		 * @throws EliminacionActionNotAllowedException
		 *             Si no se puede realizar el proceso de eliminación física
		 */
		public void checkOnEjecutarEliminacion(EliminacionSerieVO eliminacion)
				throws EliminacionActionNotAllowedException {
			if (eliminacion.getEstado() != ValoracionConstants.ESTADO_ELIMINACION_APROBADA)
				throw new EliminacionActionNotAllowedException(
						ArchivoErrorCodes.ERROR_ELIMINACION_NO_EJECUTABLE);

			if (eliminacion.getFechaEjecucion().compareTo(
					DBUtils.getFechaActual()) > 0)
				throw new EliminacionActionNotAllowedException(
						ArchivoErrorCodes.ERROR_ELIMINACION_NO_EJECUTABLE);
		}

		/**
		 * Comrpueba si se puede realizar la destrucción física
		 * 
		 * @param eliminacion
		 *            Eliminacion que se desea comprobar si se puede realizar su
		 *            destrucción física
		 * @throws EliminacionActionNotAllowedException
		 *             Si no se puede realizar la destrucción física
		 */
		public void checkOnDestruccionFisica(EliminacionSerieVO eliminacion)
				throws EliminacionActionNotAllowedException {
			if (eliminacion.getEstado() != ValoracionConstants.ESTADO_ELIMINACION_PENDIENTE_DESTRUCCION)
				throw new EliminacionActionNotAllowedException(
						ArchivoErrorCodes.ERROR_ELIMINACION_NO_DESTRUIBLE);
		}

		/**
		 * Comprueba si se puede finalizar la elimianción
		 * 
		 * @param eliminacion
		 *            Eliminacion que se desea comprobar si se puede finalizar
		 * @throws EliminacionActionNotAllowedException
		 *             Si no se puede finalizar la eliminacion
		 */
		public void checkOnFinalizarEliminacion(EliminacionSerieVO eliminacion)
				throws EliminacionActionNotAllowedException {
			if (eliminacion.getEstado() != ValoracionConstants.ESTADO_ELIMINACION_DESTRUCCION_REALIZADA)
				throw new EliminacionActionNotAllowedException(
						ArchivoErrorCodes.ERROR_ELIMINACION_NO_FINALIZABLE);
		}
	}

	protected IDetalleDBEntity getDetalleDBEntity() {
		return detalleDBEntity;
	}

	/**
	 * Devuelve una subconsulta de los ids de las unidades documentales a
	 * eliminar en una selección
	 * 
	 * @param eliminacion
	 *            Eliminacion
	 * @return Array de String con los ids de las unidades documentales a
	 *         eliminar
	 */
	public String getSubConsultaIDsUdocsAEliminar(EliminacionSerieVO eliminacion) {

		String subConsultaIdsUdocConservar = eliminacionUDOCConservadaDBEntity
				.getSubConsultaIdsUdocsEliminacionConservarXId(eliminacion
						.getId());
		return eliminacionSerieDBEntity.getSubConsultaIDsUdocsAEliminar(
				eliminacion, subConsultaIdsUdocConservar);
	}

	/**
	 * Devuelve los ids de las unidades documentales relacionadas con las
	 * unidades a eliminar en una selección
	 * 
	 * @param eliminacion
	 *            Eliminacion
	 * @return Array de String con los ids de las unidades documentales a
	 *         eliminar
	 */
	public String[] getIDsUdocsRelAEliminar(EliminacionSerieVO eliminacion) {

		String subConsultaIdsUdocConservar = eliminacionUDOCConservadaDBEntity
				.getSubConsultaIdsUdocsEliminacionConservarXId(eliminacion
						.getId());
		return eliminacionSerieDBEntity.getIDsUdocsRelAEliminar(eliminacion,
				subConsultaIdsUdocConservar);
	}

	/**
	 * Obtiene el número de unidades documentales que están en una unidad de
	 * instalación donde hay más unidades documentales que no están incluidas
	 * dentro de los identificadores
	 * 
	 * @param idsElementos
	 *            Array que contiene las cadenas con los identificadores de los
	 *            elementos seleccionados
	 * @return Número de unidades documentales
	 */
	public int getCountUInstalacionIncompletas(EliminacionSerieVO eliminacion) {
		String[] idsElementos = getIDsUdocsRelAEliminar(eliminacion);
		return uDocEnUiDepositoDbEntity
				.getCountUInstalacionIncompletas(idsElementos);
	}

	/**
	 * Obtiene los identificadores de de las unidades de instalación que se
	 * eliminarán parcialmente
	 * 
	 * @param idEliminacion
	 *            Cadena que contiene el identificador de la eliminación
	 * @param idArchivo
	 *            Cadena que contiene el identificador del archivo
	 * @return {@link HashMap} Key/Value: Cadena que contiene el identificador
	 *         de la unidad documental
	 */
	public HashMap getUInstalacionParciales(List listaUdocs) {
		String[] idsElementos = ArrayUtils.getArrayIds(listaUdocs);
		HashMap mapUInstalacion = new HashMap();
		List listaUinst = uDocEnUiDepositoDbEntity
				.getUInstalacionIncompletas(idsElementos);
		if (ListUtils.isNotEmpty(listaUdocs)
				&& ListUtils.isNotEmpty(listaUinst)) {
			for (Iterator iterator = listaUinst.iterator(); iterator.hasNext();) {
				IKeyId vo = (IKeyId) iterator.next();
				if (vo != null) {
					mapUInstalacion.put(vo.getId(), vo.getId());
				}
			}
		}

		return mapUInstalacion;
	}

	public int getNumeroUnidadesInstalacion(String idEliminacion) {
		return eliminacionUDOCEliminadaDBEntity
				.getCountNumeroUinstalacion(idEliminacion);
	}

	private void addResumenUinstEliminacionVO(EliminacionSerieVO eliminacion) {
		if (eliminacion.isAutorizada()) {
			int total = getNumeroUnidadesInstalacion(eliminacion.getId());
			UnidadesDocumentalesEliminacionVO unidadesAElminar = getUnidadesAEliminar(
					eliminacion.getId(), true);

			if (unidadesAElminar != null) {
				int parciales = unidadesAElminar.getNumeroUInstParciales();
				int numUdocs = unidadesAElminar.getNumeroUdocsAEliminar();
				int completas = total - parciales;

				ResumenUinstEliminacionVO resumen = new ResumenUinstEliminacionVO(
						total, parciales, completas, numUdocs);
				eliminacion.setResumenUInst(resumen);
			}

		}
	}

	public boolean hasUnidadesDocumentalesEnSeleccionSinFinalizar(
			TablaTemporalFondosVO tablaTemporalUDocs) {

		int[] estadosExcluir = new int[] { ValoracionConstants.ESTADO_ELIMINACION_FINALIZADA };

		int numeroEliminacionesConUdocsSeleccionadas = eliminacionSerieDBEntity
				.getCountEliminacionesConUdocsSeleccionadas(tablaTemporalUDocs,
						estadosExcluir);

		if (numeroEliminacionesConUdocsSeleccionadas > 0) {
			return true;
		}

		return false;
	}
}