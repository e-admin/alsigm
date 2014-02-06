
package ieci.tecdoc.sbo.idoc.dao;

import ieci.tecdoc.core.db.*;
import java.util.ArrayList;

public final class DaoArchDetRows implements DbOutputRecordSet
{
   private ArrayList  m_al;
   
   public DaoArchDetRows()
   {    
      m_al = new ArrayList();  
   }
   
   public int count()
   {
      return m_al.size();
   }
    
   public DaoArchDetRow getRecord(int index)
   {
      return (DaoArchDetRow) m_al.get(index);
   }
   
   public DbOutputRecord newRecord()
   {
      
      DaoArchDetRow rec = new DaoArchDetRow();
      
      m_al.add(rec);
      
      return rec;
      
   }
   
} // class
