package ieci.tecdoc.sgm.entidades.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class CriterioBusquedaEntidades extends RetornoServicio{

	private String nombreCorto;
	private String nombreLargo;
	
	public String getNombreCorto() {
		return nombreCorto;
	}
	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
	}
	public String getNombreLargo() {
		return nombreLargo;
	}
	public void setNombreLargo(String nombreLargo) {
		this.nombreLargo = nombreLargo;
	}
}
