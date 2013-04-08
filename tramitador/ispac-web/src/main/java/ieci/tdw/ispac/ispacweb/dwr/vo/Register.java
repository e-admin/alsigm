package ieci.tdw.ispac.ispacweb.dwr.vo;

import java.io.Serializable;

public class Register implements Serializable {

	
	/**
	 * Número de registro
	 */
	private String registerNumber;
	/**
	 * Fecha en la que se realizó el registro
	 */
	private String registerDate;
	/**
	 * Usuario que realizó el registro
	 */
	private String user;
	/**
	 * Destinatario del registro
	 */
	private String addressee;
	/**
	 * Oficina registradora
	 */
	private String office;
	/**
	 * Fecha de creación de la oficina registradora
	 */
	private String creationOfficeDate;
	/**
	 * Unida administrativa origen
	 */
	private String uniAdmOrigin;
	/**
	 * Unidad administrativa destino
	 */
	private String uniAdmAddressee;
	/**
	 * Tipo de asunto del registro
	 */
	private String subjectType;
	/**
	 * Resumen
	 */
	private String summary;
	
	/**
	 * Constructor 
	 * @param numRegister número de registro
	 * @param dateRegister fecha del registro
	 * @param user usuario registrador
	 * @param addressee destinatario
	 * @param office oficina registradora
	 * @param dateCreationOffice fecha creación de la oficina registradora
	 * @param uniAdmOrigin unidad administrativa origen
	 * @param uniAdmAddressee unidad administrativa destino
	 * @param typeSubject tipo de asunto
	 * @param summary resumen
	 */
	public Register(String numRegister, String dateRegister) {
		super();
		this.registerNumber = numRegister;
		this.registerDate = dateRegister;
		
	}


	/**
	 * Constructor.
	 */
	public Register() {
		super();
	}

	
	
	public String getNumRegister() {
		return registerNumber;
	}


	public void setNumRegister(String numRegister) {
		this.registerNumber = numRegister;
	}


	public String getDateRegister() {
		return registerDate;
	}


	public void setDateRegister(String dateRegister) {
		this.registerDate = dateRegister;
	}


	public String getUser() {
		return user;
	}


	public void setUser(String user) {
		this.user = user;
	}


	public String getAddressee() {
		return addressee;
	}


	public void setAddressee(String addressee) {
		this.addressee = addressee;
	}


	public String getOffice() {
		return office;
	}


	public void setOffice(String office) {
		this.office = office;
	}


	public String getDateCreationOffice() {
		return creationOfficeDate;
	}


	public void setDateCreationOffice(String creationOfficeDate) {
		this.creationOfficeDate = creationOfficeDate;
	}


	public String getUniAdmOrigin() {
		return uniAdmOrigin;
	}


	public void setUniAdmOrigin(String uniAdmOrigin) {
		this.uniAdmOrigin = uniAdmOrigin;
	}


	public String getUniAdmAddressee() {
		return uniAdmAddressee;
	}


	public void setUniAdmAddressee(String uniAdmAddressee) {
		this.uniAdmAddressee = uniAdmAddressee;
	}


	public String getSubjectType() {
		return subjectType;
	}


	public void setSubjectType(String subjectType) {
		this.subjectType = subjectType;
	}


	public String getSummary() {
		return summary;
	}


	public void setSummary(String summary) {
		this.summary = summary;
	}



	
	
}
