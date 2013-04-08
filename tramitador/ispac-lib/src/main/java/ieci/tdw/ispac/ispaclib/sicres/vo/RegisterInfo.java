package ieci.tdw.ispac.ispaclib.sicres.vo;

import java.util.Calendar;

/**
 * Información registral de un apunte.
 */
public class RegisterInfo {

	/* Oficina de registro.
	 */
    private RegisterOffice registerOffice;

    /* Número de registro.
     */
    private String registerNumber;

    /* Fecha de la anotación.
     */
    private Calendar registerDate;

    /* Tipo del registro.
     */
    private String registerType;

    /* Código del libro de registro
     */
    private String bookCode;
    
    private String bookId;
    
    public RegisterInfo() {
    }

    public RegisterInfo(RegisterOffice registerOffice,
           			    String registerNumber,
           			    Calendar registerDate,
           			    String registerType) {
    	
        this.registerOffice = registerOffice;
        this.registerNumber = registerNumber;
        this.registerDate = registerDate;
        this.registerType = registerType;
    }

	/**
	 * @return Returns the registerDate.
	 */
	public Calendar getRegisterDate() {
		return registerDate;
	}
	/**
	 * @param registerDate The registerDate to set.
	 */
	public void setRegisterDate(Calendar registerDate) {
		this.registerDate = registerDate;
	}

	/**
	 * @return Returns the registerNumber.
	 */
	public String getRegisterNumber() {
		return registerNumber;
	}
	/**
	 * @param registerNumber The registerNumber to set.
	 */
	public void setRegisterNumber(String registerNumber) {
		this.registerNumber = registerNumber;
	}

	/**
	 * @return Returns the registerOffice.
	 */
	public RegisterOffice getRegisterOffice() {
		return registerOffice;
	}
	/**
	 * @param registerOffice The registerOffice to set.
	 */
	public void setRegisterOffice(RegisterOffice registerOffice) {
		this.registerOffice = registerOffice;
	}

	/**
	 * @return Returns the registerType.
	 */
	public String getRegisterType() {
		return registerType;
	}
	/**
	 * @param registerType The registerType to set.
	 */
	public void setRegisterType(String registerType) {
		this.registerType = registerType;
	}

	public String getBookCode() {
		return bookCode;
	}

	public void setBookCode(String bookCode) {
		this.bookCode = bookCode;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

}