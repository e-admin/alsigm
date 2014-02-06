package ieci.tecdoc.core.db;

public final class DbEngine
{

   public static final int    SQLSERVER     = 1;
   public static final int    ORACLE        = 2;
   public static final int    MYSQL         = 3;
   public static final int    POSTGRESQL    = 4;
   public static final int    DB2           = 5;

   public static final String SQLSERVER_STR    = "Microsoft";
   public static final String ORACLE_STR       = "Oracle";
   public static final String MYSQL_STR        = "MySQL";
   public static final String POSTGRESQL_STR   = "PostgreSQL";
   public static final String DB2_STR          = "DB2/NT";
   public static final String DB2_STR2         = "DB2 UDB for AS/400";
   
   public static final String ORACLE_VERSION_8 = "Oracle8";
   
   private DbEngine()
   {
   }

} // class
