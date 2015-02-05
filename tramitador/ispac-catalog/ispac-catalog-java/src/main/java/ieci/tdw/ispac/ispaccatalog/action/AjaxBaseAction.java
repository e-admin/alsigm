package ieci.tdw.ispac.ispaccatalog.action;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.impl.SessionAPIFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public abstract class AjaxBaseAction extends BaseAction {


	/**
	 * This is the main action called from the Struts framework.
	 *
	 * @param mapping The ActionMapping used to select this instance.
	 * @param form The optional ActionForm bean for this request.
	 * @param request The HTTP Request we are processing.
	 * @param response The HTTP Response we are processing.
	 * @throws Exception
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{

			SessionAPI sesion = null;

			try {
			
				sesion = SessionAPIFactory.getSessionAPI(request, response);

				String username = sesion.getUserName();
				request.setAttribute("User", username);

				return executeAction(mapping, form, request, response, sesion);

			}catch(Throwable e){
				throw new ISPACException(e);
			}finally {
					sesion.release();
			}
	}

}
