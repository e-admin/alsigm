package es.ieci.tecdoc.isicres.admin.estructura.dao;

/**
 * Proporciona toda la funcionalidad necesaria para manejar árbol de
 * directorios invesDoc. 
 */
public interface Directories
{
   /**
    * Obtiene información básica de directorios de un mismo padre
    * @param parentDirId Identificador del padre
    * @return La información mencionada
    * @throws Exception Errores
    * @see BasicDirectories
    */
   public BasicDirectories getChildrenFormDirectory(int dirId, String entidad) throws Exception;
   
}
   
