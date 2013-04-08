package ieci.tdw.ispac.ispaclib.dao.procedure;

import ieci.tdw.ispac.api.ISPACEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.dao.ActionDAO;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;

public class PNumExpDAO extends ObjectDAO {
	
	static final String TABLENAME 	= "SPAC_NUMEXP_CONTADOR";
	//static final String IDSEQUENCE 	= "";
	static final String IDKEY 		= "ID_PCD";

	protected static String mProductName = null;

	/**
	 * 
	 * @throws ISPACException
	 */
	public PNumExpDAO() throws ISPACException {
		super(null);
	}

	/**
	 * 
	 * @param cnt
	 * @throws ISPACException
	 */
	public PNumExpDAO(DbCnt cnt) throws ISPACException {
		super(cnt, null);
		//load(cnt);
	}
	
	/**
	 * 
	 * @param cnt
	 * @param id
	 * @throws ISPACException
	 */
	public PNumExpDAO(DbCnt cnt, int id) throws ISPACException {
		super(cnt, id, null);
	}
	
	public String getTableName() {
		return TABLENAME;
	}

	public void load(DbCnt cnt) throws ISPACException
	{
		mProductName = cnt.getProductName();
		load(cnt, getDefaultSQL(ActionDAO.LOAD));
	}

	protected String getDefaultSQL(int nActionDAO) 
	throws ISPACException 
	{	
		String keyValue = String.valueOf(ISPACEntities.ENTITY_NULLREGKEYID);
		if (getIdPcd() != null)
			keyValue = getIdPcd();
		
		String where = " WHERE " + getKeyName() + " = " + keyValue; 
		if (nActionDAO == ActionDAO.LOAD)
		{
			return getLock(where);
		}
		return where;
	}

	protected void newObject(DbCnt cnt) 
	throws ISPACException 
	{	
	}

	public String getKeyName() 
	throws ISPACException 
	{	
		return IDKEY;
	}
	
	public String getAnio()
	throws ISPACException 
	{	
		return getString("ANIO");
	}
	
	public int getContador()
	throws ISPACException 
	{	
		return getInt("CONTADOR");
	}
	
	public String getFormato()
	throws ISPACException 
	{	
		return getString("FORMATO");
	}

	public String getIdPcd()
	throws ISPACException 
	{	
		return getString("ID_PCD");
	}
		
	
	static public PNumExpDAO getCounterByPcd(DbCnt cnt, int idPcd)
	throws ISPACException
	{
	    mProductName = cnt.getProductName();
		String sql = getLock(" WHERE ID_PCD = " + idPcd);
	    
		CollectionDAO objlist=new CollectionDAO(PNumExpDAO.class);
		objlist.query(cnt,sql);
		if (objlist.next())
			return (PNumExpDAO)objlist.value();
		return null;
	}

	public static PNumExpDAO getDefaultCounter(DbCnt cnt) throws ISPACException {
		return getCounterByPcd(cnt, ISPACEntities.ENTITY_NULLREGKEYID);
	}

	static String getLock(String where) {
		
		// Bloquea el registro
		if (mProductName.equals( "MICROSOFT SQL SERVER")) 
		{
			//return " WITH (UPDLOCK) WHERE " + IDKEY + " = " + getInt(IDKEY);
			return " WITH (UPDLOCK) " + where;
		}
        else {
        	//return " WHERE " + IDKEY + " = " + getInt(IDKEY) + " FOR UPDATE";
        	return where + " FOR UPDATE";
        }
	}
	
	public void delete(DbCnt cnt , int idPcd) throws ISPACException
	{
		
		String sql = "DELETE FROM " + getTableName() + " WHERE ID_PCD="+idPcd;
		cnt.directExec(sql);
		mbNewObject = true;
	}
		
}