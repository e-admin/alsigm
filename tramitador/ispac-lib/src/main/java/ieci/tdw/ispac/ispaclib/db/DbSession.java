package ieci.tdw.ispac.ispaclib.db;

import ieci.tdw.ispac.api.errors.ISPACException;

/**
 * DbSession
 *
 * @deprecated utilizado por el antiguo cat&aacute;logo
 * @version $Revision: 1.1 $ $Date: 2007/04/24 11:17:01 $
 */
public class DbSession
{

	/** Creates a new instance of DbSession */

	public DbSession()
	{

	}

	/**
	 * Crea una sesión en BBDD
	 * @return <code>true</code> si todo va bien
	 */

	public void newSession(String sTicket, String sSessionInfo, String sUser)
			throws ISPACException
	{
	    DbCnt dba = new DbCnt();
	    String sql = "INSERT INTO SPAC_SESIONES (ID, FECHA, DATOS, USUARIO) VALUES (";
	    sql = sql + quote(sTicket) + ", SYSDATE, " + quote(sSessionInfo)
	    		+ " , " + quote(sUser) + ")";

	    dba.getConnection();

	    int nRows = -1;
	    try
	    {
	      nRows = dba.directExec(sql);
	    }
	    catch (ISPACException e)
	    {
	      throw e;
	    }
	    finally
	    {
	      dba.closeConnection();
	    }

	    if (nRows != 1)
	      throw new ISPACException("DbSession::newSession (" + sTicket + "," + sSessionInfo + "," + sUser + "), número de registros insertados = " + nRows);
	}

	/**
	 * Valida que la sesión existe en BBDD, y actualiza su TS de uso
	 * @return <code>true</code> La sesión es valida o no
	 */

	public boolean isSession(String sTicket) throws ISPACException
	{
		boolean bRet = false;
		int nRows = 0;
		DbCnt dba = new DbCnt();
		String sql = "UPDATE SPAC_SESIONES SET FECHA = SYSDATE ";
		sql = sql + " WHERE ID = " + quote(sTicket);

		dba.getConnection();

	    try
	    {
	  		nRows = dba.directExec(sql);
	    }
	    catch (ISPACException e)
	    {
	      throw e;
	    }
	    finally
	    {
	      dba.closeConnection();
	    }

		bRet = (nRows == 1);
		return bRet;
	}

	/**
	 * Elimina la sesión y sus bloqueos de la BBDD
	 */
	public void deleteSession(String sTicket) throws ISPACException
	{
	    boolean bTx = false;
			int nRows = 0;
			DbCnt dba = new DbCnt();
			dba.getConnection();

	    try
	    {
	      // 1º : Iniciar transacción
	      dba.openTX();
	      // 2º : Eliminar bloqueos de la sesión
	      String sSql = "DELETE FROM SPAC_BLOQUEOS WHERE ID = " + quote(sTicket);
	      nRows = dba.directExec(sSql);
	      if (nRows >= 0)
	      {
	        if (nRows != 0)
	        {
	          // TODO Reportar al log la desaparición de una sesión
	          // con bloqueos pendientes
	        }

	        // 3º : Eliminar la sesión
	        sSql = "DELETE FROM SPAC_SESIONES WHERE ID = " + quote(sTicket);
	        nRows = dba.directExec(sSql);
	        bTx = true;
	      }
	    }
	    catch (ISPACException e)
	    {
	      throw e;
	    }
	    finally
	    {
	      // 3º : Confirmar (o anular) la transacción
	      dba.closeTX(bTx);
	      dba.closeConnection();
	    }
	}

	/**
   * Devuelve el nombre de usuario asociado al ticket
	 * @param sTicket ticket de sesión
	 * @return sUser usuario de la sesión
	 */

