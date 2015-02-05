package ieci.tdw.ispac.ispacweb.api.impl;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IRespManagerAPI;
import ieci.tdw.ispac.api.ISPACEntities;
import ieci.tdw.ispac.api.IWorklistAPI;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.errors.ISPACNullObject;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.IProcedure;
import ieci.tdw.ispac.api.item.IResponsible;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.api.item.util.ListCollection;
import ieci.tdw.ispac.ispaclib.app.EntityApp;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.context.StateContext;
import ieci.tdw.ispac.ispaclib.dao.entity.IEntityDef;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacweb.api.IScheme;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.impl.states.TaskState;
import ieci.tdw.ispac.ispacweb.header.ContextHeader;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.struts.util.MessageResources;

/**
 *
 * Filtros estándar aplicados a la visibilidad de los registros de la entidad de documentos según el estado
 * de tramitación
 *
 * Tramitando una fase:
 *
            sQuery = "(ESTADO = 'FINALIZADO' OR (ESTADO = 'BORRADOR' AND AUTOR = '"
                   + DBUtil.replaceQuotes(mcontext.getUser().getUID())
                   + "')) AND ID_FASE = "
                   + Integer.toString(stageId)
                   + " AND ID_TRAMITE = 0";

 *
 * Tramitando un proceso
 *
            sQuery = "ESTADO = 'FINALIZADO'";
            return EntityFactoryDAO.getInstance().getSchemeDocumentEntity(cnt,entityId,expedient,sQuery).disconnect();

 *
 * Tramitando una tarea
 *
            sQuery = "( ESTADO = 'FINALIZADO' OR (ESTADO = 'BORRADOR' AND AUTOR = '"
                   + DBUtil.replaceQuotes(mcontext.getUser().getUID())
                   + "' )) AND ID_TRAMITE = "
                   + Integer.toString(taskId);

 *
 */
public class Scheme implements IScheme
{
    ClientContext mcct;

    IItemCollection mcatalog;
    Map mscheme;

    public Scheme(ClientContext cct)
    {
        this.mcct = cct;
        mcatalog = null;
        mscheme = null;
    }
    
    /**
     * Devuelve el esquema resumido de entidades correspondientes al estado
     * de tramitaci&oacute;n. Muestra los registros de la entidad de documentos
     * según el comportamiento estandar de ISPAC
     *
     * @param  iState  el estado actual de tramitaci&oacute;n
     * @return  Mapa de ids de entidad (String) a los objetos IItem
     * @throws ISPACException
     */
    public Map getSchemeEntities(IState iState)
            throws ISPACException
    {
        if (mscheme!=null)
            return mscheme;

        mscheme=new LinkedHashMap();

        // Lista de entidades
        IItemCollection catalog=getCatalogEntityScheme(iState);

        while (catalog.next())
        {
            IItem ctentity=catalog.value();
            int entityId=ctentity.getKeyInt();
            IItemCollection itemcol=getEntitySchemeFilter(iState,entityId);
            mscheme.put(String.valueOf(entityId),itemcol);
        }
        return mscheme;
    }

    /**
     * Establece el esquema resumido de entidades correspondientes al estado
     * de tramitaci&oacute;n.
     * 
     * @return  Mapa con el esquema resumido de entidades
     */
    public void setSchemeEntities(Map mscheme) {
    	this.mscheme = mscheme;
    }
    
    /**
     * Devuelve el cat&aacute;logo de entidades correspondiente al estado
     * de tramitaci&oacute;n.
     *
     * @param  iState  el estado actual de tramitaci&oacute;n
     * @return  Mapa de ids de entidad (String) a los objetos IItem
     * @throws ISPACException
     */
    public IItemCollection getCatalogEntityScheme(IState iState) throws ISPACException
    {
        if (mcatalog!=null)
            return mcatalog;

        ArrayList catalog=new ArrayList();

        IInvesflowAPI invesFlowAPI = mcct.getAPI();
        //TODO Revisar este método y hacerlo más eficiente.
        IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();
        IItemCollection collection = null;
        
        // TODO Habilitar cuando se vuelvan a activar las entidades a nivel de subproceso
        /*
        if (iState.getSubPcdId() > 0) {
        	
        	// Lista de entidades de un subproceso
        	collection = entitiesAPI.getProcedureEntities(iState.getSubPcdId(), iState.getActivityPcdId(), 0);
        }
        else {
        */
        
        // La entidad de trámite se establece en última posición
        // ya que nunca se muestra en las pestañas
        IItem ctentTask = null;
        
    	// Lista de entidades de un procedimiento
    	collection = entitiesAPI.getProcedureEntities(iState.getPcdId(), iState.getStagePcdId(), iState.getTaskPcdId());
        
        while (collection.next())
        {
            IItem procent = collection.value();
            int entity = procent.getInt("ID_ENT");
            
            // En el trámite no se muestra la pestaña de documentos
            if (!((iState.getClass() == TaskState.class) &&
            	 (entity == SpacEntities.SPAC_DT_DOCUMENTOS))) {
	            
            	// Información de la entidad
	            IItem ctent = entitiesAPI.getCatalogEntity(entity);
	            
	            if (entity != SpacEntities.SPAC_DT_TRAMITES) {
	            	
	            	// Añadir la entidad de catálogo a la lista de entidades
	            	// correspondientes al estado de tramitación actual
	            	catalog.add(ctent);
	            }
	            else {
	            	// La entidad de trámite se establecerá en última posición
	            	ctentTask = ctent;
	            }
            }
        }
        
        // Establecer en última posición la entidad de trámite
        if (ctentTask != null) {
        	
        	catalog.add(ctentTask);
        }
        
        mcatalog = new ListCollection(catalog);
        return mcatalog;
    }

