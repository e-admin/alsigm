
package es.ieci.tecdoc.isicres.admin.sbo.idoc.datree;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbOutputRecord;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbOutputStatement;

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
