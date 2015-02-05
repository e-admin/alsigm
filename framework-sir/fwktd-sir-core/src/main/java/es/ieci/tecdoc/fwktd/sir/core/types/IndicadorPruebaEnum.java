package es.ieci.tecdoc.fwktd.sir.core.types;

import es.ieci.tecdoc.fwktd.core.enums.StringValuedEnum;

/**
 * Enumerados para las constantes de los indicadores de prueba.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class IndicadorPruebaEnum extends StringValuedEnum {

	private static final long serialVersionUID = -8306937534950169600L;

	private static final String NORMAL_VALUE = "0";
	private static final String PRUEBA_VALUE = "1";

	private static final String NORMAL_STRING = "NORMAL";
	private static final String PRUEBA_STRING = "PRUEBA";

	/**
	 * Es un mensaje normal.
	 */
	public static final IndicadorPruebaEnum NORMAL = new IndicadorPruebaEnum(
			NORMAL_STRING, NORMAL_VALUE);

	/**
	 * El mensaje es una prueba.
	 */
	public static final IndicadorPruebaEnum PRUEBA = new IndicadorPruebaEnum(
			PRUEBA_STRING, PRUEBA_VALUE);

	/**
	 * Constructor.
	 *
	 * @param name
	 *            Nombre del enumerado.
	 * @param value
	 *            Valor del enumerado.
	 */
	protected IndicadorPruebaEnum(String name, String value) {
		super(name, value);
	}

	/**
	 * Obtiene la constante asociada al valor.
	 * @param value Valor de la constante
	 * @return Constante.
	 */
	public static IndicadorPruebaEnum getIndicadorPrueba(String value) {
		return (IndicadorPruebaEnum) StringValuedEnum.getEnum(IndicadorPruebaEnum.class, value);
	}
}
