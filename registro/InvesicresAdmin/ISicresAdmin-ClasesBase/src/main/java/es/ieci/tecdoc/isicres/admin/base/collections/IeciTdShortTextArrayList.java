
package es.ieci.tecdoc.isicres.admin.base.collections;

import java.io.Serializable;
import java.util.ArrayList;

public final class IeciTdShortTextArrayList implements Serializable
{
   
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
   
} // class
