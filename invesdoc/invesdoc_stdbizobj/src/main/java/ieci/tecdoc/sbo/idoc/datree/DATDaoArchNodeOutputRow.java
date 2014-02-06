
package ieci.tecdoc.sbo.idoc.datree;

import ieci.tecdoc.core.db.*;

public final class DATDaoArchNodeOutputRow implements DbOutputRecord
{
   public int      m_id;
   public String   m_name;
   public int      m_accessType;
   public int      m_acsId;
      
   public DATDaoArchNodeOutputRow()
   {      
   }   
   
   public void getStatementValues(DbOutputStatement stmt) throws Exception
   {      
                  
      int i = 1;
      
      m_id         = stmt.getLongInteger(i++);
      m_name       = stmt.getShortText(i++); 
      m_accessType = stmt.getLongInteger(i++);
      m_acsId      = stmt.getLongInteger(i++);      
      
   }
   
   public String toString()
   {

      StringBuffer buffer = new StringBuffer();
      buffer.append("DATDaoArchNodeOutputRow [");
      buffer.append("id = ").append(m_id);
      buffer.append(", name = ").append(m_name);  
      buffer.append(", accessType = ").append(m_accessType);  
      buffer.append(", acsId = ").append(m_acsId);      
      buffer.append("]");
      return buffer.toString();

   }
   
} // class
