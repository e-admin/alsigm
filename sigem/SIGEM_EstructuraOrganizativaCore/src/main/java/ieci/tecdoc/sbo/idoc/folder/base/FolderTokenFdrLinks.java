
package ieci.tecdoc.sbo.idoc.folder.base;

import java.io.Serializable;

import ieci.tecdoc.core.exception.IeciTdException;

/**
 * Representa una lista de enlaces, {@link FolderTokenFdrLink}. 
 * Proporciona métodos para gestionar dicha lista.
 * 
 * @author 
 *
 * @see ieci.tecdoc.sbo.idoc.folder.base.FolderTokenDocTreeNode
 * @see ieci.tecdoc.sbo.idoc.api.FolderFdrLinkObject
 * @see ieci.tecdoc.sbo.idoc.folder.base.FolderTokenDividers
 * @see ieci.tecdoc.sbo.idoc.folder.base.FolderTokenDocuments
 */

public final class FolderTokenFdrLinks extends FolderTokenDocTreeNodes implements Serializable
{
   /**
    * Constructor
    *
    */
   public FolderTokenFdrLinks()
   {
      super();     
   }
     
   /**
    * Agrega un nuevo enlace a la lista
    * 
    * @param id identificador del nodo
    * @param name nombre del enlace
    * @param parentId identificador del nodo padre
    * @param srvArchId identificador del archivador destino
    * @param srvArchName nombre del archivador destino
    * @param srvFdrId identificador de la carpeta destino
    */
   public void add(int id, String name, int parentId,
                             int srvArchId, String srvArchName,
                             int srvFdrId)
   {
      
      FolderTokenFdrLink link;
      
      link = new FolderTokenFdrLink(id, name, parentId,
                                    srvArchId, srvArchName, srvFdrId);
      
      super.add(link);
      
   }  
   
   /**
    * Devuelve el enlace con el indice especificado 
    * @param index indice del enlace
    * @return enlace buscado
    * @see FolderTokenFdrLink
    */
   public FolderTokenFdrLink get(int index)
   {
      return (FolderTokenFdrLink) super.getNodes().get(index);
   } 
   
   /**
    * Devuelve el enlace con el identificador especificado 
    * @param id identificador del enlace
    * @return enlace buscado
    * @throws Exception devuelve IeciTdException si no encuentra el
    * enlace en la lista
    * @see FolderTokenFdrLink
    */
   public FolderTokenFdrLink findById(int id) throws Exception
   {
      int                idx;
      FolderTokenFdrLink link = null;
           
      idx = super.findIndexById(id);
      
      if (idx == -1)
      {
         throw new IeciTdException(FolderBaseError.EC_NOT_FOUND, 
                                   FolderBaseError.EM_NOT_FOUND); 

      }
      
      link = get(idx);
      
      return link;
   }
   
   /**
    * Devuelve el enlace con el nombre especificado 
    * @param name nombre del enlace
    * @return enlace buscado
    * @throws Exception devuelve IeciTdException si no encuentra el
    * enlace en la lista
    * @see FolderTokenFdrLink
    */
   public FolderTokenFdrLink findByName(String name) throws Exception
   {
      int                idx;
      FolderTokenFdrLink link = null;
           
      idx = super.findIndexByName(name);
      
      if (idx == -1)
      {
         throw new IeciTdException(FolderBaseError.EC_NOT_FOUND, 
                                   FolderBaseError.EM_NOT_FOUND); 

      }
      
      link = get(idx);
      
      return link;
   }
   
   /**
    * Devuelve una lista con todos los enlaces
    * @param alsoRemoveLinks Si este flag está a <tt>true</tt>
    * tambien se copian los nodos marcados como eliminados
    * @return lista con todos los enlaces
    * @throws Exception
    */
   protected FolderTokenFdrLinks getAllFolderLinks(boolean alsoRemoveLinks) 
                                 throws Exception
   {
      int                 i;
      FolderTokenFdrLinks links = new FolderTokenFdrLinks();
      FolderTokenFdrLink  link; 
      boolean             addLink; 
      
      for(i = 0; i < count(); i++)
      {
         addLink = true;
         
         link = get(i);
         
         if (link.isRemove() && !alsoRemoveLinks)
         {
            addLink = false;            
         }
         
         if (addLink)
            links.add(link);
      }
      
      return links;
   }
   
   /**
    * Devuelve una lista con los enlaces hijos del nodo especificado
    * @param parentId identificador del nodo padre
    * @param alsoRemoveChildren Si este flag está a <tt>true</tt>
    * tambien se copian los nodos marcados como eliminados
    * @return lista de enlaces hijos del nodo especificado
    * @throws Exception
    */
   public FolderTokenFdrLinks getChildren(int parentId, boolean alsoRemoveChildren) 
                              throws Exception
   {
      int                 i;
      FolderTokenFdrLinks links = new FolderTokenFdrLinks();
      FolderTokenFdrLink  link; 
      boolean             addLink;           
      
      for(i = 0; i < count(); i++)
      {
         link = get(i);
         
         if (link.getParentId() == parentId)
         {
            addLink = true;
            
            if (!alsoRemoveChildren && link.isRemove())
               addLink = false;
            
            if (addLink)                    
               links.add(link);                    
         
         }
      }
      
      return links;
   }
   
      
} // class
