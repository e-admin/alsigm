package ieci.tdw.ispac.ispaclib.invesicres.registro.api;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tecdoc.core.db.DbConnectionConfig;
import ieci.tecdoc.sbo.idoc.api.Archive;
import ieci.tecdoc.sbo.idoc.api.ArchiveObject;
import ieci.tecdoc.sbo.idoc.api.Folder;
import ieci.tecdoc.sbo.idoc.api.FolderObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

/**
 * Conexión con inveSicres.
 *
 */
public class InvesDocConnection {
       
	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(InvesDocConnection.class);
	
	private DbConnectionConfig dbConConfig = null;
	private Connection connection = null;
	private Archive archiveApi = null;
	private Folder folderApi = null;
	private String url = null;
	private String dbUser = null;
	private String dbPass = null;
	private int iDocUserId = 0;
	private String iDocUserName = null;
	private String ctxName = null;
	private DataSource mDataSource = null;
	
	private boolean connected = false;
	private boolean startTrans = false;
	
	
	/**
	 * Inicializa la conexión a la Base de datos usando un Pool de Conexiones
	 * @param ctxName El nombre JNDI que está asociado al DataSource de conexión
	 * @throws ISPACException
	 */
	public InvesDocConnection(String ctxName) throws ISPACException {
		this.ctxName = ctxName;
		//Conectar a la base de datos
		dbConConfig = new DbConnectionConfig(ctxName, null, null);
		try {
			//Obtener interface para acceso a los Archivadores
			archiveApi = new Archive();
			archiveApi.setConnectionConfig(dbConConfig);
			//Obtener interface para acceso a las Carpetas
			folderApi = new Folder();
			folderApi.setConnectionConfig(dbConConfig);
		} catch (Exception e) {
			throw new ISPACException("Error inicializando conexión a la base de datos.", e);
		}			    
	}
	
	/**
	 * Inicializa la conexión a la Base de datos usando un Pool de Conexiones
	 * @param ctxName El nombre JNDI que está asociado al DataSource de conexión
	 * @param iDocUserId Identificador del Usuario invesDoc
	 * @param iDocUserName Nombre del Usuario invesDoc
	 * @throws ISPACException
	 */
	public InvesDocConnection(String ctxName, int iDocUserId, String iDocUserName) throws ISPACException {
		this(ctxName);
		this.iDocUserId = iDocUserId;
		this.iDocUserName = iDocUserName;
	}
	
	/**
	 * Inicializa la conexión a la Base de datos usando una conexión directa
	 * @param drvClsName Nombre de la clase java que implementa el driver JDBC.
	 * @param url URL de conexión con la base de datos.
	 * @param dbuser Usuario de conexión a la base de datos.
	 * @param dbpwd Clave del usuario de conexión a la base de datos.
	 * @throws ISPACException si ocurre algún error.
	 */
	public InvesDocConnection(String drvClsName, String url, String dbuser, String dbpwd) throws ISPACException {
		this.ctxName = null;
		this.url = url;
		this.dbUser = dbuser;
		this.dbPass = dbpwd;

		//Conectar a la base de datos
		dbConConfig = new DbConnectionConfig(drvClsName, url, dbuser, dbpwd);
		
		try {
			//Obtener interface para acceso a los Archivadores
			archiveApi = new Archive();
			archiveApi.setConnectionConfig(dbConConfig);
			//Obtener interface para acceso a las Carpetas
			folderApi = new Folder();
			folderApi.setConnectionConfig(dbConConfig);
		} catch (Exception e) {
			throw new ISPACException("Error inicializando conexión a la base de datos.", e);
		}			    
	}

	
	/**
	 * Establece la conexión a la base de datos
	 * @throws ISPACException
	 */
	public void Connect() throws ISPACException {
		try {
			if (ctxName != null) {
//				InitialContext v_ctx = new InitialContext();
//				mDataSource = (DataSource) v_ctx.lookup(ctxName);
				mDataSource = getDataSource(ctxName);
				connection = mDataSource.getConnection();
				connected = true;
			} else {
				connection = DriverManager.getConnection(url, dbUser, dbPass);
				connected = true;
			}
		} catch (Exception e) {
			throw new ISPACException("Error conectando a la base de datos.", e);
		}
	}

