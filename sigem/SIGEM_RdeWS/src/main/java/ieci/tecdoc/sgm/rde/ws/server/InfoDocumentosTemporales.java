package ieci.tecdoc.sgm.rde.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class InfoDocumentosTemporales extends RetornoServicio{
	
	private InfoDocumentoTemporal[] documentos;

	public InfoDocumentoTemporal[] getDocumentos() {
		return documentos;
	}

	public void setDocumentos(InfoDocumentoTemporal[] documentos) {
		this.documentos = documentos;
	}
}
