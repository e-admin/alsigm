package common.vos;

/**
 * Clase que encapsula la información de un tipo.
 */
public class TypeDescVO {

	/** Identificador del tipo. */
	private int id = 0;

	/** Descripción del tipo. */
	private String description = null;

	/**
	 * Constructor.
	 */
	public TypeDescVO() {
	}

	/**
	 * Constructor.
	 * 
	 * @param id
	 *            Identificador del tipo.
	 * @param description
	 *            Descripción del tipo.
	 */
	public TypeDescVO(int id, String description) {
		setId(id);
		setDescription(description);
	}

	/**
	 * Obtiene el identificador del tipo.
	 * 
	 * @return Identificador del tipo.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Establece el identificador del tipo.
	 * 
	 * @param id
	 *            Identificador del tipo.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Obtiene la descripción del tipo.
	 * 
	 * @return Descripción del tipo.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Establece la descripción del tipo.
	 * 
	 * @param description
	 *            Descripción del tipo.
	 */
	public void setDescription(String description) {
		this.description = description;
	}
}