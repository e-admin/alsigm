package organizacion.view.actions;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import organizacion.OrganizacionConstants;
import organizacion.model.bi.GestionOrganizacionBI;
import organizacion.model.vo.OrganizacionVO;
import organizacion.model.vo.UserOrganoVO;
import organizacion.persistence.db.IOrganizacionDBEntity;
import organizacion.view.form.OrganizacionForm;

import se.usuarios.ServiceClient;
import util.ErrorsTag;
import util.TreeNode;
import util.TreeView;

import common.Constants;
import common.Messages;
import common.actions.ActionRedirect;
import common.actions.BaseAction;
import common.bi.ServiceRepository;
import common.model.UserInfo;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;
import common.navigation.TitlesToolBar;
import common.util.DateUtils;

import fondos.FondosConstants;

public class GestionOrganizacionAction extends BaseAction {

	public void verOrganizacionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ServiceRepository services = getServiceRepository(request);
		GestionOrganizacionBI organizacionBI = services
				.lookupGestionOrganizacionBI();
		String idOrganizacion = request.getParameter("idOrganizacion");
		OrganizacionVO organizacion = organizacionBI
				.getOrganizacionById(idOrganizacion);
		request.setAttribute(OrganizacionConstants.RESUMEN_ORGANIZACION,
				organizacion);

		removeInTemporalSession(request,
				OrganizacionConstants.LISTA_USUARIOS_KEY);
		List usuariosActualesOrgano = organizacionBI
				.getUsuariosByOrgano(idOrganizacion);
		request.setAttribute(OrganizacionConstants.LISTA_USUARIOS_KEY,
				usuariosActualesOrgano);

		List padres = new ArrayList();
		if (!IOrganizacionDBEntity.INSTITUCION.equals(String
				.valueOf(organizacion.getTipo().intValue()))) {
			OrganizacionVO organizacionPadre = organizacionBI
					.getOrganizacionById(organizacion.getIdOrgPadre());
			if (organizacionPadre != null)
				getPadres(organizacionPadre, organizacionBI, padres);
		}
		setInTemporalSession(request,
				OrganizacionConstants.JERARQUIA_ELEMENTO_ORGANIZACION, padres);

		List descendientes = organizacionBI
				.getOrganizacionesByIdPadre(organizacion.getId());
		setInTemporalSession(request,
				OrganizacionConstants.LISTA_DESCENDIENTES_KEY, descendientes);

		if (Boolean.valueOf(request.getParameter("refreshView")).booleanValue())
			request.setAttribute(OrganizacionConstants.REFRESH_VIEW_KEY,
					Boolean.TRUE);

		ClientInvocation invocation = saveCurrentInvocation(
				KeysClientsInvocations.ORGANIZACION_VER_ELEMENTO, request);
		invocation
				.setTitleNavigationToolBar(TitlesToolBar.ORGANIZACION_VER_ELEMENTO
						+ organizacion.getTipo());

		if (IOrganizacionDBEntity.HISTORICO.equals(organizacion.getEstado()
				.toString())) {
			removeInTemporalSession(request,
					OrganizacionConstants.ORGANIZACION_VIEW_NAME);
		} else {
			TreeView treeView = (TreeView) getFromTemporalSession(request,
					OrganizacionConstants.ORGANIZACION_VIEW_NAME);
			if (treeView != null) {

				// Obtener todos los padres del nodo
				String nodePath = "";
				if (padres != null) {
					ListIterator itPadres = padres.listIterator();

					if (itPadres.hasNext()) {
						OrganizacionVO org = (OrganizacionVO) itPadres.next();
						nodePath = org.getItemPath();
					}
					while (itPadres.hasNext()) {
						OrganizacionVO org = (OrganizacionVO) itPadres.next();
						nodePath += "/" + org.getItemPath();
					}
				}

				if (!"".equals(nodePath)) {
					TreeNode nodeToShow = treeView.getNode(nodePath + "/"
							+ organizacion.getItemPath());
					if (nodeToShow != null) {
						treeView.setSelectedNode(nodeToShow);
						nodeToShow.setVisible();
					}
				} else {
					TreeNode nodeToShow = treeView.getNode(organizacion
							.getItemPath());
					if (nodeToShow != null) {
						treeView.setSelectedNode(nodeToShow);
						nodeToShow.setVisible();
					}
				}

				request.setAttribute(OrganizacionConstants.REFRESH_VIEW_KEY,
						Boolean.TRUE);
			}
		}

