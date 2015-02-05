
package ieci.tdw.ispac.ispaclib.dao.cat;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.dao.idsequences.IdSequenceMgr;
import ieci.tdw.ispac.ispaclib.db.DbCnt;


public class CTHelpDAO extends ObjectDAO {

	private static final long serialVersionUID = 1L;
	
	static final String TBNAME	= "SPAC_AYUDAS";
	static final String SQNAME	= "SPAC_SQ_ID_AYUDAS";
	static final String PKNAME	= "ID";
	static final String TIPO_OBJ_PROCEDURE="3";

	/**
	 * 
	 * @throws ISPACException
	 */
	public CTHelpDAO() throws ISPACException {
		super(null);
	}
	
	/**
	 * @param cnt
	 * @throws ISPACException
	 */
	public CTHelpDAO(DbCnt cnt) throws ISPACException {
		super(cnt, null);
	}

	/**
	 * @param cnt
	 * @param id
	 * @throws ISPACException
	 */
	public CTHelpDAO(DbCnt cnt, int id) throws ISPACException {
		super(cnt, id, null);
	}

	public String getTableName() {
		return TBNAME;
	}

	protected String getDefaultSQL(int nActionDAO) throws ISPACException {
		return " WHERE " + PKNAME + " = " + getInt(PKNAME);
	}

	protected void newObject(DbCnt cnt) throws ISPACException {
		set(PKNAME, IdSequenceMgr.getIdSequence(cnt, SQNAME));
	}

	public String getKeyName() throws ISPACException {
		return PKNAME;
	}

	/**
	 * Obtiene la lista de ayudas asociadas a un procedimiento
	 * @param cnt
	 * @param pcdId Identificador del procedimiento
	 * @return
	 * @throws ISPACException
	 */
	public static CollectionDAO getProcedureHelps(DbCnt cnt,int pcdId)
	throws ISPACException
	{
		String sql = "WHERE  TIPO_OBJ="+TIPO_OBJ_PROCEDURE+" AND ID_OBJ="+pcdId;

		CollectionDAO collection = new CollectionDAO(CTHelpDAO.class);
		collection.query(cnt,sql);
		return collection;
	}

}