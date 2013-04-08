package fondos.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import auditoria.ArchivoDetails;
import auditoria.logger.DataLoggingEvent;
import auditoria.logger.LoggingEvent;

import common.bi.GestionCuadroClasificacionBI;
import common.bi.GestionPrevisionesBI;
import common.bi.GestionRelacionesEntregaBI;
import common.bi.IServiceBase;
import common.bi.ServiceRepository;
import common.definitions.ArchivoActions;
import common.definitions.ArchivoModules;
import common.definitions.ArchivoObjects;
import common.util.ArrayUtils;
import common.util.DateUtils;

import fondos.vos.DivisionFraccionSerieVO;
import fondos.vos.ElementoCuadroClasificacionVO;
import fondos.vos.SerieVO;
import fondos.vos.TablaTemporalFondosVO;

public class AuditFondos {

	private static AuditFondos instancia = new AuditFondos();

	private static LoggingEvent getLogginEvent(IServiceBase serviceToAudit,
			int action) {

		LoggingEvent event = new LoggingEvent(ArchivoModules.FONDOS_MODULE,
				action, serviceToAudit.getServiceClient(), false);
		event.setServiceToAudit(serviceToAudit);

		serviceToAudit.getLogger().add(event);

		return event;
	}

	public static LoggingEvent getLogginEventAlta(IServiceBase serviceToAudit) {
		return getLogginEvent(serviceToAudit,
				ArchivoActions.FONDOS_MODULE_ALTA_ELEMENTO);
	}

	public static LoggingEvent getLogginEventUpdate(IServiceBase serviceToAudit) {
		return getLogginEvent(serviceToAudit,
				ArchivoActions.FONDOS_MODULE_MODIFICACION_ELEMENTO);
	}

	public static LoggingEvent getLogginEventRemove(
			IServiceBase serviceToAudit,
			ElementoCuadroClasificacionVO elementoCF) {
		if (elementoCF.isTipoSerie())
			return getLogginEvent(serviceToAudit,
					ArchivoActions.FONDOS_MODULE_BAJA_SERIE);
		else
			return getLogginEvent(serviceToAudit,
					ArchivoActions.FONDOS_MODULE_BAJA_ELEMENTO);

	}

	public static LoggingEvent getLogginEventSolicitudAlta(
			IServiceBase serviceToAudit) {
		return getLogginEvent(serviceToAudit,
				ArchivoActions.FONDOS_MODULE_SOLICITUD_ALTA_SERIE);
	}

	public static LoggingEvent getLogginEventAutorizarDenegarSolicitudAlta(
			IServiceBase serviceToAudit) {
		return getLogginEvent(serviceToAudit,
				ArchivoActions.FONDOS_MODULE_AUTORIZACION_DENEGACION_SERIE);
	}

	public static LoggingEvent getLogginEventGestionSolicitudesSerie(
			IServiceBase serviceToAudit) {
		return getLogginEvent(serviceToAudit,
				ArchivoActions.FONDOS_MODULE_GESTION_SOLICITUDES_SERIE);
	}

	public static LoggingEvent getLogginEventSolicitudCambiosEstadoOModif(
			IServiceBase serviceToAudit) {
		return getLogginEvent(serviceToAudit,
				ArchivoActions.FONDOS_MODULE_SOLICITUD_CAMBIOS_SERIE);
	}

	public static LoggingEvent getLogginEventModificacionSerie(
			IServiceBase serviceToAudit) {
		return getLogginEvent(serviceToAudit,
				ArchivoActions.FONDOS_MODULE_MODIFICACION_SERIE);
	}

	public static LoggingEvent getLogginEventIdentificacion(
			IServiceBase serviceToAudit) {
		return getLogginEvent(serviceToAudit,
				ArchivoActions.FONDOS_MODULE_IDENTIFICACION_SERIE);
	}

	public static LoggingEvent getLogginEventMove(IServiceBase serviceToAudit) {
		return getLogginEvent(serviceToAudit,
				ArchivoActions.FONDOS_MODULE_MOVER_ELEMENTO);
	}

