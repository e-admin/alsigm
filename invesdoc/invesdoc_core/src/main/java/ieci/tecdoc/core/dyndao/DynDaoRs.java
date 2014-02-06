
package ieci.tecdoc.core.dyndao;

import java.util.ArrayList;
import ieci.tecdoc.core.db.*;

public final class DynDaoRs implements DbOutputRecordSet
{
   private ArrayList     m_al;
   private DbColumnDef[] m_colsDef;
   
   public DynDaoRs(DbColumnDef[] colsDef)
   {
      m_al      = new ArrayList();  
      m_colsDef = colsDef;
   }
   
   public int count()
   {
      return m_al.size();
   }
   
   public DynDaoRec getRecord(int index)
   {
      return (DynDaoRec) m_al.get(index);
   }
   
   public DbOutputRecord newRecord()
   {
      
      DynDaoRec rec = new DynDaoRec(m_colsDef);
      
      m_al.add(rec);
      
      return rec;
      
   }
   
   
   
} // class
