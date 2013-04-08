package valoracion.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.log4j.Appender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import se.usuarios.ServiceClient;
import valoracion.ValoracionConstants;
import valoracion.exceptions.EliminacionActionNotAllowedException;
import valoracion.utils.ExceptionMapper;
import valoracion.vos.EliminacionSerieVO;
import valoracion.vos.HistoricoUDOCVO;
import valoracion.vos.ThreadSelectionMap;
import valoracion.vos.UnidadDocumentalEliminacionVO;
import valoracion.vos.UnidadesDocumentalesEliminacionVO;
import xml.config.ConfiguracionSistemaArchivo;
import xml.config.ConfiguracionSistemaArchivoFactory;
import auditoria.ArchivoDetails;
import auditoria.ArchivoErrorCodes;
import auditoria.logger.ArchivoLogger;
import auditoria.logger.DataLoggingEvent;
import auditoria.logger.IArchivoLogger;
import auditoria.logger.LoggingEvent;

import common.Constants;
import common.MotivoEliminacionUnidadInstalacion;
import common.bi.DefaultServiceSession;
import common.bi.GestionCuadroClasificacionBI;
import common.bi.GestionDescripcionBI;
import common.bi.GestionDocumentosElectronicosBI;
import common.bi.GestionDocumentosVitalesBI;
import common.bi.GestionEliminacionBI;
import common.bi.GestionSeriesBI;
import common.bi.GestionUnidadDocumentalBI;
import common.bi.ServiceRepository;
import common.bi.ServiceSession;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.DbDataSourceMultiEntidadImpl;
import common.db.ITransactionManager;
import common.db.TransactionManagerImpl;
import common.definitions.ArchivoActions;
import common.definitions.ArchivoModules;
import common.definitions.ArchivoObjects;
import common.exceptions.ConfigException;
import common.lang.MutableInt;
import common.util.CustomDateFormat;
import common.util.DateUtils;
import common.util.FileHelper;
import common.util.StringUtils;

import deposito.model.GestorEstructuraDepositoBI;
import deposito.vos.FormatoHuecoVO;
import deposito.vos.UDocEnUiDepositoVO;
import deposito.vos.UInsDepositoVO;
import descripcion.model.xml.card.TipoFicha;
import docelectronicos.TipoObjeto;
import fondos.model.ElementoCuadroClasificacion;
import fondos.vos.ElementoCuadroClasificacionVO;

/**
 * Clase que realiza en background el proceso de finalizacion de una
 * eliminacion.
 */
public class FinalizarEliminacionThread extends Thread {
	/** Appender donde se escribirá el log */
	private Appender appender = null;
	/** Directorio donde se ubicara el log */
	private String directorio = "c:\\";
	/** Extension de los fichero de log */
	private String logExtension = ".log";
	/** Eliminacion que se va a finalizar */
	private String idEliminacion = null;
	private ServiceClient serviceClient = null;
	private ThreadSelectionMap mapEliminaciones = null;

	/**
	 * Constructor
	 * 
	 * @param idEliminacion
	 *            Identificador de la eliminación
	 * @param serviceClient
	 *            serviceClient
	 */
	public FinalizarEliminacionThread(String idEliminacion,
			ServiceClient serviceClient, ThreadSelectionMap mapEliminaciones) {
		this.serviceClient = serviceClient;
		this.idEliminacion = idEliminacion;
		this.mapEliminaciones = mapEliminaciones;
	}

	/**
	 * Metodo para la creacion de un evento específico para el modulo de
	 * eliminacion de series
	 */
	private LoggingEvent getLogginEvent(IArchivoLogger auditLogger, int action,
			ServiceClient serviceClient) {
		LoggingEvent le = new LoggingEvent(ArchivoModules.FONDOS_MODULE,
				action, serviceClient, false);

		auditLogger.add(le);

		return le;
	}

