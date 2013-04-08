package ieci.tecdoc.sgm.ct.ws.server;
/*
 * $Id: ContenidoDocumento.java,v 1.1.2.1 2008/01/28 11:20:47 jconca Exp $
 */
import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class ContenidoDocumento extends RetornoServicio {

	private String contenidoB64;

	public String getContenidoB64() {
		return contenidoB64;
	}

	public void setContenidoB64(String contenidoB64) {
		this.contenidoB64 = contenidoB64;
	}
}
