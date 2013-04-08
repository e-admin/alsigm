package ieci.tecdoc.sgm.admsistema.proceso.managers.clean;

import ieci.tecdoc.sgm.admsistema.util.Defs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Iecisa
 * @version $Revision$
 *
 */
public abstract class DefaultDbCleanManagerImpl implements IDbCleanManager {


    private Map jdbcDrivers = null;
    private Map jdbcUrlPatterns = null;


	/**
	 * Constructor.
	 */
	public DefaultDbCleanManagerImpl() {
	}

	public Map getJdbcDrivers() {
		return jdbcDrivers;
	}

	public void setJdbcDrivers(Map jdbcDrivers) {
		this.jdbcDrivers = jdbcDrivers;
	}

	public Map getJdbcUrlPatterns() {
		return jdbcUrlPatterns;
	}

	public void setJdbcUrlPatterns(Map jdbcUrlPatterns) {
		this.jdbcUrlPatterns = jdbcUrlPatterns;
	}

	public abstract String getDbName();
	public abstract String getCleanUserKey();
	public abstract String getCleanUserPwdKey();
	public abstract Map getSQLSentences();
	public abstract String[][] getDynamicTablesForCleaning();
	public abstract String[] getDynamicTablesForCleaningCondition();

	/**
	 * Limpia la base de datos
	 * @param logger Logger
	 * @param options Opciones para la limpieza de la base de datos.
	 * @return true si la base de datos se ha limpiado correctamente.
	 */
	public boolean clean(Logger logger, Map options) {

		boolean ret = true;
		Connection conn = null;

		try {

			conn = openConnection(logger, options);

			executeSQLSentences(logger, conn, options);
			cleanDynamicTables(logger, conn);

		} catch (Throwable t) {
			logger.error("Error al limpiar la base de datos: " + getDbName(), t);
		} finally {
			try {
				closeConnection(conn);
			} catch (Exception e) {
				logger.error("Error al cerrar la conexión a la base de datos: " + getDbName(), e);
			}
		}

		return ret;
	}

	protected Connection openConnection(Logger logger, Map options) throws SQLException, ClassNotFoundException {

		Connection conn = null;

		String dbType = (String) options.get(Defs.BD_TIPO_BASE_DATOS_IMP);
		if (StringUtils.isNotBlank(dbType)) {

			// Obtener el driver JDBC
			String jdbcDriver = (String) jdbcDrivers.get(dbType.toLowerCase());
			if (StringUtils.isBlank(jdbcDriver)) {
				logger.error("No se ha configurado un driver JDBC para el tipo de base de datos [" + dbType.toLowerCase() + "]");
				return conn;
			}

	    	// Patrón de la URL JDBC
	    	String jdbcUrl = (String) jdbcUrlPatterns.get(dbType.toLowerCase());
			if (StringUtils.isBlank(jdbcDriver)) {
				logger.error("No se ha configurado un patrón de URL JDBC para el tipo de base de datos [" + dbType.toLowerCase() + "]");
				return conn;
			}

			// Reemplazar comodines
			jdbcUrl = StringUtils.replace(jdbcUrl, "${host}", (String) options.get(Defs.BD_HOST_IMP));
			jdbcUrl = StringUtils.replace(jdbcUrl, "${port}", (String) options.get(Defs.BD_PUERTO_IMP));
			jdbcUrl = StringUtils.replace(jdbcUrl, "${db}", getDbName() + "_" + (String) options.get(Defs.ID_ENTIDAD_IMP));
			jdbcUrl = StringUtils.replace(jdbcUrl, "${sid}", (String) options.get(Defs.BD_INSTANCIA_IMP));

	    	// Instanciar el driver JDBC
	    	Class.forName(jdbcDriver);

	    	if (logger.isDebugEnabled()) {
	    		logger.debug("Conectando a: " + jdbcUrl);
	    	}

	    	String user = (String) options.get(getCleanUserKey());
	    	String passwd = (String) options.get(getCleanUserPwdKey());

	    	if (StringUtils.isBlank(user)) {
		    	user = (String) options.get(Defs.BD_USUARIO_IMP);
		    	passwd = (String) options.get(Defs.BD_PASS_IMP);
	    	}

	    	// Establecer la conexión a la base de datos
	    	conn = DriverManager.getConnection(jdbcUrl, user, passwd);

		} else {
			logger.error("No se ha establecido el tipo de base de datos");
		}

		return conn;
	}

	protected void closeConnection(Connection conn) throws SQLException {
		if (conn != null) {
			conn.close();
		}
	}

