package ieci.tecdoc.sgm.estructura.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;


public class PerfilesUsuario extends RetornoServicio{
	
	private PerfilUsuario[] perfilesUsuario;
	
	public PerfilUsuario[] getPerfilesUsuario() {
		return perfilesUsuario;
	}

	public void setPerfilesUsuario(PerfilUsuario[] perfilesUsuario) {
		this.perfilesUsuario = perfilesUsuario;
	}

}