    /**
     * Devuelve el resumen de los registros correspondientes al estado de tramitaci&oacute;n.
     * Muestra los registros de la entidad según el comportamiento estandar de ISPAC
     *
     * @param  iState  el estado actual de tramitación
     * @param  entityId  identificador de entidad
     * @return  Colecci&oacute;n con los
     * @throws ISPACException
     */
    public IItemCollection getEntitySchemeFilter(IState iState, int entityId) throws ISPACException
    {
        IInvesflowAPI invesFlowAPI = mcct.getAPI();

        // Lista de entidades de una fase de un procedimiento
        IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();

        // Sólo se filtra la entidad de documentos
        if (entityId == ISPACEntities.DT_ID_DOCUMENTOS)
        {
//            //Filtros específicos a aplicar a los documentos++++
//            if (iState.getTaskId()!=0)
//                return entitiesAPI.getSchemeTaskDocuments(iState.getNumexp(),iState.getTaskId());
//            if (iState.getStageId()!=0)
//                return entitiesAPI.getSchemeStageDocuments(iState.getNumexp(),iState.getStageId());
//            if (iState.getProcessId()!=0)
//                return entitiesAPI.getSchemeProcessDocuments(iState.getNumexp());
        	
        	//En el esquema nos interesa tener ahora todos los documentos del expediente, independientemente del estado (IState)
    	    Property[] columns = new Property[1];
    	    columns[0] = new Property(1,"ESTADO",Types.VARCHAR);
    	    
        	return entitiesAPI.getSchemeEntities(entityId, iState.getNumexp(), "", columns);
        }
        
        if (entityId == ISPACEntities.DT_ID_TRAMITES)
        {
            return getSchemeTasks(iState);
        }

        return entitiesAPI.getSchemeEntities(entityId, iState.getNumexp());
    }

    
    /**
     * Devuelve el resumen de los registros correspondientes al estado de tramitaci&oacute;n.
     * Muestra los registros de la entidad según el comportamiento estandar de ISPAC
     *
     * @param  iState  el estado actual de tramitación
     * @param  entityDef definición de la entidad
     * @return  Colecci&oacute;n con los
     * @throws ISPACException
     */
    public IItemCollection getEntitySchemeFilter(IState iState, IEntityDef entitydef) throws ISPACException
    {
        IInvesflowAPI invesFlowAPI = mcct.getAPI();

        // Lista de entidades de una fase de un procedimiento
        IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();
        
        int entityId = entitydef.getId();

        // Sólo se filtra la entidad de documentos
        if (entityId == ISPACEntities.DT_ID_DOCUMENTOS)
        {
//            //Filtros específicos a aplicar a los documentos++++
//            if (iState.getTaskId()!=0)
//                return entitiesAPI.getSchemeTaskDocuments(iState.getNumexp(),iState.getTaskId());
//            if (iState.getStageId()!=0)
//                return entitiesAPI.getSchemeStageDocuments(iState.getNumexp(),iState.getStageId());
//            if (iState.getProcessId()!=0)
//                return entitiesAPI.getSchemeProcessDocuments(iState.getNumexp());
        	
        	// En el esquema nos interesa tener ahora todos los documentos del expediente, independientemente del estado (IState)
    	    Property[] columns = new Property[1];
    	    columns[0] = new Property(1,"ESTADO",Types.VARCHAR);
    	    
        	return entitiesAPI.getSchemeEntities(entitydef, iState.getNumexp(), "", columns);
        }
        
        if (entityId == ISPACEntities.DT_ID_TRAMITES)
        {
            return getSchemeTasks(entitydef, iState);
        }

        return entitiesAPI.getSchemeEntities(entitydef, iState.getNumexp());
    }

    /**
     * Devuelve el resumen de los registros correspondientes al estado de tramitaci&oacute;n.
     * Adem&aacute;s a&#241;ade al resumen de la entidad las propiedades especificadas en extracolumns[] y los filtra
     * mediante la expresi&oacute;n SQL query.
     *
     * @param  iState  el estado actual de tramitaci&oacute;n
     * @param  entityId  identificador de entidad
     * @return  Colecci&oacute;n
     * @throws ISPACException
     */
    public IItemCollection getEntityScheme(IState iState, int entityId, String query, Property[] extracolumns) throws ISPACException
    {
        IInvesflowAPI invesFlowAPI = mcct.getAPI();

        // Lista de entidades de una fase de un procedimiento
        IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();
        return entitiesAPI.getSchemeEntities(entityId, iState.getNumexp(), query, extracolumns);
    }

    /**
    * Se recuperan los registros de la entidad trámites
    * asociados con el expediente y actualizando el campo
    * estado de estos registros dependiendo de si es un tramite
    * abierto propio o abierto ajeno
    *
    * @param  iState  el estado actual de tramitaci&oacute;n
    * @return   Colecci&oacute;n de entidades de tr&aacute;mite
    * @throws ISPACException
    */
    public IItemCollection getSchemeTasks(IState iState) throws ISPACException
    {
        // Se recuperan los registros de la entidad trámites
        // asociados con el expediente y actualizando el campo
        // estado de estos registros dependiendo de si es un tramite
        // abierto propio o abierto ajeno
        IInvesflowAPI invesFlowAPI = mcct.getAPI();
        IWorklistAPI worklistAPI = invesFlowAPI.getWorkListAPI();
        IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();

        IItemCollection enttasks=null;
        IItemCollection ownedtasks=null;

        //No hay proceso en el contexto.
        if (iState.getProcessId()==0)
            return null;

        Property[] columns = new Property[3];
        columns[0] = new Property(1,"ESTADO",Types.INTEGER);
        columns[1] = new Property(2,"ID_TRAM_EXP",Types.INTEGER);
        columns[2] = new Property(3,"ID_SUBPROCESO",Types.INTEGER);

        if (iState.getStagePcdId()!=0)
        {
            // crear la entidad através de getSchemeEntities(int entityId, String expedient,String query,Property[] extraprop)
            // query = "id_fase_pcd = " + stagepcd + "
            // Property[] --> ESTADO, ID_TRAM_EXP
            String query = "ID_FASE_PCD = " + iState.getStagePcdId();
            enttasks = entitiesAPI.getSchemeEntities(ISPACEntities.DT_ID_TRAMITES, iState.getNumexp(), query, columns);
            ownedtasks = worklistAPI.findStageActiveTasks(iState.getProcessId(),iState.getStagePcdId());
        }
        else
        {
            // crear la entidad através de getSchemeEntities(int entityId, String expedient,String query,Property[] extraprop)
            // query = null
            //Property[] --> ESTADO, ID_TRAM_EXP
            enttasks = entitiesAPI.getSchemeEntities(ISPACEntities.DT_ID_TRAMITES, iState.getNumexp(), null, columns);
            ownedtasks = worklistAPI.findActiveTasks(iState.getProcessId());
        }

        return setTaskState(enttasks,ownedtasks);
    }
    
