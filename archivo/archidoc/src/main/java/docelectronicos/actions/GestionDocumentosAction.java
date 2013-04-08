package docelectronicos.actions;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import util.NavigationContextStack;
import util.TreeModel;
import util.TreeNode;
import util.TreeView;

import common.Constants;
import common.RequestUtil;
import common.actions.ActionRedirect;
import common.actions.NodeSelectionHandlerAction;
import common.actions.TreeViewManager;
import common.navigation.ClientInvocation;
import common.navigation.InvocationStack;
import common.navigation.KeysClientsInvocations;
import common.util.StringUtils;
import common.util.TypeConverter;

import docelectronicos.DocumentosConstants;
import docelectronicos.TipoNodo;
import docelectronicos.TipoObjeto;
import docelectronicos.exceptions.DocElectronicosException;
import docelectronicos.exceptions.DocElectronicosSecurityException;
import docelectronicos.model.DocumentosTreeView;
import docelectronicos.vos.DocumentosTreeModelItemVO;

/**
 * Acción para la gestión de los documentos electrónicos.
 */
public class GestionDocumentosAction extends TreeViewManager implements
		NodeSelectionHandlerAction {

	/**
	 * Muestra la página de inicio de los documentos electrónicos de un objeto.
	 * 
	 * @param mapping
	 *            {@link ActionMapping} con los mapeos asociado.
	 * @param form
	 *            {@link ActionForm} asociado al action.
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 */
	protected void homeExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		saveCurrentInvocation(
				KeysClientsInvocations.DOCUMENTOS_ELECTRONICOS_ENTRADA, request);
		doHomeLogic(mapping, form, request, response);
	}

	/**
	 * Muestra la página de inicio de los documentos electrónicos de una unidad
	 * documental.
	 * 
	 * @param mapping
	 *            {@link ActionMapping} con los mapeos asociado.
	 * @param form
	 *            {@link ActionForm} asociado al action.
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 */
	protected void homeUDocExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		InvocationStack invocationStack = getInvocationStack(request);

		boolean enContenidoSerie = false;
		int contSerieIndex = invocationStack
				.getIndexClientInvocation(KeysClientsInvocations.CUADRO_CONTENIDO_SERIE);
		int cuadroVerUDocIndex = invocationStack
				.getIndexClientInvocation(KeysClientsInvocations.CUADRO_VER_UNIDAD_DOCUMENTAL);
		if (contSerieIndex != -1 && contSerieIndex < cuadroVerUDocIndex)
			enContenidoSerie = true;
		if ((invocationStack.getLastClientInvocation().getKeyClient()
				.equals(KeysClientsInvocations.CUADRO_VER_UNIDAD_DOCUMENTAL))
				&& (invocationStack.getLastClientInvocation().getPath()
						.indexOf("manageVistaCuadroClasificacion") == -1)
				&& (!enContenidoSerie)) {
			ClientInvocation cli = invocationStack.getLastClientInvocation();
			cli.setPath(request.getContextPath()
					+ "/action/manageVistaCuadroClasificacion");

			String id = null;
			Object o = cli.getParameters().get(Constants.ID);
			if ((o instanceof String[]) && ((String[]) o).length > 0)
				id = ((String[]) o)[0];
			else
				id = (String) o;
			if (StringUtils.isEmpty(id)) {
				o = cli.getParameters().get("idelementocf");
				if ((o instanceof String[]) && ((String[]) o).length > 0)
					id = ((String[]) o)[0];
				else
					id = (String) o;
			}
			cli.getParameters().clear();
			cli.addParameter("actionToPerform", "goHome");
			cli.addParameter("itemID", id);

		}

		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.DOCUMENTOS_ELECTRONICOS_ENTRADA, request);
		invocation.setVisibleInNavigationToolBar(false);

		doHomeLogic(mapping, form, request, response);
	}

	protected void viewDocumentCFExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// Leer el identificador del objeto
		String id = request.getParameter(Constants.ID);
		if (logger.isInfoEnabled())
			logger.info("Id Objeto: " + id);

		setInTemporalSession(request, DocumentosConstants.OBJECT_ID_KEY, id);

		// Leer el tipo de objeto
		int tipo = TypeConverter.toInt(request.getParameter("tipo"),
				TipoObjeto.ELEMENTO_CF);
		if (logger.isInfoEnabled())
			logger.info("Tipo Objeto: " + tipo);

		setInTemporalSession(request, DocumentosConstants.OBJECT_TYPE_KEY,
				new Integer(tipo));

		getGestionDocumentosElectronicosBI(request)
				.inicializaClasificadoresYRepEcm(id, TipoObjeto.ELEMENTO_CF,
						false);

		setInTemporalSession(request,
				DocumentosConstants.ACCESO_DOCUMENTOS_IDELEMENTO_KEY, id);

		setReturnActionFordward(request,
				mapping.findForward("ver_documento_tree"));

	}

	protected void doHomeLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// Leer el identificador del objeto
		String id = request.getParameter(Constants.ID);
		if (logger.isInfoEnabled())
			logger.info("Id Objeto: " + id);

		setInTemporalSession(request, DocumentosConstants.OBJECT_ID_KEY, id);

		// Leer el tipo de objeto
		int tipo = TypeConverter.toInt(request.getParameter("tipo"),
				TipoObjeto.DESCRIPTOR);
		if (logger.isInfoEnabled())
			logger.info("Tipo Objeto: " + tipo);

		setInTemporalSession(request, DocumentosConstants.OBJECT_TYPE_KEY,
				new Integer(tipo));

		try {
			// Inicializar los clasificadores por defecto
			getGestionDocumentosElectronicosBI(request)
					.inicializaClasificadoresYRepEcm(id, tipo, false);

			setReturnActionFordward(request, mapping.findForward("home"));
		} catch (DocElectronicosException e) {
			guardarError(request, e);
			InvocationStack invStk = getInvocationStack(request);
			ClientInvocation cli = invStk
					.getClientInvocation(invStk.getSize() - 2);
			if (cli.getKeyClient().indexOf("CUADRO") > -1) {
				cli.setPath(request.getContextPath()
						+ "/action/manageVistaCuadroClasificacion");
				cli.addParameter("actionToPerform", "goHome");
				cli.addParameter("contentForwardName",
						request.getParameter("contentForwardName"));
				cli.addParameter("itemID", id);
			}

			goLastClientExecuteLogic(mapping, form, request, response);
		} catch (DocElectronicosSecurityException e) {
			// Añadir los errores al request
			obtenerErrores(request, true).add(ActionErrors.GLOBAL_MESSAGE,
					new ActionError(Constants.ERROR_ACCION_NO_PERMITIDA));

			InvocationStack invStk = getInvocationStack(request);
			ClientInvocation cli = invStk
					.getClientInvocation(invStk.getSize() - 2);
			if (cli.getKeyClient().indexOf("CUADRO") > -1) {
				cli.setPath(request.getContextPath()
						+ "/action/manageVistaCuadroClasificacion");
				cli.addParameter("actionToPerform", "goHome");
				cli.addParameter("contentForwardName",
						request.getParameter("contentForwardName"));
				cli.addParameter("itemID", id);
			}

			goLastClientExecuteLogic(mapping, form, request, response);
		}
	}

	/**
	 * Muestra el árbol de documentos electrónicos de un objeto.
	 * 
	 * @param mapping
	 *            {@link ActionMapping} con los mapeos asociado.
	 * @param form
	 *            {@link ActionForm} asociado al action.
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 */
	public void crearVistaExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		setReturnActionFordward(request,
				crearVista(mappings, form, request, response));
	}

	/**
	 * Crea el árbol de documentos electrónicos de un objeto.
	 * 
	 * @param mapping
	 *            {@link ActionMapping} con los mapeos asociado.
	 * @param form
	 *            {@link ActionForm} asociado al action.
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 */
	public ActionForward crearVista(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		removeInTemporalSession(request, DocumentosConstants.DOCUMENT_TREE_KEY);
		String id = (String) getFromTemporalSession(request,
				DocumentosConstants.OBJECT_ID_KEY);
		Integer tipo = (Integer) getFromTemporalSession(request,
				DocumentosConstants.OBJECT_TYPE_KEY);

		TreeModel treeModel = getGestionDocumentosElectronicosBI(request)
				.getTreeModel(id, tipo.intValue());

		DocumentosTreeView treeView = new DocumentosTreeView(treeModel);
		treeView.setNodeSelectionHandler(this);
		treeView.expandNodes();

		setInTemporalSession(request, DocumentosConstants.DOCUMENT_TREE_KEY,
				treeView);

		return mappings.findForward("done");
	}

	public void verNodoExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		setReturnActionFordward(request,
				verNodo(mappings, form, request, response));
	}

	public ActionForward verNodo(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TreeView treeView = (TreeView) getFromTemporalSession(request,
				DocumentosConstants.DOCUMENT_TREE_KEY);

		String pNode = request.getParameter("node");
		if (logger.isDebugEnabled())
			logger.debug("Nodo seleccionado: " + pNode);

		if (treeView != null && StringUtils.isNotBlank(pNode)) {
			if (isRootNode(pNode)) {
				ActionForward ret = new ActionForward();
				ret.setPath("/action/clasificador?method=retrieve");
				ret.setRedirect(true);
				return ret;
			} else {
				TreeNode node = treeView.getNode(URLDecoder.decode(pNode,
						Constants.ENCODING_ISO_8859_1));
				if (node != null) {
					treeView.setSelectedNode(node);
					node.setVisible();
					NavigationContextStack navContext = NavigationContextStack
							.getInstance(request.getSession());
					navContext.setOnCancelURI(mappings.getPath(), RequestUtil
							.getParameterString(request.getParameterMap()));
					return getForwardVistaNodo(node, mappings, request);
				}
			}
		}

		setInTemporalSession(request, DocumentosConstants.DOCUMENT_TREE_KEY,
				treeView);

		return mappings.findForward("viewRefresher");
	}

	public ActionForward getForwardVistaNodo(TreeNode node,
			ActionMapping mappings, HttpServletRequest request) {
		DocumentosTreeModelItemVO modelItem = (DocumentosTreeModelItemVO) node
				.getModelItem();
		if (logger.isDebugEnabled())
			logger.debug("Nodo: " + modelItem.getItemPath() + " [TIPO: "
					+ modelItem.getNodeType() + "]");

		ActionRedirect view = null;

		if (modelItem.getNodeType() == TipoNodo.CLASIFICADOR)
			view = new ActionRedirect(mappings.findForward("verClasificador"));
		else if (modelItem.getNodeType() == TipoNodo.DOCUMENTO)
			view = new ActionRedirect(mappings.findForward("verDocumento"));

		view.addParameter(Constants.ID, modelItem.getItemId());
		view.addParameter(
				"idObjeto",
				getFromTemporalSession(request,
						DocumentosConstants.OBJECT_ID_KEY));
		view.addParameter(
				"tipoObjeto",
				getFromTemporalSession(request,
						DocumentosConstants.OBJECT_TYPE_KEY));

		return view;
	}

	/**
	 * Metodo que retorna el path del action encargado de ejecutar la accion
	 * verNodo
	 * 
	 * @return Path del action.
	 */
	public String getHandlerPath() {
		return "/action/documentos?actionToPerform=verNodo";
	}
}