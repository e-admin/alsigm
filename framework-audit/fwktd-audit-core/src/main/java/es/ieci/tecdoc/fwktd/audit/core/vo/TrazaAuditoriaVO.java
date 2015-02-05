package es.ieci.tecdoc.fwktd.audit.core.vo;

import java.util.Date;

import es.ieci.tecdoc.fwktd.core.model.Entity;

/**
 * @author Iecisa
 * Clase entidad que representará la traza a auditar de algún evento generado en las aplicaciones
 */
public class TrazaAuditoriaVO extends Entity {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6073271693049957206L;

	/**
	 * identificador de la aplicación que ha generado el evento de a auditar,
	 * 
	 * DATO REQUERIDO
	 */
	protected Long appId;
	
	/**
	 * descripcion de la aplicación que ha generado el evento de a auditar  
	 */
	protected String appDescription;
	
	/**
	 * identificador del evento del que se está haciendo aditoría 
	 * Cada aplicación debería poseer unos enumerados que indiquen todos los tipos de eventos que van a auditar, ya que son ellas
	 * las que conocen su modelo de negocio y son ellas las que indicarán que eventos van a auditar
	 * 
	 * DATO REQUERIDO
	 */
	protected Long eventType;
	
	/**
	 * descripcion del evento auditado
	 * 
	 * DATO REQUERIDO
	 */
	protected String eventDescription;
	
	/**
	 * fecha en la que se ha producido el evento a auditar
	 * 
	 * DATO REQUERIDO
	 */
	protected Date eventDate;
	
	/**
	 * identificador del usuario que ha desencadenado el evento a auditar
	 * 
	 *  DATO REQUERIDO
	 */
	protected String userId;
	
	/**
	 * nombre del usuario que ha desencadenado el evento a auditar
	 * 
	 * DATO REQUERIDO
	 */
	protected String userName;

	/**
	 * ip del usuario que ha desencadenado el evento a auditar
	 */
	protected String userIp;
	
	/**
	 * nombre del host de la ip del usuario que ha desencadenado el evento a auditar
	 */
	protected String userHostName;
	
	/**
	 * tipo del objeto a auditar, se refiere por ejemplo a un nombre nombre de la tabla o objeto o clase o entidad logica a auditar
	 * 
	 * Cada aplicación debería poseer unos enumerados que indiquen todos los tipos de eventos que van a auditar, ya que son ellas
	 * las que conocen su modelo de negocio y son ellas las que indicarán que tipos de entidades aa auditar
	 * 
	 * DATO REQUERIDO
	 */
	protected String objectType;
	
	/**
	 * descripcion del objeto que estamos auditando
	 * 
	 * DATO REQUERIDO
	 */
	protected String objectTypeDescription;
	
	/**
	 * identificador del objeto que vamos a auditar
	 * Por ejemplo, si estamos auditando un objeto que representa un registro de una tabla seria su primary key
	 * 
	 */
	protected String objectId;
	
	/**
	 * Campo del objeto a auditar.
	 * Por ejemplo, si estamos auditando un objeto que representa el campo de un registro de una tabla 
	 */
	protected String objectField;
	
	
	/**
	 * antiguo valor modificado que se va auditar
	 */
	protected String oldValue;
	
	/**
	 * antiguo valor modificado que se va auditar
	 * 
	 */
	protected String newValue;
	

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public String getAppDescription() {
		return appDescription;
	}

	public void setAppDescription(String appDescription) {
		this.appDescription = appDescription;
	}

	public Long getEventType() {
		return eventType;
	}

	public void setEventType(Long eventType) {
		this.eventType = eventType;
	}

	public String getEventDescription() {
		return eventDescription;
	}

	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}

	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserIp() {
		return userIp;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

	public String getUserHostName() {
		return userHostName;
	}

	public void setUserHostName(String userHostName) {
		this.userHostName = userHostName;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public String getObjectTypeDescription() {
		return objectTypeDescription;
	}

	public void setObjectTypeDescription(String objectTypeDescription) {
		this.objectTypeDescription = objectTypeDescription;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getObjectField() {
		return objectField;
	}

	public void setObjectField(String objectField) {
		this.objectField = objectField;
	}

	public String getOldValue() {
		return oldValue;
	}

	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	public String getNewValue() {
		return newValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}
	
	
	

}
