/*
 * Created on 04-abr-2005
 *
 */
package ieci.tecdoc.mvc.action.adminUser.ldap;

import ieci.tecdoc.core.db.DBSessionManager;
import ieci.tecdoc.core.db.DbConnection;
import ieci.tecdoc.core.ldap.LdapConnCfg;
import ieci.tecdoc.core.ldap.LdapConnection;
import ieci.tecdoc.mvc.action.BaseAction;
import ieci.tecdoc.mvc.service.ServiceTree;
import ieci.tecdoc.mvc.service.ServiceTreeLdap;
import ieci.tecdoc.mvc.service.adminUser.ldap.ServiceLdap;
import ieci.tecdoc.mvc.util.Constantes;
import ieci.tecdoc.mvc.util.NaryTree;
import ieci.tecdoc.mvc.util.Node;
import ieci.tecdoc.mvc.util.NodeImplLDAP;
import ieci.tecdoc.mvc.util.SessionHelper;
import ieci.tecdoc.sbo.config.CfgMdoConfig;
import ieci.tecdoc.sbo.uas.ldap.UasConfigUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.sun.jndi.ldap.LdapURL;

/**
 * @author Antonio María
 *
 */
public class UserTree extends BaseAction {

    /* (non-Javadoc)
     * @see ieci.tecdoc.mvc.action.BaseAction#executeLogic(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    private static Logger logger = Logger.getLogger(UserTree.class);
    NaryTree tree;
    ServiceTree serviceTree;
    public static String LdapRootDn;
    ServiceLdap serviceLdap;
    
    protected ActionForward executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
    	String entidad=SessionHelper.getEntidad(request);
        
        String id = request.getParameter("id");
        String action = request.getParameter("action");
        
        HttpSession session = request.getSession(false);
        
        boolean borraCookies = false;
            
        if (id == null)
        {
    		LdapConnection ldapConn = new LdapConnection();

    		DbConnection.open(DBSessionManager.getSession(entidad));
            LdapConnCfg connCfg = UasConfigUtil.createLdapConnConfig(entidad);
            ldapConn.open(connCfg);
            LdapURL Ldap_url = new LdapURL(ldapConn.getUrl());
            LdapRootDn = Ldap_url.getDN();
            
            Node nodoRaiz = new NodeImplLDAP();
            tree=new NaryTree(null,nodoRaiz);
            int maxChildrenLdap = ((Integer)request.getSession().getServletContext().getAttribute(Constantes.MAX_CHILDREN_LDAP) ).intValue();
            serviceTree = new ServiceTreeLdap (tree, maxChildrenLdap, entidad);
            
            nodoRaiz.setTitle(LdapRootDn);
            int hashCode = serviceTree.getHashCode(LdapRootDn);
            nodoRaiz.setId(hashCode);
            serviceLdap = new ServiceLdap(maxChildrenLdap, entidad);
            
            boolean hasChildren = serviceLdap.hasChildLdap("");
            nodoRaiz.setHasChild( hasChildren );
            
            serviceTree.addChildren(hashCode, entidad);
            session.setAttribute("tree", tree);
            
            borraCookies = true;
            
        }
        else{
            
            tree = (NaryTree) session.getAttribute("tree");
            if (tree == null){
                logger.error("La estructura de Arbol deberia estar en memoria");
                throw new Exception("La estructura de Arbol deberia estar en memoria");
            }
            //serviceTree = new ServiceTreeLdap (tree);
            
            serviceTree.addChildren(Integer.parseInt(id), entidad);
            //session.setAttribute("tree", tree);
        }
        
        String arbol = serviceTree.getTreeString();
        request.setAttribute("tree",arbol);
        Boolean b = new Boolean(borraCookies);
        
        request.setAttribute("borraCookies",b);

        if (action == null )
            return mapping.findForward("success");
        else
            return mapping.findForward("addPerms");
    }

    
}
