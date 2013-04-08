/*
 * Created on 24-mar-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ieci.tecdoc.mvc.action.adminUser.bd;

import ieci.tecdoc.core.exception.IeciTdException;
import ieci.tecdoc.idoc.admin.api.ObjFactory;
import ieci.tecdoc.idoc.admin.api.user.Group;
import ieci.tecdoc.idoc.admin.api.user.UserAccess;
import ieci.tecdoc.mvc.action.BaseAction;
import ieci.tecdoc.mvc.error.MvcError;
import ieci.tecdoc.mvc.form.adminUser.bd.BasicForm;
import ieci.tecdoc.mvc.util.Constantes;
import ieci.tecdoc.mvc.util.SessionHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    	int id = basicForm.getId();
    	
    	int userId = getUserId(request);
    	Group group = ObjFactory.createGroup(userId);
    	
    	UserAccess userAccess = ObjFactory.createUserAccess(); 
    	boolean userCanDeleteGroup = userAccess.userCanDeleteGroup(userId, id, entidad);
        if (!userCanDeleteGroup)
            throw new IeciTdException(String.valueOf(MvcError.EC_NOT_CAN_DELETE_GROUP), null);
        
        
    	group.load(id, entidad);
    	group.delete(entidad);
    	
        if (logger.isDebugEnabled())
    	    logger.debug("Borrado grupo con id: " + id);

    	ActionMessages messages = new ActionMessages();
    	ActionMessage msg = new ActionMessage("message.group.delete");
    	messages.add(Globals.MESSAGES_KEY, msg);
    	
    	request.setAttribute("tipo", String.valueOf(Constantes.GROUP) );
    	saveMessages(request, messages);
    	
    	return mapping.findForward("success");
    }

}
