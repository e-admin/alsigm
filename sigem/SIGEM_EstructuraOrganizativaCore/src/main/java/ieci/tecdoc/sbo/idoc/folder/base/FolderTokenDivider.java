
package ieci.tecdoc.sbo.idoc.folder.base;

import java.io.Serializable;

/**
 * Clase que encapsula la información referente a un nodo clasificador.
 * Los clasificadores pertenecen a la jerarquía de nodos de una carpeta.
 * 
 * @author 
 * 
 * @see ieci.tecdoc.sbo.idoc.folder.base.FolderTokenDocTreeNode 
 * @see ieci.tecdoc.sbo.idoc.folder.base.FolderTokenFdrLink 
 * @see ieci.tecdoc.sbo.idoc.folder.base.FolderTokenDocument 

 *
 */

public final class FolderTokenDivider extends FolderTokenDocTreeNode implements Serializable
{
   /**  */
   private boolean  m_isFixed;   
      
   /**
    * Constructor 
    * 
    * @param id identificador del clasificador
    * @param name nombre del clasificador
    * @param parentId identificador del clasificador padre
    * @param isFixed <tt>true</tt> si es fijo
    */
   public FolderTokenDivider(int id, String name, int parentId,
                             boolean isFixed)
   {
      super(id, name, parentId);
     
      m_isFixed = isFixed;     
   }
   
   /**
    * Constructor 
    *  
    * @param id identificador del clasificador
    * @param name nombre del clasificador
    * @param parentId identificador del clasificador padre
    * @param isFixed <tt>true</tt> si es fijo
    * @param editFlag
    */
   public FolderTokenDivider(int id, String name, int parentId,
                             boolean isFixed, int editFlag)
   {
      super(id, name, parentId, editFlag);
   
      m_isFixed = isFixed;     
   }
   
   /**
    * Devuelve <tt>true</tt> si el clasificador es fijo
    * @return <tt>true</tt> si el clasificador es fijo
    */
   public boolean isFixed()
   {
      return m_isFixed;
   } 
  
   /**
    * @see Object#toString();
    */
   public String toString() {
      
      StringBuffer buffer = new StringBuffer();
      
      buffer.append("FolderTokenDivider[");
      buffer.append(super.toString());
      buffer.append(", m_isFixed = ").append(m_isFixed);
      buffer.append("]");
      
      return buffer.toString();
   }
} // class
