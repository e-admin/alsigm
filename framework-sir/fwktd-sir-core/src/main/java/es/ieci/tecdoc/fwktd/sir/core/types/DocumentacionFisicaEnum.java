package es.ieci.tecdoc.fwktd.sir.core.types;

import es.ieci.tecdoc.fwktd.core.enums.StringValuedEnum;

/**
 * Enumerados para las constantes de documentación física.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class DocumentacionFisicaEnum extends StringValuedEnum {

	private static final long serialVersionUID = 5843076203308644656L;

	private static final String DOCUMENTACION_FISICA_REQUERIDA_VALUE = "1";
	private static final String DOCUMENTACION_FISICA_COMPLEMENTARIA_VALUE = "2";
	private static final String SIN_DOCUMENTACION_FISICA_VALUE = "3";

	private static final String DOCUMENTACION_FISICA_REQUERIDA_STRING = "Acompaña documentación física requerida";
	private static final String DOCUMENTACION_FISICA_COMPLEMENTARIA_STRING = "Acompaña documentación física complementaria";
	private static final String SIN_DOCUMENTACION_FISICA_STRING = "No acompaña documentación física";

	/**
	 * DOCUMENTACION_FISICA_REQUERIDA
	 */
	public static final DocumentacionFisicaEnum DOCUMENTACION_FISICA_REQUERIDA = new DocumentacionFisicaEnum(
			DOCUMENTACION_FISICA_REQUERIDA_STRING,
			DOCUMENTACION_FISICA_REQUERIDA_VALUE);

	/**
	 * DOCUMENTACION_FISICA_COMPLEMENTARIA.
	 */
	public static final DocumentacionFisicaEnum DOCUMENTACION_FISICA_COMPLEMENTARIA = new DocumentacionFisicaEnum(
			DOCUMENTACION_FISICA_COMPLEMENTARIA_STRING,
			DOCUMENTACION_FISICA_COMPLEMENTARIA_VALUE);

	/**
	 * SIN_DOCUMENTACION_FISICA.
	 */
	public static final DocumentacionFisicaEnum SIN_DOCUMENTACION_FISICA = new DocumentacionFisicaEnum(
			SIN_DOCUMENTACION_FISICA_STRING, SIN_DOCUMENTACION_FISICA_VALUE);

	/**
	 * Constructor.
	 *
	 * @param name
	 *            Nombre del enumerado.
	 * @param value
	 *            Valor del enumerado.
	 */
	protected DocumentacionFisicaEnum(String name, String value) {
		super(name, value);
	}

	/**
	 * Obtiene la constante asociada al valor.
	 * @param value Valor de la constante
	 * @return Constante.
	 */
	public static DocumentacionFisicaEnum getDocumentacionFisica(String value) {
		return (DocumentacionFisicaEnum) StringValuedEnum.getEnum(DocumentacionFisicaEnum.class, value);
	}
}
