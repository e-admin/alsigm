package ieci.tdw.ispac.services.ws.server.dto;

import ieci.tdw.ispac.services.dto.RetornoServicio;

public class ListaInfoBExpedientes extends RetornoServicio {

	private static final long serialVersionUID = 1L;
	
	InfoBExpediente[] expedientes = null;

	public InfoBExpediente[] getExpedientes() {
		return expedientes;
	}

	public void setExpedientes(InfoBExpediente[] expedientes) {
		this.expedientes = expedientes;
	}
	
}
