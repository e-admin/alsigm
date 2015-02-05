
package ieci.tecdoc.sbo.idoc.folder.base;

import java.io.Serializable;

/**
 * Clase base de la jerarquía de nodos para el árbol de documentos de
 * una carpeta. 
 * 
 * @author
 * @see ieci.tecdoc.sbo.idoc.folder.base.FolderTokenDivider 
 * @see ieci.tecdoc.sbo.idoc.folder.base.FolderTokenFdrLink 
 * @see ieci.tecdoc.sbo.idoc.folder.base.FolderTokenDocument 
 *
 */

public class FolderTokenDocTreeNode implements Serializable
{
   /** Identificador del nodo */
   private int     m_id;
   
   /** Nombre del nodo */
   private String  m_name;
   
   /** Identificador del nodo padre */
   private int     m_parentId;
   
   /** Flag con el estado del nodo */
   private int     m_editFlag;   
      
   /**
    * Constructor
    * 
    * @param id identificador del nodo
    * @param name nombre del nodo
    * @param parentId identificador del padre del nodo
    */
   protected FolderTokenDocTreeNode(int id, String name, int parentId)
   {
      m_id         = id;
      m_name       = name;
      m_parentId   = parentId;
      m_editFlag   = FolderEditFlag.NONE;      
   }
   
   /**
    * Constructor
    * 
    * @param id identificador del nodo
    * @param name nombre del nodo
    * @param parentId identificador del padre del nodo
    * @param editFlag estado del nodo 
    * @see FolderEditFlag
    */
   protected FolderTokenDocTreeNode(int id, String name, int parentId,
                                    int editFlag)
   {
      m_id         = id;
      m_name       = name;
      m_parentId   = parentId;
      m_editFlag   = editFlag;      
   }
   
   /**
    * Devuelve el identificador del nodo
    * 
    * @return identificador del nodo
    */
   public int getId()
   {
      return m_id;
   }
   
   /**
    * Devuelve el nombre del nodo
    * 
    * @return el nombre del nodo
    */
   public String getName()
   {
      return m_name;
   }
   
   /** 
    * Devuelve el identificador del padre del nodo
    * 
    * @return identificador del padre del nodo
    */
   public int getParentId()
   {
      return m_parentId;
   }
   
   /**
    * Devuelve <tt>true</tt> si el nodo es nuevo
    * 
    * @return <tt>true</tt> si el nodo es nuevo
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
    * Devuelve <tt>true</tt> si el nodo ha sido eliminado
    * 
    * @return <tt>true</tt> si el nodo ha sido eliminado
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
    * Devuelve <tt>true</tt> si el nodo ha sido modificado
    * 
    * @return <tt>true</tt> si el nodo ha sido modificado
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
    * Cambia el padre del nodo por el indicado
    * 
    * @param newParentId identificador del nuevo nodo padre
    */
   protected void changeParentId(int newParentId)
   {
      m_parentId = newParentId;
   }
   
   /**
    * Cambia el identificador del nodo
    * 
    * @param newId el nuevo identificador del nodo
    */
   protected void changeId(int newId)
   {
      m_id = newId;
   }
   
   /**
    * Limpia el flag de estado del nodo
    *
    */
   protected void clearEditInfo()
   {
      m_editFlag = FolderEditFlag.NONE;  
   }
   
   /**
    * Establece un nuevo valo para el flag de estado
    * 
    * @param flag nuevo valo para el flag de estado
    * @see FolderEditFlag
    */
   protected void setEditFlag(int flag)
   {
      m_editFlag = flag;  
   }
   
   /**
    * Cambia el nombre del nodo
    * 
    * @param name nuevo nombre para el nodo
    */
   protected void rename(String name)
   {
      m_name = name;

      if (m_editFlag == FolderEditFlag.NONE)
         m_editFlag = FolderEditFlag.UPDATE;
   }
   
   /**
    * Devuelve <tt>true</tt> si se ha modificado el flag de estado
    * del nodo
    * 
    * @return <tt>true</tt> si se ha modificado el flag de estado
    * del nodo
    */
   public boolean isModified()
   {
      boolean is;
      
      if (m_editFlag == FolderEditFlag.NONE)
         is = false;
      else
         is = true;

      return is;
   }
   
   
   /**
    * @see Object#toString()
    */
   public String toString() {
    
      StringBuffer buffer = new StringBuffer();
      
      buffer.append("FolderTokenDocTreeNode[");
      buffer.append("m_id = ").append(m_id);
      buffer.append(", m_name = ").append(m_name);
      buffer.append(", m_parentId = ").append(m_parentId);
      buffer.append(", m_editFlag = ").append(m_editFlag);
      buffer.append("]");
      
      return buffer.toString();
   }
} // class
