package ieci.tdw.ispac.services.ws.server.dto;

import ieci.tdw.ispac.services.dto.RetornoServicio;

public class Entero extends RetornoServicio {

	private static final long serialVersionUID = 1L;
	
	int valor = 0;

	public int getValor() {
		return valor;
	}

	public void setValor(int contenido) {
		this.valor = contenido;
	}

}
