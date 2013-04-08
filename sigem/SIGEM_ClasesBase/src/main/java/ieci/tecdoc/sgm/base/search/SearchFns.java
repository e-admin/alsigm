
package ieci.tecdoc.sgm.base.search;

import ieci.tecdoc.sgm.base.dbex.DbColumnDef;
import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dyndao.*;

public final class SearchFns
{
     
   private SearchFns()
   {
   }
   
   public static SearchResult executeQuery(DbColumnDef[] selectColsDef,
                                           SearchCondition query, DbConnection dbConn)
                              throws Exception
   {
      String qual; 
      String tblName;
      int    dbEngine;

      dbEngine = dbConn.getEngine();
      
      qual    = query.getSqlQual(dbEngine);
      tblName = query.getTblName();
      
      return executeQuery(tblName, selectColsDef, qual, dbConn);      
      
   }
   
   public static SearchResult executeQuery(String tblName, DbColumnDef[] selectColsDef,
                                           String qual, DbConnection dbConn)
                              throws Exception
   {
      SearchResult rs = null;
      DynDaoRs     rows;
      
      DynDaoTbl tbl = new DynDaoTbl(tblName, selectColsDef);
      
      rows = tbl.selectRows(selectColsDef, qual, dbConn); 
      
      rs = new SearchResult(rows); 
      
      return rs;
      
   } 
      
} // class
