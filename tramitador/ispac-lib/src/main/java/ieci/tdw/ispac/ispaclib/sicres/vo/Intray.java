package ieci.tdw.ispac.ispaclib.sicres.vo;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

public class Intray implements Serializable {

	/**
	 * Identificador del registro de distribución.
	 */
	private String id = null;
	
	/**
	 * Número de registro.
	 */
	private String registerNumber = null;

	/**
	 * Fecha de registro.
	 */
	private Date registerDate = null;

	/**
	 * Estado del registro de distribución.
	 */
	private int state = 0;

	/**
	 * Fecha de estado.
	 */
	private Date stateDate = null;

	/**
	 * Origen de la distribución.
	 */
	private String sender = null;
	
	/**
	 * Destino de la distribución.
	 */
	private String destination = null;
	
	/**
	 * Fecha de distribución.
	 */
	private Date date = null;
	
	/**
	 * Mensaje de distribución.
	 */
	private String message = null;
	
	/**
	 * Asunto del registro.
	 */
	private String matter = null;

	/**
	 * Tipo de asunto del registro.
	 */
	private String matterTypeName = null;

	/**
	 * Oficina del registro.
	 */
	private String registerOffice = null;
	
	/**
	 * Origen del registro.
	 */
	private String registerOrigin = null;

	/**
	 * Remitentes del registro
	 */
	private ThirdPerson[] registerSender = null;
	
	/**
	 * Destino del registro
	 */
	private String registerDestination = null;

	/**
	 * Destinatarios del registro
	 */
	private ThirdPerson[] registerAddressee = null;
	
	
	/**
	 * Constructor.
	 */
	public Intray() {
		super();
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getRegisterNumber() {
		return registerNumber;
	}


	public void setRegisterNumber(String registerNumber) {
		this.registerNumber = registerNumber;
	}


	public Date getRegisterDate() {
		return registerDate;
	}


	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}


	public int getState() {
		return state;
	}


	public void setState(int estado) {
		this.state = estado;
	}


	public String getSender() {
		return sender;
	}


	public void setSender(String sender) {
		this.sender = sender;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public String getMatter() {
		return matter;
	}


	public void setMatter(String matter) {
		this.matter = matter;
	}


	public String getMatterTypeName() {
		return matterTypeName;
	}


	public void setMatterTypeName(String matterTypeName) {
		this.matterTypeName = matterTypeName;
	}


	public Date getStateDate() {
		return stateDate;
	}


	public void setStateDate(Date stateDate) {
		this.stateDate = stateDate;
	}


	public String getDestination() {
		return destination;
	}


	public void setDestination(String destination) {
		this.destination = destination;
	}


	public String getRegisterOffice() {
		return registerOffice;
	}


	public void setRegisterOffice(String registerOffice) {
		this.registerOffice = registerOffice;
	}


	public String getRegisterOrigin() {
		return registerOrigin;
	}


	public void setRegisterOrigin(String registerOrigin) {
		this.registerOrigin = registerOrigin;
	}


	public ThirdPerson[] getRegisterSender() {
		return registerSender;
	}


	public void setRegisterSender(ThirdPerson[] registerSender) {
		this.registerSender = registerSender;
	}


	public String getRegisterDestination() {
		return registerDestination;
	}


	public void setRegisterDestination(String registerDestination) {
		this.registerDestination = registerDestination;
	}


	public ThirdPerson[] getRegisterAddressee() {
		return registerAddressee;
	}


	public void setRegisterAddressee(ThirdPerson[] registerAddressee) {
		this.registerAddressee = registerAddressee;
	}


	/**
	 * {@inheritDoc}
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Intray [id=");
		builder.append(id);
		builder.append(", registerNumber=");
		builder.append(registerNumber);
		builder.append(", registerDate=");
		builder.append(registerDate);
		builder.append(", state=");
		builder.append(state);
		builder.append(", stateDate=");
		builder.append(stateDate);
		builder.append(", sender=");
		builder.append(sender);
		builder.append(", destination=");
		builder.append(destination);
		builder.append(", date=");
		builder.append(date);
		builder.append(", message=");
		builder.append(message);
		builder.append(", matter=");
		builder.append(matter);
		builder.append(", matterTypeName=");
		builder.append(matterTypeName);
		builder.append(", registerOffice=");
		builder.append(registerOffice);
		builder.append(", registerOrigin=");
		builder.append(registerOrigin);
		builder.append(", registerSender=");
		builder.append(Arrays.toString(registerSender));
		builder.append(", registerDestination=");
		builder.append(registerDestination);
		builder.append(", registerAddressee=");
		builder.append(Arrays.toString(registerAddressee));
		builder.append("]");
		return builder.toString();
	}
	
	
	
}
