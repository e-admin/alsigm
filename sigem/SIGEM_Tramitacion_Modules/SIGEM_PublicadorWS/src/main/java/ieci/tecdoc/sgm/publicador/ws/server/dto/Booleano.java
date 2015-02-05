package ieci.tecdoc.sgm.publicador.ws.server.dto;

import java.io.Serializable;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class Booleano extends RetornoServicio   {

	boolean valor = false;

	public boolean getValor() {
		return valor;
	}

	public void setValor(boolean contenido) {
		this.valor = contenido;
	}
	
}
