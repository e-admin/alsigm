package fondos.actions;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import se.usuarios.AppUser;
import util.CollectionUtils;
import util.TreeModelItem;
import util.TreeNode;
import util.TreeView;

import common.ConfigConstants;
import common.Constants;
import common.actions.ActionRedirect;
import common.actions.NodeSelectionHandlerAction;
import common.actions.TreeViewManager;
import common.bi.GestionCuadroClasificacionBI;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;
import common.view.POUtils;

import fondos.FondosConstants;
import fondos.model.ElementoCuadroClasificacion;
import fondos.model.TipoNivelCF;
import fondos.vos.ElementoCuadroClasificacionVO;
import fondos.vos.INivelCFVO;

/**
 * Controlador para el manejo de la vista del cuadro de clasificación de fondos
 * documentales
 */
public class GestionVistaCuadroClasificacion extends TreeViewManager implements
		NodeSelectionHandlerAction {

	public void goHomeExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		setReturnActionFordward(request,
				goHome(mappings, form, request, response));
	}

	public ActionForward goHome(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		GestionCuadroClasificacionBI bi = getGestionCuadroClasificacionBI(request);

		String idElementoCF = request.getParameter("itemID");

		ElementoCuadroClasificacionVO elementoCF = null;
		if (idElementoCF != null) {
			elementoCF = bi.getElementoCuadroClasificacion(idElementoCF);
			if (elementoCF != null
					&& elementoCF.getTipo() == TipoNivelCF.UNIDAD_DOCUMENTAL
							.getIdentificador()) {
				String forwardName = request.getParameter("contentForwardName");
				if (forwardName == null)
					forwardName = "verUnidadDocumental";
				ActionRedirect vistaUdoc = new ActionRedirect(
						mappings.findForward(forwardName));
				vistaUdoc.addParameter(Constants.ID, elementoCF.getId());
				request.setAttribute("showContentURL", vistaUdoc.getPath());
				elementoCF = bi.getElementoCuadroClasificacion(elementoCF
						.getIdPadre());
			}
		} else {
			ClientInvocation invocation = saveCurrentInvocation(
					KeysClientsInvocations.CUADRO_PORTADA_GESTION_FONDOS,
					request);
			invocation.setAsReturnPoint(true);
		}

		TreeView treeView = (TreeView) getFromTemporalSession(request,
				FondosConstants.CUADRO_CLF_VIEW_NAME);
		if (treeView == null) {
			treeView = new CuadroClasificacionTreeView(
					bi.getCuadroClasificacion());
			treeView.setNodeSelectionHandler(this);
			setInTemporalSession(request, FondosConstants.CUADRO_CLF_VIEW_NAME,
					treeView);
		}

		AppUser appUser = getAppUser(request);
		if (elementoCF != null) {
			List ancestros = bi.getAncestors(elementoCF.getId());
			ElementoCuadroClasificacionVO padre = null;
			if (!CollectionUtils.isEmpty(ancestros)) {
				padre = (ElementoCuadroClasificacionVO) ancestros.get(0);
				ElementoCuadroClasificacionVO hijo;
				for (int i = 1; i < ancestros.size(); i++) {
					hijo = (ElementoCuadroClasificacionVO) ancestros.get(i);

					if (!(!appUser.isPersonalArchivo()
							&& ConfigConstants
									.getInstance()
									.getOcultarClasificadoresFondosUsuariosOficina()
							&& padre.getTipo() == ElementoCuadroClasificacion.TIPO_CL_FONDO && (hijo
							.getTipo() == ElementoCuadroClasificacion.TIPO_FONDO || hijo
							.getTipo() == ElementoCuadroClasificacion.TIPO_CL_FONDO))) {
						hijo.setParentElement(padre);
					}
					padre = hijo;
				}
			}
			if (!(!appUser.isPersonalArchivo()
					&& ConfigConstants.getInstance()
							.getOcultarClasificadoresFondosUsuariosOficina() && elementoCF
					.getTipo() == ElementoCuadroClasificacion.TIPO_FONDO)) {
				elementoCF.setParentElement(padre);
			}

			TreeNode nodeToShow = treeView.findNode((TreeModelItem) elementoCF);
			if (nodeToShow != null) {
				treeView.setSelectedNode(nodeToShow);
				nodeToShow.setVisible();
			}
			request.setAttribute("viewAction", "obtenerVista");
			request.setAttribute("viewName",
					FondosConstants.CUADRO_CLF_VIEW_NAME);
		}

		return mappings.findForward("homefondos");
	}

	public void crearVistaExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		setReturnActionFordward(request,
				crearVista(mappings, form, request, response));
	}

	public ActionForward crearVista(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		GestionCuadroClasificacionBI bi = getGestionCuadroClasificacionBI(request);
		TreeView treeView = (TreeView) getFromTemporalSession(request,
				FondosConstants.CUADRO_CLF_VIEW_NAME);
		if (treeView == null) {
			treeView = new CuadroClasificacionTreeView(
					bi.getCuadroClasificacion());
			treeView.setNodeSelectionHandler(this);
			setInTemporalSession(request, FondosConstants.CUADRO_CLF_VIEW_NAME,
					treeView);
		}

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
				FondosConstants.CUADRO_CLF_VIEW_NAME);
		if (treeView != null && request.getParameter("node") != null) {
			String pNode = request.getParameter("node");
			if (isRootNode(pNode)
					|| isRootNode(URLDecoder.decode(pNode,
							Constants.ENCODING_ISO_8859_1))) {
				ActionForward ret = new ActionForward();
				ret.setPath("/action/manageVistaCuadroClasificacion?actionToPerform=goHome");
				ret.setRedirect(true);
				return ret;
			} else {
				TreeNode node = treeView.getNode(URLDecoder.decode(pNode,
						Constants.ENCODING_ISO_8859_1));
				if (node != null) {
					treeView.setSelectedNode(node);
					node.setVisible();
					return getForwardVistaNodo(node, mappings, request);
				}
			}
		}

		return mappings.findForward("viewRefresher");
	}

	public ActionForward getForwardVistaNodo(TreeNode node,
			ActionMapping mappings, HttpServletRequest request) {
		ElementoCuadroClasificacionVO elementoCF = (ElementoCuadroClasificacionVO) node
				.getModelItem();
		INivelCFVO nivelElementoCF = getGestionCuadroClasificacionBI(request)
				.getNivelCF(elementoCF.getIdNivel());
		TipoNivelCF tipoNivel = nivelElementoCF.getTipoNivel();
		ActionForward view = null;
		if (tipoNivel == TipoNivelCF.CLASIFICADOR_FONDOS)
			view = new ActionRedirect(
					mappings.findForward("verClasificadorFondos"));
		else if (tipoNivel == TipoNivelCF.FONDO)
			view = new ActionRedirect(mappings.findForward("verFondo"));
		else if (tipoNivel == TipoNivelCF.CLASIFICADOR_SERIE)
			view = new ActionRedirect(
					mappings.findForward("verClasificadorSeries"));
		else if (tipoNivel == TipoNivelCF.SERIE)
			view = new ActionRedirect(mappings.findForward("verSerie"));

		((ActionRedirect) view)
				.addParameter("idelementocf", elementoCF.getId());
		return view;
	}

	public String getHandlerPath() {
		return "/action/manageVistaCuadroClasificacion?actionToPerform=verNodo";
	}

	public class ElementoBandejaFondo extends ElementoCuadroClasificacion {

		int nivelEnBandeja;
		int numeroHijos;

		// ElementoCuadroClasificacionVO elemento;
		//
		ElementoBandejaFondo(ElementoCuadroClasificacionVO elemento,
				int nivelEnBandeja, int numeroHijos) {
			POUtils.copyVOProperties(this, elemento);
			this.nivelEnBandeja = nivelEnBandeja;
			this.numeroHijos = numeroHijos;
		}

		/**
		 * @return Returns the nivelEnBandeja.
		 */
		public int getNivelEnBandeja() {
			return nivelEnBandeja;
		}

		/**
		 * @param nivelEnBandeja
		 *            The nivelEnBandeja to set.
		 */
		public void setNivelEnBandeja(int nivelEnBandeja) {
			this.nivelEnBandeja = nivelEnBandeja;
		}

		/**
		 * @return Returns the numeroHijos.
		 */
		public int getNumeroHijos() {
			return numeroHijos;
		}

		/**
		 * @param numeroHijos
		 *            The numeroHijos to set.
		 */
		public void setNumeroHijos(int numeroHijos) {
			this.numeroHijos = numeroHijos;
		}

	}

	protected int[] buscarElemento(ElementoCuadroClasificacionVO padre,
			List padres) {
		int index = 0;
		int ret[] = new int[] { -1, -1, -1 };
		for (Iterator itPadres = padres.iterator(); itPadres.hasNext();) {
			ElementoBandejaFondo unPadre = (ElementoBandejaFondo) itPadres
					.next();
			if (unPadre.getId().equalsIgnoreCase(padre.getId())) {
				ret[0] = index;
				ret[1] = unPadre.getNivelEnBandeja();
				ret[2] = unPadre.getNumeroHijos();
				return ret;
			}
			index++;
		}

		return ret;
	}

	public List obtenerDesdeNivelOrigenHastaNivelDeFondo(AppUser appUser,
			GestionCuadroClasificacionBI cuadroBI, List elementosPadre,
			int nivelBandeja) {
		List ret = new ArrayList();

		if (!CollectionUtils.isEmpty(elementosPadre)) {
			ElementoCuadroClasificacionVO element;
			int numHijos;
			List listaDeHijos;

			for (Iterator itElementos = elementosPadre.iterator(); itElementos
					.hasNext();) {
				element = (ElementoCuadroClasificacionVO) itElementos.next();
				numHijos = 0;
				if (!appUser.isPersonalArchivo()
						&& ConfigConstants
								.getInstance()
								.getOcultarClasificadoresFondosUsuariosOficina()) {
					ret.add(new ElementoBandejaFondo(element, nivelBandeja,
							numHijos));
				} else {

					listaDeHijos = new ArrayList();
					if (!element.isTipoFondo()) {
						listaDeHijos = obtenerDesdeNivelOrigenHastaNivelDeFondo(
								appUser,
								cuadroBI,
								cuadroBI.getHijosElementoCuadroClasificacion(element
										.getId()), nivelBandeja + 1);

						if (!CollectionUtils.isEmpty(listaDeHijos))
							numHijos = listaDeHijos.size();
					}
					ret.add(new ElementoBandejaFondo(element, nivelBandeja,
							numHijos));
					ret.addAll(listaDeHijos);
				}
			}
		}
		return ret;
	}

	public void resumenFondosExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// obtengo todos los clasificadores de primer nivel
		GestionCuadroClasificacionBI cuadroBI = getGestionCuadroClasificacionBI(request);
		List elementosRaizCuadroClasificacion = cuadroBI.getRootNodes();
		AppUser appUser = getAppUser(request);
		List bandejaObjects = obtenerDesdeNivelOrigenHastaNivelDeFondo(appUser,
				cuadroBI, elementosRaizCuadroClasificacion, 0);

		String reloadTreeView = request
				.getParameter(Constants.TREE_VIEW_RELOAD);
		if (reloadTreeView != null) {
			request.setAttribute(Constants.TREE_VIEW_RELOAD, Boolean.TRUE);
		}

		if (Boolean.valueOf(request.getParameter("refreshView")).booleanValue())
			request.setAttribute(FondosConstants.REFRESH_VIEW_KEY, Boolean.TRUE);

		request.setAttribute(FondosConstants.LISTA_ELEMENTOS_BANDEJA_KEY,
				bandejaObjects);

		// cambio realizado para que al pulsar sobre el enlace en la miga de
		// pan, aparezcan solo los nodos raices
		// CuadroClasificacionTreeView treeView = (TreeView)
		// getFromTemporalSession(request,
		// FondosConstants.CUADRO_CLF_VIEW_NAME);
		CuadroClasificacionTreeView treeView = new CuadroClasificacionTreeView(
				cuadroBI.getCuadroClasificacion());
		treeView.setNodeSelectionHandler(this);
		setInTemporalSession(request, FondosConstants.CUADRO_CLF_VIEW_NAME,
				treeView);

		treeView.setSelectedNode(null);

		String viewAction = (String) request.getAttribute("viewAction");
		String viewName = (String) request.getAttribute("viewName");

		ClientInvocation cli = saveCurrentInvocation(
				KeysClientsInvocations.CUADRO_RESUMEN_FONDOS, request,
				treeView, viewAction, viewName);
		cli.setAsReturnPoint(true);

		Collection listaTiposSubniveles = cuadroBI
				.getTiposSubniveles(TipoNivelCF.ID_NIVEL_RAIZ);

		request.setAttribute(FondosConstants.CHILD_TYPES_ELEMENTO_CF_KEY,
				listaTiposSubniveles);
		setInTemporalSession(request, FondosConstants.LISTA_SUBNIVELES_KEY,
				cuadroBI.getNivelesCF(TipoNivelCF.ID_NIVEL_RAIZ));

		setInTemporalSession(request, FondosConstants.ELEMENTO_CF_KEY,
				new ElementoCuadroClasificacion());

		setReturnActionFordward(request, mappings.findForward("resumenFondos"));
	}

	public void exportarCuadroExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		saveCurrentInvocation(KeysClientsInvocations.EXPORTAR_CUADRO, request);

		setReturnActionFordward(request, mappings.findForward("exportarCuadro"));
	}

}