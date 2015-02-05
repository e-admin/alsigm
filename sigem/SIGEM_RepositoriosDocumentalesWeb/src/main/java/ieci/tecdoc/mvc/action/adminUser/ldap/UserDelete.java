/*
 * Created on 24-mar-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ieci.tecdoc.mvc.action.adminUser.ldap;

import ieci.tecdoc.core.db.DBSessionManager;
import ieci.tecdoc.core.db.DbConnection;
import ieci.tecdoc.core.db.DbConnectionConfig;
import ieci.tecdoc.core.exception.IeciTdException;
import ieci.tecdoc.core.ldap.LdapConnCfg;
import ieci.tecdoc.core.ldap.LdapConnection;
import ieci.tecdoc.idoc.admin.api.ObjFactory;
import ieci.tecdoc.idoc.admin.api.exception.UserErrorCodes;
import ieci.tecdoc.idoc.admin.api.user.LdapUser;
import ieci.tecdoc.idoc.admin.api.user.UserDefs;
import ieci.tecdoc.idoc.admin.api.user.UserProfile;
import ieci.tecdoc.idoc.admin.api.user.UserProfiles;
import ieci.tecdoc.mvc.action.BaseAction;
import ieci.tecdoc.mvc.dto.access.UserConnectedDTO;
import ieci.tecdoc.mvc.error.MvcError;
import ieci.tecdoc.mvc.form.adminUser.ldap.UserForm;
import ieci.tecdoc.mvc.service.adminUser.ServiceCertificate;
import ieci.tecdoc.mvc.util.Constantes;
import ieci.tecdoc.mvc.util.SessionHelper;
import ieci.tecdoc.sbo.config.CfgMdoConfig;
import ieci.tecdoc.sbo.uas.ldap.UasConfigUtil;

import java.util.HashMap;
import java.util.Map;

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
public class UserDelete extends BaseAction{
    private static Logger logger = Logger.getLogger(UserDelete.class);
    int userId;
    public ActionForward executeLogic(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception{
    	
    	String entidad=SessionHelper.getEntidad(request);
    	
    	UserForm userForm= (UserForm) form;
    	String guid = userForm.getGuid();
    	
    	userId = getUserId(request);
    	int id = userForm.getId();
    	HttpSession session = request.getSession(false);
    	UserConnectedDTO user = (UserConnectedDTO) session.getAttribute(Constantes.TOKEN_USER_CONNECTED);
    	/* Estas funciones, no sirven para LDAP
    	UserAccess userAccess = ObjFactory.createUserAccess(); 
    	boolean userCanDeleteUser = userAccess.userCanDeleteUser(userId, id);
    	*/
        DbConnectionConfig dbconf=null;
        LdapConnection ldapConn = new LdapConnection();
        LdapConnCfg   connCfg = null; 
        DbConnection.open(DBSessionManager.getSession(entidad));
        connCfg = UasConfigUtil.createLdapConnConfig(entidad);
    	ldapConn.open(connCfg);
            
    	LdapUser LUser =ObjFactory.createLdapUser(userId);
    	LUser.loadFromGuid(guid, entidad);
    	
    	UserProfiles profiles=LUser.getProfiles();
        // General
        userForm.setGuid(guid);
        userForm.setId(id);
        
        Map _profiles = new HashMap();
        
        // Sistema
        UserProfile profile=profiles.getProductProfile(UserDefs.PRODUCT_SYSTEM);
        boolean userCanDeleteUser = user.getIsSystemXSuperUser()? true :  ( user.getUserProfile().intValue() ==  UserDefs.PROFILE_SUPERUSER ) && ( profile.getProfile() != UserDefs.PROFILE_SUPERUSER );
        
    	ActionMessages messages = new ActionMessages();
    	try {
    	    if (!userCanDeleteUser)
                throw new IeciTdException(String.valueOf(MvcError.EC_NOT_CAN_DELETE_USER), null);
    	    
    	    LUser.delete(entidad);
    	    
    	    ServiceCertificate service = ServiceCertificate.getInstance();
    	    service.deleteIdCert(LUser.getId(), entidad);
    	    
            if (logger.isDebugEnabled())
        	    logger.debug("Borrado usuario con guid: " + guid);

        	
        	ActionMessage msg = new ActionMessage("message.user.delete");
        	messages.add(Globals.MESSAGES_KEY, msg);
        	request.setAttribute("tipo", String.valueOf(Constantes.PERSON) );
        	saveMessages(request, messages);
        	return mapping.findForward("success");
    	}catch (IeciTdException ex){
    	    String errorCode = ex.getErrorCode();
    	    if (errorCode.equals(String.valueOf(UserErrorCodes.EC_USER_CONNECTED)))
    	    {
            	ActionMessage message  =  new ActionMessage(errorCode);
                messages.add(ActionMessages.GLOBAL_MESSAGE, message);
    	        request.setAttribute("bundle", Constantes.MESSAGES_API_ERRORS);
    	        request.setAttribute("guid",guid);
    	        saveMessages(request, messages);
    	        return mapping.findForward("failure");
    	    }
    	    else if (errorCode.equals(String.valueOf(MvcError.EC_NOT_CAN_DELETE_USER)))
    	    {
    	        ActionMessage message  =  new ActionMessage(errorCode);
                messages.add(ActionMessages.GLOBAL_MESSAGE, message);
                
                request.setAttribute("bundle", Constantes.MESSAGES_GENERAL_ERRORS);
    	        request.setAttribute("guid",guid);
    	        saveMessages(request, messages);
    	        return mapping.findForward("failure");
    	    }
    	    logger.debug(ex);
    	    throw ex;
    	}
    }

}
