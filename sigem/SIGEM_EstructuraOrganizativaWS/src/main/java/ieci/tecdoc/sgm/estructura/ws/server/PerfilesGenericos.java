package ieci.tecdoc.sgm.estructura.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class PerfilesGenericos extends RetornoServicio{
	
	private PerfilesUsuario perfilesUsuario;
	
	public PerfilesGenericos() {
		perfilesUsuario = new PerfilesUsuario();
	}

	public PerfilesUsuario getPerfilesUsuario() {
		return perfilesUsuario;
	}

	public void setPerfilesUsuario(PerfilesUsuario perfilesUsuario) {
		this.perfilesUsuario = perfilesUsuario;
	}
	
	

}
