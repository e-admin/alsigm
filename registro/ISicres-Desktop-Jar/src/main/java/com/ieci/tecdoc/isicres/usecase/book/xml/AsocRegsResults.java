package com.ieci.tecdoc.isicres.usecase.book.xml;

public class AsocRegsResults {

	private Integer bookId;
	private Integer folderId;
	private String folderNumber;
	private String folderDate;
	private String summary;

	public AsocRegsResults(Integer bookId, Integer folderId,
			String folderNumber, String folderDate, String summary) {
		this.bookId = bookId;
		this.folderId = folderId;
		this.folderNumber = folderNumber;
		this.folderDate = folderDate;
		this.summary = summary;
	}

	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public Integer getFolderId() {
		return folderId;
	}

	public void setFolderId(Integer folderId) {
		this.folderId = folderId;
	}

	public String getFolderNumber() {
		return folderNumber;
	}

	public void setFolderNumber(String folderNumber) {
		this.folderNumber = folderNumber;
	}

	public String getFolderDate() {
		return folderDate;
	}

	public void setFolderDate(String folderDate) {
		this.folderDate = folderDate;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	

}
