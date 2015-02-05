/*
 * Created on 06-abr-2005
 *
 */
package ieci.tecdoc.mvc.action.adminUser.ldap;

import ieci.tecdoc.core.db.DBSessionManager;
import ieci.tecdoc.core.db.DbConnection;
import ieci.tecdoc.core.ldap.LdapAttribute;
import ieci.tecdoc.core.ldap.LdapConnCfg;
import ieci.tecdoc.core.ldap.LdapConnection;
import ieci.tecdoc.idoc.admin.api.ObjFactory;
import ieci.tecdoc.idoc.admin.api.user.LdapUser;
import ieci.tecdoc.mvc.action.BaseAction;
import ieci.tecdoc.mvc.util.SessionHelper;
import ieci.tecdoc.sbo.config.CfgMdoConfig;
import ieci.tecdoc.sbo.uas.ldap.UasConfigUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Antonio María
 *
 */
public class UserNew extends BaseAction{

    /* (non-Javadoc)
     * @see ieci.tecdoc.mvc.action.BaseAction#executeLogic(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    private static Logger logger = Logger.getLogger(UserNew.class);
    protected ActionForward executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
    	String entidad=SessionHelper.getEntidad(request);
        
    	LdapConnCfg   connCfg = null; 
        String guid = request.getParameter("guid");

        DbConnection.open(DBSessionManager.getSession(entidad));
	    LdapConnection ldapConn = new LdapConnection();
	    connCfg = UasConfigUtil.createLdapConnConfig(entidad);
	    ldapConn.open(connCfg);
        String Guid=LdapAttribute.getGuidAttributeName(ldapConn).toLowerCase(); //segun motor
        
	    String rootuser=UasConfigUtil.createUasAuthConfig(entidad).getUserStart();
	    
	    LdapUser user = ObjFactory.createLdapUser(0);
	    String guid_m=ieci.tecdoc.core.ldap.LdapBasicFns.formatGuid(ldapConn,guid);
	    user.createFromLdapUser(rootuser,Guid,guid_m, entidad);
	    user.store(entidad);
	    
	    // HttpSession session = request.getSession(false);
	    
	    // String userAttName = session.getAttribute("userAttName").toString();

	    // Añado el certificado digital, cuando se modifica el usuario y no cuando se da de alta
	    /*
        String idCert = request.getParameter("idCert");
        ServiceCertificate service = ServiceCertificate.getInstance();
	    service.addIdCert(user.getId(),idCert,userAttName);
	    */
	    
	    if (logger.isDebugEnabled())
            logger.debug("Dado de alta nuevo usuario: " + user.getFullName() + "con id: " + user.getId());
        request.setAttribute("guid",user.getGuid());
        
        return (mapping.findForward("success"));
    }
    
    

}
