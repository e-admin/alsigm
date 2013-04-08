package ieci.tecdoc.sgm.admsistema.proceso.clonar;

import ieci.tecdoc.sgm.admsistema.proceso.DefaultProcessManagerImpl;
import ieci.tecdoc.sgm.admsistema.proceso.managers.IDbExportManager;
import ieci.tecdoc.sgm.admsistema.proceso.utils.FileUtils;
import ieci.tecdoc.sgm.admsistema.util.Defs;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Iecisa
 * @version $Revision$
 *
 */

public class CloneProcessImpl extends DefaultProcessManagerImpl {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(CloneProcessImpl.class);

	private String[] dbNames = null;
	private String repositoryDbName = null;

	private String exportBaseDir = System.getProperty("user.home") + File.separator + Defs.EXPORTAR;
	private Logger exportLogger = null;

	private String importBaseDir = System.getProperty("user.home") + File.separator + Defs.IMPORTAR;
	private Logger importLogger = null;


	/**
	 * Constructor.
	 */
	public CloneProcessImpl() {
		super();
	}

	/**
	 * @return the dbNames
	 */
	public String[] getDbNames() {
		return dbNames;
	}

	/**
	 * @param dbNames the dbNames to set
	 */
	public void setDbNames(String[] dbNames) {
		this.dbNames = dbNames;
	}

	public Logger getExportLogger() {
		return exportLogger;
	}

	public Logger getImportLogger() {
		return importLogger;
	}

	public String getRepositoryDbName() {
		return repositoryDbName;
	}

	public void setRepositoryDbName(String repositoryDbName) {
		this.repositoryDbName = repositoryDbName;
	}

	/**
	 * Realiza la clonación de una entidad
	 * @param options Parámetros para la clonación
	 * @return true si la clonación se ha realizado con éxito.
	 * @throws Exception si ocurre algún error.
	 */
	public boolean execute(Map options) throws Exception {

		if (logger.isInfoEnabled()) {
			logger.info("Inicio del proceso de clonación");
		}

		try {

			// Exportando entidad
			executeExport(options);

			if (isOk()) {
				exportLogger.info("[FIN] Proceso de exportación finalizado correctamente");
			} else  {
				exportLogger.warn("[FIN] Proceso de exportación finalizado con ERRORES");
			}

		} finally {
			releaseLogger(exportLogger);
		}

		if (isOk()) {

			try {

				// Importando entidad
				executeImport(options);

				if (isOk()) {
					importLogger.info("[FIN] Proceso de importación finalizado correctamente");
				} else  {
					importLogger.warn("[FIN] Proceso de importación finalizado con ERRORES");
				}
			} finally {
				releaseLogger(importLogger);
			}
		}

		if (isOk()) {
			logger.info("Proceso de clonación ha finalizado correctamente");
		} else  {
			logger.warn("Proceso de clonación ha finalizado con ERRORES");
		}

		return isOk();
	}

	protected void executeExport(Map options) throws Exception {

		// Establecer la fecha actual
		options.put("CURRENT_DATE", getCurrentDate());

		configureExportLogger(options);

		if (exportLogger.isInfoEnabled()) {
			exportLogger.info("Inicio del proceso de exportación");
		}

		exportDatabases(options);
		exportRepository(options);
	}

	protected void executeImport(Map options) throws Exception {

		// Establecer la fecha actual
		options.put("CURRENT_DATE", getCurrentDate());

		configureImportLogger(options);

		if (importLogger.isInfoEnabled()) {
			importLogger.info("Inicio del proceso de importación");
		}

		importDatabases(options);
		updateRepoInfoInDb(options);
		importRepository(options);
	}

