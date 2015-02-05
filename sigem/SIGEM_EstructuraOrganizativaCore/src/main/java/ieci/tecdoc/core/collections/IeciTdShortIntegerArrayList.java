
package ieci.tecdoc.core.collections;

import java.util.ArrayList;

public final class IeciTdShortIntegerArrayList
{
   
   private ArrayList m_al;
   
   public IeciTdShortIntegerArrayList()
   {
      m_al = new ArrayList();      
   }
   
   public int count()
   {
      return m_al.size();
   }
   
   public void add(short val)
   {
      m_al.add(new Short(val));
   }

   public short get(int index)
   {
      return ((Short) m_al.get(index)).shortValue();
   }
   
} // class
