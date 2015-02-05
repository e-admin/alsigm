package ieci.tdw.ispac.ispaclib.dao.publisher;

import ieci.tdw.ispac.api.IPublisherAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.db.DbPreparedStatement;
import ieci.tdw.ispac.ispaclib.db.DbQuery;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * @author david
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PubErrorDAO extends ObjectDAO
{
	static final String TABLENAME 	= "PUB_ERRORES";

	/**
	 * @throws ISPACException
	 */
	public PubErrorDAO() throws ISPACException {
		super(null);
	}
	
	/**
	 * @param cnt
	 * @throws ISPACException
	 */
	public PubErrorDAO(DbCnt cnt) throws ISPACException {
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
		return " WHERE ID_HITO = " + getInt("ID_HITO") + " AND ID_APLICACION = " + getInt("ID_APLICACION") + " AND ID_SISTEMA = '" + DBUtil.replaceQuotes(getString("ID_SISTEMA")) + "' AND ID_OBJETO = '" + DBUtil.replaceQuotes(getString("ID_OBJETO")) + "'";
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

//	public static PubErrorDAO getError(DbCnt cnt, int milestoneId,
//			int applicationId, String systemId, String objectId)
//			throws ISPACException {
//		String sql = " WHERE ID_HITO = " + milestoneId
//				+ " AND ID_APLICACION = " + applicationId
//				+ " AND ID_SISTEMA = '" + DBUtil.replaceQuotes(systemId)
//				+ "' AND ID_OBJETO = '" + DBUtil.replaceQuotes(objectId) + "'";
//		CollectionDAO objlist = new CollectionDAO(PubErrorDAO.class);
//		objlist.query(cnt, sql);
//		if (objlist.next())
//			return (PubErrorDAO) objlist.value();
//		return null;
//	}

	public static PubErrorDAO getError(DbCnt cnt, int milestoneId,
			int applicationId, String systemId) throws ISPACException {
		String sql = " WHERE ID_HITO = " + milestoneId
				+ " AND ID_APLICACION = " + applicationId
				+ " AND ID_SISTEMA = '" + DBUtil.replaceQuotes(systemId)
				+ "'";
		CollectionDAO objlist = new CollectionDAO(PubErrorDAO.class);
		objlist.query(cnt, sql);
		if (objlist.next())
			return (PubErrorDAO) objlist.value();
		return null;
	}

	public static List IdObjectErrorsList(DbCnt cnt) throws ISPACException {
		final String sql = "SELECT DISTINCT ID_OBJETO FROM PUB_ERRORES";

		List idList = new ArrayList();
		DbPreparedStatement ps = null;
		DbQuery dbq = null;

		try {
			ps = cnt.prepareDBStatement(sql);
			dbq = ps.executeQuery();
			while (dbq.next()) {
				idList.add(dbq.getString("ID_OBJETO"));
			}
		} finally {
			if (dbq != null) {
				dbq.close();
			}
			if (ps != null) {
				ps.close();
			}
		}

		return idList;
	}

	public static boolean isErrorMilestone(DbCnt cnt, String idObjeto) throws ISPACException {
		
		String sql = "SELECT COUNT(ID_OBJETO) FROM PUB_HITOS_ACTIVOS WHERE ID_OBJETO=" + idObjeto 
			+ " AND ESTADO=" + IPublisherAPI.MILESTONE_STATUS_ERROR;

		DbQuery dbq = null;
		
		try { 
			dbq = cnt.executeDbQuery(sql);
			
			int res = dbq.getInt(1);
			return (res > 0);
			
		} finally {
			if (dbq != null) {
				dbq.close();
			}
		}
	}

}