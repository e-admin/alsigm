package ieci.tdw.ispac.ispacmgr.mgr;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISPACEntities;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.IWorklistAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.IProcess;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.entity.IEntityDef;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacmgr.bean.scheme.RegEntityBean;
import ieci.tdw.ispac.ispacmgr.bean.scheme.SchemeEntityBean;
import ieci.tdw.ispac.ispacmgr.common.constants.ActionsConstants;
import ieci.tdw.ispac.ispactx.TXConstants;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IScheme;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;
import ieci.tdw.ispac.ispacweb.api.ManagerState;
import ieci.tdw.ispac.ispacweb.api.impl.states.SubProcessState;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

/**
 * Clase para la gestión del esquema.
 */
public class SchemeMgr {

    /**
     * Carga los datos del esquema en la request y
     * retorna el esquema de tramitación de un expediente
     * con las entidades correspondientes al estado de tramitación.
     * 
     * @param mapping
     * @param request
     * @param session
     * @param state
     * @return Esquema de tramitación de un expediente
     * @throws Exception
     */
    public static IScheme loadScheme(ActionMapping mapping, 
    								 HttpServletRequest request, 
    								 SessionAPI session, 
    								 IState state) throws Exception {

		ClientContext cct = session.getClientContext();
		if(cct.getStateContext().getStageId()!=0){
		request.setAttribute("stageId",cct.getStateContext().getStageId()+"");
		}
		String fase_actual=(String) request.getSession().getAttribute("stageId");
		String fase_actual_pcd=request.getParameter("stagePcdIdActual");
		//Cuando estamos visualizando un tramite de otra fase viene como parametro
		if(StringUtils.isEmpty(fase_actual)){
			fase_actual=request.getParameter("stageId");
		}
		if(StringUtils.isEmpty(fase_actual_pcd)){
			fase_actual_pcd=state.getStagePcdId()+"";
		}
		int stagePcdId=0;
		if(StringUtils.isNotEmpty(fase_actual_pcd)){
			stagePcdId=Integer.parseInt(fase_actual_pcd);
		}
		IManagerAPI managerAPI = ManagerAPIFactory.getInstance().getManagerAPI(cct);
		
		IInvesflowAPI invesFlowAPI = session.getAPI();
		IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();
		IWorklistAPI worklistAPI = invesFlowAPI.getWorkListAPI();
	    ISecurityAPI securityAPI = invesFlowAPI.getSecurityAPI();
		
		String appLanguage = cct.getAppLanguage();

    	///////////////////////////////////////////////
		// Acceso al esquema
		IScheme scheme = managerAPI.getSchemeAPI();

        // Entidades que forman parte del esquema
		IItemCollection catalogent = scheme.getCatalogEntityScheme(state);
		
		// Esquema resumido de entidades correspondientes al estado de tramitación
		LinkedHashMap schemeEntities = new LinkedHashMap();
		
		// Filtramos las entidades asociadas al esquema
		List schemebeanlist = CollectionBean.getBeanList(SchemeEntityBean.class, catalogent);
		
		boolean inTrash = false;
		
		IProcess process = invesFlowAPI.getProcess(cct.getStateContext().getNumexp());
		if ((process != null)
				&& (process.getInt("ESTADO") == TXConstants.STATUS_DELETED)) {
			inTrash = true;
		}
		
        // Recorremos las entidades del esquema para obtener los registros de cada entidad
		// En los trámites se utilizan para generar los enlaces del área de Datos de Trámites
		// En el resto de entidades se utilizan para obtener el id del registro a cargar (primer registro de la lista)
        for (Iterator ite = schemebeanlist.iterator(); ite.hasNext();)
        {
            SchemeEntityBean scheEntBean = (SchemeEntityBean) ite.next();
            int entityId = scheEntBean.getItem().getKeyInt();
                        
            //IItemCollection itemcol = scheme.getEntitySchemeFilter(state,entityId);
            IItemCollection itemcol = scheme.getEntitySchemeFilter(state, (IEntityDef) scheEntBean.getItem());
         
            schemeEntities.put(String.valueOf(entityId), itemcol);

            List reglist = CollectionBean.getBeanList(RegEntityBean.class,itemcol);
            for (Iterator iter = reglist.iterator(); iter.hasNext();)
            {
                RegEntityBean regEntBean = (RegEntityBean) iter.next();
                regEntBean.getImage(entityId);
                
                // Listado de trámites / actividades de subproceso para el área de Datos de Trámites
                if ((entityId == ISPACEntities.DT_ID_TRAMITES) && 
                	(!(state instanceof SubProcessState))) {
                	
                	if (StringUtils.isEmpty(regEntBean.getString("ID_SUBPROCESO"))) {
                		
                		// Trámite
	                    regEntBean.setUrl("showTask.do");
	                    regEntBean.addParams(ManagerState.PARAM_TASKID, regEntBean.getProperty("ID_TRAM_EXP"));
	                    regEntBean.addParams("stagePcdIdActual", stagePcdId+"");
	                    regEntBean.addParamsId();
                	}
                	else {
                		// Subproceso
                		if (regEntBean.getItem().getInt("ESTADO") == ISPACEntities.TASKSTATUS_CLOSE) {
                	
		                    regEntBean.setUrl("showSubProcess.do");
		                    Map params = new HashMap();
		                    params.put(ManagerState.PARAM_TASKID, regEntBean.getProperty("ID_TRAM_EXP"));
		                    params.put("stagePcdIdActual", stagePcdId+"");
		                    regEntBean.setParams(params);
                		}
                		else {
                			//Si soy supervisor en modo modificación o modo lectura
                			IItemCollection activities=null;
		                    // Actividades
                			if(inTrash && 
                					(securityAPI.isFunction(cct.getRespId(), ISecurityAPI.FUNC_TOTALSUPERVISOR) ||
                					securityAPI.isFunction(cct.getRespId(), ISecurityAPI.FUNC_MONITORINGSUPERVISOR ))
                					){
                				 activities = worklistAPI.findActiveStagesInTrash(regEntBean.getItem().getInt("ID_SUBPROCESO"));
                			}
                			else{
		                     activities = worklistAPI.findActiveStages(regEntBean.getItem().getInt("ID_SUBPROCESO"));
                			}
		                    if (activities.next()) {
		                    	
			                    List reglistActivities = CollectionBean.getBeanList(RegEntityBean.class,activities);
			                    Iterator it = reglistActivities.iterator();
			                    while (it.hasNext()) {
			                    	
			                    	RegEntityBean regEntBeanActivity = (RegEntityBean) it.next();
			                    	regEntBeanActivity.setUrl("showSubProcess.do?" + ManagerState.PARAM_ACTIVITYID + "=" + regEntBeanActivity.getString("ID_STAGE"));
			                    }
			                    
			                    regEntBean.setLtRegEntityBean(reglistActivities);
		                    }
		                    
		                    Map params = new HashMap();
		                    params.put(ManagerState.PARAM_TASKID, regEntBean.getProperty("ID_TRAM_EXP"));
		                    regEntBean.setParams(params);
                		}
                	}
                }
                else {
                    regEntBean.setUrl(mapping.findForward(state.getLabel()).getPath());
                    regEntBean.addParams(state.getParameters());
                }
                
                if (state.getReadonlyReason() == ManagerState.READONLY_REASON_EXPEDIENT_CLOSED || inTrash) {
                	regEntBean.addParams(ManagerState.PARAM_READONLY, new Integer(state.getReadonlyReason()));
                }
         
                
                scheEntBean.addRegEntity(regEntBean);
            }
            
            if (entityId != ISPACEntities.DT_ID_TRAMITES) {
            	
            	// Añade el identificador de la entidad como parámetro a todos los elementos de la lista
            	scheEntBean.addParameterId();	            

	            // Etiqueta para el tab de la entidad (los trámites no se presentan en un tab sino en el área de Datos de Trámites)
	            scheEntBean.setProperty("TAB_LABEL", entitiesAPI.getEntityResourceValue(entityId, appLanguage, scheEntBean.getItem().getString("NOMBRE")));
            }
        }
        
  

        request.setAttribute("SchemeList", schemebeanlist);
        
        // Guardar el esquema resumido de entidades correspondientes al estado de tramitación
        scheme.setSchemeEntities(schemeEntities);
        
        return scheme;
	}

