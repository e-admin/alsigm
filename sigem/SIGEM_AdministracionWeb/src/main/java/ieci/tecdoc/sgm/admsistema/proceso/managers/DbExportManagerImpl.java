package ieci.tecdoc.sgm.admsistema.proceso.managers;

import ieci.tecdoc.sgm.admsistema.proceso.AccesoBBDD;
import ieci.tecdoc.sgm.admsistema.proceso.managers.clean.IDbCleanManager;
import ieci.tecdoc.sgm.admsistema.proceso.utils.FileUtils;
import ieci.tecdoc.sgm.admsistema.proceso.utils.ProcessUtils;
import ieci.tecdoc.sgm.admsistema.util.Defs;

import java.io.File;
import java.util.Hashtable;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

/** 
 * @author Iecisa 
 * @version $Revision$ 
 *
 */
public class DbExportManagerImpl implements IDbExportManager {

	private static BeanFactory beanFactory = new XmlBeanFactory(new ClassPathResource("admon-entidades-context.xml"));

	/**
	 * Mapa con los comandos de creación de bases de datos personalizados por nombre.
	 */
	private Map customCreateCommands = null;

	/**
	 * Mapa con los comandos de eliminación de bases de datos personalizados por nombre.
	 */
	private Map customDropCommands = null;
	
	/**
	 * Mapa con los comandos de importación de bases de datos personalizados por nombre.
	 */
	private Map customImportCommands = null;
	
	/**
	 * Mapa con los comandos de exportación de bases de datos personalizados por nombre.
	 */
	private Map customExportCommands = null;
	
	/**
	 * Comando de creación de la base de datos.
	 */
	private String createCommand = null;
	
	/**
	 * Comando de importación de la base de datos.
	 */
	private String importCommand = null;
	
	/**
	 * Comando de exportación de la base de datos.
	 */
	private String exportCommand = null;

	/**
	 * Comando de eliminación de la base de datos.
	 */
	private String dropCommand = null;

	
	/**
	 * Constructor.
	 */
	public DbExportManagerImpl() {
		super();
	}
	
	public String getCreateCommand() {
		return createCommand;
	}

	public void setCreateCommand(String createCommand) {
		this.createCommand = createCommand;
	}

	public String getImportCommand() {
		return importCommand;
	}

	public void setImportCommand(String importCommand) {
		this.importCommand = importCommand;
	}

	public String getExportCommand() {
		return exportCommand;
	}

	public void setExportCommand(String exportCommand) {
		this.exportCommand = exportCommand;
	}

	public String getDropCommand() {
		return dropCommand;
	}

	public void setDropCommand(String dropCommand) {
		this.dropCommand = dropCommand;
	}

	public Map getCustomCreateCommands() {
		return customCreateCommands;
	}

	public void setCustomCreateCommands(Map customCreateCommands) {
		this.customCreateCommands = customCreateCommands;
	}

	public Map getCustomDropCommands() {
		return customDropCommands;
	}

	public void setCustomDropCommands(Map customDropCommands) {
		this.customDropCommands = customDropCommands;
	}

	public Map getCustomImportCommands() {
		return customImportCommands;
	}

	public void setCustomImportCommands(Map customImportCommands) {
		this.customImportCommands = customImportCommands;
	}

	public Map getCustomExportCommands() {
		return customExportCommands;
	}

	public void setCustomExportCommands(Map customExportCommands) {
		this.customExportCommands = customExportCommands;
	}