	public static void addDataLogGuardarIdentificacion(Locale locale,
			LoggingEvent event, SerieVO element,
			IdentificacionSerie identificacionVieja,
			IdentificacionSerie identificacionNueva) {

		DataLoggingEvent dataLogginEvent = event
				.getDataLoggingEvent(ArchivoObjects.OBJECT_ELEMENTO_CUADRO,
						element.getId())
				.addDetalle(locale, ArchivoDetails.FONDO_CODIGO,
						element.getCodigo())
				.addDetalle(locale, ArchivoDetails.FONDO_TITULO,
						element.getTitulo());

		dataLogginEvent.addDetalle(locale,
				ArchivoDetails.FONDO_DEFINICION_FINAL,
				identificacionNueva.getDefinicion());
		dataLogginEvent.addDetalle(locale,
				ArchivoDetails.FONDO_DENOMINACION_FINAL,
				identificacionNueva.getDenominacion());
		dataLogginEvent.addDetalle(locale,
				ArchivoDetails.FONDO_DOCS_BASICOS_FINAL,
				identificacionNueva.getDocsBasicosUnidadDocumental());
		dataLogginEvent.addDetalle(locale,
				ArchivoDetails.FONDO_NORMATIVA_FINAL,
				identificacionNueva.getNormativa());

	}

	/**
	 * @param event
	 * @param element
	 */
	public static void addDataLogCambiosEstadoSerie(Locale locale,
			LoggingEvent event, SerieVO element, int estadoAnterior,
			int nuevoEstado) {

		event.getDataLoggingEvent(ArchivoObjects.OBJECT_ELEMENTO_CUADRO,
				element.getId())
				.addDetalle(locale, ArchivoDetails.FONDO_CODIGO,
						element.getCodigo())
				.addDetalle(locale, ArchivoDetails.FONDO_TITULO,
						element.getTitulo())
				.addDetalle(locale, ArchivoDetails.FONDO_ESTADO_INICIAL,
						EstadoSerie.getNombreEstadoSerie(estadoAnterior))
				.addDetalle(locale, ArchivoDetails.FONDO_ESTADO_FINAL,
						EstadoSerie.getNombreEstadoSerie(nuevoEstado))
				.addDetalle(locale, ArchivoDetails.FONDO_CODIGO_REF_FONDO,
						element.getCodRefFondo());
	}

	public static void addDataLogCambiosUsrGestorSerie(Locale locale,
			LoggingEvent event, SerieVO element, String nombreUserInicial,
			String idUsrInicial, String nombreUserFinal, String idUsrFinal) {

		event.getDataLoggingEvent(ArchivoObjects.OBJECT_ELEMENTO_CUADRO,
				element.getId())
				.addDetalle(locale, ArchivoDetails.FONDO_CODIGO,
						element.getCodigo())
				.addDetalle(locale, ArchivoDetails.FONDO_TITULO,
						element.getTitulo())
				.addDetalle(locale, ArchivoDetails.FONDO_USR_GESTOR_INICIAL,
						nombreUserInicial)
				.addDetalle(locale, ArchivoDetails.FONDO_ID_USR_GESTOR_INICIAL,
						idUsrInicial)
				.addDetalle(locale, ArchivoDetails.FONDO_USR_GESTOR_FINAL,
						nombreUserFinal)
				.addDetalle(locale, ArchivoDetails.FONDO_ID_USR_GESTOR_FINAL,
						idUsrFinal)
				.addDetalle(locale, ArchivoDetails.FONDO_CODIGO_REF_FONDO,
						element.getCodRefFondo());
	}

	// public static void
	// addDataLogCambiosUsrGestorSerieInCambioEstado(LoggingEvent event, SerieVO
	// element,
	// String nombreUserInicial, String nombreUserFinal) {
	// event.getDataLoggingEvent(ArchivoObjects.OBJECT_ELEMENTO_CUADRO,
	// element.getId())
	// .addDetalle(ArchivoDetails.FONDO_USR_GESTOR_INICIAL, nombreUserInicial)
	// .addDetalle(ArchivoDetails.FONDO_USR_GESTOR_FINAL, nombreUserFinal);
	// }

	public static void addDataLogAlta(Locale locale, LoggingEvent event,
			ElementoCuadroClasificacionVO element, String nombreNivel,
			String codigoReferenciaAscendenteJerarquico) {
		event.getDataLoggingEvent(ArchivoObjects.OBJECT_ELEMENTO_CUADRO,
				element.getId())
				.addDetalle(locale, ArchivoDetails.FONDO_CODIGO,
						element.getCodigo())
				.addDetalle(locale, ArchivoDetails.FONDO_TITULO,
						element.getTitulo())
				.addDetalle(locale, ArchivoDetails.FONDO_TIPO_ELEMENTO,
						nombreNivel)
				.addDetalle(locale,
						ArchivoDetails.FONDO_CODIGO_REF_ASCENDENTE_JERARQUICO,
						codigoReferenciaAscendenteJerarquico);
	}

