package common.actions;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.displaytag.properties.SortOrderEnum;
import organizacion.model.bi.GestionOrganizacionBI;

import salas.SalasConsultaConstants;
import salas.model.GestionSalasConsultaBI;
import se.instituciones.GestorOrganismos;
import se.instituciones.GestorOrganismosFactory;
import se.instituciones.exceptions.GestorOrganismosException;
import se.usuarios.AppPermissions;
import se.usuarios.AppUser;
import se.usuarios.AppUserRI;
import se.usuarios.AppUserRIFactory;
import se.usuarios.ServiceClient;
import solicitudes.model.ConsultaUnidadesDocumentalesBI;
import util.ErrorsTag;
import util.MessagesTag;
import util.MimeTypesUtil;
import util.TreeView;
import xml.config.AccionBusqueda;
import xml.config.Busqueda;
import xml.config.ConfiguracionArchivoManager;

import common.ConfigConstants;
import common.Constants;
import common.Messages;
import common.MultiEntityConstants;
import common.bi.GestionArchivosBI;
import common.bi.GestionAuditoriaBI;
import common.bi.GestionConsultasBI;
import common.bi.GestionControlUsuariosBI;
import common.bi.GestionCuadroClasificacionBI;
import common.bi.GestionDescripcionBI;
import common.bi.GestionDocumentosElectronicosBI;
import common.bi.GestionDocumentosVitalesBI;
import common.bi.GestionEliminacionBI;
import common.bi.GestionFondosBI;
import common.bi.GestionFraccionSerieBI;
import common.bi.GestionPrestamosBI;
import common.bi.GestionPrevisionesBI;
import common.bi.GestionRelacionesEACRBI;
import common.bi.GestionRelacionesEntregaBI;
import common.bi.GestionSeriesBI;
import common.bi.GestionSessionBI;
import common.bi.GestionSistemaBI;
import common.bi.GestionUnidadDocumentalBI;
import common.bi.GestionValoracionBI;
import common.bi.ServiceRepository;
import common.definitions.ArchivoModules;
import common.definitions.ArchivoTables;
import common.exceptions.ActionNotAllowedException;
import common.exceptions.CheckedArchivoException;
import common.exceptions.InvalidSessionException;
import common.exceptions.StrutsExceptionFormatter;
import common.exceptions.TooManyResultsException;
import common.navigation.ClientInvocation;
import common.navigation.InvocationStack;
import common.navigation.KeysClientsInvocations;
import common.navigation.TitlesToolBar;
import common.pagination.PageInfo;
import common.startup.ProfileLogLevel;
import common.util.ClientInvocationUtils;
import common.util.DisplayTagUtils;
import common.util.ListUtils;
import common.util.ResponseUtil;
import common.util.StringUtils;

import configuracion.bi.GestionInfoSistemaBI;
import deposito.DepositoConstants;
import deposito.exceptions.TipoElementoDepositoException;
import deposito.model.GestorEstructuraDepositoBI;
import descripcion.vos.CampoDatoVO;
import descripcion.vos.CampoTablaVO;
import descripcion.vos.ElementoCFVO;
import docelectronicos.DocumentosConstants;
import es.archigest.framework.core.exceptions.ArchigestException;
import es.archigest.framework.web.action.ArchigestActionProcessException;
import es.archigest.framework.web.action.ArchigestDispatchAction;
import export.ExportExcelReportImpl;
import export.ExportPDFReportImpl;
import export.ExportReport;
import export.ExportTxtReportImpl;
import fondos.FondosConstants;
import fondos.exceptions.PadreSinAccesoPermitidoException;
import fondos.model.TipoNivelCF;
import fondos.utils.AccionesBusquedaHelper;
import fondos.vos.AccionVO;
import fondos.vos.TablaTemporalFondosVO;
import gcontrol.model.ArchivosException;
import gcontrol.vos.ArchivoVO;
import gcontrol.vos.GrupoVO;

/**
 * Action del que heredan todos los action de la aplicación, y que define un
 * conjunto de métodos base utilizados en la capa de vista de la aplicación
 */
public abstract class BaseAction extends ArchigestDispatchAction {

	protected static final Logger PROFILE_LOGGER = Logger.getLogger("PROFILE");

	protected final static String RETURN_FORWARD_KEY = "RETURN_FORWARD_KEY";

	protected Properties getParams(HttpServletRequest request) {
		ServiceClient serviceClient = getServiceClient(request);

		Properties params = new Properties();
		if ((serviceClient != null)
				&& (StringUtils.isNotEmpty(serviceClient.getEntity()))) {

			params.put(MultiEntityConstants.ENTITY_PARAM,
					serviceClient.getEntity());
		}

		return params;
	}

	protected void preProcess(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// Usuario de la aplicación
		AppUser user = getAppUser(request);

		GestionSessionBI sessionService = getGestionSessionBI(request);
		// Obtenemos el ticket del usuario
		String ticket = request.getSession().getId();

		if (user != null && !sessionService.isAlive(ticket)) {
			request.getSession().invalidate();

			// Metemos un mensaje de sesion invalidada
			ActionErrors errors = new ActionErrors();
			errors.add(Constants.ERROR_GENERAL_MESSAGE, new ActionError(
					Constants.LOGIN_USER_INVALIDATED));

			// Añadir los errores producidos
			ErrorsTag.saveErrors(request, errors);
			// Lanzamos la exception
			throw new InvalidSessionException(Messages.getString(
					Constants.LOGIN_USER_INVALIDATED, request.getLocale()));
		}
	}

	/**
	 * En caso de que se produzca un error si no existiese una redirección
	 * previa se llamará al tratamiento de error de ArchigestDispatchAction
	 */
	protected ActionForward findFailure(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp) {
		ActionForward ret = getReturnActionFordward(req);
		if (ret == null)
			ret = mapping.findForward("globalerror");
		return ret;
	}

	/**
	 * En caso de que no se produzca ningún tipo de error si no existiese una
	 * redirección previa se llamará al tratamiento de exito de la clase base
	 */
	protected ActionForward findSuccess(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse resp) {
		ActionForward ret = getReturnActionFordward(req);
		if (ret == null)
			return super.findSuccess(mapping, form, req, resp);
		return ret;
	}

	/**
	 * Establece una actionForward de retorno q mas tarde sera tratada por el
	 * find q corresponda: finsuccess en caso de q todo la eejecucion haya sido
	 * correcta findfailure en caso de q se hay producido un error
	 *
	 * @param request
	 * @param actionForward
	 */
	protected void setReturnActionFordward(HttpServletRequest request,
			ActionForward actionForward) {
		request.setAttribute(RETURN_FORWARD_KEY, actionForward);
	}

