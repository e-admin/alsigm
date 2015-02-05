/**
 * @author Alvaro Ugarte Gómez
 * 
 * Fecha de Creación: 22-jun-2004
 */

package ieci.tecdoc.sgm.nt.exception;

/**
 * Códigos de error de excepciones del Sistema de Archivo Digital.
 */

public final class ClaveIncorrectaErrorCodigos {

   private ClaveIncorrectaErrorCodigos() {

   }

   public static final long EC_PREFIX = 10006000;

   public static final long EC_INCORRECT_GUID = EC_PREFIX + 1;
}
