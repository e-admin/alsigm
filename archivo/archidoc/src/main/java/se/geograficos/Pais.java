package se.geograficos;

/**
 * Información de un país.
 */
public class Pais implements NodoGeografico {

	/** Código del nodo geográfico. */
	private String codigo = null;

	/** Nombre del nodo geográfico. */
	private String nombre = null;

	/**
	 * Constructor.
	 */
	public Pais() {
	}

	/**
	 * Constructor.
	 * 
	 * @param codigo
	 *            Código del país.
	 * @param nombre
	 *            Nombre del país.
	 */
	public Pais(String codigo, String nombre) {
		setCodigo(codigo);
		setNombre(nombre);
	}

	/**
	 * Devuelve el código. Si no tiene código asociado se devuelve nulo.
	 * 
	 * @return Código.
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * Devuelve el nombre del nodo.
	 * 
	 * @return Nombre del nodo.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Establece el código del nodo geográfico.
	 * 
	 * @param codigo
	 *            Código del nodo geográfico.
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * Establece el nombre del nodo geográfico.
	 * 
	 * @param nombre
	 *            Nombre del nodo geográfico.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
