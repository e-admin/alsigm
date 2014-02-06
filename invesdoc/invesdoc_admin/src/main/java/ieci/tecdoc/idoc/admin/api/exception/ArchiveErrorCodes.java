
package ieci.tecdoc.idoc.admin.api.exception;

/**
 * Proporciona los códigos de error específicos del API de administración de 
 * archivadores.
 */
public class ArchiveErrorCodes
{
   
   private ArchiveErrorCodes()
   {
   
   }
   
   /**
    * Código de error base.
    */
   private static final long EC_PREFIX = 3007000;

   /**
    * El archivador ya existe.
    */
   public static final long EC_ARCH_EXITS = EC_PREFIX + 1;
   
   /**
    * El archivador no tiene campos
    */
   public static final long EC_ARCH_NO_FLDS = EC_PREFIX + 2;
   
   /**
    * El archivador no tiene nombre
    */
   public static final long EC_ARCH_NO_NAME = EC_PREFIX + 3;
   
   /**
    * El campo no existe 
    */
   public static final long EC_FLD_NO_EXISTS = EC_PREFIX + 4;
   
   /**
    * El campo ya existe 
    */
   public static final long EC_FLD_EXISTS = EC_PREFIX + 5;
   
   /**
    * El archivador tiene carpetas
    */
   public static final long EC_ARCH_HAS_FOLDERS = EC_PREFIX + 6;
   
   /**
    * El archivador tiene tabla de validación sobre alguno
    * de sus campos
    */
   
   public static final long EC_ARCH_HAS_VALIDATION_TABLE = EC_PREFIX + 7;
   
   /**
    * El identificar del archivador no es válido
    */
   public static final long EC_ARCH_NO_ID = EC_PREFIX + 8;
   
   /**
    * El Archivador no existe
    */
   public static final long EC_ARCH_NOT_EXISTS = EC_PREFIX + 9;
   
   /**
    * El archivador tiene carpetas, no se puede modificar la definición
    * de sus campos.
    * 
    */
   public static final long EC_ARCH_WITH_FDRS = EC_PREFIX + 10;
   
   /**
    * El índice no existe 
    */
   public static final long EC_IDX_NO_EXISTS  = EC_PREFIX + 11;
   
   /**
    * Existe dobles comilla en el campo descripción del archivador
    * ó de alguno de sus campos
    */
   public static final long EC_ARCH_REMARKS_EXIST_QUOTES = EC_PREFIX + 12;
   
   /**
    * Existen índices que tienen asociados campos extendidos ó campo multivalores,
    * no está permitido 
    */
   public static final long EC_ARCH_IDXS_BAD_FIELDS = EC_PREFIX + 13;
   
   /**
    * Se han definido campos documentales sobre tipo de campos no válidos
    */
   public static final long EC_ARCH_FTS_BAD_FIELDS = EC_PREFIX + 14;
   
   /**
    * No esta configurado el uso de búsqueda documental en el sistema    
    *
    */
   public static final long EC_NO_CONFIG_FTS = EC_PREFIX + 15;
   
   /**
    * Se han definido índices sobre campos no existentes
    *
    */
   public static final long EC_ARCH_IDXS_DEL_FIELDS  = EC_PREFIX + 16;
   
   /**
    * Parámetro no válido
    */
   public static final long EC_ARCH_PARAM_NO_VALID = EC_PREFIX + 17;
   
   /**
    * El identificar del padre no es válido
    */
   public static final long EC_PARENT_NO_ID = EC_PREFIX + 18;
   
   /**
    * Existen campo que han sido modificados y tiene validación
    */
   public static final long EC_ARCH_EXISTS_FLDS_WITH_VALIDATION = EC_PREFIX + 19;
   
   /**
    * El permiso no puede ser asignado sobre el archivador, aseguresé que el
    * receptor se le puede asignar el permiso
    */
   public static final long EC_ARCH_CANNT_PERM = EC_PREFIX + 20;
   
   /**
    * El índice ya existe
    */
   public static final long EC_IDX_EXISTS = EC_PREFIX + 21;
   
   /**
    * La longitud de algún campo de tipo texto no es válida
    */
   public static final long EC_ARCH_FLD_TXT_BAD_LEN  = EC_PREFIX + 22;
  
   /**
    * El nombre del campo no es válido
    */
   public static final long EC_FLD_NAME_NO_VALID = EC_PREFIX + 23;
   
   /**
    * El nombre del índice no es válido
    */
   public static final long EC_IDX_NAME_NO_VALID = EC_PREFIX + 24;
   

}
