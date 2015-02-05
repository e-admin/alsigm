/*
 * Created on 21-abr-2005
 *
 */
package ieci.tdw.ispac.ispaclib.worklist;

import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.dao.join.TableJoinFactoryDAO;
import ieci.tdw.ispac.ispaclib.dao.wl.WLProcessDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.resp.Responsible;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

/**
 * @author juanin
 *
 */
public class WLWorklistDef extends WLObjectDef
{
    int stageIdPCD;
    String respList;

    /**
     * @return Returns the respList.
     */
    public String getRespList()
    {
        return respList;
    }
    /**
     * @param respList The respList to set.
     */
    public void setRespList(String respList)
    {
        this.respList = respList;
    }
    /**
     * @return Returns the stageIdPCD.
     */
    public int getStageIdPCD()
    {
        return stageIdPCD;
    }
    /**
     * @param stageIdPCD The stageIdPCD to set.
     */
    public void setStageIdPCD(int stageIdPCD)
    {
        this.stageIdPCD = stageIdPCD;
    }

    public String getBindField()
    {
        return getName()+".NUMEXP";
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.worklist.WLObjectDef#addJoin(ieci.tdw.ispac.ispaclib.dao.join.TableJoinFactoryDAO)
     */
    public void addJoin(DbCnt cnt, TableJoinFactoryDAO joinfactory) throws ISPACException
    {
        joinfactory.addTable(WLProcessDAO.TABLENAME,getName());
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.worklist.WLObjectDef#getJoinBind()
     */
    public String getJoinBind(DbCnt cnt, String worklistbindfield) throws ISPACException
    {
    	StringBuffer sql = new StringBuffer();

    	sql.append("( ")
    	   .append(getName())
    	   .append(".ID_STAGEPCD = ")
    	   .append(getStageIdPCD());

    	if (StringUtils.isNotBlank(getRespList()) && !Responsible.SUPERVISOR.equalsIgnoreCase(getRespList())) {

	    	String sqlResponsibles = DBUtil.addInResponsibleCondition(getName() + ".RESP", getRespList());

			// Añadir la responsabilidad y consultar los permisos asignados
			sql.append(" AND ( ")
			   .append(sqlResponsibles)
			   .append(" OR ( ")
			   .append("SELECT COUNT(*) FROM SPAC_PERMISOS SPC_PERMS WHERE ( ")
			   // Procedimiento
			   .append("(SPC_PERMS.TP_OBJ = ").append(ISecurityAPI.PERMISSION_TPOBJ_PROCEDURE)
			   .append(" AND SPC_PERMS.ID_OBJ = ").append(getName()).append(".ID_PCD)  ")
			  /* // Proceso
			   .append(" OR (SPC_PERMS.TP_OBJ = ").append(ISecurityAPI.PERMISSION_TPOBJ_PROCESS)
			   .append(" AND SPC_PERMS.ID_OBJ = ").append(getName()).append(".ID) OR ")
			   // Fase
			   .append("(SPC_PERMS.TP_OBJ = ").append(ISecurityAPI.PERMISSION_TPOBJ_STAGE)
			   .append(" AND SPC_PERMS.ID_OBJ = ").append(getName()).append(".ID_STAGE) OR ")
			   // Fase con tramites
			   .append("(SPC_PERMS.TP_OBJ = ").append(ISecurityAPI.PERMISSION_TPOBJ_STAGE_TASKS)
			   .append(" AND SPC_PERMS.ID_OBJ = ").append(getName()).append(".ID_STAGE) OR ")
			   // Fase en el procedimiento
			   .append("(SPC_PERMS.TP_OBJ = ").append(ISecurityAPI.PERMISSION_TPOBJ_STAGE_PCD)
			   .append(" AND SPC_PERMS.ID_OBJ = ").append(getName()).append(".ID_STAGEPCD) OR ")
			   // Fase en el procedimiento con tramites
			   .append("(SPC_PERMS.TP_OBJ = ").append(ISecurityAPI.PERMISSION_TPOBJ_STAGE_PCD_TASKS)
			   .append(" AND SPC_PERMS.ID_OBJ = ").append(getName()).append(".ID_STAGEPCD) ")*/
			   .append(") AND ")
			   .append(DBUtil.addInResponsibleCondition("SPC_PERMS.ID_RESP", getRespList()))
			   .append(") > 0 ) ");
    	}

        sql.append(")");

        return sql.toString();
    }
}