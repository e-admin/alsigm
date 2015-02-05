/*
 * Created on 04-abr-2005
 *
 */
package ieci.tecdoc.mvc.service;

import ieci.tecdoc.mvc.util.NaryTree;
import ieci.tecdoc.mvc.util.Node;
import ieci.tecdoc.mvc.util.ldap.Nodo;

import org.apache.log4j.Logger;



/**
 * @author Antonio María
 *
 */
public abstract class ServiceTree {
    private static Logger logger = Logger.getLogger(ServiceTree.class);    
    NaryTree tree;
    public ServiceTree (NaryTree tree)
    {
        this.tree = tree;
    }
    
    public abstract void addChildren (int id, String entidad) throws Exception;
    
    public abstract void print (StringBuffer sbuff, NaryTree tree);
    
    public abstract boolean hasChildren(int id, String entidad) throws Exception;
    /*    
    public void añadeHijos(String id ) throws Exception{
        boolean enc = buscaNodo (tree, id);
        if (enc)
        {
	        Nodo nodoRaiz = (Nodo)fin.getRoot();
	        String parent;
	        if (!nodoRaiz.getHijosMostrados()){
		        if ( fin.isRoot()) // Es Nodo raiz --> "c=es"
		            parent = "";
		        else
		            parent = ((Nodo)fin.getRoot()).getValor();
		            
		            List hijos = ServiceLdap.GetLdapChildList (parent);
		            Iterator it = hijos.iterator();
		            while (it.hasNext())
		            {
		                Info hijo = (Info) it.next();
		                String dn = hijo.getValor() + parent;
		                Nodo hijoIesimo = new Nodo(hijo.getValor());
		                hijoIesimo.setTieneHijos(ServiceLdap.hasChildLdap(hijo.getValor()));
		                hijoIesimo.setTipo(hijo.getObjectClass());
		                if (logger.isDebugEnabled())
		                    logger.debug("Hijo del Ldap: " + hijo );
		                fin.addChild(hijoIesimo);
		            }
		            nodoRaiz.setHijosMostrados(true);	        
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
    */   
    public NaryTree fin = null;
    // usada en ldap tree
    /*
    public boolean buscaNodo(NaryTree arbol, String id)
    {
        boolean enc = false;
        Nodo nodo = (Nodo)arbol.getRoot();

        if ( String.valueOf(nodo.getHashCode()).equals(id) )
        {
            enc = true;  fin = arbol;
        }
        else {
            int numHijos = arbol.getNumChild();
            int i = 0;
            while (i < numHijos && !enc)
            {
                enc = buscaNodo (arbol.getChild(i), id);
                i++;
            }
        }
        return enc;
        
    }*/
    public boolean searchNode(NaryTree arbol, int id)
    {
        boolean enc = false;
        Node nodo = (Node) arbol.getRoot();

        if ( nodo.getId() == id )
        {
            enc = true;  fin = arbol;
        }
        else {
            int numHijos = arbol.getNumChild();
            int i = 0;
            while (i < numHijos && !enc)
            {
                enc = searchNode (arbol.getChild(i), id);
                i++;
            }
        }
        return enc;
    }


    /**
     * @return
     */
    public String getArbolString() {
        String arbol = new String();
        StringBuffer sbuff = new StringBuffer();
        recorre(sbuff, tree );
        arbol = sbuff.toString();
        return arbol;
    }
    public String getTreeString() {
        String arbol = new String();
        StringBuffer sbuff = new StringBuffer();
        print(sbuff, tree );
        arbol = sbuff.toString();
        return arbol;
    }
    
    public void recorre( StringBuffer sbuff, NaryTree tree)
    {
        Nodo root = (Nodo) tree.getRoot();
        
        String titulo = root.getTitle();
        String id = root.getHashCode();
        String dn = root.getValor();
        String icon = root.getIcon();
        boolean tieneHijos = root.isTieneHijos();
        int n = tree.getNumChild(); // Num de hijos del arbol cargado en memoria (Arbol parcial del ldap)
        
        if (tree.isRoot()){
            creaNodoRaiz (sbuff, dn,"");
            for (int i = 0; i < n; i++){
                 recorre(sbuff, tree.getChild(i));
            }
        }else
        {
	        NaryTree padre = tree.getParent();
	        creaNodo  (sbuff, titulo, id, icon);
	        Nodo nodoPadre = (Nodo) padre.getRoot();
	        
	        String idPadre = nodoPadre.getHashCode();
	        
	        if (padre.isRoot()){
	            raizAddChild (sbuff, id);
	        }
	        else{
	            idPadre = nodoPadre.getHashCode();
	            anyadeHijo (sbuff, idPadre, id );
	        }
	        if (n > 0) { // Si tiene hijos , sigue mostrandolos
		        for (int i = 0; i < n; i++)
		            recorre(sbuff, tree.getChild(i));
		        }else // Crea un hijo tonto
		        {
		            if (tieneHijos)
		            	anyadeTonto(sbuff, id);
		                
		        }
	    	}
    }
    public int getHashCode(String s)
    {
        return Math.abs(s.hashCode());
    }
    public void creaNodoRaiz(StringBuffer sbuff, String titulo, String icon){
        String sentencia= "\n var tree = new WebFXTree('"+ titulo +"',\"javascript:nodoRaizEvent('"+titulo +"');\",'','"+icon+"','"+icon+"',''); \n";
        sbuff.append(sentencia);
    }
    public void creaNodo(StringBuffer sbuff, String titulo, String id, String icon){
        String sentencia =
        "var nodo" + id + " = new WebFXTreeItem('"+titulo+"',\"javascript:carga('"+ id+"', '"+titulo+"'  );\",'','"+icon+"','"+icon+"','"+id+"'); \n";            
            
        sbuff.append(sentencia);
    }
    public void raizAddChild(StringBuffer sbuff, String id)
    {
        String sentencia = "tree.add(nodo"+ id +"); \n";
        sbuff.append(sentencia);
    }
    public void anyadeHijo (StringBuffer sbuff, String idPadre, String idHijo)
    {
        String sentencia = "nodo"+idPadre+".add(nodo"+ idHijo+"); \n";
        sbuff.append(sentencia);
    }
    public void anyadeTonto (StringBuffer sbuff, String id)
    {
        String sentencia =
            "var tonto = new WebFXTreeItem('Cargando ...','','','include/images/clockani.gif','',''); \n" +
        	"nodo"+id+".add(tonto); \n";
        sbuff.append(sentencia);
    }


}

