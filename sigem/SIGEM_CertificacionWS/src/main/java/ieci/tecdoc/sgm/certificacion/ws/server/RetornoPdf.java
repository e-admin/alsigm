package ieci.tecdoc.sgm.certificacion.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class RetornoPdf extends RetornoServicio {
	
	private byte[] contenido;
	private String identificador;
	
	public byte[] getContenido() {
		return contenido;
	}
	
	public void setContenido(byte[] contenido) {
		this.contenido = contenido;
	}
	
	public String getIdentificador() {
		return identificador;
	}
	
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
}
