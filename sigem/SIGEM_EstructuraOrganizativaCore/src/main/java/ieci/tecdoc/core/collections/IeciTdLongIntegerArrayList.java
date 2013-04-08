
package ieci.tecdoc.core.collections;

import java.io.Serializable;
import java.util.ArrayList;

public final class IeciTdLongIntegerArrayList implements Serializable
{
   
   private ArrayList m_al;
   
   public IeciTdLongIntegerArrayList()
   {
      m_al = new ArrayList();      
   }
   
   public int count()
   {
      return m_al.size();
   }
   
   public void add(int val)
   {
      m_al.add(new Integer(val));
   }

   public int get(int index)
   {
      return ((Integer) m_al.get(index)).intValue();
   }
   
   public int indexOf(int val)
   {
      return  m_al.indexOf(new Integer(val));
   }
   
   public boolean find(int val)
   {
      boolean find = false;
      int     idx  = m_al.indexOf(new Integer(val));
      
      if (idx != -1 )
         find = true;
      
      return find;  
   }
   
   public void remove(int idx)
   {
      m_al.remove(idx);
   }
   
   public void set(int idx, int val)
   {
      m_al.set(idx, new Integer(val));
   }
   
} // class
