package ieci.tecdoc.sgm.mensajesCortos.ws.server.dto;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;


public class Entero extends RetornoServicio   {

	int valor ;

	public int getValor() {
		return valor;
	}

	public void setValor(int contenido) {
		this.valor = contenido;
	}
	
}
