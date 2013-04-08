package ieci.tecdoc.sgm.antivirus.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class RetornoBooleano extends RetornoServicio {

	private boolean valor;

	public boolean isValor() {
		return valor;
	}

	public void setValor(boolean valor) {
		this.valor = valor;
	}
	
}
