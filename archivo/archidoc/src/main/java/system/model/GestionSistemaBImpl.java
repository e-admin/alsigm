package system.model;

import gcontrol.db.IArchivoDbEntity;
import gcontrol.vos.ArchivoVO;

import java.util.List;
import java.util.Locale;

import salas.model.GestionSalasConsultaBI;
import se.usuarios.AppPermissions;
import se.usuarios.AppUser;
import se.usuarios.ServiceClient;
import xml.config.ConfiguracionGeneral;
import xml.config.ConfiguracionServicios;
import xml.config.ConfiguracionSistemaArchivoFactory;
import auditoria.ArchivoDetails;
import auditoria.ArchivoErrorCodes;
import auditoria.logger.DataLoggingEvent;
import auditoria.logger.LoggingEvent;

import common.ConfiguracionParametrosConstants;
import common.Constants;
import common.bi.GestionConsultasBI;
import common.bi.GestionDocumentosElectronicosBI;
import common.bi.GestionDocumentosVitalesBI;
import common.bi.GestionEliminacionBI;
import common.bi.GestionFraccionSerieBI;
import common.bi.GestionPrestamosBI;
import common.bi.GestionPrevisionesBI;
import common.bi.GestionRelacionesEntregaBI;
import common.bi.GestionSeriesBI;
import common.bi.GestionSistemaBI;
import common.bi.GestionValoracionBI;
import common.bi.ServiceBase;
import common.bi.ServiceRepository;
import common.definitions.ArchivoActions;
import common.definitions.ArchivoModules;
import common.exceptions.ActionNotAllowedException;
import common.exceptions.SecurityException;
import common.security.DocumentosElectronicosSecurityManager;
import common.security.DocumentosVitalesSecurityManager;
import common.security.FondosSecurityManager;
import common.security.SecurityManagerBase;
import common.security.SecurityManagerLocator;
import common.security.ServiciosSecurityManager;
import common.security.TransferenciasSecurityManager;
import common.vos.BandejaActividadesVO;

import descripcion.db.IValidacionDBEntity;

/**
 * Bussines con los metodos de negocio para el modulo de sistema.
 */
