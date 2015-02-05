package es.ieci.tecdoc.fwktd.ldap.core.messages;

import java.text.MessageFormat;

import es.ieci.tecdoc.fwktd.core.messages.ApplicationMessages;

/**
 * Clase base tanto para mensajes como para errores
 * @author Iecisa
 * @version $Revision: 115 $
 *
 */
public final class LdapMessages{

	/**
	 * Constructor privado
	 */
	private LdapMessages(){

	}

	/**
	 * Formatea un mensaje
	 * @param bundle Bundle con los mensajes
	 * @param msgCode Codigo de mensaje
	 * @return Mensaje
	 */
	public static String formatMessage(final String bundle, final String msgCode){
		return formatMessage(bundle, msgCode, null);
	}

	/**
	 * Formatea un mensaje con sus parametros
	 * @param bundle Bundle con los mensajes
	 * @param msgCode Codigo de mensaje
	 * @param args Argumentos del mensaje
	 * @return Mensaje con sus parametros
	 */
	public static String formatMessage(final String bundle, final String msgCode, final Object [] args){
		String message = new StringBuffer(msgCode).append(": ").append(ApplicationMessages.getString(bundle, msgCode)).toString();

		if ((args!=null) && (message!=null) && (args.length>0)){
			message = MessageFormat.format(message, args);
		}

		return message;
	}
}
