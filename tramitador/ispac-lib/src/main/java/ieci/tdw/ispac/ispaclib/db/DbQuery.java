package ieci.tdw.ispac.ispaclib.db;

import ieci.tdw.ispac.api.errors.ISPACException;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

public class DbQuery
{
    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger
    .getLogger(DbQuery.class);
	// Query que representa al objeto
	private final Statement mStmt;

	// Resultados de la query
	private final ResultSet mrs;

	/**
	 * Constructor
	 *
	 * @param stmt objeto que representa la query
	 * @param rs resultset de la query
	 */
	public DbQuery(Statement stmt, ResultSet rs)
	{
		mStmt = stmt;
		mrs = rs;
	}

	/**
	 * Cierra el objeto DbQuery liberando recursos
	 *
	 * @result true si la operacion tuvo exito; false en caso contrario
	 */
	public boolean close()
	{
		try
		{
			if (mrs != null)
				mrs.close();

			if (mStmt != null)
				mStmt.close();

			return true;
		}
		catch (SQLException e)
		{
		    logger.warn("DbQuery::close()"+ e.toString());
			return false;
		}
	}

	/**
	 * Avanza al siguiente registro de los resultados obtenidos.
	 */
	public boolean next() throws ISPACException
	{
		try
		{
			return mrs.next();
		} catch (SQLException e)
		{
			throw new ISPACException("DbQuery::next(), error", e);
		}
	}

	/**
	 * Obtiene el valor de la columna de indice 'columnIndex' en formato String
	 *
	 * @param columnIndex indice de la columna
	 */
	public String getString(int columnIndex) throws ISPACException
	{
		try
		{
			return mrs.getString(columnIndex);
		} catch (SQLException e)
		{
			throw new ISPACException("DbQuery::getString(), error", e);
		}

	}

	/**
	 * Obtiene el valor de la columna de nombre 'columnName' en formato String
	 *
	 * @param columnName nombre de la columna
	 */
	public String getString(String columnName) throws ISPACException
	{
		try
		{
			return mrs.getString(columnName);
		} catch (SQLException e)
		{
			throw new ISPACException("DbQuery::getString(), error", e);
		}

	}

	/**
	 * Obtiene el valor de la columna de indice 'columnIndex' en formato int
	 *
	 * @param columnIndex indice de la columna
	 */
	public int getInt(int columnIndex) throws ISPACException
	{
		try
		{
			return mrs.getInt(columnIndex);
		} catch (SQLException e)
		{
			throw new ISPACException("DbQuery::getInt(), error", e);
		}

	}

	/**
	 * Obtiene el valor de la columna de nombre 'columnName' en formato int
	 *
	 * @param columnName nombre de la columna
	 */
	public int getInt(String columnName) throws ISPACException
	{
		try
		{
			return mrs.getInt(columnName);
		} catch (SQLException e)
		{
			throw new ISPACException("DbQuery::getInt(), error", e);
		}

	}

	/**
	 * Obtiene el valor de la columna de indice 'columnIndex' en formato Date
	 *
	 * @param columnIndex indice de la columna
	 */
	public Date getDate(int columnIndex) throws ISPACException
	{
		try
		{
			return mrs.getDate(columnIndex);
		} catch (SQLException e)
		{
			throw new ISPACException("DbQuery::getDate(), error", e);
		}
	}

	/**
	 * Obtiene el valor de la columna de nombre 'columnName' en formato Date
	 *
	 * @param columnName nombre de la columna
	 */
	public Date getDate(String columnName) throws ISPACException
	{
		try
		{
			return mrs.getDate(columnName);
		} catch (SQLException e)
		{
			throw new ISPACException("DbQuery::getDate(), error", e);
		}
	}
	/**
	 * Obtiene el valor de la columna de indice 'columnIndex' en formato Time
	 *
	 * @param columnIndex indice de la columna
	 */
	public Time getTime(int columnIndex) throws ISPACException{
		try
		{
			return mrs.getTime(columnIndex);
		} catch (SQLException e)
		{
			throw new ISPACException("DbQuery::getTime(), error", e);
		}
		
	}

	/**
	 * Obtiene el valor de la columna de nombre 'columnName' en formato Time
	 *
	 * @param columnName nombre de la columna
	 */
	public Time getTime(String columnName) throws ISPACException{
		try
		{
			return mrs.getTime(columnName);
		} catch (SQLException e)
		{
			throw new ISPACException("DbQuery::getTime(), error", e);
		}
		
	}

	/**
	 * Obtiene el valor de la columna de indice 'columnIndex' en formato Timestamp
	 *
	 * @param columnIndex indice de la columna
	 */
	public Timestamp getTimestamp(int columnIndex) throws ISPACException{
		try
		{
			return mrs.getTimestamp(columnIndex);
		} catch (SQLException e)
		{
			throw new ISPACException("DbQuery::getTimestamp(), error", e);
		}
		
	}

	/**
	 * Obtiene el valor de la columna de nombre 'columnName' en formato Timestamp
	 *
	 * @param columnName nombre de la columna
	 */
	public Timestamp getTimestamp(String columnName) throws ISPACException{
		try
		{
			return mrs.getTimestamp(columnName);
		} catch (SQLException e)
		{
			throw new ISPACException("DbQuery::getTimestamp(), error", e);
		}
		
	}
	
	
	/**
	 * Obtiene el valor de la columna de indice 'columnIndex' en formato Blob
	 *
	 * @param columnIndex indice de la columna
	 */
	public Blob getBLOB(int columnIndex)
	throws ISPACException
	{
		try
		{
			return mrs.getBlob(columnIndex);
		} catch (SQLException e)
		{
			throw new ISPACException("DbQuery::getBLOB(), error", e);
		}
	}

	/**
	 * Obtiene el valor de la columna de nombre 'columnName' en formato Blob
	 *
	 * @param columnName nombre de la columna
	 */
	public Blob getBLOB(String columnName) throws ISPACException
	{
		try
		{
			return mrs.getBlob(columnName);
		} catch (SQLException e)
		{
			throw new ISPACException("DbQuery::getBLOB(), error", e);
		}
	}
	public InputStream getBinaryStream(String columnName)throws ISPACException 
	{
		try
		{
			return mrs.getBinaryStream(columnName);
		} catch (SQLException e)
		{
			throw new ISPACException("DbQuery::getBinaryStream(), error", e);
		}
	}
	public InputStream getBinaryStream(int columnIndex) throws ISPACException 
	{
		try
		{
			return mrs.getBinaryStream(columnIndex);
		} catch (SQLException e)
		{
			throw new ISPACException("DbQuery::getBinaryStream(), error", e);
		}
	}
	
	public byte[] getBytes(String columnName) throws ISPACException
	{
		try
		{
			return mrs.getBytes(columnName);
		} catch (SQLException e)
		{
			throw new ISPACException("DbQuery::getBytes(), error", e);
		}
	}

	public byte[] getBytes(int columnIndex) throws ISPACException
	{
		try
		{
			return mrs.getBytes(columnIndex);
		} catch (SQLException e)
		{
			throw new ISPACException("DbQuery::getBytes(), error", e);
		}
	}

  public boolean wasNull () throws ISPACException
  {
    try
    {
      return mrs.wasNull();
    }
    catch (SQLException e)
    {
      throw new ISPACException("DbQuery::wasNull(), error", e);
    }
  }

}