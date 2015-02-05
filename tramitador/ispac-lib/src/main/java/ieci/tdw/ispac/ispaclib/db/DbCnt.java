package ieci.tdw.ispac.ispaclib.db;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.session.OrganizationUser;
import ieci.tdw.ispac.ispaclib.session.OrganizationUserInfo;
import ieci.tdw.ispac.ispaclib.util.ISPACConfiguration;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

public class DbCnt {

	/* =======================================================================
		Contantes para los motores de base de datos
	   =======================================================================*/
	/** Nombre de la base de datos ORACLE. */
	public static final String ORACLE_DB_NAME 		= "ORACLE";
	
	/** Nombre de la base de datos SQLServer. */
	public static final String SQLSERVER_DB_NAME 	= "MICROSOFT SQL SERVER";
	
	/** Nombre de la base de datos PostgreSQL. */
	public static final String POSTGRESQL_DB_NAME 	= "PostgreSQL";

	/** Nombre de la base de datos DB2. */
	public static final String DB2_DB_NAME 			= "DB2";
	/*========================================================================*/

	
	/* =======================================================================
		Contantes para los motores de base de datos
       =======================================================================*/
	public static final int NO_ENGINE_ID 			= -1;   
	public static final int SQLSERVER_ENGINE_ID 	= 1;
	public static final int ORACLE_ENGINE_ID    	= 2;
	public static final int POSTGRESQL_ENGINE_ID 	= 3;
	public static final int DB2_ENGINE_ID 			= 4;
	/*========================================================================*/

	/** Logger de la clase. */
	private static Logger logger = Logger.getLogger(DbCnt.class);

	/** Nombre del pool. */
	private String mpoolname = null;

	/** DataSource. */
	private DataSource mdataSource = null;

	/** Objeto de tipo conexión. */
	private Connection mcnt = null;
	
	/** Identificador interno del sistema de base datos. */
	private int idEngine = NO_ENGINE_ID;


	/**
	 * El objeto utilizar&aacute; el pool de conexiones por defecto especificado 
	 * en la configuraci&oacute;n (ISPACConfiguration).
	 *
	 */
	public DbCnt() {
	    this.mpoolname = null;
	}


	/**
	 * El objeto utilizar&aacute; el pool de conexiones suministrado.
	 * @param poolname Nombre JNDI del pool de conexiones.
	 */
	public DbCnt(String poolname) 
	{
	    this.mpoolname = poolname;
	}

	/**
	 * El nombre JNDI que utiliza la conexi&oacute;n
	 * @return Nombre Nombre JNDI del pool de conexiones.
	 */
	public String getPoolName()
	{
	    return this.mpoolname;
	}


	/**
	 * Obtiene una conexión de la base de datos y la guarda para futuro uso.
	 * Adquiere la conexión de BBDD a partir del pool de conexiones especificado 
	 * en la construcci&oacute;n del objeto.
	 * @throws ISPACException
	 */
	public Connection getConnection() throws ISPACException {
		
		if (mcnt != null) {
			return mcnt;
		}

		try {
			
//		    if (mpoolname==null) {
//		    	ISPACConfiguration config = ISPACConfiguration.getInstance();
//		        mpoolname = config.get(ISPACConfiguration.POOLNAME);
//		    }

			if (mpoolname==null) {
				OrganizationUserInfo info = OrganizationUser.getOrganizationUserInfo();
				if (info != null) {
					
					if (logger.isDebugEnabled()) {
						logger.debug("OrganizationUserInfo: " 
								+ "userName: " + info.getUserName()
								+ "userId: " + info.getUserId()
								+ "organizationName: " + info.getOrganizationName()
								+ "organizationId: " + info.getOrganizationId());
					}
					
					mpoolname =  info.getSpacPoolName();
				} else {
			    	ISPACConfiguration config = ISPACConfiguration.getInstance();
			        mpoolname = config.get(ISPACConfiguration.POOLNAME);
				}
			}			
			
			
//			InitialContext v_ctx = new InitialContext();
//			mdataSource = (DataSource) v_ctx.lookup(mpoolname);
		    mdataSource = getDataSource(mpoolname);
			mcnt = mdataSource.getConnection();
			
			if (mcnt.getTransactionIsolation() != Connection.TRANSACTION_READ_COMMITTED)
				mcnt.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			
		} catch (Exception e) {
			
			logger.warn("Imposible obtener cnx: poolName: " + mpoolname);
			
			ISPACConfiguration config = ISPACConfiguration.getInstance();
			String sCntSource = config.get(ISPACConfiguration.URL);
			String sCntDriver = config.get(ISPACConfiguration.DRIVER);

			if (sCntSource==null||sCntDriver==null) {
			    throw new ISPACException(e);
			}

			getConnectionEx(sCntDriver,sCntSource);
		}
		
		return mcnt;
	}

