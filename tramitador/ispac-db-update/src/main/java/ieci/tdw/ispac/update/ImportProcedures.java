package ieci.tdw.ispac.update;

import ieci.tdw.ispac.ScriptBase;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IProcedureAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.utils.ArrayUtils;
import ieci.tdw.ispac.ispaclib.utils.FileUtils;
import ieci.tdw.ispac.ispaclib.utils.TypeConverter;

import java.io.File;
import java.net.URL;

import org.apache.log4j.Logger;

public class ImportProcedures extends ScriptBase {
	
	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(ImportProcedures.class); 
	
	/**
	 * Identificador del procedimiento padre para la importación de procedimientos.
	 */
	private static final int DEFAULT_PARENT_PCD_ID = -1;
	
	/**
	 * Directorio con los procedimientos a importar.
	 */
	private static final String DEFAULT_DIRECTORY_PATH = "/pcds";
	
	
	public static void main(String[] args) throws Exception {
		
		checkArguments(args);

		ClientContext context = getClientContext(args);
		
		try {
			
			// Directorio contenedor de los procedimientos
			File directory = getDirectoryFile(args);
			logger.info("Directorio de procedimientos: " + directory);

			// Identificador del procedimiento padre
			int parentPcdId = getParentPcdId(args);
			logger.info("Identificador del procedimiento padre: " + parentPcdId);
			
	        context.beginTX();
	        
			// Importar procedimientos
			importProcedures(context, directory, parentPcdId);

			// Commit de la transacción
			context.endTX(true);

			logger.info("Proceso de importación de procedimientos finalizado");
		
		} catch (Throwable t) {
			
			logger.error("Error en el proceso de importación de procedimientos", t);
			
			// Rollback de la transacción
			context.endTX(false);
			
		} finally {
			context.releaseTX();
		}
	}

	protected static void checkArguments(String[] args) {
		if ((args == null) || (args.length < 4) || (args.length > 6)) {
			logger.error("Argumentos incorrectos (driverClassName url username password [directorio-procedimientos] [id-procedimiento-padre]).");
			System.exit(1);
		}

		try {
			Class.forName(args[0]);
		} catch (ClassNotFoundException cnfe) {
			logger.error("Driver JDBC '" + args[0] + "' no encontrado", cnfe);
			System.exit(1);
		}
	}
	
	protected static File getDirectoryFile(String[] args) {
		
		File directory = null;
		
		if (args.length > 4) {
			directory = new File(args[4]);
		}
		
		if (directory == null) {
			URL url = IProcedureAPI.class.getResource(DEFAULT_DIRECTORY_PATH);
			if (url != null) {
				directory = new File(url.toString());
			}
		}

		return directory;
	}
	
	protected static int getParentPcdId(String[] args) {
		int parentPcdId = DEFAULT_PARENT_PCD_ID;
		if (args.length > 5) {
			parentPcdId = TypeConverter.parseInt(args[5], DEFAULT_PARENT_PCD_ID);
		}
		return parentPcdId;
	}

	protected static void importProcedures(ClientContext context, File directory, int parentPcdId) throws ISPACException {

		if ((directory != null) && directory.isDirectory()) {
			
			// Ficheros con la información de los procedimientos
			File[] pcdFiles = directory.listFiles();
			if (!ArrayUtils.isEmpty(pcdFiles)) {
				
				logger.info("Se van a importar " + pcdFiles.length + " procedimiento/s.");
				
				for (int i = 0; i < pcdFiles.length; i++) {
					if (pcdFiles[i].getName().endsWith(".zip")) {
						importProcedure(context, pcdFiles[i], parentPcdId);
					}
				}
				
			} else {
				logger.warn("No hay procedimientos a importar en el directorio: " + directory);
			}
			
		} else {
			logger.warn("No se encuentra el directorio: " + directory);
		}
	}
	
	protected static void importProcedure(ClientContext context, File pcdFile, int parentPcdId) throws ISPACException {

		logger.info("Inicio de la importación del procedimiento: " + pcdFile);
		
		try {

	        IInvesflowAPI invesFlowAPI = context.getAPI();
	        IProcedureAPI pcdAPI = invesFlowAPI.getProcedureAPI();

	        String importLog = pcdAPI.importProcedure(parentPcdId, pcdFile, false);
				
			FileUtils.writeFile(new File(pcdFile.getAbsolutePath() + ".log"), importLog.getBytes());

			logger.info("Importación del procedimiento [" + pcdFile + "] finalizada");

		} catch (ISPACInfo e) {
			
			if ("exception.procedures.import.existProcedure".equals(e.getMessage())) {
				logger.warn("El procedimiento ya existe: " + pcdFile);
			} else {
				logger.error("Error en la importación del procedimiento: " + pcdFile, e);
				throw new ISPACException(e);
			}

		} catch (Throwable t) {
			logger.error("Error en la importación del procedimiento: " + pcdFile, t);
			throw new ISPACException(t);
		}
	}
	
}