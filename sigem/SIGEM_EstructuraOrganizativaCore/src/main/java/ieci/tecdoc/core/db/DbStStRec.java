
package ieci.tecdoc.core.db;

import ieci.tecdoc.core.collections.IeciTdStSt;

public final class DbStStRec extends IeciTdStSt implements DbOutputRecord
{
   
   public DbStStRec()
   {
      super();
   }
   
   public DbStStRec(String fld1, String fld2)
   {
      super(fld1, fld2);      
   }
   
   public void getStatementValues(DbOutputStatement stmt) throws Exception
   {      
                  
      int i = 1;
      
      m_fld1 = stmt.getShortText(i++);
      m_fld2 = stmt.getShortText(i++);       
      
   }
   
} // class
