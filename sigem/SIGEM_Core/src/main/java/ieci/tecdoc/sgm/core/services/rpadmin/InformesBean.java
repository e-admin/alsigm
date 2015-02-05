package ieci.tecdoc.sgm.core.services.rpadmin;

import java.util.ArrayList;
import java.util.List;

/**
 * Listado de Informes
 * @author Pablo Zapico
 *
 */
public class InformesBean {
	private List informes;

	public InformesBean() {
		informes = new ArrayList();
	}

	/**
	 * @return
	 */
	public int count() {
		return informes.size();
	}

	/**
	 * @param index
	 * @return
	 */
	public Oficina get(int index) {
		return (Oficina) informes.get(index);
	}

	/**
	 * @param oficina
	 */
	public void add(Informe informe) {
		informes.add(informe);
	}

	/**
	 * @return
	 */
	public List getLista() {
		return informes;
	}

	/**
	 * @param oficina
	 */
	public void setLista(List informes) {
		this.informes = informes;
	}

	/**
	 * @return
	 */
	public int getSize() {
		return this.count();
	}
}
