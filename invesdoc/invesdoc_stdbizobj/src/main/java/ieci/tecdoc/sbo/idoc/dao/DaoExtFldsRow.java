
package ieci.tecdoc.sbo.idoc.dao;

import ieci.tecdoc.core.db.*;
import java.util.Date;

public final class DaoExtFldsRow implements DbInputRecord, DbOutputRecord
{
   public  int    m_id; 
   public  int    m_fdrId;  
   public  int    m_fldId;
   public  String m_text;
   public  Date   m_ts;
      
   public DaoExtFldsRow()
   {        
   } 
   
   public void setStatementValues(DbInputStatement stmt) throws Exception
   {
      
      int i = 1;
      
      stmt.setLongInteger(i++, m_id);
      stmt.setLongInteger(i++, m_fdrId);     
      stmt.setLongInteger(i++, m_fldId);         
      stmt.setLongText(i++, m_text);
      stmt.setDateTime(i++, m_ts);
      
   }
   
   public void getStatementValues(DbOutputStatement stmt) throws Exception
   {
      
      int i = 1;
      
      m_id       = stmt.getLongInteger(i++);
      m_fdrId    = stmt.getLongInteger(i++);     
      m_fldId    = stmt.getLongInteger(i++);         
      m_text     = stmt.getLongText(i++); 
      m_ts       = stmt.getDateTime(i++); 
      
   }      
   
   
   
} // class
