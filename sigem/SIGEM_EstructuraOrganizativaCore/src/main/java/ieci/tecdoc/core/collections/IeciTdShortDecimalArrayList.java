
package ieci.tecdoc.core.collections;

import java.util.ArrayList;

public final class IeciTdShortDecimalArrayList
{
   
   private ArrayList m_al;
   
   public IeciTdShortDecimalArrayList()
   {
      m_al = new ArrayList();      
   }
   
   public int count()
   {
      return m_al.size();
   }
   
   public void add(float val)
   {
      m_al.add(new Float(val));
   }

   public float get(int index)
   {
      return ((Float) m_al.get(index)).floatValue();
   }
   
} // class
