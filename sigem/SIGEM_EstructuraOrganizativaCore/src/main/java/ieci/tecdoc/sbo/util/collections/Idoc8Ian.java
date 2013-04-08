
package ieci.tecdoc.sbo.util.collections;

import ieci.tecdoc.sbo.util.types.SboType;

// No es final porque se extiende en Idoc8DbIanRec

public class Idoc8Ian 
{
   
   public int    m_id;
   public String m_name;
   
   public Idoc8Ian()
   {
      m_id   = SboType.NULL_ID;;
      m_name = null;
   }
   
   public Idoc8Ian(int id, String name)
   {
      m_id   = id;
      m_name = name;
   }
   
} // class
