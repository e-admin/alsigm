/*
 * Created on 09-jun-2005
 *
 */
package ieci.tecdoc.mvc.action.archive;

import ieci.tecdoc.idoc.admin.api.ObjFactory;
import ieci.tecdoc.idoc.admin.api.archive.ArchiveAccess;
import ieci.tecdoc.mvc.action.BaseAction;
import ieci.tecdoc.mvc.error.MvcError;
import ieci.tecdoc.mvc.util.SessionHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

/**
 * @author Antonio María
 *
 */
public class ArchiveCanAddPerms extends BaseAction{

    /* (non-Javadoc)
     * @see ieci.tecdoc.mvc.action.BaseAction#executeLogic(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    protected ActionForward executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
    	String entidad=SessionHelper.getEntidad(request);
    	
        ActionForward forward;
        HttpSession session = request.getSession(false);
        
        int archId = Integer.parseInt(request.getParameter("oid"));
        session.setAttribute("archiveId", new Integer(archId));
        
        int userId = getUserId(request);
        ArchiveAccess archiveAccess = ObjFactory.createArchiveAccess();
        boolean userCanAddPermOnArch = archiveAccess.userCanAddPermOnArch(userId, archId, entidad );
        if (!userCanAddPermOnArch){
            ActionMessages mensajes = new ActionMessages();
            ActionMessage msg = new ActionMessage( String.valueOf(MvcError.EC_NOT_ENOUGH_PERMISSIONS));
            mensajes.add(ActionMessages.GLOBAL_MESSAGE, msg);
            saveMessages(request,mensajes);
            // throw new IeciTdException(String.valueOf(MvcError.EC_NOT_ENOUGH_PERMISSIONS), null);
        }
        forward = mapping.findForward("success");
        
        return forward;
    }

    

}
