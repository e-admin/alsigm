package ieci.tdw.ispac.ispaclib.invesdoc.directory.dao;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;

public class UserGroupDAO extends ObjectDAO {

	static final String TBNAME	= "IUSERGROUPUSER";
	static final String PKNAME	= "GROUPID";

	/**
	 * 
	 * @param cnt
	 * @throws ISPACException
	 */
	public UserGroupDAO(DbCnt cnt) throws ISPACException {
		super(cnt, null);
	}
	
	/**
	 * 
	 * @param cnt
	 * @param id
	 * @throws ISPACException
	 */
	public UserGroupDAO(DbCnt cnt, int id) throws ISPACException {
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