    /**
     * Se recuperan los registros de la entidad trámites
     * asociados con el expediente y actualizando el campo
     * estado de estos registros dependiendo de si es un tramite
     * abierto propio o abierto ajeno
     *
     * @param  entityDef definición de la entidad
     * @param  iState  el estado actual de tramitaci&oacute;n
     * @return   Colecci&oacute;n de entidades de tr&aacute;mite
     * @throws ISPACException
     */
     public IItemCollection getSchemeTasks(IEntityDef entitydef, IState iState) throws ISPACException
     {
         // Se recuperan los registros de la entidad trámites
         // asociados con el expediente y actualizando el campo
         // estado de estos registros dependiendo de si es un tramite
         // abierto propio o abierto ajeno
         IInvesflowAPI invesFlowAPI = mcct.getAPI();
         IWorklistAPI worklistAPI = invesFlowAPI.getWorkListAPI();
         IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();

         IItemCollection enttasks=null;
         IItemCollection ownedtasks=null;
       

         //No hay proceso en el contexto.
         if (iState.getProcessId()==0)
             return null;

         Property[] columns = new Property[5];
         columns[0] = new Property(1,"ESTADO",Types.INTEGER);
         columns[1] = new Property(2,"ID_TRAM_EXP",Types.INTEGER);
         columns[2] = new Property(3,"ID_SUBPROCESO",Types.INTEGER);
         columns[3]=new Property(4,"ID_FASE_EXP", Types.INTEGER);
         columns[4]=new Property(5,"FECHA_INICIO", Types.TIMESTAMP);

      /*  if (iState.getStagePcdId()!=0)
         {
             // crear la entidad através de getSchemeEntities(int entityId, String expedient,String query,Property[] extraprop)
             // query = "id_fase_pcd = " + stagepcd + "
             // Property[] --> ESTADO, ID_TRAM_EXP
             String query = "ID_FASE_PCD = " + iState.getStagePcdId();
             enttasks = entitiesAPI.getSchemeEntities(entitydef, iState.getNumexp(), query, columns);
             ownedtasks = worklistAPI.findStageActiveTasks(iState.getProcessId(),iState.getStagePcdId());
             query="ID_FASE_PCD !="+ iState.getStagePcdId() +"AND ESTADO="+ ISPACEntities.TASKSTATUS_CLOSE;
             closetasks=entitiesAPI.getSchemeEntities(entitydef, iState.getNumexp(), query, columns);
             
         }
         else
         {*/
             // crear la entidad através de getSchemeEntities(int entityId, String expedient,String query,Property[] extraprop)
             // query = null
             //Property[] --> ESTADO, ID_TRAM_EXP
             enttasks = entitiesAPI.getSchemeEntities(entitydef, iState.getNumexp(), null, columns , " FECHA_INICIO " , true);
             //Si estoy consultando un expediente en modo solo lectura es que soy supervisor
             if(iState.getReadonlyReason()==StateContext.READONLYREASON_DELETED_EXPEDIENT){
            	return enttasks;
             }else{
             ownedtasks = worklistAPI.findActiveTasks(iState.getProcessId());
             }
        // }

         return setTaskState(enttasks,ownedtasks);
     }
    /**
    * Se recuperan los registros de la entidad trámites
    * asociados con el expediente y actualizando el campo
    * estado de estos registros dependiendo de si es un tramite
    * abierto propio o abierto ajeno
    *
    * @param iState  el estado actual de tramitaci&oacute;n
    * @return Colecci&oacute;n de entidades de tr&aacute;mite
    * @throws ISPACException
    */
    public IItemCollection getTasks(IState iState) throws ISPACException
    {
        // Se recuperan los registros de la entidad trámites
        // asociados con el expediente y actualizando el campo
        // estado de estos registros dependiendo de si es un tramite
        // abierto propio o abierto ajeno
        IInvesflowAPI invesFlowAPI = mcct.getAPI();
        IWorklistAPI worklistAPI = invesFlowAPI.getWorkListAPI();
        IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();

        IItemCollection enttasks=null;
        IItemCollection ownedtasks=null;

        //No hay proceso en el contexto.
        if (iState.getProcessId()==0)
            return null;

        Property[] columns = new Property[2];
        columns[0] = new Property(1,"ESTADO",Types.INTEGER);
        columns[1] = new Property(2,"ID_TRAM_EXP",Types.INTEGER);

        if (iState.getStagePcdId()!=0)
        {
            enttasks = entitiesAPI.getStageTasks(iState.getNumexp(), iState.getStagePcdId());
            ownedtasks = worklistAPI.findStageActiveTasks(iState.getProcessId(),iState.getStagePcdId());
        }
        else
        {
            enttasks = entitiesAPI.getExpedientTasks(iState.getNumexp());
            ownedtasks = worklistAPI.findActiveTasks(iState.getProcessId());
        }

        return setTaskState(enttasks,ownedtasks);
    }

    private IItemCollection setTaskState(IItemCollection enttasks, IItemCollection ownedtasks)
    throws ISPACException
    {
        //Map ownedtaskmap = ownedtasks.toMapS();
    	Map ownedtaskmap = ownedtasks.toMapStringKey();
        Iterator iter = enttasks.iterator();

        while (iter.hasNext())
        {
            IItem task = (IItem) iter.next();
            int idTask = task.getInt("ID_TRAM_EXP");

            //TODO: Mover los valores de estado a un enumerado.
            // 1: Tramite abierto
            if (task.getInt("ESTADO") == 1)
            {
                //IItem myOpenedTask = (IItem) ownedtaskmap.get(new Integer(idTask));
            	IItem myOpenedTask = (IItem) ownedtaskmap.get(""+idTask);
                if (myOpenedTask == null)
                {
                    // 2: Tramite abierto ajeno
                    task.set("ESTADO", 2);
                }
            }
        }
        enttasks.reset();
        return enttasks;
    }

