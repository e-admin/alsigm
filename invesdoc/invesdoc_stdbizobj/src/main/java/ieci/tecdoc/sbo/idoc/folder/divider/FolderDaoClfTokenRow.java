
package ieci.tecdoc.sbo.idoc.folder.divider;

import ieci.tecdoc.core.db.*;

public final class FolderDaoClfTokenRow implements DbOutputRecord
{
   private int     m_id;
   private String  m_name;
   private int     m_type;
   private int     m_parentId;
      
   public FolderDaoClfTokenRow()
   {      
   }   
   
   public void getStatementValues(DbOutputStatement stmt) throws Exception
   {      
                  
      int i = 1;
      
      m_id       = stmt.getLongInteger(i++);     
      m_name     = stmt.getShortText(i++);
      m_type     = stmt.getLongInteger(i++);  
      m_parentId = stmt.getLongInteger(i++);  
            
   }
   
   public int getId()
   {
      return m_id;      
   }
   
   public String getName()
   {
      return m_name;
   }
   
   public int getType()
   {
      return m_type;      
   } 
   
   public int getParentId()
   {
      return m_parentId;      
   }  
   
   public String toString()
   {

      StringBuffer buffer = new StringBuffer();
      buffer.append("FolderDaoClfTokenRow [");
      buffer.append("Id = ").append(m_id);
      buffer.append(", name = ").append(m_name); 
      buffer.append(", type = ").append(m_type);
      buffer.append(", parentId = ").append(m_parentId);        
      buffer.append("]");
      return buffer.toString();

   }
   
} // class
