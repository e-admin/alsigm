
package ieci.tecdoc.sbo.idoc.dao;

import ieci.tecdoc.core.db.*;

public final class DaoArchDetRow implements DbOutputRecord, DbInputRecord
{
   public int      m_archId;
   public int      m_detType;
   public String   m_detVal;  
      
   public DaoArchDetRow()
   {      
   }   
   
   public void getStatementValues(DbOutputStatement stmt) throws Exception
   {      
                  
      int i = 1;
      
      m_archId     = stmt.getLongInteger(i++);     
      m_detType    = stmt.getLongInteger(i++);
      m_detVal     = stmt.getLongText(i++);      
      
   }
   
   public void setStatementValues(DbInputStatement stmt) throws Exception
   {      
                  
      int i = 1;
      
      stmt.setLongInteger(i++, m_archId);
      stmt.setLongInteger(i++, m_detType);  
      stmt.setLongText(i++, m_detVal);      
      
   }
   
   public String toString()
   {

      StringBuffer buffer = new StringBuffer();
      buffer.append("DaoArchDetRow [");
      buffer.append("archId = ").append(m_archId);
      buffer.append(", detType = ").append(m_detType);  
      buffer.append(", detVal = ").append(m_detVal);         
      buffer.append("]");
      return buffer.toString();

   }
   
} // class
