package es.ieci.tecdoc.isicres.admin.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Lista de filtros de tratamiento de libros
 * 
 * @see ieci.tecdoc.sgm.core.services.rpadmin.Filtro
 */
public class Filtros {

	private List filtros;

	public Filtros() {
		filtros = new ArrayList();
	}

	/**
	 * @return
	 */
	public int count() {
		return filtros.size();
	}

	/**
	 * @param index
	 * @return
	 */
	public Filtro get(int index) {
		return (Filtro) filtros.get(index);
	}

	/**
	 * @param filtro
	 */
	public void add(Filtro filtro) {
		filtros.add(filtro);
	}

	/**
	 * @return
	 */
	public List getLista() {
		return filtros;
	}

	/**
	 * @param filtros
	 */
	public void setLista(List filtros) {
		this.filtros = filtros;
	}
}
