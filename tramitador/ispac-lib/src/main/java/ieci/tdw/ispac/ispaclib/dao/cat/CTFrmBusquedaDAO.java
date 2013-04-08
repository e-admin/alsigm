/*
 * Created on Jul 5, 2004 by IECISA
 *
 */
package ieci.tdw.ispac.ispaclib.dao.cat;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.dao.idsequences.IdSequenceMgr;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.resp.Responsible;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;

public class CTFrmBusquedaDAO extends ObjectDAO
{

	public static final String TABLENAME 	= "SPAC_CT_FRMBUSQUEDA";
	static final String IDKEY 		= "ID";
	static final String IDSEQUENCE 	= "SPAC_SQ_ID_CTFRMBUSQUEDA";
	
	/**
	 * 
	 * @throws ISPACException
	 */
	public CTFrmBusquedaDAO() throws ISPACException	{
		super(null);
	}
  
	/**
	 * @param cnt
	 * @throws ISPACException
	 */
	public CTFrmBusquedaDAO(DbCnt cnt) throws ISPACException {
		super(cnt, null);
	}
  
	/**
	 * 
	 * @param cnt
	 * @param id
	 * @throws ISPACException
	 */
	public CTFrmBusquedaDAO(DbCnt cnt, int id) throws ISPACException {		
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
	
  public static CollectionDAO getSearchForms (DbCnt cnt)
  throws ISPACException
  {
  	String sql = "";
  	
  	CollectionDAO objlist=new CollectionDAO(CTFrmBusquedaDAO.class);
  	objlist.query(cnt,sql);
	
  	return objlist;
  }
  
  public CTFrmBusquedaDAO getSearchForm (DbCnt cnt, String name)
  throws ISPACException
  {
  	CTFrmBusquedaDAO frm = new CTFrmBusquedaDAO(cnt);
  	frm.load(cnt, "WHERE DESCRIPCION = '" + DBUtil.replaceQuotes(name) + "'");
  	
  	return frm;
  }

  public CollectionDAO getSearchForms(DbCnt cnt, String respList) 
  throws ISPACException
  {
  	//Obtenemos los formularios genericos junto con los especificos que le correspondan al usuario conectado
	  String sql = "";
	if (!respList.equals(Responsible.SUPERVISOR))
		sql = "WHERE TIPO = " + CTFrmBusquedaOrgDAO.GENERIC_TYPE
		+ " OR ID IN (SELECT DISTINCT ID_SEARCHFRM FROM SPAC_CT_FRMBUSQUEDA_ORG WHERE "
		+ DBUtil.addInResponsibleCondition("UID_USR", respList + ")");
  	
	sql+=" ORDER BY DESCRIPCION";
  	CollectionDAO objlist=new CollectionDAO(CTFrmBusquedaDAO.class);
  	objlist.query(cnt,sql);
  	return objlist;
}
  
}