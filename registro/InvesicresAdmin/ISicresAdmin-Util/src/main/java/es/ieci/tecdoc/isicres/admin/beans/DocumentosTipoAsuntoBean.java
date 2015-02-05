package es.ieci.tecdoc.isicres.admin.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Lista de Documentos de Tipo de Asunto
 * 
 * @see ieci.tecdoc.sgm.core.services.rpadmin.DocumentoTipoAsuntoBean
 */
public class DocumentosTipoAsuntoBean {
	private List documentosTipoAsunto;

	public DocumentosTipoAsuntoBean() {
		documentosTipoAsunto = new ArrayList();
	}

	/**
	 * @return
	 */
	public int count() {
		return documentosTipoAsunto.size();
	}

	/**
	 * @param index
	 * @return
	 */
	public TipoAsuntoBean get(int index) {
		return (TipoAsuntoBean) documentosTipoAsunto.get(index);
	}

	/**
	 * @param documento
	 */
	public void add(DocumentoTipoAsuntoBean documento) {
		documentosTipoAsunto.add(documento);
	}

	/**
	 * @return
	 */
	public List getLista() {
		return documentosTipoAsunto;
	}

	/**
	 * @param documentosTiposAsunto
	 */
	public void setLista(List documentosTiposAsunto) {
		this.documentosTipoAsunto = documentosTiposAsunto;
	}
}
