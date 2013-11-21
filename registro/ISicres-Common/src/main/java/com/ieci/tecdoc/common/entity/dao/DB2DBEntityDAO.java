package com.ieci.tecdoc.common.entity.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.isicres.AxSf;
import com.ieci.tecdoc.common.isicres.AxSfQueryField;
import com.ieci.tecdoc.common.utils.BBDDUtils;

/**
 * @author LMVICENTE
 * @creationDate 28-may-2004 10:10:05
 * @version
 * @since
 */
public class DB2DBEntityDAO extends AbstractDBEntityDAO {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	private static final Logger log = Logger.getLogger(OracleDBEntityDAO.class);

	private static final String GUION = "-";

	// private static final String TO_DATE = "to_date('";
	private static final String TO_TIMESTAMP = "TIMESTAMP(";
	private static final String TO_TIMESTAMP_QUOTE = "TIMESTAMP('";

	// private static final String FORMAT = ",'YYYY-MM-DD HH24:MI:SS')";

	/***************************************************************************
	 * Constructors
	 **************************************************************************/

	public DB2DBEntityDAO() {
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

		try {
			connection = BBDDUtils.getConnection(entidad);
			statement = connection.createStatement();
			statement.executeUpdate("DROP VIEW " + tableName);

		} catch (SQLException e) {
			log.warn("Error ejecutando [DROP VIEW " + tableName + "]."
					+ e.getMessage());
		} catch (Throwable e) {
			log.warn("Error ejecutando [DROP VIEW " + tableName + "]."
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
					.append("(SELECT concat(concat(code,'-'),matter) FROM SCR_CA B WHERE B.ID=A.FLD16) AS FLD16_TEXT,");
			result.append("ROW_NUMBER() OVER () as ROW1");
		} else {
			result
					.append(",(SELECT NAME FROM SCR_OFIC B WHERE B.ID=A.FLD5) AS FLD5_TEXT,");
			result
					.append("(SELECT concat(concat(code,'-'),name) FROM SCR_ORGS B WHERE B.ID=A.FLD7) AS FLD7_TEXT,");
			result
					.append("(SELECT concat(concat(code,'-'),name) FROM SCR_ORGS B WHERE B.ID=A.FLD8) AS FLD8_TEXT,");
			result
					.append("(SELECT concat(concat(code,'-'),matter) FROM SCR_CA B WHERE B.ID=A.FLD12) AS FLD12_TEXT,");
			result.append("ROW_NUMBER() OVER () as ROW1");
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
			result = "CREATE VIEW " + tableName + " (ARCHID," + fieldList
					+ ") AS SELECT " + bookId + "," + fieldList + " FROM A"
					+ bookId + "SF " + where;
		} else {
			result = " UNION ALL SELECT " + bookId + "," + fieldList
					+ " FROM A" + bookId + "SF " + where;
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
			result = "CREATE VIEW " + tableName + " (ARCHID," + fieldList
					+ aditionalFields + ",ROW1) AS SELECT * FROM (SELECT "
					+ bookId + "," + fieldList
					+ getSelectAditionFields(bookType) + " FROM A" + bookId
					+ "SF A" + where;
		} else {
			result = " UNION ALL SELECT " + bookId + "," + fieldList
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
		return ") as temp";
		// FETCH FIRST " + maxReportRegister + " ROWS ONLY";
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ieci.tecdoc.common.entity.dao.DBEntityDAO#getReportCreateSelect(String,
	 *      Integer, String, String)
	 */
	public String getReportCreateSelect(String tableName, Integer bookId,
			String where, String fieldList) {
		return "CREATE VIEW " + tableName + " (ARCHID," + fieldList
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
		return "CREATE VIEW " + tableName + " (ARCHID," + fieldList
				+ aditionalFields + ",ROW1) AS SELECT * FROM (SELECT " + bookId
				+ "," + fieldList + getSelectAditionFields(bookType)
				+ " FROM A" + bookId + "SF A" + where + ") as temp where ROW1<="
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
		return "CREATE VIEW " + tableName + " (ARCHID," + fieldList
				+ aditionalFields + ",ROW1) AS SELECT * FROM (SELECT " + bookId
				+ "," + fieldList + getSelectAditionFields(bookType)
				+ " FROM A" + bookId + "SF A" + where
				+ " order by fdrid desc) as temp where ROW1<2";
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
		return "CREATE VIEW " + tableName + " (ARCHID," + fieldList
				+ aditionalFields + ",ROW1) AS SELECT " + bookId + "," + fieldList
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
		return "CREATE VIEW " + tableName + " (ARCHID," + fieldList + ")";
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ieci.tecdoc.common.entity.dao.DBEntityDAO#getReportCreate(String,
	 *      String, Integer)
	 */
	public String getReportCreate(String tableName, String fieldList,
			Integer bookId) {
		return "CREATE VIEW " + tableName + " (ARCHID," + fieldList
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
				consulta = fld + " not between ? and ?";
			}else if (operator.equals(IGUAL)){
				consulta = fld + " between ? and ?";
			}else{
				consulta = fld + operator + " ?";
			}
			return consulta;


		} else {
			String result = null;
			StringBuffer buffer = new StringBuffer();
			if (operator.equals(IGUAL)) {
				buffer.append(fld);
				buffer.append(MAYOR_IGUAL);
				buffer.append(TO_TIMESTAMP_QUOTE);
				buffer.append(getDateFormated(formatedField) + MIN_TIME_QUOTE);
				// buffer.append(FORMAT + " and ");
				buffer.append(") and ");
				buffer.append(fld);
				buffer.append(MENOR_IGUAL);
				buffer.append(TO_TIMESTAMP_QUOTE);
				buffer.append(getDateFormated(formatedField) + MAX_TIME_QUOTE);
				buffer.append(")");
				result = buffer.toString();
			}
			if (operator.equals(MAYOR)) {
				buffer.append(fld);
				buffer.append(MAYOR);
				buffer.append(TO_TIMESTAMP_QUOTE);
				buffer.append(getDateFormated(formatedField) + MAX_TIME_QUOTE);
				buffer.append(")");
				result = buffer.toString();
			}
			if (operator.equals(MENOR)) {
				buffer.append(fld);
				buffer.append(MENOR);
				buffer.append(TO_TIMESTAMP_QUOTE);
				buffer.append(getDateFormated(formatedField) + MIN_TIME_QUOTE);
				buffer.append(")");
				result = buffer.toString();
			}
			if (operator.equals(MAYOR_IGUAL)) {
				buffer.append(fld);
				buffer.append(MAYOR_IGUAL);
				buffer.append(TO_TIMESTAMP_QUOTE);
				buffer.append(getDateFormated(formatedField) + MIN_TIME_QUOTE);
				buffer.append(")");
				result = buffer.toString();
			}
			if (operator.equals(MENOR_IGUAL)) {
				buffer.append(fld);
				buffer.append(MENOR_IGUAL);
				buffer.append(TO_TIMESTAMP_QUOTE);
				buffer.append(getDateFormated(formatedField) + MAX_TIME_QUOTE);
				buffer.append(")");
				result = buffer.toString();
			}
			if (operator.equals(DISTINTO)) {
				buffer.append("(" + fld);
				buffer.append(MENOR);
				buffer.append(TO_TIMESTAMP_QUOTE);
				buffer.append(getDateFormated(formatedField) + MIN_TIME_QUOTE);
				buffer.append(") or ");
				buffer.append(fld);
				buffer.append(MAYOR);
				buffer.append(TO_TIMESTAMP_QUOTE);
				buffer.append(getDateFormated(formatedField) + MAX_TIME_QUOTE);
				buffer.append("))");
				result = buffer.toString();
			}

			return result;
		}
	}

