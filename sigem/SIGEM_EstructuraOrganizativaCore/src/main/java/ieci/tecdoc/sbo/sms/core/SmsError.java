
package ieci.tecdoc.sbo.sms.core;

public final class SmsError
{
   
   private static final String EC_PREFIX = "IECI_TECDOC_SMS_";   
   
   // **************************************************************************
   
   public static final String EC_INVALID_SESS = 
   EC_PREFIX + "INVALID_SESS"; 
   
   public static final String EM_INVALID_SESS =
   "Invalid session";  
   
   // **************************************************************************

   private SmsError()
   {
   }
   
} // class
