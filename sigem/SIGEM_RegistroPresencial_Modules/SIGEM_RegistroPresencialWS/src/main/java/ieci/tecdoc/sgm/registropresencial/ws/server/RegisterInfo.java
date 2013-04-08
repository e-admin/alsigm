package ieci.tecdoc.sgm.registropresencial.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

/**
 * 
 * Información sobre un registro
 * 
 */
public class RegisterInfo extends RetornoServicio {

	/**
	 * Identificador de un registro
	 */
	private String folderId;

	/**
	 * Identificador del libro de registro
	 */
	private String bookId;

	/**
	 * Número de registro
	 */
	private String number;

	/**
	 * Fecha del registro
	 */
	private String date;

	/**
	 * Usuario que efectua el registro
	 */
	private String userName;

	/**
	 * Fecha de la última operación con el registro
	 */
	private String workDate;

	/**
	 * Estado del registro
	 */
	private String state;

	/**
	 * Oficina de registro
	 */
	private String office;

	/**
	 * Nombre de la oficina de registro
	 */
	private String officeName;

	/**
	 * @return
	 */
	public String getBookId() {
		return bookId;
	}

	/**
	 * @param bookId
	 */
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	/**
	 * @return
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return
	 */
	public String getFolderId() {
		return folderId;
	}

	/**
	 * @param folderId
	 */
	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}

	/**
	 * @return
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * @param number
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * @return
	 */
	public String getOffice() {
		return office;
	}

	/**
	 * @param office
	 */
	public void setOffice(String office) {
		this.office = office;
	}

	/**
	 * @return
	 */
	public String getOfficeName() {
		return officeName;
	}

	/**
	 * @param officeName
	 */
	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	/**
	 * @return
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return
	 */
	public String getWorkDate() {
		return workDate;
	}

	/**
	 * @param workDate
	 */
	public void setWorkDate(String workDate) {
		this.workDate = workDate;
	}

}
