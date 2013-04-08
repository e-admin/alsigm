package ieci.tdw.ispac.ispaclib.db;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.validations.EqualType;

import java.io.Serializable;
import java.sql.Types;
import java.util.HashMap;

public class InternalDataType implements Serializable {
	   
	   private static int count = 0;
 
	   
	   private int id;
	   private int jdbcType;
	   private static InternalDataType [] instances = new InternalDataType[14];
	   

	   //Declaración de los tipos de datos con los que vamos a trabajar.NO MODIFICAR EL ORDEN , porque afectaría , negativamente, a las
	   //definiciones existentes en spac_ct_entidades .
	   public static final InternalDataType SHORT_TEXT    = new InternalDataType(Types.VARCHAR);
	   public static final InternalDataType LONG_TEXT     = new InternalDataType(Types.LONGVARCHAR);   
	   public static final InternalDataType SHORT_INTEGER = new InternalDataType(Types.INTEGER);   
	   public static final InternalDataType LONG_INTEGER  = new InternalDataType(Types.INTEGER);   
	   public static final InternalDataType SHORT_DECIMAL = new InternalDataType(Types.REAL);
	   public static final InternalDataType LONG_DECIMAL  = new InternalDataType(Types.FLOAT);
	   public static final InternalDataType DATE	      = new InternalDataType(Types.DATE);
	   public static final InternalDataType LONG_BIN      = new InternalDataType(Types.LONGVARBINARY);
	   public static final InternalDataType NIF			  = new InternalDataType(Types.VARCHAR);
	   public static final InternalDataType CIF			  = new InternalDataType(Types.VARCHAR);
	   public static final InternalDataType NIE			  = new InternalDataType(Types.VARCHAR);
	   public static final InternalDataType EMAIL		  = new InternalDataType(Types.VARCHAR);
	   public static final InternalDataType CCC			  = new InternalDataType(Types.VARCHAR);
	   public static final InternalDataType TIMESTAMP     = new InternalDataType(Types.TIMESTAMP);
	   
	   
//	   public static final String NULL_SHORT_TEXT    = null;   
//	   public static final String NULL_LONG_TEXT     = null;   
//	   public static final short  NULL_SHORT_INTEGER = Short.MIN_VALUE;
//	   public static final long   NULL_LONG_INTEGER  = Long.MIN_VALUE;
//	   public static final float  NULL_SHORT_DECIMAL = Float.MIN_VALUE;
//	   public static final double NULL_LONG_DECIMAL  = Double.MIN_VALUE;
//	   public static final Date   NULL_DATE_TIME     = null;
//	   
//	   public static final String NULL_GUID = "IECI_TECDOC_NULL_GUID";
	   
	   //Mapa que apunta hacia el mapeo de tipos de la base de datos a la que nos encontramos conectados
	   private static HashMap mappingsJDBCTypesActualEngine = null;
	   private static HashMap mappingsJDBCTypesActualEngineJDBCTypes = null;
	   