	public void run() throws ConfigException {
		int numUdocsBorradas = 0;
		Set unidadesInstalacionAfectadas = new HashSet();

		EliminacionSerieVO eliminacion = null;
		ServiceRepository repository = null;
		GestionEliminacionBI serviceEliminacion = null;
		ServiceSession serviceSession = null;

		// Obtenemos la fecha actual formateada
		String fechaFormateada = CustomDateFormat.SDF_ELIMINACION_YYYYMMDD_HHMMSS
				.format(DateUtils.getFechaActual());
		Logger logger = Logger.getLogger(idEliminacion + Constants.STRING_SPACE
				+ fechaFormateada);

		try {

			DbDataSource dataSource = new DbDataSourceMultiEntidadImpl(
					serviceClient.getEngine());
			ITransactionManager txManager = new TransactionManagerImpl(
					dataSource);
			serviceSession = new DefaultServiceSession(serviceClient, txManager);

			// Obtenemos el repositorio con la informacion de sesion comun
			repository = ServiceRepository.getInstance(serviceSession);

			// Obtenemos el servicio de eliminacion
			serviceEliminacion = repository.lookupGestionEliminacionBI();

			// Obtener la eliminación
			eliminacion = serviceEliminacion.getEliminacion(idEliminacion);

			// Obtener el nombre del fichero
			ConfiguracionSistemaArchivo csa = ConfiguracionSistemaArchivoFactory
					.getConfiguracionSistemaArchivo();
			directorio = csa.getConfiguracionFondos()
					.getDirectorioLogsEliminacion();
			String nombreFichero = fechaFormateada + "-"
					+ eliminacion.getTitulo() + logExtension;

			String entity = null;
			try {
				entity = serviceSession.getSessionOwner().getEntity();
			} catch (Exception e) {
				entity = null;
			}

			if (StringUtils.isNotBlank(entity)) {
				nombreFichero = entity + "-" + nombreFichero;
			}

			String ficheroLog = FileHelper.getNormalizedToSystemFilePath(
					directorio, nombreFichero);

			appender = new FileAppender(new PatternLayout(
					"%r [%t] %-5p %x - %m\n"), ficheroLog);
			logger.addAppender(appender);
			logger.setLevel(org.apache.log4j.Level.ALL);

			// Comprobar el estado de la eliminación
			if (eliminacion.getEstado() != ValoracionConstants.ESTADO_ELIMINACION_DESTRUCCION_REALIZADA) {
				throw new EliminacionActionNotAllowedException(
						ArchivoErrorCodes.ERROR_ELIMINACION_DESTRUCCION_NO_REALIZADA);
			}

			if (mapEliminaciones.existeEliminacion(idEliminacion)) {
				throw new EliminacionActionNotAllowedException(
						ArchivoErrorCodes.ERROR_ELIMINACION_THREAD_EJECUTANDOSE);
			} else {
				mapEliminaciones.insertarEliminacion(idEliminacion);
			}

			// Abrimos la transaccion
			serviceSession.getTransactionManager().iniciarTransaccion();

			GestionUnidadDocumentalBI serviceUdoc = repository
					.lookupGestionUnidadDocumentalBI();
			GestionDescripcionBI serviceDescripcion = repository
					.lookupGestionDescripcionBI();
			GestionCuadroClasificacionBI serviceCuadro = repository
					.lookupGestionCuadroClasificacionBI();
			GestionDocumentosElectronicosBI serviceDocumentos = repository
					.lookupGestionDocumentosElectronicosBI();
			GestorEstructuraDepositoBI serviceEstructura = repository
					.lookupGestorEstructuraDepositoBI();
			GestionSeriesBI serviceSeries = repository.lookupGestionSeriesBI();
			GestionDocumentosVitalesBI docVitalesBI = repository
					.lookupGestionDocumentosVitalesBI();

			Transformer uiIDExtractor = new Transformer() {
				public Object transform(Object obj) {
					return ((UDocEnUiDepositoVO) obj).getIduinstalacion();
				}
			};

			// Obtenemos las udocs asociadas a la eliminacion
			UnidadesDocumentalesEliminacionVO unidadesDocumentales = serviceEliminacion
					.getUnidadesEliminacion(eliminacion,
							eliminacion.getIdArchivo(), null, false);
			Collection udocs = unidadesDocumentales.getListaUnidades();
			eliminacion
					.setEstado(ValoracionConstants.ESTADO_ELIMINACION_PENDIENTE_FINALIZACION);
			eliminacion.setFechaEstado(DBUtils.getFechaActual());
			eliminacion.setFechaFinalizacion(DBUtils.getFechaActual());

			serviceEliminacion.actualizarEliminacion(eliminacion);

			List partesUdoc = null;
			Map cambiosVolumenSerie = new HashMap();

			List unidadesTratadas = new ArrayList();
			for (Iterator itUdocs = udocs.iterator(); itUdocs.hasNext();) {
				UnidadDocumentalEliminacionVO udocEliminada = (UnidadDocumentalEliminacionVO) itUdocs
						.next();

				if (!unidadesTratadas.contains(udocEliminada.getIdudoc())) {
					unidadesTratadas.add(udocEliminada.getIdudoc());

					logger.info("***************************   UNIDAD DOCUMENTAL A ELIMINAR   ******************************");
					ElementoCuadroClasificacionVO elementoCuadro = serviceCuadro
							.getElementoCuadroClasificacion(udocEliminada
									.getIdudoc());
					logger.info(udocEliminada.getTitulo() + " - "
							+ udocEliminada.getIdudoc());
					logger.info("*******************************************************************************************");

					HistoricoUDOCVO historicoUDOC = new HistoricoUDOCVO();
					historicoUDOC.setCodRefUdoc(udocEliminada
							.getCodreferencia());
					historicoUDOC.setIdEliminacion(eliminacion.getId());
					historicoUDOC.setIdUDOC(udocEliminada.getIdudoc());
					historicoUDOC.setSignaturaUdoc(udocEliminada.getCodigo());
					historicoUDOC.setTituloUdoc(udocEliminada.getTitulo());
					historicoUDOC.setNumExpUdoc(udocEliminada
							.getExpedienteudoc());
					historicoUDOC.setFechaInicial(DateUtils
							.formatDate(udocEliminada.getFechaIniUdoc()));
					historicoUDOC.setFechaFinal(DateUtils
							.formatDate(udocEliminada.getFechaFinUdoc()));
					// historicoUDOC.setDatosDescrUdoc(serviceDescripcion.componerFichaElemento(elementoCuadro,
					// TipoAcceso.CONSULTA).toString());

					serviceEliminacion.crearHistoricoUDOC(historicoUDOC);
					logger.info("COMPLETADA INCORPORACIÓN DE UNIDAD DOCUMENTAL AL HISTÓRICO");

					if (elementoCuadro.tieneDescripcion()) {
						serviceDescripcion.deleteFicha(elementoCuadro.getId(),
								TipoFicha.FICHA_ELEMENTO_CF);
						logger.info("COMPLETADA ELIMINACIÓN DE DATOS DESCRIPTIVOS DE LA UNIDAD DOCUMENTAL");
					}

					serviceDocumentos.deleteDocumentos(elementoCuadro.getId(),
							TipoObjeto.ELEMENTO_CF);
					logger.info("COMPLETADA ELIMINACIÓN DE DOCUMENTOS ELECTRÓNICOS ASOCIADOS A LA UNIDAD DOCUMENTAL");

					// Eliminamos las referencias a documentos vitales
					docVitalesBI.eliminaVinculosExpediente(
							udocEliminada.getExpedienteudoc(),
							udocEliminada.getCodsistproductor());
					logger.info("COMPLETADA ELIMINACIÓN DE VÍNCULOS DE DOCUMENTOS VITALES ASOCIADOS A LA UNIDAD DOCUMENTAL");

					// Eliminar las tareas de captura relacionadas
					serviceDocumentos.eliminarTareas(elementoCuadro.getId(),
							ElementoCuadroClasificacion.TIPO_UNIDAD_DOCUMENTAL);
					logger.info("COMPLETADA ELIMINACIÓN DE TAREAS DE CAPTURA ASOCIADAS A LA UNIDAD DOCUMENTAL");

					partesUdoc = serviceUdoc.getUdocsEnUI(elementoCuadro
							.getId());
					CollectionUtils.transform(partesUdoc, uiIDExtractor);
					unidadesInstalacionAfectadas.addAll(partesUdoc);
					serviceUdoc.desinstalarUnidadDocumental(elementoCuadro
							.getId());
					logger.info("UNIDAD DOCUMENTAL DESINSTALADA");

					serviceUdoc.eliminarUdoc(udocEliminada);
					logger.info("UNIDAD DOCUMENTAL ELIMINADA DEL CUADRO DE CLASIFICACIÓN DE FONDOS DOCUMENTALES");

					numUdocsBorradas++;
					logger.info("********************** ELIMINACIÓN DE UNIDAD DOCUMENTAL COMPLETADA ************************");

					MutableInt numUdocs = (MutableInt) cambiosVolumenSerie
							.get(udocEliminada.getTipoDocumental());
					if (numUdocs == null)
						cambiosVolumenSerie.put(
								udocEliminada.getTipoDocumental(),
								new MutableInt(1));
					else
						numUdocs.incrementValue();
				}
			}

			int numUnidadesInstalacion = 0;
			// int volumenEliminado = 0;
			double volumenEliminado = 0;
			logger.info("***********************   UNIDADES DE INSTALACIÓN AFECTADAS   ****************************");
			// Comprobamos las unidades de instalacion distintas y si son vacias
			// las eliminamos
			for (Iterator itUnidades = unidadesInstalacionAfectadas.iterator(); itUnidades
					.hasNext();) {
				String idUnidadInstalacion = (String) itUnidades.next();

				List udocsEnUI = serviceUdoc
						.getUdocsEnUnidadInstalacion(idUnidadInstalacion);
				if (udocsEnUI == null || udocsEnUI.size() == 0) {
					UInsDepositoVO unidadInstalacion = serviceEstructura
							.getUinsEnDeposito(idUnidadInstalacion);

					if (unidadInstalacion == null) {
						unidadInstalacion = new UInsDepositoVO();
						unidadInstalacion.setId(idUnidadInstalacion);
					}

					logger.info("LA UNIDAD DE INSTALACIÓN CON IDENTIFICADOR: "
							+ idUnidadInstalacion
							+ " ESTÁ VACIA Y SE PROCEDE A SU ELIMINACIÓN");
					FormatoHuecoVO formato = serviceEstructura
							.getFormatoHueco(unidadInstalacion.getIdformato());
					// volumenEliminado += formato.getLongitud().intValue();

					if (formato != null) {
						if (formato.getLongitud() == null
								|| formato.getLongitud().doubleValue() == Double.MIN_VALUE)
							volumenEliminado += serviceEstructura
									.getLongitudHuecosIrregularesXIdUInstalacion(idUnidadInstalacion);
						else
							volumenEliminado += formato.getLongitud()
									.doubleValue();
					}

					serviceEstructura.deleteUInstDeposito(
							eliminacion.getIdArchivo(), unidadInstalacion,
							MotivoEliminacionUnidadInstalacion.SELECCION);
					logger.info("UNIDAD DE INSTALACIÓN ELIMINADA: "
							+ unidadInstalacion.getSignaturaui());

					serviceEstructura
							.liberaUnidadInstalacion(idUnidadInstalacion);
					logger.info("HUECO OCUPADO POR LA INSTALACIÓN ELIMINADA LIBERADO");

					logger.info("******************** ELIMINACIÓN DE UNIDAD DE INSTALACIÓN COMPLETADA **********************");

					numUnidadesInstalacion++;
				}
				// else {
				// logger.info("LA UNIDAD DE INSTALACIÓN CON IDENTIFICADOR: "+idUnidadInstalacion+" TODAVIA CONTIENE DOCUMENTACIÓN");
				// UInsDepositoVO uinstVO =
				// depositoBI.getUinsEnDeposito(idUnidadInstalacion);
				// depositoBI.organizarUDocsEnUInst(udocsEnUI, null,
				// uinstVO.getSignaturaUI());
				// }
			}

			logger.info("***************************   ACTUALIZACIÓN DATOS SERIE   *********************************");
			logger.info("*******************************************************************************************");
			serviceSeries.updateVolumenSerieNoTransaccional(eliminacion
					.getIdSerie());
			logger.info("DECREMENTADO NUMERO DE UNIDADES DOCUMENTALES: "
					+ numUdocsBorradas);
			logger.info("DECREMENTADO NUMERO DE UNIDADES DE INSTALACION: "
					+ numUnidadesInstalacion + ", LO QUE EQUIVALE A: "
					+ volumenEliminado + " CM.");
			String tipoDocumental = null;
			MutableInt volumenTipo = null;
			for (Iterator i = cambiosVolumenSerie.keySet().iterator(); i
					.hasNext();) {
				tipoDocumental = (String) i.next();
				volumenTipo = (MutableInt) cambiosVolumenSerie
						.get(tipoDocumental);
				serviceSeries.updateContenidoSerieNoTransaccional(eliminacion
						.getIdSerie());
				logger.info("VOLUMEN DE LA SERIE ACTUALIZADO. TIPO DOCUMENTAL: "
						+ tipoDocumental + " - " + volumenTipo.getValue());
			}

			// Actualizar fechas extremas de la serie
			serviceSeries.updateFechasExtremas(eliminacion.getIdSerie());

			// Actualizamos los datos de la eliminacion
			eliminacion
					.setEstado(ValoracionConstants.ESTADO_ELIMINACION_FINALIZADA);
			eliminacion.setFechaEstado(DBUtils.getFechaActual());
			eliminacion.setFechaFinalizacion(DBUtils.getFechaActual());

			serviceEliminacion.actualizarEliminacion(eliminacion);

			// Cerramos la transaccion
			serviceSession.getTransactionManager().commit();

			// Generar la pista de auditoría
			IArchivoLogger auditLogger = ArchivoLogger.getLogger(
					this.getClass(), dataSource);

			LoggingEvent event = getLogginEvent(auditLogger,
					ArchivoActions.FONDOS_MODULE_FINALIZAR_ELIMINACION,
					serviceClient);
			DataLoggingEvent logData = event.getDataLoggingEvent(
					ArchivoObjects.OBJECT_ELIMINACION, idEliminacion);

			logData.addDetalle(serviceClient.getLocale(),
					ArchivoDetails.SELECCION_COD_SELECCION,
					eliminacion.getTitulo());

			auditLogger.log();
		} catch (EliminacionActionNotAllowedException eana) {
			logger.info("*******************************************************************************************");
			logger.info("NO SE HA PODIDO FINALIZAR LA ELIMINACIÓN "
					+ idEliminacion + Constants.STRING_SPACE
					+ ExceptionMapper.getErrorsMessage(eana));
			logger.info("*******************************************************************************************");
		} catch (Throwable t) {
			logger.info("*******************************************************************************************");
			logger.info(
					"SE HA PRODUCIDO UN ERROR DURANTE EL PROCESO DE ELIMINACIÓN",
					t);

			// Cerramos la transaccion
			if (serviceSession != null) {
				serviceSession.getTransactionManager().rollback();

				if ((eliminacion != null) && (serviceEliminacion != null)) {
					try {
						// ServiceRepository repository =
						// ServiceRepository.getInstance(serviceSession);
						// GestionEliminacionBI serviceEliminacion =
						// repository.lookupGestionEliminacionBI();

						// Actualizar el estado de la selección
						EliminacionSerieVO eliminacionSerieVO = serviceEliminacion
								.abrirEliminacion(eliminacion.getId(), false);
						StringBuffer notas = new StringBuffer();
						notas.append(eliminacionSerieVO.getNotas())
								.append(" - ")
								.append(DateUtils.formatTimestamp(DateUtils
										.getFechaActual()))
								.append(": SE HA PRODUCIDO UN ERROR DURANTE EL PROCESO DE ELIMINACIÓN");
						eliminacionSerieVO.setNotas(notas.toString());

						serviceEliminacion
								.actualizarEliminacion(eliminacionSerieVO);
					} catch (Throwable t2) {
						// serviceSession.getTransactionManager().rollback();
						logger.info("ERROR AL VOLVER AL ESTADO ANTERIOR", t2);
					}
				}

				logger.info("*******************************************************************************************");
			}
		} finally {
			// Se elimina la eliminación del contexto
			mapEliminaciones.borrarEliminacion(idEliminacion);

			// Cerramos el appender
			if (appender != null) {
				appender.close();
				logger.removeAppender(appender);
			}

			// Liberar la conexión
			if (serviceSession != null) {
				serviceSession.getTransactionManager().liberarConexion();
			}
		}
	}
}
