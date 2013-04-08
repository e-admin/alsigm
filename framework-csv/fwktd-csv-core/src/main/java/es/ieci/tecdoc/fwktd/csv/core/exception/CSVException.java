package es.ieci.tecdoc.fwktd.csv.core.exception;

import es.ieci.tecdoc.fwktd.core.exception.ApplicationException;

/**
 * Excepción base del módulo de gestión de CSVs.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class CSVException extends ApplicationException {

	private static final long serialVersionUID = 1024111867043660202L;

	/**
	 * Constructor de la clase.
	 *
	 * @param messageId
	 *            clave del mensaje en el fichero de recursos con los mensajes
	 *            internacionalizados para las excepciones
	 * @param params
	 *            parámetros para componer el mensaje con el del fichero de
	 *            recursos
	 * @param defaultMessage
	 *            mensaje a mostrar en caso de que no encuentre la clave del
	 *            mensaje
	 */
	public CSVException(String messageId, String[] params, String defaultMessage) {
		super(messageId, params, defaultMessage);
	}

	/**
	 * Obtiene el id del mensaje por defecto. Se utiliza para indicar la clave,
	 * en un fichero de recursos, de la que obtener el mensaje
	 * internacionalizado de la excepción.
	 *
	 * @return el id del mensaje internacionalizado de la excepción
	 */
	@Override
	public String getDefaultMessageId() {
		return "error.csv";
	}

}
