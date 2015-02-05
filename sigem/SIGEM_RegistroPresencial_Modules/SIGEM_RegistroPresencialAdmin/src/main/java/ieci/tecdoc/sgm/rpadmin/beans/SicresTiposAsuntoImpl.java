package ieci.tecdoc.sgm.rpadmin.beans;

import ieci.tecdoc.sgm.core.services.rpadmin.TipoAsunto;

import java.util.ArrayList;
import java.util.List;

public class SicresTiposAsuntoImpl {
	/**
	 * Lista de Objetos de tipo {@link TipoAsunto}
	 */
	private List tiposAsuntos;
	
	public SicresTiposAsuntoImpl(){
		tiposAsuntos = new ArrayList();
	}
	
	public int count() {
		return tiposAsuntos.size();
	}

	public TipoAsunto get(int index) {
		return (TipoAsunto)tiposAsuntos.get(index);
	}

	public void add(TipoAsunto tipoAsunto) {
		tiposAsuntos.add(tipoAsunto);
	}

	public List getLista() {
		return tiposAsuntos;
	}

	public void setLista(List tiposAsuntos) {
		this.tiposAsuntos = tiposAsuntos;
	}
}