    /**
     * Obtiene la aplicación de tramitación buscando en el catálogo la aplicación dependiendo
     * de estado actual. Además la crea para el primer registro de la primera entidad definida
     * en el esquema.
     *
     * @param state estado actual
     * @param path ruta de la aplicación
     * @param noDefault condición de no obtener el formulario por defecto (id_fase=0,id_tramite=0)
     * @return EntityApp
     * @throws ISPACException
     */
    public EntityApp getDefaultEntityApp(IState state, String path, boolean noDefault) throws ISPACException
    {
    	int entityId = state.getEntityId();        
    	int entityRegId = state.getEntityRegId();
    	
        if ((entityId != 0) &&
        	(entityRegId != 0)) {
        	
            // Obtener la definición de la entidad a mostrar
            IEntityDef entitydef = getEntityDef(entityId);
            if (entitydef == null) {
            	return getEntityApp(state, path, entityRegId, noDefault);
            } else {
            	return getEntityApp(entitydef, state, path, entityRegId, noDefault);
            }
        }

        Map scheme = getSchemeEntities(state);

        if (entityId == 0) {
        	
        	// Obtener la entidad por defecto a mostrar
        	entityId = getDefaultEntity(state, scheme);
            state.setEntityId(entityId);
            
            // Para fase, trámite y actividad:
            // bloquear la entidad cuando se tiene el bloqueo sobre el estado actual
            state.blockDefaultEntity(mcct);
        }

        IItemCollection schemeent = (IItemCollection) scheme.get(String.valueOf(entityId));
        if (schemeent == null) {
        	
            throw new ISPACNullObject("Scheme.getDefaultEntityApp() - No existe ningun registro de entidad seleccionable");
        }

        schemeent.reset();
        if(!schemeent.next()) {
        	
            throw new ISPACNullObject("Scheme.getDefaultEntityApp() - No existe ningun registro de entidad seleccionable");
        }

        // Se obtiene el registro de entidad por defecto (el primer registro de los registros de la entidad para el numexp actual)
        IItem item = schemeent.value();
        state.setEntityRegId(item.getInt("SCHEME_ID"));
        
        // Obtener y procesar el entityapp que presenta la entidad
        EntityApp entityApp = null;
        // Obtener la definición de la entidad a mostrar
        IEntityDef entitydef = getEntityDef(entityId);
        if (entitydef == null) {
        	entityApp = getEntityApp(state, path, entityRegId, noDefault);
        } else {
        	entityApp = getEntityApp(entitydef, state, path, entityRegId, noDefault);
        }
        
        // Establecer el registro de entidad
        state.setEntityRegId(entityApp.getEntityRegId());
        
        return entityApp;
    }
    
    /**
     * Obtiene la aplicación de tramitación buscando en el catálogo la aplicación dependiendo
     * de estado actual. Además la crea para el primer registro de la primera entidad definida
     * en el esquema.
     *
     * @param state estado actual
     * @param appId identificador de aplicación
     * @param path ruta de la aplicación
     * @param noDefault condición de no obtener el formulario por defecto (id_fase=0,id_tramite=0)
     * @return EntityApp
     * @throws ISPACException
     */
    public EntityApp getDefaultEntityApp(IState state, int appId, String path, boolean noDefault) throws ISPACException
    {
    	int entityId = state.getEntityId();        
    	int entityRegId = state.getEntityRegId();
    	
        if ((entityId != 0) &&
        	(entityRegId != 0)) {
        	
            // Obtener la definición de la entidad a mostrar
            IEntityDef entitydef = getEntityDef(entityId);
            if (entitydef == null) {
            	return getEntityApp(state, entityId, entityRegId, appId, path, entityRegId, noDefault);
            } else {
            	return getEntityApp(state, entitydef, entityRegId, appId, path, entityRegId, noDefault);
            }
        }

        Map scheme = getSchemeEntities(state);

        if (entityId == 0) {
        	
        	// Obtener la entidad por defecto a mostrar
        	entityId = getDefaultEntity(state, scheme);
            state.setEntityId(entityId);
            
            // Para fase, trámite y actividad:
            // bloquear la entidad cuando se tiene el bloqueo sobre el estado actual
            state.blockDefaultEntity(mcct);
        }

        IItemCollection schemeent = (IItemCollection) scheme.get(String.valueOf(entityId));
        if (schemeent == null) {
        	
            throw new ISPACNullObject("Scheme.getDefaultEntityApp() - No existe ningun registro de entidad seleccionable");
        }

        schemeent.reset();
        if(!schemeent.next()) {
        	
            throw new ISPACNullObject("Scheme.getDefaultEntityApp() - No existe ningun registro de entidad seleccionable");
        }

        // Se obtiene el registro de entidad por defecto (el primer registro de los registros de la entidad para el numexp actual)
        IItem item = schemeent.value();
        state.setEntityRegId(item.getInt("SCHEME_ID"));
        
        // Obtener y procesar el entityapp que presenta la entidad
        EntityApp entityApp = null;
        // Obtener la definición de la entidad a mostrar
        IEntityDef entitydef = getEntityDef(entityId);
        if (entitydef == null) {
        	entityApp = getEntityApp(state, entityId, state.getEntityRegId(), appId, path, entityRegId, noDefault);
        } else {
        	entityApp = getEntityApp(state, entitydef, state.getEntityRegId(), appId, path, entityRegId, noDefault);
        }
        
        // Establecer el registro de entidad
        state.setEntityRegId(entityApp.getEntityRegId());
        
        return entityApp;
    }

