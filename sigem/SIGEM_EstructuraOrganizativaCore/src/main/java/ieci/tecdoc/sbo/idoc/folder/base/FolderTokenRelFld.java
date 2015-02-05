
package ieci.tecdoc.sbo.idoc.folder.base;

import java.io.Serializable;

/**
 * Representa un campo relacional de una carpeta
 * @author
 */

public final class FolderTokenRelFld implements Serializable
{
   
   /**
    * Identificador del campo
    */
   private int     m_id;
   
   /**
    * Nombre del campo
    */
   private String  m_name;
   
   /**
    * Valor del campo
    */
   private Object  m_val;
   
   /**
    * Estado del campo:
    * <li> FolderEditFlag.NONE
    * <li> FolderEditFlag.UPDATE
    * @see FolderEditFlag
    */
   private int     m_editFlag;   
      
   /**
    * Constructor
    * @param id identificador del campo relacional
    * @param name nombre del campo
    * @param val valor del campo
    */
   public FolderTokenRelFld(int id, String name, Object val)
   {
      m_id         = id;
      m_name       = name;
      m_val        = val;
      m_editFlag   = FolderEditFlag.NONE;      
   }
   
   /**
    * Devuelve el identificador del campo relacional
    * @return identificador del campo relacional
    */
   public int getId()
   {
      return m_id;
   }

   /**
    * Devuelve el nombre del campo relacional
    * @return nombre del campo relacional
    */   
   public String getName()
   {
      return m_name;
   }
   
   /**
    * Devuelve el valor del campo relacional
    * @return valor del campo relacional
    */   
   public Object getVal()
   {
      return m_val;
   }
   
   /**
    * Establece el valor del campo relacional y marca
    * el campo como actualizado
    * @param val valor del campo relacional
    */
   protected void setVal(Object val)
   {
      m_val = val;
      
      if (m_editFlag == FolderEditFlag.NONE)
      {
         m_editFlag = FolderEditFlag.UPDATE;
      }
   }
   
   /**
    * Marca el campo con estado NONE
    */
   protected void clearEditInfo()
   {
      m_editFlag = FolderEditFlag.NONE; 
   }
   
   /**
    * Pregunta si el campo tiene estado UPDATE
    * @return true si ha sido actualizado; false en caso contrario
    */
   public boolean isUpdate()
   {
      boolean is;
      
      if (m_editFlag == FolderEditFlag.UPDATE)
         is = true;
      else
         is = false;
      
      return is;
   }   
  
   /**
    * Devuelve un cadena que representa el objeto, en este caso,
    * la representación de un campo relacional de una carpeta
    */
   public String toString() {
      
      StringBuffer buffer = new StringBuffer();
      
      buffer.append("FolderTokenRelFld[");
      buffer.append("m_id = ").append(m_id);
      buffer.append(", m_name = ").append(m_name);
      buffer.append(", m_val = ").append(m_val);
      buffer.append(", m_editFlag = ").append(m_editFlag);
      buffer.append("]");
      
      return buffer.toString();
   }
} // class
