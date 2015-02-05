package ieci.tecdoc.sgm.tram.ws.server.dto;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class Entero extends RetornoServicio {

	int valor = 0;

	public int getValor() {
		return valor;
	}

	public void setValor(int contenido) {
		this.valor = contenido;
	}

}
