package ieci.tdw.ispac.ispaccatalog.action.pcdflow;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IProcedureAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaccatalog.action.BaseAction;
import ieci.tdw.ispac.ispaccatalog.action.form.SelectObjForm;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaccatalog.helpers.NodeNameHelper;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action para mostrar la lista de nodos posibles para crear un nuevo flujo
 * en el procedimiento.
 *
 */
public class SelectPcdFlowAction extends BaseAction {

	public ActionForward executeAction(ActionMapping mapping, 
									   ActionForm form,
									   HttpServletRequest request, 
									   HttpServletResponse response,
									   SessionAPI session) throws Exception {
		
 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_INV_PROCEDURES_EDIT });

		ClientContext cct = session.getClientContext();

		SelectObjForm selectForm = (SelectObjForm) form;
		
		// Información del nodo actual
		int pcdId = Integer.parseInt(request.getParameter("pcdId"));
		int nodeId = Integer.parseInt(request.getParameter("nodeId"));
		String flowType = request.getParameter("flowType");

		IInvesflowAPI invesFlowAPI = session.getAPI();
		IProcedureAPI procedureAPI= invesFlowAPI.getProcedureAPI();

		IItemCollection nodes = null;
		String captionKey = null;

		// Obtener los nodos no relacionados
		if ("IN".equals(flowType)) {
			captionKey = "catalog.choose.inputflow";
			nodes = procedureAPI.getAvailableInputFlowNodes(pcdId, nodeId);			
		} else if ("OUT".equals(flowType)) {
			captionKey = "catalog.choose.outputflow";
			nodes = procedureAPI.getAvailableOutputFlowNodes(pcdId, nodeId);
		}

	    if (StringUtils.isBlank(captionKey)) {
	        captionKey = "catalog.selectobj.caption.default";
	    }

		// Componer los nombres de los nodos de sincronización
		NodeNameHelper.fillNodeNames(cct.getAppLanguage(), nodes);

		request.setAttribute("CaptionKey", captionKey);
        request.setAttribute("ItemList", CollectionBean.getBeanList(nodes));
        request.setAttribute("NO_SEARCH", Boolean.TRUE);
        
        selectForm.setQuerystring(request.getQueryString());

        // Establece el formateador
        setFormatter(request, "Formatter", 
        		"/formatters/pcdflows/choosepcdflowsformatter.xml");

        return mapping.findForward("success");
	}
}