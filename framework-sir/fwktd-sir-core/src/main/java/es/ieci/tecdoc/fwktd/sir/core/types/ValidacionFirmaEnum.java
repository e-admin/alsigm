package es.ieci.tecdoc.fwktd.sir.core.types;

import org.apache.commons.lang.enums.EnumUtils;
import org.apache.commons.lang.enums.ValuedEnum;

/**
 * Enumerados para las constantes de validez de una firma digital.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class ValidacionFirmaEnum extends ValuedEnum {

	private static final long serialVersionUID = 5745032921758948050L;

	private static final int SIN_FIRMA_VALUE = 0;
	private static final int FIRMA_VALIDA_VALUE = 1;
	private static final int FIRMA_INVALIDA_VALUE = 2;
	private static final int FIRMA_EMBEBIDA_VALUE = 3;

	private static final String SIN_FIRMA_STRING = "No hay firma digital";
	private static final String FIRMA_VALIDA_STRING = "Firma digital válida";
	private static final String FIRMA_INVALIDA_STRING = "Firma digital inválida";
	private static final String FIRMA_EMBEBIDA_STRING = "Firma digital embebida";

	/**
	 * No hay firma digital
	 */
	public static final ValidacionFirmaEnum SIN_FIRMA = new ValidacionFirmaEnum(
			SIN_FIRMA_STRING, SIN_FIRMA_VALUE);

	/**
	 * Firma digital válida
	 */
	public static final ValidacionFirmaEnum FIRMA_VALIDA = new ValidacionFirmaEnum(
			FIRMA_VALIDA_STRING, FIRMA_VALIDA_VALUE);

	/**
	 * Firma digital inválida
	 */
	public static final ValidacionFirmaEnum FIRMA_INVALIDA = new ValidacionFirmaEnum(
			FIRMA_INVALIDA_STRING, FIRMA_INVALIDA_VALUE);

	/**
	 * Firma digital embebida
	 */
	public static final ValidacionFirmaEnum FIRMA_EMBEBIDA = new ValidacionFirmaEnum(
			FIRMA_EMBEBIDA_STRING, FIRMA_EMBEBIDA_VALUE);

	/**
	 * Constructor.
	 *
	 * @param name
	 *            Nombre del enumerado.
	 * @param value
	 *            Valor del enumerado.
	 */
	protected ValidacionFirmaEnum(String name, int value) {
		super(name, value);
	}

	/**
	 * Obtiene la constante asociada al valor.
	 * @param value Valor de la constante
	 * @return Constante.
	 */
	public static ValidacionFirmaEnum getValidacionFirma(int value) {
		return (ValidacionFirmaEnum) EnumUtils.getEnum(ValidacionFirmaEnum.class, value);
	}
}
