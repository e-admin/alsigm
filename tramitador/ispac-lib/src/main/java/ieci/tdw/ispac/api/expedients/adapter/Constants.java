package ieci.tdw.ispac.api.expedients.adapter;

import java.text.SimpleDateFormat;


/**
 * Clase que contiene las constantes de la aplicación.
 */
public class Constants
{

    public static final String FIELD_VARIABLE_PATTERN       = "\\x24\\x7b[^\\x7d]*\\x7d";
    
	public static final SimpleDateFormat DATETIME_FORMAT    = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static final String SPAC_EXPEDIENTES = "SPAC_EXPEDIENTES";
	
    // ========================================================================
    // Constantes para las rutas XPATH
    // ========================================================================
    public static final String XPATH_TABLE            		= "/procedure/table";
    public static final String XPATH_PROPERTY               = "./properties/property";
    public static final String XPATH_MAPPINGS               = "./mappings";
    public static final String XPATH_FIELD                  = "./field";
    public static final String XPATH_ENTITY                 = "./entity";
    public static final String XPATH_COMPOSITE              = "./composite";
    // ========================================================================
    
    
    // ========================================================================
    // Constantes para los atributos
    // ========================================================================
    public static final String ATTR_NAME        			= "name";
    public static final String ATTR_ITERATOR       			= "iterator";
    public static final String ATTR_VALUE             		= "value";
    public static final String ATTR_MULTIVALUE             	= "multivalue";
    public static final String ATTR_MAX_LENGTH        		= "maxLength";
    public static final String ATTR_DATE_FORMAT        		= "dateFormat";
    public static final String ATTR_XPATH            		= "xpath";
    public static final String ATTR_FIELD_FOREIGN_KEY		= "fieldForeignKey";
    public static final String ATTR_FIELD_PRIMARY_KEY		= "fieldPrimaryKey";
    // ========================================================================


    // ========================================================================
    // Constantes para las variables
    // ========================================================================
	public static final String VAR_TYPE_PARAM               = "param";
	public static final String VAR_TYPE_PROPERTY            = "property";
	public static final String VAR_TYPE_SYSTEM              = "system";
	public static final String VAR_TYPE_XPATH               = "xpath";
	public static final String VAR_TYPE_SEPARATOR           = ":";
    // ========================================================================

    // ========================================================================
    // Constantes para las funciones
    // ========================================================================
	public static final String VAR_FUNC_CURRENT_DATETIME    = "currentDatetime()";
    // ========================================================================

    // ========================================================================
    // Constantes para las propiedades
    // ========================================================================
    public static final String PROPERTY_PRIMARY_KEY        	= "primaryKey";
    // ========================================================================
	
    // ========================================================================
    // Constantes para las valores
    // ========================================================================
    public static final int VALUE_DEFAULT_KEY        		= 0;
    // ========================================================================	
    
    // ========================================================================
    // Constantes para los ficheros temporales
    // ========================================================================
    /** Constante para el prefijo de los ficheros temporales. */
    public static final String TEMP_FILE_NAME_PREFIX        = "ai_";
    // ========================================================================
    
    // ========================================================================
    // Constantes para los permisos
    // ========================================================================
    /** Constante para el prefijo de los ficheros temporales. */
    public static final int CAN_INIT_EXPEDIENTS      = 1;
    public static final int CAN_SEND_EXPEDIENTS_TO_TRASH=0;
    // ========================================================================

}