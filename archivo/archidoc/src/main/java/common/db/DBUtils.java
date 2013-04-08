package common.db;

import gcontrol.db.PermisosListaDbEntityImpl;
import gcontrol.model.NivelAcceso;
import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbInputStatement;
import ieci.core.db.DbOutputStatement;
import ieci.core.db.DbUtil;
import ieci.core.exception.IeciTdException;
import ieci.core.types.IeciTdType;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.apache.log4j.Logger;

import se.usuarios.AppPermissions;
import se.usuarios.GenericUserInfo;
import util.CollectionUtils;
import util.MapColumnsNamesIndex;

import common.Constants;
import common.exceptions.ArchivoModelException;
import common.util.CustomDateFormat;
import common.util.StringUtils;

/**
 * Utilidades para la construccion de consultas a la base de datos
 */
public class DBUtils {

	protected static final Logger PROFILE_LOGGER = Logger.getLogger("PROFILE");

	public static final String NULL_VALUE = "NULL_VALUE";

	public final static String IGUAL_LIKE = "igual";
	public final static String EMPIEZA_LIKE = "empieza";
	public final static String TERMINA_LIKE = "termina";
	public final static String CONTIENE_LIKE = "contiene";
	public final static String AND = " AND ";
	public final static String OR = " OR ";
	public final static String WHERE = " WHERE ";
	public final static String FROM = " FROM ";
	public final static String BETWEEN = " BETWEEN ";
	// public final static String UNION_DUMMY =
	// "SELECT '' AS ID FROM ASGFNIVELCF";
	public final static String SELECT = "SELECT ";
	public final static String COUNT_ALL = " COUNT (*) ";
	public final static String COUNT = " COUNT ";
	public final static String LIKE = " LIKE ";
	public final static String TANTO_POR_CIENTO = "%";
	public final static String COMILLA_SIMPLE = "'";
	public final static String IGUAL = "=";
	public final static String IN = " IN ";
	public final static String NOT = " NOT ";
	public final static String NOT_IN = " NOT IN ";
	public final static String IS_NULL = " IS NULL ";
	public final static String IS_NOT_NULL = " IS NOT NULL ";
	public final static String INTERSECT = " INTERSECT ";
	public final static String UNION = " UNION ";
	public final static String UNION_ALL = " UNION ALL ";
	public final static String DISTINCT = "DISTINCT ";
	public static final String NULL = " NULL ";
	public static final String MIN = " MIN ";
	public static final String MAX = " MAX ";
	public static final String SUM = " SUM";
	public static final String UPPER = " UPPER";
	public static final String MENOR = "<";
	public static final String MAYOR = ">";
	public static final String MENOR_IGUAL = "<=";
	public static final String MAYOR_IGUAL = ">=";
	public static final String QUE_COMIENCE_CON = "QCC";
	public static final String QUE_CONTENGA = "QC";

	public static final String NOMBRE_TABLA_IDS = "IDS_CTE";
	public static final String WITH = "WITH ";

	public static final String AS = " AS ";
	public static final String CONNECT_BY_PRIOR = " CONNECT BY PRIOR ";
	public static final String START_WITH = " START WITH ";

	public static final String ORDER_BY = " ORDER BY ";
	public static final String DESC = " DESC ";
	public static final String CASE_WHEN = " CASE WHEN ";
	public static final String THEN = " THEN ";
	public static final String ELSE = " ELSE ";
	public static final String END = " END ";

	public final static String GROUPBY = " GROUP BY ";
	public final static String HAVING = " HAVING ";

	public static final String ABRIR_PARENTESIS = "(";
	public static final String CERRAR_PARENTESIS = ")";

	public static final String OPERADOR_DIVISION = " / ";

	public final static int MAX_ELEMENTS_IN_QUERY = 800;

	public final static String UPDATE = "UPDATE ";
	public final static String SET = " SET ";
	public final static String OLD_TABLE = " OLD TABLE ";

	static Logger logger = Logger.getLogger(DBUtils.class);

	public static String getCondition(String qual) {
		return ((qual == null || qual.length() == 0) ? WHERE : AND);
	}

	public static String getOrCondition(String qual) {
		return ((qual == null || qual.length() == 0) ? WHERE : OR);
	}

	public static String getAnd(String qual) {
		return ((qual == null || qual.length() == 0) ? "" : AND);
	}

	public static String getOr(String qual) {
		return ((qual == null || qual.length() == 0) ? "" : OR);
	}

	public static String generateInTokenField(DbColumnDef colDef, Object values) {
		Iterator it = CollectionUtils.getIterator(values);
		StringBuffer buf = new StringBuffer("(");
		while (it.hasNext()) {
			buf.append("(")
					.append(colDef.getQualifiedName())
					.append(" IN (")
					.append(CollectionUtils.join(it, ",",
							(isNumber(colDef) ? "" : "'"),
							MAX_ELEMENTS_IN_QUERY)).append("))");
			if (it.hasNext())
				buf.append(" OR ");
		}
		buf.append(")");

		return buf.toString();
		/*
		 * return new StringBuffer(colDef.getQualifiedName()) .append(" IN (")
		 * .append(CollectionUtils.join( CollectionUtils.getIterator(values),
		 * ",", (isNumber(colDef) ? "": "'"))) .append(")") .toString();
		 */
	}

	public static String generateInTokenFieldSubQuery(DbColumnDef colDef,
			String subquery) {
		StringBuffer buf = new StringBuffer("(");
		buf.append(colDef.getQualifiedName()).append(" IN (").append(subquery)
				.append("))");
		return buf.toString();
	}

	public static String generateNotInTokenFieldSubQuery(DbColumnDef colDef,
			String subquery) {
		StringBuffer buf = new StringBuffer("(");
		buf.append(colDef.getQualifiedName()).append(" NOT IN (")
				.append(subquery).append("))");
		return buf.toString();
	}

	public static String generateOrderBy(DbColumnDef colDef) {
		StringBuffer orderBy = new StringBuffer(ORDER_BY);

		// if
		// (!colDef.getName().equals(NivelCFDBEntityImpl.NOMBRE_NIVEL_FIELD.getName()))
		orderBy.append(colDef.getQualifiedName());
		/*
		 * else { orderBy.append(colDef.getName()); }
		 */

		return orderBy.toString();
	}

	public static String generateOrderBy(DbColumnDef[] colDef) {
		StringBuffer orderBy = new StringBuffer();
		for (int i = 0; i < colDef.length; i++) {
			DbColumnDef dbCol = colDef[i];

			if (StringUtils.isNotEmpty(orderBy.toString())) {
				orderBy.append(Constants.COMMA);
			} else {
				orderBy.append(DBUtils.ORDER_BY);
			}
			orderBy.append(dbCol.getQualifiedName());
		}

		return orderBy.toString();
	}

	public static String generateGroupBy(DbColumnDef colDef) {
		StringBuffer orderBy = new StringBuffer(GROUPBY);
		orderBy.append(colDef.getQualifiedName());
		return orderBy.toString();
	}

	public static String generateGroupBy(DbColumnDef[] colDef) {
		StringBuffer orderBy = new StringBuffer();
		for (int i = 0; i < colDef.length; i++) {
			DbColumnDef dbCol = colDef[i];

			if (StringUtils.isNotEmpty(orderBy.toString())) {
				orderBy.append(Constants.COMMA);
			} else {
				orderBy.append(DBUtils.GROUPBY);
			}
			orderBy.append(dbCol.getQualifiedName());
		}

		return orderBy.toString();
	}

	public static String generateColWithNullValues(DbColumnDef colDef) {
		/* (Case When [Order] Is Null Then 1 Else 0 End) alias */
		StringBuffer orderBy = new StringBuffer();
		orderBy.append("(").append(CASE_WHEN);
		orderBy.append(colDef.getQualifiedName());
		orderBy.append(IS_NULL).append(THEN).append(1).append(ELSE).append(0)
				.append(END);
		orderBy.append(")");
		return orderBy.toString();
	}

	public static String generateOrderByWithNullValues(DbColumnDef colDef) {
		/* Order by (Case When [Order] Is Null Then 1 Else 0 End), [Order] */
		StringBuffer orderBy = new StringBuffer(ORDER_BY);
		orderBy.append("(").append(CASE_WHEN);
		orderBy.append(colDef.getQualifiedName());
		orderBy.append(IS_NULL).append(THEN).append(1).append(ELSE).append(0)
				.append(END);
		orderBy.append(")").append(", ").append(colDef.getQualifiedName());
		return orderBy.toString();
	}

	public static String generateOrderByDescWithNullValues(DbColumnDef colDef) {
		StringBuffer orderBy = new StringBuffer(
				generateOrderByWithNullValues(colDef));
		orderBy.append(DESC);
		return orderBy.toString();
	}

	/**
	 * Genera la sentencia SUM
	 *
	 * @param colDef
	 * @return
	 */
	public static String generateSUM(DbColumnDef colDef, String alias) {
		StringBuffer sql = new StringBuffer(" SUM(")
				.append(colDef.getQualifiedName()).append(") AS ")
				.append(alias).append(" ");

		return sql.toString();
	}

	/**
	 * Genera la sentencia SUM
	 *
	 * @param colDef
	 * @return
	 */
	public static String generateSUM(String textoColumma) {
		StringBuffer sql = new StringBuffer(" SUM").append(ABRIR_PARENTESIS)
				.append(textoColumma).append(CERRAR_PARENTESIS);

		return sql.toString();
	}

