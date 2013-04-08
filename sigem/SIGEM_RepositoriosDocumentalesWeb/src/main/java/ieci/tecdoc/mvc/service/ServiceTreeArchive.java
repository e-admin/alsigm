/*
 * Created on 04-may-2005
 *
 */
package ieci.tecdoc.mvc.service;

import ieci.tecdoc.idoc.admin.api.ObjFactory;
import ieci.tecdoc.idoc.admin.api.archive.BasicDirectories;
import ieci.tecdoc.idoc.admin.api.archive.BasicDirectory;
import ieci.tecdoc.idoc.admin.api.archive.Directories;
import ieci.tecdoc.mvc.util.Constantes;
import ieci.tecdoc.mvc.util.NaryTree;
import ieci.tecdoc.mvc.util.Node;
import ieci.tecdoc.mvc.util.NodeImplBD;

import org.apache.log4j.Logger;

/**
 * @author Antonio María
 *
 */
public class ServiceTreeArchive extends ServiceTree {

    /**
     * @param tree
     */
    private static Logger logger = Logger.getLogger(ServiceTreeArchive.class);
    public ServiceTreeArchive(NaryTree tree) {
        super(tree);
        // TODO Auto-generated constructor stub
    }

    /* (non-Javadoc)
     * @see ieci.tecdoc.mvc.service.ServiceTree#addChildren(java.lang.String)
     */
    public void addChildren(int id, String entidad) throws Exception {
        
        boolean enc = searchNode (tree, id ) ;
        if (enc)
        {
            Node nodoRaiz = (Node) fin.getRoot();
	        
	        if (!nodoRaiz.getChildPrinted()){
	            
	            Directories directories = ObjFactory.createDirectories();
	            BasicDirectories basicDirectories =  directories.getChildrenFormDirectory(id, entidad);
	            int n = basicDirectories.count();
	            BasicDirectory basicDirectory;
	            for (int i = 0; i < n; i ++)
	            {
	                basicDirectory = basicDirectories.get(i);
	                String name = basicDirectory.getName();
	                int idDir = basicDirectory.getId();
	                Node dir = new NodeImplBD ();
	                dir.setTitle(name);
	                dir.setId(idDir);
	                boolean hasChildren = hasChildren(idDir, entidad);
	                dir.setHasChild(hasChildren);
	                fin.addChild(dir);
	                if (logger.isDebugEnabled())
	                    logger.debug("id: " + dir.getId() + " Name: " + dir.getTitle() );
	            }
	            nodoRaiz.setChildPrinted(true);	        
	        }
	        else{
	            if (logger.isDebugEnabled())
	                logger.debug("Nodo repetido!");
	        }
        }else
        {
            if (logger.isDebugEnabled())
            logger.error("Nodo no encontrado");
        }

    }
    
    public boolean hasChildren(int id, String entidad) throws Exception {
        boolean hasChildren = false;
        Directories directories = ObjFactory.createDirectories();
        BasicDirectories basicDirectories = directories.getChildrenFormDirectory(id, entidad);
        int n = basicDirectories.count();
        if (n > 0)
            hasChildren = true;
        
        return hasChildren;
    }

    /* (non-Javadoc)
     * @see ieci.tecdoc.mvc.service.ServiceTree#print(java.lang.StringBuffer, ieci.tecdoc.mvc.util.NaryTree)
     */
    public void print (StringBuffer sbuff, NaryTree tree) {
        
        Node root = (Node) tree.getRoot();
        String title = root.getTitle();
        String id = String.valueOf(root.getId());
         
        boolean tieneHijos = root.getHasChild();
        
        int n = tree.getNumChild(); // Num de hijos del arbol cargado en memoria (Arbol parcial del ldap)
        
        if (tree.isRoot()){
            creaNodoRaiz (sbuff, title, Constantes.TOKEN_ICON_ARCH_ROOT);
            for (int i = 0; i < n; i++){
                print(sbuff, tree.getChild(i));
            }
        }else
        {
	        NaryTree padre = tree.getParent();
	        creaNodo  (sbuff, title, id, Constantes.TOKEN_ICON_ARCH_DIR);
	        Node nodoPadre = (Node) padre.getRoot();
	        
	        String idPadre = String.valueOf(nodoPadre.getId());
	        
	        if (padre.isRoot()){
	            raizAddChild (sbuff, id);
	        }
	        else{
	        	anyadeHijo (sbuff, idPadre, id );
	        }
	        if (n > 0) { // Si tiene hijos , sigue mostrandolos
		        for (int i = 0; i < n; i++)
		            print(sbuff, tree.getChild(i));
		        }else // Crea un hijo tonto
		        {
		            if (tieneHijos)
		            	anyadeTonto(sbuff, id);
		        }
	    	}
    }


} // fin class