	protected void configureExportLogger(Map options) throws IOException {

		if (options == null) {
			logger.error("No se han encontrado las opciones para configurar el fichero de log");
			setOk(false);
			return;
		}

		String exportEntityId = (String) options.get(Defs.ID_ENTIDAD_EXP);

		// Fichero de log
		//String fileName = replaceOptions(exportLogFile, options);
		String fileName = exportBaseDir + File.separator + getCurrentDate() + "_" + exportEntityId + File.separator + "estatus";
		File file = new File(fileName);

		if (logger.isDebugEnabled()) {
			logger.debug("Fichero de log de exportación: " + file.getAbsolutePath());
		}

		// Configurar el logger de exportación
		exportLogger = createLogger("EXPORT_LOGGER_" + getCurrentDate() + "_" + exportEntityId, file);
	}

	protected void exportDatabases(Map options) {

		if (dbNames == null) {
			exportLogger.error("No se ha especificado ninguna base de datos a exportar.");
			setOk(false);
			return;
		}

		if (options == null) {
			exportLogger.error("No se han encontrado las opciones de exportación de las bases de datos");
			setOk(false);
			return;
		}

		// Obtener el manager para la exportación
		String tipoBD = (String) options.get(Defs.BD_TIPO_BASE_DATOS_EXP);
		if (exportLogger.isInfoEnabled()) {
			exportLogger.info("Tipo de base de datos: " + tipoBD);
		}

		// Obtener el gestor de exportación de base de datos
		IDbExportManager dbExportManager = getDbExportManager(tipoBD);
		if (dbExportManager == null) {
			exportLogger.error("Error al eliminar las bases de datos: No se ha definido el gestor de exportación para el tipo de base de datos [" + tipoBD + "]");
			setOk(false);
			return;
		}

		if (exportLogger.isInfoEnabled()) {
			exportLogger.info("Gestor de exportación de base de datos: " + dbExportManager);
		}

		String backupId = getCurrentDate() + "_" + (String) options.get(Defs.ID_ENTIDAD_EXP);
		options.put(Defs.ID_IMPORTACION, backupId);

		for (int i = 0; i < dbNames.length; i++) {

			// Nombre de la base de datos
			String dbName = dbNames[i];

			// Fichero de exportación
			String fileName = exportBaseDir + File.separator + backupId + File.separator + "bd" + File.separator + dbName + ".backup";

			// Exportar la base de datos
			boolean ret = dbExportManager.exportDatabase(exportLogger, dbName, new File(fileName), options);
			if (ret) {
				if (exportLogger.isInfoEnabled()) {
					exportLogger.info("Base de datos [" + dbName + "] exportada correctamente.");
				}
			} else {
				exportLogger.error("Error al exportar la base de datos [" + dbName + "]");
				setOk(false);
			}
		}
	}

	protected void exportRepository(Map options) {

		if (options == null) {
			exportLogger.error("No se han encontrado las opciones de exportación del repositorio documental");
			setOk(false);
			return;
		}

		String[] ftpPaths = (String[]) options.get(Defs.FTP_PATH_EXP);
		if (ftpPaths != null) {

			String exportEntityId = (String) options.get(Defs.ID_ENTIDAD_EXP);

			for (int i = 0; i < ftpPaths.length; i++) {

				// Fichero de exportación
				String directory = exportBaseDir + File.separator + getCurrentDate() + "_" + exportEntityId
					+ File.separator + "SIGEM_Repository";

				// Exportar el repositorio de documentos
				boolean ret = getRepoExportManager().exportRepository(exportLogger, ftpPaths[i], new File(directory), options);
				if (ret) {
					if (exportLogger.isInfoEnabled()) {
						exportLogger.info("Repositorio [" + ftpPaths[i] + "] exportado correctamente.");
					}
				} else {
					exportLogger.error("Error al exportar el repositorio [" + ftpPaths[i] + "]");
					setOk(false);
				}
			}
		}
	}

