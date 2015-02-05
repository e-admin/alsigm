package es.ieci.tecdoc.fwktd.sir.api.manager;

/**
 * Interfaz para los managers de generación de códigos hash.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public interface HashManager {

	/**
	 * Genera el código hash de un contenido.
	 * @param contenido Contenido.
	 * @return Código hash del contenido.
	 */
	public byte[] generarHash(byte[] contenido);

}
