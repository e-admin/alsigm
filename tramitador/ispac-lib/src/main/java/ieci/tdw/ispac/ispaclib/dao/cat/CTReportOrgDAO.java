package ieci.tdw.ispac.ispaclib.dao.cat;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.dao.idsequences.IdSequenceMgr;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

public class CTReportOrgDAO extends ObjectDAO
{

	public static final String TABLENAME 	= "SPAC_CT_INFORMES_ORG";
	static final String IDKEY 		= "ID";
	static final String IDSEQUENCE 	= "SPAC_SQ_ID_CTINFORMES_ORG";
	
	public static final int GENERIC_TYPE = 1;
	public static final int SPECIFIC_TYPE = 2;
	
	/**
	 * 
	 * @throws ISPACException
	 */
	public CTReportOrgDAO() throws ISPACException	{
		super(null);
	}
  
	/**
	 * @param cnt
	 * @throws ISPACException
	 */
	public CTReportOrgDAO(DbCnt cnt) throws ISPACException {
		super(cnt, null);
	}
  
	/**
	 * 
	 * @param cnt
	 * @param id
	 * @throws ISPACException
	 */
	public CTReportOrgDAO(DbCnt cnt, int id) throws ISPACException {		
		super(cnt,id,null);
	}
  
	protected String getDefaultSQL(int nActionDAO) throws ISPACException
	{
		return " WHERE " + IDKEY + " = " + getInt(IDKEY);
	}
	  
	public String getKeyName() throws ISPACException
	{
	    return IDKEY;
	}
	  
	public String getTableName()
	{
	    return TABLENAME;
	}
	  
	public void newObject(DbCnt cnt) throws ISPACException
	{
	    set(IDKEY,IdSequenceMgr.getIdSequence(cnt,IDSEQUENCE));
	}

	public static void deletePermission(DbCnt cnt, String idReport, String[] uids) throws ISPACException {
	
		String stUids  = StringUtils.join(uids, "','");
		if (StringUtils.isEmpty(stUids))
			return;
		
		stUids = "'"+stUids+"'";
		String sql = " WHERE ID_INFORME = " + idReport 
				   + " AND UID_USR IN ( " + stUids + " ) ";
		
		CollectionDAO collection = new CollectionDAO(CTReportOrgDAO.class);
		collection.delete(cnt, sql);
	}
	
	public static void deleteAllUsersToReport(DbCnt cnt, String idReport)throws ISPACException {
	
		String sql = " WHERE ID_INFORME = " + idReport ;
				 
		CollectionDAO collection = new CollectionDAO(CTReportOrgDAO.class);
		collection.delete(cnt, sql);
	}
	
}