package ieci.tecdoc.sgm.core.services.rpadmin;

import java.util.ArrayList;
import java.util.List;

/**
 * Listado de Oficinas asociadas con tipos de asuntos
 * 
 * @see ieci.tecdoc.sgm.core.services.rpadmin.OficinaTipoAsuntoBean
 * 
 */
public class OficinasTipoAsuntoBean {
	private List oficinasTipoAsunto;

	public OficinasTipoAsuntoBean() {
		oficinasTipoAsunto = new ArrayList();
	}

	/**
	 * @return
	 */
	public int count() {
		return oficinasTipoAsunto.size();
	}

	/**
	 * @param index
	 * @return
	 */
	public OficinaTipoAsuntoBean get(int index) {
		return (OficinaTipoAsuntoBean) oficinasTipoAsunto.get(index);
	}

	/**
	 * @param oficina
	 */
	public void add(OficinaTipoAsuntoBean oficina) {
		oficinasTipoAsunto.add(oficina);
	}

	/**
	 * @return
	 */
	public List getLista() {
		return oficinasTipoAsunto;
	}

	/**
	 * @param oficinasTiposAsunto
	 */
	public void setLista(List oficinasTiposAsunto) {
		this.oficinasTipoAsunto = oficinasTiposAsunto;
	}
}
