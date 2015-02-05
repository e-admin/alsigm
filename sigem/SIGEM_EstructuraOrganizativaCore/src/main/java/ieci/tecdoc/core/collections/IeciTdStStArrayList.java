
package ieci.tecdoc.core.collections;

import java.util.ArrayList;

// No es final porque se extiende en DbStStRs

public class IeciTdStStArrayList
{
   
   private ArrayList m_al;
   
   public IeciTdStStArrayList()
   {
      m_al = new ArrayList();      
   }
   
   public int count()
   {
      return m_al.size();
   }
   
   public void add(IeciTdStSt item)
   {
      m_al.add(item);
   }
   
   public void add(String fld1, String fld2)
   {
      m_al.add(new IeciTdStSt(fld1, fld2));
   }
   
   public IeciTdStSt get(int index)
   {
      return (IeciTdStSt) m_al.get(index);
   }
   
} // class