	/**
	 * Método que formatea un valor en formato fecha
	 *
	 * @param formatedField
	 *            fecha a formatear
	 * @return
	 */
	protected String getDateFormated(String formatedField) {
		String where = formatedField;
		String day = where.substring(0, 2);
		String month = where.substring(3, 5);
		String year = where.substring(6, 10);
		StringBuffer buffer = new StringBuffer();
		buffer.append(year);
		buffer.append(GUION);
		buffer.append(month);
		buffer.append(GUION);
		buffer.append(day);
		return buffer.toString();
	}

	/**
	 * Método que formetea un valor en formato timestamp
	 *
	 * @param formatedField
	 *            fecha a formatear
	 * @return
	 */
	public String getTimeStampFormated(String formatedField) {
		String date = formatedField.substring(0, 10);
		String time = formatedField.substring(11, formatedField.length());
		StringBuffer buffer = new StringBuffer();
		buffer.append(getDateFormated(date));
		buffer.append(" ");
		buffer.append(time);
		return buffer.toString();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ieci.tecdoc.common.entity.dao.AbstractDBEntityDAO#getTimeStampFormat(java.lang.String)
	 */
	protected String getTimeStampFormat(String date) {
		String dateTime = getTimeStampFormated(date);
		return TO_TIMESTAMP_QUOTE + dateTime + "')";
		// return "to_date('" + dateTime + "','YYYY-MM-DD HH24:MI:SS')";
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ieci.tecdoc.common.entity.dao.AbstractDBEntityDAO#getDateQuery()
	 */
	protected String getDateQuery() {
		return "SELECT CURRENT TIMESTAMP FROM SYSIBM.SYSDUMMY1 ";
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
					.executeQuery("SELECT NEXTVAL FOR SCR_SEQCNT FROM SCR_CONFIGURATION");

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
		return DB2_TYPE;
	}

	/**
	 * Método para obtener el siguiente identificador de una secuencia
	 *
	 * @param tableName
	 *            identificador de la tabla
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
			result = "CREATE VIEW " + tableName + " (BOOKID, FDRID) AS SELECT "
					+ bookId + ", FDRID FROM A" + bookId + "SF WHERE " + where;
		} else {
			result = " UNION ALL SELECT " + bookId + ", FDRID FROM A" + bookId
					+ "SF WHERE " + where;
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ieci.tecdoc.common.entity.dao.DBEntityDAO#getTemporalTableDistributionQuerySentence(String,
	 *      Integer, String, int)
	 */
	public String getTemporalTableDistributionQuerySentenceOrderBy(String tableName,
			Integer bookId, String where, String regWhere, boolean isCreateTable,  boolean isInBook, String language) {

		StringBuffer result = new StringBuffer();

		//verificamos si el proceso es para crear la tabla o para añadirle más datos
		if (isCreateTable) {
			result.append("CREATE VIEW ").append(tableName).append(" ");
			result.append(getFieldsTableTemporalDistributionOrderBy());
			result.append(" AS ");
		} else {
			result.append(" UNION ALL ");
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
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ieci.tecdoc.common.entity.dao.DBEntityDAO#recicleBin(String)
	 */
	public void recicleBin(String entidad) {
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ieci.tecdoc.common.entity.dao.AbstractDBEntityDAO#deleteIdsGenerationTable(java.lang.Integer,
	 *      java.lang.String)
	 */
	public void deleteIdsGenerationTable(Integer userId, String entidad) {
	}


	/**
	 * Método para componer una consulta jdbc en DB2
	 *
	 * @param field
	 * @param axsfP
	 * @param ps
	 * @param index
	 * @param value
	 * @throws SQLException
	 */
	protected void assignAttribute(AxSfQueryField field, AxSf axsfP,
			PreparedStatement ps, int index, Object value) throws SQLException {
		if ((axsfP.getAttributeClass(field.getFldId()).equals(Date.class))
				&& ((field.getOperator().equals(IGUAL))
				 || (field.getOperator().equals(DISTINTO))
				 || (field.getOperator().equals(QUERY_OR)))) {

			Date date = null;
			if(field.getOperator().equals(QUERY_OR)){
				date = (Date) value;
			}else{
				date = (Date) field.getValue();
			}

			String dateFormatter = FORMATTER.format((Date) date);
			String dateDb2 = getDateFormated(dateFormatter)+ MIN_TIME;
			ps.setObject(index, dateDb2);
			index++;

			dateDb2 = getDateFormated(dateFormatter) + MAX_TIME;
			ps.setObject(index, dateDb2);

		} else if ((axsfP.getAttributeClass(field.getFldId())
				.equals(Date.class))
				&& ((field.getOperator().equals(ENTRE)))) {
			GregorianCalendar gc = new GregorianCalendar();

			gc.setTime((Date) value);
			gc.set(Calendar.SECOND, 0);
			gc.set(Calendar.MILLISECOND, 0);
			gc.set(Calendar.HOUR_OF_DAY, 0);
			gc.set(Calendar.MINUTE, 0);
			ps.setObject(index, BBDDUtils.getTimestamp(gc.getTime()));


		} else if ((axsfP.getAttributeClass(field.getFldId())
				.equals(Date.class))
				&& ((field.getOperator().equals(MAYOR)) || (field.getOperator()
						.equals(MENOR_IGUAL)))) {

			String dateFormatter = FORMATTER.format((Date) field.getValue());
			String dateDb2 = getDateFormated(dateFormatter)+ MAX_TIME;
			ps.setObject(index, dateDb2);

		}else if(axsfP.getAttributeClass(field.getFldId()).equals(Date.class)){
			String dateFormatter = FORMATTER.format((Date) field.getValue());
			String dateDb2 = getDateFormated(dateFormatter) + MIN_TIME;
			ps.setObject(index, dateDb2);
		} else {
			ps.setObject(index, value);
		}
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
