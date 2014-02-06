package ieci.tecdoc.sbo.idoc.folder.search;

/**
 * Interfaz que representa una condición de búsqueda de carpetas dentro de un
 * archivador
 */
public interface FolderSearchCondition
{

   /**
    * Devuelve la condición sql que representa la condición de búsqueda de
    * carpetas dentro de un archivador
    * 
    * @param dbEngine
    *           tipo de base de datos:
    *           <li>DbEngine.SQLSERVER_STR
    *           <li>DbEngine.ORACLE_STR
    *           <li>DbEngine.MYSQL_STR
    * @return condición sql 
    * @throws Exception si se produce algún error en la obtención de la condición sql
    */
   public String getSqlCondition(int dbEngine) throws Exception;

} // class
