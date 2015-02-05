
package ieci.tecdoc.sbo.util.collections;

import ieci.tecdoc.sbo.util.types.SboType;

public final class Idoc8Inat
{
   
   public int    m_id;
   public String m_name;
   public int    m_type;
   
   public Idoc8Inat()
   {
      m_id   = SboType.NULL_ID;
      m_name = null;
      m_type = SboType.NULL_ID;
   }
   
   public Idoc8Inat(int id, String name, int type)
   {
      m_id   = id;
      m_name = name;
      m_type = type;
   }
   
} // class
