package ieci.tecdoc.sgm.admsistema.proceso;

import java.util.Map;

/** 
 * @author Iecisa 
 * @version $Revision$ 
 *
 */
public interface IProcessManager {

	/**
	 * Realiza la ejecución de un proceso
	 * @param options Parámetros para el proceso
	 * @return true si el proceso se ha realizado con éxito. 
	 * @throws Exception si ocurre algún error.
	 */
	public boolean execute(Map options) throws Exception;
	
}
