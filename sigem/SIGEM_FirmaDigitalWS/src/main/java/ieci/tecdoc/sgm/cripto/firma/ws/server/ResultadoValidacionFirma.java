package ieci.tecdoc.sgm.cripto.firma.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class ResultadoValidacionFirma extends RetornoServicio {

	private String resultadoValidacion;
	private Firmante[] firmantes;
	
	public Firmante[] getFirmantes() {
		return firmantes;
	}
	public void setFirmantes(Firmante[] firmantes) {
		this.firmantes = firmantes;
	}
	public String getResultadoValidacion() {
		return resultadoValidacion;
	}
	public void setResultadoValidacion(String resultadoValidacion) {
		this.resultadoValidacion = resultadoValidacion;
	}
	
	

}
