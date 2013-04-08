package ieci.tecdoc.sgm.consolidacion.mgr;

import ieci.tecdoc.sgm.core.exception.SigemException;

/**
 * Interfaz para los managers de consolidación.
 *
 */
public interface ConsolidacionManager {

	/**
	 * Realiza el proceso de consolidación sobre una entidad. 
	 * @param idEntidad Identificador de la entidad.
	 * @throws SigemException si ocurre algún error.
	 */
	public abstract void consolidaEntidad(String idEntidad)
			throws SigemException;

}