	/**
	 * Genera la sentencia SUM
	 *
	 * @param colDef
	 * @return
	 */
	public static String generateSUM(String columna, String alias) {
		StringBuffer sql = new StringBuffer(" SUM(").append(columna)
				.append(") AS ").append(alias).append(" ");

		return sql.toString();
	}

	/**
	 * Genera la sentencia MAX
	 *
	 * @param colDef
	 * @return
	 */
	public static String generateMAX(DbColumnDef colDef, String alias) {
		StringBuffer sql = new StringBuffer(" MAX(")
				.append(colDef.getQualifiedName()).append(") AS ")
				.append(alias).append(" ");

		return sql.toString();
	}

	/**
	 * Genera la sentencia MIN
	 *
	 * @param colDef
	 * @return
	 */
	public static String generateMIN(DbColumnDef colDef, String alias) {
		StringBuffer sql = new StringBuffer(" MIN(")
				.append(colDef.getQualifiedName()).append(") AS ")
				.append(alias).append(" ");

		return sql.toString();
	}

	public static String generateUnaryFunction(String functionName,
			DbColumnDef field, String alias) {
		StringBuffer sql = new StringBuffer(" " + functionName.toUpperCase()
				+ "(").append(field.getQualifiedName()).append(") AS ")
				.append(alias).append(" ");

		return sql.toString();
	}

	public static String generateOrderByDesc(DbColumnDef colDef) {
		StringBuffer orderBy = new StringBuffer(ORDER_BY);
		orderBy.append(colDef.getQualifiedName());
		orderBy.append(DESC);

		return orderBy.toString();
	}

	public static String generateNotInTokenField(DbColumnDef colDef,
			Object values) {

		if (values == null)
			return "";

		// TODO NACHO Sin probar, no se llama desde ninguna parte del código
		Iterator it = CollectionUtils.getIterator(values);

		StringBuffer buf = new StringBuffer("(");
		while (it.hasNext()) {
			buf.append("(")
					.append(colDef.getQualifiedName())
					.append(" NOT IN (")
					.append(CollectionUtils.join(it, ",",
							(isNumber(colDef) ? "" : "'"),
							MAX_ELEMENTS_IN_QUERY)).append("))");
			if (it.hasNext())
				buf.append(" AND ");
		}
		buf.append(")");

		return buf.toString();

		/*
		 * return new StringBuffer(colDef.getQualifiedName())
		 * .append(" NOT IN (") .append(CollectionUtils.join(
		 * CollectionUtils.getIterator(values), ",", (isNumber(colDef) ? "":
		 * "'"))) .append(")") .toString();
		 */
	}

	public static String generateContainsTokenField(DbConnection conn,
			DbColumnDef colDef, DbColumnDef idxColDef, String valueField) {
		try {
			String value = generateContainsValue(conn, valueField);

			String nameField = colDef.getQualifiedName();
			StringBuffer buf = new StringBuffer(" ");
			if (valueField != null) {
				if (DbUtil.isOracle(conn)) {
					buf.append(
							ConstraintType.getSymbol(ConstraintType.CONTAINS))
							.append("(").append(nameField).append(",'")
							.append(value).append("')>0");
				} else if (DbUtil.isPostgreSQL(conn)) {
					buf.append(idxColDef.getQualifiedName())
							.append(ConstraintType
									.getSymbol(ConstraintType.AT_AT))
							.append(" ").append(value).append(" ");
				} else if (DbUtil.isSqlServer(conn)) {
					buf.append(
							ConstraintType.getSymbol(ConstraintType.CONTAINS))
							.append("(").append(nameField).append(",'")
							.append(value).append("')");
				} else if (DbUtil.isDB2(conn)) {
					buf.append(generateLikeFieldQualified(colDef, valueField,
							colDef.getTableName(), CONTIENE_LIKE));
				}
			}
			return buf.toString();
		} catch (IeciTdException e) {
			// TODO Auto-generated catch block
			logger.error(e);
			return null;
		}
	}

	public static String formatValueField(DbConnection conn, String valueField)
			throws IeciTdException {

		// En db2 no se está utilizando la búsqueda documental.
		if (DbUtil.isDB2(conn)) {
			return valueField;
		}

		String fvf = new String();

		valueField = valueField.trim();
		String[] words = valueField.split(Constants.STRING_SPACE, 0);
		if (words != null) {
			String joinString = DbUtil.getNativeTextsJoinSyntax(conn);
			String prefixString = DbUtil
					.getNativeTextsEscapePrefixWordSyntax(conn);
			String suffixString = DbUtil
					.getNativeTextsEscapeSuffixWordSyntax(conn);

			for (int i = 0; i < words.length; i++) {
				if (i > 0)
					fvf = fvf + joinString + prefixString + words[i]
							+ suffixString;
				else
					fvf = fvf + prefixString + words[i] + suffixString;
			}
		} else
			fvf = valueField;

		return fvf;
	}

	public static String generateContainsValue(DbConnection conn,
			String valueField) {
		try {
			// Añadido para tratar los espacios en blanco en las búsquedas
			String formattedValueField = formatValueField(conn, valueField);

			if (DbUtil.isPostgreSQL(conn))
				// return "to_tsquery('" + DbUtil.POSTGRES_DEFAULT_DICTIONARY +
				// "', '" + valueField + "')";
				return "to_tsquery('" + DbUtil.POSTGRES_DEFAULT_DICTIONARY
						+ "', '" + formattedValueField + "')";
			else if (DbUtil.isDB2(conn)) {
				return valueField;
			} else {
				// return valueField;
				return formattedValueField;
			}
		} catch (IeciTdException e) {
			logger.error(e);
			return null;
		}
	}

	public static String generateLikeTokenField(DbColumnDef colDef,
			String valueField) {
		return generateLikeTokenField(colDef, valueField, false);
	}

	public static String generateLikeTokenField(DbColumnDef colDef,
			String value, boolean ignoredCase) {

		StringBuffer buff = new StringBuffer(colDef.getQualifiedName());
		if (ignoredCase)
			buff.insert(0, " UPPER( ").append(" ) ");

		buff.append(" LIKE ");

		buff.append("'%").append(ignoredCase ? value.toUpperCase() : value)
				.append("%'");

		return buff.toString();
	}

	public static String generateLikeFieldQualified(DbColumnDef colDef,
			String valueField, String TABLE_NAME, String like) {
		return generateLikeFieldQualified(colDef, valueField, TABLE_NAME, like,
				false);
	}

	public static String generateLikeFieldQualified(DbColumnDef colDef,
			String valueField, String TABLE_NAME, String like,
			boolean ignoreCase) {
		String nameField = (TABLE_NAME != null && TABLE_NAME.length() > 0) ? TABLE_NAME
				+ "." + colDef.getName()
				: colDef.getQualifiedName();

		nameField = ignoreCase ? " UPPER( " + nameField + ")" : nameField;

		StringBuffer buf = new StringBuffer(" ");
		if (valueField != null) {
			if (like != null && like.equalsIgnoreCase(IGUAL_LIKE)) {
				// buf.append(nameField).append("='").append(newValueField).append("'");
				if (!ignoreCase)
					buf.append(nameField).append("='").append(valueField)
							.append("'");
				else
					buf.append(nameField).append("= UPPER('")
							.append(valueField).append("')");
			} else {
				if (like != null && like.equalsIgnoreCase(EMPIEZA_LIKE)) {
					valueField = new StringBuffer(valueField).append("%")
							.toString();
				} else {
					if (like != null && like.equalsIgnoreCase(TERMINA_LIKE)) {
						valueField = new StringBuffer(valueField)
								.insert(0, "%").toString();
					} else {
						valueField = new StringBuffer(valueField)
								.insert(0, "%").append("%").toString();
					}
				}
				if (!ignoreCase)
					buf.append(nameField).append(" LIKE '").append(valueField)
							.append("' ");
				else {
					buf.append(nameField).append(" LIKE UPPER('")
							.append(valueField).append("') ");
				}
			}
		}

		return buf.toString();
	}

	public static String generateNEQTokenField(DbColumnDef colDef,
			int valueField) {
		return generateTokenFieldQualified(colDef,
				Integer.toString(valueField), "", ConstraintType.DISTINCT);
	}

	public static String generateNEQTokenField(DbColumnDef colDef,
			String valueField) {
		return generateTokenFieldQualified(colDef, valueField, "",
				ConstraintType.DISTINCT);
	}

	public static String generateEQTokenField(DbColumnDef colDef, int valueField) {
		return generateTokenFieldQualified(colDef,
				Integer.toString(valueField), "", ConstraintType.EQUAL);
	}

	public static String generateEQTokenField1(DbColumnDef leftColDef,
			DbColumnDef rigthcolDef) {
		StringBuffer str = new StringBuffer(leftColDef.getQualifiedName())
				.append(Constants.EQUAL).append(rigthcolDef.getQualifiedName());

		return str.toString();
	}

	// FECHAS
	public static String generateEQTokenField(DbConnection conn,
			DbColumnDef colDef, Date fecha) {
		return generateTokenField(conn, colDef, fecha, IGUAL);
	}

	// FECHAS
	protected static String generateTokenField(DbConnection conn,
			DbColumnDef colDef, Date fecha, String operador) {

		StringBuffer retorno = new StringBuffer(Constants.BLANK)
				.append(colDef.getQualifiedName())
				.append(operador)
				.append(DBUtils.getNativeToDateSyntax(conn, fecha,
						CustomDateFormat.SDF_YYYYMMDD_HHMMSS));

		return retorno.toString();
	}

