package ieci.core.db;

import ieci.core.exception.IeciTdException;

import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import util.StringOwnTokenizer;
import xml.config.ConfiguracionFondos;
import xml.config.ConfiguracionSistemaArchivoFactory;

import common.Constants;
import common.db.DBUtils;
import common.db.TableDef;
import common.startup.ProfileLogLevel;
import common.util.ArrayUtils;
import common.util.CustomDateFormat;
import common.util.DateUtils;
import common.util.StringUtils;
import common.vos.ConsultaConnectBy;

public final class DbUtil {

	protected static final Logger PROFILE_LOGGER = Logger.getLogger("PROFILE");

	private static final DbColumnDef[] EMPTY_COLDEF_ARRAY = new DbColumnDef[0];

	private final static String SQL_SERVER_CONCAT = "+";
	private final static String ORACLE_CONCAT = "||";
	private final static String POSTGRES_CONCAT = "||";
	// DB2 Caracter de Concatenación.
	private final static String DB2_CONCAT = "||";

	private final static String SQL_SERVER_LEFT = "LEFT";
	private final static String SQL_SERVER_RIGHT = "RIGHT";
	private final static String SQL_SERVER_REPLICATE = "REPLICATE";
	private final static String ORACLE_LPAD = "LPAD";
	private final static String POSTGRES_LPAD = "LPAD";
	// DB2 Nombre del la Función LPAD
	private final static String DB2_LPAD = "LPAD"; // Función Definida por el
													// usuario.

	private final static String SQL_SERVER_CONVERT = "CONVERT";
	private final static String ORACLE_TO_NUMBER = "TO_NUMBER";
	private final static String POSTGRES_TO_NUMBER = "TO_NUMBER";
	private final static String DB2_TO_NUMBER = "GETNUMERICPOSITIVE";

	private final static String SQL_SERVER_GETNUMERICPOSITIVE = "dbo.GETNUMERICPOSITIVE";
	private final static String ORACLE_GETNUMERICPOSITIVE = "GETNUMERICPOSITIVE";
	private final static String POSTGRES_GETNUMERICPOSITIVE = "GETNUMERICPOSITIVE";
	private final static String DB2_GETNUMERICPOSITIVE = "GETNUMERICPOSITIVE";

	private final static String SQL_SERVER_LENGTH = "LEN";
	private final static String ORACLE_LENGTH = "LENGTH";
	private final static String POSTGRES_LENGTH = "LENGTH";
	// DB2 Función de Longitud
	private final static String DB2_LENGTH = "LENGTH";

	private final static String SQL_SERVER_LTRIM = "LTRIM";
	private final static String SQL_SERVER_RTRIM = "RTRIM";
	private final static String ORACLE_TRIM = "TRIM";
	private final static String POSTGRES_TRIM = "TRIM";
	// DB2 Nombre de la función para Eliminar los Espacios
	private final static String DB2_LTRIM = "LTRIM";
	private final static String DB2_RTRIM = "RTRIM";

	private final static String POSTGRES_TO_CHAR = "to_char";
	private final static String ORACLE_TO_CHAR = "TO_CHAR";
	private final static String SQL_SERVER_CAST = "CAST";
	private final static String DB2_VARCHAR_FORMAT = "TS_FMT";
	private final static String DB2_CHAR = "CHAR";

	private final static String POSTGRES_SUBSTR = "SUBSTR";
	private final static String ORACLE_SUBSTR = "SUBSTR";
	private final static String SQL_SERVER_SUBSTRING = "SUBSTRING";
	// DB2 Nombre de la función para subcadenas
	private final static String DB2_SUBSTR = "SUBSTR";

	private final static String POSTGRES_REPLACE = "REPLACE";
	private final static String ORACLE_REPLACE = "REPLACE";
	private final static String SQL_SERVER_REPLACE = "REPLACE";
	private final static String DB2_REPLACE = "REPLACE";

	private final static String POSTGRES_STR_POS = "STRPOS";
	private final static String ORACLE_INSTR = "INSTR";
	private final static String SQL_SERVER_CHARINDEX = "CHARINDEX";
	// DB2 Nombre de la función para obtener una posición dentro de una cadena
	private final static String DB2_POS_STR = "LOCATE";

	private final static String POSTGRES_ADD_MONTH = "ADD_MONTHS";
	private final static String ORACLE_ADD_MONTH = "ADD_MONTHS";
	private final static String SQL_SERVER_ADD_YEAR = "DATEADD(year,";

	// DB2 Nombre de la Constante de Años
	private final static String DB2_YEAR = "YEAR";

	private final static String POSTGRES_GREATEST = "GREATEST";
	private final static String ORACLE_GREATEST = "GREATEST";
	private final static String SQL_SERVER_GREATEST = "dbo.GREATEST";
	// DB2 Nombre de la Función GREATEST
	private final static String DB2_GREATEST = "GREATEST";

	public final static String POSTGRES_DEFAULT_DICTIONARY = "default_spanish";

	private final static String SQL_SERVER_TEXTS_JOIN = " AND ";
	private final static String ORACLE_TEXTS_JOIN = " AND ";
	private final static String ORACLE_TEXTS_ESCAPE_PREFIX = "{";
	private final static String ORACLE_TEXTS_ESCAPE_SUFFIX = "}";
	private final static String POSTGRES_TEXTS_JOIN = " & ";
	// no se utiliza la búq
	private final static String DB2_TEXTS_JOIN = "%";

	public final static String NULL_VALUE = "'nulo'";

	public final static String NOT_IN = " NOT IN ";

	public final static String MINUS = " MINUS ";
	public final static String EXCEPT = " EXCEPT ";

	public final static String DB2_DB_DATE_FORMAT = "YYYY-MM-DD HH24:MI:SS";

	private final static String POSTGRE_CONNECTBY = " connectby ";

	// Formatos
	public static final String TO_CHAR_PATTERN_AAAAMMDD = "yyyy-mm-dd";
	public static final String TO_CHAR_PATTERN_DDMM = "dd/mm/";
	public static final String TO_CHAR_PATTERN_MMDD = "mm/dd/";
	public static final String TO_CHAR_PATTERN_YYYY = "yyyy";
	// public static final String TO_CHAR_PATTERN_9999 = "9999";

	// Marcas del Hueco
	private final static String POSTGRES_MARCA_HUECO = "DEVOLVERMARCA";
	private final static String ORACLE_MARCA_HUECO = "DEVOLVERMARCA";
	private final static String SQL_SERVER_MARCA_HUECO = "dbo.DEVOLVERMARCA";
	private final static String DB2_MARCA_HUECO = "DEVOLVERMARCA";

	// Calcular codigo de referencia final del padre
	private final static String POSTGRES_CALCULARFINALCODREFPADRE = "CALCULARFINALCODREFPADRE";
	private final static String ORACLE_CALCULARFINALCODREFPADRE = "CALCULARFINALCODREFPADRE";
	private final static String SQL_SERVER_CALCULARFINALCODREFPADRE = "dbo.CALCULARFINALCODREFPADRE";
	private final static String DB2_CALCULARFINALCODREFPADRE = "CALCFINCODREFPADRE";

	private final static String POSTGRES_ISNUMERIC = "ISNUMERIC";
	private final static String ORACLE_ISNUMERIC = "ISNUMERIC";
	private final static String SQL_SERVER_ISNUMERIC = "ISNUMERIC";
	private final static String DB2_ISNUMERIC = "ISNUMERIC";

	private final static String MASCARA_CONVERT_INT_TO_VARCHAR_POSTGRE = "'99999999999999999999'";

	private DbUtil() {
	}

	/**
	 * Comprueba si la base de datos es PostgreSQL
	 * 
	 * @param conn
	 * @return
	 * @throws IeciTdException
	 */
	public static boolean isPostgreSQL(DbConnection conn)
			throws IeciTdException {
		if (conn.getEngine() == DbEngine.POSTGRES)
			return true;
		return false;
	}

