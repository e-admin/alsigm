package es.ieci.tecdoc.archidoc.organization.messages;

import java.text.MessageFormat;

import es.ieci.tecdoc.fwktd.core.messages.ApplicationMessages;

/**
 * Clase base tanto para mensajes como para errores
 * @author Iecisa
 * @version $Revision: 6586 $
 *
 */
public final class OrganizationMessages{

	/**
	 * Constructor privado
	 */
	private OrganizationMessages(){

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
		String message = msgCode + ": " +ApplicationMessages.getString(bundle, msgCode);

		if ((args!=null) && (message!=null) && (args.length>0)){
			message = MessageFormat.format(message, args);
		}

		return message;
	}
}