	protected DataSource getDataSource(String dataSourceName) throws NamingException {
		
		DataSource dataSource = null;
		
		if (StringUtils.isNotBlank(dataSourceName)) {
			
			InitialContext v_ctx = new InitialContext();

			try {
				dataSource = (DataSource) v_ctx.lookup(dataSourceName);
			} catch (NamingException e) {
				if (dataSourceName.startsWith("java:comp/env/")) {
					String directPoolName = dataSourceName.substring("java:comp/env/".length());
					dataSource = (DataSource) v_ctx.lookup(directPoolName);
				} else {
					throw e;
				}
			}
		}
		
		return dataSource;
	}

	/**
	 * Obtiene una conexión de la base de datos y la guarda para futuro uso
	 *
	 */
	public void getConnectionEx(String connectDriver, String connectString) 
			throws ISPACException {
		
		if (mcnt != null) {
			return;
		}

		try {
			
		    if (connectDriver !=null && connectDriver.length()>0) {
			    Class driverclass=Class.forName(connectDriver);
			    if (!Driver.class.isAssignableFrom(driverclass)) {
					throw new ISPACException("La clase "
							+ connectDriver
							+ " no extiende la clase Driver");
			    }
			    DriverManager.registerDriver((Driver)driverclass.newInstance());
		    }
		    
			mcnt = DriverManager.getConnection(connectString);
			if (mcnt.getTransactionIsolation() != Connection.TRANSACTION_READ_COMMITTED)
				mcnt.setTransactionIsolation( Connection.TRANSACTION_READ_COMMITTED);
		} catch (Exception e) {
			throw new ISPACException("DbCnt:getConnectionEx(" + connectDriver 
					+ "," + connectString + ")", e);
		}
	}


	/**
	 * Libera la conexi&oacute;n obtenida por getConnection o getConnectionEx.
	 * @return true si se ha liberado correctamente
	 */
	public boolean closeConnection() {
		try {
			
			if (mcnt == null) {
			    return true;
			}

			mcnt.close();
			mcnt = null;
			return true;

		} catch (SQLException e) {
		    logger.warn("Error al cerrar una conexión. Datasource: "+mpoolname
		    		+" cnt: " + mcnt.toString());
		    return false;
		}
	}

	/**
	 * Realiza una consulta a la bbdd
	 *
	 * @param sql query
	 * @return objeto DbResultSet que encapsula la consulta y sus resultados
	 * @throws ISPACException
	 */
	public DbResultSet executeQuery(String sql) throws ISPACException {
		
		ResultSet rs = null;
		Statement stmt = null;
		
		if (logger.isDebugEnabled()) {
			logger.debug("SQL: " + sql);
		}
		
		try {
			stmt = mcnt.createStatement();
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			throw new ISPACException("Error SQL Query: "+sql,e);
		}

		return new DbResultSet(stmt,rs);
	}

	
	/**
	 * 
	 * @param sql Sql a ejecutar
	 * @return Cierto se la sentencia tuvo éxito falso en caso contrario
	 * @throws ISPACException
	 */
public boolean execute(String sql) throws ISPACException {
		
		boolean ok;
		Statement stmt = null;
		
		if (logger.isDebugEnabled()) {
			logger.debug("SQL: " + sql);
		}
		
		try {
			stmt = mcnt.createStatement();
			ok = stmt.execute(sql);
		} catch (SQLException e) {
			throw new ISPACException("Error SQL Query: "+sql,e);
		}

		return ok;
	}

