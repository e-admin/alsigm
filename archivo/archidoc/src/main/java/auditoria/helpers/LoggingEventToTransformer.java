package auditoria.helpers;

import java.util.Date;

import auditoria.logger.DataLoggingEvent;
import auditoria.logger.LoggingEvent;
import auditoria.vos.DatosTrazaVO;
import auditoria.vos.TrazaVO;

/**
 * Clase de ayuda que se encarga de transformar un evento de logging en
 * distintos objetos para almacenar en BD.
 */
public class LoggingEventToTransformer {
	/**
	 * Convierte un evento de logging en una traza
	 * 
	 * @param event
	 *            LoggingEvent que deseamos transformar
	 * @return Traza generada con los datos del evento de logging
	 */
	public static TrazaVO transformToTraza(LoggingEvent event) {
		TrazaVO traza = new TrazaVO();

		traza.setCodError(event.getCodError());
		traza.setDirIP(event.getUser().getIp());
		traza.setAccion(event.getAction());
		traza.setModulo(event.getModule());
		traza.setIdUsuario(event.getUser().getId());
		traza.setTimeStamp(new Date());

		return traza;
	}

	/**
	 * Convierte un evento de loggin en los datos de una traza
	 * 
	 * @param dataEvent
	 *            {@link DataLoggingEvent} que deseamos transformar
	 * @return Datos de la traza generada con los datos del evento de logging.
	 */
	public static DatosTrazaVO transformToDatosTraza(DataLoggingEvent dataEvent) {
		DatosTrazaVO datosTraza = new DatosTrazaVO();

		datosTraza.setContenido(ContenidoTransformer.fromHashToString(dataEvent
				.getDetalles()));
		datosTraza.setIdObjeto(dataEvent.getIdObject());
		datosTraza.setTipoObjeto(dataEvent.getObject());

		return datosTraza;
	}
}
