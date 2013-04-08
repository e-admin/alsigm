package deposito.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import util.TreeNode;
import util.TreeView;

import common.actions.NodeSelectionHandlerAction;
import common.actions.TreeViewManager;

import deposito.DepositoConstants;
import deposito.forms.UbicacionForm;

/**
 * Controlador para el manejo de la vista del cuadro de clasificación de fondos
 * documentales
 */
public class GestionVistaDepositoSeleccion extends TreeViewManager implements
		NodeSelectionHandlerAction {

	public void homeExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		TreeView treeView = (TreeView) getFromTemporalSession(request,
				DepositoConstants.DEPOSITO_SEL_VIEW_NAME);
		if (treeView == null) {
			treeView = new EstructuraDepositoTreeView("Estructura Deposito",
					getGestionDespositoBI(request).getEstructuraDeposito());

			setInTemporalSession(request,
					DepositoConstants.DEPOSITO_SEL_VIEW_NAME, treeView);
		}

		treeView.setNodeSelectionHandler(this);

		setReturnActionFordward(request, mappings.findForward("done"));
	}

	public void homeSubtreeExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		TreeView treeView = (TreeView) getFromTemporalSession(request,
				DepositoConstants.DEPOSITO_SEL_SUBVIEW_NAME);

		// obtener el id de la ubicacion actual seleccionada
		UbicacionForm ubiForm = (UbicacionForm) form;

		// if (treeView == null){
		String subTreeNode = ubiForm.getIdUbicacion();
		treeView = new EstructuraDepositoTreeView("Estructura Deposito",
				getGestionDespositoBI(request).getEstructuraDeposito(
						subTreeNode));

		setInTemporalSession(request,
				DepositoConstants.DEPOSITO_SEL_SUBVIEW_NAME, treeView);
		// }

		treeView.setNodeSelectionHandler(this);

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
				DepositoConstants.DEPOSITO_SEL_VIEW_NAME);
		if (treeView == null) {
			treeView = new EstructuraDepositoTreeView("Estructura Deposito",
					getGestionDespositoBI(request).getEstructuraDeposito());
			treeView.setNodeSelectionHandler(this);
			setInTemporalSession(request,
					DepositoConstants.DEPOSITO_SEL_VIEW_NAME, treeView);
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
		return "/action/manageVistaDeposito?actionToPerform=verNodo";
	}

}