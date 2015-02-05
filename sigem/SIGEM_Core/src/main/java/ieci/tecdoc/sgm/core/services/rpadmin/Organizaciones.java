package ieci.tecdoc.sgm.core.services.rpadmin;

import java.util.ArrayList;
import java.util.List;

/**
 * Listado de organizaciones
 * 
 * @see ieci.tecdoc.sgm.core.services.rpadmin.Organizacion
 */
public class Organizaciones {

	private List organizaciones;

	public Organizaciones() {
		organizaciones = new ArrayList();
	}

	/**
	 * @return
	 */
	public int count() {
		return organizaciones.size();
	}

	/**
	 * @param i
	 * @return
	 */
	public Organizacion get(int i) {
		return (Organizacion) organizaciones.get(i);
	}

	/**
	 * @param permiso
	 */
	public void add(Organizacion permiso) {
		organizaciones.add(permiso);
	}

	/**
	 * @return
	 */
	public List getLista() {
		return organizaciones;
	}

	/**
	 * @param oficina
	 */
	public void setLista(List oficina) {
		this.organizaciones = oficina;
	}
}
