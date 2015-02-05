
package ieci.tecdoc.core.collections;

import java.util.ArrayList;

// No es final porque se extiende en DbStSiRs

public class IeciTdStSiArrayList
{
   
   private ArrayList m_al;
   
   public IeciTdStSiArrayList()
   {
      m_al = new ArrayList();      
   }
   
   public int count()
   {
      return m_al.size();
   }
   
   public void add(IeciTdStSi item)
   {
      m_al.add(item);
   }
   
   public void add(String fld1, short fld2)
   {
      m_al.add(new IeciTdStSi(fld1, fld2));
   }
   
   public IeciTdStSi get(int index)
   {
      return (IeciTdStSi) m_al.get(index);
   }
   
} // class
