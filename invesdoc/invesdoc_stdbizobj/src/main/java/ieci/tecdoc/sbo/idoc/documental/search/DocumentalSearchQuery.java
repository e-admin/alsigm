package ieci.tecdoc.sbo.idoc.documental.search;

import ieci.tecdoc.core.db.DbEngine;
import ieci.tecdoc.core.exception.IeciTdException;
import ieci.tecdoc.sbo.fss.core.FssDaoFtsTbl;
import ieci.tecdoc.sbo.fss.core.FssDaoVolVolTbl;
import ieci.tecdoc.sbo.idoc.folder.search.FolderSearchError;

import java.util.ArrayList;

/**
 * Clase que encapusula los filtros de búsqueda documental
 */

public final class DocumentalSearchQuery
{

   /**
    * Colección de objetos de tipo String. Cada uno de estos
    * objetos representa una condición de búsqueda
    */
   private ArrayList m_ftsConditions;;
   
   /**
    * Contructor
    * @param arch - referencia a un objeto de tipo ArchiveToken que cotiene información
    * del archivador
    * @see ArchiveToken
    */
   public DocumentalSearchQuery ()
   {
      m_ftsConditions = new ArrayList();
   }

   /**
    * Devuelve la condición sql que se utilizará como filtro en la búsqueda documental
    * @param dbEngine tipo de base de datos: 
    * <li> DbEngine.SQLSERVER_STR
    * <li> DbEngine.ORACLE_STR
    * <li> DbEngine.MYSQL_STR
    * @return condición sql 
    * @throws Exception - si se produce algún error en la obtención de la condición sql
    */
   public String getFtsSqlQual (int dbEngine, int [] idVisibleArchs) throws Exception
   {      
     String qual = null;
     if (dbEngine == DbEngine.ORACLE) 
       qual = getFtsSqlQualForOracle(idVisibleArchs);
     else if (dbEngine == DbEngine.SQLSERVER) 
       qual = getFtsSqlQualForSqlServer(idVisibleArchs);
     else
       throw new IeciTdException(FolderSearchError.EC_INVALID_PARAM, 
             FolderSearchError.EM_INVALID_PARAM);   
     return qual;
   }

   
   /**
    * Devuelve la condición sql que se utilizará como filtro en la búsqueda documental
    * para los ficheros que se guardan en la propia base de datos
    * @param dbEngine tipo de base de datos: 
    * <li> DbEngine.SQLSERVER_STR
    * <li> DbEngine.ORACLE_STR
    * <li> DbEngine.MYSQL_STR
    * @return condición sql 
    * @throws Exception - si se produce algún error en la obtención de la condición sql
    */
   public String getDbSqlQual (int dbEngine, int [] idVisibleArchs) throws Exception
   {
      String qual = null;
      if (dbEngine == DbEngine.ORACLE)
        qual = getDbSqlQualForOracle(idVisibleArchs);
      else if (dbEngine == DbEngine.SQLSERVER) 
        qual = getDbSqlQualForSqlServer(idVisibleArchs);
      else
        throw new IeciTdException(FolderSearchError.EC_INVALID_PARAM, 
              FolderSearchError.EM_INVALID_PARAM);      
      return qual;
   }

   /**
    * Añade una condición de búsqueda en contenido de documentos
    * @param condition expresión de búsqueda documental   
    */   
   public void addFTSSearchCondition(String condition) 
   {
      if (condition != null && ! "".equals (condition))
         m_ftsConditions.add (condition);
   }
 
   /**
    * Devuelve la condición sql que se utilizará como filtro en la búsqueda documental
    * @param dbEngine tipo de base de datos: 
    * <li> DbEngine.SQLSERVER_STR
    * <li> DbEngine.ORACLE_STR
    * <li> DbEngine.MYSQL_STR
    * @return condición sql 
    * @throws Exception - si se produce algún error en la obtención de la condición sql
    */
   public String getFtsSqlQualForOracle (int [] idVisibleArchs) throws Exception
   {
     StringBuffer sql = new StringBuffer ();

     /*
      * (CONTAINS (PATH, 'perro', 1) > 0)* AND EXTID1 IN (arch1, arch2, ...) 
      */
     String        ftsArchIdColName = FssDaoFtsTbl.getExtId1ColName(true);
     String        ftsPathColName   = FssDaoFtsTbl.getPathColName(true);

     if (idVisibleArchs != null && idVisibleArchs.length > 0) {
        sql.append(ftsArchIdColName).append( " IN (" );
        for (int i = 0; i < idVisibleArchs.length; i++) {
           if (i != 0) sql.append (',');
           sql.append (idVisibleArchs [i]);
        }
        sql.append (") ");
     }
     
     for (int i = 0; i < m_ftsConditions.size(); i++) {
        if (i != 0)
        {
           sql.append ("AND ");
        }
        else 
        {
           if (idVisibleArchs != null && idVisibleArchs.length > 0) {
              sql.append ("AND ");
           }
        }
        sql.append("CONTAINS (");
        sql.append(ftsPathColName);
        sql.append(", '" + m_ftsConditions.get (i) + "',1)>0 ");   
     } 

     if (sql.length() > 0) {
        sql.insert (0, "WHERE ");
     }
     
     return sql.toString();
   }
   
