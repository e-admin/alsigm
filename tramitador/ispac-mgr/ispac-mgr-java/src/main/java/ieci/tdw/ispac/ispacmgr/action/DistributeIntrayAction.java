package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IRespManagerAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.util.CustomItemCollection;
import ieci.tdw.ispac.ispaclib.bean.BeanFormatter;
import ieci.tdw.ispac.ispaclib.bean.CacheFormatterFactory;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.resp.Responsible;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class DistributeIntrayAction extends BaseAction {

	public ActionForward executeAction(
	ActionMapping mapping, 
	ActionForm form,
	HttpServletRequest request, 
	HttpServletResponse response,
	SessionAPI session) throws Exception {
		
	  	String type = request.getParameter("type");
	  	String uid = request.getParameter("uid");

		IInvesflowAPI invesFlowAPI = session.getAPI();
	  	IRespManagerAPI responsibleAPI = invesFlowAPI.getRespManagerAPI();

	  	if (type.equalsIgnoreCase("unit"))
	  	{
		  	Responsible responsible;
		  	
		  	if (uid == null)
		  		responsible = (Responsible) responsibleAPI.getRootResp();
		  	else
		  		responsible = (Responsible) responsibleAPI.getResp(uid);
	
		  	Collection users = responsible.getUsers();
		  	Collection units = responsible.getOrgUnits();
		  	
		  	IItemCollection ancestors = responsibleAPI.getAncestors(responsible.getUID());
		  	List ancestorsList = CollectionBean.getBeanList(ancestors);
		  	request.setAttribute("ancestors", ancestorsList);
	
		  	CustomItemCollection collection;
		  	List list;
		  	
		  	collection = new CustomItemCollection (users);
		  	list = CollectionBean.getBeanList(collection);
		  	Collections.sort(list, Responsible.getRespComparator());
		  	request.setAttribute("users", list);
		  	
		  	collection = new CustomItemCollection (units);
		  	list = CollectionBean.getBeanList(collection);
		  	Collections.sort(list, Responsible.getRespComparator());
		  	request.setAttribute("orgunits", list);
	
		  	CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
			BeanFormatter formatter = factory.getFormatter(getISPACPath("/digester/organizationformatter.xml"));
			request.setAttribute("Formatter", formatter);
	  	}
	  	else if (type.equalsIgnoreCase("group"))
	  	{
	  		
			if (uid == null)
			{
				IItemCollection collection = responsibleAPI.getAllGroups();
				List list = CollectionBean.getBeanList(collection);
				Collections.sort(list, Responsible.getRespComparator());
				request.setAttribute("groups", list);
			}
			else
			{
				Responsible responsible = (Responsible) responsibleAPI.getResp(uid);
				String name = responsible.getName();
				String dnformatted = name.replaceAll(",", ", ");
				request.setAttribute("group", dnformatted);

				Collection users = responsible.getUsers();
				CustomItemCollection collection = new CustomItemCollection (users);
				List list = CollectionBean.getBeanList(collection);
				Collections.sort(list, Responsible.getRespComparator());
				request.setAttribute("users", list);
			}

			CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
			BeanFormatter formatter = factory.getFormatter(getISPACPath("/digester/groupformatter.xml"));
			request.setAttribute("Formatter", formatter);
	  	}

		return mapping.findForward(type);
	}
}
