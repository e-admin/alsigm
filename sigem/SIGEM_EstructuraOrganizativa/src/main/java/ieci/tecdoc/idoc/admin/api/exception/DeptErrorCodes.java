package ieci.tecdoc.idoc.admin.api.exception;

/**
 * Proporciona los códigos de error específicos del API de administración de 
 * departamentos.
 */
public class DeptErrorCodes
{
	private DeptErrorCodes()
   {
   }

   /**
    * Código de error base.
    */
   private static final long EC_PREFIX = 3008000;
   
   /**
    * El departamento no existe. Por ejemplo, cuando se intenta cargar un departamento
    * cuyo identificador no es válido.
    */
   public static final long EC_DEPT_NOT_EXITS = EC_PREFIX + 1;
   
   /**
    * El departamento no tiene permisos asignados.
    */
   public static final long EC_DEPT_NOT_PERMS = EC_PREFIX + 3;

   /**
    * El permiso del departamento para el producto solicitado no existe.
    */
   public static final long EC_DEPT_NOT_PERM_FOR_PRODUCT = EC_PREFIX + 4;
   
   /**
    * Ya existe un departamento con ese nombre.
    */
   public static final long EC_DEPT_EXIST_NAME = EC_PREFIX + 5;
   
   /**
    * No se permite introducir comillas en el texto.
    */
   public static final long EC_DEPT_DESC_EXIST_QUOTES = EC_PREFIX + 6;
   
   /**
    * El departamento tiene usuarios.
    */
   public static final long EC_DEPT_HAS_USERS = EC_PREFIX + 7;
   /**
    * El departamento tiene usuarios.
    */
   public static final long EC_DEPT_HAS_DEPTS = EC_PREFIX + 8;
}
