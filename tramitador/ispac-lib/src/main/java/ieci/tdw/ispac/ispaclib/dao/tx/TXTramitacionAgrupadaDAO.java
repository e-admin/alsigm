package ieci.tdw.ispac.ispaclib.dao.tx;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.dao.idsequences.IdSequenceMgr;
import ieci.tdw.ispac.ispaclib.db.DbCnt;

/**
 * DAO para el acceso a las tramitaciones agrupadas.
 * 
 */
public class TXTramitacionAgrupadaDAO extends ObjectDAO 
{
	static final String TABLENAME 	= "SPAC_TRAMITACIONES_AGRUPADAS";
	static final String IDSEQUENCE 	= "SPAC_SQ_TRAMITACION_AGRUPADA";
	static final String IDKEY 		= "ID";
	
	/**
	 * @throws ISPACException
	 */
	public TXTramitacionAgrupadaDAO() throws ISPACException {
		super(null);
	}
	
	/**
	 * Constructor.
	 * @param cnt
	 * @throws ISPACException
	 */
	public TXTramitacionAgrupadaDAO(DbCnt cnt) throws ISPACException {
		super(cnt, null);
	}
	
	/**
	 * Constructor.
	 * @param cnt
	 * @param id
	 * @throws ISPACException
	 */
	public TXTramitacionAgrupadaDAO(DbCnt cnt, int id) throws ISPACException {
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
