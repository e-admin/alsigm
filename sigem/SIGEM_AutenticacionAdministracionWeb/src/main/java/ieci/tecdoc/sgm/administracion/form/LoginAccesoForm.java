package ieci.tecdoc.sgm.administracion.form;

import org.apache.struts.action.ActionForm;

public class LoginAccesoForm extends ActionForm{
	private String username;
	private String password;
	private String interno;
	private String idEntidadInterno;
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public String getIdEntidadInterno() {
		return idEntidadInterno;
	}

	public void setIdEntidadInterno(String idEntidadInterno) {
		this.idEntidadInterno = idEntidadInterno;
	}

	public String getInterno() {
		return interno;
	}

	public void setInterno(String interno) {
		this.interno = interno;
	}

	private final static long serialVersionUID = 0;
}
