
package ieci.tecdoc.sbo.idoc.vldtable.std;

import ieci.tecdoc.core.db.*;

public final class VldDaoVTblCtglRecA implements DbOutputRecord
{
   public int      m_id;
   public String   m_name;
   public int      m_btblId;
   public int      m_type;
   public String   m_info;

   public VldDaoVTblCtglRecA()
   {      
   }   
   
   public void getStatementValues(DbOutputStatement stmt) throws Exception
   {      
                  
      int i = 1;
      
      m_id         = stmt.getLongInteger(i++);
      m_name       = stmt.getShortText(i++); 
      m_btblId     = stmt.getLongInteger(i++);
      m_type       = stmt.getLongInteger(i++);
      m_info       = stmt.getLongText(i++) ;

   }
   
   public String toString()
   {

      StringBuffer buffer = new StringBuffer();
      buffer.append("AppDaoDirRecO [");
      buffer.append("id = ").append(m_id);
      buffer.append(", name = ").append(m_name);  
      buffer.append(", btblId = ").append(m_btblId);
      buffer.append(", type = ").append(m_type);      
      buffer.append(", info = ").append(m_info);
      buffer.append("]");
      return buffer.toString();

   }
   
} // class
