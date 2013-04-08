/*
 * Created on 04-abr-2005
 *
 */
package ieci.tecdoc.mvc.service.adminUser.ldap;

import ieci.tecdoc.core.exception.IeciTdException;
import ieci.tecdoc.core.ldap.LdapAttribute;
import ieci.tecdoc.core.ldap.LdapConnCfg;
import ieci.tecdoc.core.ldap.LdapConnection;
import ieci.tecdoc.core.ldap.LdapError;
import ieci.tecdoc.core.ldap.LdapFilter;
import ieci.tecdoc.core.ldap.LdapSearch;
import ieci.tecdoc.mvc.action.adminUser.ldap.GroupTree;
import ieci.tecdoc.mvc.action.adminUser.ldap.UserTree;
import ieci.tecdoc.mvc.service.ServiceTreeLdap;
import ieci.tecdoc.mvc.util.Constantes;
import ieci.tecdoc.mvc.util.Node;
import ieci.tecdoc.mvc.util.ldap.Info;
import ieci.tecdoc.mvc.util.ldap.LdapSearchEx;
import ieci.tecdoc.sbo.uas.ldap.UasAuthConfig;
import ieci.tecdoc.sbo.uas.ldap.UasConfigUtilLdap;
import ieci.tecdoc.sgm.core.utils.ldap.LdapUtils;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;

import org.apache.commons.lang.StringUtils;

import com.sun.jndi.ldap.LdapURL;

/**
 * @author Antonio María
 *
 */
public class ServiceLdap {
    