	/**
	 * Comprueba si la base de datos es SQL Server
	 * 
	 * @param conn
	 * @return
	 * @throws IeciTdException
	 */
	public static boolean isSqlServer(DbConnection conn) throws IeciTdException {
		if (conn.getEngine() == DbEngine.SQLSERVER)
			return true;
		return false;
	}

	/**
	 * Comprueba si la base de datos es ORACLE
	 * 
	 * @param conn
	 * @return
	 * @throws IeciTdException
	 */
	public static boolean isOracle(DbConnection conn) throws IeciTdException {
		if (conn.getEngine() == DbEngine.ORACLE)
			return true;
		return false;
	}

	/**
	 * Comprueba si la base de datos es IBM DB2
	 * 
	 * @param conn
	 * @return 26/06/2008
	 * @throws IeciTdException
	 */
	public static boolean isDB2(DbConnection conn) throws IeciTdException {
		// DB2 Comprobación de base de datos tipo DB 2
		if (conn.getEngine() == DbEngine.DB2)
			return true;
		return false;
	}

	/**
	 * Ejecuta la sentencia SQL
	 * 
	 * @param conn
	 *            Conexión
	 * @param stmtText
	 *            Sentnecia SQL
	 * @throws Exception
	 */
	public static void executeStatement(DbConnection conn, String stmtText)
			throws Exception {
		Statement stmt = null;
		try {
			stmt = conn.getJdbcConnection().createStatement();

			if (PROFILE_LOGGER.isEnabledFor(ProfileLogLevel.BEGIN_QUERY)) {
				StringBuffer logMessage = new StringBuffer()
						.append(System.currentTimeMillis()).append(" [")
						.append(stmtText).append("]");
				PROFILE_LOGGER.log(ProfileLogLevel.BEGIN_QUERY, logMessage);
			}

			stmt.execute(stmtText);

			if (PROFILE_LOGGER.isEnabledFor(ProfileLogLevel.END_QUERY))
				PROFILE_LOGGER.log(ProfileLogLevel.END_QUERY,
						String.valueOf(System.currentTimeMillis()));

			stmt.close();
		} catch (Exception e) {
			try {
				if (stmt != null)
					stmt.close();
				throw e;
			} catch (Exception e1) {
				throw e;
			}
		}

	}

	/**
	 * Realiza el Count de la Columna Especificada
	 * 
	 * @param colDef
	 *            Columna
	 * @return String de la consulta del Count.
	 */
	public static String getCountColumn(DbColumnDef colDef) {
		StringBuffer sql = new StringBuffer(DBUtils.COUNT).append("(")
				.append(colDef.getQualifiedName()).append(") ");

		return sql.toString();
	}

	/**
	 * Obtiene la lista de columnas de la tabla
	 * 
	 * @param colDefs
	 *            Columnas
	 * @return
	 */
	public static String getNameColumnNames(DbColumnDef[] colDefs) {
		if (colDefs == null)
			return null;
		StringBuffer cols = new StringBuffer();
		for (int i = 0; i < colDefs.length; i++) {
			cols.append(colDefs[i].getName());
			if (i + 1 < colDefs.length)
				cols.append(",");
		}
		return cols.toString();
	}

	/**
	 * Obtiene la lista de columnas de la tabla
	 * 
	 * @param colDefs
	 *            Columnas
	 * @return
	 */
	public static String getColumnNames(DbColumnDef[] colDefs) {

		StringBuffer tbdr;
		int count, i;

		tbdr = new StringBuffer();

		count = colDefs.length;

		tbdr.append(colDefs[0].getQualifiedName());

		for (i = 1; i < count; i++)
			tbdr.append(",").append(colDefs[i].getQualifiedName());

		return tbdr.toString();

	}

	/**
	 * Obtiene la lista de columnas de la tabla
	 * 
	 * @param colDefs
	 *            Columnas
	 * @return
	 */
	public static String getColumnNamesOnly(DbColumnDef[] colDefs) {

		StringBuffer tbdr;
		int count, i;

		tbdr = new StringBuffer();

		count = colDefs.length;

		tbdr.append(colDefs[0].getAliasOrName());

		for (i = 1; i < count; i++)
			tbdr.append(",").append(colDefs[i].getAliasOrName());

		return tbdr.toString();

	}

	/**
	 * Obtiene la lista de columnas de la tabla
	 * 
	 * @param colDefs
	 *            Columnas
	 * @return
	 */
	public static String getColumnNamesWithAlias(DbColumnDef[] colDefs) {

		StringBuffer tbdr;
		int count, i;

		tbdr = new StringBuffer();

		count = colDefs.length;

		tbdr.append(colDefs[0].getQualifiedName());
		if (!StringUtils.isEmpty(colDefs[0].bindPropertyVO)) {
			tbdr.append(" as ").append(colDefs[0].bindPropertyVO);
		}

		for (i = 1; i < count; i++) {
			tbdr.append(",").append(colDefs[i].getQualifiedName());

			if (!StringUtils.isEmpty(colDefs[i].bindPropertyVO)) {
				tbdr.append(" as ").append(colDefs[i].bindPropertyVO);
			}

		}

		return tbdr.toString();

	}

	public static String getTableNames(DbTableDef[] tableDefs) {
		StringBuffer tablesNamesBuff = new StringBuffer(
				tableDefs[0].getAliasedName());
		for (int i = 1; i < tableDefs.length; i++) {
			tablesNamesBuff.append(",").append(tableDefs[i].getAliasedName());
		}
		return tablesNamesBuff.toString();
	}

	public static String getColumnNames(Collection colDefs) {
		return getColumnNames((DbColumnDef[]) colDefs
				.toArray(EMPTY_COLDEF_ARRAY));
	}

	public static String getColumnNames(DbColumnDef colDef1) {
		return colDef1.getName();
	}

	public static String getColumnNames(DbColumnDef colDef1, DbColumnDef colDef2) {

		StringBuffer tbdr;

		tbdr = new StringBuffer();

		tbdr.append(colDef1.getName());
		tbdr.append(',').append(colDef2.getName());

		return tbdr.toString();

	}

	public static String getColumnNames(DbColumnDef colDef1,
			DbColumnDef colDef2, DbColumnDef colDef3) {

		StringBuffer tbdr;

		tbdr = new StringBuffer();

		tbdr.append(colDef1.getName());
		tbdr.append(',').append(colDef2.getName());
		tbdr.append(',').append(colDef3.getName());

		return tbdr.toString();

	}

