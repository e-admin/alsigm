package es.ieci.tecdoc.fwktd.sir.core.types;

import es.ieci.tecdoc.fwktd.core.enums.StringValuedEnum;

/**
 * Enumerados para las constantes de los tipos de transporte.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class TipoTransporteEnum extends StringValuedEnum {

	private static final long serialVersionUID = 8931765285973718104L;

	private static final String SERVICIO_MENSAJEROS_VALUE = "01";
	private static final String CORREO_POSTAL_VALUE = "02";
	private static final String CORREO_POSTAL_CERTIFICADO_VALUE = "03";
	private static final String BUROFAX_VALUE = "04";
	private static final String EN_MANO_VALUE = "05";
	private static final String FAX_VALUE = "06";
	private static final String OTROS_VALUE = "07";

	private static final String SERVICIO_MENSAJEROS_STRING = "Servicio de mensajeros";
	private static final String CORREO_POSTAL_STRING = "Correo postal";
	private static final String CORREO_POSTAL_CERTIFICADO_STRING = "Correo postal certificado";
	private static final String BUROFAX_STRING = "Burofax";
	private static final String EN_MANO_STRING = "En mano";
	private static final String FAX_STRING = "Fax";
	private static final String OTROS_STRING = "Otros";

	/**
	 * Servicio de mensajeros.
	 */
	public static final TipoTransporteEnum SERVICIO_MENSAJEROS = new TipoTransporteEnum(
			SERVICIO_MENSAJEROS_STRING, SERVICIO_MENSAJEROS_VALUE);

	/**
	 * Correo postal.
	 */
	public static final TipoTransporteEnum CORREO_POSTAL = new TipoTransporteEnum(
			CORREO_POSTAL_STRING, CORREO_POSTAL_VALUE);

	/**
	 * Correo postal certificado.
	 */
	public static final TipoTransporteEnum CORREO_POSTAL_CERTIFICADO = new TipoTransporteEnum(
			CORREO_POSTAL_CERTIFICADO_STRING, CORREO_POSTAL_CERTIFICADO_VALUE);

	/**
	 * Burofax.
	 */
	public static final TipoTransporteEnum BUROFAX = new TipoTransporteEnum(
			BUROFAX_STRING, BUROFAX_VALUE);

	/**
	 * En mano.
	 */
	public static final TipoTransporteEnum EN_MANO = new TipoTransporteEnum(
			EN_MANO_STRING, EN_MANO_VALUE);

	/**
	 * Fax.
	 */
	public static final TipoTransporteEnum FAX = new TipoTransporteEnum(
			FAX_STRING, FAX_VALUE);

	/**
	 * Otros.
	 */
	public static final TipoTransporteEnum OTROS = new TipoTransporteEnum(
			OTROS_STRING, OTROS_VALUE);

	/**
	 * Constructor.
	 *
	 * @param name
	 *            Nombre del enumerado.
	 * @param value
	 *            Valor del enumerado.
	 */
	protected TipoTransporteEnum(String name, String value) {
		super(name, value);
	}

	/**
	 * Obtiene la constante asociada al valor.
	 * @param value Valor de la constante
	 * @return Constante.
	 */
	public static TipoTransporteEnum getTipoTransporte(String value) {
		return (TipoTransporteEnum) StringValuedEnum.getEnum(TipoTransporteEnum.class, value);
	}
}
