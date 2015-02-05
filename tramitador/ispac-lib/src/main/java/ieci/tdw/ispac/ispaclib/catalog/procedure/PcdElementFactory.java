package ieci.tdw.ispac.ispaclib.catalog.procedure;

import ieci.tdw.ispac.api.ISPACEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.graph.GInfo;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IProcedure;
import ieci.tdw.ispac.ispaclib.catalog.procedure.element.FlowElement;
import ieci.tdw.ispac.ispaclib.catalog.procedure.element.ProcedureElement;
import ieci.tdw.ispac.ispaclib.catalog.procedure.element.StageElement;
import ieci.tdw.ispac.ispaclib.catalog.procedure.element.SyncNodeElement;
import ieci.tdw.ispac.ispaclib.catalog.procedure.element.TaskElement;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.CTProcedureDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.CTStageDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.CTTaskDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PEntidadDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PFaseDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PFlujoDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PNodoDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PProcedimientoDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PSincNodoDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PTramiteDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * PcdElementFactory
 *
 */
public class PcdElementFactory
{
	public ProcedureElement createProcedure(ClientContext ctx, DbCnt cnt,
			int parentPcdId, String name, int type) throws ISPACException {

	    PProcedimientoDAO pprocdao=new PProcedimientoDAO(cnt);
	    pprocdao.createNew(cnt);

	    pprocdao.set("NVERSION",1);
	    pprocdao.set("NOMBRE",name);
	    pprocdao.set("TIPO",type);
	    pprocdao.set("ESTADO",IProcedure.PCD_STATE_DRAFT);
	    pprocdao.set("ID_RESP",(String)null);
	    pprocdao.set("ID_RESP_SEC",(String)null);

	    pprocdao.set("ID_GROUP",pprocdao.getKeyInt());

	    pprocdao.set("ID_CRT",ctx.getRespId());
	    pprocdao.set("NOMBRE_CRT", (ctx.getUser() != null ? ctx.getUser().getName() : null));
	    pprocdao.set("TS_CRT",new Date());

	    pprocdao.set("ID_UPD",ctx.getRespId());
	    pprocdao.set("NOMBRE_UPD", (ctx.getUser() != null ? ctx.getUser().getName() : null));
	    pprocdao.set("TS_UPD",new Date());

	    CTProcedureDAO ctprocdao = null;
	    if (type == IPcdElement.TYPE_PROCEDURE) {
		    ctprocdao = new CTProcedureDAO(cnt);
		    ctprocdao.createNew(cnt);
		    ctprocdao.set("ID",pprocdao.getKeyInt());
		    ctprocdao.set("COD_PCD","PCD-"+pprocdao.getKeyInt());
		    ctprocdao.set("NOMBRE",name);
		    ctprocdao.set("TITULO",name);
		    ctprocdao.set("ASUNTO",name);
		    ctprocdao.set("ID_PADRE",parentPcdId);
	    }

	    List entitylist = createDefaultEntities(cnt, pprocdao.getKeyInt());

	    return new ProcedureElement(ctx,pprocdao,ctprocdao,entitylist);
	}

	public ProcedureElement importProcedure(ClientContext ctx, DbCnt cnt,
			int parentPcdId, String name, int type) throws ISPACException {

	    PProcedimientoDAO pprocdao=new PProcedimientoDAO(cnt);
	    pprocdao.createNew(cnt);

	    pprocdao.set("NVERSION",1);
	    pprocdao.set("NOMBRE",name);
	    pprocdao.set("TIPO",type);
	    pprocdao.set("ID_RESP",(String)null);
	    pprocdao.set("ID_RESP_SEC",(String)null);
	    pprocdao.set("ID_GROUP",pprocdao.getKeyInt());
	    pprocdao.set("ID_CRT",ctx.getRespId());
	    pprocdao.set("NOMBRE_CRT", (ctx.getUser() != null ? ctx.getUser().getName() : null));
	    pprocdao.set("TS_CRT",new Date());
	    pprocdao.set("ID_UPD",ctx.getRespId());
	    pprocdao.set("NOMBRE_UPD", (ctx.getUser() != null ? ctx.getUser().getName() : null));
	    pprocdao.set("TS_UPD",new Date());

	    CTProcedureDAO ctprocdao = null;

	    if (type == IPcdElement.TYPE_PROCEDURE) {

	    	// Procedimiento en borrador
	    	pprocdao.set("ESTADO",IProcedure.PCD_STATE_DRAFT);

		    ctprocdao = new CTProcedureDAO(cnt);
		    ctprocdao.createNew(cnt);

		    ctprocdao.set("ID",pprocdao.getKeyInt());
		    ctprocdao.set("COD_PCD","PCD-"+pprocdao.getKeyInt());
		    ctprocdao.set("NOMBRE",name);
		    ctprocdao.set("ID_PADRE",parentPcdId);
	    }
	    else {
	    	// Subproceso en vigente
	    	pprocdao.set("ESTADO",IProcedure.PCD_STATE_CURRENT);
	    }

	    return new ProcedureElement(ctx,pprocdao,ctprocdao);
	}

