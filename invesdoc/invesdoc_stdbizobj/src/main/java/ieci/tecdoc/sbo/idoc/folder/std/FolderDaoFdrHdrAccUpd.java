
package ieci.tecdoc.sbo.idoc.folder.std;

import ieci.tecdoc.core.db.*;
import ieci.tecdoc.sbo.idoc.dao.*;
import java.util.Date;

public final class FolderDaoFdrHdrAccUpd implements DaoInputXRow
{
   public  int    m_accrId;
   public  Date   m_accDate;
   public  int    m_accCount;
      
   public FolderDaoFdrHdrAccUpd()
   {        
   } 
   
   public void setStatementValues(DbInputStatement stmt) throws Exception
   {
      
      int i = 1;
         
      stmt.setLongInteger(i++, m_accrId);
      stmt.setDateTime(i++, m_accDate);
      stmt.setLongInteger(i++, m_accCount);
      
   } 
   
   public String getColumnNames(String tblName) throws Exception
   {   
      
      StringBuffer tbdr;
      DaoFdrHdrTbl tbl = new DaoFdrHdrTbl(tblName);
      
      tbdr = new StringBuffer();

      tbdr.append(tbl.getAccessUserIdColName(false));      
      tbdr.append(",").append(tbl.getAccessTSColName(false));  
      tbdr.append(",").append(tbl.getAccessCountColName(false)); 
      
      return tbdr.toString();
      
   } 
   
   
   
} // class