public class GestionSistemaBImpl extends ServiceBase implements
		GestionSistemaBI {

	IArchivoDbEntity _archivoDBEntity = null;
	IValidacionDBEntity _valoresTablaValidacionDBEntity = null;

	public GestionSistemaBImpl(IArchivoDbEntity archivoDBEntity,
			IValidacionDBEntity valoresTablaValidacionDBEntity) {
		_archivoDBEntity = archivoDBEntity;
		_valoresTablaValidacionDBEntity = valoresTablaValidacionDBEntity;
	}

	/**
	 * Registra el intento de acceso de un usuario al sistema
	 * 
	 * @param identifier
	 *            Identificador del usuario
	 * @param ip
	 *            Dirección IP desde la que el usuario accede al sistema
	 * @param isOk
	 *            <b>true</b> si el acceso al sistema ha sido garantizado por el
	 *            subsistema de autentificación y control de acceso y
	 *            <b>false</b> en caso contrario
	 */
	public void doLogin(String identifier, String ip, boolean isOk) {
		// Generamos el evento
		LoggingEvent event = getLogginEvent(isOk ? ArchivoActions.SISTEMA_MODULE_LOGIN_OK
				: ArchivoActions.SISTEMA_MODULE_LOGIN_FAIL);
		event.getUser().setIp(ip);
		event.getUser().setId(identifier);
		Locale locale = getServiceClient().getLocale();

		// Si el identificador es nulo->registramos el login en los datos
		// adjuntos
		if (!isOk) {
			DataLoggingEvent data = new DataLoggingEvent();

			data.addDetalle(locale, ArchivoDetails.LOGIN_FAIL, identifier);
			event.setCodError(ArchivoErrorCodes.ERROR_LOGIN_NO_VALIDO);
			event.getData().add(data);
		}

		// Registramos el evento
		getLogger().add(event);
	}

	/**
	 * Registra la salida de un usuario del sistema
	 */
	public void doLogout() {
		// Generamos el evento
		LoggingEvent event = getLogginEvent(ArchivoActions.SISTEMA_MODULE_LOGOUT_OK);

		getLogger().add(event);
	}

	/**
	 * Metodo para la creacion de un evento específico para el modulo de sistema
	 */
	public LoggingEvent getLogginEvent(int action) {
		return new LoggingEvent(ArchivoModules.SISTEMA_MODULE, action,
				getServiceClient(), true);
	}

	/**
	 * Obtiene la lista de soportes de documentación que están actualmente
	 * definidos en el sistema
	 * 
	 * @return Lista de soportes {@link system.vos.SoporteVO}
	 */
	public List getListaSoportes() {
		ConfiguracionGeneral config = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionGeneral();
		return _valoresTablaValidacionDBEntity.getValoresTablaValidacion(config
				.getListaValidacionSoportes());
	}

	/**
	 * Obtiene la lista de formtos de documento que están actualmente definidos
	 * en el sistema
	 * 
	 * @return Lista de valores con los formatos de documento
	 *         {@link descripcion.vos.TextoTablaValidacionVO}
	 */
	public Object getListaFormatosDocumento() {
		ConfiguracionGeneral config = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionGeneral();
		return _valoresTablaValidacionDBEntity.getValoresTablaValidacion(config
				.getListaValidacionFormatos());
	}

	/**
	 * Obtiene la lista de archivos cuya gestión está siendo realizada por el
	 * sistema
	 * 
	 * @return Lista de archivos
	 */
	public List getArchivos() {
		return _archivoDBEntity.getAll();
	}

	/**
	 * Obtiene la informacion referente a un archivo
	 * 
	 * @param idArchivoReceptor
	 *            Identificador del archivo
	 * @return Datos del archivo {@link ArchivoVO}
	 */
	public ArchivoVO getArchivo(String idArchivoReceptor) {
		return _archivoDBEntity.getArchivoXId(idArchivoReceptor);
	}

	/**
	 * Obtiene por codigo de archivo. En caso de exsitri mas de uno devolvera el
	 * primero
	 * 
	 * @param codigoArchivoReceptor
	 * @return Datos del archivo {@link ArchivoVO}
	 */
	public ArchivoVO getArchivoXCodigo(String codigoArchivoReceptor) {
		return _archivoDBEntity.getArchivoXCodArchivo(codigoArchivoReceptor);
	}

	/**
	 * Construye un resumen de las actividades que tiene pendientes un usuario
	 * 
	 * @param userVO
	 *            Usuario para el que se solicitan la actividades pendientes
	 * @return Resumen de las actividades pendientes del usuario
	 */
	public BandejaActividadesVO getActividadesUsuario(AppUser userVO) {
		ServiceClient userAsServiceClient = ServiceClient.create(userVO);
		ServiceRepository services = ServiceRepository
				.getInstance(userAsServiceClient);
		SecurityManagerBase securityManager = SecurityManagerLocator
				.loockupTransferenciasSM();

		BandejaActividadesVO actividadesUsuario = new BandejaActividadesVO(
				userVO);

		GestionPrevisionesBI previsionBI = services
				.lookupGestionPrevisionesBI();
		try {
			securityManager
					.check(TransferenciasSecurityManager.GESTION_PREVISION_EN_ORGANO_REMITENTE,
							userAsServiceClient);
			int previsionesEnElaboracion = previsionBI
					.getCountPrevisionesEnElaboracion(userVO.getId());
			actividadesUsuario
					.setPrevisionesEnElaboracion(previsionesEnElaboracion);
		} catch (SecurityException se) {
		}

		try {
			securityManager
					.check(TransferenciasSecurityManager.GESTION_PREVISION_EN_ORGANO_REMITENTE,
							userAsServiceClient);
			int previsionesFinalizadas = previsionBI
					.getCountPrevisionesAceptadasRechazadas(userVO.getId());
			actividadesUsuario
					.setPrevisionesAceptadasORechazadas(previsionesFinalizadas);
		} catch (SecurityException se) {
		}

		try {
			// transferenciasSecurityManager.check(TransferenciasSecurityManager.GESTION_PREVISION_EN_ARCHIVO_RECEPTOR,
			// userAsServiceClient);
			int previsionesAGestionar = previsionBI
					.getCountPrevisionesAGestionar();
			actividadesUsuario.setPrevisionesAGestionar(previsionesAGestionar);
		} catch (SecurityException se) {
		} catch (ActionNotAllowedException anae) {
		}

		GestionRelacionesEntregaBI relacionBI = services
				.lookupGestionRelacionesBI();
		try {
			securityManager
					.check(TransferenciasSecurityManager.GESTION_RELACION_EN_ORGANO_REMITENTE,
							userAsServiceClient);
			int relacionesEnElaboracion = relacionBI
					.getCountRelacionesEnElaboracionXUser(userVO.getId());
			actividadesUsuario
					.setRelacionesEnElaboracion(relacionesEnElaboracion);
		} catch (SecurityException se) {
		}

		try {
			securityManager
					.check(TransferenciasSecurityManager.GESTION_RELACION_EN_ORGANO_REMITENTE,
							userAsServiceClient);
			int relacionesFinalizadas = relacionBI
					.getCountRelacionesFinalizadasXUser(userVO.getId());
			actividadesUsuario.setRelacionesFinalizadas(relacionesFinalizadas);
		} catch (SecurityException se) {
		}

		try {
			securityManager
					.check(TransferenciasSecurityManager.GESTION_RELACION_EN_ORGANO_REMITENTE,
							userAsServiceClient);
			int relacionesRechazadas = relacionBI
					.getCountRelacionesRechazadasXUser(userVO.getId());
			actividadesUsuario.setRelacionesRechazadas(relacionesRechazadas);
		} catch (SecurityException se) {
		}

		try {
			// transferenciasSecurityManager.check(TransferenciasSecurityManager.GESTION_RELACION_EN_ARCHIVO_RECEPTOR,
			// userAsServiceClient);
			int relacionesAGestionar = relacionBI
					.getCountRelacionesAGestionar();
			actividadesUsuario.setRelacionesAGestionar(relacionesAGestionar);
		} catch (SecurityException se) {
		} catch (ActionNotAllowedException anae) {
		}

		try {
			// transferenciasSecurityManager.check(TransferenciasSecurityManager.GESTION_SOLICITUD_RESERVA,
			// userAsServiceClient);
			int reservasPendientes = relacionBI.getCountRelacionesAReservar();
			actividadesUsuario.setReservasPendientes(reservasPendientes);
		} catch (SecurityException se) {
		} catch (ActionNotAllowedException anae) {
		}

		try {
			securityManager.check(
					TransferenciasSecurityManager.UBICAR_RELACION,
					userAsServiceClient);
			int relacionesAUbicar = relacionBI.getCountRelacionesAUbicar(userVO
					.getIdsArchivosUser());
			actividadesUsuario.setRelacionesAUbicar(relacionesAUbicar);
		} catch (SecurityException se) {
		}

		try {
			securityManager
					.check(TransferenciasSecurityManager.ELABORACION_INGRESOS_DIRECTOS,
							userAsServiceClient);
			int ingresosEnElaboracion = relacionBI
					.getCountIngresosEnElaboracionXUser(userVO.getId());
			actividadesUsuario.setIngresosEnElaboracion(ingresosEnElaboracion);
		} catch (SecurityException se) {
		}

		try {
			securityManager
					.check(TransferenciasSecurityManager.ELABORACION_INGRESOS_DIRECTOS,
							userAsServiceClient);
			int ingresosFinalizados = relacionBI
					.getCountIngresosFinalizadosXUser(userVO.getId());
			actividadesUsuario.setIngresosFinalizados(ingresosFinalizados);
		} catch (SecurityException se) {
		}

		securityManager = SecurityManagerLocator.loockupServiciosSM();
		GestionPrestamosBI prestamoBI = services.lookupGestionPrestamosBI();
		try {
			securityManager.check(
					ServiciosSecurityManager.PRESTAMOS_X_SOLICITANTE_ACTION,
					userAsServiceClient);
			int prestamosEnElaboracion = prestamoBI
					.getCountPrestamosEnElaboracionXIdUsuario(userVO.getId());
			actividadesUsuario
					.setPrestamosEnElaboracion(prestamosEnElaboracion);
		} catch (SecurityException se) {
		}

		try {
			// TODO Comprobar cuando entra por aquí cuando esté claro el tema de
			// permisos
			securityManager.check(
					ServiciosSecurityManager.PRESTAMOS_GESTION_ACTION,
					userAsServiceClient);

			if (userVO.isPersonalArchivo()) {
				int prestamosAGestionar = prestamoBI
						.getCountListadoGestionar(userAsServiceClient
								.getIdsArchivosUser());
				actividadesUsuario.setPrestamosAGestionar(prestamosAGestionar);
			}
		} catch (SecurityException se) {
		}

		try {
			// TODO Comprobar cuando entra por aquí cuando esté claro el tema de
			// permisos
			securityManager.check(
					ServiciosSecurityManager.PRESTAMOS_GESTION_ACTION,
					userAsServiceClient);

			if (userVO.isPersonalArchivo()) {
				int prestamosAGestionarReserva = prestamoBI
						.getCountListadoGestionarReserva();
				actividadesUsuario
						.setPrestamosAGestionarReserva(prestamosAGestionarReserva);
			}
		} catch (SecurityException se) {
		}

		try {
			securityManager.check(
					ServiciosSecurityManager.ENTREGAR_PRESTAMO_ACTION,
					userAsServiceClient);

			if (userAsServiceClient.isPersonalArchivo()) {
				int prestamosAEntregar = prestamoBI
						.getCountListadoEntregar(userAsServiceClient
								.getIdsArchivosUser());
				actividadesUsuario.setPrestamosAEntregar(prestamosAEntregar);
			}
		} catch (SecurityException se) {
		}

		GestionConsultasBI consultaBI = services.lookupGestionConsultasBI();
		if (userVO.hasPermission(AppPermissions.ESTANDAR_SOLICITUD_CONSULTAS)) {
			int consultasEnElaboracion = consultaBI
					.getCountConsultasAbiertasXUsuarioConsultor(userVO.getId());
			actividadesUsuario
					.setConsultasEnElaboracion(consultasEnElaboracion);
		}

		try {
			securityManager.check(
					ServiciosSecurityManager.CONSULTAS_GESTION_ACTION,
					userAsServiceClient);

			if (userVO.isPersonalArchivo()) {
				int consultasAGestionar = consultaBI
						.getCountListadoConsultasGestionar(userAsServiceClient);
				actividadesUsuario.setConsultasAGestionar(consultasAGestionar);
			}
		} catch (SecurityException se) {
		}

		try {
			securityManager.check(
					ServiciosSecurityManager.CONSULTAS_GESTION_ACTION,
					userAsServiceClient);

			if (userVO.isPersonalArchivo()) {
				int consultasAGestionarReserva = consultaBI
						.getCountListadoConsultasGestionarReserva(userAsServiceClient);
				actividadesUsuario
						.setConsultasAGestionarReserva(consultasAGestionarReserva);
			}
		} catch (SecurityException se) {
		}

		try {
			securityManager.check(
					ServiciosSecurityManager.CONSULTAS_ENTREGAR_ACTION,
					userAsServiceClient);
			if (userVO.isPersonalArchivo()) {
				int consultasAEntregar = consultaBI
						.getCountListadoEntregar(userAsServiceClient
								.getIdsArchivosUser());
				actividadesUsuario.setConsultasAEntregar(consultasAEntregar);
			}
		} catch (SecurityException se) {
		}

		// documentación de unidades documentales a revisar
		try {
			securityManager.check(
					ServiciosSecurityManager.REVISION_DOCUMENTACION_UDOC,
					userAsServiceClient);
			int documentacionUDocsRevisar = prestamoBI
					.getCountDocumentacionUDocsARevisar(userVO.getId());
			actividadesUsuario
					.setDocumentacionUDocsRevisar(documentacionUDocsRevisar);
		} catch (SecurityException se) {
		}

		securityManager = SecurityManagerLocator.lookupFondosSecurityManager();
		GestionSeriesBI serieBI = services.lookupGestionSeriesBI();
		GestionValoracionBI valoracionBI = services.lookupGestionValoracionBI();

		try {
			securityManager.check(
					FondosSecurityManager.SOLICITUD_ALTA_SERIE_ACTION,
					userAsServiceClient);
			int solicitudes = serieBI.getCountSolicitudesGestor(userVO.getId());
			actividadesUsuario.setSolicitudesPendientes(solicitudes);
		} catch (SecurityException se) {
		}

		if (userVO.hasPermission(AppPermissions.GESTOR_SERIE)) {
			int seriesAIdentificar = serieBI
					.getCountSeriesEnIdentificacion(userVO.getId());
			actividadesUsuario.setSeriesEnIdentificacion(seriesAIdentificar);
			int valoraciones = valoracionBI
					.getCountValoracionesEnElaboracion(userVO.getId());
			actividadesUsuario.setValoracionesEnElaboracion(valoraciones);
		}
		if (userVO.hasPermission(AppPermissions.SOLICITUD_ALTA_SERIE)
				|| userVO.hasPermission(AppPermissions.GESTOR_SERIE)) {
			int solicitudes = serieBI.getCountSolicitudesGestor(userVO.getId());
			actividadesUsuario.setSolicitudesPendientes(solicitudes);
		}

		try {
			securityManager.check(
					FondosSecurityManager.GESTION_SOLICITUDES_SERIE_ACTION,
					userAsServiceClient);
			int solicitudesAGestionar = serieBI.getCountSolicitudesAGestionar();
			actividadesUsuario.setSolicitudesAGestionar(solicitudesAGestionar);
		} catch (SecurityException se) {
		}

		if (userVO.hasPermission(AppPermissions.GESTION_VALORACIONES)) {
			int valoracionesAGestionar = valoracionBI
					.getCountValoracionesAGestionar(userVO.getId());
			actividadesUsuario
					.setValoracionesAGestionar(valoracionesAGestionar);
		}

		GestionEliminacionBI seleccionBI = services
				.lookupGestionEliminacionBI();
		try {
			securityManager.check(
					FondosSecurityManager.CREAR_ELIMINACION_ACTION,
					userAsServiceClient);
			int seleccionesEnCurso = seleccionBI
					.getCountEliminacionesEnElaboracion(userVO.getId(),
							userVO.getIdsArchivosUser());
			actividadesUsuario.setSeleccionesEnElaboracion(seleccionesEnCurso);
		} catch (SecurityException se) {
		}

		// Documentos vitales a gestionar
		GestionDocumentosVitalesBI docsVitalesBI = services
				.lookupGestionDocumentosVitalesBI();
		DocumentosVitalesSecurityManager docVitSecurity = SecurityManagerLocator
				.lookupDocumentosVitalesSM();
		try {
			docVitSecurity
					.check(DocumentosVitalesSecurityManager.EDICION_DOCUMENTOS_VITALES_ACTION,
							userAsServiceClient);
			int documentosVitalesAGestionar = docsVitalesBI
					.getCountDocumentosVitalesAGestionar();
			actividadesUsuario
					.setDocumentosVitalesAGestionar(documentosVitalesAGestionar);
		} catch (SecurityException se) {
		}

		// tareas a gestionar
		GestionDocumentosElectronicosBI documentosElectronicos = services
				.lookupGestionDocumentosElectronicosBI();
		DocumentosElectronicosSecurityManager docSecurity = SecurityManagerLocator
				.loockupDocumentosElectronicosSM();
		try {
			docSecurity
					.check(DocumentosElectronicosSecurityManager.ADMINISTRACION_TAREAS_CAPTURA_ACTION,
							userAsServiceClient);
			int tareasAGestionar = documentosElectronicos
					.getCountTareasAGestionar();
			actividadesUsuario.setTareasAGestionar(tareasAGestionar);
		} catch (SecurityException se) {
		}

		try {
			docSecurity
					.check(DocumentosElectronicosSecurityManager.CAPTURA_DOCUMENTOS_ACTION,
							userAsServiceClient);
			int tareasPendientes = documentosElectronicos
					.getCountTareasPendientes();
			actividadesUsuario.setTareasPendientes(tareasPendientes);
		} catch (SecurityException se) {
		}

		GestionFraccionSerieBI fraccionSerieBI = services
				.lookupGestionFraccionSerieBI();

		// divisiones de fracciones de serie en elaboración
		try {
			securityManager.check(FondosSecurityManager.ALTA_ELEMENTO_ACTION,
					userAsServiceClient);
			int divisionesFSEnElaboracion = fraccionSerieBI
					.getCountDivisionesFSEnElaboracion();
			actividadesUsuario
					.setDivisionesFSEnElaboracion(divisionesFSEnElaboracion);
		} catch (SecurityException se) {
		}

		// divisiones de fracciones de serie finalizadas
		try {
			securityManager.check(FondosSecurityManager.ALTA_ELEMENTO_ACTION,
					userAsServiceClient);
			int divisionesFSFinalizadas = fraccionSerieBI
					.getCountDivisionesFSFinalizadas();
			actividadesUsuario
					.setDivisionesFSFinalizadas(divisionesFSFinalizadas);
		} catch (SecurityException se) {
		}

		// Registros de consulta en sala abiertos
		GestionSalasConsultaBI salasBI = services.lookupGestionSalasBI();
		if (userVO.hasPermission(AppPermissions.REGISTRO_ASISTENCIA_SALAS)) {
			String mostrarSalas = ConfiguracionSistemaArchivoFactory
					.getConfiguracionSistemaArchivo()
					.getConfiguracionParametros()
					.getValor(ConfiguracionParametrosConstants.MOSTRAR_SALAS);
			if (Constants.TRUE_STRING.equals(mostrarSalas)) {
				int registrosAbiertos = salasBI.getCountRegistrosAbiertos();
				actividadesUsuario.setRegistrosAbiertos(registrosAbiertos);
			}
		}

		return actividadesUsuario;
	}

	/**
	 * Obtiene la lista de posibles tipos de entrega de unidades documentales
	 * para préstamos y consultas que están actualmente definidos en el sistema
	 * 
	 * @return Lista de tipos de entrega
	 *         {@link descripcion.vos.TextoTablaValidacionVO}
	 */
	public List getListaTiposEntrega() {
		ConfiguracionServicios config = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionServicios();
		return _valoresTablaValidacionDBEntity.getValoresTablaValidacion(config
				.getListaValidacionTipoEntrega());
	}

}