	public ProcedureElement importProcedureAsVersion(ClientContext ctx, DbCnt cnt, PProcedimientoDAO parentPcd) throws ISPACException {

	    PProcedimientoDAO pprocdao=new PProcedimientoDAO(cnt);
	    pprocdao.createNew(cnt);

	    // Incrementar la versión
	    pprocdao.set("NVERSION",parentPcd.getInt("NVERSION") + 1);
	    // Mismo nombre que el procedimiento padre
	    pprocdao.set("NOMBRE",parentPcd.get("NOMBRE"));
	    pprocdao.set("TIPO",IPcdElement.TYPE_PROCEDURE);
	    pprocdao.set("ID_RESP",(String)null);
	    pprocdao.set("ID_RESP_SEC",(String)null);
   	    // Mismo grupo que el procedimiento padre
	    pprocdao.set("ID_GROUP",parentPcd.get("ID_GROUP"));
	    pprocdao.set("ID_CRT",ctx.getRespId());
	    pprocdao.set("NOMBRE_CRT", (ctx.getUser() != null ? ctx.getUser().getName() : null));
	    pprocdao.set("TS_CRT",new Date());
	    pprocdao.set("ID_UPD",ctx.getRespId());
	    pprocdao.set("NOMBRE_UPD", (ctx.getUser() != null ? ctx.getUser().getName() : null));
	    pprocdao.set("TS_UPD",new Date());

	    // Procedimiento en borrador
    	pprocdao.set("ESTADO",IProcedure.PCD_STATE_DRAFT);

    	CTProcedureDAO ctparentPcd = new CTProcedureDAO(cnt, parentPcd.getKeyInt());

    	CTProcedureDAO ctprocdao = new CTProcedureDAO(cnt);
	    ctprocdao.createNew(cnt);

	    ctprocdao.set("ID",pprocdao.getKeyInt());
	    // Misma información de catálogo
	    ctprocdao.set("COD_PCD",ctparentPcd.get("COD_PCD"));
	    ctprocdao.set("NOMBRE",ctparentPcd.get("NOMBRE"));
	    ctprocdao.set("ID_PADRE",ctparentPcd.get("ID_PADRE"));

	    return new ProcedureElement(ctx,pprocdao,ctprocdao);
	}

