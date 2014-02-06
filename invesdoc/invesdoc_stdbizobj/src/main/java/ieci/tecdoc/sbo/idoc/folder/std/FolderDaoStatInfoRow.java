
package ieci.tecdoc.sbo.idoc.folder.std;

import ieci.tecdoc.core.db.*;
import ieci.tecdoc.sbo.idoc.dao.*;

public final class FolderDaoStatInfoRow implements DaoOutputRow
{
   private  int   m_stat;
   private  int   m_userId;
  
   public FolderDaoStatInfoRow()
   {      
   }   
   
   public void getStatementValues(DbOutputStatement stmt) throws Exception
   {      
                  
      int i = 1;
      
      m_stat   = stmt.getLongInteger(i++);     
      m_userId = stmt.getLongInteger(i++);        
   }
   
   public String getColumnNames() throws Exception
   {   
      
      StringBuffer tbdr;
            
      tbdr = new StringBuffer();

      tbdr.append(DaoFdrStatTbl.getStatColName(false));      
      tbdr.append(",").append(DaoFdrStatTbl.getUserIdColName(false));  
          
      return tbdr.toString();
      
   } 
   
   public int getStat()
   {
      return m_stat;
   }
   
   public int getUserId()
   {
      return m_userId;
   }
   
   public String toString()
   {

      StringBuffer buffer = new StringBuffer();
      buffer.append("FolderDaoStatInfoRow [");
      buffer.append("stat = ").append(m_stat);
      buffer.append(", userId = ").append(m_userId);  
      buffer.append("]");
      return buffer.toString();

   }
   
} // class