	// TEXTOS
	public static String generateEQTokenField(DbColumnDef colDef,
			String valueField, boolean ignoreCase) {
		return generateTokenFieldQualified(colDef, valueField, "",
				ConstraintType.EQUAL, ignoreCase);
	}

	public static String generateEQTokenField(DbColumnDef colDef,
			String valueField) {
		return generateTokenFieldQualified(colDef, valueField, "",
				ConstraintType.EQUAL);
	}

	public static String generateGTTokenField(DbColumnDef colDef,
			String valueField) {
		return generateTokenFieldQualified(colDef, valueField, "",
				ConstraintType.GREATER);
	}

	public static String generateGTTokenField(DbColumnDef colDef, int valueField) {
		return generateTokenFieldQualified(colDef, valueField, "",
				ConstraintType.GREATER);
	}

	public static String generateGTEQTokenField(DbColumnDef colDef,
			String valueField) {
		return generateTokenFieldQualified(colDef, valueField, "",
				ConstraintType.GREATER_OR_EQUAL);
	}

	public static String generateGTEQTokenField(DbColumnDef colDef,
			int valueField) {
		return generateTokenFieldQualified(colDef, valueField, "",
				ConstraintType.GREATER_OR_EQUAL);
	}

	public static String generateLTTokenField(DbColumnDef colDef,
			String valueField) {
		return generateTokenFieldQualified(colDef, valueField, "",
				ConstraintType.LESS);
	}

	public static String generateLTTokenField(DbColumnDef colDef, int valueField) {
		return generateTokenFieldQualified(colDef, valueField, "",
				ConstraintType.LESS);
	}

	public static String generateLTEQTokenField(DbColumnDef colDef,
			String valueField) {
		return generateTokenFieldQualified(colDef, valueField, "",
				ConstraintType.LESS_OR_EQUAL);
	}

	public static String generateLTEQTokenField(DbColumnDef colDef,
			int valueField) {
		return generateTokenFieldQualified(colDef, valueField, "",
				ConstraintType.LESS_OR_EQUAL);
	}

	public static String generateJoinCondition(String table1,
			DbColumnDef colDef1, String table2, DbColumnDef colDef2) {
		return new StringBuffer().append(table1).append(".")
				.append(colDef1.getName()).append("=").append(table2)
				.append(".").append(colDef2.getName()).toString();
	}

	public static String generateFieldIncrement(DbColumnDef column, int cantidad) {
		return new StringBuffer().append(column.getName()).append("=")
				.append(column.getName()).append(cantidad >= 0 ? "+" : "-")
				.append(Math.abs(cantidad)).toString();
	}

	public static String generateFieldIncrement(DbColumnDef column,
			double cantidad) {
		return new StringBuffer().append(column.getName()).append("=")
				.append(column.getName()).append(cantidad >= 0 ? "+" : "-")
				.append(Math.abs(cantidad)).toString();
	}

	public static String generateJoinCondition(DbColumnDef colDef1,
			DbColumnDef colDef2) {
		if (colDef1.getTableName() == null)
			throw new NotProperlyDefinedColumnException(colDef1);
		if (colDef2.getTableName() == null)
			throw new NotProperlyDefinedColumnException(colDef2);

		return new StringBuffer(colDef1.getQualifiedName()).append("=")
				.append(colDef2.getQualifiedName()).toString();
	}

	// TODO
	/*
	 * public static String generateOuterJoinCondition(DbColumnDef colDef1,
	 * DbColumnDef colDef2 ) { if (colDef1.getTableName() == null) throw new
	 * NotProperlyDefinedColumnException(colDef1); if (colDef2.getTableName() ==
	 * null) throw new NotProperlyDefinedColumnException(colDef2);
	 *
	 * return new StringBuffer(colDef1.getQualifiedName()).append("(+)=")
	 * .append(colDef2.getQualifiedName()).toString(); }
	 */

	public static String generateOuterJoin(JoinDefinition[] joins) {
		return generateOuterJoin(joins, false);
	}

	public static String generateOuterJoin(JoinDefinition[] joins,
			boolean rigthOuterJoin) {
		StringBuffer query = new StringBuffer();

		DbColumnDef leftColDef, rightColDef = null;
		for (int i = 0; i < joins.length; i++) {
			leftColDef = joins[i].getLeftCol();
			rightColDef = joins[i].getRightCol();
			if (leftColDef.getTableDeclaration() == null)
				throw new NotProperlyDefinedColumnException(leftColDef);
			if (rightColDef.getTableDeclaration() == null)
				throw new NotProperlyDefinedColumnException(rightColDef);
			query.append(leftColDef.getTableDeclaration());
			if (rigthOuterJoin)
				query.append(" RIGHT OUTER JOIN ");
			else
				query.append(" LEFT OUTER JOIN ");
			query.append(rightColDef.getTableDeclaration());
			query.append(" ON ").append(leftColDef.getQualifiedName())
					.append("=").append(rightColDef.getQualifiedName())
					.toString();
		}
		return query.toString();
	}

	public static String generateInnerJoinCondition(TableDef tableDef,
			JoinDefinition[] joins) {
		StringBuffer sql = new StringBuffer(tableDef.getDeclaration());
		DbColumnDef leftColDef, rightColDef;
		for (int i = 0; i < joins.length; i++) {
			leftColDef = joins[i].getLeftCol();
			rightColDef = joins[i].getRightCol();
			if (leftColDef.getTableDeclaration() == null)
				throw new NotProperlyDefinedColumnException(leftColDef);
			if (rightColDef.getTableDeclaration() == null)
				throw new NotProperlyDefinedColumnException(rightColDef);
			sql.append(" INNER JOIN ")
					.append(rightColDef.getTableDeclaration()).append(" ON ")
					.append(leftColDef.getQualifiedName()).append("=")
					.append(rightColDef.getQualifiedName()).toString();
		}
		return sql.toString();
	}

	public static String generateInnerJoinChainedCondition(
			JoinDefinition[] joins) {
		StringBuffer sql = new StringBuffer();
		DbColumnDef leftColDef, rightColDef;
		for (int i = 0; i < joins.length; i++) {
			leftColDef = joins[i].getLeftCol();
			rightColDef = joins[i].getRightCol();
			if (leftColDef.getTableDeclaration() == null)
				throw new NotProperlyDefinedColumnException(leftColDef);
			if (rightColDef.getTableDeclaration() == null)
				throw new NotProperlyDefinedColumnException(rightColDef);
			sql.append(" INNER JOIN ")
					.append(rightColDef.getTableDeclaration()).append(" ON ")
					.append(leftColDef.getQualifiedName()).append("=")
					.append(rightColDef.getQualifiedName()).toString();
		}
		return sql.toString();
	}

	public static String generateLeftOuterJoinCondition(TableDef tableDef,
			JoinDefinition[] joins) {
		StringBuffer sql = new StringBuffer(tableDef.getDeclaration());
		DbColumnDef leftColDef, rightColDef;
		String sqlAppend = null;
		for (int i = 0; i < joins.length; i++) {
			leftColDef = joins[i].getLeftCol();
			rightColDef = joins[i].getRightCol();
			sqlAppend = joins[i].getSqlAppend();
			if (leftColDef.getTableDeclaration() == null)
				throw new NotProperlyDefinedColumnException(leftColDef);
			if (rightColDef.getTableDeclaration() == null)
				throw new NotProperlyDefinedColumnException(rightColDef);
			sql.append(" LEFT OUTER JOIN ")
					.append(rightColDef.getTableDeclaration()).append(" ON ")
					.append(leftColDef.getQualifiedName()).append("=")
					.append(rightColDef.getQualifiedName()).toString();
			if (sqlAppend != null) {
				sql.append(sqlAppend);
			}
		}
		return sql.toString();
	}

	public static String generateLeftOuterJoinChainedCondition(
			JoinDefinition[] joins) {
		StringBuffer sql = new StringBuffer();
		DbColumnDef leftColDef, rightColDef;
		String sqlAppend = null;
		for (int i = 0; i < joins.length; i++) {
			leftColDef = joins[i].getLeftCol();
			rightColDef = joins[i].getRightCol();
			sqlAppend = joins[i].getSqlAppend();
			if (leftColDef.getTableDeclaration() == null)
				throw new NotProperlyDefinedColumnException(leftColDef);
			if (rightColDef.getTableDeclaration() == null)
				throw new NotProperlyDefinedColumnException(rightColDef);
			sql.append(" LEFT OUTER JOIN ")
					.append(rightColDef.getTableDeclaration()).append(" ON ")
					.append(leftColDef.getQualifiedName()).append("=")
					.append(rightColDef.getQualifiedName()).toString();
			if (sqlAppend != null) {
				sql.append(sqlAppend);
			}
		}
		return sql.toString();
	}

	/*
	 * public static String generateOuterJoinCondition(String table1,
	 * DbColumnDef colDef1, String table2, DbColumnDef colDef2) {
	 *
	 * return new
	 * StringBuffer(getQualifiedColumnName(table1,colDef1)).append("(+)=")
	 * .append(getQualifiedColumnName(table2, colDef2)).toString();
	 *
	 * }
	 */

	public static String generateTableSet(String[] tables) {
		if (tables == null || tables.length == 0)
			return "";
		StringBuffer buff = new StringBuffer();
		buff.append(" ");
		buff.append(tables[0]);
		for (int i = 1; i < tables.length; i++)
			buff.append(",").append(tables[i]);
		buff.append(" ");
		return buff.toString();
	}

