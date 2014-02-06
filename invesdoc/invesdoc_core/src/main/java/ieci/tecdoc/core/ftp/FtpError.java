package ieci.tecdoc.core.ftp;

public final class FtpError
{

   private static final String EC_PREFIX       = "IECI_TECDOC_CORE_FTP_";

   // **************************************************************************

   public static final String  EC_INVALID_RESP = EC_PREFIX + "INVALID_RESP";

   public static final String  EM_INVALID_RESP = "Invalid response";

   // **************************************************************************

   public static final String  EC_NATIVE_ERR   = EC_PREFIX + "NATIVE_ERR";

   public static final String  EM_NATIVE_ERR   = "Native error";

   // **************************************************************************

   private FtpError()
   {
   }

} // class
