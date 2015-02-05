/*
 * Created on 04-abr-2005
 *
 */
package ieci.tecdoc.mvc.service.adminUser.ldap;

import ieci.tecdoc.core.db.DBSessionManager;
import ieci.tecdoc.core.db.DbConnection;
import ieci.tecdoc.core.exception.IeciTdException;
import ieci.tecdoc.core.ldap.LdapAttribute;
import ieci.tecdoc.core.ldap.LdapConnCfg;
import ieci.tecdoc.core.ldap.LdapConnection;
import ieci.tecdoc.core.ldap.LdapError;
import ieci.tecdoc.core.ldap.LdapFilter;
import ieci.tecdoc.core.ldap.LdapSearch;
import ieci.tecdoc.mvc.action.adminUser.ldap.UserTree;
import ieci.tecdoc.mvc.util.ldap.Info;
import ieci.tecdoc.mvc.util.ldap.LdapSearchEx;
import ieci.tecdoc.sbo.uas.ldap.UasConfigUtil;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;

import com.sun.jndi.ldap.LdapURL;

/**
 * @author Antonio María
 *
 */
public class ServiceLdap {
    
    private LdapConnection ldapConn;
    private int maxChildrenLdap;
    public ServiceLdap(int maxChildrenLdap, String entidad) throws Exception
    {
        DbConnection.open(DBSessionManager.getSession(entidad));
        LdapConnCfg connCfg = UasConfigUtil.createLdapConnConfig(entidad);
        
        this.ldapConn = new LdapConnection();
        this.maxChildrenLdap = maxChildrenLdap;
        ldapConn.open(connCfg);
    }
    public LdapConnection getLdapConnection()
    {
        return ldapConn;
    }
    
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
    public boolean hasChildLdap(String parent) throws Exception
    {
        
        String s = "";
        if (!parent.equals("")){
            int i = parent.indexOf(UserTree.LdapRootDn);
            s = parent.substring(0,i-1);
        }
        int r=0;
        boolean hasChild = false;
		//LdapConnection ldapConn = new LdapConnection();

		//DbConnection.open(CfgMdoConfig.getDbConfig());
		//LdapConnCfg connCfg = UasConfigUtil.createLdapConnConfig();
        //ldapConn.open(connCfg);

		LdapSearch search = new LdapSearch();
		search.initialize(ldapConn,s, 1,"objectclass=*", null, maxChildrenLdap);
		search.execute();
			
		if (search.next())
		    hasChild = true;
			        
		search.release();

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
    public List GetLdapChildList(String Parent) throws Exception {
        int i=0;
        String Childs [] = null;
        List hijos = new ArrayList();
				
        
        String s = "";
        if (!Parent.equals("")){
            i = Parent.indexOf(UserTree.LdapRootDn);
            s = Parent.substring(0,i-1);
        }
		//LdapConnection ldapConn = new LdapConnection();		
		//DbConnection.open(CfgMdoConfig.getDbConfig());
		//LdapConnCfg connCfg = UasConfigUtil.createLdapConnConfig();
        //ldapConn.open(connCfg);
        
		LdapURL Ldap_url = new LdapURL(ldapConn.getUrl());
		//String LdapRootDn = Ldap_url.getDN();

		LdapSearchEx search=null;
		
		search = new LdapSearchEx();
		search.initialize(ldapConn,s, 1,"objectclass=*", null, maxChildrenLdap);
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

    public NamingEnumeration getObjectAttributes(String dn ) throws Exception {
		//LdapConnection ldapConn = new LdapConnection();		
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
		//DbConnection.open(CfgMdoConfig.getDbConfig());
		//LdapConnCfg connCfg = UasConfigUtil.createLdapConnConfig();
 		//ldapConn.open(connCfg);
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
    public Map getAttributesMap (String dn) throws Exception
    {
        LdapSearchEx search=null;
        
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
		search = new LdapSearchEx();
		search.initialize(ldapConn,rootSearch, 1,filtro, null, maxChildrenLdap);
		search.execute();
        if (!search.next())
           throw new IeciTdException(LdapError.EC_NOT_FOUND, LdapError.EM_NOT_FOUND);
        
        NamingEnumeration atts = search.getAttributes();
		search.release();
        
        Map map = new TreeMap();
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
        return map;
    }
    
    public String getGroupFilter()
    {
        String groupFilter =  LdapFilter.getGroupFilter(ldapConn);
        return groupFilter; 
    }
    public String getGuidAttributeName()
    {
        String guid = LdapAttribute.getGuidAttributeName(ldapConn).toLowerCase(); //segun motor
        return guid;
    }
}