	// public static String generateTokenField(DbColumnDef colDef, int operator,
	// String valueField, boolean ignoredCase) {
	// return generateTokenFieldQualified(colDef, valueField, null,
	// operator,ignoredCase);
	// }

	public static String generateTokenField(DbColumnDef colDef, int operator,
			String valueField) {
		return generateTokenFieldQualified(colDef, valueField, null, operator);
	}

	public static String generateTokenFieldQualified(DbColumnDef colDef,
			int valueField, String TABLE_NAME, int operator) {
		return generateTokenFieldQualified(colDef,
				Integer.toString(valueField), TABLE_NAME, operator);
	}

	// public static String generateTokenFieldQualified(DbColumnDef colDef,
	// String valueField, String TABLE_NAME, int operator) {
	// return generateTokenFieldQualified(colDef, valueField, TABLE_NAME,
	// operator, false);
	// }

	public static String generateTokenFieldQualified(DbColumnDef colDef,
			String valueField, String TABLE_NAME, int operator) {
		// String startStrIgnoredCase = " ", endStrIgnoredCase = " ";
		// if (ignoredCase){
		// startStrIgnoredCase = " UPPER( ";
		// endStrIgnoredCase = " ) ";
		// }

		String nameField = (TABLE_NAME != null && TABLE_NAME.length() > 0) ? TABLE_NAME
				+ "." + colDef.getName()
				: colDef.getQualifiedName();
		StringBuffer buf = new StringBuffer(" ");
		if (valueField != null) {
			if (isNumber(colDef))
				buf.append(nameField).append(" ")
						.append(ConstraintType.getSymbol(operator)).append(" ")
						.append(valueField).append(" ");
			else if (isDate(colDef))
				buf.append(nameField).append(" ")
						.append(ConstraintType.getSymbol(operator))
						.append(" '").append(valueField).append("' ");
			else {
				if (operator == ConstraintType.LIKE)
					return generateLikeTokenField(colDef, valueField);
				else if (operator == ConstraintType.CONTAINS)
					buf.append(ConstraintType.getSymbol(operator)).append("(")
							.append(nameField).append(",").append(valueField)
							.append(")>0");
				else {
					// buf.append(startStrIgnoredCase)
					// .append(nameField)
					// .append(endStrIgnoredCase)
					// .append(" ");
					//
					// if (ignoredCase)
					// valueField = valueField.toUpperCase();
					//
					// if (operator == ConstraintType.LIKE)
					// valueField = new
					// StringBuffer(valueField).insert(0,"%").append("%").toString();

					// buf.append(" ").append(nameField).append(" ").append(ConstraintType.getSymbol(operator)).append(" '").append(valueField).append("' ");
					buf.append(nameField)
							.append(ConstraintType.getSymbol(operator))
							.append(" '").append(valueField).append("' ");
				}
			}
			/*
			 * if (isNumber) return " " + nameField + "=" + valueField + " ";
			 * else if (isDate) return " " + nameField + "='" + valueField +
			 * "' "; else return " " + nameField + "='" + valueField + "' ";
			 */
		} else
			switch (operator) {
			case ConstraintType.EQUAL:
				buf.append(nameField).append(" IS NULL");
				break;
			case ConstraintType.DISTINCT:
				buf.append(nameField).append(" IS NOT NULL");
				break;
			default:
				buf.append(nameField)
						.append(ConstraintType.getSymbol(operator))
						.append("NULL");
				break;
			}
		return buf.toString();
	}

	public static String generateTokenFieldQualified(DbColumnDef colDef,
			String valueField, String TABLE_NAME, int operator,
			boolean ignoredCase) {
		String startStrIgnoredCase = " ", endStrIgnoredCase = " ";
		if (ignoredCase) {
			startStrIgnoredCase = " UPPER( ";
			endStrIgnoredCase = " ) ";
		}

		String nameField = (TABLE_NAME != null && TABLE_NAME.length() > 0) ? TABLE_NAME
				+ "." + colDef.getName()
				: colDef.getQualifiedName();
		StringBuffer buf = new StringBuffer(" ");
		if (valueField != null) {
			if (isNumber(colDef))
				buf.append(nameField).append(" ")
						.append(ConstraintType.getSymbol(operator)).append(" ")
						.append(valueField).append(" ");
			else if (isDate(colDef))
				buf.append(nameField).append(" ")
						.append(ConstraintType.getSymbol(operator))
						.append(" '").append(valueField).append("' ");
			else {
				if (operator == ConstraintType.LIKE)
					return generateLikeTokenField(colDef, valueField);
				else if (operator == ConstraintType.CONTAINS)
					buf.append(ConstraintType.getSymbol(operator)).append("(")
							.append(nameField).append(",").append(valueField)
							.append(")>0");
				else {
					buf.append(startStrIgnoredCase).append(nameField)
							.append(endStrIgnoredCase).append(" ");

					if (ignoredCase)
						valueField = valueField.toUpperCase();

					if (operator == ConstraintType.LIKE)
						valueField = new StringBuffer(valueField)
								.insert(0, "%").append("%").toString();

					buf.append(" ").append(ConstraintType.getSymbol(operator))
							.append(" '").append(valueField).append("' ");
				}
			}
		} else
			switch (operator) {
			case ConstraintType.EQUAL:
				buf.append(nameField).append(" IS NULL");
				break;
			case ConstraintType.DISTINCT:
				buf.append(nameField).append(" IS NOT NULL");
				break;
			default:
				buf.append(nameField)
						.append(ConstraintType.getSymbol(operator))
						.append("NULL");
				break;
			}
		return buf.toString();
	}

	public static String generateEQTokenFunction(DbColumnDef colDef,
			String valueField) {
		String nameField = colDef.getQualifiedName();
		StringBuffer buf = new StringBuffer(" ");
		if (valueField != null) {

			buf.append(nameField).append(ConstraintType.getSymbol(0))
					.append(valueField);
		} else
			buf.append(nameField).append(" IS NULL");
		return buf.toString();
	}

	// private static void logParameters(StringBuffer buffer, Object[] args){
	// if ((args!=null)&&(args.length>0)){
	// buffer.append("(");
	// for (int i=0;i<args.length;i++){
	// if (args[i]!=null){
	// if (i>0){
	// buffer.append(",");
	// }
	// if (args[i] instanceof Object []){
	// logParameters(buffer,(Object[])args[i]);
	// } else if (args[i] instanceof Collection){
	// logParameters(buffer,((Collection)args[i]).toArray());
	// } else {
	// buffer.append(args[i]);
	// }
	// }
	// }
	// buffer.append(")");
	// }
	// }

	public static void fillVoWithDbOutputStament(
			DbColumnDef[] COLUMN_DEFINITIONS, DbOutputStatement stmt,
			Object valueObject) throws Exception {
		String propertyName = null;
		int columnType;
		DbColumnDef colDef = null;
		StringBuffer setterNameBuff = new StringBuffer();

		// if (PROFILE_LOGGER.isEnabledFor(ProfileLogLevel.POPULATE_VO)) {
		// StringBuffer logMessage = new StringBuffer()
		// .append(System.currentTimeMillis())
		// .append(" [").append(valueObject.getClass().getName())
		// .append("::").append("Numero parametros:").append(COLUMN_DEFINITIONS.length)
		// .append("::").append("Parametros:");
		//
		// logParameters(logMessage, COLUMN_DEFINITIONS);
		//
		// logMessage.append("::").append("Numero parametros resultado:").append(stmt.getColumnCount())
		// .append("::").append("Parametros resultado:");
		//
		// logParameters(logMessage, stmt.getMetadataColumns());
		//
		// logMessage.append("]");
		//
		// PROFILE_LOGGER.log(ProfileLogLevel.POPULATE_VO,
		// logMessage.toString());
		// }

		for (int i = 0; i < COLUMN_DEFINITIONS.length; i++) {
			// busqueda del metodo del vo => si no esta mapeado en el col def
			// buscara un metodo que se llame igual q la columna de la la tabla
			colDef = COLUMN_DEFINITIONS[i];

			propertyName = colDef.getBindPropertyVO();
			if (propertyName == null)
				propertyName = colDef.getName();
			setterNameBuff.append("set").append(propertyName);
			columnType = colDef.getDataType();
			Method setterMethod = null;
			Object[] paramValue = new Object[1];

			if (columnType == DbDataType.SHORT_TEXT) {
				setterMethod = getMethod(valueObject.getClass(),
						setterNameBuff.toString(), String.class);
				paramValue[0] = stmt.getShortText(i + 1);
			} else if (columnType == DbDataType.LONG_TEXT) {
				setterMethod = getMethod(valueObject.getClass(),
						setterNameBuff.toString(), String.class);
				paramValue[0] = stmt.getLongText(i + 1);
			} else if (columnType == DbDataType.LONG_INTEGER
					|| columnType == DbDataType.SHORT_INTEGER) {
				try {
					setterMethod = getMethod(valueObject.getClass(),
							setterNameBuff.toString(), int.class);
				} catch (Exception e) {
					setterMethod = getMethod(valueObject.getClass(),
							setterNameBuff.toString(), Integer.class);
				}
				paramValue[0] = new Integer(stmt.getLongInteger(i + 1));
			} else if (columnType == DbDataType.LONG_DECIMAL
					|| columnType == DbDataType.SHORT_DECIMAL) {
				try {
					setterMethod = getMethod(valueObject.getClass(),
							setterNameBuff.toString(), double.class);
				} catch (Exception e) {
					setterMethod = getMethod(valueObject.getClass(),
							setterNameBuff.toString(), Double.class);
				}
				paramValue[0] = new Double(stmt.getLongDecimal(i + 1));
			} else if (columnType == DbDataType.DATE_TIME) {
				setterMethod = getMethod(valueObject.getClass(),
						setterNameBuff.toString(), Date.class);
				paramValue[0] = stmt.getDateTime(i + 1);
			}
			if (paramValue != null) {
				setterMethod.invoke(valueObject, paramValue);
			}
			setterNameBuff.setLength(0);
		}

	}

