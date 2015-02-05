
package ieci.tecdoc.sbo.idoc.folder.base;

import java.io.Serializable;
import java.util.ArrayList;
import ieci.tecdoc.core.exception.IeciTdException;

/**
 * Colección de campos multivalor de una carpeta de un archivador. Cada
 * elemento de esta colección corresponde con un objeto de tipo
 * FolderTokenMultFlds 
 * 
 * @author 
 * @see FolderTokenMultFlds
 */

public final class FolderTokenMultFlds implements Serializable
{
   /**
    * Colección de campos multivalor
    */
   private ArrayList m_multFlds;
   
   /**
    * Constructor
    */
   public FolderTokenMultFlds()
   {
      m_multFlds = new ArrayList();      
   }
   
   /**
    * Devuelve el número de campos multivalor que hay en la colección
    * @return
    */
   public int count()
   {
      return m_multFlds.size();
   }
      
   /**
    * Añade un nuevo campo multivalor a la colección
    * @param id identificador del campo
    * @param name nombre del campo
    * @param vals valores del campo
    */
   public void add(int id, String name, ArrayList vals)
   {
      
      FolderTokenMultFld fld;
      
      fld = new FolderTokenMultFld(id, name, vals);
      
      m_multFlds.add(fld);
      
   } 
   
   /**
    * Devuelve el campo multivalor i-ésimo de la colección 
    * @param index índice
    * @return referencia a un objeto de tipo FolderTokenMultFld que representa
    * a un campo multivalor de una carpeta
    */
   public FolderTokenMultFld get(int index)
   {
      return (FolderTokenMultFld) m_multFlds.get(index);
   }
   
   /**
    * Devuelve un campo multivalor de la coleccion con un determinado identificador
    * @param fldId identificador del campo multivalor
    * @return referencia a un objeto de tipo FolderTokenMultFld que representa
    * a un campo multivalor de una carpeta
    * @throws Exception si no se encuentra el campo
    */
   public FolderTokenMultFld findById(int fldId) throws Exception
   {
      int                i;
      FolderTokenMultFld fld = null;
      boolean            find = false;
      
      for(i = 0; i < count(); i++)
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
         throw new IeciTdException(FolderBaseError.EC_NOT_FOUND, 
                                   FolderBaseError.EM_NOT_FOUND); 

      }
      
      return fld;
   }
   
   /**
    * Devuelve un campo multivalor de la coleccion con un determinado nombre
    * @param fldName nombre del campo multivalor
    * @return referencia a un objeto de tipo FolderTokenMultFld que representa
    * a un campo multivalor de una carpeta
    * @throws Exception si no se encuentra el campo
    */
   public FolderTokenMultFld findByName(String fldName) throws Exception
   {
      int                i;
      FolderTokenMultFld fld = null;
      boolean            find = false;
      
      for(i = 0; i < count(); i++)
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
         throw new IeciTdException(FolderBaseError.EC_NOT_FOUND, 
                                   FolderBaseError.EM_NOT_FOUND); 

      }
      
      return fld;
   }
   
   /**
    * Devuelve los valores asociados a un campo multivalor de la colección con
    * un determinado nombre
    * @param fldName nombre del campo multivalor
    * @return colección de valores del campo multivalor
    * @throws Exception si no se encuentra el campo
    */
   public ArrayList getFldValues(String fldName) throws Exception
   {
      FolderTokenMultFld fld = null;
      ArrayList          vals;
      
      fld = findByName(fldName);
      
      vals = fld.getVals();   
      
      return vals;
   }
   
   /**
    * Devuelve los valores asociados a un campo multivalor de la colección con
    * un determinado identificador
    * @param fldId identificador del campo multivalor
    * @return colección de valores del campo multivalor
    * @throws Exception si no se encuentra el campo
    */   
   public ArrayList getFldValues(int fldId) throws Exception
   {
      FolderTokenMultFld fld = null;
      ArrayList          vals;
      
      fld = findById(fldId);
      
      vals = fld.getVals();   
      
      return vals;
   }
   
   /**
    * Devuelve los valores asociados con el campo multivalor i-ésimo de la coleccion
    * @param idx índice
    * @return colección de valores del campo multivalor
    * @throws Exception si no se encuentra el campo
    */
   public ArrayList getValues(int idx) throws Exception
   {
      FolderTokenMultFld fld = null;
      ArrayList          vals;
      
      fld = get(idx);
      
      vals = fld.getVals();   
      
      return vals;
   }
   
   /**
    * Devuelve la posición que ocupa el campo con un determinado identificador
    * dentro de la colección
    * @param fldId identificador del campo  multivalor
    * @return índice dentro de la colección; -1 si no se encuentra
    * @throws Exception
    */
   public int findIndexById(int fldId) throws Exception
   {
      int                i;
      FolderTokenMultFld fld = null;
      int                idx = -1;
      
      for(i = 0; i < count(); i++)
      {
         fld = get(i);
         
         if (fld.getId() == fldId)
         {
            idx = i;
            break;            
         }
      }
      
      return idx;
   }
   
   /**
    * Añade un nuevo valor al campo i-ésimo de la colección
    * @param idx índice 
    * @param val nuevo valor 
    * @throws Exception
    */
   protected void addValue(int idx, Object val) throws Exception
   {
      FolderTokenMultFld fld = null;
      ArrayList          vals;
      
      fld = get(idx);
      
      fld.addVal(val);         
   }
   
   /**
    * Elimina los valores al campo i-ésimo de la colección
    * @param idx índice
    * @throws Exception
    */
   protected void removeValues(int idx) throws Exception
   {
      FolderTokenMultFld fld = null;
      ArrayList          vals;
      
      fld = get(idx);
      
      fld.removeValues();         
   }
   
   /**
    * Marca todos los campos de la colección con estado NONE
    * @throws Exception
    */
   protected void clearEditInfo() throws Exception
   {
      int                i;
      FolderTokenMultFld fld = null;
      
      for(i = 0; i < count(); i++)
      {
         fld = get(i);
         fld.clearEditInfo();  
      }      
   }
         
   /**
    * Devuelve una representación textual del objeto, es decir, de la colección
    * de campos multivalor
    * @return representación textual del objeto, es decir, de la colección
    * de campos multivalor
    */
   public String toString() {
      
      StringBuffer buffer = new StringBuffer();
      
      buffer.append("FolderTokenMultFlds[");
      
      for(int i = 0; i < m_multFlds.size(); i++)
      {

          buffer.append(" [folderTokenMultFlds-").append(i+1);
          buffer.append(" = ").append((m_multFlds.get(i)).toString());
          buffer.append("] ");

      }

      buffer.append("]");
      
      return buffer.toString();
   }
} // class
