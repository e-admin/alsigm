package ieci.tecdoc.sgm.core.services.rpadmin;

import java.util.ArrayList;
import java.util.List;

/**
 * Agrupación de lista de distribuciones
 * 
 * @see ieci.tecdoc.sgm.core.services.rpadmin.Distribucion
 * 
 */
public class Distribuciones {

	private List distribuciones;

	public Distribuciones() {
		distribuciones = new ArrayList();
	}

	/**
	 * @return
	 */
	public int count() {
		return distribuciones.size();
	}

	/**
	 * @param index
	 * @return
	 */
	public Distribucion getDistribuciones(int index) {
		if (index >= distribuciones.size()) {
			distribuciones.add(index, new Distribuciones());
		}
		return (Distribucion) distribuciones.get(index);
	}

	/**
	 * @param index
	 * @param distribucion
	 */
	public void setDistribuciones(int index, Distribucion distribucion) {
		if (index < distribuciones.size()) {
			distribuciones.set(index, distribucion);
		} else {
			distribuciones.add(index, distribucion);
		}
	}

	/**
	 * @param distribucion
	 */
	public void add(Distribucion distribucion) {
		distribuciones.add(distribucion);
	}

	/**
	 * @return
	 */
	public List getLista() {
		return distribuciones;
	}

	/**
	 * @param distribucion
	 */
	public void setLista(List distribucion) {
		this.distribuciones = distribucion;
	}
}
