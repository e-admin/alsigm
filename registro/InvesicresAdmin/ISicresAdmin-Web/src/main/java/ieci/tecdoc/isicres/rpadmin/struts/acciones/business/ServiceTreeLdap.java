package ieci.tecdoc.isicres.rpadmin.struts.acciones.business;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.naming.InvalidNameException;
import javax.naming.directory.Attribute;
import javax.naming.directory.BasicAttribute;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.sun.jndi.ldap.LdapName;

import es.ieci.tecdoc.isicres.admin.beans.Entidad;
import es.ieci.tecdoc.isicres.admin.core.collections.IeciTdShortTextArrayList;
import es.ieci.tecdoc.isicres.admin.core.ldap.Info;
import es.ieci.tecdoc.isicres.admin.core.ldap.LdapAttribute;
import es.ieci.tecdoc.isicres.admin.core.ldap.LdapConnection;
import es.ieci.tecdoc.isicres.admin.core.ldap.LdapConstants;
import es.ieci.tecdoc.isicres.admin.core.ldap.LdapSearchConfig;
import es.ieci.tecdoc.isicres.admin.core.ldap.LdapService;
import es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin;

public class ServiceTreeLdap extends ServiceTree {

    private static Logger logger = Logger.getLogger(ServiceTreeLdap.class);
    private LdapService ldapService;
    private String rootDn;
    private int treeType;

    public ServiceTreeLdap(NaryTree tree, int maxChildrenLdap, String rootDn, int treeType) throws Exception {
        super(tree);
        this.rootDn = rootDn;
        this.treeType = treeType;
        ldapService = new LdapService(maxChildrenLdap, this.rootDn, treeType);
    }

