package docvitales.model;

import java.util.Locale;

import auditoria.ArchivoDetails;
import auditoria.logger.DataLoggingEvent;
import auditoria.logger.LoggingEvent;

import common.Messages;
import common.bi.IServiceBase;
import common.definitions.ArchivoActions;
import common.definitions.ArchivoModules;
import common.definitions.ArchivoObjects;
import common.util.StringUtils;
import common.util.TypeConverter;

import docvitales.DocumentosVitalesConstants;
import docvitales.vos.InfoBDocumentoVitalExtVO;
import docvitales.vos.TipoDocumentoVitalVO;

/**
 * Clase para auditar la gestión de los documentos vitales.
 */
public class AuditoriaDocumentosVitales {

	/**
	 * Obtiene un evento de auditoría.
	 * 
	 * @param service
	 *            Servicio a auditar.
	 * @param action
	 *            Código de la acción a auditar.
	 * @return Evento de auditoría ({@link LoggingEvent}).
	 */
	public static LoggingEvent getLogginEvent(IServiceBase service, int action) {
		// Crear el evento
		LoggingEvent event = new LoggingEvent(
				ArchivoModules.DOCUMENTOS_VITALES_MODULE, action,
				service.getServiceClient(), false);

		// Añadir el evento al logger
		service.getLogger().add(event);

		return event;
	}

	// ========================================================================
	// AUDITORÍA SOBRE TIPOS DE DOCUMENTOS VITALES
	// ========================================================================

	/**
	 * Audita la creación de un tipo de documento vital.
	 * 
	 * @param event
	 *            Evento de auditoría.
	 * @param tipo
	 *            Tipo de documento vital.
	 */
	public static void auditaAltaTipoDocumentoVital(Locale locale,
			LoggingEvent event, TipoDocumentoVitalVO tipo) {
		// Detalle de auditoría
		DataLoggingEvent data = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_TIPO_DOCUMENTO_VITAL, tipo.getId());

