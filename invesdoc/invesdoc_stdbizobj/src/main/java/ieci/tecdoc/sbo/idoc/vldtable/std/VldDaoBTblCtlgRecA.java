
package ieci.tecdoc.sbo.idoc.vldtable.std;

import ieci.tecdoc.core.db.*;

public final class VldDaoBTblCtlgRecA implements DbOutputRecord
{
   public int      m_id;
   public String   m_name;
   public String      m_def;

   public VldDaoBTblCtlgRecA()
   {      
   }   
   
   public void getStatementValues(DbOutputStatement stmt) throws Exception
   {      
                  
      int i = 1;
      
      m_id         = stmt.getLongInteger(i++);
      m_name       = stmt.getShortText(i++); 
      m_def        = stmt.getLongText(i++) ;

   }
   
   public String toString()
   {

      StringBuffer buffer = new StringBuffer();
      buffer.append("AppDaoDirRecO [");
      buffer.append("id = ").append(m_id);
      buffer.append(", name = ").append(m_name);  
      buffer.append(", defs = ").append(m_def);
      buffer.append("]");
      return buffer.toString();

   }
   
} // class