		setReturnActionFordward(request,
				mappings.findForward("info_organizacion"));
	}

	private void getPadres(OrganizacionVO organizacionVO,
			GestionOrganizacionBI organizacionBI, List padres) {
		if (organizacionVO != null
				&& !IOrganizacionDBEntity.INSTITUCION.equals(String
						.valueOf(organizacionVO.getTipo().intValue()))) {
			if (organizacionVO.getIdOrgPadre() != null) {
				OrganizacionVO organizacionPadre = organizacionBI
						.getOrganizacionById(organizacionVO.getIdOrgPadre());
				getPadres(organizacionPadre, organizacionBI, padres);
			}
		}
		padres.add(organizacionVO);
	}

	public void editarOrganizacionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ServiceRepository services = getServiceRepository(request);
		GestionOrganizacionBI organizacionBI = services
				.lookupGestionOrganizacionBI();
		OrganizacionForm organizacionForm = (OrganizacionForm) form;
		String idOrganizacion = request.getParameter("idOrganizacion");
		OrganizacionVO organizacion = organizacionBI
				.getOrganizacionById(idOrganizacion);
		organizacionForm.set(organizacion);

		if (organizacion.getIdOrgPadre() != null) {
			OrganizacionVO padre = organizacionBI
					.getOrganizacionById(organizacion.getIdOrgPadre());
			((OrganizacionForm) form).setNombrePadre(padre.getNombre());
		}

		List padres = new ArrayList();
		if (!IOrganizacionDBEntity.INSTITUCION.equals(String
				.valueOf(organizacion.getTipo().intValue()))) {
			OrganizacionVO organizacionPadre = organizacionBI
					.getOrganizacionById(organizacion.getIdOrgPadre());
			if (organizacionPadre != null)
				getPadres(organizacionPadre, organizacionBI, padres);
		}
		setInTemporalSession(request,
				OrganizacionConstants.JERARQUIA_ELEMENTO_ORGANIZACION, padres);
		saveCurrentInvocation(
				KeysClientsInvocations.ORGANIZACION_EDITAR_ELEMENTO, request);
		setReturnActionFordward(request,
				mappings.findForward("edicion_organizacion"));
	}

	public void eliminarOrganizacionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ServiceRepository services = getServiceRepository(request);
		GestionOrganizacionBI organizacionBI = services
				.lookupGestionOrganizacionBI();
		String idOrganizacion = request.getParameter("idOrganizacion");
		OrganizacionVO organizacionVO = organizacionBI
				.getOrganizacionById(idOrganizacion);

		// Lista de organos descendientes del padre
		List descendientes = organizacionBI
				.getOrganizacionesByIdPadre(organizacionVO.getId());

		// Solo se pueden borrar aquellos organos que no tienen descendientes.
		if ((IOrganizacionDBEntity.BORRADOR.equals(organizacionVO.getEstado()
				.toString()) || IOrganizacionDBEntity.VIGENTE
				.equals(organizacionVO.getEstado().toString()))
				&& descendientes.isEmpty()) {

			// Si esta vigente borramos sus posibles usuarios asociados, si esta
			// en borrador no puede tener usuarios asociados.
			if (IOrganizacionDBEntity.VIGENTE.equals(organizacionVO.getEstado()
					.toString()))
				eliminarUsuariosOrgano(organizacionBI, organizacionVO);

			organizacionBI.eliminarOrganizacion(idOrganizacion);

			EstructuraOrganizacionTreeView treeView = (EstructuraOrganizacionTreeView) getFromTemporalSession(
					request, OrganizacionConstants.ORGANIZACION_VIEW_NAME);

			if (treeView != null) {
				TreeNode node = treeView.getSelectedNode();
				treeView.itemRemoved(node.getModelItem());
				request.setAttribute(OrganizacionConstants.REFRESH_VIEW_KEY,
						new Boolean(true));

				TreeNode parent = node.getParent();
				if (parent != null) {
					treeView.setSelectedNode(parent);
					OrganizacionVO parentVO = (OrganizacionVO) parent
							.getModelItem();
					ActionRedirect vistaOrganizacion = new ActionRedirect(
							mappings.findForward("redirect_to_info_organizacion"),
							true);
					vistaOrganizacion.addParameter("idOrganizacion",
							parentVO.getId());
					vistaOrganizacion.addParameter("refreshView", "true");
					popLastInvocation(request);
					setReturnActionFordward(request, vistaOrganizacion);

				} else {
					goBackExecuteLogic(mappings, form, request, response);
				}
			} else {
				goBackExecuteLogic(mappings, form, request, response);
			}
		} else {
			ActionErrors errors = new ActionErrors();
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							OrganizacionConstants.ERRORS_ORGANIZACION_TIENE_DESCENDIENTES,
							organizacionVO.getNombre()));
			ErrorsTag.saveErrors(request, errors);
			ClientInvocation lastInvocation = getInvocationStack(request)
					.getLastClientInvocation();
			ActionRedirect forward = new ActionRedirect(new ActionForward(
					lastInvocation.getInvocationURI()));
			forward.addParameter("refreshView", "true");
			setReturnActionFordward(request, forward);
		}
	}

	/**
	 * Metodo encargado de borrar todos los usuarios asociados a un determinado
	 * Organo, siempre y cuando este organo se encuentre en estado vigente.
	 * 
	 * @param organizacionBI
	 * @param organizacionVO
	 */
	private void eliminarUsuariosOrgano(GestionOrganizacionBI organizacionBI,
			OrganizacionVO organizacionVO) {
		organizacionBI.eliminarUsuariosOrgano(organizacionVO.getId());
	}

	/**
	 * Lista todas las organizaciones que existen creadas en la Base de Datos.
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
	public void listaOrganizacionesExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionOrganizacionBI organizacionBI = services
				.lookupGestionOrganizacionBI();
		List organizaciones = organizacionBI
				.buscarOrganizaciones(new OrganizacionVO());
		request.setAttribute("listaOrganizaciones", organizaciones);
		request.setAttribute("viewAction", "obtenerVista");
		request.setAttribute("viewName",
				OrganizacionConstants.ORGANIZACION_VIEW_NAME);
		saveCurrentInvocation(
				KeysClientsInvocations.USUARIOS_LISTA_ORGANIZACIONES, request);
		setReturnActionFordward(request,
				mappings.findForward("listadoOrganizaciones"));
	}

	public void altaInstitucionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		saveCurrentInvocation(
				KeysClientsInvocations.ORGANIZACION_CREAR_INSTITUCION, request);
		OrganizacionForm organizacionForm = (OrganizacionForm) form;
		organizacionForm.clear();
		organizacionForm.setTipo(IOrganizacionDBEntity.INSTITUCION);

		EstructuraOrganizacionTreeView treeView = (EstructuraOrganizacionTreeView) getFromTemporalSession(
				request, OrganizacionConstants.ORGANIZACION_VIEW_NAME);

		if (treeView != null) {
			treeView.setSelectedNode(null);
		}
		setReturnActionFordward(request,
				mappings.findForward("nueva_organizacion"));
	}

	public void altaOrganoExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionOrganizacionBI organizacionBI = services
				.lookupGestionOrganizacionBI();

		saveCurrentInvocation(KeysClientsInvocations.ORGANIZACION_CREAR_ORGANO,
				request);
		OrganizacionForm organizacionForm = (OrganizacionForm) form;
		organizacionForm.clear();
		String idOrgano = request.getParameter("idOrgano");
		OrganizacionVO organo = organizacionBI.getOrganizacionById(idOrgano);
		organizacionForm.setIdPadre(idOrgano);
		organizacionForm.setNombrePadre(organo.getCodigo() + " "
				+ organo.getNombre());
		organizacionForm.setTipo(IOrganizacionDBEntity.ORGANO);
		setReturnActionFordward(request,
				mappings.findForward("nueva_organizacion"));
	}

	public void altaOrganizacionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		saveCurrentInvocation(
				KeysClientsInvocations.ORGANIZACION_CREAR_ORGANIZACION, request);
		OrganizacionForm organizacionForm = (OrganizacionForm) form;
		organizacionForm.clear();
		setReturnActionFordward(request,
				mappings.findForward("nueva_organizacion"));
	}

	/**
	 * Metodo encargado de crear una nueva organización con los datos insertados
	 * en el formulario.
	 * 
	 * @param mappings
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void crearOrganizacionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ServiceRepository services = getServiceRepository(request);
		GestionOrganizacionBI organizacionBI = services
				.lookupGestionOrganizacionBI();
		OrganizacionVO organizacionVO = new OrganizacionVO();
		OrganizacionForm organizacionForm = (OrganizacionForm) form;

		ActionErrors errors = validateForm(request, organizacionForm);
		if (!errors.isEmpty()) {
			ErrorsTag.saveErrors(request, errors);
			setReturnActionFordward(request,
					mappings.findForward("nueva_organizacion"));
		} else {
			errors = validateOrganizacion(organizacionForm, organizacionBI);
			if (!errors.isEmpty()) {
				ErrorsTag.saveErrors(request, errors);
				setReturnActionFordward(request,
						mappings.findForward("nueva_organizacion"));
			} else {
				/* Si no tiene padre es de tipo institución, sino es un órgano. */
				if (StringUtils.isEmpty(organizacionForm.getIdPadre())
						|| IOrganizacionDBEntity.INSTITUCION
								.equals(organizacionForm.getTipo())) {
					organizacionForm.setTipo(IOrganizacionDBEntity.INSTITUCION);
					organizacionForm.setNivel(IOrganizacionDBEntity.NIVEL_RAIZ);
					organizacionForm.setIdPadre(null);
				} else {
					int nivelPadre = organizacionBI
							.getOrganizacionById(organizacionForm.getIdPadre())
							.getNivel().intValue();
					organizacionForm.setTipo(IOrganizacionDBEntity.ORGANO);
					organizacionForm.setNivel(String.valueOf(nivelPadre + 1));
				}

				// Se crean siempre como borradores excepto si se chequea la
				// casilla "vigente"
				if (organizacionForm.isVigente())
					organizacionForm.setEstado(IOrganizacionDBEntity.VIGENTE);
				else
					organizacionForm.setEstado(IOrganizacionDBEntity.BORRADOR);

				organizacionForm.populateGeneral(organizacionVO);

				if (!estadoPadreCorrecto(organizacionVO, organizacionBI)) {
					errors = new ActionErrors();
					errors.add(
							ActionErrors.GLOBAL_MESSAGE,
							new ActionError(
									OrganizacionConstants.ERRORS_ORGANIZACION_CREAR_ORGANO,
									organizacionVO.getNombre()));
					ErrorsTag.saveErrors(request, errors);
					setReturnActionFordward(request,
							mappings.findForward("nueva_organizacion"));
				} else {
					organizacionBI.insertarOrganizacion(organizacionVO);

					// Añadirlo al árbol
					EstructuraOrganizacionTreeView treeView = (EstructuraOrganizacionTreeView) getFromTemporalSession(
							request,
							OrganizacionConstants.ORGANIZACION_VIEW_NAME);
					if (treeView != null) {
						TreeNode nodoSeleccionado = treeView.getSelectedNode();
						OrganizacionVO elementoPadre = null;
						if (nodoSeleccionado != null) {
							elementoPadre = (OrganizacionVO) nodoSeleccionado
									.getModelItem();
						} else {
							elementoPadre = new OrganizacionVO();
						}
						TreeNode addedNode = treeView.itemAdded(elementoPadre,
								organizacionVO);
						nodoSeleccionado = treeView.setSelectedNode(addedNode);
					}

					ActionRedirect vistaOrganizacion = new ActionRedirect(
							mappings.findForward("redirect_to_info_organizacion"),
							true);
					vistaOrganizacion.addParameter("idOrganizacion",
							organizacionVO.getId());
					vistaOrganizacion.addParameter("refreshView", "true");
					popLastInvocation(request);
					setReturnActionFordward(request, vistaOrganizacion);
				}
			}
		}
	}

	/**
	 * Metodo encargado de comprobar si el organo que vamos a crear tiene un
	 * estado correcto
	 * 
	 * @param organizacionVO
	 * @param organizacionBI
	 * @return
	 */
	private boolean estadoPadreCorrecto(OrganizacionVO organizacionVO,
			GestionOrganizacionBI organizacionBI) {

		if (IOrganizacionDBEntity.ORGANO.equals(organizacionVO.getTipo()
				.toString())
				&& StringUtils.isNotEmpty(organizacionVO.getIdOrgPadre())) {
			OrganizacionVO organizacionPadre = organizacionBI
					.getOrganizacionById(organizacionVO.getIdOrgPadre());
			if (IOrganizacionDBEntity.VIGENTE.equals(organizacionVO.getEstado()
					.toString())
					&& IOrganizacionDBEntity.BORRADOR.equals(organizacionPadre
							.getEstado().toString())) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Metodo encargado de actualizar los datos introducidos para una
	 * determinada organización.
	 * 
	 * @param mappings
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void guardarOrganizacionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ServiceRepository services = getServiceRepository(request);
		GestionOrganizacionBI organizacionesService = services
				.lookupGestionOrganizacionBI();
		OrganizacionVO organizacionVO = new OrganizacionVO();
		OrganizacionForm organizacionForm = (OrganizacionForm) form;

		ActionErrors errors = validateForm(request, organizacionForm);
		if (!errors.isEmpty()) {
			ErrorsTag.saveErrors(request, errors);
			setReturnActionFordward(request,
					mappings.findForward("edicion_organizacion"));
		} else {
			errors = validateOrganizacion(organizacionForm,
					organizacionesService);
			if (!errors.isEmpty()) {
				ErrorsTag.saveErrors(request, errors);
				setReturnActionFordward(request,
						mappings.findForward("edicion_organizacion"));
			} else {
				String idOrganizacion = organizacionForm.getId();
				organizacionVO = organizacionesService
						.getOrganizacionById(idOrganizacion);

				// Se cargan los datos de la organización
				organizacionForm.populate(organizacionVO);

				// Actualiza los datos en la base de datos
				organizacionesService.actualizarOrganizacion(organizacionVO);

				// Modificarlo en el árbol
				EstructuraOrganizacionTreeView treeView = (EstructuraOrganizacionTreeView) getFromTemporalSession(
						request, OrganizacionConstants.ORGANIZACION_VIEW_NAME);
				if (treeView != null) {
					TreeNode node = treeView.getSelectedNode();
					node.setTreeModelItem(organizacionVO);
					treeView.itemModified(node.getModelItem());

					// Actualizar el árbol
					request.setAttribute(
							OrganizacionConstants.REFRESH_VIEW_KEY,
							Boolean.TRUE);
				}

				popLastInvocation(request);
				goLastClientExecuteLogic(mappings, form, request, response);
			}
		}
	}

	/**
	 * Metodo que valida el formulario para indicar si algun campo obligatorio
	 * esta vacío.
	 * 
	 * @param request
	 * @param organizacionForm
	 * @return ActionErrors errores
	 */
	private ActionErrors validateForm(HttpServletRequest request,
			OrganizacionForm organizacionForm) {
		ActionErrors errors = new ActionErrors();

		if (StringUtils.isEmpty(organizacionForm.getCodigo())) {
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(Constants.ERROR_REQUIRED, Messages
							.getString(Constants.ETIQUETA_CODIGO)));
		}

		if (StringUtils.isEmpty(organizacionForm.getNombre())) {
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(Constants.ERROR_REQUIRED, Messages
							.getString(Constants.ETIQUETA_NOMBRE)));
		}

		if (organizacionForm.isVigente()) {
			if (StringUtils.isEmpty(organizacionForm.getInicio())) {
				errors.add(
						ActionErrors.GLOBAL_MESSAGE,
						new ActionError(
								Constants.ERROR_REQUIRED,
								Messages.getString(OrganizacionConstants.ETIQUETA_INICIO_VIGENCIA)));
			} else if (!DateUtils.isDate(organizacionForm.getInicio())) {
				errors.add(
						Constants.ERROR_DATE_INVALID,
						new ActionError(Constants.ERROR_DATE_INVALID, Messages
								.getString("organizacion.org.fechainicio")));
			} else {
				Date fechaInicio = DateUtils.getDate(organizacionForm
						.getInicio());
				Date fechaActual = DateUtils.getFechaActual();
				if (DateUtils.isFechaMayor(fechaInicio, fechaActual))
					errors.add(
							Constants.ERROR_DATE_AFTER_TODAY,
							new ActionError(
									Constants.ERROR_DATE_AFTER_TODAY,
									Messages.getString("organizacion.org.fechainicio")));
			}
		}
		return errors;
	}

	/**
	 * Metodo encargado de validar si el codigo y nombre de la organización ya
	 * existen en la base de datos, ya que dichos campos han de ser únicos.
	 * 
	 * @param organizacionForm
	 * @param organizacionBI
	 * @return ActionErrors errores
	 */
	private ActionErrors validateOrganizacion(
			OrganizacionForm organizacionForm,
			GestionOrganizacionBI organizacionBI) {

		ActionErrors errors = new ActionErrors();
		OrganizacionVO organizacionVO = new OrganizacionVO();
		organizacionVO.setCodigo(organizacionForm.getCodigo());
		List organizaciones = organizacionBI
				.buscarOrganizaciones(organizacionVO);

		if (!organizaciones.isEmpty()) {
			OrganizacionVO orgVO = (OrganizacionVO) organizaciones.get(0);
			if (!orgVO.getId().equals(organizacionForm.getId())) {
				errors.add(
						ActionErrors.GLOBAL_MESSAGE,
						new ActionError(
								OrganizacionConstants.ERRORS_CODIGO_ORGANIZACION_YA_EXISTE,
								organizacionForm.getCodigo()));
			}
		}

		organizacionVO.setCodigo("");
		organizacionVO.setNombre(organizacionForm.getNombre());
		organizaciones.clear();
		organizaciones = organizacionBI.buscarOrganizaciones(organizacionVO);
		if (!organizaciones.isEmpty()) {
			OrganizacionVO orgVO = (OrganizacionVO) organizaciones.get(0);
			if (!orgVO.getId().equals(organizacionForm.getId())) {
				errors.add(
						ActionErrors.GLOBAL_MESSAGE,
						new ActionError(
								OrganizacionConstants.ERRORS_NOMBRE_ORGANIZACION_YA_EXISTE,
								organizacionForm.getNombre()));
			}
		}
		return errors;
	}

	public void verBuscadorExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		request.getSession().removeAttribute("usuario");

		saveCurrentInvocation(
				KeysClientsInvocations.USUARIO_BUSQUEDA_ORGANIZACION, request);
		setReturnActionFordward(request,
				mappings.findForward("resultado_busqueda_organizaciones"));
	}

	public void buscarOrganizacionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		saveCurrentInvocation(
				KeysClientsInvocations.USUARIO_BUSQUEDA_ORGANIZACION, request);

		OrganizacionForm organizacionForm = (OrganizacionForm) form;
		OrganizacionVO organizacionVO = new OrganizacionVO();
		organizacionVO.setCodigo(organizacionForm.getCodigo());
		organizacionVO.setNombre(organizacionForm.getNombre());
		if (StringUtils.isNotEmpty(organizacionForm.getTipo()))
			organizacionVO.setTipo(new Integer(organizacionForm.getTipo()));
		if (StringUtils.isNotEmpty(organizacionForm.getEstado()))
			organizacionVO.setEstado(new Integer(organizacionForm.getEstado()));

		UserOrganoVO userOrganoVO = new UserOrganoVO();
		userOrganoVO.setNombreUsuario(organizacionForm.getSearchTokenUsuario());

		ServiceRepository services = getServiceRepository(request);
		GestionOrganizacionBI organizacionesService = services
				.lookupGestionOrganizacionBI();
		List organizaciones = organizacionesService.buscarOrganizaciones(
				organizacionVO, userOrganoVO);
		request.setAttribute(OrganizacionConstants.LISTA_ORGANIZACIONES_KEY,
				organizaciones);

		setReturnActionFordward(request,
				mappings.findForward("resultado_busqueda_organizaciones"));
	}

	public void quitarUsuariosDeOrganoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		OrganizacionForm organizacionForm = (OrganizacionForm) form;
		ServiceRepository services = getServiceRepository(request);
		GestionOrganizacionBI organizacionBI = services
				.lookupGestionOrganizacionBI();

		String[] usuariosSeleccionados = organizacionForm
				.getUsuariosSeleccionados();
		ActionErrors errors = validateEliminarUsuarios(form);
		if (errors == null) {
			for (int i = 0; i < usuariosSeleccionados.length; i++)
				organizacionBI.eliminarUsuarioOrgano(usuariosSeleccionados[i]);
		} else
			ErrorsTag.saveErrors(request, errors);

		goLastClientExecuteLogic(mappings, form, request, response);
	}

	private ActionErrors validateEliminarUsuarios(ActionForm form) {
		OrganizacionForm organizacionForm = (OrganizacionForm) form;
		String[] usuarios = organizacionForm.getUsuariosSeleccionados();
		ActionErrors errors = null;
		if (usuarios == null || usuarios.length == 0) {
			errors = new ActionErrors();
			errors.add(
					OrganizacionConstants.NECESARIO_SELECCIONAR_UN_USUARIO,
					new ActionError(
							OrganizacionConstants.NECESARIO_SELECCIONAR_UN_USUARIO));
		}
		return errors;
	}

	public void cambiarEstadoVigenciaExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ServiceRepository services = getServiceRepository(request);
		GestionOrganizacionBI organizacionBI = services
				.lookupGestionOrganizacionBI();
		String idOrganizacion = request.getParameter("idOrganizacion");
		OrganizacionVO organizacionVO = organizacionBI
				.getOrganizacionById(idOrganizacion);

		// Pasar a estado vigente
		if ((StringUtils.isNotEmpty(organizacionVO.getIdOrgPadre()) && IOrganizacionDBEntity.VIGENTE
				.equals(organizacionBI
						.getOrganizacionById(organizacionVO.getIdOrgPadre())
						.getEstado().toString()))
				|| IOrganizacionDBEntity.INSTITUCION.equals(organizacionVO
						.getTipo().toString())) {
			organizacionVO
					.setEstado(new Integer(IOrganizacionDBEntity.VIGENTE));
			setInTemporalSession(request,
					OrganizacionConstants.ORGANIZACION_KEY, organizacionVO);
			setReturnActionFordward(request,
					mappings.findForward("insertar_fecha_vigencia"));
		} else {
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError(
					OrganizacionConstants.ERRORS_ORGANIZACION_PASO_VIGENCIA,
					organizacionVO.getNombre()));
			ErrorsTag.saveErrors(request, errors);
			goLastClientExecuteLogic(mappings, form, request, response);
		}
	}

	public void cambiarEstadoHistoricoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ServiceRepository services = getServiceRepository(request);
		GestionOrganizacionBI organizacionBI = services
				.lookupGestionOrganizacionBI();
		String idOrganizacion = request.getParameter("idOrganizacion");
		OrganizacionVO organizacionVO = organizacionBI
				.getOrganizacionById(idOrganizacion);

		List descendientes = organizacionBI
				.getOrganizacionesByIdPadre(organizacionVO.getId());

		// Pasar a estado historico
		if (StringUtils.isNotEmpty(organizacionVO.getId())
				&& descendientes.isEmpty()) {
			organizacionVO.setEstado(new Integer(
					IOrganizacionDBEntity.HISTORICO));
			setInTemporalSession(request,
					OrganizacionConstants.ORGANIZACION_KEY, organizacionVO);
			setReturnActionFordward(request,
					mappings.findForward("insertar_fecha_vigencia"));
		} else {
			ActionErrors errors = new ActionErrors();
			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError(
					OrganizacionConstants.ERRORS_ORGANIZACION_PASO_HISTORICO,
					organizacionVO.getNombre()));
			ErrorsTag.saveErrors(request, errors);
			goLastClientExecuteLogic(mappings, form, request, response);
		}
	}

	public void seleccionarNuevoPadreExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ServiceRepository services = getServiceRepository(request);
		GestionOrganizacionBI organizacionBI = services
				.lookupGestionOrganizacionBI();
		String idOrganizacion = request.getParameter("idOrganizacion");
		OrganizacionVO organizacionVO = organizacionBI
				.getOrganizacionById(idOrganizacion);
		setInTemporalSession(request, OrganizacionConstants.ORGANIZACION_KEY,
				organizacionVO);
		OrganizacionForm organizacionForm = (OrganizacionForm) form;
		organizacionForm.setId(idOrganizacion);

		List padres = new ArrayList();
		if (!IOrganizacionDBEntity.INSTITUCION.equals(String
				.valueOf(organizacionVO.getTipo().intValue()))) {
			OrganizacionVO organizacionPadre = organizacionBI
					.getOrganizacionById(organizacionVO.getIdOrgPadre());
			if (organizacionPadre != null)
				getPadres(organizacionPadre, organizacionBI, padres);
		}
		setInTemporalSession(request,
				OrganizacionConstants.JERARQUIA_ELEMENTO_ORGANIZACION, padres);

		saveCurrentInvocation(KeysClientsInvocations.ORGANIZACION_MOVER,
				request);

		setReturnActionFordward(request,
				mappings.findForward("mover_organizacion"));
	}

	public void moverOrganizacionExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ServiceRepository services = getServiceRepository(request);
		GestionOrganizacionBI organizacionBI = services
				.lookupGestionOrganizacionBI();

		OrganizacionForm organizacionForm = (OrganizacionForm) form;
		String idOrganizacion = organizacionForm.getId();
		OrganizacionVO organizacionVO = organizacionBI
				.getOrganizacionById(idOrganizacion);

		String idNuevoPadre = organizacionForm.getIdPadre();
		ActionErrors errors = new ActionErrors();

		if (StringUtils.isEmpty(idNuevoPadre))
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							Constants.ERROR_REQUIRED,
							Messages.getString(OrganizacionConstants.ETIQUETA_NOMBRE_PADRE)));
		else {
			OrganizacionVO nuevoPadre = organizacionBI
					.getOrganizacionById(idNuevoPadre);
			// No puede seleccionarse como padre a sí mismo
			if (organizacionVO.getId().equals(nuevoPadre.getId()))
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError(
						OrganizacionConstants.ERRORS_ORGANIZACION_MOVER_IGUAL,
						organizacionVO.getNombre()));
			// No puede seleccionarse como padre un organo hijo
			else if (esOrganoHijo(organizacionBI, organizacionVO,
					nuevoPadre.getId()))
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError(
						OrganizacionConstants.ERRORS_ORGANIZACION_MOVER_HIJO,
						organizacionVO.getNombre()));
			// Si es borrador, el padre solo puede ser borrador o vigente
			else if (IOrganizacionDBEntity.BORRADOR.equals(organizacionVO
					.getEstado().toString())
					&& IOrganizacionDBEntity.HISTORICO.equals(nuevoPadre
							.getEstado().toString())) {
				errors.add(
						ActionErrors.GLOBAL_MESSAGE,
						new ActionError(
								OrganizacionConstants.ERRORS_ORGANIZACION_MOVER_BORRADOR,
								organizacionVO.getNombre()));
				// Si es vigente, el padre solo puede ser vigente
			} else if (IOrganizacionDBEntity.VIGENTE.equals(organizacionVO
					.getEstado().toString())
					&& !IOrganizacionDBEntity.VIGENTE.equals(nuevoPadre
							.getEstado().toString())) {
				errors.add(
						ActionErrors.GLOBAL_MESSAGE,
						new ActionError(
								OrganizacionConstants.ERRORS_ORGANIZACION_MOVER_VIGENTE,
								organizacionVO.getNombre()));
			} else {
				organizacionVO.setIdOrgPadre(idNuevoPadre);
				organizacionVO.setNivel(new Integer(nuevoPadre.getNivel()
						.intValue() + 1));
				organizacionBI.actualizarOrganizacion(organizacionVO);

				EstructuraOrganizacionTreeView treeView = (EstructuraOrganizacionTreeView) getFromTemporalSession(
						request, OrganizacionConstants.ORGANIZACION_VIEW_NAME);

				if (treeView != null) {
					TreeNode nodeNuevoPadre = treeView.getNode(treeView
							.getTreeModel().calculateItemPath(nuevoPadre));

					TreeNode nodeFondo = treeView.getSelectedNode();
					treeView.removeNode(nodeFondo);
					nodeFondo = treeView.insertNode(nodeNuevoPadre,
							organizacionVO);
					treeView.setSelectedNode(nodeFondo);
				}
				request.setAttribute(FondosConstants.REFRESH_VIEW_KEY,
						Boolean.TRUE);

				ActionRedirect vistaOrganizacion = new ActionRedirect(
						mappings.findForward("redirect_to_info_organizacion"),
						true);
				vistaOrganizacion.addParameter("idOrganizacion",
						organizacionVO.getId());
				vistaOrganizacion.addParameter("refreshView", "true");
				popLastInvocation(request);
				setReturnActionFordward(request, vistaOrganizacion);
			}
		}

		if (!errors.isEmpty()) {
			ErrorsTag.saveErrors(request, errors);
			List padres = new ArrayList();
			if (!IOrganizacionDBEntity.INSTITUCION.equals(String
					.valueOf(organizacionVO.getTipo().intValue()))) {
				OrganizacionVO organizacionPadre = organizacionBI
						.getOrganizacionById(organizacionVO.getIdOrgPadre());
				if (organizacionPadre != null)
					getPadres(organizacionPadre, organizacionBI, padres);
			}
			setInTemporalSession(request,
					OrganizacionConstants.JERARQUIA_ELEMENTO_ORGANIZACION,
					padres);
			setReturnActionFordward(request,
					mappings.findForward("mover_organizacion"));
		}
	}

	private boolean esOrganoHijo(GestionOrganizacionBI organizacionBI,
			OrganizacionVO organizacionVO, String idNuevoPadre) {
		List hijos = organizacionBI.getOrganizacionesByIdPadre(organizacionVO
				.getId());
		for (Iterator iter = hijos.iterator(); iter.hasNext();) {
			OrganizacionVO organo = (OrganizacionVO) iter.next();
			if (organo.getId().equals(idNuevoPadre))
				return true;
		}
		return false;
	}

	public void verBuscadorUsuarioExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ActionRedirect buscadorUsuario = new ActionRedirect(
				mappings.findForward("redirect_to_buscador_usuario"), true);
		buscadorUsuario.addParameter("origen", "true");
		setReturnActionFordward(request, buscadorUsuario);
	}

	public void verBuscadorOrgExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		OrganizacionForm organizacionForm = (OrganizacionForm) form;
		List usuariosEncontrados = (List) getFromTemporalSession(request,
				OrganizacionConstants.LISTA_USUARIOS_EXTERNOS);
		String usuarioSeleccionado = organizacionForm
				.getIdUsrSisExtGestorSeleccionado();
		UserInfo userInfo = (UserInfo) CollectionUtils.find(
				usuariosEncontrados, new FinderUserInfo(usuarioSeleccionado));
		organizacionForm.setSearchTokenUsuario(userInfo.getName());

		request.getSession().removeAttribute("usuario");

		saveCurrentInvocation(
				KeysClientsInvocations.USUARIO_BUSQUEDA_ORGANIZACION, request);
		setReturnActionFordward(request,
				mappings.findForward("resultado_busqueda_organizaciones"));
	}

	private class FinderUserInfo implements Predicate {
		String externalUserId = null;

		FinderUserInfo(String externalUserId) {
			this.externalUserId = externalUserId;
		}

		public boolean evaluate(Object arg0) {
			UserInfo userToEval = ((UserInfo) arg0);
			boolean equal = false;

			if (userToEval.getExternalUserId() != null)
				equal = userToEval.getExternalUserId().equalsIgnoreCase(
						externalUserId);
			else
				equal = true;

			return equal;
		}
	}
}