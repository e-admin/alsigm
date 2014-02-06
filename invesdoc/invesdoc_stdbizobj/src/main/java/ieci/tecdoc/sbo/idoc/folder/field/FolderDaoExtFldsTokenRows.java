
package ieci.tecdoc.sbo.idoc.folder.field;

import java.util.ArrayList;
import ieci.tecdoc.core.db.*;
import ieci.tecdoc.sbo.idoc.dao.*;

public final class FolderDaoExtFldsTokenRows implements DaoOutputXRows
{
   private ArrayList  m_al;
     
   public FolderDaoExtFldsTokenRows()
   {
      m_al = new ArrayList();       
   }
   
   public int count()
   {
      return m_al.size();
   }
   
   public FolderDaoExtFldsTokenRow getRecord(int index)
   {
      return (FolderDaoExtFldsTokenRow) m_al.get(index);
   }
   
   public DbOutputRecord newRecord()
   {
      
      FolderDaoExtFldsTokenRow rec = new FolderDaoExtFldsTokenRow();
      
      m_al.add(rec);
      
      return rec;
      
   }
   
   public String getColumnNames(String tblName) throws Exception
   {   
      
      StringBuffer tbdr;
      DaoExtFldsTbl tbl = new DaoExtFldsTbl(tblName);
      
      tbdr = new StringBuffer();

      tbdr.append(tbl.getFldIdColName(false));      
      tbdr.append(",").append(tbl.getTextColName(false));    

      return tbdr.toString();
      
   }
   
   
   
} // class
