
package ieci.tecdoc.sbo.idoc.datree;

import ieci.tecdoc.core.db.*;
import java.util.ArrayList;
import ieci.tecdoc.sbo.idoc.dao.*;

public final class DATDaoArchNodeRows implements DaoOutputRows
{
   private ArrayList  m_al;
   
   public DATDaoArchNodeRows()
   {    
      m_al = new ArrayList();  
   }
   
   public int count()
   {
      return m_al.size();
   }
    
   public DATDaoArchNodeOutputRow getRecord(int index)
   {
      return (DATDaoArchNodeOutputRow) m_al.get(index);
   }
   
   public DbOutputRecord newRecord()
   {
      
      DATDaoArchNodeOutputRow rec = new DATDaoArchNodeOutputRow();
      
      m_al.add(rec);
      
      return rec;
      
   }
   
   public String getColumnNames() throws Exception
   {   
      
      StringBuffer tbdr;
      
      tbdr = new StringBuffer();

      tbdr.append(DaoArchHdrTbl.getIdColName(false));      
      tbdr.append(",").append(DaoArchHdrTbl.getNameColName(false));
      tbdr.append(",").append(DaoArchHdrTbl.getAccessTypeColName(false));
      tbdr.append(",").append(DaoArchHdrTbl.getAcsIdColName(false));

      return tbdr.toString();
      
   }
      
   
} // class
