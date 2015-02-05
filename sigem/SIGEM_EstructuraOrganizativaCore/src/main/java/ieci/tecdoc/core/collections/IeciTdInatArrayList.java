
package ieci.tecdoc.core.collections;

import java.util.ArrayList;

public class IeciTdInatArrayList
{
   
   private ArrayList m_al;
   
   public IeciTdInatArrayList()
   {
      m_al = new ArrayList();      
   }
   
   public int count()
   {
      return m_al.size();
   }
   
   public void add(IeciTdInat item)
   {
      m_al.add(item);
   }
   
   public void add(String id, String name, short type)
   {
      m_al.add(new IeciTdInat(id, name, type));
   }
   
   public IeciTdInat get(int index)
   {
      return (IeciTdInat) m_al.get(index);
   }
   
} // class
