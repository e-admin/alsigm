
package ieci.tecdoc.sbo.idoc.folder.divider;

import ieci.tecdoc.core.db.*;
import java.util.Date;
import ieci.tecdoc.sbo.idoc.dao.*;

public final class FolderDaoClfRowUpd implements DaoInputXRow
{
   public  String  m_name;  
   public  int     m_updUsrId;    
   public  Date    m_updTs;   
      
   public FolderDaoClfRowUpd()
   {        
   } 
   
   public void setStatementValues(DbInputStatement stmt) throws Exception
   {
      
      int i = 1;
      
      stmt.setShortText(i++, m_name); 
      stmt.setLongInteger(i++, m_updUsrId);  
      stmt.setDateTime(i++, m_updTs); 
           
   }
   
   public String getColumnNames(String tblName) throws Exception
   {   
      
      StringBuffer  tbdr;
      DaoClfTbl     tbl = new DaoClfTbl(tblName);
      
      tbdr = new StringBuffer();

      tbdr.append(tbl.getNameColName(false));      
      tbdr.append(",").append(tbl.getUpdUserIdColName(false));
      tbdr.append(",").append(tbl.getUpdTSColName(false));  
            
      return tbdr.toString();
      
   }     
   
   
   
} // class
