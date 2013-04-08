package ieci.tecdoc.sgm.core.services.registro;

/**
 * Datos de un registro
 * 
 */
public class RegisterInfo {

	/**
	 * Identificador de un registro
	 */
	protected String folderId;

	/**
	 * Identificador del libro al que pertenece el registro
	 */
	protected String bookId;

	/**
	 * Numero del registro
	 */
	protected String number;

	/**
	 * Fecha del registro
	 */
	protected String date;

	/**
	 * Nombre del usuario que realiza el apunte
	 */
	protected String userName;

	/**
	 * Fecha de trabajo
	 */
	protected String workDate;

	/**
	 * Estado del registro
	 * 
	 * @value: 0.- Completo
	 * @value: 1.- Incompleto
	 * @value: 2.- Reservado
	 * @value: 3.- Anulado
	 */
	protected String state;

	/**
	 * Identificador de la Oficina desde la que se realizo el apunte
	 */
	protected String office;

	/**
	 * Nombre de la Oficina desde la que se realizo el apunte
	 */
	protected String officeName;

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

}
