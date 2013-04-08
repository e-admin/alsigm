package ieci.tdw.ispac.ispaclib.catalog.procedure.element;

import ieci.tdw.ispac.api.ISPACEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.rule.EventsDefines;
import ieci.tdw.ispac.ispaclib.bpm.Process;
import ieci.tdw.ispac.ispaclib.bpm.SubProcess;
import ieci.tdw.ispac.ispaclib.catalog.procedure.ExportProcedureMgr;
import ieci.tdw.ispac.ispaclib.catalog.procedure.IPcdElement;
import ieci.tdw.ispac.ispaclib.catalog.procedure.PcdElementBuilder;
import ieci.tdw.ispac.ispaclib.catalog.procedure.ProcedureUtil;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PDepTramiteDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PEventoDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PRelPlazoDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PTramiteDAO;
import ieci.tdw.ispac.ispaclib.dao.publisher.PubReglaDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.XmlTag;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;

/**
 * TaskElement
 *
 *
 * @version $Revision: 1.15 $ $Date: 2009/03/17 12:11:50 $
 */
public class TaskElement implements IPcdElement
{

    private PTramiteDAO taskdao;
    private List taskapplist;
    private List eventlist;
    private List deadlinelist;
    private List pubrulelist;
    private List dependencylist;

    public TaskElement(PTramiteDAO taskdao)
    throws ISPACException
    {
        this.taskdao = taskdao;
        this.taskapplist = null;
        this.eventlist = null;
        this.deadlinelist = null;
        this.pubrulelist = null;
        this.dependencylist = null;
    }

    private TaskElement(PTramiteDAO taskdao, List taskapplist, List eventlist, List deadlinelist, List pubrulelist, List dependencylist)
    throws ISPACException
    {
        this.taskdao = taskdao;
        this.taskapplist = taskapplist;
        this.eventlist = eventlist;
        this.deadlinelist = deadlinelist;
        this.pubrulelist = pubrulelist;
        this.dependencylist = dependencylist;
    }

