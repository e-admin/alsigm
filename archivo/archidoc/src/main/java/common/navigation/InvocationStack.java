package common.navigation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

/**
 * Barra de navegación mediante la que se gestiona la miga de pan que permite
 * mantener la secuencia de peticiones realizadas por el usuario y que le
 * permite volver a cualquiera de los puntos por los que ha ido pasando
 */
public class InvocationStack {

	private final static Logger logger = Logger
			.getLogger(InvocationStack.class);

	/*
	 * estas dos ctes se podrian pasar al iniciar la pila, para eliminar la
	 * depencia del NavigationController
	 */
	// private final static String URI_NAVIGATION_CONTROLLER =
	// "/archivo/action/navigateAction?method=goBackStep";
	private final static String URI_NAVIGATION_CONTROLLER = "/action/navigateAction?method=goBackKey";

	// private static final String KEY_PARAMETER_STEP = "step";
	private static final String KEY_PARAMETER_KEY = "key";

	final static ArrayList patternsIgnoredParams = new ArrayList();

	LinkedList callStack = null;
	LinkedList configStack = null;
	ClientInvocation home = null;

	boolean disableLink = false;

	public boolean isDisableLink() {
		return disableLink;
	}

	public void setDisableLink(boolean disableLink) {
		this.disableLink = disableLink;
	}

	public InvocationStack(ClientInvocation home, HttpServletRequest request) {
		this.callStack = new LinkedList();
		this.configStack = new LinkedList();
		setHome(home);
		home.executeLogicInSave(request);
	}

	/**
	 * Incorpora una petición a la barra de navegación
	 * 
	 * @param inv
	 *            Datos de la petición a almacenar en la barra de navegación
	 * @param request
	 *            Request origen de la petición
	 */
	public void saveClientInvocation(ClientInvocation inv,
			HttpServletRequest request) {

		synchronized (this) {

			// si la nueva invocacion es igual a alguna de las anteriores
			// desapilo hasta esa y sobrescribo
			int size = getSize();
			// boolean encontrado = false;
			for (int i = size - 1; i >= 0; i--) {
				if (getClientInvocation(i).equals(inv)) {

					logger.debug(inv.getInvocationURI()
							+ "Considerado igual a otra invocacion");

					// encontrado = true;

					// desapilar hacia arriba de esa en adelante
					for (int j = i + 1; j < size; j++) {
						if (!getLastClientInvocation()
								.isVisibleInNavigationToolBar())
							getLastClientInvocation()
									.setVisibleInNavigationToolBar(true);
						popLastClientInvocation(request, true);
					}

					// cojo la ultima y extraigo sus nombres de objetos de
					// session
					ClientInvocation lastClient = getLastClientInvocation();
					HashMap sessionContent = lastClient.getTemporal();
					if (sessionContent != null) {
						if (inv.getTemporal() != null)
							sessionContent.putAll(inv.getTemporal());
					} else
						sessionContent = inv.getTemporal();

					// al nuevo cli le establezco el total de objetos de session
					inv.setTemporal(sessionContent);
					inv.setAsReturnPoint(lastClient.isAsReturnPoint());
					callStack.removeLast();
					// parada del for
					i = 0;
				}
			}

			callStack.add(inv);
			inv.executeLogicInSave(request);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Guardado en pila '" + inv.getKeyClient() + "' [uri:'"
					+ inv.getInvocationURI() + "']");
			int i = 0;
			if (callStack != null) {
				logger.debug("***********************************************");
				for (Iterator itCallStack = callStack.iterator(); itCallStack
						.hasNext();) {
					i++;
					ClientInvocation cli = (ClientInvocation) itCallStack
							.next();
					logger.debug(" PILA(" + i + ") - key:" + cli.getKeyClient()
							+ " - URI:" + cli.getInvocationURI()
							+ " - PuntoRetorno:" + cli.isAsReturnPoint() + "");
					if (cli.getTemporal() != null) {
						// for (Iterator itTemporal =
						// cli.getTemporal().keySet().iterator();
						// itTemporal.hasNext();) {
						// Evita un ConcurrentModificationException que se lanza
						// a veces, si se usa la sentencia anterior.
						// TODO Localizar porque se lanza esa excepcion.
						HashMap listaTemporal = (HashMap) cli.getTemporal()
								.clone();
						for (Iterator itTemporal = listaTemporal.keySet()
								.iterator(); itTemporal.hasNext();) {
							String keyAttribute = (String) itTemporal.next();
							logger.debug("\tTemporal keyAttribute:"
									+ keyAttribute);
						}
					}
				}
				logger.debug("***********************************************");
			}
		}
	}

