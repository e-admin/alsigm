package ieci.tdw.ispac.ispaclib.worklist;

import ieci.tdw.ispac.api.ISPACEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.dao.join.TableJoinFactoryDAO;
import ieci.tdw.ispac.ispaclib.dao.wl.WLTaskDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;

public class WLTaskListDef extends WLObjectDef
{
	// Identificador del trámite en el catálogo
    int ctTaskId = ISPACEntities.ENTITY_NULLREGKEYID;
    // Identificador del procedimiento
    int pcdId = ISPACEntities.ENTITY_NULLREGKEYID;
    // Identificador del trámite en el procedimiento
    int idTaskPCD = ISPACEntities.ENTITY_NULLREGKEYID;
    // Responsabilidad
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
     * @return Returns the ctTaskId.
     */
    public int getCtTaskId()
    {
        return ctTaskId;
    }
    /**
     * @param ctTaskId The ctTaskId to set.
     */
    public void setCtTaskId(int ctTaskId)
    {
        this.ctTaskId = ctTaskId;
    }

    /**
     * @return Returns the pcdId.
     */
    public int getPcdId()
    {
        return pcdId;
    }
    /**
     * @param pcdId The pcdId to set.
     */
    public void setPcdId(int pcdId)
    {
        this.pcdId = pcdId;
    }

    /**
     * @return Returns the idTaskPCD.
     */
    public int getIdTaskPCD()
    {
        return idTaskPCD;
    }
    /**
     * @param idTaskPCD The idTaskPCD to set.
     */
    public void setIdTaskPCD(int idTaskPCD)
    {
        this.idTaskPCD = idTaskPCD;
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
        joinfactory.addTable(WLTaskDAO.TABLENAME,getName());
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.worklist.WLObjectDef#getJoinBind()
     */
    public String getJoinBind(DbCnt cnt, String worklistbindfield) throws ISPACException
    {
		StringBuffer sql = new StringBuffer();

		sql.append(" ( ");

		// Identificador del trámite en el catálogo
		if (getCtTaskId() != ISPACEntities.ENTITY_NULLREGKEYID) {

			sql.append(getName())
             .append(".ID_CTTASK = ")
             .append(getCtTaskId());
		}

		// Identificador del procedimiento
		if (getPcdId() != ISPACEntities.ENTITY_NULLREGKEYID) {

			if (sql.length() > 3) {
				sql.append(" AND ");
			}
			sql.append(getName())
			.append(".ID_PROC = ")
			.append(getPcdId());
		}

		// Identificador del trámite en el procedimiento
		if (getIdTaskPCD() != ISPACEntities.ENTITY_NULLREGKEYID) {

			if (sql.length() > 3) {
				sql.append(" AND ");
			}
			sql.append(getName())
			.append(".ID_TASK = ")
			.append(getIdTaskPCD());
		}

		// Añadir los responsables cuando no es el Supervisor
		sql.append(DBUtil.addAndInResponsibleCondition(getName() + ".RESP", getRespList()))
            .append(" ) ");

        return sql.toString();
    }

}