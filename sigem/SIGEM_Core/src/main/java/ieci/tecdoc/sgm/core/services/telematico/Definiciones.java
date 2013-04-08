package ieci.tecdoc.sgm.core.services.telematico;

/**
 * Clase que define códigos de registro
 *
 */
public class Definiciones 
{
   private Definiciones()
   {
   }
   
   public static final String REGISTRY_REQUEST_CODE = "Solicitud Firmada";
   public static final String REGISTRY_REQUEST_NOTSIGNED_CODE = "Solicitud";
   public static final String REGISTRY_RECEIPT_CODE = "Justificante de Registro";
   
   public static final String REQUEST_HEADER = "Solicitud_Registro";
   public static final String REGISTRY_DATA = "Datos_Registro";
   public static final String REGISTRY_NUMBER = "Numero_Registro";
   public static final String REGISTRY_DATE = "Fecha_Presentacion";
   public static final String REGISTRY_HOUR = "Hora_Presentacion";
   public static final String SIGNED_DATA = "Datos_Firmados";
   public static final String GENERIC_DATA = "Datos_Genericos";
   public static final String ORG = "Organismo";
   public static final String TOPIC = "Asunto";
   public static final String DESCRIPTION = "Descripcion";
   public static final String ADDRESSEE = "Destino";
   public static final String SUBJECT = "Materia";
   public static final String TYPE = "Tipo";
   public static final String SUBTYPE = "Subtipo";
   public static final String IDIOM = "Idioma";
   public static final String SENDER = "Remitente";
   public static final String ID = "Documento_Identificacion";
   public static final String SENDER_ID_TYPE = "Tipo";
   public static final String SENDER_ID = "Numero";
   public static final String SENDER_NAME = "Nombre";
   public static final String SENDER_SURNAME = "Apellidos";
   public static final String SENDER_EMAIL = "Correo_Electronico";
   public static final String SENDER_PHONE = "Telefono";
   public static final String FOLDER_ID = "Numero_Expediente";
   public static final String SPECIFIC_DATA = "Datos_Especificos";
   public static final String ADDRESS= "Domicilio";
   public static final String DOCUMENTS = "Documentos";
   public static final String DOCUMENT = "Documento";
   public static final String CODE = "Codigo";
   public static final String EXTENSION = "Extension";
   public static final String HASH = "Hash";
   public static final String SIGNATURE_HOOK = "Firma";
   public static final String VALIDATION_HOOK = "Contenido";
   public static final String ANTIVIRUS = "Antivirus";
   public static final String ANTIVIRUS_OK = "OK";
   public static final String ANTIVIRUS_ERROR = "VIRUS";
   public static final String ORG_ES = "SIGEM";
   public static final String SIGNATURE = "Firma";
   public static final String XPATH_REGISTRY_DATA = "Datos_Registro";
   public static final String XPATH_GENERIC_DATA = "Datos_Firmados/Datos_Genericos";
   public static final String XPATH_SPECIFIC_DATA = "Datos_Firmados/Datos_Especificos";
   public static final String XPATH_DOCUMENTS = "Datos_Firmados/Documentos";
   public static final String XPATH_SENDER_DATA = XPATH_GENERIC_DATA + "/" + SENDER;
}