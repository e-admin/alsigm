package es.ieci.tecdoc.fwktd.sir.api.types;

import org.apache.commons.lang.enums.ValuedEnum;

/**
 * Enumerados para las constantes de bandejas de ficheros de intercambio.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class BandejaEnum extends ValuedEnum {

	private static final long serialVersionUID = 4521369915323267855L;

	private static final int ENVIADOS_VALUE = 0;
	private static final int RECIBIDOS_VALUE = 1;

	private static final String ENVIADOS_STRING = "ENVIADOS";
	private static final String RECIBIDOS_STRING = "RECIBIDOS";

	/**
	 * ENVIADOS.
	 */
	public static final BandejaEnum ENVIADOS = new BandejaEnum(
			ENVIADOS_STRING, ENVIADOS_VALUE);

	/**
	 * RECIBIDOS.
	 */
	public static final BandejaEnum RECIBIDOS = new BandejaEnum(
			RECIBIDOS_STRING, RECIBIDOS_VALUE);


	/**
	 * Constructor.
	 *
	 * @param name
	 *            Nombre del enumerado.
	 * @param value
	 *            Valor del enumerado.
	 */
	protected BandejaEnum(String name, int value) {
		super(name, value);
	}

}
