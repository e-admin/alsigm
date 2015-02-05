package ieci.tecdoc.sgm.registro.util;

import ieci.tecdoc.sgm.base.datetime.DatePattern;
import ieci.tecdoc.sgm.base.datetime.DateTimeUtil;
import ieci.tecdoc.sgm.base.xml.lite.XmlTextBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * Clase que contiene los parámetros de consulta del asiento de registro.
 */

public class RegistroConsulta implements Serializable {

	private String registryNumber;

	private int oprRegistryNumber; // Operador de registro

	private String firstRegistryDate;

	private String lastRegistryDate;
	
	private String firstEffectiveDate;

	private String lastEffectiveDate;

	private String senderId; // NIF, CIF...

	private String topic;

	private String addressee;

	private String folderId;

	private String subject;

	private String type;

	private String subtype;

	private int status;

	/**
	 * Constructor de la clase RegistroConsulta
	 *
	 */
	public RegistroConsulta() {
		this.registryNumber = "";
		this.oprRegistryNumber = 0;
		this.firstRegistryDate = null;
		this.lastRegistryDate = null;
		this.senderId = "";
		this.topic = "";
		this.addressee = "";
		this.folderId = "";
		this.subject = "";
		this.type = "";
		this.subtype = "";
		this.status = -1;

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
	public void setOprRegistryNumber(int oprRegistryNumber) {
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
	public void setStatus(int status) {
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
	public int getOprRegistryNumber() {
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
	public int getStatus() {
		return this.status;
	}

	/**
	 * Devuelve una cadena XMl con los datos de la consulta del registro.
	 * 
	 * @param headline
	 *            Indica si el XML debe llevar cabecera.
	 * @return String Cadena XML con los datos de la consulta.
	 */
	public String toXML(boolean headline) {
		XmlTextBuilder bdr;
		String tagName = "Registry_Document";
		String value;
		bdr = new XmlTextBuilder();
		if (headline)
			bdr.setStandardHeader();

		bdr.addOpeningTag(tagName);

		bdr.addSimpleElement("RegistryNumber", registryNumber);
		if (firstRegistryDate == null)
			value = "";
		else
			value = firstRegistryDate;//DateTimeUtil.getDateTime(firstRegistryDate,
					//DatePattern.XML_TIMESTAMP_PATTERN);
		bdr.addSimpleElement("FirstRegistryDate", value);
		if (lastRegistryDate == null)
			value = "";
		else
			value = lastRegistryDate;//DateTimeUtil.getDateTime(lastRegistryDate,
					//DatePattern.XML_TIMESTAMP_PATTERN);
		bdr.addSimpleElement("LastRegistryDate", value);
		bdr.addSimpleElement("SenderId", senderId);
		bdr.addSimpleElement("Topid", topic);
		bdr.addSimpleElement("Addressee", addressee);
		bdr.addSimpleElement("FolderId", folderId);
		bdr.addSimpleElement("Subject", subject);
		bdr.addSimpleElement("Type", type);
		bdr.addSimpleElement("Subtype", subtype);
		bdr.addSimpleElement("Status", Integer.toString(status));

		bdr.addClosingTag(tagName);

		return bdr.getText();
	}

	/**
	 * Devuelve una cadena con los datos de la consulta del registro.
	 * 
	 * @return String Cadena XML con los datos de la consulta.
	 */
	public String toString() {
		return toXML(false);
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