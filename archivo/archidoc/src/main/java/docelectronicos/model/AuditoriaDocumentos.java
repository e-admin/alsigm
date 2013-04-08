package docelectronicos.model;

import gcontrol.vos.UsuarioVO;

import java.util.Locale;

import auditoria.ArchivoDetails;
import auditoria.logger.DataLoggingEvent;
import auditoria.logger.LoggingEvent;

import common.bi.GestionControlUsuariosBI;
import common.bi.IServiceBase;
import common.definitions.ArchivoActions;
import common.definitions.ArchivoModules;
import common.definitions.ArchivoObjects;

import docelectronicos.TipoObjeto;
import docelectronicos.vos.DocClasificadorVO;
import docelectronicos.vos.DocDocumentoVO;
import docelectronicos.vos.DocTCapturaVO;

/**
 * Clase para auditar la gestión de los documentos electrónicos.
 */
public class AuditoriaDocumentos {

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
				ArchivoModules.DOCUMENTOS_ELECTRONICOS_MODULE, action,
				service.getServiceClient(), false);

		// Añadir el evento al logger
		service.getLogger().add(event);

		return event;
	}

	/**
	 * Audita la inserción de un clasificador de documentos electrónicos.
	 * 
	 * @param event
	 *            Evento de auditoría.
	 * @param clasificador
	 *            Clasificador de documentos electrónicos.
	 */
	public static void auditaInsercionClasificador(Locale locale,
			LoggingEvent event, DocClasificadorVO clasificador) {
		// Detalle de la auditoría
		DataLoggingEvent data = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_CLASIFICADOR, clasificador.getId());

		data.addDetalle(locale,
				ArchivoDetails.DOCUMENTOS_ELECTRONICOS_NOMBRE_CLASIFICADOR,
				clasificador.getNombre());
		data.addDetalle(locale,
				ArchivoDetails.DOCUMENTOS_ELECTRONICOS_ID_CLF_PADRE,
				clasificador.getIdClfPadre());
		data.addDetalle(
				locale,
				clasificador.getTipoObjeto() == TipoObjeto.ELEMENTO_CF ? ArchivoDetails.DOCUMENTOS_ELECTRONICOS_ID_ELEMENTO_CF
						: ArchivoDetails.DOCUMENTOS_ELECTRONICOS_ID_DESCRIPTOR,
				clasificador.getIdObjeto());
		data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_ELECTRONICOS_MARCAS,
				new Integer(clasificador.getMarcas()).toString());
		data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_ELECTRONICOS_ESTADO,
				new Integer(clasificador.getEstado()).toString());
		data.addDetalle(locale,
				ArchivoDetails.DOCUMENTOS_ELECTRONICOS_DESCRIPCION,
				clasificador.getDescripcion());
	}

	/**
	 * Audita la modificación de un clasificador de documentos electrónicos.
	 * 
	 * @param service
	 *            Servicio a auditar.
	 * @param clasificador
	 *            Clasificador de documentos electrónicos.
	 */
	public static void auditaModificacionClasificador(Locale locale,
			IServiceBase service, DocClasificadorVO clasificador) {
		// Evento de auditoría
		LoggingEvent event = getLogginEvent(
				service,
				ArchivoActions.DOCUMENTOS_ELECTRONICOS_MODULE_MODIFICACION_CLASIFICADOR);

		// Detalle de auditoría
		DataLoggingEvent data = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_CLASIFICADOR, clasificador.getId());

		data.addDetalle(locale,
				ArchivoDetails.DOCUMENTOS_ELECTRONICOS_NOMBRE_CLASIFICADOR,
				clasificador.getNombre());
		data.addDetalle(locale,
				ArchivoDetails.DOCUMENTOS_ELECTRONICOS_ID_CLF_PADRE,
				clasificador.getIdClfPadre());
		data.addDetalle(
				locale,
				clasificador.getTipoObjeto() == TipoObjeto.ELEMENTO_CF ? ArchivoDetails.DOCUMENTOS_ELECTRONICOS_ID_ELEMENTO_CF
						: ArchivoDetails.DOCUMENTOS_ELECTRONICOS_ID_DESCRIPTOR,
				clasificador.getIdObjeto());
		data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_ELECTRONICOS_MARCAS,
				new Integer(clasificador.getMarcas()).toString());
		data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_ELECTRONICOS_ESTADO,
				new Integer(clasificador.getEstado()).toString());
		data.addDetalle(locale,
				ArchivoDetails.DOCUMENTOS_ELECTRONICOS_DESCRIPCION,
				clasificador.getDescripcion());
	}

	/**
	 * Audita la eliminación de un clasificador de documentos electrónicos.
	 * 
	 * @param service
	 *            Servicio a auditar.
	 * @param id
	 *            Identificador del clasificador.
	 * @param nombre
	 *            Nombre del clasificador
	 */
	public static void auditaEliminacionClasificador(Locale locale,
			IServiceBase service, String id, String nombre) {
		// Evento de auditoría
		LoggingEvent event = getLogginEvent(service,
				ArchivoActions.DOCUMENTOS_ELECTRONICOS_MODULE_BAJA_CLASIFICADOR);

		// Detalle de auditoría
		DataLoggingEvent data = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_CLASIFICADOR, id);

		data.addDetalle(locale,
				ArchivoDetails.DOCUMENTOS_ELECTRONICOS_NOMBRE_CLASIFICADOR,
				nombre);
	}

	/**
	 * Audita la inserción de un documento electrónico.
	 * 
	 * @param event
	 *            Evento de auditoría.
	 * @param documento
	 *            Documento electrónico.
	 */
	public static void auditaInsercionDocumento(Locale locale,
			LoggingEvent event, DocDocumentoVO documento) {
		// Detalle de la auditoría
		DataLoggingEvent data = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_DOCUMENTO, documento.getId());

		data.addDetalle(locale,
				ArchivoDetails.DOCUMENTOS_ELECTRONICOS_NOMBRE_DOCUMENTO,
				documento.getNombre());
		data.addDetalle(locale,
				ArchivoDetails.DOCUMENTOS_ELECTRONICOS_ID_CLF_PADRE,
				documento.getIdClfPadre());
		data.addDetalle(
				locale,
				documento.getTipoObjeto() == TipoObjeto.ELEMENTO_CF ? ArchivoDetails.DOCUMENTOS_ELECTRONICOS_ID_ELEMENTO_CF
						: ArchivoDetails.DOCUMENTOS_ELECTRONICOS_ID_DESCRIPTOR,
				documento.getIdObjeto());
		data.addDetalle(locale,
				ArchivoDetails.DOCUMENTOS_ELECTRONICOS_TAMANO_FICHERO,
				new Double(documento.getTamanoFich()).toString());
		data.addDetalle(locale,
				ArchivoDetails.DOCUMENTOS_ELECTRONICOS_NOMBRE_ORIGINAL_FICHERO,
				documento.getNombreOrgFich());
		data.addDetalle(locale,
				ArchivoDetails.DOCUMENTOS_ELECTRONICOS_EXTENSION_FICHERO,
				documento.getExtFich());
		data.addDetalle(locale,
				ArchivoDetails.DOCUMENTOS_ELECTRONICOS_ID_FICHERO,
				documento.getIdFich());
		data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_ELECTRONICOS_ESTADO,
				new Integer(documento.getEstado()).toString());
		data.addDetalle(locale,
				ArchivoDetails.DOCUMENTOS_ELECTRONICOS_DESCRIPCION,
				documento.getDescripcion());
	}

	/**
	 * Audita la modificación de un documento electrónico.
	 * 
	 * @param service
	 *            Servicio a auditar.
	 * @param documento
	 *            Documento electrónico.
	 */
	public static void auditaModificacionDocumento(Locale locale,
			IServiceBase service, DocDocumentoVO documento) {
		// Evento de auditoría
		LoggingEvent event = getLogginEvent(
				service,
				ArchivoActions.DOCUMENTOS_ELECTRONICOS_MODULE_MODIFICACION_DOCUMENTO);

		// Detalle de auditoría
		DataLoggingEvent data = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_DOCUMENTO, documento.getId());

		data.addDetalle(locale,
				ArchivoDetails.DOCUMENTOS_ELECTRONICOS_NOMBRE_DOCUMENTO,
				documento.getNombre());
		data.addDetalle(locale,
				ArchivoDetails.DOCUMENTOS_ELECTRONICOS_ID_CLF_PADRE,
				documento.getIdClfPadre());
		data.addDetalle(
				locale,
				documento.getTipoObjeto() == TipoObjeto.ELEMENTO_CF ? ArchivoDetails.DOCUMENTOS_ELECTRONICOS_ID_ELEMENTO_CF
						: ArchivoDetails.DOCUMENTOS_ELECTRONICOS_ID_DESCRIPTOR,
				documento.getIdObjeto());
		data.addDetalle(locale,
				ArchivoDetails.DOCUMENTOS_ELECTRONICOS_TAMANO_FICHERO,
				new Double(documento.getTamanoFich()).toString());
		data.addDetalle(locale,
				ArchivoDetails.DOCUMENTOS_ELECTRONICOS_NOMBRE_ORIGINAL_FICHERO,
				documento.getNombreOrgFich());
		data.addDetalle(locale,
				ArchivoDetails.DOCUMENTOS_ELECTRONICOS_EXTENSION_FICHERO,
				documento.getExtFich());
		data.addDetalle(locale,
				ArchivoDetails.DOCUMENTOS_ELECTRONICOS_ID_FICHERO,
				documento.getIdFich());
		data.addDetalle(locale, ArchivoDetails.DOCUMENTOS_ELECTRONICOS_ESTADO,
				new Integer(documento.getEstado()).toString());
		data.addDetalle(locale,
				ArchivoDetails.DOCUMENTOS_ELECTRONICOS_DESCRIPCION,
				documento.getDescripcion());
	}

	/**
	 * Audita la eliminación de un documento electrónico.
	 * 
	 * @param service
	 *            Servicio a auditar.
	 * @param id
	 *            Identificador del documento electrónico.
	 * @param nombre
	 *            Nombre del documento electrónico.
	 * @param idFich
	 *            Identificador del fichero en el repositorio.
	 */
	public static void auditaEliminacionDocumento(Locale locale,
			IServiceBase service, String id, String nombre, String idFich) {
		// Evento de auditoría
		LoggingEvent event = getLogginEvent(service,
				ArchivoActions.DOCUMENTOS_ELECTRONICOS_MODULE_BAJA_DOCUMENTO);

		// Detalle de auditoría
		DataLoggingEvent data = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_DOCUMENTO, id);

		data.addDetalle(locale,
				ArchivoDetails.DOCUMENTOS_ELECTRONICOS_NOMBRE_DOCUMENTO, nombre);
		data.addDetalle(locale,
				ArchivoDetails.DOCUMENTOS_ELECTRONICOS_ID_FICHERO, idFich);
	}

	// public static void auditaInsercionTarea(LoggingEvent event, DocTCapturaVO
	// tarea)
	// {
	// // Detalle de la auditoría
	// DataLoggingEvent data = event.getDataLoggingEvent(
	// ArchivoObjects.OBJECT_TAREA, tarea.getId());
	//
	// data.addDetalle(ArchivoDetails.DOCUMENTOS_ELECTRONICOS_TITULO_OBJETO,
	// tarea.getTituloObj());
	// data.addDetalle(
	// TipoObjeto.valueOf(tarea.getTipoObj()) == TipoObjeto.ELEMENTO_CF
	// ? ArchivoDetails.DOCUMENTOS_ELECTRONICOS_ID_ELEMENTO_CF
	// : ArchivoDetails.DOCUMENTOS_ELECTRONICOS_ID_DESCRIPTOR,
	// tarea.getIdObj());
	// data.addDetalle(ArchivoDetails.DOCUMENTOS_ELECTRONICOS_USUARIO_CAPTURA,
	// new
	// StringBuffer(tarea.getNombreUsuario()).append(tarea.getApellidosUsuario()).toString());
	// }

	public static void auditaDetalleCambioEstadoTarea(Locale locale,
			LoggingEvent event, DocTCapturaVO tarea, int oldEstado,
			int newEstado) {
		// Detalle de la auditoría
		DataLoggingEvent data = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_TAREA, tarea.getId());

		data.addDetalle(locale,
				ArchivoDetails.DOCUMENTOS_ELECTRONICOS_ESTADO_ANTERIOR,
				new Integer(oldEstado).toString());
		data.addDetalle(locale,
				ArchivoDetails.DOCUMENTOS_ELECTRONICOS_NUEVO_ESTADO,
				new Integer(newEstado).toString());
		data.addDetalle(locale,
				ArchivoDetails.DOCUMENTOS_ELECTRONICOS_TITULO_OBJETO,
				tarea.getTituloObj());
		data.addDetalle(
				locale,
				TipoObjeto.valueOf(tarea.getTipoObj()) == TipoObjeto.ELEMENTO_CF ? ArchivoDetails.DOCUMENTOS_ELECTRONICOS_ID_ELEMENTO_CF
						: ArchivoDetails.DOCUMENTOS_ELECTRONICOS_ID_DESCRIPTOR,
				tarea.getIdObj());
		data.addDetalle(
				locale,
				ArchivoDetails.DOCUMENTOS_ELECTRONICOS_USUARIO_CAPTURA,
				new StringBuffer(tarea.getNombreUsuario()).append(
						tarea.getApellidosUsuario()).toString());
	}

	// public static void auditaEliminarTarea(LoggingEvent event, DocTCapturaVO
	// tarea)
	// {
	// // Detalle de la auditoría
	// DataLoggingEvent data = event.getDataLoggingEvent(
	// ArchivoObjects.OBJECT_TAREA, tarea.getId());
	//
	// data.addDetalle(ArchivoDetails.DOCUMENTOS_ELECTRONICOS_TITULO_OBJETO,
	// tarea.getTituloObj());
	// data.addDetalle(
	// TipoObjeto.valueOf(tarea.getTipoObj()) == TipoObjeto.ELEMENTO_CF
	// ? ArchivoDetails.DOCUMENTOS_ELECTRONICOS_ID_ELEMENTO_CF
	// : ArchivoDetails.DOCUMENTOS_ELECTRONICOS_ID_DESCRIPTOR,
	// tarea.getIdObj());
	// data.addDetalle(ArchivoDetails.DOCUMENTOS_ELECTRONICOS_USUARIO_CAPTURA,
	// new
	// StringBuffer(tarea.getNombreUsuario()).append(tarea.getApellidosUsuario()).toString());
	// }

	public static void auditaDetalleInfoTarea(Locale locale,
			LoggingEvent event, DocTCapturaVO tarea,
			GestionControlUsuariosBI usuarioService) {
		// Detalle de la auditoría
		DataLoggingEvent data = event.getDataLoggingEvent(
				ArchivoObjects.OBJECT_TAREA, tarea.getId());

		data.addDetalle(locale,
				ArchivoDetails.DOCUMENTOS_ELECTRONICOS_TITULO_OBJETO,
				tarea.getTituloObj());
		data.addDetalle(
				locale,
				TipoObjeto.valueOf(tarea.getTipoObj()) == TipoObjeto.ELEMENTO_CF ? ArchivoDetails.DOCUMENTOS_ELECTRONICOS_ID_ELEMENTO_CF
						: ArchivoDetails.DOCUMENTOS_ELECTRONICOS_ID_DESCRIPTOR,
				tarea.getIdObj());

		UsuarioVO usuario = usuarioService.getUsuario(tarea.getIdUsrCaptura());
		if (usuario != null) {
			data.addDetalle(locale,
					ArchivoDetails.DOCUMENTOS_ELECTRONICOS_USUARIO_CAPTURA,
					usuario.getNombreCompleto());
		}
	}

}
