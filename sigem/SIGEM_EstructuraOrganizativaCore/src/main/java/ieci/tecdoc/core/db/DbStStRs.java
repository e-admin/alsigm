
package ieci.tecdoc.core.db;

import ieci.tecdoc.core.collections.IeciTdStStArrayList;

public final class DbStStRs extends IeciTdStStArrayList
                            implements DbOutputRecordSet
{
   
   public DbStStRs()
   {
      super();      
   }
   public int count()
   {
      return super.count();
   }
   
   public void add(String fld1, String fld2)
   {
      
      DbStStRec rec = new DbStStRec(fld1, fld2);
      
      super.add(rec);
      
   }
   
   public DbStStRec getRecord(int index)
   {
      return (DbStStRec) super.get(index);
   }
   
   public DbOutputRecord newRecord()
   {
      
      DbStStRec rec = new DbStStRec();
      
      super.add(rec);
      
      return rec;
      
   }
   
} // class
