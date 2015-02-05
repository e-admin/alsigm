package ieci.tdw.ispac.ispaccatalog.action.procedure.tree;

import ieci.tdw.ispac.ispaclib.bean.TreeModel;
import ieci.tdw.ispac.ispaclib.bean.TreeModelItem;
import ieci.tdw.ispac.ispaclib.bean.TreeModelListener;
import ieci.tdw.ispac.ispaclib.bean.TreeNode;
import ieci.tdw.ispac.ispaclib.bean.TreeView;

import java.util.List;

public class CuadroEntidadTreeView extends TreeView implements TreeModelListener {
 
	public static final String NOMBRE_ATRIBUTO_TIPO_ELEMENTO = "TIPO_ELEMENTO";
	   public static final String NOMBRE_CUADRO_TREE_VIEW = null;
	   
	   public CuadroEntidadTreeView(TreeModel model, String language) {
	      super(NOMBRE_CUADRO_TREE_VIEW, model, language);
	   }
	     
	   public TreeNode insertNode(TreeNode parent,TreeModelItem modelItem) {
	      TreeNode nuevoNodo = super.insertNode(parent,modelItem);
	      //el tipo de elemento se usa para en el script que pinta los iconos
	      ElementoCuadro elem = (ElementoCuadro) modelItem; 
	      nuevoNodo.setNodeAtribute(
                  NOMBRE_ATRIBUTO_TIPO_ELEMENTO, 
                  String.valueOf(elem.getType()));
	      
//	      // Selección del tipo de nodo
//	      ElementoCuadro elem = (ElementoCuadro) modelItem; 
//	      
//	      Nivel nivel = cfg.findNivel(elem.getIdNivel());
//	      if (nivel != null)
//	          nuevoNodo.setNodeAtribute(
//	                  NOMBRE_ATRIBUTO_TIPO_ELEMENTO, 
//	                  nivel.getTipo());

	      return nuevoNodo;
	   }
	   
	   public void itemsAdded(TreeModelItem source, List addedItems) {
	   }

	   public TreeNode itemAdded(TreeModelItem source, TreeModelItem addedItem) {
	      TreeNode parentNode = getNode(source.getItemPath());
	      return insertNode(parentNode, addedItem);
	   }
	   
	   public void itemRemoved(TreeModelItem item) {
	   }

	   public void itemModified(TreeModelItem item) {
	   }

	   
	}



