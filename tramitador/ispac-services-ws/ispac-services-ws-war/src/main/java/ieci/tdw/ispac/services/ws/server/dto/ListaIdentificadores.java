package ieci.tdw.ispac.services.ws.server.dto;

import ieci.tdw.ispac.services.dto.RetornoServicio;

public class ListaIdentificadores extends RetornoServicio {

	private static final long serialVersionUID = 1L;
	
	String[] identificadores = null;

	public String[] getIdentificadores() {
		return identificadores;
	}

	public void setIdentificadores(String[] identificadores) {
		this.identificadores = identificadores;
	}
	
}
