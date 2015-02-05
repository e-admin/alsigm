package ieci.tecdoc.sgm.tram.ws.server.dto;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class ListaInfoBExpedientes extends RetornoServicio {

	InfoBExpediente[] expedientes = null;

	public InfoBExpediente[] getExpedientes() {
		return expedientes;
	}

	public void setExpedientes(InfoBExpediente[] expedientes) {
		this.expedientes = expedientes;
	}
	
}
