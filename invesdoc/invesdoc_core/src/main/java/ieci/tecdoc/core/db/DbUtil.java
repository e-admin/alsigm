package ieci.tecdoc.core.db;

import org.apache.log4j.Logger;

import ieci.tecdoc.core.exception.IeciTdException;

import java.sql.SQLWarning;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public final class DbUtil
{

   /**
    * Logger for this class
    */
   private static final Logger logger = Logger.getLogger(DbUtil.class);

   private DbUtil()
   {
   }

   public static void executeStatement(String stmtText) throws Exception
   {

      Statement stmt = null;

      try
      {
         stmt = DbConnection.getJdbcConnection().createStatement();
         stmt.execute(stmtText);
         stmt.close();
      }
      catch (Exception e)
      {
         try
         {
            if (stmt != null) stmt.close();
            throw e;
         }
         catch (Exception e1)
         {
            throw e;
         }
      }

   }

   public static String getColumnNames(DbColumnDef[] colDefs)
   {

      StringBuffer tbdr;
      int count, i;

      tbdr = new StringBuffer();

      count = colDefs.length;

      tbdr.append(colDefs[0].getName());

      for (i = 1; i < count; i++)
         tbdr.append(",").append(colDefs[i].getName());

      return tbdr.toString();

   }

   public static String getColumnNames(DbColumnDef colDef1)
   {
      return colDef1.getName();
   }

   public static String getColumnNames(DbColumnDef colDef1, DbColumnDef colDef2)
   {

      StringBuffer tbdr;

      tbdr = new StringBuffer();

      tbdr.append(colDef1.getName());
      tbdr.append(',').append(colDef2.getName());

      return tbdr.toString();

   }

   public static String getColumnNames(DbColumnDef colDef1,
         DbColumnDef colDef2, DbColumnDef colDef3)
   {

      StringBuffer tbdr;

      tbdr = new StringBuffer();

      tbdr.append(colDef1.getName());
      tbdr.append(',').append(colDef2.getName());
      tbdr.append(',').append(colDef3.getName());

      return tbdr.toString();

   }

   public static String getColumnNames(DbColumnDef colDef1,
         DbColumnDef colDef2, DbColumnDef colDef3, DbColumnDef colDef4)
   {

      StringBuffer tbdr;

      tbdr = new StringBuffer();

      tbdr.append(colDef1.getName());
      tbdr.append(',').append(colDef2.getName());
      tbdr.append(',').append(colDef3.getName());
      tbdr.append(',').append(colDef4.getName());

      return tbdr.toString();

   }

   public static String getColumnNames(DbColumnDef colDef1,
         DbColumnDef colDef2, DbColumnDef colDef3, DbColumnDef colDef4,
         DbColumnDef colDef5)
   {

      StringBuffer tbdr;

      tbdr = new StringBuffer();

      tbdr.append(colDef1.getName());
      tbdr.append(',').append(colDef2.getName());
      tbdr.append(',').append(colDef3.getName());
      tbdr.append(',').append(colDef4.getName());
      tbdr.append(',').append(colDef5.getName());

      return tbdr.toString();

   }

   public static String getColumnNames(DbColumnDef colDef1,
         DbColumnDef colDef2, DbColumnDef colDef3, DbColumnDef colDef4,
         DbColumnDef colDef5, DbColumnDef colDef6)
   {

      StringBuffer tbdr;

      tbdr = new StringBuffer();

      tbdr.append(colDef1.getName());
      tbdr.append(',').append(colDef2.getName());
      tbdr.append(',').append(colDef3.getName());
      tbdr.append(',').append(colDef4.getName());
      tbdr.append(',').append(colDef5.getName());
      tbdr.append(',').append(colDef6.getName());

      return tbdr.toString();

   }

   public static String getColumnNames(DbColumnDef colDef1,
         DbColumnDef colDef2, DbColumnDef colDef3, DbColumnDef colDef4,
         DbColumnDef colDef5, DbColumnDef colDef6, DbColumnDef colDef7)
   {

      StringBuffer tbdr;

      tbdr = new StringBuffer();

      tbdr.append(colDef1.getName());
      tbdr.append(',').append(colDef2.getName());
      tbdr.append(',').append(colDef3.getName());
      tbdr.append(',').append(colDef4.getName());
      tbdr.append(',').append(colDef5.getName());
      tbdr.append(',').append(colDef6.getName());
      tbdr.append(',').append(colDef7.getName());

      return tbdr.toString();

   }

   public static String getColumnNames(DbColumnDef colDef1,
         DbColumnDef colDef2, DbColumnDef colDef3, DbColumnDef colDef4,
         DbColumnDef colDef5, DbColumnDef colDef6, DbColumnDef colDef7,
         DbColumnDef colDef8)
   {

      StringBuffer tbdr;

      tbdr = new StringBuffer();

      tbdr.append(colDef1.getName());
      tbdr.append(',').append(colDef2.getName());
      tbdr.append(',').append(colDef3.getName());
      tbdr.append(',').append(colDef4.getName());
      tbdr.append(',').append(colDef5.getName());
      tbdr.append(',').append(colDef6.getName());
      tbdr.append(',').append(colDef7.getName());
      tbdr.append(',').append(colDef8.getName());

      return tbdr.toString();

   }

   public static String getColumnNames(DbColumnDef colDef1,
         DbColumnDef colDef2, DbColumnDef colDef3, DbColumnDef colDef4,
         DbColumnDef colDef5, DbColumnDef colDef6, DbColumnDef colDef7,
         DbColumnDef colDef8, DbColumnDef colDef9)
   {

      StringBuffer tbdr;

      tbdr = new StringBuffer();

      tbdr.append(colDef1.getName());
      tbdr.append(',').append(colDef2.getName());
      tbdr.append(',').append(colDef3.getName());
      tbdr.append(',').append(colDef4.getName());
      tbdr.append(',').append(colDef5.getName());
      tbdr.append(',').append(colDef6.getName());
      tbdr.append(',').append(colDef7.getName());
      tbdr.append(',').append(colDef8.getName());
      tbdr.append(',').append(colDef9.getName());

      return tbdr.toString();

   }

   public static String getColumnNames(DbColumnDef colDef1,
         DbColumnDef colDef2, DbColumnDef colDef3, DbColumnDef colDef4,
         DbColumnDef colDef5, DbColumnDef colDef6, DbColumnDef colDef7,
         DbColumnDef colDef8, DbColumnDef colDef9, DbColumnDef colDef10)
   {

      StringBuffer tbdr;

      tbdr = new StringBuffer();

      tbdr.append(colDef1.getName());
      tbdr.append(',').append(colDef2.getName());
      tbdr.append(',').append(colDef3.getName());
      tbdr.append(',').append(colDef4.getName());
      tbdr.append(',').append(colDef5.getName());
      tbdr.append(',').append(colDef6.getName());
      tbdr.append(',').append(colDef7.getName());
      tbdr.append(',').append(colDef8.getName());
      tbdr.append(',').append(colDef9.getName());
      tbdr.append(',').append(colDef10.getName());

      return tbdr.toString();

   }

   public static String getColumnNames(DbColumnDef colDef1,
         DbColumnDef colDef2, DbColumnDef colDef3, DbColumnDef colDef4,
         DbColumnDef colDef5, DbColumnDef colDef6, DbColumnDef colDef7,
         DbColumnDef colDef8, DbColumnDef colDef9, DbColumnDef colDef10,
         DbColumnDef colDef11)
   {

      StringBuffer tbdr;

      tbdr = new StringBuffer();

      tbdr.append(colDef1.getName());
      tbdr.append(',').append(colDef2.getName());
      tbdr.append(',').append(colDef3.getName());
      tbdr.append(',').append(colDef4.getName());
      tbdr.append(',').append(colDef5.getName());
      tbdr.append(',').append(colDef6.getName());
      tbdr.append(',').append(colDef7.getName());
      tbdr.append(',').append(colDef8.getName());
      tbdr.append(',').append(colDef9.getName());
      tbdr.append(',').append(colDef10.getName());
      tbdr.append(',').append(colDef11.getName());

      return tbdr.toString();

   }

   public static String getColumnNames(DbColumnDef colDef1,
         DbColumnDef colDef2, DbColumnDef colDef3, DbColumnDef colDef4,
         DbColumnDef colDef5, DbColumnDef colDef6, DbColumnDef colDef7,
         DbColumnDef colDef8, DbColumnDef colDef9, DbColumnDef colDef10,
         DbColumnDef colDef11, DbColumnDef colDef12)
   {

      StringBuffer tbdr;

      tbdr = new StringBuffer();

      tbdr.append(colDef1.getName());
      tbdr.append(',').append(colDef2.getName());
      tbdr.append(',').append(colDef3.getName());
      tbdr.append(',').append(colDef4.getName());
      tbdr.append(',').append(colDef5.getName());
      tbdr.append(',').append(colDef6.getName());
      tbdr.append(',').append(colDef7.getName());
      tbdr.append(',').append(colDef8.getName());
      tbdr.append(',').append(colDef9.getName());
      tbdr.append(',').append(colDef10.getName());
      tbdr.append(',').append(colDef11.getName());
      tbdr.append(',').append(colDef12.getName());

      return tbdr.toString();

   }

   public static String getColumnNames(DbColumnDef colDef1,
         DbColumnDef colDef2, DbColumnDef colDef3, DbColumnDef colDef4,
         DbColumnDef colDef5, DbColumnDef colDef6, DbColumnDef colDef7,
         DbColumnDef colDef8, DbColumnDef colDef9, DbColumnDef colDef10,
         DbColumnDef colDef11, DbColumnDef colDef12, DbColumnDef colDef13)
   {

      StringBuffer tbdr;

      tbdr = new StringBuffer();

      tbdr.append(colDef1.getName());
      tbdr.append(',').append(colDef2.getName());
      tbdr.append(',').append(colDef3.getName());
      tbdr.append(',').append(colDef4.getName());
      tbdr.append(',').append(colDef5.getName());
      tbdr.append(',').append(colDef6.getName());
      tbdr.append(',').append(colDef7.getName());
      tbdr.append(',').append(colDef8.getName());
      tbdr.append(',').append(colDef9.getName());
      tbdr.append(',').append(colDef10.getName());
      tbdr.append(',').append(colDef11.getName());
      tbdr.append(',').append(colDef12.getName());
      tbdr.append(',').append(colDef13.getName());

      return tbdr.toString();

   }

   public static Date getCurrentDateTime()
   {
      return new GregorianCalendar().getTime();
   }

   public static String getNativeDateTimeSyntax(Date data, boolean onlyDate)
         throws Exception
   {

      String str;
      int engine;

      engine = DbConnection.getEngine();

      str = getNativeDateTimeSyntax(engine, data, onlyDate);
      
      return str;

   }

   public static String getNativeDateTimeSyntax(int engine, Date data, boolean onlyDate) throws Exception
	{
	
		String str;
		
		if (engine == DbEngine.SQLSERVER)
		   str = getSqlServerNativeDateTimeSyntax(data, onlyDate);
		else if (engine == DbEngine.ORACLE)
		   str = getOracleNativeDateTimeSyntax(data, onlyDate);
		else if (engine == DbEngine.POSTGRESQL)
		   str = getPostGreSqlNativeDateTimeSyntax(data, onlyDate);
		else
		{
		   throw new IeciTdException(DbError.EC_INVALID_ENGINE,
		         DbError.EM_INVALID_ENGINE);
		}
		
		return str;
	
	}
   
   
   private static String getSqlServerNativeDateTimeSyntax(Date data,
         boolean onlyDate)
   {

      String str;
      String pattern;
      SimpleDateFormat sdf;

      if (onlyDate)
         pattern = "yyyy-MM-dd";
      else
         pattern = "yyyy-MM-dd HH:mm:ss";

      sdf = new SimpleDateFormat(pattern);

      str = "CONVERT(datetime,'" + sdf.format(data) + "',120)";

      return str;

   }

   private static String getOracleNativeDateTimeSyntax(Date data,
         boolean onlyDate)
   {

      String str;
      String pattern;
      SimpleDateFormat sdf;

      if (onlyDate)
         pattern = "yyyy-MM-dd";
      else
         pattern = "yyyy-MM-dd HH:mm:ss";

      sdf = new SimpleDateFormat(pattern);

      if (onlyDate)
         pattern = "YYYY-MM-DD";
      else
         pattern = "YYYY-MM-DD HH24:MI:SS";

      str = "TO_DATE('" + sdf.format(data) + "','" + pattern + "')";

      return str;

   }
   
   private static String getPostGreSqlNativeDateTimeSyntax(Date data,
	                                                       boolean onlyDate)
   {

	      String str;
	      String pattern;
	      SimpleDateFormat sdf;

	      if (onlyDate)
	         pattern = "yyyy-MM-dd";
	      else
	         pattern = "yyyy-MM-dd HH:mm:ss";

	      sdf = new SimpleDateFormat(pattern);

	      if (onlyDate)
	         pattern = "YYYY-MM-DD";
	      else
	         pattern = "YYYY-MM-DD HH24:MI:SS";

	      str = "TO_DATE('" + sdf.format(data) + "','" + pattern + "')";

	      return str;

   }
   
   public static void manipulateWarning(SQLWarning warning)
   {
      while (warning != null) {
         StringBuffer   stb         =  new StringBuffer("SQLWarning detected: "); 
         String         message     =  warning.getMessage();
         String         sqlState    =  warning.getSQLState();
         int            errorCode   =  warning.getErrorCode();
         
         stb.append(" warningMessage = ").append(warning.getMessage());
         stb.append(" sqlState = ").append(warning.getSQLState());
         stb.append(" errorCode = ").append(warning.getErrorCode());
         
         logger.warn(stb.toString());
         
         warning = warning.getNextWarning();
     }
         
   }

} // class