	/**
	 * Obtiene las tablas de la base de datos.
	 * @param tableNamePattern Patrón de las tablas
	 * @return Objeto DbResultSet que encapsula la consulta y sus resultados.
	 * @throws ISPACException si ocurre algún error.
	 */
	public DbResultSet getTables(String tableNamePattern) 
			throws ISPACException {
		
		ResultSet rs = null;
		DatabaseMetaData dbdata = null;
		
		if (StringUtils.isBlank(tableNamePattern)) {
		    tableNamePattern = "%";
		}
		
		try {
			
			dbdata = mcnt.getMetaData();

			/* 
			 * Reglas de obtención de tablas y columnas:
			 *
			 * ORACLE: No existe catálogo y se deben buscar en el esquema del 
			 * usuario
			 *
			 * SQLSERVER: El esquema corresponde al propietario de las tablas. 
			 * Un usuario puede tener acceso a tablas en una base de datos de 
			 * las que no tiene porqué ser el propietario. La forma de buscarlas
			 * será restringiendo por BBDD que en el caso de SQLSERVER 
			 * corresponde al catálogo.
			 * 
			 * POSTGRESQL: El nombre de la tabla de ir en minúsculas.
			 *
			 */

			if (isOracleDbEngine()) {
				String schemePattern = ISPACConfiguration.getInstance().get(ISPACConfiguration.SCHEME_DBA);
				if (StringUtils.isBlank(schemePattern)) {
					schemePattern = null;
				}
				rs = dbdata.getTables(mcnt.getCatalog(), 
						schemePattern, //schemePattern
						tableNamePattern.trim().toUpperCase(), 
						null); // columnNamePattern
			} else if (isSqlServerDbEngine()) {
				rs = dbdata.getTables(mcnt.getCatalog(), 
						null, //schemePattern
						tableNamePattern.trim().toUpperCase(), 
						null); // columnNamePattern
			} else {
				rs = dbdata.getTables(mcnt.getCatalog(), 
						null, //schemePattern
						tableNamePattern.trim().toLowerCase(), 
						null); //columnNamePattern
			}
			
		} catch (SQLException e) {
			throw new ISPACException(e);
		}
		
		return new DbResultSet(null,rs);
	}

	/**
	 * Obtiene la información de una tabla en base de datos.
	 * @param tableName Nombre de la tabla.
	 * @return Información de la tabla.
	 * @throws ISPACException si ocurre algún error.
	 */
	public DbResultSet getColumns(String tableName) throws ISPACException {
		return getColumns(tableName, null);
	}

	/**
	 * Obtiene la información de una tabla en base de datos.
	 * @param tableName Nombre de la tabla.
	 * @param columnNamePattern Patrón del nombre de la columna.
	 * @return Información de la tabla.
	 * @throws ISPACException si ocurre algún error.
	 */
	public DbResultSet getColumns(String tableName, String columnNamePattern) 
			throws ISPACException {
		
		ResultSet rs = null;
		DatabaseMetaData dbdata = null;
		
		if (StringUtils.isBlank(tableName)) {
			throw new ISPACException("No se ha proporcionado un nombre de tabla o vista");
		}

		try {
			
			dbdata = mcnt.getMetaData();
			
			/* 
			 * Reglas de obtención de tablas y columnas:
			 *
			 * ORACLE: No existe catálogo y se deben buscar en el esquema del 
			 * usuario
			 *
			 * SQLSERVER: El esquema corresponde al propietario de las tablas. 
			 * Un usuario puede tener acceso a tablas en una base de datos de 
			 * las que no tiene porqué ser el propietario. La forma de buscarlas
			 * será restringiendo por BBDD que en el caso de SQLSERVER 
			 * corresponde al catálogo.
			 * 
			 * POSTGRESQL: El nombre de la tabla de ir en minúsculas.
			 *
			 */
			if (isOracleDbEngine()) {
				String schemePattern = ISPACConfiguration.getInstance().get(ISPACConfiguration.SCHEME_DBA);
				if (StringUtils.isBlank(schemePattern)) {
					schemePattern = null;
				}
				rs = dbdata.getColumns(mcnt.getCatalog(), 
						schemePattern, //schemePattern
						tableName.trim().toUpperCase(), 
						columnNamePattern);
			} else if (isSqlServerDbEngine()) {
				rs = dbdata.getColumns(mcnt.getCatalog(), 
						null, //schemePattern
						tableName.trim().toUpperCase(), 
						columnNamePattern);
			} else if (isDB2DbEngine()) {
				rs = dbdata.getColumns(null, 
						"%", //schemePattern
						tableName.trim().toUpperCase(), 
						"%");
			} else {
				rs = dbdata.getColumns(mcnt.getCatalog(), 
						null, //schemePattern
						tableName.trim().toLowerCase(), 
						columnNamePattern);
			}

		} catch (SQLException e) {
			throw new ISPACException(e);
		}

		return new DbResultSet(null, rs);
	}

