
package es.ieci.tecdoc.isicres.admin.base.dyndao;


import java.util.ArrayList;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbColumnDef;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbOutputRecord;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbOutputRecordSet;


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
