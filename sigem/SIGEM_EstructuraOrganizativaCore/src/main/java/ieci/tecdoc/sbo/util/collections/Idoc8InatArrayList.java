
package ieci.tecdoc.sbo.util.collections;

import java.util.ArrayList;

public class Idoc8InatArrayList
{
   
   private ArrayList m_al;
   
   public Idoc8InatArrayList()
   {
      m_al = new ArrayList();      
   }
   
   public int count()
   {
      return m_al.size();
   }
   
   public void add(Idoc8Inat item)
   {
      m_al.add(item);
   }
   
   public void add(int id, String name, int type)
   {
      m_al.add(new Idoc8Inat(id, name, type));
   }
   
   public Idoc8Inat get(int index)
   {
      return (Idoc8Inat) m_al.get(index);
   }
   
} // class