	   //Mapeo de tipos internos a SQL 
	   static HashMap mappingsJDBCTypesPostgreSQL = null;
	   static HashMap mappingsJDBCTypesPostgreSQLJDBCTypes = null;
	   static HashMap mappingsJDBCTypesSQLServer = null;
	   static HashMap mappingsJDBCTypesSQLServerJDBCTypes = null;
	   static HashMap mappingsJDBCTypesOracle8 = null;
	   static HashMap mappingsJDBCTypesOracle8JDBCTypes = null;
	   static HashMap mappingsJDBCTypesOracle = null;
	   static HashMap mappingsJDBCTypesOracleJDBCTypes = null;
	   static HashMap mappingsJDBCTypesDB2 = null;
	   static HashMap mappingsJDBCTypesDB2JDBCTypes = null;
	   static{
		  mappingsJDBCTypesPostgreSQL = new HashMap();
		  mappingsJDBCTypesPostgreSQLJDBCTypes = new HashMap();
    	  registerInternalDataType(SHORT_TEXT, mappingsJDBCTypesPostgreSQL, mappingsJDBCTypesPostgreSQLJDBCTypes, "varchar", Types.VARCHAR);
    	  registerInternalDataType(LONG_TEXT, mappingsJDBCTypesPostgreSQL, mappingsJDBCTypesPostgreSQLJDBCTypes, "text", Types.LONGVARCHAR);
    	  registerInternalDataType(SHORT_INTEGER, mappingsJDBCTypesPostgreSQL, mappingsJDBCTypesPostgreSQLJDBCTypes, "numeric", Types.NUMERIC);
    	  registerInternalDataType(LONG_INTEGER, mappingsJDBCTypesPostgreSQL, mappingsJDBCTypesPostgreSQLJDBCTypes, "integer", Types.INTEGER);
    	  registerInternalDataType(SHORT_DECIMAL, mappingsJDBCTypesPostgreSQL, mappingsJDBCTypesPostgreSQLJDBCTypes, "numeric", Types.NUMERIC);
    	  registerInternalDataType(LONG_DECIMAL, mappingsJDBCTypesPostgreSQL, mappingsJDBCTypesPostgreSQLJDBCTypes, "double precision", Types.FLOAT);
    	  registerInternalDataType(DATE, mappingsJDBCTypesPostgreSQL, mappingsJDBCTypesPostgreSQLJDBCTypes, "timestamp", Types.TIMESTAMP);
    	  registerInternalDataType(LONG_BIN, mappingsJDBCTypesPostgreSQL, mappingsJDBCTypesPostgreSQLJDBCTypes, "bytea", Types.LONGVARBINARY);
    	  registerInternalDataType(NIF, mappingsJDBCTypesPostgreSQL, mappingsJDBCTypesPostgreSQLJDBCTypes, "varchar", Types.VARCHAR);
    	  registerInternalDataType(CIF, mappingsJDBCTypesPostgreSQL, mappingsJDBCTypesPostgreSQLJDBCTypes, "varchar", Types.VARCHAR);
    	  registerInternalDataType(NIE, mappingsJDBCTypesPostgreSQL, mappingsJDBCTypesPostgreSQLJDBCTypes, "varchar", Types.VARCHAR);
    	  registerInternalDataType(EMAIL, mappingsJDBCTypesPostgreSQL, mappingsJDBCTypesPostgreSQLJDBCTypes, "varchar", Types.VARCHAR);
    	  registerInternalDataType(CCC, mappingsJDBCTypesPostgreSQL, mappingsJDBCTypesPostgreSQLJDBCTypes, "varchar", Types.VARCHAR);
    	  registerInternalDataType(TIMESTAMP, mappingsJDBCTypesPostgreSQL, mappingsJDBCTypesPostgreSQLJDBCTypes, "timestamp", Types.TIMESTAMP);

    	  mappingsJDBCTypesSQLServer = new HashMap();
    	  mappingsJDBCTypesSQLServerJDBCTypes = new HashMap();
    	  registerInternalDataType(SHORT_TEXT, mappingsJDBCTypesSQLServer, mappingsJDBCTypesSQLServerJDBCTypes, "varchar", Types.VARCHAR);
    	  registerInternalDataType(LONG_TEXT, mappingsJDBCTypesSQLServer, mappingsJDBCTypesSQLServerJDBCTypes, "text", Types.LONGVARCHAR);
    	  registerInternalDataType(SHORT_INTEGER, mappingsJDBCTypesSQLServer, mappingsJDBCTypesSQLServerJDBCTypes, "numeric", Types.NUMERIC);
    	  registerInternalDataType(LONG_INTEGER, mappingsJDBCTypesSQLServer, mappingsJDBCTypesSQLServerJDBCTypes, "int", Types.INTEGER);
    	  registerInternalDataType(SHORT_DECIMAL, mappingsJDBCTypesSQLServer, mappingsJDBCTypesSQLServerJDBCTypes, "numeric", Types.NUMERIC);
    	  registerInternalDataType(LONG_DECIMAL, mappingsJDBCTypesSQLServer, mappingsJDBCTypesSQLServerJDBCTypes, "float", Types.FLOAT);
    	  registerInternalDataType(DATE, mappingsJDBCTypesSQLServer, mappingsJDBCTypesSQLServerJDBCTypes, "datetime", Types.TIMESTAMP);
    	  registerInternalDataType(LONG_BIN, mappingsJDBCTypesSQLServer, mappingsJDBCTypesSQLServerJDBCTypes, "image", Types.LONGVARBINARY);
    	  registerInternalDataType(NIF, mappingsJDBCTypesSQLServer, mappingsJDBCTypesSQLServerJDBCTypes, "varchar", Types.VARCHAR);
    	  registerInternalDataType(CIF, mappingsJDBCTypesSQLServer, mappingsJDBCTypesSQLServerJDBCTypes, "varchar", Types.VARCHAR);
    	  registerInternalDataType(NIE, mappingsJDBCTypesSQLServer, mappingsJDBCTypesSQLServerJDBCTypes, "varchar", Types.VARCHAR);
    	  registerInternalDataType(EMAIL, mappingsJDBCTypesSQLServer, mappingsJDBCTypesSQLServerJDBCTypes, "varchar", Types.VARCHAR);
    	  registerInternalDataType(CCC, mappingsJDBCTypesSQLServer, mappingsJDBCTypesSQLServerJDBCTypes, "varchar", Types.VARCHAR);
    	  registerInternalDataType(TIMESTAMP, mappingsJDBCTypesSQLServer, mappingsJDBCTypesSQLServerJDBCTypes, "datetime", Types.TIMESTAMP);
    	  
    	  mappingsJDBCTypesOracle8 = new HashMap();
    	  mappingsJDBCTypesOracle8JDBCTypes = new HashMap();
    	  registerInternalDataType(SHORT_TEXT, mappingsJDBCTypesOracle8, mappingsJDBCTypesOracle8JDBCTypes, "VARCHAR2", Types.VARCHAR);
    	  registerInternalDataType(LONG_TEXT, mappingsJDBCTypesOracle8, mappingsJDBCTypesOracle8JDBCTypes, "LONG", Types.LONGVARCHAR);
    	  registerInternalDataType(SHORT_INTEGER, mappingsJDBCTypesOracle8, mappingsJDBCTypesOracle8JDBCTypes, "NUMBER", Types.DECIMAL);
    	  registerInternalDataType(LONG_INTEGER, mappingsJDBCTypesOracle8, mappingsJDBCTypesOracle8JDBCTypes, "NUMBER", Types.DECIMAL);
    	  registerInternalDataType(SHORT_DECIMAL, mappingsJDBCTypesOracle8, mappingsJDBCTypesOracle8JDBCTypes, "NUMBER", Types.DECIMAL);
    	  registerInternalDataType(LONG_DECIMAL, mappingsJDBCTypesOracle8, mappingsJDBCTypesOracle8JDBCTypes, "NUMBER", Types.DECIMAL);
    	  registerInternalDataType(DATE, mappingsJDBCTypesOracle8, mappingsJDBCTypesOracle8JDBCTypes, "DATE", Types.TIMESTAMP);
    	  registerInternalDataType(LONG_BIN, mappingsJDBCTypesOracle8, mappingsJDBCTypesOracle8JDBCTypes, "LONG RAW", Types.LONGVARBINARY);
    	  registerInternalDataType(NIF, mappingsJDBCTypesOracle8, mappingsJDBCTypesOracle8JDBCTypes, "VARCHAR2", Types.VARCHAR);
    	  registerInternalDataType(CIF, mappingsJDBCTypesOracle8, mappingsJDBCTypesOracle8JDBCTypes, "VARCHAR2", Types.VARCHAR);
    	  registerInternalDataType(NIE, mappingsJDBCTypesOracle8, mappingsJDBCTypesOracle8JDBCTypes, "VARCHAR2", Types.VARCHAR);
    	  registerInternalDataType(EMAIL, mappingsJDBCTypesOracle8, mappingsJDBCTypesOracle8JDBCTypes, "VARCHAR2", Types.VARCHAR);
    	  registerInternalDataType(CCC, mappingsJDBCTypesOracle8, mappingsJDBCTypesOracle8JDBCTypes, "VARCHAR2", Types.VARCHAR);
    	  registerInternalDataType(TIMESTAMP, mappingsJDBCTypesOracle8, mappingsJDBCTypesOracle8JDBCTypes, "DATE", Types.TIMESTAMP);
    	  
    	  mappingsJDBCTypesOracle = new HashMap();
    	  mappingsJDBCTypesOracleJDBCTypes = new HashMap();
    	  registerInternalDataType(SHORT_TEXT, mappingsJDBCTypesOracle, mappingsJDBCTypesOracleJDBCTypes, "VARCHAR2", Types.VARCHAR);
    	  registerInternalDataType(LONG_TEXT, mappingsJDBCTypesOracle, mappingsJDBCTypesOracleJDBCTypes, "CLOB", Types.CLOB);
    	  registerInternalDataType(SHORT_INTEGER, mappingsJDBCTypesOracle, mappingsJDBCTypesOracleJDBCTypes, "NUMBER", Types.DECIMAL);
    	  registerInternalDataType(LONG_INTEGER, mappingsJDBCTypesOracle, mappingsJDBCTypesOracleJDBCTypes, "NUMBER", Types.DECIMAL);
    	  registerInternalDataType(SHORT_DECIMAL, mappingsJDBCTypesOracle, mappingsJDBCTypesOracleJDBCTypes, "NUMBER", Types.DECIMAL);
    	  registerInternalDataType(LONG_DECIMAL, mappingsJDBCTypesOracle, mappingsJDBCTypesOracleJDBCTypes, "NUMBER", Types.DECIMAL);
    	  registerInternalDataType(DATE, mappingsJDBCTypesOracle, mappingsJDBCTypesOracleJDBCTypes, "DATE", Types.TIMESTAMP);
    	  registerInternalDataType(LONG_BIN, mappingsJDBCTypesOracle, mappingsJDBCTypesOracleJDBCTypes, "LONG RAW", Types.LONGVARBINARY);
    	  registerInternalDataType(NIF, mappingsJDBCTypesOracle, mappingsJDBCTypesOracleJDBCTypes, "VARCHAR2", Types.VARCHAR);
    	  registerInternalDataType(CIF, mappingsJDBCTypesOracle, mappingsJDBCTypesOracleJDBCTypes, "VARCHAR2", Types.VARCHAR);
    	  registerInternalDataType(NIE, mappingsJDBCTypesOracle, mappingsJDBCTypesOracleJDBCTypes, "VARCHAR2", Types.VARCHAR);
    	  registerInternalDataType(EMAIL, mappingsJDBCTypesOracle, mappingsJDBCTypesOracleJDBCTypes, "VARCHAR2", Types.VARCHAR);
    	  registerInternalDataType(CCC, mappingsJDBCTypesOracle, mappingsJDBCTypesOracleJDBCTypes, "VARCHAR2", Types.VARCHAR);
    	  registerInternalDataType(TIMESTAMP, mappingsJDBCTypesOracle, mappingsJDBCTypesOracleJDBCTypes, "DATE", Types.TIMESTAMP);

		  mappingsJDBCTypesDB2 = new HashMap();
		  mappingsJDBCTypesDB2JDBCTypes = new HashMap();
    	  registerInternalDataType(SHORT_TEXT, mappingsJDBCTypesDB2, mappingsJDBCTypesDB2JDBCTypes, "VARCHAR", Types.VARCHAR);
    	  registerInternalDataType(LONG_TEXT, mappingsJDBCTypesDB2, mappingsJDBCTypesDB2JDBCTypes, "CLOB", Types.CLOB);
    	  registerInternalDataType(SHORT_INTEGER, mappingsJDBCTypesDB2, mappingsJDBCTypesDB2JDBCTypes, "NUM", Types.NUMERIC);
    	  registerInternalDataType(LONG_INTEGER, mappingsJDBCTypesDB2, mappingsJDBCTypesDB2JDBCTypes, "INTEGER", Types.NUMERIC);
    	  registerInternalDataType(SHORT_DECIMAL, mappingsJDBCTypesDB2, mappingsJDBCTypesDB2JDBCTypes, "NUM", Types.NUMERIC);
    	  registerInternalDataType(LONG_DECIMAL, mappingsJDBCTypesDB2, mappingsJDBCTypesDB2JDBCTypes, "DOUBLE", Types.NUMERIC);
    	  registerInternalDataType(DATE, mappingsJDBCTypesDB2, mappingsJDBCTypesDB2JDBCTypes, "TIMESTAMP", Types.TIMESTAMP);
    	  registerInternalDataType(LONG_BIN, mappingsJDBCTypesDB2, mappingsJDBCTypesDB2JDBCTypes, "BLOB", Types.LONGVARBINARY);
    	  registerInternalDataType(NIF, mappingsJDBCTypesDB2, mappingsJDBCTypesDB2JDBCTypes, "VARCHAR", Types.VARCHAR);
    	  registerInternalDataType(CIF, mappingsJDBCTypesDB2, mappingsJDBCTypesDB2JDBCTypes, "VARCHAR", Types.VARCHAR);
    	  registerInternalDataType(NIE, mappingsJDBCTypesDB2, mappingsJDBCTypesDB2JDBCTypes, "VARCHAR", Types.VARCHAR);
    	  registerInternalDataType(EMAIL, mappingsJDBCTypesDB2, mappingsJDBCTypesDB2JDBCTypes, "VARCHAR", Types.VARCHAR);
    	  registerInternalDataType(CCC, mappingsJDBCTypesDB2, mappingsJDBCTypesDB2JDBCTypes, "VARCHAR", Types.VARCHAR);
    	  registerInternalDataType(TIMESTAMP, mappingsJDBCTypesDB2, mappingsJDBCTypesDB2JDBCTypes, "TIMESTAMP", Types.TIMESTAMP);
	   }

