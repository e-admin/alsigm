package ieci.tecdoc.sgm.registro.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class Registros extends RetornoServicio {

	private Registro[] registros;

	public Registro[] getRegistros() {
		return registros;
	}

	public void setRegistros(Registro[] registros) {
		this.registros = registros;
	}
}