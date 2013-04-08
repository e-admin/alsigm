package ieci.tecdoc.sgm.registro.exception;

/**
 * Proporciona los códigos de error específicos del módulo de registro telemático.
 */
public class RegistroCodigosError
{
   private RegistroCodigosError()
   {
   }

   /**
    * Código de error base.
    */
   private static final long EC_PREFIX = 6001000;

   /**
    * No se ha podido crear la solicitud de registro.
    */
   public static final long EC_CREATE_REGISTRY_REQUEST = EC_PREFIX + 1;
   
   /**
    * Error en la base de datos de registro.
    */
   public static final long EC_ERR_DB = EC_PREFIX + 2;
   
   /**
    * La información suministrada no es válida.
    */
   public static final long EC_VALIDATE_INFO = EC_PREFIX + 3;
   
   /**
    * Hay virus en el documento.
    */
   public static final long EC_VIRUS = EC_PREFIX + 4;
   
   /**
    * Se requiere el documento.
    */
   public static final long EC_REQUIRED_DOCUMENT = EC_PREFIX + 5;
   
   /**
    * Se han anexado más documentos de los requeridos.
    */
   public static final long EC_MORE_DOCUMENTS = EC_PREFIX + 6;
   
   /**
    * Ha excedido el tamaño máximo permitido en el documento.
    */
   public static final long EC_DOCUMENT_TOO_LARGE = EC_PREFIX + 7;
   
   /**
    * es una extensión no soportada para el documento.
    */
   public static final long EC_EXTENSION = EC_PREFIX + 8;

   /**
    * No se ha podido efectuar la operación de registro.
    */
   public static final long EC_REGISTER = EC_PREFIX + 9;
   
   /**
    * No se ha podido generar un número de registro.
    */
   public static final long EC_REGISTRY_NUMBER = EC_PREFIX + 10;
   
   /**
    * La tabla de números de registro no ha sido inicializada.
    */
   public static final long EC_REGISTRY_NUMBER_NOT_INITIALIZED = EC_PREFIX + 11;
   
   /**
    * No se ha podido generar la evidencia forense.
    */
   public static final long EC_FORENSIC_EVIDENCE = EC_PREFIX + 12;
   
   /**
    * El número de registro no existe.
    */
   public static final long EC_BAD_REGISTRY_NUMBER = EC_PREFIX + 13;
   
   /**
    * No se han podido eliminar los documentos temporales.
    */
   public static final long EC_CLEAN_DOCUMENTS = EC_PREFIX + 14;
   
   /**
    * No se ha podido recuperar el justificante de registro.
    */
   public static final long EC_GET_RECEIPT = EC_PREFIX + 15;
   
   /**
    * No se ha podido recuperar la información del registro.
    */
   public static final long EC_GET_REGISTRY = EC_PREFIX + 16;
   
    /**
     * Error de configuración.
     */
    public static final long EC_CONFIG = EC_PREFIX + 17;

    /**
     * Error en la gestión de los documentos.
     */
    public static final long EC_MANAGE_DOCUMENTS = EC_PREFIX + 18;

    /**
     * Error en la gestión de los documentos.
     */
    public static final long EC_LOG = EC_PREFIX + 19;

    /**
     * La firma no es correcta en el documento.
     */
    public static final long EC_SIGNATURE = EC_PREFIX + 20;

    /**
     * El formato del documento no es correcto.
     */
    public static final long EC_DOCUMENT = EC_PREFIX + 21;

    /**
     * El formato de la solicitud de registro no es correcto.
     */
    public static final long EC_CONTENT_REQUEST = EC_PREFIX + 22;

    /**
     * La solicitud de registro no está firmada correctamente.
     */
    public static final long EC_SIGN_REQUEST = EC_PREFIX + 23;

    /**
     * El contenido del justificante de registro no es correcto.
     */
    public static final long EC_CONTENT_RECEIPT = EC_PREFIX + 24;

    /**
     * El justificante de registro no está firmado correctamente.
     */
    public static final long EC_SIGN_RECEIPT = EC_PREFIX + 25;

