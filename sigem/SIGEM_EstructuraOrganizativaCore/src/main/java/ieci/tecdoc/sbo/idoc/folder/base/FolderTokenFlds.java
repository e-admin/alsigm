
package ieci.tecdoc.sbo.idoc.folder.base;

import java.io.Serializable;
import java.util.ArrayList;
import ieci.tecdoc.core.exception.IeciTdException;

/**
 * Este campo representa todos los campos de una carpeta de un archivador. Hay tres
 * tipos de campos: relacionales, multivalor y extendidos.
 *
 * @author egonzalez
 * @see FolderTokenRelFlds
 * @see FolderTokenMultFlds
 * @see FolderTokenExtFlds
 */

public final class FolderTokenFlds implements Serializable
{   
   /**
    * Colección de campos relacionales de una carpeta de un archivador
    */
   private FolderTokenRelFlds  m_relFlds;
   
   /**
    * Colección de campos multivalor de una carpeta de un archivador
    */   
   private FolderTokenMultFlds m_multFlds;
   
   /**
    * Colección de campos extendidos de una carpeta de un archivador
    */   
   private FolderTokenExtFlds  m_extFlds;
   
   /**
    * Constructor
    */
   public FolderTokenFlds()
   {
      m_relFlds  = null;  
      m_multFlds = null; 
      m_extFlds  = null;   
   }
   
   /**
    * Devuelve los campo relacionales de una carpeta 
    * @return referencia a un objeto de tipo FolderTokenRelFlds que representa una colección
    * de campos relacionales
    * @see FolderTokenRelFlds
    */
   public FolderTokenRelFlds getRelFlds()
   {
      return m_relFlds;
   }
         
   /**
    * Establece una colección de campos relacionales como los campos relacionales de una carpeta
    * @param relFlds referencia a un objeto de tipo FolderTokenRelFlds que es una colección de 
    * campos relacionados
    * @see FolderTokenRelFlds
    */
   public void setRelFlds(FolderTokenRelFlds relFlds)
   {      
      m_relFlds = relFlds;       
   } 

   
   /**
    * Establece una colección de campos multivalor como los campos multivalor de una carpeta
    * @param relFlds referencia a un objeto de tipo FolderTokenMultFlds que es una colección de 
    * campos multivalor
    * @see FolderTokenMultFlds
    */   
   public void setMultFlds(FolderTokenMultFlds multFlds)
   {      
      m_multFlds = multFlds;       
   } 
   
   /**
    * Devuelve los campo multivalor de una carpeta 
    * @return referencia a un objeto de tipo FolderTokenMultFlds que representa una colección
    * de campos multivalor
    * @see FolderTokenMultFlds
    */   
   public FolderTokenMultFlds getMultFlds()
   {
      return m_multFlds;
   }

   /**
    * Devuelve los campo extendidos de una carpeta 
    * @return referencia a un objeto de tipo FolderTokenExtFlds que representa una colección
    * de campos extendidos
    * @see FolderTokenExtFlds
    */      
   public void setExtFlds(FolderTokenExtFlds extFlds)
   {      
      m_extFlds = extFlds;       
   } 
   
   /**
    * Devuelve los campo extendidos de una carpeta 
    * @return referencia a un objeto de tipo FolderTokenExtFlds que representa una colección
    * de campos extendidos
    * @see FolderTokenExtFlds
    */      
   public FolderTokenExtFlds getExtFlds()
   {
      return m_extFlds;
   }
   
   /**
    * Devuelve el valor del campo relacional con un determinado identificador 
    * @param fldId identificador de un campo relacional
    * @return valor del campo relacional
    * @throws Exception si no se encuentra el campo
    */
   public Object getRelFldValue(int fldId) throws Exception
   {
      Object val;
      
      val = m_relFlds.getFldValue(fldId);
      
      return val;
   }
   
