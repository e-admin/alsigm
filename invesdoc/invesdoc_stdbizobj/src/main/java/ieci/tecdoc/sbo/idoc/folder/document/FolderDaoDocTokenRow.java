
package ieci.tecdoc.sbo.idoc.folder.document;

import ieci.tecdoc.core.db.*;

public final class FolderDaoDocTokenRow implements DbOutputRecord
{
   private int     m_id;    
   private int     m_clfId;
   
   public FolderDaoDocTokenRow()
   {      
   }   
   
   public void getStatementValues(DbOutputStatement stmt) throws Exception
   {      
                  
      int i = 1;
      
      m_id        = stmt.getLongInteger(i++);        
      m_clfId     = stmt.getLongInteger(i++); 
                 
   }
   
   public int getId()
   {
      return m_id;      
   }   
   
   public int getClfId()
   {
      return m_clfId;      
   }    
   
   public String toString()
   {

      StringBuffer buffer = new StringBuffer();
      buffer.append("FolderDaoDocTokenRow [");
      buffer.append("Id = ").append(m_id);     
      buffer.append(", clfId = ").append(m_clfId);      
      buffer.append("]");
      return buffer.toString();

   }
   
} // class
