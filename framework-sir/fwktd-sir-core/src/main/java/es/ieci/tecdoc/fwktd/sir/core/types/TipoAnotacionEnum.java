package es.ieci.tecdoc.fwktd.sir.core.types;

import es.ieci.tecdoc.fwktd.core.enums.StringValuedEnum;

/**
 * Enumerados para las constantes de tipos de anotaciones.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class TipoAnotacionEnum extends StringValuedEnum {

	private static final long serialVersionUID = -3247740417854603857L;

	private static final String PENDIENTE_VALUE = "01";
	private static final String ENVIO_VALUE = "02";
	private static final String REENVIO_VALUE = "03";
	private static final String RECHAZO_VALUE = "04";

	private static final String PENDIENTE_STRING = "Pendiente";
	private static final String ENVIO_STRING = "Envío";
	private static final String REENVIO_STRING = "Reenvío";
	private static final String RECHAZO_STRING = "Rechazo";


	/**
	 * PENDIENTE
	 */
	public static final TipoAnotacionEnum PENDIENTE = new TipoAnotacionEnum(
			PENDIENTE_STRING, PENDIENTE_VALUE);

	/**
	 * ENVIO.
	 */
	public static final TipoAnotacionEnum ENVIO = new TipoAnotacionEnum(
			ENVIO_STRING, ENVIO_VALUE);

	/**
	 * REENVIO.
	 */
	public static final TipoAnotacionEnum REENVIO = new TipoAnotacionEnum(
			REENVIO_STRING, REENVIO_VALUE);

	/**
	 * RECHAZO.
	 */
	public static final TipoAnotacionEnum RECHAZO = new TipoAnotacionEnum(
			RECHAZO_STRING, RECHAZO_VALUE);

	/**
	 * Constructor.
	 *
	 * @param name
	 *            Nombre del enumerado.
	 * @param value
	 *            Valor del enumerado.
	 */
	protected TipoAnotacionEnum(String name, String value) {
		super(name, value);
	}

	/**
	 * Obtiene la constante asociada al valor.
	 * @param value Valor de la constante
	 * @return Constante.
	 */
	public static TipoAnotacionEnum getTipoAnotacion(String value) {
		return (TipoAnotacionEnum) StringValuedEnum.getEnum(TipoAnotacionEnum.class, value);
	}
}
