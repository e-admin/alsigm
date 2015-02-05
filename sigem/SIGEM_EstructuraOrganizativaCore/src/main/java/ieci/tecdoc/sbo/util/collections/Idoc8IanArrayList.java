
package ieci.tecdoc.sbo.util.collections;

import java.util.ArrayList;

// No es final porque se extiende en Idoc8DbIanRs

public class Idoc8IanArrayList
{
   
   private ArrayList m_al;
   
   public Idoc8IanArrayList()
   {
      m_al = new ArrayList();      
   }
   
   public int count()
   {
      return m_al.size();
   }
   
   public void add(Idoc8Ian item)
   {
      m_al.add(item);
   }
   
   public void add(int id, String name)
   {
      m_al.add(new Idoc8Ian(id, name));
   }
   
   public Idoc8Ian get(int index)
   {
      return (Idoc8Ian) m_al.get(index);
   }
   
} // class
