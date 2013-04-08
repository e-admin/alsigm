package es.ieci.tecdoc.isicres.admin.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Lista de Libros de registro
 * 
 * @see ieci.tecdoc.sgm.core.services.rpadmin.Libro
 */
public class Libros {

	private List libros;

	public Libros() {
		libros = new ArrayList();
	}

	public int count() {
		return libros.size();
	}

	public Libro get(int index) {
		return (Libro) libros.get(index);
	}

	public void add(Libro libro) {
		libros.add(libro);
	}

	public List getLista() {
		return libros;
	}

	public void setLista(List oficina) {
		this.libros = oficina;
	}
}