    /**
     * Añade los hijos a un nodo
     * @param id Identificador del nodo
     * @param entidad Entidad del nodo
     * @throws Exception
     */
    public void addChildren(int id, String entidad) throws Exception {
        boolean enc = searchNode(tree, id );

        if (enc)
        {
	        Node nodoRaiz = (Node)fin.getRoot();
	        if (!nodoRaiz.getChildPrinted()){
	        		// El Dn del nodo se guarda en el código
	        		String nodeDn = nodoRaiz.getCodigo();

	        		// Obtener la lista de hijos
		        	List hijos = ldapService.GetLdapChildList (nodeDn, entidad);
		            Iterator it = hijos.iterator();
		            while (it.hasNext())
		            {
		                Info hijo = (Info) it.next();
		                NodeImplLDAP hijoIesimo = new NodeImplLDAP();

		                hijoIesimo.setCodigo(hijo.getValor());

		                // Comprobar si tiene hijos
		                boolean hasChildren = ldapService.hasChildLdap(hijo.getValor(), entidad);
		                hijoIesimo.setHasChild(hasChildren);
		                byte type = hijo.getObjectClass();

		                hijoIesimo.setId(getHashCode(hijo.getValor()));
		                hijoIesimo.setValidForSelection(isValidForSelection(hijo.getObjectClassObj(), treeType));

		                hijoIesimo.setTitle(getLdapTitle(hijo.getValor()));
		                hijoIesimo.setTipo(type);
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
     * @see ieci.tecdoc.sgm.rpadmin.struts.acciones.business.ServiceTree#print(java.lang.StringBuffer, ieci.tecdoc.sgm.rpadmin.struts.acciones.business.NaryTree, int, boolean, java.util.Locale)
     */
    public void print(StringBuffer sbuff, NaryTree tree, int tipo, boolean filtro, Locale locale) {

        NodeImplLDAP root = (NodeImplLDAP) tree.getRoot();

        String validForSelection = root.getValidForSelection();
        String dn = root.getTitle();
        String id = String.valueOf(root.getId());
        String icon = root.getIcon();
        boolean hasChildren = root.getHasChild();
        int n = tree.getNumChild(); // Num de hijos del arbol cargado en memoria (Arbol parcial del ldap)

        String title = getLdapTitle(dn);
        if (tree.isRoot()){

        	List functionValues = new ArrayList();
        	functionValues.add(root.getValidForSelection());
            creaNodoRaiz (sbuff, title, id, icon, "", functionValues);
            for (int i = 0; i < n; i++){
                print(sbuff, tree.getChild(i),tipo, filtro, locale);
            }
        }else
        {
	        NaryTree padre = tree.getParent();
        	List functionValues = new ArrayList();
        	functionValues.add(root.getValidForSelection());
	        creaNodo  (sbuff, title, id, icon, icon,tipo, "", functionValues);
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
		            print(sbuff, tree.getChild(i),tipo, filtro, locale);
		        }else // Crea un hijo tonto
		        {
		            if (hasChildren)
		            	anyadeTonto(sbuff, id, locale);
		        }
	    	}

    }

    /**
     * Permite obtener el título de un nodo
     * @param nodeDn Dn del nodo
     * @return Título del nodo
     */
    public static String getLdapTitle(String nodeDn){
    	String title = nodeDn;
    	try {
    		if ((nodeDn!=null)&&(!nodeDn.equals(""))){
//    			int i = nodeDn.indexOf(",");
//    			title = nodeDn.substring(0,i);
    			title = getLdapTitleDn(nodeDn);
    		}
    	} catch (Exception e) {
    		logger.debug("Error al obtener el title del nodo: "+ nodeDn );
    	}
    	return title;
    }

    /**
     * Permite obtener el título de un nodo a la derecha del =
     * @param nodeDn Dn del nodo
     * @return Título del nodo
     */
    public static String getLdapNodeTitle(String nodeDn){
    	String title = nodeDn;
    	try {
    		if ((nodeDn!=null)&&(!nodeDn.equals(""))){
    			int i = nodeDn.indexOf(",");
    			title = nodeDn.substring(0,i);

    			i = title.indexOf("=");
    			title = title.substring(i+1,title.length());
    		}
    	} catch (Exception e) {
    		logger.debug("Error al obtener el title del nodo: "+ nodeDn );
    	}
    	return title;
    }

    /**
     * Permite comprobar si un nodo es válido para selección
     * @param objectClass Atributo objectClass del nodo
     * @return si el nodo es seleccionable
     */
    public static String isValidForSelection(Attribute objectClass, int treeType){
    	if (objectClass!=null){

    		String validObjectClass = getValidSelectionClasses(treeType);
    		if (validObjectClass!=null){
    			String [] validObjectClassArray = validObjectClass.split(",");

    			if ((validObjectClassArray!=null)&&(validObjectClassArray.length>0)){

    				for (int i=0;i<validObjectClassArray.length;i++){
    					String validClass = validObjectClassArray[i];
    					if ((validClass!=null)&&(!"".equals(validClass.trim()))&&(objectClass.contains(validClass.trim()))){
    						return NodeImplLDAP.VALID_FOR_SELECTION;
    					}
    				}
    			}
    		}
    	}

    	return NodeImplLDAP.NOT_VALID_FOR_SELECTION;

    }

    /**
     * Permite comprobar si un nodo es válido para selección
     * @param arrayList lista de valores del objectClass
     * @param conn conexión a ldap
     * @return {@link NodeImplLDAP.VALID_FOR_SELECTION} si es válido {@link NodeImplLDAP.NOT_VALID_FOR_SELECTION} si no lo es
     */
    public static String isValidForSelection(IeciTdShortTextArrayList arrayList, LdapConnection conn, int treeType){
    	return isValidForSelection(getObjectClassAttributeLowercase(arrayList, conn), treeType);

    }

    /**
     * Permite obtener el objectClass de un nodo en minúscula
     * @param arrayList lista de valores del objectClass
     * @param conn conexión a ldap
     * @return {@link Attribute} en minúscula
     */
    private static Attribute getObjectClassAttributeLowercase(IeciTdShortTextArrayList arrayList, LdapConnection conn){

	   BasicAttribute attrsLowercase = new BasicAttribute(LdapAttribute.getObjectClassAttributeName(conn));
    	if ((arrayList!=null)&&(arrayList.count()>0)){
    		for (int i=0;i<arrayList.count();i++){
    			String attribute = (String) arrayList.get(i);
    			if ((attribute!=null)&&(!"".equals(attribute.trim()))){
    				attrsLowercase.add(attribute.toLowerCase().trim());
    			}
    		}
    	}
    	return attrsLowercase;
    }


    /**
     * Permite saber si un nodo tiene hijos
     * @param id Id del nodo
     * @param entidad Entidad del nodo
     * @return Si el nodo tiene o no hijos
     * @throws Exception
     */
    public boolean hasChildren(int id, String entidad) throws Exception {
    	boolean tieneHijos = false;
    	boolean enc = searchNode(tree, id );
        if (enc)
        {
	        Node parent = (Node) fin.getParent();
		    tieneHijos = parent.getHasChild();
        }else
        {
            if (logger.isDebugEnabled())
            logger.debug("Nodo no encontrado");
        }
        return tieneHijos;
    }

    /**
     * Permite obtener las clases válidas para selección dependiento del tipo del árbol
     * @return clases válidas para selección dependiento del tipo del árbol
     */
    private static String getValidSelectionClasses(int treeType){
    	if (treeType == LdapConstants.LDAP_TYPE_TREE_USER)
    		return LdapSearchConfig.getLdapUserValidSelectionClasses();
    	else if (treeType == LdapConstants.LDAP_TYPE_TREE_GROUP)
    		return LdapSearchConfig.getLdapGroupValidSelectionClasses();
    	else
    		return LdapSearchConfig.getLdapUserValidSelectionClasses() + ","
					+ LdapSearchConfig.getLdapGroupValidSelectionClasses();
    }

	/* (non-Javadoc)
	 * @see ieci.tecdoc.sgm.rpadmin.struts.acciones.business.ServiceTree#addChildren(int, ieci.tecdoc.sgm.core.services.rpadmin.ServicioRPAdmin, ieci.tecdoc.sgm.core.services.dto.Entidad, int, boolean)
	 */
	public void addChildren(int id, ISicresServicioRPAdmin servicio, Entidad entidad,
			int tipo, boolean filtro) throws Exception {
		addChildren(id, entidad.getIdentificador());

	}

	/* (non-Javadoc)
	 * @see ieci.tecdoc.sgm.rpadmin.struts.acciones.business.ServiceTree#hasChildren(int, ieci.tecdoc.sgm.core.services.rpadmin.ServicioRPAdmin, ieci.tecdoc.sgm.core.services.dto.Entidad, int, boolean)
	 */
	public boolean hasChildren(int id, ISicresServicioRPAdmin servicio,
			Entidad entidad, int tipo, boolean filtro) throws Exception {
		return hasChildren(id, entidad.getIdentificador());
	}

	/* (non-Javadoc)
	 * @see ieci.tecdoc.sgm.rpadmin.struts.acciones.business.ServiceTree#printOrg(java.lang.StringBuffer, ieci.tecdoc.sgm.rpadmin.struts.acciones.business.NaryTree, int, boolean, java.util.Locale)
	 */
	public void printOrg(StringBuffer sbuff, NaryTree tree, int tipo,
			boolean filtro, Locale locale) {
		print(sbuff, tree, tipo, filtro, locale);

	}


    /**
     * Metodo que obtiene el titulo del nodo pasado como parametro
     * @param nodeDn
     * @return
     */
    public static String getLdapTitleDn(String nodeDn){
    	String result = nodeDn;
    	LdapName ldapName = null;

    	try {
    		if(StringUtils.isNotEmpty(nodeDn)){
	    		ldapName = new LdapName(nodeDn);
	    		result = ldapName.get(ldapName.size()-1);
    		}
		} catch (InvalidNameException e) {
    		logger.debug("Error al obtener el title del nodo: "+ nodeDn );
		}

		return result;
    }


}
