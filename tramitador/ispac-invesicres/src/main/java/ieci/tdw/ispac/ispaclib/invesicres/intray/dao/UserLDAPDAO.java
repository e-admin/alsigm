package ieci.tdw.ispac.ispaclib.invesicres.intray.dao;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;

public class UserLDAPDAO extends ObjectDAO {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	static final String TBNAME = "IUSERLDAPUSERHDR";

	static final String PKNAME = "ID";

	/**
	 * 
	 * @param cnt
	 * @throws ISPACException
	 */
	public UserLDAPDAO(DbCnt cnt) throws ISPACException {
		super(cnt, null);
	}

	/**
	 * 
	 * @param cnt
	 * @param id
	 * @throws ISPACException
	 */
	public UserLDAPDAO(DbCnt cnt, int id) throws ISPACException {
		super(cnt, id, null);
	}

	public String getTableName() {
		return TBNAME;
	}

	protected String getDefaultSQL(int nActionDAO) throws ISPACException {
		return " WHERE " + PKNAME + " = " + getInt(PKNAME);
	}

	protected void newObject(DbCnt cnt) throws ISPACException {
		set(PKNAME, 0);
	}

	public String getKeyName() throws ISPACException {
		return PKNAME;
	}
}