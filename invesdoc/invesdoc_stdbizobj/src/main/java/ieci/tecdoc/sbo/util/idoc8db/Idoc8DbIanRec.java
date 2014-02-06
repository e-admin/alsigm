
package ieci.tecdoc.sbo.util.idoc8db;

import ieci.tecdoc.core.db.DbOutputStatement;
import ieci.tecdoc.core.db.DbOutputRecord;
import ieci.tecdoc.sbo.util.collections.Idoc8Ian;

public final class Idoc8DbIanRec extends Idoc8Ian implements DbOutputRecord
{
   
   public Idoc8DbIanRec()
   {
      super();
   }
   
   public Idoc8DbIanRec(int id, String name)
   {
      super(id, name);      
   }
   
   public void getStatementValues(DbOutputStatement stmt) throws Exception
   {      
                  
      int i = 1;
      
      m_id   = stmt.getLongInteger(i++);
      m_name = stmt.getShortText(i++);       
      
   }
   
} // class
