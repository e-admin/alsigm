
package es.ieci.tecdoc.isicres.admin.sbo.idoc.folder.std;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbOutputStatement;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.dao.DaoFdrStatTbl;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.dao.DaoOutputRow;


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
