
package ieci.tecdoc.sbo.idoc.dao;

import ieci.tecdoc.core.db.*;
import java.util.Date;

public final class DaoPageAnnsRow implements DbInputRecord, DbOutputRecord
{
   public  int    m_id; 
   public  String m_data;   
   public  int    m_crtUsrId;
   public Date    m_crtTs;
   public  int    m_updUsrId;    
   public Date    m_updTs;    
      
   public DaoPageAnnsRow()
   {        
   } 
   
   public void setStatementValues(DbInputStatement stmt) throws Exception
   {
      
      int i = 1;
      
      stmt.setLongInteger(i++, m_id);
      stmt.setLongText(i++, m_data);      
      stmt.setLongInteger(i++, m_crtUsrId);
      stmt.setDateTime(i++, m_crtTs);
      stmt.setLongInteger(i++, m_updUsrId);  
      stmt.setDateTime(i++, m_updTs);   
           
   } 
   
   public void getStatementValues(DbOutputStatement stmt) throws Exception
   {
      
      int i = 1;
      
      m_id        = stmt.getLongInteger(i++);
      m_data      = stmt.getLongText(i++);  
      m_crtUsrId  = stmt.getLongInteger(i++);
      m_crtTs     = stmt.getDateTime(i++);
      m_updUsrId  = stmt.getLongInteger(i++);  
      m_updTs     = stmt.getDateTime(i++);
     
   }   
   
} // class
