package ieci.tdw.ispac.ispacmgr.mgr;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISessionAPI;
import ieci.tdw.ispac.api.IWorklistAPI;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACNullObject;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.IProcedure;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.join.TableJoinFactoryDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXProcesoDAO;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispactx.TXConstants;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.IWorklist;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;
import ieci.tdw.ispac.ispacweb.api.ManagerState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;


public class SpacMgr {
    
	private static final Logger logger = Logger.getLogger(SpacMgr.class);
	
	public static int PARENT_EXPEDIENTS = 0;
    public static int CHILD_EXPEDIENTS = 1;
    public static int ALL_EXPEDIENTS = 2;
    
    /**
     * Carga el Expediente que se corresponde con el estado actual en la request
     * @param session
     * @param state
     * @param request
     * @throws ISPACException
     */
    public static void loadExpedient(ISessionAPI session, IState state, 
    		HttpServletRequest request) throws ISPACException {
        
        IEntitiesAPI entitiesAPI = session.getAPI().getEntitiesAPI();
        ItemBean itemBean = new ItemBean(entitiesAPI.getExpedient(
        		state.getNumexp()));
        request.setAttribute("Expedient", itemBean);
    }

    /**
     * Carga los datos necesarios para acceder a los Expedientes 
     * relacionados con el expediente actual.
     * @param session Session API de ISPAC
     * @param state Estado actual del tramitador
     * @param request
     * @throws ISPACException
     */
    public static void loadRelatedExpedient(ISessionAPI session, 
    		HttpServletRequest request, String numExp, int type) 
    		throws ISPACException {
        
        IItemCollection itemCol = null;
        if (type == ALL_EXPEDIENTS || type == PARENT_EXPEDIENTS){
            //loadParentsExpedient(session, state, request);
            itemCol = getExpedientByDependence(session, numExp, PARENT_EXPEDIENTS);
            
            //Establecemos como rol la parte derecha(descendiente) de la relacion
            organizeRelation(itemCol,"SPAC_EXP_RELACIONADOS:RELACION", 1);
            
            setExpedientAttribute(session, request, itemCol, "supExp", "SPAC_EXP_RELACIONADOS:NUMEXP_PADRE");
        }
        if (type == ALL_EXPEDIENTS || type == CHILD_EXPEDIENTS){
            //loadChildsExpedient(session, state, request);
            itemCol = getExpedientByDependence(session, numExp, CHILD_EXPEDIENTS);
            
            //Establecemos como rol la parte izquierda(antecesor) de la relacion
            organizeRelation(itemCol,"SPAC_EXP_RELACIONADOS:RELACION", 0);
            
            setExpedientAttribute(session, request, itemCol, "subExp", "SPAC_EXP_RELACIONADOS:NUMEXP_HIJO");
        }
    }

    /**
     * Le da la vuelta a los papeles de la relacion entre los expedientes.
     * @param itemCol Coleccion de relacion de expedientes.
     * @param relationField Nombre del campo que contiene la relación.
     * @throws ISPACException
     */
    public static void organizeRelation(IItemCollection itemCol, 
    		String relationField, int rol) throws ISPACException {
        IItem item;
        while(itemCol.next()){
            item = itemCol.value();
            String relacion = item.getString(relationField);
            String[] roles = relacion.split("/");
            if (roles.length > rol) {
            	item.set(relationField, roles[rol]);
            }
            else {
            	item.set(relationField, relacion);
            }
        }
        itemCol.reset();
    }

