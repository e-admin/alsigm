package ieci.tecdoc.sgm.core.services.rpadmin;

import java.util.ArrayList;
import java.util.List;

/**
 * Listado de oficinas registrales
 * 
 * @see ieci.tecdoc.sgm.core.services.rpadmin.Oficina
 * 
 */
public class Oficinas {

	private List oficinas;

	public Oficinas() {
		oficinas = new ArrayList();
	}

	/**
	 * @return
	 */
	public int count() {
		return oficinas.size();
	}

	/**
	 * @param index
	 * @return
	 */
	public Oficina get(int index) {
		return (Oficina) oficinas.get(index);
	}

	/**
	 * @param oficina
	 */
	public void add(Oficina oficina) {
		oficinas.add(oficina);
	}

	/**
	 * @return
	 */
	public List getLista() {
		return oficinas;
	}

	/**
	 * @param oficina
	 */
	public void setLista(List oficina) {
		this.oficinas = oficina;
	}

	/**
	 * @return
	 */
	public int getSize() {
		return this.count();
	}
}
