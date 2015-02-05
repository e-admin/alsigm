package ieci.tecdoc.sgm.core.services.rpadmin;

/**
 * Libro de registro
 * 
 */
public class Libro {

	public static final int LIBRO_ENTRADA = 1;
	public static final int LIBRO_SALIDA = 2;

	public static final int ABRIR_LIBRO = 0;
	public static final int CERRAR_LIBRO = 1;

	private int id;
	private String nombre;
	private int tipo;
	private int estado;

	/**
	 * @return
	 */
	public int getEstado() {
		return estado;
	}

	/**
	 * @param estado
	 */
	public void setEstado(int estado) {
		this.estado = estado;
	}

	/**
	 * @return
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return
	 */
	public int getTipo() {
		return tipo;
	}

	/**
	 * @param tipo
	 */
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
}