		// Detalles de auditoría
		data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_VITALES_NOMBRE_TIPO,
				tipo.getNombre());
		data.addDetalle(locale,
				ArchivoDetails.DOCUMENTOS_VITALES_DESCRIPCION_TIPO,
				tipo.getDescripcion());
	}

	/**
	 * Audita la modificación de un tipo de documento vital.
	 * 
	 * @param service
	 *            Servicio a auditar.
	 * @param tipo
	 *            Tipo de documento vital.
	 * @param tipoAnt
	 *            Tipo de documento vital anterior.
	 */
	public static void auditaModificacionTipoDocumentoVital(Locale locale,
			IServiceBase service, TipoDocumentoVitalVO tipo,
			TipoDocumentoVitalVO tipoAnt) {
		// Evento de auditoría
		LoggingEvent event = getLogginEvent(service,
				ArchivoActions.DOCUMENTOS_VITALES_MODULE_MODIFICACION_TIPO);

		// Detalle de auditoría
		DataLoggingEvent data = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_TIPO_DOCUMENTO_VITAL, tipo.getId());

		// Detalles de auditoría
		data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_VITALES_NOMBRE_TIPO,
				tipo.getNombre());
		if (!StringUtils.equals(tipo.getNombre(), tipoAnt.getNombre()))
			data.addDetalle(locale,
					ArchivoDetails.DOCUMENTOS_VITALES_NOMBRE_TIPO_ANTERIOR,
					tipoAnt.getNombre());

		data.addDetalle(locale,
				ArchivoDetails.DOCUMENTOS_VITALES_DESCRIPCION_TIPO,
				tipo.getDescripcion());
		if (!StringUtils
				.equals(tipo.getDescripcion(), tipoAnt.getDescripcion()))
			data.addDetalle(
					locale,
					ArchivoDetails.DOCUMENTOS_VITALES_DESCRIPCION_TIPO_ANTERIOR,
					tipoAnt.getDescripcion());
	}

	/**
	 * Audita la eliminación de un tipo de documento vital.
	 * 
	 * @param service
	 *            Servicio a auditar.
	 * @param tipo
	 *            Tipo de documento vital.
	 */
	public static void auditaEliminacionTipoDocumentoVital(Locale locale,
			IServiceBase service, TipoDocumentoVitalVO tipo) {
		// Evento de auditoría
		LoggingEvent event = getLogginEvent(service,
				ArchivoActions.DOCUMENTOS_VITALES_MODULE_ELIMINACION_TIPO);

		if (tipo != null) {
			// Detalle de auditoría
			DataLoggingEvent data = event.getDataLoggingEvent(
					ArchivoObjects.OBJECT_TIPO_DOCUMENTO_VITAL, tipo.getId());

			// Detalles de auditoría
			data.addDetalle(locale,
					ArchivoDetails.DOCUMENTOS_VITALES_NOMBRE_TIPO,
					tipo.getNombre());
			data.addDetalle(locale,
					ArchivoDetails.DOCUMENTOS_VITALES_DESCRIPCION_TIPO,
					tipo.getDescripcion());
		}
	}

	// ========================================================================
	// AUDITORÍA SOBRE DOCUMENTOS VITALES
	// ========================================================================

	/**
	 * Audita la modificación de un documento vital.
	 * 
	 * @param event
	 *            Evento de auditoría.
	 * @param docVital
	 *            Documento vital.
	 */
	public static void auditaInsercionDocumentoVital(Locale locale,
			LoggingEvent event, InfoBDocumentoVitalExtVO docVital) {
		// Detalle de auditoría
		DataLoggingEvent data = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_DOCUMENTO_VITAL, docVital.getId());

		// Detalles de auditoría
		data.addDetalle(locale,
				ArchivoDetails.DOCUMENTOS_VITALES_ID_BD_TERCEROS,
				docVital.getIdBdTerceros());
		data.addDetalle(locale,
				ArchivoDetails.DOCUMENTOS_VITALES_NUM_IDENTIDAD,
				docVital.getNumIdentidad());
		data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_VITALES_IDENTIDAD,
				docVital.getIdentidad());
		data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_VITALES_ID_TIPO,
				docVital.getIdTipoDocVit());
		data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_VITALES_FECHA_CAD,
				TypeConverter.toString(docVital.getFechaCad()));
		data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_VITALES_ESTADO,
				Messages.getString(
						DocumentosVitalesConstants.LABEL_DOCVITALES_ESTADO
								+ "." + docVital.getEstadoDocVit(), locale));
		data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_VITALES_FECHA_CR,
				TypeConverter.toString(docVital.getFechaCr()));
		data.addDetalle(locale,
				ArchivoDetails.DOCUMENTOS_VITALES_ID_USUARIO_CR,
				docVital.getIdUsuarioCr());
		data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_VITALES_ID_FICH,
				docVital.getIdFich());
		data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_VITALES_NOMBRE_FICH,
				docVital.getNombreOrgFich());
		data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_VITALES_EXT_FICH,
				docVital.getExtFich());
		data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_VITALES_TAMANO_FICH,
				new Double(docVital.getTamanoFich()).toString());
		data.addDetalle(locale,
				ArchivoDetails.DOCUMENTOS_VITALES_OBSERVACIONES,
				docVital.getObservaciones());
	}

	// /**
	// * Audita la modificación de un documento vital.
	// * @param service Servicio a auditar.
	// * @param docVital Documento vital.
	// * @param docVitalAnt Documento vital anterior.
	// */
	// public static void auditaModificacionDocumentoVital(IServiceBase service,
	// InfoBDocumentoVitalExtVO docVital, InfoBDocumentoVitalExtVO docVitalAnt)
	// {
	// // Evento de auditoría
	// LoggingEvent event = getLogginEvent(service,
	// ArchivoActions.DOCUMENTOS_VITALES_MODULE_MODIFICACION);
	//
	// // Detalle de auditoría
	// DataLoggingEvent data = event.getDataLoggingEvent(
	// ArchivoObjects.OBJECT_DOCUMENTO_VITAL, docVital.getId());
	//
	// // Detalles de auditoría
	// data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_VITALES_ID_BD_TERCEROS,
	// docVital.getIdBdTerceros());
	// data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_VITALES_NUM_IDENTIDAD,
	// docVital.getNumIdentidad());
	// data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_VITALES_IDENTIDAD,
	// docVital.getIdentidad());
	// if (!StringUtils.equals(docVital.getIdBdTerceros(),
	// docVitalAnt.getIdBdTerceros()))
	// {
	// data.addDetalle(locale,
	// ArchivoDetails.DOCUMENTOS_VITALES_ID_BD_TERCEROS_ANTERIOR,
	// docVitalAnt.getIdBdTerceros());
	// data.addDetalle(locale,
	// ArchivoDetails.DOCUMENTOS_VITALES_NUM_IDENTIDAD_ANTERIOR,
	// docVitalAnt.getNumIdentidad());
	// data.addDetalle(locale,
	// ArchivoDetails.DOCUMENTOS_VITALES_IDENTIDAD_ANTERIOR,
	// docVitalAnt.getIdentidad());
	// }
	// data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_VITALES_ID_TIPO,
	// docVital.getIdTipoDocVit());
	// if (!StringUtils.equals(docVital.getIdTipoDocVit(),
	// docVitalAnt.getIdTipoDocVit()))
	// data.addDetalle(locale,
	// ArchivoDetails.DOCUMENTOS_VITALES_ID_TIPO_ANTERIOR,
	// docVitalAnt.getIdTipoDocVit());
	// data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_VITALES_NOMBRE_TIPO,
	// docVital.getNombreTipoDocVit());
	// if (!StringUtils.equals(docVital.getNombreTipoDocVit(),
	// docVitalAnt.getNombreTipoDocVit()))
	// data.addDetalle(locale,
	// ArchivoDetails.DOCUMENTOS_VITALES_NOMBRE_TIPO_ANTERIOR,
	// docVitalAnt.getNombreTipoDocVit());
	// data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_VITALES_FECHA_CAD,
	// TypeConverter.toString(docVital.getFechaCad()));
	// if (docVital.getFechaCad() != null
	// && !docVital.getFechaCad().equals(docVitalAnt.getFechaCad()))
	// data.addDetalle(locale,
	// ArchivoDetails.DOCUMENTOS_VITALES_FECHA_CAD_ANTERIOR,
	// TypeConverter.toString(docVitalAnt.getFechaCad()));
	// data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_VITALES_ESTADO,
	// Messages.getString(DocumentosVitalesConstants.LABEL_DOCVITALES_ESTADO+"."
	// + docVital.getEstadoDocVit()));
	// data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_VITALES_FECHA_CR,
	// TypeConverter.toString(docVital.getFechaCr()));
	// data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_VITALES_ID_USUARIO_CR,
	// docVital.getIdUsuarioCr());
	// if (docVital.getFechaVig() != null)
	// data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_VITALES_FECHA_VIG,
	// TypeConverter.toString(docVital.getFechaVig()));
	// if (StringUtils.isNotBlank(docVital.getIdUsuarioVig()))
	// data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_VITALES_ID_USUARIO_VIG,
	// docVital.getIdUsuarioVig());
	// data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_VITALES_ID_FICH,
	// docVital.getIdFich());
	// data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_VITALES_NOMBRE_FICH,
	// docVital.getNombreOrgFich());
	// data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_VITALES_EXT_FICH,
	// docVital.getExtFich());
	// data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_VITALES_TAMANO_FICH,
	// new Double(docVital.getTamanoFich()).toString());
	// data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_VITALES_OBSERVACIONES,
	// docVital.getObservaciones());
	// if (!StringUtils.equals(docVital.getObservaciones(),
	// docVitalAnt.getObservaciones()))
	// data.addDetalle(locale,
	// ArchivoDetails.DOCUMENTOS_VITALES_OBSERVACIONES_ANTERIOR,
	// docVitalAnt.getObservaciones());
	// }

	/**
	 * Audita la validación de un documento vital.
	 * 
	 * @param service
	 *            Servicio a auditar.
	 * @param docVital
	 *            Documento vital.
	 */
	public static void auditaValidacionDocumentoVital(Locale locale,
			IServiceBase service, InfoBDocumentoVitalExtVO docVital) {
		// Evento de auditoría
		LoggingEvent event = getLogginEvent(service,
				ArchivoActions.DOCUMENTOS_VITALES_MODULE_VALIDACION);

		// Detalle de auditoría
		DataLoggingEvent data = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_DOCUMENTO_VITAL, docVital.getId());

		// Detalles de auditoría
		data.addDetalle(locale,
				ArchivoDetails.DOCUMENTOS_VITALES_ID_BD_TERCEROS,
				docVital.getIdBdTerceros());
		data.addDetalle(locale,
				ArchivoDetails.DOCUMENTOS_VITALES_NUM_IDENTIDAD,
				docVital.getNumIdentidad());
		data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_VITALES_IDENTIDAD,
				docVital.getIdentidad());
		data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_VITALES_ID_TIPO,
				docVital.getIdTipoDocVit());
		data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_VITALES_NOMBRE_TIPO,
				docVital.getNombreTipoDocVit());
		data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_VITALES_FECHA_CAD,
				TypeConverter.toString(docVital.getFechaCad()));
		data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_VITALES_ESTADO,
				Messages.getString(
						DocumentosVitalesConstants.LABEL_DOCVITALES_ESTADO
								+ "." + docVital.getEstadoDocVit(), locale));
		data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_VITALES_FECHA_CR,
				TypeConverter.toString(docVital.getFechaCr()));
		data.addDetalle(locale,
				ArchivoDetails.DOCUMENTOS_VITALES_ID_USUARIO_CR,
				docVital.getIdUsuarioCr());
		data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_VITALES_FECHA_VIG,
				TypeConverter.toString(docVital.getFechaVig()));
		data.addDetalle(locale,
				ArchivoDetails.DOCUMENTOS_VITALES_ID_USUARIO_VIG,
				docVital.getIdUsuarioVig());
		data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_VITALES_ID_FICH,
				docVital.getIdFich());
		data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_VITALES_NOMBRE_FICH,
				docVital.getNombreOrgFich());
		data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_VITALES_EXT_FICH,
				docVital.getExtFich());
		data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_VITALES_TAMANO_FICH,
				new Double(docVital.getTamanoFich()).toString());
		data.addDetalle(locale,
				ArchivoDetails.DOCUMENTOS_VITALES_OBSERVACIONES,
				docVital.getObservaciones());
	}

	/**
	 * Audita el rechazo de un documento vital.
	 * 
	 * @param service
	 *            Servicio a auditar.
	 * @param docVital
	 *            Documento vital.
	 */
	public static void auditaRechazoDocumentoVital(Locale locale,
			IServiceBase service, InfoBDocumentoVitalExtVO docVital) {
		// Evento de auditoría
		LoggingEvent event = getLogginEvent(service,
				ArchivoActions.DOCUMENTOS_VITALES_MODULE_RECHAZO);

		// Detalle de auditoría
		DataLoggingEvent data = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_DOCUMENTO_VITAL, docVital.getId());

		// Detalles de auditoría
		data.addDetalle(locale,
				ArchivoDetails.DOCUMENTOS_VITALES_ID_BD_TERCEROS,
				docVital.getIdBdTerceros());
		data.addDetalle(locale,
				ArchivoDetails.DOCUMENTOS_VITALES_NUM_IDENTIDAD,
				docVital.getNumIdentidad());
		data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_VITALES_IDENTIDAD,
				docVital.getIdentidad());
		data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_VITALES_ID_TIPO,
				docVital.getIdTipoDocVit());
		data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_VITALES_NOMBRE_TIPO,
				docVital.getNombreTipoDocVit());
		data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_VITALES_FECHA_CAD,
				TypeConverter.toString(docVital.getFechaCad()));
		data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_VITALES_ESTADO,
				Messages.getString(
						DocumentosVitalesConstants.LABEL_DOCVITALES_ESTADO
								+ "." + docVital.getEstadoDocVit(), locale));
		data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_VITALES_FECHA_CR,
				TypeConverter.toString(docVital.getFechaCr()));
		data.addDetalle(locale,
				ArchivoDetails.DOCUMENTOS_VITALES_ID_USUARIO_CR,
				docVital.getIdUsuarioCr());
		data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_VITALES_ID_FICH,
				docVital.getIdFich());
		data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_VITALES_NOMBRE_FICH,
				docVital.getNombreOrgFich());
		data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_VITALES_EXT_FICH,
				docVital.getExtFich());
		data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_VITALES_TAMANO_FICH,
				new Double(docVital.getTamanoFich()).toString());
		data.addDetalle(locale,
				ArchivoDetails.DOCUMENTOS_VITALES_OBSERVACIONES,
				docVital.getObservaciones());
	}

	/**
	 * Audita el paso a histórico de un documento vital.
	 * 
	 * @param service
	 *            Servicio a auditar.
	 * @param docVital
	 *            Documento vital.
	 */
	public static void auditaPasoAHistoricoDocumentoVital(Locale locale,
			IServiceBase service, InfoBDocumentoVitalExtVO docVital) {
		// Evento de auditoría
		LoggingEvent event = getLogginEvent(service,
				ArchivoActions.DOCUMENTOS_VITALES_MODULE_PASO_A_HISTORICO);

		// Detalle de auditoría
		DataLoggingEvent data = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_DOCUMENTO_VITAL, docVital.getId());

		// Detalles de auditoría
		data.addDetalle(locale,
				ArchivoDetails.DOCUMENTOS_VITALES_ID_BD_TERCEROS,
				docVital.getIdBdTerceros());
		data.addDetalle(locale,
				ArchivoDetails.DOCUMENTOS_VITALES_NUM_IDENTIDAD,
				docVital.getNumIdentidad());
		data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_VITALES_IDENTIDAD,
				docVital.getIdentidad());
		data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_VITALES_ID_TIPO,
				docVital.getIdTipoDocVit());
		data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_VITALES_NOMBRE_TIPO,
				docVital.getNombreTipoDocVit());
		data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_VITALES_FECHA_CAD,
				TypeConverter.toString(docVital.getFechaCad()));
		data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_VITALES_ESTADO,
				Messages.getString(
						DocumentosVitalesConstants.LABEL_DOCVITALES_ESTADO
								+ "." + docVital.getEstadoDocVit(), locale));
		data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_VITALES_FECHA_CR,
				TypeConverter.toString(docVital.getFechaCr()));
		data.addDetalle(locale,
				ArchivoDetails.DOCUMENTOS_VITALES_ID_USUARIO_CR,
				docVital.getIdUsuarioCr());
		data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_VITALES_FECHA_VIG,
				TypeConverter.toString(docVital.getFechaVig()));
		data.addDetalle(locale,
				ArchivoDetails.DOCUMENTOS_VITALES_ID_USUARIO_VIG,
				docVital.getIdUsuarioVig());
		data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_VITALES_ID_FICH,
				docVital.getIdFich());
		data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_VITALES_NOMBRE_FICH,
				docVital.getNombreOrgFich());
		data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_VITALES_EXT_FICH,
				docVital.getExtFich());
		data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_VITALES_TAMANO_FICH,
				new Double(docVital.getTamanoFich()).toString());
		data.addDetalle(locale,
				ArchivoDetails.DOCUMENTOS_VITALES_OBSERVACIONES,
				docVital.getObservaciones());
	}

	/**
	 * Audita la eliminación de un documento vital.
	 * 
	 * @param service
	 *            Servicio a auditar.
	 * @param docVital
	 *            Documento vital.
	 */
	public static void auditaEliminacionDocumentoVital(Locale locale,
			IServiceBase service, InfoBDocumentoVitalExtVO docVital) {
		// Evento de auditoría
		LoggingEvent event = getLogginEvent(service,
				ArchivoActions.DOCUMENTOS_VITALES_MODULE_ELIMINACION);

		// Detalle de auditoría
		DataLoggingEvent data = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_DOCUMENTO_VITAL, docVital.getId());

		// Detalles de auditoría
		data.addDetalle(locale,
				ArchivoDetails.DOCUMENTOS_VITALES_ID_BD_TERCEROS,
				docVital.getIdBdTerceros());
		data.addDetalle(locale,
				ArchivoDetails.DOCUMENTOS_VITALES_NUM_IDENTIDAD,
				docVital.getNumIdentidad());
		data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_VITALES_IDENTIDAD,
				docVital.getIdentidad());
		data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_VITALES_ID_TIPO,
				docVital.getIdTipoDocVit());
		data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_VITALES_NOMBRE_TIPO,
				docVital.getNombreTipoDocVit());
		data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_VITALES_FECHA_CAD,
				TypeConverter.toString(docVital.getFechaCad()));
		data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_VITALES_ESTADO,
				Messages.getString(
						DocumentosVitalesConstants.LABEL_DOCVITALES_ESTADO
								+ "." + docVital.getEstadoDocVit(), locale));
		data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_VITALES_FECHA_CR,
				TypeConverter.toString(docVital.getFechaCr()));
		data.addDetalle(locale,
				ArchivoDetails.DOCUMENTOS_VITALES_ID_USUARIO_CR,
				docVital.getIdUsuarioCr());
		data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_VITALES_ID_FICH,
				docVital.getIdFich());
		data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_VITALES_NOMBRE_FICH,
				docVital.getNombreOrgFich());
		data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_VITALES_EXT_FICH,
				docVital.getExtFich());
		data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_VITALES_TAMANO_FICH,
				new Double(docVital.getTamanoFich()).toString());
		data.addDetalle(locale,
				ArchivoDetails.DOCUMENTOS_VITALES_OBSERVACIONES,
				docVital.getObservaciones());
	}

}
