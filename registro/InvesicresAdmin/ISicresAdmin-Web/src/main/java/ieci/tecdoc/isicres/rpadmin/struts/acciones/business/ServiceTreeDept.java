package ieci.tecdoc.isicres.rpadmin.struts.acciones.business;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.bouncycastle.asn1.cmp.GenRepContent;

import es.ieci.tecdoc.isicres.admin.beans.Distribucion;
import es.ieci.tecdoc.isicres.admin.beans.Entidad;
import es.ieci.tecdoc.isicres.admin.beans.OptionBean;
import es.ieci.tecdoc.isicres.admin.beans.OptionsBean;
import es.ieci.tecdoc.isicres.admin.beans.Organizacion;
import es.ieci.tecdoc.isicres.admin.beans.Organizaciones;
import es.ieci.tecdoc.isicres.admin.business.util.ConstantesIntercambioRegistral;
import es.ieci.tecdoc.isicres.admin.business.vo.EntidadRegistralVO;
import es.ieci.tecdoc.isicres.admin.business.vo.UnidadRegistralVO;
import es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin;

public class ServiceTreeDept extends ServiceTree {
	/**
     * @param tree
     */

	private static final Logger logger = Logger.getLogger(ServiceTreeDept.class);
	public ServiceTreeDept(NaryTree tree) {
        super(tree);
        // TODO Auto-generated constructor stub
    }

