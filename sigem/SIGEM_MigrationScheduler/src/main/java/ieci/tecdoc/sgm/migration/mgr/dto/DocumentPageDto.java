package ieci.tecdoc.sgm.migration.mgr.dto;

public class DocumentPageDto {

	/**
	 * Listado de documentos
	 */
	private DocumentDto[] documentDto = null;
	
	/**
	 * Nombre del documento
	 */
	private String documentName = null;

	public DocumentDto[] getDocumentDto() {
		return documentDto;
	}

	public void setDocumentDto(DocumentDto[] documentDto) {
		this.documentDto = documentDto;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
	
}
