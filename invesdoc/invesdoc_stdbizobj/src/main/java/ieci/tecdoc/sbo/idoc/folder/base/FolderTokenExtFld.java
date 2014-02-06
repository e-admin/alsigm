
package ieci.tecdoc.sbo.idoc.folder.base;

import java.io.Serializable;

/**
 * Encapsula la información referente a un campo extendido de 
 * una carpeta. Los campos extendidos son los de texto largo, 
 * y se gestionan de forma distinta a los normales 
 * 
 * @author
 * @see ieci.tecdoc.sbo.idoc.folder.base.FolderTokenRelFld
 * @see ieci.tecdoc.sbo.idoc.folder.base.FolderTokenMultFld
 *
 */
public final class FolderTokenExtFld implements Serializable
{
   /** Identificador del campo */
   private int     m_id;
   /** Nombre del campo */
   private String  m_name;
   /** Valor del campo */
   private String  m_val;
   /** Flag del estado del campo */
   private int     m_editFlag;   
      
   /**
    * Constructor 
    * 
    * @param id identificador del campo
    * @param name nombre del campo
    * @param val valor del campo
    */
   public FolderTokenExtFld(int id, String name, String val)
   {
      m_id         = id;
      m_name       = name;
      m_val        = val;
      m_editFlag   = FolderEditFlag.NONE;      
   }
   
   /**
    * Devuelve el identificador del campo
    * 
    * @return el identificador del campo
    */
   public int getId()
   {
      return m_id;
   }
   
   /**
    * Devuelve el nombre del campo
    * 
    * @return el nombre del campo
    */
   public String getName()
   {
      return m_name;
   }
   
   /**
    * Devuelve el valor del campo
    * 
    * @return el valor del campo
    */
   public String getVal()
   {
      return m_val;
   }
   
   /**
    * Devuelve <tt>true</tt> si el campo está marcado como nuevo
    * 
    * @return <tt>true</tt> si el campo es nuevo
    */
   public boolean isNew()
   {
      boolean is;
      
      if (m_editFlag == FolderEditFlag.NEW)
         is = true;
      else
         is = false;
      
      return is;
   }
   
   /**
    * Devuelve <tt>true</tt> si el campo está marcado como modificado
    * 
    * @return <tt>true</tt> si el campo está marcado como modificado
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
    * Devuelve <tt>true</tt> si el campo está marcado como eliminado
    * 
    * @return <tt>true</tt> si el campo está marcado como eliminado
    */
   public boolean isRemove()
   {
      boolean is;
      
      if (m_editFlag == FolderEditFlag.REMOVE)
         is = true;
      else
         is = false;
      
      return is;
   }
   
   /**
    * Establece el valor del campo. Tambien actualiza el valor
    * del flag (editFlag).
    * 
    * @param value valor del campo
    */
   protected void setVal(String value)
   {
      
      if ( (value == null)&& (m_val != null) )
      {
         if (m_editFlag == FolderEditFlag.NEW)
            m_editFlag = FolderEditFlag.NONE;
         else
            m_editFlag = FolderEditFlag.REMOVE;
      }
      else if ( (m_val == null)&& (value != null))
      {
         if (m_editFlag == FolderEditFlag.REMOVE)
            m_editFlag = FolderEditFlag.UPDATE;
         else
            m_editFlag = FolderEditFlag.NEW;
      }
      else if ((m_val != null) && (value != null))
      {
         if (!m_val.equals(value))
         {
            if (m_editFlag == FolderEditFlag.NONE)
               m_editFlag = FolderEditFlag.UPDATE;            
         }
      }
      
      m_val = value;
      
   }
   
   /**
    * Resetea el valor del tag al valor por defecto (sin estado)
    *
    */
   protected void clearEditInfo()
   {
      m_editFlag = FolderEditFlag.NONE; 
   }
   
   
   
  
   /**
    * @see Object#toString()
    */
   public String toString() {
      
      StringBuffer buffer = new StringBuffer();
      
      buffer.append("FolderTokenExtFld[");
      buffer.append("m_id = ").append(m_id);
      buffer.append(", m_name = ").append(m_name);
      buffer.append(", m_val = ").append(m_val);
      buffer.append(", m_editFlag = ").append(m_editFlag);
      buffer.append("]");
      
      return buffer.toString();
   }
} // class
