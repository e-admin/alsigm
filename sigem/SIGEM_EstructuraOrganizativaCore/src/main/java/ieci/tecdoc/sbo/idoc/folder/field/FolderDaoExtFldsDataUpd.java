
package ieci.tecdoc.sbo.idoc.folder.field;

import ieci.tecdoc.sbo.idoc.dao.DaoExtFldsTbl;
import ieci.tecdoc.sbo.idoc.dao.DaoInputXRow;
import ieci.tecdoc.sgm.base.dbex.DbInputStatement;

import java.util.Date;


public final class FolderDaoExtFldsDataUpd implements DaoInputXRow
{
   public  String m_text;
   public  Date   m_ts;
      
   public FolderDaoExtFldsDataUpd()
   {        
   } 
   
   public void setStatementValues(DbInputStatement stmt) throws Exception
   {
      
      int i = 1;
         
      stmt.setLongText(i++, m_text);
      stmt.setDateTime(i++, m_ts);
      
   } 
   
   public String getColumnNames(String tblName) throws Exception
   {   
      
      StringBuffer  tbdr;
      DaoExtFldsTbl tbl = new DaoExtFldsTbl(tblName);
      
      tbdr = new StringBuffer();

      tbdr.append(tbl.getTextColName(false));      
      tbdr.append(",").append(tbl.getTSColName(false));  
            
      return tbdr.toString();
      
   }    
   
   
   
} // class
