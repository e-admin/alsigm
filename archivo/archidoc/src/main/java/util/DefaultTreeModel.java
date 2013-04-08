package util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class DefaultTreeModel extends TreeModel {
	private Collection rootNodes = null;

	public DefaultTreeModel(Collection rootNodes) {
		setRootNodes(rootNodes);
	}

	public DefaultTreeModel() {
		setRootNodes(new ArrayList());
	}

	public void addItem(TreeModelItem item) {
		if (rootNodes == null)
			rootNodes = new ArrayList();

		rootNodes.add(item);
	}

	public Collection getRootNodes() {
		return rootNodes;
	}

	public void setRootNodes(Collection rootNodes) {
		this.rootNodes = rootNodes;
	}

	public void notifyItemAdded(TreeModelItem source, TreeModelItem addedItem) {
	}

	public void notifyItemsAdded(TreeModelItem source, List addedItems) {
	}

	public void registerView(TreeView treeView) {
	}

	public void addTreeModelListener(TreeModelListener listener) {

	}

	public void removeTreeModelListener(TreeModelListener listener) {
	}
}