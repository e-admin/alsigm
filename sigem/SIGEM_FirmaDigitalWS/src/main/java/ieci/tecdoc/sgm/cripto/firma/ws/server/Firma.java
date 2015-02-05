package ieci.tecdoc.sgm.cripto.firma.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class Firma extends RetornoServicio {

	private String firmaB64;

	public String getFirmaB64() {
		return firmaB64;
	}

	public void setFirmaB64(String firmaB64) {
		this.firmaB64 = firmaB64;
	}
	
	
}
