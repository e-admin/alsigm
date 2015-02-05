/**
 * @author Carlos Navas
 * 
 * Fecha de Creación: 11/05/2007
 */
package ieci.tecdoc.sgm.pe.exception;
/*
 * $Id: GuidIncorrectoCodigosError.java,v 1.1.2.1 2008/01/25 12:30:16 jconca Exp $
 */
/**
 * Códigos de error de excepciones.
 */
public final class GuidIncorrectoCodigosError {

   private GuidIncorrectoCodigosError() {

   }

   public static final long EC_PREFIX = 10006000;

   public static final long EC_INCORRECT_GUID = EC_PREFIX + 1;
}