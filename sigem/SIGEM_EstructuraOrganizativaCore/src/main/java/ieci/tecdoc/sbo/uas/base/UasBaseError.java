
package ieci.tecdoc.sbo.uas.base;

public final class UasBaseError
{
   
   private static final String EC_PREFIX = "IECI_TECDOC_UAS_";   
   
   // **************************************************************************
        
   public static final String EC_INVALID_AUTH_SPEC = 
   EC_PREFIX + "INVALID_AUTH_SPEC"; 
   
   public static final String EM_INVALID_AUTH_SPEC =
   "Invalid authorization specification (user / password)";  
   
// **************************************************************************

   public static final String  EC_INVALID_USER_NAME =
   EC_PREFIX + "INVALID_USER_NAME";
   
   public static final String  EM_INVALID_USER_NAME =
   "Invalid User Name";

   
   // **************************************************************************
        
         
   public static final String EC_TOO_MANY_LOGIN_ATTEMPTS = 
   EC_PREFIX + "TOO_MANY_LOGIN_ATTEMPTS"; 
   
   public static final String EM_TOO_MANY_LOGIN_ATTEMPTS =
   "Too many login attempts";  
   
      
   // **************************************************************************
          
  
   private UasBaseError()
   {
   }
   
} // class
