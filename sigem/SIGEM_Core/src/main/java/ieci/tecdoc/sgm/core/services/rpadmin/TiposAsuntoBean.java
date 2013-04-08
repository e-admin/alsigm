package ieci.tecdoc.sgm.core.services.rpadmin;

import java.util.ArrayList;
import java.util.List;

/**
 * Listado de la extension de tipo de asunto
 * 
 * @see ieci.tecdoc.sgm.core.services.rpadmin.TipoAsuntoBean
 * 
 */
public class TiposAsuntoBean {

	private List tiposAsunto;

	public TiposAsuntoBean() {
		tiposAsunto = new ArrayList();
	}

	/**
	 * @return
	 */
	public int count() {
		return tiposAsunto.size();
	}

	/**
	 * @param index
	 * @return
	 */
	public TipoAsuntoBean get(int index) {
		return (TipoAsuntoBean) tiposAsunto.get(index);
	}

	/**
	 * @param tipoAsunto
	 */
	public void add(TipoAsunto tipoAsunto) {
		tiposAsunto.add(tipoAsunto);
	}

	/**
	 * @return
	 */
	public List getLista() {
		return tiposAsunto;
	}

	/**
	 * @param tiposAsunto
	 */
	public void setLista(List tiposAsunto) {
		this.tiposAsunto = tiposAsunto;
	}
}