
package ieci.tecdoc.core.db;

import ieci.tecdoc.core.collections.IeciTdStSiArrayList;

public final class DbStSiRs extends IeciTdStSiArrayList
                            implements DbOutputRecordSet
{
   
   public DbStSiRs()
   {
      super();      
   }
   
   public int count()
   {
      return super.count();
   }
   
   public void add(String fld1, short fld2)
   {
      
      DbStSiRec rec = new DbStSiRec(fld1, fld2);
      
      super.add(rec);
      
   }
   
   public DbStSiRec getRecord(int index)
   {
      return (DbStSiRec) super.get(index);
   }
   
   public DbOutputRecord newRecord()
   {
      
      DbStSiRec rec = new DbStSiRec();
      
      super.add(rec);
      
      return rec;
      
   }
   
} // class
