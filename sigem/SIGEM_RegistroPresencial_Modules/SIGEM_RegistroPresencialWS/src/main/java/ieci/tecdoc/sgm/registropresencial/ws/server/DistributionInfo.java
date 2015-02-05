package ieci.tecdoc.sgm.registropresencial.ws.server;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

/**
 * Almacena información de la distribucion de un registro
 * 
 */
public class DistributionInfo extends RetornoServicio {

	/**
	 * Identificador del registro distribuido.
	 */
	private String dtrId = null;

	/**
	 * Identificador del libro de registro.
	 */
	private String bookId = null;

	/**
	 * Tipo del libro de registro.
	 * 
	 * @value: 1-Entrada
	 * @value: 2-Salida.
	 */
	private String bookType = null;

	/**
	 * Nombre del libro de registro
	 */
	private String bookName = null;

	/**
	 * Identificador del registro.
	 */
	private String folderId = null;

	/**
	 * Fecha en la que se generó la distribución.
	 */
	private String distributionDate = null;

	/**
	 * Tipo de origen de la distribución:
	 * 
	 * @value: 1-Usuario
	 * @value: 2-Departamento
	 * @value: 3-Grupo.
	 */
	private String senderType = null;

	/**
	 * Identificador del origen de la distribución. Su significado depende del
	 * valor de senderType.
	 */
	private String senderId = null;

	/**
	 * Código y nombre del origen de la distribución.
	 */
	private String senderName = null;

	/**
	 * Tipo de destino de la distribución:
	 * 
	 * @value: 1-Usuario
	 * @value: 2-Departamento
	 * @value: 3-Grupo.
	 */
	private String destinationType = null;

	/**
	 * Identificador del destino de la distribución. Su significado depende del
	 * valor de destinationType
	 */
	private String destinationId = null;

	/**
	 * . Código y nombre del destino de la distribución.
	 */
	private String destinationName = null;

	/**
	 * Estado actual de la distribución:
	 * 
	 * @value: 1-Pendiente
	 * @value: 2-Aceptado
	 * @value: 3-Archivado
	 * @value: 4-Rechazado
	 * @value: 5-Redistribuido
	 */
	private String state = null;

	/**
	 * Descripción del estado de la distribución
	 */
	private String stateDescription = null;

	/**
	 * Fecha en la que se llegó al estado actual
	 */
	private String stateDate = null;

	/**
	 * Mensaje que acompaña a la distribución
	 */
	private String Message = null;

	/**
	 * Nombre del usuario
	 */
	private String user = null;

	/**
	 * Número de registro
	 */
	private String registerNumber = null;

	/**
	 * Fecha del registro
	 */
	private String registerDate = null;

	/**
	 * Nombre del destino del registro
	 */
	private String registerDestinationName = null;

	/**
	 * Descripción del tipo de asunto del registro
	 */
	private String registerMatterTypeName = null;

	/**
	 * Tipo de asunto del registro
	 */
	private String registerMatter = null;

	/**
	 * Nombre del origen del registro
	 */
	private String registerSenderName = null;

	/**
	 * Oficina de registro del Registro
	 */
	private String registerOffice = null;

	/**
	 * Interesados asociados al registro distribuido Remitentes en el caso de
	 * que sea un registro de entrada y destinatarios en el caso de que sea un
	 * registro de salida
	 */
	private PersonInfo[] sendersOrReceivers = null;

	/**
	 * @return the dtrId
	 */
	public String getDtrId() {
		return dtrId;
	}

	/**
	 * @param dtrId
	 *            the dtrId to set
	 */
	public void setDtrId(String dtrId) {
		this.dtrId = dtrId;
	}

	/**
	 * @return the bookId
	 */
	public String getBookId() {
		return bookId;
	}

	/**
	 * @param bookId
	 *            the bookId to set
	 */
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	/**
	 * @return the bookType
	 */
	public String getBookType() {
		return bookType;
	}

	/**
	 * @param bookType
	 *            the bookType to set
	 */
	public void setBookType(String bookType) {
		this.bookType = bookType;
	}

	/**
	 * @return the bookName
	 */
	public String getBookName() {
		return bookName;
	}

	/**
	 * @param bookName
	 *            the bookName to set
	 */
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	/**
	 * @return the folderId
	 */
	public String getFolderId() {
		return folderId;
	}

