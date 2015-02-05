package ieci.tdw.ispac.ispaclib.dao.cat;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.dao.idsequences.IdSequenceMgr;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

public class CTFrmBusquedaOrgDAO extends ObjectDAO
{

	private static final long serialVersionUID = 1L;
	
	static final String TABLENAME 	= "SPAC_CT_FRMBUSQUEDA_ORG";
	static final String IDKEY 		= "ID";
	static final String IDSEQUENCE 	= "SPAC_SQ_ID_CTFRMBUSQUEDA_ORG";
	
	public static final int GENERIC_TYPE = 1;
	public static final int SPECIFIC_TYPE = 2;
	
	/**
	 * 
	 * @throws ISPACException
	 */
	public CTFrmBusquedaOrgDAO() throws ISPACException	{
		super(null);
	}
  
	/**
	 * @param cnt
	 * @throws ISPACException
	 */
	public CTFrmBusquedaOrgDAO(DbCnt cnt) throws ISPACException {
		super(cnt, null);
	}
  
	/**
	 * 
	 * @param cnt
	 * @param id
	 * @throws ISPACException
	 */
	public CTFrmBusquedaOrgDAO(DbCnt cnt, int id) throws ISPACException {		
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

	public void deletePermission (DbCnt cnt, String idSearchForm, String[] uids)
	throws ISPACException
	{
	
		String stUids  = StringUtils.join(uids, "','");
		if (StringUtils.isEmpty(stUids))
			return;
		stUids = "'"+stUids+"'";
		String sql = "WHERE ID_SEARCHFRM = " + idSearchForm + " AND UID_USR IN (" + stUids + ")";
		
		CollectionDAO collection = new CollectionDAO(CTFrmBusquedaOrgDAO.class);
		collection.delete(cnt, sql);
//		collection.query(cnt,sql);
//		while (collection.next()) {
//			CTFrmBusquedaOrgDAO entity = (CTFrmBusquedaOrgDAO) collection.value();
//			entity.delete(cnt);
//		}		
		
	}
}