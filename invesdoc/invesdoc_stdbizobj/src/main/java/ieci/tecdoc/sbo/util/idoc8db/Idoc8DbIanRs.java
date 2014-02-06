
package ieci.tecdoc.sbo.util.idoc8db;

import ieci.tecdoc.core.db.DbOutputRecord;
import ieci.tecdoc.core.db.DbOutputRecordSet;
import ieci.tecdoc.sbo.util.collections.Idoc8IanArrayList;

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
