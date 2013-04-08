/*
 * Created on 09-jun-2005
 *
 */
package ieci.tecdoc.mvc.action.archive;

import ieci.tecdoc.core.exception.IeciTdException;
import ieci.tecdoc.idoc.admin.api.ObjFactory;
import ieci.tecdoc.idoc.admin.api.archive.Archive;
import ieci.tecdoc.idoc.admin.api.archive.ArchiveAccess;
import ieci.tecdoc.idoc.admin.internal.Defs;
import ieci.tecdoc.mvc.action.BaseAction;
import ieci.tecdoc.mvc.error.MvcError;
import ieci.tecdoc.mvc.form.archive.ArchiveForm;
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
public class ArchiveDelete extends BaseAction{

    /* (non-Javadoc)
     * @see ieci.tecdoc.mvc.action.BaseAction#executeLogic(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    protected ActionForward executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
    	String entidad=SessionHelper.getEntidad(request);
    	
    	ArchiveForm archiveForm = (ArchiveForm) form;
        
        int archId = archiveForm.getGeneral().getId();
        int userId = getUserId(request);
        ArchiveAccess archiveAccess = ObjFactory.createArchiveAccess();
        
        boolean userCanDeleteArch = archiveAccess.userCanDeleteArch(userId, archId, entidad);
        if (!userCanDeleteArch)
            throw new IeciTdException(String.valueOf(MvcError.EC_NOT_ENOUGH_PERMISSIONS), null);
        
        boolean isLdap = isLdapMethod(entidad);
        Archive archive = ObjFactory.createArchive(userId, Defs.NULL_ID, isLdap);
        int parentId = archive.getParentId();
        archive.delete(archId, entidad);
        
        ActionMessages messages = new ActionMessages();
    	ActionMessage msg = new ActionMessage("message.archive.delete");
    	messages.add(Globals.MESSAGES_KEY, msg);
    	request.setAttribute("parentId", new Integer(parentId));
    	saveMessages(request, messages);
        
        return mapping.findForward("success");
    }

}
