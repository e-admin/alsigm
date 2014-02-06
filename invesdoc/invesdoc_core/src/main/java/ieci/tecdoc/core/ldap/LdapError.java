
package ieci.tecdoc.core.ldap;
   
public final class LdapError
{
    
   private static final String EC_PREFIX = "IECI_TECDOC_CORE_LDAP_";

   // **************************************************************************
        
   public static final String EC_INVALID_AUTH_SPEC = 
   EC_PREFIX + "INVALID_AUTH_SPEC"; 
   
   public static final String EM_INVALID_AUTH_SPEC =
   "Invalid authorization specification (user / password)";   
   
   // **************************************************************************
       
   public static final String EC_INVALID_ENGINE = 
   EC_PREFIX + "INVALID_ENGINE";
      
   public static final String EM_INVALID_ENGINE =
   "Invalid engine";
   
   // **************************************************************************
       
   public static final String EC_INVALID_PROVIDER = 
   EC_PREFIX + "INVALID_PROVIDER";
      
   public static final String EM_INVALID_PROVIDER =
   "Invalid provider";
   
   // **************************************************************************
       
   public static final String EC_NOT_FOUND = 
   EC_PREFIX + "NOT_FOUND";
      
   public static final String EM_NOT_FOUND =
   "Not found";   
   
   // **************************************************************************
       
   public static final String EC_INVALID_DN = 
   EC_PREFIX + "INVALID_DN";
      
   public static final String EM_INVALID_DN =
   "Invalid Dn";
   
   // **************************************************************************   
   
   private LdapError()
   {
   }
   
} // class
