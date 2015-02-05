package ieci.tdw.ispac.ispaclib.dao.tx;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.dao.idsequences.IdSequenceMgr;
import ieci.tdw.ispac.ispaclib.db.DbCnt;


/**
 * DAO para el acceso a las tramitaciones agrupadas.
 * 
 */
public class TXTramitacionAgrupadaExpsDAO extends ObjectDAO 
{
	static final String TABLENAME 	= "SPAC_TRAMITCS_AGRUPADAS_EXPS";
	static final String IDSEQUENCE 	= "SPAC_SQ_TRAMTCS_GROUP_EXPS";
	static final String IDKEY 		= "ID";
	
	/**
	 * @throws ISPACException
	 */
	public TXTramitacionAgrupadaExpsDAO() throws ISPACException {
		super(null);
	}
	
	/**
	 * Constructor.
	 * @param cnt
	 * @throws ISPACException
	 */
	public TXTramitacionAgrupadaExpsDAO(DbCnt cnt) throws ISPACException {
		super(cnt, null);
	}
	
	/**
	 * Constructor.
	 * @param cnt
	 * @param id
	 * @throws ISPACException
	 */
	public TXTramitacionAgrupadaExpsDAO(DbCnt cnt, int id) throws ISPACException {
		super(cnt, id, null);
	}

	public String getTableName() {
		return TABLENAME;
	}

	protected void newObject(DbCnt cnt) throws ISPACException {
		set(IDKEY, IdSequenceMgr.getIdSequence(cnt, IDSEQUENCE));
	}

	protected String getDefaultSQL(int nActionDAO) throws ISPACException {
		return " WHERE " + IDKEY + " = " + getInt(IDKEY);
	}

	public String getKeyName() throws ISPACException {
		return IDKEY;
	}
}
