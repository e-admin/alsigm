
package ieci.tecdoc.sbo.idoc.folder.document;

import ieci.tecdoc.core.db.*;
import java.util.Date;
import ieci.tecdoc.sbo.idoc.dao.*;

public final class FolderDaoPageRowUpd implements DaoInputXRow
{
   public  String m_name;    
   public  int    m_fileId;   
   public  String m_loc; 
   public  int    m_annId;
   public  int    m_updUsrId;    
   public  Date   m_updTs;   
      
   public FolderDaoPageRowUpd()
   {        
   } 
   
   public void setStatementValues(DbInputStatement stmt) throws Exception
   {
      
      int i = 1;
      
      stmt.setShortText(i++, m_name);
      stmt.setLongInteger(i++, m_fileId); 
      stmt.setShortText(i++, m_loc);
      stmt.setLongInteger(i++, m_annId);
      stmt.setLongInteger(i++, m_updUsrId);  
      stmt.setDateTime(i++, m_updTs); 
      
   } 
   
   public String getColumnNames(String tblName) throws Exception
   {   
      
      StringBuffer  tbdr;
      DaoPageTbl    tbl = new DaoPageTbl(tblName);
      
      tbdr = new StringBuffer();

      tbdr.append(tbl.getNameColName(false));      
      tbdr.append(",").append(tbl.getFileIdColName(false));
      tbdr.append(",").append(tbl.getLocColName(false));
      tbdr.append(",").append(tbl.getAnnIdColName(false));
      tbdr.append(",").append(tbl.getUpdUserIdColName(false));
      tbdr.append(",").append(tbl.getUpdTSColName(false));    
            
      return tbdr.toString();
      
   }   
   
   
   
} // class
