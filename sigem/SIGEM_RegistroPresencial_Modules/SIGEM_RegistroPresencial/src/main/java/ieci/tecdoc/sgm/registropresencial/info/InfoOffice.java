package ieci.tecdoc.sgm.registropresencial.info;


/**
 * 
 * @author 66575267
 * 
 */
public class InfoOffice {

	private Integer id;
	private String code;
	private String acron;
	private String name;
	private int deptid;

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
		buffer.append("] code [");
		buffer.append(code);
		buffer.append("] name [");
		buffer.append(name);
		buffer.append("] acron [");
		buffer.append(acron);
		buffer.append("] deptid [");
		buffer.append(deptid);

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
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the acron
	 */
	public String getAcron() {
		return acron;
	}

	/**
	 * @param acron
	 *            the acron to set
	 */
	public void setAcron(String acron) {
		this.acron = acron;
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
	 * @return the deptid
	 */
	public int getDeptid() {
		return deptid;
	}

	/**
	 * @param deptid
	 *            the deptid to set
	 */
	public void setDeptid(int deptid) {
		this.deptid = deptid;
	}

}
