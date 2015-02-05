
package ieci.tecdoc.core.search;

import ieci.tecdoc.core.dyndao.*;

import ieci.tecdoc.sgm.base.dbex.DbColumnDef;
import ieci.tecdoc.sgm.base.dbex.DbConnection;


public final class SearchFns
{
     
   private SearchFns()
   {
   }
   
   public static SearchResult executeQuery(DbColumnDef[] selectColsDef,
                                           SearchCondition query, String entidad)
                              throws Exception
   {
      String qual; 
      String tblName;
      int    dbEngine;

      DbConnection dbConn=new DbConnection();
      
      dbEngine = dbConn.getEngine();
      
      qual    = query.getSqlQual(dbEngine);
      tblName = query.getTblName();
      
      return executeQuery(tblName, selectColsDef, qual, entidad);      
      
   }
   
   public static SearchResult executeQuery(String tblName, DbColumnDef[] selectColsDef,
                                           String qual, String entidad)
                              throws Exception
   {
      SearchResult rs = null;
      DynDaoRs     rows;
      
      DynDaoTbl tbl = new DynDaoTbl(tblName, selectColsDef);
      
      rows = tbl.selectRows(selectColsDef, qual, entidad); 
      
      rs = new SearchResult(rows); 
      
      return rs;
      
   } 
      
} // class