	public boolean isPresent(String keyClientInvocation) {
		for (int i = getSize() - 1; i >= 0; i--) {
			if (getClientInvocation(i).getKeyClient().equalsIgnoreCase(
					keyClientInvocation))
				return true;
		}
		return false;
	}

	public ClientInvocation getClientInvocation(String keyClientInvocation) {
		for (int i = getSize() - 1; i >= 0; i--) {
			if (getClientInvocation(i).getKeyClient().equalsIgnoreCase(
					keyClientInvocation))
				return getClientInvocation(i);
		}
		return null;
	}

	public int getIndexClientInvocation(String keyClientInvocation) {
		for (int i = getSize() - 1; i >= 0; i--) {
			if (getClientInvocation(i).getKeyClient().equalsIgnoreCase(
					keyClientInvocation))
				return i;
		}
		return -1;
	}

	public ClientInvocation getLastClientInvocation() {
		if (callStack.size() > 0)
			return (ClientInvocation) callStack.getLast();
		else
			return home;
	}

	public ClientInvocation getClientInvocation(int index) {
		if (callStack.size() > 0 && index > 0)
			return (ClientInvocation) callStack.get(index - 1);

		else if (callStack.size() == 0 || index == 0)
			return getHome();

		return null;
	}

	public void deleteFormFromSession(HttpServletRequest request,
			ClientInvocation invocacionActual) {
		if (invocacionActual.getFormName() == null)
			return;

		// si el nivel anterior al actual usa el mismo formulario -> conservarlo
		boolean borrar = true;
		ClientInvocation invocacionAnterior = null;
		for (Iterator it = callStack.iterator(); it.hasNext();) {
			invocacionAnterior = ((ClientInvocation) it.next());
			// llegamos hasta el penultimo
			if (!it.hasNext())
				break;
			if (invocacionActual.getFormName().equals(
					invocacionAnterior.getFormName())) {
				borrar = false;
				break;
			}
		}
		if (borrar)
			request.getSession()
					.removeAttribute(invocacionActual.getFormName());
	}

	public ClientInvocation popLastClientInvocation(HttpServletRequest request) {
		return popLastClientInvocation(request, false);
	}

	public ClientInvocation popLastClientInvocation(HttpServletRequest request,
			boolean deleteForm) {
		ClientInvocation invocation = null;
		if (callStack.size() > 0) {
			invocation = getLastClientInvocation();
			while (!invocation.isVisibleInNavigationToolBar()
					&& callStack.size() > 0) {
				invocation.executeLogicInPop(request);
				if (deleteForm)
					deleteFormFromSession(request, invocation);

				callStack.removeLast();
				invocation = getLastClientInvocation();
			}
			if (deleteForm)
				deleteFormFromSession(request, invocation);

			if (invocation != home)
				callStack.removeLast();
		} else
			invocation = home;

		invocation.executeLogicInPop(request);

		return invocation;
		// if (callStack.size() == 0) {
		// home.executeLogicInPop(request);
		// return home;
		// }
		// getLastClientInvocation().executeLogicInPop(request);
		// return (ClientInvocation)callStack.removeLast();
	}

