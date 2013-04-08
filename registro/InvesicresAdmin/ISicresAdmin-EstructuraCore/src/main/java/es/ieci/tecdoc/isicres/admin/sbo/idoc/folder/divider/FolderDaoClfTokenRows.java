
package es.ieci.tecdoc.isicres.admin.sbo.idoc.folder.divider;


import java.util.ArrayList;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbOutputRecord;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.dao.DaoClfTbl;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.dao.DaoOutputXRows;

public final class FolderDaoClfTokenRows implements DaoOutputXRows
{
   private ArrayList  m_al;
     
   public FolderDaoClfTokenRows()
   {
      m_al = new ArrayList();       
   }
   
   public int count()
   {
      return m_al.size();
   }
   
   public FolderDaoClfTokenRow getRecord(int index)
   {
      return (FolderDaoClfTokenRow) m_al.get(index);
   }
   
   public DbOutputRecord newRecord()
   {
      
      FolderDaoClfTokenRow rec = new FolderDaoClfTokenRow();
      
      m_al.add(rec);
      
      return rec;
   }
      
      
   public String getColumnNames(String tblName) throws Exception
   {   
      
      StringBuffer tbdr;
      DaoClfTbl tbl = new DaoClfTbl(tblName);
      
      tbdr = new StringBuffer();

      tbdr.append(tbl.getIdColName(false));      
      tbdr.append(",").append(tbl.getNameColName(false));  
      tbdr.append(",").append(tbl.getTypeColName(false)); 
      tbdr.append(",").append(tbl.getParentIdColName(false));     

      return tbdr.toString();
      
   }
    
   
} // class
