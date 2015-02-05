package ieci.tecdoc.sgm.admsistema.form;

import org.apache.struts.action.ActionForm;

public class UsuarioForm extends ActionForm {

	private String administradorEntidades;
	private String administradorPermisosEntidades;
	private String administradorUsername;
	private String administradorNombre;
	private String administradorApellidos;
	private String administradorPassword;
	private String administradorPasswordNuevo;
	private String administradorPasswordRepetido;
	
	public String getAdministradorApellidos() {
		return administradorApellidos;
	}

	public void setAdministradorApellidos(String administradorApellidos) {
		this.administradorApellidos = administradorApellidos;
	}

	public String getAdministradorNombre() {
		return administradorNombre;
	}

	public void setAdministradorNombre(String administradorNombre) {
		this.administradorNombre = administradorNombre;
	}

	public String getAdministradorPassword() {
		return administradorPassword;
	}

	public void setAdministradorPassword(String administradorPassword) {
		this.administradorPassword = administradorPassword;
	}

	public String getAdministradorPasswordNuevo() {
		return administradorPasswordNuevo;
	}

	public void setAdministradorPasswordNuevo(String administradorPasswordNuevo) {
		this.administradorPasswordNuevo = administradorPasswordNuevo;
	}

	public String getAdministradorPasswordRepetido() {
		return administradorPasswordRepetido;
	}

	public void setAdministradorPasswordRepetido(
			String administradorPasswordRepetido) {
		this.administradorPasswordRepetido = administradorPasswordRepetido;
	}

	public String getAdministradorUsername() {
		return administradorUsername;
	}

	public void setAdministradorUsername(String administradorUsername) {
		this.administradorUsername = administradorUsername;
	}

	public String getAdministradorEntidades() {
		return administradorEntidades;
	}

	public void setAdministradorEntidades(String administradorEntidades) {
		this.administradorEntidades = administradorEntidades;
	}

	public String getAdministradorPermisosEntidades() {
		return administradorPermisosEntidades;
	}

	public void setAdministradorPermisosEntidades(String administradorPermisosEntidades) {
		this.administradorPermisosEntidades = administradorPermisosEntidades;
	}
	
	private final static long serialVersionUID = 0;
}