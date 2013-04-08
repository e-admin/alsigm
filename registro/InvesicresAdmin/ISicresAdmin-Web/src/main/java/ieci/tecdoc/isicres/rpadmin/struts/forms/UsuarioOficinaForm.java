package ieci.tecdoc.isicres.rpadmin.struts.forms;

import org.apache.struts.validator.ValidatorActionForm;

public class UsuarioOficinaForm extends ValidatorActionForm {
	
	private static final long serialVersionUID = 1L;
	private String idOfic;	
	private boolean usuarios;
	private boolean agregados;
	private boolean usuariosDisponibles;
	
	/**
	 * @return the idOfic
	 */
	public String getIdOfic() {
		return idOfic;
	}
	
	/**
	 * @param idOfic the idOfic to set
	 */
	public void setIdOfic(String idOfic) {
		this.idOfic = idOfic;
	}
	
	/**
	 * @return the usuarios
	 */
	public boolean isUsuarios() {
		return usuarios;
	}
	
	/**
	 * @param usuarios the usuarios to set
	 */
	public void setUsuarios(boolean usuarios) {
		this.usuarios = usuarios;
	}
	
	/**
	 * @return the agregados
	 */
	public boolean isAgregados() {
		return agregados;
	}
	
	/**
	 * @param agregados the agregados to set
	 */
	public void setAgregados(boolean agregados) {
		this.agregados = agregados;
	}

	public boolean isUsuariosDisponibles() {
		return usuariosDisponibles;
	}

	public void setUsuariosDisponibles(boolean usuariosDisponibles) {
		this.usuariosDisponibles = usuariosDisponibles;
	}
}