	private List createDefaultEntities(DbCnt cnt, int pcdId)
			throws ISPACException {

	    List entitylist=new ArrayList();

	    // EXPEDIENTES
	    PEntidadDAO pentity=new PEntidadDAO(cnt);
	    pentity.createNew(cnt);
	    pentity.set("ID_ENT",ISPACEntities.DT_ID_EXPEDIENTES);
	    pentity.set("ID_PCD",pcdId);
        pentity.set("ORDEN",pentity.getKeyInt());

	    entitylist.add(pentity);

	    // TRAMITES
	    pentity=new PEntidadDAO(cnt);
	    pentity.createNew(cnt);
	    pentity.set("ID_ENT",ISPACEntities.DT_ID_TRAMITES);
	    pentity.set("ID_PCD",pcdId);
        pentity.set("ORDEN",pentity.getKeyInt());

	    entitylist.add(pentity);

	    // DOCUMENTOS
	    pentity=new PEntidadDAO(cnt);
	    pentity.createNew(cnt);
	    pentity.set("ID_ENT",ISPACEntities.DT_ID_DOCUMENTOS);
	    pentity.set("ID_PCD",pcdId);
        pentity.set("ORDEN",pentity.getKeyInt());

	    entitylist.add(pentity);

	    // INTERVINIENTES
	    pentity=new PEntidadDAO(cnt);
	    pentity.createNew(cnt);
	    pentity.set("ID_ENT",ISPACEntities.DT_ID_INTERVINIENTES);
	    pentity.set("ID_PCD",pcdId);
        pentity.set("ORDEN",pentity.getKeyInt());

	    entitylist.add(pentity);

	    
	    // REGISTROS_ES
	    pentity=new PEntidadDAO(cnt);
	    pentity.createNew(cnt);
	    pentity.set("ID_ENT",ISPACEntities.DT_ID_REGISTROS_ES);
	    pentity.set("ID_PCD",pcdId);
        pentity.set("ORDEN",pentity.getKeyInt());

	    entitylist.add(pentity);	    
	    
	    
	    return entitylist;
	}

	public Map createStages(DbCnt cnt, List ctstages) throws ISPACException {

	    LinkedHashMap pstagemap=new LinkedHashMap();

	    for (Iterator iter = ctstages.iterator(); iter.hasNext();) {
			IItem ctstage = (IItem) iter.next();

			StageElement pcdelement=createStage(cnt, 0, ctstage.getInt("ID"),
					ctstage.getString("NOMBRE"), new GInfo());

			pstagemap.put(pcdelement.getIntegerId(),pcdelement);
		}
	    return pstagemap;
	}

	public StageElement createStage(DbCnt cnt, int ctstageid, GInfo ginfo)
			throws ISPACException {
	    CTStageDAO ctstagedao=new CTStageDAO(cnt,ctstageid);
        return createStage(cnt, 0, ctstagedao.getKeyInt(),
        		ctstagedao.getString("NOMBRE"), ginfo);
	}

	public StageElement createStage(DbCnt cnt, int pcdId, int ctstageId,
			String stageName, GInfo ginfo) throws ISPACException {

	    PFaseDAO pstagedao=new PFaseDAO(cnt);

	    //IMPORTANTE - El nodo debe tener el mismo identificador que la fase
	    //o nodo de sincronización correspondiente.
	    pstagedao.createNew(cnt);
	    pstagedao.set("ID_PCD",pcdId);
	    pstagedao.set("NOMBRE",stageName);
	    pstagedao.set("ID_RESP",(String)null);
	    pstagedao.set("ID_RESP_SEC",(String)null);
	    pstagedao.set("ID_CTFASE",ctstageId);

	    PNodoDAO pnodedao=new PNodoDAO(cnt);
	    pnodedao.createNew(cnt);
	    pnodedao.set("ID",pstagedao.getKeyInt());
	    pnodedao.set("ID_PCD",pcdId);
	    pnodedao.set("TIPO",PNodoDAO.NODE_OBJ_STAGE);
	    pnodedao.set("G_INFO", (ginfo != null ? ginfo.toXml() : (String) null));

	    return new StageElement(pstagedao,pnodedao);
	}

	public Map createActivities(DbCnt cnt, List activitiesNames)
			throws ISPACException {

	    LinkedHashMap pactivitiesmap = new LinkedHashMap();

	    if (activitiesNames != null) {
		    for (int i = 0; i < activitiesNames.size(); i++) {
				String activityName = (String) activitiesNames.get(i);

				StageElement stage = createStage(cnt, 0, -1, activityName, new GInfo());

				pactivitiesmap.put(stage.getIntegerId(), stage);
			}
	    }

	    return pactivitiesmap;
	}

