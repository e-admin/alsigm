package organizacion.view.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import organizacion.OrganizacionConstants;
import organizacion.model.bi.GestionOrganizacionBI;
import organizacion.model.vo.OrganizacionVO;

import util.TreeNode;
import util.TreeView;

import common.actions.NodeSelectionHandlerAction;
import common.actions.TreeViewManager;
import common.bi.ServiceRepository;

/**
 * Controlador para el manejo de la vista del cuadro de selección de
 * organizaciones padre.
 */
public class GestionVistaOrganizacionSeleccion extends TreeViewManager
		implements NodeSelectionHandlerAction {

	public void homeExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		ServiceRepository services = getServiceRepository(request);
		GestionOrganizacionBI organizacionBI = services
				.lookupGestionOrganizacionBI();

		Collection instituciones = null;

		instituciones = organizacionBI.getInstituciones();

		List listaInstituciones = new ArrayList();
		if (instituciones != null && instituciones.size() > 0) {
			Iterator it = instituciones.iterator();
			while (it.hasNext()) {
				OrganizacionVO institucion = (OrganizacionVO) it.next();
				listaInstituciones.add(institucion.getId());
			}
		}

		TreeView treeView = new EstructuraOrganizacionTreeView(
				"Estructura organizacion",
				organizacionBI.getEstructuraOrganizacion());
		treeView.setNodeSelectionHandler(this);
		setInTemporalSession(request,
				OrganizacionConstants.ORGANIZACION_SEL_VIEW_NAME, treeView);

		setReturnActionFordward(request, mappings.findForward("done"));
	}

	public void crearVistaExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		setReturnActionFordward(request,
				crearVista(mappings, form, request, response));
	}

	public ActionForward crearVista(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		ServiceRepository services = getServiceRepository(request);

		GestionOrganizacionBI organizacionBI = services
				.lookupGestionOrganizacionBI();
		TreeView treeView = (TreeView) getFromTemporalSession(request,
				OrganizacionConstants.ORGANIZACION_SEL_VIEW_NAME);
		if (treeView == null) {
			treeView = new EstructuraOrganizacionTreeView(
					"Estructura organizacion",
					organizacionBI.getEstructuraOrganizacion());
			treeView.setNodeSelectionHandler(this);
			setInTemporalSession(request,
					OrganizacionConstants.ORGANIZACION_SEL_VIEW_NAME, treeView);
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

	public String getHandlerPath() {
		return "/action/manageVistaOrganizacionSeleccion?actionToPerform=verNodo";
	}

	public ActionForward getForwardVistaNodo(String idOrganizacion,
			ActionMapping mappings, HttpServletRequest request) {
		return null;
	}

	public ActionForward getForwardVistaNodo(TreeNode node,
			ActionMapping mappings, HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}