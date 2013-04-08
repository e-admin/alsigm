package ieci.tecdoc.sgm.registro.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

/**
 * Contendor de documentos.
 * 
 * @author IECISA
 *
 */
public class Documentos extends RetornoServicio {

	private DocumentoExtendido[] documentos;

	public DocumentoExtendido[] getDocumentos() {
		return documentos;
	}

	public void setDocumentos(DocumentoExtendido[] documentos) {
		this.documentos = documentos;
	}
}