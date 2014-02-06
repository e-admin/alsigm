
package ieci.tecdoc.core.collections;

import java.util.ArrayList;

public final class IeciTdLongDecimalArrayList
{
   
   private ArrayList m_al;
   
   public IeciTdLongDecimalArrayList()
   {
      m_al = new ArrayList();      
   }
   
   public int count()
   {
      return m_al.size();
   }
   
   public void add(double val)
   {
      m_al.add(new Double(val));
   }

   public double get(int index)
   {
      return ((Double) m_al.get(index)).doubleValue();
   }
   
} // class
