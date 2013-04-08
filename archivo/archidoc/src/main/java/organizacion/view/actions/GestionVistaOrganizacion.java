package organizacion.view.actions;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import organizacion.OrganizacionConstants;
import organizacion.model.bi.GestionOrganizacionBI;
import organizacion.model.vo.OrganizacionVO;

import util.CollectionUtils;
import util.TreeModelItem;
import util.TreeNode;
import util.TreeView;

import common.Constants;
import common.actions.ActionRedirect;
import common.actions.NodeSelectionHandlerAction;
import common.actions.TreeViewManager;
import common.bi.ServiceRepository;
import common.exceptions.OrganizacionException;
import common.navigation.KeysClientsInvocations;

public class GestionVistaOrganizacion extends TreeViewManager implements
		NodeSelectionHandlerAction {

	private static final Logger logger = Logger
			.getLogger(GestionVistaOrganizacion.class);

	public void goHomeExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		GestionOrganizacionBI bi = getGestionOrganizacionBI(request);
		String idOrganizacion = request.getParameter("idOrganizacion");

		OrganizacionVO organizacionVO = null;
		if (idOrganizacion != null) {
			organizacionVO = bi.getOrganizacionById(idOrganizacion);
			String forwardName = request.getParameter("contentForwardName");
			if (forwardName == null)
				forwardName = "verOrganizacion";

			String idParamName = "idOrganizacion";
			ActionRedirect redirectVistaNodo = new ActionRedirect(
					mappings.findForward("verOrganizacion"));
			redirectVistaNodo.addParameter(idParamName, idOrganizacion);

			request.setAttribute("showContentURL", redirectVistaNodo.getPath());
		}

		TreeView treeView = (TreeView) getFromTemporalSession(request,
				OrganizacionConstants.ORGANIZACION_VIEW_NAME);
		if (treeView == null) {
			treeView = new EstructuraOrganizacionTreeView(
					"Estructura organizacion", bi.getEstructuraOrganizacion());
			treeView.setNodeSelectionHandler(this);
			setInTemporalSession(request,
					OrganizacionConstants.ORGANIZACION_VIEW_NAME, treeView);
		}

		if (organizacionVO != null) {
			List ancestros = bi.getAncestors(idOrganizacion);
			OrganizacionVO padre = null;
			if (!CollectionUtils.isEmpty(ancestros)) {
				padre = (OrganizacionVO) ancestros.get(0);
				OrganizacionVO hijo;
				for (int i = 1; i < ancestros.size(); i++) {
					hijo = (OrganizacionVO) ancestros.get(i);
					hijo.setParentElement(padre);
					padre = hijo;
				}
			}
			organizacionVO.setParentElement(padre);
			TreeNode nodeToShow = treeView
					.findNode((TreeModelItem) organizacionVO);
			if (nodeToShow != null) {
				treeView.setSelectedNode(nodeToShow);
				nodeToShow.setVisible();
			}
			request.setAttribute("viewAction", "obtenerVista");
			request.setAttribute("viewName",
					OrganizacionConstants.ORGANIZACION_VIEW_NAME);
		}

		setReturnActionFordward(request,
				mappings.findForward("homeorganizacion"));
	}

	public void crearVistaExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		setReturnActionFordward(request,
				crearVista(mappings, form, request, response));
	}

	public ActionForward crearVista(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		ServiceRepository services = getServiceRepository(request);

		GestionOrganizacionBI organizacionBI = services
				.lookupGestionOrganizacionBI();
		TreeView treeView = (TreeView) getFromTemporalSession(request,
				OrganizacionConstants.ORGANIZACION_VIEW_NAME);
		if (treeView == null) {
			treeView = new EstructuraOrganizacionTreeView(
					"Estructura organizacion",
					organizacionBI.getEstructuraOrganizacion());
			treeView.setNodeSelectionHandler(this);
			setInTemporalSession(request,
					OrganizacionConstants.ORGANIZACION_VIEW_NAME, treeView);
		}

		return mappings.findForward("done");
	}

	public void loadHomeOrganizacionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ServiceRepository services = getServiceRepository(request);
		GestionOrganizacionBI organizacionBI = services
				.lookupGestionOrganizacionBI();

		Collection instituciones = organizacionBI.getInstituciones();
		request.setAttribute(OrganizacionConstants.LISTA_INSTITUCIONES_KEY,
				instituciones);

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
				OrganizacionConstants.ORGANIZACION_VIEW_NAME, treeView);

		String reloadTreeView = request
				.getParameter(Constants.TREE_VIEW_RELOAD);
		if (reloadTreeView != null) {
			request.setAttribute(Constants.TREE_VIEW_RELOAD, Boolean.TRUE);
		}

		request.setAttribute(OrganizacionConstants.REFRESH_VIEW_KEY,
				Boolean.TRUE);
		String viewAction = (String) request.getAttribute("viewAction");
		String viewName = (String) request.getAttribute("viewName");

		saveCurrentInvocation(KeysClientsInvocations.ORGANIZACION_HOME,
				request, treeView, viewName, viewAction);
		setReturnActionFordward(request,
				mappings.findForward("lista_instituciones"));
	}

	public void verNodoExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionForward forward = verNodo(mappings, form, request, response);

		if (forward != null) {
			setReturnActionFordward(request, forward);
		} else {
			goLastClientExecuteLogic(mappings, form, request, response);
		}
	}

	public ActionForward verNodo(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionForward forwardVistaNodo = null;

		TreeView treeView = (TreeView) getFromTemporalSession(request,
				OrganizacionConstants.ORGANIZACION_VIEW_NAME);

		if (Boolean.valueOf(request.getParameter("refreshView")).booleanValue())
			request.setAttribute(OrganizacionConstants.REFRESH_VIEW_KEY,
					Boolean.TRUE);

		String pNode = request.getParameter("node");
		if (isRootNode(pNode)) {
			ActionForward ret = new ActionForward();
			ret.setPath("/action/manageVistaOrganizacion?actionToPerform=homeOrganizacion");
			ret.setRedirect(true);
			return ret;
		} else {
			if (treeView != null) {
				TreeNode node = treeView.getNode(URLDecoder.decode(pNode,
						Constants.ENCODING_ISO_8859_1));
				if (node != null) {
					treeView.setSelectedNode(node);
					node.setVisible();
					forwardVistaNodo = getForwardVistaNodo(node, mappings,
							request);
				} else if (pNode != null && node == null) {
					logger.debug("El nodo con id "
							+ node
							+ " no se encuentra en el sistema por que contiene espacios");
					request.setAttribute(
							OrganizacionConstants.REFRESH_VIEW_KEY,
							Boolean.TRUE);
					guardarError(
							request,
							new OrganizacionException(
									OrganizacionException.ERROR_AL_MOSTRAR_ELEMENTO_ORGANIZACION));
				} else {
					logger.debug("El nodo con id "
							+ node
							+ " seleccionado actualmente no se encuentra o ha sido eliminado");
					request.setAttribute(
							OrganizacionConstants.REFRESH_VIEW_KEY,
							Boolean.TRUE);
					guardarError(request, new OrganizacionException(
							OrganizacionException.ERROR_ELEMENTO_NO_ENCONTRADO));
				}
			} else {
				String idOrganizacion = request.getParameter("idOrganizacion");
				forwardVistaNodo = getForwardVistaNodo(idOrganizacion,
						mappings, request);
			}

		}
		return forwardVistaNodo;
	}

	public String getHandlerPath() {
		return "/action/manageVistaOrganizacion?actionToPerform=verNodo";
	}

	public void homeOrganizacionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		removeInTemporalSession(request,
				OrganizacionConstants.ORGANIZACION_VIEW_NAME);
		setReturnActionFordward(request,
				mappings.findForward("homeorganizacion"));
	}

	public ActionForward getForwardVistaNodo(TreeNode node,
			ActionMapping mappings, HttpServletRequest request)
			throws Exception {
		OrganizacionVO organizacionVO = (OrganizacionVO) node.getModelItem();

		String paramValue = organizacionVO.getId();
		String idParamName = "idOrganizacion";
		ActionRedirect redirectVistaNodo = new ActionRedirect(
				mappings.findForward("verOrganizacion"));
		redirectVistaNodo.addParameter(idParamName, paramValue);
		return redirectVistaNodo;
	}

	public ActionForward getForwardVistaNodo(String idOrganizacion,
			ActionMapping mappings, HttpServletRequest request)
			throws Exception {

		String idParamName = "idOrganizacion";
		ActionRedirect redirectVistaNodo = new ActionRedirect(
				mappings.findForward("verOrganizacion"));
		redirectVistaNodo.addParameter(idParamName, idOrganizacion);
		return redirectVistaNodo;
	}

}