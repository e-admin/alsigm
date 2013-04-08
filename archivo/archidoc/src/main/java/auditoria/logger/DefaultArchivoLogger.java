package auditoria.logger;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import org.apache.log4j.Logger;
import org.apache.log4j.NDC;

import auditoria.db.CritAccionesDBEntityImpl;
import auditoria.db.CritUsuariosDBEntityImpl;
import auditoria.db.DatosTrazaDBEntityImpl;
import auditoria.db.ICritAccionesDBEntity;
import auditoria.db.ICritUsuariosDBEntity;
import auditoria.db.IDatosTrazaDBEntity;
import auditoria.db.ITrazaDBEntity;
import auditoria.db.TrazaDBEntityImpl;
import auditoria.helpers.LoggingEventToTransformer;
import auditoria.vos.CritUsuarioVO;
import auditoria.vos.DatosTrazaVO;
import auditoria.vos.TrazaVO;

import common.db.DbDataSource;
import common.db.TransactionManagerFactory;

import es.archigest.framework.core.logging.LoggingManager;

/**
 * Implementacion por defecto del logger para la aplicacion
 */
public class DefaultArchivoLogger implements IArchivoLogger {

	/** Logger de la clase */
	private static Logger logger = Logger.getLogger(DefaultArchivoLogger.class);

	/** DbDataSource */
	private DbDataSource ds = null;

	/** Clase a la que esta asociada el logger */
	private Class clase = null;

	/** Pila de eventos */
	private Stack eventos = null;

	/**
	 * Cosntructor por defecto
	 * 
	 * @param clase
	 *            Clase a la que esta asociada el logger
	 */
	public DefaultArchivoLogger(Class clase) {
		this.clase = clase;
		this.eventos = new Stack();
	}

	/**
	 * Cosntructor por defecto
	 * 
	 * @param clase
	 *            Clase a la que esta asociada el logger
	 * @param ds
	 *            DbDataSource
	 */
	public DefaultArchivoLogger(Class clase, DbDataSource ds) {
		this.clase = clase;
		this.ds = ds;
		this.eventos = new Stack();
	}

	/**
	 * Realiza el logging de un evento, almacenando toda la información del
	 * evento en base de datos en funcion de la criticidad, usuario, etc.
	 * 
	 * @param event
	 *            Evento que se desea registrar
	 * @throws Si
	 *             se produce un error durante el registro del
	 *             evento.Actualmente no se lanza nunca.
	 */
	public void log(LoggingEvent event) throws LoggingException {
		if (logger.isDebugEnabled())
			logger.debug("Se va a proceder a registrar el evento:\n"
					+ event.toXML());

		// Comprobamos si el evento es o no registrable
		if (this.isLoggable(event)) {
			// Registramos el evento
			this.logEvent(event);
		}

	}

	/**
	 * Comprueba si el evento pasado es o no registrable por el sistema de
	 * auditoría
	 * 
	 * @param event
	 *            Evento producido
	 * @return Verdadero si se debe registrar el evento o false en caso
	 *         contrario
	 * @throws LoggingException
	 *             Si el logging manager no se ha establecido correctamente
	 */
	private boolean isLoggable(LoggingEvent event) throws LoggingException {
		boolean result = false;
		int actionLogLevel = Integer.MIN_VALUE;

		// Comprobamos si esta correctamente instalado el DS
		if (verifyDS()) {
			actionLogLevel = this.getActionLogLevel(event);

			// Comprobamos el nivel de criticidad de la accion en funcion de si
			// es un evento de usuario o de sistema
			if (event != null && !event.isSystemEvent()) {
				// Evento de usuario
				int userLogLevel = event.getUser().getLogLevel();

				if (userLogLevel != Integer.MAX_VALUE)
					if (actionLogLevel >= userLogLevel)
						result = true;
			} else {
				// Evento de sistema
				int globalLogLevel = getGlobalLogLevel();

				if (globalLogLevel != Integer.MIN_VALUE
						&& actionLogLevel >= globalLogLevel)
					result = true;
			}
		}

		return result;
	}

	/**
	 * Obtiene el nivel de log global de la aplicacion
	 * 
	 * @return Nivel global de log de la aplicación.
	 */
	private int getGlobalLogLevel() {
		ICritUsuariosDBEntity critUsuariosDBEntity = new CritUsuariosDBEntityImpl(
				this.ds);

		return critUsuariosDBEntity
				.getGroupLogLevel(CritUsuarioVO.GLOBAL_GROUP);
	}

