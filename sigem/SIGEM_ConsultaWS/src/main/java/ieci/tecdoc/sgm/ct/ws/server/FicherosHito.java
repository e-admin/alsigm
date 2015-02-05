package ieci.tecdoc.sgm.ct.ws.server;
/*
 * $Id: FicherosHito.java,v 1.1.2.1 2008/01/28 11:20:47 jconca Exp $
 */
import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class FicherosHito extends RetornoServicio {

	private String guid;
	
	private FicheroHito[] ficherosHito;

	public FicheroHito[] getFicherosHito() {
		return ficherosHito;
	}

	public void setFicherosHito(FicheroHito[] ficherosHitos) {
		this.ficherosHito = ficherosHitos;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}
	
	
}
