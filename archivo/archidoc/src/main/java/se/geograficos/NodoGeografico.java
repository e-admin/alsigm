package se.geograficos;

/**
 * Interfaz para la información de un elemento dentro de la jerarquía
 * geográfica.
 */
public interface NodoGeografico {

	/**
	 * Devuelve el código. Si no tiene código asociado se devuelve nulo.
	 * 
	 * @return Código.
	 */
	public String getCodigo();

	/**
	 * Devuelve el nombre del nodo.
	 * 
	 * @return Nombre del nodo.
	 */
	public String getNombre();
}
