package ieci.tecdoc.sgm.estructura.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;


public class Listas extends RetornoServicio{
	
	private Lista[] listas;

	public Lista[] getListas() {
		return listas;
	}

	public void setListas(Lista[] listas) {
		this.listas = listas;
	}
	
}
