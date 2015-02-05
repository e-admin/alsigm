package es.ieci.tecdoc.isicres.admin.base.db;

public final class DbEngine
{

   public static final int    SQLSERVER     = 1;
   public static final int    ORACLE        = 2;
   public static final int    MYSQL         = 3;
   
   /*
    * Modificaciones para adaptar la aplicación a DB2
    * 05-feb-2009 / SLuna
    * Inicio modificaciones
    */
   public static final int	  POSTGRESQL	= 4;
   public static final int	  DB2			= 5;
   /*
    * Fin modificaciones
    */
   

   public static final String SQLSERVER_STR  = "Microsoft";
   public static final String ORACLE_STR     = "Oracle";
   public static final String MYSQL_STR      = "MySQL";
   public static final String ORACLE_VERSION_8 = "Oracle8";
   
   /*
    * Modificaciones para adaptar la aplicación a DB2
    * 05-feb-2009 / SLuna
    * Inicio modificaciones
    */
   public static final String POSTGRESQL_STR = "PostgreSQL";
   public static final String DB2_STR		 = "DB2";
   /*
    * Fin modificaciones
    */
   
   // public static final String ORACLE_VERSION_9 = "Oracle9i";
   
   private DbEngine()
   {
   }

} // class
