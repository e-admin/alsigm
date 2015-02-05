package es.ieci.tecdoc.fwktd.sir.core.types;

import es.ieci.tecdoc.fwktd.core.enums.StringValuedEnum;

/**
 * Enumerados para las constantes de los tipos de documentos de identificación
 * de interesados y representantes.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class TipoDocumentoIdentificacionEnum extends StringValuedEnum {

	private static final long serialVersionUID = -7451760723494525790L;

	private static final String NIF_VALUE = "N";
	private static final String CIF_VALUE = "C";
	private static final String PASAPORTE_VALUE = "P";
	private static final String NIE_VALUE = "E";
	private static final String OTROS_PERSONA_FISICA_VALUE = "X";
	private static final String CODIGO_ORIGEN_VALUE = "O";

	private static final String NIF_STRING = "NIF";
	private static final String CIF_STRING = "CIF";
	private static final String PASAPORTE_STRING = "Pasaporte";
	private static final String NIE_STRING = "NIE";
	private static final String OTROS_PERSONA_FISICA_STRING = "Otros de persona física";
	private static final String CODIGO_ORIGEN_STRING = "Código de origen";

	/**
	 * NIF
	 */
	public static final TipoDocumentoIdentificacionEnum NIF = new TipoDocumentoIdentificacionEnum(NIF_STRING,
			NIF_VALUE);

	/**
	 * CIF.
	 */
	public static final TipoDocumentoIdentificacionEnum CIF = new TipoDocumentoIdentificacionEnum(
			CIF_STRING, CIF_VALUE);

	/**
	 * Pasaporte.
	 */
	public static final TipoDocumentoIdentificacionEnum PASAPORTE = new TipoDocumentoIdentificacionEnum(
			PASAPORTE_STRING, PASAPORTE_VALUE);

	/**
	 * NIE.
	 */
	public static final TipoDocumentoIdentificacionEnum NIE = new TipoDocumentoIdentificacionEnum(
			NIE_STRING, NIE_VALUE);

	/**
	 * Otros de persona física.
	 */
	public static final TipoDocumentoIdentificacionEnum OTROS_PERSONA_FISICA = new TipoDocumentoIdentificacionEnum(
			OTROS_PERSONA_FISICA_STRING, OTROS_PERSONA_FISICA_VALUE);

	/**
	 * Código de origen.
	 */
	public static final TipoDocumentoIdentificacionEnum CODIGO_ORIGEN = new TipoDocumentoIdentificacionEnum(
			CODIGO_ORIGEN_STRING, CODIGO_ORIGEN_VALUE);


	/**
	 * Constructor.
	 *
	 * @param name
	 *            Nombre del enumerado.
	 * @param value
	 *            Valor del enumerado.
	 */
	protected TipoDocumentoIdentificacionEnum(String name, String value) {
		super(name, value);
	}

	/**
	 * Obtiene la constante asociada al valor.
	 * @param value Valor de la constante
	 * @return Constante.
	 */
	public static TipoDocumentoIdentificacionEnum getTipoDocumentoIdentificacion(String value) {
		return (TipoDocumentoIdentificacionEnum) StringValuedEnum.getEnum(TipoDocumentoIdentificacionEnum.class, value);
	}
}
