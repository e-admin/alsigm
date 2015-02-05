/*
 * invesflow Java - ISPAC
 * $Source: /TEST/SIGEM/ispaclib/src/ieci/tdw/ispac/ispaclib/catalog/procedure/PcdStageTaskDef.java,v $
 * $Revision: 1.1 $
 * $Date: 2007/04/24 11:16:59 $
 * $Author: luismimg $
 *
 */
package ieci.tdw.ispac.ispaclib.catalog.procedure;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * PcdStageTaskDef
 *
 *
 * @version $Revision: 1.1 $ $Date: 2007/04/24 11:16:59 $
 */
public class PcdStageTaskDef
{
    List ctstagesid=null;
    Map ctstagestask=null;

    public PcdStageTaskDef()
    {
        ctstagesid=new LinkedList();
        ctstagestask=new LinkedHashMap();
    }

    /**
     * @return Devuelve el valor de la propiedad ctstagesid.
     */
    public List getCTStagesId()
    {
        return ctstagesid;
    }
    /**
     * @param ctstagesid Cambia el valor de la propiedad ctstagesid.
     */
    public void setCTStagesId(List ctstagesid)
    {
        this.ctstagesid = ctstagesid;
    }
    /**
     * @return Devuelve el valor de la propiedad ctstagestask.
     */
    public Map getCTStagesTask()
    {
        return ctstagestask;
    }
    /**
     * @param ctstagestask Cambia el valor de la propiedad ctstagestask.
     */
    public void setCTStagesTask(Map ctstagestask)
    {
        this.ctstagestask = ctstagestask;
    }

    public void putCTStagesTaskList(Integer ctstageid,List cttaskidlist)
    {
        ctstagestask.put(ctstageid,cttaskidlist);
    }
}
