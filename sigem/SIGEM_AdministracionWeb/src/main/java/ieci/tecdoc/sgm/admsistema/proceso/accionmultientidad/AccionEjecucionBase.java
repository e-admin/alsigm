package ieci.tecdoc.sgm.admsistema.proceso.accionmultientidad;

import ieci.tecdoc.sgm.admsistema.proceso.DefaultProcessManagerImpl;
import ieci.tecdoc.sgm.admsistema.proceso.IProcessManager;
import ieci.tecdoc.sgm.admsistema.util.Defs;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * Clase base para la ejecución de las acciones multientidad.
 *
 * @author IECISA
 *
 */
public abstract class AccionEjecucionBase extends DefaultProcessManagerImpl implements IProcessManager  {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(AccionEjecucionBase.class);

	/**
	 * Directorio base para las acciones multientidad.
	 */
	protected String accionMultientidadBaseDir = System.getProperty("user.home") + File.separator + Defs.ACCION_MULTIENTIDAD;

	/**
	 * Logger para el proceso asociado a la acción.
	 */
	protected Logger processActionLogger = null;

	/**
	 * Constructor.
	 */
	public AccionEjecucionBase() {
		// En la clase base se establece el currentDate
		super();
	}

	/**
	 * Configurar el Logger del proceso asociado a la acción.
	 *
	 * @param options Opciones de configuración de la acción.
	 * @param sufixDir Sufijo para el nombre del directorio asociado a la ejecución de la acción.
	 * @param prefixLogName Prefijo para el nombre del Logger asociado a la ejecución de la acción.
	 * @throws IOException
	 */
	protected void configureProcessActionLogger(Map options, String sufixDir, String prefixLogName) throws IOException {

		if (options == null) {
			logger.error("No se han encontrado las opciones para configurar el fichero de Log");
			setOk(false);
			return;
		}

		// Fichero para el Log
		String fileName = getAccionMultientidadBaseDir() + File.separator + getCurrentDate() + sufixDir + File.separator + "estatus";
		File file = new File(fileName);

		if (logger.isDebugEnabled()) {
			logger.debug("Fichero de Log para la accion multientidad: " + file.getAbsolutePath());
		}

		// En la clase base, configurar el Logger para la acción multientidad
		setProcessActionLogger(createLogger(prefixLogName + getCurrentDate(), file));

		if (logger.isDebugEnabled()) {
			logger.debug("Logger para la accion multientidad: " + getProcessActionLogger().getName());
		}
	}

	public String getAccionMultientidadBaseDir() {
		return accionMultientidadBaseDir;
	}

	public void setAccionMultientidadBaseDir(String accionMultientidadBaseDir) {
		this.accionMultientidadBaseDir = accionMultientidadBaseDir;
	}

	public Logger getProcessActionLogger() {
		return processActionLogger;
	}

	public void setProcessActionLogger(Logger processActionLogger) {
		this.processActionLogger = processActionLogger;
	}
}