	public SyncNodeElement createSyncNode(DbCnt cnt, int pcdId, int type, GInfo ginfo)
			throws ISPACException {

	    PSincNodoDAO psincnododao = new PSincNodoDAO(cnt);

	    //IMPORTANTE - El nodo debe tener el mismo identificador que la fase
	    //o nodo de sincronización correspondiente.
	    psincnododao.createNew(cnt);
	    psincnododao.set("ID_PCD", pcdId);
	    psincnododao.set("TIPO", type);

	    PNodoDAO pnodedao = new PNodoDAO(cnt);
	    pnodedao.createNew(cnt);
	    pnodedao.set("ID", psincnododao.getKeyInt());
	    pnodedao.set("ID_PCD", pcdId);
	    pnodedao.set("TIPO", PNodoDAO.NODE_OBJ_SYNCNODE);
	    pnodedao.set("G_INFO", (ginfo != null ? ginfo.toXml() : (String) null));

	    return new SyncNodeElement(psincnododao, pnodedao);
	}

	public Map createTasks(DbCnt cnt,StageElement stageelement,Map cttaskidsmap) throws ISPACException
	{
        Integer ctstageid=stageelement.getCatalogIntegerId();
        List cttaskidslist=(List)cttaskidsmap.get(ctstageid);
        if (cttaskidslist!=null&&!cttaskidslist.isEmpty())
        {
           return createTasks(cnt,cttaskidslist);
        }

        return null;
	}

	public Map createTasks(DbCnt cnt,List cttaskidslist) throws ISPACException
	{
	    String ids = cttaskidslist.toString();
	    ids = ids.substring(1, ids.length() - 1);

	    LinkedHashMap pstaskmap=new LinkedHashMap();

	    CollectionDAO coldao=new CollectionDAO(CTTaskDAO.class);
	    coldao.query(cnt,"WHERE ID IN ( "+ids+")");
	    while(coldao.next())
	    {
	        IPcdElement ptask=createTask(cnt,coldao.value());
	        pstaskmap.put(ptask.getCatalogIntegerId(),ptask);
	    }

	    return pstaskmap;
	}

	public TaskElement createTask(DbCnt cnt,int cttaskid)
	throws ISPACException
	{
	    CTTaskDAO cttaskdao=new CTTaskDAO(cnt,cttaskid);
	    return createTask(cnt,cttaskdao);
	}

	public TaskElement createTask(DbCnt cnt,ObjectDAO cttaskdao)
	throws ISPACException
	{
	    PTramiteDAO ptaskdao=new PTramiteDAO(cnt);

	    ptaskdao.createNew(cnt);
	    ptaskdao.set("ID_PCD",0);
	    ptaskdao.set("ID_FASE",0);
	    ptaskdao.set("LIBRE",0);
	    ptaskdao.set("NOMBRE",cttaskdao.getString("NOMBRE"));
	    ptaskdao.set("ORDEN", ptaskdao.getKeyInt());
	    ptaskdao.set("ID_RESP",(String)null);
	    ptaskdao.set("ID_RESP_SEC",(String)null);
	    ptaskdao.set("ID_CTTRAMITE",cttaskdao.getKeyInt());
	    ptaskdao.set("ID_PCD_SUB", cttaskdao.getString("ID_SUBPROCESO"));

	    return new TaskElement(ptaskdao);
	}

	public FlowElement createFlow(DbCnt cnt)
	throws ISPACException
	{
	    PFlujoDAO pflowdao=new PFlujoDAO(cnt);

	    pflowdao.createNew(cnt);
	    pflowdao.set("ID_PCD",0);
	    pflowdao.set("ID_ORIGEN",0);
	    pflowdao.set("ID_DESTINO",0);

	    return new FlowElement(pflowdao);
	}

	public FlowElement createFlow(DbCnt cnt,IPcdElement nodeorig,IPcdElement nodedest)
	throws ISPACException
	{
	    if (nodeorig==null || nodedest ==null)
	        return null;

	    PFlujoDAO pflowdao=new PFlujoDAO(cnt);

	    pflowdao.createNew(cnt);
	    pflowdao.set("ID_PCD",0);
	    pflowdao.set("ID_ORIGEN",nodeorig.getId());
	    pflowdao.set("ID_DESTINO",nodedest.getId());

	    return new FlowElement(pflowdao);
	}
}
