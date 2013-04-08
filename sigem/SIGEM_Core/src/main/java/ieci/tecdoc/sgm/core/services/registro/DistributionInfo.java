package ieci.tecdoc.sgm.core.services.registro;

/**
 * Información de la distribuion
 * 
 */
public class DistributionInfo {

	/**
	 * Identificador de la distribucion
	 */
	protected Integer dtrId = null;

	/**
	 * Identificador del libro al que pertenece el registro distribuido
	 */
	protected Integer bookId = null;

	/**
	 * Tipo del libro al que pertenece el registro distribuido
	 */
	protected Integer bookType = null;

	/**
	 * Nombre del libro al que pertenece el registro distribuido
	 */
	protected String bookName = null;

	/**
	 * Identificador del registro distribucion
	 */
	protected Integer folderId = null;

	/**
	 * Fecha de la distribución
	 */
	protected String distributionDate = null;

	/**
	 * Tipo de Origen de la distribución
	 * 
	 * @value = 1.- Usuario
	 * @value = 2.- Departamento
	 * @value = 3.- Grupo
	 */
	protected Integer senderType = null;

	/**
	 * Identificador del Origen de la distribución
	 */
	protected Integer senderId = null;

	/**
	 * Nombre del Origen de la distribución
	 */
	protected String senderName = null;

	/**
	 * Tipo de Destino de la distribución
	 * 
	 * @value = 1.- Usuario
	 * @value = 2.- Departamento
	 * @value = 3.- Grupo
	 */
	protected Integer destinationType = null;

	/**
	 * Identificador del Destino de la distribución
	 */
	protected Integer destinationId = null;

	/**
	 * Nombre del Destino de la distribución
	 */
	protected String destinationName = null;

	/**
	 * Estado de la distribución
	 * 
	 * @value: 1.- Pendiente
	 * @value: 2.- Aceptado
	 * @value: 3.- Archivado
	 * @value: 4.- Rechazado
	 * @value: 5.- Redistribuido
	 */
	protected Integer state = null;

	/**
	 * Descripción del estado de la distribución
	 */
	protected String stateDescription = null;

	/**
	 * Fecha en la que la distribución al estado en el que se encuentra
	 */
	protected String stateDate = null;

	/**
	 * Información o comentarios sobre la distribución
	 */
	protected String Message = null;

	/**
	 * Usuarió que realizó la distribucion o el registro en el caso de la
	 * distribución automática
	 */
	protected String user = null;

	/**
	 * Número del Registro distribuido
	 */
	protected String registerNumber = null;

	/**
	 * Tipo del registro distribuido
	 * 
	 * @value: 1.- Entrada
	 * @value: 2.- Salida
	 */
	protected String registerType = null;

	/**
	 * Fecha de creación del registro
	 */
	protected String registerDate = null;

	/**
	 * Nombre del destino del registro
	 */
	protected String registerDestinationName = null;

	/**
	 * Nombre del origen del registro
	 */
	protected String registerSenderName = null;

	/**
	 * Tipo de asunto del registro
	 */
	protected String registerMatterTypeName = null;

	/**
	 * Asunto del registro
	 */
	protected String registerMatter = null;

	/**
	 * Oficina de registro del Registro
	 */
	protected String registerOffice = null;

	/**
	 * Interesados asociados al registro distribuido Remitentes en el caso de
	 * que sea un registro de entrada y destinatarios en el caso de que sea un
	 * registro de salida
	 */
	protected PersonInfo[] sendersOrReceivers = null;

	/**
	 * @return
	 */
	public Integer getBookId() {
		return bookId;
	}

	/**
	 * @param bookId
	 */
	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	/**
	 * @return
	 */
	public String getBookName() {
		return bookName;
	}

	/**
	 * @param bookName
	 */
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	/**
	 * @return
	 */
	public Integer getBookType() {
		return bookType;
	}

	/**
	 * @param bookType
	 */
	public void setBookType(Integer bookType) {
		this.bookType = bookType;
	}

	/**
	 * @return
	 */
	public Integer getDestinationId() {
		return destinationId;
	}

	/**
	 * @param destinationId
	 */
	public void setDestinationId(Integer destinationId) {
		this.destinationId = destinationId;
	}

	/**
	 * @return
	 */
	public String getDestinationName() {
		return destinationName;
	}

