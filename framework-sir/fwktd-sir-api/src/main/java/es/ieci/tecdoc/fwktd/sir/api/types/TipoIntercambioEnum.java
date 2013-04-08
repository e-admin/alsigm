package es.ieci.tecdoc.fwktd.sir.api.types;

import org.apache.commons.lang.enums.ValuedEnum;

/**
 * Enumerados para las constantes de tipos de intercambio.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class TipoIntercambioEnum extends ValuedEnum {

	private static final long serialVersionUID = -7551983248599173392L;

	private static final int ENVIADO_VALUE = 0;
	private static final int RECIBIDO_VALUE = 1;

	private static final String ENVIADO_STRING = "Enviado";
	private static final String RECIBIDO_STRING = "Recibido";

	/**
	 * Enviado.
	 */
	public static final TipoIntercambioEnum ENVIADO = new TipoIntercambioEnum(
			ENVIADO_STRING, ENVIADO_VALUE);

	/**
	 * Recibido.
	 */
	public static final TipoIntercambioEnum RECIBIDO = new TipoIntercambioEnum(
			RECIBIDO_STRING, RECIBIDO_VALUE);

	/**
	 * Constructor.
	 *
	 * @param name
	 *            Nombre del enumerado.
	 * @param value
	 *            Valor del enumerado.
	 */
	protected TipoIntercambioEnum(String name, int value) {
		super(name, value);
	}
}
