package ieci.tdw.ispac.ispaclib.dao.security;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.dao.idsequences.IdSequenceMgr;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;

public class FechSustitucionesDAO_v5_2_7 extends ObjectDAO {
	
	static final String TABLENAME = "SPAC_SS_FECHSUSTITUCIONES";

	static final String IDSEQUENCE = "SPAC_SQ_ID_SSFECHSUSTITUCIONES";

	static final String IDKEY = "ID";
	
	/**
	 * 
	 * @throws ISPACException
	 */
	public FechSustitucionesDAO_v5_2_7() throws ISPACException {
		super(null);
	}
	
	/**
	 * @param cnt
	 * @throws ISPACException
	 */
	public FechSustitucionesDAO_v5_2_7(DbCnt cnt) throws ISPACException {
		super(cnt, null);
	}

	/**
	 * 
	 * @param cnt
	 * @param id
	 * @throws ISPACException
	 */
	public FechSustitucionesDAO_v5_2_7(DbCnt cnt, int id) throws ISPACException {
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

	static public CollectionDAO getFechas(DbCnt cnt, String sustituidoUID)
	throws ISPACException
	{
		String sql="WHERE UID_SUSTITUIDO = '" + DBUtil.replaceQuotes(sustituidoUID) + "'";
		CollectionDAO objlist=new CollectionDAO(FechSustitucionesDAO.class);
		objlist.query(cnt,sql);
		return objlist;
	}

	
	

}
