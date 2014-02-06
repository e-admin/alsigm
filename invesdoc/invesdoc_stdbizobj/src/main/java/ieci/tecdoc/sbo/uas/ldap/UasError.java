package ieci.tecdoc.sbo.uas.ldap;

public final class UasError
{

   //~ Static fields/initializers ---------------------------------------------

   private static final String EC_PREFIX       = "IECI_TECDOC_UAS_";

   
   // **************************************************************************

   public static final String  EC_NO_USER_IN_IDOC_SYSTEM  =
   EC_PREFIX + "NO_USER_IN_IDOC_SYSTEM";
   
   public static final String  EM_NO_USER_IN_IDOC_SYSTEM  = 
   "User isn´t owned to invesDoc System";  

}