	/**
	 * Obtiene el nivel de log de una accion que genero un evento de logging
	 * 
	 * @param event
	 *            Evento producido con la accion
	 * @return Nivel de criticidad de la accion
	 */
	private int getActionLogLevel(LoggingEvent event) {
		ICritAccionesDBEntity cadbe = new CritAccionesDBEntityImpl(this.ds);

		return cadbe.getActionLogLevel(event.getModule(), event.getAction());
	}

	/**
	 * Comprueba si se ha establecido el transaction manager en el logger
	 * 
	 * @return Verdadero si se ha establecido correctamente
	 * @throws LoggingException
	 *             Si el logging manager no se ha establecido correctamente
	 */
	private boolean verifyDS() throws LoggingException {
		if (this.ds == null)
			ds = TransactionManagerFactory.getTransactionManager();
		// throw new
		// LoggingException(DefaultSigiaLogger.class,"verifyTM","No se ha establecido correctamente el TransactionManager para el logger");

		return true;
	}

	/**
	 * Realiza el registro de un evento con la información pasada generando la
	 * traza y la información de los datos pasados.
	 * 
	 * @param event
	 *            Evento que queremos registrar
	 */
	private void logEvent(LoggingEvent event) {
		ITrazaDBEntity trazaEntity = new TrazaDBEntityImpl(this.ds);
		IDatosTrazaDBEntity datosTrazaEntity = new DatosTrazaDBEntityImpl(
				this.ds);

		// Insertamos la traza
		TrazaVO traza = LoggingEventToTransformer.transformToTraza(event);
		traza = trazaEntity.insertTraza(traza);
		if (logger.isDebugEnabled())
			logger.debug("Insertada traza:\n" + traza.toXML());
		// Insertamos la traza en la pista de auditoria del framework
		LoggingManager.getLogging().getAuditingTrack()
				.info("[" + NDC.peek() + "] Insertada traza :" + traza.toXML());

		// Insertamos los datos de la traza si existen
		if (event.getData() != null && event.getData().size() > 0) {
			Iterator it = event.getData().iterator();

			while (it.hasNext()) {
				DatosTrazaVO datosTraza = LoggingEventToTransformer
						.transformToDatosTraza((DataLoggingEvent) it.next());
				datosTraza.setIdTraza(traza.getId());
				datosTraza = datosTrazaEntity.insertDatosTraza(datosTraza);
				if (logger.isDebugEnabled())
					logger.debug("Insertados datos de traza:\n"
							+ datosTraza.toXML());
				// Insertamos los datos asociados en la pista de auditoria del
				// framework
				LoggingManager
						.getLogging()
						.getAuditingTrack()
						.info("[" + NDC.peek() + "] Insertados datos :"
								+ datosTraza.toXML());
			}
		}
	}

	/**
	 * Obtiene la clase a la que está asociado el logger.
	 * 
	 * @return Clase a la que está asociado el logger.
	 */
	public Class getClase() {
		return clase;
	}

	/**
	 * Realiza el loggin de la pila de eventos que se han almacenado en el
	 * logger
	 * 
	 * @throws LoggingException
	 *             Si se produce un error durante el registro de los eventos.
	 */
	public void log() throws LoggingException {
		LoggingEvent event = null;
		Iterator it = this.eventos.iterator();

		while (it.hasNext()) {
			event = (LoggingEvent) it.next();

			this.log(event);
		}
		// Vaciamos la pila
		eventos.removeAllElements();
	}

	/**
	 * Añade un evento a la pila de eventos que se almacenan en el logger.
	 * 
	 * @param event
	 *            Evento que se desea registrar
	 * @throws LoggingException
	 *             Si se produce un error durante el registro del
	 *             evento.Actualmente no se lanza nunca.
	 */
	public void add(LoggingEvent event) throws LoggingException {
		if (logger.isDebugEnabled())
			logger.debug("Se va a añadir a la pila el evento:\n"
					+ event.toXML());

		this.eventos.add(event);
	}

	/**
	 * Añade un conjunto de enventos a la pila de eventos que se almacenan en el
	 * logger
	 * 
	 * @param logginEvent
	 *            Lista de eventos a registrar
	 */
	public void addAll(List loggingEvents) {
		if (loggingEvents != null) {
			if (logger.isDebugEnabled())
				for (Iterator i = loggingEvents.iterator(); i.hasNext();)
					logger.debug("Se añade a la pila el evento:\n"
							+ ((LoggingEvent) i.next()).toXML());
			this.eventos.addAll(loggingEvents);
		}
	}

	public DbDataSource getDs() {
		return ds;
	}

	public void setDs(DbDataSource ds) {
		this.ds = ds;
	}

	public LoggingEvent peek() {
		LoggingEvent event = null;

		if (!eventos.isEmpty())
			event = (LoggingEvent) eventos.peek();

		return event;
	}
}
