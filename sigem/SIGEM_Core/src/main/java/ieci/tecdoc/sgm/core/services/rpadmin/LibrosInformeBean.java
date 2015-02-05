package ieci.tecdoc.sgm.core.services.rpadmin;

import java.util.ArrayList;
import java.util.List;

public class LibrosInformeBean {
	private List librosInforme;

	public LibrosInformeBean() {
		librosInforme = new ArrayList();
	}

	/**
	 * @return
	 */
	public int count() {
		return librosInforme.size();
	}

	/**
	 * @param index
	 * @return
	 */
	public LibrosInformeBean get(int index) {
		return (LibrosInformeBean) librosInforme.get(index);
	}

	/**
	 * @param libro
	 */
	public void add(LibroInformeBean libro) {
		librosInforme.add(libro);
	}

	/**
	 * @return
	 */
	public List getLista() {
		return librosInforme;
	}

	/**
	 * @param perfiles informe
	 */
	public void setLista(List librosInforme) {
		this.librosInforme = librosInforme;
	}

}
