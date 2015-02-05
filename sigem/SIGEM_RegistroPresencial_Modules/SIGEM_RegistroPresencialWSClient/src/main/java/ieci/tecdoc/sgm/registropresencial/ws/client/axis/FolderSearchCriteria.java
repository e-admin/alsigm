/**
 * 
 */
package ieci.tecdoc.sgm.registropresencial.ws.client.axis;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

/**
 * @author 66575267
 * 
 */
public class FolderSearchCriteria extends RetornoServicio {

	private String folderId;

	private String folderNumber;

	private FieldsSearchCriteria fields;

	private BookId bookId;

	public String getFolderId() {
		return folderId;
	}

	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}

	public String getFolderNumber() {
		return folderNumber;
	}

	public void setFolderNumber(String folderNumber) {
		this.folderNumber = folderNumber;
	}

	public FieldsSearchCriteria getFields() {
		return fields;
	}

	public void setFields(FieldsSearchCriteria fields) {
		this.fields = fields;
	}

	public BookId getBookId() {
		return bookId;
	}

	public void setBookId(BookId bookId) {
		this.bookId = bookId;
	}

}