	/**
	 * Realiza una consulta a la bbdd
	 *
	 * @param sql query
	 * @return objeto DbQuery que encapsula la consulta y sus resultados
	 * @throws ISPACException
	 */
	public DbQuery executeDbQuery(String sql) throws ISPACException {
		
		DbQuery dbq = null;

		if (logger.isDebugEnabled()) {
			logger.debug("SQL: " + sql);
		}

		try {
			Statement stmt = mcnt.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			dbq = new DbQuery(stmt, rs);
		} catch (SQLException e) {
			throw new ISPACException(e);
		}

		return dbq;
	}

	/**
	 * Realiza una operación INSERT, UPDATE ó DELETE sobre la conexión
	 *
	 * @param sql texto de la sentencia INSERT, UPDATE ó DELETE
	 * @return Número de filas insertadas, borradas o actualizadas, o
	 *         <code>-1</code> en caso de error
	 * @throws ISPACException
	 */
	public int directExec(final String sql) throws ISPACException  {
		
		int nRows = 0;

		if (logger.isDebugEnabled()) {
			logger.debug("SQL: " + sql);
		}

		try {
			Statement st = mcnt.createStatement();
			nRows = st.executeUpdate(sql);
			st.close();
		} catch (SQLException e) {
			throw new ISPACException(e);
		}
		return nRows;
	}

	/**
	 * Crea un objeto de tipo DbPreparedStatement a partir de la query pasada
	 * como argumento. Este objeto encapsula un query precompilada en bbdd, que
	 * tendr&aacute; una serie de parametros a inicializar
	 *
	 * @param sql query
	 * @return objeto DbPreparedStatement que encapsula la query precompilada y
	 *         parametrizada
	 * @throws ISPACException
	 */
	public PreparedStatement prepareStatement(String sql) 
			throws ISPACException {
		
		if (logger.isDebugEnabled()) {
			logger.debug("SQL: " + sql);
		}

		try
		{
			return mcnt.prepareStatement(sql);

		} catch (Exception e)
		{
			throw new ISPACException(e);
		}
	}

	/**
	 * Crea un objeto de tipo DbPreparedStatement a partir de la consulta pasada
	 * como argumento. Este objeto encapsula una consulta precompilada en bbdd, que
	 * tendr&aacute; una serie de parametros a inicializar
	 *
	 * @param sql query
	 * @return objeto DbPreparedStatement que encapsula la query precompilada y
	 *         parametrizada
	 * @throws ISPACException
	 */
	public DbPreparedStatement prepareDBStatement(String sql) 
			throws ISPACException {
		
		DbPreparedStatement dbps = null;

		if (logger.isDebugEnabled()) {
			logger.debug("SQL: " + sql);
		}

		try {
			PreparedStatement ps = mcnt.prepareStatement(sql);
			dbps = new DbPreparedStatement(ps);
		} catch (Exception e) {
			throw new ISPACException(e);
		}

		return dbps;
	}

	/**
	 * Crea un objeto de tipo DbCallableStatement que representa la llamada a un
	 * procedimiento almacenado
	 *
	 * @param sql bloque pl-sql que encapsula la llamada al procedimiento almacenado
	 * @return objeto de tipo DbCallalbleStatement
	 * @throws ISPACException
	 */
	public DbCallableStatement prepareCall(String sql) throws ISPACException {

		DbCallableStatement dbcs = null;

		if (logger.isDebugEnabled()) {
			logger.debug("SQL: " + sql);
		}

		try {
			CallableStatement cs = mcnt.prepareCall(sql);
			dbcs = new DbCallableStatement(cs);
		} catch (Exception e) {
			throw new ISPACException(e);
		}

		return dbcs;
	}

	/**
	 * Inicia una transacci&oacute;n para esta conexi&oacute;n.
	 *
	 * @throws ISPACException
	 */
	public void openTX() throws ISPACException {
		
		try {
			mcnt.setAutoCommit(false);
		} catch (SQLException e) {
			throw new ISPACException(e);
		}
	}

	/**
	 * Devuelve si existe o no una  transacci&oacute;n en curso
	 * @return  true si existe la transaccci&oacute;n
	 * @throws ISPACException
	 */
	public boolean ongoingTX() throws ISPACException {
		try {
			return !mcnt.getAutoCommit();
		} catch (SQLException e) {
			throw new ISPACException(e);
		}
	}


