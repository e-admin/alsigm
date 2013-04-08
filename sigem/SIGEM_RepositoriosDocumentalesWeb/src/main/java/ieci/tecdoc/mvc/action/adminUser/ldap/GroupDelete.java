/*
 * Created on 24-mar-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ieci.tecdoc.mvc.action.adminUser.ldap;

import ieci.tecdoc.core.exception.IeciTdException;
import ieci.tecdoc.idoc.admin.api.ObjFactory;
import ieci.tecdoc.idoc.admin.api.user.LdapGroup;
import ieci.tecdoc.idoc.admin.api.user.UserDefs;
import ieci.tecdoc.mvc.action.BaseAction;
import ieci.tecdoc.mvc.dto.access.UserConnectedDTO;
import ieci.tecdoc.mvc.error.MvcError;
import ieci.tecdoc.mvc.form.adminUser.bd.BasicForm;
import ieci.tecdoc.mvc.util.Constantes;
import ieci.tecdoc.mvc.util.SessionHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

/**
 * @author antmaria
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GroupDelete extends BaseAction{
    private static Logger logger = Logger.getLogger(GroupDelete.class);
    
    
    public ActionForward executeLogic(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception{
    	
    	String entidad=SessionHelper.getEntidad(request);
    	
    	BasicForm basicForm = (BasicForm) form;
    	String guid = basicForm.getGuid();
    	
    	int userId = getUserId(request);
    	
    	
    	LdapGroup group = ObjFactory.createLdapGroup(userId);
    	group.loadFromGuid(guid, entidad);
    	int groupId = group.getId();
    	
    	HttpSession session = request.getSession(false);
    	UserConnectedDTO user = (UserConnectedDTO) session.getAttribute(Constantes.TOKEN_USER_CONNECTED);

    	boolean userCanDeleteGroup =  ( user.getUserProfile().intValue() ==  UserDefs.PROFILE_SUPERUSER );
    	ActionMessages messages = new ActionMessages();
    	try {
    	    if (!userCanDeleteGroup)
                throw new IeciTdException(String.valueOf(MvcError.EC_NOT_CAN_DELETE_GROUP), null);
        	group.delete(entidad);
            if (logger.isDebugEnabled())
        	    logger.debug("Borrado grupo con guid: " + guid);

        	
        	ActionMessage msg = new ActionMessage("message.group.delete");
        	messages.add(Globals.MESSAGES_KEY, msg);
        	
        	saveMessages(request, messages);
        	
        	return mapping.findForward("success");
    	}catch (IeciTdException ex){
    	    String errorCode = ex.getErrorCode();
    	    if (ex.getErrorCode().equals(String.valueOf(MvcError.EC_NOT_CAN_DELETE_GROUP)))
    	    {
    	        ActionMessage message  =  new ActionMessage(errorCode);
                messages.add(ActionMessages.GLOBAL_MESSAGE, message);
                
                request.setAttribute("bundle", Constantes.MESSAGES_GENERAL_ERRORS);
    	        request.setAttribute("guid",guid);
    	        saveMessages(request, messages);
    	        
    	        return mapping.findForward("failure");
    	    }
    	    else
    	        throw ex;
    	}
    	
        

    }

}