	/*
	 * private static String findMethodName(String nameToFind, Class
	 * objectClass) throws Exception{ // Method[] nameMethods =
	 * objectClass.getMethods(); Method[] nameMethods =
	 * objectClass.getDeclaredMethods(); for (int i = 0; i < nameMethods.length;
	 * i++) { if (nameMethods[i].getName().equalsIgnoreCase(nameToFind)) return
	 * nameMethods[i].getName(); } nameMethods = objectClass.getMethods(); for
	 * (int i = 0; i < nameMethods.length; i++) { if
	 * (nameMethods[i].getName().equalsIgnoreCase(nameToFind)) return
	 * nameMethods[i].getName(); } throw new Exception("El metodo "+nameToFind+
	 * " no ha sido encontrado en el objeto " + objectClass.toString()); }
	 */

	// private static List getValidTypes(int columnType) {
	// Class aValidType = null;
	// List validColumnTypes = new ArrayList();
	// if (columnType == DbDataType.SHORT_TEXT || columnType ==
	// DbDataType.LONG_TEXT) {
	// validColumnTypes.add(String.class);
	// } else if (columnType == DbDataType.LONG_INTEGER || columnType ==
	// DbDataType.SHORT_INTEGER) {
	// validColumnTypes.add(int.class);
	// validColumnTypes.add(Integer.class);
	// } else if (columnType == DbDataType.LONG_DECIMAL || columnType ==
	// DbDataType.SHORT_DECIMAL) {
	// validColumnTypes.add(double.class);
	// validColumnTypes.add(Double.class);
	// } else if (columnType == DbDataType.DATE_TIME)
	// validColumnTypes.add(Date.class);
	// return validColumnTypes;
	// }

	private static Method getMethod(Class objectClass, String propertyToSet,
			Class paramType) throws Exception {
		String methodName = null;
		Method[] nameMethods = objectClass.getDeclaredMethods();
		for (int i = 0; i < nameMethods.length; i++) {
			if (nameMethods[i].getName().equalsIgnoreCase(propertyToSet))
				methodName = nameMethods[i].getName();
		}
		nameMethods = objectClass.getMethods();
		for (int i = 0; i < nameMethods.length; i++) {
			if (nameMethods[i].getName().equalsIgnoreCase(propertyToSet))
				methodName = nameMethods[i].getName();
		}
		if (methodName == null)
			throw new Exception("La propiedad " + propertyToSet
					+ " no tiene ningún método 'set' en la clase "
					+ objectClass.toString());

		Method method = null;
		if (paramType != null) {
			Class[] paramTypesArray = null;
			do {
				paramTypesArray = new Class[] { paramType };
				try {
					method = objectClass.getMethod(methodName, paramTypesArray);
				} catch (NoSuchMethodException nse) {
					paramType = paramType.getSuperclass();
				}
			} while (method == null && paramType != null);
			if (method == null)
				throw new Exception("La propiedad " + propertyToSet
						+ " no tiene metodo 'set' en la clase "
						+ objectClass.toString() + " cont tipo "
						+ paramType.getName());
		} else
			method = objectClass.getMethod(methodName, null);
		return method;
	}

	public static String generateORTokens(DbColumnDef colDef, int[] valuesFields) {
		String[] sentences = new String[valuesFields.length];

		for (int i = 0; i < valuesFields.length; i++) {
			sentences[i] = generateEQTokenField(colDef,
					Integer.toString(valuesFields[i]));
		}

		return generateORTokens(sentences);
	}

	public static String generateORTokens(DbColumnDef colDef,
			Integer[] valuesFields) {
		String[] sentences = new String[valuesFields.length];

		for (int i = 0; i < valuesFields.length; i++) {
			sentences[i] = generateEQTokenField(colDef,
					valuesFields[i].toString());
		}

		return generateORTokens(sentences);
	}

	public static String generateORTokensQualified(String TABLE_NAME,
			DbColumnDef colDef, int[] valuesFields) {
		String[] sentences = new String[valuesFields.length];

		for (int i = 0; i < valuesFields.length; i++) {
			sentences[i] = generateTokenFieldQualified(colDef,
					Integer.toString(valuesFields[i]), TABLE_NAME,
					ConstraintType.EQUAL);
		}

		return generateORTokens(sentences);
	}

	public static String generateORTokensQualified(String TABLE_NAME,
			DbColumnDef colDef, String[] valuesFields) {
		String[] sentences = new String[valuesFields.length];

		for (int i = 0; i < valuesFields.length; i++) {
			sentences[i] = generateTokenFieldQualified(colDef, valuesFields[i],
					TABLE_NAME, ConstraintType.EQUAL);
		}

		return generateORTokens(sentences);
	}

	public static String generateORTokens(DbColumnDef colDef,
			Object[] valuesFields) {

		String[] sentences = new String[valuesFields.length];

		for (int i = 0; i < valuesFields.length; i++) {
			sentences[i] = generateEQTokenField(colDef,
					valuesFields[i].toString());
		}
		return generateORTokens(sentences);

	}

	/**
	 * Genera una sentencia como esta (campo1=valor1 AND campo2=valor2) OR
	 * (campo1=valor3 and campo2=valor4)
	 *
	 * @param colDef
	 *            Columna de la primera igualdad
	 * @param valuesFields
	 *            Valores de la primera igualdad
	 * @param secondColDef
	 *            Columna de la segunda igualdad
	 * @param secondValuesFields
	 *            Valores de la segunda igualdad
	 * @return Sentencia de ors y ands
	 */
	public static String generateORTokens(DbColumnDef colDef,
			Object[] valuesFields, DbColumnDef secondColDef,
			Object[] secondValuesFields) {

		String[] sentences = new String[valuesFields.length];

		for (int i = 0; i < valuesFields.length; i++) {
			StringBuffer sentencia = new StringBuffer("(")
					.append(generateEQTokenField(colDef,
							valuesFields[i].toString()))
					.append(DBUtils.AND)
					.append(generateEQTokenField(secondColDef,
							secondValuesFields[i].toString())).append(")");

			sentences[i] = sentencia.toString();
		}
		return generateORTokens(sentences);

	}

	public static String generateORTokens(String[] sentences) {
		return generateLogicToken(sentences, OR);
	}

	public static String generateUNIONTokens(String[] sentences) {
		return generateLogicToken(sentences, UNION);
	}

	public static String generateANDTokens(String[] sentences) {
		return generateLogicToken(sentences, AND);
	}

	private static String generateLogicToken(String[] str, String logicToken) {
		StringBuffer buffer = new StringBuffer();
		if (str.length > 0)
			buffer.append(" (");
		for (int i = 0; i < str.length; i++) {
			if (i > 0)
				buffer.append(logicToken).append(" ");
			buffer.append(str[i]);
		}
		if (str.length > 0)
			buffer.append(" )");

		return buffer.toString();
	}

	// public final static Date getFechaActual() {
	// return DbUtil.getCurrentDateTime();
	// }

	// public final static int getAnoActual() {
	// return Calendar.getInstance().get(Calendar.YEAR);
	// }
	//
	// public final static int getDiaActual() {
	// return Calendar.getInstance().get(Calendar.DATE);
	// }
	//
	// public final static int getMesActual() {
	// return Calendar.getInstance().get(Calendar.MONTH+1);
	// }

	public static String getQualifiedColumnName(String tableName,
			DbColumnDef colDef) {
		return getQualifiedColumnNames(tableName, new DbColumnDef[] { colDef });
	}

	public static String getQualifiedColumnNames(String tableName,
			DbColumnDef[] colDefs) {

		StringBuffer tbdr;
		int count, i;

		tbdr = new StringBuffer();

		count = colDefs.length;

		if (StringUtils.isNotBlank(tableName))
			tbdr.append(tableName).append(".");

		tbdr.append(colDefs[0].getName());

		for (i = 1; i < count; i++) {
			tbdr.append(",");

			if (StringUtils.isNotBlank(tableName))
				tbdr.append(tableName).append(".");

			tbdr.append(colDefs[i].getName());
		}

		return tbdr.toString();
	}

	public static String getQualifiedColumnNames(DbColumnDef[] colDefs) {
		StringBuffer cols = new StringBuffer();
		for (int i = 0; i < colDefs.length; i++) {
			cols.append(colDefs[i].getQualifiedName());
			if (i + 1 < colDefs.length)
				cols.append(",");
		}
		return cols.toString();
	}

