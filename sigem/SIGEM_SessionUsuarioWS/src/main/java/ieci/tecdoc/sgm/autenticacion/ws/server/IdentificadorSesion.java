package ieci.tecdoc.sgm.autenticacion.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class IdentificadorSesion extends RetornoServicio {

	private String sessionId;

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
}
