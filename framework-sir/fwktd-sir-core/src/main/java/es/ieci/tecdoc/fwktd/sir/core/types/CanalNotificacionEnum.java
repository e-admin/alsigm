package es.ieci.tecdoc.fwktd.sir.core.types;

import es.ieci.tecdoc.fwktd.core.enums.StringValuedEnum;

/**
 * Enumerados para las constantes para los canales de preferencia de
 * notificación.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class CanalNotificacionEnum extends StringValuedEnum {

	private static final long serialVersionUID = 510782789331581437L;

	private static final String DIRECCION_POSTAL_VALUE = "01";
	private static final String DIRECCION_ELECTRONICA_HABILITADA_VALUE = "02";
	private static final String COMPARECENCIA_ELECTRONICA_VALUE = "03";

	private static final String DIRECCION_POSTAL_STRING = "Dirección Postal";
	private static final String DIRECCION_ELECTRONICA_HABILITADA_STRING = "Dirección electrónica habilitada";
	private static final String COMPARECENCIA_ELECTRONICA_STRING = "Comparecencia electrónica";

	/**
	 * Dirección postal.
	 */
	public static final CanalNotificacionEnum DIRECCION_POSTAL = new CanalNotificacionEnum(DIRECCION_POSTAL_STRING,
			DIRECCION_POSTAL_VALUE);

	/**
	 * Dirección electrónica habilitada.
	 */
	public static final CanalNotificacionEnum DIRECCION_ELECTRONICA_HABILITADA = new CanalNotificacionEnum(
			DIRECCION_ELECTRONICA_HABILITADA_STRING, DIRECCION_ELECTRONICA_HABILITADA_VALUE);

	/**
	 * Comparecencia electrónica.
	 */
	public static final CanalNotificacionEnum COMPARECENCIA_ELECTRONICA = new CanalNotificacionEnum(
			COMPARECENCIA_ELECTRONICA_STRING, COMPARECENCIA_ELECTRONICA_VALUE);

	/**
	 * Constructor.
	 *
	 * @param name
	 *            Nombre del enumerado.
	 * @param value
	 *            Valor del enumerado.
	 */
	protected CanalNotificacionEnum(String name, String value) {
		super(name, value);
	}

	/**
	 * Obtiene la constante asociada al valor.
	 * @param value Valor de la constante
	 * @return Constante.
	 */
	public static CanalNotificacionEnum getCanalNotificacion(String value) {
		return (CanalNotificacionEnum) StringValuedEnum.getEnum(CanalNotificacionEnum.class, value);
	}
}
