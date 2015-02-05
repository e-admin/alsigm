package ieci.tecdoc.sgm.tram.ws.server.dto;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class ListaIdentificadores extends RetornoServicio {

	String[] identificadores = null;

	public String[] getIdentificadores() {
		return identificadores;
	}

	public void setIdentificadores(String[] identificadores) {
		this.identificadores = identificadores;
	}
	
}