   /**
    * Devuelve el valor del campo relacional con un determinado nombre 
    * @param fldName nombre de un campo relacional
    * @return valor del campo relacional
    * @throws Exception si no se encuentra el campo
    */   
   public Object getRelFldValue(String fldName) throws Exception
   {
      Object val;
      
      val = m_relFlds.getFldValue(fldName);
      
      return val;
   }
   
   /**
    * Devuelve el valor del campo extendido con un determinado identificador 
    * @param fldId identificador de un campo extendido
    * @return valor del campo
    * @throws Exception si no se encuentra el campo
    */   
   public String getExtFldValue(int fldId) throws Exception
   {
      String val;
      
      val = m_extFlds.getFldValue(fldId);
      
      return val;
   }
   
   /**
    * Devuelve el valor del campo extendido con un determinado nombre 
    * @param fldName nombre de un campo extendido
    * @return valor del campo
    * @throws Exception si no se encuentra el campo
    */      
   public String getExtFldValue(String fldName) throws Exception
   {
      String val;
      
      val = m_extFlds.getFldValue(fldName);
      
      return val;
   }
   
   /**
    * Devuelve los valores del campo multivalor con un determinado identificador 
    * @param fldId identificador de un campo multivalor
    * @return valores del campo
    * @throws Exception si no se encuentra el campo
    */     
   public ArrayList getMultFldValues(int fldId) throws Exception
   {
      ArrayList vals;
      
      vals = m_multFlds.getFldValues(fldId);
      
      return vals;
   }
   
   /**
    * Devuelve los valores del campo multivalor con un determinado nombre 
    * @param fldName nombre de un campo multivalor
    * @return valores del campo
    * @throws Exception si no se encuentra el campo
    */    
   public ArrayList getMultFldValue(String fldName) throws Exception
   {
      ArrayList vals;
      
      vals = m_multFlds.getFldValues(fldName);
      
      return vals;
   }
   
   private int findRelFldIndexById(int fldId) throws Exception
   {
      int idx;
      
      idx = m_relFlds.findIndexById(fldId);
      
      return idx;
   }
   
   private int findExtFldIndexById(int fldId) throws Exception
   {
      int idx;
      
      idx = m_extFlds.findIndexById(fldId);
      
      return idx;
   }
   
   private int findMultFldIndexById(int fldId) throws Exception
   {
      int idx;
      
      idx = m_multFlds.findIndexById(fldId);
      
      return idx;
   }
   
   /**
    * Devuelve el valor de un campo con un determinado id. El campo los busca
    * entre los campos relacionales y extendidos
    * @param fldId identificador del campo
    * @return valor del campo
    * @throws Exception si el campo es multivalor
    */
   public Object getFieldValue(int fldId) throws Exception
   {
      int    idx;
      Object val = null;
      
      idx = m_relFlds.findIndexById(fldId);
      
      if (idx != -1)
      {
         val = m_relFlds.getValue(idx);
      }
      else
      {
         idx = m_extFlds.findIndexById(fldId);
         
         if (idx != -1)
         {
            val = m_extFlds.getValue(idx);
         }
         else
         {
            throw new IeciTdException(FolderBaseError.EC_FIELD_IS_MULT, 
                                      FolderBaseError.EM_FIELD_IS_MULT); 

         }
      }
      
      return val;
   }
   
   /**
    * Devuelve los valores asociados a un campo multivalor con un determinado identificador
    * @param fldId identificador del campo 
    * @return valores asociados
    * @throws Exception si el campo no es multivalor
    */
   public ArrayList getFieldValues(int fldId) throws Exception
   {
      int       idx;
      ArrayList vals = null;
      
      idx = m_multFlds.findIndexById(fldId);
      
      if (idx != -1)
      {
         vals = m_multFlds.getValues(idx);
      }
      else
      {
         throw new IeciTdException(FolderBaseError.EC_FIELD_IS_NOT_MULT, 
                                   FolderBaseError.EM_FIELD_IS_NOT_MULT); 

      }
      
      return vals;
   }
   
