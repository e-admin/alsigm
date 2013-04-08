
package es.ieci.tecdoc.isicres.admin.sbo.idoc.dao;



import java.util.ArrayList;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbOutputRecord;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbOutputRecordSet;

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