	protected ActionForward getReturnActionFordward(HttpServletRequest request) {
		return (ActionForward) request.getAttribute(RETURN_FORWARD_KEY);
	}

	/**
	 * Obtenemos un objeto de la sesión por su nombre.
	 */
	protected Object getSessionObject(HttpServletRequest req, String attrName) {
		Object sessionObj = null;
		HttpSession session = req.getSession(false);

		if (session != null) {
			sessionObj = session.getAttribute(attrName);
		}
		return sessionObj;
	}

	protected void executeLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		/* String methodKey = */obtainMethodKey(mapping, request);
		if (PROFILE_LOGGER.isEnabledFor(ProfileLogLevel.BEGIN_ACTION)) {
			AppUser appUser = getAppUser(request);
			StringBuffer logMessage = new StringBuffer()
					.append(System.currentTimeMillis()).append(" [")
					.append(appUser.getNombreCompleto()).append("]");
			PROFILE_LOGGER.log(ProfileLogLevel.BEGIN_ACTION,
					logMessage.toString());
		}
		super.executeLogic(mapping, form, request, response);

		if (PROFILE_LOGGER.isEnabledFor(ProfileLogLevel.BEGIN_ACTION))
			PROFILE_LOGGER.log(ProfileLogLevel.END_ACTION,
					String.valueOf(System.currentTimeMillis()));

		// checkeos de memoria
		String gc = ((String) request.getParameter("garbageCollector"));
		if ((!StringUtils.isEmpty(gc)) && gc.equals("true")) {
			callGarbageCollector();
		}

		boolean debug = ConfigConstants.getInstance().getDebug();

