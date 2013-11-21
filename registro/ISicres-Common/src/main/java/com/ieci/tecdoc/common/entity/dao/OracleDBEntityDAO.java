package com.ieci.tecdoc.common.entity.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.utils.BBDDUtils;

/**
 * @author LMVICENTE
 * @creationDate 28-may-2004 10:10:05
 * @version
 * @since
 */
public class OracleDBEntityDAO extends AbstractDBEntityDAO {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	private static final Logger log = Logger.getLogger(OracleDBEntityDAO.class);

	private static final String TO_DATE = "to_date('";

	private static final String FORMAT = ",'DD/MM/YYYY HH24:MI:SS')";

	/***************************************************************************
	 * Constructors
	 **************************************************************************/

	public OracleDBEntityDAO() {
		super();
	}

	/***************************************************************************
	 * Public methods
	 **************************************************************************/

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ieci.tecdoc.common.entity.dao.AbstractDBEntityDAO#getLikeCharacter()
	 */
	public String getLikeCharacter() {
		return "%";
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ieci.tecdoc.common.entity.dao.DBEntityDAO#updateFromTable(String,
	 *      String[], String[], String[], String[], String)
	 */
	public void updateFromTable(String tableName, String oldColumnName[],
			String newColumnName[], String linkedTableName[],
			String linkedColumnName[], String entidad) throws SQLException {
		Statement statement = null;
		Connection connection = null;

		StringBuffer query = new StringBuffer();
		query.append("UPDATE " + tableName + " AUX_TABLE SET ");
		for (int i = 0; i < newColumnName.length; i++) {
			if (i != 0) {
				query.append(",");
			}
			query.append("AUX_TABLE." + newColumnName[i] + "=(SELECT "
					+ linkedColumnName[i] + " FROM " + tableName + ","
					+ linkedTableName[i] + " WHERE " + tableName + "."
					+ oldColumnName[i] + "=" + linkedTableName[i]
					+ ".ID AND AUX_TABLE." + oldColumnName[i] + "=" + tableName
					+ "." + oldColumnName[i] + " GROUP BY "
					+ linkedColumnName[i] + ")");
		}

		try {
			connection = BBDDUtils.getConnection(entidad);
			statement = connection.createStatement();
			statement.executeUpdate(query.toString());
		} catch (SQLException e) {
			log.warn("Error ejecutando [" + query + "]", e);
		} catch (Throwable e) {
			log.warn("Error ejecutando [" + query + "]", e);
		} finally {
			BBDDUtils.close(statement);
			BBDDUtils.close(connection);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ieci.tecdoc.common.entity.dao.DBEntityDAO#alterTableAddColumnString(String,
	 *      String[], String)
	 */
	public void alterTableAddColumnString(String tableName,
			String newColumnName[], String entidad) throws SQLException {
		Statement statement = null;
		Connection connection = null;

		StringBuffer query = new StringBuffer();
		query.append("ALTER TABLE " + tableName + " ADD (");
		for (int i = 0; i < newColumnName.length; i++) {
			if (i != 0) {
				query.append(",");
			}
			query.append(newColumnName[i] + " VARCHAR2(1500)");
		}
		query.append(")");

		try {
			connection = BBDDUtils.getConnection(entidad);
			statement = connection.createStatement();
			statement.executeUpdate(query.toString());
		} catch (SQLException e) {
			log.warn("Error ejecutando [" + query.toString() + "]", e);
		} catch (Throwable e) {
			log.warn("Error ejecutando [" + query.toString() + "]", e);
		} finally {
			BBDDUtils.close(statement);
			BBDDUtils.close(connection);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ieci.tecdoc.common.entity.dao.DBEntityDAO#dropTableOrView(String,
	 *      String)
	 */
	public void dropTableOrView(String tableName, String entidad) {
		Statement statement = null;
		Connection connection = null;
		String version = null;

		try {
			connection = BBDDUtils.getConnection(entidad);
			statement = connection.createStatement();
			statement.executeUpdate("DROP TABLE " + tableName);

			version = getVersion(entidad);
			String aux1 = version.substring(0, version.indexOf("."));
			String aux = version.substring(version.indexOf(".") + 1, version
					.length());

			if (aux1.equals("10")
					&& aux.substring(0, aux.indexOf(".")).equals("1")) {
				purgeTable(tableName, entidad);
			}
		} catch (SQLException e) {
			log.warn("Error ejecutando [DROP TABLE " + tableName + "]."
					+ e.getMessage());
		} catch (Throwable e) {
			log.warn("Error ejecutando [DROP TABLE " + tableName + "]."
					+ e.getMessage());
		} finally {
			BBDDUtils.close(statement);
			BBDDUtils.close(connection);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ieci.tecdoc.common.entity.dao.DBEntityDAO#getSelectAditionFields(int)
	 */
	public String getSelectAditionFields(int bookType) {
		StringBuffer result = new StringBuffer();
		if (bookType == 1) {
			result
					.append(",(SELECT NAME FROM SCR_OFIC B WHERE B.ID=A.FLD5) AS FLD5_TEXT,");
			result
					.append("(SELECT concat(concat(code,'-'),name) FROM SCR_ORGS B WHERE B.ID=A.FLD7) AS FLD7_TEXT,");
			result
					.append("(SELECT concat(concat(code,'-'),name) FROM SCR_ORGS B WHERE B.ID=A.FLD8) AS FLD8_TEXT,");
			result
					.append("(SELECT CODE FROM SCR_ORGS B WHERE B.ID=A.FLD13) AS FLD13_TEXT,");
			result
					.append("(SELECT concat(concat(code,'-'),matter) FROM SCR_CA B WHERE B.ID=A.FLD16) AS FLD16_TEXT");
		} else {
			result
					.append(",(SELECT NAME FROM SCR_OFIC B WHERE B.ID=A.FLD5) AS FLD5_TEXT,");
			result
					.append("(SELECT concat(concat(code,'-'),name) FROM SCR_ORGS B WHERE B.ID=A.FLD7) AS FLD7_TEXT,");
			result
					.append("(SELECT concat(concat(code,'-'),name) FROM SCR_ORGS B WHERE B.ID=A.FLD8) AS FLD8_TEXT,");
			result
					.append("(SELECT concat(concat(code,'-'),matter) FROM SCR_CA B WHERE B.ID=A.FLD12) AS FLD12_TEXT");
		}
		return result.toString();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ieci.tecdoc.common.entity.dao.DBEntityDAO#getReportCreateSelectMultiple(String,
	 *      Integer, String, String, int)
	 */
	public String getReportCreateSelectMultiple(String tableName,
			Integer bookId, String where, String fieldList, int index) {
		String result = null;

		if (index == 0) {
			result = "CREATE TABLE " + tableName + " (ARCHID," + fieldList
					+ ") AS SELECT " + bookId + "," + fieldList + " FROM A"
					+ bookId + "SF " + where;
		} else {
			result = " UNION SELECT " + bookId + "," + fieldList + " FROM A"
					+ bookId + "SF " + where;
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ieci.tecdoc.common.entity.dao.DBEntityDAO#getReportCreateSelectMultiple1(String,
	 *      Integer, String, String, int, String, int, Integer)
	 */
	public String getReportCreateSelectMultiple1(String tableName,
			Integer bookId, String where, String fieldList, int index,
			String aditionalFields, int bookType, Integer maxReportRegister) {
		String result = null;

		if (index == 0) {
			result = "CREATE TABLE " + tableName + " (ARCHID," + fieldList
					+ aditionalFields + ") AS SELECT * FROM (SELECT " + bookId
					+ "," + fieldList + getSelectAditionFields(bookType)
					+ " FROM A" + bookId + "SF A" + where;
		} else {
			result = " UNION SELECT " + bookId + "," + fieldList
					+ getSelectAditionFields(bookType) + " FROM A" + bookId
					+ "SF A" + where;
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ieci.tecdoc.common.entity.dao.DBEntityDAO#getFinalQuery(Integer)
	 */
	public String getFinalQuery(Integer maxReportRegister) {
		return ") where rownum <=" + maxReportRegister;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ieci.tecdoc.common.entity.dao.DBEntityDAO#getReportCreateSelect(String,
	 *      Integer, String, String)
	 */
	public String getReportCreateSelect(String tableName, Integer bookId,
			String where, String fieldList) {
		return "CREATE TABLE " + tableName + " (ARCHID," + fieldList
				+ ") AS SELECT " + bookId + "," + fieldList + " FROM A"
				+ bookId + "SF " + where;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ieci.tecdoc.common.entity.dao.DBEntityDAO#getReportCreateSelect1(String,
	 *      Integer, String, String, String, int, Integer)
	 */
	public String getReportCreateSelect1(String tableName, Integer bookId,
			String where, String fieldList, String aditionalFields,
			int bookType, Integer maxReportRegister) {
		return "CREATE TABLE " + tableName + " (ARCHID," + fieldList
				+ aditionalFields + ") AS SELECT * FROM (SELECT " + bookId
				+ "," + fieldList + getSelectAditionFields(bookType)
				+ " FROM A" + bookId + "SF A" + where + ") where rownum<="
				+ maxReportRegister;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ieci.tecdoc.common.entity.dao.DBEntityDAO#getReportCreateSelectLastRegister(String,
	 *      Integer, String, String, String, int)
	 */
	public String getReportCreateSelectLastRegister(String tableName,
			Integer bookId, String where, String fieldList,
			String aditionalFields, int bookType) {
		return "CREATE TABLE " + tableName + " (ARCHID," + fieldList
				+ aditionalFields + ") AS SELECT * FROM (SELECT " + bookId
				+ "," + fieldList + getSelectAditionFields(bookType)
				+ " FROM A" + bookId + "SF A" + where
				+ " order by fdrid desc)where rownum<2";
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ieci.tecdoc.common.entity.dao.DBEntityDAO#getReportCreateSelectLastRegister1(String,
	 *      Integer, int, String, String, int)
	 */
	public String getReportCreateSelectLastRegister1(String tableName,
			Integer bookId, int fdrid, String fieldList,
			String aditionalFields, int bookType) {
		return "CREATE TABLE " + tableName + " (ARCHID," + fieldList
				+ aditionalFields + ") AS SELECT " + bookId + "," + fieldList
				+ getSelectAditionFields(bookType) + " FROM A" + bookId
				+ "SF A WHERE FDRID=" + fdrid;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ieci.tecdoc.common.entity.dao.DBEntityDAO#getReportCreate(String,
	 *      String)
	 */
	public String getReportCreate(String tableName, String fieldList) {
		return "CREATE TABLE " + tableName + " (ARCHID," + fieldList + ")";
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ieci.tecdoc.common.entity.dao.DBEntityDAO#getReportCreate(String,
	 *      String, Integer)
	 */
	public String getReportCreate(String tableName, String fieldList,
			Integer bookId) {
		return "CREATE TABLE " + tableName + " (ARCHID," + fieldList
				+ ") AS SELECT " + bookId + "," + fieldList + " FROM A"
				+ bookId + "SF WHERE 1=0";
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ieci.tecdoc.common.entity.dao.AbstractDBEntityDAO#getDateField(java.lang.String,
	 *      java.lang.String, int, java.lang.String)
	 */
	protected String getDateField(String fld, String operator, int type,
			String formatedField) {
		if (type == 1) {
			String consulta = null;
			if(operator.equals(DISTINTO)){
				consulta = fld + " not between(to_date(?,'DD/MM/YYYY HH24:MI:SS')) and (to_date(?,'DD/MM/YYYY HH24:MI:SS'))";
			}else if (operator.equals(IGUAL)){
				consulta = fld + " between(to_date(?,'DD/MM/YYYY HH24:MI:SS')) and (to_date(?,'DD/MM/YYYY HH24:MI:SS'))";
			}else{
				consulta = fld + operator + " to_date(?,'DD/MM/YYYY HH24:MI:SS')";
			}
			return consulta;

		} else {
			String result = null;
			StringBuffer buffer = new StringBuffer();
			if (operator.equals(IGUAL)) {
				buffer.append(fld);
				buffer.append(MAYOR_IGUAL);
				buffer.append(TO_DATE);
				buffer.append(formatedField + MIN_TIME_QUOTE);
				buffer.append(FORMAT + " and ");
				buffer.append(fld);
				buffer.append(MENOR_IGUAL);
				buffer.append(TO_DATE);
				buffer.append(formatedField + MAX_TIME_QUOTE);
				buffer.append(FORMAT);
				result = buffer.toString();
			}
			if (operator.equals(MAYOR)) {
				buffer.append(fld);
				buffer.append(MAYOR);
				buffer.append(TO_DATE);
				buffer.append(formatedField + MAX_TIME_QUOTE);
				buffer.append(FORMAT);
				result = buffer.toString();
			}
			if (operator.equals(MENOR)) {
				buffer.append(fld);
				buffer.append(MENOR);
				buffer.append(TO_DATE);
				buffer.append(formatedField + MIN_TIME_QUOTE);
				buffer.append(FORMAT);
				result = buffer.toString();
			}
			if (operator.equals(MAYOR_IGUAL)) {
				buffer.append(fld);
				buffer.append(MAYOR_IGUAL);
				buffer.append(TO_DATE);
				buffer.append(formatedField + MIN_TIME_QUOTE);
				buffer.append(FORMAT);
				result = buffer.toString();
			}
			if (operator.equals(MENOR_IGUAL)) {
				buffer.append(fld);
				buffer.append(MENOR_IGUAL);
				buffer.append(TO_DATE);
				buffer.append(formatedField + MAX_TIME_QUOTE);
				buffer.append(FORMAT);
				result = buffer.toString();
			}
			if (operator.equals(DISTINTO)) {
				buffer.append("(" + fld);
				buffer.append(MENOR);
				buffer.append(TO_DATE);
				buffer.append(formatedField + MIN_TIME_QUOTE);
				buffer.append(FORMAT + " or ");
				buffer.append(fld);
				buffer.append(MAYOR);
				buffer.append(TO_DATE);
				buffer.append(formatedField + MAX_TIME_QUOTE);
				buffer.append(FORMAT + ")");
				result = buffer.toString();
			}

			return result;
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ieci.tecdoc.common.entity.dao.AbstractDBEntityDAO#getTimeStampFormat(java.lang.String)
	 */
	protected String getTimeStampFormat(String date) {
		return "to_date('" + date + "','DD/MM/YYYY HH24:MI:SS')";
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ieci.tecdoc.common.entity.dao.AbstractDBEntityDAO#getDateQuery()
	 */
	protected String getDateQuery() {
		return "SELECT SYSDATE FROM DUAL";
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ieci.tecdoc.common.entity.dao.DBEntityDAO#getNextIdForScrModifreg(java.lang.Integer,
	 *      java.lang.String)
	 */
	public int getNextIdForScrModifreg(Integer userId, String entidad)
			throws SQLException {
		return getContador4SCRREGORIGDOC(userId, entidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ieci.tecdoc.common.entity.dao.DBEntityDAO#getNextIdForScrDistReg(java.lang.Integer,
	 *      java.lang.String)
	 */
	public int getNextIdForScrDistReg(Integer userId, String entidad)
			throws SQLException {
		return getContador4SCRREGORIGDOC(userId, entidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ieci.tecdoc.common.entity.dao.DBEntityDAO#getNextIdForScrDistRegState(java.lang.Integer,
	 *      java.lang.String)
	 */
	public int getNextIdForScrDistRegState(Integer userId, String entidad)
			throws SQLException {
		return getContador4SCRREGORIGDOC(userId, entidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ieci.tecdoc.common.entity.dao.DBEntityDAO#getNextIdForScrRegAsoc(java.lang.Integer,
	 *      java.lang.String)
	 */
	public int getNextIdForScrRegAsoc(Integer userId, String entidad)
			throws SQLException {
		return getContador4SCRREGORIGDOC(userId, entidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ieci.tecdoc.common.entity.dao.DBEntityDAO#getContador4SCRREGORIGDOC(java.lang.Integer,
	 *      java.lang.String)
	 */
	public int getContador4SCRREGORIGDOC(Integer userId, String entidad)
			throws SQLException {
		Statement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		int result = 0;

		try {
			connection = BBDDUtils.getConnection(entidad);
			statement = connection.createStatement();
			resultSet = statement
					.executeQuery("SELECT SCR_SEQCNT.NEXTVAL FROM DUAL");

			if (resultSet.next()) {
				result = resultSet.getInt(1);
			}
		} catch (SQLException e) {
			log
					.warn(
							"Resulta imposible obtener el identificador de la secuencia [SCR_SEQCNT]",
							e);
			throw e;
		} catch (Throwable e) {
			log
					.warn(
							"Resulta imposible obtener el identificador de la secuencia [SCR_SEQCNT]",
							e);
			throw new SQLException(
					"Resulta imposible obtener el identificador de la secuencia [SCR_SEQCNT]");
		} finally {
			BBDDUtils.close(resultSet);
			BBDDUtils.close(statement);
			BBDDUtils.close(connection);
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ieci.tecdoc.common.entity.dao.AbstractDBEntityDAO#getNextIdForInter(java.lang.Integer,
	 *      java.lang.String)
	 */
	public int getNextIdForInter(Integer userId, String entidad)
			throws SQLException {
		return getContador4SCRREGORIGDOC(userId, entidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ieci.tecdoc.common.entity.dao.AbstractDBEntityDAO#getContador4PERSONS(java.lang.Integer,
	 *      java.lang.String)
	 */
	public int getContador4PERSONS(Integer userId, String entidad)
			throws SQLException {
		return getContador("PERSONS", entidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ieci.tecdoc.common.entity.dao.AbstractDBEntityDAO#getContador4SCRADDRESS(java.lang.Integer,
	 *      java.lang.String)
	 */
	public int getContador4SCRADDRESS(Integer userId, String entidad)
			throws SQLException {
		return getContador("SCR_ADDRESS", entidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ieci.tecdoc.common.entity.dao.AbstractDBEntityDAO#getDataBaseType()
	 */
	public String getDataBaseType() {
		return ORACLE_TYPE;
	}

	/**
	 * Método para obtener el siguiente identificador de una secuencia
	 *
	 * @param tableName identificador de la tabla
	 * @param entidad
	 * @return
	 * @throws SQLException
	 */
	protected int getContador(String tableName, String entidad)
			throws SQLException {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		int result = 0;

		try {
			connection = BBDDUtils.getConnection(entidad);
			statement = connection
					.prepareStatement("UPDATE SCR_CONTADOR SET CONTADOR=CONTADOR+1 WHERE TABLAID=?");
			statement.setString(1, tableName);
			int affected = statement.executeUpdate();
			BBDDUtils.close(statement);

			if (affected == 0) {
				statement = connection
						.prepareStatement("INSERT INTO SCR_CONTADOR(TABLAID,CONTADOR) VALUES (?,1)");
				statement.setString(1, tableName);
				affected = statement.executeUpdate();
				BBDDUtils.close(statement);
			}

			statement = connection
					.prepareStatement("SELECT CONTADOR FROM SCR_CONTADOR WHERE TABLAID=?");
			statement.setString(1, tableName);
			resultSet = statement.executeQuery();

			if (resultSet.next()) {
				result = resultSet.getInt(1);
			}
			BBDDUtils.close(statement);
		} catch (SQLException e) {
			log.warn("Resulta imposible obtener el identificador para ["
					+ tableName + "]", e);
			throw e;
		} catch (Throwable e) {
			log.warn("Resulta imposible obtener el identificador para ["
					+ tableName + "]", e);
			throw new SQLException(
					"Resulta imposible obtener el identificador para ["
							+ tableName + "]");
		} finally {
			BBDDUtils.close(resultSet);
			BBDDUtils.close(statement);
			BBDDUtils.close(connection);
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ieci.tecdoc.common.entity.dao.DBEntityDAO#getTemporalTableDistributionQuerySentence(String,
	 *      Integer, String, int)
	 */
	public String getTemporalTableDistributionQuerySentence(String tableName,
			Integer bookId, String where, int index) {
		String result = null;

		if (index == 0) {
			result = "CREATE TABLE " + tableName
					+ " (BOOKID, FDRID) AS SELECT " + bookId + ", FDRID FROM A"
					+ bookId + "SF WHERE " + where;
		} else {
			result = " UNION SELECT " + bookId + ", FDRID FROM A" + bookId
					+ "SF WHERE " + where;
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ieci.tecdoc.common.entity.dao.DBEntityDAO#getTemporalTableDistributionQuerySentenceOrderBy(String,
	 *      Integer, String, int)
	 */
	public String getTemporalTableDistributionQuerySentenceOrderBy(String tableName,
			Integer bookId, String where, String regWhere, boolean isCreateTable,  boolean isInBook, String language) {

		StringBuffer result = new StringBuffer();

		//verificamos si el proceso es para crear la tabla o para añadirle más datos
		if (isCreateTable) {
			result.append("CREATE TABLE ").append(tableName).append(" ");
			result.append(getFieldsTableTemporalDistributionOrderBy());
			result.append(" AS ");
		} else {
			result.append(" UNION ");
		}
		//obtenemos la consulta a realizar según el tipo de libro y el id del libro
		result.append(createQueryForTableTemporalDistributionOrderBy(bookId, isInBook, language));

		if(StringUtils.isNotBlank(where)){
			result.append(" AND ").append(where);
		}

		// Se concatena al criterio de búsqueda, el criterio de búsqueda por
		// campos por del registro
		if(StringUtils.isNotBlank(regWhere)){
			if(StringUtils.isNotBlank(where)){
				result.append(" AND ");
			}
			result.append(regWhere);
		}

		return result.toString();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ieci.tecdoc.common.entity.dao.AbstractDBEntityDAO#getVersion(java.lang.String)
	 */
	public String getVersion(String entidad) {
		Statement statement = null;
		Connection connection = null;
		String result = null;
		ResultSet resultSet = null;

		try {
			connection = BBDDUtils.getConnection(entidad);
			statement = connection.createStatement();

			resultSet = statement
					.executeQuery("select value from nls_database_parameters where parameter = 'NLS_RDBMS_VERSION'");
			if (resultSet.next()) {
				result = resultSet.getString(1);
			}

		} catch (SQLException e) {
			log.warn("Error obteniendo la versión." + e.getMessage());
		} catch (Throwable e) {
			log.warn("Error obteniendo la versión.." + e.getMessage());
		} finally {
			BBDDUtils.close(statement);
			BBDDUtils.close(resultSet);
			BBDDUtils.close(connection);
		}
		return result;
	}

	/**
	 * Método que elimina de la papelera de reciclage una tabla eliminada
	 *
	 * @param tableName
	 *            nombre de la tabla a eliminar
	 * @param entidad
	 */
	public void purgeTable(String tableName, String entidad) {
		Statement statement = null;
		Connection connection = null;

		try {
			connection = BBDDUtils.getConnection(entidad);
			statement = connection.createStatement();

			statement.executeUpdate("PURGE TABLE " + tableName);
		} catch (SQLException e) {
			log.warn("Error ejecutando [PURGE TABLE " + tableName + "]."
					+ e.getMessage());
		} catch (Throwable e) {
			log.warn("Error ejecutando [PURGE TABLE " + tableName + "]."
					+ e.getMessage());
		} finally {
			BBDDUtils.close(statement);
			BBDDUtils.close(connection);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ieci.tecdoc.common.entity.dao.DBEntityDAO#recicleBin(String)
	 */
	public void recicleBin(String entidad) {
		Statement statement = null;
		Connection connection = null;

		try {
			connection = BBDDUtils.getConnection(entidad);
			statement = connection.createStatement();

			statement.executeUpdate("ALTER SESSION SET RECYCLEBIN=OFF ");
		} catch (SQLException e) {
			log.warn("Error ejecutando [ALTER SESSION SET RECYCLEBIN=OFF]."
					+ e.getMessage());
		} catch (Throwable e) {
			log.warn("Error ejecutando [ALTER SESSION SET RECYCLEBIN=OFF]."
					+ e.getMessage());
		} finally {
			BBDDUtils.close(statement);
			BBDDUtils.close(connection);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ieci.tecdoc.common.entity.dao.AbstractDBEntityDAO#deleteIdsGenerationTable(java.lang.Integer,
	 *      java.lang.String)
	 */
	public void deleteIdsGenerationTable(Integer userId, String entidad) {
	}

	/***************************************************************************
	 * Private methods
	 **************************************************************************/

	/***************************************************************************
	 * Inner classes
	 **************************************************************************/

	/***************************************************************************
	 * Test brench
	 **************************************************************************/

}
