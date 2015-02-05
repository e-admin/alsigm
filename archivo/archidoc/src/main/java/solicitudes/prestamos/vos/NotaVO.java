package solicitudes.prestamos.vos;

/**
 * Clase que encapsula una nota de un prestamo
 */
public class NotaVO {

	private String nombre = null;
	private String id = null;

	/**
	 * Constructor por defecto
	 */
	public NotaVO() {
	}

	/**
	 * Constructor con valores
	 */
	public NotaVO(String nombre, String id) {
		this.id = id;
		this.nombre = nombre;
	}

	/**
	 * Obtiene el identificador del estado
	 * 
	 * @return Identificador del estado
	 */
	public String getId() {
		return id;
	}

	/**
	 * Establece el identificador del estado
	 * 
	 * @param id
	 *            identificador del estado
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Obtiene el nombre del estado
	 * 
	 * @return nombre del estado
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Establece el nombre del estado
	 * 
	 * @param nombre
	 *            nombre del estado
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}