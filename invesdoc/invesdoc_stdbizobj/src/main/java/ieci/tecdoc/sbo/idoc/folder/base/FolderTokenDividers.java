
package ieci.tecdoc.sbo.idoc.folder.base;

import java.io.Serializable;

import ieci.tecdoc.core.exception.IeciTdException;
import ieci.tecdoc.core.collections.IeciTdLiLiArrayList;
import ieci.tecdoc.core.collections.IeciTdLiLi;

/**
 * Representa una lista de nodos clasificadores, {@link FolderTokenDivider}. Proporciona
 * métodos para gestionar dicha lista.
 * 
 * @author 
 *
 * @see ieci.tecdoc.sbo.idoc.folder.base.FolderTokenDocTreeNode
 * @see ieci.tecdoc.sbo.idoc.api.FolderDividerObjects
 * @see ieci.tecdoc.sbo.idoc.folder.base.FolderTokenDocuments
 * @see ieci.tecdoc.sbo.idoc.folder.base.FolderTokenFdrLinks
 */
public final class FolderTokenDividers extends FolderTokenDocTreeNodes implements Serializable
{
   /**
    * Constructor
    *
    */
   public FolderTokenDividers()
   {
      super();     
   }
      
   /**
    * Agrega un nuevo nodo clasificador a la lista de nodos
    * 
    * @param id identificador del nodo
    * @param name nombre del nodo
    * @param parentId identificador del nodo padre
    * @param isFixed <tt>true</tt> si el nodo es fijo
    */
   public void add(int id, String name, int parentId,
                   boolean isFixed)
   {
      
      FolderTokenDivider div;
      
      div = new FolderTokenDivider(id, name, parentId, isFixed);
      
      super.add(div);
      
   }  
   
   /**
    * Agrega un nuevo nodo clasificador a la lista obteniendo el siguiente
    * identificador para ese nodo.
    * 
    * @param name nombre del nodo
    * @param parentId identificador del nodo padre
    * @param isFixed <tt>true</tt> si el nodo es fijo
    * @return el identificador asignado al nodo
    * @throws Exception
    */
   public int addNewDivider(String name, int parentId,
                            boolean isFixed) 
              throws Exception
   {

      FolderTokenDivider div;
      int                id;
      
      id = super.generateNextNewNodeId(); 
      
      div = new FolderTokenDivider(id, name, parentId, 
                                   isFixed, FolderEditFlag.NEW);
      
      super.add(div);
      
      return id;

   } 
   
   /**
    * Devuelve el nodo con el índice especificado
    * 
    * @param index índice del nodo
    * @return El nodo clasificador
    */
   public FolderTokenDivider get(int index)
   {
      return (FolderTokenDivider) super.getNodes().get(index);
   } 
   
   /**
    * Busca un nodo clasificador a partir de su id
    * 
    * @param id identificador del nodo
    * @return el nodo encontrado
    * @throws Exception arroja IeciTdException si no encuentra el nodo
    * en la lista
    */
   public FolderTokenDivider findById(int id) throws Exception
   {
      int                idx;
      FolderTokenDivider div = null;
           
      idx = super.findIndexById(id);
      
      if (idx == -1)
      {
         throw new IeciTdException(FolderBaseError.EC_NOT_FOUND, 
                                   FolderBaseError.EM_NOT_FOUND); 

      }
      
      div = get(idx);
      
      return div;
   }
   
   /**
    * Busca un nodo clasificador a partir de su nombre
    * 
    * @param name nombre del nodo
    * @return el nodo encontrado
    * @throws Exception arroja IeciTdException si no encuentra el nodo
    * en la lista
    */
   public FolderTokenDivider findByName(String name) throws Exception
   {
      int                idx;
      FolderTokenDivider div = null;
           
      idx = super.findIndexByName(name);
      
      if (idx == -1)
      {
         throw new IeciTdException(FolderBaseError.EC_NOT_FOUND, 
                                   FolderBaseError.EM_NOT_FOUND); 

      }
      
      div = get(idx);
      
      return div;
   }
   
   
   /**
    * Devuelve la lista de nodos clasificadores hijos del nodo especificado
    * 
    * @param parentId identificador del nodo padre
    * @param alsoRemoveChildren Si este flag esta a <tt>true</tt> tambien se 
    * agregan los nodos hijos marcados como eliminados  
    * @return lista de nodos clasificadores hijos
    * @throws Exception
    */
   public FolderTokenDividers getChildren(int parentId, boolean alsoRemoveChildren) 
                               throws Exception
   {
      int                  i;
      FolderTokenDividers divs = new FolderTokenDividers();
      FolderTokenDivider  div; 
      boolean             addDiv;     
      
      for(i = 0; i < count(); i++)
      {
         div = get(i);
         
         if (div.getParentId() == parentId)
         {
            addDiv = true;
            
            if (!alsoRemoveChildren && div.isRemove())
               addDiv = false;
            
            if (addDiv)
               divs.add(div);                  

         }
      }
      
      return divs;
   }
   
