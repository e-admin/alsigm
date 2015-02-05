package ieci.tdw.ispac.services.ws.server.dto;

import ieci.tdw.ispac.services.dto.RetornoServicio;


public class Binario extends RetornoServicio {

	private static final long serialVersionUID = 1L;
	
	byte[] contenido = null;

	public byte[] getContenido() {
		return contenido;
	}

	public void setContenido(byte[] contenido) {
		this.contenido = contenido;
	}
	
}
