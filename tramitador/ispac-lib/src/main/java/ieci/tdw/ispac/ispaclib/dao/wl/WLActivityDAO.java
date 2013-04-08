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
 * DAO de acceso a la información de las actividades en la bandeja de trabajo.
 * 
 */
public class WLActivityDAO extends ObjectDAO {
	
	private static final long serialVersionUID = -7795791983492511599L;
	
	static final String TABLENAME = "SPAC_WL_ACTIVITY";
	// static final String IDSEQUENCE
	static final String IDKEY = "ID";

	static final Property[] TABLECOLUMNS=
	{
        new Property(0,"ID_PCD_TRAMITE",Types.NUMERIC),
        new Property(1,"ID_PCD",Types.NUMERIC),
        new Property(2,"ID_FASE",Types.NUMERIC),
        new Property(3,"NOMBRE",Types.VARCHAR),
        new Property(4,"COUNT","COUNT(ID_FASE)",Types.NUMERIC)
	};

	public WLActivityDAO() throws ISPACException {
		super(null);
	}

	public WLActivityDAO(DbCnt cnt) throws ISPACException {
		super(cnt, null);
	}

	public WLActivityDAO(DbCnt cnt, int id) throws ISPACException {
		super(cnt, id, null);
	}

	public WLActivityDAO(Property[] args) throws ISPACException {
		super(args);
	}

	public WLActivityDAO(DbCnt cnt, Property[] args) throws ISPACException {
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

	public static CollectionDAO getActivities(DbCnt cnt, String resp, int proc_id, List id_pcd_padres)
	throws ISPACException
	{
		StringBuffer sql = new StringBuffer();

		sql.append(" WHERE ESTADO = 1 AND ID_PCD = ").append(proc_id)
		   .append(addAndInResponsibleOrExistPermissionCondition(resp , id_pcd_padres))
		   .append(" GROUP BY ID_PCD_TRAMITE, ID_PCD, ID_FASE, NOMBRE");

		CollectionDAO objlist = new CollectionDAO(WLActivityDAO.class,
				new Object[] { TABLECOLUMNS });
		objlist.query(cnt, sql.toString());

		return objlist;
	}

	protected static String addInResponsibleOrExistPermissionConditionPcdSubPcd(
			String resp, List id_pcd_padres) throws ISPACException {
		
		String sql = " ";

		// Obtener la condición SQL de responsabilidad y de permisos
		// siempre que la responsabilidad no sea de Supervisor
		if (StringUtils.isNotBlank(resp)
				&& !Responsible.SUPERVISOR.equalsIgnoreCase(resp)) {

			String sqlResponsibles = DBUtil.addInResponsibleCondition(TABLENAME
					+ ".ID_RESP", resp);

			// Añadir la responsabilidad y consultar los permisos asignados
			sql = getSqlInResponsibleOrExistPermissionCondition(
					sqlResponsibles,
					DBUtil.addInResponsibleCondition("SPC_PERMS.ID_RESP", resp),
					id_pcd_padres);
		}

		return sql;
	}
	
	protected static String getSqlInResponsibleOrExistPermissionCondition(
			String inResponsibleCondition, String inPermResponsibleCondition,
			List id_pcd_padres) throws ISPACException {
		
		StringBuffer sql = new StringBuffer();
		String cond = "ID_PCD_TRAMITE";

		if (id_pcd_padres != null && id_pcd_padres.size() > 0) {
			cond = ((Integer) id_pcd_padres.get(0)).toString();
			for (int i = 1; i < id_pcd_padres.size(); i++) {
				cond += " OR  SPC_PERMS.ID_OBJ=" + id_pcd_padres.get(i) + " ";
			}
		}
		sql.append(" ( ")
				.append(inResponsibleCondition)
				.append(" OR ( ")
				.append("SELECT COUNT(*) FROM SPAC_PERMISOS SPC_PERMS WHERE ( ")
				// Procedimiento
				.append("(SPC_PERMS.TP_OBJ = ")
				.append(ISecurityAPI.PERMISSION_TPOBJ_PROCEDURE)
				.append(" AND ( SPC_PERMS.ID_OBJ =" + cond + " ) ) ")
				.append(") AND ").append(inPermResponsibleCondition)
				.append(") > 0 ) ");

		return sql.toString();
	}

	protected static String addAndInResponsibleOrExistPermissionCondition(
			String resp, List id_pcd_padres) throws ISPACException {

		String sql = addInResponsibleOrExistPermissionConditionPcdSubPcd(resp,
				id_pcd_padres);

		if (StringUtils.isNotBlank(sql)) {
			sql = " AND " + sql;
		}

		return sql;
	}
}