		if (debug) {
			Runtime runtime = Runtime.getRuntime();
			long memoriaMax = runtime.maxMemory();
			long memoriaReservada = runtime.totalMemory();
			long memoriaReservadaLibre = runtime.freeMemory();

			request.setAttribute("memMax", new Long(memoriaMax / 1024));
			request.setAttribute("memReservada", new Long(
					memoriaReservada / 1024));
			request.setAttribute("memReservadaLibre", new Long(
					memoriaReservadaLibre / 1024));
			request.setAttribute("memReservadaUsada", new Long(
					(memoriaReservada - memoriaReservadaLibre) / 1024));
			String salida = "";
			Enumeration sessionElements = request.getSession()
					.getAttributeNames();
			int nElems = 0;
			for (; sessionElements.hasMoreElements();) {
				String name = (String) sessionElements.nextElement();
				nElems++;
				Object obj = request.getSession().getAttribute(name);
				salida += name + " (" + obj.getClass() + ") = ";
				if (obj instanceof List) {
					List lista = ((List) obj);
					salida += lista.size();
				} else {
					salida += request.getSession().getAttribute(name)
							.toString().length();
				}
				salida += /* " tam="+SizeOf.sizeof(obj)+" bytes"+ */"\n\n";
			}
			request.setAttribute("nObjetosEnSession", new Integer(nElems));
			request.setAttribute("objects", salida);
		}
	}

	private void callGarbageCollector() {
		System.gc();
	}

	/**
	 * Guarda el mensaje de error asociado a una excepcion en la lista de
	 * errores El error sera visto mediante la etiqueta archivo:errors en la jsp
	 * a presentar al usuario
	 */
	public ActionErrors guardarError(HttpServletRequest request,
			TipoElementoDepositoException tede) {
		ActionErrors errores = obtenerErrores(request, true);
		errores.add(ActionErrors.GLOBAL_ERROR, StrutsExceptionFormatter
				.getInstance().formatException(tede, request.getLocale()));
		ErrorsTag.saveErrors(request, errores);
		return errores;
	}

	/**
	 * Guarda el mensaje de error asociado a una excepcion en la lista de
	 * errores El error sera visto mediante la etiqueta archivo:errors en la jsp
	 * a presentar al usuario
	 */
	public ActionErrors guardarError(HttpServletRequest request,
			ActionNotAllowedException anae) {
		ActionErrors errores = obtenerErrores(request, true);
		errores.add(ActionErrors.GLOBAL_ERROR, StrutsExceptionFormatter
				.getInstance().formatException(anae));
		ErrorsTag.saveErrors(request, errores);
		return errores;
	}

	public ActionErrors guardarError(HttpServletRequest request,
			CheckedArchivoException cae) {
		ActionErrors errores = obtenerErrores(request, true);
		errores.add(ActionErrors.GLOBAL_ERROR, StrutsExceptionFormatter
				.getInstance().formatException(cae));
		ErrorsTag.saveErrors(request, errores);
		return errores;
	}

	public ActionErrors guardarError(HttpServletRequest request,
			ArchivosException ae) {
		ActionErrors errores = obtenerErrores(request, true);
		errores.add(ActionErrors.GLOBAL_ERROR, StrutsExceptionFormatter
				.getInstance().formatException(ae));
		ErrorsTag.saveErrors(request, errores);
		return errores;
	}

	public void guardarError(HttpServletRequest request,
			TooManyResultsException e) {
		// Añadir los errores al request
		obtenerErrores(request, true).add(
				ActionErrors.GLOBAL_MESSAGE,
				new ActionError(Constants.ERROR_TOO_MANY_RESULTS, new Object[] {
						new Integer(e.getCount()),
						new Integer(e.getMaxNumResults()) }));

	}

	protected ActionErrors obtenerErrores(HttpServletRequest request,
			boolean create) {
		ActionErrors errores = (ActionErrors) request.getSession()
				.getAttribute(ErrorsTag.KEY_ERRORS);
		if (errores == null && create)
			request.getSession().setAttribute(ErrorsTag.KEY_ERRORS,
					errores = new ActionErrors());
		return errores;
	}

	/**
	 * Captura de y muestra de una excepcion
	 */
	protected void catchException(Exception exception, ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		super.catchException(exception, mapping, form, request, response);
		Throwable error = ((ArchigestActionProcessException) exception)
				.getNested();

		request.setAttribute(Constants.EXCEPCION_KEY, error);

		setReturnActionFordward(request, mapping.findForward("globalerror"));
	}

	protected InvocationStack getInvocationStack(HttpServletRequest request) {
		InvocationStack invStack = (InvocationStack) request.getSession()
				.getAttribute(Constants.INVOCATION_STACK_KEY);
		if (invStack == null) {
			Map parameters = null;
			ClientInvocation cli = ClientInvocation.getInstance(
					KeysClientsInvocations.KEY_HOME, request.getContextPath(),
					Constants.HOME_URL, parameters, true);

			cli.setTitleNavigationToolBar(TitlesToolBar
					.getATitleForClientInvocation(cli));
			invStack = new InvocationStack(cli, request);
			request.getSession().setAttribute(Constants.INVOCATION_STACK_KEY,
					invStack);
		}
		return invStack;
	}

	/**
	 * Borrado de la pila de invocaciones
	 *
	 * @param request
	 */
	protected void resetInvocationStack(HttpServletRequest request) {
		getInvocationStack(request).reset(request);
	}

	public final String MAPPING_ACTION_INSTANCE = "org.apache.struts.action.mapping.instance";

	protected ClientInvocation saveCurrentInvocation(String keyNode,
			HttpServletRequest request) {
		return saveComunCurrentInvocation(keyNode, request, null, null, null);
	}

	protected ClientInvocation saveCurrentInvocation(String keyNode,
			HttpServletRequest request, TreeView treeView, String viewName,
			String viewAction) {
		return saveComunCurrentInvocation(keyNode, request, treeView, viewName,
				viewAction);
	}

	private void setRefreshReloadParameters(HttpServletRequest request,
			boolean forceTreeViewRefresh) {
		String reloadTreeView = request
				.getParameter(Constants.TREE_VIEW_RELOAD);
		if (reloadTreeView != null) {
			request.setAttribute(Constants.TREE_VIEW_RELOAD, Boolean.TRUE);
		}

		if (forceTreeViewRefresh
				|| Boolean.valueOf(request.getParameter("refreshView"))
						.booleanValue()) {
			request.setAttribute(DepositoConstants.REFRESH_VIEW_KEY,
					Boolean.TRUE);
			request.setAttribute(FondosConstants.REFRESH_VIEW_KEY, Boolean.TRUE);
			request.setAttribute(SalasConsultaConstants.REFRESH_VIEW_KEY,
					Boolean.TRUE);
		}
	}

	protected ClientInvocation saveCurrentTreeViewInvocationAndRefresh(
			String keyNode, HttpServletRequest request, String treeViewName) {
		return saveCurrentTreeViewInvocationAndRefresh(keyNode, request,
				treeViewName, false);
	}

	protected ClientInvocation saveCurrentTreeViewInvocationAndRefresh(
			String keyNode, HttpServletRequest request, String treeViewName,
			boolean forceTreeViewRefresh) {
		TreeView treeView = (TreeView) getFromTemporalSession(request,
				treeViewName);
		return saveCurrentTreeViewInvocationAndRefresh(keyNode, request,
				treeView, forceTreeViewRefresh);
	}

	protected ClientInvocation saveCurrentTreeViewInvocationAndRefresh(
			String keyNode, HttpServletRequest request, TreeView treeView) {
		return saveCurrentTreeViewInvocationAndRefresh(keyNode, request,
				treeView, false);
	}

	protected ClientInvocation saveCurrentTreeViewInvocationAndRefresh(
			String keyNode, HttpServletRequest request, TreeView treeView,
			boolean forceTreeViewRefresh) {
		setRefreshReloadParameters(request, forceTreeViewRefresh);

		String viewAction = (String) request.getAttribute("viewAction");
		String viewName = (String) request.getAttribute("viewName");

		return saveCurrentInvocation(keyNode, request, treeView, viewAction,
				viewName);
	}

	private ClientInvocation saveComunCurrentInvocation(String keyNode,
			HttpServletRequest request, TreeView treeView, String viewName,
			String viewAction) {
		// HashMap parameters = getMapParamsFromQuery(request.getQueryString());
		Map parameters = request.getParameterMap();

		ClientInvocation cli = ClientInvocation.getInstance(keyNode, request,
				parameters, false, treeView, viewName, viewAction);
		ActionMapping mapping = ((ActionMapping) request
				.getAttribute(MAPPING_ACTION_INSTANCE));
		if (mapping != null) {
			String formName = mapping.getName();
			if (!StringUtils.isEmpty(formName)) {
				cli.setFormName(formName);
			}
		}
		// cli.setInTemporalSession(request, nameObj, obj)

		if (!PageInfo.checkExportingList(request))
			saveInStack(cli, request);

		return cli;
	}

	// protected ClientInvocation saveInvocation(String keyNode,
	// HttpServletRequest request, String actionPath) {
	// return saveInvocation(keyNode, request, actionPath, null);
	// }

	// private String getURL(String totalPath) {
	// String[] tokensPath = totalPath.split("\\?");
	// if (tokensPath.length==2){
	// return tokensPath[0];
	// }else{
	// return totalPath;
	// }
	// }

	// private HashMap getMapParamsFromQuery(String query){
	// HashMap map = new FastHashMap();
	//
	// if (query!=null) {
	// String[] params = query.split("&");
	// for (int i = 0; i < params.length; i++) {
	// String[] tokensFromEqual = params[i].split("=");
	// if (tokensFromEqual.length == 2) {
	// map.put(tokensFromEqual[0], tokensFromEqual[1]);
	// }
	// }
	// }
	//
	// return map;
	// }
	//
	// private HashMap getParams(String totalPath) {
	// String[] tokensPath = totalPath.split("\\?");
	// if (tokensPath.length==2){
	// return getMapParamsFromQuery(tokensPath[1]);
	// }else{
	// return null;
	// }
	// }

	private void saveInStack(ClientInvocation cli, HttpServletRequest request) {
		cli.setTitleNavigationToolBar(TitlesToolBar
				.getATitleForClientInvocation(cli));
		getInvocationStack(request).saveClientInvocation(cli, request);
	}

	protected ClientInvocation popLastInvocation(HttpServletRequest request) {
		return getInvocationStack(request).popLastClientInvocation(request);
	}

	/**
	 * Metodo a ejecutar cuando desde la interfaz se realize un cancel.
	 */
	public void goBackExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		InvocationStack invocationStack = getInvocationStack(request);
		ClientInvocation lastClientReturnPoint = invocationStack
				.goBackClientInvocation(request);

		if ((lastClientReturnPoint != null)
				&& (lastClientReturnPoint == invocationStack.getHome())) {
			String uri = "/action/homepage?method=loadBandeja";
			setReturnActionFordward(request, new ActionForward(uri, true));
		} else {
			ActionRedirect redirect = new ActionRedirect(new ActionForward(
					lastClientReturnPoint.getInvocationURI(), true), true);

			if (lastClientReturnPoint.getTreeView() != null) {
				redirect.addParameter(Constants.TREE_VIEW_RELOAD, Boolean.TRUE);
			}
			setReturnActionFordward(request, redirect);
		}

	}

	/**
	 * Metodo a ejecutar cuando desde la interfaz se realize un cancel desde un
	 * resultado de busqueda.
	 */
	public void goBackBusquedaExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		Boolean showInformeBusqueda = (Boolean) getFromTemporalSession(request, Constants.SHOW_INFORME_BUSQUEDA_BUTTON);

		InvocationStack invocationStack = getInvocationStack(request);
		ClientInvocation lastClientReturnPoint = invocationStack
				.goBackClientInvocation(request);

		if(Boolean.TRUE.equals(showInformeBusqueda)){
			setInTemporalSession(request, Constants.SHOW_INFORME_BUSQUEDA_BUTTON, Boolean.TRUE);
		}

		if ((lastClientReturnPoint != null)
				&& (lastClientReturnPoint == invocationStack.getHome())) {
			String uri = "/action/homepage?method=loadBandeja";
			uri = ClientInvocationUtils.getInvocationURIWithoutEmptyParameters(
					uri, invocationStack.getHome().getAddedParams());
			setReturnActionFordward(request, new ActionForward(uri, true));
		} else {
			ActionRedirect redirect = new ActionRedirect(new ActionForward(
					lastClientReturnPoint.getInvocationURI(), true), true);
			if (lastClientReturnPoint.getTreeView() != null) {
				redirect.addParameter(Constants.TREE_VIEW_RELOAD, Boolean.TRUE);
			}
			setReturnActionFordward(request, redirect);
		}
	}

	/**
	 * Metodo a ejecutar cuando desde la interfaz se realize un cancel.
	 */
	public void goBackTwiceExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		InvocationStack invocationStack = getInvocationStack(request);
		ClientInvocation lastClientInvocation = invocationStack
				.goBackTwiceClientInvocation(request);

		if ((lastClientInvocation != null)
				&& (lastClientInvocation == invocationStack.getHome())) {
			setReturnActionFordward(request, new ActionForward(
					"/action/homepage?method=loadBandeja", true));
		} else {
			ActionRedirect redirect = new ActionRedirect(new ActionForward(
					lastClientInvocation.getInvocationURI(), true), true);
			if (lastClientInvocation.getTreeView() != null) {
				redirect.addParameter(Constants.TREE_VIEW_RELOAD, Boolean.TRUE);
			}
			setReturnActionFordward(request, redirect);
		}
	}

	/**
	 * Metodo a ejecutar para volver a la ultima uri metida en la pila
	 */
	public void goLastClientExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		InvocationStack invocationStack = getInvocationStack(request);
		ClientInvocation lastClientInvocation = popLastInvocation(request);

		if ((lastClientInvocation != null)
				&& (lastClientInvocation == invocationStack.getHome())) {
			setReturnActionFordward(request, new ActionForward(
					"/action/homepage?method=loadBandeja", true));
		} else {
			ActionRedirect redirect = new ActionRedirect(new ActionForward(
					lastClientInvocation.getInvocationURI(), true), true);
			if (lastClientInvocation.getTreeView() != null) {
				redirect.addParameter(Constants.TREE_VIEW_RELOAD, Boolean.TRUE);
			}
			setReturnActionFordward(request, redirect);
		}
	}

	/**
	 * Metodo a ejecutar cuando desde a interfaz se desee volver a una
	 * invocacion marcada como punto de retorno.
	 *
	 * @param mappings
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void goReturnPointExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		InvocationStack invocationStack = getInvocationStack(request);
		ClientInvocation lastClientReturnPoint = invocationStack
				.goToReturnPoint(request);

		if ((lastClientReturnPoint != null)
				&& (lastClientReturnPoint == invocationStack.getHome())) {
			setReturnActionFordward(request, new ActionForward(
					"/action/homepage?method=loadBandeja", true));
		} else {
			ActionRedirect redirect = new ActionRedirect(new ActionForward(
					lastClientReturnPoint.getInvocationURI(), true), true);
			if (lastClientReturnPoint.getTreeView() != null) {
				redirect.addParameter(Constants.TREE_VIEW_RELOAD, Boolean.TRUE);
			}
			setReturnActionFordward(request, redirect);
		}
	}

	/**
	 * Metodo que retorna una forward con redirect a true, y un atributo de
	 * nombre pMethodName y valor nombre de metodo methodName. El nombre del
	 * action sera identico al de la ultima peticion realizada.
	 *
	 * @param request
	 * @param pMethodName
	 * @param methodName
	 * @return
	 */
	public ActionForward redirectForwardMethod(HttpServletRequest request,
			String pMethodName, String methodName) {
		ActionForward ret = new ActionForward();
		ret.setPath(request.getServletPath() + request.getPathInfo() + "?"
				+ pMethodName + "=" + methodName);
		ret.setRedirect(true);
		return ret;

	}

	/**
	 * Metodo que retorna una forward con redirect a true, y un atributo de
	 * nombre pMethodName y valor nombre de metodo methodName. El nombre del
	 * action sera indicado por nameAction.
	 *
	 * @param request
	 * @param nameAction
	 * @param pMethodName
	 * @param methodName
	 * @return
	 */
	public ActionForward redirectForwardMethod(HttpServletRequest request,
			String nameAction, String pMethodName, String methodName) {
		ActionForward ret = new ActionForward();
		ret.setPath(request.getServletPath() + nameAction + "?" + pMethodName
				+ "=" + methodName);
		ret.setRedirect(true);
		return ret;

	}

	/**
	 * Coloca sobre el objeto temporal session un objeto
	 *
	 * @param request
	 * @param nameObject
	 * @param value
	 */
	public void setInTemporalSession(HttpServletRequest request,
			String nameObject, Object value) {
		getInvocationStack(request).setInSession(request, nameObject, value);
	}

	/**
	 * Obtiene de session temporal un objeto
	 *
	 * @param request
	 * @param nameObject
	 * @return
	 */
	public Object getFromTemporalSession(HttpServletRequest request,
			String nameObject) {
		return getInvocationStack(request).getFromSession(request, nameObject);
	}

	/**
	 * Borra la session temporal
	 *
	 * @param request
	 */
	public void cleanTemporalSession(HttpServletRequest request) {
		getInvocationStack(request).cleanSession(request);
	}

	public void removeInTemporalSession(HttpServletRequest request,
			String nameAttribute) {
		getInvocationStack(request).removeInTemporalSession(request,
				nameAttribute);
	}

	public AppUser getAppUser(HttpServletRequest request) {
		return (AppUser) request.getSession()
				.getAttribute(Constants.USUARIOKEY);
	}

	public ServiceClient getServiceClient(HttpServletRequest request) {
		AppUser appUser = getAppUser(request);
		return (appUser != null ? ServiceClient.create(appUser) : null);
	}

	public GestionSeriesBI getGestionSeriesBI(HttpServletRequest request) {
		return ServiceRepository.getInstance(
				ServiceClient.create(getAppUser(request)))
				.lookupGestionSeriesBI();
	}

	public GestionFondosBI getGestionFondosBI(HttpServletRequest request) {
		return ServiceRepository.getInstance(
				ServiceClient.create(getAppUser(request)))
				.lookupGestionFondosBI();
	}

	public GestionAuditoriaBI getGestionAuditoriaBI(HttpServletRequest request) {
		return ServiceRepository.getInstance(
				ServiceClient.create(getAppUser(request)))
				.lookupGestionAuditoriaBI();
	}

	public GestionPrevisionesBI getGestionPrevisionesBI(
			HttpServletRequest request) {
		return ServiceRepository.getInstance(
				ServiceClient.create(getAppUser(request)))
				.lookupGestionPrevisionesBI();
	}

	public GestionRelacionesEntregaBI getGestionRelacionesBI(
			HttpServletRequest request) {
		return ServiceRepository.getInstance(
				ServiceClient.create(getAppUser(request)))
				.lookupGestionRelacionesBI();
	}

	public GestionRelacionesEACRBI getGestionRelacionesEACRBI(
			HttpServletRequest request) {
		return ServiceRepository.getInstance(
				ServiceClient.create(getAppUser(request)))
				.lookupGestionRelacionesEACRBI();
	}

	public GestionControlUsuariosBI getGestionControlUsuarios(
			HttpServletRequest request) {
		return ServiceRepository.getInstance(
				ServiceClient.create(getAppUser(request)))
				.lookupGestionControlUsuariosBI();
	}

	public GestionOrganizacionBI getGestionOrganizacionBI(
			HttpServletRequest request) {
		return ServiceRepository.getInstance(
				ServiceClient.create(getAppUser(request)))
				.lookupGestionOrganizacionBI();
	}

	public AppUserRI getAppUserRI(HttpServletRequest request) {
		return AppUserRIFactory.createAppUserRI();
	}

	public GestionCuadroClasificacionBI getGestionCuadroClasificacionBI(
			HttpServletRequest request) {
		return ServiceRepository.getInstance(
				ServiceClient.create(getAppUser(request)))
				.lookupGestionCuadroClasificacionBI();
	}

	public GestorEstructuraDepositoBI getGestorEstructuraDepositoBI(
			HttpServletRequest request) {
		return ServiceRepository.getInstance(
				ServiceClient.create(getAppUser(request)))
				.lookupGestorEstructuraDepositoBI();
	}

	public GestionUnidadDocumentalBI getGestionUnidadDocumentalBI(
			HttpServletRequest request) {
		return ServiceRepository.getInstance(
				ServiceClient.create(getAppUser(request)))
				.lookupGestionUnidadDocumentalBI();

	}

	public GestionFraccionSerieBI getGestionFraccionSerieBI(
			HttpServletRequest request) {
		return ServiceRepository.getInstance(
				ServiceClient.create(getAppUser(request)))
				.lookupGestionFraccionSerieBI();

	}

	public ServiceRepository getServiceRepository(HttpServletRequest request) {
		return ServiceRepository.getInstance(ServiceClient
				.create(getAppUser(request)));

	}

	public GestionDescripcionBI getGestionDescripcionBI(
			HttpServletRequest request) {
		return ServiceRepository.getInstance(getServiceClient(request))
				.lookupGestionDescripcionBI();
	}

	public GestionPrestamosBI getGestionPrestamosBI(HttpServletRequest request) {
		return ServiceRepository.getInstance(getServiceClient(request))
				.lookupGestionPrestamosBI();
	}

	public GestionConsultasBI getGestionConsultasBI(HttpServletRequest request) {
		return ServiceRepository.getInstance(getServiceClient(request))
				.lookupGestionConsultasBI();
	}

	public GestionArchivosBI getGestionArchivosBI(HttpServletRequest request) {
		return ServiceRepository.getInstance(getServiceClient(request))
				.lookupGestionArchivosBI();
	}

	public ConsultaUnidadesDocumentalesBI getConsultaUnidadesDocumentalesBI(
			HttpServletRequest request) {
		return ServiceRepository.getInstance(getServiceClient(request))
				.lookupConsultaUnidadesDocumentalesBI();
	}

	public GestionSistemaBI getGestionSistemaBI(HttpServletRequest request) {
		return ServiceRepository.getInstance(getServiceClient(request))
				.lookupGestionSistemaBI();
	}

	public GestionDocumentosElectronicosBI getGestionDocumentosElectronicosBI(
			HttpServletRequest request) {
		return ServiceRepository.getInstance(getServiceClient(request))
				.lookupGestionDocumentosElectronicosBI();
	}

	public GestionDocumentosVitalesBI getGestionDocumentosVitalesBI(
			HttpServletRequest request) {
		return ServiceRepository.getInstance(getServiceClient(request))
				.lookupGestionDocumentosVitalesBI();
	}

	public GestorOrganismos getGestorOrganismosSE(HttpServletRequest request)
			throws GestorOrganismosException {
		// Obtener información del usuario conectado
		AppUser appUser = getAppUser(request);

		// Obtener la entidad para el usuario conectado
		Properties params = null;

		if ((appUser != null) && (StringUtils.isNotEmpty(appUser.getEntity()))) {
			params = new Properties();
			params.put(MultiEntityConstants.ENTITY_PARAM, appUser.getEntity());
		}

		return GestorOrganismosFactory.getConnectorByUserType(
				appUser.getUserType(), params);
	}

	public GestionValoracionBI getGestionValoracionBI(HttpServletRequest request) {
		return ServiceRepository.getInstance(
				ServiceClient.create(getAppUser(request)))
				.lookupGestionValoracionBI();
	}

	public GestionEliminacionBI getGestionEliminacionBI(
			HttpServletRequest request) {
		return ServiceRepository.getInstance(
				ServiceClient.create(getAppUser(request)))
				.lookupGestionEliminacionBI();
	}

	public GestionSessionBI getGestionSessionBI(HttpServletRequest request) {
		return ServiceRepository.getInstance(
				ServiceClient.create(getAppUser(request)))
				.lookupGestionSessionBI();
	}

	public GestorEstructuraDepositoBI getGestionDespositoBI(
			HttpServletRequest request) {
		return ServiceRepository.getInstance(
				ServiceClient.create(getAppUser(request)))
				.lookupGestorEstructuraDepositoBI();
	}

	public GestionSalasConsultaBI getGestionSalasBI(HttpServletRequest request) {
		return ServiceRepository.getInstance(
				ServiceClient.create(getAppUser(request)))
				.lookupGestionSalasBI();
	}

	/**
	 * Muestra el contenido de un fichero.
	 *
	 * @param response
	 *            {@link HttpServletResponse}
	 * @param fileName
	 *            Nombre del fichero.
	 * @param content
	 *            Contenido del fichero
	 * @throws IOException
	 *             si ocurre algún error.
	 */
	protected void download(HttpServletResponse response, String fileName,
			byte[] content) throws IOException {
		response.setContentType("application/octet-stream");
		response.setHeader("Content-disposition", new StringBuffer(
				"attachment; filename=\"").append(fileName).append("\"")
				.toString());
		ResponseUtil.getInstance().agregarCabecerasHTTP(response);
		response.setContentLength(content.length);

		ServletOutputStream os = response.getOutputStream();
		os.write(content, 0, content.length);
		os.flush();
		os.close();
	}

	/**
	 * Muestra el contenido de un fichero en un IFrame.
	 *
	 * @param response
	 *            {@link HttpServletResponse}
	 * @param fileName
	 *            Nombre del fichero.
	 * @param content
	 *            Contenido del fichero
	 * @throws IOException
	 *             si ocurre algún error.
	 */
	protected void downloadIFrame(HttpServletResponse response,
			String fileName, byte[] content) throws IOException {
		response.setContentType(MimeTypesUtil.getInstance().getMimeType(
				fileName));
		response.setHeader("Content-disposition", "inline; filename=\""
				+ fileName + "\"");
		ResponseUtil.getInstance().agregarCabecerasHTTP(response);
		response.setContentLength(content.length);

		ServletOutputStream os = response.getOutputStream();
		os.write(content);
		os.flush();
		os.close();
	}

	/**
	 * Comprueba si el usuario que está conectado a la aplicación, pertenece a
	 * archivo
	 *
	 * @param HttpServletRequet
	 *            request
	 * @return <b>true</b> Si el usuario pertenece a Archivo.<br>
	 *         <b>false</b> en caso contrario
	 * @author lucas
	 */
	protected boolean perteneceArchivo(HttpServletRequest request) {
		boolean perteneceArchivo = false;
		AppUser usr = (AppUser) request.getSession().getAttribute(
				Constants.USUARIOKEY);

		String idUsuario = Constants.STRING_EMPTY;
		if (usr != null)
			idUsuario = usr.getId();
		GestionControlUsuariosBI usuariosService = getGestionControlUsuarios(request);
		List gruposUsuario = usuariosService.getGruposUsuario(idUsuario);

		if (gruposUsuario != null && gruposUsuario.size() > 0) {
			Iterator it = gruposUsuario.iterator();

			while (it.hasNext() && !perteneceArchivo) {
				GrupoVO grupo = (GrupoVO) it.next();
				if (StringUtils.isNotEmpty(grupo.getIdArchivo())) {
					perteneceArchivo = true;
				}
			}
		}
		return perteneceArchivo;
	}

	/**
	 * Obtiene el nombre del Archivo de configuración por usuario
	 *
	 * @param request
	 * @param tipoBusqueda
	 * @return
	 */
	protected String getKeyCfgBusquedas(HttpServletRequest request,
			String keyCfgBusqueda) {
		if (perteneceArchivo(request)) {
			keyCfgBusqueda += ConfiguracionArchivoManager.BUSQUEDAS_ARCHIVO_KEY;
		}

		return keyCfgBusqueda;
	}

	public ExportReport getExportReport(HttpServletRequest request) {
		String formatoInforme = (String) request
				.getAttribute(Constants.FORMATO_INFORME_LISTADO_KEY);
		if (formatoInforme == null)
			return null;
		if (formatoInforme.equals(Constants.FORMATO_PDF)) {
			ServiceRepository services = ServiceRepository
					.getInstance(ServiceClient.create(getAppUser(request)));
			GestionInfoSistemaBI infoSistema = services.lookupInfoSistemaBI();
			return new ExportPDFReportImpl(infoSistema.getManejadorIText(),
					getAppUser(request).getEntity());
		} else if (formatoInforme.equals(Constants.FORMATO_XLS)) {
			return new ExportExcelReportImpl();
		} else if (formatoInforme.equals(Constants.FORMATO_TXT)) {
			return new ExportTxtReportImpl();
		}
		return null;
	}

	// public void updateEstadoRelacionRechazada
	// RelacionEntregaVO
	// relacion=(RelacionEntregaVO)getFromTemporalSession(request,
	// TransferenciasConstants.RELACION_KEY);
	// if(relacion!=null){
	// ServiceRepository services =
	// ServiceRepository.getInstance(ServiceClient.create(getAppUser(request)));
	// GestionRelacionesEntregaBI relacionBI =
	// services.lookupGestionRelacionesBI();
	// if(relacion.isRechazada()){
	// relacion.setEstado(EstadoREntrega.ABIERTA.getIdentificador());
	// try{ relacionBI.updateRelacion(relacion); }
	// catch(ActionNotAllowedException e){
	// guardarError(request, e);
	// }
	// }
	// }

	public boolean puedeAccederUsuarioAElemento(HttpServletRequest request,
			String idElemento) {
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionCuadroClasificacionBI cuadroBI = services
				.lookupGestionCuadroClasificacionBI();
		ActionErrors errors = null;
		boolean resp = true;
		try {
			if (!cuadroBI.checkPermitidoConsultaElemento(idElemento)) {
				errors = obtenerErrores(request, true);
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
						FondosConstants.ERROR_ACCESO_ELEMENTO_SIN_PERMISOS));
				resp = false;
			}
		} catch (PadreSinAccesoPermitidoException e) {
			errors = obtenerErrores(request, true);
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					FondosConstants.ERROR_ACCESO_PADRES_ELEMENTO_SIN_PERMISOS,
					TipoNivelCF.getTipoNivel(e.getTipoElemento()).getNombre(),
					e.getTitulo()));
			resp = false;
		} catch (Exception e) {
			obtenerErrores(request, true).add(ActionErrors.GLOBAL_ERROR,
					new ActionError(e.getLocalizedMessage()));
			resp = false;
		}
		return resp;
	}

	public boolean checkPermisosSobreElemento(HttpServletRequest request,
			String idElemento, Object[] permisos) {
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionCuadroClasificacionBI cuadroBI = services
				.lookupGestionCuadroClasificacionBI();
		ActionErrors errors = null;
		try {
			if (!cuadroBI.checkPermisosElemento(idElemento, permisos)) {
				errors = obtenerErrores(request, true);
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
						Constants.ERROR_SIN_PERMISOS));
				return false;
			}
		} catch (Exception e) {
			obtenerErrores(request, true).add(ActionErrors.GLOBAL_ERROR,
					new ActionError(e.getLocalizedMessage()));
			return false;
		}
		return true;
	}

	// protected int getPageNumber(HttpServletRequest request){
	// int pageNumber=1;
	// if(DisplayTagUtils.getPageNumber(request)!=null){
	// pageNumber=Integer.parseInt(DisplayTagUtils.getPageNumber(request));
	// }else if(getFromTemporalSession(request, Constants.PAGE_NUMBER)!=null){
	// pageNumber=((Integer)getFromTemporalSession(request,
	// Constants.PAGE_NUMBER)).intValue();
	// }
	// return pageNumber;
	// }

	protected String getOrderColumn(HttpServletRequest request,
			Busqueda busqueda) {
		// String orderColumn = ElementoCFVO.getDefaultOrderingColName();
		// De momento ordenamos sólo por un campo, cambiar si algún día se
		// consideran más
		int orderColumnPos = 0;
		if (busqueda != null && busqueda.getCamposOrdenacion() != null
				&& busqueda.getCamposOrdenacion().size() > 0)
			orderColumnPos = ElementoCFVO
					.getColumnNumberByXMLColumnName((String) busqueda
							.getCamposOrdenacion().get(0));
		String orderColumn = ElementoCFVO.getColumnNameByNumber(orderColumnPos);

		if (DisplayTagUtils.isSorting(request)) {
			if (DisplayTagUtils.getOrderColumn(request) != null)
				orderColumn = DisplayTagUtils.getOrderColumn(request);

		} else {
			String lastOrderColumn = (String) getFromTemporalSession(request,
					Constants.LAST_ORDER);
			if (lastOrderColumn != null)
				orderColumn = lastOrderColumn;

		}
		return orderColumn;
	}

	protected SortOrderEnum getOrderCurrentDirection(
			HttpServletRequest request, Busqueda busqueda) {
		SortOrderEnum currentOrderDirection = null;
		SortOrderEnum lastOrderDirection = (SortOrderEnum) getFromTemporalSession(
				request, Constants.LAST_ORDER_DIRECTION);

		if (lastOrderDirection == null)
			lastOrderDirection = busqueda.getTipoOrdenacionEnum();

		if (DisplayTagUtils.isSorting(request)) {
			String orderColumn = getOrderColumn(request, busqueda);
			String lastOrderColumn = (String) getFromTemporalSession(request,
					Constants.LAST_ORDER);

			if (orderColumn.equals(lastOrderColumn)) {
				if (lastOrderDirection == null) {
					currentOrderDirection = busqueda.getTipoOrdenacionEnum();
				} else {
					if (SortOrderEnum.ASCENDING.getName().equalsIgnoreCase(
							lastOrderDirection.getName())) {
						currentOrderDirection = SortOrderEnum.DESCENDING;
					} else {
						currentOrderDirection = SortOrderEnum.ASCENDING;
					}
				}
			} else {
				if (lastOrderDirection == null) {
					currentOrderDirection = busqueda.getTipoOrdenacionEnum();
				} else {
					currentOrderDirection = lastOrderDirection;
				}
			}
			return currentOrderDirection;
		} else {
			if (lastOrderDirection == null)
				return busqueda.getTipoOrdenacionEnum();
			else
				return lastOrderDirection;
		}
	}

	/**
	 * Obtiene la lista de acciones que puede ejecutar el usuario.
	 *
	 * @param request
	 * @param listaAcciones
	 * @return
	 */
	public List getListaAcciones(HttpServletRequest request, List listaAcciones) {
		List listaAccionesConFiltro = new ArrayList();

		if (ListUtils.isNotEmpty(listaAcciones)) {
			for (Iterator iterator = listaAcciones.iterator(); iterator
					.hasNext();) {
				AccionVO accionVO = (AccionVO) iterator.next();

				AccionBusqueda accionBusqueda = ConfiguracionArchivoManager
						.getInstance().getAccionBusqueda(accionVO.getValue());

				if (AccionesBusquedaHelper.accionPermitida(
						getServiceClient(request), accionBusqueda)) {
					listaAccionesConFiltro.add(accionVO);
				}
			}
		}
		return listaAccionesConFiltro;
	}

	public Busqueda getCfgBusqueda(HttpServletRequest request, String key) {
		Busqueda busqueda = ConfiguracionArchivoManager.getInstance()
				.getCfgBusqueda(key);

		List listaAcciones = getListaAcciones(request, busqueda.getLtAcciones());

		setInTemporalSession(request, Constants.LISTA_ACCIONES_BUSQUEDA_KEY,
				listaAcciones);

		return busqueda;
	}

	public void accionSinPermisos(HttpServletRequest request) {
		ActionErrors errors = getErrors(request, true);
		errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionError(
				Constants.ERROR_SIN_PERMISOS));
		ErrorsTag.saveErrors(request, errors);
		setInTemporalSession(request, "usarCache", Boolean.TRUE);
	}

	public void accionDatosElementoNoEncontrado(HttpServletRequest request,
			String labelElemento) {
		ActionErrors errors = getErrors(request, true);
		errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionError(
				SalasConsultaConstants.ERROR_DATOS_ELEMENTO_NO_ENCONTRADOS,
				labelElemento));
		ErrorsTag.saveErrors(request, errors);
		setInTemporalSession(request, "usarCache", Boolean.TRUE);
	}

	public void accionDatosElementaNoEncontrados(HttpServletRequest request,
			String labelElementa) {
		ActionErrors errors = getErrors(request, true);
		errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionError(
				SalasConsultaConstants.ERROR_DATOS_ELEMENTA_NO_ENCONTRADOS,
				labelElementa));
		ErrorsTag.saveErrors(request, errors);
		setInTemporalSession(request, "usarCache", Boolean.TRUE);
	}

	public ActionForward getForwardById(String path, String id) {
		ActionForward ret = new ActionForward();
		ret.setPath(path + id);
		ret.setRedirect(true);
		return ret;
	}

	public boolean isAdministradorSistema(HttpServletRequest request) {
		if (getServiceClient(request).hasPermission(
				AppPermissions.ADMINISTRACION_TOTAL_SISTEMA)) {
			return true;
		}
		return false;
	}

	/**
	 * Retorna una lista de los Archivos a los que pertenece el usuario
	 * conectado.
	 *
	 * @return Lista de {@link ArchivoVO}
	 */
	public List getListaArchivosUsuarioConectado(HttpServletRequest request) {
		List listaArchivos = new ArrayList();

		// if(isAdministradorSistema(request)){
		// listaArchivos = getGestionArchivosBI(request).getListaArchivos();
		// }
		// else{
		String[] idsArchivosUsuarioConectado = getAppUser(request)
				.getIdsArchivosUser();
		listaArchivos = getGestionArchivosBI(request).getArchivosXId(
				idsArchivosUsuarioConectado);
		// }
		return listaArchivos;
	}

	protected void setTablaTemporales(HttpServletRequest request,
			TablaTemporalFondosVO tablaTemporal) {
		setInTemporalSession(request, FondosConstants.TABLA_TEMPORAL,
				tablaTemporal);
	}

	protected TablaTemporalFondosVO getTablaTemporalFondosVO(
			HttpServletRequest request) {
		return (TablaTemporalFondosVO) getFromTemporalSession(request,
				FondosConstants.TABLA_TEMPORAL);
	}

	protected ActionErrors comprobarExistenciaRegistroByKey(
			HttpServletRequest request, ActionErrors errors, int module,
			int codigoTabla, String key, String labelTipoElemento,
			String labelEtiqueta) throws ArchigestException {
		boolean existeRegistro = false;

		if (key != null) {
			key = StringUtils.trim(key);
			if (ArchivoModules.DESCRIPCION_MODULE == module) {
				existeRegistro = getGestionDescripcionBI(request)
						.existeRegistroByKey(key, codigoTabla);
			} else {
				throw new ArchigestException(
						"Método no implementado para el módulo "
								+ ArchivoModules.getModuleName(module));
			}

			if (existeRegistro) {
				if (errors == null) {
					errors = new ActionErrors();
				}
				errors.add(

						Constants.EXISTE_ELEMENTO_DUPLICADO,
						new ActionError(Constants.EXISTE_ELEMENTO_DUPLICADO,
								Messages.getString(labelEtiqueta,request.getLocale()),
								Messages.getString(labelTipoElemento,
										request.getLocale()), Messages
										.getString(labelEtiqueta,
												request.getLocale()), key));

			}
		}
		return errors;
	}

	protected ActionErrors comprobarExistenciaRegistroByValue(
			HttpServletRequest request, ActionErrors errors, int module,
			int codigoTabla, String key, String value,
			String labelTipoElemento, String labelEtiqueta)
			throws ArchigestException {
		boolean existeRegistro = false;

		if (StringUtils.isNotEmpty(value)) {
			value = StringUtils.trim(value);
			if (ArchivoModules.DESCRIPCION_MODULE == module) {
				existeRegistro = getGestionDescripcionBI(request)
						.existeRegistroByValue(key, value, codigoTabla);
			} else {
				throw new ArchigestException(
						"Método no implementado para el módulo "
								+ ArchivoModules.getModuleName(module));
			}

			if (existeRegistro) {
				if (errors == null) {
					errors = new ActionErrors();
				}
				errors.add(

						Constants.EXISTE_ELEMENTO_DUPLICADO,
						new ActionError(Constants.EXISTE_ELEMENTO_DUPLICADO,
								Messages.getString(labelEtiqueta,request.getLocale()),
								Messages.getString(labelTipoElemento,
										request.getLocale()), Messages
										.getString(labelEtiqueta,
												request.getLocale()), value));

			}
		}
		return errors;
	}

	/**
	 * Comprueba si existe una etiquetaXml en campos dato o campos tabla
	 * @param request
	 * @param errors
	 * @param etiquetaXml
	 * @param id
	 * @param tabla
	 * @return
	 */
	public ActionErrors comprobarExistenciaEtiquetaXml(HttpServletRequest request,
			ActionErrors errors, String etiquetaXml, String id, String label,
			int tabla) {

		GestionDescripcionBI descripcionService = getGestionDescripcionBI(request);

		CampoDatoVO campoDatoVO = descripcionService
				.getCampoDatoByEtiqueta(etiquetaXml);

		if (campoDatoVO != null) {
			if ((ArchivoTables.ADCAMPODATO_TABLE == tabla && !campoDatoVO.getId().equals(id))
			 || (ArchivoTables.ADCAMPODATO_TABLE != tabla)) {
				errors.add(
						Constants.EXISTE_ELEMENTO_DUPLICADO,
						new ActionError(Constants.EXISTE_ELEMENTO_DUPLICADO,
								Messages.getString(label,request.getLocale()),
								Messages.getString(Constants.LABEL_CAMPO_DATO,
										request.getLocale()), Messages
										.getString(
												Constants.ETIQUETA_ETIQUETAXML,
												request.getLocale()),
								etiquetaXml));

			}
		}

		CampoTablaVO campoTablaVO = descripcionService.getCampoTablaByEtiqueta(etiquetaXml);

		if(campoTablaVO != null){
			if ((ArchivoTables.ADCAMPOTBL_TABLE == tabla && !campoTablaVO.getId().equals(id))
					 || (ArchivoTables.ADCAMPOTBL_TABLE != tabla)) {
						errors.add(
								Constants.EXISTE_ELEMENTO_DUPLICADO,
								new ActionError(Constants.EXISTE_ELEMENTO_DUPLICADO,
										Messages.getString(label,request.getLocale()),
										Messages.getString(Constants.LABEL_CAMPO_TABLA,
												request.getLocale()), Messages
												.getString(
														Constants.ETIQUETA_ETIQUETAXML,
														request.getLocale()),
										etiquetaXml));
					}
		}


		campoTablaVO = descripcionService.getCampoTablaByEtiquetaFila(etiquetaXml);

		if(campoTablaVO != null){
			if ((ArchivoTables.ADCAMPOTBL_TABLE == tabla && !campoTablaVO.getId().equals(id))
					 || (ArchivoTables.ADCAMPOTBL_TABLE != tabla)) {
						errors.add(
								Constants.EXISTE_ELEMENTO_DUPLICADO,
								new ActionError(Constants.EXISTE_ELEMENTO_DUPLICADO,
										Messages.getString(label,request.getLocale()),
										Messages.getString(Constants.LABEL_CAMPO_TABLA,
												request.getLocale()), Messages
												.getString(
														Constants.ETIQUETA_ETIQUETAXMLFILA,
														request.getLocale()),
										etiquetaXml));
					}
		}

		return errors;
	}

	protected void cargarListaRepositoriosECM(HttpServletRequest request) {

		List listaRepositoriosEcm = getGestionDocumentosElectronicosBI(request)
				.getRepositoriosEcm();

		if (listaRepositoriosEcm == null) {
			listaRepositoriosEcm = new ArrayList();

			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionError(
					DocumentosConstants.ERROR_ECM_NO_CONFIGURADOS));

			MessagesTag.saveMessages(request, messages);
		}

		setInTemporalSession(request, FondosConstants.REPOSITORIOS_ECM_KEY,
				listaRepositoriosEcm);
	}


	protected String getXmlPretty(String xml){
        Source xmlInput = new StreamSource(new StringReader(xml));
        StreamResult xmlOutput = new StreamResult(new StringWriter());

		try {
			Transformer transformer;

			transformer = TransformerFactory.newInstance()
			                .newTransformer();

	        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	        transformer.transform(xmlInput, xmlOutput);
	        return xmlOutput.getWriter().toString();
		} catch (TransformerConfigurationException e) {
			logger.error(e);
			return xml;
		} catch (TransformerFactoryConfigurationError e) {
			logger.error(e);
			return xml;
		} catch (TransformerException e) {
			logger.error(e);
			return xml;
		}
	}
}