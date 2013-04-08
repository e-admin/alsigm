package deposito.actions;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import util.CollectionUtils;
import util.TreeModelItem;
import util.TreeNode;
import util.TreeView;

import common.ConfigConstants;
import common.Constants;
import common.actions.ActionRedirect;
import common.actions.NodeSelectionHandlerAction;
import common.actions.TreeViewManager;
import common.bi.ServiceRepository;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;
import common.util.ArrayUtils;
import common.view.POUtils;

import deposito.DepositoConstants;
import deposito.forms.HuecoForm;
import deposito.model.DepositoException;
import deposito.model.GestorEstructuraDepositoBI;
import deposito.vos.DepositoVO;
import deposito.vos.ElementoVO;
import deposito.vos.HuecoID;
import deposito.vos.HuecoVO;
import deposito.vos.InformeOcupacion;

/**
 * Controlador para el manejo de la vista en forma de árbol del depósito físico
 */
public class GestionVistaDeposito extends TreeViewManager implements
		NodeSelectionHandlerAction {

	private static final Logger logger = Logger
			.getLogger(GestionVistaDeposito.class);

	public void goHomeExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		GestorEstructuraDepositoBI depositoService = getGestorEstructuraDepositoBI(request);

		String idElementoDeposito = request.getParameter("itemID");
		String tipoElementoDeposito = request.getParameter("itemTipo");
		if (idElementoDeposito != null) {
			TreeView treeView = (TreeView) getFromTemporalSession(request,
					DepositoConstants.DEPOSITO_VIEW_NAME);
			if (treeView == null) {
				treeView = new EstructuraDepositoTreeView(
						"Estructura depósito",
						depositoService.getEstructuraDeposito());
				treeView.setNodeSelectionHandler(this);
				setInTemporalSession(request,
						DepositoConstants.DEPOSITO_VIEW_NAME, treeView);
			}

			TreeNode nodeToShow = null;
			ElementoVO elementoDepositoVO = null;
			if (tipoElementoDeposito.equals(DepositoVO.ID_TIPO_HUECO)) {
				String idHueco = idElementoDeposito;
				HuecoID huecoID = HuecoForm.getHuecoID(idHueco);
				HuecoVO huecoVO = depositoService.getInfoHueco(huecoID);
				String idTipoPadre = request.getParameter("itemPadreTipo");

				elementoDepositoVO = depositoService.getInfoElemento(
						huecoVO.getIdElemAPadre(), idTipoPadre);

				nodeToShow = treeView
						.findNode((TreeModelItem) elementoDepositoVO);
				((ElementoVO) nodeToShow.getModelItem()).setHueco(true);
				((ElementoVO) nodeToShow.getModelItem())
						.setIdHueco(idElementoDeposito);
			} else {
				// obtencion de elemento a ver
				elementoDepositoVO = depositoService.getInfoElemento(
						idElementoDeposito, tipoElementoDeposito);
				nodeToShow = treeView
						.findNode((TreeModelItem) elementoDepositoVO);
				((ElementoVO) nodeToShow.getModelItem()).setHueco(false);
				((ElementoVO) nodeToShow.getModelItem()).setIdHueco(null);

			}

			// buscar el nodo para dejarlo como nodo seleccionado
			if (nodeToShow != null) {
				treeView.setSelectedNode(nodeToShow);
				nodeToShow.setVisible();
			}

			// indicar refresco del arbol
			request.setAttribute("viewAction", "obtenerVista");
			// parametro necesario para la treeframelayaout.jsp!!!!!
			request.setAttribute("viewName",
					DepositoConstants.DEPOSITO_VIEW_NAME);

		} else
			removeInTemporalSession(request,
					DepositoConstants.DEPOSITO_VIEW_NAME);

		setReturnActionFordward(request,
				goHome(mappings, form, request, response));
	}

	public ActionForward goHome(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		return mappings.findForward("homedeposito");
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

		GestorEstructuraDepositoBI depositoBI = services
				.lookupGestorEstructuraDepositoBI();
		TreeView treeView = (TreeView) getFromTemporalSession(request,
				DepositoConstants.DEPOSITO_VIEW_NAME);
		if (treeView == null) {
			treeView = new EstructuraDepositoTreeView("Estructura depósito",
					depositoBI.getEstructuraDeposito());
			treeView.setNodeSelectionHandler(this);
			setInTemporalSession(request, DepositoConstants.DEPOSITO_VIEW_NAME,
					treeView);
		}

		return mappings.findForward("done");
	}

	public void verNodoExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		setReturnActionFordward(request,
				verNodo(mappings, form, request, response));
	}

	public void verPadreExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		TreeView treeView = (TreeView) getFromTemporalSession(request,
				DepositoConstants.DEPOSITO_VIEW_NAME);
		String pNodePath = request.getParameter("node");
		if (treeView != null && StringUtils.isNotBlank(pNodePath)) {
			TreeNode node = treeView.getNode(pNodePath).getParent();
			treeView.setSelectedNode(node);
			setReturnActionFordward(request,
					getForwardVistaNodo(node, mappings, request));
			if (Boolean.valueOf(request.getParameter("refreshView"))
					.booleanValue())
				request.setAttribute(DepositoConstants.REFRESH_VIEW_KEY,
						Boolean.TRUE);

		} else
			goLastClientExecuteLogic(mappings, form, request, response);
	}

	public ActionForward verNodo(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		// usado para indicar si el arbol debe de refrescarse
		if (Boolean.valueOf(request.getParameter("refreshView")).booleanValue())
			request.setAttribute(DepositoConstants.REFRESH_VIEW_KEY,
					Boolean.TRUE);
		ActionForward forwardVistaNodo = mappings
				.findForward("lista_ubicaciones");

		TreeView treeView = (TreeView) getFromTemporalSession(request,
				DepositoConstants.DEPOSITO_VIEW_NAME);
		if (treeView != null) {
			String pNode = request.getParameter("node");
			if (isRootNode(pNode)) {
				ActionForward ret = new ActionForward();
				ret.setPath("/action/manageVistaDeposito?actionToPerform=homeDeposito");
				ret.setRedirect(true);
				return ret;
			} else {
				try {
					TreeNode node = treeView.getNode(URLDecoder.decode(pNode,
							Constants.ENCODING_ISO_8859_1));
					if (node != null) {
						treeView.setSelectedNode(node);
						node.setVisible();
						forwardVistaNodo = getForwardVistaNodo(node, mappings,
								request);
						((ElementoVO) node.getModelItem()).setHueco(false);
						((ElementoVO) node.getModelItem()).setIdHueco(null);
					} else {
						logger.debug("El nodo seleccionado actualmente no existe, ha sido eliminado");
						// refrescar el arbol e indicar que se ha producido un
						// error
						request.setAttribute(
								DepositoConstants.REFRESH_VIEW_KEY,
								Boolean.TRUE);
						guardarError(request, new DepositoException());

						goBackExecuteLogic(mappings, form, request, response);

					}
				} catch (UnsupportedEncodingException uee) {
					logger.error("Error decodificando parametro con path de elemento de depósito: "
							+ uee);
				}
			}
		}
		return forwardVistaNodo;
	}

	public void mostrarEstadoVistaExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		setReturnActionFordward(request,
				mostrarEstadoVista(mappings, form, request, response));

	}

	public ActionForward mostrarEstadoVista(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		TreeView treeView = (TreeView) getFromTemporalSession(request,
				DepositoConstants.DEPOSITO_VIEW_NAME);
		TreeNode selectedNode = treeView.getSelectedNode();
		request.setAttribute("viewAction", "obtenerVista");
		request.setAttribute("currentView",
				getForwardVistaNodo(selectedNode, mappings, request).getPath());
		return mappings.findForward("homedeposito");
	}

	public ActionForward getForwardVistaNodo(TreeNode node,
			ActionMapping mappings, HttpServletRequest request) {

		ActionRedirect redirectVistaNodo = null;
		String idParamName = null;
		ElementoVO elementoDeposito = (ElementoVO) node.getModelItem();

		String paramValue = elementoDeposito.getId();
		if (elementoDeposito.isHueco()) {
			redirectVistaNodo = new ActionRedirect(
					mappings.findForward("verHueco"));
			idParamName = "idHueco";
			paramValue = elementoDeposito.getIdHueco();
			elementoDeposito.setHueco(false);
			elementoDeposito.setIdHueco(null);
		} else if (DepositoVO.ID_TIPO_ELEMENTO_UBICACION
				.equals(elementoDeposito.getIdTipoElemento())) {
			redirectVistaNodo = new ActionRedirect(
					mappings.findForward("verUbicacion"));
			idParamName = "idUbicacion";
		} else if (elementoDeposito.isAsignable()) {
			redirectVistaNodo = new ActionRedirect(
					mappings.findForward("verAsignable"));
			idParamName = "idAsignable";
		} else {
			redirectVistaNodo = new ActionRedirect(
					mappings.findForward("verNoAsignable"));
			idParamName = "idNoAsignable";
		}
		redirectVistaNodo.addParameter(idParamName, paramValue);
		return redirectVistaNodo;
	}

	public String getHandlerPath() {
		return "/action/manageVistaDeposito?actionToPerform=verNodo";
	}

	public class ElementoBandejaDeposito extends ElementoVO {

		InformeOcupacion informeOcupacion = null;
		int nivelEnBandeja;
		int numeroHijos;

		ServiceRepository services = null;
		transient GestorEstructuraDepositoBI depositoBI = null;

		ElementoBandejaDeposito(ElementoVO elementoVO, int nivelEnBandeja,
				int numeroHijos, ServiceRepository services) {
			POUtils.copyVOProperties(this, elementoVO);
			this.services = services;
			this.nivelEnBandeja = nivelEnBandeja;
			this.numeroHijos = numeroHijos;

		}

		public int getNivelEnBandeja() {
			return nivelEnBandeja;
		}

		public void setNivelEnBandeja(int nivelEnBandeja) {
			this.nivelEnBandeja = nivelEnBandeja;
		}

		public boolean isTipoDeposito() {
			return (nivelEnBandeja == 1);
		}

		public boolean isTipoUbicacion() {
			return (nivelEnBandeja == 0);
		}

		public InformeOcupacion getInformeOcupacion() {
			if (informeOcupacion == null)
				informeOcupacion = getDepositoBI().getInformeOcupacionDeposito(
						getId());
			return informeOcupacion;
		}

		public boolean isAsignable() {
			return false;
		}

		private GestorEstructuraDepositoBI getDepositoBI() {
			if (depositoBI == null)
				depositoBI = services.lookupGestorEstructuraDepositoBI();
			return depositoBI;
		}
	}

	public Collection obtenerDesdeNivelOrigenHastaNivelDeposito(
			ServiceRepository services,
			GestorEstructuraDepositoBI depositoService, int maxNivel,
			Collection elementosPadre, int nivelBandeja) throws Exception {
		List ret = new ArrayList();
		if (!CollectionUtils.isEmpty(elementosPadre)) {
			for (Iterator itElementos = elementosPadre.iterator(); itElementos
					.hasNext();) {
				ElementoVO padre = (ElementoVO) itElementos.next();
				int numHijos = 0;
				Collection listaDeHijos = new ArrayList();
				if (nivelBandeja <= maxNivel) {
					List childs = depositoService.getHijosElemento(
							padre.getId(), padre.getIdTipoElemento());
					listaDeHijos = obtenerDesdeNivelOrigenHastaNivelDeposito(
							services, depositoService, maxNivel, childs,
							nivelBandeja + 1);

					if (!CollectionUtils.isEmpty(listaDeHijos))
						numHijos = listaDeHijos.size();
				}
				ret.add(new ElementoBandejaDeposito(padre, nivelBandeja,
						numHijos, services));
				ret.addAll(listaDeHijos);
			}
		}
		return ret;
	}

	public void resumenDepositoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String reloadTreeView = request
				.getParameter(Constants.TREE_VIEW_RELOAD);
		if (reloadTreeView != null) {
			request.setAttribute(Constants.TREE_VIEW_RELOAD, Boolean.TRUE);
		}

		if (Boolean.valueOf(request.getParameter("refreshView")).booleanValue())
			request.setAttribute(DepositoConstants.REFRESH_VIEW_KEY,
					Boolean.TRUE);

		// TreeView treeView = (TreeView) getFromTemporalSession(request,
		// DepositoConstants.DEPOSITO_VIEW_NAME);
		ServiceRepository services = getServiceRepository(request);
		GestorEstructuraDepositoBI depositoBI = services
				.lookupGestorEstructuraDepositoBI();

		TreeView treeView = new EstructuraDepositoTreeView(
				"Estructura depósito", depositoBI.getEstructuraDeposito());
		treeView.setNodeSelectionHandler(this);
		setInTemporalSession(request, DepositoConstants.DEPOSITO_VIEW_NAME,
				treeView);
		String viewAction = (String) request.getAttribute("viewAction");
		String viewName = (String) request.getAttribute("viewName");

		ClientInvocation cli = saveCurrentInvocation(
				KeysClientsInvocations.DEPOSITO_INFORME_OCUPACION, request,
				treeView, viewName, viewAction);
		cli.setAsReturnPoint(true);

		// obtengo todos las ubicaciones
		GestorEstructuraDepositoBI depositoService = services
				.lookupGestorEstructuraDepositoBI();
		Collection ubicaciones = null;

		if (ConfigConstants.getInstance().getMostrarTodasUbicaciones()) {
			ubicaciones = depositoService.getEdificios();
		} else {
			List custodyArchiveList = services.getServiceClient()
					.getCustodyArchiveList();
			ubicaciones = depositoService.getUbicacionesXIdsArchivo(ArrayUtils
					.toString(custodyArchiveList.toArray()));
		}

		Collection bandejaObjects = obtenerDesdeNivelOrigenHastaNivelDeposito(
				services, depositoService, 0, ubicaciones, 0);

		request.setAttribute(DepositoConstants.LISTA_ELEMENTOS_BANDEJA_KEY,
				bandejaObjects);
		setReturnActionFordward(request,
				mappings.findForward("resumenOcupacion"));
	}

	public void homeDepositoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		setReturnActionFordward(request, mappings.findForward("homedeposito"));
	}

	public void loadHomeDepositoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ServiceRepository services = getServiceRepository(request);
		GestorEstructuraDepositoBI depositoBI = services
				.lookupGestorEstructuraDepositoBI();

		Collection ubicaciones = null;

		if (ConfigConstants.getInstance().getMostrarTodasUbicaciones()) {
			ubicaciones = depositoBI.getEdificios();
		} else {
			List custodyArchiveList = services.getServiceClient()
					.getCustodyArchiveList();
			ubicaciones = depositoBI.getUbicacionesXIdsArchivo(ArrayUtils
					.toString(custodyArchiveList.toArray()));
		}

		request.setAttribute(DepositoConstants.LISTA_UBICACIONES_KEY,
				ubicaciones);

		List listaUbicaciones = new ArrayList();
		if (ubicaciones != null && ubicaciones.size() > 0) {
			Iterator it = ubicaciones.iterator();
			while (it.hasNext()) {
				DepositoVO ubicacion = (DepositoVO) it.next();
				listaUbicaciones.add(ubicacion.getId());
			}
		}

		request.setAttribute(DepositoConstants.RESUMEN_OCUPACION,
				depositoBI.getResumenOcupacionByUbicaciones(listaUbicaciones));

		// cambio realizado para que al pulsar sobre el enlace en la miga de
		// pan, aparezcan solo los nodos raices
		// TreeView treeView = (TreeView) getFromTemporalSession(request,
		// DepositoConstants.DEPOSITO_VIEW_NAME);
		TreeView treeView = new EstructuraDepositoTreeView(
				"Estructura depósito", depositoBI.getEstructuraDeposito());
		treeView.setNodeSelectionHandler(this);
		setInTemporalSession(request, DepositoConstants.DEPOSITO_VIEW_NAME,
				treeView);
		// if (treeView != null) {
		// treeView.setSelectedNode(null);
		// } else {
		// treeView = new
		// EstructuraDepositoTreeView("Estructura depósito",depositoBI.getEstructuraDeposito());
		// treeView.setNodeSelectionHandler(this);
		// setInTemporalSession(request, DepositoConstants.DEPOSITO_VIEW_NAME,
		// treeView);
		// }

		String reloadTreeView = request
				.getParameter(Constants.TREE_VIEW_RELOAD);
		if (reloadTreeView != null) {
			request.setAttribute(Constants.TREE_VIEW_RELOAD, Boolean.TRUE);
		}

		// if
		// (Boolean.valueOf(request.getParameter("refreshView")).booleanValue())
		request.setAttribute(DepositoConstants.REFRESH_VIEW_KEY, Boolean.TRUE);

		// if
		// (Boolean.valueOf(request.getParameter("refreshView")).booleanValue())
		// request.setAttribute(DepositoConstants.REFRESH_VIEW_KEY,
		// Boolean.TRUE);
		String viewAction = (String) request.getAttribute("viewAction");
		String viewName = (String) request.getAttribute("viewName");

		saveCurrentInvocation(KeysClientsInvocations.DEPOSITO_HOME, request,
				treeView, viewName, viewAction);
		setReturnActionFordward(request,
				mappings.findForward("lista_ubicaciones"));
	}

}