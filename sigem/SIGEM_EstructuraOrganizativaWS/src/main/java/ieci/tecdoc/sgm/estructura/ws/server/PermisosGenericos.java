package ieci.tecdoc.sgm.estructura.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class PermisosGenericos extends RetornoServicio{
	
	private Permisos permisos;
	
	public PermisosGenericos() {
		permisos = new Permisos();
	}
	
	public Permisos getPermisos() {
		return permisos;
	}
	public void setPermisos(Permisos permisos) {
		this.permisos = permisos;
	}
	
}
