package ieci.tecdoc.sgm.core.services.registro;

/**
 * Información de un libro
 * 
 */
public class BookInfo {

	public static final int LIBRO_ENTRADA = 1;
	public static final int LIBRO_SALIDA = 2;

	/**
	 * Identificador del libro
	 */
	protected Integer id;

	/**
	 * Identificador del archivador asociado al libro
	 */
	protected Integer idocArchId;

	/**
	 * Estado en el que se encuentra el libro
	 * 
	 * @value: 0.- Abierto
	 * @value: 1.- Cerrado
	 */
	protected int state;

	/**
	 * Tipo de libro
	 * 
	 * @value: 1.- Entrada
	 * @value: 2.- Salida
	 */
	protected int type;

	/**
	 * Nombre del libro
	 */
	protected String name;

	/**
	 * Comentarios o información adicional del libro
	 */
	protected String remarks;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("##");
		buffer.append(" id [");
		buffer.append(id);
		buffer.append("] idocArchId [");
		buffer.append(idocArchId);
		buffer.append("] name [");
		buffer.append(name);
		buffer.append("] remarks [");
		buffer.append(remarks);
		buffer.append("] type [");
		buffer.append(type);
		buffer.append("] state [");
		buffer.append(state);

		buffer.append("]");
		return buffer.toString();
	}

	/**
	 * @return the id
	 */
	/**
	 * @return
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	/**
	 * @param id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the idocArchId
	 */
	/**
	 * @return
	 */
	public Integer getIdocArchId() {
		return idocArchId;
	}

	/**
	 * @param idocArchId
	 *            the idocArchId to set
	 */
	/**
	 * @param idocArchId
	 */
	public void setIdocArchId(Integer idocArchId) {
		this.idocArchId = idocArchId;
	}

	/**
	 * @return the state
	 */
	/**
	 * @return
	 */
	public int getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	/**
	 * @param state
	 */
	public void setState(int state) {
		this.state = state;
	}

	/**
	 * @return the type
	 */
	/**
	 * @return
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	/**
	 * @param type
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @return the name
	 */
	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the remarks
	 */
	/**
	 * @return
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks
	 *            the remarks to set
	 */
	/**
	 * @param remarks
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
