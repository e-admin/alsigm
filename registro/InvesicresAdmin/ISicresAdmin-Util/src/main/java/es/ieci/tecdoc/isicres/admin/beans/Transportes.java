package es.ieci.tecdoc.isicres.admin.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Listado de tipo de transportes
 * 
 * 
 * 
 */
public class Transportes {

	private List transportes;

	public Transportes() {
		transportes = new ArrayList();
	}

	/**
	 * @return
	 */
	public int count() {
		return transportes.size();
	}

	/**
	 * @param index
	 * @return
	 */
	public Transporte get(int index) {
		return (Transporte) transportes.get(index);
	}

	/**
	 * @param transporte
	 */
	public void add(Transporte transporte) {
		transportes.add(transporte);
	}

	/**
	 * @return
	 */
	public List getLista() {
		return transportes;
	}

	/**
	 * @param transportes
	 */
	public void setLista(List transportes) {
		this.transportes = transportes;
	}
}
