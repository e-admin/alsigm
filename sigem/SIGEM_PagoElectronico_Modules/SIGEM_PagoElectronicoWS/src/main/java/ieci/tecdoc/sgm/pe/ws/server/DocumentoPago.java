package ieci.tecdoc.sgm.pe.ws.server;
/*
 * $Id: DocumentoPago.java,v 1.1.4.1 2008/01/25 12:30:46 jconca Exp $
 */
import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class DocumentoPago extends RetornoServicio {

	private String documentoPago;

	public String getDocumentoPago() {
		return documentoPago;
	}

	public void setDocumentoPago(String documentoPago) {
		this.documentoPago = documentoPago;
	}
	
	
}
