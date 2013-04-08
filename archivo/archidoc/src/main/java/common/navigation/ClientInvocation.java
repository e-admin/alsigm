package common.navigation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import util.TreeView;

import common.util.ClientInvocationUtils;
import common.util.StringUtils;

public class ClientInvocation {

	private HashMap temporal = null;// mapeo de nombres en session, para desp
									// borrarlos

	protected final static Logger logger = Logger
			.getLogger(ClientInvocation.class);

	final static ArrayList patternsIgnoredParams = new ArrayList();
	static {
		// patrones representados mediante una Expresion regular
		// que siguen los nombres de parametros a no tener en cuenta a la hora
		// de comparar la direccion
		// a añadir a la cima de la pila con la ultima que existe en la cima
		patternsIgnoredParams.add("d\\-");// empiezan por d-

	}

	String contextPath;
	String path;
	Map parameters;
	Map addedParams;

	boolean AsReturnPoint;
	String titleNavigationToolBar;
	boolean visibleInNavigationToolBar;
	boolean asInitialClientInNavigationToolBar;
	String formName;
	TreeView treeView = null;
	String viewName = null;
	String viewAction = null;

	private String keyClient;

	public String getKeyClient() {
		return this.keyClient;
	}

	protected void setKeyClient(String keyClient) {
		this.keyClient = keyClient;
	}

	public void setAsReturnPoint(boolean returnPoint) {
		this.AsReturnPoint = returnPoint;
	}

	public String getTitleNavigationToolBar() {
		return this.titleNavigationToolBar;
	}

	public ClientInvocation setTitleNavigationToolBar(
			String titleToolBarNavigation) {
		this.titleNavigationToolBar = titleToolBarNavigation;
		return this;
	}

	protected ClientInvocation(String keyClient, String contextPath,
			String path, Map parameters, boolean returnPoint) {
		this.contextPath = contextPath;
		this.path = path;
		// this.parameters = parameters;
		if (parameters != null)
			this.parameters = new HashMap(parameters);
		this.AsReturnPoint = returnPoint;
		this.addedParams = new HashMap();
		this.keyClient = keyClient;
		this.visibleInNavigationToolBar = true;
		this.asInitialClientInNavigationToolBar = false;
	}

	protected ClientInvocation(String keyClient, String contextPath,
			String path, Map parameters, boolean returnPoint,
			TreeView treeView, String viewName, String viewAction) {
		this(keyClient, contextPath, path, parameters, returnPoint);
		this.treeView = treeView;
		this.viewName = viewName;
		this.viewAction = viewAction;
	}

	public boolean isAsInitialClientInNavigationToolBar() {
		return this.asInitialClientInNavigationToolBar;
	}

	public void setAsInitialClientInNavigationToolBar(
			boolean initialClientInNavigationToolBar) {
		this.asInitialClientInNavigationToolBar = initialClientInNavigationToolBar;
	}

	public boolean isVisibleInNavigationToolBar() {
		return this.visibleInNavigationToolBar;
	}

	public void setVisibleInNavigationToolBar(boolean visibleInNavigationToolBar) {
		this.visibleInNavigationToolBar = visibleInNavigationToolBar;
	}

	public Map getParameters() {
		return parameters;
	}

