package common.db;

import ieci.core.db.DbConnection;

import org.apache.log4j.Logger;

import common.exceptions.DBException;
import common.startup.ProfileLogLevel;

public class TransactionManagerImpl implements ITransactionManager {

	private boolean allowHint = true;

	private String dbFactoryClass;

	/** Logger de la clase */
	static Logger logger = Logger.getLogger(TransactionManagerImpl.class);

	public boolean getAllowHint() {
		return allowHint;
	}

	protected static final Logger PROFILE_LOGGER = Logger.getLogger("PROFILE");

	public final static int COMMIT_TRANSACCION = 1;

	public final static int ROLLBACK_TRANSACCION = 0;

	DbDataSource dataSource = null;
	private DbConnection conexion = null;

	public TransactionManagerImpl(DbDataSource dataSource) {
		this.dataSource = dataSource;
		this.allowHint = dataSource.getAllowHint();
	}

	// contructor vacio
	TransactionManagerImpl() {
	}

	int openContexts = 0;

	public DbConnection iniciarTransaccion() {
		// ya tenemos conexion
		try {
			if (this.conexion != null) {
				if (!this.conexion.inTransaction()) {
					this.conexion.beginTransaction();
				}
			} else {
				this.conexion = obtenerConexion();
				this.conexion.beginTransaction();
			}
			openContexts++;
			if (PROFILE_LOGGER.isEnabledFor(ProfileLogLevel.BEGIN_TX))
				PROFILE_LOGGER.log(ProfileLogLevel.BEGIN_TX,
						String.valueOf(System.currentTimeMillis()));

			return this.conexion;
		} catch (Exception e) {
			logger.error("Error al iniciar la Transacción", e);
			throw new DataAccessException();
		}
	}

	public void commit() throws DBException {
		if (--openContexts == 0) {
			completarTransaccion(COMMIT_TRANSACCION);
			if (PROFILE_LOGGER.isEnabledFor(ProfileLogLevel.COMMIT_TX))
				PROFILE_LOGGER.log(ProfileLogLevel.COMMIT_TX,
						String.valueOf(System.currentTimeMillis()));
		}
	}

	public void rollback() throws DBException {
		completarTransaccion(ROLLBACK_TRANSACCION);
		this.openContexts = 0;
		if (PROFILE_LOGGER.isEnabledFor(ProfileLogLevel.ROLLBACK_TX))
			PROFILE_LOGGER.log(ProfileLogLevel.ROLLBACK_TX,
					String.valueOf(System.currentTimeMillis()));
	}

	private void completarTransaccion(int status) throws DBException {
		try {
			if (transactionInProgress()) {
				boolean commitTransaccion = false;
				if (status == COMMIT_TRANSACCION)
					commitTransaccion = true;
				conexion.endTransaction(commitTransaccion);
			}
		} catch (Exception e) {
			throw new DBException(e);
		} finally {
			liberarConexion();
		}
	}

	public DbConnection obtenerConexion() {
		try {
			if (this.conexion == null)
				this.conexion = dataSource.obtenerConexion();
			return this.conexion;
		} catch (Exception e) {
			throw new DBException(e);
		}
	}

	public void liberarConexion() throws DBException {
		if (!transactionInProgress()) {
			try {
				if (conexion != null)
					conexion.close();
				if (dataSource != null)
					dataSource.liberarConexion();
			} catch (Exception e) {
				throw new DBException(e);
			}
			this.conexion = null;
		}
	}

	public boolean transactionInProgress() {
		if (conexion == null)
			return false;
		return conexion.inTransaction();
	}

	public void setDbFactoryClass(String dbFactoryClass) {
		this.dbFactoryClass = dbFactoryClass;
	}

	public String getDbFactoryClass() {
		return dbFactoryClass;
	}
}