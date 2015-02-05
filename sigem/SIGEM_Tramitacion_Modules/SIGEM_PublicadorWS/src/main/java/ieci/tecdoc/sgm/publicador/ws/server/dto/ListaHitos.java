package ieci.tecdoc.sgm.publicador.ws.server.dto;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class ListaHitos extends RetornoServicio  {

	Hito[] hitos = null;

	public Hito[] getHitos() {
		return hitos;
	}

	public void setHitos(Hito[] hitos) {
		this.hitos = hitos;
	}
	
}
