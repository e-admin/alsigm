package ieci.tecdoc.idoc.admin.api.archive;

/**
 * Maneja los accesos a la gestión de directorios y archivadores.
 * 
 */

public interface ArchiveAccess
{
   /**
    * Obtiene si el usuario conectado puede ver el archivador.
    * @param connectedUserId Identificador del usuario conectado.
    * @param archId Identificador del archivador.
    * @return true / false
    * @throws Exception Errores
    */
   public boolean userCanViewArch (int connectedUserId, int archId) throws Exception;
   
   /**
    * Obtiene si el usuario conectado puede crear un archivador en el directorio especificado.
    * @param connectedUserId Identificador del usuario conectado.
    * @param dirId Identificador del directorio padre
    * @return true / false
    * @throws Exception Errores
    */
   public boolean userCanCreateArch(int connectedUserId, int dirId) throws Exception;
   
   /**
    * Obtiene si el usuario conectado puede eliminar el archivador.
    * @param connectedUserId Identificador del usuario conectado.
    * @param archId Identificador del archivador.
    * @return true / false
    * @throws Exception Errores
    */
   public boolean userCanDeleteArch(int connectedUserId, int archId) throws Exception;
   
   /**
    * Obtiene si el usuario conectado puede editar el archivador.
    * @param connectedUserId Identificador del usuario conectado.
    * @param archId Identificador del archivador.
    * @return true / false
    * @throws Exception Errores
    */
   public boolean userCanEditArch(int connectedUserId, int archId) throws Exception;
   
   /**
    * Obtiene si el usuario conectado puede mover el archivador al destino especificado.
    * @param connectedUserId Identificador del usuario conectado.
    * @param archId Identificador del archivador.
    * @param dstDirId Identificador del directorio destino.
    * @return true / false
    * @throws Exception Errores
    */
   public boolean userCanMoveArch(int connectedUserId, int archId, int dstDirId) throws Exception;
   
   /**
    * Obtiene si el usuario conectado puede asignar permisos sobre el archivador.
    * @param connectedUserId Identificador del usuario conectado.
    * @param archId Identificador del archivador.
    * @return true / false
    * @throws Exception Errores
    */
   public boolean userCanAddPermOnArch(int connectedUserId, int archId) throws Exception;
   
   /**
    * Obtiene si el usuario conectado puede ver el directorio.
    * @param connectedUserId Identificador del usuario conectado.
    * @param dirId Identificador del directorio.
    * @return true / false
    * @throws Exception Errores
    */
   public boolean userCanViewDir (int connectedUserId, int dirId) throws Exception;
   
   /**
    * Obtiene si el usuario conectado puede crear el directorio.
    * @param connectedUserId Identificador del usuario conectado.
    * @param dirId Identificador del directorio padre.
    * @return true / false
    * @throws Exception Errores
    */
   public boolean userCanCreateDir (int connectedUserId, int dirId) throws Exception;
   
   /**
    * Obtiene si el usuario conectado puede eliminar el directorio.
    * @param connectedUserId Identificador del usuario conectado.
    * @param dirId Identificador del directorio.
    * @return true / false
    * @throws Exception Errores
    */
   public boolean userCanDeleteDir (int connectedUserId, int dirId) throws Exception;
   
   /**
    * Obtiene si el usuario conectado puede editar el directorio.
    * @param connectedUserId Identificador del usuario conectado.
    * @param dirId Identificador del directorio.
    * @return true / false
    * @throws Exception Errores
    */
   public boolean userCanEditDir(int connectedUserId, int dirId) throws Exception;
   
   /**
    *  
    * Obtiene si el usuario conectado puede mover el directorio.
    * @param connectedUserId Identificador del usuario conectado.
    * @param dirId Identificador del directorio.
    * @param dstDirId Identificador del directorio destino.
    * @return true / false
    * @throws Exception Errores
    */
   public boolean userCanMoveDir (int connectedUserId, int dirId, int dstDirId) throws Exception;
   
}
