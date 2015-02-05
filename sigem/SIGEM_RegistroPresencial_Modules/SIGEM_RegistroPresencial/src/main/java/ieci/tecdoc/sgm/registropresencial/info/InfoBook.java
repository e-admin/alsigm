package ieci.tecdoc.sgm.registropresencial.info;

public class InfoBook {

	public static final int LIBRO_ENTRADA = 1;
	public static final int LIBRO_SALIDA = 2;

	private Integer id;
	private Integer idocArchId;
	private int state;
	private int type;

	private String name;
	private String remarks;

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
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the idocArchId
	 */
	public Integer getIdocArchId() {
		return idocArchId;
	}

	/**
	 * @param idocArchId
	 *            the idocArchId to set
	 */
	public void setIdocArchId(Integer idocArchId) {
		this.idocArchId = idocArchId;
	}

	/**
	 * @return the state
	 */
	public int getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(int state) {
		this.state = state;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks
	 *            the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