	public static void fillDbInputStmt(DbColumnDef[] COLUMN_DEFINITIONS,
			DbInputStatement stmt, Object valueObject) throws Exception {
		String propertyName = null;
		Method getter = null;
		int columnType;
		DbColumnDef colDef = null;
		StringBuffer getterNameBuff = new StringBuffer();
		for (int i = 0; i < COLUMN_DEFINITIONS.length; i++) {
			colDef = COLUMN_DEFINITIONS[i];
			propertyName = colDef.getBindPropertyVO();
			if (propertyName == null)
				propertyName = colDef.getName();
			getterNameBuff.append("get").append(propertyName);
			columnType = colDef.getDataType();
			getter = getMethod(valueObject.getClass(),
					getterNameBuff.toString(), null);

			if (columnType == DbDataType.SHORT_TEXT) {
				String shortText = (String) getter.invoke(valueObject, null);
				if (shortText == null || shortText.equals("")) {
					shortText = IeciTdType.NULL_SHORT_TEXT;
				}

				stmt.setShortText(i + 1, shortText);
			} else if (columnType == DbDataType.LONG_TEXT)
				stmt.setLongText(i + 1,
						(String) getter.invoke(valueObject, null));
			else if (columnType == DbDataType.LONG_INTEGER
					|| columnType == DbDataType.SHORT_INTEGER) {
				Object value = getter.invoke(valueObject, null);

				int intValue = DbDataType.NULL_LONG_INTEGER;
				if (value != null) {
					if (value instanceof Integer)
						intValue = ((Integer) value).intValue();
					else if (value instanceof Long)
						intValue = ((Long) value).intValue();
				}

				stmt.setLongInteger(i + 1, intValue);

			} else if (columnType == DbDataType.LONG) {
				Long value = (Long) getter.invoke(valueObject, null);
				long longValue = DbDataType.NULL_LONG;
				if (value != null)
					longValue = value.longValue();

				stmt.setLong(i + 1, longValue);

			} else if (columnType == DbDataType.LONG_DECIMAL
					|| columnType == DbDataType.SHORT_DECIMAL) {

				Double value = (Double) getter.invoke(valueObject, null);
				double dblValue = DbDataType.NULL_LONG_DECIMAL;
				if (value != null)
					dblValue = value.doubleValue();
				stmt.setLongDecimal(i + 1, dblValue);
			} else if (COLUMN_DEFINITIONS[i].getDataType() == DbDataType.DATE_TIME)
				stmt.setDateTime(i + 1, (Date) getter.invoke(valueObject, null));
			getterNameBuff.setLength(0);
		}
	}

