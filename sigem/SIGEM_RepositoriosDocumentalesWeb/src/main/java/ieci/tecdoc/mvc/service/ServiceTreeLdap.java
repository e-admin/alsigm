/*
 * Created on 04-may-2005
 *
 */
package ieci.tecdoc.mvc.service;

import ieci.tecdoc.mvc.service.adminUser.ldap.ServiceLdap;
import ieci.tecdoc.mvc.util.NaryTree;
import ieci.tecdoc.mvc.util.Node;
import ieci.tecdoc.mvc.util.NodeImplLDAP;
import ieci.tecdoc.mvc.util.ldap.Info;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * @author Antonio María
 *
 */
public class ServiceTreeLdap extends ServiceTree {

    /**
     * @param tree
     */
    private static Logger logger = Logger.getLogger(ServiceTreeLdap.class);
    private ServiceLdap serviceLdap;
    public ServiceTreeLdap(NaryTree tree, int maxChildrenLdap, String entidad) throws Exception {
        super(tree);
        serviceLdap = new ServiceLdap(maxChildrenLdap, entidad); 
        // TODO Auto-generated constructor stub
    }

    /* (non-Javadoc)
     * @see ieci.tecdoc.mvc.service.ServiceTree#addChildren(java.lang.String)
     */
    public void addChildren(int id, String entidad) throws Exception {
        boolean enc = searchNode(tree, id );
        if (enc)
        {
	        Node nodoRaiz = (Node)fin.getRoot();
	        String parent;
	        if (!nodoRaiz.getChildPrinted()){
		        if ( fin.isRoot()) // Es Nodo raiz --> "c=es"
		            parent = "";
		        else
		            parent = nodoRaiz.getTitle();
		            // List hijos = ServiceLdap.GetLdapChildList (parent);
		        	List hijos = serviceLdap.GetLdapChildList (parent);
		            Iterator it = hijos.iterator();
		            while (it.hasNext())
		            {
		                Info hijo = (Info) it.next();
		                String dn = hijo.getValor() + parent;
		                //Node hijoIesimo = new Nodo(hijo.getValor());
		                NodeImplLDAP hijoIesimo = new NodeImplLDAP();
		                hijoIesimo.setTitle(hijo.getValor());
		                
		                boolean hasChildren = serviceLdap.hasChildLdap(hijo.getValor());
		                //boolean hasChildren = ServiceLdap.hasChildLdap(hijo.getValor());
		                hijoIesimo.setHasChild(hasChildren);
		                byte type = hijo.getObjectClass();
		                hijoIesimo.setId(getHashCode(dn));
		                hijoIesimo.setType(type );
		                //hijoIesimo.setTieneHijos();
		                //hijoIesimo.setTipo(hijo.getObjectClass());
		                if (logger.isDebugEnabled())
		                    logger.debug("Hijo del Ldap: " + hijo );
		                fin.addChild(hijoIesimo);
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
            logger.debug("Nodo no encontrado");
        }


    }

    /* (non-Javadoc)
     * @see ieci.tecdoc.mvc.service.ServiceTree#print(java.lang.StringBuffer, ieci.tecdoc.mvc.util.NaryTree)
     */
    public void print(StringBuffer sbuff, NaryTree tree) {
        
        NodeImplLDAP root = (NodeImplLDAP) tree.getRoot();
        
        String dn = root.getTitle();
        String id = String.valueOf(root.getId());
        String icon = root.getIcon();
        boolean hasChildren = root.getHasChild();
        int n = tree.getNumChild(); // Num de hijos del arbol cargado en memoria (Arbol parcial del ldap)
        
        String title = getTitle(dn);
        if (tree.isRoot()){
            
            creaNodoRaiz (sbuff, title,"");
            for (int i = 0; i < n; i++){
                print(sbuff, tree.getChild(i));
            }
        }else
        {
	        NaryTree padre = tree.getParent();
	        creaNodo  (sbuff, title, id, icon);
	        Node nodoPadre = (Node) padre.getRoot();
	        
	        String idPadre = String.valueOf(nodoPadre.getId());
	        
	        if (padre.isRoot()){
	            raizAddChild (sbuff, id);
	        }
	        else{
	            idPadre = String.valueOf(nodoPadre.getId());
	            anyadeHijo (sbuff, idPadre, id );
	        }
	        if (n > 0) { // Si tiene hijos , sigue mostrandolos
		        for (int i = 0; i < n; i++)
		            print(sbuff, tree.getChild(i));
		        }else // Crea un hijo tonto
		        {
		            if (hasChildren)
		            	anyadeTonto(sbuff, id);
		        }
	    	}
        
    }

    /**
     * @param dn
     * @return
     */
    private String getTitle(String dn) {
        
        Node root = (Node)tree.getRoot();
        String rootDn = root.getTitle();
        String title = dn;
        if (!rootDn.equals(dn))
        {
            //int pos = dn.indexOf(rootDn);
            //title = dn.substring(0, pos -1);
            int pos = dn.indexOf(",");
            title = dn.substring(0, pos );            
        }
        return title;
    }

    /* (non-Javadoc)
     * @see ieci.tecdoc.mvc.service.ServiceTree#hasChildren(int)
     */
    public boolean hasChildren(int id, String entidad) throws Exception {
        // TODO Auto-generated method stub
        return false;
    }

}
