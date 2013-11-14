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
public class SQLServerOracleDBEntityDAO extends AbstractDBEntityDAO {

	/***************************************************************************
	 * Attributes
	 **************************************************************************/

	private static final Logger log = Logger
			.getLogger(SQLServerOracleDBEntityDAO.class);

	/***************************************************************************
	 * Constructors
	 **************************************************************************/

	public SQLServerOracleDBEntityDAO() {
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
		query.append("ALTER TABLE " + tableName + " ADD ");
		int i = 0;
		for (i = 0; i < newColumnName.length; i++) {
			if (i != 0) {
				query.append(",");
			}
			query.append(newColumnName[i] + " NVARCHAR(1500)");
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
					.append(",(SELECT NAME FROM SCR_OFIC AS B WHERE B.ID=A.FLD5) AS FLD5_TEXT,");
			result
					.append("(SELECT CODE + '-' + NAME FROM SCR_ORGS AS B WHERE B.ID=A.FLD7) AS FLD7_TEXT,");
			result
					.append("(SELECT CODE + '-' + NAME FROM SCR_ORGS AS B WHERE B.ID=A.FLD8) AS FLD8_TEXT,");
			result
					.append("(SELECT CODE FROM SCR_ORGS AS B WHERE B.ID=A.FLD13) AS FLD13_TEXT,");
			result
					.append("(SELECT CODE + '-' + MATTER FROM SCR_CA AS B WHERE B.ID=A.FLD16) AS FLD16_TEXT");
		} else {
			result
					.append(",(SELECT NAME FROM SCR_OFIC AS B WHERE B.ID=A.FLD5) AS FLD5_TEXT,");
			result
					.append("(SELECT CODE + '-' + NAME FROM SCR_ORGS AS B WHERE B.ID=A.FLD7) AS FLD7_TEXT,");
			result
					.append("(SELECT CODE + '-' + NAME FROM SCR_ORGS AS B WHERE B.ID=A.FLD8) AS FLD8_TEXT,");
			result
					.append("(SELECT CODE + '-' + MATTER FROM SCR_CA AS B WHERE B.ID=A.FLD12) AS FLD12_TEXT");
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
					+ ") AS SELECT " + bookId + " as ARCHID," + fieldList
					+ " FROM A" + bookId + "SF " + where;
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
			result = "CREATE VIEW " + tableName + " (ARCHID," + fieldList
					+ aditionalFields + ") AS SELECT TOP " + maxReportRegister
					+ " * from (SELECT " + bookId + " as ARCHID," + fieldList
					+ getSelectAditionFields(bookType) + " FROM A" + bookId
					+ "SF AS A" + where;
		} else {
			result = " UNION SELECT " + bookId + "," + fieldList
					+ getSelectAditionFields(bookType) + " FROM A" + bookId
					+ "SF AS A" + where;
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ieci.tecdoc.common.entity.dao.DBEntityDAO#getFinalQuery(Integer)
	 */
	public String getFinalQuery(Integer maxReportRegister) {
		return ") as t";
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
				+ ") AS SELECT " + bookId + " as ARCHID," + fieldList
				+ " FROM A" + bookId + "SF " + where;
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
				+ aditionalFields + ") AS SELECT TOP " + maxReportRegister
				+ " " + bookId + " as ARCHID," + fieldList
				+ getSelectAditionFields(bookType) + " FROM A" + bookId
				+ "SF AS A" + where;
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
				+ aditionalFields + ") AS SELECT TOP 1 " + bookId
				+ " as ARCHID," + fieldList + getSelectAditionFields(bookType)
				+ " FROM A" + bookId + "SF AS A" + where
				+ " order by fdrid desc";
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
				+ aditionalFields + ") AS SELECT " + bookId + " as ARCHID,"
				+ fieldList + getSelectAditionFields(bookType) + " FROM A"
				+ bookId + "SF A WHERE FDRID=" + fdrid;
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
				+ ") AS SELECT " + bookId + " as ARCHID," + fieldList
				+ " FROM A" + bookId + "SF WHERE 1=0";
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
	 * @see com.ieci.tecdoc.common.entity.dao.AbstractDBEntityDAO#getDateField(java.lang.String,
	 *      java.lang.String, int, java.lang.String)
	 */
	protected String getDateField(String fld, String operator, int type,
			String formatedField) {

		String consulta = null;

		// distinguimos el tipo de consulta ha realizar. Cuanto el tipo de la
		// consulta es uno, se genera una consulta tipo
		// prepareStatement, es decir de valores dinamicos, en cambio para el
		// resto de valores la sentencia
		// generada es una sentencia SQL estática con el valor pasado como
		// pamarametro a esta función (formatedField)
		if (type == 1) {
			if(operator.equals(DISTINTO)){
				consulta = fld + " not between ? and ?";
			}else if (operator.equals(IGUAL)){
				consulta = fld + " between ? and ?";
			}else{
				consulta = fld + operator + " ?";
			}
		}else{
			StringBuffer buffer = new StringBuffer();
			if (operator.equals(IGUAL)) {
				buffer.append(fld);
				buffer.append(MAYOR_IGUAL);
				buffer.append(getTimeStampFormat(formatedField + MIN_TIME));
				buffer.append(" and ");
				buffer.append(fld);
				buffer.append(MENOR_IGUAL);
				buffer.append(getTimeStampFormat(formatedField + MAX_TIME));
			}
			if (operator.equals(MAYOR)) {
				buffer.append(fld);
				buffer.append(MAYOR);
				buffer.append(getTimeStampFormat(formatedField + MAX_TIME));
			}
			if (operator.equals(MENOR)) {
				buffer.append(fld);
				buffer.append(MENOR);
				buffer.append(getTimeStampFormat(formatedField + MIN_TIME));
			}
			if (operator.equals(MAYOR_IGUAL)) {
				buffer.append(fld);
				buffer.append(MAYOR_IGUAL);
				buffer.append(getTimeStampFormat(formatedField + MIN_TIME));
			}
			if (operator.equals(MENOR_IGUAL)) {
				buffer.append(fld);
				buffer.append(MENOR_IGUAL);
				buffer.append(getTimeStampFormat(formatedField + MAX_TIME));
			}
			if (operator.equals(DISTINTO)) {
				buffer.append("(" + fld);
				buffer.append(MENOR);
				buffer.append(getTimeStampFormat(formatedField + MIN_TIME));
				buffer.append(" or ");
				buffer.append(fld);
				buffer.append(MAYOR);
				buffer.append(getTimeStampFormat(formatedField + MAX_TIME));
				buffer.append(")");
			}
			consulta = buffer.toString();
		}

		return consulta;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ieci.tecdoc.common.entity.dao.AbstractDBEntityDAO#getTimeStampFormat(java.lang.String)
	 */
	protected String getTimeStampFormat(String date) {
		return " convert(datetime,'" + date + "',0)";
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ieci.tecdoc.common.entity.dao.AbstractDBEntityDAO#getDateQuery()
	 */
	protected String getDateQuery() {
		return "SELECT GETDATE()";
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ieci.tecdoc.common.entity.dao.DBEntityDAO#getContador4SCRREGORIGDOC(java.lang.Integer,
	 *      java.lang.String)
	 */
	public int getContador4SCRREGORIGDOC(Integer userId, String entidad)
			throws SQLException {
		// return getContador("SCR_REGORIGDOC");
		return getContador(userId.intValue(), entidad);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ieci.tecdoc.common.entity.dao.AbstractDBEntityDAO#getNextIdForInter(java.lang.Integer,
	 *      java.lang.String)
	 */
	public int getNextIdForInter(Integer userId, String entidad)
			throws SQLException {
		// return getContador("SCR_REGINT");
		return getContador(userId.intValue(), entidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ieci.tecdoc.common.entity.dao.DBEntityDAO#getNextIdForScrRegAsoc(java.lang.Integer,
	 *      java.lang.String)
	 */
	public int getNextIdForScrRegAsoc(Integer userId, String entidad)
			throws SQLException {
		// return getContador("SCR_REGASOC");
		return getContador(userId.intValue(), entidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ieci.tecdoc.common.entity.dao.DBEntityDAO#getNextIdForScrModifreg(java.lang.Integer,
	 *      java.lang.String)
	 */
	public int getNextIdForScrModifreg(Integer userId, String entidad)
			throws SQLException {
		// return getContador("SCR_MODIFREG");
		return getContador(userId.intValue(), entidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ieci.tecdoc.common.entity.dao.DBEntityDAO#getNextIdForScrDistReg(java.lang.Integer,
	 *      java.lang.String)
	 */
	public int getNextIdForScrDistReg(Integer userId, String entidad)
			throws SQLException {
		// return getContador("SCR_DISTREG");
		return getContador(userId.intValue(), entidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ieci.tecdoc.common.entity.dao.DBEntityDAO#getNextIdForScrDistRegState(java.lang.Integer,
	 *      java.lang.String)
	 */
	public int getNextIdForScrDistRegState(Integer userId, String entidad)
			throws SQLException {
		// return getContador("SCR_DISTREGSTATE");
		return getContador(userId.intValue(), entidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ieci.tecdoc.common.entity.dao.AbstractDBEntityDAO#getContador4PERSONS(java.lang.Integer,
	 *      java.lang.String)
	 */
	public int getContador4PERSONS(Integer userId, String entidad)
			throws SQLException {
		return getContador(userId.intValue(), entidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ieci.tecdoc.common.entity.dao.AbstractDBEntityDAO#getContador4SCRADDRESS(java.lang.Integer,
	 *      java.lang.String)
	 */
	public int getContador4SCRADDRESS(Integer userId, String entidad)
			throws SQLException {
		return getContador(userId.intValue(), entidad);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ieci.tecdoc.common.entity.dao.AbstractDBEntityDAO#getDataBaseType()
	 */
	public String getDataBaseType() {
		return SQLSERVER_TYPE;
	}

	/**
	 * Método para obtener el siguiente identificador de una secuencia
	 *
	 * @param userId
	 *            identificador del usuario
	 * @param entidad
	 * @return
	 * @throws SQLException
	 */
	protected int getContador(int userId, String entidad) throws SQLException {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		int result = 0;

		try {
			connection = BBDDUtils.getConnection(entidad);

			statement = connection
					.prepareStatement("INSERT INTO SCR_SEQCNT(USERID) VALUES (?)");
			statement.setInt(1, userId);
			statement.executeUpdate();
			BBDDUtils.close(statement);

			statement = connection
					.prepareStatement("SELECT MAX(ID) FROM SCR_SEQCNT WHERE USERID=?");
			statement.setInt(1, userId);
			resultSet = statement.executeQuery();

			if (resultSet.next()) {
				result = resultSet.getInt(1);
			}
			BBDDUtils.close(statement);
		} catch (SQLException e) {
			log.warn("Resulta imposible obtener el identificador para ["
					+ userId + "]", e);
			throw e;
		} catch (Throwable e) {
			log.warn("Resulta imposible obtener el identificador para ["
					+ userId + "]", e);
			throw new SQLException(
					"Resulta imposible obtener el identificador para ["
							+ userId + "]");
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
			result.append("CREATE VIEW ").append(tableName).append(" ");
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
		PreparedStatement statement = null;
		Connection connection = null;

		try {
			connection = BBDDUtils.getConnection(entidad);
			statement = connection.prepareStatement(DELETE_SCR_SEQCNT);
			statement.setInt(1, userId.intValue());
			statement.executeUpdate();

			log.info("DELETE FROM SEQCNT FOR USERID: " + userId.intValue());

		} catch (SQLException e) {
			log.warn(
					"Resulta imposible vaciar la tabla de generación de ids para el usuario ["
							+ DELETE_SCR_SEQCNT + "]", e);
		} catch (Throwable e) {
			log.warn(
					"Resulta imposible vaciar la tabla de generación de ids para el usuario ["
							+ DELETE_SCR_SEQCNT + "]", e);
		} finally {
			BBDDUtils.close(statement);
			BBDDUtils.close(connection);
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
