package ieci.tdw.ispac.ispaclib.dao.security;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.dao.idsequences.IdSequenceMgr;
import ieci.tdw.ispac.ispaclib.db.DbCnt;

public class FechSustitucionesDAO extends ObjectDAO {
	
	public static final String TABLENAME = "SPAC_SS_FECHSUSTITUCIONES";

	static final String IDSEQUENCE = "SPAC_SQ_ID_SSFECHSUSTITUCIONES";

	static final String IDKEY = "ID";
	
	/**
	 * 
	 * @throws ISPACException
	 */
	public FechSustitucionesDAO() throws ISPACException {
		super(null);
	}
	
	/**
	 * @param cnt
	 * @throws ISPACException
	 */
	public FechSustitucionesDAO(DbCnt cnt) throws ISPACException {
		super(cnt, null);
	}

	/**
	 * 
	 * @param cnt
	 * @param id
	 * @throws ISPACException
	 */
	public FechSustitucionesDAO(DbCnt cnt, int id) throws ISPACException {
		super(cnt, id, null);
	}

	public String getTableName() {
		return TABLENAME;
	}

	protected String getDefaultSQL(int nActionDAO) throws ISPACException {
		return " WHERE " + IDKEY + " = " + getInt(IDKEY);
	}

	public String getKeyName() throws ISPACException {
		return IDKEY;
	}

	public void newObject(DbCnt cnt) throws ISPACException {
		set(IDKEY, IdSequenceMgr.getIdSequence(cnt, IDSEQUENCE));
	}

}