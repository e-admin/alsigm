
package ieci.tecdoc.core.collections;

import java.util.ArrayList;
import java.util.Date;

public final class IeciTdDateTimeArrayList
{
   
   private ArrayList m_al;
   
   public IeciTdDateTimeArrayList()
   {
      m_al = new ArrayList();      
   }
   
   public int count()
   {
      return m_al.size();
   }
   
   public void add(Date val)
   {
      m_al.add(val);
   }

   public Date get(int index)
   {
      return (Date) m_al.get(index);
   }
   
} // class
