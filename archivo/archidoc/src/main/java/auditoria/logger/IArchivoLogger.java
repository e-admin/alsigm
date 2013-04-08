package auditoria.logger;

import java.util.List;

/**
 * Interface que deben implementar cualquier logger que se cree para la
 * aplicacion
 */
public interface IArchivoLogger {
	/**
	 * Realiza el logging de un evento, almacenando toda la información del
	 * evento en base de datos en funcion de la criticidad, usuario, etc.
	 * 
	 * @param event
	 *            Evento que se desea registrar
	 * @throws LoggingException
	 *             Si se produce un error durante el registro del
	 *             evento.Actualmente no se lanza nunca.
	 */
	public void log(LoggingEvent event) throws LoggingException;

	/**
	 * Realiza el loggin de la pila de eventos que se han almacenado en el
	 * logger
	 * 
	 * @throws LoggingException
	 *             Si se produce un error durante el registro de los eventos.
	 */
	public void log() throws LoggingException;

	/**
	 * Añade un evento a la pila de eventos que se almacenan en el logger.
	 * 
	 * @param event
	 *            Evento que se desea registrar
	 * @throws LoggingException
	 *             Si se produce un error durante el registro del
	 *             evento.Actualmente no se lanza nunca.
	 */
	public void add(LoggingEvent event) throws LoggingException;

	/**
	 * Añade un conjunto de eventos a la pila de eventos que se almacenan en el
	 * logger.
	 * 
	 * @param logginEvents
	 *            Lista de eventos a registrar
	 */
	public void addAll(List logginEvents);

	/**
	 * Devuelve el último elemento de la pila sin sacarlo(operacion top)
	 * 
	 * @return Evento de logging
	 */
	public LoggingEvent peek();
}
