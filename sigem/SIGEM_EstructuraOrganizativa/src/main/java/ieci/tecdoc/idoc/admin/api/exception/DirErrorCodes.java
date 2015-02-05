package ieci.tecdoc.idoc.admin.api.exception;

/**
 * Proporciona los códigos de error específicos del API de administración de 
 * directorios.
 */

public class DirErrorCodes 
{
   private DirErrorCodes()
   {
   }
   
   /**
    * Código de error base.
    */
   private static final long EC_PREFIX = 3006000;

   /**
    * El directorio ya existe.
    */
   public static final long EC_DIR_EXITS = EC_PREFIX + 1;
   
   /**
    * El directorio no existe.
    */
   public static final long EC_DIR_NOT_EXITS = EC_PREFIX + 2;
   
   /**
    * El directorio no se puede eliminar. Tiene hijos.
    */
   public static final long EC_DIR_HAS_CHILDREN = EC_PREFIX + 3;
   
}