    /**
     * Obtiene la colección de expedientes en función de
     * la dependencia con el expediente actual.
     * @param session Session API de ISPAC
     * @param state Estado actual del tramitador
     * @param dependence Tipo de dependencia del expediente actual
     * @return Colección de Expedientes relacionados
     * @throws ISPACException
     */
    public static IItemCollection getExpedientByDependence(ISessionAPI session, 
    		String numExp, int dependence) throws ISPACException{
    	
    	IInvesflowAPI invesflowAPI = session.getAPI();

    
        TableJoinFactoryDAO factory = new TableJoinFactoryDAO();
		factory.addTable(TXProcesoDAO.TABLENAME, "SPAC_PROCESOS");
		factory.addTable("SPAC_EXP_RELACIONADOS", "SPAC_EXP_RELACIONADOS");

        String sql="";
	     
        if (dependence == ALL_EXPEDIENTS){
            //Expedientes relacionados con el actual, sean dependientes de éste o que éste dependa de ellos
//TODO: Mirar a ver si se añade otro campo y entonces no filtramos por RELACION = 'Directa'    
           sql = "WHERE (NUMEXP_HIJO = '" + DBUtil.replaceQuotes(numExp) + "' OR NUMEXP_PADRE = '" + DBUtil.replaceQuotes(numExp) + "')" +
            		" AND RELACION != 'Directa' AND SPAC_PROCESOS.ESTADO!="+TXConstants.STATUS_DELETED+
            		" AND SPAC_PROCESOS.NUMEXP== '" + DBUtil.replaceQuotes(numExp) + "')";
            	
        }
        else if (dependence == PARENT_EXPEDIENTS){
            //Expedientes relacionados con el actual siendo éste dependiente de ellos
//TODO: Mirar a ver si se añade otro campo y entonces no filtramos por RELACION = 'Directa'   
        	
            sql="WHERE NUMEXP_HIJO = '" + DBUtil.replaceQuotes(numExp) + "' " +
            		" AND RELACION != 'Directa' AND SPAC_PROCESOS.ESTADO!="+TXConstants.STATUS_DELETED+
            		" AND SPAC_PROCESOS.NUMEXP=NUMEXP_PADRE ";

            		
        }
        else if (dependence == CHILD_EXPEDIENTS){
            //Expedientes relacionados con el actual siendo dependientes del mismo
//TODO: Mirar a ver si se añade otro campo y entonces no filtramos por RELACION = 'Directa'  
   
        	sql= "WHERE NUMEXP_PADRE = '" + DBUtil.replaceQuotes(numExp) + "' " +
            		" AND RELACION != 'Directa'  AND SPAC_PROCESOS.ESTADO!="+TXConstants.STATUS_DELETED+
            		" AND SPAC_PROCESOS.NUMEXP=NUMEXP_HIJO ";

            		
        }
       
        return factory.queryTableJoin(session.getClientContext().getConnection(), sql ).disconnect();
       
    }

    /**
     * Introduce en la request un atributo con el nombre <code>nameAttribute</code>
     * que contendrá la colección de expedientes contenida en <code>itemCol</code> 
     * @param session Session API de ISPAC
     * @param request
     * @param itemCol Colección de expedientes
     * @param nameAttribute Nombre del atributo a introducir en la request
     * @param field
     * @throws ISPACException
     */
    private static void setExpedientAttribute(ISessionAPI session, 
    		HttpServletRequest request, IItemCollection itemCol, 
    		String nameAttribute, String field) throws ISPACException{
    	
        List list = new LinkedList();
        ItemBean itemExp = null;
        IItem item = null;
        while(itemCol.next()){        
            item = itemCol.value();
            //Si el expediente ya ha sido cerrado al buscarlo en los procesos 
            //se lanzara una excepcion.
            //Si esto se produce buscamos el expediente en la tabla de 
            //expedientes.
            try{
                //Buscamos primero el expediente en la lista de procesos, ya 
            	//que luego usamos el ID_STAGE para acceder a el,
                //Campo que no se encuentra en SPAC_EXPEDIENTES
                itemExp = new ItemBean(session.getAPI().getWorkListAPI()
                		.getProcess(item.getString(field)));    
            }catch (ISPACException e){
                if (e.getCause().getClass().getName().equals(
                		ISPACNullObject.class.getName()))
                    itemExp = new ItemBean(session.getAPI().getEntitiesAPI()
                    		.getExpedient(item.getString(field)));
            }
            itemExp.setProperty("RELACION", item.getString("SPAC_EXP_RELACIONADOS:RELACION"));
            itemExp.setProperty("RELACION_ID", item.getString("SPAC_EXP_RELACIONADOS:ID"));
            list.add(itemExp);
        }
        request.setAttribute(nameAttribute,list);
    }
    