	/**
	 * Crea la base de datos.
	 * @param logger Logger
	 * @param dbName Nombre de la base de datos.
	 * @param options Opciones de creación de la base de datos.
	 * @return true si la base de datos se ha creado correctamente.
	 */
	public boolean createDatabase(Logger logger, String dbName, Map options) {
		
		boolean ret = true;
		
		if (logger.isInfoEnabled()) {
			logger.info("Creando base de datos [" + dbName + "]...");
		}
		
		// Comprobar si hay comando específico
		String cmd = MapUtils.getString(customCreateCommands, dbName);
		
		// Si no hay comando específico, utilizar el genérico
		if (StringUtils.isBlank(cmd)) {
			cmd = createCommand;
		}
		
		// Componer el comando para crear la base de datos
		cmd = replaceOptions(cmd, options);
		cmd = StringUtils.replace(cmd, "${dbName}", dbName);
			
		if (logger.isDebugEnabled()) {
			logger.debug("Comando de creación de BD transformado: " + cmd);
		}

		// Ejecutar el comando de creación de la base de datos
		int exitValue = ProcessUtils.executeCommand(cmd, logger);
		if (exitValue != 0) {
			ret = false;
		}
		
		return ret;
	}
	
	/**
	 * Importa la base de datos.
	 * @param logger Logger
	 * @param dbName Nombre de la base de datos.
	 * @param importFile Fichero para la importación de datos.
	 * @param options Opciones de importación de la base de datos.
	 * @return true si la base de datos se ha importado correctamente.
	 */
	public boolean importDatabase(Logger logger, String dbName, File importFile, Map options) {
		
		boolean ret = true;
		
		if (logger.isInfoEnabled()) {
			logger.info("Importando base de datos [" + dbName + "] desde: " + importFile);
		}
		
		if (importFile != null) {
		
			// Comprobar si hay comando específico
			String cmd = MapUtils.getString(customImportCommands, dbName);
			
			// Si no hay comando específico, utilizar el genérico
			if (StringUtils.isBlank(cmd)) {
				cmd = importCommand;
			}
			
			// Comando de importación nativo a ejecutar
			cmd = replaceOptions(cmd, options);
			cmd = StringUtils.replace(cmd, "${dbName}", dbName);
			cmd = StringUtils.replace(cmd, "${file}", importFile.getAbsolutePath());

			if (logger.isDebugEnabled()) {
				logger.debug("Comando BD transformado: " + cmd);
			}
			
			int exitValue = ProcessUtils.executeCommand(cmd, logger);
			if (exitValue != 0) {
				ret = false;
			}
				
		} else {
			logger.error("No se ha encontrado el fichero de importación de la base de datos [" + dbName + "]: " + importFile);
			ret = false;
		}
		
		return ret;
	}
	
	/**
	 * Limpia la base de datos.
	 * @param logger Logger
	 * @param dbName Nombre de la base de datos.
	 * @param options Opciones de limpieza de la base de datos.
	 * @return true si la base de datos se ha limpiado correctamente.
	 */
	public boolean cleanDatabase(Logger logger, String dbName, Map options) {
		
		boolean ret = false;
		
		if (logger.isInfoEnabled()) {
			logger.info("Limpiando la base de datos [" + dbName + "]...");
		}
		
		// Limpiar la base de datos
		IDbCleanManager dbCleanManager = (IDbCleanManager) beanFactory.getBean(dbName + "DbCleanManager");
		if (dbCleanManager != null) {
			ret = dbCleanManager.clean(logger, options);
		}
		
		return ret;
	}
	
