/**
 * @author Alvaro Ugarte Gómez
 * 
 * Fecha de Creación: 22-jun-2004
 */

package ieci.tecdoc.sgm.nt.database.exception;

/**
 * Códigos de error de excepciones del Sistema de Archivo Digital.
 */

public final class DbErrorCodes {

   private DbErrorCodes() {

   }

   public static final long EC_PREFIX = 20004000;

   public static final long NT_GET_ALL_VALUES = EC_PREFIX + 1;   
   public static final long NT_INSERT_ALL_VALUES = EC_PREFIX + 2;
   public static final long NT_ADD_VALUE = EC_PREFIX + 3;
   
}