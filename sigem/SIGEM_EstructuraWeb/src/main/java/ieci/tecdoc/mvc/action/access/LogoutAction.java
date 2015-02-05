/*
 * Created on 07-jun-2005
 *
 */
package ieci.tecdoc.mvc.action.access;

import ieci.tecdoc.mvc.action.BaseAction;
import ieci.tecdoc.mvc.dto.access.UserConnectedDTO;
import ieci.tecdoc.mvc.util.Constantes;
import ieci.tecdoc.sgm.core.admin.web.AutenticacionAdministracion;

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
        
    	HttpSession session  = request.getSession(false);
    	session.invalidate();
    	
        return mapping.findForward("success");
    }

}