   /**
    * Devuelve la condición sql que se utilizará como filtro en la búsqueda documental
    * para los ficheros que se guardan en la propia base de datos
    * @param dbEngine tipo de base de datos: 
    * <li> DbEngine.SQLSERVER_STR
    * <li> DbEngine.ORACLE_STR
    * <li> DbEngine.MYSQL_STR
    * @return condición sql 
    * @throws Exception - si se produce algún error en la obtención de la condición sql
    */
   public String getDbSqlQualForOracle (int [] idVisibleArchs) throws Exception
   {

      StringBuffer sql = new StringBuffer ();

      /*
       * (CONTAINS (PATH, 'perro', 1) > 0)* AND EXTID1 IN (arch1, arch2, ...) 
       */
      String        ftsArchIdColName = FssDaoVolVolTbl.CD_EXTID1.getName ();
      String        ftsPathColName   = FssDaoVolVolTbl.CD_FILEVAL.getName ();

      if (idVisibleArchs != null) {
         sql.append(ftsArchIdColName).append( " IN (" );
         for (int i = 0; i < idVisibleArchs.length; i++) {
            if (i != 0) sql.append (',');
            sql.append (idVisibleArchs [i]);
         }
         sql.append (") ");
      }
      
      for (int i = 0; i < m_ftsConditions.size(); i++) {
         if (i != 0)
         {
            sql.append ("AND ");
         }
         else 
         {
            if (idVisibleArchs != null && idVisibleArchs.length > 0) {
               sql.append ("AND ");
            }
         }
         sql.append("CONTAINS (");
         sql.append(ftsPathColName);
         sql.append(", '" + m_ftsConditions.get (i) + "',1)>0 ");   
      } 

      if (sql.length() > 0) {
         sql.insert (0, "WHERE ");
      }
      
      return sql.toString();

   }
   
   public String getFtsSqlQualForSqlServer(int[] idVisibleArchs)
        throws Exception
  {
    StringBuffer sql = new StringBuffer();

    String ftsArchIdColName = FssDaoFtsTbl.getExtId1ColName(true);
    String ftsPathColName = FssDaoFtsTbl.getPathColName(true);

    /*
     * SELECT EXTID1, EXTID2, EXTID3 FROM IVOLFILEFTS WHERE EXTID1 IN (1,2,3)
     * AND IVOLFILEFTS.PATH IN ( SELECT PATH FROM OPENQUERY(FILESYSTEM,'SELECT
     * PATH FROM SCOPE() WHERE CONTAINS (CONTENTS,''pincha'')') )
     */

    if (idVisibleArchs != null && idVisibleArchs.length > 0)
    {
      sql.append(ftsArchIdColName).append(" IN (");
      for (int i = 0; i < idVisibleArchs.length; i++)
      {
        if (i != 0) sql.append(',');
        sql.append(idVisibleArchs[i]);
      }
      sql.append(") ");
    }

    for (int i = 0; i < m_ftsConditions.size(); i++)
    {
      if (i != 0)
      {
        sql.append("AND ");
      }
      else
      {
        if (idVisibleArchs != null && idVisibleArchs.length > 0)
        {
          sql.append("AND ");
        }
      }

      sql.append(ftsPathColName).append(" IN ");
      sql.append("(SELECT PATH FROM OPENQUERY(FILESYSTEM,'SELECT PATH FROM SCOPE() WHERE CONTAINS (CONTENTS,''" + m_ftsConditions.get(i) + "'')'))");
    } 
       
    if (sql.length() > 0)
    {
      sql.insert(0, "WHERE ");
    }

    return sql.toString();
  }
   
   public String getDbSqlQualForSqlServer(int[] idVisibleArchs) throws Exception
  {

    StringBuffer sql = new StringBuffer();

    /*
     * SELECT EXTID1, EXTID2, EXTID3 FROM IVOLVOLTBL WHERE EXTID1 IN (1,2,3) AND
     * CONTAINS(FILEVAL,'semana')
     */
    String ftsArchIdColName = FssDaoVolVolTbl.CD_EXTID1.getName();
    String ftsPathColName = FssDaoVolVolTbl.CD_FILEVAL.getName();
    String ftsTableName = FssDaoVolVolTbl.TN;

    if (idVisibleArchs != null)
    {
      sql.append(ftsTableName).append(".").append(ftsArchIdColName).append(
            " IN (");
      for (int i = 0; i < idVisibleArchs.length; i++)
      {
        if (i != 0) sql.append(',');
        sql.append(idVisibleArchs[i]);
      }
      sql.append(") ");
    }

    for (int i = 0; i < m_ftsConditions.size(); i++)
    {
      if (i != 0)
      {
        sql.append("AND ");
      }
      else
      {
        if (idVisibleArchs != null && idVisibleArchs.length > 0)
        {
          sql.append("AND ");
        }
      }
      sql.append("CONTAINS (").append(ftsTableName).append(".");
      sql.append(ftsPathColName);
      sql.append(", '" + m_ftsConditions.get(i) + "')");
    }

    if (sql.length() > 0)
    {
      sql.insert(0, "WHERE ");
    }

    return sql.toString();
  }
} // class
