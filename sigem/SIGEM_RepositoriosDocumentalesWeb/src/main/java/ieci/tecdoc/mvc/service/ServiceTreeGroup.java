/*
 * Created on 04-may-2005
 *
 */
package ieci.tecdoc.mvc.service;

import ieci.tecdoc.idoc.admin.api.user.Group;
import ieci.tecdoc.idoc.admin.api.user.Groups;
import ieci.tecdoc.mvc.util.Constantes;
import ieci.tecdoc.mvc.util.NaryTree;
import ieci.tecdoc.mvc.util.Node;
import ieci.tecdoc.mvc.util.NodeImplBD;

/**
 * @author Antonio María
 *
 */
public class ServiceTreeGroup extends ServiceTree{

    /**
     * @param tree
     */
    public ServiceTreeGroup(NaryTree tree) {
        super(tree);
        // TODO Auto-generated constructor stub
    }

    /* (non-Javadoc)
     * @see ieci.tecdoc.mvc.service.ServiceTree#addChildren(int)
     */
    public void addChildren(int id, String entidad) throws Exception {
        
        Groups grupos = new Groups();
        grupos.loadLite(entidad);
        int n = grupos.count();
        Group grupo;
        for (int i = 0; i < n; i ++){
            grupo = grupos.getGroup(i);
            Node group = new NodeImplBD ();
            group.setId(grupo.getId());
            group.setTitle(grupo.getName());
            tree.addChild(group);
        }
    }

    /* (non-Javadoc)
     * @see ieci.tecdoc.mvc.service.ServiceTree#print(java.lang.StringBuffer, ieci.tecdoc.mvc.util.NaryTree)
     */
    public void print(StringBuffer sbuff, NaryTree tree) {
        
        Node root = (Node) tree.getRoot();
        String nombre = root.getTitle();
        String id = String.valueOf(root.getId());
        boolean hasChild = root.getHasChild();
        
        int n = tree.getNumChild(); // Num de hijos del arbol
        
        if (tree.isRoot()){
            creaNodoRaiz (sbuff, nombre, Constantes.TOKEN_ICON_GROUP_BD);
            for (int i = 0; i < n; i++){
                print(sbuff, tree.getChild(i));
            }
        }else
        {
	        NaryTree padre = tree.getParent();
	        creaNodo  (sbuff, nombre, id, Constantes.TOKEN_ICON_GROUP_BD);
	        Node nodoPadre = (Node) padre.getRoot();
	        
	        String idPadre = String.valueOf(nodoPadre.getId());
	        
	        if (padre.isRoot()){
	            raizAddChild (sbuff, id);
	        }
	        else{
	            //idPadre = nodoPadre.getHashCode();
	        	anyadeHijo (sbuff, idPadre, id );
	        }
	        if (n > 0) { // Si tiene hijos , sigue mostrandolos
		        for (int i = 0; i < n; i++)
		            print(sbuff, tree.getChild(i));
		        }else // Crea un hijo tonto
		        {
		            if (hasChild)
		            	anyadeTonto(sbuff, id);
		        }
	    	}


        
    }

    /* (non-Javadoc)
     * @see ieci.tecdoc.mvc.service.ServiceTree#hasChildren(int)
     */
    public boolean hasChildren(int id, String entidad) throws Exception {
        Groups grupos = new Groups();
        grupos.loadLite(entidad);
        int n = grupos.count();
        boolean hasChildren = false;
        if (n > 0)
            hasChildren = true;
        return hasChildren;
    }

}
