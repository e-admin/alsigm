package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SetSubstituteAction extends BaseAction {

	public ActionForward executeAction(ActionMapping mapping,
									   ActionForm form,
									   HttpServletRequest request,
									   HttpServletResponse response,
									   SessionAPI session) throws Exception {
		
		// Entidad de sustituto
		String entityName = request.getParameter("entity");
		if(request.getParameter("formName")!=null){
			request.setAttribute("formName", request.getParameter("formName"));
		}
		
		// Código del sustituto
		String value = request.getParameter("value");

		IInvesflowAPI invesFlowAPI = session.getAPI();
		IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();

		String sQuery = "WHERE VALOR = '" + DBUtil.replaceQuotes(value) + "'";
        IItemCollection collection = entitiesAPI.queryEntities(entityName, sQuery);
        
		if (collection.next())
		{
			IItem substitute = collection.value();
			// Salva en la petición el bean del sustituto
			request.setAttribute("Substitute", new ItemBean(substitute));
		}
        	
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
	
}