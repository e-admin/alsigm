package ieci.tecdoc.sgm.admsistema.proceso.accionmultientidad;

import ieci.tecdoc.sgm.admsistema.proceso.IProcessManager;

import java.util.Map;

import org.apache.log4j.Logger;

/**
 * Clase encargada de ejecutar una accion de multientidad
 * @author IECISA
 *
 */
public class EjecutarAccion implements Runnable {

	/**
	 * Constructor
	 * @param params Parametros de ejecucion
	 */
	public EjecutarAccion(Map params) {
		super();
		setParameters(params);

		String nombreClaseEjecutora = (String)params.get(PARAM_ACCION_MULTIENTIDAD_NOMBRE_CLASE_EJECUTORA);
		try {
			ejecutarAccionProcess = instanceEjecucionAccion(nombreClaseEjecutora);
		} catch (Exception e) {
			logger.error(e);
		}
	}

	/**
	 * Permite instanciar la accion de ejecucuib necesaria
	 * @param nombreClaseEjecutora nombre de la clase ejecutora
	 * @return
	 * @throws Exception
	 */
	private IProcessManager instanceEjecucionAccion(String nombreClaseEjecutora) throws Exception{
		Class claseEjecutora = Class.forName(nombreClaseEjecutora);
		return (IProcessManager) claseEjecutora.newInstance();
	}

	public Map getParameters() {
		return parameters;
	}

	public void setParameters(Map parameters) {
		this.parameters = parameters;
	}

	public void run() {
		try {

			if (ejecutarAccionProcess != null) {
				ejecutarAccionProcess.execute(parameters);
			} else {
				logger.error("No se ha cargado correctamente la clase de ejecucion");
			}

		} catch (Throwable t) {
			logger.error("Error en el proceso de ejecucion de accion de multientidad", t);
		}
	}

	/**
	 * Metodo para iniciar la ejecucion de una accion. Es necesario que en params se incluyan
	 * los siguientes parametros de configuracion:
	 *
	 * <ul>
	 * <li>EjecutarAccion.PARAM_ACCION_MULTIENTIDAD_VO = AccionMultientidadVO con la informacion de configuracion de la accion</li>
	 * <li>EjecutarAccion.PARAM_ACCION_MULTIENTIDAD_NOMBRE_CLASE_EJECUTORA = String con el nombre de la clase ejecutora</li>
	 * </ul>
	 *
	 * @param params
	 * @return
	 */
	public static boolean ejecutarAccion(Map params) {

		Thread process = new Thread(new EjecutarAccion(params));
		process.start();

		return true;
	}

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(EjecutarAccion.class);

	/**
	 * Parametros de exportacion
	 */
	private Map parameters = null;

	/**
	 * Gestor de ejecucion de acciones de multientidad
	 */
	private IProcessManager ejecutarAccionProcess = null;

	/**
	 * Nombre del parametro con el VO de configuracion de la accion
	 */
	public static final String PARAM_ACCION_MULTIENTIDAD_VO = "ACCION_MULTIENTIDAD_VO";

	/**
	 * Nombre del parametro con el nombre de la clase ejecutora
	 */
	public static final String PARAM_ACCION_MULTIENTIDAD_NOMBRE_CLASE_EJECUTORA = "ACCION_MULTIENTIDAD_NOMBRE_CLASE_EJECUTORA";

	/**
	 * Nombre del parametro con la sesión en la aplicación de administración
	 */
	public static final String PARAM_SESION_APP_ADMINISTRACION = "SESION_APP_ADMINISTRACION";
}