	   private InternalDataType(int jdbcType){
		   id = count;
		   this.jdbcType = jdbcType;
		   instances[count++] = this;
	   }
	   public static InternalDataType getInstance(int internalId){
		   return instances[internalId];
	   }
	   public int getId(){
		   return id;
	   }
	   
	   private static void registerInternalDataType(InternalDataType internalDataType, HashMap mapDataTypeSQLString, HashMap mapDataTypeSQLJdbc, String dataTypeSQLString, int dataTypeSQLJdbc)
	   {
		   Integer dataTypeId = new Integer(internalDataType.getId());
		   mapDataTypeSQLString.put(dataTypeId, dataTypeSQLString);
		   mapDataTypeSQLJdbc.put(dataTypeId, new Integer(dataTypeSQLJdbc));
	   }

	   public boolean isTypeOf(int idDataType){
		
		   if(id==idDataType){
			   return true;
		   }
//		   if(id==SHORT_TEXT.id && (CIF.id==idDataType || NIE.id==idDataType || NIF.id==idDataType|| CCC.id==idDataType || EMAIL.id==idDataType)){
//			   return true;
//		   } 
		   return false;
	   }
	   
	   public static String getNativeDbTypeSQL(DbCnt cnt, InternalDataType internalDataType) throws ISPACException
	   {
	      if (mappingsJDBCTypesActualEngine == null){
		      if (cnt.isOracleDbEngine()){
		    	  mappingsJDBCTypesActualEngine = mappingsJDBCTypesOracle;
		      } else if (cnt.isPostgreSQLDbEngine()){
		    	  mappingsJDBCTypesActualEngine = mappingsJDBCTypesPostgreSQL;
		      } else if (cnt.isSqlServerDbEngine()){
		    	  mappingsJDBCTypesActualEngine = mappingsJDBCTypesSQLServer;
		      } else if (cnt.isDB2DbEngine()){
		    	  mappingsJDBCTypesActualEngine = mappingsJDBCTypesDB2;
		      } else{
		    	  throw new ISPACException("Base de datos no soportada");
		      }
	      }
	      return (String)mappingsJDBCTypesActualEngine.get(new Integer(internalDataType.getId()));
	   }
	   
