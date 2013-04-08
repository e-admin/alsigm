/*
 * Created on 04-may-2005
 *
 */
package ieci.tecdoc.mvc.service;

import ieci.tecdoc.idoc.admin.api.user.Department;
import ieci.tecdoc.idoc.admin.api.user.Departments;
import ieci.tecdoc.mvc.util.Constantes;
import ieci.tecdoc.mvc.util.NaryTree;
import ieci.tecdoc.mvc.util.Node;
import ieci.tecdoc.mvc.util.NodeImplBD;

import org.apache.log4j.Logger;

/**
 * @author Antonio María
 *
 */
public class ServiceTreeDept extends ServiceTree {

    /**
     * @param tree
     */
    private static Logger logger = Logger.getLogger(ServiceTreeDept.class);
    public ServiceTreeDept(NaryTree tree) {
        super(tree);
        // TODO Auto-generated constructor stub
    }

    /* (non-Javadoc)
     * @see ieci.tecdoc.mvc.service.ServiceTree#addChildren(java.lang.String)
     */
    public void addChildren(int id, String entidad) throws Exception {
        
        //boolean enc = buscaNodo (tree, id);
        boolean enc = searchNode(tree, id);
        if (enc)
        {
            Node nodoRaiz = (Node) fin.getRoot();
	        
	        if (!nodoRaiz.getChildPrinted()){
	            
	            Departments depts= new Departments();
	            depts.loadLite(id, entidad);
	            int numDptos = depts.count();
	            for (int i = 0; i < numDptos; i ++ )
	            {
	                Department objDept = depts.getDepartment(i);
	                int idDept = objDept.getId();
	                //objDept.load(idDept);
	                String name = objDept.getName();
	                boolean hasChild = hasChildren(idDept, entidad);
	                Node depto = new NodeImplBD ();
	                depto.setId(idDept);
	                depto.setTitle(name);
	                depto.setHasChild(hasChild);
	                fin.addChild(depto);
	            }
	            nodoRaiz.setChildPrinted(true);
	        }
	        else
	            ; // System.out.println ("nodo repetido!");
        }else
        {
            logger.error(" Nodo no encontrado");
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
        
        int n = tree.getNumChild(); // Num de hijos del arbol cargado en memoria (Arbol parcial del ldap)
        
        if (tree.isRoot()){
            creaNodoRaiz (sbuff, nombre, Constantes.TOKEN_ICON_DEPARTAMENTS);
            for (int i = 0; i < n; i++){
                print(sbuff, tree.getChild(i));
            }
        }else
        {
            
	        NaryTree padre = tree.getParent();
	        creaNodo  (sbuff, nombre, id, Constantes.TOKEN_ICON_DEPARTAMENTS);
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
    public boolean hasChildren(int id, String entidad) throws Exception
    {
        boolean tieneHijos = false;
        Departments depts=new Departments();
        depts.loadLite(id, entidad);
        int numDptos = depts.count();
        if (numDptos > 0 )
            tieneHijos = true;
        return tieneHijos ;
    }
}
