/**
 * @author José Antonio Nogales Rincón
 * 
 * Fecha de Creación: 20-mar-2007
 */

package ieci.tecdoc.sgm.nt.exception;

/**
 * Códigos de error de excepciones del Sistema de Archivo Digital.
 */

public final class DocumentosRepositorioErrorCodigos {

   private DocumentosRepositorioErrorCodigos() {

   }

   public static final long EC_PREFIX = 10005000;

   public static final long EC_STORE_DOCUMENT = EC_PREFIX + 1;
   public static final long EC_STORE_DOCUMENTS = EC_PREFIX + 2;
   public static final long EC_RETRIEVE_DOCUMENT = EC_PREFIX + 3;
   public static final long EC_DELETE_DOCUMENT = EC_PREFIX + 4;
   public static final long EC_STORE_AND_GET_HASH = EC_PREFIX + 5;
   public static final long EC_FILE_NOT_FOUND = EC_PREFIX + 6;
   public static final long EC_TOO_MANY_ROWS = EC_PREFIX + 7;
   public static final long EC_UPDATE_STATE = EC_PREFIX + 8;
}