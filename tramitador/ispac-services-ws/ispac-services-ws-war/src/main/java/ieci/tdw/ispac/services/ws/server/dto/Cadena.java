package ieci.tdw.ispac.services.ws.server.dto;

import ieci.tdw.ispac.services.dto.RetornoServicio;

public class Cadena extends RetornoServicio {

	private static final long serialVersionUID = 1L;
	
	String valor = "";

	public String getValor() {
		return valor;
	}

	public void setValor(String contenido) {
		this.valor = contenido;
	}

}
