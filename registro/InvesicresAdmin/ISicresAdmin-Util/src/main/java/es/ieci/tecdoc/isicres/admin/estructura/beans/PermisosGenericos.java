package es.ieci.tecdoc.isicres.admin.estructura.beans;

import java.io.Serializable;

public class PermisosGenericos implements Serializable{
	private Permisos permisos;
	
	public PermisosGenericos() {
		permisos=new Permisos();
	}
	
	public Permisos getPermisos() {
		return permisos;
	}
	public void setPermisos(Permisos permisos) {
		this.permisos = permisos;
	}
	
}
