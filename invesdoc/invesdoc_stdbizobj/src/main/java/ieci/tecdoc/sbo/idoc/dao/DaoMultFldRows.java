
package ieci.tecdoc.sbo.idoc.dao;

import java.util.ArrayList;
import ieci.tecdoc.core.db.*;

public final class DaoMultFldRows implements DbOutputRecordSet
{
   private ArrayList  m_al;
   private int        m_fldDbType;
   
   public DaoMultFldRows(int fldDbType)
   {
      m_al        = new ArrayList();  
      m_fldDbType = fldDbType;
   }
   
   public int count()
   {
      return m_al.size();
   }
   
   public DaoMultFldRow getRecord(int index)
   {
      return (DaoMultFldRow) m_al.get(index);
   }
   
   public DbOutputRecord newRecord()
   {
      
      DaoMultFldRow rec = new DaoMultFldRow(m_fldDbType);
      
      m_al.add(rec);
      
      return rec;
      
   }
   
   
   
} // class
