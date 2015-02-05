/*
 * Created on 04-abr-2005
 *
 */
package ieci.tecdoc.mvc.action.adminUser.ldap;

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
import ieci.tecdoc.sbo.uas.ldap.UasAuthConfig;
import ieci.tecdoc.sbo.uas.ldap.UasConfigUtilLdap;

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
public class GroupTree extends BaseAction {

    /* (non-Javadoc)
     * @see ieci.tecdoc.mvc.action.BaseAction#executeLogic(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    private static Logger logger = Logger.getLogger(GroupTree.class);
    protected ActionForward executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
    	String entidad=SessionHelper.getEntidad(request);
        
        String id = request.getParameter("id");
        String action = request.getParameter("action");
        
        boolean borraCookies = false;
        
        ServiceTree serviceTree = null;
        NaryTree tree;
        if (id == null)
        {
    		LdapConnection ldapConn = new LdapConnection();

            LdapConnCfg connCfg = UasConfigUtilLdap.createLdapConnConfig(entidad);
            ldapConn.open(connCfg);
            LdapURL Ldap_url = new LdapURL(ldapConn.getUrl());
            String ldapRootDn = Ldap_url.getDN();
            
            
     	    UasAuthConfig  authCfg = UasConfigUtilLdap.createUasAuthConfig(entidad);	
            String ldapRootDnParent = authCfg.getGroupStart();
            
            Node nodoRaiz = new NodeImplLDAP();
            tree=new NaryTree(null,nodoRaiz);
            int maxChildrenLdap = ((Integer)request.getSession().getServletContext().getAttribute(Constantes.MAX_CHILDREN_LDAP) ).intValue();
            
            serviceTree = new ServiceTreeLdap (tree, maxChildrenLdap, Constantes.LDAP_TYPE_TREE_GROUP);
            request.getSession(false).setAttribute(Constantes.LDAP_SERVICE_GROUP_TREE, serviceTree);
            
            
            nodoRaiz.setTitle(ldapRootDnParent);
            int hashCode = serviceTree.getHashCode(ldapRootDn);
            nodoRaiz.setId(hashCode);
            ServiceLdap serviceLdap = new ServiceLdap(maxChildrenLdap, Constantes.LDAP_TYPE_TREE_GROUP);
            
            boolean hasChildren = serviceLdap.hasChildLdap("",entidad);
            nodoRaiz.setHasChild( hasChildren );
            
            serviceTree.addChildren(hashCode, entidad);
            request.getSession(false).setAttribute("treeGroup", tree);
            
            borraCookies = true;
            
        }
        else{
            
            tree = (NaryTree) request.getSession(false).getAttribute("treeGroup");
            if (tree == null){
                logger.error("La estructura de Arbol deberia estar en memoria");
                throw new Exception("La estructura de Arbol deberia estar en memoria");
            }
            //serviceTree = new ServiceTreeLdap (tree);
            serviceTree = (ServiceTree)request.getSession(false).getAttribute(Constantes.LDAP_SERVICE_GROUP_TREE);
            serviceTree.addChildren(Integer.parseInt(id), entidad);
            //session.setAttribute("tree", tree);
        }
        
        String arbol = serviceTree.getTreeString();
        request.setAttribute("tree",arbol);
        Boolean b = new Boolean(borraCookies);
        
        request.setAttribute("borraCookies",b);
        request.setAttribute("typeTree", String.valueOf(Constantes.LDAP_TYPE_TREE_GROUP));

        if (action == null )
            return mapping.findForward("success");
        else
            return mapping.findForward("addPerms");
    }

    
}