	public static DataLoggingEvent addDataLogUpdateStep1(Locale locale,
			LoggingEvent event, ElementoCuadroClasificacionVO previousElement) {
		ServiceRepository services = ServiceRepository.getInstance(event
				.getUser(), event.getServiceToAudit().getTransactionManager());
		GestionCuadroClasificacionBI cuadroBI = services
				.lookupGestionCuadroClasificacionBI();
		return event
				.getDataLoggingEvent(ArchivoObjects.OBJECT_ELEMENTO_CUADRO,
						previousElement.getId())
				.addDetalle(locale, ArchivoDetails.FONDO_CODIDO_INICIAL,
						previousElement.getCodigo())
				.addDetalle(locale, ArchivoDetails.FONDO_DENOMINACION_INICIAL,
						previousElement.getTitulo())
				.addDetalle(
						locale,
						ArchivoDetails.FONDO_TIPO_ELEMENTO,
						cuadroBI.getNivelCF(previousElement.getIdNivel())
								.getNombre());

	}

	public static void addDataLogUpdateStep2(Locale locale,
			DataLoggingEvent loggingEvent,
			ElementoCuadroClasificacionVO elementModified) {
		loggingEvent.addDetalle(locale, ArchivoDetails.FONDO_CODIGO_MODIFICADO,
				elementModified.getCodigo()).addDetalle(locale,
				ArchivoDetails.FONDO_DENOMINACION_FINAL,
				elementModified.getTitulo());
	}

	public static void addDataLogRemove(Locale locale, LoggingEvent event,
			ElementoCuadroClasificacionVO element,
			String codigoReferenciaAscendenteJerarquico) {

		GestionCuadroClasificacionBI cuadroBI = ServiceRepository.getInstance(
				event.getServiceToAudit().getServiceSession())
				.lookupGestionCuadroClasificacionBI();

		String tipoElemento = cuadroBI.getNivelCF(element.getIdNivel())
				.getNombre();

		event.getDataLoggingEvent(ArchivoObjects.OBJECT_ELEMENTO_CUADRO,
				element.getId())
				.addDetalle(locale, ArchivoDetails.FONDO_CODIGO,
						element.getCodigo())
				.addDetalle(locale, ArchivoDetails.FONDO_TITULO,
						element.getTitulo())
				.addDetalle(locale, ArchivoDetails.FONDO_TIPO_ELEMENTO,
						tipoElemento)
				.addDetalle(locale,
						ArchivoDetails.FONDO_CODIGO_REF_ASCENDENTE_JERARQUICO,
						codigoReferenciaAscendenteJerarquico);

	}

	public static DataLoggingEvent addDataLogMoveStep1(Locale locale,
			LoggingEvent event, ElementoCuadroClasificacionVO element,
			String codigoReferenciaAscendenteJerarquico) {
		ServiceRepository services = ServiceRepository.getInstance(event
				.getUser(), event.getServiceToAudit().getTransactionManager());
		GestionCuadroClasificacionBI cuadroBI = services
				.lookupGestionCuadroClasificacionBI();
		return event
				.getDataLoggingEvent(ArchivoObjects.OBJECT_ELEMENTO_CUADRO,
						element.getId())
				.addDetalle(locale,
						ArchivoDetails.FONDO_CODIGO_REF_ASCENDENTE_JERARQUICO,
						codigoReferenciaAscendenteJerarquico)
				.addDetalle(locale, ArchivoDetails.FONDO_TIPO_ELEMENTO,
						cuadroBI.getNivelCF(element.getIdNivel()).getNombre());
	}

	public static LoggingEvent addDataLogMoveStep1(Locale locale,
			LoggingEvent event, TablaTemporalFondosVO tablaTemporalFondosVO) {
		ServiceRepository services = ServiceRepository.getInstance(event
				.getUser(), event.getServiceToAudit().getTransactionManager());
		GestionCuadroClasificacionBI cuadroBI = services
				.lookupGestionCuadroClasificacionBI();

		List nombres = cuadroBI.getNombresNiveles(tablaTemporalFondosVO);
		HashMap codsReferenciaPadre = cuadroBI
				.getPairsIdCodRefPadre(tablaTemporalFondosVO);

		for (Iterator it = nombres.iterator(); it.hasNext();) {
			Object[] tupla = (Object[]) it.next();
			DataLoggingEvent dataEvent = event.getDataLoggingEvent(
					ArchivoObjects.OBJECT_ELEMENTO_CUADRO, (String) tupla[0]);
			dataEvent.addDetalle(locale,
					ArchivoDetails.FONDO_CODIGO_REF_ASCENDENTE_JERARQUICO,
					(String) codsReferenciaPadre.get((String) tupla[0]));
			dataEvent.addDetalle(locale, ArchivoDetails.FONDO_TIPO_ELEMENTO,
					(String) tupla[1]);
		}
		return event;
	}

