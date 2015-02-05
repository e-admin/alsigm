
package ieci.tecdoc.sbo.idoc.folder.base;

import java.util.ArrayList;
import ieci.tecdoc.core.exception.IeciTdException;

/**
 * Encapsula una lista de campo extendidos {@link FolderTokenExtFld}.
 * Proporciona una serie de métodos sencillos para gestionarla.
 * 
 * @author
 * @see FolderTokenExtFld
 */
public final class FolderTokenExtFlds
{
   /** Lista de campos extendidos */
   private ArrayList m_extFlds;
   
   /**
    * Constructor
    *
    */
   public FolderTokenExtFlds()
   {
      m_extFlds = new ArrayList();      
   }
   
   /**
    * Devuelve el numero de campos de la lista
    * 
    * @return numero de campos de la lista
    */
   public int count()
   {
      return m_extFlds.size();
   }
      
   /**
    * Agrega un nuevo campo extendido a la lista
    * 
    * @param id identificador del campo
    * @param name nombre del campo
    * @param val valor del campo
    */
   public void add(int id, String name, String val)
   {
      
      FolderTokenExtFld fld;
      
      fld = new FolderTokenExtFld(id, name, val);
      
      m_extFlds.add(fld);
      
   }  
   
   /**
    * Devuelve el campo con el indice indicado de la lista
    * @param index indice del campo
    * @return el campo buscado
    * @see FolderTokenExtFld
    */
   public FolderTokenExtFld get(int index)
   {
      return (FolderTokenExtFld) m_extFlds.get(index);
   } 
   
   /**
    * Busca un campo en la lista a través de su id
    * 
    * @param fldId identificador del campo
    * @return el campo buscado
    * @throws Exception arroja IeciTdException si el campo no existe
    * @see FolderTokenExtFld
    */
   public FolderTokenExtFld findById(int fldId) throws Exception
   {
      int               i;
      FolderTokenExtFld fld = null;
      boolean           find = false;
      
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
    * Busca un campo en la lista a través de su nombre
    * 
    * @param fldName nombre del campo
    * @return el campo buscado
    * @throws Exception arroja IeciTdException si el campo no existe
    * @see FolderTokenExtFld
    */
   public FolderTokenExtFld findByName(String fldName) throws Exception
   {
      int               i;
      FolderTokenExtFld fld = null;
      boolean           find = false;
      
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
    * Devuelve el valor del campo con el nombre indicado
    * 
    * @param fldName nombre del campo
    * @return el valor del campo
    * @throws Exception arroja IeciTdException si no encuentra
    * el campo
    */
   public String getFldValue(String fldName) throws Exception
   {
      FolderTokenExtFld fld = null;
      String            val;
      
      fld = findByName(fldName);
      
      val = fld.getVal();   
      
      return val;
   }
   
   /**
    * Devuelve el valor del campo con el id indicado
    * 
    * @param fldId identificador del campo
    * @return el valor del campo
    * @throws Exception arroja IeciTdException si no encuentra
    * el campo
    */
   public String getFldValue(int fldId) throws Exception
   {
      FolderTokenExtFld fld = null;
      String            val;
      
      fld = findById(fldId);
      
      val = fld.getVal();   
      
      return val;
   }
   
   /**
    * Devuelve el valor del campo con el índice indicado
    * @param idx índice del campo
    * @return valor del campo
    * @throws Exception
    */
   public String getValue(int idx) throws Exception
   {
      FolderTokenExtFld fld = null;
      String            val;
      
      fld = get(idx);
      
      val = fld.getVal();   
      
      return val;
   }
   
   /**
    * Devuelve el indice del campo con el id especificado
    * @param fldId identificador del campo
    * @return indice del campo
    * @throws Exception
    */
   public int findIndexById(int fldId) throws Exception
   {
      int               i;
      FolderTokenExtFld fld = null;
      int               idx = -1;
      
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
    * Establece un valor para el campo con el indice indicado
    * @param idx indice del campo
    * @param val valor para el campo
    * @throws Exception
    */
   protected void setValue(int idx, String val) throws Exception
   {
      FolderTokenExtFld fld = null;      
      
      fld = get(idx);
      
      fld.setVal(val); 
      
   }
   
   /**
    * Devuelve el numero de campos nuevos de la lista
    * @return numero de campos nuevos de la lista
    * @throws Exception
    */
   public int getNumNewFlds() throws Exception
   {
      int               i;
      FolderTokenExtFld fld = null;
      int               num = 0;
      
      for(i = 0; i < count(); i++)
      {
         fld = get(i);
         
         if ( fld.isNew() && (fld.getVal()!= null) ) 
         {
            num = num + 1; 
         }
      }
      
      return num;
   }
   
   /**
    * Resetea el flag de estado del campo a su valor por
    * defecto (sin estado)
    * 
    * @throws Exception
    */
   protected void clearEditInfo() throws Exception
   {
      int                i;
      FolderTokenExtFld fld = null;
      
      for(i = 0; i < count(); i++)
      {
         fld = get(i);
         fld.clearEditInfo();  
      }      
   }
      
   /**
    * @see Object#toString()
    */
   public String toString() {
      
      StringBuffer buffer = new StringBuffer();
      
      buffer.append("FolderTokenExtFlds[");
      
      for(int i = 0; i < m_extFlds.size(); i++)
      {

          buffer.append(" [folderTokenExtFlds_").append(i+1);
          buffer.append(" = ").append((m_extFlds.get(i)).toString());
          buffer.append("] ");

      }

      buffer.append("]");
      
      return buffer.toString();
   }
} // class