	public static Date getFechaActual() {

		Calendar calendar = Calendar.getInstance();

		calendar.set(Calendar.DAY_OF_WEEK_IN_MONTH,
				calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH));
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
		calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();

	}

	public static Date getFechaHoraActual() {

		Calendar calendar = Calendar.getInstance();

		calendar.set(Calendar.DAY_OF_WEEK_IN_MONTH,
				calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH));
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
		calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
		calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND, calendar.get(Calendar.MILLISECOND));

		return calendar.getTime();

	}

	/**
	 * Obtiene una representación de un booleano en formato carácter.
	 *
	 * @param bool
	 *            Booleano
	 * @return Carácter.
	 */
	public static String getStringValue(boolean bool) {
		return (bool ? Constants.TRUE_STRING : Constants.FALSE_STRING);
	}

	public static boolean getBooleanValue(String str) {
		boolean value = false;
		if (str != null && str.equalsIgnoreCase(Constants.TRUE_STRING))
			value = true;
		return value;
	}

	/*
	 * public static String booleanToString(boolean value) { return value ?
	 * DBUtils.TRUE_STRING : DBUtils.FALSE_STRING;
	 *
	 * }
	 */

	public static MapColumnsNamesIndex getMapIndexColumnsNames(
			String columnsNames) throws Exception {
		MapColumnsNamesIndex ret = new MapColumnsNamesIndex();
		String[] columns = columnsNames.split(",");
		for (int i = 0; i < columns.length; i++) {
			ret.put(columns[i].trim());

		}
		return ret;
	}

	/**
	 * Indica si la columna es numérica.
	 *
	 * @param colDef
	 *            Columna.
	 * @return true si la columna es numérica.
	 */
	public static boolean isNumber(DbColumnDef colDef) {
		int dataType = colDef.getDataType();
		return ((dataType == DbDataType.LONG_DECIMAL)
				|| (dataType == DbDataType.LONG_INTEGER)
				|| (dataType == DbDataType.SHORT_DECIMAL) || (dataType == DbDataType.SHORT_INTEGER));
	}

	/**
	 * Indica si la columna es una fecha.
	 *
	 * @param colDef
	 *            Columna.
	 * @return true si la columna es una fecha.
	 */
	public static boolean isDate(DbColumnDef colDef) {
		int dataType = colDef.getDataType();
		return (dataType == DbDataType.DATE_TIME);
	}

	// tablas debe venir relleno al menos con una
	public static void addPermissionsCheckingClausesWithPermissions(
			GenericUserInfo serviceClient, StringBuffer tablas,
			StringBuffer clausulaWhere, DbColumnDef nivelAccesoField,
			DbColumnDef archivoField, DbColumnDef listaAccesoField,
			Object[] listaPermisos) {
		if (!serviceClient
				.hasPermission(AppPermissions.ADMINISTRACION_TOTAL_SISTEMA)
				&& !serviceClient
						.hasPermission(AppPermissions.CONSULTA_TOTAL_SISTEMA)) {
			clausulaWhere
					.append(getCondition(clausulaWhere.toString()))
					.append("(")
					.append(DBUtils.generateEQTokenField(nivelAccesoField,
							NivelAcceso.PUBLICO));

			if ((archivoField != null)
					&& (serviceClient.getCustodyArchiveList().size() > 0)) {
				clausulaWhere
						.append(" OR (")
						.append(DBUtils.generateEQTokenField(nivelAccesoField,
								NivelAcceso.ARCHIVO))
						.append(" AND ")
						.append(DBUtils.generateInTokenField(archivoField,
								serviceClient.getCustodyArchiveList()))
						.append(")");
			}

			if ((listaAccesoField != null)
					&& !serviceClient.getAcls().isEmpty()) {
				// conseguir el nombre de la tabla más alias, entre comas
				// añadir en esos lugares el left join
				int posTablaElemento = tablas.indexOf(listaAccesoField
						.getTableName());
				// int
				// posInicioComa=tablas.substring(0,posTablaElemento).lastIndexOf(",")+1;
				// //la primera se conserva
				int posFinComa = tablas.indexOf(",", posTablaElemento); // se
																		// elimina
																		// la
																		// ultima
				if (posFinComa == -1) // la tabla esta al final
					posFinComa = tablas.length();

				// tablas.delete(posInicioComa,posFinComa);
				// posInicioComa
				// tablas.insert(posFinComa,generateLeftOuterJoinCondition(new
				// TableDef(""),
				// new JoinDefinition[]{new
				// JoinDefinition(listaAccesoField,PermisosListaDbEntityImpl.CAMPO_IDLISTCA)}));

				// clausulaWhere.insert(6,new StringBuffer("").append(
				// generateJoinCondition(listaAccesoField,
				// PermisosListaDbEntityImpl.CAMPO_IDLISTCA))
				// .append(" AND "));

				StringBuffer subQuery = new StringBuffer(DBUtils.SELECT)
						.append(PermisosListaDbEntityImpl.CAMPO_IDLISTCA)
						.append(DBUtils.FROM)
						.append(PermisosListaDbEntityImpl.TABLE_NAME_PERMISOS_LISTA_CONTROL)
						.append(DBUtils.WHERE)
						.append(DBUtils.generateInTokenField(
								PermisosListaDbEntityImpl.CAMPO_PERM,
								CollectionUtils.createList(listaPermisos)))
						.append(DBUtils.AND)
						.append(generateJoinCondition(listaAccesoField,
								PermisosListaDbEntityImpl.CAMPO_IDLISTCA));

				clausulaWhere
						.append(" OR ((")
						.append(DBUtils.generateEQTokenField(nivelAccesoField,
								NivelAcceso.ARCHIVO))
						.append(" OR ")
						.append(DBUtils.generateEQTokenField(nivelAccesoField,
								NivelAcceso.RESTRINGIDO))
						.append(") AND ")
						.append(DBUtils.generateInTokenField(listaAccesoField,
								serviceClient.getAcls().keySet()))
						.append(" AND")
						.append(DBUtils.generateInTokenFieldSubQuery(
								listaAccesoField, subQuery.toString()))
						.append(")");

			}

			clausulaWhere.append(")");
		}
	}

	public static void addPermissionsCheckingClauses(
			GenericUserInfo serviceClient, StringBuffer clausulaWhere,
			DbColumnDef nivelAccesoField, DbColumnDef archivoField,
			DbColumnDef listaAccesoField) {
		if (!serviceClient
				.hasPermission(AppPermissions.ADMINISTRACION_TOTAL_SISTEMA)
				&& !serviceClient
						.hasPermission(AppPermissions.CONSULTA_TOTAL_SISTEMA)) {
			clausulaWhere
					.append(getCondition(clausulaWhere.toString()))
					.append("(")
					.append(DBUtils.generateEQTokenField(nivelAccesoField,
							NivelAcceso.PUBLICO));

			if ((archivoField != null)
					&& (serviceClient.getCustodyArchiveList().size() > 0)) {
				clausulaWhere
						.append(" OR (")
						.append(DBUtils.generateEQTokenField(nivelAccesoField,
								NivelAcceso.ARCHIVO))
						.append(" AND ")
						.append(DBUtils.generateInTokenField(archivoField,
								serviceClient.getCustodyArchiveList()))
						.append(")");
			}

			if ((listaAccesoField != null)
					&& !serviceClient.getAcls().isEmpty()) {
				clausulaWhere
						.append(" OR ((")
						.append(DBUtils.generateEQTokenField(nivelAccesoField,
								NivelAcceso.ARCHIVO))
						.append(" OR ")
						.append(DBUtils.generateEQTokenField(nivelAccesoField,
								NivelAcceso.RESTRINGIDO))
						.append(") AND ")
						.append(DBUtils.generateInTokenField(listaAccesoField,
								serviceClient.getAcls().keySet())).append(")");
			}
			clausulaWhere.append(")");
		}
	}

	public static void addPermissionsCheckingClausesArchiveUser(
			GenericUserInfo serviceClient, StringBuffer clausulaWhere,
			DbColumnDef nivelAccesoField, DbColumnDef listaAccesoField) {
		if (!serviceClient
				.hasPermission(AppPermissions.ADMINISTRACION_TOTAL_SISTEMA)
				&& !serviceClient
						.hasPermission(AppPermissions.CONSULTA_TOTAL_SISTEMA)) {
			clausulaWhere
					.append(getCondition(clausulaWhere.toString()))
					.append("(")
					.append(DBUtils.generateEQTokenField(nivelAccesoField,
							NivelAcceso.PUBLICO));

			// Si el usuario es un usuario de archivo
			if (serviceClient.getCustodyArchiveList().size() > 0) {
				clausulaWhere
						.append(" OR (")
						.append(DBUtils.generateEQTokenField(nivelAccesoField,
								NivelAcceso.ARCHIVO)).append(")");
			}

			if ((listaAccesoField != null)
					&& !serviceClient.getAcls().isEmpty()) {
				clausulaWhere
						.append(" OR ((")
						.append(DBUtils.generateEQTokenField(nivelAccesoField,
								NivelAcceso.ARCHIVO))
						.append(" OR ")
						.append(DBUtils.generateEQTokenField(nivelAccesoField,
								NivelAcceso.RESTRINGIDO))
						.append(") AND ")
						.append(DBUtils.generateInTokenField(listaAccesoField,
								serviceClient.getAcls().keySet())).append(")");
			}

			clausulaWhere.append(")");
		}
	}

	public static String getNativeSysDateSyntax(DbConnection conn) {
		try {
			return DbUtil.getNativeDateTimeSyntax(conn, new Date(), false);
		} catch (Exception e) {
			throw new ArchivoModelException(e, DBUtils.class.getName(),
					e.getMessage());
		}
	}

	public static String getNativeGreatestSyntax(DbConnection conn,
			String nameColumn1, String nameColumn2) {
		try {
			return DbUtil.getNativeGreatestSyntax(conn, nameColumn1,
					nameColumn2);
		} catch (Exception e) {
			throw new ArchivoModelException(e, DBUtils.class.getName(),
					e.getMessage());
		}
	}

	public static String getNativeAddYearSyntax(DbConnection conn,
			String nameColumDate, String incremento) {
		try {
			return DbUtil.getNativeAddYearSyntax(conn, nameColumDate,
					incremento);
		} catch (Exception e) {
			throw new ArchivoModelException(e, DBUtils.class.getName(),
					e.getMessage());
		}
	}

	public static String getNativeStrSyntax(DbConnection conn, String nameColumn) {
		try {
			return DbUtil.getNativeStrSyntax(conn, nameColumn);
		} catch (Exception e) {
			throw new ArchivoModelException(e, DBUtils.class.getName(),
					e.getMessage());
		}
	}

	public static String getNativeStrPosSyntax(DbConnection conn,
			DbColumnDef colDef, String value) {
		try {
			return DbUtil.getNativeStrPosSyntax(conn, colDef, value);
		} catch (Exception e) {
			throw new ArchivoModelException(e, DBUtils.class.getName(),
					e.getMessage());
		}
	}

	public static String getNativeStrPosSyntax(DbConnection conn,
			DbColumnDef colDef, DbColumnDef value) {
		try {
			return DbUtil.getNativeStrPosSyntax(conn, colDef, value);
		} catch (Exception e) {
			throw new ArchivoModelException(e, DBUtils.class.getName(),
					e.getMessage());
		}
	}

	public static String getNativeStrPosDB2SpecialSyntax(DbConnection conn,
			DbColumnDef colDef, DbColumnDef value) {
		try {
			return DbUtil.getNativeStrPosSyntax(conn, colDef, value, true);
		} catch (Exception e) {
			throw new ArchivoModelException(e, DBUtils.class.getName(),
					e.getMessage());
		}
	}

	public static String getNativeToCharSyntax(DbConnection conn,
			String nameColumn, String pattern) {
		try {
			return DbUtil.getNativeToCharWithPatternSyntax(conn, nameColumn,
					pattern);
		} catch (Exception e) {
			throw new ArchivoModelException(e, DBUtils.class.getName(),
					e.getMessage());
		}
	}

	public static String getNativeToNumberSyntax(DbConnection conn,
			String nameColumn, int numberOfDigits) {
		try {
			return DbUtil.getNativeToNumberSyntax(conn, nameColumn,
					numberOfDigits);
		} catch (Exception e) {
			throw new ArchivoModelException(e, DBUtils.class.getName(),
					e.getMessage());
		}
	}

	public static String getNativeReplaceSyntax(DbConnection conn,
			DbColumnDef field, String patronAReemplazar, String valorReemplazo) {
		try {
			return DbUtil.getNativeReplaceSyntax(conn, field,
					patronAReemplazar, valorReemplazo);
		} catch (Exception e) {
			throw new ArchivoModelException(e, DBUtils.class.getName(),
					e.getMessage());
		}
	}

	public static String getNativeCalcularFinalCodRefPadreSyntax(
			DbConnection conn, String codReferencia, String codRefFondo,
			String codigo, String finalCodRefPadreActual) {
		try {
			return DbUtil.getNativeCalcularFinalCodReferenciaPadreSyntax(conn,
					codReferencia, codRefFondo, codigo, finalCodRefPadreActual);
		} catch (Exception e) {
			throw new ArchivoModelException(e, DBUtils.class.getName(),
					e.getMessage());
		}
	}

	public static String getNativeConcatSyntax(DbConnection conn) {
		try {
			return DbUtil.getNativeConcatSyntax(conn);
		} catch (Exception e) {
			throw new ArchivoModelException(e, DBUtils.class.getName(),
					e.getMessage());
		}

	}

	public static String getNativeTrimSyntax(DbConnection conn,
			String nameColumn) {
		try {
			return DbUtil.getNativeTrimSyntax(conn, nameColumn);
		} catch (Exception e) {
			throw new ArchivoModelException(e, DBUtils.class.getName(),
					e.getMessage());
		}

	}

	public static String getNativeGetNumericPositiveSyntax(DbConnection conn,
			String value) {
		try {
			return DbUtil.getNativeGetNumericPositiveSyntax(conn, value);
		} catch (Exception e) {
			throw new ArchivoModelException(e, DBUtils.class.getName(),
					e.getMessage());
		}
	}

	public static String getNativeDevolverMarcaSyntax(DbConnection conn,
			String nameColumn, int bit) {
		try {
			return DbUtil.getNativeDevolverMarcaSyntax(conn, nameColumn, bit);
		} catch (Exception e) {
			throw new ArchivoModelException(e, DBUtils.class.getName(),
					e.getMessage());
		}
	}

	public static String getNativeIsNumericSyntax(DbConnection conn,
			String nameColumn) {
		try {
			return DbUtil.getNativeIsNumericSyntax(conn, nameColumn);
		} catch (Exception e) {
			throw new ArchivoModelException(e, DBUtils.class.getName(),
					e.getMessage());
		}
	}

	public static String getNativeLPadSyntax(DbConnection conn,
			String nameColumn, int numChars, String fillText) {
		try {
			return DbUtil.getNativeLPadSyntax(conn, nameColumn, numChars,
					fillText);
		} catch (Exception e) {
			throw new ArchivoModelException(e, DBUtils.class.getName(),
					e.getMessage());
		}
	}

	public static String getNativeDateTimeSyntax(DbConnection conn, Date date,
			boolean onlyDate) {
		try {
			return DbUtil.getNativeDateTimeSyntax(conn, date, onlyDate);
		} catch (Exception e) {
			throw new ArchivoModelException(e, DBUtils.class.getName(),
					e.getMessage());
		}
	}

	// public static String getNativeToDateSyntax(DbConnection conn, Date date,
	// String javaPattern, String bdPattern){
	// try {
	// return DbUtil.getNativeToDateSyntax(conn, date, javaPattern, bdPattern);
	// } catch (Exception e) {
	// throw new ArchivoModelException(e,
	// DBUtils.class.getName(),e.getMessage());
	// }
	// }

	public static String getNativeToDateSyntax(DbConnection conn, Date date,
			SimpleDateFormat dateFormat) {
		try {
			return DbUtil.getNativeToDateSyntax(conn, date, dateFormat);
		} catch (Exception e) {
			throw new ArchivoModelException(e, DBUtils.class.getName(),
					e.getMessage());
		}
	}

	// ELiminada porque no se utiliza en la aplicación, no tenía sentido cuando
	// se llamaba con una columna de tipo fecha, pero
	// si que puede tener sentido cuando se llama para una columna de tipo
	// string
	// public static String getNativeToDateSyntax(DbConnection conn, DbColumnDef
	// date, SimpleDateFormat dateFormat){
	// try {
	// return DbUtil.getNativeToDateSyntax(conn, date, dateFormat);
	// } catch (Exception e) {
	// throw new ArchivoModelException(e,
	// DBUtils.class.getName(),e.getMessage());
	// }
	// }

	// public static String getNativeToDateSyntax(DbConnection conn, String
	// date, SimpleDateFormat dateFormat, boolean dataIsNameColumn){
	// try {
	// return DbUtil.getNativeToDateSyntax(conn, date, dateFormat,
	// dataIsNameColumn);
	// } catch (Exception e) {
	// throw new ArchivoModelException(e,
	// DBUtils.class.getName(),e.getMessage());
	// }
	// }

	public static String getNativeLengthSyntax(DbConnection conn,
			String nameColumn) {
		try {
			return DbUtil.getNativeLengthSyntax(conn, nameColumn);
		} catch (Exception e) {
			throw new ArchivoModelException(e, DBUtils.class.getName(),
					e.getMessage());
		}
	}

	public static String getNativeSubstrSyntax(DbConnection conn,
			String nameColumn, String begin, String end) {
		try {
			return DbUtil.getNativeSubstrSyntax(conn, nameColumn, begin, end);
		} catch (Exception e) {
			throw new ArchivoModelException(e, DBUtils.class.getName(),
					e.getMessage());
		}
	}

	public static String getNativeToDateSyntax(DbConnection conn, String date,
			SimpleDateFormat dateFormat) {
		try {
			return DbUtil.getNativeToDateSyntax(conn, date, dateFormat);
		} catch (Exception e) {
			throw new ArchivoModelException(e, DBUtils.class.getName(),
					e.getMessage());
		}
	}

	public static String getNativeIfNullSintax(DbConnection conn,
			String sqlToCheck, String sqlDefaultValue) {
		try {
			return DbUtil.getNativeIfNullSyntax(conn, sqlToCheck,
					sqlDefaultValue);
		} catch (Exception e) {
			throw new ArchivoModelException(e, DBUtils.class.getName(),
					e.getMessage());
		}
	}

	public static String getNativeIfNullSintaxSUM(DbConnection conn,
			String sqlToCheck, String alias, String sqlDefaultValue) {
		StringBuffer sql = new StringBuffer();
		try {
			sqlToCheck = generateSUM(sqlToCheck);

			sql.append(
					DbUtil.getNativeIfNullSyntax(conn, sqlToCheck,
							sqlDefaultValue)).append(AS).append(alias);
			return sql.toString();
		} catch (Exception e) {
			throw new ArchivoModelException(e, DBUtils.class.getName(),
					e.getMessage());
		}
	}

	/**
	 * Genera la condición IS NULL
	 *
	 * @param conn
	 *            Conexión a la base de datos
	 * @param colDef
	 *            Columna
	 * @return Condición IS NULL de la Columna
	 */
	public static String generateIsNullCondition(DbConnection conn,
			DbColumnDef colDef) {
		String retorno = new StringBuffer(colDef.getQualifiedName()).append(
				IS_NULL).toString();
		return retorno;
	}

	/**
	 * Genera la condición IS NOT NULL
	 *
	 * @param conn
	 *            Conexión a la base de datos
	 * @param colDef
	 *            Columna
	 * @return Condición IS NOT NULL de la Columna
	 */
	public static String generateIsNotNullCondition(DbConnection conn,
			DbColumnDef colDef) {
		String retorno = new StringBuffer(colDef.getQualifiedName()).append(
				IS_NOT_NULL).toString();
		return retorno;
	}

	/**
	 * Genera la condicion distinto de vacio
	 *
	 * @param conn
	 *            Conexión a la base de datos
	 * @param colDef
	 *            Columna
	 * @return Condición !='' de la Columna
	 */
	public static String generateIsNotEmptyValue(DbConnection conn,
			DbColumnDef colDef) {
		String retorno = new StringBuffer(colDef.getQualifiedName()).append(
				" != ''").toString();
		return retorno;
	}

	/**
	 * Obtiene la condición de igualdad como cadena de un campo de tipo fecha
	 *
	 * @param colDef
	 * @param valueField
	 * @return
	 */
	public static String generateTokenFieldDateAnioMesDia(DbConnection conn,
			DbColumnDef colDef, String operador, String valueField) {
		StringBuffer str = new StringBuffer();
		str.append(
				getNativeToCharSyntax(conn, colDef.getQualifiedName(),
						DbUtil.TO_CHAR_PATTERN_AAAAMMDD)).append(operador)
				.append(" '").append(valueField).append("' ");

		return str.toString();
	}

	/**
	 * Obtiene la condición de igualdad como cadena de un campo de tipo fecha
	 *
	 * @param colDef
	 * @param valueField
	 * @return
	 */
	public static String generateTokenFieldDateAnioMesDia(DbConnection conn,
			DbColumnDef colDef, String operador, Date fecha) {
		String valueField = CustomDateFormat.SDF_YYYYMMDD.format(fecha);
		return generateTokenFieldDateAnioMesDia(conn, colDef, operador,
				valueField);
	}

	/**
	 * Obtiene la condición de igualdad como cadena de un campo de tipo fecha
	 *
	 * @param colDef
	 * @param valueField
	 * @return
	 */
	public static String generateBetweenDateAnioMesDia(DbConnection conn,
			DbColumnDef colDef, String valueField, String valueField2,
			String pattern) {
		StringBuffer str = new StringBuffer();
		str.append(
				getNativeToCharSyntax(conn, colDef.getQualifiedName(), pattern))
				.append(BETWEEN).append(" '").append(valueField).append("' ")
				.append(AND).append(" '").append(valueField2).append("' ");
		return str.toString();
	}

	/**
	 * Obtiene la condición de igualdad como cadena de un campo de tipo fecha
	 *
	 * @param colDef
	 * @param valueField
	 * @return
	 */
	public static String generateBetweenDateAnioMesDia(DbConnection conn,
			DbColumnDef colDef, Date fecha1, Date fecha2, String pattern) {

		String valueField = CustomDateFormat.SDF_YYYYMMDD.format(fecha1);
		String valueField2 = CustomDateFormat.SDF_YYYYMMDD.format(fecha2);

		return generateBetweenDateAnioMesDia(conn, colDef, valueField,
				valueField2, pattern);
	}

	/**
	 * Obtiene la condición de rango de 2 números
	 *
	 * @param colDef
	 * @param valueField
	 * @return
	 */
	public static String generateBetweenNumerico(DbColumnDef colDef,
			int valueField, int valueField2) {
		StringBuffer str = new StringBuffer();
		str.append(colDef.getQualifiedName()).append(BETWEEN).append(" ")
				.append(valueField).append(" ").append(AND).append(" ")
				.append(valueField2).append(" ");
		return str.toString();
	}

	/**
	 * Obtiene la condición de rango de 2 cadenas
	 *
	 * @param colDef
	 * @param valueField
	 * @return
	 */
	public static String generateBetweenCadena(DbColumnDef colDef,
			String valueField, String valueField2) {
		StringBuffer str = new StringBuffer();
		str.append(colDef.getQualifiedName()).append(BETWEEN).append("'")
				.append(valueField).append("'").append(AND).append("'")
				.append(valueField2).append("'");
		return str.toString();
	}

	/**
	 * Obtiene la palabra del MINUS
	 *
	 * @param conn
	 *            Conexión
	 * @return Palabra clave del Minus
	 */
	public static String getMinusKey(DbConnection conn) {
		try {
			return DbUtil.getNativeMinusSintax(conn);
		} catch (Exception e) {
			throw new ArchivoModelException(e, DBUtils.class.getName(),
					e.getMessage());
		}
	}

	public static String getUnionDummy(String aliasCampo) {
		String sql = "SELECT '' AS " + aliasCampo + " FROM ASGFNIVELCF";
		return sql;
	}

	public static DbColumnDef getCustomizedField(DbColumnDef column,
			String alias) {
		return new DbColumnDef(column.getBindPropertyVO(), alias,
				column.getName(), column.getDataType(), column.getMaxLen(),
				column.isNullable());
	}

	public static TableDef getCustomizedTable(TableDef tabla, String alias) {
		return new TableDef(tabla.getName(), alias);
	}

	public static String getCountDistinct(DbColumnDef columna) {
		StringBuffer sql = new StringBuffer(COUNT).append(ABRIR_PARENTESIS)
				.append(DISTINCT).append(columna.getQualifiedName())
				.append(CERRAR_PARENTESIS);

		return sql.toString();
	}

	public static String getCount(DbColumnDef columna) {
		StringBuffer sql = new StringBuffer(COUNT).append(ABRIR_PARENTESIS)
				.append(columna.getQualifiedName()).append(CERRAR_PARENTESIS);

		return sql.toString();
	}

	public static String getCountDefault() {
		StringBuffer sql = new StringBuffer(COUNT).append(ABRIR_PARENTESIS)
				.append("1").append(CERRAR_PARENTESIS);

		return sql.toString();
	}

	public static String getCountDistinctAnio(DbConnection conn,
			DbColumnDef columna) throws IeciTdException {
		StringBuffer sql = new StringBuffer(COUNT)
				.append(ABRIR_PARENTESIS)
				.append(DISTINCT)
				.append(DbUtil.getNativeToCharWithPatternSyntax(conn,
						columna.getQualifiedName(), DbUtil.TO_CHAR_PATTERN_YYYY))
				.append(CERRAR_PARENTESIS);

		return sql.toString();
	}
}
