
package ieci.tecdoc.sbo.idoc.dao;

import ieci.tecdoc.sgm.base.dbex.DbInputRecord;
import ieci.tecdoc.sgm.base.dbex.DbInputStatement;
import ieci.tecdoc.sgm.base.dbex.DbOutputRecord;
import ieci.tecdoc.sgm.base.dbex.DbOutputStatement;

public final class DaoXNIdRow implements DbInputRecord, DbOutputRecord
{
   public  int  m_type; 
   public  int  m_parentId;  
   public  int  m_id;
      
   public DaoXNIdRow()
   {        
   } 
   
   public void setStatementValues(DbInputStatement stmt) throws Exception
   {
      
      int i = 1;
      
      stmt.setLongInteger(i++, m_type);
      stmt.setLongInteger(i++, m_parentId);     
      stmt.setLongInteger(i++, m_id); 
      
   } 
   
   public void getStatementValues(DbOutputStatement stmt) throws Exception
   {
      
      int i = 1;
      
      m_type     = stmt.getLongInteger(i++);
      m_parentId = stmt.getLongInteger(i++);     
      m_id       = stmt.getLongInteger(i++); 
      
   }   
   
   
   
} // class
