package es.ieci.tecdoc.fwktd.sir.core.types;

import es.ieci.tecdoc.fwktd.core.enums.StringValuedEnum;

/**
 * Enumerados para las constantes de validez de documentos.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class ValidezDocumentoEnum extends StringValuedEnum {

	private static final long serialVersionUID = -6970050482974754452L;

	private static final String COPIA_VALUE = "01";
	private static final String COPIA_COMPULSADA_VALUE = "02";
	private static final String COPIA_ORIGINAL_VALUE = "03";
	private static final String ORIGINAL_VALUE = "04";

	private static final String COPIA_STRING = "Copia";
	private static final String COPIA_COMPULSADA_STRING = "Copia compulsada";
	private static final String COPIA_ORIGINAL_STRING = "Copia original";
	private static final String ORIGINAL_STRING = "Original";

	/**
	 * COPIA
	 */
	public static final ValidezDocumentoEnum COPIA = new ValidezDocumentoEnum(
			COPIA_STRING, COPIA_VALUE);

	/**
	 * COPIA_COMPULSADA.
	 */
	public static final ValidezDocumentoEnum COPIA_COMPULSADA = new ValidezDocumentoEnum(
			COPIA_COMPULSADA_STRING, COPIA_COMPULSADA_VALUE);

	/**
	 * COPIA_ORIGINAL.
	 */
	public static final ValidezDocumentoEnum COPIA_ORIGINAL = new ValidezDocumentoEnum(
			COPIA_ORIGINAL_STRING, COPIA_ORIGINAL_VALUE);

	/**
	 * ORIGINAL.
	 */
	public static final ValidezDocumentoEnum ORIGINAL = new ValidezDocumentoEnum(
			ORIGINAL_STRING, ORIGINAL_VALUE);

	/**
	 * Constructor.
	 *
	 * @param name
	 *            Nombre del enumerado.
	 * @param value
	 *            Valor del enumerado.
	 */
	protected ValidezDocumentoEnum(String name, String value) {
		super(name, value);
	}

	/**
	 * Obtiene la constante asociada al valor.
	 * @param value Valor de la constante
	 * @return Constante.
	 */
	public static ValidezDocumentoEnum getValidezDocumento(String value) {
		return (ValidezDocumentoEnum) StringValuedEnum.getEnum(ValidezDocumentoEnum.class, value);
	}
}
