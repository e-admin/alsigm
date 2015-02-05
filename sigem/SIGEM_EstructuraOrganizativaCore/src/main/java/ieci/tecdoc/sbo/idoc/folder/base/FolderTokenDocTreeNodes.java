
package ieci.tecdoc.sbo.idoc.folder.base;

import java.io.Serializable;
import java.util.ArrayList;
import ieci.tecdoc.sbo.util.types.SboType;
import ieci.tecdoc.core.exception.IeciTdException;

/**
 * Clase base de la jerarquía de colecciones de nodos de carpeta. 
 * Dentro contiene una lista de nodos {@link FoderTokenDocTreeNode}
 * 
 * @author 
 *
 * @see ieci.tecdoc.sbo.idoc.folder.base.FolderTokenDocTreeNodes
 * @see ieci.tecdoc.sbo.idoc.folder.base.FolderTokenDividers
 */
public class FolderTokenDocTreeNodes implements Serializable
{
   /** Lista de nodos */
   private ArrayList m_nodes;
   
   /** Siguiente identificador para un nuevo nodo */
   private int       m_nextNewNodeId;
   
   /**
    * Constructor
    *
    */
   public FolderTokenDocTreeNodes()
   {
      m_nodes = new ArrayList(); 
      m_nextNewNodeId = SboType.NULL_ID - 1;
   }
   
   /**
    * Devuelve el numero de nodos de la lista
    * 
    * @return
    */
   public int count()
   {
      return m_nodes.size();
   } 
   
   /**
    * Agrega un nuevo nodo a la lista
    * 
    * @param idx indice del nuevo nodo
    * @param node nuevo nodo
    */
   protected void add(int idx, FolderTokenDocTreeNode node)
   {
      m_nodes.add(idx, node);
   }
   
   /**
    * Agrega un nuevo nodo a la lista
    * 
    * @param node nuevo nodo
    */
   protected void add(FolderTokenDocTreeNode node)
   {
      m_nodes.add(node);
   }
   
   /**
    * Genera un id para un nuevo nodo
    * 
    * @return id generado
    */
   protected int generateNextNewNodeId()
   {
      int nextId = m_nextNewNodeId;
      
      m_nextNewNodeId = m_nextNewNodeId -1;
      
      return nextId;
   }
   
   /**
    * Devuelve la lista de nodos
    * 
    * @return lista de nodos
    */
   protected ArrayList getNodes()
   {
      return m_nodes;
   }
   
   /**
    * Devuelve el indice de un nodo en la lista a partir de su id
    * 
    * @param id identificador del nodo
    * @return el indice del nodo buscado. <tt>-1</tt> si el nodo 
    * no se encuentra
    * @throws Exception
    */
   public int findIndexById(int id) throws Exception
   {
      int                    i;
      FolderTokenDocTreeNode node = null;
      int                    idx  = -1;
      
      for(i = 0; i < count(); i++)
      {
         node = (FolderTokenDocTreeNode)m_nodes.get(i);
         
         if (node.getId() == id)
         {
            idx = i;
            break;            
         }
      }
      
      return idx;
   }
   
   /**
    * Devuelve el indice de un nodo en la lista a partir de su nombre
    * 
    * @param id identificador del nodo
    * @return el indice del nodo buscado. <tt>-1</tt> si el nodo 
    * no se encuentra
    * @throws Exception
    */
   public int findIndexByName(String name) throws Exception
   {
      int                    i;
      FolderTokenDocTreeNode node = null;
      int                    idx  = -1;
      
      for(i = 0; i < count(); i++)
      {
         node = (FolderTokenDocTreeNode)m_nodes.get(i);
         
         if (node.getName().equals(name))
         {
            idx = i;
            break;            
         }
      }
      
      return idx;      
   }   
   
   /**
    * Devuelve el id del nodo padre de un nodo 
    * 
    * @param idx índice del nodo del que se desea encontrar
    * el padre
    * @return identificador del nodo padre del nodo buscado
    * @throws Exception
    */
   public int getParentId(int idx) throws Exception
   {
      FolderTokenDocTreeNode node = null;
            
      node = (FolderTokenDocTreeNode)m_nodes.get(idx);
      
      return node.getParentId();
   }
   
   /**
    * Devuelve <tt>true</tt> si existe un nodo con el nombre especificado 
    * @param name nombre del nodo
    * @return <tt>true</tt> si existe un nodo con el nombre especificado
    * @throws Exception
    */
   public boolean nameExists(String name)
                  throws Exception
   {
      boolean exists;
      int     idx;
      
      idx = findIndexByName(name);
      
      if (idx == -1)
         exists = false;
      else
         exists = true;
   
      return exists;
   }
   
   /**
    * De vuelve el numero de nodos nuevos que se encuentran en la lista de nodos
    * 
    * @return numero de nodos nuevos que se encuentran en la lista
    * @throws Exception
    */
   public int getNumNewNodes() throws Exception
   {
      int                    i;
      FolderTokenDocTreeNode node = null;
      int                    num = 0;
      
      for(i = 0; i < count(); i++)
      {
         node = (FolderTokenDocTreeNode)m_nodes.get(i);
         
         if ( node.isNew() ) 
         {
            num = num + 1; 
         }
      }
      
      return num;
   }
   
