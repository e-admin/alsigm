/*
 * invesflow Java - ISPAC
 * $Source: /TEST/SIGEM/ispaclib/src/ieci/tdw/ispac/ispaclib/catalog/procedure/PcdElementBuilder.java,v $
 * $Revision: 1.2 $
 * $Date: 2008/09/23 09:06:20 $
 * $Author: davidfa $
 *
 */
package ieci.tdw.ispac.ispaclib.catalog.procedure;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.catalog.procedure.element.DeadlineElement;
import ieci.tdw.ispac.ispaclib.catalog.procedure.element.FlowElement;
import ieci.tdw.ispac.ispaclib.catalog.procedure.element.ProcedureElement;
import ieci.tdw.ispac.ispaclib.catalog.procedure.element.StageElement;
import ieci.tdw.ispac.ispaclib.catalog.procedure.element.SyncNodeElement;
import ieci.tdw.ispac.ispaclib.catalog.procedure.element.TaskElement;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PFaseDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PFlujoDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PNodoDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PRelPlazoDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PSincNodoDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PTramiteDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * PcdElementBuilder
 *
 *
 * @version $Revision: 1.2 $ $Date: 2008/09/23 09:06:20 $
 */
public class PcdElementBuilder
{


    public ProcedureElement buildProcedureElement(ClientContext ctx,DbCnt cnt,int procedureId)
    throws ISPACException
    {
        ProcedureElement procedure=new ProcedureElement(ctx,cnt,procedureId);
        procedure.load(cnt);
        return procedure;
    }


    public Map buildFlowElement(DbCnt cnt,CollectionDAO flows)
    throws ISPACException
    {
        LinkedHashMap map=new LinkedHashMap();

		while(flows.next())
		{
		    FlowElement flowdef=new FlowElement((PFlujoDAO)flows.value());
		    flowdef.load(cnt);
		    map.put(flowdef.getIntegerId(),flowdef);
		}
		return map;
    }

    public Map buildSyncNodeElement(DbCnt cnt,CollectionDAO syncnodes,Map nodemap)
    throws ISPACException
    {
        LinkedHashMap map=new LinkedHashMap();

		while(syncnodes.next())
		{
		    PSincNodoDAO syncnodedao=(PSincNodoDAO)syncnodes.value();
		    PNodoDAO nodedao=(PNodoDAO)nodemap.get(syncnodedao.getKey());
		    if (nodedao==null)
		        throw new ISPACException("El nodo de sincronización debe tener definido un nodo.");
		    SyncNodeElement nodedef=new SyncNodeElement(syncnodedao,nodedao);
			nodedef.load(cnt);
			map.put(nodedef.getIntegerId(),nodedef);
		}
		return map;
    }

    public Map buildStageElement(DbCnt cnt,CollectionDAO stages,Map nodemap)
    throws ISPACException
    {
        LinkedHashMap map=new LinkedHashMap();
		while(stages.next())
		{
		    PFaseDAO stagedao=(PFaseDAO)stages.value();
		    PNodoDAO nodedao=(PNodoDAO)nodemap.get(stagedao.getKey());
		    if (nodedao==null)
		        throw new ISPACException("La fase debe tener definido un nodo.");
		    StageElement nodedef=new StageElement(stagedao,nodedao);
			nodedef.load(cnt);
			map.put(nodedef.getIntegerId(),nodedef);
		}
		return map;
    }

    public Map buildTaskElement(DbCnt cnt,CollectionDAO tasks)
    throws ISPACException
    {
		LinkedHashMap map=new LinkedHashMap();

		while(tasks.next())
		{
		    TaskElement taskdef=new TaskElement((PTramiteDAO)tasks.value());
		    taskdef.load(cnt);
			map.put(taskdef.getIntegerId(),taskdef);
		}
		return map;
    }

    public List buildDeadlineElement(DbCnt cnt,CollectionDAO reldeadlines)
    throws ISPACException
    {
		List list=new ArrayList();

		while(reldeadlines.next())
		{
		    DeadlineElement deadlinedef=new DeadlineElement((PRelPlazoDAO)reldeadlines.value());
		    deadlinedef.load(cnt);
			list.add(deadlinedef);
		}
		return list;
    }
}
