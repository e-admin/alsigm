package ieci.tdw.ispac.ispaclib.dao.cat;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.dao.join.TableJoinFactoryDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PProcedimientoDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;


public class CTProcedureDAO extends ObjectDAO {

    public static final String TABLENAME   = "SPAC_CT_PROCEDIMIENTOS";
    //static final String IDSEQUENCE  = "SPAC_SQ_ID_CTPROCEDIMIENTOS";
    static final String IDKEY       = "ID";
    
    /**
     * @throws ISPACException
     */
    public CTProcedureDAO() throws ISPACException {
        super(null);
    }
    
	/**
	 * @param cnt
	 * @throws ISPACException
	 */
	public CTProcedureDAO(DbCnt cnt) throws ISPACException {
		super(cnt, null);
	}
	
    /**
     * @param cnt
     * @param id
     * @throws ISPACException
     */
    public CTProcedureDAO(DbCnt cnt, int id) throws ISPACException {
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
       //El ID de procedimiento en el catálogo es el mismo que
       //el del procedimiento en SPAC_P_PROCEDIMIENTO

      //  set(IDKEY,IdSequenceMgr.getIdSequence(cnt,IDSEQUENCE));
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
    
	static public int countChildrenProcedures(DbCnt cnt, int procedureId) throws ISPACException
	{
	    String sql = "WHERE ID_PADRE = " + procedureId;
	    
		CollectionDAO objlist = new CollectionDAO(CTProcedureDAO.class);
		return objlist.count(cnt, sql);
	}
	
	static public boolean isUniqueCode(DbCnt cnt, String codPcd, int groupId) throws ISPACException
	{
		TableJoinFactoryDAO factory = new TableJoinFactoryDAO();
		factory.addTable(TABLENAME, "CTPROCEDIMIENTOS");
		factory.addTable(PProcedimientoDAO.TABLENAME, "PPROCEDIMIENTOS");

	    String sql = "WHERE CTPROCEDIMIENTOS.ID = PPROCEDIMIENTOS.ID"
	    		   + " AND PPROCEDIMIENTOS.ID_GROUP <> " + groupId
	    		   + " AND " + DBUtil.getToUpperSQL("CTPROCEDIMIENTOS.COD_PCD") + " = '" + DBUtil.replaceQuotes(codPcd.toUpperCase()) + "'";
	    
	    return (factory.countTableJoin(cnt, sql)==0);
	}
	
	static public CollectionDAO getProcedureByCode(DbCnt cnt, String codPcd, int state) throws ISPACException
	{
		TableJoinFactoryDAO factory = new TableJoinFactoryDAO();
		factory.addTable(TABLENAME, "CTPROCEDIMIENTOS");
		factory.addTable(PProcedimientoDAO.TABLENAME, "PPROCEDIMIENTOS");

	    String sql = "WHERE CTPROCEDIMIENTOS.ID = PPROCEDIMIENTOS.ID"
	    		   + " AND " + DBUtil.getToUpperSQL("CTPROCEDIMIENTOS.COD_PCD") + " = '" + DBUtil.replaceQuotes(codPcd.toUpperCase()) + "'"
	    		   + " AND PPROCEDIMIENTOS.ESTADO = " + state;
	    
	    return factory.queryTableJoin(cnt, sql);
	}

	static public CollectionDAO getProcedureById(DbCnt cnt, int idPcd) throws ISPACException
	{
		TableJoinFactoryDAO factory = new TableJoinFactoryDAO();
		factory.addTable(TABLENAME, "CTPROCEDIMIENTOS");
		factory.addTable(PProcedimientoDAO.TABLENAME, "PPROCEDIMIENTOS");

	    String sql = "WHERE CTPROCEDIMIENTOS.ID = PPROCEDIMIENTOS.ID"
	    		   + " AND CTPROCEDIMIENTOS.ID = " + idPcd;

	    return factory.queryTableJoin(cnt, sql);
	}	
}