
package ieci.tecdoc.sbo.idoc.folder.document;

import java.util.ArrayList;
import ieci.tecdoc.core.db.*;
import ieci.tecdoc.sbo.idoc.dao.*;

public final class FolderDaoDocTokenRows implements DaoOutputXRows
{
   private ArrayList  m_al;
     
   public FolderDaoDocTokenRows()
   {
      m_al = new ArrayList();       
   }
   
   public int count()
   {
      return m_al.size();
   }
   
   public FolderDaoDocTokenRow getRecord(int index)
   {
      return (FolderDaoDocTokenRow) m_al.get(index);
   }
   
   public DbOutputRecord newRecord()
   {
      
      FolderDaoDocTokenRow rec = new FolderDaoDocTokenRow();
      
      m_al.add(rec);
      
      return rec;
      
   }
   
   public String getColumnNames(String tblName) throws Exception
   {   
      
      StringBuffer tbdr;
      DaoDocTbl tbl = new DaoDocTbl(tblName);
      
      tbdr = new StringBuffer();

      tbdr.append(tbl.getIdColName(false));      
      tbdr.append(",").append(tbl.getClfIdColName(false));

      return tbdr.toString();
      
   }
   
   public int getRecordClfId(int index)
   {
      return getRecord(index).getClfId();
   }

   public int getRecordId(int index)
   {
      return getRecord(index).getId();
   }
   
   
   
} // class
