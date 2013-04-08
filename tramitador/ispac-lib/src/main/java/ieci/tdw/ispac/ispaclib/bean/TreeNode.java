package ieci.tdw.ispac.ispaclib.bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Elementos con los que contruir una vista de una estructura de datos organizada 
 * como árbol jerárquico
 */
public class TreeNode implements INode {

   boolean lazyLoad = true;

   Object nodeId;
   Map nodeAtributes = new HashMap();;
   TreeNode parent = null;
   TreeView treeView = null;
   TreeModelItem modelItem = null;
   List childs = null;
   String path = null;
   boolean selected = false;
   boolean collapsed = true;
   boolean isLeaf = false;
   String nodeLabel = null;
   String language = null;

   // Comprobar si nodo padre hace falta. Creo que con mantener la relacion
   // padre hijo en el modelo es suficiente
   public TreeNode(TreeModelItem modelItem, TreeNode parentNode, TreeView treeView, String language) {
	  
      setTreeModelItem(modelItem);
      this.parent = parentNode;
      this.treeView = treeView;
      if (parentNode != null) {
         if (parentNode.getChilds() == null)
            try {
               parentNode.refresh();
            } catch (Exception e) {
            }
         else
            parentNode.addChild(this);
      }
      
      this.language = language;
   }

   public void setTreeModelItem(TreeModelItem modelItem) {
      this.modelItem = modelItem;
      this.nodeId = modelItem.getItemId();
      this.path = modelItem.getItemPath();
      this.isLeaf = modelItem.isLeaf();
   }

   public Object getNodeId() {
      //return nodeId;
      return modelItem.getItemId().toString();
   }

   public void setNodeId(Object nodeId) {
      this.nodeId = nodeId;
   }

//   public String getNodeLabel(List languagePreferences) {
//      //return nodeLabel;
//      if (nodeLabel == null){
//    	  nodeLabel = "etiqueta";
//      }
//      return nodeLabel;
//   }

   public String getNodeLabel() {
	      if (nodeLabel == null){
	    	  nodeLabel = modelItem.getItemName();
	      }
	      return nodeLabel;
   }

   public void removeNodeLabel() {
       nodeLabel = null;
   }


   public TreeNode getParent() {
      return parent;
   }

   public void setParent(TreeNode parent) {
      this.parent = parent;
   }

   public void addChild(TreeNode aNode) {
      if (this.childs == null)
         this.childs = new ArrayList();
      aNode.setParent(this);
      this.childs.add(aNode);
      if (treeView.getNodeComparator() != null)
         Collections.sort(this.childs, treeView.getNodeComparator());
   }

   public Collection getChilds() {
      return this.childs;
   }

   public String getNodePath() {
      /*
       * if (this.path == null) { StringBuffer nodePath = new StringBuffer(); if
       * (this.parent != null) { nodePath.append(parent.getNodePath());
       * nodePath.append("/"); } nodePath.append("item");
       * nodePath.append(this.getNodeId()); this.path = nodePath.toString(); }
       */
      return this.path;
   }

   public String getAtributo(String atributo) {
      return (String) this.nodeAtributes.get(atributo);
   }

   public void setNodeAtribute(String atributo, String valorAtributo) {
      this.nodeAtributes.put(atributo, valorAtributo);
   }

   public Map getAtributos() {
      return this.nodeAtributes;
   }

//   public String toString(String ) {
//      return "Label: " + this.nodeLabel + " | id: " + this.nodeId + " | path: " + this.path;
//   }

   public boolean isCollapsed() {
      return collapsed;
   }

   public void setCollapsed(boolean collapsed, boolean recursive) {
      if (!collapsed && this.childs == null)
         loadChilds();
      this.collapsed = collapsed;
      if (recursive && this.childs != null)
         for (Iterator i = this.childs.iterator(); i.hasNext();)
            ((TreeNode) i.next()).setCollapsed(true, recursive);
   }

   private void loadChilds() {
	   
      this.childs = new ArrayList();
      //TreeSet childItems = new TreeSet(modelItem.getChildItems());
      Collection childs = treeView.getTreeModel().getChilds(language, modelItem);
      if (childs != null)
         for (Iterator i = childs.iterator(); i.hasNext();)
            treeView.insertNode(this, (TreeModelItem) i.next());
   }

   public void refresh() {
      loadChilds();
   }

   public TreeView getTreeView() {
      return treeView;
   }

   public TreeNode getChild(String childID) {
      try {
         //if (this.childs == null || childs.indexOf(new TreeNodeID(childID))
         // == -1)
         if (this.childs == null)
            loadChilds();
         int childIndex = childs.indexOf(new TreeNodeID(childID));
         return childIndex < 0 ? null : (TreeNode) childs.get(childIndex);
      } catch (Exception e) {
         return null;
      }
   }

   public void removeChild(TreeNode child) {
      this.childs.remove(child);
   }

   public boolean equals(Object oObject) {
      if (this == oObject)
         return true;
      return this.nodeId.equals(((TreeNode) oObject).getNodeId());
   }

   public Collection getChildIDs() {
      List childIDs = null;
      if (this.childs != null) {
         childIDs = new ArrayList();
         for (Iterator i = childs.iterator(); i.hasNext();)
            childIDs.add(((TreeNode) i.next()).getNodeId());
      }
      return childIDs;
   }

   public boolean isLeaf() {
      return isLeaf;
   }

   public void setLeaf(boolean isLeaf) {
      this.isLeaf = isLeaf;
   }

   public boolean isSelected() {
      return selected;
   }

   public void setSelected(boolean selected) {
      this.selected = selected;
      if (this.childs == null)
         this.loadChilds();
   }

   public TreeModelItem getModelItem() {
      return modelItem;
   }

   public void setVisible() {
      setCollapsed(false, false);
      if (parent != null)
         parent.setVisible();
   }
   
}