	protected DataSource getDataSource(String dataSourceName) throws NamingException {
		
		InitialContext v_ctx = new InitialContext();

		try {
			return (DataSource) v_ctx.lookup(dataSourceName);
		} catch (NamingException e) {
			if (dataSourceName.startsWith("java:comp/env/")) {
				String directPoolName = dataSourceName.substring("java:comp/env/".length());
				return (DataSource) v_ctx.lookup(directPoolName);
			} else {
				throw e;
			}
		}
	}

	/**
	 * Desconecta de la base de datos
	 */
	public void Disconnect() {
		try {
			if (connection != null)	connection.close();
			connection = null;
			connected = false;
		} catch (SQLException e) {
		}
	}
	
	/**
	 * Inicia transación en la base de datos
	 * @throws ISPACException
	 */
	public void StartTrans() throws ISPACException {
		if (!connected) throw new ISPACException("No esta conectado a la base de datos");
		try {
			if (connection != null) connection.setAutoCommit(false);
			startTrans = true;
		} catch (SQLException e) {
			throw new ISPACException("Error al iniciar transación.", e);
		}
	}
	
	/**
	 * Confirma transación en la base de datos
	 * @throws ISPACException
	 */
	public void CommitTrans() throws ISPACException {
		if (!connected) throw new ISPACException("No esta conectado a la base de datos");
		try {
			if (connection != null && startTrans) {
				startTrans = false;
				connection.commit();
				connection.setAutoCommit(true);
			}
		} catch (SQLException e) {
			throw new ISPACException("Error al confirmar la transación.", e);
		}
	}
	
	/**
	 * Cancela transación en la base de datos
	 * @throws ISPACException
	 */
	public void RollbackTrans() throws ISPACException {
		if (!connected) throw new ISPACException("No esta conectado a la base de datos");
		try {
			if (connection != null && startTrans) {
				startTrans = false;
				connection.rollback();
				connection.setAutoCommit(true);
			} 
		} catch (SQLException e) {
			throw new ISPACException("Error al cancelar la transación.", e);
		}
	}
		
	/**
	 * Carga un archivador
	 * @param id Identificador del Archivador
	 * @return Devuelve un objeto que hace referencia al Archivador
	 * @throws ISPACException
	 */
	public ArchiveObject LoadArchive(int id) throws ISPACException {
		if (!connected) throw new ISPACException("No esta conectado a la base de datos");
		try {
			return archiveApi.loadArchive(null, id);
		} catch (Exception e) {
			throw new ISPACException( 
					"Error al cargar archivador (id:" + id + ").", e);
		}
	}
		
	/**
	 * Carga una carpeta dado su Identificador
	 * @param archive Objeto que hace referencia al Archivador que contiene la carpeta
	 * @param folderId Identificador de la carpeta
	 * @return Devuelve un objeto que hace referencia a la Carpeta
	 * @throws ISPACException
	 */
	public FolderObject LoadFolderFromId(ArchiveObject archive, int folderId) throws ISPACException {
		if (!connected) throw new ISPACException("No esta conectado a la base de datos");
		try {
			return folderApi.loadFolder(null, iDocUserId, archive, folderId); 
		} catch (Exception e) {
			throw new ISPACException(
					"Error al cargar carpeta (ArchiveId:" + archive.getId() + " / FolderId:" + folderId + ").",
					e);
		}
		
	}
	
	/**
	 * Carga una carpeta dado su Identificador
	 * @param archiveId Identificador del Archivador que contiene la carpeta
	 * @param folderId Identificador de la carpeta
	 * @return Devuelve un objeto que hace referencia a la Carpeta
	 * @throws ISPACException
	 */
	public FolderObject LoadFolderFromId(int archiveId, int folderId) throws ISPACException {
		ArchiveObject arch = LoadArchive(archiveId);
		return LoadFolderFromId(arch, folderId);
	}
	
	/**
	 * Carga una carpeta que cumple una condición
	 * @param archive Objeto que hace referencia al Archivador que contiene la carpeta
	 * @param qual Condición SQL que debe cumplir la carpeta
	 * @return Devuelve un objeto que hace referencia a la Carpeta
	 * @throws ISPACException
	 */
	public FolderObject LoadFolderFromQual(ArchiveObject archive, String qual) throws ISPACException{
		if (!connected) throw new ISPACException("No esta conectado a la base de datos");
		String strFdrId = DbSelect1C1R("FDRID", "A" + archive.getId() + "SF", qual);
		if (strFdrId == null || strFdrId.length() == 0) return null;
		try {
			int fdrId = Integer.parseInt(strFdrId);
			return LoadFolderFromId(archive, fdrId);
		} catch (NumberFormatException e) {
			return null;
		}
		
	}
	