    /* (non-Javadoc)
     * @see ieci.tecdoc.mvc.service.ServiceTree#addChildren(java.lang.String)
     */
    public void addChildren(int id, ISicresServicioRPAdmin oServicio, Entidad entidad, int tipo, boolean filtro) throws Exception {

        boolean generateDepto = false;
        boolean generateGrupos = false;
        boolean generateOrg = false;
        boolean generateEntidadRegistral = false;
        boolean generateUnidadRegistral = false;
        boolean enc = searchNode(tree, id);
        if (enc)
        {
            Node nodoRaiz = (Node) fin.getRoot();

	        if (!nodoRaiz.getChildPrinted()){

	        	OptionsBean options = new OptionsBean();
	        	Organizaciones organizaciones = null;
	        	List entidadesOUnidadesRegistrales = null;
	        	if( tipo == Distribucion.TIPO_DEPARTAMENTO) {
	        		options = oServicio.obtenerDepartamentosHijos(id, entidad);
	            	generateDepto = true;
	            } else if (tipo == Distribucion.TIPO_GRUPO) {
	            	options = oServicio.obtenerGruposHijos(id, entidad);
	            	generateGrupos = true;
	            } else if(tipo == Distribucion.TIPO_ORGANIZACION) {
	            	organizaciones = oServicio.obtenerHijosOrganizacion(id, entidad);
	            	generateOrg = true;
	            }

	        	if( generateGrupos || generateDepto) {
		            int numDptos = options.count();
		            for (int i = 0; i < numDptos; i ++ )
		            {
		                OptionBean obj = options.get(i);
		                int idObj = Integer.parseInt(obj.getCodigo());
		                String name = obj.getDescripcion();
		                boolean hasChild = hasChildren(idObj, oServicio, entidad, tipo, filtro);
		                Node group = new NodeImplBD ();
		                group.setId(idObj);
		                group.setTitle(name);
		                group.setHasChild(hasChild);
		                fin.addChild(group);
		            }
		            nodoRaiz.setChildPrinted(true);
	        	}
	        	else if( generateOrg )
	        	{
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
	        	else if(generateEntidadRegistral)
	        	{
	        		int numEntidades = entidadesOUnidadesRegistrales.size();
	        		for (int i = 0; i < numEntidades; i ++ )
		            {
	        			EntidadRegistralVO entidadRegistral = (EntidadRegistralVO)entidadesOUnidadesRegistrales.get(i);
	        			boolean hasChild = hasChildren(entidadRegistral.getId(), oServicio, entidad, tipo, filtro);
	        			Node org = new NodeImplBD ();
			            org.setCodigo(entidadRegistral.getCode());
			            org.setId(entidadRegistral.getId());
			            org.setTitle(entidadRegistral.getName());
			            org.setTipo(ConstantesIntercambioRegistral.TIPO_ORG_ENTIDAD_REGISTRAL);
			            org.setHasChild(hasChild);
			            fin.addChild(org);
		            }
	        	}
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

    public void print(StringBuffer sbuff, NaryTree tree, int tipo, boolean filtro, Locale locale) {
        Node root = (Node) tree.getRoot();
        String nombre = root.getTitle();
        String id = String.valueOf(root.getId());;
        String codigo = root.getCodigo();

        boolean hasChild = root.getHasChild();

        int n = tree.getNumChild(); // Num de hijos del arbol cargado en memoria (Arbol parcial del ldap)

        String icon = null;
        String iconOpen = null;
        if(tipo == Distribucion.TIPO_DEPARTAMENTO) {
        	icon = Constantes.TOKEN_ICON_DEPARTAMENTS;
        	iconOpen = Constantes.TOKEN_ICON_DEPARTAMENTS;
        } else if( tipo == Distribucion.TIPO_GRUPO) {
        	icon = Constantes.TOKEN_ICON_GROUP_BD;
        	iconOpen = Constantes.TOKEN_ICON_GROUP_BD;
        } else if( tipo == Distribucion.TIPO_ORGANIZACION) {
        	icon = Constantes.TOKEN_ICON_ORGANIZATIONAL_UNIT;
        	iconOpen = Constantes.TOKEN_ICON_ORGANIZATIONAL_UNIT_OPEN;
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

        String icon = Constantes.TOKEN_ICON_ORGANIZATIONAL_UNIT;
        String iconOpen = Constantes.TOKEN_ICON_ORGANIZATIONAL_UNIT_OPEN;
        if (tree.isRoot()){
        	creaNodoRaiz (sbuff, nombre, id, icon, codigo, new ArrayList());
            for (int i = 0; i < n; i++){
                printOrg(sbuff, tree.getChild(i), tipo, filtro, locale);
            }
        }else
        {
            if(tipo==ConstantesIntercambioRegistral.TIPO_INTERCAMBIO_REGISTRAL
            		&& tipoOrg==ConstantesIntercambioRegistral.TIPO_ORG_ENTIDAD_REGISTRAL)
            {
            	id = ConstantesIntercambioRegistral.PREFIJO_ID_ENTIDAD_REGISTRAL+id;
            }
            else if(tipo==ConstantesIntercambioRegistral.TIPO_INTERCAMBIO_REGISTRAL
            		&& tipoOrg==ConstantesIntercambioRegistral.TIPO_ORG_UNIDAD_REGISTRAL)
            {
            	id = ConstantesIntercambioRegistral.PREFIJO_ID_UNIDAD_REGISTRAL+id;
            }


	        NaryTree padre = tree.getParent();
	        creaNodo  (sbuff, nombre, id, icon, iconOpen, tipoOrg, uid, new ArrayList());
	        Node nodoPadre = (Node) padre.getRoot();
	        //Este id padre no le pone la E o la U...
	        String idPadre = String.valueOf(nodoPadre.getId());
	        if(tipo==ConstantesIntercambioRegistral.TIPO_INTERCAMBIO_REGISTRAL
            		&& tipoOrg==ConstantesIntercambioRegistral.TIPO_ORG_UNIDAD_REGISTRAL)
            {
	        	idPadre = ConstantesIntercambioRegistral.PREFIJO_ID_ENTIDAD_REGISTRAL+idPadre;
            }
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

    public boolean hasChildren(int id, ISicresServicioRPAdmin oServicio, Entidad entidad, int tipo, boolean filtro) throws Exception
    {
        boolean tieneHijos = false;
        boolean childrenDept = false;
        boolean childrenGroup = false;
        boolean childrenOrg = false;
        boolean childrenIntercambioRegistral = false;
        int numDptos = -1;
        OptionsBean options = new OptionsBean();
        Organizaciones organizaciones = null;
        List entidadesOUnidadesRegistrales = null;
        if( tipo == Distribucion.TIPO_DEPARTAMENTO) {
        	options = oServicio.obtenerDepartamentosHijos(id, entidad);
        	childrenDept = true;
        } else if (tipo == Distribucion.TIPO_GRUPO) {
        	options = oServicio.obtenerGruposHijos(id, entidad);
        	childrenGroup = true;
        }  else if (tipo == Distribucion.TIPO_ORGANIZACION) {
        	organizaciones = oServicio.obtenerHijosOrganizacion(id, entidad);
        	childrenOrg = true;
        }

        if( childrenGroup || childrenDept) {
	        numDptos = options.count();
        } else if( childrenOrg ) {
        	numDptos = organizaciones.count();
        }
        else if(childrenIntercambioRegistral)
        {
        	numDptos = entidadesOUnidadesRegistrales.size();
        }

        if (numDptos > 0 )
            tieneHijos = true;
        return tieneHijos;
    }
}