    public void load(DbCnt cnt) throws ISPACException
    {
        taskapplist=taskdao.getTaskApps(cnt).toList();
        eventlist=PEventoDAO.getEvents(cnt,EventsDefines.EVENT_OBJ_TASK,getId()).toList();

        PcdElementBuilder objdeffactory = new PcdElementBuilder();
        CollectionDAO coldao = PRelPlazoDAO.getRelDeadline(cnt,PRelPlazoDAO.DEADLINE_OBJ_TASK,getId());
        deadlinelist = objdeffactory.buildDeadlineElement(cnt,coldao);
        pubrulelist = PubReglaDAO.getRulesByTask(cnt, getId()).toList();
        dependencylist = PDepTramiteDAO.getDependenciesByTask(cnt, getId()).toList();
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.NodeDef#getId()
     */
    public int getId()
    throws ISPACException
    {
        if (taskdao==null)
            throw new ISPACException("TaskElement - La estructura del trámite no está debidamente construida");

        return taskdao.getKeyInt();
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.NodeDef#getIntegerId()
     */
    public Integer getIntegerId()
    throws ISPACException
    {
        if (taskdao==null)
            throw new ISPACException("TaskElement - La estructura del trámite no está debidamente construida");

        return taskdao.getKeyInteger();
    }


    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.IPcdElement#getCatalogId()
     */
    public int getCatalogId() throws ISPACException
    {
        if (taskdao==null)
            throw new ISPACException("TaskElement - La estructura del trámite no está debidamente construida");

        return taskdao.getInt("ID_CTTRAMITE");
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.IPcdElement#getCatalogIntegerId()
     */
    public Integer getCatalogIntegerId() throws ISPACException
    {
        if (taskdao==null)
            throw new ISPACException("TaskElement - La estructura del trámite no está debidamente construida");
        return new Integer(taskdao.getInt("ID_CTTRAMITE"));
    }

    public String getName() throws ISPACException {
        if (taskdao==null) {
            throw new ISPACException("TaskElement - La estructura del trámite no está debidamente construida");
        }

        return taskdao.getString("NOMBRE");
    }

    public String getResponsibleId() throws ISPACException {
        if (taskdao==null) {
            throw new ISPACException("TaskElement - La estructura del trámite no está debidamente construida");
        }

        return taskdao.getString("ID_RESP");
    }

    public String getSubPcdId()
    throws ISPACException
    {
        if (taskdao==null)
            throw new ISPACException("TaskElement - La estructura del trámite no está debidamente construida");

        return taskdao.getString("ID_PCD_SUB");
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.NodeDef#duplicate(ieci.tdw.ispac.ispaclib.db.DbCnt)
     */
    public IPcdElement duplicate(DbCnt cnt) throws ISPACException
    {
        PTramiteDAO newtaskdao=(PTramiteDAO)taskdao.duplicate(cnt);
        newtaskdao.set("ORDEN", newtaskdao.getKeyInteger());

        List newtaskapplist=ProcedureUtil.duplicate(cnt,taskapplist);
        List neweventlist=ProcedureUtil.duplicate(cnt,eventlist);
        List newdeadlinelist=ProcedureUtil.duplicatePcdElement(cnt,deadlinelist);

        ProcedureUtil.setProperty(newtaskapplist,"ID_TRAMITE",newtaskdao.getKey());
        ProcedureUtil.setProperty(neweventlist,"ID_OBJ",newtaskdao.getKey());

        ProcedureUtil.setPcdElementProperty(newdeadlinelist,null,null,newtaskdao.getKeyInteger());

        // Reglas de publicación
        List newpubrulelist = ProcedureUtil.duplicate(cnt, pubrulelist);
        ProcedureUtil.setProperty(newpubrulelist, "ID_TRAMITE", newtaskdao.getKey());

        // Lista de dependencias entre trámites
        List newdependencylist=ProcedureUtil.duplicate(cnt,dependencylist);
        ProcedureUtil.setProperty(newdependencylist, "ID_TRAMITE_HIJO", newtaskdao.getKey());
        
        return new TaskElement(newtaskdao, newtaskapplist, neweventlist, newdeadlinelist, newpubrulelist, newdependencylist);
    }


    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.NodeDef#store(ieci.tdw.ispac.ispaclib.db.DbCnt)
     */
    public void store(DbCnt cnt) throws ISPACException
    {
        taskdao.store(cnt);
        ProcedureUtil.store(cnt,taskapplist);
        ProcedureUtil.store(cnt,eventlist);
        ProcedureUtil.storePcdElement(cnt,deadlinelist);

        // Duplicar las reglas de publicación
        // En el duplicate el nuevo PTramiteDAO aún no tiene establecidos
        // los identificadores de la nueva fase y del nuevo procedimiento también duplicados
        ProcedureUtil.setProperty(pubrulelist, "ID_FASE", taskdao.getInt("ID_FASE"));
        ProcedureUtil.setProperty(pubrulelist, "ID_PCD", taskdao.getInt("ID_PCD"));
        ProcedureUtil.store(cnt, pubrulelist);
        
        // Almacenar las dependencias del trámite 
        ProcedureUtil.store(cnt, dependencylist);
    }


    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.IPcdElement#delete(ieci.tdw.ispac.ispaclib.db.DbCnt)
     */
    public void delete(DbCnt cnt) throws ISPACException
    {
        taskdao.delete(cnt);
        ProcedureUtil.delete(cnt,taskapplist);
        ProcedureUtil.delete(cnt,eventlist);
        ProcedureUtil.deletePcdElement(cnt,deadlinelist);
        ProcedureUtil.delete(cnt, pubrulelist);
        ProcedureUtil.delete(cnt, dependencylist);
    }


    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.IObjDef#setProcedureId(ieci.tdw.ispac.ispaclib.db.DbCnt, int)
     */
    public void setProcedureId(int procedureId) throws ISPACException
    {
        if (taskdao!=null)
            taskdao.set("ID_PCD",procedureId);
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.IObjDef#setStagePcdId(ieci.tdw.ispac.ispaclib.db.DbCnt, int)
     */
    public void setStagePcdId(int stagePcdId) throws ISPACException
    {
        if (taskdao!=null)
            taskdao.set("ID_FASE",stagePcdId);
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.IObjDef#setTaskPcdId(ieci.tdw.ispac.ispaclib.db.DbCnt, int)
     */
    public void setTaskPcdId(int taskPcdId) throws ISPACException
    {
        this.taskapplist=null;
        this.eventlist=null;
        this.deadlinelist=null;
        this.pubrulelist=null;
    }

    public void setObligatory() throws ISPACException
    {
        if (taskdao!=null)
            taskdao.set("OBLIGATORIO",ISPACEntities.PCD_TASK_OBLIGATORY);
    }

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.IPcdElement#getOrigId()
     */
	public Integer getOrigId() throws ISPACException {
		return null;
	}

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.IPcdElement#getDestId()
     */
	public Integer getDestId() throws ISPACException {
		return null;
	}

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.IPcdElement#setOrigId(java.lang.Integer)
     */
	public void setOrigId(Integer elementId) throws ISPACException {
	}

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.IPcdElement#setDestId(java.lang.Integer)
     */
	public void setDestId(Integer elementId) throws ISPACException {
	}

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.IPcdElement#getOrigElement()
     */
	public List getOrigElement() throws ISPACException {
		return null;
	}

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.IPcdElement#getDestElement()
     */
	public List getDestElement() throws ISPACException {
		return null;
	}

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.IPcdElement#addOrigElement(ieci.tdw.ispac.ispaclib.catalog.procedure.IPcdElement)
     */
	public void addOrigElement(IPcdElement element) throws ISPACException {
	}

    /* (non-Javadoc)
     * @see ieci.tdw.ispac.ispaclib.catalog.procedure.IPcdElement#addDestElement(ieci.tdw.ispac.ispaclib.catalog.procedure.IPcdElement)
     */
	public void addDestElement(IPcdElement element) throws ISPACException {
	}

	public String getBPMTaskId() throws ISPACException {
        if (taskdao==null) {
            throw new ISPACException("TaskElement - La estructura del trámite no está debidamente construida");
        }
        return taskdao.getString("ID_TRAMITE_BPM");
	}

	public void setBPMTaskId(String bpmPcdId) throws ISPACException {
        if (taskdao==null) {
            throw new ISPACException("TaskElement - La estructura del trámite no está debidamente construida");
        }
        taskdao.set("ID_TRAMITE_BPM", bpmPcdId);
	}

    public String toXml() throws ISPACException {

        StringBuffer buffer = new StringBuffer();

        buffer.append(XmlTag.newTag("name", taskdao.getString("NOMBRE")));
        buffer.append(XmlTag.newTag("idcttask", taskdao.getString("ID_CTTRAMITE")));
        buffer.append(XmlTag.newTag("idresp", taskdao.getString("ID_RESP")));
        buffer.append(XmlTag.newTag("idpcdsub", taskdao.getString("ID_PCD_SUB")));

        return XmlTag.newTag("task", buffer.toString(),
                XmlTag.newAttr("id", Integer.toString(getId())));
    }

    /**
     * Compone el modelo de procesos para el BPM.
     * @return Modelo de procesos.
     * @throws ISPACException si ocurre algún error.
     */
    public Process getProcessModel() throws ISPACException {

    	SubProcess model = new SubProcess();

    	model.setId(getBPMTaskId());
    	model.setSpacProcessId(getId());
    	model.setName(getName());
    	model.setRespId(getResponsibleId());
    	model.setSubProcessId(this.getSubPcdId());
    	return model;
    }

    public String toXpdl(DbCnt cnt,
    					 Map ctStageIds,
 			 			 Map ctTaskIds,
 			 			 Map ctRuleIds,
 			 			 Map ctEntityIds,
 			 			 Map ctTpDocIds,
 			 			 Map subPcdIds) throws ISPACException {

    	// Trámite en el catálogo
        Integer idCatalog = new Integer(getCatalogId());
        if (!ctTaskIds.containsKey(idCatalog)) {
        	ctTaskIds.put(idCatalog, null);
        }

        // Subproceso asociado al trámite
        String subPcdId = getSubPcdId();
        if (StringUtils.isNotEmpty(subPcdId)) {
            Integer idSubPcd = new Integer(subPcdId);
            if (!subPcdIds.containsKey(idSubPcd)) {
            	subPcdIds.put(idSubPcd, null);
            }
        }

        String sXpdl = null;
        StringBuffer buffer =  new StringBuffer();

        String attributes = XmlTag.newAttr(ExportProcedureMgr.ATR_ID, String.valueOf(getId())) +
        					XmlTag.newAttr(ExportProcedureMgr.ATR_NAME, ExportProcedureMgr.exportName(getName())) +
        					XmlTag.newAttr(ExportProcedureMgr.ATR_ID_CATALOGO, taskdao.getString("ID_CTTRAMITE"));

        if (taskdao.getInt("OBLIGATORIO") == ISPACEntities.PCD_TASK_OBLIGATORY) {
        	attributes += XmlTag.newAttr(ExportProcedureMgr.ATR_OBLIGATORY, ExportProcedureMgr.VAL_YES);
        }

        // Eventos
        buffer.append(ExportProcedureMgr.eventsToXml(eventlist, ctRuleIds));

        // Formularios asociados a entidades
        buffer.append(ExportProcedureMgr.formsToXml(taskapplist, ctEntityIds, ctRuleIds));

        // Dependencias de otros trámites
        buffer.append(ExportProcedureMgr.dependenciesToXml(dependencylist));
        
        sXpdl = XmlTag.newTag(ExportProcedureMgr.TAG_TASK, buffer.toString(), attributes.toString());
        return sXpdl;
    }

	public String toXpdl(DbCnt cnt, Map ctStageIds, Map ctTaskIds,
			Map ctRuleIds, Map ctEntityIds, Map ctTpDocIds, Map subPcdIds,
			Map ctHelpsIds) throws ISPACException {
		return null;
	}

	public void deleteChildDependencies(DbCnt cnt, int ptaskId) throws ISPACException {
		if (!CollectionUtils.isEmpty(dependencylist)) {
			Iterator dependenciesIterator = dependencylist.iterator();
			while (dependenciesIterator.hasNext()) {
				PDepTramiteDAO dependency = (PDepTramiteDAO) dependenciesIterator.next();
				if (dependency.getInt(PDepTramiteDAO.ID_TRAMITE_PADRE) == ptaskId) {
					dependency.delete(cnt);
				}
			}
		}
	}

	public void updateDependencies(DbCnt cnt, Map<Integer, Integer> idsMap) throws ISPACException {
		if (!CollectionUtils.isEmpty(dependencylist)) {
			Iterator dependenciesIterator = dependencylist.iterator();
			while (dependenciesIterator.hasNext()) {
				PDepTramiteDAO dependency = (PDepTramiteDAO) dependenciesIterator.next();
				int parentTaskId = dependency.getInt(PDepTramiteDAO.ID_TRAMITE_PADRE);
				Integer newParentTaskId = idsMap.get(Integer.valueOf(parentTaskId));
				if (newParentTaskId == null) {
					
					// Eliminar la dependencia debido a que el trámite del que
					// depende no existe en la fase del procedimiento
					dependenciesIterator.remove();
				} else {
					dependency.set(PDepTramiteDAO.ID_TRAMITE_PADRE, newParentTaskId);
				}
			}
		}
	}
}