package ieci.tdw.ispac.ispaclib.invesicres.intray.dao;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.dao.ActionDAO;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.invesicres.InveSicresSecuence;

public class DistributionIntrayDAO extends ObjectDAO {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	static final String TBNAME = "SCR_DISTREG";

	static final String PKNAME = "ID";

	protected String mProductName = null;

	/**
	 * 
	 * @throws ISPACException
	 */
	public DistributionIntrayDAO() throws ISPACException {
		super(null);
	}

	/**
	 * 
	 * @param cnt
	 * @throws ISPACException
	 */
	public DistributionIntrayDAO(DbCnt cnt) throws ISPACException {
		super(cnt, null);
	}

	/**
	 * 
	 * @param cnt
	 * @param id
	 * @throws ISPACException
	 */
	public DistributionIntrayDAO(DbCnt cnt, int id) throws ISPACException {
		super(cnt, id, null);
	}

	public String getTableName() {
		return TBNAME;
	}

	public void load(DbCnt cnt) throws ISPACException {
		mProductName = cnt.getProductName();
		load(cnt, getDefaultSQL(ActionDAO.LOAD));
	}

	protected String getDefaultSQL(int nActionDAO) throws ISPACException {
		if (nActionDAO == ActionDAO.LOAD) {
			// Bloquea el registro
			if (mProductName.equals("MICROSOFT SQL SERVER")) {
				return " WITH (UPDLOCK) WHERE " + PKNAME + " = "
						+ getInt(PKNAME);
			}
			/*
			else if (mProductName.startsWith("ORACLE")) {
				return " WHERE " + PKNAME + " = " + getInt(PKNAME)
						+ " FOR UPDATE";
			}
			*/
			else {
				return " WHERE " + PKNAME + " = " + getInt(PKNAME)
				+ " FOR UPDATE";
			}
		}

		return " WHERE " + PKNAME + " = " + getInt(PKNAME);
	}

	protected void newObject(DbCnt cnt) throws ISPACException {
		set(PKNAME, InveSicresSecuence.getNexSecuence(cnt));
	}

	public String getKeyName() throws ISPACException {
		return PKNAME;
	}
}