    private int getDefaultEntity(IState state, Map scheme) throws ISPACException {
    	
    	// Entidad por defecto a mostrar
    	// El expediente si está visible
    	// o el trámite cuando se visualiza un trámite
    	if (state.getSubProcessId() != 0) {
    		
    		if (scheme.containsKey(String.valueOf(ISPACEntities.DT_ID_EXPEDIENTES))) {
    		
    			return ISPACEntities.DT_ID_EXPEDIENTES;
    		}
    	}
    	else if ((state.getTaskId() != 0) &&
    			 (scheme.containsKey(String.valueOf(ISPACEntities.DT_ID_TRAMITES)))) {
            
    		return ISPACEntities.DT_ID_TRAMITES;
    	}
    	else if ((state.getStageId() != 0) &&
    			 (scheme.containsKey(String.valueOf(ISPACEntities.DT_ID_EXPEDIENTES)))) {
            
    		return ISPACEntities.DT_ID_EXPEDIENTES;
    	}

    	// Obtener la primera entidad como entidad a mostrar
    	Entry schemeentry = null;
    	
        Iterator it = scheme.entrySet().iterator();
        if (it.hasNext()) {
        	
        	schemeentry = (Entry) it.next();
        	String key = (String) schemeentry.getKey();
        	
        	if (key != String.valueOf(ISPACEntities.DT_ID_TRAMITES)) { 
        		
        		return Integer.parseInt(key);
        	}
        	else if (it.hasNext()) {
        		
        		schemeentry = (Entry) it.next();
        		return Integer.parseInt( (String) schemeentry.getKey());
        	}
        }
        
        throw new ISPACNullObject("Scheme.getDefaultEntity() - No existe ninguna entidad seleccionable");
    }
    
    private IEntityDef getEntityDef(int entityId) throws ISPACException {
    	
        // Obtener la definición de la entidad a mostrar
        IEntityDef entitydef = null;
        if (mcatalog != null) {
        	
        	mcatalog.reset();
	        Map ctEntities = mcatalog.toMapStringKey();
	        entitydef = (IEntityDef) ctEntities.get(String.valueOf(entityId));
        }
        
        return entitydef;
    }

    /**
     * Obtiene la aplicación de tramitación buscando en el catálogo la aplicación dependiendo
     * de estado actual. La entidad y registro son también los indicados en el estado actual.
     *
     * @param state estado actual
     * @param path ruta de la aplicación
     * @param urlKey Clave de la entidad en la url
     * @param noDefault condición de no obtener el formulario por defecto (id_fase=0,id_tramite=0)
     * @return EntityApp la aplicación solicitada
     * @throws ISPACException
     */
    public EntityApp getEntityApp(IState state, String path, int urlKey, boolean noDefault) throws ISPACException
    {
        int entityId = state.getEntityId();
        int entityRegId = state.getEntityRegId();

        return getEntityApp(state, entityId, entityRegId, 0, path, urlKey, noDefault);
    }
    
    /**
     * Obtiene la aplicación de tramitación buscando en el catálogo la aplicación dependiendo
     * de estado actual. La entidad y registro son también los indicados en el estado actual.
     *
     * @param entityDef definición de la entidad
     * @param state estado actual
     * @param path ruta de la aplicación
     * @param urlKey Clave de la entidad en la url
     * @param noDefault condición de no obtener el formulario por defecto (id_fase=0,id_tramite=0)
     * @return EntityApp la aplicación solicitada
     * @throws ISPACException
     */
    public EntityApp getEntityApp(IEntityDef entitydef, IState state, String path, int urlKey, boolean noDefault) throws ISPACException
    {
        int entityRegId = state.getEntityRegId();

        return getEntityApp(state, entitydef, entityRegId, 0, path, urlKey, noDefault);
    }

    /**
     * Obtiene la aplicación de tramitación para la entidad y registro indicados buscando en el catálogo dependiendo
     * del estado actual.
     *
     * @param state estado actual
     * @param entityId identificador de entidad
     * @param entityRegId identificador de registro de entidad
     * @param path ruta de la aplicación
     * @param urlKey Clave de la entidad en la url
     * @param noDefault condición de no obtener el formulario por defecto (id_fase=0,id_tramite=0)
     * @return EntityApp la aplicación solicitada
     * @throws ISPACException
     */
    public EntityApp getEntityApp(IState state, int entityId, int entityRegId, String path, int urlKey, boolean noDefault) throws ISPACException
    {
        // Obtener la definición de la entidad a mostrar
        IEntityDef entitydef = getEntityDef(entityId);
        if (entitydef == null) {
        	return getEntityApp(state, entityId, entityRegId, 0, path, urlKey, noDefault);
        } else {
        	return getEntityApp(state, entitydef, entityRegId, 0, path, urlKey, noDefault);
        }
    }
    
    /**
     * Obtiene la aplicación de tramitación para la entidad y registro indicados buscando en el catálogo dependiendo
     * del estado actual.
     *
     * @param state estado actual
     * @param entityDef definición de la entidad
     * @param entityRegId identificador de registro de entidad
     * @param path ruta de la aplicación
     * @param urlKey Clave de la entidad en la url
     * @param noDefault condición de no obtener el formulario por defecto (id_fase=0,id_tramite=0)
     * @return EntityApp la aplicación solicitada
     * @throws ISPACException
     */
    public EntityApp getEntityApp(IState state, IEntityDef entitydef, int entityRegId, String path, int urlKey, boolean noDefault) throws ISPACException
    {
        return getEntityApp(state, entitydef, entityRegId, 0, path, urlKey, noDefault);
    }

