package ieci.tecdoc.sgm.registropresencial.info;

public class InfoRegister {
	private String folderId;
	private String bookId;
	private String number;
	private String date;
	private String userName;
	private String workDate;
	private String state;
	private String office;
	private String officeName;

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getFolderId() {
		return folderId;
	}

	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getOffice() {
		return office;
	}

	public void setOffice(String office) {
		this.office = office;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getWorkDate() {
		return workDate;
	}

	public void setWorkDate(String workDate) {
		this.workDate = workDate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("##");
		buffer.append(" folderId [");
		buffer.append(folderId);
		buffer.append("] bookId [");
		buffer.append(bookId);
		buffer.append("] number [");
		buffer.append(number);
		buffer.append("] date [");
		buffer.append(date);
		buffer.append("] userName [");
		buffer.append(userName);
		buffer.append("] workDate [");
		buffer.append(workDate);
		buffer.append("] state [");
		buffer.append(state);
		buffer.append("] office [");
		buffer.append(office);
		buffer.append(" officeName [");
		buffer.append(officeName);

		buffer.append("]");
		return buffer.toString();
	}

}
