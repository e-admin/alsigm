
package ieci.tecdoc.core.search;

import ieci.tecdoc.core.dyndao.*;
import ieci.tecdoc.core.db.DbColumnDef;
import ieci.tecdoc.core.db.DbConnection;

public final class SearchFns
{
     
   private SearchFns()
   {
   }
   
   public static SearchResult executeQuery(DbColumnDef[] selectColsDef,
                                           SearchCondition query)
                              throws Exception
   {
      String qual; 
      String tblName;
      int    dbEngine;

      dbEngine = DbConnection.getEngine();
      
      qual    = query.getSqlQual(dbEngine);
      tblName = query.getTblName();
      
      return executeQuery(tblName, selectColsDef, qual);      
      
   }
   
   public static SearchResult executeQuery(String tblName, DbColumnDef[] selectColsDef,
                                           String qual)
                              throws Exception
   {
      SearchResult rs = null;
      DynDaoRs     rows;
      
      DynDaoTbl tbl = new DynDaoTbl(tblName, selectColsDef);
      
      rows = tbl.selectRows(selectColsDef, qual); 
      
      rs = new SearchResult(rows); 
      
      return rs;
      
   } 
      
} // class
