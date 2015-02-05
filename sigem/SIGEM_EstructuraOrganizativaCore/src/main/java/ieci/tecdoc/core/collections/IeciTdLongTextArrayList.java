
package ieci.tecdoc.core.collections;

import java.util.ArrayList;

public final class IeciTdLongTextArrayList
{
   
   private ArrayList m_al;
   
   public IeciTdLongTextArrayList()
   {
      m_al = new ArrayList();      
   }
   
   public int count()
   {
      return m_al.size();
   }
   
   public void add(String val)
   {
      m_al.add(val);
   }

   public String get(int index)
   {
      return (String) m_al.get(index);
   }
   
} // class
