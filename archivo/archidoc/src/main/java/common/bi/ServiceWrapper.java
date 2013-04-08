package common.bi;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;

import org.apache.log4j.Logger;

import auditoria.ArchivoErrorCodes;
import auditoria.logger.LoggingEvent;

import common.db.ITransactionManager;
import common.exceptions.ActionNotAllowedException;
import common.exceptions.ArchivoModelException;
import common.exceptions.CheckedArchivoException;
import common.exceptions.DBException;
import common.startup.ProfileLogLevel;

class ServiceWrapper implements InvocationHandler {

	private final static Logger logger = Logger.getLogger(ServiceWrapper.class);

	protected static final Logger PROFILE_LOGGER = Logger.getLogger("PROFILE");

	IServiceBase wrappedService = null;

	ServiceWrapper(IServiceBase wrappedService) {
		this.wrappedService = wrappedService;
	}

	private void logParameters(StringBuffer buffer, Object[] args) {
		if ((args != null) && (args.length > 0)) {
			buffer.append("(");
			for (int i = 0; i < args.length; i++) {
				if (args[i] != null) {
					if (i > 0) {
						buffer.append(",");
					}
					if (args[i] instanceof Object[]) {
						logParameters(buffer, (Object[]) args[i]);
					} else if (args[i] instanceof Collection) {
						logParameters(buffer, ((Collection) args[i]).toArray());
					} else {
						buffer.append(args[i]);
					}
				}
			}
			buffer.append(")");
		}
	}

	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		if (logger.isDebugEnabled())
			logger.debug("Llamada metodo de servicio "
					+ wrappedService.getClass().getName() + "::"
					+ method.getName());
		ITransactionManager txManager = wrappedService.getTransactionManager();
		try {
			if (PROFILE_LOGGER.isEnabledFor(ProfileLogLevel.BEGIN_BI)) {
				StringBuffer logMessage = new StringBuffer()
						.append(System.currentTimeMillis()).append(" [")
						.append(wrappedService.getClass().getName())
						.append("::").append(method.getName());

				logParameters(logMessage, args);

				logMessage.append("]");

				PROFILE_LOGGER.log(ProfileLogLevel.BEGIN_BI,
						logMessage.toString());
			}
			Object returnObj = method.invoke(wrappedService, args);

			// Log the events
			wrappedService.getLogger().log();

			if (PROFILE_LOGGER.isEnabledFor(ProfileLogLevel.END_BI))
				PROFILE_LOGGER.log(ProfileLogLevel.END_BI,
						String.valueOf(System.currentTimeMillis()));

			if (logger.isDebugEnabled())
				logger.debug("Llamada a servicio completada");
			return returnObj;
		} catch (InvocationTargetException ite) {
			Throwable te = ite.getTargetException();
			Throwable serviceException = te;
			if (logger.isDebugEnabled())
				logger.debug("Excepcion durante llamada al servicio "
						+ te.getClass().getName());
			txManager.rollback();
			int errorCode = ArchivoErrorCodes.ERROR_DESCONOCIDO;
			if (te instanceof ActionNotAllowedException)
				errorCode = ((ActionNotAllowedException) te).getCodError();
			else if (te instanceof common.exceptions.SecurityException)
				errorCode = ArchivoErrorCodes.ERROR_ACCION_NO_PERMITIDA;
			else if (!(te instanceof CheckedArchivoException)
					&& !(te instanceof DBException)) {
				String message = te.getMessage();
				if (message == null)
					message = te.getClass().getName();
				serviceException = new ArchivoModelException(
						wrappedService.getClass(), method.getName(), message);
			}
			if (errorCode != ArchivoErrorCodes.ERROR_DESCONOCIDO) {
				// Log del error
				LoggingEvent event = wrappedService.getLogger().peek();
				if (event != null)
					event.setCodError(errorCode);
				wrappedService.getLogger().log();
			}
			/*
			 * if (te instanceof ActionNotAllowedException) {
			 * ActionNotAllowedException anae = (ActionNotAllowedException)te;
			 * 
			 * //Log del error LoggingEvent event =
			 * wrappedService.getLogger().peek();
			 * 
			 * if (event!=null) event.setCodError( anae.getCodError() );
			 * 
			 * wrappedService.getLogger().log();
			 * 
			 * } else { if (te instanceof common.exceptions.SecurityException) {
			 * //Log del error LoggingEvent event =
			 * wrappedService.getLogger().peek();
			 * 
			 * if (event!=null) event.setCodError(
			 * ArchivoErrorCodes.ERROR_ACCION_NO_PERMITIDA );
			 * 
			 * wrappedService.getLogger().log(); } else { if (!(te instanceof
			 * CheckedArchivoException)) { String message = te.getMessage(); if
			 * (message == null) message = te.getClass().getName();
			 * serviceException = new
			 * ArchivoModelException(wrappedService.getClass(),
			 * method.getName(), message); } } }
			 */
			if (PROFILE_LOGGER.isEnabledFor(ProfileLogLevel.ERROR_BI))
				PROFILE_LOGGER.log(ProfileLogLevel.ERROR_BI,
						String.valueOf(System.currentTimeMillis()), te);

			throw serviceException;
		} finally {
			txManager.liberarConexion();
		}
	}
}