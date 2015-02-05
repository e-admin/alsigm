package ieci.tecdoc.sgm.ct.ws.server;
/*
 * $Id: Expedientes.java,v 1.1.2.1 2008/01/28 11:20:47 jconca Exp $
 */
import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class Expedientes extends RetornoServicio {

	private Expediente[] expedientes;

	public Expediente[] getExpedientes() {
		return expedientes;
	}

	public void setExpedientes(Expediente[] expedientes) {
		this.expedientes = expedientes;
	}
}
