package ieci.tecdoc.sgm.registro.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

/**
 * Clase que contiene los parámetros de consulta del asiento de registro.
 */

public class RegistroConsulta extends RetornoServicio {

	private String registryNumber;

	private String oprRegistryNumber; 

	private String firstRegistryDate;

	private String lastRegistryDate;
	
	private String firstEffectiveDate;

	private String lastEffectiveDate;

	private String senderId;

	private String topic;

	private String addressee;

	private String folderId;

	private String subject;

	private String type;

	private String subtype;

	private String status;

	/**
	 * Constructor de la clase RegistroConsulta
	 *
	 */
	public RegistroConsulta() {
		this.registryNumber = "";
		this.oprRegistryNumber = "0";
		this.firstRegistryDate = null;
		this.lastRegistryDate = null;
		this.firstEffectiveDate = null;
		this.lastEffectiveDate = null;
		this.senderId = "";
		this.topic = "";
		this.addressee = "";
		this.folderId = "";
		this.subject = "";
		this.type = "";
		this.subtype = "";
		this.status = "-1";

	}

	/**
	 * Establece el número de registro
	 * @param registryNumber Número de registro.
	 */
	public void setRegistryNumber(String registryNumber) {
		this.registryNumber = registryNumber;
	}

	/**
	 * Establece el identificador del operador de registro.
	 * @param oprRegistryNumber Identificador de operador de registro.
	 */
	public void setOprRegistryNumber(String oprRegistryNumber) {
		this.oprRegistryNumber = oprRegistryNumber;
	}

	/**
	 * Establece la fecha inicial del rango de fechas a incluir en la consulta.
	 * @param firstRegistryDate Fecha inicial del rango de consulta.
	 */
	public void setFirstRegistryDate(String firstRegistryDate) {
		this.firstRegistryDate = firstRegistryDate;
	}

	/**
	 * Establece la fecha de fin del rango de fechas de la consulta.
	 * 
	 * @param lastRegistryDate
	 *            Fecha fin del rango de la consulta.
	 */
	public void setLastRegistryDate(String lastRegistryDate) {
		this.lastRegistryDate = lastRegistryDate;
	}

	/**
	 * Establece el identificador del ciudadano.
	 * 
	 * @param senderId
	 *            Identificador del ciudadano.
	 */
	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	/**
	 * Establece el objeto del registro para la consulta.
	 * 
	 * @param topic
	 *            Objecto del registro.
	 */
	public void setTopic(String topic) {
		this.topic = topic;
	}

	/**
	 * Establece el organismos destintario
	 * 
	 * @param addressee
	 *            Organismo destinatario.
	 */
	public void setAddressee(String addressee) {
		this.addressee = addressee;
	}

	/**
	 * Establece el identificador de archivor del registro.
	 * 
	 * @param folderId
	 *            Identificador de archivador.
	 */
	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}

	/**
	 * Establece el asunto del registro.
	 * 
	 * @param subject
	 *            Asunto del registro.
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * Establece el tipo de registro.
	 * 
	 * @param type
	 *            Tipo de registro.
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Establece el subtipo del registro.
	 * 
	 * @param subtype
	 *            Subtipo del registro.
	 */
	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}

	/**
	 * Establece el estado del registro.
	 * 
	 * @param status
	 *            Estado del registro.
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Devuelve el número de registro.
	 * 
	 * @return String Número de registro.
	 */
	public String getRegistryNumber() {
		return this.registryNumber;
	}

	/**
	 * Devuelve el número del operador de registro.
	 * 
	 * @return int Número de operador de registro.
	 */
	public String getOprRegistryNumber() {
		return this.oprRegistryNumber;
	}

	/**
	 * Devuelve la fecha inicial del rango de fechas de la consulta.
	 * 
	 * @return String Fecha inicial del rango de consulta.
	 */
	public String getFirstRegistryDate() {
		return this.firstRegistryDate;
	}

	/**
	 * Devuelve la fecha final del rango de fechas de la consulta.
	 * 
	 * @return String Fecha final del rango de la consulta.
	 */
	public String getLastRegistryDate() {
		return this.lastRegistryDate;
	}

	/**
	 * Devuelve el identificador del ciudadano.
	 * 
	 * @return String identificador de ciudadano.
	 */
	public String getSenderId() {
		return this.senderId;
	}

	/**
	 * Devuelve el objeto del registro.
	 * 
	 * @return String Objeto del registro.
	 */
	public String getTopic() {
		return this.topic;
	}

	/**
	 * Devuelve el organismo destintario del registro.
	 * 
	 * @return String Identificador del organismo destinatario.
	 */
	public String getAddressee() {
		return this.addressee;
	}

	/**
	 * Devuelve el identificador de archivador.
	 * 
	 * @return String identificador de archivor.
	 */
	public String getFolderId() {
		return this.folderId;
	}

	/**
	 * Devuelve el asunto del registro.
	 * 
	 * @return String Asunto del registro.
	 */
	public String getSubject() {
		return this.subject;
	}

	/**
	 * Devuelve el tipo del registro.
	 * 
	 * @return String Tipo del registro.
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * Devuelve el subtipo del registro.
	 * 
	 * @return String subtipo de registro.
	 */
	public String getSubtype() {
		return this.subtype;
	}

	/**
	 * Devuelve el estado del registro.
	 * 
	 * @return int Estado del registro.
	 */
	public String getStatus() {
		return this.status;
	}

	public static final String EMPIEZA = "empieza";
	public static final String TERMINA = "termina";
	public static final String CONTIENE = "contiene";
	
	public static final int COD_IGUAL = 1;
	public static final int COD_MAYOR = 2;
	public static final int COD_MENOR = 3;
	public static final int COD_CONTIENE = 4; 
	public static final int COD_EMPIEZA = 5;
	public static final int COD_TERMINA = 6;

	public String getFirstEffectiveDate() {
		return firstEffectiveDate;
	}

	public void setFirstEffectiveDate(String firstEffectiveDate) {
		this.firstEffectiveDate = firstEffectiveDate;
	}

	public String getLastEffectiveDate() {
		return lastEffectiveDate;
	}

	public void setLastEffectiveDate(String lastEffectiveDate) {
		this.lastEffectiveDate = lastEffectiveDate;
	}

}