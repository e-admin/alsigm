/*
 * Created on 18-may-2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package ieci.tdw.ispac.ispaclib.dao.wl;

import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.resp.Responsible;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.sql.Types;
import java.util.List;

/**
 * @author marisa
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class WLStageDAO extends ObjectDAO {
	static final String TABLENAME 	= "SPAC_FASES";
	//static final String IDSEQUENCE
	static final String IDKEY 		= "ID";

	static final Property[] TABLECOLUMNS=
	{
        new Property(0,"ID_FASE",Types.NUMERIC),
        new Property(1,"COUNT","COUNT(ID_FASE)",Types.NUMERIC)
	};

	/**
	 *
	 * @throws ISPACException
	 */
	public WLStageDAO() throws ISPACException {
		super(TABLECOLUMNS);
	}

	/**
	 *
	 * @param cnt
	 * @throws ISPACException
	 */
	public WLStageDAO(DbCnt cnt) throws ISPACException {
		super(cnt, TABLECOLUMNS);
	}

	/**
	 *
	 * @param cnt
	 * @param id
	 * @throws ISPACException
	 */
	public WLStageDAO(DbCnt cnt, int id) throws ISPACException {
		super(cnt, id, TABLECOLUMNS);
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
	 * método que devuelve una colección de fases de un procedimiento
	 * en función de la responsabilidad
	 *
	 * @param cnt clase DbCnt
	 * @param resp responsable
	 * @param proc_id identificador del procedimiento
	 * return colección de fases
	 */
	public static CollectionDAO getStages(DbCnt cnt, String resp, int proc_id)
	throws ISPACException
	{
		return getStages(cnt, resp, proc_id, null);
	}
	
	public static CollectionDAO getStages(DbCnt cnt, String resp, int proc_id, List id_pcd_padres)
	throws ISPACException
	{
		StringBuffer sql = new StringBuffer();

		sql.append(" WHERE ESTADO = 1 AND ID_PCD = ")
		   .append(proc_id)
		   .append(addAndInResponsibleOrExistPermissionCondition(resp , id_pcd_padres))
		   .append(" GROUP BY ID_FASE ORDER BY ID_FASE");

		CollectionDAO objlist = new CollectionDAO(WLStageDAO.class);
		objlist.query(cnt, sql.toString());

		return objlist;
	}

	/**
	 * Obtiene la fase por numero de expediente
	 *
	 * @param cnt clase DbCnt
	 * @param resp responsable
	 * @param numExp número de expediente
	 * @return fase
	 * @throws ISPACException
	 */
	public static CollectionDAO getStage(DbCnt cnt, String resp, String numExp)
	throws ISPACException
	{
		StringBuffer sql = new StringBuffer();

		sql.append(" WHERE ESTADO = 1 AND NUMEXP = '")
		   .append(DBUtil.replaceQuotes(numExp))
		   .append("'")
		   .append(addAndInResponsibleOrExistPermissionCondition(resp))
		   .append(" GROUP BY ID_FASE");

		CollectionDAO objlist = new CollectionDAO(WLStageDAO.class);
		objlist.query(cnt, sql.toString());

		return objlist;
	}
	
	public static String addInResponsibleOrExistPermissionCondition(String inResponsibleCondition, String inPermResponsibleCondition)
	throws ISPACException{

		StringBuffer sql = new StringBuffer();

		if (StringUtils.isNotBlank(inResponsibleCondition)) {

			// Añadir la responsabilidad
			// y consultar los permisos asignados
			sql.append(getSqlInResponsibleOrExistPermissionCondition(inResponsibleCondition, inPermResponsibleCondition,null ));
		}

		return sql.toString();
	}

	public static String addInResponsibleOrExistPermissionCondition(String resp)
	throws ISPACException
	{
		return addInResponsibleOrExistPermissionConditionPcdSubPcd(resp, null);
	}

	
	public static String addInResponsibleOrExistPermissionConditionPcdSubPcd(String resp , List id_pcd_padres)
	throws ISPACException
	{
		String sql = " ";

		// Obtener la condición SQL de responsabilidad y de permisos
		// siempre que la responsabilidad no sea de Supervisor
		if (StringUtils.isNotBlank(resp) && !Responsible.SUPERVISOR.equalsIgnoreCase(resp)) {

			String sqlResponsibles = DBUtil.addInResponsibleCondition("ID_RESP", resp);

			// Añadir la responsabilidad y consultar los permisos asignados
			sql = getSqlInResponsibleOrExistPermissionCondition(sqlResponsibles, DBUtil.addInResponsibleCondition("SPC_PERMS.ID_RESP", resp),id_pcd_padres);
		}

		return sql;
	}
	protected static String getSqlInResponsibleOrExistPermissionCondition(String inResponsibleCondition, String inPermResponsibleCondition, List id_pcd_padres)
	throws ISPACException
	{
		StringBuffer sql = new StringBuffer();
		String cond="ID_PCD";

		if(id_pcd_padres!=null  && id_pcd_padres.size()>0){
			cond=((Integer) id_pcd_padres.get(0)).toString();
			for(int i=1; i<id_pcd_padres.size();i++)
			{
				cond+=" OR  SPC_PERMS.ID_OBJ="+id_pcd_padres.get(i)+" ";
			}
		}
		sql.append(" ( ")
		   .append(inResponsibleCondition)
		   .append(" OR ( ")
		   .append("SELECT COUNT(*) FROM SPAC_PERMISOS SPC_PERMS WHERE ( ")
		   // Procedimiento
		   .append("(SPC_PERMS.TP_OBJ = ").append(ISecurityAPI.PERMISSION_TPOBJ_PROCEDURE)
		   .append(" AND ( SPC_PERMS.ID_OBJ ="+cond+" ) ) ")
	/*	   // Proceso
		   .append(" OR (SPC_PERMS.TP_OBJ = ").append(ISecurityAPI.PERMISSION_TPOBJ_PROCESS)
		   .append(" AND SPC_PERMS.ID_OBJ = ID_EXP) OR ")
		   // Fase
		   .append("(SPC_PERMS.TP_OBJ = ").append(ISecurityAPI.PERMISSION_TPOBJ_STAGE)
		   .append(" AND SPC_PERMS.ID_OBJ = ID) OR ")
		   // Fase con tramites
		   .append("(SPC_PERMS.TP_OBJ = ").append(ISecurityAPI.PERMISSION_TPOBJ_STAGE_TASKS)
		   .append(" AND SPC_PERMS.ID_OBJ = ID) OR ")
		   // Fase en el procedimiento
		   .append("(SPC_PERMS.TP_OBJ = ").append(ISecurityAPI.PERMISSION_TPOBJ_STAGE_PCD)
		   .append(" AND SPC_PERMS.ID_OBJ = ID_FASE) OR ")
		   // Fase en el procedimiento con tramites
		   .append("(SPC_PERMS.TP_OBJ = ").append(ISecurityAPI.PERMISSION_TPOBJ_STAGE_PCD_TASKS)
		   .append(" AND SPC_PERMS.ID_OBJ = ID_FASE) ")*/
		   .append(") AND ")
		   .append(inPermResponsibleCondition)
		   .append(") > 0 ) ");

		return sql.toString();
	}

	public static String addAndInResponsibleOrExistPermissionCondition(String resp)
	throws ISPACException
	{
		return addAndInResponsibleOrExistPermissionCondition(resp, null);
	}
	
	public static String addAndInResponsibleOrExistPermissionCondition(String resp , List id_pcd_padres)
	throws ISPACException
	{
		String sql = addInResponsibleOrExistPermissionConditionPcdSubPcd(resp, id_pcd_padres);

		if (StringUtils.isNotBlank(sql)) {
			sql = " AND " + sql;
		}

		return sql;
	}
}