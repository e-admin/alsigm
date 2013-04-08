
package es.ieci.tecdoc.isicres.admin.base.dbex;

import java.io.InputStream;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;

import es.ieci.tecdoc.isicres.admin.base.exception.IeciTdException;
import es.ieci.tecdoc.isicres.admin.base.types.IeciTdType;

   
public final class DbDataType
{

   public static final int UNSPECIFIED = 0;
   
   public static final int SHORT_TEXT    = IeciTdType.SHORT_TEXT;   
   public static final int LONG_TEXT     = IeciTdType.LONG_TEXT;   
   public static final int SHORT_INTEGER = IeciTdType.SHORT_INTEGER;   
   public static final int LONG_INTEGER  = IeciTdType.LONG_INTEGER;   
   public static final int SHORT_DECIMAL = IeciTdType.SHORT_DECIMAL;
   public static final int LONG_DECIMAL  = IeciTdType.LONG_DECIMAL;
   public static final int DATE_TIME     = IeciTdType.DATE_TIME;
   public static final int TIMESTAMP 	 = IeciTdType.TIMESTAMP;
   public static final int LONG_BINARY   = IeciTdType.LONG_BINARY;
   public static final int BINARY_STREAM   = IeciTdType.BINARY_STREAM;
   
   public static final String NULL_SHORT_TEXT    = IeciTdType.NULL_SHORT_TEXT;   
   public static final String NULL_LONG_TEXT     = IeciTdType.NULL_LONG_TEXT;   
   public static final short  NULL_SHORT_INTEGER = IeciTdType.NULL_SHORT_INTEGER;
   public static final int    NULL_LONG_INTEGER  = IeciTdType.NULL_LONG_INTEGER;
   public static final float  NULL_SHORT_DECIMAL = IeciTdType.NULL_SHORT_DECIMAL;
   public static final double NULL_LONG_DECIMAL  = IeciTdType.NULL_LONG_DECIMAL;
   public static final Date   NULL_DATE_TIME     = IeciTdType.NULL_DATE_TIME;
   public static final Timestamp NULL_TIMESTAMP	 = IeciTdType.NULL_TIMESTAMP;
   public static final byte[] NULL_LONG_BINARY   = IeciTdType.NULL_LONG_BINARY;
   public static final InputStream NULL_BINARY_STREAM   = IeciTdType.NULL_BINARY_STREAM;
   public static final byte[] NULL_BYTES   = IeciTdType.NULL_BYTES;
   
   private DbDataType()
   {
   }

   static int getJdbcType(int dataType) throws Exception
   {
      
      int type;
      
      if (dataType == SHORT_TEXT)
         type = Types.VARCHAR;
      else if (dataType == LONG_TEXT)
         type = Types.LONGVARCHAR;         
      else if (dataType == SHORT_INTEGER)         
         type = Types.SMALLINT;         
      else if (dataType == LONG_INTEGER)
         type = Types.INTEGER;         
      else if (dataType == SHORT_DECIMAL)
         type = Types.REAL;         
      else if (dataType == LONG_DECIMAL)
         type = Types.FLOAT;         
      else if (dataType == DATE_TIME)
         type = Types.TIMESTAMP;
      else if (dataType == TIMESTAMP)
          type = Types.TIMESTAMP;
      else if (dataType == BINARY_STREAM)
         type = Types.BLOB;    
      else
      {
         throw new IeciTdException(DbError.EC_INVALID_DATA_TYPE,
                                   DbError.EM_INVALID_DATA_TYPE);         
      }
      
      return type;
      
   }

   public static String getNativeDbType(int dataType, int engine, String version) throws Exception
   {

      String type;

      if (engine == DbEngine.SQLSERVER)
         type = getSqlServerNativeDbType(dataType);
      else if (engine == DbEngine.ORACLE)
         type = getOracleNativeDbType(dataType, version);
      else if (engine == DbEngine.POSTGRESQL)
         type = getPostgreNativeDbType(dataType);
      else if (engine == DbEngine.DB2)
    	  type = getDb2NativeDbType(dataType);
      else
      {
         throw new IeciTdException(DbError.EC_INVALID_ENGINE,
                                   DbError.EM_INVALID_ENGINE);
      }

      return type;

   }
   
