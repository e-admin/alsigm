/*
 * Created on 04-may-2005
 *
 */
package ieci.tecdoc.mvc.action.archive;

import ieci.tecdoc.idoc.admin.api.ObjFactory;
import ieci.tecdoc.idoc.admin.api.system.SystemCfg;
import ieci.tecdoc.mvc.action.BaseAction;
import ieci.tecdoc.mvc.service.ServiceTree;
import ieci.tecdoc.mvc.service.ServiceTreeArchive;
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
public class DirTree extends BaseAction {

    /* (non-Javadoc)
     * @see ieci.tecdoc.mvc.action.BaseAction#executeLogic(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    private static Logger logger = Logger.getLogger(DirTree.class);
    NaryTree tree;
    ServiceTree serviceTree;
    protected ActionForward executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
    	String entidad=SessionHelper.getEntidad(request);
    	String id = request.getParameter("id");
        
        boolean borraCookies = false;

        if (id == null )
        {
            SystemCfg systemCfg = ObjFactory.createSystem();
            String rootNameArchs = systemCfg.getRootNameArchs(entidad);
            
            Node nodoRaiz = new NodeImplBD();
            nodoRaiz.setTitle(rootNameArchs);
            nodoRaiz.setId(0);
            
            //NodoBasic nodoRaiz = new NodoBasic("Warda", 0);
            nodoRaiz.setHasChild (true);
            
            tree=new NaryTree(null,nodoRaiz);
            serviceTree = new ServiceTreeArchive(tree);
            
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

        return mapping.findForward("success");
    }

}
