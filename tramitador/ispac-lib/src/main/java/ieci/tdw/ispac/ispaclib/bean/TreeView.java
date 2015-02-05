package ieci.tdw.ispac.ispaclib.bean;

import ieci.tdw.ispac.ispaclib.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.JXPathIntrospector;
import org.apache.log4j.Logger;


/**
 * Componente empleado en la visualización de una jerarquia de elementos en
 * forma de arbol
 */
public class TreeView implements INode, HttpSessionBindingListener {

    /** Logger de la clase */
    private static final Logger logger = Logger.getLogger(TreeView.class);
    
   protected String name = null;

   /**  Listas de nodos raiz */
   protected List treeNodes;

   protected TreeModel treeModel = null;

   TreeNode selectedNode = null;

   NodeSelectionHandlerAction nodeSelectionHandler = null;

   Comparator nodeComparator = null;
   
   String viewName = null;
   
   String language = null;
   
   public TreeView(String name, TreeModel model, String language) {
      this.name = name;
      //this.viewName = Messages.getString(name);
      this.viewName = name;
      this.language = language;
      this.initialize(model);
   }

   private void initialize(TreeModel model) {
      this.treeNodes = new ArrayList();
      Collection rootItems = model.getRootNodes();
      if (model != null && rootItems != null) {
         for (Iterator i = rootItems.iterator(); i.hasNext();)
            insertNode(null, (TreeModelItem) i.next());
         this.treeModel = model;
      }
   }

   /**
    * Colapsa los nodos raiz del arbol
    * 
    * @param recursive
    *           Indica si el colapsado se debe propagar tambien de forma
    *           recursiva a todos los niveles de la jerarquia
    * @throws Exception
    */
   /*public void collapse(boolean recursive) throws Exception {
      for (Iterator i = this.treeNodes.iterator(); i.hasNext();)
         ((TreeNode) i.next()).setCollapsed(true, recursive);
   }*/

   /**
    * Recupera un nodo del arbol a partir del path de dicho nodo dentro de la
    * jerarquia
    * 
    * @param nodePath
    * @return El nodo correspondiente al path solicitado o nulo en caso de no
    *         ser un paz valido dentro de la jerarquia
    */
   public TreeNode getNode(String nodePath) {
      synchronized (this) {
         try {
            JXPathIntrospector.registerDynamicClass(TreeNode.class, TreeNodeXPathHandler.class);
            JXPathIntrospector.registerDynamicClass(TreeView.class, TreeNodeXPathHandler.class);
            JXPathContext xPathContext = JXPathContext.newContext(this);
            return (TreeNode) xPathContext.getValue(nodePath);            
         } 
         catch (Exception e) {
        	 return null;
         }
      }
   }


   /**
    * Incorpora un nuevo nodo a la jerarquia
    * 
    * @param parent
    *           Elemento padre del nodo que se va a incorporar
    * @param modelItem
    *           Información a almacenar en el nodo a incorporar a la jerarquia
    * @return El nodo insertado
    */
   public TreeNode insertNode(TreeNode parent, TreeModelItem modelItem) {
	   
      TreeNode newNode = new TreeNode(modelItem, parent, this, language);
      if (parent == null) {
         treeNodes.add(newNode);
         if (nodeComparator != null)
            Collections.sort(treeNodes, nodeComparator);
      }
      return newNode;
   }

   public void removeNode(TreeNode node) {
      if (node.getParent() == null)
         treeNodes.remove(node);
      else
         node.getParent().removeChild(node);
   }

   public Collection getRootNodes() {
      return this.treeNodes;
   }

   public String getName() {
      return this.name;
   }

   public TreeModel getTreeModel() {
      return treeModel;
   }

   public void setTreeModel(TreeModel treeModel) {
      this.treeModel = treeModel;
   }

   public TreeNode getSelectedNode() {
      return selectedNode;
   }

