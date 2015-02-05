package es.ieci.tecdoc.isicres.admin.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Listado de permisos de un usuario u oficina sobre un libro
 * 
 * @see ieci.tecdoc.sgm.core.services.rpadmin.PermisoSicres
 * 
 */
public class PermisosSicres {

	private List permisos;

	public PermisosSicres() {
		permisos = new ArrayList();
	}

	/**
	 * @return
	 */
	public int count() {
		return permisos.size();
	}

	/**
	 * @param index
	 * @return
	 */
	public PermisoSicres getPermisos(int index) {
		if (index >= permisos.size()) {
			for (int i = permisos.size(); i <= index; i++) {
				permisos.add(new PermisoSicres());
			}
		}
		return (PermisoSicres) permisos.get(index);
	}

	/**
	 * @param permiso
	 */
	public void add(PermisoSicres permiso) {
		permisos.add(permiso);
	}

	/**
	 * @param permiso
	 */
	public void remove(PermisoSicres permiso) {
		permisos.remove(permiso);
	}

	/**
	 * @return
	 */
	public List getLista() {
		return permisos;
	}

	/**
	 * @param oficina
	 */
	public void setLista(List oficina) {
		this.permisos = oficina;
	}
}
