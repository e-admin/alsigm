package solicitudes.prestamos.vos;

/**
 * Clase que encapsula un estado de un prestamo
 */
public class EstadoVO {

	private String nombre = null;
	private int id = 0;

	/**
	 * Constructor por defecto
	 */
	public EstadoVO() {
	}

	/**
	 * Constructor con valores
	 */
	public EstadoVO(String nombre, int id) {
		this.id = id;
		this.nombre = nombre;
	}

	/**
	 * Obtiene el identificador del estado
	 * 
	 * @return Identificador del estado
	 */
	public int getId() {
		return id;
	}

	/**
	 * Establece el identificador del estado
	 * 
	 * @param id
	 *            identificador del estado
	 */
	public void setId(int id) {
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