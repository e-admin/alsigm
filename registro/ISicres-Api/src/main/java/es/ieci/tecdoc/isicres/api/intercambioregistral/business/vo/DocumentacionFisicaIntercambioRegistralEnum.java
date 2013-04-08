package es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo;

import es.ieci.tecdoc.fwktd.core.enums.StringValuedEnum;

public class DocumentacionFisicaIntercambioRegistralEnum extends StringValuedEnum{

	private static final String DOCUMENTACION_FISICA_REQUERIDA_VALUE = "1";
	private static final String DOCUMENTACION_FISICA_COMPLEMENTARIA_VALUE = "2";
	private static final String SIN_DOCUMENTACION_FISICA_VALUE = "3";

	private static final String DOCUMENTACION_FISICA_REQUERIDA_STRING = "Requerida";
	private static final String DOCUMENTACION_FISICA_COMPLEMENTARIA_STRING = "Complementaria";
	private static final String SIN_DOCUMENTACION_FISICA_STRING = "No tiene";

	protected DocumentacionFisicaIntercambioRegistralEnum(String name, String value) {
		super(name, value);
	}
	
	public static final DocumentacionFisicaIntercambioRegistralEnum DOCUMENTACION_FISICA_REQUERIDA = new DocumentacionFisicaIntercambioRegistralEnum(
			DOCUMENTACION_FISICA_REQUERIDA_STRING,
			DOCUMENTACION_FISICA_REQUERIDA_VALUE);


	public static final DocumentacionFisicaIntercambioRegistralEnum DOCUMENTACION_FISICA_COMPLEMENTARIA = new DocumentacionFisicaIntercambioRegistralEnum(
			DOCUMENTACION_FISICA_COMPLEMENTARIA_STRING,
			DOCUMENTACION_FISICA_COMPLEMENTARIA_VALUE);


	public static final DocumentacionFisicaIntercambioRegistralEnum SIN_DOCUMENTACION_FISICA = new DocumentacionFisicaIntercambioRegistralEnum(
			SIN_DOCUMENTACION_FISICA_STRING, SIN_DOCUMENTACION_FISICA_VALUE);


	/**
	 * Obtiene la constante asociada al valor.
	 * @param value Valor de la constante
	 * @return Constante.
	 */
	public static DocumentacionFisicaIntercambioRegistralEnum getDocumentacionFisica(String value) {
		return (DocumentacionFisicaIntercambioRegistralEnum) StringValuedEnum.getEnum(DocumentacionFisicaIntercambioRegistralEnum.class, value);
	}
	
}
