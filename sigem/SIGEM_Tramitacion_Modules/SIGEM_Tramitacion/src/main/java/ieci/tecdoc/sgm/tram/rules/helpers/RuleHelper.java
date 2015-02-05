package ieci.tecdoc.sgm.tram.rules.helpers;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.rule.IRuleContext;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.context.StateContext;
import ieci.tdw.ispac.ispaclib.messages.MessagesFormatter;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class RuleHelper {

	/** Nombre del fichero de recursos. */
	private static String BUNDLE_NAME = "ieci.tecdoc.sgm.tram.rules.RuleMessages";

	/**
	 * Devuelve el nombre del archivo de recursos que contiene los mensajes.
	 *
	 * @return El nombre del archivo de recursos mencionado.
	 */
	private static String getMessagesFile() {
		return BUNDLE_NAME;
	}

	/**
	 * Obtiene un mensaje del archivo de recursos en el idioma especificiado.
	 *
	 * @param locale Código de localización con el idioma especificado
	 * @param key Clave a buscar en el archivo de recursos
	 * @return El mensaje en el idoma especificado
	 */
	public static String getMessage(Locale locale, String key) {

		try {
			return ResourceBundle.getBundle(getMessagesFile(), locale).getString(key);
		}
		catch (MissingResourceException mre) {
			return key;
		}
	}

	/**
	 * Obtiene un mensaje del archivo de recursos en el idioma especificiado.
	 *
	 * @param locale Código de localización con el idioma especificado
	 * @param key Clave a buscar en el archivo de recursos
	 * @param params Parámetros para sustituir en el texto
	 * @return El mensaje en el idoma especificado
	 */
	public static String getMessage(Locale locale, String key, Object [] params) {

		try {
			String text = getMessage(locale, key);
			if ((text != null) &&
				(text.length() > 0) &&
				(params != null)) {

				// Establecer los parámetros en el mensaje
				text = MessagesFormatter.format(text, params);
			}

			return text;
		}
		catch (MissingResourceException e) {
			return key;
		}
	}

	/**
	 * Establece un mensaje en el contexto de la regla
	 * en función del contexto de tramitación.
	 *
	 * @param rulectx Contexto de la regla.
	 * @param stateClass Estado a comparar con el contexto de tramitación.
	 * @param stateKeyMessage Clave del mensaje si el estado coincide con el contexto de tramitación.
	 * @param stateArgs Argumentos del mensaje si el estado coincide con el contexto de tramitación.
	 * @param noStateKeyMessage Clave del mensaje si el estado no coincide con el contexto de tramitación.
	 * @param noStateArgs Argumentos del mensaje si el estado no coincide con el contexto de tramitación.
	 * @throws ISPACException Si se produce algún error.
	 */
	public static void setInfoMessage(IRuleContext rulectx,
									  Class stateClass,
									  String stateKeyMessage,
									  String[] stateArgs,
									  String noStateKeyMessage,
									  String[] noStateArgs) throws ISPACException {

		IClientContext cct = rulectx.getClientContext();

		// Mensaje en función del contexto de tramitación
		StateContext stateContext = cct.getStateContext();
		if (stateContext != null) {

			IManagerAPI managerAPI = ManagerAPIFactory.getInstance().getManagerAPI((ClientContext) cct);

			IState currentState = managerAPI.currentState(stateContext.getStateTicket());
			if (currentState.getClass().equals(stateClass)) {

				rulectx.setInfoMessage(RuleHelper.getMessage(cct.getLocale(), stateKeyMessage, stateArgs));
				return;
			}
		}

		rulectx.setInfoMessage(RuleHelper.getMessage(cct.getLocale(), noStateKeyMessage, noStateArgs));
	}

}
