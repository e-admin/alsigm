package ieci.tecdoc.sgm.mensajesCortos.ws.server.dto;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;


public class ListaBytes extends RetornoServicio{

	byte[]bytes = null;

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
	
}
