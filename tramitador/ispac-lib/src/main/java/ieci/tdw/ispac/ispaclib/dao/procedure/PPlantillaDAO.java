
package ieci.tdw.ispac.ispaclib.dao.procedure;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.dao.idsequences.IdSequenceMgr;
import ieci.tdw.ispac.ispaclib.db.DbCnt;


public class PPlantillaDAO extends ObjectDAO
{

	static final String TBNAME = "SPAC_P_PLANTDOC";
	static final String SQNAME = "SPAC_SQ_ID_PPLANTILLAS";
	static final String KEYNAME = "ID";

	/**
	 * 
	 * @throws ISPACException
	 */
	public PPlantillaDAO() throws ISPACException {
		super(null);
	}
	
	/**
	 * @param cnt
	 * @throws ISPACException
	 */
	public PPlantillaDAO(DbCnt cnt) throws ISPACException {
		super(cnt, null);
	}

	/**
	 * @param cnt
	 * @param id
	 * @throws ISPACException
	 */
	public PPlantillaDAO(DbCnt cnt, int id) throws ISPACException {
		super(cnt, id, null);
	}

	public String getTableName()
	{
		return TBNAME;
	}

	protected String getDefaultSQL(int nActionDAO) throws ISPACException
	{
		return " WHERE " + KEYNAME + " = " + getInt(KEYNAME);
	}

	protected void newObject(DbCnt cnt) throws ISPACException
	{
		set(KEYNAME, IdSequenceMgr.getIdSequence(cnt, SQNAME));
	}

	public String getKeyName() throws ISPACException
	{
		return KEYNAME;
	}
	
	public static CollectionDAO getTemplates(DbCnt cnt,int typedocId)
	throws ISPACException
	{
		String sql = "WHERE ID_TPDOC = " + typedocId + " ORDER BY NOMBRE ";
		
		CollectionDAO collection = new CollectionDAO(PPlantillaDAO.class);
		collection.query(cnt,sql);
		return collection;
	}
}