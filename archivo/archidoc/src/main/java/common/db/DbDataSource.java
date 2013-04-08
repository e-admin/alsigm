package common.db;

import ieci.core.db.DbConnection;

import common.exceptions.DBException;

public interface DbDataSource {

	public DbConnection obtenerConexion();

	public void liberarConexion() throws DBException;

	public boolean getAllowHint();

	public String getDbFactoryClass();

	public void setDbFactoryClass(String dbFactoryClass);
}
