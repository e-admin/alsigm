package ieci.tdw.ispac.ispaclib.dao.publisher;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;


/**
 * @author david
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PubHitoActivoDAO extends ObjectDAO
{
	static final String TABLENAME 	= "PUB_HITOS_ACTIVOS";

	/**
	 * @throws ISPACException
	 */
	public PubHitoActivoDAO() throws ISPACException {
		super(null);
	}
	
	/**
	 * @param cnt
	 * @throws ISPACException
	 */
	public PubHitoActivoDAO(DbCnt cnt) throws ISPACException {
		super(cnt, null);
	}

	/* (non-Javadoc)
	 * @see ieci.tdw.ispac.ispactx.dao.ObjectDAO#getTableName()
	 */
	public String getTableName()
	{
		return TABLENAME;
	}

	/* (non-Javadoc)
	 * @see ieci.tdw.ispac.ispactx.dao.ObjectDAO#getDefaultSQL(int)
	 */
	protected String getDefaultSQL(int nActionDAO)
	throws ISPACException
	{
		return " WHERE ID_HITO = " + getInt("ID_HITO") + " AND ID_APLICACION = " + getInt("ID_APLICACION") + " AND ID_SISTEMA = '" + DBUtil.replaceQuotes(getString("ID_SISTEMA")) + "'";
	}

	/* (non-Javadoc)
	 * @see ieci.tdw.ispac.ispactx.dao.ObjectDAO#newObject(ieci.tdw.ispac.ispactx.dbx.DbCnt)
	 */
	protected void newObject(DbCnt cnt) throws ISPACException
	{
	}

	public String getKeyName() throws ISPACException
	{
		return "";
	}

	public static PubHitoActivoDAO getHitoActivo(DbCnt cnt, int milestoneId, int applicationId, String systemId)throws ISPACException
	{
	    String sql=" WHERE ID_HITO = " + milestoneId + " AND ID_APLICACION = " + applicationId + " AND ID_SISTEMA = '" + DBUtil.replaceQuotes(systemId) + "'";
	    CollectionDAO objlist=new CollectionDAO(PubHitoActivoDAO.class);
	    objlist.query(cnt,sql);
	    if (objlist.next())
	        return (PubHitoActivoDAO)objlist.value();
        return null;
	}
	
}