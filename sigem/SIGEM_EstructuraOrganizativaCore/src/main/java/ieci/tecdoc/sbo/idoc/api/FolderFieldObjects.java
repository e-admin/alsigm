
package ieci.tecdoc.sbo.idoc.api;

import java.util.ArrayList;
import ieci.tecdoc.sbo.idoc.folder.base.FolderTokenFlds;

/**
 * Esta clase encapsula los valores de los campos de una carpeta
 */
public final class FolderFieldObjects
{   
   /**
    * Este objeto encapsula los campos de una carpeta
    */
   private FolderTokenFlds  m_flds;
   
   /**
    * Constructor
    * @param flds campos de una carpeta
    */
   protected FolderFieldObjects(FolderTokenFlds flds)
   {
      m_flds = flds;  
   }
   
   /**
    * Devuelve los campos de una carpeta
    * @return campos de una carpeta 
    */
   public FolderTokenFlds getFieldsToken()
   {
      return m_flds;
   }
   
   /**
    * Devuelve el valor del campo
    * @param fldId identificador del campo
    * @return referencia a un objeto que contiene el valor del campo
    * @throws Exception si se produce un error obteniendo el valor
    */
   public Object getFieldValue(int fldId) throws Exception
   {
      Object val = null;

      val = m_flds.getFieldValue(fldId);
      
      return val;
   }
   
   /**
    * Devuelve la lista de valores asociada al campo
    * @param fldId identificador del campo
    * @return referencia a un ArrayList que contiene los valores asociados al campo
    * @throws Exception si se produce un error obteniendo los valores
    */

   public ArrayList getFieldValues(int fldId) throws Exception
   {
      ArrayList vals = null;

      vals = m_flds.getFieldValues(fldId);
      
      return vals;
   }   
   
} // class
