package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IRespManagerAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.util.CustomItemCollection;
import ieci.tdw.ispac.ispaclib.bean.BeanFormatter;
import ieci.tdw.ispac.ispaclib.bean.CacheFormatterFactory;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.resp.Responsible;
import ieci.tdw.ispac.ispacmgr.common.constants.ActionsConstants;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SelectOrgAction extends BaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {
	  	
		IInvesflowAPI invesFlowAPI = session.getAPI();
	  	IRespManagerAPI respAPI = invesFlowAPI.getRespManagerAPI();
	  	
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

	  	//Se obtienen los organos descendientes
	  	Collection orgUnits = resp.getOrgUnits();
	  	CustomItemCollection icOrgUnits = new CustomItemCollection (orgUnits);
	  	List orgUnitsList = CollectionBean.getBeanList(icOrgUnits);
	  	request.setAttribute(ActionsConstants.ORG_UNITS, orgUnitsList);
	  	
	  	//Se obtienen el antecesor
	  	if (resp.getOrgUnit() != null) {
	  		
	  		if ((organization != null) &&
	  			(resp.getUID().equals(organization.getString("UID_ORGANISMO")))) {
	  			
	  			request.removeAttribute(ActionsConstants.PARENT_UID);
	  		}
	  		else {
	  			request.setAttribute(ActionsConstants.PARENT_UID,resp.getOrgUnit().getUID());
	  		}
	  	}
	  	
	  	////////////////////////
	  	//FORMATEADOR
	  	CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
		BeanFormatter formatter = factory.getFormatter(getISPACPath("/digester/selectorgformatter.xml"));
		request.setAttribute("Formatter", formatter);

		return mapping.findForward("success");
	}

}
