package es.ieci.tecdoc.fwktd.sir.api.types;

import es.ieci.tecdoc.fwktd.core.enums.StringValuedEnum;

/**
 * Enumerados para las constantes de los tipos de mensajes.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class TipoMensajeEnum extends StringValuedEnum {

	private static final long serialVersionUID = -7663807248849317682L;

	private static final String ACK_VALUE = "01";
	private static final String ERROR_VALUE = "02";
	private static final String CONFIRMACION_VALUE = "03";

	private static final String ACK_STRING = "ACK";
	private static final String ERROR_STRING = "ERROR";
	private static final String CONFIRMACION_STRING = "CONFIRMACION";

	/**
	 * Mensaje de aceptación (ACK).
	 */
	public static final TipoMensajeEnum ACK = new TipoMensajeEnum(ACK_STRING,
			ACK_VALUE);

	/**
	 * Mensaje de error.
	 */
	public static final TipoMensajeEnum ERROR = new TipoMensajeEnum(
			ERROR_STRING, ERROR_VALUE);

	/**
	 * Mensaje de confirmación.
	 */
	public static final TipoMensajeEnum CONFIRMACION = new TipoMensajeEnum(
			CONFIRMACION_STRING, CONFIRMACION_VALUE);

	/**
	 * Constructor.
	 *
	 * @param name
	 *            Nombre del enumerado.
	 * @param value
	 *            Valor del enumerado.
	 */
	protected TipoMensajeEnum(String name, String value) {
		super(name, value);
	}

	/**
	 * Obtiene la constante asociada al valor.
	 * @param value Valor de la constante
	 * @return Constante.
	 */
	public static TipoMensajeEnum getTipoMensaje(String value) {
		return (TipoMensajeEnum) StringValuedEnum.getEnum(TipoMensajeEnum.class, value);
	}
}