	/** 
	 * Carga una carpeta que cumple una condición
	 * @param archiveId Identificador del Archivador que contiene la carpeta
	 * @param qual Condición SQL que debe cumplir la carpeta
	 * @return Devuelve un objeto que hace referencia a la Carpeta
	 * @throws ISPACException
	 */
	public FolderObject LoadFolderFromQual(int archiveId, String qual) throws ISPACException {
		ArchiveObject arch = LoadArchive(archiveId);
		return LoadFolderFromQual(arch, qual);
	}

	
	/**
	 * Ejecuta una consulta sobre la base de datos y retorna el dato 
	 * de una columna de la primera fila encontrada
	 * @param column Columna de la cual se desea obtener el dato
	 * @param table Tabla sobre la que se ejecutará la consulta
	 * @param qual Condición SQL para obtener la consulta
	 * @return Devuelve el valor del campo indicado en el parametro <code>column</code>
	 * @throws ISPACException
	 */
	public String DbSelect1C1R(String column, String table, String qual) throws ISPACException {
		if (!connected) throw new ISPACException("No esta conectado a la base de datos");
		ResultSet rset = null;
		Statement stmt = null;
		String result = null;
		String sql = "SELECT " + column + " FROM " + table + " " + qual;

		column = column.trim();
		int blanco = column.indexOf(' ');
		if (blanco > 0){
			String columnAux = column.substring(blanco);
			column = columnAux;
		}
		
		try {
			stmt = connection.createStatement();
			if (stmt.execute(sql)) {
				rset = stmt.getResultSet();
				if (rset.next()) result = rset.getString(column);
			}
		} catch (SQLException e) {
			throw new ISPACException("Error al ejecutar una consulta SQL.", e);
		} finally {
			if (rset != null) {
				try {
					rset.close();
				} catch (SQLException e) {
					logger.warn("Error al cerrar el resultSet", e);
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					logger.warn("Error al cerrar el statement", e);
				}
			}
		}
		
		return result;
	}
	
	/**
	 * Ejecuta una consulta sobre la base de datos y retorna N columnas de la
	 * primera fila encontrada.
	 * @param columns Lista de las columnas a obtener
	 * @param table Tabla sobre la que se ejecuta la consulta
	 * @param qual Condición SQL para obtener la consulta
	 * @return Devuelve una lista de [CLAVE]/[VALOR] compuesta por [NOMBRE CAMPO]/[VALOR CAMPO]
	 * @throws ISPACException
	 */
	public LinkedHashMap DbSelectNC1R(String columns, String table, String qual) throws ISPACException {
		if (!connected) throw new ISPACException("No esta conectado a la base de datos");
		ResultSet rset = null;
		Statement stmt = null;
		LinkedHashMap result = null;
		String[] cols = columns.split(",");

		for (int i = 0; i < cols.length; i++) {
			String column = cols[i].trim();
			int blanco = column.lastIndexOf(' ');
			if (blanco > 0){
				String columnAux = column.substring(blanco+1);
				cols[i] = columnAux.trim();
			}
		}
		
		
		//Componer sql
		String sql = "SELECT " + columns + " FROM " + table + " " + qual;
		//Ejecutar consulta
		try {
			stmt = connection.createStatement();
			if (stmt.execute(sql)) {
				result = new LinkedHashMap();
				rset = stmt.getResultSet();
				if (rset.next()) {
					for (int i = 0; i < cols.length; i++) {
						String column = cols[i].trim();
						result.put(column, rset.getString(column));
					}
				}
			}
		} catch (SQLException e) {
			throw new ISPACException("Error al ejecutar una consulta SQL.", e);
		} finally {
			if (rset != null) {
				try {
					rset.close();
				} catch (SQLException e) {
					logger.warn("Error al cerrar el resultSet", e);
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					logger.warn("Error al cerrar el statement", e);
				}
			}
		}
		
		return result;
	}
	
