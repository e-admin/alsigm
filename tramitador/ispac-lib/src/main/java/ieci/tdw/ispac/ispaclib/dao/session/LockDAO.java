/*
 * BloqueoDAO.java
 *
 * Created on May 21, 2004, 11:34 AM
 */

package ieci.tdw.ispac.ispaclib.dao.session;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;

public class LockDAO extends ObjectDAO
{

	static final String TABLENAME = "SPAC_S_BLOQUEOS";
	// No existe secuencia para generar claves primarias en esta tabla
	static final String IDSEQUENCE = null;
	// La clave es multivaluada
	static final String IDKEY = null;

	static final String IDKEY1 = "ID_OBJ";
	static final String IDKEY2 = "TP_OBJ";

	/**
	 * 
	 * @throws ISPACException
	 */
	public LockDAO() throws ISPACException {
		super(null);
	}

	/**
	 * 
	 * @param cnt
	 * @throws ISPACException
	 */
	public LockDAO(DbCnt cnt) throws ISPACException {
		super(cnt, null);
	}
	
	protected String getDefaultSQL(int nActionDAO) throws ISPACException
	{
		return " WHERE " + IDKEY1 + " = " + getInt(IDKEY1) + " AND " + IDKEY2
				+ " = " + getInt(IDKEY2);
	}

	public String getKeyName() throws ISPACException
	{
		throw new ISPACException("El acceso a la tabla " + TABLENAME
				+ " tiene más de una clave única");
	}

	public String getTableName()
	{
		return TABLENAME;
	}

	protected void newObject(DbCnt cnt) throws ISPACException
	{
	}

}
