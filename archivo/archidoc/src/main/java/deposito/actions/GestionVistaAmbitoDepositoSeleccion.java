package deposito.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import util.TreeNode;
import util.TreeView;

import common.Messages;
import common.actions.NodeSelectionHandlerAction;
import common.actions.TreeViewManager;
import common.util.StringUtils;

import deposito.DepositoConstants;
import deposito.forms.UbicacionForm;

/**
 * Controlador para el manejo de la vista del cuadro de clasificación de fondos
 * documentales
 */
public class GestionVistaAmbitoDepositoSeleccion extends TreeViewManager
		implements NodeSelectionHandlerAction {

	private static final String TREE_LABEL_KEY = "archigest.archivo.ambito.deposito.tree.titulo";

	public void homeExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		TreeView treeView = (TreeView) getFromTemporalSession(request,
				DepositoConstants.AMBITO_DEPOSITO_SEL_VIEW_NAME);
		if (treeView == null) {
			treeView = new EstructuraDepositoTreeView(Messages.getString(
					TREE_LABEL_KEY, request.getLocale()),
					getGestionDespositoBI(request).getEstructuraDeposito());

			setInTemporalSession(request,
					DepositoConstants.AMBITO_DEPOSITO_SEL_VIEW_NAME, treeView);
		}

		request.setAttribute("viewAction", "obtenerVistaAmbitoDeposito");
		request.setAttribute("viewName",
				DepositoConstants.AMBITO_DEPOSITO_VIEW_NAME);

		treeView.setNodeSelectionHandler(this);

		setReturnActionFordward(request, mappings.findForward("done"));
	}

	public void homeSubtreeExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		TreeView treeView = (TreeView) getFromTemporalSession(request,
				DepositoConstants.AMBITO_DEPOSITO_SEL_VIEW_NAME);

		// obtener el id de la ubicacion actual seleccionada
		UbicacionForm ubiForm = (UbicacionForm) form;

		// if (treeView == null){
		String subTreeNode = ubiForm.getIdUbicacion();
		if (StringUtils.isNotEmpty(subTreeNode)) {
			treeView = new EstructuraDepositoTreeView(Messages.getString(
					TREE_LABEL_KEY, request.getLocale()),
					getGestionDespositoBI(request).getEstructuraDeposito(
							subTreeNode));

			setInTemporalSession(request,
					DepositoConstants.AMBITO_DEPOSITO_SEL_VIEW_NAME, treeView);
			// }

			treeView.setNodeSelectionHandler(this);
		}
		request.setAttribute("viewAction", "obtenerVistaAmbitoDeposito");
		request.setAttribute("viewName",
				DepositoConstants.AMBITO_DEPOSITO_VIEW_NAME);

		setReturnActionFordward(request, mappings.findForward("doneSub"));
	}

	public void crearVistaExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		setReturnActionFordward(request,
				crearVista(mappings, form, request, response));
	}

	public ActionForward crearVista(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		TreeView treeView = (TreeView) getFromTemporalSession(request,
				DepositoConstants.AMBITO_DEPOSITO_SEL_VIEW_NAME);
		if (treeView == null) {
			treeView = new EstructuraDepositoTreeView(Messages.getString(
					TREE_LABEL_KEY, request.getLocale()),
					getGestionDespositoBI(request).getEstructuraDeposito());
			treeView.setNodeSelectionHandler(this);
			setInTemporalSession(request,
					DepositoConstants.AMBITO_DEPOSITO_SEL_VIEW_NAME, treeView);
		}

		return mappings.findForward("done");
	}

	public void verNodoExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		setReturnActionFordward(request,
				verNodo(mappings, form, request, response));
	}

	public ActionForward verNodo(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return mappings.findForward("viewRefresher");
	}

	public ActionForward getForwardVistaNodo(TreeNode node,
			ActionMapping mappings, HttpServletRequest request) {
		return null;
	}

	public String getHandlerPath() {
		return "/action/ambitoDepositoSeleccion?actionToPerform=verNodo";
	}

	public void expandirNodoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		setReturnActionFordward(
				request,
				expandirNodo(mappings, form, request, response,
						DepositoConstants.AMBITO_DEPOSITO_SEL_VIEW_NAME));
	}

}