	   public static int getJdbcNativeDbTypeSQL(DbCnt cnt, InternalDataType internalDataType) throws ISPACException
	   {
	      if (mappingsJDBCTypesActualEngineJDBCTypes == null){
		      if (cnt.isOracleDbEngine()){
		    	  mappingsJDBCTypesActualEngineJDBCTypes = mappingsJDBCTypesOracleJDBCTypes;
		      } else if (cnt.isPostgreSQLDbEngine()){
		    	  mappingsJDBCTypesActualEngineJDBCTypes = mappingsJDBCTypesPostgreSQLJDBCTypes;
		      } else if (cnt.isSqlServerDbEngine()){
		    	  mappingsJDBCTypesActualEngineJDBCTypes = mappingsJDBCTypesSQLServerJDBCTypes;
		      } else if (cnt.isDB2DbEngine()){
		    	  mappingsJDBCTypesActualEngineJDBCTypes = mappingsJDBCTypesDB2JDBCTypes;
		      } else{
		    	  throw new ISPACException("Base de datos no soportada");
		      }
	      }
	      return ((Integer) mappingsJDBCTypesActualEngineJDBCTypes.get(new Integer(internalDataType.getId()))).intValue();
	   }

	   public static String getNativeDbTypeSQL(DbCnt cnt, int dataType) throws ISPACException
	   {
	      if (mappingsJDBCTypesActualEngine == null){
		      if (cnt.isOracleDbEngine()){
		    	  mappingsJDBCTypesActualEngine = mappingsJDBCTypesOracle;
		      } else if (cnt.isPostgreSQLDbEngine()){
		    	  mappingsJDBCTypesActualEngine = mappingsJDBCTypesPostgreSQL;
		      } else if (cnt.isSqlServerDbEngine()){
		    	  mappingsJDBCTypesActualEngine = mappingsJDBCTypesSQLServer;
		      } else if (cnt.isDB2DbEngine()){
		    	  mappingsJDBCTypesActualEngine = mappingsJDBCTypesDB2;
		      } else{
		    	  throw new ISPACException("Base de datos no soportada");
		      }
	      }
	      
	    
	    return (String)mappingsJDBCTypesActualEngine.get(EqualType.getDataTypeEquivalente(dataType));
	     
	   }
	   
	   public boolean equals(Object obj) {
		   return (((InternalDataType)obj).getId() == this.id);
	   }

	public int getJdbcType() {
		return jdbcType;
	}
	  
//	   public static boolean isNullShortText(String value)
//	   {
//	      return (value == NULL_SHORT_TEXT);
//	   }
//	   
//	   public static boolean isNullLongText(String value)
//	   {
//	      return (value == NULL_LONG_TEXT);
//	   }
//	   
//	   public static boolean isNullShortInteger(short value)
//	   {
//	      return (value == NULL_SHORT_INTEGER);
//	   }
//	   
//	   public static boolean isNullLongInteger(int value)
//	   {
//	      return (value == NULL_LONG_INTEGER);
//	   }
//	   
//	   public static boolean isNullShortDecimal(float value)
//	   {
//	      return (value == NULL_SHORT_DECIMAL);
//	   }
//	   
//	   public static boolean isNullLongDecimal(double value)
//	   {
//	      return (value == NULL_LONG_DECIMAL);
//	   }
//	   
//	   public static boolean isNullDateTime(Date value)
//	   {
//	      return (value == NULL_DATE_TIME);
//	   }

}
