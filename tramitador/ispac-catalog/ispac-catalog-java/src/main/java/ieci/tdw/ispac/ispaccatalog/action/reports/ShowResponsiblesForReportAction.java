package ieci.tdw.ispac.ispaccatalog.action.reports;

import ieci.tdw.ispac.api.ICatalogAPI;
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


public class ShowResponsiblesForReportAction extends BaseAction {
	
	public ActionForward executeAction(ActionMapping mapping, 
									   ActionForm form,
									   HttpServletRequest request, 
									   HttpServletResponse response,
									   SessionAPI session) throws Exception {

	// Comprobar si el usuario tiene asignadas las funciones adecuadas
	FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
		ISecurityAPI.FUNC_COMP_REPORTS_READ,
		ISecurityAPI.FUNC_COMP_REPORTS_EDIT });

  	IResponsible directoryResp = null;
	IInvesflowAPI invesflowapi= session.getAPI();
	IRespManagerAPI respAPI = invesflowapi.getRespManagerAPI();
	ICatalogAPI catalogAPI = invesflowapi.getCatalogAPI();
		
  	// Extraccion de parametros: uid del supervisor, tipo de supervision
	// y tipo de vista (organizacion o grupos)
  	String id = request.getParameter("id");
  	request.setAttribute ("id", id);

  	
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

//  	// si llega, llega desde la accion addsuperviseds
//  	String alreadySupervised = request.getParameter("alreadysupervised");
//  	if (alreadySupervised != null)
//  	{
//  		IResponsible asResp = respAPI.getResp(alreadySupervised);
//  		String errorKey = "";
//  		if (iKindOfSuperv == ISecurityAPI.SUPERV_FOLLOWEDMODE)
//  			errorKey = "error.addsuperviseds.alreadysupervised.total.type";
//  		else
//  			errorKey = "error.addsuperviseds.alreadysupervised.monitoring.type";
//  		
//  		ActionMessages errors = new ActionMessages();
//		errors.add("user",new ActionMessage(errorKey, asResp.getRespName()));
//		saveErrors(request,errors);
//  	}

  	// Calculamos responsables ya seleccioandos
  	String titleKey = null;
  	
  	
    //Obtenemos los ya seleccionados
  	IItemCollection responsibles=catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_CT_INFORMES_ORG, "WHERE ID_INFORME = " + id);
  	
  	
  	request.setAttribute("sustitutosSeleccionadosTitleKey", "catalog.searchfrm.title.supervised");
  	request.setAttribute("titleKey", titleKey);
  	request.setAttribute("buttonActionKey", "forms.button.supervise");
  	

    CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
    BeanFormatter formatter = factory.getFormatter(getISPACPath("/formatters/supervision/supervisionformatter.xml"));
    request.setAttribute("SupervisionFormatter", formatter);

  	// Enviamos responsables
  	List lResponsibles = CollectionBean.getBeanList(responsibles);
//  	Collections.sort(lResponsibles, Responsible.getRespComparator());
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

	  	// No mostrar los ya seleccionados
	  	eliminateResponsiblesAlreadySelected (usersList, lResponsibles);
  		eliminateResponsiblesAlreadySelected (orgUnitsList, lResponsibles);
	  	
	  	request.setAttribute("supervUsers", usersList);
	  	request.setAttribute("supervOrgunits", orgUnitsList);
	  	request.setAttribute("titleNavigator", "catalog.searchfrm.titleNavigator.organization");
	  	
  	}
  	else if (supervDirectoryView.equals("groups"))
  	{
    	if (dirUid == null) {
    		
    		// Todos los grupos
      		IItemCollection groups = respAPI.getAllGroups();
      		List groupsList = CollectionBean.getBeanList(groups);
      		Collections.sort(groupsList, Responsible.getRespComparator());
      		// No mostrar los ya seleccionados
      		eliminateResponsiblesAlreadySelected(groupsList, lResponsibles);
      		request.setAttribute("supervGroups", groupsList);
    	}
    	else {
    		// Usuarios de un grupo
    		IResponsible group = respAPI.getResp(dirUid);
    		IItemCollection users = group.getRespUsers();
    		List usersList = CollectionBean.getBeanList(users);
    		Collections.sort(usersList, Responsible.getRespComparator());
    		// No mostrar los ya seleccionados
    		eliminateResponsiblesAlreadySelected(usersList, usersList);
    	  	request.setAttribute("supervUsers", usersList);
        	request.setAttribute("groupName", group.getName());
    	}
    	request.setAttribute("titleNavigator", "catalog.searchfrm.titleNavigator.groups");
  	}

  	return mapping.findForward("success");
  }

	private void eliminateResponsiblesAlreadySelected (List list, List filter)
	throws ISPACException
	{

	ListIterator filterIter = filter.listIterator();
		while (filterIter.hasNext())
		{
			//IResponsible filterItem = (IResponsible) filterIter.next();
			ItemBean filterItem = (ItemBean) filterIter.next();
			String filterUID = filterItem.getString("UID_USR");
			ListIterator listIter = list.listIterator();
			while (listIter.hasNext())
			{
				//IResponsible item = (IResponsible) listIter.next();
				ItemBean item = (ItemBean) listIter.next();
				if (item.getString("UID").equals(filterUID))
				{
					list.remove(item);
					break;
				}
			}
		}
	}
}
