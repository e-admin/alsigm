package ieci.tecdoc.sgm.estructura.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class Permisos  extends RetornoServicio{

	private Permiso[] permisos;
	
	public Permiso[] getPermisos() {
		return permisos;
	}

	public void setPermisos(Permiso[] permisos) {
		this.permisos = permisos;
	}
	
}
