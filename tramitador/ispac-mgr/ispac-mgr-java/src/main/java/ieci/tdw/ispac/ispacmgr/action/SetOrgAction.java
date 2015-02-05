package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IRespManagerAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.Properties;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.item.GenericItem;
import ieci.tdw.ispac.ispaclib.resp.Responsible;

import java.sql.Types;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SetOrgAction extends BaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {
		IInvesflowAPI invesFlowAPI = session.getAPI();
	  	IRespManagerAPI respAPI = invesFlowAPI.getRespManagerAPI();

	  	Responsible resp = null;

	  	String uid = request.getParameter("uid");
	  	
	  	if (uid == null)
	  		throw new ISPACInfo("No se puede establecer Organo. Identificador no suministrado.");

	  	resp = (Responsible) respAPI.getResp(uid);
		ItemBean value = convert2ItemBean(resp);
		request.setAttribute("Value", value);
		
//		/*
//		 * Nombre de la variable de sesión que mantiene los parámetros
//		 * del tag de búsqueda.
//		 */
//		String parameters = request.getParameter("parameters");
//		// Obtiene los parámetros del tag de búsqueda y los salva en el request
//		String sParameters = session.getClientContext().getSsVariable( parameters);
//		if (sParameters != null)
//		{
//			request.setAttribute(parameters, ActionFrameTag.toMap( sParameters));
//		}
		
	  	return mapping.findForward("success");

	}

	
	private ItemBean convert2ItemBean(Responsible resp) throws ISPACException{
		Properties properties = new Properties();
		int i = 0;
		properties.add(new Property (i++, "UID", Types.VARCHAR));
		properties.add(new Property (i, "NAME", Types.VARCHAR));
		
		GenericItem item = new GenericItem(properties, "UID");
		
		item.set("UID", resp.getUID());
		item.set("NAME", resp.getName());
		
		return new ItemBean(item);
	}
}
