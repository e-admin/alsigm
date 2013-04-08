//
// FileName: Keys.java
//
package com.ieci.tecdoc.common.isicres;

import java.util.Locale;

/**
 * @author lmvicente
 * @version @since @creationDate 02-mar-2004
 */

public interface Keys {

    public static Locale LOCALE_DEFECTO = new Locale("es", "ES");
    public static Locale LOCALE_CATALAN = new Locale("ca", "ES");
    public static Locale LOCALE_PAISVASCO = new Locale("eu", "ES");
    public static Locale LOCALE_GALICIA = new Locale("gl", "ES");

    public static String JNDI_EXTENSIONEJB_NAME = "ejb/Extension";
    public static String JNDI_SECURITYEJB_NAME = "ejb/Security";
    public static String JNDI_BOOKEJB_NAME = "ejb/Book";
    public static String JNDI_ATTRIBUTESEJB_NAME = "ejb/Attributes";
    public static String JNDI_VALIDATIONEJB_NAME = "ejb/Validation";
    public static String JNDI_REPORTSEJB_NAME = "ejb/Reports";
    public static String JNDI_DISTRIBUTIONEJB_NAME = "ejb/Distribution";


    public static String QUERY_FIRST_PAGE = "QueryFirstPage";
    public static String QUERY_LAST_PAGE = "QueryLastPage";
    public static String QUERY_NEXT_PAGE = "QueryNextPage";
    public static String QUERY_PREVIOUS_PAGE = "QueryPreviousPage";
    public static String QUERY_ALL = "QueryAll";

    public static final String QUERY_EQUAL_TEXT_VALUE = "=";
    public static final int QUERY_EQUAL_VALUE = 1;
    public static final String QUERY_NOT_EQUAL_TEXT_VALUE = "!=";
    public static final int QUERY_NOT_EQUAL_VALUE = 2;
    public static final String QUERY_LESSER_TEXT_VALUE = "<";
    public static final int QUERY_LESSER_VALUE = 16;
    public static final String QUERY_GREATER_TEXT_VALUE = ">";
    public static final int QUERY_GREATER_VALUE = 4;
    public static final String QUERY_LESSER_EQUAL_TEXT_VALUE = "<=";
    public static final int QUERY_LESSER_EQUAL_VALUE = 32;
    public static final String QUERY_GREATER_EQUAL_TEXT_VALUE = ">=";
    public static final int QUERY_GREATER_EQUAL_VALUE = 8;
    public static final String QUERY_BETWEEN_TEXT_VALUE = "..";
    public static final int QUERY_BETWEEN_VALUE = 64;
    public static final String QUERY_LIKE_TEXT_VALUE = "%";
    public static final int QUERY_LIKE_VALUE = 128;
    public static final String QUERY_OR_TEXT_VALUE = "|";
    public static final int QUERY_OR_VALUE = 256;
    public static final String QUERY_ABC_TEXT_VALUE = "Abc";
    public static final int QUERY_ABC_VALUE = 512;
    public static final String QUERY_IN_AND_TEXT_VALUE = "C&";
    public static final int QUERY_IN_AND_VALUE = 1024;
    public static final String QUERY_IN_OR_TEXT_VALUE = "c|";
    public static final int QUERY_IN_OR_VALUE = 2048;
    public static final String QUERY_DEPEND_OF_TEXT_VALUE = "dde";
    public static final String QUERY_NEXO_OR = "or";
    public static final String QUERY_NEXO_AND = "and";

    public static final int EREG_FDR_MATTER = 17;
    public static final int SREG_FDR_MATTER = 13;

    public static final int REPORT_TYPE_CM = 2;
    public static final int REPORT_TYPE_LM = 0;
    public static final int REPORT_TYPE_RMD = 1;
    public static final int REPORT_TYPE_RMO = 3;

    public static final int DISTRIBUCION_IN_DIST = 1;
    public static final int DISTRIBUCION_OUT_DIST = 2;

    //Eventos
    public static final String DISTRIBUTION_MANUAL_EVENT = "DMANUAL";
    public static final String DISTRIBUTION_ACCEPT_EVENT = "DACC";
    public static final String DISTRIBUTION_ARCHIVE_EVENT = "DARC";
    public static final String DISTRIBUTION_REJECT_EVENT = "DREJ";
    public static final String DISTRIBUTION_REDISTRIBUTE_EVENT = "DRED";
    public static final String REGISTER_CREATE_EVENT = "RCRE";
    public static final String REGISTER_MODIF_EVENT = "RMOD";


	public static final String XPATH_PERSONA_ID = "//Persona/Id";
	public static final String XPATH_PERSONA_DOMICILIO = "//Persona/Domicilios/Domicilio";
	public static final String XPATH_DOMICILIOS_DOMICILIO = "//Domicilios/Domicilio";
	public static final String XML_ID_TEXT = "Id";
	public static final String XML_DIRECCION_TEXT = "Direccion";
	public static final String XML_CODPOSTAL_TEXT = "CodPostal";
	public static final String XML_POBLACION_TEXT = "Poblacion";
	public static final String XML_PROVINCIA_TEXT = "Provincia";
	public static final String XML_PREFERENCIA_TEXT = "Preferencia";

	public static final String XPATH_PERSONA_DIRECCION_TEL = "//Persona/Telematicas/Telematica";
	public static final String XML_TYPE_ADDRTEL_TEXT = "TipoTel";
	public static final String XML_ID_TEXT_TEL = "IdTel";
	public static final String XML_PREFERENCIA_TEXT_TEL = "PreferenciaTel";
	public static final String XML_DIRECCION_TEXT_TEL = "DireccionTel";

	//Tamaño maximo que se puede almacenar en la columna NAME de la tabla SCR_REGINT
	public static final int MAX_LENGTH_STRING_DATA_INTERESADO = 95;

}

