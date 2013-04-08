/**
 * @author Alvaro Ugarte Gómez
 * 
 * Fecha de Creación: 22-jun-2004
 */

package ieci.tecdoc.sgm.pe.database.exception;
/*
 * $Id: DbCodigosError.java,v 1.1.2.1 2008/01/25 12:30:16 jconca Exp $
 */
/**
 * Códigos de error de excepciones del Sistema de Archivo Digital.
 */

public final class DbCodigosError {

   private DbCodigosError() {

   }

   public static final long EC_PREFIX = 10004000;

   public static final long EC_GET_ALL_VALUES = EC_PREFIX + 1;
   public static final long EC_GET_EXTENSION_VALUES = EC_PREFIX + 2;
   public static final long EC_INSERT_ALL_VALUES = EC_PREFIX + 3;
   public static final long EC_SELECT_NUMSEC_VALUE = EC_PREFIX + 4;
   public static final long EC_GET_NEXT_SEQUENCE_NUMBER_EXCEPTION = EC_PREFIX + 5;
   public static final long EC_MAX_SEQUENCE_NUMBER = EC_PREFIX + 6;
   public static final long EC_LOAD_MIME_TYPES_BY_EXTENSION_EXCEPTION = EC_PREFIX + 7;
   public static final long EC_ADD_VALUE = EC_PREFIX + 8;
   public static final long EC_DELETE_ALL_VALUES = EC_PREFIX + 9;   
   public static final long EC_SELECT_BY_GUID_NOT_FOUND = EC_PREFIX + 10;
   public static final long EC_SELECT_BY_GUID_EXCEPTION = EC_PREFIX + 11;
   public static final long EC_CLOSE_CONNECTION = EC_PREFIX + 12;
   public static final long EC_INCORRECT_GUID = EC_PREFIX + 13;
   public static final long EC_RETRIEVE_DOCUMENT = EC_PREFIX + 14;
   public static final long EC_TRANSACTION_REQUIRED = EC_PREFIX + 15;
   public static final long EC_QUERY_EXCEPTION = EC_PREFIX + 16;
}