   /**
    * Devuelve una nueva lista con todos los nodos clasificadores
    * 
    * @param alsoRemoveChildren Si este flag esta a <tt>true</tt> tambien se 
    * agregan los nodos hijos marcados como eliminados  
    * @return
    * @throws Exception
    */
   protected FolderTokenDividers getAllDividers(boolean alsoRemoveDividers) 
                                 throws Exception
   {
      int                 i;
      FolderTokenDividers divs = new FolderTokenDividers();
      FolderTokenDivider  div; 
      boolean             addDiv; 
      
      for(i = 0; i < count(); i++)
      {
         addDiv = true;
         
         div = get(i);
         
         if (div.isRemove() && !alsoRemoveDividers)
         {
            addDiv = false;            
         }
         
         if (addDiv)
            divs.add(div);
      }
      
      return divs;
   }
   
   /**
    * Devuelve una lista con todos los nodos marcados como nuevos.
    * Se ordenan para que vengan los padres antes que los hijos
    * 
    * @return lista con todos los nodos marcados como nuevos
    * @throws Exception
    */
   protected FolderTokenDividers getNewDividers() 
                                 throws Exception
   {
      int                  i, j;
      FolderTokenDividers divs = new FolderTokenDividers();
      FolderTokenDivider  div;       
      FolderTokenDivider  newDiv;
      boolean             addDiv; 
                      
      
      for(i = 0; i < count(); i++)
      {
         div = get(i);
         
         if (div.isNew())
         {
            addDiv = false;
                                            
            for(j = 0; j < divs.count(); j++)
            {
               newDiv = divs.get(j);
               
               if ( newDiv.getId() < div.getId() )
               {
                  divs.add(j, div);
                  addDiv = true;
                  break;                  
               }
               
            }
            
            if (!addDiv)
               divs.add(div);                  

         }
         
      }
      
      return divs;
   }
   
   /**
    * Devuelve una lista con todos los nodos marcados como eliminados.
    * 
    * @return lista con todos los nodos marcados como eliminados
    * @throws Exception
    */
   protected FolderTokenDividers getRemoveDividers() 
                                 throws Exception
   {
      int                  i;
      FolderTokenDividers divs = new FolderTokenDividers();
      FolderTokenDivider  div;   
      
      for(i = 0; i < count(); i++)
      {
         div = get(i);
         
         if (div.isRemove())
         {
           divs.add(div);
         }         
      }
      
      return divs;
   }
   
   /**
    * Devuelve una lista con todos los nodos marcados como modificados.
    * 
    * @return lista con todos los nodos marcados como modificados
    * @throws Exception
    */
   protected FolderTokenDividers getUpdateDividers() 
                                 throws Exception
   {
      int                  i;
      FolderTokenDividers divs = new FolderTokenDividers();
      FolderTokenDivider  div;   
      
      for(i = 0; i < count(); i++)
      {
         div = get(i);
         
         if (div.isUpdate())
         {
           divs.add(div);
         }         
      }
      
      return divs;
   }
      
   /**
    * Cambia los nodos hijos de un nodo a otro. 
    * 
    * @param parentId identificador del antiguo padre
    * @param newParentId identificador del nuevo padre
    * @throws Exception
    */
   private void refreshParentId(int parentId, int newParentId) 
                throws Exception
   {
      FolderTokenDividers divs = null;
      int                 i;
      FolderTokenDivider  div = null;
      
      divs = getChildren(parentId, true);
      
      for(i = 0; i < divs.count(); i++)
      {
         div = divs.get(i);
         div.changeParentId(newParentId);
      }       

   }
   
   /**
    * Encuentra todos los nodos marcados como modificados y les restea
    * el flag de estado, es decir, les quita la marca de modificados
    *
    */
   protected void refreshUpdateDividers() 
   {
      int                 i;
      FolderTokenDivider  div = null;
      
      for(i = 0; i < count(); i++)
      {
         div = get(i);
         
         if (div.isUpdate())         
            div.clearEditInfo(); 
      } 
   }
   
   /**
    * 
    * @param newDivIds
    * @throws Exception
    */
   protected void refreshNewDividers(IeciTdLiLiArrayList newDivIds) 
                  throws Exception
   {
      int                 i;
      FolderTokenDivider  div = null;
      IeciTdLiLi          newDivId;
      int                 id;
      int                 newId;
      
      for(i = 0; i < newDivIds.count(); i++)
      {
         newDivId = newDivIds.get(i);
         id       = newDivId.m_val1;
         newId    = newDivId.m_val2;
         
         div       = findById(id); 
         
         div.changeId(newId);
         refreshParentId(id, newId); 
         
         div.clearEditInfo(); 
      } 
      
      super.resetNextNewNodeId();
   }
   
      
} // class
