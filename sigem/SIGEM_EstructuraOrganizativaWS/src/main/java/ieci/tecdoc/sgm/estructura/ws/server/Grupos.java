package ieci.tecdoc.sgm.estructura.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class Grupos extends RetornoServicio{

	private GruposLista gruposLista;
	
	public Grupos() {
		gruposLista=new GruposLista();
	}

	public GruposLista getGruposLista() {
		return gruposLista;
	}

	public void setGruposLista(GruposLista gruposLista) {
		this.gruposLista = gruposLista;
	}
	
	
	
}
