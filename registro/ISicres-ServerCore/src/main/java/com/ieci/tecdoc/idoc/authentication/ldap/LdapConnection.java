
package com.ieci.tecdoc.idoc.authentication.ldap;

import java.util.Hashtable;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.directory.InitialDirContext;

import com.ieci.tecdoc.common.exception.SecurityException;
import com.ieci.tecdoc.idoc.decoder.dbinfo.LDAPDef;

public final class LdapConnection
{
   
   private InitialDirContext m_initDirCtx;  
   private int               m_engine;       
   private int               m_provider;
   private String            m_url;

   public LdapConnection()
   {
      m_initDirCtx = null;
      m_engine     = 0;      
      m_provider   = 0;
   }
   
   /*
    
    engine: ActiveDirectory, iPlanet, ...
    provider: Sun, ...
    url: "ldap://host:port/baseDn"
    poolTimeout: en milisegundos. 
    
   */
   
   public void open(LdapConnCfg config) throws Exception{
	   open(config,config.getUser(),config.getPwd());
   }
   
   public void open(LdapConnCfg config,String user,String passwd) throws Exception{
	   Hashtable env;      
	      
	      m_engine = config.getEngine();
	      m_provider = config.getProvider();     
	      m_url         = config.getUrl();
	            
	      env = new Hashtable();
	         
	      env.put(Context.INITIAL_CONTEXT_FACTORY, getInitCtxFry(m_provider));
	      env.put(Context.PROVIDER_URL, m_url);
	      env.put(Context.SECURITY_AUTHENTICATION, "simple");
	      env.put(Context.SECURITY_PRINCIPAL, user);
	      env.put(Context.SECURITY_CREDENTIALS, passwd);
	      
	      env.put("java.naming.ldap.version", "2");
	      
	      if (m_engine == LdapEngine.ACTIVE_DIRECTORY)
	         env.put("java.naming.ldap.attributes.binary", "objectGUID");
	      
	      configurePool(m_provider, env, config.getPool(), config.getPoolTimeOut());
	      
	      try{
	         m_initDirCtx = new InitialDirContext(env);
	      } catch (AuthenticationException e) {
				throw new SecurityException(
						SecurityException.ERROR_CAN_NOT_CONNECT_LDAP);
	      }
   }
   
   public void open(LDAPDef ldapDef, String userDn, String userPwd, int provider)
                    throws Exception
   {
      
      Hashtable env;      
      
      m_engine = ldapDef.getLdapEngine();
      m_provider = provider;
      
	  StringBuffer buffer = new StringBuffer();
	  
	    buffer.append("ldap://");
		buffer.append(ldapDef.getLdapServer());
		buffer.append(":");
		buffer.append(ldapDef.getLdapPort());
      
      m_url         = buffer.toString();
            
      env = new Hashtable();
         
      env.put(Context.INITIAL_CONTEXT_FACTORY, getInitCtxFry(provider));
      env.put(Context.PROVIDER_URL, buffer.toString());
      env.put(Context.SECURITY_AUTHENTICATION, "simple");
      env.put(Context.SECURITY_PRINCIPAL, userDn);
      env.put(Context.SECURITY_CREDENTIALS, userPwd);
      
      env.put("java.naming.ldap.version", "2");
      
      if (ldapDef.getLdapEngine() == LdapEngine.ACTIVE_DIRECTORY)
         env.put("java.naming.ldap.attributes.binary", "objectGUID");
      
      try
      {
         m_initDirCtx = new InitialDirContext(env);
      }
      catch (AuthenticationException e)
      {
			throw new SecurityException(
					SecurityException.ERROR_CAN_NOT_CONNECT_LDAP);
      }
      
   }
   
   private static void configurePool(int provider, Hashtable env,boolean pool, int poolTimeout) {
		if (!pool) return;
		
		if (provider == LdapProvider.SUN) {
			env.put("com.sun.jndi.ldap.connect.pool", "true");
			env.put("com.sun.jndi.ldap.connect.pool.timeout", 
			Integer.toString(poolTimeout));         
		}
   }
   
   public void close() throws Exception
   {      

      if (m_initDirCtx != null)
      {
         m_initDirCtx.close();
         m_initDirCtx = null;                              
      }
 
   } 

   public static void ensureClose(LdapConnection conn, Exception exc)
                      throws Exception
   {
      
      try
      {
         if (conn != null) conn.close();
         throw exc;
      }
      catch (Exception e)
      {
         throw exc;
      }
      
   }

   public InitialDirContext getInitDirContext()
   {
      return m_initDirCtx;
   }

   public int getEngine()
   {
      return m_engine;
   }

   public int getProvider()
   {
      return m_provider;
   }

   public String getUrl()
   {
      return m_url;
   }

   public String getBaseDn() throws Exception
   {
      return m_initDirCtx.getNameInNamespace();
   }
      
  
   // **************************************************************************
   
   private static void checkEngine(int engine) throws Exception
   {
      
      if (engine == LdapEngine.ACTIVE_DIRECTORY) return;
      if (engine == LdapEngine.I_PLANET) return;
      if (engine == LdapEngine.OPENLDAP) return;
      
		throw new SecurityException(
				SecurityException.ERROR_AUTHENTICATION_POLICY_NOTFOUND);
      
   }
   
   private static String getInitCtxFry(int provider)
   {
      
      String fry = null;
      
      if (provider == LdapProvider.SUN)
         fry = "com.sun.jndi.ldap.LdapCtxFactory";
      
      return fry;
      
   }   
} 
