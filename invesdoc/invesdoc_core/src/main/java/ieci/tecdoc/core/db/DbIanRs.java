
package ieci.tecdoc.core.db;

import ieci.tecdoc.core.collections.IeciTdIanArrayList;

public final class DbIanRs extends IeciTdIanArrayList 
                           implements DbOutputRecordSet
{
   
   public DbIanRs()
   {
      super();      
   }
   public int count()
   {
      return super.count();
   }
   
   public void add(String id, String name)
   {
      
      DbIanRec rec = new DbIanRec(id, name);
      
      super.add(rec);
      
   }
   
   public DbIanRec getRecord(int index)
   {
      return (DbIanRec) super.get(index);
   }
   
   public DbOutputRecord newRecord()
   {
      
      DbIanRec rec = new DbIanRec();
      
      super.add(rec);
      
      return rec;
      
   }
   
} // class
