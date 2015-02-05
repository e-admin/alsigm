
package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author juanin
 *
 * Acción que devuelve el estado en que estamos.
 */
public class ShowManagerState extends BaseAction
{

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispacmgr.action.BaseAction#executeAction(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, ieci.tdw.ispac.api.impl.SessionAPI)
     */
    public ActionForward executeAction(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response,
            SessionAPI session) throws Exception
    {
    	IManagerAPI managerAPI=ManagerAPIFactory.getInstance().getManagerAPI(session.getClientContext());
		IState currentstate = managerAPI.currentState(getStateticket(request));
		
		ActionForward action = mapping.findForward(currentstate.getLabel());
		return new ActionForward(action.getName(),
			action.getPath() + currentstate.getQueryString(), false);
    }

}
