package common.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import organizacion.model.EstructuraOrganizacion;

import util.TreeNode;
import util.TreeView;

import common.Constants;

import deposito.model.EstructuraDeposito;

/**
 * Action base del que heredan los action que se encargan de gestionar los
 * arboles de deposito y fondos
 */
public abstract class TreeViewManager extends BaseAction {

	public abstract ActionForward crearVista(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response);

	public abstract ActionForward getForwardVistaNodo(TreeNode node,
			ActionMapping mappings, HttpServletRequest request)
			throws Exception;

	public void showSelectedNodeExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setReturnActionFordward(request,
				showSelectedNode(mappings, form, request, response));
	}

	private ActionForward showSelectedNode(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String viewName = request.getParameter("viewName");
		TreeView treeView = (TreeView) request.getSession().getAttribute(
				viewName);
		if (treeView != null && treeView.getSelectedNode() != null)
			return getForwardVistaNodo(treeView.getSelectedNode(), mappings,
					request);
		else
			return null;
	}

	public void obtenerVistaExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		setReturnActionFordward(request,
				obtenerVista(mappings, form, request, response));
	}

	private ActionForward obtenerVista(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String viewName = (String) request.getParameter("viewName");
		// if (viewName == null) viewName = "estructuraDeposito";
		TreeView treeView = (TreeView) request.getSession().getAttribute(
				viewName);
		if (treeView != null)
			updateEstadoVista(treeView, request.getParameter("openNodes"),
					request.getParameter("closedNodes"),
					request.getParameter("selectedNode"));
		// else deberia de devolver a pagina de error porque la vista no esta
		// disponible (o crearla);
		return mappings.findForward("done");
	}

	protected void updateEstadoVista(TreeView treeView, String pOpenNodes,
			String pClosedNodes, String pSelectedNode) {
		updateEstadoVista(treeView, pOpenNodes, pClosedNodes, pSelectedNode,
				null);
	}

	protected void updateEstadoVista(TreeView treeView, String pOpenNodes,
			String pClosedNodes, String pSelectedNode, String idNodoAExpandir) {
		// String idItemPadre=null;
		String nodo = null;
		// if(treeView.getTreeModel() instanceof EstructuraDeposito){
		// idItemPadre=((EstructuraDeposito)treeView.getTreeModel()).getIdElementoForSubtree();
		// }

		pClosedNodes = removeSelectedFromClosedNodes(treeView, pClosedNodes,
				idNodoAExpandir);

		if (pOpenNodes != null) {
			// treeView.collapse(true);
			if (pOpenNodes.length() > 0) {
				String[] openNodes = pOpenNodes.split("-");
				TreeNode nodeToExpand = null;
				for (int i = 0; i < openNodes.length; i++) {
					nodo = openNodes[i];
					// if(!StringUtils.isEmpty(idItemPadre)){
					// nodo=nodo.substring(nodo.indexOf(idItemPadre)+idItemPadre.length()+1);
					// }
					nodeToExpand = treeView.getNode(nodo);
					/*
					 * if (nodeToExpand != null) nodeToExpand.setVisible();
					 */
					if (nodeToExpand != null)
						nodeToExpand.setCollapsed(false, false);
				}
			}
		}
		if (pClosedNodes != null) {
			if (pClosedNodes.length() > 0) {
				String[] closedNodes = pClosedNodes.split("-");
				for (int i = 0; i < closedNodes.length; i++) {
					nodo = closedNodes[i];
					// if(!StringUtils.isEmpty(idItemPadre)){
					// nodo=nodo.substring(nodo.indexOf(idItemPadre)+idItemPadre.length()+1);
					// }
					TreeNode node = treeView.getNode(nodo);
					if (node != null)
						node.setCollapsed(true, false);
				}
			}
		}
		if (pSelectedNode != null && pSelectedNode.length() > 0)
			try {
				TreeNode selectedNode = treeView.getSelectedNode();
				if (selectedNode == null
						|| !StringUtils.equals(pSelectedNode, treeView
								.getSelectedNode().getNodePath())) {
					nodo = pSelectedNode;
					// if(!StringUtils.isEmpty(idItemPadre)){
					// nodo=nodo.substring(nodo.indexOf(idItemPadre)+idItemPadre.length()+1);
					// }
					TreeNode nodeToSelect = treeView.getNode(nodo);
					if (nodeToSelect != null) {
						if (treeView.getSelectedNode() != nodeToSelect)
							treeView.setSelectedNode(nodeToSelect);
						nodeToSelect.setVisible();
					}
				}
			} catch (Exception e) {
				logger.error(e);
			}
	}

	public void expandirNodoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		setReturnActionFordward(request,
				expandirNodo(mappings, form, request, response));
	}

	protected ActionForward expandirNodo(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response, String viewName) {

		TreeView treeView = (TreeView) getFromTemporalSession(request, viewName);
		if (treeView != null) {
			String nodoSeleccionado = request.getParameter("node");
			String idItemPadre = null;
			if (treeView.getTreeModel() instanceof EstructuraDeposito) {
				idItemPadre = ((EstructuraDeposito) treeView.getTreeModel())
						.getIdElementoForSubtree();
			} else if (treeView.getTreeModel() instanceof EstructuraOrganizacion) {
				idItemPadre = ((EstructuraOrganizacion) treeView.getTreeModel())
						.getIdElementoForSubtree();
			}

			ActionForward f = mappings.findForward("done");
			if (!StringUtils.isEmpty(idItemPadre)) {
				// nodoSeleccionado=nodoSeleccionado.substring(nodoSeleccionado.indexOf(idItemPadre)+idItemPadre.length()+1);
				f = mappings.findForward("doneSub");
			}
			TreeNode nodeToUpdate = treeView.getNode(nodoSeleccionado);
			updateEstadoVista(treeView, request.getParameter("openNodes"),
					request.getParameter("closedNodes"),
					request.getParameter("selectedNode"), nodoSeleccionado);
			if (nodeToUpdate != null)
				nodeToUpdate.setCollapsed(false, false);

			return f;

		} else
			return crearVista(mappings, form, request, response);
	}

	protected ActionForward expandirNodo(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		String viewName = (String) request.getParameter("viewName");
		return expandirNodo(mappings, form, request, response, viewName);
	}

	/**
	 * Indica si el nodo es raíz.
	 * 
	 * @param node
	 *            Identificador del nodo.
	 * @return true si el nodo es el raíz.
	 */
	protected boolean isRootNode(String node) {
		// return ((node != null) && "/".equals(node));
		return ((node != null) && Constants.TREE_VIEW_ROOT_NODE.equals(node));
	}

	private String removeSelectedFromClosedNodes(TreeView treeView,
			String closedNodes, String pathNodoAExpandir) {
		// obtener lista con los ids de los nodos a cerrar
		if (StringUtils.isEmpty(closedNodes))
			return closedNodes;
		String[] idsNodosACerrar = closedNodes.split("-");
		List idsRamaSeleccionada = new ArrayList();
		String pathNodoSeleccionado = "";
		if (treeView == null)
			return closedNodes;

		TreeNode nodeAux = treeView.getSelectedNode();
		if (nodeAux == null)
			return closedNodes;
		do {
			if (pathNodoSeleccionado.length() > 0)
				pathNodoSeleccionado += "/";
			idsRamaSeleccionada.add(nodeAux.getNodeId());
			pathNodoSeleccionado += "item" + nodeAux.getNodeId();
			nodeAux = nodeAux.getParent();
		} while (nodeAux != null);

		// si se esta abriendo una rama, puede ser que el usuario haya cerrado
		// algun nodo padre del nodo actualmente seleccionado
		// en ese caso el nodo cerrado debe continuar cerrado
		if (pathNodoAExpandir != null
				&& pathNodoSeleccionado.indexOf(pathNodoAExpandir) == -1)
			return closedNodes;

		for (int i = 0; i < idsNodosACerrar.length; i++) {
			String idNodoACerrar = idsNodosACerrar[i]
					.substring(idsNodosACerrar[i].lastIndexOf("/") + 1);
			for (Iterator it = idsRamaSeleccionada.iterator(); it.hasNext();) {
				String id = (String) it.next();

				int pos = idNodoACerrar.indexOf("item" + id);
				if (pos != -1) { // hay que quitar el nodo
					idsNodosACerrar[i] = "";
					break;
				}
			}
		}

		String returnString = "";
		for (int i = 0; i < idsNodosACerrar.length; i++) {
			if (!StringUtils.isEmpty(idsNodosACerrar[i].trim())) {
				if (returnString.length() > 0)
					returnString += "-";
				returnString += idsNodosACerrar[i];
			}
		}
		return returnString;
	}
}