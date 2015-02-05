
package ieci.tecdoc.core.collections;

import java.util.ArrayList;

// No es final porque se extiende en DbIanRs

public class IeciTdIanArrayList
{
   
   private ArrayList m_al;
   
   public IeciTdIanArrayList()
   {
      m_al = new ArrayList();      
   }
   
   public int count()
   {
      return m_al.size();
   }
   
   public void add(IeciTdIan item)
   {
      m_al.add(item);
   }
   
   public void add(String id, String name)
   {
      m_al.add(new IeciTdIan(id, name));
   }
   
   public IeciTdIan get(int index)
   {
      return (IeciTdIan) m_al.get(index);
   }
   
} // class
