/*
 * SessionDAO.java
 *
 * Created on May 21, 2004, 11:12 AM
 */

package ieci.tdw.ispac.ispaclib.dao.session;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.UUIDGen;

public class SessionDAO extends ObjectDAO
{

    static final String TABLENAME = "SPAC_S_SESIONES";

    // No existe secuencia para generar claves primarias en esta tabla. Las
    // claves primarias son generadas a través de un generador de claves
    static final String IDSEQUENCE = null;

    static final String IDKEY = "ID";

    static final String IDOBJ = "ID_OBJ";

    static final String TPOBJ = "TP_OBJ";

    /**
     * 
     * @throws ISPACException
     */
    public SessionDAO() throws ISPACException {
        super(null);
    }
    
    /**
     * 
     * @param cnt
     * @throws ISPACException
     */
	public SessionDAO(DbCnt cnt) throws ISPACException {
		super(cnt, null);
	}

	/**
	 * 
	 * @param cnt
	 * @param ticket
	 * @throws ISPACException
	 */
    public SessionDAO(DbCnt cnt, String ticket) throws ISPACException {
        super(cnt, (Object) ticket, null);
    }

    protected String getDefaultSQL(int nActionDAO) throws ISPACException
    {
        return " WHERE " + IDKEY + " = '" + getString(IDKEY) + "'";
    }

    public String getKeyName() throws ISPACException
    {
        return IDKEY;
    }

    public String getTableName()
    {
        return TABLENAME;
    }

    protected void newObject(DbCnt cnt) throws ISPACException
    {
        set(IDKEY, UUIDGen.getUUID(this));
    }

    public static CollectionDAO getSesiones(DbCnt cnt, String sql)
            throws ISPACException
    {
        CollectionDAO objlist = new CollectionDAO(SessionDAO.class);
        objlist.query(cnt, sql);
        return objlist;
    }

    public CollectionDAO getBloqueos(DbCnt cnt) throws ISPACException
    {
        String sql = "WHERE ID = '" + getString(IDKEY) + "'";
        CollectionDAO objlist = new CollectionDAO(LockDAO.class);
        objlist.query(cnt, sql);
        return objlist;
    }

    public CollectionDAO getVariables(DbCnt cnt) throws ISPACException
    {
        String sql = "WHERE ID_SES = '" + getString(IDKEY) + "'";
        CollectionDAO objlist = new CollectionDAO(SsVariableDAO.class);
        objlist.query(cnt, sql);
        return objlist;
    }

    public LockDAO getBloqueo(DbCnt cnt, int idObj, int tpObj)
            throws ISPACException
    {
        String sql = "WHERE ID_OBJ = " + idObj + " AND TP_OBJ = " + tpObj
                + " AND ID ='" + getString(IDKEY) + "'";
        CollectionDAO objlist = new CollectionDAO(LockDAO.class);
        objlist.query(cnt, sql);
        LockDAO lock = null;
        if (objlist.next())
            lock = (LockDAO) objlist.value();
        return lock;
    }
    
    public LockDAO getBloqueo(DbCnt cnt, int idObj, int tpObj, String numexp)
    throws ISPACException
	{
    	String sql = "WHERE ID_OBJ = " + idObj + " AND TP_OBJ = " + tpObj
	        	+ " AND ID = '" + getString(IDKEY) + "' AND NUMEXP = '" + DBUtil.replaceQuotes(numexp) + "'";
		CollectionDAO objlist = new CollectionDAO(LockDAO.class);
		objlist.query(cnt, sql);
		LockDAO lock = null;
		if (objlist.next())
		    lock = (LockDAO) objlist.value();
		return lock;
	}

    public SsVariableDAO getVariable(DbCnt cnt, String name)
            throws ISPACException
    {
        String sql = "WHERE ID_SES = '" + getString(IDKEY) +
        		"' AND NOMBRE = '" + DBUtil.replaceQuotes(name) + "'";
        CollectionDAO objlist = new CollectionDAO(SsVariableDAO.class);
        objlist.query(cnt, sql);

        if (!objlist.next())
            return null;

        return (SsVariableDAO) objlist.value();
    }
    
}