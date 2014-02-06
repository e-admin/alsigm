
package ieci.tecdoc.sbo.idoc.folder.field;

import ieci.tecdoc.core.db.*;

public final class FolderDaoExtFldsTokenRow implements DbOutputRecord
{
   private int     m_fldId;
   private String  m_text;
      
   public FolderDaoExtFldsTokenRow()
   {      
   }   
   
   public void getStatementValues(DbOutputStatement stmt) throws Exception
   {      
                  
      int i = 1;
      
      m_fldId = stmt.getLongInteger(i++);     
      m_text  = stmt.getLongText(i++);
            
   }
   
   public int getFldId()
   {
      return m_fldId;      
   }
   
   public String getText()
   {
      return m_text;
   }   
   
   public String toString()
   {

      StringBuffer buffer = new StringBuffer();
      buffer.append("FolderDaoExtFldsTokenRow [");
      buffer.append("fldId = ").append(m_fldId);
      buffer.append(", text = ").append(m_text);         
      buffer.append("]");
      return buffer.toString();

   }
   
} // class
