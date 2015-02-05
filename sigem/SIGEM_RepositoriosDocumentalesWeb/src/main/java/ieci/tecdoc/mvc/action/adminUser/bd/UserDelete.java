/*
 * Created on 24-mar-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ieci.tecdoc.mvc.action.adminUser.bd;

import ieci.tecdoc.core.exception.IeciTdException;
import ieci.tecdoc.idoc.admin.api.ObjFactory;
import ieci.tecdoc.idoc.admin.api.user.User;
import ieci.tecdoc.idoc.admin.api.user.UserAccess;
import ieci.tecdoc.idoc.admin.internal.Defs;
import ieci.tecdoc.mvc.action.BaseAction;
import ieci.tecdoc.mvc.error.MvcError;
import ieci.tecdoc.mvc.form.adminUser.bd.UserForm;
import ieci.tecdoc.mvc.service.adminUser.ServiceCertificate;
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
public class UserDelete extends BaseAction{
    private static Logger logger = Logger.getLogger(UserDelete.class);
    int userId;
    
    public ActionForward executeLogic(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception{
    	
    	String entidad=SessionHelper.getEntidad(request);
    	
    	UserForm userForm= (UserForm) form;
    	userId = getUserId(request);
    	int id = userForm.getId();
    	
    	UserAccess userAccess = ObjFactory.createUserAccess(); 
    	boolean userCanDeleteUser = userAccess.userCanDeleteUser(userId, id, entidad);
        if (!userCanDeleteUser)
            throw new IeciTdException(String.valueOf(MvcError.EC_NOT_CAN_DELETE_USER), null);
    	
    	User user = ObjFactory.createUser(userId, Defs.NULL_ID);
    	user.load(id, entidad);
    	user.delete(entidad);
    	if (useCertificate()){
    		ServiceCertificate service = ServiceCertificate.getInstance();
            service.deleteIdCert(id, entidad);	
    	}
        
        if (logger.isDebugEnabled())
    	    logger.debug("Borrado usuario con id: " + id);

    	ActionMessages messages = new ActionMessages();
    	ActionMessage msg = new ActionMessage("message.user.delete");
    	messages.add(Globals.MESSAGES_KEY, msg);
    	
    	request.setAttribute("tipo", String.valueOf(Constantes.PERSON) );
    	saveMessages(request, messages);
    	
    	return mapping.findForward("success");
    }
    
    
}