	/**
	 * @param folderId
	 *            the folderId to set
	 */
	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}

	/**
	 * @return the distributionDate
	 */
	public String getDistributionDate() {
		return distributionDate;
	}

	/**
	 * @param distributionDate
	 *            the distributionDate to set
	 */
	public void setDistributionDate(String distributionDate) {
		this.distributionDate = distributionDate;
	}

	/**
	 * @return the senderType
	 */
	public String getSenderType() {
		return senderType;
	}

	/**
	 * @param senderType
	 *            the senderType to set
	 */
	public void setSenderType(String senderType) {
		this.senderType = senderType;
	}

	/**
	 * @return the senderId
	 */
	public String getSenderId() {
		return senderId;
	}

	/**
	 * @param senderId
	 *            the senderId to set
	 */
	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	/**
	 * @return the senderName
	 */
	public String getSenderName() {
		return senderName;
	}

	/**
	 * @param senderName
	 *            the senderName to set
	 */
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	/**
	 * @return the destinationType
	 */
	public String getDestinationType() {
		return destinationType;
	}

	/**
	 * @param destinationType
	 *            the destinationType to set
	 */
	public void setDestinationType(String destinationType) {
		this.destinationType = destinationType;
	}

	/**
	 * @return the destinationId
	 */
	public String getDestinationId() {
		return destinationId;
	}

	/**
	 * @param destinationId
	 *            the destinationId to set
	 */
	public void setDestinationId(String destinationId) {
		this.destinationId = destinationId;
	}

	/**
	 * @return the destinationName
	 */
	public String getDestinationName() {
		return destinationName;
	}

	/**
	 * @param destinationName
	 *            the destinationName to set
	 */
	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the stateDescription
	 */
	public String getStateDescription() {
		return stateDescription;
	}

	/**
	 * @param stateDescription
	 *            the stateDescription to set
	 */
	public void setStateDescription(String stateDescription) {
		this.stateDescription = stateDescription;
	}

	/**
	 * @return the stateDate
	 */
	public String getStateDate() {
		return stateDate;
	}

	/**
	 * @param stateDate
	 *            the stateDate to set
	 */
	public void setStateDate(String stateDate) {
		this.stateDate = stateDate;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return Message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		Message = message;
	}

	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * @return the registerNumber
	 */
	public String getRegisterNumber() {
		return registerNumber;
	}

	/**
	 * @param registerNumber
	 *            the registerNumber to set
	 */
	public void setRegisterNumber(String registerNumber) {
		this.registerNumber = registerNumber;
	}

	/**
	 * @return the registerDate
	 */
	public String getRegisterDate() {
		return registerDate;
	}

	/**
	 * @param registerDate
	 *            the registerDate to set
	 */
	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}

	/**
	 * @return the registerDestinationName
	 */
	public String getRegisterDestinationName() {
		return registerDestinationName;
	}

	/**
	 * @param registerDestinationName
	 *            the registerDestinationName to set
	 */
	public void setRegisterDestinationName(String registerDestinationName) {
		this.registerDestinationName = registerDestinationName;
	}

	/**
	 * @return the registerMatterTypeName
	 */
	public String getRegisterMatterTypeName() {
		return registerMatterTypeName;
	}

	/**
	 * @param registerMatterTypeName
	 *            the registerMatterTypeName to set
	 */
	public void setRegisterMatterTypeName(String registerMatterTypeName) {
		this.registerMatterTypeName = registerMatterTypeName;
	}

	/**
	 * @return the registerMatter
	 */
	public String getRegisterMatter() {
		return registerMatter;
	}

	/**
	 * @param registerMatter
	 *            the registerMatter to set
	 */
	public void setRegisterMatter(String registerMatter) {
		this.registerMatter = registerMatter;
	}

	/**
	 * @return the registerSenderName
	 */
	public String getRegisterSenderName() {
		return registerSenderName;
	}

	/**
	 * @param registerSenderName
	 *            the registerSenderName to set
	 */
	public void setRegisterSenderName(String registerSenderName) {
		this.registerSenderName = registerSenderName;
	}

	/**
	 * @return the registerOffice
	 */
	public String getRegisterOffice() {
		return registerOffice;
	}

	/**
	 * @param registerOffice
	 *            the registerOffice to set
	 */
	public void setRegisterOffice(String registerOffice) {
		this.registerOffice = registerOffice;
	}

	/**
	 * @return the sendersOrReceivers
	 */
	public PersonInfo[] getSendersOrReceivers() {
		return sendersOrReceivers;
	}

	/**
	 * @param sendersOrReceivers
	 *            the sendersOrReceivers to set
	 */
	public void setSendersOrReceivers(PersonInfo[] sendersOrReceivers) {
		this.sendersOrReceivers = sendersOrReceivers;
	}

}