	protected void configureImportLogger(Map options) throws IOException {

		if (options == null) {
			logger.error("No se han encontrado las opciones para configurar el fichero de log");
			setOk(false);
			return;
		}

		String importEntityId = (String) options.get(Defs.ID_ENTIDAD_IMP);

		// Fichero de log
		String fileName = importBaseDir + File.separator + getCurrentDate() + "_" + importEntityId + File.separator + "estatus";
		File file = new File(fileName);

		if (logger.isDebugEnabled()) {
			logger.debug("Fichero de log de importación: " + file.getAbsolutePath());
		}

		// Configurar el logger de exportación
		importLogger = createLogger("IMPORT_LOGGER_" + getCurrentDate() + "_" + importEntityId, file);
	}

	protected void importDatabases(Map options) {

		if (dbNames == null) {
			importLogger.error("No se ha especificado ninguna base de datos a importar.");
			setOk(false);
			return;
		}

		if (options == null) {
			importLogger.error("No se han encontrado las opciones de importación de las bases de datos");
			setOk(false);
			return;
		}

		String importId = (String) options.get(Defs.ID_IMPORTACION);
		if (StringUtils.isBlank(importId)) {
			importLogger.error("No se ha encontrado el identificador de importación de las bases de datos");
			setOk(false);
			return;
		}

		// Obtener el manager para la importación
		String tipoBD = (String) options.get(Defs.BD_TIPO_BASE_DATOS_IMP);
		if (importLogger.isInfoEnabled()) {
			importLogger.info("Tipo de base de datos: " + tipoBD);
		}

		// Obtener el gestor de importación (el mismo que el de exportación) de base de datos
		IDbExportManager dbExportManager = getDbExportManager(tipoBD);
		if (dbExportManager == null) {
			importLogger.error("Error al eliminar las bases de datos: No se ha definido el gestor de importación para el tipo de base de datos [" + tipoBD + "]");
			setOk(false);
			return;
		}

		if (importLogger.isInfoEnabled()) {
			importLogger.info("Gestor de importación de base de datos: " + dbExportManager);
		}

		String dbCleaning = (String) options.get(Defs.LIMPIAR_BD);

		for (int i = 0; i < dbNames.length; i++) {

			// Nombre de la base de datos
			String dbName = dbNames[i];

			if (importLogger.isDebugEnabled()) {
				importLogger.info("Importando base de datos [" + dbName + "]...");
			}

			// Fichero de importación
			String fileName = null;
			if (StringUtils.isNotBlank(importId)) {
				fileName = exportBaseDir + File.separator + importId + File.separator + "bd" + File.separator + dbName + ".backup";
			}

			if (StringUtils.isNotBlank(fileName)) {

				File importFile = new File(fileName);

				// Crear la base de datos
				boolean ret = dbExportManager.createDatabase(importLogger, dbName, options);
				if (ret) {

					if (importLogger.isInfoEnabled()) {
						importLogger.info("Base de datos [" + dbName + "] creada correctamente.");
					}

					// Importar la base de datos
					ret = dbExportManager.importDatabase(importLogger, dbName, importFile, options);
					if (ret) {

						if (importLogger.isInfoEnabled()) {
							importLogger.info("Base de datos [" + dbName + "] importada correctamente.");
						}

						// Limpiar base de datos si se ha seleccionado así
						if ("0".equals(dbCleaning)) {

							ret = dbExportManager.cleanDatabase(importLogger, dbName, options);
							if (ret) {
								if (importLogger.isInfoEnabled()) {
									importLogger.info("Limpieza de base de datos " + dbName + " realizada correctamente");
								}
							} else {
								importLogger.error("Error en la limpieza de la base de datos [" + dbName + "]");
								setOk(false);
							}

						} else {
							importLogger.debug("Limpieza de base de datos desactivada");
						}
					} else {
						importLogger.error("Error al importar la base de datos [" + dbName + "]");
						setOk(false);
					}

				} else {
					importLogger.error("Error al crear la base de datos [" + dbName + "]");
					setOk(false);
				}

			} else {
				importLogger.error("No se ha encontrado el fichero de importación de la base de datos [" + dbName + "]");
				setOk(false);
			}
		}
	}

