package ieci.tdw.ispac.services.ws.server.dto;

import ieci.tdw.ispac.services.dto.RetornoServicio;


public class Booleano extends RetornoServicio {

	private static final long serialVersionUID = 1L;
	
	boolean valor = false;

	public boolean getValor() {
		return valor;
	}

	public void setValor(boolean contenido) {
		this.valor = contenido;
	}
	
}