	public static void addDataLogMoveStep2(Locale locale,
			DataLoggingEvent event, ElementoCuadroClasificacionVO element,
			String codigoReferenciaNuevoAscendenteJerarquico) {
		event.addDetalle(locale, ArchivoDetails.FONDO_CODIGO_MOVIDO,
				element.getCodigo()).addDetalle(locale,
				ArchivoDetails.FONDO_CODIGO_REF_NUEVO_ASCENDENTE_JERARQUICO,
				codigoReferenciaNuevoAscendenteJerarquico);
	}

	public static void addDataLogMoveStep2(Locale locale, LoggingEvent event,
			TablaTemporalFondosVO tablaTemporal,
			String codigoReferenciaNuevoAscendenteJerarquico) {
		ServiceRepository services = ServiceRepository.getInstance(event
				.getUser(), event.getServiceToAudit().getTransactionManager());
		GestionCuadroClasificacionBI cuadroBI = services
				.lookupGestionCuadroClasificacionBI();

		HashMap codigos = cuadroBI.getPairsIdCodigo(tablaTemporal);

		for (Iterator it = event.getData().iterator(); it.hasNext();) {
			DataLoggingEvent data = (DataLoggingEvent) it.next();
			data.addDetalle(locale, ArchivoDetails.FONDO_CODIGO_MOVIDO,
					(String) codigos.get(data.getIdObject()))
					.addDetalle(
							locale,
							ArchivoDetails.FONDO_CODIGO_REF_NUEVO_ASCENDENTE_JERARQUICO,
							codigoReferenciaNuevoAscendenteJerarquico);
		}
	}

	public static void addDataLogMoveStep2ByCodigo(Locale locale,
			DataLoggingEvent event, String codigoElement,
			String codigoReferenciaNuevoAscendenteJerarquico) {

		event.addDetalle(locale, ArchivoDetails.FONDO_CODIGO_MOVIDO,
				codigoElement).addDetalle(locale,
				ArchivoDetails.FONDO_CODIGO_REF_NUEVO_ASCENDENTE_JERARQUICO,
				codigoReferenciaNuevoAscendenteJerarquico);
	}

	private static String stateToString(int state) {
		return state == ElementoCuadroClasificacion.VIGENTE ? "VIGENTE"
				: "NO VIGENTE";
	}

	public static void addDataLogUpdateState(Locale locale, LoggingEvent event,
			ElementoCuadroClasificacionVO element, int estadoInicial,
			int estadoFinal) {
		ServiceRepository services = ServiceRepository.getInstance(event
				.getUser(), event.getServiceToAudit().getTransactionManager());
		GestionCuadroClasificacionBI cuadroBI = services
				.lookupGestionCuadroClasificacionBI();
		event.getDataLoggingEvent(ArchivoObjects.OBJECT_ELEMENTO_CUADRO,
				element.getId())
				.addDetalle(locale, ArchivoDetails.FONDO_CODIGO,
						element.getCodigo())
				.addDetalle(locale, ArchivoDetails.FONDO_DENOMINACION,
						element.getTitulo())
				.addDetalle(locale, ArchivoDetails.FONDO_ESTADO_INICIAL,
						stateToString(estadoInicial))
				.addDetalle(locale, ArchivoDetails.FONDO_ESTADO_FINAL,
						stateToString(estadoFinal))
				.addDetalle(locale, ArchivoDetails.FONDO_TIPO_ELEMENTO,
						cuadroBI.getNivelCF(element.getIdNivel()).getNombre());

	}

	public static void addDataLogUpdatePublicacionUdoc(Locale locale,
			LoggingEvent event, ElementoCuadroClasificacionVO element) {
		event.getDataLoggingEvent(ArchivoObjects.OBJECT_ELEMENTO_CUADRO,
				element.getId())
				.addDetalle(locale, ArchivoDetails.FONDO_CODIGO,
						element.getCodigo())
				.addDetalle(locale, ArchivoDetails.FONDO_DENOMINACION,
						element.getTitulo());
	}

