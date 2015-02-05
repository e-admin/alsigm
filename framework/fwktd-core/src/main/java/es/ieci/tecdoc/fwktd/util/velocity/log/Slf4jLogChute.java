/*
 * 
 */
package es.ieci.tecdoc.fwktd.util.velocity.log;

import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.log.LogChute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase bridge para poder hacer uso de SLF4J desde Velocity. Por ahora no se da
 * soporte desde el proyecto, por lo que se incorpora esta clase como workaround
 * mientras dan solución a la issue [VELOCITY-621|{@link https
 * ://issues.apache.org/jira/browse/VELOCITY-621}]
 * 
 * 
 * @author IECISA
 * 
 */
public class Slf4jLogChute implements LogChute {

	/**
	 * {@inheritDoc}
	 */
	public void init(RuntimeServices rs) throws Exception {
		// do nothing
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isLevelEnabled(int level) {
		boolean enabled = false;

		switch (level) {
		case TRACE_ID:
			enabled = logger.isTraceEnabled();
			break;
		case DEBUG_ID:
			enabled = logger.isDebugEnabled();
			break;
		case INFO_ID:
			enabled = logger.isInfoEnabled();
			break;
		case WARN_ID:
			enabled = logger.isWarnEnabled();
			break;
		case ERROR_ID:
			enabled = logger.isErrorEnabled();
			break;
		}

		return enabled;
	}

	/**
	 * {@inheritDoc}
	 */
	public void log(int level, String message) {
		switch (level) {
		case TRACE_ID:
			logger.trace(message);
			break;
		case DEBUG_ID:
			logger.debug(message);
			break;
		case INFO_ID:
			logger.info(message);
			break;
		case WARN_ID:
			logger.warn(message);
			break;
		case ERROR_ID:
			logger.error(message);
			break;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void log(int level, String message, Throwable t) {
		switch (level) {
		case TRACE_ID:
			logger.trace(message, t);
			break;
		case DEBUG_ID:
			logger.debug(message, t);
			break;
		case INFO_ID:
			logger.info(message, t);
			break;
		case WARN_ID:
			logger.warn(message, t);
			break;
		case ERROR_ID:
			logger.error(message, t);
			break;
		}
	}

	// Members

	/**
	 * Logger de la clase.
	 */
	protected static final Logger logger = LoggerFactory
			.getLogger(Slf4jLogChute.class);
}
