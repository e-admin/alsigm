package ieci.tecdoc.isicres.rpadmin.struts.acciones.business;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import es.ieci.tecdoc.isicres.admin.beans.Entidad;
import es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin;

public abstract class ServiceTree {

	private static final Logger logger = Logger.getLogger(ServiceTree.class);
	public static ServiceTree instance = null;

    NaryTree tree;
    public ServiceTree (NaryTree tree)
    {
        this.tree = tree;
    }

    public abstract void addChildren (int id, ISicresServicioRPAdmin oServicio, Entidad entidad, int tipo, boolean filtro) throws Exception;

    public abstract void print (StringBuffer sbuff, NaryTree tree, int tipo, boolean filtro, Locale locale);

    public abstract void printOrg (StringBuffer sbuff, NaryTree tree, int tipo, boolean filtro, Locale locale);

    public abstract boolean hasChildren(int id, ISicresServicioRPAdmin oServicio, Entidad entidad, int tipo, boolean filtro) throws Exception;

    public NaryTree fin = null;

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
    public String getArbolString1(int tipo, String codigo, boolean filtro, Locale locale) {
        String arbol = new String();
        StringBuffer sbuff = new StringBuffer();
        recorre(sbuff, tree, tipo, codigo, filtro, locale);
        arbol = sbuff.toString();
        return arbol;
    }
    public String getTreeString( int tipo, boolean filtro, Locale locale) {
        String arbol = new String();
        StringBuffer sbuff = new StringBuffer();
        print(sbuff, tree, tipo, filtro, locale);
        arbol = sbuff.toString();
        return arbol;
    }

    public String getTreeStringOrg( int tipo, boolean filtro, Locale locale) {
        String arbol = new String();
        StringBuffer sbuff = new StringBuffer();
        printOrg(sbuff, tree, tipo, filtro, locale);
        arbol = sbuff.toString();
        return arbol;
    }

    public void recorre( StringBuffer sbuff, NaryTree tree, int tipo, String codigo, boolean filtro, Locale locale)
    {
        Nodo root = (Nodo) tree.getRoot();

        String titulo = root.getTitle();
        String id = root.getHashCode();
        String dn = root.getValor();
        String icon = root.getIcon();
        boolean tieneHijos = root.isTieneHijos();
        int n = tree.getNumChild(); // Num de hijos del arbol cargado en memoria (Arbol parcial del ldap)

        if (tree.isRoot()){
            creaNodoRaiz (sbuff, dn, id, icon, "", new ArrayList());
            for (int i = 0; i < n; i++){
                 recorre(sbuff, tree.getChild(i), tipo, codigo, filtro, locale);
            }
        }
        else
        {
	        NaryTree padre = tree.getParent();
	        String iconOpen = Constantes.TOKEN_ICON_ORGANIZATIONAL_UNIT_OPEN;
	        creaNodo  (sbuff, titulo, id, icon, iconOpen, tipo, codigo, new ArrayList());
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
		            recorre(sbuff, tree.getChild(i), tipo, codigo, filtro, locale);
		        }else // Crea un hijo tonto
		        {
		            if (tieneHijos)
		            	anyadeTonto(sbuff, id, locale);

		        }
	    	}
    }
    public int getHashCode(String s)
    {
        return Math.abs(s.hashCode());
    }

    private String getFunctionValuesString(List functionValues){
    	StringBuffer ret = new StringBuffer();
    	if (functionValues!=null){

    		Iterator it = functionValues.iterator();
    		while (it.hasNext()){
    			String value = (String) it.next();
    			ret.append(",");
    			ret.append("'").append(value).append("'");
    		}
    	}

    	return ret.toString();
    }

    public void creaNodoRaiz(StringBuffer sbuff, String titulo, String id, String icon, String codigo, List functionValues){

		String sentencia = "\n var nombreNodo=''; var nodoTonto=\""
				+ titulo
				+ "\"; var tree = new WebFXTree(nodoTonto,\"javascript:nodoRaizEvent('"
				+ id + "',nodoTonto,'" + codigo + "'"
				+ getFunctionValuesString(functionValues) + ");\",'','" + icon
				+ "','" + icon + "',''); \n";

        sbuff.append(sentencia);
    }
    public void creaNodo(StringBuffer sbuff, String titulo, String id, String icon, String iconOpen, int tipo, String codigo, List functionValues){
    	String idNodo = id.replaceAll("-", "_");

		String sentencia = "\n var nombreNodo_" + idNodo + "=\""
				+ titulo + "\" ; var nodo"
				+ idNodo + " = new WebFXTreeItem(nombreNodo_" + idNodo
				+ ",\"javascript:carga('" + id + "', nombreNodo_" + idNodo
				+ ", '" + codigo + "', " + tipo
				+ getFunctionValuesString(functionValues) + "  );\",'','"
				+ icon + "','" + iconOpen + "','" + id + "'); \n";

        sbuff.append(sentencia);
    }

    public void raizAddChild(StringBuffer sbuff, String id)
    {
    	String idNodo = id.replaceAll("-", "_");
        String sentencia = "tree.add(nodo"+ idNodo +"); \n";
        sbuff.append(sentencia);
    }
    public void anyadeHijo (StringBuffer sbuff, String idPadre, String idHijo)
    {
    	String idNodoPadre = idPadre.replaceAll("-", "_");
    	String idNodoHijo = idHijo.replaceAll("-", "_");
        String sentencia = "nodo"+idNodoPadre+".add(nodo"+ idNodoHijo+"); \n";
        sbuff.append(sentencia);
    }
    public void anyadeTonto (StringBuffer sbuff, String id, Locale locale)
    {
    	ResourceBundle rb = ResourceBundle.getBundle("ApplicationResource", locale);
    	String idNodo = id.replaceAll("-", "_");
        String sentencia =
            "nodoTonto=\""+ rb.getString("cargandoJS") +"\"; var tonto = new WebFXTreeItem(nodoTonto,'','','img/arbol/clockani.gif','',''); \n" +
        	"nodo"+idNodo+".add(tonto); \n";
        sbuff.append(sentencia);
    }

	public NaryTree getTree() {
		return tree;
	}

}
