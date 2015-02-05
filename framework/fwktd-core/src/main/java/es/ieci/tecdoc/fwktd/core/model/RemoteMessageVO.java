/*
 * 
 */
package es.ieci.tecdoc.fwktd.core.model;

import org.springframework.validation.ObjectError;

/**
 * Value Object para mensajes internacionalizables remotos
 * 
 * @author IECISA
 * 
 */
public class RemoteMessageVO extends Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -536019821844969362L;

	/**
	 * Código del mensaje.
	 */
	private String messageId;

	/**
	 * Parámetros del mensaje
	 */
	private String[] params;

	/**
	 * Mensaje por defecto.
	 */
	private String defaultMessage;

	/**
	 * Constructor por defecto.
	 */
	public RemoteMessageVO() {
		super();
	}

	/**
	 * Constructor a partir de <code> ObjectError </code>
	 * 
	 * @param error
	 *            Error
	 */
	public RemoteMessageVO(ObjectError error) {
		this(error.getCode(), (String[]) error.getArguments(), error
				.getDefaultMessage());
	}

	/**
	 * Constructor
	 * 
	 * @param messageId
	 *            código del mensaje
	 * @param params
	 *            parametros
	 * @param defaultMessage
	 *            mensaje por defecto
	 */
	public RemoteMessageVO(String messageId, String[] params,
			String defaultMessage) {
		super();
		this.messageId = messageId;
		this.params = params;
		this.defaultMessage = defaultMessage;
	}

	/**
	 * Mensaje por defecto sin internacionalizar
	 * 
	 * @return mensaje por defecto.
	 */
	public String getDefaultMessage() {
		return defaultMessage;
	}

	/**
	 * Setter mensaje por defecto.
	 * 
	 * @param defaultMessage
	 */
	public void setDefaultMessage(String defaultMessage) {
		this.defaultMessage = defaultMessage;
	}

	/**
	 * Código del mensaje
	 * 
	 * @return codigo del mensaje
	 */
	public String getMessageId() {
		return messageId;
	}

	/**
	 * Setter código del mensaje
	 * 
	 * @param messageId
	 *            código del mensaje
	 */
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	/**
	 * Parámetros del mensaje
	 * 
	 * @return parametros
	 */
	public String[] getParams() {
		return params;
	}

	/**
	 * parámetros del mensaje
	 * 
	 * @param params
	 *            parametros
	 */
	public void setParams(String[] params) {
		this.params = params;
	}

}