   private static String getSqlServerNativeDbType(int dataType) throws Exception
   {

      String type;

      if (dataType == SHORT_TEXT)
         type = "varchar";
      else if (dataType == LONG_TEXT)
         type = "text";
      else if (dataType == SHORT_INTEGER)
         type = "smallint";
      else if (dataType == LONG_INTEGER)
         type = "int";
      else if (dataType == SHORT_DECIMAL)
         type = "real";
      else if (dataType == LONG_DECIMAL)
         type = "float";
      else if (dataType == DATE_TIME)
         type = "datetime";
      else if (dataType == TIMESTAMP)
          type = "timestamp";
      else
      {
         throw new IeciTdException(DbError.EC_INVALID_DATA_TYPE,
                                   DbError.EM_INVALID_DATA_TYPE);
      }
      
      return type;

   }
   
   private static String getOracleNativeDbType(int dataType, String version) throws Exception
   {

      String type;

      if (dataType == SHORT_TEXT)
         type = "VARCHAR2";
      else if (dataType == LONG_TEXT)
      {
    	 if (version.indexOf(DbEngine.ORACLE_VERSION_8) == -1)
            type = "CLOB";
         else
            type = "LONG";
      }
      else if (dataType == SHORT_INTEGER)
         type = "NUMBER(5)";
      else if (dataType == LONG_INTEGER)
         type = "NUMBER(10)";
      else if (dataType == SHORT_DECIMAL)
         type = "NUMBER";
      else if (dataType == LONG_DECIMAL)
         type = "NUMBER";
      else if (dataType == DATE_TIME)
         type = "DATE";
      else if (dataType == TIMESTAMP)
          type = "TIMESTAMP";
      else if (dataType == LONG_BINARY)
         type = "BLOB";
      else
      {
         throw new IeciTdException(DbError.EC_INVALID_DATA_TYPE,
                                   DbError.EM_INVALID_DATA_TYPE);
      }
      
      return type;

   }
 
   private static String getPostgreNativeDbType(int dataType) throws Exception
   {

      String type;

      if (dataType == SHORT_TEXT)
         type = "character varying";
      else if (dataType == LONG_TEXT)
         type = "text";
      else if (dataType == SHORT_INTEGER)
         type = "integer";
      else if (dataType == LONG_INTEGER)
         type = "integer";
      else if (dataType == SHORT_DECIMAL)
         type = "double precision";
      else if (dataType == LONG_DECIMAL)
         type = "double precision";
      else if (dataType == DATE_TIME)
         type = "timestamp without time zone";
      else if (dataType == TIMESTAMP)
          type = "timestamp without time zone";
      else
      {
         throw new IeciTdException(DbError.EC_INVALID_DATA_TYPE,
                                   DbError.EM_INVALID_DATA_TYPE);
      }
      
      return type;

   }
   
   private static String getDb2NativeDbType(int dataType) throws Exception
   {
      
	  String type;
	   
	  if (dataType == SHORT_TEXT)
		 type = "varchar";
	  else if (dataType == LONG_TEXT)
		 type = "CLOB"; 
	  else if (dataType == SHORT_INTEGER)
		 type = "smallint";
	  else if (dataType == LONG_INTEGER)
	     type = "integer";
	  else if (dataType == SHORT_DECIMAL)
		 type = "double" ;
	  else if (dataType == LONG_DECIMAL)
		 type = "double";
	  else if (dataType == DATE_TIME)
		 type = "timestamp";
	  else if (dataType == LONG_BINARY)
		 type = "BLOB"; 
	  else
	  {
		  throw new IeciTdException(DbError.EC_INVALID_DATA_TYPE,
				                    DbError.EM_INVALID_DATA_TYPE);
	  }
	  
	  return type;
	  
   }   
   
   public static boolean isNullShortText(String value)
   {
      return (value == NULL_SHORT_TEXT);
   }
   
   public static boolean isNullLongText(String value)
   {
      return (value == NULL_LONG_TEXT);
   }
   
   public static boolean isNullShortInteger(short value)
   {
      return (value == NULL_SHORT_INTEGER);
   }
   
   public static boolean isNullLongInteger(int value)
   {
      return (value == NULL_LONG_INTEGER);
   }
   
   public static boolean isNullShortDecimal(float value)
   {
      return (value == NULL_SHORT_DECIMAL);
   }
   
   public static boolean isNullLongDecimal(double value)
   {
      return (value == NULL_LONG_DECIMAL);
   }
   
   public static boolean isNullDateTime(Date value)
   {
      return (value == NULL_DATE_TIME);
   }
      
   public static boolean isNullTimestamp(Date value)
   {
      return (value == NULL_TIMESTAMP);
   }
   
   public static boolean isNullLongBinary(byte[] value)
   {
      return (value == NULL_LONG_BINARY);
   }
    
   public static boolean isNullBinaryStream(byte[] value)
   {
      return (value.equals(NULL_BINARY_STREAM));
   }
   
} // class
