package ieci.core.db;

import ieci.core.exception.IeciTdException;
import ieci.core.types.IeciTdType;

import java.sql.Types;
import java.util.Date;

public final class DbDataType {

	public static final int SHORT_TEXT = IeciTdType.SHORT_TEXT;
	public static final int LONG_TEXT = IeciTdType.LONG_TEXT;
	public static final int SHORT_INTEGER = IeciTdType.SHORT_INTEGER;
	public static final int LONG_INTEGER = IeciTdType.LONG_INTEGER;
	public static final int LONG = IeciTdType.LONG;
	public static final int SHORT_DECIMAL = IeciTdType.SHORT_DECIMAL;
	public static final int LONG_DECIMAL = IeciTdType.LONG_DECIMAL;
	public static final int DATE_TIME = IeciTdType.DATE_TIME;
	public static final int OBJECT = IeciTdType.OBJECT;
	public static final int MUTABLE_INT = 213;

	public static final String NULL_SHORT_TEXT = IeciTdType.NULL_SHORT_TEXT;
	public static final String NULL_LONG_TEXT = IeciTdType.NULL_LONG_TEXT;
	public static final short NULL_SHORT_INTEGER = IeciTdType.NULL_SHORT_INTEGER;
	public static final int NULL_LONG_INTEGER = IeciTdType.NULL_LONG_INTEGER;
	public static final long NULL_LONG = IeciTdType.NULL_LONG;
	public static final float NULL_SHORT_DECIMAL = IeciTdType.NULL_SHORT_DECIMAL;
	public static final double NULL_LONG_DECIMAL = IeciTdType.NULL_LONG_DECIMAL;
	public static final Date NULL_DATE_TIME = IeciTdType.NULL_DATE_TIME;
	public static final Object NULL_OBJECT = IeciTdType.NULL_OBJECT;

	private DbDataType() {
	}

	public static int getDbDataType(int jdbcType) throws Exception {

		int dataType;

		if (jdbcType == Types.DECIMAL)
			dataType = SHORT_INTEGER;

		else if (jdbcType == Types.CLOB)
			dataType = LONG_TEXT;

		else if (jdbcType == Types.VARCHAR)
			dataType = SHORT_TEXT;

		else if (jdbcType == Types.LONGVARCHAR)
			dataType = LONG_TEXT;

		else if (jdbcType == Types.SMALLINT)
			dataType = SHORT_INTEGER;

		else if (jdbcType == Types.INTEGER)
			dataType = LONG_INTEGER;

		else if (jdbcType == Types.REAL)
			dataType = SHORT_DECIMAL;

		else if (jdbcType == Types.FLOAT)
			dataType = LONG_DECIMAL;

		else if (jdbcType == Types.TIMESTAMP)
			dataType = DATE_TIME;

		else if (jdbcType == Types.DATE)
			dataType = DATE_TIME;

		else if (jdbcType == Types.JAVA_OBJECT)
			dataType = OBJECT;

		else {
			throw new IeciTdException(DbError.EC_INVALID_DATA_TYPE,
					DbError.EM_INVALID_DATA_TYPE);
		}

		return dataType;

	}

	static int getJdbcType(int dataType) throws Exception {

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
		else if (dataType == OBJECT)
			type = Types.JAVA_OBJECT;
		else {
			throw new IeciTdException(DbError.EC_INVALID_DATA_TYPE,
					DbError.EM_INVALID_DATA_TYPE);
		}

		return type;

	}

	static String getNativeDbType(int dataType, int engine) throws Exception {

		String type;

		if (engine == DbEngine.SQLSERVER){
			type = getSqlServerNativeDbType(dataType);
		}else if (engine == DbEngine.ORACLE){
			type = getOracleNativeDbType(dataType);
		}
		else if(engine == DbEngine.POSTGRES){
			type = getPostgreNativeDbType(dataType);
		}
		else if(engine == DbEngine.DB2){
			type = getDB2NativeDbType(dataType);
		}

		else {
			throw new IeciTdException(DbError.EC_INVALID_ENGINE,
					DbError.EM_INVALID_ENGINE);
		}

		return type;

	}

	private static String getSqlServerNativeDbType(int dataType)
			throws Exception {

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
		else {
			throw new IeciTdException(DbError.EC_INVALID_DATA_TYPE,
					DbError.EM_INVALID_DATA_TYPE);
		}

		return type;

	}

	private static String getOracleNativeDbType(int dataType) throws Exception {

		String type;

		if (dataType == SHORT_TEXT)
			type = "VARCHAR2";
		else if (dataType == LONG_TEXT)
			type = "CLOB";
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
		else {
			throw new IeciTdException(DbError.EC_INVALID_DATA_TYPE,
					DbError.EM_INVALID_DATA_TYPE);
		}

		return type;

	}

	private static String getPostgreNativeDbType(int dataType) throws Exception {

		String type;

		if (dataType == SHORT_TEXT)
			type = "VARCHAR";
		else if (dataType == LONG_TEXT)
			type = "CLOB";
		else if (dataType == SHORT_INTEGER)
			type = "SMALLINT";
		else if (dataType == LONG_INTEGER)
			type = "INTEGER";
		else if (dataType == SHORT_DECIMAL)
			type = "FLOAT";
		else if (dataType == LONG_DECIMAL)
			type = "FLOAT";
		else if (dataType == DATE_TIME)
			type = "TIMESTAMP";
		else {
			throw new IeciTdException(DbError.EC_INVALID_DATA_TYPE,
					DbError.EM_INVALID_DATA_TYPE);
		}

		return type;

	}

	private static String getDB2NativeDbType(int dataType) throws Exception {

		String type;

		if (dataType == SHORT_TEXT)
			type = "VARCHAR";
		else if (dataType == LONG_TEXT)
			type = "TEXT";
		else if (dataType == SHORT_INTEGER)
			type = "SMALLINT";
		else if (dataType == LONG_INTEGER)
			type = "INTEGER";
		else if (dataType == SHORT_DECIMAL)
			type = "FLOAT";
		else if (dataType == LONG_DECIMAL)
			type = "FLOAT";
		else if (dataType == DATE_TIME)
			type = "TIMESTAMP";
		else {
			throw new IeciTdException(DbError.EC_INVALID_DATA_TYPE,
					DbError.EM_INVALID_DATA_TYPE);
		}

		return type;

	}

	public static boolean isNullShortText(String value) {
		return (value == NULL_SHORT_TEXT);
	}

	public static boolean isNullLongText(String value) {
		return (value == NULL_LONG_TEXT);
	}

	public static boolean isNullShortInteger(short value) {
		return (value == NULL_SHORT_INTEGER);
	}

	public static boolean isNullLongInteger(int value) {
		return (value == NULL_LONG_INTEGER);
	}

	public static boolean isNullLong(long value) {
		return (value == NULL_LONG);
	}

	public static boolean isNullShortDecimal(float value) {
		return (value == NULL_SHORT_DECIMAL);
	}

	public static boolean isNullLongDecimal(double value) {
		return (value == NULL_LONG_DECIMAL);
	}

	public static boolean isNullDateTime(Date value) {
		return (value == NULL_DATE_TIME);
	}

	public static boolean isNullObject(Object value) {
		return (value == NULL_OBJECT);
	}

} // class
