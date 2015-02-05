package es.ieci.tecdoc.isicres.api.documento.electronico.business.vo;

public class DocumentoElectronicoAnexoContenidoVO {
	
	/**
	 *  contenido binario
	 */
	private byte[] content;

	/**
	 * 
	 * cadena que identifica donde obtener el recurso fisico
	 * 
	 */
	private String docUID;

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public String getDocUID() {
		return docUID;
	}

	public void setDocUID(String docUID) {
		this.docUID = docUID;
	}

}