	/**
	 * Termina un contexto de transacción y restaura la conexión a modo
	 * autocommit.
	 *
	 * @param commit <code>true</code> para terminar con COMMIT, <code>false</code> para terminar con
	 *            ROLLBACK
	 * @return true si todo va bien, false si hay alg&uacute;n error.
	 * @throws ISPACException
	 */
	public void closeTX(boolean commit) throws ISPACException {
		try {
			if (commit) {
				mcnt.commit();
			} else {
				mcnt.rollback();
			}
			mcnt.setAutoCommit(true);
		} catch (SQLException e) {
			throw new ISPACException(e);
		}
	}

	/**
	 * Se ejecuta antes de eliminar de memoria el objeto
	 */
//	public void finalize()
//	{
		/*
		 * if (m_cnt != null) { try { if (m_cnt.getAutoCommit()) {
		 * System.out.println("WARNING!!! Liberando una transacción perdida.
		 * Nombre " + m_cnt.toString() + " \n"); m_cnt.rollback(); } }
		 * catch(SQLException e) { e.printStackTrace();
		 * System.out.println("ERROR!!! Error al cancelar una transacción.
		 * Nombre " + m_cnt.toString() + " \n");
		 *  } closeConnection(); }
		 */
//	}

	public String toString() {
		
		if (mcnt == null) {
			return "";
		}

		StringBuffer sRet = new StringBuffer("");

		Map typeMap;

		try {
			typeMap = mcnt.getTypeMap();
		} catch (SQLException e) {
			return "";
		}
		for (Iterator i = typeMap.keySet().iterator(); i.hasNext();) {
			Object key = i.next();
			sRet.append("Key: ");
			sRet.append(key);
			sRet.append("Value: ");
			sRet.append(typeMap.get(key));
		}
		return sRet.toString();
	}

	/**
	 * @return
	 * @throws ISPACException
	 */
	public String getProductName() throws ISPACException {
		return getProductName(mcnt);
	}

	public static String getProductName(Connection connection) throws ISPACException {
		try {
			DatabaseMetaData dbmd = connection.getMetaData();
			return dbmd.getDatabaseProductName().trim().toUpperCase();
		} catch (SQLException e) {
			throw new ISPACException(e);
		}
	}

	public int getMajorVersion(){
		return getMajorVersion(mcnt);
	}
	
	public int getMinorVersion(){
		return getMinorVersion(mcnt);
	}
	
	public static int getMajorVersion(Connection connection) {
		DatabaseMetaData dbmd;
		try {
			dbmd = connection.getMetaData();
			return dbmd.getDatabaseMajorVersion();
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}	
}
	
	public static int getMinorVersion(Connection connection) {
			DatabaseMetaData dbmd;
			try {
				dbmd = connection.getMetaData();
				return dbmd.getDatabaseMinorVersion();
			} catch (SQLException e) {
				throw new RuntimeException(e.getMessage());
			}	
	}

	public int getEngine() {
		if (idEngine < 0) {
			idEngine = getEngine(mcnt);
		}
		return idEngine;
	}

	public static int getEngine(Connection connection) {
		try {
			String productName = getProductName(connection);
			if (productName.equalsIgnoreCase(ORACLE_DB_NAME)) {
				return ORACLE_ENGINE_ID;
			}else if (productName.equalsIgnoreCase(SQLSERVER_DB_NAME)) {
				return SQLSERVER_ENGINE_ID;
			}else if (productName.equalsIgnoreCase(POSTGRESQL_DB_NAME)) {
				return POSTGRESQL_ENGINE_ID;
			}else if (productName.startsWith(DB2_DB_NAME)) {
				return DB2_ENGINE_ID;
			}else{
				throw new RuntimeException("Base de datos no soportada por la aplicacion");
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public boolean isSqlServerDbEngine() {
		return getEngine()==SQLSERVER_ENGINE_ID;
	}
	public boolean isOracleDbEngine() {
		return getEngine()==ORACLE_ENGINE_ID;
	}
	public boolean isPostgreSQLDbEngine() {
		return getEngine()==POSTGRESQL_ENGINE_ID;
	}
	public boolean isDB2DbEngine() {
		return getEngine()==DB2_ENGINE_ID;
	}
	
	public void setConnection(Connection cnt) {
		this.mcnt = cnt;
	}

}