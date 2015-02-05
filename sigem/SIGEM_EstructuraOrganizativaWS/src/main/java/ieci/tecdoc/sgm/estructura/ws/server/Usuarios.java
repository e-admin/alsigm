package ieci.tecdoc.sgm.estructura.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class Usuarios extends RetornoServicio{
	
	private Usuario[] usuarios;
	
	public Usuario[] getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Usuario[] usuarios) {
		this.usuarios = usuarios;
	}
		
}