	protected void updateRepoInfoInDb(Map options) {

		if (options == null) {
			exportLogger.error("No se han encontrado las opciones para la actualización de la información del repositorio");
			setOk(false);
			return;
		}

		// Obtener el manager para la importación
		String tipoBD = (String) options.get(Defs.BD_TIPO_BASE_DATOS_IMP);
		if (importLogger.isInfoEnabled()) {
			importLogger.info("Tipo de base de datos: " + tipoBD);
		}

		// Obtener el gestor de importación de base de datos
		IDbExportManager dbExportManager = getDbExportManager(tipoBD);
		if (dbExportManager == null) {
			importLogger.error("Error al eliminar las bases de datos: No se ha definido el gestor de importación para el tipo de base de datos [" + tipoBD + "]");
			setOk(false);
			return;
		}

		if (importLogger.isInfoEnabled()) {
			importLogger.info("Gestor de importación de base de datos: " + dbExportManager);
		}

		boolean ret = dbExportManager.updateRepoInfo(importLogger, repositoryDbName, options);
		if (ret) {
			if (importLogger.isInfoEnabled()) {
				importLogger.info("Información del repositorio en BD [" + repositoryDbName + "]actualizada correctamente");
			}
		} else {
			importLogger.error("Error en la actualización de la información del repositorio en base de datos [" + repositoryDbName + "]");
			setOk(false);
		}

	}

	protected void importRepository(Map options) {

		if (options == null) {
			importLogger.error("No se han encontrado las opciones de importación del repositorio documental");
			setOk(false);
			return;
		}

		String importId = (String) options.get(Defs.ID_IMPORTACION);
		if (StringUtils.isBlank(importId)) {
			importLogger.error("No se ha encontrado el identificador de importación del repositorio documental");
			setOk(false);
			return;
		}

		String dbCleaning = (String) options.get(Defs.LIMPIAR_BD);
		String importEntityId = (String) options.get(Defs.ID_ENTIDAD_IMP);

		// Directorio de importación
		File ftpRepoDir = null;

		// Limpiar base de datos si se ha seleccionado así
		if ("0".equals(dbCleaning)) {

			// Limpiar contenido del repositorio
			File srcDir = new File(exportBaseDir + File.separator + importId + File.separator + "SIGEM_Repository");
			ftpRepoDir = new File(importBaseDir + File.separator + getCurrentDate() + "_" + importEntityId + File.separator + "SIGEM_Repository");

			FileUtils.ensureExistDirectory(ftpRepoDir);

			getRepoExportManager().cleanRepository(importLogger, srcDir, ftpRepoDir);

		} else {
			if (StringUtils.isNotBlank(importId)) {
				ftpRepoDir = new File(exportBaseDir + File.separator + importId + File.separator + "SIGEM_Repository");
			}
		}

		if ((ftpRepoDir == null) || !ftpRepoDir.isDirectory()) {
			importLogger.error("El directorio de importación no existe: " + ftpRepoDir);
			setOk(false);
			return;
		}

		File[] ftpHosts = ftpRepoDir.listFiles();
		if ((ftpHosts == null) || (ftpHosts.length == 0)) {
			importLogger.error("El directorio de importación no existe: " + ftpRepoDir);
			setOk(false);
			return;
		}

		for (int contHosts = 0; contHosts < ftpHosts.length; contHosts++) {

			if (importLogger.isInfoEnabled()) {
				importLogger.info("Importando repositorios del host: " + ftpHosts[contHosts]);
			}

			File[] ftpPaths = ftpHosts[contHosts].listFiles();
			for (int i = 0; i < ftpPaths.length; i++) {

				boolean ret = getRepoExportManager().importRepository(importLogger, ftpPaths[i], options);
				if (ret) {
					if (importLogger.isInfoEnabled()) {
						importLogger.info("Repositorio [" + ftpPaths[i].getName() + "] importado correctamente.");
					}
				} else {
					importLogger.error("Error al importar el repositorio [" + ftpPaths[i].getName() + "]");
					setOk(false);
				}
			}
		}
	}
}
