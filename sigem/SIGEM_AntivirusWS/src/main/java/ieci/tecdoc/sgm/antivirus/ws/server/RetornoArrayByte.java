package ieci.tecdoc.sgm.antivirus.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class RetornoArrayByte extends RetornoServicio {

	private String contenido;

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}
	
}
