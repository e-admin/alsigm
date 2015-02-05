package ieci.tdw.ispac.ispaclib.dao.cat;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;

public class CTHierarchyTableDAO extends ObjectDAO {

	static String TABLENAME 		= "SPAC_CT_JERARQUIA_";
	static final String ID_PADRE 	= "ID_PADRE";
	static final String ID_HIJA 	= "ID_HIJA";
	
	/**
	 * @param cnt
	 * @param id
	 * @throws ISPACException
	 */
	public CTHierarchyTableDAO(DbCnt cnt, Integer id) throws ISPACException	{
		super(cnt, TABLENAME + id.toString(), null, null);
		TABLENAME = TABLENAME + id.toString();
	}

	/**
	 * 
	 * @param cnt
	 * @param id
	 * @throws ISPACException
	 */
    public CTHierarchyTableDAO(DbCnt cnt, int id) throws ISPACException {
		super(cnt, TABLENAME + String.valueOf(id), null, null);
		TABLENAME = TABLENAME + String.valueOf(id);
    }

	public String getTableName()
	{
		return TABLENAME;
	}

	/* (non-Javadoc)
	 * @see ieci.tdw.ispac.ispactx.dao.ObjectDAO#newObject(int)
	 */
	protected void newObject(DbCnt cnt)
		throws ISPACException
	{
	}

	/* (non-Javadoc)
	 * @see ieci.tdw.ispac.ispactx.dao.ObjectDAO#getDefaultSQL(int)
	 */
	protected String getDefaultSQL(int nActionDAO) throws ISPACException
	{
		return " WHERE " + ID_PADRE + " = " + getInt(ID_PADRE) + " AND " + ID_HIJA + " = " + getInt(ID_HIJA);
	}

	public String getKeyName() throws ISPACException
	{
		return null;
	}
    
}