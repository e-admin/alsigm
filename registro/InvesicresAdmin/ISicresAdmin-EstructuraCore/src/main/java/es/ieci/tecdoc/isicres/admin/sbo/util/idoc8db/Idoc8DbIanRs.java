
package es.ieci.tecdoc.isicres.admin.sbo.util.idoc8db;

import es.ieci.tecdoc.isicres.admin.core.db.DbOutputRecord;
import es.ieci.tecdoc.isicres.admin.core.db.DbOutputRecordSet;
import es.ieci.tecdoc.isicres.admin.sbo.util.collections.Idoc8IanArrayList;

public final class Idoc8DbIanRs extends Idoc8IanArrayList 
                                implements DbOutputRecordSet
{
   
   public Idoc8DbIanRs()
   {
      super();      
   }
   public int count()
   {
      return super.count();
   }
   
   public void add(int id, String name)
   {
      
      Idoc8DbIanRec rec = new Idoc8DbIanRec(id, name);
      
      super.add(rec);
      
   }
   
   public Idoc8DbIanRec getRecord(int index)
   {
      return (Idoc8DbIanRec) super.get(index);
   }
   
   public DbOutputRecord newRecord()
   {
      
      Idoc8DbIanRec rec = new Idoc8DbIanRec();
      
      super.add(rec);
      
      return rec;
      
   }
   
} // class
