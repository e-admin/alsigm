/**
 * 
 */
package ieci.tecdoc.sgm.core.services.registro;

/**
 * Información de una oficina
 * 
 * @author 66575267
 * 
 */
public class OfficeInfo {

	/**
	 * Identiticador de la oficina
	 */
	protected Integer id;

	/**
	 * Código de la oficina
	 */
	protected String code;

	/**
	 * Acronimo de la oficina
	 */
	protected String acron;

	/**
	 * Nombre de la oficina
	 */
	protected String name;

	/**
	 * Identificador del departamento
	 */
	protected int deptid;

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
	 * @return the code
	 */
	/**
	 * @return
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	/**
	 * @param code
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
	/**
	 * @param acron
	 */
	public void setAcron(String acron) {
		this.acron = acron;
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
	 * @return the deptid
	 */
	/**
	 * @return
	 */
	public int getDeptid() {
		return deptid;
	}

	/**
	 * @param deptid
	 *            the deptid to set
	 */
	/**
	 * @param deptid
	 */
	public void setDeptid(int deptid) {
		this.deptid = deptid;
	}

}
