
package ieci.tecdoc.sbo.idoc.datree;

import java.util.ArrayList;

public class DATNodeList
{
   
   private ArrayList m_al;
   
   public DATNodeList()
   {
      m_al = new ArrayList();      
   }
   
   public int count()
   {
      return m_al.size();
   }
   
   public void add(DATNode item)
   {
      m_al.add(item);
   }
   
   public void add(int id, String name, int type)
   {
      m_al.add(new DATNode(id, name, type));
   }
   
   public DATNode get(int index)
   {
      return (DATNode) m_al.get(index);
   }
   
} // class
