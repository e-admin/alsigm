
package es.ieci.tecdoc.isicres.admin.sbo.idoc.api;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbConnection;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.folder.search.FolderSearchCondition;

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
