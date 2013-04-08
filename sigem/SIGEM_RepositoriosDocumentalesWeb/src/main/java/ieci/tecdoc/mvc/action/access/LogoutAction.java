/*
 * Created on 07-jun-2005
 *
 */
package ieci.tecdoc.mvc.action.access;

import ieci.tecdoc.mvc.action.BaseAction;
import ieci.tecdoc.mvc.dto.access.UserConnectedDTO;
import ieci.tecdoc.mvc.util.Constantes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Antonio María
 *
 */
public class LogoutAction extends BaseAction {

    /* (non-Javadoc)
     * @see ieci.tecdoc.mvc.action.BaseAction#executeLogic(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    private static final Logger logger = Logger.getLogger(LogoutAction.class);
    
    protected ActionForward executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        if(request.getSession(false) != null)
        {
           HttpSession session  = request.getSession(false);
           
           if(logger.isDebugEnabled())
           {
               logger.debug("Session to invalidate: "+session.getId());
           }
           session.invalidate();
           logger.debug("Logout executed. Session Invalidate");
        }
        return mapping.findForward("success");
    }

}