	/**
	 * @param destinationName
	 */
	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}

	/**
	 * @return
	 */
	public Integer getDestinationType() {
		return destinationType;
	}

	/**
	 * @param destinationType
	 */
	public void setDestinationType(Integer destinationType) {
		this.destinationType = destinationType;
	}

	/**
	 * @return
	 */
	public String getDistributionDate() {
		return distributionDate;
	}

	/**
	 * @param distributionDate
	 */
	public void setDistributionDate(String distributionDate) {
		this.distributionDate = distributionDate;
	}

	/**
	 * @return
	 */
	public Integer getDtrId() {
		return dtrId;
	}

	/**
	 * @param dtrId
	 */
	public void setDtrId(Integer dtrId) {
		this.dtrId = dtrId;
	}

	/**
	 * @return
	 */
	public Integer getFolderId() {
		return folderId;
	}

	/**
	 * @param folderId
	 */
	public void setFolderId(Integer folderId) {
		this.folderId = folderId;
	}

	/**
	 * @return
	 */
	public String getMessage() {
		return Message;
	}

	/**
	 * @param message
	 */
	public void setMessage(String message) {
		Message = message;
	}

	/**
	 * @return
	 */
	public String getRegisterDate() {
		return registerDate;
	}

	/**
	 * @param registerDate
	 */
	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}

	/**
	 * @return
	 */
	public String getRegisterDestinationName() {
		return registerDestinationName;
	}

	/**
	 * @param registerDestinationName
	 */
	public void setRegisterDestinationName(String registerDestinationName) {
		this.registerDestinationName = registerDestinationName;
	}

	/**
	 * @return
	 */
	public String getRegisterMatter() {
		return registerMatter;
	}

	/**
	 * @param registerMatter
	 */
	public void setRegisterMatter(String registerMatter) {
		this.registerMatter = registerMatter;
	}

	/**
	 * @return
	 */
	public String getRegisterMatterTypeName() {
		return registerMatterTypeName;
	}

	/**
	 * @param registerMatterTypeName
	 */
	public void setRegisterMatterTypeName(String registerMatterTypeName) {
		this.registerMatterTypeName = registerMatterTypeName;
	}

	/**
	 * @return
	 */
	public Integer getSenderId() {
		return senderId;
	}

	/**
	 * @param senderId
	 */
	public void setSenderId(Integer senderId) {
		this.senderId = senderId;
	}

	/**
	 * @return
	 */
	public String getSenderName() {
		return senderName;
	}

	/**
	 * @param senderName
	 */
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	/**
	 * @return
	 */
	public Integer getSenderType() {
		return senderType;
	}

	/**
	 * @param senderType
	 */
	public void setSenderType(Integer senderType) {
		this.senderType = senderType;
	}

	/**
	 * @return
	 */
	public Integer getState() {
		return state;
	}

	/**
	 * @param state
	 */
	public void setState(Integer state) {
		this.state = state;
	}

	/**
	 * @return
	 */
	public String getStateDate() {
		return stateDate;
	}

	/**
	 * @param stateDate
	 */
	public void setStateDate(String stateDate) {
		this.stateDate = stateDate;
	}

	/**
	 * @return
	 */
	public String getStateDescription() {
		return stateDescription;
	}

	/**
	 * @param stateDescription
	 */
	public void setStateDescription(String stateDescription) {
		this.stateDescription = stateDescription;
	}

	/**
	 * @return
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @param user
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * @return
	 */
	public String getRegisterNumber() {
		return registerNumber;
	}

	/**
	 * @param registerNumber
	 */
	public void setRegisterNumber(String registerNumber) {
		this.registerNumber = registerNumber;
	}

	/**
	 * @return
	 */
	public String getRegisterType() {
		return registerType;
	}

	/**
	 * @param registerType
	 */
	public void setRegisterType(String registerType) {
		this.registerType = registerType;
	}

	/**
	 * @return
	 */
	public String getRegisterSenderName() {
		return registerSenderName;
	}

	/**
	 * @param registerSenderName
	 */
	public void setRegisterSenderName(String registerSenderName) {
		this.registerSenderName = registerSenderName;
	}

	/**
	 * @return
	 */
	public String getRegisterOffice() {
		return registerOffice;
	}

	/**
	 * @param registerOffice
	 */
	public void setRegisterOffice(String registerOffice) {
		this.registerOffice = registerOffice;
	}

	/**
	 * @return
	 */
	public PersonInfo[] getSendersOrReceivers() {
		return sendersOrReceivers;
	}

	/**
	 * @param sendersOrReceivers
	 */
	public void setSendersOrReceivers(PersonInfo[] sendersOrReceivers) {
		this.sendersOrReceivers = sendersOrReceivers;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("##");
		buffer.append(" dtrId [");
		buffer.append(dtrId);
		buffer.append("] bookId [");
		buffer.append(bookId);
		buffer.append("] bookType [");
		buffer.append(bookType);
		buffer.append("] bookName [");
		buffer.append(bookName);
		buffer.append("] folderId [");
		buffer.append(folderId);
		buffer.append("] distributionDate [");
		buffer.append(distributionDate);
		buffer.append("] senderType [");
		buffer.append(senderType);
		buffer.append("] senderId [");
		buffer.append(senderId);
		buffer.append(" senderName [");
		buffer.append(senderName);
		buffer.append("] destinationType [");
		buffer.append(destinationType);
		buffer.append("] destinationId [");
		buffer.append(destinationId);
		buffer.append("] destinationName [");
		buffer.append(destinationName);
		buffer.append("] state [");
		buffer.append(state);
		buffer.append("] stateDescription [");
		buffer.append(stateDescription);
		buffer.append("] stateDate [");
		buffer.append(stateDate);
		buffer.append("] Message [");
		buffer.append(Message);
		buffer.append("] user [");
		buffer.append(user);
		buffer.append("] registerNumber [");
		buffer.append(registerNumber);
		buffer.append("] registerType [");
		buffer.append(registerType);
		buffer.append("] registerDate [");
		buffer.append(registerDate);
		buffer.append("] registerDestinationName [");
		buffer.append(registerDestinationName);
		buffer.append("] registerSenderName [");
		buffer.append(registerSenderName);
		buffer.append(" registerMatterTypeName [");
		buffer.append(registerMatterTypeName);
		buffer.append("] registerMatter [");
		buffer.append(registerMatter);
		buffer.append("] registerOffice [");
		buffer.append(registerOffice);

		if (sendersOrReceivers != null && sendersOrReceivers.length > 0) {
			for (int i = 0; i < sendersOrReceivers.length; i++) {
				buffer.append("] sendersOrReceivers ( ");
				buffer.append(i);
				buffer.append(" ) [");
				buffer.append(sendersOrReceivers[i].getPersonName());
				buffer.append("; ");
				buffer.append(sendersOrReceivers[i].getDirection());
			}
		}

		buffer.append("]");
		return buffer.toString();
	}

}