	public ClientInvocation goBackClientInvocation(HttpServletRequest request) {
		/*
		 * Se esta solicitando la url para ir hacia atras por lo que estaras
		 * viendo ya una nueva pantalla por lo que el top de la pila sera la
		 * invocacion que da lugar a la visualizacion de esa pagina. Por eso
		 * primero se elimina esa de la pila y se devuelve la que esta justo a
		 * continuacion
		 */
		popLastClientInvocation(request, true);
		return popLastClientInvocation(request);
	}

	public ClientInvocation goBackTwiceClientInvocation(
			HttpServletRequest request) {
		popLastClientInvocation(request, true);
		popLastClientInvocation(request, true);
		return popLastClientInvocation(request);
	}

	/**
	 * Metodo para saltar hacia atras en la pila de navegacion
	 * 
	 * @param request
	 * @param nClients
	 * @return
	 */

	public ClientInvocation goBackNClientsInvocation(
			HttpServletRequest request, int nClients) {
		ClientInvocation cli = null;
		for (int i = 0; i < nClients; i++) {
			boolean deleteForm = (i < nClients - 1);
			cli = popLastClientInvocation(request, deleteForm);
		}
		return cli;
	}

	// sino la encuentra no modifica la pila de llamadas, en su lugar devuelve
	// el ultimo ClientInvocation (sin extraerlo)
	public ClientInvocation goBackUntilPreviousKeyClient(
			HttpServletRequest request, String keyClient) {
		int nNivelesComprobados = 0;
		if (keyClient != null) {
			for (Iterator it = callStack.iterator(); it.hasNext();) {
				if (((ClientInvocation) (it.next())).getKeyClient().equals(
						keyClient))
					return goBackNClientsInvocation(request, callStack.size()
							- nNivelesComprobados);
				nNivelesComprobados++;
			}
		}
		return ((ClientInvocation) (callStack.getLast()));
	}

	public ClientInvocation goBackKeyClientInvocation(
			HttpServletRequest request, String keyClient) {
		ClientInvocation cli = null;
		int size = getSize();
		for (int i = 0; i < size; i++) {
			boolean deleteForm = false;
			if (callStack.size() > 0) {
				if (!((ClientInvocation) callStack.getLast()).getKeyClient()
						.equals(keyClient)) {
					deleteForm = true;
				}
			}
			cli = popLastClientInvocation(request, deleteForm);
			if (cli.getKeyClient().equals(keyClient))
				return cli;
		}
		return goBackNClientsInvocation(request, size);
	}

	/**
	 * Reseteo de la pila
	 * 
	 * @param request
	 */
	public void reset(HttpServletRequest request) {

		// desapilar clientes y liberar su recursos
		if (callStack != null)
			while (callStack.size() > 0)
				popLastClientInvocation(request, true);

		// for (int i = 0; i <= callStack.size(); i++) {
		// popLastClientInvocation(request);
		// }

		configStack.clear();
		home.cleanTemporalSession(request);
		setHome(home);
	}

	public ClientInvocation goToReturnPoint(HttpServletRequest request) {
		ClientInvocation ret = null;
		if (callStack != null) {
			popLastClientInvocation(request);
			ret = popLastClientInvocation(request);
			// tenemos en cuenta que ahora el tamanio de pila es uno menos pq
			// hemos realizado un pop
			while (!ret.isAsReturnPoint()) {
				deleteFormFromSession(request, ret);
				ret = popLastClientInvocation(request);
			}
		}
		return ret;
	}

	public ClientInvocation goToLastReturnPoint(HttpServletRequest request) {
		ClientInvocation ret = null;
		if (callStack != null) {
			ret = popLastClientInvocation(request);
			// tenemos en cuenta que ahora el tamanio de pila es uno menos pq
			// hemos realizado un pop
			while (!ret.isAsReturnPoint()) {
				deleteFormFromSession(request, ret);
				ret = popLastClientInvocation(request);
			}
		}
		return ret;
	}

