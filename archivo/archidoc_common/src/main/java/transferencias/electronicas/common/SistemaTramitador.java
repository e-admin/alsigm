package transferencias.electronicas.common;

public class SistemaTramitador {
	private String id;
	private String nombre;

	public SistemaTramitador(String id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}
	/**
	 * @return el id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id el codigo a fijar
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return el nombre
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre el nombre a fijar
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
