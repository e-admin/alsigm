
package ieci.tdw.ispac.ispaclib.db;

import ieci.tdw.ispac.api.errors.ISPACException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class DbResultSet
{
	// Query que representa al objeto
	private final Statement mstmt;
	// Resultados de la query
	private final ResultSet mrs;

	/**
	 * Constructor
	 * 
	 * @param stmt objeto que representa la query
	 * @param rs resultset de la query
	 */
	public DbResultSet(Statement stmt, ResultSet rs)
	{
		mstmt = stmt;
		mrs = rs;
	}

	public ResultSet getResultSet()
	{
		return mrs;
	}
	public Statement getStatement()
	{
		return mstmt;
	}
	
	/**
	 * Cierra el objeto DbResultSet liberando recursos
	 * 
	 */
	public void close() throws ISPACException
	{
		try
		{
			if (mrs != null)
				mrs.close();
			
			if (mstmt != null)
				mstmt.close();
		} 
		catch (SQLException e)
		{
			throw new ISPACException("Error en DbResultSet.close()", e);
		}
	}
}
