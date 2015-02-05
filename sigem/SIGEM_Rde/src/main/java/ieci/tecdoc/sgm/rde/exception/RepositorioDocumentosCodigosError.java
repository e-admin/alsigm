/**
 * @author José Antonio Nogales Rincón
 * 
 * Fecha de Creación: 20-mar-2007
 */

package ieci.tecdoc.sgm.rde.exception;

/**
 * Códigos de error de excepciones del Sistema de Archivo Digital.
 */

public final class RepositorioDocumentosCodigosError {

   private RepositorioDocumentosCodigosError() {

   }

   /**
    * Código base para los errores asociados a la exception RepositorioDocumentos
    */
   public static final long EC_PREFIX = 10005000;
   /**
    * Error al alamacenar un documento
    */
   public static final long EC_STORE_DOCUMENT = EC_PREFIX + 1;
   /**
    * Error al almacenar un conjunto de documentos
    */
   public static final long EC_STORE_DOCUMENTS = EC_PREFIX + 2;
   /**
    * Error al obtener un documento
    */
   public static final long EC_RETRIEVE_DOCUMENT = EC_PREFIX + 3;
   /**
    * Error al borrar un documento
    */
   public static final long EC_DELETE_DOCUMENT = EC_PREFIX + 4;
   /**
    * Error al almacenar y obtener el hash de un conjunto de documentos
    */
   public static final long EC_STORE_AND_GET_HASH = EC_PREFIX + 5;
   /**
    * Error al no encontrar el fichero correspondiente a un documento
    */
   public static final long EC_FILE_NOT_FOUND = EC_PREFIX + 6;
}