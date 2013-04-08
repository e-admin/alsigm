package ieci.tecdoc.sgm.tram.ws.server.dto;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class ListaInfoBProcedimientos extends RetornoServicio {

	InfoBProcedimiento[] procedimientos = null;

	public InfoBProcedimiento[] getProcedimientos() {
		return procedimientos;
	}

	public void setProcedimientos(InfoBProcedimiento[] procedimientos) {
		this.procedimientos = procedimientos;
	}
	
}
