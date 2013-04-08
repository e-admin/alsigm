package deposito.db.commonPostgreSQLServer;

public class TuplaIDIDPadreNombre {

	String id = null;
	String idPadre = null;
	String nombre = null;

	public TuplaIDIDPadreNombre() {

	}

	public TuplaIDIDPadreNombre(String id, String idPadre, String nombre) {
		this.id = id;
		this.idPadre = idPadre;
		this.nombre = nombre;
	}

	public String getIdPadre() {
		return idPadre;
	}

	public void setIdPadre(String idPadre) {
		this.idPadre = idPadre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return Returns the id.
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            The id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}
}