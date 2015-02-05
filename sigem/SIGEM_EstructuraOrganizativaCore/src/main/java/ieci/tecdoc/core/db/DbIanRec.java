
package ieci.tecdoc.core.db;

import ieci.tecdoc.core.collections.IeciTdIan;

public final class DbIanRec extends IeciTdIan implements DbOutputRecord
{
   
   public DbIanRec()
   {
      super();
   }
   
   public DbIanRec(String id, String name)
   {
      super(id, name);      
   }
   
   public void getStatementValues(DbOutputStatement stmt) throws Exception
   {      
                  
      int i = 1;
      
      m_id   = stmt.getShortText(i++);
      m_name = stmt.getShortText(i++);       
      
   }
   
} // class
