
package ieci.tecdoc.sbo.idoc.api;

import ieci.tecdoc.sbo.idoc.folder.search.FolderSearchCondition;

public class FolderSearchCondObject
                                              
{ 
   
   FolderSearchCondition m_condition;
   int                   m_dbEngine;
   
   
   protected FolderSearchCondObject(int dbEngine, FolderSearchCondition cond) throws Exception
   {
      m_condition = cond;
      m_dbEngine  = dbEngine;            
   } 
  
   public String getSqlCondition() throws Exception
   {
      String sqlCond = m_condition.getSqlCondition(m_dbEngine);
      
      return sqlCond;
   }

   protected FolderSearchCondition getSearchCondition() 
   {      
      return m_condition;
   }
    
   
} // class
