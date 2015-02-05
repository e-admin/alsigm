package es.ieci.tecdoc.fwktd.sir.core.types;

import org.apache.commons.lang.enums.EnumUtils;
import org.apache.commons.lang.enums.ValuedEnum;

/**
 * Enumerados para las constantes de validez de un certificado.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class ValidacionCertificadoEnum extends ValuedEnum {

	private static final long serialVersionUID = 5745032921758948050L;

	private static final int SIN_CERTIFICADO_VALUE = 0;
	private static final int CERTIFICADO_VALIDO_VALUE = 1;
	private static final int CERTIFICADO_NO_X509_VALUE = 2;
	private static final int CERTIFICADO_EXPIRADO_VALUE = 3;
	private static final int CERTIFICADO_NO_VALIDO_AUN_VALUE = 4;
	private static final int CERTIFICADO_REVOCADO_VALUE = 5;
	private static final int CERTIFICADO_ERRONEO_VALUE = 6;

	private static final String SIN_CERTIFICADO_STRING = "No hay certificado";
	private static final String CERTIFICADO_VALIDO_STRING = "Certificado válido";
	private static final String CERTIFICADO_NO_X509_STRING = "Certificado no es X509";
	private static final String CERTIFICADO_EXPIRADO_STRING = "Certificado expirado";
	private static final String CERTIFICADO_NO_VALIDO_AUN_STRING = "Certificado no válido aún";
	private static final String CERTIFICADO_REVOCADO_STRING = "Certificado revocado";
	private static final String CERTIFICADO_ERRONEO_STRING = "Certificado erróneo";

	/**
	 * No hay certificado
	 */
	public static final ValidacionCertificadoEnum SIN_CERTIFICADO = new ValidacionCertificadoEnum(
			SIN_CERTIFICADO_STRING, SIN_CERTIFICADO_VALUE);

	/**
	 * Certificado válido
	 */
	public static final ValidacionCertificadoEnum CERTIFICADO_VALIDO = new ValidacionCertificadoEnum(
			CERTIFICADO_VALIDO_STRING, CERTIFICADO_VALIDO_VALUE);

	/**
	 * Certificado no X509
	 */
	public static final ValidacionCertificadoEnum CERTIFICADO_NO_X509 = new ValidacionCertificadoEnum(
			CERTIFICADO_NO_X509_STRING, CERTIFICADO_NO_X509_VALUE);

	/**
	 * Certificado expirado
	 */
	public static final ValidacionCertificadoEnum CERTIFICADO_EXPIRADO = new ValidacionCertificadoEnum(
			CERTIFICADO_EXPIRADO_STRING, CERTIFICADO_EXPIRADO_VALUE);

	/**
	 * Certificado no válido aún
	 */
	public static final ValidacionCertificadoEnum CERTIFICADO_NO_VALIDO_AUN = new ValidacionCertificadoEnum(
			CERTIFICADO_NO_VALIDO_AUN_STRING, CERTIFICADO_NO_VALIDO_AUN_VALUE);

	/**
	 * Certificado revocado
	 */
	public static final ValidacionCertificadoEnum CERTIFICADO_REVOCADO = new ValidacionCertificadoEnum(
			CERTIFICADO_REVOCADO_STRING, CERTIFICADO_REVOCADO_VALUE);

	/**
	 * Certificado erróneo
	 */
	public static final ValidacionCertificadoEnum CERTIFICADO_ERRONEO = new ValidacionCertificadoEnum(
			CERTIFICADO_ERRONEO_STRING, CERTIFICADO_ERRONEO_VALUE);

	/**
	 * Constructor.
	 *
	 * @param name
	 *            Nombre del enumerado.
	 * @param value
	 *            Valor del enumerado.
	 */
	protected ValidacionCertificadoEnum(String name, int value) {
		super(name, value);
	}

	/**
	 * Obtiene la constante asociada al valor.
	 * @param value Valor de la constante
	 * @return Constante.
	 */
	public static ValidacionCertificadoEnum getValidacionCertificado(int value) {
		return (ValidacionCertificadoEnum) EnumUtils.getEnum(ValidacionCertificadoEnum.class, value);
	}
}
