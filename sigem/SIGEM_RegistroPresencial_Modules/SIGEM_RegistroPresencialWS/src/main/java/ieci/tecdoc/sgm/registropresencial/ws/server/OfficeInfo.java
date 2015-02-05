package ieci.tecdoc.sgm.registropresencial.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

/**
 * Almacena la información de una oficina
 *
 */
public class OfficeInfo extends RetornoServicio {

	/**
	 * Identiticador de la oficina
	 */
	protected String id;

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
	protected String deptid;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
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
	public String getDeptid() {
		return deptid;
	}

	/**
	 * @param deptid
	 *            the deptid to set
	 */
	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}

}