   /**
    * Establece el valor de un campo con un determinado identificador. El campo lo
    * busca en la colección de campos relacionales y extendidos
    * @param fldId identficador del campo
    * @param val valor del campo
    * @throws Exception si el campo es multivalor
    */
   public void setFieldValue(int fldId, Object val) 
               throws Exception
   {
      int    idx;
           
      idx = m_relFlds.findIndexById(fldId);
      
      if (idx != -1)
      {
          m_relFlds.setValue(idx, val);
      }
      else
      {
         idx = m_extFlds.findIndexById(fldId);
         
         if (idx != -1)
         {
            m_extFlds.setValue(idx, (String)val);
         }
         else
         {
            throw new IeciTdException(FolderBaseError.EC_FIELD_IS_MULT, 
                                      FolderBaseError.EM_FIELD_IS_MULT); 

         }
      }
      
   }
   
   /**
    * Añade un valor a un campo multivalor con un determinado identificador. 
    * @param fldId identficador del campo
    * @param val valor del campo
    * @throws Exception si el campo no es multivalor
    */   
   public void addFieldValue(int fldId, Object val) throws Exception
   {
      int idx;
      
      
      idx = m_multFlds.findIndexById(fldId);
      
      if (idx != -1)
      {
         m_multFlds.addValue(idx, val);
      }
      else
      {
         throw new IeciTdException(FolderBaseError.EC_FIELD_IS_NOT_MULT, 
                                   FolderBaseError.EM_FIELD_IS_NOT_MULT); 

      }
      
   }
   
   /**
    * Elimina los valores asociados a un campo multivalor con un determinado identificador. 
    * @param fldId identificador del campo
    * @throws Exception si el campo no es multivalor
    */
   public void removeFieldValues(int fldId) throws Exception
   {
      int  idx;
      
      idx = m_multFlds.findIndexById(fldId);
      
      if (idx != -1)
      {
         m_multFlds.removeValues(idx);
      }
      else
      {
         throw new IeciTdException(FolderBaseError.EC_FIELD_IS_NOT_MULT, 
                                   FolderBaseError.EM_FIELD_IS_NOT_MULT); 

      }
     
   }
   
   /**
    * Devuelve el número de campo extendidos con estado igual a NEW
    * @return número de campo extendidos con estado igual a NEW
    * @throws Exception 
    */
   public int getNumNewExtFields() throws Exception
   {
      return m_extFlds.getNumNewFlds();
   }
   
   /**
    * Establece el estado de todos los campos de la carpeta a NONE
    * @throws Exception
    */
   public void clearEditInfo() throws Exception
   {      
      m_relFlds.clearEditInfo();  
      m_extFlds.clearEditInfo();
      m_multFlds.clearEditInfo();
   }
   
   /**
    * Dice si la carpeta tiene algun campo relacional con estado UPDATE
    * @return true si tiene algun campo relacional con estado UPDATE
    * @throws Exception
    */
   public boolean existsAnyRelFldValueUpdate()
                  throws Exception
   {
      boolean exists;
      
      exists = m_relFlds.existsAnyFldUpdate();
      
      return exists;
   }
   
   /**
    * Devuelve una colección de campo relacionales con estado UPDATE
    * @return colección de campo relacionales con estado UPDATE
    * @throws Exception
    */
   public FolderTokenRelFlds getUpdateRelFlds()
                             throws Exception
   {
      return m_relFlds.getUpdateFlds();
   }
        
   /**
    * Devuelve una representacion textual del objeto, en este caso, la coleccion de campos
    * de la carpeta
    * @return representacion textual del objeto, en este caso, la coleccion de campos
    * de la carpeta
    */
   public String toString() {
      
      StringBuffer buffer = new StringBuffer();
      
      buffer.append("FolderTokenFlds[");
      buffer.append("m_relFlds = ").append(m_relFlds);
      buffer.append(", m_multFlds = ").append(m_multFlds);
      buffer.append(", m_extFlds = ").append(m_extFlds);
      buffer.append("]");
      
      return buffer.toString();
   }
} // class
