package common.vos;

/**
 * Interfaz para los objetos restringibles.
 */
public interface Restringible {

	/**
	 * Obtiene el nivel de acceso al objeto.
	 * 
	 * @return Nivel de acceso.
	 */
	public int getNivelAcceso();

	/**
	 * Obtiene el identificador del archivo receptor.
	 * 
	 * @return Identificador del archivo receptor.
	 */
	public String getIdArchivo();

	/**
	 * Obtiene el identificador de la lista de control de acceso.
	 * 
	 * @return Identificador de la lista de control de acceso.
	 */
	public String getIdLCA();

}
