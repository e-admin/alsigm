package es.ieci.tecdoc.fwktd.sir.core.types;

import es.ieci.tecdoc.fwktd.core.enums.StringValuedEnum;

/**
 * Enumerados para las constantes de tipos de documentos.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class TipoDocumentoEnum extends StringValuedEnum {

	private static final long serialVersionUID = 6641385403533452576L;

	private static final String FORMULARIO_VALUE = "01";
	private static final String DOCUMENTO_ADJUNTO_VALUE = "02";
	private static final String FICHERO_TECNICO_INTERNO_VALUE = "03";

	private static final String FORMULARIO_STRING = "Formulario";
	private static final String DOCUMENTO_ADJUNTO_STRING = "Documento adjunto al formulario";
	private static final String FICHERO_TECNICO_INTERNO_STRING = "Fichero técnico interno";

	/**
	 * FORMULARIO
	 */
	public static final TipoDocumentoEnum FORMULARIO = new TipoDocumentoEnum(
			FORMULARIO_STRING, FORMULARIO_VALUE);

	/**
	 * DOCUMENTO_ADJUNTO.
	 */
	public static final TipoDocumentoEnum DOCUMENTO_ADJUNTO = new TipoDocumentoEnum(
			DOCUMENTO_ADJUNTO_STRING, DOCUMENTO_ADJUNTO_VALUE);

	/**
	 * FICHERO_TECNICO_INTERNO.
	 */
	public static final TipoDocumentoEnum FICHERO_TECNICO_INTERNO = new TipoDocumentoEnum(
			FICHERO_TECNICO_INTERNO_STRING, FICHERO_TECNICO_INTERNO_VALUE);

	/**
	 * Constructor.
	 *
	 * @param name
	 *            Nombre del enumerado.
	 * @param value
	 *            Valor del enumerado.
	 */
	protected TipoDocumentoEnum(String name, String value) {
		super(name, value);
	}

	/**
	 * Obtiene la constante asociada al valor.
	 * @param value Valor de la constante
	 * @return Constante.
	 */
	public static TipoDocumentoEnum getTipoDocumento(String value) {
		return (TipoDocumentoEnum) StringValuedEnum.getEnum(TipoDocumentoEnum.class, value);
	}
}
