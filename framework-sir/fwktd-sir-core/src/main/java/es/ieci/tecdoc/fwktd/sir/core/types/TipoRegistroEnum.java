package es.ieci.tecdoc.fwktd.sir.core.types;

import es.ieci.tecdoc.fwktd.core.enums.StringValuedEnum;

/**
 * Enumerados para las constantes de tipos de registro.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class TipoRegistroEnum extends StringValuedEnum {

	private static final long serialVersionUID = -7551983248599173392L;

	private static final String ENTRADA_VALUE = "0";
	private static final String SALIDA_VALUE = "1";

	private static final String ENTRADA_STRING = "Registro de Entrada";
	private static final String SALIDA_STRING = "Registro de Salida";

	/**
	 * Registro de entrada.
	 */
	public static final TipoRegistroEnum ENTRADA = new TipoRegistroEnum(
			ENTRADA_STRING, ENTRADA_VALUE);

	/**
	 * Registro de salida.
	 */
	public static final TipoRegistroEnum SALIDA = new TipoRegistroEnum(
			SALIDA_STRING, SALIDA_VALUE);

	/**
	 * Constructor.
	 *
	 * @param name
	 *            Nombre del enumerado.
	 * @param value
	 *            Valor del enumerado.
	 */
	protected TipoRegistroEnum(String name, String value) {
		super(name, value);
	}

	/**
	 * Obtiene la constante asociada al valor.
	 * @param value Valor de la constante
	 * @return Constante.
	 */
	public static TipoRegistroEnum getTipoRegistro(String value) {
		return (TipoRegistroEnum) StringValuedEnum.getEnum(TipoRegistroEnum.class, value);
	}
}
