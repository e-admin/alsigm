package ieci.tecdoc.isicres.rpadmin.struts.forms;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;

public class EntidadRegistralForm extends RPAdminBaseForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	protected String id;
	protected String name;
	protected String code;
	protected String idOrgs;
	protected String codOrgs;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getIdOrgs() {
		return idOrgs;
	}

	public void setIdOrgs(String idOrgs) {
		this.idOrgs = idOrgs;
	}

	public String[] getAttrsToUpper() {
		// TODO Auto-generated method stub
		return null;
	}

	public void toUpperCase(HttpServletRequest request)
			throws IllegalAccessException, InvocationTargetException {
		// TODO Auto-generated method stub

	}

	public String getCodOrgs() {
		return codOrgs;
	}

	public void setCodOrgs(String codOrgs) {
		this.codOrgs = codOrgs;
	}

}