	public LinkedList DbSelectNCNR(String columns, String table, String qual) throws ISPACException {
		if (!connected) throw new ISPACException("No esta conectado a la base de datos");
		ResultSet rset = null;
		Statement stmt = null;
		LinkedList result = null;
		String[] cols = columns.split(",");
		
		for (int i = 0; i < cols.length; i++) {
			String column = cols[i].trim();
			int blanco = column.lastIndexOf(' ');
			if (blanco > 0){
				String columnAux = column.substring(blanco+1);
				cols[i] = columnAux.trim();
			}
		}
		
		//Componer sql
		String sql = "SELECT " + columns + " FROM " + table + " " + qual;
		try {
			//Ejecutar Consulta
			stmt = connection.createStatement();
			if (stmt.execute(sql)) {
				rset = stmt.getResultSet();
				result = new LinkedList();
				while (rset.next()) {
					LinkedHashMap row = new LinkedHashMap();
					for (int i = 0; i < cols.length; i++) {
						String column = cols[i].trim();
						row.put(column, rset.getString(column));
					}
					result.add(row);
				}
			}
		} catch (SQLException e) {
			throw new ISPACException("Error al ejecutar una consulta SQL.", e);
		} finally {
			if (rset != null) {
				try {
					rset.close();
				} catch (SQLException e) {
					logger.warn("Error al cerrar el resultSet", e);
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					logger.warn("Error al cerrar el statement", e);
				}
			}
		}
		return result;
	}
	
	public FolderObject newFolder(ArchiveObject archive) throws ISPACException {
		try {
			return folderApi.newFolder(null,archive);
		} catch (Exception e) {
			throw new ISPACException("Error al crear una carpeta para el archivador " + archive.getName(), e);
		}
	}
	
	public FolderObject newFolder(int archiveId) throws ISPACException {
		ArchiveObject archive = LoadArchive(archiveId);
		return newFolder(archive);
	}
	
	public void createFolder(ArchiveObject archive, FolderObject folder) throws ISPACException {
		try {
			folderApi.createFolder(iDocUserId, archive, folder);
		} catch (Exception e) {
			throw new ISPACException("Error al guardar la carpeta.", e);
		}
	}
	
	public void createFolder(int archiveId, FolderObject folder) throws ISPACException {
		ArchiveObject archive = LoadArchive(archiveId);
		createFolder(archive, folder);
	}
	
	public void storeFolder(ArchiveObject archive, FolderObject folder) throws ISPACException {
		try {
			folderApi.editFolder(null, iDocUserId, archive, folder.getId());
			folderApi.storeFolder(iDocUserId, archive, folder);
			folderApi.terminateEditFolder(iDocUserId, archive, folder.getId());
		} catch (Exception e) {
			throw new ISPACException("Error al guardar la carpeta.", e);
		}
	}
	
	public void storeFolder(int archiveId, FolderObject folder) throws ISPACException {
		ArchiveObject archive = LoadArchive(archiveId);
		storeFolder(archive, folder);
	}
	
	/**
	 * @return Retorna archiveApi.
	 */
	public Archive getArchiveApi() {
		return archiveApi;
	}
	/**
	 * @return Retorna connection.
	 */
	public Connection getConnection() {
		return connection;
	}
	/**
	 * @return Retorna dbConConfig.
	 */
	public DbConnectionConfig getDbConConfig() {
		return dbConConfig;
	}
	/**
	 * @return Retorna dbUser.
	 */
	public String getDbUser() {
		return dbUser;
	}
	/**
	 * @return Retorna folderApi.
	 */
	public Folder getFolderApi() {
		return folderApi;
	}
	/**
	 * @return Retorna connected.
	 */
	public boolean isConnected() {
		return connected;
	}
    /**
     * @return Retorna iDocUserId.
     */
    public int getIDocUserId() {
        return iDocUserId;
    }
    /**
     * @return Retorna iDocUserName.
     */
    public String getIDocUserName() {
        return iDocUserName;
    }
    
	/* ============================================
	 * Constantes para los motores de base de datos
     * ============================================
     */
    
	/** Identificador interno del sistema de base datos. */
	private int idEngine = DbCnt.NO_ENGINE_ID;
    
	private int getEngine() {
		if (idEngine < 0) {
			idEngine = DbCnt.getEngine(connection);
		}
		
		return idEngine;
	}
	
	public boolean isSqlServerDbEngine() {
		return getEngine()==DbCnt.SQLSERVER_ENGINE_ID;
	}
	public boolean isOracleDbEngine() {
		return getEngine()==DbCnt.ORACLE_ENGINE_ID;
	}
	public boolean isPostgreSQLDbEngine(){
		return getEngine()==DbCnt.POSTGRESQL_ENGINE_ID;
	}
	public boolean isDB2DbEngine(){
		return getEngine()==DbCnt.DB2_ENGINE_ID;
	}
	
}
