
package ieci.tecdoc.core.db;

import ieci.tecdoc.core.collections.IeciTdStSi;

public final class DbStSiRec extends IeciTdStSi implements DbOutputRecord
{
   
   public DbStSiRec()
   {
      super();
   }
   
   public DbStSiRec(String fld1, short fld2)
   {
      super(fld1, fld2);      
   }
   
   public void getStatementValues(DbOutputStatement stmt) throws Exception
   {      
                  
      int i = 1;
      
      m_fld1 = stmt.getShortText(i++);
      m_fld2 = stmt.getShortInteger(i++);       
      
   }
   
} // class
