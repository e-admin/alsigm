package ieci.tecdoc.sbo.idoc.folder.base;

import java.io.Serializable;
import java.util.ArrayList;
import ieci.tecdoc.core.exception.IeciTdException;

/**
 * Representa una colección con los campos relacionales de una carpeta  
 */

public final class FolderTokenRelFlds implements Serializable
{
   
   /**
    * Colección de campos relacionales de una carpeta. Cada elemento de esta
    * colección es un objeto de tipo FolderTokenRelFld
    */
   private ArrayList m_relFlds;
   
   /**
    * Constructor 
    */
   public FolderTokenRelFlds()
   {
      m_relFlds = new ArrayList();      
   }
   
   /**
    * Devuelve el número de campos relacionales de una carpeta
    * @return número de campos relacionales de una carpeta
    */
   public int count()
   {
      return m_relFlds.size();
   }
      
   /**
    * Añade un campo relacional a la colección
    * @param id identificador del campo
    * @param name nombre del campo
    * @param val valor del campo
    */
   public void add(int id, String name, Object val)
   {
      
      FolderTokenRelFld fld;
      
      fld = new FolderTokenRelFld(id, name, val);
      
      m_relFlds.add(fld);
      
   } 
   
   /**
    * Añade un campo relacional a la colección
    * @param fld referencia a un objeto de tipo FolderTokenRelFld que representa
    * a un campo relacional de la carpeta
    * @see FolderTokenRelFld
    */
   public void add(FolderTokenRelFld fld)
   {      
      m_relFlds.add(fld);      
   }  
   
   /**
    * Devuelve el campo relacional i-ésimo de la carpeta
    * @param index índice i-esimo
    * @return referencia a un objeto de tipo FolderTokenRelFld que representa
    * a un campo relacional de la carpeta
    * @see FolderTokenRelFld
    */
   public FolderTokenRelFld get(int index)
   {
      return (FolderTokenRelFld) m_relFlds.get(index);
   } 
   
   /**
    * Busca un campo relacional a partir del identificador
    * @param fldId identificador del campo relacional a buscar
    * @return referencia a un objeto de tipo FolderTokenRelFld que representa
    * a un campo relacional de la carpeta
    * @throws Exception si no encuentra el campo relacional
    * @see FolderTokenRelFld
    */
   public FolderTokenRelFld findById(int fldId) throws Exception
   {
      int               i;
      FolderTokenRelFld fld = null;
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
    * Busca un campo relacional a partir del nombre
    * @param fldName nombre del campo relacional a buscar
    * @return referencia a un objeto de tipo FolderTokenRelFld que representa
    * a un campo relacional de la carpeta
    * @throws Exception si no encuentra el campo relacional
    * @see FolderTokenRelFld
    */
   public FolderTokenRelFld findByName(String fldName) throws Exception
   {
      int               i;
      FolderTokenRelFld fld = null;
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
    * Devuelve el valor del campo relacional que tiene un determinado nombre
    * @param fldName nombre del campo relacional
    * @return valor del campo
    * @throws Exception si no encuentra el campo
    */
   public Object getFldValue(String fldName) throws Exception
   {
      FolderTokenRelFld fld = null;
      Object            val;
      
      fld = findByName(fldName);
      
      val = fld.getVal();   
      
      return val;
   }
   
   /**
    * Devuelve el valor del campo relacional que tiene un determinado identificador
    * @param fldId identificador del campo relacional
    * @return valor del campo
    * @throws Exception si no encuentra el campo
    */   
   public Object getFldValue(int fldId) throws Exception
   {
      FolderTokenRelFld fld = null;
      Object            val;
      
      fld = findById(fldId);
      
      val = fld.getVal();   
      
      return val;
   }
   
   /**
    * Devuelve el valor del campo relacional i-ésimo de la colección
    * @param idx índice
    * @return valor del campo
    * @throws Exception
    */
   public Object getValue(int idx) throws Exception
   {
      FolderTokenRelFld fld = null;
      Object            val;
      
      fld = get(idx);
      
      val = fld.getVal();   
      
      return val;
   }
   
   /**
    * Devuelve el índice dentro de la colección en el que se encuentra
    * un campo relacional con un determinado identificador
    * @param fldId identificador del campo relacional
    * @return índice dentro de la colección; -1 si no se encuentra
    * @throws Exception
    */
   public int findIndexById(int fldId) throws Exception
   {
      int               i;
      FolderTokenRelFld fld = null;
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
    * Establece el valor del campo relacional que se encuentra en la posición
    * i-ésima de la colección
    * @param idx índice
    * @param val valor
    * @throws Exception
    */
   protected void setValue(int idx, Object val) throws Exception
   {
      FolderTokenRelFld fld = null;     
      
      fld = get(idx);
      
      fld.setVal(val); 
     
   }
   
   /**
    * Marca todos los campos de la colección con estado NONE
    * @throws Exception
    */
   protected void clearEditInfo() throws Exception
   {
      int                i;
      FolderTokenRelFld  fld = null;
      
      for(i = 0; i < count(); i++)
      {
         fld = get(i);
         fld.clearEditInfo();  
      }      
   }
   
   /**
    * Informa de si existe algún campo cuyo estado sea UPDATE
    * @return true si algun campo ha sido modificado; false en caso 
    * contrario
    * @throws Exception
    */
   public boolean existsAnyFldUpdate() throws Exception
   {
      int                i;
      FolderTokenRelFld  fld    = null;
      boolean            exists = false;
      
      for(i = 0; i < count(); i++)
      {
         fld = get(i);
         if (fld.isUpdate())
         {
            exists = true;
            break;
         }
      }
      
      return exists;      
   }
   
   /**
    * Devuelve la colección de campos relacionales cuyo estado sea UPDATE
    * @return referencia a un objeto de tipo FolderTokenRelFlds que contiene
    * una colección de campos relacionales
    * @throws Exception
    */
   public FolderTokenRelFlds getUpdateFlds() throws Exception
   {
      int                 i;
      FolderTokenRelFlds  updFlds = new FolderTokenRelFlds();
      FolderTokenRelFld   fld    = null;
           
      for(i = 0; i < count(); i++)
      {
         fld = get(i);
         if (fld.isUpdate())
         {
            updFlds.add(fld);           
         }
      }
      
      return updFlds;      
   }
      
   /**
    * Devuelve la representación del objeto, que corresponde con
    * una colección de campos relacionales
    * @return representación del objeto, que corresponde con
    * una colección de campos relacionales
    */
   public String toString() {
      
      StringBuffer buffer = new StringBuffer();
      
      buffer.append("FolderTokenRelFlds[");
      
      for(int i = 0; i < m_relFlds.size(); i++)
      {

          buffer.append(" [folderTokenRelFlds_").append(i+1);
          buffer.append(" = ").append((m_relFlds.get(i)).toString());
          buffer.append("] ");

      }
      
      buffer.append("]");
     
      return buffer.toString();
   }
} // class
