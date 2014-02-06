package ieci.tecdoc.idoc.admin.api.archive;

/**
 * Proporciona toda la funcionalidad necesaria para manejar archivadores invesDoc. 
 */
public interface Archives
{
   /**
    * Obtiene información básica de archivadores de un mismo padre
    * @param parentDirId Identificador del padre
    * @return La información mencionada
    * @throws Exception Errores
    * @see BasicArchives
    */
   public BasicArchives getArchivesByDirectory(int parentDirId) throws Exception;
   
}
