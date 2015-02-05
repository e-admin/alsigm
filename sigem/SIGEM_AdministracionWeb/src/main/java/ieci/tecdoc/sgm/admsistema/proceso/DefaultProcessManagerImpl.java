package ieci.tecdoc.sgm.admsistema.proceso;

import ieci.tecdoc.sgm.admsistema.proceso.managers.IDbExportManager;
import ieci.tecdoc.sgm.admsistema.proceso.managers.IRepoExportManager;
import ieci.tecdoc.sgm.admsistema.proceso.utils.FileUtils;
import ieci.tecdoc.sgm.admsistema.util.Defs;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

/** 
 * @author Iecisa 
 * @version $Revision$ 
 *
 */
public abstract class DefaultProcessManagerImpl implements IProcessManager {

	/**
	 * Formateador de fechas 
	 */
	private static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
	
	/**
	 * Patrón para los ficheros de log
	 */
	protected static String DEFAULT_LOG_PATTERN = "%d %-5p - %m%n";
	
	//private IDbExportManager dbExportManager = null;
	private Map dbExportManagersMap = null;
	private IRepoExportManager repoExportManager = null;

	private String currentDate = null;
	private boolean isOk = true;


	/**
	 * Constructor.
	 */
	public DefaultProcessManagerImpl() {
		super();
		this.currentDate = SDF.format(new Date());
	}
	
	public IDbExportManager getDbExportManager(String tipoBD) {
		IDbExportManager dbExportManager = null;
		if ((dbExportManagersMap != null) && (tipoBD != null)){
			dbExportManager = (IDbExportManager) dbExportManagersMap.get(tipoBD.toLowerCase());
		}
		return dbExportManager;
	}

	public Map getDbExportManagersMap() {
		return dbExportManagersMap;
	}

	public void setDbExportManagersMap(Map dbExportManagersMap) {
		this.dbExportManagersMap = dbExportManagersMap;
	}

	public IRepoExportManager getRepoExportManager() {
		return repoExportManager;
	}

	public void setRepoExportManager(IRepoExportManager repoExportManager) {
		this.repoExportManager = repoExportManager;
	}

	public String getCurrentDate() {
		return currentDate;
	}

	public boolean isOk() {
		return isOk;
	}

	public void setOk(boolean isOk) {
		this.isOk = isOk;
	}

	protected Logger createLogger(String logName, File logFile) throws IOException {
		
		// Asegurarse de que existe el directorio del fichero
		FileUtils.ensureExistDirectory(logFile.getParentFile());
		
		// Appender
		FileAppender fileAppender = new FileAppender(new PatternLayout(DEFAULT_LOG_PATTERN), logFile.getAbsolutePath());

		// Configurar el logger
		Logger logger = Logger.getLogger(logName);
		logger.addAppender(fileAppender);
		logger.setLevel(Level.ALL);
		
		return logger;
	}

	protected void releaseLogger(Logger logger) throws IOException {
		if (logger != null) {
			logger.removeAllAppenders();
		}
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