    /**
     * Obtiene los expedientes abiertos para cada fase y para cada procedimiento
     * de los que se encuentran en la colección <code>itcPcd</code>.
     * @param session Sesión.
     * @param itcPcd Colección de Procedimientos.
     * @param resp Responsabilidades del usuario conectado (supervisar y sustituir). 
     * @throws ISPACException
     */
    public static Map getStagesProcs(ISessionAPI session, 
    								 IItemCollection itcPcd, 
    								 String resp) throws ISPACException {
    	
        ClientContext cct = session.getClientContext();
        IManagerAPI managerAPI = ManagerAPIFactory.getInstance()
        	.getManagerAPI(cct);
        IInvesflowAPI invesFlowAPI = cct.getAPI();
        IWorklist managerwl = managerAPI.getWorklistAPI();
        
        //Nos situamos al inicio de la coleccion
        itcPcd.reset();
        
        List stagesproclist = null;
		Map map = null;
		Map mapStages = new LinkedHashMap();
		
		//Recorremos la coleccion de procedimientos
		while( itcPcd.next()){
			stagesproclist = new LinkedList();
		    
		    //Identificador del procedimiento con el vamos a trabajar
		    int pcdId=itcPcd.value().getKeyInt();
		    
		    //mapa con los parametros para el cambio de estado  
		    map = new HashMap();
		    String[] pcdIds = {""+pcdId};
		    map.put(ManagerState.PARAM_PCDID,pcdIds);
		    
		    //Obtenemos los datos del procedimiento con el que estamos 
		    //trabajando
		    IProcedure iProcedure = invesFlowAPI.getProcedure(pcdId);
		    //TxProcedure con los nodos del procedimiento
		    //fases, trámites y nodos de sincronización en Maps con claves enteras
		    
			//Fases asociadas al procedimiento
		    Map stages = iProcedure.getStages().toMap();
		    
		    //Fases con expedientes abiertos para el procedimiento
		    IItemCollection itcStage = managerwl.getStages(managerAPI.newState(ManagerState.STAGESLIST, map), resp);
		    
		    //Recorremos las fases obtenidas de la lista de trabajo
		    Iterator it=itcStage.iterator();
		    while (it.hasNext()){
		        ItemBean stage = new ItemBean((IItem) it.next());
		        //añadimos a la fase el nombre
		        stage.setProperty("NOMBRE", ((IItem)stages.get(new Integer(
		        		stage.getString("ID_FASE")))).get("NOMBRE") );
		        //introducimos la fase en la lista de fases con expedientes 
		        //abiertos
		        stagesproclist.add(stage);
		    }
		    //metemos en un Map, cuya clave sera el id del procedimiento, 
		    //un List con las fases que tienen expedientes abiertos
		    mapStages.put(""+pcdId,stagesproclist);
		}
		
		return mapStages;
    }

    /**
     * Obtiene los trámites abiertos por procedimiento.
     * @param session Sesión.
     * @param resp Responsabilidades del usuario conectado (supervisar y sustituir). 
     * @throws ISPACException
     */
	public static Map<String, List<ItemBean>> getPcdTasksMap(
			ISessionAPI session, String resp) throws ISPACException {
    	
		if (logger.isInfoEnabled()) {
			logger.info("Obteniendo del mapa de trámites por procedimiento: resp=[" + resp + "]");
		}
		
        ClientContext cct = session.getClientContext();
        IInvesflowAPI invesflowAPI = cct.getAPI();
        IWorklistAPI workListAPI = invesflowAPI.getWorkListAPI();

        // Mapa de trámites por procedimiento
		Map<String, List<ItemBean>> tasksMap = new LinkedHashMap<String, List<ItemBean>>();

		// Listado de trámites abiertos
        IItemCollection procedureTasks = workListAPI.getProcedureTasksGroupByPcd(resp);
        while (procedureTasks.next()) {

        	// Información del trámite
        	ItemBean task = new ItemBean(procedureTasks.value());
	        if (logger.isInfoEnabled()) {
	        	logger.info("Trámite: id=[" + task.getString("ID_CTTASK") + "], nombre=[" + task.getString("NAME") + "]");
	        }
	        
	        // Se añade el nombre del trámite
	        task.setProperty("NOMBRE", task.getString("NAME"));
	        
	        // Añadir el trámite al mapa de trámties
	        String pcdId = task.getString("ID_PROC");
	        List<ItemBean> taskList = tasksMap.get(pcdId);
	        if (taskList == null) {
	        	taskList = new ArrayList<ItemBean>();
	        	tasksMap.put(pcdId, taskList);
	        }
	        taskList.add(task);
	    }

		return tasksMap;
    }

	
    /**
     * Obtiene los trámites cerrados por procedimiento.
     * @param session Sesión.
     * @param resp Responsabilidades del usuario conectado (supervisar y sustituir). 
     * @throws ISPACException
     */
	public static Map<String, List<ItemBean>> getPcdClosedTasksMap(
			ISessionAPI session, String resp) throws ISPACException {
		if (logger.isInfoEnabled()) {
			logger.info("Obteniendo del mapa de trámites cerrados por procedimiento: resp=[" + resp + "]");
		}
		
        ClientContext cct = session.getClientContext();
        IInvesflowAPI invesflowAPI = cct.getAPI();
        IWorklistAPI workListAPI = invesflowAPI.getWorkListAPI();

        // Mapa de trámites por procedimiento
		Map<String, List<ItemBean>> tasksMap = new LinkedHashMap<String, List<ItemBean>>();

		// Listado de trámites cerrados
        IItemCollection procedureTasks = workListAPI.getProcedureClosedTasksGroupByPcd(resp);
        while (procedureTasks.next()) {

        	// Información del trámite
        	ItemBean task = new ItemBean(procedureTasks.value());
	        if (logger.isInfoEnabled()) {
	        	logger.info("Trámite: id=[" + task.getString("ID_CTTASK") + "], nombre=[" + task.getString("NAME") + "]");
	        }
	        
	        // Se añade el nombre del trámite
	        task.setProperty("NOMBRE", task.getString("NAME"));
	        
	        // Añadir el trámite al mapa de trámties
	        String pcdId = task.getString("ID_PROC");
	        List<ItemBean> taskList = tasksMap.get(pcdId);
	        if (taskList == null) {
	        	taskList = new ArrayList<ItemBean>();
	        	tasksMap.put(pcdId, taskList);
	        }
	        taskList.add(task);
	    }

		return tasksMap;
	}	
	
