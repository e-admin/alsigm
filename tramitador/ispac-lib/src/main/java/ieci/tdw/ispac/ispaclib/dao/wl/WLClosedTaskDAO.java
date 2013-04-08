package ieci.tdw.ispac.ispaclib.dao.wl;

import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.ITask;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.resp.Responsible;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;

import java.sql.Types;

import org.apache.commons.lang.StringUtils;

/**
 * DAO para la información de trámites cerrados de la lista de trabajo.
 * 
 */
public class WLClosedTaskDAO extends ObjectDAO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -726409677701046371L;
	
	public static final String TABLENAME 	= "SPAC_WL_CLOSE_TASK";
	//static final String IDSEQUENCE
	public static final String IDKEY 		= "ID";

	
	final static Property[] TABLECOLUMNS = 
	{
        new Property(0,"ID_CTTASK",Types.NUMERIC),
        new Property(1,"NAME",Types.VARCHAR),
        new Property(2,"COUNT","COUNT(*)",Types.NUMERIC),
        new Property(3,"TIPO", Types.NUMERIC),
        new Property(4,"ID_SUBPCD", Types.NUMERIC)
	};		
	
	final static Property[] TABLECOLUMNS_PCD = 
	{
        new Property(0,"ID_CTTASK",Types.NUMERIC),
        new Property(1,"NAME",Types.VARCHAR),
        new Property(2,"COUNT","COUNT(*)",Types.NUMERIC),
        new Property(3,"TIPO", Types.NUMERIC),
        new Property(4,"ID_PROC", Types.NUMERIC)
	};	

	/**
	 * 
	 * @throws ISPACException
	 */
	public WLClosedTaskDAO() throws ISPACException {
		super(null);
	}

	/**
	 * 
	 * @param cnt
	 * @throws ISPACException
	 */
	public WLClosedTaskDAO(DbCnt cnt) throws ISPACException {
		super(cnt, null);
	}

	/**
	 * 
	 * @param cnt
	 * @param id
	 * @throws ISPACException
	 */
	public WLClosedTaskDAO(DbCnt cnt, int id) throws ISPACException {
		super(cnt, id, null);
	}
	
	/**
	 * 
	 * @param args
	 * @throws ISPACException
	 */
	public WLClosedTaskDAO(Property[] args)throws ISPACException {
		super(args);
	}
	
	/**
	 * 
	 * @param cnt
	 * @param args
	 * @throws ISPACException
	 */
	public WLClosedTaskDAO(DbCnt cnt, Property[] args)throws ISPACException {
		super(cnt, args);
	}

	public String getTableName() {
		return TABLENAME;
	}

	protected String getDefaultSQL(int nActionDAO) throws ISPACException {
		return " WHERE " + IDKEY + " = " + getInt(IDKEY);
	}

	protected void newObject(DbCnt cnt) throws ISPACException {
		throw new ISPACException("No se tiene secuencia");
	}

	public String getKeyName() throws ISPACException {
		return IDKEY;
	}



	/**
	 * método que devuelve una colección de trámites cerrados
	 * de un expediente
	 * @param cnt clase DbCnt
	 * @param numexp número de expediente
	 * @return colección de trámites activos
	 */
	public static CollectionDAO getTaskByNumexp(DbCnt cnt, String numexp)
			throws ISPACException {
		String sql = "WHERE NUMEXP = '" + DBUtil.replaceQuotes(numexp) + "'";
		CollectionDAO objlist = new CollectionDAO(WLClosedTaskDAO.class);
		objlist.query(cnt,sql);
		return objlist;
	}

    public static CollectionDAO getTasks(DbCnt cnt, String [] taskids)
    		throws ISPACException {
    	
        String sql = "WHERE ID IN (" + StringUtils.join(taskids, ',') + ") ";
        CollectionDAO objlist = new CollectionDAO(WLClosedTaskDAO.class);
        objlist.query(cnt, sql);
        return objlist;
    }
	
 

	public static CollectionDAO getTasksGroupByPcd(DbCnt cnt, String resp)
	throws ISPACException
	{
		StringBuffer sql = new StringBuffer();

		sql.append(" WHERE 1=1 ")
		   .append(addAndInResponsibleOrExistPermissionCondition(resp))
		   .append(" GROUP BY ID_CTTASK, NAME, TIPO, ID_SUBPCD, ID_PROC ORDER BY NAME");

		CollectionDAO objlist = new CollectionDAO(WLClosedTaskDAO.class, new Object[]{TABLECOLUMNS_PCD});
		objlist.query(cnt, sql.toString());

		return objlist;
	}

	
	/**
	 * método que devuelve una colección de trámites cerrados de un trámite de un procedimiento
	 * en función de la responsabilidad
	 * 
	 * @param cnt clase DbCnt
	 * @param resp responsable
	 * @param idTaskPCD identificador del trámite en el procedimiento
	 * @return colección de trámites activos
	 */
	public static CollectionDAO getTasksPCD(DbCnt cnt, String resp, int taskPcdId)
	throws ISPACException
	{
		StringBuffer sql = new StringBuffer();

		sql.append(" WHERE ID_TASK = ")
		   .append(taskPcdId)
		   .append(addAndInResponsibleOrExistPermissionCondition(resp));

		CollectionDAO objlist = new CollectionDAO(WLClosedTaskDAO.class);
		objlist.query(cnt, sql.toString());

		return objlist;
	}

	/**
	 * método que devuelve una colección de trámites cerrados de un trámite
	 * en función de la responsabilidad
	 * 
	 * @param cnt clase DbCnt
	 * @param resp responsable
	 * @param taskCtlId identificador del trámite en el catálogo
	 * @return colección de trámites activos
	 * @throws ISPACException
	 */
	public static CollectionDAO getTasksCTL(DbCnt cnt, String resp, int taskCtlId)
	throws ISPACException
	{
		return getTasksCTL(cnt, resp, taskCtlId, -1);
	}

	/**
	 * método que devuelve una colección de trámites cerrados de un trámite
	 * en función de la responsabilidad
	 * 
	 * @param cnt clase DbCnt
	 * @param resp responsable
	 * @param taskCtlId identificador del trámite en el catálogo
	 * @param pcdId Identificador del procedimiento
	 * @return colección de trámites activos
	 * @throws ISPACException
	 */
	public static CollectionDAO getTasksCTL(DbCnt cnt, String resp, int taskCtlId, int pcdId)
	throws ISPACException
	{
		StringBuffer sql = new StringBuffer();

		sql.append(" WHERE ID_CTTASK = ")
		   .append(taskCtlId);
		   
		if (pcdId > -1) {
			sql.append(" AND ID_PROC = ").append(pcdId);
		}
		
		sql.append(addAndInResponsibleOrExistPermissionCondition(resp));

		CollectionDAO objlist = new CollectionDAO(WLClosedTaskDAO.class);
		objlist.query(cnt, sql.toString());

		return objlist;
	}	
	
	
    /**
	 * método que devuelve una colección de trámites activos
	 * en función de la responsabilidad
	 * 
	 * @param cnt clase DbCnt
	 * @param resp responsable
     * @return colección de trámites activos
     * @throws ISPACException
     */
	public static CollectionDAO getTasks(DbCnt cnt, String resp)
	throws ISPACException
	{
		StringBuffer sql = new StringBuffer();

		sql.append(" WHERE 1 = 1")
		   .append(addAndInResponsibleOrExistPermissionCondition(resp))
		   .append(" GROUP BY ID_CTTASK, NAME, TIPO, ID_SUBPCD ORDER BY NAME");

		CollectionDAO objlist = new CollectionDAO(WLClosedTaskDAO.class, new Object[]{TABLECOLUMNS});
		objlist.query(cnt, sql.toString());

		return objlist;
	}	
	
	
	
	public static String addInResponsibleOrExistPermissionCondition(String inResponsibleCondition, String inPermResponsibleCondition)
	throws ISPACException
	{
		StringBuffer sql = new StringBuffer();

		if (StringUtils.isNotBlank(inResponsibleCondition)) {

			// Añadir la responsabilidad
			// y consultar los permisos asignados
			sql.append(getSqlInResponsibleOrExistPermissionCondition(inResponsibleCondition, inPermResponsibleCondition));
		}

		return sql.toString();
	}

	public static String addInResponsibleOrExistPermissionCondition(String resp)
	throws ISPACException
	{
		String sql = " ";

		// Obtener la condición SQL de responsabilidad y de permisos
		// siempre que la responsabilidad no sea de Supervisor
		if (StringUtils.isNotBlank(resp) && !Responsible.SUPERVISOR.equalsIgnoreCase(resp)) {

			String sqlResponsibles = DBUtil.addInResponsibleCondition("RESP", resp);

			// Añadir la responsabilidad y consultar los permisos asignados
			sql = getSqlInResponsibleOrExistPermissionCondition(sqlResponsibles, DBUtil.addInResponsibleCondition("SPC_PERMS.ID_RESP", resp));
		}

		return sql;
	}

	protected static String getSqlInResponsibleOrExistPermissionCondition(String inResponsibleCondition, String inPermResponsibleCondition)
	throws ISPACException
	{
		StringBuffer sql = new StringBuffer();

		sql.append(" ( ")
		   .append(inResponsibleCondition)
		   .append(" OR ( ")
		   .append("SELECT COUNT(*) FROM SPAC_PERMISOS SPC_PERMS WHERE ( ")
		   // Procedimiento
		   .append("(SPC_PERMS.TP_OBJ = ").append(ISecurityAPI.PERMISSION_TPOBJ_PROCEDURE)
		   .append(" AND SPC_PERMS.ID_OBJ = ID_PROC)  ")

		   .append(") AND ")
		   .append(inPermResponsibleCondition)
		   .append(") > 0 ) ");

		return sql.toString();
	}

	public static String addAndInResponsibleOrExistPermissionCondition(String resp)
	throws ISPACException
	{
		String sql = addInResponsibleOrExistPermissionCondition(resp);

		if (StringUtils.isNotBlank(sql)) {
			sql = " AND " + sql;
		}

		return sql;
	}
}