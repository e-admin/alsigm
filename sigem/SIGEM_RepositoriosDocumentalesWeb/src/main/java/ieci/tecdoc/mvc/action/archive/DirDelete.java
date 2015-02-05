/*
 * Created on 08-jun-2005
 *
 */
package ieci.tecdoc.mvc.action.archive;

import ieci.tecdoc.mvc.action.BaseAction;
import ieci.tecdoc.mvc.form.archive.DirForm;
import ieci.tecdoc.mvc.service.ServiceArchive;
import ieci.tecdoc.mvc.util.Constantes;
import ieci.tecdoc.mvc.util.SessionHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

/**
 * @author Antonio María
 *
 */
public class DirDelete extends BaseAction{

    /* (non-Javadoc)
     * @see ieci.tecdoc.mvc.action.BaseAction#executeLogic(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    ServiceArchive serviceArchive;
    protected ActionForward executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
    	String entidad=SessionHelper.getEntidad(request);
    	
    	DirForm dirForm = (DirForm) form;
        String id = request.getParameter("id");
        
        boolean isLdap = isLdapMethod(entidad);
        
        serviceArchive = new ServiceArchive();
        int Id = Integer.parseInt(id);
        int userConnected = getUserId(request);
        
        serviceArchive.dirDelete (userConnected, Id, isLdap, entidad);
        
        ActionMessages messages = new ActionMessages();
    	ActionMessage msg = new ActionMessage("message.dir.delete");
    	messages.add(Globals.MESSAGES_KEY, msg);
    	
    	request.setAttribute("type", new Byte(Constantes.DIRECTORY));
    	saveMessages(request, messages);
        
        return mapping.findForward("success");

    }

}
