/*
 * Created on 04-may-2005
 *
 */
package ieci.tecdoc.mvc.action.adminUser.bd;

import ieci.tecdoc.mvc.action.BaseAction;
import ieci.tecdoc.mvc.service.ServiceTree;
import ieci.tecdoc.mvc.service.ServiceTreeDept;
import ieci.tecdoc.mvc.util.NaryTree;
import ieci.tecdoc.mvc.util.Node;
import ieci.tecdoc.mvc.util.NodeImplBD;
import ieci.tecdoc.mvc.util.SessionHelper;

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
public class DeptTree extends BaseAction {

    /* (non-Javadoc)
     * @see ieci.tecdoc.mvc.action.BaseAction#executeLogic(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    private static Logger logger = Logger.getLogger(DeptTree.class);
    NaryTree tree;
    ServiceTree serviceTree;
    protected ActionForward executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String entidad=SessionHelper.getEntidad(request);
    	String id = request.getParameter("id");
        String action = request.getParameter("action");
        boolean borraCookies = false;

        if (id == null )
        {
            Node nodoRaiz = new NodeImplBD();
            
            tree=new NaryTree(null,nodoRaiz);
            serviceTree = new ServiceTreeDept(tree);
            nodoRaiz.setId(0);
            boolean hasChild = serviceTree.hasChildren(0, entidad);
            nodoRaiz.setHasChild(hasChild);
            nodoRaiz.setTitle("Departamentos");
            if (hasChild)
                serviceTree.addChildren(nodoRaiz.getId(), entidad);
            //session.setAttribute("tree", tree);
            borraCookies = true;
            
        }
        else{
            /*
            tree = (NaryTree) session.getAttribute("tree");
            if (tree == null){
                logger.error("La estructura de Arbol deberia estar en memoria");
                throw new Exception("La estructura de Arbol deberia estar en memoria");
            }
            serviceTree = new ServiceTreeArchive (tree);
            */
            serviceTree.addChildren(Integer.parseInt(id ), entidad);
            
             //session.setAttribute("tree", tree);
        }
        
        String arbol = serviceTree.getTreeString();
        request.setAttribute("treeString",arbol);
        Boolean b = new Boolean(borraCookies);
        request.setAttribute("borraCookies",b);
        if (action == null)
            return mapping.findForward("success");
        else
            return mapping.findForward("addPerm");
    }

}
