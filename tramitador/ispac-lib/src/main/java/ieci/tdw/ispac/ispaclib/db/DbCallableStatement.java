package ieci.tdw.ispac.ispaclib.db;

import ieci.tdw.ispac.api.errors.ISPACException;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbCallableStatement extends DbPreparedStatement
{
    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger
    .getLogger(DbCallableStatement.class);

	// procedimiento almacenado
	private CallableStatement mcs;

	/**
	 * Constructor
	 *
	 * @param cs
	 *            procedimiento almacenado
	 */
	public DbCallableStatement(CallableStatement cs)
	{
		super(cs);
		mcs = cs;
	}

	/**
	 * Cierra el objeto que representa el procedimiento almacenado @result true
	 * si la operacion tuvo exito; false en caso contrario
	 */
	public boolean close()
	{
		try
		{
			mcs.close();
			return true;
		} catch (SQLException e)
		{
		    logger.warn("DbCallableStatement::close()"+ e.toString());
			return false;
		}
	}

	/**
	 * Ejecuta el procedimiento almacenado @result true si la operacion tuvo
	 * exito; false en caso contrario
	 */
	public void execute() throws ISPACException
	{
		try
		{
			mcs.execute();
		}
    catch (SQLException e)
		{
			throw new ISPACException ("DbCallableStatement::execute (), error", e);
		}
	}

	/**
	 * Obtiene el valor del parametro de indice 'paramIndex' en formato String
	 * @param paramIndex indice del parametro
	 */
	public String getString(int paramIndex) throws ISPACException
	{
    try
    {
      return mcs.getString(paramIndex);
    }
    catch (SQLException e)
    {
      throw new ISPACException ("DbCallableStatement::getString (" + paramIndex + "), error", e);
    }
	}

	/**
	 * Obtiene el valor del parametro de nombre 'paramName' en formato String
	 * @param paramName nombre del parametro
	 */
	public String getString(String paramName) throws ISPACException
	{
    try
    {
      return mcs.getString(paramName);
    }
    catch (SQLException e)
    {
      throw new ISPACException ("DbCallableStatement::getString (" + paramName + "), error", e);
    }
	}

	/**
	 * Obtiene el valor del parametro de indice 'paramIndex' en formato int
	 * @param paramIndex indice del parametro
	 */
	public int getInt(int paramIndex) throws ISPACException
	{
    try
    {
      return mcs.getInt(paramIndex);
    }
    catch (SQLException e)
    {
      throw new ISPACException ("DbCallableStatement::getInt (" + paramIndex + "), error", e);
    }

	}

	/**
	 * Obtiene el valor del parametro de nombre 'paramName' en formato int
	 * @param paramName nombre del parametro
	 */
	public int getInt(String paramName) throws ISPACException
	{
    try
    {
      return mcs.getInt(paramName);
    }
    catch (SQLException e)
    {
      throw new ISPACException ("DbCallableStatement::getInt (" + paramName + "), error", e);
    }
	}

	/**
	 * Obtiene el valor del parametro de indice 'paramIndex' en formato Date
	 * @param paramIndex indice del parametro
	 */
	public Date getDate(int paramIndex) throws ISPACException
	{
    try
    {
      return mcs.getDate(paramIndex);
    }
    catch (SQLException e)
    {
      throw new ISPACException ("DbCallableStatement::getDate (" + paramIndex + "), error", e);
    }
	}

	/**
	 * Obtiene el valor del parametro de nombre 'paramName' en formato Date
	 * @param paramName nombre del parametro
	 */
	public Date getDate(String paramName) throws ISPACException
	{
    try
    {
      return mcs.getDate(paramName);
    }
    catch (SQLException e)
    {
      throw new ISPACException ("DbCallableStatement::getDate (" + paramName + "), error", e);
    }
	}

	/**
	 * Obtiene el valor del parametro de indice 'paramIndex' en formato DbQuery
	 * @param paramIndex indice del parametro
	 */
	public DbQuery getDbQuery(int paramIndex) throws ISPACException
	{
    try
    {
  		DbQuery dbq = new DbQuery(null, (ResultSet) mcs.getObject(paramIndex));
    	return dbq;
    }
    catch (SQLException e)
    {
      throw new ISPACException ("DbCallableStatement::getDbQuery (" + paramIndex + "), error", e);
    }
	}

	/**
	 * Obtiene el valor del parametro de nombre 'paramName' en formato Object
	 * @param paramName nombre del parametro
	 */
	public DbQuery getDbQuery(String paramName) throws ISPACException
	{
    try
    {
      DbQuery dbq = new DbQuery(null, (ResultSet) mcs.getObject(paramName));
      return dbq;
    }
    catch (SQLException e)
    {
      throw new ISPACException ("DbCallableStatement::getDbQuery (" + paramName + "), error", e);
    }
	}

	/**
	 * Registra los parametros de salida especificado el tipo de estos
	 * @param paramIndex indice del parametro
	 * @param type definidos segun java.sql.Types
	 */
	public void registerOutputParameter(int paramIndex, int type)
			throws ISPACException
	{
    try
    {
  		mcs.registerOutParameter(paramIndex, type);
    }
    catch (SQLException e)
    {
      throw new ISPACException ("DbCallableStatement::registerOutputParameter (" + paramIndex +"," + type + "), error", e);
    }
	}
}
