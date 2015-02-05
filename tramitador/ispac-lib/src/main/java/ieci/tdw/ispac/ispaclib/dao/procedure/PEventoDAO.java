/*
 * Created on 03-may-2004
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
 * @author juanin
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PEventoDAO extends ObjectDAO
{
	
	private static final long serialVersionUID = 1L;
	
	static final String TABLENAME 	= "SPAC_P_EVENTOS";
	static final String IDSEQUENCE 	= "SPAC_SQ_ID_PEVENTOS";
//	static final String IDKEY 		= "ORDEN";

	/**
	 * @throws ISPACException
	 */
	public PEventoDAO() throws ISPACException {
		super(null);
	}
	
	/**
	 * @param cnt
	 * @throws ISPACException
	 */
	public PEventoDAO(DbCnt cnt) throws ISPACException {
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
//        return "WHERE ORDEN = " + get("ORDEN");

 	   // if (nActionDAO != ActionDAO.UPDATE)
	        return "WHERE TP_OBJ = " + get("TP_OBJ") + " AND ID_OBJ = " + get("ID_OBJ") + " AND EVENTO = "
					+ get("EVENTO") + " AND ID_REGLA = " + get("ID_REGLA") + " AND ORDEN = " + get("ORDEN");
	    
	   /* return "WHERE TP_OBJ = " + get("TP_OBJ") + " AND ID_OBJ = " + get("ID_OBJ") + " AND EVENTO = "
				+ get("EVENTO") + " AND ID_REGLA = " + get("ID_REGLA");*/

	}

	/* (non-Javadoc)
	 * @see ieci.tdw.ispac.ispactx.dao.ObjectDAO#newObject(ieci.tdw.ispac.ispactx.dbx.DbCnt)
	 */
	protected void newObject(DbCnt cnt) throws ISPACException
	{
	    //set("ORDEN",IdSequenceMgr.getIdSequence(cnt,IDSEQUENCE));

	}

	public String getKeyName() throws ISPACException
	{
		//throw new ISPACException("El acceso a la tabla "+TABLENAME+" tiene más de una clave única");
	    return "";
	}

	public void setOrderSequence(DbCnt cnt) throws ISPACException
	{
	   set("ORDEN",IdSequenceMgr.getIdSequence(cnt,IDSEQUENCE));
	}


	public static PEventoDAO getEvent(DbCnt cnt, int TypeObj, int nIdObj, int EventCode,
	        int ruleId, int nOrder)throws ISPACException
	{
	    String sql="WHERE TP_OBJ = " + TypeObj + " AND ID_OBJ = "+nIdObj+
			" AND EVENTO = " + EventCode + " AND ORDEN = " + nOrder +
			" AND ID_REGLA = " + ruleId +" ORDER BY ORDEN";
	    CollectionDAO objlist=new CollectionDAO(PEventoDAO.class);
	    objlist.query(cnt,sql);
	    if (objlist.next())
	        return (PEventoDAO)objlist.value();
        return null;
	}

	public static CollectionDAO getEvents(DbCnt cnt, int TypeObj, int nIdObj)
	throws ISPACException
	{
		String sql="WHERE TP_OBJ = "+TypeObj+" AND ID_OBJ = "+nIdObj+" ORDER BY EVENTO,ORDEN";
		CollectionDAO objlist=new CollectionDAO(PEventoDAO.class);
		objlist.query(cnt,sql);
		return objlist;
	}

	public static CollectionDAO getEvents(DbCnt cnt, int TypeObj, int nIdObj, int EventCode)
	throws ISPACException
	{
		String sql="WHERE TP_OBJ = "+TypeObj+" AND ID_OBJ = "+nIdObj+
			" AND EVENTO = "+EventCode+" ORDER BY ORDEN";
		CollectionDAO objlist=new CollectionDAO(PEventoDAO.class);
		objlist.query(cnt,sql);
		return objlist;
	}
	public static PEventoDAO getEventRulePred(DbCnt cnt, int TypeObj, int nIdObj, int EventCode, int nOrder)
	throws ISPACException
	{
	    String sql="WHERE TP_OBJ = " + TypeObj + " AND ID_OBJ = "+nIdObj+
			" AND EVENTO = " + EventCode + " AND ORDEN >= " + (nOrder + 1) + " ORDER BY ORDEN";
	    CollectionDAO objlist=new CollectionDAO(PEventoDAO.class);
	    objlist.query(cnt,sql);
	    if (objlist.next())
	        return (PEventoDAO)objlist.value();
        return null;
	}

	public static PEventoDAO getEventRuleAnt(DbCnt cnt, int TypeObj, int nIdObj, int EventCode, int nOrder)
	throws ISPACException
	{
	    String sql="WHERE TP_OBJ = " + TypeObj + " AND ID_OBJ = "+nIdObj+
			" AND EVENTO = " + EventCode + " AND ORDEN <= " + (nOrder - 1) + " ORDER BY ORDEN DESC";
	    CollectionDAO objlist=new CollectionDAO(PEventoDAO.class);
	    objlist.query(cnt,sql);
	    if (objlist.next())
	        return (PEventoDAO)objlist.value();
	    return null;
	}
	
}
