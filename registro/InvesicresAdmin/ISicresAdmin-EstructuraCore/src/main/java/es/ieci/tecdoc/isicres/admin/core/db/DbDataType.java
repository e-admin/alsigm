
package es.ieci.tecdoc.isicres.admin.core.db;

import java.sql.Types;
import java.util.Date;

import es.ieci.tecdoc.isicres.admin.core.exception.IeciTdException;
import es.ieci.tecdoc.isicres.admin.core.types.IeciTdType;
   
public final class DbDataType
{
      
   public static final int SHORT_TEXT    = IeciTdType.SHORT_TEXT;   
   public static final int LONG_TEXT     = IeciTdType.LONG_TEXT;   
   public static final int SHORT_INTEGER = IeciTdType.SHORT_INTEGER;   
   public static final int LONG_INTEGER  = IeciTdType.LONG_INTEGER;   
   public static final int SHORT_DECIMAL = IeciTdType.SHORT_DECIMAL;
   public static final int LONG_DECIMAL  = IeciTdType.LONG_DECIMAL;
   public static final int DATE_TIME     = IeciTdType.DATE_TIME;
   public static final int LONG_BIN      = IeciTdType.LONG_BIN;

   
   public static final String NULL_SHORT_TEXT    = IeciTdType.NULL_SHORT_TEXT;   
   public static final String NULL_LONG_TEXT     = IeciTdType.NULL_LONG_TEXT;   
   public static final short  NULL_SHORT_INTEGER = IeciTdType.NULL_SHORT_INTEGER;
   public static final int    NULL_LONG_INTEGER  = IeciTdType.NULL_LONG_INTEGER;
   public static final float  NULL_SHORT_DECIMAL = IeciTdType.NULL_SHORT_DECIMAL;
   public static final double NULL_LONG_DECIMAL  = IeciTdType.NULL_LONG_DECIMAL;
   public static final Date   NULL_DATE_TIME     = IeciTdType.NULL_DATE_TIME;
   
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
      else if (dataType == LONG_BIN)
         type = Types.LONGVARBINARY;
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
      /* 
	 * @SF-SEVILLA ADICION para preguntar por PostgreSQL
	 * 02-may-2006 / antmaria
	 */
      else if (engine == DbEngine.POSTGRESQL)
          type = getPostgreSQLNativeDbType(dataType);
      // FIN ADICION
      
      /*
       * Para preguntar por DB2
       */
      else if (engine == DbEngine.DB2)
     	 type = getDb2NativeDbType(dataType);
      /*
       * Fin
       */
      else
      {
         throw new IeciTdException(DbError.EC_INVALID_ENGINE,
                                   DbError.EM_INVALID_ENGINE);
      }

      return type;

   }
   // 1/03/2006 ADICION para PostgreSQL
   private static String getPostgreSQLNativeDbType(int dataType) throws Exception
   {
      
      String type;

      if (dataType == SHORT_TEXT)
         type = "VARCHAR";
      else if (dataType == LONG_TEXT)
         type = "TEXT";
      else if (dataType == SHORT_INTEGER)
         type = "INT2";
      else if (dataType == LONG_INTEGER)
         type = "INT4";
      else if (dataType == SHORT_DECIMAL)
         type = "FLOAT4";
      else if (dataType == LONG_DECIMAL)
         type = "FLOAT8";
      else if (dataType == DATE_TIME)
         type = "TIMESTAMP";
      else if (dataType == LONG_BIN)
         type = "BYTEA";
      else
      {
         throw new IeciTdException(DbError.EC_INVALID_DATA_TYPE,
                                   DbError.EM_INVALID_DATA_TYPE);
      }
      
      return type;

   }
   // FIN ADICION
   
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
      else if (dataType == LONG_BIN)
         type = "image";
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
      else if (dataType == LONG_BIN)
    	  type = "BLOB";
      	// Este codigo no se utiliza en la adaptacion de invesdoc.
		// {
		// /*
		// * @SF-SEVILLA Corregir conversion de tipos
		// * 02-may-2006 / antmaria
		// */
		// if (version.indexOf(DbEngine.ORACLE_VERSION_9) != -1)
		// type = "BLOB";
		// else
		// type = "LONG RAW";
		//       } 	  
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
	  else if (dataType == LONG_BIN)
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

   /**
    * Funcion que devuelve una cadena con el tipo de campo asociado a una base de datos.
    * Es similar a la ya existente, salvo que se le añade un parametro mas, idocVersion,
    * ya que parece que algunos tipos de campos en Invesdoc 9 han habian cambiado.
    * SEVILLA - 23/3/2005
    * @param dataType
    * @param engine
    * @param version Si es mayor o igual que 9, toma los tipos que se han definido en SEVILLA
    * @return Dicha cadena
    * @throws Exception
    */
   public static String getNativeDbType(int dataType, int engine, String version, int idocVersion ) throws Exception
   {
	   String type = null;
	   if (idocVersion >= 9){
		   if (engine == DbEngine.ORACLE)
			   type = getOracleNativeDbTypeIdoc9(dataType,version);
		   else if (engine == DbEngine.POSTGRESQL)
		       type = getPostgreSQLNativeDbType(dataType);
	   }
	   else
		   type = getNativeDbType(dataType,engine,version);
	   return type;
   }
   
   
   /**
    * @SF-SEVILLA
    * Devuelve una cadena con el tipo de campo correspondiente en oracle. Es similiar
    * a la ya existente, pero esta se usa pq algunos campos habian cambiado en invesdoc 9.
    * 02-may-2006 / antmaria
    * @param dataType
    * @param version
    * @return Dicha cadena
    * @throws Exception
    */
   private static String getOracleNativeDbTypeIdoc9(int dataType, String version) throws Exception
   {

      String type;

      if (dataType == SHORT_DECIMAL)
          type = "NUMBER(12,5)"; // Se estable precision en esta version
      else if (dataType == LONG_DECIMAL)
          type = "NUMBER(20,4)"; // Se estable precision en esta version
      else
    	  type = getOracleNativeDbType(dataType,version);
      
      return type;

   }
} // class