    /**
     * Obtiene la aplicación de tramitación para la entidad y registro indicados. Si no se suministra el id
     * de aplicación se busca en el catálogo dependiendo del estado actual.
     *
     * @param state estado actual
     * @param entityId identificador de entidad
     * @param entityRegId identificador de registro de entidad
     * @param appId identificador de aplicación
     * @param path ruta de la aplicación
     * @param urlKey Clave de la entidad en la url
     * @return EntityApp la aplicación solicitada
     * @throws ISPACException
     */
    public EntityApp getEntityApp(IState state,
    							  int entityId,
    							  int entityRegId,
    							  int appId,
    							  String path,
    							  int urlKey,
    							  boolean noDefault) throws ISPACException {
    	    	
        if (entityId == 0) {
            throw new ISPACInfo("exception.expedients.entities.noVisibles");
        }
        
        state.setEntityId(entityId);
                
        if (entityRegId == 0) {
            entityRegId=ISPACEntities.ENTITY_NULLREGKEYID;
        }
        else {
            state.setEntityRegId(entityRegId);
        }

        IInvesflowAPI invesFlowAPI = mcct.getAPI();
        IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();
        
        EntityApp entityapp = null;
        
        // El Id de aplicación ya viene especificado
        if (appId != 0) {
        	entityapp = entitiesAPI.getEntityApp(state.getNumexp(), state.getPcdId(), appId, entityId, entityRegId, path, urlKey);
        }
        
        // TODO Habilitar cuando se vuelvan a activar las entidades a nivel de subproceso y asignar formularios a nivel de actividad y subproceso
        /*
        if ((entityapp == null) && (state.getActivityPcdId() != 0)) {
            entityapp = entitiesAPI.getStageEntityApp(state.getNumexp(), state.getPcdId(), state.getActivityPcdId(), entityId, entityRegId, path, urlKey);
        }

        if ((entityapp == null) && (state.getSubPcdId() != 0)) {
            entityapp = entitiesAPI.getProcedureEntityApp(state.getNumexp(), state.getSubPcdId(), entityId, entityRegId, path, urlKey);
        }
        */
        
    	// Indicador de formulario en sólo lectura (FRM_READONLY)
    	Map params = new HashMap();

        //if ((entityapp == null) && (state.getTaskPcdId() != 0) && (state.getSubProcessId() == 0)) {
        //if ((entityapp == null) && (state.getTaskPcdId() != 0)) {
        if ((entityapp == null) && (state.getTaskPcdId() != 0) && (!noDefault)) {
            entityapp = entitiesAPI.getTaskEntityApp(state.getNumexp(), state.getPcdId(), state.getTaskPcdId(), entityId, entityRegId, path, urlKey, noDefault, params);
        }
        
        if ((entityapp == null) && (state.getStagePcdId() != 0)) {
            entityapp = entitiesAPI.getStageEntityApp(state.getNumexp(), state.getPcdId(), state.getStagePcdId(), entityId, entityRegId, path, urlKey, params);
        }

        if ((entityapp == null) && (state.getPcdId() != 0)) {
            entityapp = entitiesAPI.getProcedureEntityApp(state.getNumexp(), state.getPcdId(), entityId, entityRegId, path, urlKey, params);
        }
        
        // Formulario por defecto de la entidad
        if (entityapp == null) {
        	
        	IItem entity = entitiesAPI.getCatalogEntity(entityId);
        	String frmEdit = entity.getString("FRM_EDIT");
        	if (StringUtils.isNotEmpty(frmEdit)) {
        		entityapp = entitiesAPI.getEntityApp(state.getNumexp(), state.getPcdId(), Integer.parseInt(frmEdit), entityId, entityRegId, path, urlKey);
        	}
        	
        	// No se ha podido encontrar ningún formulario para la entidad
        	if (entityapp == null) {
        	
	        	// Desbloquear la entidad
	        	state.unblockEntity(mcct);
	            	
	        	throw new ISPACInfo("exception.expedients.entity.noDefaultForm", new String[] {entity.getString("NOMBRE")});
        	}
        }
        
        // Comprobar si el formulario tiene que estar en sólo lectura
        processReadonly(state, params);
        
        return entityapp;
    }
    
    /**
     * Obtiene la aplicación de tramitación para la entidad y registro indicados. Si no se suministra el id
     * de aplicación se busca en el catálogo dependiendo del estado actual.
     *
     * @param state estado actual
     * @param entityDef definición de la entidad
     * @param entityRegId identificador de registro de entidad
     * @param appId identificador de aplicación
     * @param path ruta de la aplicación
     * @param urlKey Clave de la entidad en la url
     * @return EntityApp la aplicación solicitada
     * @throws ISPACException
     */
    public EntityApp getEntityApp(IState state,
    							  IEntityDef entitydef,
    							  int entityRegId,
    							  int appId,
    							  String path,
    							  int urlKey,
    							  boolean noDefault) throws ISPACException {
    	
    	int entityId = entitydef.getId();
    	    	
        if (entityId == 0) {
            throw new ISPACInfo("exception.expedients.entities.noVisibles");
        }

        state.setEntityId(entityId);

        if (entityRegId == 0) {
            entityRegId=ISPACEntities.ENTITY_NULLREGKEYID;
        }
        else {
            state.setEntityRegId(entityRegId);
        }

        IInvesflowAPI invesFlowAPI = mcct.getAPI();
        IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();
        
        EntityApp entityapp = null;
        
        // El Id de aplicación ya viene especificado
        if (appId != 0) {
        	entityapp = entitiesAPI.getEntityApp(state.getNumexp(), state.getPcdId(), appId, entitydef, entityRegId, path, urlKey);
        }
        
        // TODO Habilitar cuando se vuelvan a activar las entidades a nivel de subproceso y asignar formularios a nivel de actividad y subproceso
        /*
        if ((entityapp == null) && (state.getActivityPcdId() != 0)) {
            entityapp = entitiesAPI.getStageEntityApp(state.getNumexp(), state.getPcdId(), state.getActivityPcdId(), entityId, entityRegId, path, urlKey);
        }

        if ((entityapp == null) && (state.getSubPcdId() != 0)) {
            entityapp = entitiesAPI.getProcedureEntityApp(state.getNumexp(), state.getSubPcdId(), entityId, entityRegId, path, urlKey);
        }
        */
        
    	// Indicador de formulario en sólo lectura (FRM_READONLY)
    	Map params = new HashMap();

        //if ((entityapp == null) && (state.getTaskPcdId() != 0) && (state.getSubProcessId() == 0)) {
        if ((entityapp == null) && (state.getTaskPcdId() != 0)) {
            entityapp = entitiesAPI.getTaskEntityApp(state.getNumexp(), state.getPcdId(), state.getTaskPcdId(), entitydef, entityRegId, path, urlKey, noDefault, params);
        }

        if ((entityapp == null) && (state.getStagePcdId() != 0)) {
            entityapp = entitiesAPI.getStageEntityApp(state.getNumexp(), state.getPcdId(), state.getStagePcdId(), entitydef, entityRegId, path, urlKey, params);
        }

        if ((entityapp == null) && (state.getPcdId() != 0)) {
            entityapp = entitiesAPI.getProcedureEntityApp(state.getNumexp(), state.getPcdId(), entitydef, entityRegId, path, urlKey, params);
        }
        
        // Formulario por defecto de la entidad
        if (entityapp == null) {
        	
        	String frmEdit = entitydef.getAppIdString();
        	if (StringUtils.isNotEmpty(frmEdit)) {
        		
        		entityapp = entitiesAPI.getEntityApp(state.getNumexp(), state.getPcdId(), Integer.parseInt(frmEdit), entitydef, entityRegId, path, urlKey);
        	}
        
        	// No se ha podido encontrar ningún formulario para la entidad
        	if (entityapp == null) {
        	
	        	// Desbloquear la entidad
	        	state.unblockEntity(mcct);
	            	
	        	throw new ISPACInfo("exception.expedients.entity.noDefaultForm", new String[] {entitydef.getName()});
        	}
        }
        
        // Comprobar si el formulario tiene que estar en sólo lectura
        processReadonly(state, params);
        
        return entityapp;
    }
    
