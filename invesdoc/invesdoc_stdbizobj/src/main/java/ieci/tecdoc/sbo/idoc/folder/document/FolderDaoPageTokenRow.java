
package ieci.tecdoc.sbo.idoc.folder.document;

import ieci.tecdoc.core.db.*;

public final class FolderDaoPageTokenRow implements DbOutputRecord
{
   private int     m_id;
   private String  m_name;
   private int     m_sortOrder;
   private int     m_docId;
   private int     m_fileId;
   private String  m_loc;
   private int     m_annId;
      
   public FolderDaoPageTokenRow()
   {      
   }   
   
   public void getStatementValues(DbOutputStatement stmt) throws Exception
   {      
                  
      int i = 1;
      
      m_id        = stmt.getLongInteger(i++);     
      m_name      = stmt.getShortText(i++);
      m_sortOrder = stmt.getLongInteger(i++);  
      m_docId     = stmt.getLongInteger(i++); 
      m_fileId    = stmt.getLongInteger(i++); 
      m_loc       = stmt.getShortText(i++); 
      m_annId     = stmt.getLongInteger(i++);
            
   }
   
   public int getId()
   {
      return m_id;      
   }
   
   public String getName()
   {
      return m_name;
   }
   
   public int getSortOrder()
   {
      return m_sortOrder;      
   } 
   
   public int getDocId()
   {
      return m_docId;      
   } 
   
   public int getFileId()
   {
      return m_fileId;      
   }
   
   public String getLoc()
   {
      return m_loc;      
   } 
   
   public int getAnnId()
   {
      return m_annId;
   }
   
   public String toString()
   {

      StringBuffer buffer = new StringBuffer();
      buffer.append("FolderDaoPageTokenRow [");
      buffer.append("Id = ").append(m_id);
      buffer.append(", name = ").append(m_name); 
      buffer.append(", sortOrder = ").append(m_sortOrder);
      buffer.append(", docId = ").append(m_docId);
      buffer.append(", fileId = ").append(m_fileId);
      buffer.append(", loc = ").append(m_loc);        
      buffer.append("]");
      return buffer.toString();

   }
   
} // class
