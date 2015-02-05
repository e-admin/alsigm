package ieci.tecdoc.core.db;

public final class DbError
{

   private static final String EC_PREFIX             = "IECI_TECDOC_CORE_DATABASE_";

   // **************************************************************************

   public static final String  EC_INVALID_AUTH_SPEC  = EC_PREFIX
                                                           + "INVALID_AUTH_SPEC";

   public static final String  EM_INVALID_AUTH_SPEC  = "Invalid authorization specification (user / password)";

   // **************************************************************************

   public static final String  EC_INVALID_DATA_TYPE  = EC_PREFIX
                                                           + "INVALID_DATA_TYPE";

   public static final String  EM_INVALID_DATA_TYPE  = "Invalid data type";

   // **************************************************************************

   public static final String  EC_ALREADY_IN_TRANS   = EC_PREFIX
                                                           + "ALREADY_IN_TRANS";

   public static final String  EM_ALREADY_IN_TRANS   = "A transaction has already been started";

   // **************************************************************************

   public static final String  EC_NOT_IN_TRANS       = EC_PREFIX
                                                           + "NOT_IN_TRANS";

   public static final String  EM_NOT_IN_TRANS       = "A transaction has not been started";

   // **************************************************************************

   public static final String  EC_INVALID_ENGINE     = EC_PREFIX
                                                           + "INVALID_ENGINE";

   public static final String  EM_INVALID_ENGINE     = "Invalid engine";

   // **************************************************************************

   public static final String  EC_NOT_FOUND          = EC_PREFIX + "NOT_FOUND";

   public static final String  EM_NOT_FOUND          = "Not found";

   // **************************************************************************

   public static final String  EC_INVALID_CONN_STR   = EC_PREFIX
                                                           + "INVALID_CONN_STR";

   public static final String  EM_INVALID_CONN_STR   = "Invalid connection string";

   // **************************************************************************

   public static final String  EC_NO_EXIST_CONN      = EC_PREFIX
                                                           + "NO_EXIST_CONN";

   public static final String  EM_NO_EXIST_CONN      = "A Connection does not exists";

   // **************************************************************************

   public static final String  EC_NOT_CLOSED_CONN    = EC_PREFIX
                                                           + "NOT_CLOSED_CONN";

   public static final String  EM_NOT_CLOSED_CONN    = "The Connection has not been closed";

   // **************************************************************************

   public static final String  EC_ALREADY_EXIST_CONN = EC_PREFIX
                                                           + "ALREADY_EXIST_CONN";

   public static final String  EM_ALREADY_EXIST_CONN = "Already exist a connection";

   // **************************************************************************

   private DbError()
   {
   }

} // class
