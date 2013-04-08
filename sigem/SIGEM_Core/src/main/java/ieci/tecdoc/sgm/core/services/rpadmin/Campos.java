package ieci.tecdoc.sgm.core.services.rpadmin;

import java.util.ArrayList;
import java.util.List;

/**
 * Lista de campos de Registro
 * 
 * @see ieci.tecdoc.sgm.core.services.rpadmin.Campo
 * 
 */
public class Campos {

	private List campos;

	public Campos() {
		campos = new ArrayList();
	}

	/**
	 * 
	 * @return
	 */
	public int count() {
		return campos.size();
	}

	/**
	 * 
	 * @param index
	 * @return
	 */
	public Campo get(int index) {
		return (Campo) campos.get(index);
	}

	/**
	 * 
	 * @param campo
	 */
	public void add(Campo campo) {
		campos.add(campo);
	}

	/**
	 * 
	 * @return
	 */
	public List getLista() {
		return campos;
	}

	/**
	 * 
	 * @param campos
	 */
	public void setLista(List campos) {
		this.campos = campos;
	}
}
