
package ieci.tecdoc.sbo.idoc.dao;

import java.util.ArrayList;
import ieci.tecdoc.core.exception.IeciTdException;

public final class DaoRelFlds
{
   
   private ArrayList m_flds;
   
   public DaoRelFlds()
   {
      m_flds = new ArrayList();      
   }
   
   public int count()
   {
      return m_flds.size();
   }
      
   public void add(int id, String name, String colName)
   {
      
      DaoRelFld fld = new DaoRelFld(id, name, colName); 
      
      m_flds.add(fld);
      
   }  
   
   public DaoRelFld get(int index)
   {
      return (DaoRelFld) m_flds.get(index);
   } 
   
   public DaoRelFld findById(int fldId) throws Exception
   {
      int       i;
      DaoRelFld fld = null;
      boolean   find = false;
      int       numFlds = count();
      
      for(i = 0; i < numFlds; i++)
      {
         fld = get(i);
         
         if (fld.getId() == fldId)
         {
            find = true;
            break;            
         }
      }
      
      if (!find)
      {
         throw new IeciTdException(DaoError.EC_NOT_FOUND, 
                                   DaoError.EM_NOT_FOUND); 

      }
      
      return fld;
   }
   
   public DaoRelFld findByName(String fldName) throws Exception
   {
      int       i;
      DaoRelFld fld = null;
      boolean   find = false;
      int       numFlds = count();
      
      for(i = 0; i < numFlds; i++)
      {
         fld = get(i);
         
         if (fld.getName().equals(fldName))
         {
            find = true;
            break;            
         }
      }
      
      if (!find)
      {
         throw new IeciTdException(DaoError.EC_NOT_FOUND, 
                                   DaoError.EM_NOT_FOUND); 

      }
      
      return fld;
   }
   
   
} // class
