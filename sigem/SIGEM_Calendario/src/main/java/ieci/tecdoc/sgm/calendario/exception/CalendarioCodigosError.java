/*
 * Created on 11-ago-2005
 * @autor IECI S.A.
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ieci.tecdoc.sgm.calendario.exception;

/**
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CalendarioCodigosError 
{

   /**
	* Código de error base.
	*/
   public static final long EC_PREFIX = 4001000;
	
   /**
    * Error en la base de datos de catálogo de trámites.
    */
   public static final long EC_ERR_DB = EC_PREFIX + 1;
   
   /**
    * El identificador de trámite no es válido.
    */
   public static final long EC_BAD_PROCEDUREID = EC_PREFIX + 2;

   /**
    * El identificador de documento no es válido.
    */
   public static final long EC_BAD_DOCUMENTID = EC_PREFIX + 3;

    /**
     * Error al crear el documento.
     */
    public static final long EC_ADD_DOCUMENT = EC_PREFIX + 4;

    /**
     * Error al eliminar el documento.
     */
    public static final long EC_DELETE_DOCUMENT = EC_PREFIX + 5;

    /**
     * Error al recuperar la información del documento.
     */
    public static final long EC_GET_DOCUMENT = EC_PREFIX + 6;

    /**
     * Error al actualizar la información del documento.
     */
    public static final long EC_UPDATE_DOCUMENT = EC_PREFIX + 7;
    
    /**
     * Error al crear el trámite.
     */
    public static final long EC_ADD_PROCEDURE = EC_PREFIX + 8;

    /**
     * Error al eliminar el trámite.
     */
    public static final long EC_DELETE_PROCEDURE = EC_PREFIX + 9;

    /**
     * Error al recuperar la información del trámite.
     */
    public static final long EC_GET_PROCEDURE = EC_PREFIX + 10;

    /**
     * Error al actualizar la información del trámite.
     */
    public static final long EC_UPDATE_PROCEDURE = EC_PREFIX + 11;
    
    /**
     * El documento está siendo utilizado en algún trámite. Debe eliminarlo en primer lugar de dicho trámite.
     */
    public static final long EC_DOCUMENT_USED = EC_PREFIX + 12;
    
    /**
     * Error al recuperar la lista de trámites.
     */
    public static final long EC_GET_PROCEDURES = EC_PREFIX + 13;

    /**
     * Error al recuperar la lista de trámites.
     */
    public static final long EC_GET_DOCUMENTS = EC_PREFIX + 14;
    
    /**
     * Error al recuperar un documento asociado a un trámite 
     */
    public static final long EC_GET_PROCEDUREDOCUMENT = EC_PREFIX + 15;
    
    /**
     * Error, el identificador de trámite o el de documento no es válido
     */
    public static final long EC_BAD_PROCEDUREID_OR_DOCUMENTID = EC_PREFIX + 16;

    /**
     * Error al añadir un documento asociado a un trámite
     */
    public static final long EC_ADD_PROCEDUREDOCUMENT = EC_PREFIX + 17;
    
    /**
     * Error al eliminar un documento asociado a un trámite
     */
    public static final long EC_DELETE_PROCEDUREDOCUMENT = EC_PREFIX + 18;
    
    /**
     * Error al actualizar un documento asociado a un trámite
     */
    public static final long EC_UPDATE_PROCEDUREDOCUMENT = EC_PREFIX + 19;
    
    /**
     * Error al obtener un órganoi destinatario
     */
    public static final long EC_GET_ADDRESSEE = EC_PREFIX + 20;
    
    /**
     * Error, identificador de órgano destinatario no válido
     */
    public static final long EC_BAD_ADDRESSEEID = EC_PREFIX + 21;
    
    /**
     * Error al añadir un nuevo órgano destinatario
     */
    public static final long EC_ADD_ADDRESSEE = EC_PREFIX + 22;
    
    /**
     * Error al actualizar un órgano destinatario
     */
    public static final long EC_UPDATE_ADDRESSEE = EC_PREFIX + 23;
    
    /**
     * Error al obtener el listado de órganos destinarios
     */
    public static final long EC_GET_ADDRESSEES = EC_PREFIX + 24;
    
    /**
     * Error, órgano destinatario en uso
     */
    public static final long EC_ADDRESSEE_USED = EC_PREFIX + 25;
    
    /**
     * Error al eliminar un órgano destinatario
     */
    public static final long EC_DELETE_ADDRESSEE = EC_PREFIX + 26;
    
    /**
     * Error al obtener un conector
     */
    public static final long EC_GET_HOOK = EC_PREFIX + 27;
    
    /**
     * Error, el identificador del conector no es válido
     */
    public static final long EC_BAD_HOOKID = EC_PREFIX + 28;
    
    /**
     * Error al añadir un nuevo conector
     */
    public static final long EC_ADD_HOOK = EC_PREFIX + 29;
    
    /**
     * Error al actualizar un conector
     */
    public static final long EC_UPDATE_HOOK = EC_PREFIX + 30;
    
    /**
     * Error al eliminar un conector
     */
    public static final long EC_DELETE_HOOK = EC_PREFIX + 31;
    
    /**
     * Error al obtener listado de conectores
     */
    public static final long EC_GET_HOOKS = EC_PREFIX + 32;
    
    /**
     * Error al obtener un tipo de conector
     */
    public static final long EC_GET_HOOKTYPE = EC_PREFIX + 33;
    
    /**
     * Error, el identificador de tipo de conector no es válido
     */
    public static final long EC_BAD_HOOKTYPEID = EC_PREFIX + 34;
    
    /**
     * Error al añadir un nuevo tipo de conector
     */
    public static final long EC_ADD_HOOKTYPE = EC_PREFIX + 35;
    
    /**
     * Error al actualizar un tipo de conector
     */
    public static final long EC_UPDATE_HOOKTYPE = EC_PREFIX + 36;
    
    /**
     * Error al eliminar un tipo de conector
     */
    public static final long EC_DELETE_HOOKTYPE = EC_PREFIX + 37;
    
    /**
     * Error al obtener el listado de tipos de conector 
     */
    public static final long EC_GET_HOOKTYPES = EC_PREFIX + 38;
    
    /**
     * Error al añadir un nuevo conector de autenticación
     */
    public static final long EC_ADD_AUTENTICATION_HOOK = EC_PREFIX + 39;
    
    /**
     * Error, el identificador de trámite o de conector no es válido
     */
    public static final long EC_BAD_PROCEDUREID_OR_HOOKID = EC_PREFIX + 40;
    
    /**
     * Error al actualizar un conector de autenticación
     */
    public static final long EC_UPDATE_AUTENTICATION_HOOK = EC_PREFIX + 41;
    
    /**
     * Error al eliminar un conector de autenticación
     */
    public static final long EC_DELETE_AUTENTICATION_HOOK = EC_PREFIX + 42;
    
    /**
     * Error al obtener el listado de conectores de autenticación 
     */
    public static final long EC_GET_AUTENTICATION_HOOKS = EC_PREFIX + 43;
    
    /**
     * Error al obtener un conectores de autenticación 
     */
    public static final long EC_GET_AUTENTICATION_HOOK = EC_PREFIX + 44;

    /**
     * Error al obtener un calendario
     */
    public static final long EC_GET_CALENDAR = EC_PREFIX + 45;
    
    /**
     * Error si no hay un calendario
     */
    public static final long EC_NO_CALENDAR = EC_PREFIX + 46;
    
    /**
     * Error al insertar un calendario
     */
    public static final long EC_ADD_CALENDAR = EC_PREFIX + 47;
    
    /**
     * Error al actualizar un calendario
     */
    public static final long EC_UPDATE_CALENDAR = EC_PREFIX + 48;
    
    /**
     * Error al eliminar un calendario
     */
    public static final long EC_DELETE_CALENDAR = EC_PREFIX + 49;
    
    /**
     * Error al obtener un dia del calendario
     */
    public static final long EC_GET_CALENDAR_DAY = EC_PREFIX + 50;
    
    /**
     * Error si no hay un dia del calendario
     */
    public static final long EC_NO_CALENDAR_DAY = EC_PREFIX + 51;
    
    /**
     * Error al insertar un dia del calendario
     */
    public static final long EC_ADD_CALENDAR_DAY = EC_PREFIX + 52;
    
    /**
     * Error al actualizar un dia del calendario
     */
    public static final long EC_UPDATE_CALENDAR_DAY= EC_PREFIX + 53;
    
    /**
     * Error al eliminar un dia del calendario
     */
    public static final long EC_DELETE_CALENDAR_DAY = EC_PREFIX + 54;
    
    /**
     * Error al obtener listado de dias del calendario
     */
    public static final long EC_GET_CALENDAR_DAYS = EC_PREFIX + 55;
    
    /**
     * Error al obtener el identificador del dia del calendario
     */
    public static final long EC_GET_CALENDAR_DAY_ID = EC_PREFIX + 56;
}