	protected void cleanDynamicTables(Logger logger, Connection conn) throws Exception {

		String[][] dynamicTables = getDynamicTablesForCleaning();
		String[] condition = getDynamicTablesForCleaningCondition();
		String[] valores = (String[])search(logger, conn, condition);

    	if ((valores != null) && (valores.length > 0)) {
    		for (int i = 0; i < valores.length; i++) {
    			for (int j = 0; j < dynamicTables.length; j++) {
    				String tableName = StringUtils.replace(dynamicTables[j][0], "##", valores[i]);
    				cleanTable(logger, conn, new String[] { tableName, dynamicTables[j][1]} );
    			}
    		}
    	}
    }

	protected String[] search(Logger logger, Connection conn, String[] condition) {

    	if ((condition != null) && (condition.length >= 2)) {

    		String sql = "SELECT " + condition[0] + " FROM " + condition[1];
    		if (StringUtils.isNotBlank(condition[2])) {
    			sql += " WHERE " + condition[2];
    		}

    		Statement stmt = null;
    		ResultSet rs = null;
    		List valores = new ArrayList();

    		try {
	    		stmt = conn.createStatement();
	    		rs = stmt.executeQuery(sql);

		    	while(rs.next()) {
		    		valores.add(rs.getString(condition[0]));
		    	}

    		} catch (SQLException e) {
    			logger.error("Error al realizar la búsqueda [" + StringUtils.join(condition, " | ") + "]", e);
    		} finally {
    			closeResultSet(logger, rs);
    			closeStatement(logger, stmt);
    		}

    		return (String[])valores.toArray(new String[valores.size()]);

    	} else {
    		return new String[0];
    	}
    }

	protected void cleanTable(Logger logger, Connection conn, String[] table) {
    	Statement stmt = null;

		String tableName = table[0];
		String condition = table[1];

		try {

    		stmt = conn.createStatement();

    		if (StringUtils.isBlank(condition)) {
	    		stmt.execute("DELETE FROM " + tableName);
	    		if (logger.isDebugEnabled()) {
	    			logger.debug("Tabla [" + tableName + "] limpiada");
	    		}
    		} else {
	    		stmt.execute("DELETE FROM " + tableName + " WHERE " + condition);
	    		if (logger.isDebugEnabled()) {
	    			logger.debug("Tabla [" + tableName + "] limpiada con la cláusula [" + condition + "]");
	    		}
    		}

		} catch (SQLException e) {
			logger.error("Error al limpiar la tabla [" + tableName + "]", e);
		} finally {
			closeStatement(logger, stmt);
		}

	}

	protected void executeSQLSentences(Logger logger, Connection conn, Map options) {

		String dbType = (String) options.get(Defs.BD_TIPO_BASE_DATOS_IMP);

		Map allSqlSentences = getSQLSentences();

		List sqlSentences = new ArrayList();

		// Obtener las sentencias aplicables a todas las bases de datos
		String[] aSQLSentences = (String[]) allSqlSentences.get("*");
		if (aSQLSentences != null) {
			sqlSentences.addAll(Arrays.asList(aSQLSentences));
		}

		// Obtener las sentencias aplicables al tipo de base de datos actual
		aSQLSentences = (String[]) allSqlSentences.get(dbType.toLowerCase());
		if (aSQLSentences != null) {
			sqlSentences.addAll(Arrays.asList(aSQLSentences));
		}

		for (int i = 0; i < sqlSentences.size(); i++) {
			executeUpdate(logger, conn, (String) sqlSentences.get(i));
		}
	}

	protected void executeUpdate(Logger logger, Connection conn, String sql) {

		Statement stmt = null;

		if (logger.isDebugEnabled()) {
			logger.debug("Ejecutando sentencia SQL: " + sql);
		}

		try {
			conn.setAutoCommit(false);

			stmt = conn.createStatement();
			stmt.executeUpdate(sql);

			conn.commit();

		} catch (SQLException e) {
			logger.error("Error al ejecutar la sentencia SQL: " + sql, e);

			try {
				conn.rollback();
			} catch (SQLException e1) {
				logger.error("Error al ejecutar el rollback en la sentencia SQL: " + sql, e1);
			}
		} finally {
			closeStatement(logger, stmt);
		}
	}

	protected void closeResultSet(Logger logger, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				logger.error("Error al cerrar el resultSet", e);
			}
		}
	}

	protected void closeStatement(Logger logger, Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				logger.error("Error al cerrar el statement", e);
			}
		}
	}
}