   /**
    * Devuelve el numero de nodos eliminados que se encuentran en la lista de nodos
    * 
    * @return numero de nodos eliminados que se encuentran en la lista
    * @throws Exception
    */
   public int getNumRemoveNodes() throws Exception
   {
      int                    i;
      FolderTokenDocTreeNode node = null;
      int                    num = 0;
      
      for(i = 0; i < count(); i++)
      {
         node = (FolderTokenDocTreeNode)m_nodes.get(i);
         
         if ( node.isRemove() ) 
         {
            num = num + 1; 
         }
      }
      
      return num;
   }
   
   /**
    * Devuelve el numero de nodos modificados que se encuentran en la lista de nodos
    * 
    * @return numero de nodos modificados que se encuentran en la lista
    * @throws Exception
    */
   public int getNumUpdateNodes() throws Exception
   {
      int                    i;
      FolderTokenDocTreeNode node = null;
      int                    num = 0;
      
      for(i = 0; i < count(); i++)
      {
         node = (FolderTokenDocTreeNode)m_nodes.get(i);
         
         if ( node.isUpdate() ) 
         {
            num = num + 1; 
         }
      }
      
      return num;
   }
   
   /**
    * Resetea el contador de nuevos ids de nodos
    * 
    * @throws Exception
    */
   protected void resetNextNewNodeId()throws Exception
   {
      m_nextNewNodeId = SboType.NULL_ID;
   } 
   
   /**
    * Elimina de la lista de nodos el nodo con el id especificado
    * 
    * @param id identificador del nodo a eliminar
    * @throws Exception arroja IeciTdException si el nodo no se encuentra
    * en la lista
    */
   protected void removeNode(int id)throws Exception
   {      
       int                    idx;
       FolderTokenDocTreeNode node = null;
            
       idx = findIndexById(id);
         
       if (idx == -1)
       {
          throw new IeciTdException(FolderBaseError.EC_NOT_FOUND, 
                                    FolderBaseError.EM_NOT_FOUND); 

       }
       
       node = (FolderTokenDocTreeNode)m_nodes.get(idx);
       
       if (node.isNew())
          m_nodes.remove(idx);
       else
          node.setEditFlag(FolderEditFlag.REMOVE);         
       
   } 
   
   /**
    * Elimina definitivamente de la lista de nodos un nodo que estaba
    * marcado como eliminado. 
    * 
    * @param id identificador del nodo a borrar 
    * @throws Exception arroja IeciTdException si no encuentra el nodo
    * o si dicho nodo no esta marcado como eliminado.
    */
   protected void refreshRemoveNode(int id)throws Exception
   {      
       int                    idx;
       FolderTokenDocTreeNode node = null;
            
       idx = findIndexById(id);
         
       if (idx == -1)
       {
          throw new IeciTdException(FolderBaseError.EC_NOT_FOUND, 
                                    FolderBaseError.EM_NOT_FOUND); 

       }
       
       node = (FolderTokenDocTreeNode)m_nodes.get(idx);
       
       if (!node.isRemove())
       {
          throw new IeciTdException(FolderBaseError.EC_NO_REMOVE_NODE, 
                                    FolderBaseError.EM_NO_REMOVE_NODE); 

       }
       
       m_nodes.remove(idx);
   }
   
   /**
    * Elimina todos los nodos marcados como eliminados
    * 
    * @throws Exception
    */
   protected void refreshRemoveNodes()throws Exception
   {      
      int                    i;
      FolderTokenDocTreeNode node = null;
      int                    numNodes = count();
      
      for(i = 0; i < numNodes; i++)
      {
         node = (FolderTokenDocTreeNode)m_nodes.get(i);
         
         if ( node.isRemove() ) 
         {
            m_nodes.remove(i);
            numNodes = numNodes - 1; 
         }
      }
   } 
   
   /**
    * Devuelve el identificador de un nodo de la lista a partir del
    * nombre de dicho nodo
    * 
    * @param name nombre del nodo
    * @return identificador del nodo
    * @throws Exception
    */
   protected int getNodeId(String name) throws Exception
   {
      FolderTokenDocTreeNode node = null;
      int                    idx;
      
      idx =  findIndexByName(name);
            
      node = (FolderTokenDocTreeNode)m_nodes.get(idx);
            
      return node.getId();
   }

      
   /**
    * @see Object#toString()
    */
   public String toString() {
      
      StringBuffer buffer = new StringBuffer();
      
      buffer.append("FolderTokenDocTreeNodes[");
      
      for(int i = 0; i < m_nodes.size(); i++)
      {

          buffer.append(" [m_nodes_").append(i+1);
          buffer.append(" = ").append((m_nodes.get(i)).toString());
          buffer.append("] ");

      }
      
      buffer.append(", m_nextNewNodeId = ").append(m_nextNewNodeId);
      buffer.append("]");
      
      return buffer.toString();
   }
} // class