   /**
    * Establece un nodo de la jerarquia como el elemento seleccionado
    * @param selectedNode Nodo a establecer como nodo seleccionado
    */
   public TreeNode setSelectedNode(TreeNode selectedNode) {
      if (this.selectedNode != null)
         this.selectedNode.setSelected(false);
      this.selectedNode = selectedNode;
      if (selectedNode != null)
         selectedNode.setSelected(true);
      return selectedNode;
   }

   public TreeNode getChild(final String childID) {
      int childIndex = treeNodes.indexOf((new TreeNodeID(childID)));
      return childIndex < 0 ? null : (TreeNode) treeNodes.get(childIndex);
   }

//   public TreeModelItem getParentElement(TreeModelItem modelItem) {
//       //si el padre es nulo 
//       TreeModelItem padreElemento = modelItem.getParent();
//       if (padreElemento==null){
//           //es un deposito su padre sera un edificio DepositoVO y el elemento sera un no asignable
//           ElementoDeposito noAsignable1Nivel = (ElementoDeposito)modelItem;
//           return noAsignable1Nivel.getDeposito();
//       }else
//           return padreElemento;
//   }

   public TreeNode findNode(TreeModelItem modelItem) {
//      StringBuffer nodePath = new StringBuffer();
//      nodePath.append("item").append(modelItem.getItemId());
//      TreeModelItem parentItem = getParentElement(modelItem);
//      while (parentItem != null) {
//         nodePath.insert(0, "/").insert(0, parentItem.getItemId()).insert(0, "item");
//         parentItem = parentItem.getParent();
//      }
      return getNode(modelItem.getItemPath());
   }
/*
   public TreeNode findNode(TreeModelItem modelItem) {
      StringBuffer nodePath = new StringBuffer();
      nodePath.append("item").append(modelItem.getItemId());
      TreeModelItem parentItem = modelItem.getParent();
      while (parentItem != null) {
         nodePath.insert(0, "/").insert(0, parentItem.getItemId()).insert(0, "item");
         parentItem = parentItem.getParent();
      }
      return getNode(nodePath.toString());
   }

 */
   public void setNodeSelectionHandler(NodeSelectionHandlerAction nodeSelectionHandler) {
      this.nodeSelectionHandler = nodeSelectionHandler;
   }

   public NodeSelectionHandlerAction getNodeSelectionHandler() {
      return nodeSelectionHandler;
   }

   public Comparator getNodeComparator() {
      return nodeComparator;
   }

   public void setNodeComparator(Comparator nodeComparator) {
      this.nodeComparator = nodeComparator;
   }
   
	public void valueBound(HttpSessionBindingEvent arg0) {
	    logger.debug("TreeView disponible en session");
	}
	public void valueUnbound(HttpSessionBindingEvent arg0) {
	logger.debug("TreeView eliminado de session "+arg0.toString());
	}
	
	public String getViewName() {
	    return viewName;
	}

	public void refreshNode(String nodePath) {
		if (nodePath != null) {
			TreeNode node = getNode(nodePath);
			if (node != null) {
				node.refresh();
			}
		}
	}
	
	/**
	 * Invalidad el texto del arbol para que vuelva a ser obtenido
	 *
	 */
	public void invalidateTreeText(){
	    //borramos los textos de los nodos para que se obtengan de nuevo
	    Collection rootNodes  = getRootNodes();
	    if (!CollectionUtils.isEmpty(rootNodes)){
	        for (Iterator i = rootNodes.iterator(); i.hasNext();) {
	            deleteNodeNames((TreeNode) i.next());
	        }
	    }
	}
	
	private void deleteNodeNames(TreeNode node){
	    node.removeNodeLabel();
	    Collection nodeChilds = node.getChilds();
	    if (nodeChilds != null && nodeChilds.size() > 0) {
	       for (Iterator i = nodeChilds.iterator(); i.hasNext();) {
	           deleteNodeNames((TreeNode) i.next());
	       }
	    }
	}

}