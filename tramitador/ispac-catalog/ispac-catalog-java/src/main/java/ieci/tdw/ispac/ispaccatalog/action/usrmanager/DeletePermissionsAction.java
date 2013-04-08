package ieci.tdw.ispac.ispaccatalog.action.usrmanager;

import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.ispaccatalog.action.BaseAction;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class DeletePermissionsAction extends BaseAction
{
    public ActionForward executeAction(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response,
            SessionAPI session) throws Exception
    {

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
 		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_INV_PROCEDURES_EDIT });

 		int regId = Integer.parseInt(request.getParameter("regId"));

        ISecurityAPI api = session.getAPI().getSecurityAPI();
        api.deletePermissions(regId);

        ActionForward forward = new ActionForward("/showPermissions.do?regId=" + request.getParameter("procId"), true);

        return forward;
    }
}
