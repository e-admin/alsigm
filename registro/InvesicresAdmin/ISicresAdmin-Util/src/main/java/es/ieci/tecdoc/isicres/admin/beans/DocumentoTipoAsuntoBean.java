package es.ieci.tecdoc.isicres.admin.beans;

public class DocumentoTipoAsuntoBean {
	private int id;
	private int idMatter;
	private String description;
	private int mandatory;

	// Campos Auxiliares
	private int estado = Estados.SIN_CAMBIOS;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the idMatter
	 */
	public int getIdMatter() {
		return idMatter;
	}

	/**
	 * @param idMatter
	 *            the idMatter to set
	 */
	public void setIdMatter(int idMatter) {
		this.idMatter = idMatter;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the mandatory
	 */
	public int getMandatory() {
		return mandatory;
	}

	/**
	 * @param mandatory
	 *            the mandatory to set
	 */
	public void setMandatory(int mandatory) {
		this.mandatory = mandatory;
	}

	/**
	 * @return the estado
	 */
	public int getEstado() {
		return estado;
	}

	/**
	 * @param estado
	 *            the estado to set
	 */
	public void setEstado(int estado) {
		this.estado = estado;
	}

}
