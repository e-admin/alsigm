package ieci.tdw.ispac.ispaclib.invesicres.intray.dao;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.invesicres.InveSicresSecuence;

public class StateIntrayDAO extends ObjectDAO {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	static final String TBNAME = "SCR_DISTREGSTATE";

	static final String PKNAME = "ID";

	/**
	 * 
	 * @throws ISPACException
	 */
	public StateIntrayDAO() throws ISPACException {
		super(null);
	}

	/**
	 * 
	 * @param cnt
	 * @throws ISPACException
	 */
	public StateIntrayDAO(DbCnt cnt) throws ISPACException {
		super(cnt, null);
	}

	/**
	 * 
	 * @param cnt
	 * @param id
	 * @throws ISPACException
	 */
	public StateIntrayDAO(DbCnt cnt, int id) throws ISPACException {
		super(cnt, id, null);
	}

	public String getTableName() {
		return TBNAME;
	}

	protected String getDefaultSQL(int nActionDAO) throws ISPACException {
		return " WHERE " + PKNAME + " = " + getInt(PKNAME);
	}

	protected void newObject(DbCnt cnt) throws ISPACException {
		set(PKNAME, InveSicresSecuence.getNexSecuence(cnt));
	}

	public String getKeyName() throws ISPACException {
		return PKNAME;
	}
}