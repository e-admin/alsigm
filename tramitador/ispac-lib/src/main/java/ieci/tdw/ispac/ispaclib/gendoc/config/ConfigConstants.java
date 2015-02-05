package ieci.tdw.ispac.ispaclib.gendoc.config;

public class ConfigConstants {

	
	public static final String CONFIG_ATTRIBUTE_ID = "@id";

	public static final String CONFIG_ATTRIBUTE_ALIAS = "@alias";

	public static final String CONFIG_ATTRIBUTE_DEFAULT = "@default";

	public static final String CONFIG_ATTRIBUTE_USER = "@user";

	public static final String CONFIG_ATTRIBUTE_PASSWORD = "@password";

	public static final String CONFIG_ATTRIBUTE_SITEURL = "@siteUrl";

	public static final String CONFIG_ATTRIBUTE_LIBRARY = "@library";

	public static final String CONFIG_ATTRIBUTE_TOKEN_TYPE = "@type";
	
	public static final String CONFIG_ATTRIBUTE_TOKEN_VALUE_TYPE = "@type";
	
	public static final String CONFIG_XPATH_PROPERTY = "Properties/Property";

	public static final String CONFIG_XPATH_REPOSITORY = "/Repositories/Repository";

	public static final String CONFIG_ATTRIBUTE_PROPERTY_NAME = "name";

	public static final String CONFIG_TAG_FOLDERPATH = "FolderPath";


	public static final String CONFIG_TOKEN_NAME = "Name";

	public static final String CONFIG_TOKEN_VALUE = "Value";

	public static final String CONFIG_TOKEN_DOCUMENT_NAME = "docName";

	public static final String CONFIG_XPATH_ALIAS_TO_REPLACE = "_ALIAS_";
	
	public static final String CONFIG_XPATH_TOKENS = "/Repositories/Repository[@alias='" + CONFIG_XPATH_ALIAS_TO_REPLACE + "']/Tokens/Token";
	
	public static final String CONFIG_XPATH_MAPPINGS = "/Repositories/Repository[@alias='" + CONFIG_XPATH_ALIAS_TO_REPLACE + "']/MetaDataMappings/Mapping";
	
	public static final String CONFIG_MAPPING_SOURCE = "Source";

	public static final String CONFIG_MAPPING_DESTINATION = "Destination";

	public static final String CONFIG_ATTRIBUTE_MAPPING_TYPE = "@type";

	public static final String CONFIG_ATTRIBUTE_MAPPING_FORMAT = "@format";
	
	public static final String CONFIG_MAPPING_SOURCE_TYPE_TOKEN = "token";

	public static final String CONFIG_MAPPING_SOURCE_TYPE_CONSTANT = "constant";
	
	public static final String CONFIG_MAPPING_SOURCE_TYPE_DYNAMIC = "dynamic";

	public static final String CONFIG_TOKEN_CONCATENATE_CHAR = "+";
	
	public static final String CONFIG_TOKEN_TYPE_CONSTANT = "constant";
	
	public static final String CONFIG_TOKEN_TYPE_XPATH = "xpath";
	
	public static final String CONFIG_TOKEN_TYPE_EXPRESION = "expression";
	
	//==============================================================================
	//Tipos de datos de los metadatos
	public static final String CONFIG_DESTINATION_TYPE_STRING = "string";
	
	public static final String CONFIG_DESTINATION_TYPE_INTEGER = "integer";
	
	public static final String CONFIG_DESTINATION_TYPE_FLOAT = "float";

	public static final String CONFIG_DESTINATION_TYPE_DATE = "date";
	
	public static final String CONFIG_DESTINATION_TYPE_DATETIME = "datetime";

	public static final String CONFIG_DESTINATION_TYPE_BOOLEAN = "boolean";

	public static final String CONFIG_DESTINATION_TYPE_CURRENCY = "currency";
	//==============================================================================

	
	public static final String CONFIG_ATTRIBUTE_MAPPING_DESTINATION_TYPE = "@type";
	
	public static final String CONFIG_ATTRIBUTE_MAPPING_DESTINATION_FORMAT = "@format";

	public static final String CONFIG_OBLIGATORY_PREFIX_DYNAMIC_VALUE = "_";
	
	public static final String CONFIG_DYNAMIC_YEAR = CONFIG_OBLIGATORY_PREFIX_DYNAMIC_VALUE + "YEAR";
	
	public static final String CONFIG_DYNAMIC_MONTH = CONFIG_OBLIGATORY_PREFIX_DYNAMIC_VALUE + "MONTH";
	
	public static final String CONFIG_DYNAMIC_DAY = CONFIG_OBLIGATORY_PREFIX_DYNAMIC_VALUE + "DAY";
	
	public static final String CONFIG_DYNAMIC_HOUR = CONFIG_OBLIGATORY_PREFIX_DYNAMIC_VALUE + "HOUR";
	
	public static final String CONFIG_DYNAMIC_MINUTE = CONFIG_OBLIGATORY_PREFIX_DYNAMIC_VALUE + "MINUTE";
	
	public static final String CONFIG_DYNAMIC_SECOND = CONFIG_OBLIGATORY_PREFIX_DYNAMIC_VALUE + "SECOND";

	public static final String CONFIG_TOKEN_REF_PATTERN = "\\$\\{[^}]*}";


}
