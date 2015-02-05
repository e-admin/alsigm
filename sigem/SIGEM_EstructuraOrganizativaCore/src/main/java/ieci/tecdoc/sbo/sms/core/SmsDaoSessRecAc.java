
package ieci.tecdoc.sbo.sms.core;

import ieci.tecdoc.sgm.base.dbex.DbInputRecord;
import ieci.tecdoc.sgm.base.dbex.DbInputStatement;
import ieci.tecdoc.sgm.base.dbex.DbOutputRecord;
import ieci.tecdoc.sgm.base.dbex.DbOutputStatement;

import java.util.Date;


public final class SmsDaoSessRecAc implements DbInputRecord, DbOutputRecord
{
   
   public int    m_id; 
   public String m_appId; 
   public int    m_userId;    
   public Date   m_crtTs;
   
   public SmsDaoSessRecAc()
   {
   }
   
   public void setStatementValues(DbInputStatement stmt) throws Exception
   {
      
      int i = 1;
      
      stmt.setLongInteger(i++, m_id);  
      stmt.setShortText(i++, m_appId); 
      stmt.setLongInteger(i++, m_userId);      
      stmt.setDateTime(i++, m_crtTs);   
      
   }
   
   public void getStatementValues(DbOutputStatement stmt) throws Exception
   {      
            
      int i = 1;
      
      m_id     = stmt.getLongInteger(i++);
      m_appId  = stmt.getShortText(i++);
      m_userId = stmt.getLongInteger(i++);       
      m_crtTs  = stmt.getDateTime(i++);    
      
   }
   
} // class
