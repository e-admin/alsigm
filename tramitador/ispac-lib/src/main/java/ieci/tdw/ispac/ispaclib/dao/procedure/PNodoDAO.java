/*
 * Created on 03-may-2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package ieci.tdw.ispac.ispaclib.dao.procedure;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.graph.GInfo;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;


/**
 * @author juanin
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PNodoDAO extends ObjectDAO
{
	static final String TABLENAME 	= "SPAC_P_NODOS";
	//static final String IDSEQUENCE 	= "SPAC_SQ_ID_PNODOS";
	static final String IDKEY 		= "ID";

	public static final int NODE_OBJ_STAGE		= 1;
    public static final int NODE_OBJ_SYNCNODE	= 2;
    
	/**
	 * @throws ISPACException
	 */
	public PNodoDAO() throws ISPACException {
		super(null);
	}
    
	/**
	 * @param cnt
	 * @throws ISPACException
	 */
	public PNodoDAO(DbCnt cnt) throws ISPACException {
		super(cnt, null);
	}

	/**
	 * @param cnt
	 * @param id
	 * @throws ISPACException
	 */
	public PNodoDAO(DbCnt cnt, int id) throws ISPACException {
		super(cnt, id, null);
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
	    //IMPORTANTE - El nodo debe tener el mismo identificador que la fase o nodo de sincronización
	    //correspondiente.
	}


	/* (non-Javadoc)
	 * @see ieci.tdw.ispac.ispactx.dao.ObjectDAO#getDefaultSQL(int)
	 */
	protected String getDefaultSQL(int nActionDAO) throws ISPACException
	{
		return " WHERE " + IDKEY + " = " + getInt(IDKEY);
	}

	public String getKeyName() throws ISPACException
	{
		return IDKEY;
	}

	public boolean isSyncNode() throws ISPACException {
		return (getInt("TIPO") == NODE_OBJ_SYNCNODE);
	}

	public boolean isStage() throws ISPACException {
		return (getInt("TIPO") == NODE_OBJ_STAGE);
	}
	
	public GInfo getGInfo() throws ISPACException {
		
		String g_info = getString("G_INFO");
		if (StringUtils.isNotBlank(g_info)) {
			
			return GInfo.parse(g_info);
		}
		
		return null;
	}

}