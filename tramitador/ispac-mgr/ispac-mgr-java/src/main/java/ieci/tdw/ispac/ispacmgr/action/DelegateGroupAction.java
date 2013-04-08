package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IRespManagerAPI;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.IProcedure;
import ieci.tdw.ispac.api.item.util.CustomItemCollection;
import ieci.tdw.ispac.ispaclib.bean.CacheFormatterFactory;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.resp.Responsible;
import ieci.tdw.ispac.ispaclib.utils.XmlFacade;
import ieci.tdw.ispac.ispacmgr.comparators.GroupComparator;
import ieci.tdw.ispac.ispacmgr.menus.MenuFactory;
import ieci.tdw.ispac.ispacmgr.mgr.SchemeMgr;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;
import ieci.tdw.ispac.ispacweb.api.ManagerState;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class DelegateGroupAction extends BaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {

		ClientContext cct = session.getClientContext();
		String group = request.getParameter("group");
		String idsStage = request.getParameter("idsStage");
		String idsTask = request.getParameter("idsTask");

		IInvesflowAPI invesFlowAPI = session.getAPI();
		IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();


		///////////////////////////////////////////////
		// Cambio del estado de tramitación
		IManagerAPI managerAPI=ManagerAPIFactory.getInstance()
			.getManagerAPI(cct);
		Map params = request.getParameterMap();
		IState state = managerAPI.enterState(getStateticket(request),
				ManagerState.DELEGATE,params);
		storeStateticket(state,response);

		IItemCollection collection = null;

		if (StringUtils.isNotBlank(idsStage)) {
			
			// Información de los expedientes seleccionados
			collection = invesFlowAPI.getStages(idsStage.replace('-',','));
			List listBean = CollectionBean.getBeanList(collection);
			
			//Recorremos las fases a delegar para obtener su nombre del 
			//catalogo, para ser mostrado en la vista
			Iterator it = listBean.iterator();
			ItemBean itemBean = null;
			StringBuffer ids = new StringBuffer("");
			while(it.hasNext()){
			    itemBean = (ItemBean)it.next();
			    if (!ids.toString().equals(""))
			        ids.append(",");
			    ids.append(itemBean.getString("ID_FASE"));
			}

			IItemCollection stageCollection = invesFlowAPI.getEntitiesAPI()
				.queryEntities(SpacEntities.SPAC_P_FASES, 
						new StringBuffer("WHERE ID IN (")
							.append(ids).append(" )").toString());
			Map stagesMap = CollectionBean.getBeanMap(stageCollection, "ID");
			
			request.setAttribute("stagesMap", stagesMap);

			request.setAttribute("selectedProc", listBean);
		} else {
			idsStage = "";
		}

		if (StringUtils.isNotBlank(idsTask)) {
			
			// Información de los expedientes seleccionados
			collection = invesFlowAPI.getTasks(idsTask.replace('-',','));
			request.setAttribute("selectedTask",
					CollectionBean.getBeanList(collection));

		} else {
			idsTask = "";
		}

		request.setAttribute("idsStage", idsStage);
		request.setAttribute("idsTask", idsTask);

        // Cargar el contexto de la cabecera (miga de pan)
        SchemeMgr.loadContextHeader(state, request, getResources(request), session);

    	// Establecer el menu
        request.setAttribute("menus", MenuFactory.getDelegateMenu(cct, getResources(request)));

		////////////////////////////
		// RESPONSABLES
		IRespManagerAPI respAPI = invesFlowAPI.getRespManagerAPI();

		if (StringUtils.isNotBlank(group)) {
		    
		    //Obtenemos los grupos del catalogo de procedimientos		    
		    List listItemGroups = null;
		    
		    //Comprobamos si se recibe identificador de procedimiento, ya sea del estado o por parametro,
		    //en cuyo caso obtenemos los grupos a los que se puede delegar del catalogo segun el id del procedimiento
		    int pcdId = state.getPcdId();
		    if (pcdId == 0 && StringUtils.isNotEmpty(request.getParameter("pcdId")))
		        pcdId = Integer.parseInt(request.getParameter("pcdId"));
		    if (pcdId != 0 && invesFlowAPI.getProcedure(pcdId).getInt("TIPO") == IProcedure.PROCEDURE_TYPE)
		        listItemGroups = getGroupListFromCt(entitiesAPI, pcdId, respAPI.getAllGroups());
		    
		    //Si no hay ningun grupo asignado al Procedimiento se muestran todos
		    if ((listItemGroups == null) || (listItemGroups.size() == 0)){
			IItemCollection groups = respAPI.getAllGroups();
				listItemGroups = CollectionBean.getBeanList(groups);
				Collections.sort(listItemGroups, Responsible.getRespComparator());
		    }
		    
		  	IItem organization = null;
//			MULTIORGANIZACION EN UNA MISMA BBDD		  	
// Comprobar si la multiorganización está activada
//			String multiOrganization = ISPACConfiguration.getInstance().get(ISPACConfiguration.MULTIORGANIZATION);
//			if ((multiOrganization != null) &&
//				(multiOrganization.toUpperCase().equals("YES"))) {
//				
//				// Organismo de conexión (cuando hay varios organismos tramitando)
//				organization = (IItem) request.getSession().getAttribute("organization");
//			}
			
	  		if (organization != null){
	  			request.setAttribute("groups", filterGroups(listItemGroups, organization.getString("CODIGO")));
		  	}
		  	else {
		  		request.setAttribute("groups", listItemGroups);
		  	}
		}
		else {
			String uid = request.getParameter("uid");
			Responsible resp = (Responsible) respAPI.getResp(uid);
			String name = resp.getName();
			String dnformatted = name.replaceAll(",", ", ");
			request.setAttribute("group", dnformatted);

			Collection users = resp.getUsers();
			CustomItemCollection icUsers = new CustomItemCollection (users);
			List usersList = CollectionBean.getBeanList(icUsers);
			Collections.sort(usersList, Responsible.getRespComparator());
			request.setAttribute("users", usersList);
		}

	  	// FORMATEADOR
		//FormatterManager.storeBFDelegateGroupAction(request, getPath());
		CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
		request.setAttribute("Formatter", factory.getFormatter(getISPACPath("/digester/groupformatter.xml")));

		return mapping.findForward("success");
	}

	/**
	 * @param entitiesAPI Api de acceso a entidades
	 * @param pcdId Identificador de procedimiento
	 * @return un List con los grupos asociados al procedimiento
	 * @throws ISPACException
	 */
	private List getGroupListFromCt(IEntitiesAPI entitiesAPI, int pcdId, IItemCollection groups) throws ISPACException{
		//Obtenemos los grupos del catalogo de procedimientos
	    IItem item = entitiesAPI.getEntity(SpacEntities.SPAC_CT_PROCEDIMIENTOS, pcdId);
	    //Obtenemos el XML que almacena los grupos asignados al procedimiento
	    String xmlGroups = item.getString("GRUPOS_DELEGACION");
	    //Si no hay grupos asociados al procedimiento, retornamos null
	    if ((xmlGroups == null) || (xmlGroups.trim().length() == 0))
	        return null;
	    XmlFacade xmlFacade = new XmlFacade(xmlGroups);
	    //Obtenemos la lista uid's de los grupos
	    List listGroupsUids = xmlFacade.getList("/grupos/grupo/uid");
	    Iterator itUids = listGroupsUids.iterator();
	    List listItemGroups = new LinkedList();
	    //Iteramos sobre la lista de uids para crear un ItemBean con 
	    //el nombre y uid de cada grupo e introducirlo en la lista a retornar 
	    while(itUids.hasNext()){
	        ItemBean group = getGroup(groups, ""+itUids.next());
	        if (group != null)
	            //Añadimos el grupo como ItemBean a la lista
	            listItemGroups.add(group);
	    }
	    //Ordenamos los grupos por el nombre
	    Collections.sort(listItemGroups, new GroupComparator() );
	    return listItemGroups;
	}
	
	/**
	 * @param groups Colección con todos los grupos
	 * @param uid Identificador único de un grupo
	 * @return el Nombre de el grupo cuyo identificador único es <code>uid</code>
	 * @throws ISPACException
	 */
	private ItemBean getGroup(IItemCollection groups, String uid) throws ISPACException{
		Map map = CollectionBean.getBeanMap(groups, "UID");
		return (ItemBean)map.get(uid);
	}
	
	private List filterGroups(List ltGroups, String codeOrg) throws ISPACException {
		
		if ((ltGroups != null) &&
			(!ltGroups.isEmpty())) {
			
			List ltFilterGroups = new ArrayList();
			
			Iterator it = ltGroups.iterator();
			while (it.hasNext()) {
				
				ItemBean group = (ItemBean) it.next();
				String name = group.getString("NAME");
				if (name.toUpperCase().startsWith(codeOrg)) {
					
					ltFilterGroups.add(group);
				}
			}
			
			return ltFilterGroups;
		}
		
		return ltGroups;
	}
	
}
