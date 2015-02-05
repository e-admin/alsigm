package docelectronicos.model;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import util.TreeModel;
import util.TreeModelItem;
import util.TreeModelListener;
import util.TreeNode;
import util.TreeView;
import docelectronicos.vos.DocumentosTreeModelItemVO;

public class DocumentosTreeView extends TreeView implements TreeModelListener {

	/** Logger de la clase. */
	protected static final Logger logger = Logger
			.getLogger(DocumentosTreeView.class);

	/**
	 * Constructor.
	 * 
	 * @param model
	 *            Contenido del árbol.
	 */
	public DocumentosTreeView(TreeModel model) {
		super("ROOT", model);
		setNodeComparator(new Comparator() {
			public int compare(Object arg0, Object arg1) {
				return ((TreeNode) arg0).getNodeLabel().compareTo(
						((TreeNode) arg1).getNodeLabel());
			}
		});
	}

	public TreeNode insertNode(TreeNode parent, TreeModelItem modelItem) {
		TreeNode nuevoNodo = super.insertNode(parent, modelItem);
		nuevoNodo
				.setNodeAtribute("TIPO_NODO", String
						.valueOf(((DocumentosTreeModelItemVO) modelItem)
								.getNodeType()));
		return nuevoNodo;
	}

	public void itemsAdded(TreeModelItem source, List addedItems) {
	}

	public TreeNode itemAdded(TreeModelItem source, TreeModelItem addedItem) {
		TreeNode parentNode = getNode(source.getItemPath());
		return insertNode(parentNode, addedItem);
	}

	// public TreeNode itemAdded(TreeModelItem addedItem)
	// {
	// TreeNode parentNode = null;
	// TreeModelItem parentItem = addedItem.getParent();
	// if (parentItem != null)
	// parentNode = getNode(parentItem.getItemPath());
	//
	// return insertNode(parentNode, addedItem);
	// }

	public void itemRemoved(TreeModelItem item) {
	}

	public void itemModified(TreeModelItem item) {
	}

	public void expandNodes() {
		Iterator rootNodesIt = getRootNodes().iterator();
		while (rootNodesIt.hasNext()) {
			TreeNode rootNode = (TreeNode) rootNodesIt.next();
			try {
				rootNode.setCollapsed(true, true);
			} catch (Exception e) {
				logger.warn("Error al expandir los nodos", e);
			}
		}
	}
}
