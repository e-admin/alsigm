package ieci.tecdoc.sgm.usuario.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class ListaUsuarios extends RetornoServicio {

	private Usuario[] users;

	
	public Usuario[] getUsers() {
		return users;
	}

	public void setUsers(Usuario[] users) {
		this.users = users;
	}
	
	
}
