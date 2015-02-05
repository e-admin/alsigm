package ieci.tdw.ispac.ispaccatalog.action;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.context.ClientContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SetCTProcedureEntityAction extends BaseAction {

	public ActionForward executeAction( ActionMapping mapping,
										ActionForm form,
										HttpServletRequest request,
										HttpServletResponse response,
										SessionAPI session) throws Exception {
		
		ClientContext cct = session.getClientContext();
		
		IInvesflowAPI invesFlowAPI = session.getAPI();
		ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();
		IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();
		
//        String codetable=substform.getCodetable();
//        String codefield=substform.getCodefield();

//        /*
//         * Nombre de la variable de sesión que mantiene los parámetros
//         * del tag de búsqueda.
//         */
//        String parameters=substform.getParameters();
//
//        // Obtiene los parámetros del tag de búsqueda y los salva en el request
//		String sParameters = session.getClientContext().getSsVariable( parameters);
//		if (sParameters != null)
//		{
//			request.setAttribute(parameters, ActionFrameTag.toMap( sParameters));
//		}

		// Código del sustituto
		String selectcode = request.getParameter("selectcode");

		if (selectcode==null)
		    return mapping.findForward("success");
		
		IItem substitute = catalogAPI.getProcedureEntity(Integer.parseInt(selectcode));
		ItemBean itemBean = new ItemBean(substitute);
		
		String etiqueta = entitiesAPI.getEntityResourceValue(substitute.getInt("CTENTITY:ID"), cct.getAppLanguage(), substitute.getString("CTENTITY:NOMBRE"));
		itemBean.setProperty("ETIQUETA", etiqueta);

		// Salva en la petición el bean
		request.setAttribute("Substitute", itemBean);

		return mapping.findForward("success");
	}
	
}