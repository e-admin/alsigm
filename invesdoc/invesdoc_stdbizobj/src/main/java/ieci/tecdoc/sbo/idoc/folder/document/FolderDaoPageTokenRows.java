
package ieci.tecdoc.sbo.idoc.folder.document;

import java.util.ArrayList;
import ieci.tecdoc.core.db.*;
import ieci.tecdoc.sbo.idoc.dao.*;

public final class FolderDaoPageTokenRows implements DaoOutputXRows
{
   private ArrayList  m_al;
     
   public FolderDaoPageTokenRows()
   {
      m_al = new ArrayList();       
   }
   
   public int count()
   {
      return m_al.size();
   }
   
   public FolderDaoPageTokenRow getRecord(int index)
   {
      return (FolderDaoPageTokenRow) m_al.get(index);
   }
   
   public DbOutputRecord newRecord()
   {
      
      FolderDaoPageTokenRow rec = new FolderDaoPageTokenRow();
      
      m_al.add(rec);
      
      return rec;
      
   }
   
   public String getColumnNames(String tblName) throws Exception
   {   
      
      StringBuffer tbdr;
      DaoPageTbl   tbl = new DaoPageTbl(tblName);
      
      tbdr = new StringBuffer();

      tbdr.append(tbl.getIdColName(false));      
      tbdr.append(",").append(tbl.getNameColName(false));
      tbdr.append(",").append(tbl.getSortOrderColName(false));
      tbdr.append(",").append(tbl.getDocIdColName(false));
      tbdr.append(",").append(tbl.getFileIdColName(false));
      tbdr.append(",").append(tbl.getLocColName(false));
      tbdr.append(",").append(tbl.getAnnIdColName(false));

      return tbdr.toString();
      
   }
   
   
   
} // class
