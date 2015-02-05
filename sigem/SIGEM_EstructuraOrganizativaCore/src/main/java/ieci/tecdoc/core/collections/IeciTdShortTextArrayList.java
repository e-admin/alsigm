
package ieci.tecdoc.core.collections;

import java.io.Serializable;
import java.util.ArrayList;

public final class IeciTdShortTextArrayList implements Serializable
{
   
   private ArrayList m_al;
   
   public IeciTdShortTextArrayList()
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
   
   public void remove (String val)
   {
      m_al.remove(val);
   }
   
} // class
