package ieci.tecdoc.sbo.idoc.login;

public final class LoginError
{

   private static final String EC_PREFIX                = "IECI_TECDOC_IDOC_LOGIN_";

   // **************************************************************************

   public static final String  EC_NOT_AUTH_ENTER_APP    = EC_PREFIX + "NOT_AUTH_ENTER_APP";

   public static final String  EM_NOT_AUTH_ENTER_APP    = "No está autorizado a entrar en la aplicación";

   // **************************************************************************

   private LoginError()
   {
   }

} // class
