
package ieci.tecdoc.core.collections;

// No es final porque se extiende en DbIanRec

public class IeciTdIan
{
   
   public String m_id;
   public String m_name;
   
   public IeciTdIan()
   {
      m_id   = null;
      m_name = null;
   }
   
   public IeciTdIan(String id, String name)
   {
      m_id   = id;
      m_name = name;
   }
   
} // class