	public static void addDataLogSolicitudSerie(Locale locale,
			LoggingEvent event, ElementoCuadroClasificacionVO element,
			String notificacionAdministrativa) {
		ServiceRepository services = ServiceRepository.getInstance(event
				.getUser(), event.getServiceToAudit().getTransactionManager());
		GestionCuadroClasificacionBI cuadroBI = services
				.lookupGestionCuadroClasificacionBI();
		event.getDataLoggingEvent(ArchivoObjects.OBJECT_ELEMENTO_CUADRO,
				element.getId())
				.addDetalle(locale, ArchivoDetails.FONDO_CODIGO,
						element.getCodigo())
				.addDetalle(locale, ArchivoDetails.FONDO_TITULO,
						element.getTitulo())
				.addDetalle(locale, ArchivoDetails.FONDO_CODIGO_REF_FONDO,
						element.getCodRefFondo())
				.addDetalle(locale, ArchivoDetails.FONDO_TIPO_ELEMENTO,
						cuadroBI.getNivelCF(element.getIdNivel()).getNombre())
				.addDetalle(locale,
						ArchivoDetails.FONDO_NOTIFICACION_ADMINISTRATIVA,
						notificacionAdministrativa);
	}

	public static void addDataLogAutorizarSolicitudSerie(Locale locale,
			LoggingEvent event, ElementoCuadroClasificacionVO element,
			String notificacionAdministrativa) {
		event.getDataLoggingEvent(ArchivoObjects.OBJECT_ELEMENTO_CUADRO,
				element.getId())
				.addDetalle(locale, ArchivoDetails.FONDO_CODIGO,
						element.getCodigo())
				.addDetalle(locale, ArchivoDetails.FONDO_TITULO,
						element.getTitulo())
				.addDetalle(locale, ArchivoDetails.FONDO_CODIGO_REF_FONDO,
						element.getCodRefFondo())
				.addDetalle(locale, ArchivoDetails.FONDO_ACCION_TOMADA,
						"aceptada")
				.addDetalle(locale,
						ArchivoDetails.FONDO_NOTIFICACION_ADMINISTRATIVA,
						notificacionAdministrativa);
	}

	public static void addDataLogDenegarSolicitudSerie(Locale locale,
			LoggingEvent event, ElementoCuadroClasificacionVO element,
			String notificacionAdministrativa, String motivoRechazo) {
		event.getDataLoggingEvent(ArchivoObjects.OBJECT_ELEMENTO_CUADRO,
				element.getId())
				.addDetalle(locale, ArchivoDetails.FONDO_CODIGO,
						element.getCodigo())
				.addDetalle(locale, ArchivoDetails.FONDO_TITULO,
						element.getTitulo())
				.addDetalle(locale, ArchivoDetails.FONDO_CODIGO_REF_FONDO,
						element.getCodRefFondo())
				.addDetalle(locale, ArchivoDetails.FONDO_ACCION_TOMADA,
						"denegada")
				.addDetalle(locale, ArchivoDetails.FONDO_MOTIVO, motivoRechazo)
				.addDetalle(locale,
						ArchivoDetails.FONDO_NOTIFICACION_ADMINISTRATIVA,
						notificacionAdministrativa);
	}

	public static PistaAuditoriaFraccionSerie crearPistaAuditoria(int action,
			IServiceBase service) {
		return instancia.new PistaAuditoriaFraccionSerie(action, service);
	}

	public static LoggingEvent getLogginEventGestionUdocsSerie(
			IServiceBase serviceToAudit) {
		return getLogginEvent(serviceToAudit,
				ArchivoActions.FONDOS_MODULE_ELIMINACION_MARCA_CONSERVADA_UDOCS);
	}

	public class PistaAuditoriaFraccionSerie implements IAuditFondos {

		// private ServiceSession ss=null;
		protected GestionPrevisionesBI servicioPrevisiones = null;
		protected GestionRelacionesEntregaBI servicioRelaciones = null;
		private DataLoggingEvent logData = null;
		private LoggingEvent le = null;

		public PistaAuditoriaFraccionSerie(int action, IServiceBase service) {
			getLogginEvent(action, service);
		}

