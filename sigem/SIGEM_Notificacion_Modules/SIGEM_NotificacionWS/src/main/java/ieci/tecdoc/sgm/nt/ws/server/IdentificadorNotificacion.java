package ieci.tecdoc.sgm.nt.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class IdentificadorNotificacion extends RetornoServicio {

	private String codigoDeNotificacion;

	public String getCodigoDeNotificacion() {
		return codigoDeNotificacion;
	}

	public void setCodigoDeNotificacion(String codigoDeNotificacion) {
		this.codigoDeNotificacion = codigoDeNotificacion;
	}
	
	
}
