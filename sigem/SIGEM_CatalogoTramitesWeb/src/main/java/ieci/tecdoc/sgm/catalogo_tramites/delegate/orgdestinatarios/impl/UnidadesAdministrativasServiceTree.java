package ieci.tecdoc.sgm.catalogo_tramites.delegate.orgdestinatarios.impl;



import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.rpadmin.Distribucion;
import ieci.tecdoc.sgm.core.services.rpadmin.Organizacion;
import ieci.tecdoc.sgm.core.services.rpadmin.Organizaciones;
import ieci.tecdoc.sgm.core.services.rpadmin.ServicioRPAdmin;

import java.util.ArrayList;
import java.util.Locale;

import org.apache.log4j.Logger;

public class UnidadesAdministrativasServiceTree extends ServiceTree {

	private static final Logger logger = Logger.getLogger(UnidadesAdministrativasServiceTree.class);
	
	public UnidadesAdministrativasServiceTree(NaryTree tree) {
        super(tree);
    }

    /* (non-Javadoc)
     * @see ieci.tecdoc.mvc.service.ServiceTree#addChildren(java.lang.String)
     */
    public void addChildren(int id, ServicioRPAdmin oServicio, Entidad entidad, int tipo, boolean filtro) throws Exception {

        boolean generateOrg = false;
        boolean enc = searchNode(tree, id);
        if (enc)
        {
            Node nodoRaiz = (Node) fin.getRoot();

	        if (!nodoRaiz.getChildPrinted()){

	        	Organizaciones organizaciones = null;
	        	if(tipo == Distribucion.TIPO_ORGANIZACION) {
	            	organizaciones = oServicio.obtenerHijosOrganizacion(id, entidad);
	            	generateOrg = true;
	            }

	        	if( generateOrg ) {
	        		int numDptos = organizaciones.count();
		            for (int i = 0; i < numDptos; i ++ )
		            {
		            	Organizacion objOrg = (Organizacion)organizaciones.get(i);
		                int idOrg = objOrg.getId();
		                String uid = objOrg.getUid();
		                String name = objOrg.getNombre();
		                int tipoOrg = objOrg.getTipo();
		                boolean hasChild = hasChildren(idOrg, oServicio, entidad, tipo, filtro);
		                Node org = new NodeImplBD ();
		                org.setCodigo(uid);
		                org.setId(idOrg);
		                org.setTitle(name);
		                org.setTipo(tipoOrg);
		                org.setHasChild(hasChild);
		                fin.addChild(org);
		            }
		            nodoRaiz.setChildPrinted(true);
	        	}
	        }
	        else {
	        }
        }else{
            logger.error(" Nodo no encontrado");
        }
    }

    /* (non-Javadoc)
     * @see ieci.tecdoc.mvc.service.ServiceTree#print(java.lang.StringBuffer, ieci.tecdoc.mvc.util.NaryTree)
     */

    public void print(StringBuffer sbuff, NaryTree tree, int tipo, boolean filtro, Locale locale) {
        Node root = (Node) tree.getRoot();
        String nombre = root.getTitle();
        String id = String.valueOf(root.getId());;
        String codigo = root.getCodigo();

        boolean hasChild = root.getHasChild();

        int n = tree.getNumChild(); // Num de hijos del arbol cargado en memoria (Arbol parcial del ldap)

        String icon = null;
        String iconOpen = null;
        if( tipo == Distribucion.TIPO_ORGANIZACION) {
        	icon = UnidadesAdministrativasTreeConstants.TOKEN_ICON_ORGANIZATIONAL_UNIT;
        	iconOpen = UnidadesAdministrativasTreeConstants.TOKEN_ICON_ORGANIZATIONAL_UNIT_OPEN;
        }

        if (tree.isRoot()){
            creaNodoRaiz (sbuff, nombre, id, icon, codigo, new ArrayList());
            for (int i = 0; i < n; i++){
                print(sbuff, tree.getChild(i), tipo, filtro, locale);
            }
        }else
        {

	        NaryTree padre = tree.getParent();
	        creaNodo  (sbuff, nombre, id, icon, iconOpen, tipo, codigo, new ArrayList());
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
		            print(sbuff, tree.getChild(i), tipo, filtro, locale);
		        }else // Crea un hijo tonto
		        {
		            if (hasChild)
		            	anyadeTonto(sbuff, id, locale);
		        }
	    	}
    }

    public void printOrg(StringBuffer sbuff, NaryTree tree, int tipo, boolean filtro, Locale locale) {
        Node root = (Node) tree.getRoot();
        String nombre = root.getTitle();

        String id = String.valueOf(root.getId());
        String uid = root.getCodigo();
        String codigo = root.getCodigo();

        int tipoOrg = root.getTipo();
        boolean hasChild = root.getHasChild();

        int n = tree.getNumChild(); // Num de hijos del arbol cargado en memoria (Arbol parcial del ldap)

        String icon = UnidadesAdministrativasTreeConstants.TOKEN_ICON_ORGANIZATIONAL_UNIT;
        String iconOpen = UnidadesAdministrativasTreeConstants.TOKEN_ICON_ORGANIZATIONAL_UNIT_OPEN;
        if (tree.isRoot()){
        	creaNodoRaiz (sbuff, nombre, id, icon, codigo, new ArrayList());
            for (int i = 0; i < n; i++){
                printOrg(sbuff, tree.getChild(i), tipo, filtro, locale);
            }
        }else
        {
	        NaryTree padre = tree.getParent();
	        creaNodo  (sbuff, nombre, id, icon, iconOpen, tipoOrg, uid, new ArrayList());
	        Node nodoPadre = (Node) padre.getRoot();
	        //Este id padre no le pone la E o la U...
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
		            printOrg(sbuff, tree.getChild(i), tipo, filtro, locale);
		        }else // Crea un hijo tonto
		        {
		            if (hasChild)
		            	anyadeTonto(sbuff, id, locale);
		        }
	    	}
    }

    public boolean hasChildren(int id, ServicioRPAdmin oServicio, Entidad entidad, int tipo, boolean filtro) throws Exception
    {
        boolean tieneHijos = false;
        boolean childrenOrg = false;

        int numDptos = -1;
        Organizaciones organizaciones = null;
        if (tipo == Distribucion.TIPO_ORGANIZACION) {
        	organizaciones = oServicio.obtenerHijosOrganizacion(id, entidad);
        	childrenOrg = true;
        }

        if( childrenOrg ) {
        	numDptos = organizaciones.count();
        }

        if (numDptos > 0 )
            tieneHijos = true;
        return tieneHijos;
    }
}