	/* objetos a la vista */
	public class NodeToView {

		String resourceMessage;

		String uri;

		String target = "_self";

		boolean linkable = false;

		public String getResourceMessage() {
			return this.resourceMessage;
		}

		public void setResourceMessage(String resourceMessage) {
			this.resourceMessage = resourceMessage;
		}

		public String getUri() {
			return this.uri;
		}

		public void setUri(String uri) {
			this.uri = uri;
		}

		/**
		 * @return Returns the target.
		 */
		public String getTarget() {
			return target;
		}

		/**
		 * @param target
		 *            The target to set.
		 */
		public void setTarget(String target) {
			this.target = target;
		}

		public boolean isLinkable() {
			return linkable;
		}

		public void setLinkable(boolean linkable) {
			this.linkable = linkable;
		}
	}

	public List getNavigationToolBar() {

		LinkedList ret = new LinkedList();

		ClientInvocation cli = null;

		int sizeNavigationToolBar = getSize();
		boolean initialFounded = false;
		for (int i = sizeNavigationToolBar; (i > 0) && initialFounded == false; i--) {
			NodeToView node = new NodeToView();
			cli = getClientInvocation(i - 1);

			if (cli.isVisibleInNavigationToolBar()) {

				node.setResourceMessage(cli.getTitleNavigationToolBar());
				// node.setUri(new
				// StringBuffer(URI_NAVIGATION_CONTROLLER).append("&")
				// .append(KEY_PARAMETER_STEP).append("=").append(sizeNavigationToolBar-i+1).toString());
				node.setUri(new StringBuffer(URI_NAVIGATION_CONTROLLER)
						.append("&").append(KEY_PARAMETER_KEY).append("=")
						.append(cli.getKeyClient()).toString());
				node.setTarget(getTargetClient(i - 1));
				node.setLinkable(true);
				ret.addFirst(node);
			}

			if (cli.isAsInitialClientInNavigationToolBar())
				return ret;

		}

		return ret;

	}

	/**
	 * Ha de buscar desde la posicion actual hasta el ultimo cliente con
	 * configuracion de vista. Y devolver el target de este. En caso de q no
	 * exista desde este nodo ningun cliente con configuracion de vista el top
	 * sera self
	 * 
	 * @param pos
	 * @return
	 */
	public String getTargetClient(int pos) {
		if (pos == 0)
			return "_top";
		else
			return "_self";
	}

	public ClientInvocation getHome() {
		home.setAsReturnPoint(true);
		return this.home;
	}

	public void setHome(ClientInvocation home) {
		this.home = home;
		home.setAsReturnPoint(true);
	}

	public void setInSession(HttpServletRequest request, String nameObject,
			Object value) {
		getLastReturnPoint(request).setInTemporalSession(request, nameObject,
				value);
	}

	public Object getFromSession(HttpServletRequest request, String nameObject) {
		return getLastReturnPoint(request).getFromTemporalSession(request,
				nameObject);
	}

	public ClientInvocation getLastReturnPoint(HttpServletRequest request) {
		int i = getSize() - 1;
		ClientInvocation invocation = null;
		invocation = getClientInvocation(i);
		while (!invocation.isAsReturnPoint() && i >= 0) {
			invocation = getClientInvocation(i);
			i--;
		}

		// siempre va devolver uno pq el home es un punto de retorno
		return invocation;
	}

	public void cleanSession(HttpServletRequest request) {
		getLastReturnPoint(request).cleanTemporalSession(request);

	}

	public void removeInTemporalSession(HttpServletRequest request,
			String nameAttribute) {
		getLastReturnPoint(request).removeInTemporalSession(request,
				nameAttribute);

	}

	/**
	 * 
	 * @return Uno mas de las URLS almacenadas en pila, ya que la primera es la
	 *         HOME
	 */
	public int getSize() {
		if (callStack != null)
			return callStack.size() + 1;
		return 1;

	}

}
