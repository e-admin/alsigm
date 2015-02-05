package ieci.tecdoc.sgm.rpadmin.beans;

import java.util.ArrayList;
import java.util.List;

public class ColeccionGeneralImpl {

	protected List lista;
	
	public ColeccionGeneralImpl(){
		lista = new ArrayList();
	}
	
	public int count() {
		return lista.size();
	}

	public List getLista() {
		return lista;
	}

	public void setLista(List oficina) {
		this.lista = oficina;
	}
}
