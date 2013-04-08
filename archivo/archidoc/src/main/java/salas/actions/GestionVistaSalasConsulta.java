package salas.actions;

import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import salas.SalasConsultaConstants;
import salas.exceptions.SalasConsultaException;
import salas.model.GestionSalasConsultaBI;
import salas.vos.SalasConsultaVOBase;
import se.usuarios.AppUser;
import util.TreeNode;
import util.TreeView;

import common.Constants;
import common.Messages;
import common.actions.ActionRedirect;
import common.actions.NodeSelectionHandlerAction;
import common.actions.TreeViewManager;

public class GestionVistaSalasConsulta extends TreeViewManager implements
		NodeSelectionHandlerAction {

	private static final Logger logger = Logger
			.getLogger(GestionVistaSalasConsulta.class);

	public void verListadoEdificiosSalasConsultaExecuteLogic(
			ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			AppUser appUser = getAppUser(request);

			GestionSalasConsultaBI salasBI = getGestionSalasBI(request);

			List listaEdificios = salasBI.getEdificiosByIdsArchivo(appUser
					.getIdsArchivosUser());

			request.setAttribute(SalasConsultaConstants.LISTA_EDIFICIOS_KEY,
					listaEdificios);

			setReturnActionFordward(
					request,
					mappings.findForward(SalasConsultaConstants.FORWARD_LISTADO_EDIFICIOS));
		} catch (SecurityException e) {
			accionSinPermisos(request);
			goLastClientExecuteLogic(mappings, form, request, response);
		}
	}

	public void goHomeExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		getTreeViewSalasConsulta(request);
		setReturnActionFordward(request,
				mappings.findForward("home_salas_consulta"));
	}

	public void crearVistaExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		setReturnActionFordward(request,
				crearVista(mappings, form, request, response));
	}

	private TreeView getTreeViewSalasConsulta(HttpServletRequest request) {
		TreeView treeView = (TreeView) getFromTemporalSession(request,
				SalasConsultaConstants.SALAS_CONSULTA_VIEW_NAME);
		if (treeView == null) {
			GestionSalasConsultaBI bi = getGestionSalasBI(request);
			treeView = new EstructuraSalasConsultaTreeView(Messages.getString(
					SalasConsultaConstants.ETIQUETA_ARBOL_NAME,
					request.getLocale()), bi.getEstructuraSalasConsulta());
			treeView.setNodeSelectionHandler(this);
			setInTemporalSession(request,
					SalasConsultaConstants.SALAS_CONSULTA_VIEW_NAME, treeView);
		}

		return treeView;
	}

	public ActionForward crearVista(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		getTreeViewSalasConsulta(request);
		return mappings.findForward("done");
	}

	public void loadHomeSalasConsultaExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		getTreeViewSalasConsulta(request);

		String reloadTreeView = request
				.getParameter(Constants.TREE_VIEW_RELOAD);
		if (reloadTreeView != null) {
			request.setAttribute(Constants.TREE_VIEW_RELOAD, Boolean.TRUE);
		}

		setReturnActionFordward(request,
				mappings.findForward("ver_listado_edificios_salas_consulta"));
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

		TreeView treeView = getTreeViewSalasConsulta(request);

		if (Boolean.valueOf(request.getParameter("refreshView")).booleanValue())
			request.setAttribute(SalasConsultaConstants.REFRESH_VIEW_KEY,
					Boolean.TRUE);

		String pNode = request.getParameter("node");
		if (isRootNode(pNode)) {
			ActionForward ret = new ActionForward();
			ret.setPath(SalasConsultaConstants.PATH_ACTION_HOME);
			ret.setRedirect(true);
			return ret;
		} else {
			TreeNode node = treeView.getNode(URLDecoder.decode(pNode,
					Constants.ENCODING_ISO_8859_1));
			if (node != null) {
				treeView.setSelectedNode(node);
				node.setVisible();
				forwardVistaNodo = getForwardVistaNodo(node, mappings, request);
			} else if (pNode != null && node == null) {
				logger.debug("El nodo con id "
						+ node
						+ " no se encuentra en el sistema por que contiene espacios");
				request.setAttribute(SalasConsultaConstants.REFRESH_VIEW_KEY,
						Boolean.TRUE);
				guardarError(request, new SalasConsultaException(
						SalasConsultaException.ERROR_AL_MOSTRAR_ELEMENTO));
			} else {
				logger.debug("El nodo con id "
						+ node
						+ " seleccionado actualmente no se encuentra o ha sido eliminado");
				request.setAttribute(SalasConsultaConstants.REFRESH_VIEW_KEY,
						Boolean.TRUE);
				guardarError(request, new SalasConsultaException(
						SalasConsultaException.ERROR_ELEMENTO_NO_ENCONTRADO));
			}
		}
		return forwardVistaNodo;
	}

	public String getHandlerPath() {
		return SalasConsultaConstants.PATH_ACTION_VER_NODO;
	}

	public void homeSalasConsultaExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		removeInTemporalSession(request,
				SalasConsultaConstants.SALAS_CONSULTA_VIEW_NAME);
		setReturnActionFordward(request,
				mappings.findForward("home_salas_consulta"));
	}

	public ActionForward getForwardVistaNodo(TreeNode node,
			ActionMapping mappings, HttpServletRequest request)
			throws Exception {
		ActionRedirect redirectVistaNodo = null;
		String idParamName = null;
		SalasConsultaVOBase elemento = (SalasConsultaVOBase) node
				.getModelItem();
		String paramValue = elemento.getId();

		if (SalasConsultaConstants.TIPO_ELEMENTO_EDIFICIO.equals(elemento
				.getTipo())) {
			redirectVistaNodo = new ActionRedirect(
					mappings.findForward(SalasConsultaConstants.GLOBAL_FORWARD_VER_EDIFICIO));
			idParamName = SalasConsultaConstants.PARAM_ID_EDIFICIO;
		} else if (SalasConsultaConstants.TIPO_ELEMENTO_SALA.equals(elemento
				.getTipo())) {
			redirectVistaNodo = new ActionRedirect(
					mappings.findForward(SalasConsultaConstants.GLOBAL_FORWARD_VER_SALA));
			idParamName = SalasConsultaConstants.PARAM_ID_SALA;
		}

		redirectVistaNodo.addParameter(idParamName, paramValue);
		return redirectVistaNodo;
	}

}