/*
 * Created on 31-mar-2005
 *
 */
package ieci.tecdoc.mvc.action.adminUser.bd;

import ieci.tecdoc.mvc.action.BaseAction;
import ieci.tecdoc.mvc.service.ServiceTree;
import ieci.tecdoc.mvc.service.ServiceTreeGroup;
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
public class GroupTree extends BaseAction {

    private static Logger logger = Logger.getLogger(GroupTree.class);
    //List grupos;
    NaryTree tree;
    ServiceTree serviceTree;
    protected ActionForward executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
    	String entidad=SessionHelper.getEntidad(request);
        String action = request.getParameter("action");
        
        Node nodoRaiz = new NodeImplBD();
        
        tree = new NaryTree(null,nodoRaiz);
        serviceTree = new ServiceTreeGroup(tree);
        nodoRaiz.setId(0);
        boolean hasChild = serviceTree.hasChildren(0, entidad);
        nodoRaiz.setHasChild(hasChild);
        if (hasChild)
            serviceTree.addChildren(0, entidad);
        nodoRaiz.setTitle("Grupos");
        
        boolean borraCookies = false;
        
        String arbol = serviceTree.getTreeString();
        request.setAttribute("treeString",arbol);
        Boolean b = new Boolean(borraCookies);
        
        /*
        List grupos = getGroupList();
        Iterator it = grupos.iterator();
        
        StringBuffer sbuff = new StringBuffer();
        creaNodoRaiz(sbuff,"Grupos");
        while (it.hasNext())
        {
            TipoGrupo obj = (TipoGrupo) it.next();
            String nombre = obj.getNombre();
            String id = obj.getId();
            creaNodo(sbuff, nombre, id);
            añadeHijo(sbuff, id);
        }
        String arbol = sbuff.toString(); 
        request.setAttribute("tree", arbol);
        */
        if (action != null )
            return mapping.findForward("addPerms");
        else
            return mapping.findForward("success");
    
    }
    
    /*
    private List getGroupList() throws Exception{
        
        List lista = new ArrayList();
        
        Groups grupos = new Groups();
        grupos.loadLite();
        int n = grupos.count();
        Group grupo;
        for (int i = 0; i < n; i ++){
            grupo = grupos.getGroup(i);
            TipoGrupo objGrupo = new TipoGrupo(String.valueOf(grupo.getId()),grupo.getName());
            lista.add(objGrupo);
            if (logger.isDebugEnabled())
            {
                logger.debug(objGrupo.toString());
                
            }

        }
        return lista;
    }
    */
    /*
    public void creaNodoRaiz(StringBuffer sbuff, String titulo){
        String sentencia= "\n var tree = new WebFXTree('"+ titulo +"','javascript:nodoRaizEvent();','','"+Constantes.TOKEN_ICON_GROUP_BD+"','"+Constantes.TOKEN_ICON_GROUP_BD+"',''); \n";
        sbuff.append(sentencia);
    }
    public void creaNodo(StringBuffer sbuff, String titulo, String id){
        String sentencia =
        "var nodo" + id + " = new WebFXTreeItem('"+titulo+"','javascript:carga("+id+")','','"+Constantes.TOKEN_ICON_GROUP_BD+"','"+Constantes.TOKEN_ICON_GROUP_BD+"','"+id+"'); \n";
        sbuff.append(sentencia);
    }
    public void añadeHijo(StringBuffer sbuff, String id)
    {
        String sentencia = "tree.add(nodo"+ id +"); \n";
        sbuff.append(sentencia);
    }
    
    class TipoGrupo
    {
        String id;
        String nombre;
        TipoGrupo(String id, String nombre){
            this.id = id;
            this.nombre = nombre;
        }
        String getId()
        {
            return id;
        }
        String getNombre()
        {
            return nombre;
        }
        public String toString()
        {
            String s =  "Grupo: " + id + " "+ nombre;
            return s;
        }
    }
    */

}
