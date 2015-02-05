/*
 * Created on 06-abr-2005
 *
 */
package ieci.tecdoc.mvc.action.adminUser.ldap;

import ieci.tecdoc.core.ldap.LdapAttribute;
import ieci.tecdoc.core.ldap.LdapConnCfg;
import ieci.tecdoc.core.ldap.LdapConnection;
import ieci.tecdoc.idoc.admin.api.ObjFactory;
import ieci.tecdoc.idoc.admin.api.user.LdapGroup;
import ieci.tecdoc.mvc.action.BaseAction;
import ieci.tecdoc.mvc.util.SessionHelper;
import ieci.tecdoc.sbo.uas.ldap.UasConfigUtil;

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
 * @author Antonio María
 *
 */
public class GroupNew extends BaseAction{

    /* (non-Javadoc)
     * @see ieci.tecdoc.mvc.action.BaseAction#executeLogic(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    private static Logger logger = Logger.getLogger(UserNew.class);
    protected ActionForward executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
    	String entidad=SessionHelper.getEntidad(request);
    	
    	LdapConnCfg   connCfg = null; 
        String guid = request.getParameter("guid");

        //DbConnection.open(DBSessionManager.getSession(entidad));
	    LdapConnection ldapConn = new LdapConnection();
	    connCfg = UasConfigUtil.createLdapConnConfig(entidad);
	    
	    try {
		    ldapConn.open(connCfg);
		    
		    String rootuser=UasConfigUtil.createUasAuthConfig(entidad).getUserStart();
		    
		    // Segun motor de LDAP
	        String Guid=LdapAttribute.getGuidAttributeName(ldapConn).toLowerCase(); 
	        String guid_m=ieci.tecdoc.core.ldap.LdapBasicFns.formatGuid(ldapConn,guid);
	        
	        LdapGroup ldapGroup = ObjFactory.createLdapGroup(0);
	        ldapGroup.createFromLdapGroup(rootuser,Guid,guid_m, entidad);
	        
	        ldapGroup.store(entidad);
	        
	        if (logger.isDebugEnabled())
	            logger.debug("Grupo dado de alta: " + ldapGroup.getFullName() + " con " + " id: " + ldapGroup.getId());
	    }
	    finally {
	    	try {
				if (ldapConn != null )
					ldapConn.close();
			}catch(Exception e){ }
	    }

        request.setAttribute("guid", guid);
        
        ActionMessages messages = new ActionMessages();
        
    	ActionMessage msg = new ActionMessage("message.group.darDeAlta");
    	messages.add(Globals.MESSAGES_KEY, msg);
    	
    	saveMessages(request, messages);
        
        return mapping.findForward("success");
    }

}
