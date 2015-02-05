/*
 * Created on 18-may-2005
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
public class DirNew extends BaseAction{

    /* (non-Javadoc)
     * @see ieci.tecdoc.mvc.action.BaseAction#executeLogic(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    ServiceArchive serviceArchive;
    protected ActionForward executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
    	String entidad=SessionHelper.getEntidad(request);
    	
    	DirForm dirForm = (DirForm) form;
        String id = request.getParameter("id");
        int parentId = Integer.parseInt(id);
        
        String submitted = request.getParameter("submitted");
        boolean isLdap = isLdapMethod(entidad);
        int userConnected = getUserId(request);
        ActionForward forward;
        if (submitted == null)
        {
            dirForm.executeReset(mapping, request);
            serviceArchive = new ServiceArchive();
            serviceArchive.newDir(dirForm, userConnected, parentId, isLdap, false, entidad);
            forward = mapping.findForward("input");
        }
        else
        {
            serviceArchive.saveDir(dirForm,true, entidad);
            
            ActionMessages messages = new ActionMessages();
        	ActionMessage msg = new ActionMessage("message.dir.new");
        	messages.add(Globals.MESSAGES_KEY, msg);
        	
        	request.setAttribute("type", new Byte(Constantes.DIRECTORY));
        	saveMessages(request, messages);
        	forward = mapping.findForward("success");
        }
        return forward;
    }

}
