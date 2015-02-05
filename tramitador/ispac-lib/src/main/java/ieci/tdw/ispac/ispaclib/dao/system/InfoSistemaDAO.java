package ieci.tdw.ispac.ispaclib.dao.system;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;

public class InfoSistemaDAO extends ObjectDAO {
	
	static final String TABLENAME 	= "SPAC_INFOSISTEMA";
	//static final String IDSEQUENCE 	= "";
	//static final String IDKEY 		= "";

	/**
	 * 
	 * @throws ISPACException
	 */
	public InfoSistemaDAO() throws ISPACException {
		super(null);
	}

	/**
	 * 
	 * @param cnt
	 * @throws ISPACException
	 */
	public InfoSistemaDAO(DbCnt cnt) throws ISPACException {
		super(cnt, null);
	}
	
	public String getTableName() {
		return TABLENAME;
	}


	protected String getDefaultSQL(int nActionDAO) 
	throws ISPACException 
	{	
		return null;
	}

	protected void newObject(DbCnt cnt) 
	throws ISPACException 
	{	
	}

	public String getKeyName() 
	throws ISPACException 
	{	
		return null;
	}
	
	public String getVersion()
	throws ISPACException 
	{	
		return getString("VALOR");
	}
	
	public static InfoSistemaDAO getVersionApp(DbCnt cnt)
	throws ISPACException
	{
		String sql = "WHERE CODIGO = 'VERSIONAPP' ORDER BY FECHA_ACTUALIZACION DESC";
	    
		CollectionDAO objlist = new CollectionDAO(InfoSistemaDAO.class);
		objlist.query(cnt, sql);
		
		if (objlist.next()) {
			
			return (InfoSistemaDAO) objlist.value();
		}
		
		return null;
	}
	
}