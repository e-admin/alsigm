package ieci.tecdoc.idoc.admin.api.archive;

/**
 * Proporciona toda la funcionalidad necesaria para manejar permisos sobre 
 * archivadores invesDoc. 
 */

public interface ArchivePerms
{
   
   /**
    * Añade los permisos especificados 
    * 
    * @param destType Destinatario del permiso (departamento, usuario, grupo)
    * 						(ej: Defs.DESTINATION_USER)
    * @param destId Identificador del destinatario
    * @param acsId Identificador de objeto sobre el que se añade el permiso 
    * 					(ej:AcsId del archivador)
    * @param perms Permisos (ej: Defs.OBJ_PERM_CREATION |Defs.OBJ_PERM_UPDATE)
    * @throws Exception Errores
    */
   public void addPerms(int destType, int destId, int acsId, int perms ) 
					throws Exception;
   
   
   /**
    * Elimina los permisos especificador
    * 
    * @param destType Destinatario del permiso (departamento, usuario, grupo)
    * 						(ej: Defs.DESTINATION_USER)
    * @param destId Identificador del destinatario 
    * @param archiId Identificador del archivador
    * @param perms Permisos (ej: Defs.OBJ_PERM_CREATION |Defs.OBJ_PERM_UPDATE)
    * @throws Exception Errores
    */
   public void deletePerms(int destType, int destId, int archId, int perms)
					throws Exception;
   
   
   /**
    * Obtiene los permisos.
    * 
    * @param destType Destinatario del permiso (departamento, usuario, grupo)
    * 						(ej: Defs.DESTINATION_USER)
    * @param destId Identificador del destinatario    
    * @param archId Identificador del archivador
    * 
    * @return Permisos (uno ó varios de:
    * 							Defs.OBJ_PERM_CREATION
    * 							Defs.OBJ_PERM_DELETION
    * 							Defs.OBJ_PERM_UPDATE
    * 							Defs.OBJ_PERM_QUERY 
    * @throws Exception Errores
    */   
   
   public int loadPerms(int destType, int destId, int archIdd)
   				throws Exception;
   
   
}
