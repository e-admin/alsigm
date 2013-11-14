package es.ieci.tecdoc.isicres.api.compulsa.justificante.business.manager.impl;

/**
 * @author Iecisa
 * @version $Revision$
 *
 */

public class CompulsaJustificanteKeys {

	public static final String FIELD_TYPE_STRING_CERTIFICATE = "Certificate";
	public static final String FIELD_TYPE_STRING_HASH = "Hash";
	public static final String FIELD_TYPE_STRING_FUNCNAME = "FuncName";
	public static final String FIELD_TYPE_STRING_FILESCAN = "FileScan";
	public static final String FIELD_TYPE_STRING_FILEXADES = "FileXades";
	public static final String FIELD_TYPE_STRING_XADESFORMAT = "XadesFormat";

	public static final int FIELD_TYPE_CERTIFICATE = 1;
	public static final int FIELD_TYPE_HASH = 2;
	public static final int FIELD_TYPE_FUNCNAME = 3;
	public static final int FIELD_TYPE_XADESFORMAT = 4;
	public static final int FIELD_TYPE_FILESCAN = 5;
	public static final int FIELD_TYPE_FILEXADES = 6;

	public static final String SEPARADOR = "/";
	public static final String BARRA = "|";
	public static final String DOSPATH = "\\";
	public static final String PUNTO = ".";
	public static final String GUIONBAJO = "_";

	public static final String ENTIDAD_DEFAULT = "";
	public static final String ENTIDAD_DEFAULT_CODE = "999";

	public static final String DATE_FORMAT_LOCATOR = "yyyy-MM-dd HH:mm:ss.0";
	public static final String DATE_FORMAT_COMPULSA = "dd/MM/yyyy HH:mm:ss";

	public static final String EXTENSION_XADES = "xades";
	public static final String EXTENSION_XSIG = "xsig";

	// Keys de los values especificos
	public static final String KEY_SESSIONID = "SessionID";

	public static final String KEY_XADES_FORMAT = "xadesFormat";
	public static final String KEY_FIRMANTE = "funcName";
	public static final String KEY_CERTIFICATE = "certificado";
	public static final String KEY_HASH = "hash";

	public static final String KEY_PATH_TEMP = "tempPath";
	public static final String KEY_PATH_BEGIN = "beginPath";
	public static final String KEY_PATH_FONDO = "fondoPath";
	public static final String KEY_PATH_DATOS = "datosPath";

	public static final String KEY_MARGEN = "margen";
	public static final String KEY_POSITION_Y = "positionY";
	public static final String KEY_FONT = "font";
	public static final String KEY_ENCODING = "encoding";
	public static final String KEY_FONT_SIZE = "fontSize";
	public static final String KEY_BAND = "band";
	public static final String KEY_BAND_SIZE = "bandSize";

	public static final String KEY_INPUTSTREAM = "inputstream";
	public static final String KEY_OUTPUTSTREAM = "outputstream";

	public static final String I18N_PDF_WATER_MARK_FONT = "pdf.water.mark.font";
	public static final String I18N_PDF_WATER_MARK_ENCODING = "pdf.water.mark.encoding";
	public static final String I18N_PDF_WATER_MARK_SIZE = "pdf.water.mark.size";
	public static final String I18N_PDF_WATER_MARK_POSITION_X = "pdf.water.mark.position.x";
	public static final String I18N_PDF_WATER_MARK_POSITION_Y = "pdf.water.mark.position.y";
	public static final String I18N_PDF_WATER_BAND_VH = "pdf.water.mark.band.vh";
	public static final String I18N_PDF_WATER_BAND_SIZE = "pdf.water.mark.band.size";
	public static final String I18N_LABEL_COMPUL_DOCUMENTS = "label.compul.documents";

	/**
	 * Etiqueta que se reemplazara por el número de registro.
	 */
	public static String TAG_NUM_REGISTRO = "num_registro";

	/**
	 * Etiqueta que se reemplazara por la oficina de registro.
	 */
	public static String TAG_OFICINA_REGISTRO = "oficina_registro";

	/**
	 * Etiqueta que se reemplazara por el codigo de oficina de registro
	 */
	public static String TAG_COD_OFICINA_REGISTRO = "codigo_oficina_registro";

	/**
	 * Etiqueta que se reemplazara por el asunto de registro.
	 */
	public static String TAG_ASUNTO = "asunto";

	/**
	 * Etiqueta que se reemplazara por el usuario que realizo el registro.
	 */
	public static String TAG_USUARIO = "usuario";

	/**
	 * Etiqueta que se reemplazara por el origen del registro.
	 */
	public static String TAG_ORIGEN = "origen";

	/**
	 * Etiqueta que se reemplazara por el destino del registro.
	 */
	public static String TAG_DESTINO = "destino";

	/**
	 * Etiqueta que se reemplazara por el remitente del registro.
	 */
	public static String TAG_REMITENTE = "remitente";

	/**
	 * Etiqueta que se reemplazara por el CN del certificado.
	 */
	public static String TAG_CERTIFICADO = "certificado";

	/**
	 * Etiqueta que se reemplazara por el nombre del firmante recibido mediante el CN del certificado.
	 */
	public static String TAG_CERTIFICADO_SOLO_NOMBRE = "firmante";

	/**
	 * Etiqueta que se reemplazara por la Fecha de Compulsa
	 */
	public static String TAG_FECHA_COMPULSA = "fecha_compulsa";

	/**
	 * Etiqueta que se reemplazara por el Numero Total de Paginas
	 */
	public static String TAG_TOTAL_PAGES = "total_paginas";

	/**
	 * Etiqueta que se reemplazara por el Numero de Pagina Actual
	 */
	public static String TAG_CURRENT_PAGE = "numero_pagina";

	/**
	 * Etiqueta que se reemplazara por el Localizador del Documento
	 */
	public static String TAG_LOCATOR = "localizador";

}
