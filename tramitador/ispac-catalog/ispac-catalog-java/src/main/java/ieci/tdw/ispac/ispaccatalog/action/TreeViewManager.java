package ieci.tdw.ispac.ispaccatalog.action;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.ispaclib.bean.TreeNode;
import ieci.tdw.ispac.ispaclib.bean.TreeView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action base del que heredan los action que se encargan de gestionar los arboles de deposito y fondos 
 */
public abstract class TreeViewManager extends BaseDispatchAction {

   public abstract ActionForward crearVista(ActionMapping mappings, ActionForm form,
            HttpServletRequest request, HttpServletResponse response, SessionAPI session) throws ISPACException ;

    public abstract ActionForward getForwardVistaNodo(TreeNode node, ActionMapping mappings,
            HttpServletRequest request, SessionAPI sessionApi) throws ISPACException ;
    
    public ActionForward showSelectedNode(ActionMapping mappings, ActionForm form,
            HttpServletRequest request, HttpServletResponse response, SessionAPI sessionApi) throws ISPACException  {
        String viewName = request.getParameter("viewName");
        TreeView treeView = (TreeView) request.getSession().getAttribute(viewName);
        if (treeView != null && treeView.getSelectedNode() != null)
            return getForwardVistaNodo(treeView.getSelectedNode(), mappings, request, sessionApi);
        else
            return null;
    }
    
    public ActionForward obtenerVista(ActionMapping mappings, ActionForm form,
            HttpServletRequest request, HttpServletResponse response, SessionAPI sessionApi) throws ISPACException  {
        String viewName = (String) request.getParameter("viewName");
        //if (viewName == null) viewName = "estructuraDeposito";
        TreeView treeView = (TreeView) request.getSession().getAttribute(viewName);
        if (treeView != null)
            updateEstadoVista(treeView, 
                              request.getParameter("openNodes"), 
                              request.getParameter("closedNodes"), 
                              request.getParameter("selectedNode"),sessionApi);
        // else deberia de devolver a pagina de error porque la vista no esta
        // disponible (o crearla);
        return mappings.findForward("done");
    }   
   
    protected void updateEstadoVista(TreeView treeView, String pOpenNodes, String pClosedNodes, String pSelectedNode, SessionAPI sessionApi){
        if (pOpenNodes != null) {
            //treeView.collapse(true);
            if (pOpenNodes.length() > 0) {
                String[] openNodes = pOpenNodes.split("-");
                TreeNode nodeToExpand = null;
                for (int i = 0; i < openNodes.length; i++) {
                    nodeToExpand = treeView.getNode(openNodes[i]);
                    if (nodeToExpand != null) {
                    	nodeToExpand.setCollapsed(false,false);
                    }
                }
            }
        }
        if (pClosedNodes != null) {
           if (pClosedNodes.length() > 0) {
               String[] closedNodes = pClosedNodes.split("-");
               for (int i = 0; i < closedNodes.length; i++) {
                   TreeNode closeNode = treeView.getNode(closedNodes[i]);
                   if (closeNode != null) {
                	   closeNode.setCollapsed(true,false);
                   }
               }
           }
       }
        if (pSelectedNode != null && pSelectedNode.length() > 0)
            try {
                TreeNode selectedNode = treeView.getSelectedNode();
                if (selectedNode == null
                                || !StringUtils.equals(pSelectedNode,
                                                treeView.getSelectedNode().getNodePath())) {
                    TreeNode nodeToSelect = treeView.getNode(pSelectedNode);
                    if (nodeToSelect != null) {
                        if (treeView.getSelectedNode() != nodeToSelect)
                            treeView.setSelectedNode(nodeToSelect);
                        nodeToSelect.setVisible();
                    }
                }
            } catch (Exception e) {
                System.out.println("ERROR: " + e);
            }
    }
	
    public ActionForward expandirNodo(ActionMapping mappings, ActionForm form,
            HttpServletRequest request, HttpServletResponse response, SessionAPI sessionApi) throws ISPACException {
        String viewName = (String) request.getParameter("viewName");
        TreeView treeView = (TreeView) request.getSession().getAttribute(viewName);
        if (treeView != null) {
            TreeNode nodeToUpdate = treeView.getNode(request.getParameter("node"));
            updateEstadoVista(treeView, 
                              request.getParameter("openNodes"), 
                              request.getParameter("closedNodes"), 
                              request.getParameter("selectedNode"),sessionApi);
            if (nodeToUpdate != null)
                nodeToUpdate.setCollapsed(false, false);
        
            if (nodeToUpdate.getChilds()==null ||nodeToUpdate.getChilds().size()==0){
            	request.setAttribute("nodoNoTieneHijos", new Boolean(true));
            }
            return mappings.findForward("done");
            
        } else
            return crearVista(mappings, form, request, response, sessionApi);
    }

    /**
     * Indica si el nodo es raíz.
     * @param node Identificador del nodo.
     * @return true si el nodo es el raíz.
     */
    protected boolean isRootNode(String node)
    {
        return ((node != null) && "/".equals(node));
    }
}