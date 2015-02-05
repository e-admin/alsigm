package salas.actions;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import salas.model.EstructuraSalasConsulta;
import salas.vos.SalasConsultaVOBase;
import util.TreeModelItem;
import util.TreeModelListener;
import util.TreeNode;
import util.TreeView;

import common.util.StringUtils;

public class EstructuraSalasConsultaTreeView extends TreeView implements
		TreeModelListener, HttpSessionBindingListener {

	public static final String NOMBRE_ATRIBUTO_TIPO_ELEMENTO = "ID_TIPO_ELEMENTO";
	public static final String NOMBRE_ATRIBUTO_ID_ELEMENTO = "ID_ELEMENTO";
	public static final String NOMBRE_ATRIBUTO_PATHNAME_ELEMENTO = "PATHNAME_ELEMENTO";

	// public static final String NOMBRE_ATRIBUTO_CODORDEN_ELEMENTO =
	// "CODORDEN_ELEMENTO";

	public EstructuraSalasConsultaTreeView(String viewName,
			EstructuraSalasConsulta estructuraSalasConsulta) {
		super(viewName, estructuraSalasConsulta);
	}

	public TreeNode insertNode(TreeNode parent, TreeModelItem modelItem) {
		TreeNode nuevoNodo = super.insertNode(parent, modelItem);

		nuevoNodo.setNodeAtribute(NOMBRE_ATRIBUTO_TIPO_ELEMENTO,
				((SalasConsultaVOBase) modelItem).getTipo());
		nuevoNodo.setNodeAtribute(NOMBRE_ATRIBUTO_ID_ELEMENTO,
				((SalasConsultaVOBase) modelItem).getId());
		nuevoNodo.setNodeAtribute(NOMBRE_ATRIBUTO_PATHNAME_ELEMENTO,
				((SalasConsultaVOBase) modelItem).getPathName());
		// nuevoNodo.setNodeAtribute(NOMBRE_ATRIBUTO_CODORDEN_ELEMENTO,
		// ((ElementoVO)modelItem).getCodorden());
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

		TreeNode parentNode = getNode(source.getItemPath());
		if (StringUtils.isEmpty((String) source.getItemId()))
			parentNode = null;
		if (parentNode != null)
			parentNode.setCollapsed(false, false);
		return insertNode(parentNode, addedItem);
	}

	public void itemRemoved(TreeModelItem removedItem) {
		TreeNode nodeToRemove = null;
		if (removedItem != null)
			nodeToRemove = this.getNode(removedItem.getItemPath());
		if (nodeToRemove != null)
			this.removeNode(nodeToRemove);
	}

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
}
