package es.ieci.tecdoc.fwktd.sir.api.types;

import org.apache.commons.lang.enums.ValuedEnum;

/**
 * Enumerados para las constantes de tipos de ficheros de intercambio.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class TipoFicheroEnum extends ValuedEnum {

	private static final long serialVersionUID = -6283244085784833137L;

	private static final int DESCONOCIDO_VALUE = 0;
	private static final int FICHERO_DATOS_INTERCAMBIO_VALUE = 1;
	private static final int MENSAJE_VALUE = 2;

	private static final String DESCONOCIDO_STRING = "Desconocido";
	private static final String FICHERO_DATOS_INTERCAMBIO_STRING = "Fichero de datos de intercambio";
	private static final String MENSAJE_STRING = "Mensaje";

	/**
	 * Desconocido.
	 */
	public static final TipoFicheroEnum DESCONOCIDO = new TipoFicheroEnum(
			DESCONOCIDO_STRING, DESCONOCIDO_VALUE);

	/**
	 * Fichero de datos de intercambio.
	 */
	public static final TipoFicheroEnum FICHERO_DATOS_INTERCAMBIO = new TipoFicheroEnum(
			FICHERO_DATOS_INTERCAMBIO_STRING, FICHERO_DATOS_INTERCAMBIO_VALUE);

	/**
	 * Mensaje.
	 */
	public static final TipoFicheroEnum MENSAJE = new TipoFicheroEnum(
			MENSAJE_STRING, MENSAJE_VALUE);

	/**
	 * Constructor.
	 *
	 * @param name
	 *            Nombre del enumerado.
	 * @param value
	 *            Valor del enumerado.
	 */
	protected TipoFicheroEnum(String name, int value) {
		super(name, value);
	}

}
