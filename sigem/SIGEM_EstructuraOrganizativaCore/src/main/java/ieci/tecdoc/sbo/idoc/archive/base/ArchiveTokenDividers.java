
package ieci.tecdoc.sbo.idoc.archive.base;

import java.io.Serializable;
import java.util.ArrayList;

public final class ArchiveTokenDividers implements Serializable
{
   
   private ArrayList m_al;
   
   public ArchiveTokenDividers()
   {
      m_al = new ArrayList();      
   }
   
   public int count()
   {
      return m_al.size();
   }
   
   public void add(ArchiveTokenDivider item)
   {      
      m_al.add(item);      
   }
   
   public void add(int id, String name, int parentId, int type)
   {
      
      ArchiveTokenDivider div;
      
      div = new ArchiveTokenDivider(id, name, parentId, type);
      
      m_al.add(div);
      
   }
   
   public ArchiveTokenDivider get(int index)
   {
      return (ArchiveTokenDivider) m_al.get(index);
   }   
      
   /**
    * toString methode: creates a String representation of the object
    * @return the String representation
    * @author info.vancauwenberge.tostring plugin

    */
   public String toString() {
      StringBuffer buffer = new StringBuffer();
      buffer.append("ArchiveTokenDividers[");
      
      for(int i = 0; i < m_al.size(); i++)
      {

          buffer.append(" [divider").append(i+1);
          buffer.append(" = ").append((m_al.get(i)).toString());
          buffer.append("] ");

      }
      
      buffer.append("]");
      return buffer.toString();
   }
} // class
