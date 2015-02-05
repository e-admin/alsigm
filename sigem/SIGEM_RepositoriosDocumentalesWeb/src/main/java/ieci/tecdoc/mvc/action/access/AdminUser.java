/*
 * Created on 29-mar-2005
 *
 */
package ieci.tecdoc.mvc.action.access;

import java.util.HashMap;

import ieci.tecdoc.mvc.util.MvcDefs;
import ieci.tecdoc.mvc.util.SessionHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Antonio María
 *
 */
public class AdminUser extends Action{

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
    	String entidad=SessionHelper.getEntidad(request);
    	
        HttpSession session	= request.getSession();
		//int loginMethod = ((Integer)session.getServletContext().getAttribute(MvcDefs.TOKEN_LOGIN_METHOD)).intValue();
        HashMap hash=(HashMap)session.getServletContext().getAttribute(MvcDefs.TOKEN_LOGIN_METHOD);
        int loginMethod=((Integer)hash.get(entidad)).intValue();
		if ( loginMethod == 1 )
		    return mapping.findForward("adminUserBD");
		else
		    return mapping.findForward("adminUserLDAP");
    }
    

}
