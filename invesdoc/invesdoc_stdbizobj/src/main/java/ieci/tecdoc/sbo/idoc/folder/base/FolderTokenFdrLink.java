
package ieci.tecdoc.sbo.idoc.folder.base;

import java.io.Serializable;


/**
 * Clase que encapsula la información referente a un enlace a otra
 * carpeta. Los enlaces a otras carpetas pertenecen a la jerarquía 
 * de nodos de una carpeta.
 * 
 * @author 
 *
 * @see ieci.tecdoc.sbo.idoc.folder.base.FolderTokenDocTreeNode 
 * @see ieci.tecdoc.sbo.idoc.folder.base.FolderTokenDivider 
 * @see ieci.tecdoc.sbo.idoc.folder.base.FolderTokenDocument 
 */
public final class FolderTokenFdrLink extends FolderTokenDocTreeNode implements Serializable
{
   /** Identificador del archivador destino */
   private int     m_srvArchId;
   
   /** Nombre del archivador destino */
   private String  m_srvArchName;
   
   /** Identificador de la carpeta destino */
   private int     m_srvFdrId;
  
   /**
    * Constructor 
    * 
    * @param id identificador del nodo
    * @param name nombre del nodo
    * @param parentId identificador del nodo padre
    * @param srvArchId identificador del archivador destino 
    * @param srvArchName nombre del archivador destino
    * @param srvFdrId identificador de la carpeta destino
    */
   public FolderTokenFdrLink(int id, String name, int parentId,
                             int srvArchId, String srvArchName,
                             int srvFdrId)
   {
      super(id, name, parentId);
      
      m_srvArchId   = srvArchId;
      m_srvArchName = srvArchName;
      m_srvFdrId    = srvFdrId;    
   }   
   
   /**
    * Devuelve el identificador del archivador destino
    * 
    * @return identificador del archivador destino
    */
   public int getSrvArchId()
   {
      return m_srvArchId;
   }
   
   /**
    * Devuelve el nombre del arhivador destino
    * 
    * @return nombre del arhivador destino
    */
   public String getSrvArchName()
   {
      return m_srvArchName;
   }
   
   /**
    * Devuelve el identificador de la carpeta destino
    * 
    * @return identificador de la carpeta destino
    */
   public int getSrvFdrId()
   {
      return m_srvFdrId;
   }  
  
   /**
    * @see Object#toString()
    */
   public String toString() {
      
      StringBuffer buffer = new StringBuffer();
      
      buffer.append("FolderTokenFdrLink[");
      buffer.append(super.toString());
      buffer.append(", m_srvArchId = ").append(m_srvArchId);
      buffer.append(", m_srvArchName = ").append(m_srvArchName);
      buffer.append(", m_srvFdrId = ").append(m_srvFdrId);
      buffer.append("]");
      
      return buffer.toString();
   }
} // class
