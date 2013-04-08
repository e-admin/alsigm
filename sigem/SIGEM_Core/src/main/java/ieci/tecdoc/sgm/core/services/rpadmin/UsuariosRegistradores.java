package ieci.tecdoc.sgm.core.services.rpadmin;

import java.util.ArrayList;
import java.util.List;

/**
 * Listado de VO de usuario
 * 
 * @see ieci.tecdoc.sgm.core.services.rpadmin.UsuarioRegistrador
 * 
 */
public class UsuariosRegistradores {

	private List usuarios;

	public UsuariosRegistradores() {
		usuarios = new ArrayList();
	}

	/**
	 * @return
	 */
	public int count() {
		return usuarios.size();
	}

	/**
	 * @param index
	 * @return
	 */
	public UsuarioRegistrador get(int index) {
		return (UsuarioRegistrador) usuarios.get(index);
	}

	/**
	 * @param usuario
	 */
	public void add(UsuarioRegistrador usuario) {
		usuarios.add(usuario);
	}

	/**
	 * @return
	 */
	public List getLista() {
		return usuarios;
	}

	/**
	 * @param usuarios
	 */
	public void setLista(List usuarios) {
		this.usuarios = usuarios;
	}
}
