
package ieci.tecdoc.sbo.idoc.archive.base;

import java.io.Serializable;
import java.util.ArrayList;

public final class ArchiveTokenValidation implements Serializable
{
   
   private ArrayList m_fldsVld;
   
   public ArchiveTokenValidation()
   {
      m_fldsVld = new ArrayList();      
   }
   
   public int numFldsValidation()
   {
      return m_fldsVld.size();
   }
   
   public void addFldValidation(ArchiveTokenFldVld item)
   {      
      m_fldsVld.add(item);      
   }   
   
   public void setFldValidation(int index, ArchiveTokenFldVld item)
   {      
      m_fldsVld.set(index, item);      
   }   
   
   public ArchiveTokenFldVld getFldValidationById(int fldId) throws Exception
   {
      int i = findFldValidationIndex(fldId);
      return getFldValidation(i); 
   }

   public boolean hasFldValidation(int fldId) throws Exception
   {
      try{
         int i = findFldValidationIndex(fldId);
         getFldValidation(i);
         return true;
      }catch (Exception e){
         return false;
      }

   }

   public ArchiveTokenFldVld getFldValidation(int index)
   {
      return (ArchiveTokenFldVld) m_fldsVld.get(index);
   }
   
   public int findFldValidationIndex(int fldId) throws Exception
   {
      int                i;     
      int                idx = -1;
      ArchiveTokenFldVld vld = null;
      
      for(i = 0; i < numFldsValidation(); i++)
      {
         vld = getFldValidation(i);
         
         if (vld.getId() == fldId)
         {
            idx = i;
            break;            
         }
      }
      
      return idx;
   }
      
   /**
    * toString methode: creates a String representation of the object
    * @return the String representation
    * @author info.vancauwenberge.tostring plugin

    */
   public String toString() {
      StringBuffer buffer = new StringBuffer();
      buffer.append("ArchiveTokenValidation[");

      for(int i = 0; i < m_fldsVld.size(); i++)
      {

          buffer.append(" [FieldValidation").append(i+1);
          buffer.append(" = ").append((m_fldsVld.get(i)).toString());
          buffer.append("] ");

      }

      buffer.append("]");
      return buffer.toString();
   }
} // class
