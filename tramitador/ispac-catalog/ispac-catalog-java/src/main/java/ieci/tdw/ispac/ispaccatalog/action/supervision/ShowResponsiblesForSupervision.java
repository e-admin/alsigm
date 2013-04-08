package ieci.tdw.ispac.ispaccatalog.action.supervision;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IRespManagerAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.IResponsible;
import ieci.tdw.ispac.ispaccatalog.action.BaseAction;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.bean.BeanFormatter;
import ieci.tdw.ispac.ispaclib.bean.CacheFormatterFactory;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.resp.Responsible;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;


public class ShowResponsiblesForSupervision extends BaseAction {
	
	public ActionForward executeAction(ActionMapping mapping, 
									   ActionForm form,
									   HttpServletRequest request, 
									   HttpServletResponse response,
									   SessionAPI session) throws Exception {

		// Comprobar si el usuario tiene asignadas las funciones adecuadas
 		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
 			ISecurityAPI.FUNC_PERM_READ,
 			ISecurityAPI.FUNC_PERM_EDIT });

	  	IResponsible directoryResp = null;
		IInvesflowAPI invesflowapi= session.getAPI();
		IRespManagerAPI respAPI = invesflowapi.getRespManagerAPI();
			
	  	String captionkey = request.getParameter("captionkey");
	  	request.setAttribute("captionkey", captionkey);
	
	  	// Extraccion de parametros: uid del supervisor, tipo de supervision
		// y tipo de vista (organizacion o grupos)
	  	String uid = request.getParameter("uid");
	  	request.setAttribute ("uid", uid);
	  	String kindOfSuperv = request.getParameter("kindOfSuperv");
	  	request.setAttribute ("kindOfSuperv", kindOfSuperv);
	  	int iKindOfSuperv = Integer.parseInt(kindOfSuperv);
	  	String supervDirectoryView = request.getParameter("supervDirView");
	  	String dirUid = request.getParameter ("dirUid");
	  	request.setAttribute ("dirUid", dirUid);
	  	
	  	// Parámetros para volver
	  	String view = request.getParameter("view");
	  	request.setAttribute("view", view);
	  	String uidGroup = request.getParameter("uidGroup");
	  	if(StringUtils.isNotEmpty(uidGroup)) {
	  		request.setAttribute("uidGroup", uidGroup);
	  	}
	
	  	// si llega, llega desde la accion addsuperviseds
	  	String alreadySupervised = request.getParameter("alreadysupervised");
	  	if (alreadySupervised != null)
	  	{
	  		IResponsible asResp = respAPI.getResp(alreadySupervised);
	  		String errorKey = "";
	  		if (iKindOfSuperv == ISecurityAPI.SUPERV_FOLLOWEDMODE)
	  			errorKey = "error.addsuperviseds.alreadysupervised.total.type";
	  		else
	  			errorKey = "error.addsuperviseds.alreadysupervised.monitoring.type";
	  		
	  		ActionMessages errors = new ActionMessages();
			errors.add("user",new ActionMessage(errorKey, asResp.getName()));
			saveErrors(request,errors);
	  	}
	
	  	// Calculamos responsables ya supervisados
	  	String titleKey = null;
	  	IItemCollection responsibles = null;
	
	  	if (iKindOfSuperv == ISecurityAPI.SUPERV_FOLLOWEDMODE) {
	  		
	  		titleKey = "supervision.monitoring.type.title";
	  		responsibles = respAPI.getFollowedModeSuperviseds(uid);
	  	}
	  	else if (iKindOfSuperv == ISecurityAPI.SUPERV_TOTALMODE) {
	  		
	  		titleKey = "supervision.total.type.title";
	  		responsibles = respAPI.getTotalModeSuperviseds(uid);
	  	}
	  	
	  	request.setAttribute("sustitutosSeleccionadosTitleKey", "catalog.supervision.title.supervised");
	  	request.setAttribute("titleKey", titleKey);
	  	request.setAttribute("buttonActionKey", "forms.button.supervise");
	  	
	
	    CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
	    BeanFormatter formatter = factory.getFormatter(getISPACPath("/formatters/supervision/supervisionformatter.xml"));
	    request.setAttribute("SupervisionFormatter", formatter);
	
	  	// Enviamos responsables
	  	List lResponsibles = CollectionBean.getBeanList(responsibles);
	  	Collections.sort(lResponsibles, Responsible.getRespComparator());
	  	request.setAttribute ("responsibles", lResponsibles);
	  	
	  	// Añadmimos beanformatter para los responsables
	    formatter = factory.getFormatter(getISPACPath("/formatters/supervision/organizationformatter.xml"));
	    request.setAttribute("OrganizationFormatter", formatter);
	
	
	  	// Enviamos directorios: organizacion o grupos:la vista x defecto es
	  	// la de organizacion
	  	if (supervDirectoryView == null || supervDirectoryView.equals("organization"))
	  	{
	  		// Calculamos responsable del directorio
	    	if (dirUid == null)
	    		//directoryResp = respAPI.getRespFromRoot();  	// ESTO ESTABA ANTES Y LO CAMBIE POR LA LINEA DE ABAJO
	    		directoryResp = respAPI.getRootResp();
	    	else
	    		directoryResp = respAPI.getResp(dirUid);
	  		// Ancestros
		  	IItemCollection ancestors = respAPI.getAncestors(directoryResp.getUID());
		  	List ancestorsList = CollectionBean.getBeanList(ancestors);
		  	request.setAttribute("supervAncestors", ancestorsList);
	
		  	// Usuarios y departamentos
		  	IItemCollection users = directoryResp.getRespUsers();
		  	IItemCollection orgUnits = directoryResp.getRespOrgUnits();
	
		  	List usersList = CollectionBean.getBeanList(users);
		  	List orgUnitsList = CollectionBean.getBeanList(orgUnits);
		  	Collections.sort(usersList, Responsible.getRespComparator());
		  	Collections.sort(orgUnitsList, Responsible.getRespComparator());
	
		  	// No seleccionables los ya seleccionados
		  	if (iKindOfSuperv == ISecurityAPI.SUPERV_FOLLOWEDMODE)
		  	{
		  		IItemCollection fm_responsibles = respAPI.getFollowedModeSuperviseds(uid);
		  		List lfm_responsibles = CollectionBean.getBeanList(fm_responsibles);
		  		//Collections.sort(lfm_responsibles, Responsible.getRespComparator());
		  		
		  		setResponsiblesAlreadySelected(usersList, lfm_responsibles);
		  		setResponsiblesAlreadySelected(orgUnitsList, lfm_responsibles);
		  	}
		  	else if (iKindOfSuperv == ISecurityAPI.SUPERV_TOTALMODE)
		  	{
			  	IItemCollection tm_responsibles = respAPI.getTotalModeSuperviseds(uid);
			  	List ltm_responsibles = CollectionBean.getBeanList(tm_responsibles);
			  	//Collections.sort(ltm_responsibles, Responsible.getRespComparator());
			  	
			  	setResponsiblesAlreadySelected(usersList, ltm_responsibles);
			  	setResponsiblesAlreadySelected(orgUnitsList, ltm_responsibles);
		  	}

		  	// Eliminar de la lista de usuarios el supervisor
		  	eliminateSupervisor(usersList, uid);
		  	
		  	// No seleccionable el departamento
		  	setResponsibleAlreadySelected(orgUnitsList, uid);
	
		  	request.setAttribute("supervUsers", usersList);
		  	request.setAttribute("supervOrgunits", orgUnitsList);
		  	request.setAttribute("titleNavigator", "catalog.supervision.titleNavigator.organization");
	  	}
	  	else if (supervDirectoryView.equals("groups"))
	  	{
	    	if (dirUid == null) {
	    		
	    		// Todos los grupos
	      		IItemCollection groups = respAPI.getAllGroups();
	      		List groupsList = CollectionBean.getBeanList(groups);
	      		Collections.sort(groupsList, Responsible.getRespComparator());
	      		
	      		// Eliminar de la lista de grupos el supervisor
	      		eliminateSupervisor(groupsList, uid);
	      		
	      		// No seleccionables los ya seleccionados
	      		setResponsiblesAlreadySelected(groupsList, lResponsibles);
	      		
	      		request.setAttribute("supervGroups", groupsList);
	    	}
	    	else {
	    		// Usuarios de un grupo
	    		IResponsible group = respAPI.getResp(dirUid);
	    		IItemCollection users = group.getRespUsers();
	    		List usersList = CollectionBean.getBeanList(users);
	    		Collections.sort(usersList, Responsible.getRespComparator());
	    	
	    	  	// No seleccionables los ya seleccionados
	    	  	if (iKindOfSuperv == ISecurityAPI.SUPERV_FOLLOWEDMODE)
	    	  	{
	    	  		IItemCollection fm_responsibles = respAPI.getFollowedModeSuperviseds(uid);
	    	  		List lfm_responsibles = CollectionBean.getBeanList(fm_responsibles);
	    	  		//Collections.sort(lfm_responsibles, Responsible.getRespComparator());
	    	  		
	    	  		setResponsiblesAlreadySelected(usersList, lfm_responsibles);
	    	  	}
	    	  	else if (iKindOfSuperv == ISecurityAPI.SUPERV_TOTALMODE)
	    	  	{
	    		  	IItemCollection tm_responsibles = respAPI.getTotalModeSuperviseds(uid);
	    		  	List ltm_responsibles = CollectionBean.getBeanList(tm_responsibles);
	    		  	//Collections.sort(ltm_responsibles, Responsible.getRespComparator());
	    		  	
	    		  	setResponsiblesAlreadySelected(usersList, ltm_responsibles);
	    	  	}
	    		
	    	  	// Eliminar de la lista el supervisor
	    	  	eliminateSupervisor(usersList, uid);
	    	  	
	    	  	request.setAttribute("supervUsers", usersList);
	        	request.setAttribute("groupName", group.getName());
	    	}
	    	
	    	request.setAttribute("titleNavigator", "catalog.supervision.titleNavigator.groups");
	  	}
	
	  	return mapping.findForward("success");
	}

	private void setResponsiblesAlreadySelected(List list, 
												List filter) throws ISPACException {

		ListIterator filterIter = filter.listIterator();
		
		while (filterIter.hasNext())
		{
			ItemBean filterItem = (ItemBean) filterIter.next();
			String filterUID = filterItem.getString("UID");
			
			setResponsibleAlreadySelected(list, filterUID);
		}
	}
	
	private void setResponsibleAlreadySelected(List list, 
											   String filterUID) throws ISPACException {

		ListIterator listIter = list.listIterator();
		while (listIter.hasNext())
		{
			ItemBean item = (ItemBean) listIter.next();
			if (item.getString("UID").equals(filterUID))
			{
				//list.remove(item);
				item.setProperty("SELECTED", Boolean.TRUE);
				break;
			}
		}
	}
	
	private void eliminateSupervisor(List list, 
									 String uid) throws ISPACException {

		ListIterator iter = list.listIterator();
		while (iter.hasNext())
		{
			ItemBean item = (ItemBean) iter.next();
			if (item.getString("UID").equals(uid))
			{
				list.remove(item);
				break;
			}			
		}
	}	
	
}