	/**
	 * Actualiza la información del repositorio documental en la base de datos.
	 * 
	 * @param logger
	 *            Logger.
	 * @param dbName
	 *            Nombre de la base de datos que contiene la información del
	 *            repositorio.
	 * @param options
	 *            Opciones de actualización de la base de datos
	 * @return true si la base de datos se ha actualizado correctamente.
	 */
	public boolean updateRepoInfo(Logger logger, String dbName, Map options) {
		
		boolean ret = false;
		
		if (logger.isInfoEnabled()) {
			logger.info("Actualizando la información del repositorio en BD...");
		}
		
		try {
			
			Hashtable hashtable = new Hashtable();
			
			hashtable.put(AccesoBBDD.ID_ENTIDAD, (String) options.get(Defs.ID_ENTIDAD_IMP));

			hashtable.put(AccesoBBDD.FTP_HOST, (String) options.get(Defs.FTP_HOST_IMP));
			hashtable.put(AccesoBBDD.FTP_PUERTO, (String) options.get(Defs.FTP_PUERTO_IMP));
			hashtable.put(AccesoBBDD.FTP_PATH, (String) options.get(Defs.FTP_PATH_IMP));
			hashtable.put(AccesoBBDD.FTP_USUARIO, (String) options.get(Defs.FTP_USUARIO_IMP));
			hashtable.put(AccesoBBDD.FTP_PASS, (String) options.get(Defs.FTP_PASS_IMP));
			
			hashtable.put(AccesoBBDD.BD_HOST, (String) options.get(Defs.BD_HOST_IMP));
			hashtable.put(AccesoBBDD.BD_PUERTO, (String) options.get(Defs.BD_PUERTO_IMP));
			hashtable.put(AccesoBBDD.BD_USUARIO, (String) options.get(Defs.BD_USUARIO_IMP));
			hashtable.put(AccesoBBDD.BD_PASS, (String) options.get(Defs.BD_PASS_IMP));
			hashtable.put(AccesoBBDD.BD_TIPO, (String) options.get(Defs.BD_TIPO_BASE_DATOS_IMP));
			hashtable.put(AccesoBBDD.BD, dbName);
			
			hashtable.put(AccesoBBDD.BD_INSTANCE, (String) options.get(Defs.BD_INSTANCIA_IMP));
			
			String usuarioRpImp = (String) options.get(Defs.BD_USUARIO_RP_IMP);
			String passRpImp = (String) options.get(Defs.BD_PASS_RP_IMP);
			
			if ((usuarioRpImp != null) && (usuarioRpImp.trim().length() > 0)
					&& (passRpImp != null) && (passRpImp.trim().length() > 0)) {
				hashtable.put(AccesoBBDD.BD_USUARIO, usuarioRpImp);
				hashtable.put(AccesoBBDD.BD_PASS, passRpImp);
			}
			
			
			AccesoBBDD.cambiar(hashtable);
			ret = true;
			
		} catch (Exception e) {
			logger.error("Error al actualizar la información del repositorio en BD", e);
		}
		
		return ret;
	}

	/**
	 * Exporta la base de datos.
	 * @param logger Logger
	 * @param dbName Nombre de la base de datos.
	 * @param exportFile Fichero para la exportación de datos.
	 * @param options Opciones de exportación de la base de datos.
	 * @return true si la base de datos se ha exportado correctamente.
	 */
	public boolean exportDatabase(Logger logger, String dbName, File exportFile, Map options) {
		
		boolean ret = true;
		
		if (logger.isInfoEnabled()) {
			logger.info("Exportando base de datos [" + dbName + "] al fichero: " + exportFile);
		}

		if (exportFile != null) {
			
			// Asegurarse de que existe el directorio del fichero
			FileUtils.ensureExistDirectory(exportFile.getParentFile());
								
			// Comprobar si hay comando específico
			String cmd = MapUtils.getString(customExportCommands, dbName);
			
			// Si no hay comando específico, utilizar el genérico
			if (StringUtils.isBlank(cmd)) {
				cmd = exportCommand;
			}
			
			// Componer el comando de exportación nativo a ejecutar
			cmd = replaceOptions(cmd, options);
			cmd = StringUtils.replace(cmd, "${dbName}", dbName);
			cmd = StringUtils.replace(cmd, "${file}", exportFile.getAbsolutePath());
	
			if (logger.isDebugEnabled()) {
				logger.debug("Comando de exportación de BD transformado: " + cmd);
			}
			
			// Ejecutar el comando de exportación de base de datos
			int exitValue = ProcessUtils.executeCommand(cmd, logger);
			if (exitValue != 0) {
				ret = false;
			}
			
		} else {
			logger.error("No se ha especificado el fichero de exportación de la base de datos [" + dbName + "]");
			ret = false;
		}
		
		return ret;
	}

