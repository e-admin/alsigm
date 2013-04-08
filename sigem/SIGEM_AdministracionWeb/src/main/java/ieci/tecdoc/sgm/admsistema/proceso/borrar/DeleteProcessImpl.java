package ieci.tecdoc.sgm.admsistema.proceso.borrar;

import ieci.tecdoc.sgm.admsistema.proceso.DefaultProcessManagerImpl;
import ieci.tecdoc.sgm.admsistema.proceso.managers.IDbExportManager;
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

public class DeleteProcessImpl extends DefaultProcessManagerImpl {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(DeleteProcessImpl.class);
	
	private String[] dbNames = null;
	
	private Logger deleteLogger = null;
	private String logFile = System.getProperty("user.home") + File.separator + "borrar_${exportEntityId}_${date}.log";
	
	
	/**
	 * Constructor.
	 */
	public DeleteProcessImpl() {
		super();
	}

	public String[] getDbNames() {
		return dbNames;
	}

	public void setDbNames(String[] dbNames) {
		this.dbNames = dbNames;
	}

	/**
	 * Realiza la clonación de una entidad
	 * @param options Parámetros para la clonación
	 * @return true si la clonación se ha realizado con éxito. 
	 * @throws Exception si ocurre algún error.
	 */
	public boolean execute(Map options) throws Exception {

		try {
			
			// Establecer la fecha actual
			options.put("CURRENT_DATE", getCurrentDate());
			
			configureDeleteLogger(options);
	
			if (deleteLogger.isInfoEnabled()) {
				deleteLogger.info("Inicio del proceso de eliminación de entidades");
			}
			
			// Eliminando la entidad
			deleteDatabases(options);
			deleteRepository(options);
	
			if (isOk()) {
				deleteLogger.info("[FIN] Proceso de eliminación de entidades ha finalizado correctamente");
			} else  {
				deleteLogger.warn("[FIN] Proceso de eliminación de entidades ha finalizado con ERRORES");
			}
		} finally {
			releaseLogger(deleteLogger);
		}
		
		return isOk();
	}

	protected void configureDeleteLogger(Map options) throws IOException {
		
		if (options == null) {
			logger.error("No se han encontrado las opciones para configurar el fichero de log");
			setOk(false);
			return;
		}

		// Fichero de log
		String logFileName = replaceOptions(logFile, options);
		File file = new File(logFileName);
		if (logger.isDebugEnabled()) {
			logger.debug("Fichero de log de eliminación de entidades: " + file.getAbsolutePath());
		}
		
		deleteLogger = createLogger("DELETE_LOGGER_" + getCurrentDate() + "_" + (String) options.get(Defs.ID_ENTIDAD_EXP), file);
	}
	
	protected void deleteDatabases(Map options) {

		if (dbNames == null) {
			deleteLogger.error("No se ha especificado ninguna base de datos a exportar.");
			setOk(false);
			return;
		}
		
		if (options == null) {
			deleteLogger.error("No se han encontrado las opciones de exportación de las bases de datos");
			setOk(false);
			return;
		}

		// Obtener el manager para la exportación
		String tipoBD = (String) options.get(Defs.BD_TIPO_BASE_DATOS_EXP);
		if (deleteLogger.isInfoEnabled()) {
			deleteLogger.info("Tipo de base de datos: " + tipoBD);
		}
		
		// Obtener el gestor de exportación de base de datos
		IDbExportManager dbExportManager = getDbExportManager(tipoBD);
		if (dbExportManager == null) {
			deleteLogger.error("Error al eliminar las bases de datos: No se ha definido el gestor de exportación para el tipo de base de datos [" + tipoBD + "]");
			setOk(false);
			return;
		}

		if (deleteLogger.isInfoEnabled()) {
			deleteLogger.info("Gestor de exportación de base de datos: " + dbExportManager);
		}

		for (int i = 0; i < dbNames.length; i++) {
			
			// Nombre de la base de datos
			String dbName = dbNames[i];
			
			
			// Eliminar la base de datos
			boolean ret = dbExportManager.dropDatabase(deleteLogger, dbName, options);
			if (ret) {
				if (deleteLogger.isInfoEnabled()) {
					deleteLogger.info("Base de datos [" + dbName + "] eliminada correctamente.");
				}
			} else {
				deleteLogger.error("Error al eliminar la base de datos [" + dbName + "]");
				setOk(false);
			}
		}
	}
	
	protected void deleteRepository(Map options) {
		
		if (options == null) {
			deleteLogger.error("No se han encontrado las opciones de eliminación del repositorio documental");
			setOk(false);
			return;
		}

		String[] ftpPaths = (String[]) options.get(Defs.FTP_PATH_EXP);
		if (ftpPaths != null) {

			// Eliminar los repositorios de documentos
			boolean ret = getRepoExportManager().deleteRepositories(deleteLogger, ftpPaths, options);
			if (ret) {
				if (deleteLogger.isInfoEnabled()) {
					deleteLogger.info("Repositorios [" + StringUtils.join(ftpPaths, ",") + "] eliminados correctamente.");
				}
			} else {
				deleteLogger.error("Error al eliminar los repositorios [" + StringUtils.join(ftpPaths, ",") + "]");
				setOk(false);
			}
		}
	}


}
