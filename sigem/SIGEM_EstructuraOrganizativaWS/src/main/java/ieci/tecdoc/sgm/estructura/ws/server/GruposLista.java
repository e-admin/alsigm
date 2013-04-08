package ieci.tecdoc.sgm.estructura.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class GruposLista extends RetornoServicio{

	private Grupo[] grupos;

	public Grupo[] getGrupos() {
		return grupos;
	}

	public void setGrupos(Grupo[] grupos) {
		this.grupos = grupos;
	}
	
}
