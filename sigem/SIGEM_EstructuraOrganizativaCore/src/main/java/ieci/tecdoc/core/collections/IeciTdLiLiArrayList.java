
package ieci.tecdoc.core.collections;

import java.util.ArrayList;

public class IeciTdLiLiArrayList
{
   
   private ArrayList m_al;
   
   public IeciTdLiLiArrayList()
   {
      m_al = new ArrayList();      
   }
   
   public int count()
   {
      return m_al.size();
   }
   
   public void add(IeciTdLiLi item)
   {
      m_al.add(item);
   }
   
   public void add(int val1, int val2)
   {
      m_al.add(new IeciTdLiLi(val1, val2));
   }
   
   public IeciTdLiLi get(int index)
   {
      return (IeciTdLiLi) m_al.get(index);
   }

   public int getIndexByFirstValue(int val1)
   {
      int         numObjects = count();
      int         i;
      int         idx = -1;
      IeciTdLiLi  obj;

      for(i = 0; i < numObjects; i++)
      {
         obj = get(i);
         
         if (obj.m_val1 == val1)
         {                                                       
            idx = i;
            break; 
         }         
      }
      
      return idx;
   }
   
} // class
