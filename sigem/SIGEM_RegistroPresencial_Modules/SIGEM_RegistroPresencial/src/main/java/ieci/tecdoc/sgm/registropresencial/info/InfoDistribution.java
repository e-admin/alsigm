package ieci.tecdoc.sgm.registropresencial.info;

import ieci.tecdoc.sgm.core.services.registro.Interested;

public class InfoDistribution {
	private Integer dtrId = null;

	private Integer bookId = null;

	private Integer bookType = null;

	private String bookName = null;

	private Integer folderId = null;

	private String distributionDate = null;

	private Integer senderType = null;

	private Integer senderId = null;

	private String senderName = null;

	private Integer destinationType = null;

	private Integer destinationId = null;

	private String destinationName = null;

	private Integer state = null;

	private String stateDescription = null;

	private String stateDate = null;

	private String Message = null;

	private String user = null;

	private String registerNumber = null;

	private String registerType = null;

	private String registerDate = null;

	private String registerDestinationName = null;

	private String registerSenderName = null;

	private String registerMatterTypeName = null;

	private String registerMatter = null;

	private String registerOffice = null;

	private Interested[] sendersOrReceivers = null;

	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public Integer getBookType() {
		return bookType;
	}

	public void setBookType(Integer bookType) {
		this.bookType = bookType;
	}

	public Integer getDestinationId() {
		return destinationId;
	}

	public void setDestinationId(Integer destinationId) {
		this.destinationId = destinationId;
	}

	public String getDestinationName() {
		return destinationName;
	}

	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}

	public Integer getDestinationType() {
		return destinationType;
	}

	public void setDestinationType(Integer destinationType) {
		this.destinationType = destinationType;
	}

	public String getDistributionDate() {
		return distributionDate;
	}

	public void setDistributionDate(String distributionDate) {
		this.distributionDate = distributionDate;
	}

	public Integer getDtrId() {
		return dtrId;
	}

	public void setDtrId(Integer dtrId) {
		this.dtrId = dtrId;
	}

	public Integer getFolderId() {
		return folderId;
	}

	public void setFolderId(Integer folderId) {
		this.folderId = folderId;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}

	public String getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}

	public String getRegisterDestinationName() {
		return registerDestinationName;
	}

	public void setRegisterDestinationName(String registerDestinationName) {
		this.registerDestinationName = registerDestinationName;
	}

	public String getRegisterMatter() {
		return registerMatter;
	}

	public void setRegisterMatter(String registerMatter) {
		this.registerMatter = registerMatter;
	}

	public String getRegisterMatterTypeName() {
		return registerMatterTypeName;
	}

	public void setRegisterMatterTypeName(String registerMatterTypeName) {
		this.registerMatterTypeName = registerMatterTypeName;
	}

	public Integer getSenderId() {
		return senderId;
	}

	public void setSenderId(Integer senderId) {
		this.senderId = senderId;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public Integer getSenderType() {
		return senderType;
	}

	public void setSenderType(Integer senderType) {
		this.senderType = senderType;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getStateDate() {
		return stateDate;
	}

	public void setStateDate(String stateDate) {
		this.stateDate = stateDate;
	}

	public String getStateDescription() {
		return stateDescription;
	}

	public void setStateDescription(String stateDescription) {
		this.stateDescription = stateDescription;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getRegisterNumber() {
		return registerNumber;
	}

	public void setRegisterNumber(String registerNumber) {
		this.registerNumber = registerNumber;
	}

	public String getRegisterType() {
		return registerType;
	}

	public void setRegisterType(String registerType) {
		this.registerType = registerType;
	}

	public String getRegisterSenderName() {
		return registerSenderName;
	}

	public void setRegisterSenderName(String registerSenderName) {
		this.registerSenderName = registerSenderName;
	}

	public String getRegisterOffice() {
		return registerOffice;
	}

	public void setRegisterOffice(String registerOffice) {
		this.registerOffice = registerOffice;
	}

	public Interested[] getSendersOrReceivers() {
		return sendersOrReceivers;
	}

	public void setSendersOrReceivers(Interested[] sendersOrReceivers) {
		this.sendersOrReceivers = sendersOrReceivers;
	}

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
				buffer.append(sendersOrReceivers[i].getName());
				buffer.append("; ");
				buffer.append(sendersOrReceivers[i].getAddress());
			}
		}

		buffer.append("]");
		return buffer.toString();
	}

}