    /**
     * No se ha podido obtener la información del documento.
     */
    public static final long EC_GET_DOCUMENT = EC_PREFIX + 26;

    /**
     * No se ha podido recuperar el contenido del documento.
     */
    public static final long EC_GET_DOCUMENT_CONTENT = EC_PREFIX + 27;

    /**
     * No se ha podido verificar la integridad del registro.
     */
    public static final long EC_VERIFY_REGISTRY = EC_PREFIX + 28;
    
    /**
     * No se ha podido verificar la integridad del registro.
     */
    public static final long EC_DOCUMENT_INTEGRITY = EC_PREFIX + 29;

    /**
     * No es válido el documento cuyo código es.
     */
    public static final long EC_VERIFY_DOCUMENT = EC_PREFIX + 30;
    
    /**
     * No existe ningún registro que coincida con los parámetros de la
     * búsqueda
     */
    public static final long EC_NO_REGISTRY =  EC_PREFIX + 31;
    
    /**
     * Error al actualizar el estado de un registro consolidado
     */
    public static final long EC_ERR_CONSOLIDATED_REGISTRY =  EC_PREFIX + 32;
    
    /**
     * Error al transformar documento para impresión
     */
    public static final long EC_ERR_PDF_PRINTING_FORMAT =  EC_PREFIX + 33;
    
    /**
     * Error al obtener un documento de un registro
     */
    public static final long EC_RETRIEVE_REGISTRY_DOCUMENT =  EC_PREFIX + 34;
    
    /**
     * El número de registro o el código de documento no es válido
     */
    public static final long EC_INCORRECT_RN_OR_DC =  EC_PREFIX + 35;
    
    /**
     * Error al añadir un nuevo documento de registro
     */
    public static final long EC_ADD_REGISTRY_DOCUMENT =  EC_PREFIX + 36;
    
    /**
     * Error al actualizar un documento de registro
     */
    public static final long EC_UPDATE_REGISTRY_DOCUMENT =  EC_PREFIX + 37;
    
    /**
     * Error al actualizar los documentos de un registro
     */
    public static final long EC_UPDATE_REGISTRY_DOCUMENTS =  EC_PREFIX + 38;
    
    /**
     * El número de registro no es válido
     */
    public static final long EC_INCORRECT_REGISTRY_NUMBER = EC_PREFIX + 39;
    
    /**
     * Error al eliminar los documentos de un registro
     */
    public static final long EC_DELETE_REGISTRY_DOCUMENTS =  EC_PREFIX + 40;
    
    /**
     * Error al obtener todos los documentos de un registro
     */
    public static final long EC_GET_REGISTRY_DOCUMENTS =  EC_PREFIX + 41;
    
    /**
     * Error al eliminar un registro ya realizado (cuando falla al iniciarse expedientes)
     */
    public static final long EC_UNDO_REGISTRY =  EC_PREFIX + 42;
    
    /**
     * Error al iniar un expediente automáticamente
     */
    public static final long EC_INIT_EXPEDIENT =  EC_PREFIX + 43;
    
    /**
     * Error al obtener justificante de otro usuario
     */
    public static final long EC_JUSTIFICANTE_SIN_PERMISOS =  EC_PREFIX + 44;
    
    /**
     * Error al obtener el documento de otro usuario
     */
    public static final long EC_DOCUMENTO_SIN_PERMISOS =  EC_PREFIX + 45;
    
    /**
     * Error al eliminar un documento CSV GUI
     */
    public static final long EC_DELETE_DOCUMENTO_CSV =  EC_PREFIX + 46;
    
    /**
     * Error al añadir un nuevo documento CSV GUI
     */
    public static final long EC_ADD_DOCUMENTO_CSV =  EC_PREFIX + 47;
    
    
    /**
     * Error al obtener un documento CSV GUI
     */
    public static final long EC_RETRIEVE_DOCUMENTO_CSV =  EC_PREFIX + 48;
}