	public void setParameters(Map parameters) {
		this.parameters = parameters;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public boolean isAsReturnPoint() {
		return this.AsReturnPoint;
	}

	public void addParameter(String name, Object value) {
		addedParams.put(name, value);
	}

	public void addParameters(Map params) {
		addedParams.putAll(params);
	}

	public String getInvocationURI() {
		return getInvocationURIWithoutEmptyParameters();
	}

	/**
	 * Permite eliminar de la url de la invocación todos los parámetros que son
	 * vacíos y que por tanto no aportan nada a la url
	 * 
	 * @return Url de la invocacion
	 */
	private String getInvocationURIWithoutEmptyParameters() {
		String invocationPath;
		if (this.parameters != null) {
			Map uriParams = new HashMap(this.parameters);
			uriParams.putAll(addedParams);
			invocationPath = ClientInvocationUtils
					.getInvocationURIWithoutEmptyParameters(this.path,
							uriParams);
		} else
			invocationPath = this.path;

		invocationPath = StringUtils.replace(invocationPath, contextPath, "");
		return invocationPath;
	}

	public static ClientInvocation getInstance(String keyNode,
			HttpServletRequest request, Map parameters, boolean returnPoint) {
		return getClientInvocationInstance(keyNode, request.getContextPath(),
				request.getRequestURI(), parameters, returnPoint, null, null,
				null);
	}

	public static ClientInvocation getInstance(String keyNode,
			HttpServletRequest request, Map parameters, boolean returnPoint,
			TreeView treeView, String viewName, String viewAction) {
		return getClientInvocationInstance(keyNode, request.getContextPath(),
				request.getRequestURI(), parameters, returnPoint, treeView,
				viewName, viewAction);
	}

	public static ClientInvocation getInstance(String keyNode,
			String contextPath, String uri, Map parameters, boolean returnPoint) {
		return getClientInvocationInstance(keyNode, contextPath, uri,
				parameters, returnPoint, null, null, null);
	}

	public static ClientInvocation getInstance(String keyNode,
			String contextPath, String uri, Map parameters,
			boolean returnPoint, TreeView treeView, String viewName,
			String viewAction) {
		return getClientInvocationInstance(keyNode, contextPath, uri,
				parameters, returnPoint, treeView, viewName, viewAction);
	}

	private static ClientInvocation getClientInvocationInstance(String keyNode,
			String contextPath, String uri, Map parameters,
			boolean returnPoint, TreeView treeView, String viewName,
			String viewAction) {
		ClientInvocation ret = new ClientInvocation(keyNode, contextPath, uri,
				parameters, returnPoint, treeView, viewName, viewAction);
		return ret;
	}

	public void executeLogicInSave(HttpServletRequest request) {

	}

	public void executeLogicInPop(HttpServletRequest request) {
		if (temporal != null && temporal.size() > 0)
			cleanTemporalSession(request);

	}

	public boolean equals(ClientInvocation o) {
		return (this.keyClient.equalsIgnoreCase(o.keyClient));
	}

	protected Map getCleanedParametersMap(Map parameters) {
		Map ret = new HashMap();
		if (parameters != null) {
			for (Iterator itParameters = parameters.keySet().iterator(); itParameters
					.hasNext();) {
				String nameParameter = (String) itParameters.next();
				if (!parameterMustBeIgnored(nameParameter))
					ret.put(nameParameter, parameters.get(nameParameter));
			}
		}
		return ret.size() > 0 ? ret : null;
	}

	private boolean parameterMustBeIgnored(String nameParameter) {
		Pattern patron = null;
		Matcher m = null;
		String patternNameTemporalParam = null;
		for (Iterator iter = patternsIgnoredParams.iterator(); iter.hasNext();) {
			patternNameTemporalParam = (String) iter.next();
			if (nameParameter.length() >= patternNameTemporalParam.length()) {
				patron = Pattern.compile(patternNameTemporalParam);
				m = patron.matcher(nameParameter);
				boolean patronIsVerifiedAtStarting = m.lookingAt();
				if (patronIsVerifiedAtStarting)
					return true;
			}
		}
		return false;
	}

	public void setInTemporalSession(HttpServletRequest request,
			String nameObj, Object obj) {
		if (temporal == null)
			temporal = new HashMap();

		temporal.put(nameObj, nameObj);
		request.getSession().setAttribute(nameObj, obj);
	}

	public void setInTemporalSession(HttpServletRequest request, String nameObj) {
		if (temporal == null)
			temporal = new HashMap();

		temporal.put(nameObj, nameObj);
	}

	public void setBooleanInTemporalSession(HttpServletRequest request,
			boolean value, String nameKeyValue) {
		setInTemporalSession(request, nameKeyValue, Boolean.toString(value));
	}

	public boolean getBooleanFromInTemporalSession(HttpServletRequest request,
			String nameKeyValue) {
		try {
			return ((String) getFromTemporalSession(request, nameKeyValue))
					.equalsIgnoreCase("true") ? true : false;
		} catch (Exception e) {
			return ((Boolean) getFromTemporalSession(request, nameKeyValue))
					.booleanValue();
		}
	}

	public Object getFromTemporalSession(HttpServletRequest request,
			String nameObj) {
		return request.getSession().getAttribute(nameObj);
	}

	public void removeInTemporalSession(HttpServletRequest request,
			String nameObj) {
		request.getSession().removeAttribute(nameObj);
	}

	public void cleanTemporalSession(HttpServletRequest request) {
		// borrar de session obj cuyo nombre este en temporal
		if (temporal != null) {
			Iterator it = temporal.keySet().iterator();
			while (it.hasNext()) {
				request.getSession().removeAttribute((String) it.next());
			}

			// borrar temporal
			temporal.clear();
		}
	}

	protected HashMap getTemporal() {
		return this.temporal;
	}

	protected void setTemporal(HashMap temporal) {
		this.temporal = temporal;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public TreeView getTreeView() {
		return treeView;
	}

	public void setTreeView(TreeView treeView) {
		this.treeView = treeView;
	}

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public String getViewAction() {
		return viewAction;
	}

	public void setViewAction(String viewAction) {
		this.viewAction = viewAction;
	}

	public final Map getAddedParams() {
		return addedParams;
	}

}
