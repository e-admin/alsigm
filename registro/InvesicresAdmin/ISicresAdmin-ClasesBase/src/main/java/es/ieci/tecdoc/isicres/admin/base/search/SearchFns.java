
package es.ieci.tecdoc.isicres.admin.base.search;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbColumnDef;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.base.dyndao.*;

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
