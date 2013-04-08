package ieci.tdw.ispac.ispaclib.bean;

import java.util.List;

/**
 * Un componente TreeView se apoya en una estructura de elementos en forma de arbol jerarquico.
 * Este interface permite que esas estructuras sean informadas cuando se realizan modificaciones
 * en el componente que permite su visualización 
 * @see TreeView, TreeModel
 */
public interface TreeModelListener {
   
   public void itemsAdded(TreeModelItem source,List addedItems);
   public TreeNode itemAdded(TreeModelItem source,TreeModelItem addedItem);
//   public TreeNode itemAdded(TreeModelItem item);   
   public void itemRemoved(TreeModelItem item);
   public void itemModified(TreeModelItem item);
}
