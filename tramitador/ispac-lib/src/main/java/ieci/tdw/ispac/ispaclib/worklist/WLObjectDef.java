/*
 * Created on 15-abr-2005
 *
 */
package ieci.tdw.ispac.ispaclib.worklist;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.dao.join.TableJoinFactoryDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;

/**
 * @author juanin
 *
 */
abstract public class WLObjectDef
{
    String name;

    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name=name;
    }

    abstract public String getJoinBind(DbCnt cnt, String worklistbind) throws ISPACException;
    abstract public void addJoin(DbCnt cnt, TableJoinFactoryDAO joinfactory) throws ISPACException;
}
