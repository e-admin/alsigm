/*
 * Created on 04-abr-2005
 *
 */
package es.ieci.tecdoc.isicres.admin.core.ldap;


import java.util.ArrayList;
import java.util.List;

import javax.naming.directory.Attribute;

import org.apache.log4j.Logger;

import es.ieci.tecdoc.isicres.admin.sbo.uas.ldap.UasConfigUtilLdap;


/**
 * @author Antonio María
 *
 */
public class LdapService {

	private static Logger logger = Logger.getLogger(LdapService.class);

    private int maxChildrenLdap;
    private String rootDn;
    private int treeType;

    public LdapService(int maxChildrenLdap, String rootDn, int treeType) throws Exception
    {
        this.maxChildrenLdap = maxChildrenLdap;
        this.rootDn = rootDn;
        this.treeType = treeType;
    }

   /* public Info getNode(String nodeDn, String entidad) {
    	// Obtener el dn de búsqueda del nodo
    	String searchDn = getLdapSearchText(nodeDn);

    	Info info = null;
    	LdapConnCfg connCfg;
    	LdapConnection ldapConn = null;
    	try {
    		connCfg = UasConfigUtilLdap.createLdapConnConfig(entidad);

    		ldapConn = new LdapConnection();
    		ldapConn.open(connCfg);

    		LdapSearchEx search = new LdapSearchEx();
    		search.initialize(ldapConn, searchDn, LdapScope.BASE, getFilter(), null, maxChildrenLdap);
    		search.execute();

        	if(search.next()){
        		Attribute objectClass = search.getAttributeValuesLowercase(LdapAttribute.getObjectClassAttributeName(ldapConn));
        		info = new Info(nodeDn, objectClass);
        	}
    		search.release();
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		try {
    			ldapConn.close();
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    	}
    	return info;
    }*/

    /**
     * Permite saber si un nodo tiene hijos
     * @param nodeDn Dn del nodo
     * @param entidad Entidad del nodo
     * @return Si el nodo tiene hijos
     */
    public boolean hasChildLdap(String nodeDn, String entidad)
    {

    	// Obtener el dn de búsqueda del nodo
    	String searchDn = getLdapSearchText(nodeDn);

    	boolean hasChild = false;

    	LdapConnCfg connCfg;
    	LdapConnection ldapConn = null;
    	try {
    		connCfg = UasConfigUtilLdap.createLdapConnConfig(entidad);
    		ldapConn = new LdapConnection();
    		ldapConn.open(connCfg);

    		LdapSearch search = new LdapSearch();
    		search.initialize(ldapConn, searchDn, LdapScope.ONELEVEL, getFilter(), null, maxChildrenLdap);
    		search.execute();

    		if (search.next())
    			hasChild = true;

    		search.release();
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		try {
    			ldapConn.close();
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    	}

    	return hasChild ;

    }

    /**
     * Permite obtener la lista de hijos de un nodo
     * @param nodeDn Dn del nodo
     * @param entidad Entidad del nodo
     * @return Lista de hijos del nodo de tipo {@link Info}
     * @throws Exception
     */
    public List GetLdapChildList(String nodeDn, String entidad) throws Exception {
        List hijos = new ArrayList();

        // Obtener el dn de búsqueda del nodo
        String searchDn = getLdapSearchText(nodeDn);

        LdapConnCfg connCfg;
        LdapConnection ldapConn = null;
        try {
        	connCfg = UasConfigUtilLdap.createLdapConnConfig(entidad);
        	ldapConn = new LdapConnection();
        	ldapConn.open(connCfg);

        	LdapSearchEx search=null;

        	search = new LdapSearchEx();
        	search.initialize(ldapConn, searchDn, LdapScope.ONELEVEL, getFilter(), null, maxChildrenLdap);
        	search.execute();

        	while (search.next())
        	{
        		String dn = search.getEntryDn();
        		Attribute objectClass = search.getAttributeValuesLowercase(LdapAttribute.getObjectClassAttributeName(ldapConn));
        		Info info = new Info(dn, objectClass);
        		hijos.add(info);
        	}
        	search.release() ;

        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
        	try {
        		ldapConn.close();
        	} catch (Exception e) {
        		e.printStackTrace();
        	}
        }

        return hijos;
    }

    /**
     * Permite obtener el dn a buscar a partir de un nodo
     * @param nodeDn Dn del nodo a buscar
     * @return Dn del nodo a buscar eliminando la parte raíz del nodo
     */
    private String getLdapSearchText(String nodeDn){
        String searchText = "";
        try {
			if ((nodeDn!=null)&&(!nodeDn.equals(""))){
				if( nodeDn.indexOf(this.rootDn) != -1) {
			    	int i = nodeDn.indexOf(this.rootDn);
			    	searchText = nodeDn.substring(0,i-1);
				}
			}
		} catch (Exception e) {
			logger.debug("Error al obtener el Dn del nodo: "+ nodeDn + " a partir del rootDn: " + this.rootDn );
		}
        return searchText;
    }

    private String getFilter(){
    	if (treeType == LdapConstants.LDAP_TYPE_TREE_USER)
    		return LdapSearchConfig.getLdapUserSearchFilter();
    	else
    		return LdapSearchConfig.getLdapGroupSearchFilter();
    }

}
