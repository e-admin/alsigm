package ieci.tecdoc.sgm.ct.ws.server;
/*
 * $Id: FicherosHitos.java,v 1.1.2.1 2008/01/28 11:20:47 jconca Exp $
 */
import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class FicherosHitos extends RetornoServicio {

	private FicherosHito[] ficherosHitos;

	public FicherosHito[] getFicherosHitos() {
		return ficherosHitos;
	}

	public void setFicherosHitos(FicherosHito[] ficherosHitos) {
		this.ficherosHitos = ficherosHitos;
	}
}
