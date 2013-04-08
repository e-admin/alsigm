package es.ieci.tecdoc.isicres.admin.estructura.beans;

import java.io.Serializable;

public class PerfilesGenericos implements Serializable{
	
	private PerfilesUsuario perfilesUsuario;
	
	public PerfilesGenericos() {
		perfilesUsuario=new PerfilesUsuario();
	}

	public PerfilesUsuario getPerfilesUsuario() {
		return perfilesUsuario;
	}

	public void setPerfilesUsuario(PerfilesUsuario perfilesUsuario) {
		this.perfilesUsuario = perfilesUsuario;
	}
	
	

}
