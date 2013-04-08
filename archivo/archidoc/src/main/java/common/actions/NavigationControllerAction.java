package common.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.navigator.menu.MenuRepository;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import common.Constants;
import common.navigation.ClientInvocation;
import common.view.menu.SelectableComponentMenuRepository;
import common.view.menu.SelectableMenuComponent;

import deposito.DepositoConstants;

/**
 * Controlador para la gestión de la navegación a través de la selección de
 * opciones de menú, mediante la miga de pan y a través de los enlaces de acceso
 * rápido de la bandeja de entrada
 */
public class NavigationControllerAction extends BaseAction {

	protected static final String KEY_PARAMETER_STEP = "step";

	Logger logger = Logger.getLogger(NavigationControllerAction.class);

	public void goBackKeyExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			String keyClient = request.getParameter("key");
			if (!StringUtils.isBlank(keyClient)) {
				ClientInvocation clientInvocation = getInvocationStack(request)
						.goBackKeyClientInvocation(request, keyClient);
				ActionRedirect redirect = new ActionRedirect(new ActionForward(
						clientInvocation.getInvocationURI(), true), true);
				if (clientInvocation.getTreeView() != null) {
					redirect.addParameter(Constants.TREE_VIEW_RELOAD,
							Boolean.TRUE);
					redirect.addParameter(DepositoConstants.REFRESH_VIEW_KEY,
							Boolean.TRUE);
				}
				setReturnActionFordward(request, redirect);
			} else {
				logger.error("Invocacion de la barra de navegacion incorrecta, falta el parametro key");
				goBackExecuteLogic(mapping, form, request, response);
			}
		} catch (Exception e) {
			setReturnActionFordward(request, mapping.findForward("homepage"));
		}
	}

	public void goBackStepExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			String step = request.getParameter("step");
			if (!StringUtils.isBlank(step)) {
				ClientInvocation clientInvocation = getInvocationStack(request)
						.goBackNClientsInvocation(request,
								Integer.parseInt(step));
				ActionRedirect redirect = new ActionRedirect(new ActionForward(
						clientInvocation.getInvocationURI(), true), true);
				if (clientInvocation.getTreeView() != null) {
					redirect.addParameter(Constants.TREE_VIEW_RELOAD,
							Boolean.TRUE);
				}
				setReturnActionFordward(request, redirect);
			} else {
				logger.error("Invocacion de la barra de navegacion incorrecta, falta el parametro Step");
				goBackExecuteLogic(mapping, form, request, response);
			}
		} catch (Exception e) {
			setReturnActionFordward(request, mapping.findForward("homepage"));
		}
	}

	public void menuOptionExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		resetInvocationStack(request);
		String selectedMenuName = request.getParameter("menuName");
		SelectableComponentMenuRepository userMenus = (SelectableComponentMenuRepository) request
				.getSession().getAttribute(MenuRepository.MENU_REPOSITORY_KEY);
		SelectableMenuComponent menuComponent = (SelectableMenuComponent) userMenus
				.getMenu(selectedMenuName);
		userMenus.setSelectedMenu(menuComponent);
		ActionForward forward = mapping.findForward(request
				.getParameter("menuOption"));
		// forward.setRedirect(true);
		setReturnActionFordward(request,
				forward != null ? forward : mapping.findForward("homepage"));
	}
}