
package ieci.tecdoc.core.collections;

public final class IeciTdInat
{
   
   public String m_id;
   public String m_name;
   public short  m_type;
   
   public IeciTdInat()
   {
      m_id   = null;
      m_name = null;
      m_type = 0;
   }
   
   public IeciTdInat(String id, String name, short type)
   {
      m_id   = id;
      m_name = name;
      m_type = type;
   }
   
} // class