		private LoggingEvent getLogginEvent(int action, IServiceBase service) {
			// se rellenan variables static de la clase.
			// la clase es como un contenedor de variables (globales)
			// los atributos almacenan los datos de la ultima pista de auditoria
			// creada.
			// de esta forma es posible añadir más detalles/campos a los
			// detalles.

			// ss=service.getServiceSession();
			if (service instanceof GestionPrevisionesBI)
				servicioPrevisiones = (GestionPrevisionesBI) service;
			if (service instanceof GestionRelacionesEntregaBI)
				servicioRelaciones = (GestionRelacionesEntregaBI) service;
			logData = null;
			le = new LoggingEvent(ArchivoModules.FONDOS_MODULE, action,
					service.getServiceClient(), false);

			// Lo añadimos a la pila
			service.getLogger().add(le);
			return le;
		}

		/*
		 * public void addDetalleBasico(UnidadDocumental udoc){ if(logData!=null
		 * && logData.getDetalle(ArchivoDetails.FONDO_ID_FRACCION_SERIE)!=null)
		 * return; if(logData==null){ logData =
		 * le.getDataLoggingEvent(ArchivoObjects.OBJECT_FRACCION_SERIE,
		 * udoc.getId()); }
		 * logData.addDetalle(ArchivoDetails.FONDO_ID_FRACCION_SERIE
		 * ,udoc.getCodReferencia());
		 * 
		 * }
		 */

		public void addDetalleBasico(Locale locale,
				DivisionFraccionSerieVO divisionFS) {
			if (logData != null
					&& logData
							.getDetalle(ArchivoDetails.FONDO_ID_FRACCION_SERIE) != null)
				return;
			if (logData == null) {
				logData = le.getDataLoggingEvent(
						ArchivoObjects.OBJECT_FRACCION_SERIE,
						divisionFS.getIdFS());
			}
			logData.addDetalle(locale, ArchivoDetails.FONDO_ID_FRACCION_SERIE,
					divisionFS.getCodReferencia());

		}

		public void auditaFraccionSerieDividida(Locale locale,
				String uDocsDeFranccionSerie) {
			logData.addDetalleNoVacio(locale,
					ArchivoDetails.FONDO_UDOCS_DE_FRACCION_SERIE,
					uDocsDeFranccionSerie);
		}

		public void auditaEliminarFraccionSerie(Locale locale, String cadena) {
			logData.addDetalleNoVacio(locale,
					ArchivoDetails.FONDO_FRACCION_DE_SERIE_ELIMINADA, cadena);
		}

		public void auditaActualizadaFechasExtremas(Locale locale, String cadena) {
			logData.addDetalleNoVacio(locale,
					ArchivoDetails.FONDO_SERIE_ACTUALIZADA, cadena);
		}
	}

	public static LoggingEvent addDataLogEliminarMarcaConservada(Locale locale,
			LoggingEvent event, String[] ids) {

		if (ArrayUtils.isNotEmpty(ids)) {

			ServiceRepository services = ServiceRepository.getInstance(event
					.getUser(), event.getServiceToAudit()
					.getTransactionManager());

			GestionCuadroClasificacionBI servicio = services
					.lookupGestionCuadroClasificacionBI();

			for (int i = 0; i < ids.length; i++) {
				String idElemento = ids[i];

				IElementoCuadroClasificacion elemento = servicio
						.getElementoCuadroClasificacionConFechas(idElemento);
				if (elemento != null) {
					ElementoCuadroClasificacion udoc = (ElementoCuadroClasificacion) elemento;

					event.getDataLoggingEvent(ArchivoObjects.OBJECT_UDOC,
							udoc.getId())
							.addDetalleNoVacio(locale,
									ArchivoDetails.FONDO_CODIGO_REFERENCIA,
									udoc.getCodReferencia())
							.addDetalleNoVacio(locale,
									ArchivoDetails.FONDO_CODIGO,
									udoc.getCodigo())
							.addDetalleNoVacio(locale,
									ArchivoDetails.FONDO_TITULO,
									udoc.getTitulo())
							.addDetalleNoVacio(
									locale,
									ArchivoDetails.FONDO_FEXTREMAINICIAL,
									DateUtils.formatDate(udoc.getFechaInicial()))
							.addDetalleNoVacio(locale,
									ArchivoDetails.FONDO_FEXTREMAFINAL,
									DateUtils.formatDate(udoc.getFechaFinal()))
							.addDetalleNoVacio(locale,
									ArchivoDetails.FONDO_IDELIMINACION,
									udoc.getIdEliminacion());
				}
				;
			}
		}
		return event;
	}
}
