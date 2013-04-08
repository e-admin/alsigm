package ieci.tecdoc.sgm.registropresencial.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

/**
 * Almacena una lista de documentos
 * 
 * 
 */
public class Documents extends RetornoServicio {

	/**
	 * Lista de documentos
	 */
	private Document[] documents;

	/**
	 * @return
	 */
	public Document[] getDocuments() {
		return documents;
	}

	/**
	 * @param documents
	 */
	public void setDocuments(Document[] documents) {
		this.documents = documents;
	}

}
