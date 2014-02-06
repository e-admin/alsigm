package ieci.tecdoc.sbo.uas.std;

public final class UasError
{

   //~ Static fields/initializers ---------------------------------------------

   private static final String EC_PREFIX       = "IECI_TECDOC_UAS_";

   
   // **************************************************************************

   public static final String  EC_USER_LOCKED  =
   EC_PREFIX + "USER_LOCKED";
   
   public static final String  EM_USER_LOCKED  = 
   "User is locked";

   // **************************************************************************

   public static final String  EC_MUST_CHANGE_PWD =
   EC_PREFIX + "MUST_CHANGE_PWD";

   public static final String  EM_MUST_CHANGE_PWD =
   "Must change password";

   // **************************************************************************

   public static final String  EC_INVALID_PWD_LEN =
   EC_PREFIX + "INVALID_PWD_LEN";

   public static final String  EM_INVALID_PWD_LEN = 
   "Invalid password length";

   // **************************************************************************

   public static final String  EC_OLD_AND_NEW_PWD_CANNOT_MATCH =
   EC_PREFIX + "OLD_AND_NEW_PWD_CANNOT_MATCH";

   public static final String  EM_OLD_AND_NEW_PWD_CANNOT_MATCH =
   "Old and new password cannot match";

   // **************************************************************************

   public static final String  EC_TWO_NEW_PWDS_DO_NOT_MATCH = 
   EC_PREFIX + "TWO_NEW_PWDS_DO_NOT_MATCH";

   public static final String  EM_TWO_NEW_PWDS_DO_NOT_MATCH =
   "The two entries of the new password do not match";

// **************************************************************************

   public static final String  EC_PWD_MUST_BE_ALFANUM =
   EC_PREFIX + "MUST_BE_ALFANUM";

   public static final String  EM_PWD_MUST_BE_ALFANUM = 
   "The new password must has numbers and letters ";
   
}