    /**
     * Clona las entidades (principal y secundarias) de la aplicación de tramitación
     * para la entidad y registro indicados.
     *
     * @param state estado actual
     * @param entityId identificador de entidad
     * @param entityRegId identificador de registro de entidad
     * @param newExpedient nuevo expediente a asignar a las entidades clonadas
     * @param entityFields campos de las entidades que se clonan
     * @return Map identificadores de catálogo de las entidades secundarias que se han clonado
     * @throws ISPACException
     */
    public Map cloneEntityApp(IState state,
    						  int entityId,
    						  int entityRegId,
    						  IItem newExpedient,
    						  Map entityFields) throws ISPACException {
    	return cloneEntityApp(state, entityId, entityRegId, newExpedient, entityFields, null);
	}

    /**
     * Clona las entidades (principal y secundarias) de la aplicación de tramitación
     * para la entidad y registro indicados.
     *
     * @param state estado actual
     * @param entityId identificador de entidad
     * @param entityRegId identificador de registro de entidad
     * @param newExpedient nuevo expediente a asignar a las entidades clonadas
     * @param entityFields campos de las entidades que se clonan
     * @param noCloneSecondaryCtEntityIds identificadores de las entidades secundarias que no se van a clonar
     * @return Map identificadores de catálogo de las entidades secundarias que se han clonado
     * @throws ISPACException
     */
    public Map cloneEntityApp(IState state,
    						  int entityId,
    						  int entityRegId,
    						  IItem newExpedient,
    						  Map entityFields,
    						  Map noCloneSecondaryCtEntityIds) throws ISPACException {

		IInvesflowAPI invesFlowAPI = mcct.getAPI();
		IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();
	
		EntityApp entityapp = null;
		
		if ((entityapp == null) && (state.getStagePcdId() != 0)) {
			entityapp = entitiesAPI.getStageEntityApp(state.getNumexp(), state.getPcdId(), state.getStagePcdId(), entityId, entityRegId, null, entityRegId, null);
		}
		
		if ((entityapp == null) && (state.getPcdId() != 0)) {
			entityapp = entitiesAPI.getProcedureEntityApp(state.getNumexp(), state.getPcdId(), entityId, entityRegId, null, entityRegId, null);
		}
		
		// Formulario por defecto de la entidad
		if (entityapp == null) {
		
        	IItem entity = entitiesAPI.getCatalogEntity(entityId);
        	String frmEdit = entity.getString("FRM_EDIT");
        	if (StringUtils.isNotEmpty(frmEdit)) {
        		entityapp = entitiesAPI.getEntityApp(state.getNumexp(), state.getPcdId(), Integer.parseInt(frmEdit), entityId, entityRegId, null, entityRegId);
        	}
		}
		
		if (entityapp != null) {
			return entityapp.clone(newExpedient, entityFields, noCloneSecondaryCtEntityIds);
		}
		
		return null;
	}
        
    /**
     * Comprueba si el formulario tiene que estar en sólo lectura.
     * 
     * @param state state estado actual
     * @param params parámetros (FRM_READONLY = formulario en sólo lectura)
     * @throws ISPACException
     */
    private void processReadonly(IState state,
    							 Map params) throws ISPACException {
    	        
        // Formulario en sólo lectura 
        // cuando no se ha establecido previamente en el estado
        if (!state.getReadonly()) {
        	
	        Boolean readonlyForm = (Boolean) params.get("FRM_READONLY");
	        if ((readonlyForm != null) &&
	        	(readonlyForm.booleanValue())) {
	        	
	        	// Establecer en el estado el formulario como sólo lectura
	        	state.setReadonly(true);
	        	state.setReadonlyReason(StateContext.READONLYREASON_FORM);
	        	
	        	// Desbloquear la entidad por estar en sólo lectura
	        	state.unblockEntity(mcct);
	        }
        }
    }

    /**
     * Crea un registro para la entidad indicada y obtiene la aplicación de tramitación correspondiente. Si no se suministra el id
     * de aplicación se busca en el catálogo dependiendo del estado actual.
     *
     * @param state estado actual
     * @param entityId identificador de entidad
     * @param path ruta de la aplicación
     * @return EntityApp la aplicación solicitada
     * @throws ISPACException
     */
    /*
    public EntityApp newEntityApp(IState state, String path) throws ISPACException
    {
        return newEntityApp(state, state.getEntityId(), 0, path);
    }
    */

    /**
     * Crea un registro para la entidad indicada y obtiene la aplicación de tramitación correspondiente. Si no se suministra el id
     * de aplicación se busca en el catálogo dependiendo del estado actual.
     *
     * @param state estado actual
     * @param entityId identificador de entidad
     * @param path ruta de la aplicación
     * @return EntityApp la aplicación solicitada
     * @throws ISPACException
     */
    /*
    public EntityApp newEntityApp(IState state, int entityId, String path) throws ISPACException
    {
        return newEntityApp(state, entityId, 0, path);
    }
    */

