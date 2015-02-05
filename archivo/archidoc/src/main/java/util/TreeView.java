package util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.JXPathException;
import org.apache.commons.jxpath.JXPathIntrospector;
import org.apache.log4j.Logger;
import organizacion.model.EstructuraOrganizacion;
import salas.model.EstructuraSalasConsulta;

import common.actions.NodeSelectionHandlerAction;

import deposito.model.EstructuraDeposito;

/**
 * Componente empleado en la visualización de una jerarquia de elementos en
 * forma de arbol
 */
public class TreeView implements INode, HttpSessionBindingListener {

	/** Logger de la clase */
	private static final Logger logger = Logger.getLogger(TreeView.class);

	protected String name = null;

	/** Listas de nodos raiz */
	protected List treeNodes;

	protected TreeModel treeModel = null;

	TreeNode selectedNode = null;

	NodeSelectionHandlerAction nodeSelectionHandler = null;

	Comparator nodeComparator = null;

	public TreeView(String viewName, TreeModel model) {
		this.name = viewName;
		this.initialize(model);
	}

	private void initialize(TreeModel model) {
		this.treeNodes = new ArrayList();
		Collection rootItems = model.getRootNodes();
		if (model != null && rootItems != null) {
			TreeModelItem nodo = null;
			TreeNode parent = null;
			for (Iterator it = rootItems.iterator(); it.hasNext();) {
				nodo = ((TreeModelItem) it.next());
				parent = insertNode(null, nodo);
			}
			// si es un subarbol, debe aparecer el nodo seleccionado y debajo
			// todos sus descendientes
			if (model instanceof EstructuraDeposito) {
				if (((EstructuraDeposito) model).getIdElementoForSubtree() != null) {
					// salvo que hubiera el padre no sea una ubicacion en cuyo
					// caso apareceran directamente los hijos
					if (rootItems.size() == 1) {
						parent.getTreeView().setTreeModel(model);
						parent.refresh();
						parent.setCollapsed(false, false);
					}
				}
			} else if (model instanceof EstructuraOrganizacion) {
				if (((EstructuraOrganizacion) model).getIdElementoForSubtree() != null) {
					// salvo que hubiera el padre no sea una ubicacion en cuyo
					// caso apareceran directamente los hijos
					if (rootItems.size() == 1) {
						parent.getTreeView().setTreeModel(model);
						parent.refresh();
						parent.setCollapsed(false, false);
					}
				}
			} else if (model instanceof EstructuraSalasConsulta) {
				if (((EstructuraSalasConsulta) model).getIdElementoForSubtree() != null) {
					// salvo que hubiera el padre no sea una ubicacion en cuyo
					// caso apareceran directamente los hijos
					if (rootItems.size() == 1) {
						parent.getTreeView().setTreeModel(model);
						parent.refresh();
						parent.setCollapsed(false, false);
					}
				}

			}

			this.treeModel = model;
		}
	}

	/**
	 * Colapsa los nodos raiz del arbol
	 * 
	 * @param recursive
	 *            Indica si el colapsado se debe propagar tambien de forma
	 *            recursiva a todos los niveles de la jerarquia
	 * @throws Exception
	 */
	/*
	 * public void collapse(boolean recursive) throws Exception { for (Iterator
	 * i = this.treeNodes.iterator(); i.hasNext();) ((TreeNode)
	 * i.next()).setCollapsed(true, recursive); }
	 */

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
				JXPathIntrospector.registerDynamicClass(TreeNode.class,
						TreeNodeXPathHandler.class);
				JXPathIntrospector.registerDynamicClass(TreeView.class,
						TreeNodeXPathHandler.class);
				JXPathContext xPathContext = JXPathContext.newContext(this);
				return (TreeNode) xPathContext.getValue(nodePath);
			} catch (JXPathException jxpe) {
				return null;
			}
		}
	}

	/**
	 * Incorpora un nuevo nodo a la jerarquia
	 * 
	 * @param parent
	 *            Elemento padre del nodo que se va a incorporar
	 * @param modelItem
	 *            Información a almacenar en el nodo a incorporar a la jerarquia
	 * @return El nodo insertado
	 */
	public TreeNode insertNode(TreeNode parent, TreeModelItem modelItem) {
		TreeNode newNode = new TreeNode(modelItem, parent, this);
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
	 * 
	 * @param selectedNode
	 *            Nodo a establecer como nodo seleccionado
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
		int childIndex = treeNodes.indexOf(new TreeNodeID(childID));
		return childIndex < 0 ? null : (TreeNode) treeNodes.get(childIndex);
	}

	public TreeNode findNode(TreeModelItem modelItem) {
		return getNode(modelItem.getItemPath());
	}

	public void setNodeSelectionHandler(
			NodeSelectionHandlerAction nodeSelectionHandler) {
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
		logger.debug("TreeView eliminado de session " + arg0.toString());
	}

	public void selectTreeNode(TreeModelItem nodeToSelect) {
		// obtener una lista con los ids desde el padre raiz al nodo a
		// seleccionar.
		String[] itemsRamaASeleccionar = nodeToSelect.getItemPath().split("/");

		// recorrer el arbol desde el nodo raiz y extender todos los nodos
		// necesarios hasta llegar al hoja
		String pathNodoABuscar = "";
		TreeNode node = null;
		for (int i = 0; i < itemsRamaASeleccionar.length; i++) {
			if (pathNodoABuscar.length() > 0)
				pathNodoABuscar += "/";
			pathNodoABuscar += itemsRamaASeleccionar[i];

			node = getNode(pathNodoABuscar);
			if (node != null) {
				node.setCollapsed(false, false);
			}
		}
		if (node != null) {
			setSelectedNode(node);
			node.setVisible();
		}
	}
}