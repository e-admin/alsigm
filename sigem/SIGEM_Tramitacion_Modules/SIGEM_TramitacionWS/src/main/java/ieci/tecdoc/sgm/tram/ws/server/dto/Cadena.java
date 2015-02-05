package ieci.tecdoc.sgm.tram.ws.server.dto;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class Cadena extends RetornoServicio {

	String valor = "";

	public String getValor() {
		return valor;
	}

	public void setValor(String contenido) {
		this.valor = contenido;
	}

}
