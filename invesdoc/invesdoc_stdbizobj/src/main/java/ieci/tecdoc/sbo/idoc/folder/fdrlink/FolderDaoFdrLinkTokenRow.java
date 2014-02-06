
package ieci.tecdoc.sbo.idoc.folder.fdrlink;

import ieci.tecdoc.core.db.*;

public final class FolderDaoFdrLinkTokenRow implements DbOutputRecord
{
   private int     m_id;
   private String  m_name;
   private int     m_cntrClfId;
   private int     m_srvrArchId;
   private int     m_srvrFdrId;
      
   public FolderDaoFdrLinkTokenRow()
   {      
   }   
   
   public void getStatementValues(DbOutputStatement stmt) throws Exception
   {      
                  
      int i = 1;
      
      m_id          = stmt.getLongInteger(i++);     
      m_name        = stmt.getShortText(i++);
      m_cntrClfId   = stmt.getLongInteger(i++);  
      m_srvrArchId  = stmt.getLongInteger(i++); 
      m_srvrFdrId   = stmt.getLongInteger(i++); 
            
   }
   
   public int getId()
   {
      return m_id;      
   }
   
   public String getName()
   {
      return m_name;
   }
   
   public int getCntrClfId()
   {
      return m_cntrClfId;      
   } 
   
   public int getSrvrArchId()
   {
      return m_srvrArchId;      
   } 
   
   public int getSrvrFdrId()
   {
      return m_srvrFdrId;      
   } 
   
   public String toString()
   {

      StringBuffer buffer = new StringBuffer();
      buffer.append("FolderDaoFdrLinkTokenRow [");
      buffer.append("Id = ").append(m_id);
      buffer.append(", name = ").append(m_name); 
      buffer.append(", cntrClfId = ").append(m_cntrClfId);
      buffer.append(", srvrArchId = ").append(m_srvrArchId);
      buffer.append(", srvrFdrId = ").append(m_srvrFdrId);        
      buffer.append("]");
      return buffer.toString();

   }
   
} // class
