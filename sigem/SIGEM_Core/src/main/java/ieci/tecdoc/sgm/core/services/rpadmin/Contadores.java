package ieci.tecdoc.sgm.core.services.rpadmin;

import java.util.ArrayList;
import java.util.List;

/**
 * Lista de contadores de registro
 * 
 * @see ieci.tecdoc.sgm.core.services.rpadmin.Contador
 */
public class Contadores {

	private List contadores;

	public Contadores() {
		contadores = new ArrayList();
	}

	/**
	 * 
	 * @return
	 */
	public int count() {
		return contadores.size();
	}

	/**
	 * 
	 * @param index
	 * @return
	 */
	public Contador get(int index) {
		return (Contador) contadores.get(index);
	}

	/**
	 * @param contador
	 */
	public void add(Contador contador) {
		contadores.add(contador);
	}

	/**
	 * @return
	 */
	public List getLista() {
		return contadores;
	}

	/**
	 * @param contadores
	 */
	public void setLista(List contadores) {
		this.contadores = contadores;
	}
}
