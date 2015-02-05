package ieci.tecdoc.sgm.cripto.validacion.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class ResultadoValidacionHash extends RetornoServicio {

	private String resultadoValidacion;

	public String getResultadoValidacion() {
		return resultadoValidacion;
	}

	public void setResultadoValidacion(String resultadoValidacion) {
		this.resultadoValidacion = resultadoValidacion;
	}
}
