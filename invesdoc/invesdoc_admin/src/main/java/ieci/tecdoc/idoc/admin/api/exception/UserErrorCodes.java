
package ieci.tecdoc.idoc.admin.api.exception;

/**
 * Proporciona los códigos de error específicos del API de administración de 
 * usuarios.
 */
 
public class UserErrorCodes 
{
   private UserErrorCodes()
   {
   }

   /**
    * Código de error base.
    */
   private static final long EC_PREFIX = 3001000;

   /**
    * El usuario ya existe. Por ejemplo, cuando se crea un usuario a partir
    * de un servicio de directorio Ldap que ha sido creado previamente.
    */
   public static final long EC_USER_EXITS = EC_PREFIX + 1;

   /**
    * El usuario no existe. Por ejemplo, cuando se intenta cargar un usuario
    * cuyo identificador no es válido.
    */
   public static final long EC_USER_NOT_EXITS = EC_PREFIX + 2;

   /**
    * El usuario no tiene permisos asignados.
    */
   public static final long EC_USER_NOT_PERMS = EC_PREFIX + 3;

   /**
    * El usuario no tiene perfiles asignados.
    */
   public static final long EC_USER_NOT_PROFILES = EC_PREFIX + 4;

   /**
    * El perfil del usuario para el producto solicitado no existe.
    */
   public static final long EC_USER_NOT_PROFILE_FOR_PRODUCT = EC_PREFIX + 5;

   /**
    * El permiso del usuario para el producto solicitado no existe.
    */
   public static final long EC_USER_NOT_PERM_FOR_PRODUCT = EC_PREFIX + 6;

   /**
    * El usuario no se puede eliminar. Se produce cuando el usuario a eliminar
    * coincide con el que está conectado administrado el sistema.
    */
   public static final long EC_USER_CONNECTED = EC_PREFIX + 7;
   
   /**
    * No se permite introducir comillas en el texto.
    */
   public static final long EC_USER_DESC_EXIST_QUOTES = EC_PREFIX + 8;
   
   /**
    * La contraseña no puede ser vacía.
    */
   public static final long EC_USER_PASS_EMPTY = EC_PREFIX + 9;
   
   /**
    * Contraseña no válida. No se admiten blancos.
    */
   public static final long EC_USER_PASS_WHITESPACE = EC_PREFIX + 10;
   
   /**
    * No existe la configuración del usuario.
    */
   public static final long EC_USER_NOT_CONFIG = EC_PREFIX + 11;
   
   /**
    * El tamaño de la contraseña debe ser mayor.
    */
   public static final long EC_USER_PASS_LENGTH = EC_PREFIX + 12;
   
   /**
    * Ya existe un usuario con ese nombre    
    */
   public static final long EC_USER_EXISTS_NAME = EC_PREFIX + 13;

}