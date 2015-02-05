
package ieci.tdw.ispac.ispaclib.dao.security;

import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SecurityAPI;
import ieci.tdw.ispac.api.item.IProcess;
import ieci.tdw.ispac.api.item.IStage;
import ieci.tdw.ispac.api.item.ITask;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.MemberDAO;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.dao.entity.EntityDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class PermissionDAO extends ObjectDAO
{
	static final String TABLENAME = "SPAC_PERMISOS";
	// No existe secuencia para generar claves primarias en esta tabla
	static final String IDSEQUENCE = null;

	// La clave es multivaluada
	static final String IDKEY = null;

	static final String IDKEY1 = "TP_OBJ";
	static final String IDKEY2 = "ID_OBJ";
	static final String IDKEY3 = "ID_RESP";

	public static final String FIELD_PERMISION_TYPE = "PERMISO";

	/**
	 *
	 * @throws ISPACException
	 */
	public PermissionDAO() throws ISPACException {
		super(null);
	}

	/**
	 * @param cnt
	 * @throws ISPACException
	 */
	public PermissionDAO(DbCnt cnt) throws ISPACException {
		super(cnt, null);
	}

	/**
	 *
	 * @param cnt
	 * @param id
	 * @throws ISPACException
	 */
	public PermissionDAO(DbCnt cnt, int id) throws ISPACException {
		super(cnt, id, null);
	}

	public String getTableName()
	{
		return TABLENAME;
	}

	protected String getDefaultSQL(int nActionDAO) throws ISPACException
	{
		return " WHERE " + IDKEY1 + " = " + getInt(IDKEY1) + " AND " + IDKEY2
				+ " = " + getInt(IDKEY2) + "  AND " + IDKEY3 + " = '" + DBUtil.replaceQuotes(getString(IDKEY3)) + "'" ;
	}

	public String getKeyName() throws ISPACException
	{
		throw new ISPACException("El acceso a la tabla " + TABLENAME
				+ " tiene más de una clave única");
	}

	public void newObject(DbCnt cnt) throws ISPACException
	{
	}
	
	public void setNewObject(){
		mbNewObject = true;
		
		MemberDAO member=((MemberDAO) mMembersMap.get(IDKEY1));
		member.markDirty();
		mMembersMap.put(IDKEY1, member);
		
		member=((MemberDAO) mMembersMap.get(IDKEY2));
		member.markDirty();
		mMembersMap.put(IDKEY2, member);
		
		member=((MemberDAO) mMembersMap.get(IDKEY3));
		member.markDirty();
		mMembersMap.put(IDKEY3, member);
		
		member=((MemberDAO) mMembersMap.get("RESP_NAME"));
		member.markDirty();
		mMembersMap.put("RESP_NAME", member);
		
		member=((MemberDAO) mMembersMap.get("PERMISO"));
		member.markDirty();
		mMembersMap.put("PERMISO", member);
	   


	}

	public static boolean existPermissions(DbCnt cnt, ITask task, String resp, int[] typePermissions)
		    throws ISPACException
			{
				boolean exist = false;
				StringBuffer sqlWhere = new StringBuffer();

				sqlWhere.append(getSqlWhere(task))
						.append(addAndInTypePermissionsCondition(typePermissions))
						.append(" AND ")
						.append(DBUtil.addInResponsibleCondition("ID_RESP", resp));

				CollectionDAO objlist = new CollectionDAO(PermissionDAO.class);
				int count = objlist.count(cnt, sqlWhere.toString());

				if (count > 0) {
					exist = true;
				}

				return exist;
			}
	
	public static boolean existPermissions(DbCnt cnt, IStage stage, String resp, int[] typePermissions)
    throws ISPACException
	{
		boolean exist = false;
		StringBuffer sqlWhere = new StringBuffer();
		
	

		sqlWhere.append(getSqlWhere(stage))
				.append(addAndInTypePermissionsCondition(typePermissions))
				.append(" AND ")
				.append(DBUtil.addInResponsibleCondition("ID_RESP", resp));

		CollectionDAO objlist = new CollectionDAO(PermissionDAO.class);
		int count = objlist.count(cnt, sqlWhere.toString());

		if (count > 0) {
			exist = true;
		}

		return exist;
	}

	protected static String addAndInTypePermissionsCondition(int[] typePermissions) {

		StringBuffer sql = new StringBuffer();

		if (typePermissions != null) {

			sql.append(" AND ")
			   .append(FIELD_PERMISION_TYPE)
			   .append(" IN ( ");

			for (int i = 0; i < typePermissions.length; i++) {
				if (i != 0) {
					sql.append(" ,");
				}
				sql.append(typePermissions[i]);
			}
			sql.append(" ) ");
		}

		return sql.toString();
	}

	public static CollectionDAO getPermissions(DbCnt cnt, IStage stage, String resp)
    throws ISPACException
	{
		StringBuffer sqlWhere = new StringBuffer();

		sqlWhere.append(getSqlWhere(stage))
				.append(" AND ")
				.append(DBUtil.addInResponsibleCondition("ID_RESP", resp));

        CollectionDAO objlist = new CollectionDAO(PermissionDAO.class);
        objlist.queryDistinct(cnt, sqlWhere.toString(), new String[] {FIELD_PERMISION_TYPE});
        return objlist;
	}

	protected static String getSqlWhere(IStage stage)
    throws ISPACException
	{
		StringBuffer sqlWhere = new StringBuffer();

		sqlWhere.append(" WHERE ( ")

				// Procedimiento
		   		.append("(TP_OBJ = ").append(ISecurityAPI.PERMISSION_TPOBJ_PROCEDURE)
		   		.append(" AND ID_OBJ = ").append(stage.getInt("ID_PCD")).append(")  ")
		   		/*// Proceso
		   		.append(" OR (TP_OBJ = ").append(ISecurityAPI.PERMISSION_TPOBJ_PROCESS)
		   		.append(" AND ID_OBJ = ").append(stage.getInt("ID_EXP")).append(") OR ")
		   		// Fase
		   		.append("(TP_OBJ = ").append(ISecurityAPI.PERMISSION_TPOBJ_STAGE)
		   		.append(" AND ID_OBJ = ").append(stage.getInt("ID")).append(") OR ")
		   		// Fase con tramites
		   		.append("(TP_OBJ = ").append(ISecurityAPI.PERMISSION_TPOBJ_STAGE_TASKS)
		   		.append(" AND ID_OBJ = ").append(stage.getInt("ID")).append(") OR ")
		   		// Fase en el procedimiento
		   		.append("(TP_OBJ = ").append(ISecurityAPI.PERMISSION_TPOBJ_STAGE_PCD)
		   		.append(" AND ID_OBJ = ").append(stage.getInt("ID_FASE")).append(") OR ")
		   		// Fase en el procedimiento con tramites
		   		.append("(TP_OBJ = ").append(ISecurityAPI.PERMISSION_TPOBJ_STAGE_PCD_TASKS)
		   		.append(" AND ID_OBJ = ").append(stage.getInt("ID_FASE")).append(") ")*/
	.append(") ");

		return sqlWhere.toString();
	}

	public static CollectionDAO getPermissions(DbCnt cnt, ITask task, String resp)
    throws ISPACException
	{
		StringBuffer sqlWhere = new StringBuffer();

		sqlWhere.append(getSqlWhere(task))
				.append(" AND ")
				.append(DBUtil.addInResponsibleCondition("ID_RESP", resp));

        CollectionDAO objlist = new CollectionDAO(PermissionDAO.class);
        objlist.queryDistinct(cnt, sqlWhere.toString(), new String[] {FIELD_PERMISION_TYPE});
        return objlist;
	}
	
	public static boolean existPermissionEditNotFollowSupervision(DbCnt cnt , IStage stage, String cadenaResp  , Map supervisorsUID)
	throws ISPACException
	{
		boolean exist=false;
		StringBuffer sqlWhere = new StringBuffer();
		sqlWhere.append(getSqlWhere(stage))
				.append(" AND ")
				.append(DBUtil.addInResponsibleCondition("ID_RESP", cadenaResp))
				.append(" AND PERMISO="+ISecurityAPI.PERMISSION_TYPE_EDIT)
				.append(" AND ID_RESP NOT IN ( SELECT UID_SUPERVISADO FROM SPAC_SS_SUPERVISION WHERE ")
				.append(" TIPO="+SecurityAPI.SUPERV_FOLLOWEDMODE)
				.append(" AND  ("+DBUtil.addInResponsibleCondition("UID_SUPERVISOR", SupervisionDAO.getSupervisorsUID(supervisorsUID))+"))");

        CollectionDAO objlist = new CollectionDAO(PermissionDAO.class);
       int count= objlist.count(cnt, sqlWhere.toString());
   		if (count > 0) {
   			exist = true;
   		}

   		return exist;
		
	}
	
	
	public static boolean existPermissionEditNotFollowSupervision(DbCnt cnt , ITask task, String cadenaResp, Map supervisorsUID)
	throws ISPACException
	{
		
		{
			boolean exist=false;
			StringBuffer sqlWhere = new StringBuffer();
			sqlWhere.append(getSqlWhere(task))
					.append(" AND ")
					.append(DBUtil.addInResponsibleCondition("ID_RESP", cadenaResp))
					.append(" AND PERMISO="+ISecurityAPI.PERMISSION_TYPE_EDIT)
					.append(" AND ID_RESP NOT IN ( SELECT UID_SUPERVISADO FROM SPAC_SS_SUPERVISION WHERE ")
					.append(" TIPO="+SecurityAPI.SUPERV_FOLLOWEDMODE)
					.append(" AND  ("+DBUtil.addInResponsibleCondition("UID_SUPERVISOR", SupervisionDAO.getSupervisorsUID(supervisorsUID))+"))");

	        CollectionDAO objlist = new CollectionDAO(PermissionDAO.class);
	       int count= objlist.count(cnt, sqlWhere.toString());
	   		if (count > 0) {
	   			exist = true;
	   		}

	   		return exist;
			
		}
		
	}
	
	public static boolean existPermissionEditNotFollowSupervision(DbCnt cnt , IProcess process, String cadenaResp, Map supervisorsUID )
	throws ISPACException		
		{
			boolean exist=false;
			StringBuffer sqlWhere = new StringBuffer();
			sqlWhere.append(getSqlWhere(process))
					.append(" AND ")
					.append(DBUtil.addInResponsibleCondition("ID_RESP", cadenaResp))
					.append(" AND PERMISO="+ISecurityAPI.PERMISSION_TYPE_EDIT)
					.append(" AND ID_RESP NOT IN ( SELECT UID_SUPERVISADO FROM SPAC_SS_SUPERVISION WHERE ")
					.append(" TIPO="+SecurityAPI.SUPERV_FOLLOWEDMODE)
					.append(" AND  ("+DBUtil.addInResponsibleCondition("UID_SUPERVISOR", SupervisionDAO.getSupervisorsUID(supervisorsUID))+"))");

	        CollectionDAO objlist = new CollectionDAO(PermissionDAO.class);
	       int count= objlist.count(cnt, sqlWhere.toString());
	   		if (count > 0) {
	   			exist = true;
	   		}

	   		return exist;
			
		}
				


	protected static String getSqlWhere(ITask task)
    throws ISPACException
	{
		StringBuffer sqlWhere = new StringBuffer();

		sqlWhere.append(" WHERE ( ")
				// Procedimiento
		   		.append("(TP_OBJ = ").append(ISecurityAPI.PERMISSION_TPOBJ_PROCEDURE)
		   		.append(" AND ID_OBJ = ").append(task.getInt("ID_PCD")).append(")  ")
		   	/*	// Proceso
		   		.append(" OR (TP_OBJ = ").append(ISecurityAPI.PERMISSION_TPOBJ_PROCESS)
		   		.append(" AND ID_OBJ = ").append(task.getInt("ID_EXP")).append(") OR ")
		   		// Tramite
		   		.append("(TP_OBJ = ").append(ISecurityAPI.PERMISSION_TPOBJ_TASK)
		   		.append(" AND ID_OBJ = ").append(task.getInt("ID")).append(") OR ")
		   		// Tramite en el procedimiento
		   		.append("(TP_OBJ = ").append(ISecurityAPI.PERMISSION_TPOBJ_TASK_PCD)
		   		.append(" AND ID_OBJ = ").append(task.getInt("ID_TRAMITE")).append(") OR ")
		   		// Fase con tramites
		   		.append("(TP_OBJ = ").append(ISecurityAPI.PERMISSION_TPOBJ_STAGE_TASKS)
		   		.append(" AND ID_OBJ = ").append(task.getInt("ID_FASE_EXP")).append(") OR ")
		   		// Fase en el procedimiento con tramites
		   		.append("(TP_OBJ = ").append(ISecurityAPI.PERMISSION_TPOBJ_STAGE_PCD_TASKS)
		   		.append(" AND ID_OBJ = ").append(task.getInt("ID_FASE_PCD")).append(") ")*/
		   		.append(") ");

		return sqlWhere.toString();
	}

	public static boolean existPermissions(DbCnt cnt, IProcess process, String resp, int[] typePermissions)
    throws ISPACException
	{
		boolean exist = false;
		StringBuffer sqlWhere = new StringBuffer();

		sqlWhere.append(getSqlWhere(process))
				.append(addAndInTypePermissionsCondition(typePermissions))
				.append(" AND ")
				.append(DBUtil.addInResponsibleCondition("ID_RESP", resp));

		CollectionDAO objlist = new CollectionDAO(PermissionDAO.class);
		int count = objlist.count(cnt, sqlWhere.toString());

		if (count > 0) {
			exist = true;
		}

		return exist;
	}

	protected static String getSqlWhere(IProcess process)
    throws ISPACException
	{
		StringBuffer sqlWhere = new StringBuffer();

		sqlWhere.append(" WHERE ( ")
				// Procedimiento
		   		.append("(TP_OBJ = ").append(ISecurityAPI.PERMISSION_TPOBJ_PROCEDURE)
			   .append(" AND ID_OBJ = ").append(process.getInt("ID_PCD")).append(")  ")
		   		// Proceso
			   //.append(" OR (TP_OBJ = ").append(ISecurityAPI.PERMISSION_TPOBJ_PROCESS)
			   //.append(" AND ID_OBJ = ").append(process.getInt("ID")).append(") ")
			   .append(") ");

		return sqlWhere.toString();
	}

	public static CollectionDAO getPermissions(DbCnt cnt, int typeObject, int idObject, int[] typePermissions)
    throws ISPACException
	{
		StringBuffer sqlWhere = new StringBuffer();

		sqlWhere.append(" WHERE  ")
				.append(" TP_OBJ = ").append(typeObject)
				.append(" AND ID_OBJ = ").append(idObject);


		if (typePermissions != null) {
			sqlWhere.append(addAndInTypePermissionsCondition(typePermissions));
		}

        CollectionDAO objlist = new CollectionDAO(PermissionDAO.class);
        objlist.queryDistinct(cnt, sqlWhere.toString());
        return objlist;
	}
	
	public static CollectionDAO getPermissions(DbCnt cnt, int typeObject, int idObject, String uid)
		    throws ISPACException
			{
				StringBuffer sqlWhere = new StringBuffer();

				sqlWhere.append(" WHERE  ")
						.append(" TP_OBJ = ").append(typeObject)
						.append(" AND ID_OBJ = ").append(idObject)
						.append(" AND ID_RESP='"+DBUtil.replaceQuotes(uid)+"'");



		        CollectionDAO objlist = new CollectionDAO(PermissionDAO.class);
		        objlist.queryDistinct(cnt, sqlWhere.toString());
		        return objlist;
			}

	public static boolean existPermission(DbCnt cnt, int typeObject, int idObject, String idResp, int typePermission)
    throws ISPACException
	{
		boolean exist = false;
		StringBuffer sqlWhere = new StringBuffer();

		sqlWhere.append(" WHERE  ")
				.append(" TP_OBJ = ").append(typeObject)
				.append(" AND ID_OBJ = ").append(idObject)
				.append(" AND PERMISO = ").append(typePermission);



		if (StringUtils.isNotBlank(idResp)) {
			sqlWhere.append(" AND ID_RESP = '").append(DBUtil.replaceQuotes(idResp)).append("' ");
		}

		CollectionDAO objlist = new CollectionDAO(PermissionDAO.class);
		int count = objlist.count(cnt, sqlWhere.toString());

		if (count > 0) {
			exist = true;
		}

		return exist;
	}

	public static CollectionDAO getPermissions(DbCnt cnt, EntityDAO entity,String resp)throws ISPACException{
			StringBuffer sqlWhere = new StringBuffer();

			sqlWhere.append(getSqlWhere(entity))
					.append(" AND ")
					.append(DBUtil.addInResponsibleCondition("ID_RESP", resp));

	        CollectionDAO objlist = new CollectionDAO(PermissionDAO.class);
	        objlist.queryDistinct(cnt, sqlWhere.toString(), new String[] {FIELD_PERMISION_TYPE});
	        return objlist;
		}

	private static Object getSqlWhere(EntityDAO entity)
		    throws ISPACException
			{
				StringBuffer sqlWhere = new StringBuffer();

					if (entity.getCTEntityId() == SpacEntities.SPAC_DT_TRAMITES){
						sqlWhere.append(" WHERE ( ")
						// Procedimiento
				   		.append("(TP_OBJ = ").append(ISecurityAPI.PERMISSION_TPOBJ_PROCEDURE)
				   		.append(" AND ID_OBJ IN (SELECT ID_PCD FROM SPAC_EXPEDIENTES WHERE NUMEXP = '")
				   		.append(DBUtil.replaceQuotes(entity.getString("NUMEXP")))
				   		.append("')) ")
				   		.append(") ");
					}

				return sqlWhere.toString();
			}

}
