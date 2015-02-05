
package ieci.tecdoc.sbo.idoc.api;

import ieci.tecdoc.sbo.idoc.folder.search.FolderSearchCondition;
import ieci.tecdoc.sgm.base.dbex.DbConnection;

public class FolderSearchCondObject
                                              
{ 
   
   FolderSearchCondition m_condition;
   int                   m_dbEngine;
   
   
   protected FolderSearchCondObject(int dbEngine, FolderSearchCondition cond) throws Exception
   {
      m_condition = cond;
      m_dbEngine  = dbEngine;            
   } 
  
   public String getSqlCondition(DbConnection dbConn) throws Exception
   {
      String sqlCond = m_condition.getSqlCondition(dbConn, m_dbEngine);
      
      return sqlCond;
   }

   protected FolderSearchCondition getSearchCondition() 
   {      
      return m_condition;
   }
    
   
} // class
