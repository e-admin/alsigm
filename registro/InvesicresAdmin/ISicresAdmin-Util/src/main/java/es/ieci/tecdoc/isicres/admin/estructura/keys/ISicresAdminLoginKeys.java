
package es.ieci.tecdoc.isicres.admin.estructura.keys;

/**
 * Proporciona los códigos de error específicos del API de acceso(login) al 
 * sistema.
 */
 
public class ISicresAdminLoginKeys 
{
   private ISicresAdminLoginKeys()
   {
   }

   /**
    * Código de error base.
    */
   private static final long EC_PREFIX = 3009000;

   /**
    * El identificador de aplicación no es válido para el API de administración.
    */
   public static final long EC_INVALID_PRODUCT_ID = EC_PREFIX + 1;

   /**
    * El método de acceso (login) no es válido para el API de administración.
    */
   public static final long EC_INVALID_METHOD_ID = EC_PREFIX + 1;


}