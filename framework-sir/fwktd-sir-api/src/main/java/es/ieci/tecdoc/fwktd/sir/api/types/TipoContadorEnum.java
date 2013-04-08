package es.ieci.tecdoc.fwktd.sir.api.types;

import org.apache.commons.lang.enums.ValuedEnum;

/**
 * Enumerados para las constantes de los tipos de contadores.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class TipoContadorEnum extends ValuedEnum {

	private static final long serialVersionUID = 4641871717707441104L;

	private static final int IDENTIFICADOR_INTERCAMBIO_VALUE = 1;
	private static final int MENSAJE_VALUE = 2;

	private static final String IDENTIFICADOR_INTERCAMBIO_STRING = "Identificador de intercambio";
	private static final String MENSAJE_STRING = "Mensaje";

	/**
	 * Identificador de intercambio
	 */
	public static final TipoContadorEnum IDENTIFICADOR_INTERCAMBIO = new TipoContadorEnum(
			IDENTIFICADOR_INTERCAMBIO_STRING, IDENTIFICADOR_INTERCAMBIO_VALUE);

	/**
	 * Mensaje.
	 */
	public static final TipoContadorEnum MENSAJE = new TipoContadorEnum(
			MENSAJE_STRING, MENSAJE_VALUE);

	/**
	 * Constructor.
	 *
	 * @param name
	 *            Nombre del enumerado.
	 * @param value
	 *            Valor del enumerado.
	 */
	protected TipoContadorEnum(String name, int value) {
		super(name, value);
	}
}