    /**
     * Carga el contexto de la cabecera en la request.
     * @param state
     * @param request
     * @param resources Fichero de recursos
     * @throws ISPACException
     */
    public static void loadContextHeader(IState state, 
    									 HttpServletRequest request,
    									 MessageResources resources, 
    									 SessionAPI session) throws ISPACException {
        
        //if (state.isResponsible()){
			ClientContext cct = session.getClientContext();
			IManagerAPI managerAPI=ManagerAPIFactory.getInstance().getManagerAPI(cct);
			IScheme scheme = managerAPI.getSchemeAPI();
	        
			IItemCollection collection = scheme.getContextHeader(state, cct.getLocale(), resources);
	        List list = CollectionBean.getBeanList(collection);
	        request.setAttribute("ContextHeaderList", list);
	        request.setAttribute(ActionsConstants.CURRENT_STATE, state);
        //}
    }
    /**
     * Cargamos el contesto de la cabecera de la request pero con el parametro stagePcdId para la fase en lugar del que venga en el conte
     * @param state
     * @param request
     * @param resources
     * @param session
     * @param stagePcdId
     * @throws ISPACException
     */
    public static void loadContextHeader(IState state, 
			 HttpServletRequest request,
			 MessageResources resources, 
			 SessionAPI session, String stagePcdId) throws ISPACException {

		//if (state.isResponsible()){
		ClientContext cct = session.getClientContext();
		IManagerAPI managerAPI=ManagerAPIFactory.getInstance().getManagerAPI(cct);
		IScheme scheme = managerAPI.getSchemeAPI();
		
		IItemCollection collection = scheme.getContextHeader(state, cct.getLocale(), resources, stagePcdId);
		List list = CollectionBean.getBeanList(collection);
		request.setAttribute("ContextHeaderList", list);
        request.setAttribute(ActionsConstants.CURRENT_STATE, state);
		//}
}
		
}