	public String getUserName(String sTicket)	throws ISPACException
	{
		String sUser = null;
		DbQuery dbq = null;
		DbCnt dba = new DbCnt();
		dba.getConnection();

	    try
	    {
	      String sSql = "SELECT USUARIO FROM SPAC_SESIONES ";
	      sSql = sSql + " WHERE ID = " + quote(sTicket);
	      dbq = dba.executeDbQuery(sSql);
	      if (dbq.next())
	          sUser = dbq.getString("USUARIO");
	    }
	    catch (ISPACException e)
	    {
	      throw e;
	    }
	    finally
	    {
	      dbq.close();
	      dba.closeConnection();
	    }
		return sUser;
	}

	/**
	 * Bloquea un objeto.
	 * @param tpObj Tipo del objeto. Depende de la aplicación el sentido de este
	 * parámetro
	 * @param idObj Identificador del objeto.
	 */
	public void lockObj(String sTicket, int tpObj, int idObj)
		throws ISPACException
	{
		int nRows = 0;
		DbCnt dba = new DbCnt();
		dba.getConnection();

	    try
	    {
	      String sSql = "INSERT INTO SPAC_BLOQUEOS (ID, TP_OBJ, ID_OBJ) VALUES (";
	      sSql = sSql + quote(sTicket) + "," + Integer.toString(tpObj) + "," + 	Integer.toString(idObj) + ")";
	  		nRows = dba.directExec(sSql);
	    }
	    catch (ISPACException e)
	    {
	      throw e;
	    }
	    finally
	    {
	      dba.closeConnection();
	    }

		if (nRows != 1)
			throw new ISPACException("DbSession::lockObj, error, no registers were inserted in spac_bloqueos");
	}

	/**
	 * Desbloquea un objeto.
	 * @param tpObj Tipo del objeto. Depende de la aplicación el sentido de este
	 *        parámetro
	 * @param idObj Identificador del objeto.
	 */
	public void unlockObj(String sTicket, int tpObj, int idObj)
		throws ISPACException
	{
		int nRows = 0;
		DbCnt dba = new DbCnt();
		dba.getConnection();

	    try
	    {
	      String sSql = "DELETE FROM SPAC_BLOQUEOS ";
	      sSql = sSql + " WHERE ID = " + quote(sTicket);
	      sSql = sSql + " AND  TP_OBJ = " + Integer.toString(tpObj);
	      sSql = sSql + " AND  ID_OBJ = " + Integer.toString(idObj);
	      nRows = dba.directExec(sSql);
	    }
	    catch (ISPACException e)
	    {
	      throw e;
	    }
	    finally
	    {
	      dba.closeConnection();
	    }

		if (nRows != 1)
			throw new ISPACException("DbSession::unlockObj, error, no registers were deleted from spac_bloqueos");
	}

	/**
	 * Obtiene la sesión de un objeto bloqueado
	 * @param tpObj Tipo del objeto.
	 * @param idObj Identificador del objeto.
	 * @return El ticket de sesión que tiene bloqueado el objeto, o <code>null</code>
	 *         si el objeto no está bloqueado.
	 */

	public String getLockInfo(int tpObj, int idObj)
	throws ISPACException
	{
		String sTicket = null;
	    DbQuery dbq = null;
			DbCnt dba = new DbCnt();
			dba.getConnection();

	    try
	    {
	      String sSql = "SELECT ID FROM SPAC_BLOQUEOS ";
	      sSql = sSql + " WHERE TP_OBJ = " + Integer.toString(tpObj);
	      sSql = sSql + " AND  ID_OBJ = " + Integer.toString(idObj);
	      dbq = dba.executeDbQuery(sSql);
	      if (dbq.next())
	          sTicket = dbq.getString(1);
	    }
	    catch (ISPACException e)
	    {
	      throw e;
	    }
	    finally
	    {
	      dbq.close();
	      dba.closeConnection();
	    }

		return sTicket;
	}

	/**
	 * Para poner comilla simple a las cadenas...
	 */
	protected String quote(java.lang.String s)
	{
		return "'" + s + "'";
	}
}
