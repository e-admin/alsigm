package common.db;

import ieci.core.db.DbConnection;

import common.exceptions.DBException;

public interface ITransactionManager extends DbDataSource {

	public final static int COMMIT_TRANSACCION = 1;
	public final static int ROLLBACK_TRANSACCION = 0;

	public DbConnection iniciarTransaccion();

	public void commit() throws DBException;

	public void rollback() throws DBException;

	public DbConnection obtenerConexion();

	public void liberarConexion() throws DBException;

	public boolean transactionInProgress();

}