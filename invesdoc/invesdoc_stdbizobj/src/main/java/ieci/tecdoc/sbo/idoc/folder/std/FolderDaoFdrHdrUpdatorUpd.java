
package ieci.tecdoc.sbo.idoc.folder.std;

import ieci.tecdoc.core.db.*;
import ieci.tecdoc.sbo.idoc.dao.*;
import java.util.Date;

public final class FolderDaoFdrHdrUpdatorUpd implements DaoInputXRow
{
   public  int  m_updUsrId;    
   public Date  m_updTs;
      
   public FolderDaoFdrHdrUpdatorUpd()
   {        
   } 
   
   public void setStatementValues(DbInputStatement stmt) throws Exception
   {
      
      int i = 1;
      
      stmt.setLongInteger(i++, m_updUsrId);  
      stmt.setDateTime(i++, m_updTs);       
      
   }
   
   public String getColumnNames(String tblName) throws Exception
   {   
      
      StringBuffer tbdr;
      DaoFdrHdrTbl tbl = new DaoFdrHdrTbl(tblName);
      
      tbdr = new StringBuffer();

      tbdr.append(tbl.getUpdUserIdColName(false));      
      tbdr.append(",").append(tbl.getUpdTSColName(false));  
            
      return tbdr.toString();
      
   }   
   
   
   
} // class
