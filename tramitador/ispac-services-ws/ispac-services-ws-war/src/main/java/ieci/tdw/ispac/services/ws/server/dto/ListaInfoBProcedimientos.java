package ieci.tdw.ispac.services.ws.server.dto;

import ieci.tdw.ispac.services.dto.RetornoServicio;

public class ListaInfoBProcedimientos extends RetornoServicio {

	private static final long serialVersionUID = 1L;
	
	InfoBProcedimiento[] procedimientos = null;

	public InfoBProcedimiento[] getProcedimientos() {
		return procedimientos;
	}

	public void setProcedimientos(InfoBProcedimiento[] procedimientos) {
		this.procedimientos = procedimientos;
	}
	
}