    //private LdapConnection ldapConn;
    private int maxChildrenLdap;
    private int typeTree;
    public ServiceLdap(int maxChildrenLdap, int typeTree) throws Exception
    {
    	this.typeTree = typeTree;
        this.maxChildrenLdap = maxChildrenLdap;
    }
    /*
    public LdapConnection getLdapConnection()
    {
        return ldapConn;
    }
    */
    /*
    public static boolean hasChildLdap(String parent) throws Exception
    {
        
        String s = "";
        if (!parent.equals("")){
            int i = parent.indexOf(UserTree.LdapRootDn);
            s = parent.substring(0,i-1);
        }
        int r=0;
        boolean hasChild = false;
		LdapConnection ldapConn = new LdapConnection();

		DbConnection.open(CfgMdoConfig.getDbConfig());
		LdapConnCfg connCfg = UasConfigUtil.createLdapConnConfig();
        ldapConn.open(connCfg);

		LdapSearch search = new LdapSearch();
		search.initialize(ldapConn,s, 1,"objectclass=*", null, 100);
		search.execute();
			
		if (search.next())
		    hasChild = true;
			        
		search.release();

        return hasChild ;
        
    }
    */
    public boolean hasChildLdap(String parent, String entidad) 
    {
        String s = "";
        if (!parent.equals("")){
        	if( parent.indexOf(UserTree.ldapRootDn) != -1) {
        		int i = parent.indexOf(UserTree.ldapRootDn);
        		s = parent.substring(0,i-1);
        	}
        }
        int r=0;
        boolean hasChild = false;

        LdapConnCfg connCfg;
        LdapConnection ldapConn = null;
		try {
			connCfg = UasConfigUtilLdap.createLdapConnConfig(entidad);
			ldapConn = new LdapConnection();
	        ldapConn.open(connCfg);
	        
			LdapSearch search = new LdapSearch();
			search.initialize(ldapConn,s, 1,"objectclass=*", null, maxChildrenLdap);
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
    /*
    public static List GetLdapChildList(String Parent) throws Exception {
        int i=0;
        String Childs [] = null;
        List hijos = new ArrayList();
				
        
        String s = "";
        if (!Parent.equals("")){
            i = Parent.indexOf(UserTree.LdapRootDn);
            s = Parent.substring(0,i-1);
        }
		LdapConnection ldapConn = new LdapConnection();		
		DbConnection.open(CfgMdoConfig.getDbConfig());
		LdapConnCfg connCfg = UasConfigUtil.createLdapConnConfig();
        ldapConn.open(connCfg);
        
		LdapURL Ldap_url = new LdapURL(ldapConn.getUrl());
		//String LdapRootDn = Ldap_url.getDN();

		LdapSearchEx search=null;
		
		search = new LdapSearchEx();
		search.initialize(ldapConn,s, 1,"objectclass=*", null, 100);
		search.execute();
		
		while (search.next())
		{
			String dn = search.getEntryDn();
			Attribute objectClass = search.getAttributeValues("objectclass");
			Info info = new Info(dn, objectClass);
			hijos.add(info);
		}         
		search.release() ;

        return hijos;
    }
    */
    public List GetLdapChildList(String Parent, String entidad) throws Exception {
    	int i=0;
        //String Childs [] = null;
        List hijos = new ArrayList();
				
        
        String s = "";
        if (!Parent.equals("")){
        	if( Parent.indexOf(UserTree.ldapRootDn) != -1) {
	        	i = Parent.indexOf(UserTree.ldapRootDn);
	            s = Parent.substring(0,i-1);
        	}
        } 

        LdapConnCfg connCfg;
        LdapConnection ldapConn = null;
		try {
	        connCfg = UasConfigUtilLdap.createLdapConnConfig(entidad);
			ldapConn = new LdapConnection();
	        ldapConn.open(connCfg);
	        
	        if( Parent.equals("")) {
	          LdapURL Ldap_url = new LdapURL(ldapConn.getUrl());
	          UasAuthConfig  authCfg = UasConfigUtilLdap.createUasAuthConfig(entidad);	
	          String ldapRootDnUserGroup = null;
	          if( typeTree == Constantes.LDAP_TYPE_TREE_USER) {
	        	  ldapRootDnUserGroup = authCfg.getUserStart();
	          } else if( typeTree == Constantes.LDAP_TYPE_TREE_GROUP) {
	        	  ldapRootDnUserGroup = authCfg.getGroupStart();
	          }
              String ldapRootDn = Ldap_url.getDN();
              if( !ldapRootDnUserGroup.equals("") && !ldapRootDnUserGroup.equalsIgnoreCase(ldapRootDn) && !ldapRootDnUserGroup.equalsIgnoreCase(Constantes.LDAP_START_USERS)) {
            	  i = ldapRootDnUserGroup.indexOf(ldapRootDn);
                  s = ldapRootDnUserGroup.substring(0,i-1);
              }
	        }
	        
			//LdapURL Ldap_url = new LdapURL(ldapConn.getUrl());
			//String LdapRootDn = Ldap_url.getDN();
	
			LdapSearchEx search=null;
			
			search = new LdapSearchEx();
			search.initialize(ldapConn,s, 1,"objectclass=*", null, maxChildrenLdap);
			search.execute();
			
			while (search.next())
			{
				String dn = search.getEntryDn();
				//dn = (String) search.getAttributeValue("displayname");
				Attribute objectClass = search.getAttributeValues("objectclass");
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
    /*
    public static NamingEnumeration getObjectAttributes(String dn ) throws Exception {
		LdapConnection ldapConn = new LdapConnection();		
		LdapSearchEx search=null;
		NamingEnumeration ne=null;
		String filtro=null;
		String rootSearch="";
	
		int indice=dn.indexOf(",");
		if (indice != -1){
		    filtro=dn.substring(0,indice).trim();
		    int rootDnPos = dn.indexOf(UserTree.LdapRootDn);
		    if (rootDnPos -1 != indice )
		        rootSearch=dn.substring(indice+1,rootDnPos-1).trim();
		        
		}
		else
		{
		    rootSearch = "";
		    filtro = dn;
		}
		DbConnection.open(CfgMdoConfig.getDbConfig());
		LdapConnCfg connCfg = UasConfigUtil.createLdapConnConfig();
 		ldapConn.open(connCfg);
		search = new LdapSearchEx();
		search.initialize(ldapConn,rootSearch, 1,filtro, null, 100);
		search.execute();
         if (!search.next())
         {
            throw new IeciTdException(LdapError.EC_NOT_FOUND,
                                      LdapError.EM_NOT_FOUND);
         }
        ne= search.getAttributes();
		search.release() ;
		return ne;
    }
    */

    public NamingEnumeration getObjectAttributes(String dn, String entidad)  {
		//LdapConnection ldapConn = new LdapConnection();		
		LdapSearchEx search=null;
		NamingEnumeration ne=null;
		String filtro=null;
		String rootSearch="";
	
		int indice=dn.indexOf(",");
		if (indice != -1){
			
			//metodo de obtener el nombre del nodo
			String titleDn  = LdapUtils.getLdapTitleDn(dn);
			indice = titleDn.length();
			
		    //filtro=dn.substring(0,indice).trim();
			filtro=dn.substring(0,indice);
		    int rootDnPos = dn.indexOf(UserTree.ldapRootDn);
		    
		    if (rootDnPos -1 != indice )
		        rootSearch=dn.substring(indice+1,rootDnPos-1).trim();
		}
		else
		{
		    rootSearch = "";
		    filtro = dn;
		}
		
		LdapConnCfg connCfg;
        LdapConnection ldapConn = null;
		try {
	        connCfg = UasConfigUtilLdap.createLdapConnConfig(entidad);
			ldapConn = new LdapConnection();
	        ldapConn.open(connCfg);
			search = new LdapSearchEx();
			search.initialize(ldapConn,rootSearch, 1,filtro, null, 100);
			search.execute();
	         if (!search.next())
	         {
	            throw new IeciTdException(LdapError.EC_NOT_FOUND,
	                                      LdapError.EM_NOT_FOUND);
	         }
	        ne= search.getAttributes();
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
		return ne;
    }
    public Map getAttributesMap (String dn, String entidad) 
    {
        LdapSearchEx search=null;
        
		String filtro=null;
		String rootSearch="";
		
        int indice=dn.indexOf(",");
		if (indice != -1){
		    filtro=dn.substring(0,indice).trim();
		    int rootDnPos = dn.indexOf(UserTree.ldapRootDn);
		    
		    if (rootDnPos -1 != indice )
		        rootSearch=dn.substring(indice+1,rootDnPos-1).trim();
		}
		else
		{
		    rootSearch = "";
		    filtro = dn;
		}
		search = new LdapSearchEx();
		LdapConnCfg connCfg;
        LdapConnection ldapConn = null;
        Map map = null;
		try {
	        connCfg = UasConfigUtilLdap.createLdapConnConfig(entidad);
			ldapConn = new LdapConnection();
	        ldapConn.open(connCfg);
			search.initialize(ldapConn,rootSearch, 1,filtro, null, maxChildrenLdap);
			search.execute();
	        if (!search.next())
	           throw new IeciTdException(LdapError.EC_NOT_FOUND, LdapError.EM_NOT_FOUND);
	        
	        NamingEnumeration atts = search.getAttributes();
			search.release();
	        
	        map = new TreeMap();
	        while (atts.hasMore()){
	            Attribute o = (Attribute) atts.nextElement();
	            String clave = o.getID().toLowerCase();
	            String valor = "";
	            if (LdapAttribute.getGuidAttributeName(ldapConn).toLowerCase()
	                    .equals(clave)) {
	                Enumeration values = o.getAll();
	                Object element = values.nextElement();
	                valor = ieci.tecdoc.core.ldap.LdapBasicFns.formatGuid(ldapConn,
	                        element);
	            } else {
	                String s = o.toString();
	                StringTokenizer st = new StringTokenizer(s, ":");
	                clave = st.nextToken().toLowerCase().trim();
	                valor = st.nextToken().trim();
	            }
	            map.put(clave, valor);
	        }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ldapConn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
        return map;
    }
    
    public String getGroupFilter(String entidad)
    {
    	LdapConnCfg connCfg;
        LdapConnection ldapConn = null;
        String groupFilter = null;
		try {
	        connCfg = UasConfigUtilLdap.createLdapConnConfig(entidad);
			ldapConn = new LdapConnection();
	        ldapConn.open(connCfg);
	        groupFilter =  LdapFilter.getGroupFilter(ldapConn);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ldapConn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	    return groupFilter; 
    }
    public String getGuidAttributeName(String entidad)
    {
    	LdapConnCfg connCfg;
        LdapConnection ldapConn = null;
        String guid = null;
		try {
	        connCfg = UasConfigUtilLdap.createLdapConnConfig(entidad);
			ldapConn = new LdapConnection();
	        ldapConn.open(connCfg);
	        guid = LdapAttribute.getGuidAttributeName(ldapConn).toLowerCase(); //segun motor
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ldapConn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
        return guid;
    }
}
