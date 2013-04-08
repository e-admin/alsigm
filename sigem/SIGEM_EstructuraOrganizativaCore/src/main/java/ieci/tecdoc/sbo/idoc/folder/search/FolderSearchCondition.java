package ieci.tecdoc.sbo.idoc.folder.search;

import ieci.tecdoc.sgm.base.dbex.DbConnection;

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
    *           <li>DbEngine.POSTGRESQL_STR
    * @return condición sql 
    * @throws Exception si se produce algún error en la obtención de la condición sql
    */
   public String getSqlCondition(DbConnection dbConn, int dbEngine) throws Exception;

} // class
