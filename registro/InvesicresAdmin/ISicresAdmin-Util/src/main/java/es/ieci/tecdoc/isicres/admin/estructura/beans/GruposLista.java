package es.ieci.tecdoc.isicres.admin.estructura.beans;

import java.util.ArrayList;

public class GruposLista {

	private ArrayList listaGrupos;
	
	public GruposLista() {
		listaGrupos=new ArrayList();
	}

	public int count(){
		return listaGrupos.size();
	}
	
	public void add(Grupo grupo){
		listaGrupos.add(grupo);
	}
	
	public Grupo get(int index){
		return (Grupo)listaGrupos.get(index);
	}
	
}