    /**
     * Obtiene las actividades abiertas por subproceso.
     * @param sessionAPI Sesión.
     * @param resp Responsabilidades del usuario conectado (supervisar y sustituir). 
     * @throws ISPACException
     */
	public static Map<String, List<ItemBean>> getSubPcdActivitiesMap(
			ISessionAPI sessionAPI, IItemCollection subprocedures, String resp) throws ISPACException {

		if (logger.isInfoEnabled()) {
			logger.info("Obteniendo del mapa de actividades por subproceso: subprocedures=[" + subprocedures + "], resp=[" + resp + "]");
		}

        ClientContext cct = sessionAPI.getClientContext();
        IInvesflowAPI invesflowAPI = cct.getAPI();
        IWorklistAPI workListAPI = invesflowAPI.getWorkListAPI();

		// Mapa de actividades por subproceso
		Map<String, List<ItemBean>> activitiesMap = new LinkedHashMap<String, List<ItemBean>>();

        // Nos situamos al inicio de la colección
        subprocedures.reset();

		// Recorremos la colección de subprocedimientos
		while (subprocedures.next()) {
	
		    // Identificador del subprocedimiento
		    int subProcedureId = subprocedures.value().getKeyInt();

			// Listado de actividades abiertas del subprocedimiento
	        IItemCollection activities = workListAPI.getActivities(subProcedureId, resp);
	        while (activities.next()) {
	
	        	// Información de la actividad
	        	ItemBean activity = new ItemBean(activities.value());
		        if (logger.isInfoEnabled()) {
		        	logger.info("Actividad: [" + activity.getString("NOMBRE") + "]");
		        }
		        
		        // Añadir la actividad al mapa de actividades. 
		        // La clave del mapa es: ID_PROCEDIMIENTO + "-" + ID_SUBPROCEDIMIENTO 
		        String key = activity.getString("ID_PCD_TRAMITE") + "-" + activity.getString("ID_PCD");
		        List<ItemBean> activityList = activitiesMap.get(key);
		        if (activityList == null) {
		        	activityList = new ArrayList<ItemBean>();
		        	activitiesMap.put(key, activityList);
		        }
		        activityList.add(activity);
		    }
		}
		
		return activitiesMap;
	}

    public static void loadAppGestion(ISessionAPI session, IState state, 
    		HttpServletRequest request) throws ISPACException{
    	
        IEntitiesAPI entapi = session.getAPI().getEntitiesAPI();
        IItemCollection apps = entapi.queryEntities(
        		SpacEntities.SPAC_GESTION_APLICACIONES, 
        		"WHERE (ID IN (SELECT ID_APLICACION_GESTION FROM "
        			+"SPAC_GESTION_FASES"
        			+" WHERE ID_PCD = "+state.getPcdId()
        			+" AND (ID_FASE IS NULL OR ID_FASE = "
        			+state.getStagePcdId()+")))" );
        
        if (apps.next()) {
            request.setAttribute("appsGestion",
            		CollectionBean.getBeanList(apps));
        }
    }


    
}