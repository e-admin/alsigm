package ieci.tdw.ispac.ispaclib.worklist;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.dao.join.TableJoinFactoryDAO;
import ieci.tdw.ispac.ispaclib.dao.wl.WLClosedTaskDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;

public class WLClosedTaskListDef extends WLTaskListDef
{

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.worklist.WLObjectDef#addJoin(ieci.tdw.ispac.ispaclib.dao.join.TableJoinFactoryDAO)
     */
    public void addJoin(DbCnt cnt, TableJoinFactoryDAO joinfactory) throws ISPACException
    {
        joinfactory.addTable(WLClosedTaskDAO.TABLENAME,getName());
    }

}