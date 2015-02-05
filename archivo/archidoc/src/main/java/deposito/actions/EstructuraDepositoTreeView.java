package deposito.actions;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import util.TreeModelItem;
import util.TreeModelListener;
import util.TreeNode;
import util.TreeView;
import deposito.model.EstructuraDeposito;
import deposito.vos.ElementoVO;

public class EstructuraDepositoTreeView extends TreeView implements
		TreeModelListener, HttpSessionBindingListener {

	public static final String NOMBRE_ATRIBUTO_TIPO_ELEMENTO = "ID_TIPO_ELEMENTO";
	public static final String NOMBRE_ATRIBUTO_ID_ELEMENTO = "ID_ELEMENTO";
	public static final String NOMBRE_ATRIBUTO_PATHNAME_ELEMENTO = "PATHNAME_ELEMENTO";
	public static final String NOMBRE_ATRIBUTO_CODORDEN_ELEMENTO = "CODORDEN_ELEMENTO";

	public EstructuraDepositoTreeView(String viewName,
			EstructuraDeposito estructuraDeposito) {
		super(viewName, estructuraDeposito);
	}

	public TreeNode insertNode(TreeNode parent, TreeModelItem modelItem) {
		TreeNode nuevoNodo = super.insertNode(parent, modelItem);
		nuevoNodo.setNodeAtribute(NOMBRE_ATRIBUTO_TIPO_ELEMENTO,
				((ElementoVO) modelItem).getIdTipoElemento());
		nuevoNodo.setNodeAtribute(NOMBRE_ATRIBUTO_ID_ELEMENTO,
				((ElementoVO) modelItem).getId());
		nuevoNodo.setNodeAtribute(NOMBRE_ATRIBUTO_PATHNAME_ELEMENTO,
				((ElementoVO) modelItem).getPathName());
		nuevoNodo.setNodeAtribute(NOMBRE_ATRIBUTO_CODORDEN_ELEMENTO,
				((ElementoVO) modelItem).getCodorden());
		return nuevoNodo;
	}

	public void itemsAdded(TreeModelItem source, List addedItems) {
		TreeNode nodeToModify = null;
		if (source != null)
			nodeToModify = this.getNode(source.getItemPath());
		if (nodeToModify != null)
			for (Iterator i = addedItems.iterator(); i.hasNext();)
				this.insertNode(nodeToModify, (TreeModelItem) i.next());
	}

	public TreeNode itemAdded(TreeModelItem source, TreeModelItem addedItem) {
		TreeNode nodeToModify = null;
		if (source != null)
			nodeToModify = this.getNode(source.getItemPath());
		return this.insertNode(nodeToModify, addedItem);
	}

	public void itemRemoved(TreeModelItem removedItem) {
		TreeNode nodeToRemove = null;
		if (removedItem != null)
			nodeToRemove = this.getNode(removedItem.getItemPath());
		if (nodeToRemove != null)
			this.removeNode(nodeToRemove);
	}

	// TODO valorar si es mejor que el TreeNode recupere siempre sus datos del
	// ModelItem que tiene
	// por debajo o si se pone un metodo en el que se establecen los datos del
	// TreeNode a partir del ModelItem
	public void itemModified(TreeModelItem modifiedItem) {
		TreeNode nodeToModify = this.getNode(modifiedItem.getItemPath());
		if (nodeToModify != null) {
			TreeNode parent = null;
			try {
				parent = nodeToModify.getParent();
				if (parent != null)
					parent.refresh();
				else
					nodeToModify.setNodeLabel(modifiedItem.getItemName());
			} catch (Exception e) {
			}
		}
	}

	public void valueBound(HttpSessionBindingEvent evt) {
		if (treeModel != null)
			treeModel.addTreeModelListener(this);
	}

	public void valueUnbound(HttpSessionBindingEvent evt) {
		if (treeModel != null)
			treeModel.removeTreeModelListener(this);
	}

	// TODO el que recibe source y addedItem puede ser que este de mas ... en
	// fondos introduzco este que solo recibe el elemento incorporardo
	public TreeNode itemAdded(TreeModelItem item) {
		return null;
	}
}
