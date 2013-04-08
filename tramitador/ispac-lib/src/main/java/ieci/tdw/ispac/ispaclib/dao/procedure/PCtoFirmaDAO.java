/*
 * Created on 02-apr-2008
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package ieci.tdw.ispac.ispaclib.dao.procedure;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.dao.idsequences.IdSequenceMgr;
import ieci.tdw.ispac.ispaclib.db.DbCnt;


/**
 * @author omar
 *
 * Para los circuitos de firma asociados a un procedimiento
 */
public class PCtoFirmaDAO extends ObjectDAO
{
	static final String TABLENAME 	= "SPAC_P_CTOSFIRMA";
	static final String IDSEQUENCE 	= "SPAC_SQ_ID_PCTOSFIRMA";
	static final String IDKEY = "ID";

	/**
	 * @throws ISPACException
	 */
	public PCtoFirmaDAO() throws ISPACException	{
		super(null);
	}
	
	/**
	 * @param cnt
	 * @throws ISPACException
	 */
	public PCtoFirmaDAO(DbCnt cnt) throws ISPACException {
		super(cnt, null);
	}
	
	/**
	 * @throws ISPACException
	 */
	public PCtoFirmaDAO(DbCnt cnt, int id) throws ISPACException {
		 super(cnt,id,null);
	}

	public String getTableName()
	{
		return TABLENAME;
	}

	protected String getDefaultSQL(int nActionDAO) throws ISPACException
    {
	    return " WHERE " + IDKEY + " = " + getInt(IDKEY);
    }

	public String getKeyName() throws ISPACException
	{
	    return IDKEY;
	}

	public void newObject(DbCnt cnt) throws ISPACException
	{
	    set(IDKEY,IdSequenceMgr.getIdSequence(cnt,IDSEQUENCE));
	}

	public static CollectionDAO getCtosFirmas(DbCnt cnt, int idPcd)
	throws ISPACException
	{
		String sql = "WHERE ID_PCD = "+ idPcd +" ORDER BY ID_CIRCUITO";		
		CollectionDAO objlist=new CollectionDAO(PCtoFirmaDAO.class);
		objlist.query(cnt,sql);
		return objlist;
	}
	
	public static PCtoFirmaDAO getCtoFirma(DbCnt cnt, int ctofirmaId, int idPcd)
	throws ISPACException
	{
		String sql = "WHERE ID_CIRCUITO = "+ ctofirmaId +" AND ID_PCD = "+ idPcd +" ORDER BY ID_CIRCUITO";		
		CollectionDAO objlist=new CollectionDAO(PCtoFirmaDAO.class);
		objlist.query(cnt,sql);
		if (objlist.next())
			return (PCtoFirmaDAO)objlist.value();
		return null;
	}
}