    /**
     * Crea un registro para la entidad indicada y obtiene la aplicación de tramitación correspondiente. Si no se suministra el id
     * de aplicación se busca en el catálogo dependiendo del estado actual.
     *
     * @param state estado actual
     * @param entityId identificador de entidad
     * @param appId identificador de aplicación
     * @param path ruta de la aplicación
     * @return EntityApp la aplicación solicitada
     * @throws ISPACException
     */
    /*
    public EntityApp newEntityApp(IState state, int entityId, int appId, String path) throws ISPACException
    {
        if (entityId == 0) {
        	
            throw new ISPACException("ManagerAPI.newEntityApp(...) - El identificador de entidad no puede ser nulo");
        }

        state.setEntityId(entityId);

        IInvesflowAPI invesFlowAPI = mcct.getAPI();
        IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();
        
        EntityApp entityapp = null;

        if (appId != 0) {
        	entityapp = entitiesAPI.newEntityApp(state.getPcdId(), appId, entityId, path);
        }
        
        if ((entityapp == null) && (state.getTaskPcdId() != 0)) {
            entityapp = entitiesAPI.newTaskEntityApp(state.getPcdId(), state.getTaskPcdId(), entityId, path);
        }

        if ((entityapp == null) && (state.getStagePcdId() != 0)) {
        	entityapp= entitiesAPI.newStageEntityApp(state.getPcdId(), state.getStagePcdId(), entityId, path);
        }

        if ((entityapp == null) && (state.getPcdId() != 0)) {
            entityapp= entitiesAPI.newProcedureEntityApp(state.getPcdId(), entityId, path);
        }

        if (entityapp == null) {
            throw new ISPACException("ManagerAPI.getEntityApp(...) - No se ha podido encontrar ninguna aplicación para la entidad");
        }

        state.setEntityRegId(entityapp.getEntityRegId());
        
        return entityapp;
    }
    */

    /**
     * Obtiene la lista de beans con los hitos obtenidos
     *
     * @param state estado actual
     * @throws ISPACException
     */
    public List getMilestones(IState state) throws ISPACException
    {
        IInvesflowAPI invesFlowAPI = mcct.getAPI();

        int processId=state.getProcessId();
        String numexp=state.getNumexp();

        IItemCollection milestonecollection = null;

        if (processId!=0)
        {
            milestonecollection=invesFlowAPI.getMilestones(processId);
        }
        else if (numexp!=null&&numexp.length()>0)
        {
            milestonecollection=invesFlowAPI.getMilestones(numexp);
        }

        // No se puede encontrar ningún proceso para buscar hitos.
        if (milestonecollection==null)
            return new ArrayList();

        if (state.getPcdId()==0)
            return new ArrayList();

        IRespManagerAPI respManagerAPI = invesFlowAPI.getRespManagerAPI();

        List beanlist = CollectionBean.getBeanList(milestonecollection);

        IProcedure iProcedure = invesFlowAPI.getProcedure(state.getPcdId());

        // Confeccionamos la lista de itemBean para hitos
        String author = null;
        String authorName = null;
        
        int faseid = 0;
        int tramiteid = 0;

        List milestonelist = new ArrayList();
        Map autores = new HashMap();

        for (Iterator ite = beanlist.iterator(); ite.hasNext();)
        {
            ItemBean bean = (ItemBean) ite.next();

            // Nombre del autor
            author = (String) bean.getProperty("AUTOR");
            authorName = (String) autores.get(author);
            if (authorName == null) {
            	
            	// El usuario del autor puede haber sido eliminado
            	IResponsible resp = null;
            	
            	try {
            		resp = respManagerAPI.getResp(author);
                    if (resp != null) {
                    	
                    	authorName = resp.getRespName();
                    }
            	}
            	catch (ISPACException ie) {
            		
            		authorName = "UID[" + author + "]";
            	}
            	
            	autores.put(author, authorName);
            }
            
            bean.setProperty("AUTOR_NOMBRE", authorName);

            // Nombre de la fase
            faseid = bean.getItem().getInt("ID_FASE");
            IItem itemFase = iProcedure.getStage(faseid);
            if (itemFase != null)
                bean.setProperty("FASE_NOMBRE", itemFase.getString("NOMBRE"));
            else
                bean.setProperty("FASE_NOMBRE", "");

            // Nombre del trámite
            tramiteid = bean.getItem().getInt("ID_TRAMITE");
            IItem itemTramite = iProcedure.getTask(tramiteid);
            if (itemTramite != null)
                bean.setProperty("TRAMITE_NOMBRE",itemTramite.getString("NOMBRE"));
            else
                bean.setProperty("TRAMITE_NOMBRE", "");

            milestonelist.add(bean);
        }

        return milestonelist;
    }
    
    /**
     * Obtiene una colección con la definición de la cabecera de contexto.
     */
    public IItemCollection getContextHeader(IState state, Locale locale, MessageResources resources)
    throws ISPACException
    {
        List list = new ArrayList();

        ContextHeaderFactory contHeaderFact = new ContextHeaderFactory(mcct);

        ContextHeader context;

        context = contHeaderFact.createProcedureHeader(state);
        if (context != null) list.add(context);

        context = contHeaderFact.createStagePcdHeader(state);
        if (context != null)list.add(context);

        context = contHeaderFact.createTaskCtlHeader(state);
        if (context != null) list.add(context);
        
        context = contHeaderFact.createBatchTaskCtlHeader(state, locale, resources);
        if (context != null) list.add(context);

        context = contHeaderFact.createSubProcedureHeader(state);
        if (context != null) list.add(context);
        
        context = contHeaderFact.createActivityPcdHeader(state);
        if (context != null) list.add(context);
        
        context = contHeaderFact.createStageHeader(state);
        if (context != null) list.add(context);
        
        return new ListCollection(list);
    }
    public IItemCollection getContextHeader(IState state, Locale locale, MessageResources resources, String stagePcdId)
    throws ISPACException
    {
        List list = new ArrayList();

        ContextHeaderFactory contHeaderFact = new ContextHeaderFactory(mcct);

        ContextHeader context;

        context = contHeaderFact.createProcedureHeader(state);
        if (context != null) list.add(context);

        context = contHeaderFact.createStagePcdHeader(state, stagePcdId);
        if (context != null)list.add(context);

        context = contHeaderFact.createTaskCtlHeader(state);
        if (context != null) list.add(context);
        
        context = contHeaderFact.createBatchTaskCtlHeader(state, locale, resources);
        if (context != null) list.add(context);

        context = contHeaderFact.createSubProcedureHeader(state);
        if (context != null) list.add(context);
        
        context = contHeaderFact.createActivityPcdHeader(state);
        if (context != null) list.add(context);
        
        context = contHeaderFact.createStageHeader(state);
        if (context != null) list.add(context);
        
        return new ListCollection(list);
    }




    
}