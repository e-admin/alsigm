
package es.ieci.tecdoc.isicres.admin.sbo.idoc.folder.std;


import java.util.Date;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbInputStatement;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.dao.DaoFdrHdrTbl;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.dao.DaoInputXRow;


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
