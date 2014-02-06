
package ieci.tecdoc.sbo.idoc.datree;

import ieci.tecdoc.core.db.*;

public final class DATDaoDirNodeOutputRow implements DbOutputRecord
{
   public int      m_id;
   public String   m_name;   
      
   public DATDaoDirNodeOutputRow()
   {      
   }   
   
   public void getStatementValues(DbOutputStatement stmt) throws Exception
   {      
                  
      int i = 1;
      
      m_id         = stmt.getLongInteger(i++);
      m_name       = stmt.getShortText(i++);    
      
   }  
   
   public String toString()
   {

      StringBuffer buffer = new StringBuffer();
      buffer.append("DATDaoDirNodeOutputRow [");
      buffer.append("id = ").append(m_id);
      buffer.append(", name = ").append(m_name);         
      buffer.append("]");
      return buffer.toString();

   }
   
} // class
