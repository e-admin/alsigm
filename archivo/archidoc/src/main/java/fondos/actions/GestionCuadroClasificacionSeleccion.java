package fondos.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import se.usuarios.ServiceClient;
import util.TreeNode;
import util.TreeView;

import common.actions.NodeSelectionHandlerAction;
import common.actions.TreeViewManager;
import common.bi.GestionCuadroClasificacionBI;
import common.bi.ServiceRepository;

import fondos.FondosConstants;

/**
 * Controlador para el manejo de la vista del cuadro de clasificación de fondos
 * documentales
 */
public class GestionCuadroClasificacionSeleccion extends TreeViewManager
		implements NodeSelectionHandlerAction {

	public void homeExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		TreeView treeView = (TreeView) getFromTemporalSession(request,
				FondosConstants.CUADRO_CLF_SEL_VIEW_NAME);
		if (treeView == null) {
			treeView = new CuadroClasificacionTreeView(
					getGestionCuadroClasificacionBI(request)
							.getCuadroClasificacion());

			setInTemporalSession(request,
					FondosConstants.CUADRO_CLF_SEL_VIEW_NAME, treeView);
		}

		treeView.setNodeSelectionHandler(this);

		setReturnActionFordward(request, mappings.findForward("done"));
	}

	public void crearVistaExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		setReturnActionFordward(request,
				crearVista(mappings, form, request, response));
	}

	public ActionForward crearVista(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ServiceClient sc = getServiceClient(request);
		ServiceRepository services = ServiceRepository.getInstance(sc);
		GestionCuadroClasificacionBI bi = services
				.lookupGestionCuadroClasificacionBI();
		TreeView treeView = (TreeView) getFromTemporalSession(request,
				FondosConstants.CUADRO_CLF_SEL_VIEW_NAME);
		if (treeView == null) {
			treeView = new CuadroClasificacionTreeView(
					bi.getCuadroClasificacion());
			treeView.setNodeSelectionHandler(this);
			setInTemporalSession(request,
					FondosConstants.CUADRO_CLF_SEL_VIEW_NAME, treeView);
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
		return "/action/cuadroSeleccion?actionToPerform=verNodo";
	}

}