	public static String getColumnNames(DbColumnDef colDef1,
			DbColumnDef colDef2, DbColumnDef colDef3, DbColumnDef colDef4) {

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
			DbColumnDef colDef5) {

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
			DbColumnDef colDef5, DbColumnDef colDef6) {

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
			DbColumnDef colDef5, DbColumnDef colDef6, DbColumnDef colDef7) {

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
			DbColumnDef colDef8) {

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
			DbColumnDef colDef8, DbColumnDef colDef9) {

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
			DbColumnDef colDef8, DbColumnDef colDef9, DbColumnDef colDef10) {

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
			DbColumnDef colDef11) {

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
			DbColumnDef colDef11, DbColumnDef colDef12) {

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
			DbColumnDef colDef11, DbColumnDef colDef12, DbColumnDef colDef13) {

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

	/**
	 * Obtiene el carácter de concatenación de la base de datos
	 * 
	 * @param conn
	 *            Conexión
	 * @return Carácter de concatenación
	 * @throws IeciTdException
	 */
	public static String getNativeConcatSyntax(DbConnection conn)
			throws IeciTdException {
		String str = null;
		if (isSqlServer(conn))
			str = SQL_SERVER_CONCAT;
		else if (isOracle(conn))
			str = ORACLE_CONCAT;
		else if (isPostgreSQL(conn))
			str = POSTGRES_CONCAT;
		else if (isDB2(conn))
			str = DB2_CONCAT;
		else {
			throw new IeciTdException(DbError.EC_INVALID_ENGINE,
					DbError.EM_INVALID_ENGINE);
		}
		return str;
	}

	/**
	 * Sentencia para eliminar los espacios del inicio y del final del valor de
	 * la columna.
	 * 
	 * @param conn
	 *            Conexión
	 * @param nameColum
	 *            Nombre de la Columna
	 * @return
	 * @throws IeciTdException
	 */
	public static String getNativeTrimSyntax(DbConnection conn, String nameColum)
			throws IeciTdException {
		StringBuffer str = null;
		if (isSqlServer(conn))
			str = new StringBuffer(SQL_SERVER_LTRIM).append("(")
					.append(SQL_SERVER_RTRIM).append("(").append(nameColum)
					.append("))");
		else if (isOracle(conn))
			str = new StringBuffer().append(ORACLE_TRIM).append("(")
					.append(nameColum).append(")");
		else if (isPostgreSQL(conn))
			str = new StringBuffer().append(POSTGRES_TRIM).append("(")
					.append(nameColum).append(")");
		else if (isDB2(conn))
			str = new StringBuffer(DB2_LTRIM).append("(").append(DB2_RTRIM)
					.append("(").append(nameColum).append("))");
		else
			throw new IeciTdException(DbError.EC_INVALID_ENGINE,
					DbError.EM_INVALID_ENGINE);
		return str.toString();
	}

	public static String getNativeCalcularFinalCodReferenciaPadreSyntax(
			DbConnection conn, String codReferencia, String codRefFondo,
			String codigo, String finalCodRefPadreActual)
			throws IeciTdException {
		ConfiguracionFondos config = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionFondos();

		String delimitador = config.getDelimitadorCodigoReferencia();

		StringBuffer str = new StringBuffer("");
		if (isSqlServer(conn))
			str.append(SQL_SERVER_CALCULARFINALCODREFPADRE);
		else if (isOracle(conn))
			str.append(ORACLE_CALCULARFINALCODREFPADRE);
		else if (isPostgreSQL(conn))
			str.append(POSTGRES_CALCULARFINALCODREFPADRE);
		else if (isDB2(conn))
			str.append(DB2_CALCULARFINALCODREFPADRE);
		else
			throw new IeciTdException(DbError.EC_INVALID_ENGINE,
					DbError.EM_INVALID_ENGINE);

		str.append("(").append(codReferencia).append(",").append(codRefFondo)
				.append(",").append(codigo).append(",")
				.append(finalCodRefPadreActual).append(",'")
				.append(delimitador).append("')");
		return str.toString();
	}

	public static String getNativeReplaceSyntax(DbConnection conn,
			DbColumnDef field, String patronAReemplazar, String valorReemplazo)
			throws IeciTdException {
		StringBuffer str = new StringBuffer("");
		str.append(field.getAliasOrName()).append(Constants.EQUAL);
		if (isSqlServer(conn))
			str.append(SQL_SERVER_REPLACE);
		else if (isOracle(conn))
			str.append(ORACLE_REPLACE);
		else if (isPostgreSQL(conn))
			str.append(POSTGRES_REPLACE);
		else if (isDB2(conn))
			str.append(DB2_REPLACE);
		else
			throw new IeciTdException(DbError.EC_INVALID_ENGINE,
					DbError.EM_INVALID_ENGINE);

		str.append(Constants.ABRIR_PARENTESIS);
		str.append(field.getAliasOrName()).append(Constants.COMMA);
		if (DbDataType.SHORT_TEXT == field.getDataType()
				|| DbDataType.LONG_TEXT == field.getDataType()
				|| DbDataType.DATE_TIME == field.getDataType()) {
			str.append("'").append(patronAReemplazar).append("'");
			str.append(Constants.COMMA);
			str.append("'").append(valorReemplazo).append("'");
		} else {
			str.append(patronAReemplazar);
			str.append(Constants.COMMA);
			str.append(valorReemplazo);
		}
		str.append(Constants.CERRAR_PARENTESIS);
		return str.toString();
	}

	/**
	 * Sentencia para llamara la funcion DEVOLVERMARCA.
	 * 
	 * @param conn
	 *            Conexión
	 * @param nameColum
	 *            Nombre de la Columna
	 * @param bit
	 *            Al que se desea acceder para obtener la marca
	 * @return
	 * @throws IeciTdException
	 */
	public static String getNativeDevolverMarcaSyntax(DbConnection conn,
			String nameColum, int bit) throws IeciTdException {
		StringBuffer str = null;
		if (isSqlServer(conn))
			str = new StringBuffer(SQL_SERVER_MARCA_HUECO).append("(")
					.append(nameColum).append(", ").append(bit).append(")");
		else if (isOracle(conn))
			str = new StringBuffer(ORACLE_MARCA_HUECO).append("(")
					.append(nameColum).append(", ").append(bit).append(")");
		else if (isPostgreSQL(conn))
			str = new StringBuffer(POSTGRES_MARCA_HUECO).append("(")
					.append(nameColum).append(", ").append(bit).append(")");
		else if (isDB2(conn))
			str = new StringBuffer(DB2_MARCA_HUECO).append("(")
					.append(nameColum).append(", ").append(bit).append(")");
		else
			throw new IeciTdException(DbError.EC_INVALID_ENGINE,
					DbError.EM_INVALID_ENGINE);
		return str.toString();
	}

	/**
	 * Sentencia para llamara la funcion isnumeric.
	 * 
	 * @param conn
	 *            Conexión
	 * @param nameColum
	 *            Nombre de la Columna
	 * @return
	 * @throws IeciTdException
	 */
	public static String getNativeIsNumericSyntax(DbConnection conn,
			String nameColum) throws IeciTdException {
		StringBuffer str = null;
		if (isSqlServer(conn))
			str = new StringBuffer(SQL_SERVER_ISNUMERIC).append("(")
					.append(nameColum).append(")=1");
		else if (isOracle(conn))
			str = new StringBuffer(ORACLE_ISNUMERIC).append("(")
					.append(nameColum).append(")=1");
		else if (isPostgreSQL(conn))
			str = new StringBuffer(POSTGRES_ISNUMERIC).append("(")
					.append(nameColum).append(")='1'");
		else if (isDB2(conn)) // DB2_CHAR para poder comparar con la cadena '1'
			str = new StringBuffer(DB2_ISNUMERIC).append("(").append(nameColum)
					.append(")=1");
		else
			throw new IeciTdException(DbError.EC_INVALID_ENGINE,
					DbError.EM_INVALID_ENGINE);
		return str.toString();
	}

	/**
	 * Obtiene la Sentencia para poder rellenar una cadena con caracter de
	 * relleno para que la cadena tenga una longitud fija.
	 * 
	 * @param conn
	 *            Conexión
	 * @param nameColumToPad
	 *            Nombre de la Columna
	 * @param numChars
	 *            Longitud final de la cadena
	 * @param fillText
	 *            Carácter de Relleno
	 * @return
	 * @throws IeciTdException
	 */
	public static String getNativeLPadSyntax(DbConnection conn,
			String nameColumToPad, int numChars, String fillText)
			throws IeciTdException {
		StringBuffer str = new StringBuffer();
		String newNameColumToPad = nameColumToPad;
		if (isSqlServer(conn)) {

			String concatSymbol = getNativeConcatSyntax(conn);
			str.append(SQL_SERVER_RIGHT).append("(")
					.append(SQL_SERVER_REPLICATE).append("('").append(fillText)
					.append("',").append(numChars).append(")")
					.append(concatSymbol).append(SQL_SERVER_LEFT).append("(")
					.append(nameColumToPad).append(",").append(numChars)
					.append("), ").append(numChars).append(")");

			return str.toString();

		} else if (isOracle(conn))
			str.append(" ").append(ORACLE_LPAD);
		else if (isPostgreSQL(conn))
			str.append(" ").append(POSTGRES_LPAD);
		else if (isDB2(conn)) {
			// DB2 getNativeLPadSyntax
			str.append(" ").append(DB2_LPAD);
			newNameColumToPad = getNativeStrSyntax(conn, newNameColumToPad);
		} else {
			throw new IeciTdException(DbError.EC_INVALID_ENGINE,
					DbError.EM_INVALID_ENGINE);
		}
		str.append("(").append(newNameColumToPad).append(",").append(numChars)
				.append(",").append("'").append(fillText).append("'")
				.append(") ");
		return str.toString();
	}

	/**
	 * Obtiene la Sentencia SQL para convertir un número o fecha en cadena
	 * 
	 * @param conn
	 *            Conexión
	 * @param nameColumn
	 *            Nombre de la Columna
	 * @param pattern
	 *            Patrón de la cadena.
	 * @return
	 * @throws IeciTdException
	 */
	public static String getNativeToCharWithPatternSyntax(DbConnection conn,
			String nameColumn, String pattern) throws IeciTdException {
		StringBuffer str = new StringBuffer();

		if (isSqlServer(conn)) {
			if (pattern.equals(TO_CHAR_PATTERN_DDMM)) {
				str = str.append(" substring(convert(varchar,")
						.append(nameColumn).append(",103),1,6) ");
			} else if (pattern.equals(TO_CHAR_PATTERN_AAAAMMDD)) {
				str = str.append(" substring(convert(varchar,")
						.append(nameColumn).append(",120),1,10) ");
			} else if (pattern.equals(TO_CHAR_PATTERN_MMDD)) {
				str = str.append(" substring(convert(varchar,")
						.append(nameColumn).append(",101),1,6) ");
			} else if (pattern.equals(TO_CHAR_PATTERN_YYYY)) {
				str = str.append(" substring(convert(varchar,")
						.append(nameColumn).append(",121),1,4) ");
			} else
				throw new IeciTdException(DbError.EC_INVALID_DATA_TYPE,
						DbError.EM_INVALID_DATA_TYPE);
		} else if (isOracle(conn)) {
			str = str.append(ORACLE_TO_CHAR).append("(").append(nameColumn)
					.append(",'").append(pattern).append("')");
		} else if (isPostgreSQL(conn)) {
			str = str.append(POSTGRES_TO_CHAR).append("(").append(nameColumn)
					.append(",'").append(pattern).append("')");
		} else if (isDB2(conn)) {
			str = str.append(DB2_VARCHAR_FORMAT).append("(").append(nameColumn)
					.append(",'").append(pattern).append("')");
		} else {
			throw new IeciTdException(DbError.EC_INVALID_ENGINE,
					DbError.EM_INVALID_ENGINE);
		}
		return str.toString();
	}

	public static String getNativeStrSyntax(DbConnection conn, String nameColumn)
			throws IeciTdException {
		StringBuffer str = new StringBuffer();
		if (isSqlServer(conn))
			str = str.append(SQL_SERVER_CAST).append("((").append(nameColumn)
					.append(") AS VARCHAR)");
		else if (isOracle(conn))
			str = str.append(ORACLE_TO_CHAR).append("(").append(nameColumn)
					.append(")");
		else if (isPostgreSQL(conn)) {
			str = str.append(POSTGRES_TO_CHAR).append("(").append(nameColumn)
					.append("," + MASCARA_CONVERT_INT_TO_VARCHAR_POSTGRE + ")");
		} else if (isDB2(conn)) {
			str = str.append(DB2_CHAR).append("(").append(nameColumn)
					.append(")");
		} else {
			throw new IeciTdException(DbError.EC_INVALID_ENGINE,
					DbError.EM_INVALID_ENGINE);
		}
		return str.toString();
	}

	public static String getNativeStrPosSyntax(DbConnection conn,
			DbColumnDef colDef, String value) throws IeciTdException {
		StringBuffer str = new StringBuffer();
		if (isSqlServer(conn))
			str = str.append(SQL_SERVER_CHARINDEX).append("('").append(value)
					.append("',").append(colDef.getQualifiedName()).append(")");
		else if (isOracle(conn))
			str = str.append(ORACLE_INSTR).append("(")
					.append(colDef.getQualifiedName()).append(",'")
					.append(value).append("')");
		else if (isPostgreSQL(conn)) {
			str = str.append(POSTGRES_STR_POS).append("(")
					.append(colDef.getQualifiedName()).append(",'")
					.append(value).append("')");
		} else if (isDB2(conn)) {
			str = str.append(DB2_POS_STR).append("('").append(value)
					.append("',").append(colDef.getQualifiedName()).append(")");
		} else {
			throw new IeciTdException(DbError.EC_INVALID_ENGINE,
					DbError.EM_INVALID_ENGINE);
		}
		return str.toString();
	}

	public static String getNativeStrPosSyntax(DbConnection conn,
			DbColumnDef colDef, DbColumnDef value) throws IeciTdException {
		return getNativeStrPosSyntax(conn, colDef, value, false);
	}

	public static String getNativeStrPosSyntax(DbConnection conn,
			DbColumnDef colDef, DbColumnDef value, boolean db2Especial)
			throws IeciTdException {
		StringBuffer str = new StringBuffer();
		if (isSqlServer(conn))
			str = str.append(SQL_SERVER_CHARINDEX).append("(")
					.append(value.getQualifiedName()).append(",")
					.append(colDef.getQualifiedName()).append(")");
		else if (isOracle(conn))
			str = str.append(ORACLE_INSTR).append("(")
					.append(colDef.getQualifiedName()).append(",")
					.append(value.getQualifiedName()).append(")");
		else if (isPostgreSQL(conn)) {
			str = str.append(POSTGRES_STR_POS).append("(")
					.append(colDef.getQualifiedName()).append(",")
					.append(value.getQualifiedName()).append(")");
		} else if (isDB2(conn)) {
			str = str.append(DB2_POS_STR).append("(")
					.append(value.getQualifiedName()).append(",")
					.append(colDef.getQualifiedName()).append(")");
		} else {
			throw new IeciTdException(DbError.EC_INVALID_ENGINE,
					DbError.EM_INVALID_ENGINE);
		}
		return str.toString();
	}

	public static String getNativeSubstrSyntax(DbConnection conn,
			String nameColumn, String begin, String end) throws IeciTdException {
		StringBuffer str = new StringBuffer();
		if (isSqlServer(conn))
			str = str.append(SQL_SERVER_SUBSTRING).append("(")
					.append(nameColumn).append(",").append(begin).append(",")
					.append(end).append(")");
		else if (isOracle(conn))
			str = str.append(ORACLE_SUBSTR).append("(").append(nameColumn)
					.append(",").append(begin).append(",").append(end)
					.append(")");
		else if (isPostgreSQL(conn)) {
			str = str.append(POSTGRES_SUBSTR).append("(").append(nameColumn)
					.append(",").append(begin).append(",").append(end)
					.append(")");
		} else if (isDB2(conn)) {
			str = str.append(DB2_SUBSTR).append("(").append(nameColumn)
					.append(",").append(begin).append(",").append(end)
					.append(")");
		} else {
			throw new IeciTdException(DbError.EC_INVALID_ENGINE,
					DbError.EM_INVALID_ENGINE);
		}
		return str.toString();
	}

	public static String getNativeToNumberSyntax(DbConnection conn,
			String nameColumn, int numberOfDigits) throws IeciTdException {
		StringBuffer str = new StringBuffer();
		if (isSqlServer(conn))
			str = str.append(SQL_SERVER_CONVERT).append("(numeric,")
					.append(nameColumn).append(")");
		else {
			StringBuffer pattern = new StringBuffer();
			for (int i = 0; i < numberOfDigits; i++) {
				pattern.append("9");
			}
			if (isOracle(conn)) {
				str = str.append(ORACLE_TO_NUMBER).append("(")
						.append(nameColumn).append(",'")
						.append(pattern.toString()).append("')");
			} else if (isPostgreSQL(conn)) {
				str = str.append(POSTGRES_TO_NUMBER).append("(")
						.append(nameColumn).append(",'")
						.append(pattern.toString()).append("')");
			} else if (isDB2(conn)) {
				str = str.append(DB2_TO_NUMBER).append("(").append(nameColumn)
						.append(")");
			} else {
				throw new IeciTdException(DbError.EC_INVALID_ENGINE,
						DbError.EM_INVALID_ENGINE);
			}
		}
		return str.toString();
	}

	public static String getNativeGetNumericPositiveSyntax(DbConnection conn,
			String value) throws IeciTdException {
		StringBuffer str = new StringBuffer();
		if (isSqlServer(conn))
			str.append(SQL_SERVER_GETNUMERICPOSITIVE);
		else if (isOracle(conn))
			str.append(ORACLE_GETNUMERICPOSITIVE);
		else if (isPostgreSQL(conn))
			str.append(POSTGRES_GETNUMERICPOSITIVE);
		else if (isDB2(conn))
			str.append(DB2_GETNUMERICPOSITIVE);
		else {
			throw new IeciTdException(DbError.EC_INVALID_ENGINE,
					DbError.EM_INVALID_ENGINE);
		}
		return str.toString() + "(" + value + ")";
	}

	public static String getColNamesForInsertSentenceSyntax(DbConnection conn,
			String stringColsNames) throws IeciTdException {
		StringBuffer str = new StringBuffer();
		if (isSqlServer(conn))
			str = new StringBuffer(stringColsNames);
		else if (isOracle(conn))
			str = new StringBuffer(stringColsNames);
		else if (isPostgreSQL(conn)) {
			// eliminar los nombres de columna
			// coger tokens de columnas
			StringOwnTokenizer tokenizer = new StringOwnTokenizer(
					stringColsNames, ",");
			while (tokenizer.hasMoreTokens()) {
				String tok = tokenizer.nextToken();
				String[] splits = tok.split("\\.");
				if (str.length() > 0)
					str.append(",");
				if (splits != null && splits.length == 2) {
					str.append(splits[1]);
				} else {
					str.append(tok);
				}
			}
		} else if (isDB2(conn)) {
			str = new StringBuffer(stringColsNames);
		}

		return str.toString();
	}

	public static String getNativeDateTimeSyntax(DbConnection conn, Date date,
			boolean onlyDate) throws Exception {

		String str;

		if (isSqlServer(conn)) {
			str = getSqlServerNativeDateTimeSyntax(date, onlyDate);
		} else if (isOracle(conn)) {
			str = getOracleNativeDateTimeSyntax(date, onlyDate);
		} else if (isPostgreSQL(conn)) {
			str = getPostgreeNativeToDateSyntax(date, onlyDate);
		} else if (isDB2(conn)) {
			// DB2 getNativeDateTimeSyntax(DbConnection conn,Date date, boolean
			// onlyDate)
			str = getDB2NativeToDateSyntax(date, onlyDate);
		} else {
			throw new IeciTdException(DbError.EC_INVALID_ENGINE,
					DbError.EM_INVALID_ENGINE);
		}

		return str;

	}

	public static String getNativeGreatestSyntax(DbConnection conn,
			String nameColumn1, String nameColumn2) throws Exception {

		StringBuffer str = new StringBuffer();
		if (isSqlServer(conn))
			str = str.append(SQL_SERVER_GREATEST).append("(")
					.append(nameColumn1).append(",").append(nameColumn2)
					.append(")");
		else if (isOracle(conn))
			str = str.append(ORACLE_GREATEST).append("(").append(nameColumn1)
					.append(",").append(nameColumn2).append(")");
		else if (isPostgreSQL(conn)) {
			str = str.append(POSTGRES_GREATEST).append("(").append(nameColumn1)
					.append(",").append(nameColumn2).append(")");
		} else if (isDB2(conn)) {
			// DB2 getNativeGreatestSyntax
			str = str.append(DB2_GREATEST).append("(").append(nameColumn1)
					.append(",").append(nameColumn2).append(")");
		} else {
			throw new IeciTdException(DbError.EC_INVALID_ENGINE,
					DbError.EM_INVALID_ENGINE);
		}
		return str.toString();
	}

	public static String getNativeAddYearSyntax(DbConnection conn,
			String nameColumDate, String incremento) throws Exception {

		StringBuffer str;

		if (isSqlServer(conn)) {
			str = new StringBuffer().append(SQL_SERVER_ADD_YEAR)
					.append(incremento).append(",").append(nameColumDate)
					.append(")");
		} else if (isOracle(conn)) {
			str = new StringBuffer().append(ORACLE_ADD_MONTH).append(" (")
					.append(nameColumDate).append(",")
					.append(incremento + "*12").append(")");
		} else if (isPostgreSQL(conn)) {
			str = new StringBuffer().append(POSTGRES_ADD_MONTH).append(" (")
					.append(nameColumDate).append(",")
					.append(incremento + "*12").append(")");
		} else if (isDB2(conn)) {
			// DB2 getNativeAddYearSyntax
			str = new StringBuffer().append(nameColumDate).append(" + ")
					.append(incremento).append(DB2_YEAR);
		} else {
			throw new IeciTdException(DbError.EC_INVALID_ENGINE,
					DbError.EM_INVALID_ENGINE);
		}
		return str.toString();

	}

	public static String getNativeToDateSyntax(DbConnection conn, Date data,
			SimpleDateFormat dateFormat) throws Exception {

		String str;
		if (isSqlServer(conn))
			str = getSqlServerNativeToDateTimeSyntax(data, dateFormat);
		else if (isOracle(conn))
			str = getOracleNativeToDateSyntax(data, dateFormat);
		else if (isPostgreSQL(conn))
			str = getPostgreeNativeToDateSyntax(data, dateFormat);
		else if (isDB2(conn)) {
			// DB2 getNativeToDateSyntax
			str = getDB2NativeToDateSyntax(data, dateFormat);
		} else {
			throw new IeciTdException(DbError.EC_INVALID_ENGINE,
					DbError.EM_INVALID_ENGINE);
		}

		return str;
	}

	// public static String getNativeToDateSyntax(DbConnection conn, DbColumnDef
	// dateColumn, SimpleDateFormat dateFormat) throws Exception {
	//
	// String str;
	// if (isSqlServer(conn))
	// str = getSqlServerNativeToDateTimeSyntax(dateColumn, dateFormat);
	// else if (isOracle(conn))
	// str = getStandardNativeToDateSyntax(dateColumn, dateFormat);
	// else if (isPostgreSQL(conn))
	// str = getStandardNativeToDateSyntax(dateColumn, dateFormat);
	// else if(isDB2(conn)) {
	// str = getDB2NativeToDateSyntax(dateColumn, dateFormat);
	// }
	// else {
	// throw new IeciTdException(DbError.EC_INVALID_ENGINE,
	// DbError.EM_INVALID_ENGINE);
	// }
	//
	// return str;
	// }

	public static String getNativeToDateSyntax(DbConnection conn, String data,
			SimpleDateFormat dateFormat) throws Exception {

		String str;
		if (isSqlServer(conn))
			str = getSqlServerNativeToDateTimeSyntax(data, dateFormat);
		else if (isOracle(conn))
			str = getStandardNativeToDateSyntax(data, dateFormat);
		else if (isPostgreSQL(conn))
			str = getStandardNativeToDateSyntax(data, dateFormat);
		else if (isDB2(conn)) {
			str = getDb2NativeToDateTimeSyntax(data, dateFormat);
		} else {
			throw new IeciTdException(DbError.EC_INVALID_ENGINE,
					DbError.EM_INVALID_ENGINE);
		}
		return str;
	}

	public static String getNativeLengthSyntax(DbConnection conn,
			String nameColumn) throws Exception {

		StringBuffer str = new StringBuffer();
		if (isSqlServer(conn))
			str = str.append(SQL_SERVER_LENGTH).append("(").append(nameColumn)
					.append(")");
		else if (isOracle(conn))
			str = str.append(ORACLE_LENGTH).append("(").append(nameColumn)
					.append(")");
		else if (isPostgreSQL(conn)) {
			str = str.append(POSTGRES_LENGTH).append("(").append(nameColumn)
					.append(")");
		} else if (isDB2(conn)) {
			str = str.append(DB2_LENGTH).append("(").append(nameColumn)
					.append(")");
		} else {
			throw new IeciTdException(DbError.EC_INVALID_ENGINE,
					DbError.EM_INVALID_ENGINE);
		}
		return str.toString();
	}

	public static String getNativeIfNullSyntax(DbConnection conn,
			String sqlToChek, String sqlValueDefault) throws Exception {

		String str;
		if (isSqlServer(conn))
			str = new StringBuffer(" COALESCE (").append(sqlToChek).append(",")
					.append(sqlValueDefault).append(") ").toString();
		else if (isOracle(conn))
			str = new StringBuffer(" nvl (").append(sqlToChek).append(",")
					.append(sqlValueDefault).append(") ").toString();
		else if (isPostgreSQL(conn))
			str = new StringBuffer(" COALESCE (").append(sqlToChek).append(",")
					.append(sqlValueDefault).append(") ").toString();
		else if (isDB2(conn)) {
			str = new StringBuffer(" COALESCE (").append(sqlToChek).append(",")
					.append(sqlValueDefault).append(") ").toString();
		} else {
			throw new IeciTdException(DbError.EC_INVALID_ENGINE,
					DbError.EM_INVALID_ENGINE);
		}

		return str;
	}

	private static String getSqlServerNativeToDateTimeSyntax(Date data,
			SimpleDateFormat dateFormat) {
		return getSqlServerNativeToDateTimeSyntax(dateFormat.format(data),
				dateFormat);
	}

	private static String getOracleNativeToDateSyntax(Date data,
			SimpleDateFormat dateFormat) {
		return getStandardNativeToDateSyntax(dateFormat.format(data),
				dateFormat);
	}

	private static String getPostgreeNativeToDateSyntax(Date data,
			SimpleDateFormat dateFormat) {
		return getStandardNativeToDateSyntax(dateFormat.format(data),
				dateFormat);
	}

	// DB2 getDB2NativeToDateSyntax(Date data, SimpleDateFormat dateFormat)
	private static String getDB2NativeToDateSyntax(Date data,
			SimpleDateFormat dateFormat) {

		String quotedToken = new String("'");

		return new StringBuffer("TIMESTAMP(").append(quotedToken)
				.append(CustomDateFormat.SDF_YYYYMMDD_HHMMSS.format(data))
				.append(quotedToken).append(")").toString();
	}

	// private static String getDB2NativeToDateSyntax(DbColumnDef dataColumn,
	// SimpleDateFormat dateFormat) {
	//
	// return dataColumn.getQualifiedName();
	// }

	// private static String getSqlServerNativeToDateTimeSyntax(DbColumnDef
	// dataColumn, SimpleDateFormat dateFormat) {
	// return new
	// StringBuffer(SQL_SERVER_CONVERT).append("(datetime,").append(dataColumn.getQualifiedName()).append(",120)").toString();
	// }

	private static String getSqlServerNativeToDateTimeSyntax(String data,
			SimpleDateFormat dateFormat) {

		return new StringBuffer(SQL_SERVER_CONVERT).append("(datetime,'")
				.append(data).append("',120)").toString();
	}

	private static String getDb2NativeToDateTimeSyntax(String data,
			SimpleDateFormat dateFormat) throws ParseException {

		Date date = dateFormat.parse(data);

		return getDB2NativeToDateSyntax(date, null);
	}

	private static String getStandardNativeToDateSyntax(String data,
			SimpleDateFormat dateFormat) {
		String pattern = dateFormat.toPattern();
		if (pattern.equals(CustomDateFormat.SDF_YYYYMMDD.toPattern())) {
			pattern = "yyyy-MM-dd";
		} else if (pattern.equals(CustomDateFormat.SDF_YYYYMMDD_HHMMSS
				.toPattern())) {
			pattern = "YYYY-MM-DD HH24:MI:SS";
		} else if (pattern.equals(CustomDateFormat.SDF_YYYYMM.toPattern())) {
			pattern = "YYYY-DD";
		} else if (pattern.equals(CustomDateFormat.SDF_DD.toPattern())) {
			pattern = "DD";
		} else if (pattern.equals(CustomDateFormat.SDF_MM.toPattern())) {
			pattern = "MM";
		} else if (pattern.equals(CustomDateFormat.SDF_YYYY.toPattern())) {
			pattern = "yyyy";
		}

		return new StringBuffer("TO_DATE('").append(data).append("','")
				.append(pattern).append("')").toString();
	}

	// private static String getStandardNativeToDateSyntax(DbColumnDef
	// dataColumn, SimpleDateFormat dateFormat) {
	// String pattern = dateFormat.toPattern();
	// if (pattern.equals(CustomDateFormat.SDF_YYYYMMDD.toPattern())){
	// pattern = "yyyy-MM-dd";
	// }else if
	// (pattern.equals(CustomDateFormat.SDF_YYYYMMDD_HHMMSS.toPattern())){
	// pattern = "YYYY-MM-DD HH24:MI:SS";
	// }else if (pattern.equals(CustomDateFormat.SDF_YYYYMM.toPattern())){
	// pattern = "YYYY-DD";
	// }else if (pattern.equals(CustomDateFormat.SDF_DD.toPattern())){
	// pattern = "DD";
	// }else if (pattern.equals(CustomDateFormat.SDF_MM.toPattern())){
	// pattern = "MM";
	// }else if (pattern.equals(CustomDateFormat.SDF_YYYY.toPattern())){
	// pattern = "yyyy";
	// }
	//
	//
	// return new StringBuffer("TO_DATE(").append(dataColumn.getQualifiedName())
	// .append(",'").append(pattern).append("')")
	// .toString();
	// }

	private static String getOracleNativeDateTimeSyntax(Date data,
			boolean onlyDate) {

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

	private static String getPostgreeNativeToDateSyntax(Date data,
			boolean onlyDate) {

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

	/**
	 * Obtiene el texto para incluir en la consulta de para una columna de tipo
	 * date.
	 * 
	 * @param data
	 * @param onlyDate
	 * @return
	 */
	private static String getDB2NativeToDateSyntax(Date data, boolean onlyDate) {

		String str;
		String pattern;
		SimpleDateFormat sdf;

		pattern = CustomDateFormat.FORMATO_ANIO_MES_DIA_HORAS_MINUTOS_SEGUNDOS;
		sdf = new SimpleDateFormat(pattern);

		if (onlyDate) {
			// Quitar horas, minutos, segundos y milisegundos
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(data);
			Calendar calendarSoloFecha = DateUtils.getCalendar(calendar);
			data = calendarSoloFecha.getTime();
		}

		pattern = DB2_DB_DATE_FORMAT;

		str = "TIMESTAMP('" + sdf.format(data) + "')";

		return str;

	}

	private static String getSqlServerNativeDateTimeSyntax(Date data,
			boolean onlyDate) {

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

	public static String getNativeTextsJoinSyntax(DbConnection conn)
			throws IeciTdException {

		String str = new String();

		if (isSqlServer(conn))
			str = SQL_SERVER_TEXTS_JOIN;
		else if (isOracle(conn))
			str = ORACLE_TEXTS_JOIN;
		else if (isPostgreSQL(conn)) {
			str = POSTGRES_TEXTS_JOIN;
		} else if (isDB2(conn)) {
			// Db2 Por ahora no se utiliza, cuando se meta la búsqueda
			// documental revisarlo
			str = DB2_TEXTS_JOIN;
		} else {
			throw new IeciTdException(DbError.EC_INVALID_ENGINE,
					DbError.EM_INVALID_ENGINE);
		}

		return str;
	}

	public static String getNativeTextsEscapePrefixWordSyntax(DbConnection conn)
			throws IeciTdException {

		String str = new String();

		if (isOracle(conn)) {
			str = ORACLE_TEXTS_ESCAPE_PREFIX;
		} else {
			str = Constants.BLANK;
		}
		return str;
	}

	public static String getNativeTextsEscapeSuffixWordSyntax(DbConnection conn)
			throws IeciTdException {

		String str = new String();

		if (isOracle(conn)) {
			str = ORACLE_TEXTS_ESCAPE_SUFFIX;
		} else {
			str = Constants.BLANK;
		}
		return str;
	}

	public static String getNativeMinusSintax(DbConnection conn)
			throws IeciTdException {
		String str = new String();

		if (isSqlServer(conn))
			str = getSQLServerNativeMinusSintax();
		else if (isOracle(conn))
			str = getOracleNativeMinusSintax();
		else if (isPostgreSQL(conn)) {
			str = getPostgreeNativeMinusSintax();
		} else if (isDB2(conn)) {
			str = getDB2NativeMinusSintax();
		} else {
			throw new IeciTdException(DbError.EC_INVALID_ENGINE,
					DbError.EM_INVALID_ENGINE);
		}

		return str;

	}

	public static String generateDB2SqlConnectBy(String aliasTablaCTE,
			String columnasCTE, String columnas1, String tabla1, String qual1,
			String columnas2, String tablas2, String where2) {
		return generateDB2SqlConnectBy(aliasTablaCTE, columnasCTE, columnas1,
				tabla1, qual1, columnas2, tablas2, where2, null, null);
	}

	public static String generateDB2SqlConnectBy(String aliasTablaCTE,
			String columnasCTE, String columnas1, String tabla1, String qual1,
			String columnas2, String tablas2, String where2, String whereFinal,
			String columnasFinal) {
		String str = new String();

		/*
		 * TableDef tableDef1 = new TableDef("n"); TableDef tableDef2 = new
		 * TableDef(ElementoNoAsignableDBEntity.TABLE_NAME,"nplus1");
		 * 
		 * 
		 * final DbColumnDef TABLA1_CAMPO_ID = new
		 * DbColumnDef("value",tableDef1.getName(),campo_id);
		 * 
		 * final DbColumnDef TABLA2_CAMPO_ID = new
		 * DbColumnDef("value",tableDef2.getName(),campo_id); final DbColumnDef
		 * TABLA2_CAMPO_IDPADRE = new DbColumnDef(tableDef2,campo_id_padre);
		 */

		final StringBuffer sqlCompleta =

		new StringBuffer
		// WITH n(value) AS (
		("WITH ").append(aliasTablaCTE).append("(").append(columnasCTE)
				.append(") AS (").append(DBUtils.SELECT).append(columnas1)
				.append(DBUtils.FROM).append(tabla1).append(qual1)
				.append(DBUtils.UNION_ALL).append(DBUtils.SELECT)
				.append(columnas2).append(DBUtils.FROM).append(tablas2)
				.append(where2).append(") ");

		if (columnasFinal != null && whereFinal != null) {
			sqlCompleta.append(DBUtils.SELECT).append(columnasFinal)
					.append(DBUtils.FROM).append(aliasTablaCTE)
					.append(whereFinal);
		}

		str = sqlCompleta.toString();

		return str;
	}

	private static String getSQLServerNativeMinusSintax() {
		return EXCEPT;
	}

	private static String getPostgreeNativeMinusSintax() {
		return EXCEPT;
	}

	private static String getOracleNativeMinusSintax() {
		return MINUS;
	}

	private static String getDB2NativeMinusSintax() {
		return EXCEPT;
	}

	/**
	 * Obtiene la consulta para meter dentro de una clausula IN <b>SQLServer</b>
	 * 
	 * @param campo
	 * @param tabla
	 * @param qual
	 * @return
	 * @throws IeciTdException
	 */
	public static ConsultaConnectBy generateNativeSQLWithConnectBy(
			DbConnection conn, TableDef tabla, DbColumnDef idElementoField,
			DbColumnDef idPadreField, String[] idsElementoOrigen,
			Map condiciones) throws IeciTdException {
		StringBuffer sql = new StringBuffer("");
		StringBuffer withClause = new StringBuffer("");

		ConsultaConnectBy consultaSQL = null;

		if (isOracle(conn)) {
			/*
			 * SELECT ID FROM ASGFELEMENTOCF IDS_CTE START WITH id IN ('2','11')
			 * CONNECT BY PRIOR IDS_CTE.id=IDS_CTE.idPadre
			 * 
			 * -- AND restricciones
			 */

			StringBuffer qual = new StringBuffer("");
			if (condiciones != null) {
				Iterator it = condiciones.keySet().iterator();

				while (it.hasNext()) {
					qual.append(DBUtils.AND);
					DbColumnDef columna = (DbColumnDef) it.next();
					Object valor = condiciones.get(columna);

					columna = DBUtils.getCustomizedField(columna,
							tabla.getAlias());

					if (valor instanceof String) {
						String valorString = (String) valor;
						qual.append(DBUtils.generateEQTokenField(columna,
								valorString));
					} else if (valor instanceof Integer) {
						Integer valorInteger = (Integer) valor;
						if (valorInteger != null) {
							int valorInt = valorInteger.intValue();
							qual.append(DBUtils.generateEQTokenField(columna,
									valorInt));
						}
					} else if (valor instanceof String[]
							|| valor instanceof Integer[]) {
						qual.append(DBUtils
								.generateInTokenField(columna, valor));
					}
				}
			}

			sql.append(DBUtils.SELECT)
					.append(DBUtils.getCustomizedField(idElementoField,
							tabla.getAlias()).getQualifiedName())
					.append(DBUtils.FROM)
					.append(tabla.getAlias())
					.append(DBUtils.START_WITH)
					.append(DBUtils.generateInTokenField(idElementoField,
							idsElementoOrigen))
					.append(DBUtils.CONNECT_BY_PRIOR)
					.append(DBUtils.getCustomizedField(idElementoField,
							tabla.getAlias()))
					.append(Constants.EQUAL)
					.append(DBUtils.getCustomizedField(idPadreField,
							tabla.getAlias())).append(qual);

			consultaSQL = new ConsultaConnectBy(sql.toString());

			// sqlIdsAmbito.append(DBUtils.SELECT)
			// .append(idElementoField.getName())
			// .append(DBUtils.FROM)
			// .append(tabla.getAlias());

		} else if (isSqlServer(conn) || isDB2(conn)) {
			consultaSQL = new ConsultaConnectBy();

			TableDef tablaIds = DBUtils.getCustomizedTable(tabla,
					DBUtils.NOMBRE_TABLA_IDS);

			String aliasTablaPrincipal = "A";
			String aliasTablaRecursiva = "R";

			TableDef tablaRecursiva = DBUtils.getCustomizedTable(tablaIds,
					aliasTablaRecursiva);
			/*
			 * Clausula With: WITH IDS_CTE(id) AS( SELECT ASGFELEMENTOCF.ID FROM
			 * ASGFELEMENTOCF WHERE ID IN ('1') UNION ALL SELECT A.ID FROM
			 * ASGFELEMENTOCF A, IDS_CTE AS R WHERE A.IDPADRE = R.ID
			 * 
			 * --- Añadir las condiciones )
			 */

			StringBuffer qual = new StringBuffer("");
			if (condiciones != null) {
				Iterator it = condiciones.keySet().iterator();

				while (it.hasNext()) {
					qual.append(DBUtils.AND);
					DbColumnDef columna = (DbColumnDef) it.next();
					Object valor = condiciones.get(columna);

					columna = DBUtils.getCustomizedField(columna,
							aliasTablaPrincipal);

					if (valor instanceof String) {
						String valorString = (String) valor;
						qual.append(DBUtils.generateEQTokenField(columna,
								valorString));
					} else if (valor instanceof Integer) {
						Integer valorInteger = (Integer) valor;
						if (valorInteger != null) {
							int valorInt = valorInteger.intValue();
							qual.append(DBUtils.generateEQTokenField(columna,
									valorInt));
						}
					} else if (valor instanceof String[]
							|| valor instanceof Integer[]) {
						qual.append(DBUtils
								.generateInTokenField(columna, valor));
					}
				}
			}

			withClause
					.append(getNativeWithSyntax(conn))
					.append(DBUtils.NOMBRE_TABLA_IDS)
					.append(" (")
					.append(idElementoField.getName())
					.append(")")
					.append(DBUtils.AS)
					.append("(")
					.append(DBUtils.SELECT)
					.append(idElementoField.getQualifiedName())
					.append(DBUtils.FROM)
					.append(tabla.getName())
					.append(DBUtils.WHERE)
					.append(DBUtils.generateInTokenField(idElementoField,
							idsElementoOrigen))
					.append(DBUtils.UNION_ALL)
					.append(DBUtils.SELECT)
					.append(DBUtils.getCustomizedField(idElementoField,
							aliasTablaPrincipal).getQualifiedName())
					.append(DBUtils.FROM)
					.append(DBUtils.getCustomizedTable(tabla,
							aliasTablaPrincipal).getDeclaration())
					.append(Constants.COMMA)
					.append(tablaRecursiva.getDeclaration())
					.append(DBUtils.WHERE)
					.append(DBUtils.getCustomizedField(idPadreField,
							aliasTablaPrincipal))
					.append(Constants.EQUAL)
					.append(DBUtils.getCustomizedField(idElementoField,
							aliasTablaRecursiva));

			if (StringUtils.isNotEmpty(qual.toString())) {
				withClause.append(qual);
			}

			withClause.append(") "); // Fin As

			consultaSQL.setWithClause(withClause.toString());

			/*
			 * Clausula SQL SELECT ID FROM IDS_CTE
			 */
			sql.append(DBUtils.SELECT)
					.append(DBUtils.getCustomizedField(idElementoField,
							tablaIds.getAlias()).getQualifiedName())
					.append(DBUtils.FROM).append(tablaIds.getAlias());

			consultaSQL.setSqlClause(sql.toString());
		} else if (isPostgreSQL(conn)) {
			sql.append(getConnectByPostgreSyntax(tabla, idElementoField,
					idPadreField, idsElementoOrigen));

			consultaSQL = new ConsultaConnectBy();
			consultaSQL.setSqlClause(sql.toString());

		}
		return consultaSQL;
	}

	private static String getConnectByPostgreSyntax(TableDef tabla,
			DbColumnDef idElementoField, DbColumnDef idElementoPadreField,
			String[] idsOrigen) {
		/*
		 * SELECT ID FROM connectby('asgfelementocf', 'id', 'idpadre','12', 0)
		 * AS t(id text, idpadre text, level int) UNION SELECT ID FROM
		 * connectby('asgfelementocf', 'id', 'idpadre','11', 0) AS t(id text,
		 * idpadre text, level int)
		 */

		/*
		 * SELECT id FROM connectby('asgfelementocf', 'id', 'idpadre','1',0) AS
		 * t(id text, idpadre text,level int)
		 */

		StringBuffer str = new StringBuffer("");

		if (ArrayUtils.isNotEmpty(idsOrigen)) {
			for (int i = 0; i < idsOrigen.length; i++) {

				if (StringUtils.isNotEmpty(str.toString())) {
					str.append(DBUtils.UNION);
				}

				str.append(DBUtils.SELECT).append(idElementoField.getName())
						.append(DBUtils.FROM).append(POSTGRE_CONNECTBY)
						.append("('").append(tabla.getName()).append("','")
						.append(idElementoField.getName()).append("','")
						.append(idElementoPadreField.getName()).append("','")
						.append(idsOrigen[i]).append("',0").append(")")
						.append(" AS t (").append(idElementoField.getName())
						.append(" text,")
						.append(idElementoPadreField.getName())
						.append(" text,").append("level int)");
			}
		}

		return str.toString();
	}

	private static String getNativeWithSyntax(DbConnection conn)
			throws IeciTdException {
		String str = null;
		if (isSqlServer(conn) || isDB2(conn)) {
			str = DBUtils.WITH;
		}

		return str;
	}

	public static ConsultaConnectBy generateNativeSQLUpdateRecursive(
			DbConnection conn, TableDef tabla, String[] camposUpdate,
			ConsultaConnectBy consultaRecursiva) throws IeciTdException {

		StringBuffer sql = new StringBuffer("");
		ConsultaConnectBy consultaSQL = null;

		if (isOracle(conn) || isPostgreSQL(conn)) {
			/*
			 * UPDATE 'tabla' SET 'camposUpdate' 'consulta.whereClause'
			 */
			sql.append(getUpdateWithSets(tabla, camposUpdate));
			sql.append(consultaRecursiva.getWhereClause());
		} else if (isSqlServer(conn)) {
			/*
			 * 'consulta.WithClause' UPDATE 'tabla' SET 'camposUpdate'
			 * 'consulta.whereClause'
			 */
			sql.append(consultaRecursiva.getWithClause());
			sql.append(getUpdateWithSets(tabla, camposUpdate));
			sql.append(consultaRecursiva.getWhereClause());
		} else if (isDB2(conn)) {
			/*
			 * 'consulta.WithClause' SELECT COUNT(*) FROM OLD TABLE ( UPDATE
			 * 'tabla' SET 'camposUpdate' 'consulta.whereClause' )
			 */
			sql.append(consultaRecursiva.getWithClause());
			sql.append(DBUtils.SELECT).append(DBUtils.COUNT_ALL)
					.append(DBUtils.FROM).append(DBUtils.OLD_TABLE);
			sql.append(DBUtils.ABRIR_PARENTESIS);
			sql.append(getUpdateWithSets(tabla, camposUpdate));
			sql.append(consultaRecursiva.getWhereClause());
			sql.append(DBUtils.CERRAR_PARENTESIS);
		}
		consultaSQL = new ConsultaConnectBy(sql.toString());
		return consultaSQL;
	}

	/**
	 * Obtiene la sintaxis SQL del update con sus respectivos campos a
	 * reemplazar
	 * 
	 * @param tabla
	 * @param camposUpdate
	 * @return
	 */
	private static String getUpdateWithSets(TableDef tabla,
			String[] camposUpdate) {
		StringBuffer sql = new StringBuffer();
		sql.append(DBUtils.UPDATE).append(tabla.getAlias()).append(DBUtils.SET);

		if (ArrayUtils.isNotEmpty(camposUpdate)) {
			for (int i = 0; i < camposUpdate.length; i++) {
				sql.append(camposUpdate[i]);
				if ((i + 1) < camposUpdate.length) {
					sql.append(Constants.COMMA);
				}
			}
		}
		return sql.toString();
	}
}