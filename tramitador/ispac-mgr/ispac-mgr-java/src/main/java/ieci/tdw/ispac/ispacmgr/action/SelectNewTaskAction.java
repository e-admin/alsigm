package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.IProcedure;
import ieci.tdw.ispac.ispaclib.bean.CacheFormatterFactory;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispacmgr.menus.MenuFactory;
import ieci.tdw.ispac.ispacmgr.mgr.SchemeMgr;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.util.CollectionUtils;


public class SelectNewTaskAction extends BaseAction {

    public ActionForward executeAction(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response,
            SessionAPI session) throws Exception {

		//APIS
    	ClientContext cct = session.getClientContext();
        IInvesflowAPI invesFlowAPI = cct.getAPI();
		IManagerAPI managerAPI=ManagerAPIFactory.getInstance()
			.getManagerAPI(session.getClientContext());
		
		//Estado actual
		IState state = managerAPI.currentState(getStateticket(request));
		
		IProcedure procedure = invesFlowAPI.getProcedure(state.getPcdId());
		IItemCollection collection = procedure.getTasks(state.getStagePcdId());
		//Lista de Tramites que se pueden crear
		List expTaskList = CollectionBean.getBeanList(collection);
		
		//Si existe este parametro es que se han seleccionado varios expedientes 
		//sobre los que se crearan tramites, pasamos este parametro como atributo
		//TODO Corregir, no tuve tiempo :D, y recoger en newTaskList.jsp 'multibox' como parametro sin falta de pasarlo como atributo
		if (request.getParameter("multibox")!= null) {
		    request.setAttribute("multibox",
		    		request.getParameterValues("multibox"));
		}

		//Tramites ya creados para el expediente
		IEntitiesAPI entapi=invesFlowAPI.getEntitiesAPI();
        String query = new StringBuffer()
        	.append("WHERE NUMEXP = '")
        	.append(DBUtil.replaceQuotes(state.getNumexp()))
        	.append("' AND ID_FASE_PCD = ")
        	.append(state.getStagePcdId()).toString();
        
        IItemCollection itemcol=entapi.queryEntities(
        		SpacEntities.SPAC_DT_TRAMITES,query);
        Map enttaskmap=itemcol.toMapStringKey("ID_TRAM_PCD");
        
        // Trámites cerrados en la misma fase
		Map closedTaskMap = entapi.queryEntities(
				SpacEntities.SPAC_DT_TRAMITES,
				new StringBuffer("WHERE NUMEXP = '")
						.append(DBUtil.replaceQuotes(state.getNumexp()))
						.append("' AND ID_FASE_PCD = ")
						.append(state.getStagePcdId())
						.append(" AND ESTADO = ").append(3)/* CERRADO */
						.toString()).toMapStringKey("ID_TRAM_PCD");
		
		//Añadimos una propiedad a cada tramite que indica si han sido 
        //instanciados o no para este expediente y fase
        for(int i=0;i<expTaskList.size();i++)
		{
		    ItemBean task=(ItemBean)expTaskList.get(i);
		    if(enttaskmap.containsKey(task.getString("ID"))) {
		        task.setProperty("CREADO", "1");
		    } else {
		        task.setProperty("CREADO", "0");
		    }
		    
		    // Dependencias del trámite
		    IItemCollection dependencias = invesFlowAPI.getProcedureAPI().getTaskDependencies(task.getItem().getKeyInt());
		    if (dependencias != null) {
		    	task.setProperty("DEPENDENCIAS", CollectionBean.getBeanList(dependencias));
		    }
		}

        // Comprobar las dependencias de los trámties
        checkTasksDependencies(expTaskList, closedTaskMap);
        
        //Tramites obligatorios para el expediente
		//TODO Cuando se defina la forma de indicar que un tramite es obligatorio

        request.setAttribute("ExpTaskList",expTaskList);
		request.setAttribute("stageId", ""+state.getStageId());
		
		//Añadimos el formateador
		CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
		request.setAttribute("Formatter", 
				factory.getFormatter(getISPACPath("/digester/newtaskformatter.xml")));		

        //Cargamos el contexto de la cabecera (miga de pan)
        SchemeMgr.loadContextHeader(state, request, getResources(request), session);
        
		//Menú
        //Si no existe este parametro significa que queremos crear un tramite para un expediente, no para varios expedientes
        //por lo que incluimos el enlace para situarnos en el expediente
		int stageId = 0;
        if (request.getParameter("multibox")== null) {
	        stageId = state.getStageId();
        }
        request.setAttribute("menus", MenuFactory.getNewTaskMenu(cct, 
        		getResources(request), stageId));
        
        
        return mapping.findForward("success");
    }
    
    private void checkTasksDependencies(List<ItemBean> tasks, Map<String, ItemBean> closedTasks) throws ISPACException {
    	
    	// Recorrer los trámites para comprobar las dependencias
		for (int i = 0; i < tasks.size(); i++) {
			
			ItemBean task = tasks.get(i);
			boolean iniciable = true;

			// Recorrer las dependencias del trámite
			@SuppressWarnings("unchecked")
			List<ItemBean> dependencias = (List<ItemBean>) task.getProperty("DEPENDENCIAS");
			if (!CollectionUtils.isEmpty(dependencias)) {
				for (int depCont = 0; iniciable && (depCont < dependencias.size()); depCont++) {
					ItemBean dependencia = dependencias.get(depCont);
					int parentTaskId = dependencia.getItem().getInt("SPAC_P_DEP_TRAMITES:ID_TRAMITE_PADRE");
					if (closedTasks.get(String.valueOf(parentTaskId)) == null) {
						iniciable = false;
					}
				}
			}
			
			// Establecer la marca de iniciable
			task.setProperty("INICIABLE", iniciable);
		}
    }

}
