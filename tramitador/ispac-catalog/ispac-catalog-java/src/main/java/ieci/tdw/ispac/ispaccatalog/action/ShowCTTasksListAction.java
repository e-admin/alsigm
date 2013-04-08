package ieci.tdw.ispac.ispaccatalog.action;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class ShowCTTasksListAction extends BaseAction {

 	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
 		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_INV_TASKS_READ,
				ISecurityAPI.FUNC_INV_TASKS_EDIT });
 		
 		List listaTramites = getCTTaskList(request, session);
 		for(Iterator iter = listaTramites.iterator(); iter.hasNext();){
 			ItemBean item = (ItemBean) iter.next(); 			
 			if( item.getItem().get("ID_SUBPROCESO") != null)
 				item.setProperty("SUBPROCESO_ASOCIADO", new Boolean(true));
 			else
 				item.setProperty("SUBPROCESO_ASOCIADO", new Boolean(false));
 		}
 		
        // Establecer la lista de trámites
        request.setAttribute("CTTaskList", listaTramites);

   	 	// Establecer el formateador
		setFormatter(request, "CTTaskListFormatter", 
				"/formatters/cttaskslistformatter.xml");

   	 	return mapping.findForward("success");
	}
 	
 	protected List getCTTaskList(HttpServletRequest request, SessionAPI session) 
 			throws ISPACException {

 		// API del catálogo
		ICatalogAPI catalogAPI= session.getAPI().getCatalogAPI();

		// Criterio de búsqueda
		String criterio = request.getParameter("property(criterio)");
		
		StringBuffer sql = new StringBuffer();
		if (StringUtils.isNotBlank(criterio)) {
			sql.append("WHERE NOMBRE LIKE '%")
				.append(DBUtil.replaceQuotes(criterio))
				.append("%'");
		}
		sql.append(" ORDER BY NOMBRE");
		
        IItemCollection itemcol = catalogAPI.queryCTEntities(
        		ICatalogAPI.ENTITY_CT_TASK, sql.toString());
        return CollectionBean.getBeanList(itemcol);
 	}
}