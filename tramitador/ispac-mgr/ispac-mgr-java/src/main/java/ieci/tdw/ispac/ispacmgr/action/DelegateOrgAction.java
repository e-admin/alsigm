package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IRespManagerAPI;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.util.CustomItemCollection;
import ieci.tdw.ispac.ispaclib.bean.ActionBean;
import ieci.tdw.ispac.ispaclib.bean.BeanFormatter;
import ieci.tdw.ispac.ispaclib.bean.CacheFormatterFactory;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.resp.Responsible;
import ieci.tdw.ispac.ispacmgr.menus.MenuFactory;
import ieci.tdw.ispac.ispacmgr.mgr.SchemeMgr;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;
import ieci.tdw.ispac.ispacweb.api.ManagerState;
import ieci.tdw.ispac.ispacweb.menu.Menu;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class DelegateOrgAction extends BaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {

		ClientContext cct = session.getClientContext();

		String idsStage = request.getParameter("idsStage");
		if (idsStage == null)
			idsStage = "";
		String idsTask = request.getParameter("idsTask");
		if (idsTask == null)
			idsTask = "";

		IInvesflowAPI invesFlowAPI = session.getAPI();

		///////////////////////////////////////////////
		// Cambio del estado de tramitación
		IManagerAPI managerAPI=ManagerAPIFactory.getInstance().getManagerAPI(cct);
		Map params = request.getParameterMap();
		IState state = managerAPI.enterState(getStateticket(request),ManagerState.DELEGATE,params);
		storeStateticket(state,response);

		IItemCollection collection = null;
		List listBean = null;

		if (idsStage != "") {
			// Información de los expedientes seleccionados
			collection = invesFlowAPI.getStages(idsStage.replace('-',','));
			listBean = CollectionBean.getBeanList(collection);
			
//[ildfns(v1.1)] 
//-------------------------- 			
			//Recorremos las fases a delegar para obtener su nombre del catalogo, para ser mostrado en la vista
			Iterator it = listBean.iterator();
			ItemBean itemBean = null;
			StringBuffer ids = new StringBuffer("");
			while(it.hasNext()){
			    itemBean = (ItemBean)it.next();
			    if (!ids.toString().equals(""))
			        ids.append(",");
			    ids.append(itemBean.getString("ID_FASE"));
			}

			IItemCollection stageCollection = invesFlowAPI.getEntitiesAPI().queryEntities(SpacEntities.SPAC_P_FASES, "WHERE ID IN ("+ids.toString()+" )");
			Map stagesMap = CollectionBean.getBeanMap(stageCollection, "ID");
			
			request.setAttribute("stagesMap", stagesMap);
			
//Composicion de los menus			
//---------------------------        
	        List listMenus = new ArrayList();
	        Menu menu = new Menu("Inicio");
	        menu.setMsAction(new ActionBean("Inicio","/showProcedureList.do"));
	        listMenus.add(menu);
	        menu = new Menu("Salir");
	        menu.setMsAction(new ActionBean("Salir","/exit.do"));
	        listMenus.add(menu);
	        request.setAttribute("menus", listMenus);
//---------------------------			
//-------------------------- 			
			request.setAttribute("selectedProc",listBean);
		}
		if (idsTask != "") {
		    // Información de los expedientes seleccionados
			collection = invesFlowAPI.getTasks(idsTask.replace('-',','));
			listBean = CollectionBean.getBeanList(collection);
			request.setAttribute("selectedTask",listBean);
		}

		request.setAttribute("idsStage",idsStage);
		request.setAttribute("idsTask",idsTask);

        // Cargar el contexto de la cabecera (miga de pan)
        SchemeMgr.loadContextHeader(state, request, getResources(request), session);

    	// Establecer el menu
        request.setAttribute("menus", MenuFactory.getDelegateMenu(cct, getResources(request)));

		////////////////////////////
		// RESPONSABLES
		/*
		 Se van a obtener los hijos de un responsable (usuarios y grupos)
	  	 y sus ancestros. El responsable del cual se obtiene todo esto es
	  	 aquel cuyo uid se recibe como parámetro

	  	*/
        
	  	IItem organization = null;

//		MULTIORGANIZACION EN UNA MISMA BBDD	  	
// Comprobar si la multiorganización está activada
//		String multiOrganization = ISPACConfiguration.getInstance().get(ISPACConfiguration.MULTIORGANIZATION);
//		if ((multiOrganization != null) &&
//			(multiOrganization.toUpperCase().equals("YES"))) {
//			
//			// Organismo de conexión (cuando hay varios organismos tramitando)
//			organization = (IItem) request.getSession().getAttribute("organization");
//		}
		
	  	Responsible resp = null;
	  	
	  	IRespManagerAPI respAPI = invesFlowAPI.getRespManagerAPI();
	  	String uid = request.getParameter("uid");
	  	if (uid == null) {
	  		if (organization != null) {
				resp = (Responsible) respAPI.getResp(organization.getString("UID_ORGANISMO"));
			}
			else  {
				resp = (Responsible) respAPI.getRootResp();
			}
	  	}
	  	else {
	  		resp = (Responsible) respAPI.getResp(uid);
	  	}

	  	Collection users = resp.getUsers();
	  	Collection orgUnits = resp.getOrgUnits();
	  	IItemCollection ancestors = respAPI.getAncestors(resp.getUID());

	  	List ancestorsList = CollectionBean.getBeanList(ancestors);
	  	
  		if (organization != null){
  			request.setAttribute("ancestors", filterAncestor(ancestorsList, organization.getString("UID_ORGANISMO")));
	  	}
	  	else {
	  		request.setAttribute("ancestors", ancestorsList);
	  	} 	

	  	CustomItemCollection icUsers = new CustomItemCollection (users);
	  	CustomItemCollection icOrgUnits = new CustomItemCollection (orgUnits);
	  	List usersList = CollectionBean.getBeanList(icUsers);
	  	List orgUnitsList = CollectionBean.getBeanList(icOrgUnits);
	  	Collections.sort(usersList, Responsible.getRespComparator());
	  	request.setAttribute("users", usersList);
	  	Collections.sort(orgUnitsList, Responsible.getRespComparator());
	  	request.setAttribute("orgunits", orgUnitsList);

	  	////////////////////////
	  	//FORMATEADOR
	  	//FormatterManager.storeBFDelegateOrgAction(request, getPath());
	  	CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
		BeanFormatter formatter = factory.getFormatter(getISPACPath("/digester/organizationformatter.xml"));
		request.setAttribute("Formatter", formatter);

		return mapping.findForward("success");
	}
	
	private List filterAncestor(List ltAncestors, String uidOrg) throws ISPACException {
		
		if ((ltAncestors != null) &&
			(!ltAncestors.isEmpty())) {
			
			List ltFilterAncestors = new ArrayList();
			
			Iterator it = ltAncestors.iterator();
			while (it.hasNext()) {
				
				ItemBean ancestor = (ItemBean) it.next();
				String uid = ancestor.getString("UID");
				if (uid.equals(uidOrg)) {
					
					ltFilterAncestors.add(ancestor);
					return ltFilterAncestors;
				}
				else {
					ltFilterAncestors.add(ancestor);
				}
			}	
		}
		
		return ltAncestors;
	}
	
}