	/**
	 * Elimina la base de datos.
	 * @param logger Logger
	 * @param dbName Nombre de la base de datos.
	 * @param options Opciones de eliminación de la base de datos.
	 * @return true si la base de datos se ha eliminado correctamente.
	 */
	public boolean dropDatabase(Logger logger, String dbName, Map options) {
		
		boolean ret = true;
		
		if (logger.isInfoEnabled()) {
			logger.info("Eliminando la base de datos [" + dbName + "]...");
		}
		
		// Comprobar si hay comando específico
		String cmd = MapUtils.getString(customDropCommands, dbName);
		
		// Si no hay comando específico, utilizar el genérico
		if (StringUtils.isBlank(cmd)) {
			cmd = dropCommand;
		}
		
		// Componer el comando para eliminar la base de datos
		cmd = replaceOptions(cmd, options);
		cmd = StringUtils.replace(cmd, "${dbName}", dbName);
			
		if (logger.isDebugEnabled()) {
			logger.debug("Comando de eliminación de BD transformado: " + cmd);
		}

		// Ejecutar el comando de eliminación de la base de datos
		int exitValue = ProcessUtils.executeCommand(cmd, logger);
		if (exitValue != 0) {
			ret = false;
		}
		
		return ret;
	}

	protected String replaceOptions(String str, Map options) {
		
		if (StringUtils.isNotBlank(str) && (options != null)) {
			
			String importId = (String) options.get(Defs.ID_IMPORTACION);
			String backupEntityId = null;
			if (StringUtils.isNotBlank(importId)) {
				int ix = importId.lastIndexOf("_");
				if (ix > 0) {
					backupEntityId = importId.substring(ix + 1);
				}
			}
			
			str = StringUtils.replace(str, "${home}", System.getProperty("user.home"));
			str = StringUtils.replace(str, "${date}", (String) options.get("CURRENT_DATE"));
			str = StringUtils.replace(str, "${importId}", importId);
			str = StringUtils.replace(str, "${backupEntityId}", backupEntityId);
			
			str = StringUtils.replace(str, "${exportEntityId}", (String) options.get(Defs.ID_ENTIDAD_EXP));

			str = StringUtils.replace(str, "${exportDbHost}", (String) options.get(Defs.BD_HOST_EXP));
			str = StringUtils.replace(str, "${exportDbPort}", (String) options.get(Defs.BD_PUERTO_EXP));
			str = StringUtils.replace(str, "${exportDbUsername}", (String) options.get(Defs.BD_USUARIO_EXP));
			str = StringUtils.replace(str, "${exportDbPassword}", (String) options.get(Defs.BD_PASS_EXP));

			str = StringUtils.replace(str, "${exportFtpHost}", (String) options.get(Defs.FTP_HOST_EXP));
			str = StringUtils.replace(str, "${exportFtpPort}", (String) options.get(Defs.FTP_PUERTO_EXP));
			str = StringUtils.replace(str, "${exportFtpUsername}", (String) options.get(Defs.FTP_USUARIO_EXP));
			str = StringUtils.replace(str, "${exportFtpPassword}", (String) options.get(Defs.FTP_PASS_EXP));

			str = StringUtils.replace(str, "${importEntityId}", (String) options.get(Defs.ID_ENTIDAD_IMP));
			
			str = StringUtils.replace(str, "${importDbHost}", (String) options.get(Defs.BD_HOST_IMP));
			str = StringUtils.replace(str, "${importDbPort}", (String) options.get(Defs.BD_PUERTO_IMP));
			str = StringUtils.replace(str, "${importDbUsername}", (String) options.get(Defs.BD_USUARIO_IMP));
			str = StringUtils.replace(str, "${importDbPassword}", (String) options.get(Defs.BD_PASS_IMP));

			str = StringUtils.replace(str, "${importFtpHost}", (String) options.get(Defs.FTP_HOST_IMP));
			str = StringUtils.replace(str, "${importFtpPort}", (String) options.get(Defs.FTP_PUERTO_IMP));
			str = StringUtils.replace(str, "${importFtpUsername}", (String) options.get(Defs.FTP_USUARIO_IMP));
			str = StringUtils.replace(str, "${importFtpPassword}", (String) options.get(Defs.FTP_PASS_IMP));
		}
		
		return str;
	}
}
