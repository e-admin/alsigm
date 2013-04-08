package auditoria.logger;

import common.db.DbDataSource;

/**
 * Clase Factoria que se encarga de la generación del logger para la aplicacion.
 */
public class ArchivoLogger {

	/** Logger de la clase */
	// private static Logger logger = Logger.getLogger(ArchivoLogger.class);

	/**
	 * Obtiene el logger de la aplicacion.
	 * 
	 * @param Clase
	 *            a la que se asocia el logger
	 * @return Logger {@link IArchivoLogger} de la aplicacion.
	 * @throws LoggingException
	 *             Si se produce un error instanciando el logger de la
	 *             aplicacion.
	 */
	public static IArchivoLogger getLogger(Class clase) throws LoggingException {
		IArchivoLogger appLogger = null;

		// logger.info("Realizando comprobaciones de configuración de Logging");
		// Realizar comprobaciones de configuracion u otras cosas

		// logger.info("Instanciando logger");
		appLogger = new DefaultArchivoLogger(clase);

		return appLogger;
	}

	/**
	 * Obtiene el logger de la aplicacion.
	 * 
	 * @param Clase
	 *            a la que se asocia el logger
	 * @param ds
	 *            DbDataSource para obtener conexiones
	 * @return Logger {@link IArchivoLogger} de la aplicacion.
	 * @throws LoggingException
	 *             Si se produce un error instanciando el logger de la
	 *             aplicacion.
	 */
	public static IArchivoLogger getLogger(Class clase, DbDataSource ds)
			throws LoggingException {
		IArchivoLogger appLogger = null;

		// logger.info("Realizando comprobaciones de configuración de Logging");
		// Realizar comprobaciones de configuracion u otras cosas

		// logger.info("Instanciando logger");
		appLogger = new DefaultArchivoLogger(clase, ds);

		return appLogger;
	}
}
