
package ieci.tecdoc.sbo.config;

import ieci.tecdoc.core.ldap.LdapEngine;


public final class CfgDefs
{
   //Motor de búsqueda documental	
   public static final int FTS_ENGINE_NONE         = 0;   
   public static final int FTS_ENGINE_INTERMEDIA   = 1;   //Intermedia de Oracle
   public static final int FTS_ENGINE_INDEXSERVER  = 3;   //Microsoft Index Server   
   
   //Plataforma del motor documental
   public static final int FTS_PLATFORM_NONE       = 0;
   public static final int FTS_PLATFORM_WINDOWS    = 1;
   public static final int FTS_PLATFORM_NT         = 2;
   public static final int FTS_PLATFORM_UNIX       = 3;
      
   //Motor de servidor Ldap	
   public static final int LDAP_ENGINE_NONE               = 0;   
   public static final int LDAP_ENGINE_ACTIVE_DIRECTORY   = LdapEngine.ACTIVE_DIRECTORY;   
   public static final int LDAP_ENGINE_IPLANET            = LdapEngine.I_PLANET; 
   public static final int LDAP_ENGINE_OPENLDAP           = LdapEngine.OPENLDAP;   
   
   private CfgDefs()
   {
   }
   
} // class
