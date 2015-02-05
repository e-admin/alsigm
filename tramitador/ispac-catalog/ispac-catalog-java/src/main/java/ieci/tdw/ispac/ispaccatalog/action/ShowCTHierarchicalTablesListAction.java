package ieci.tdw.ispac.ispaccatalog.action;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ShowCTHierarchicalTablesListAction extends BaseAction {

 	public ActionForward executeAction(ActionMapping mapping,
 									   ActionForm form,
 									   HttpServletRequest request,
 									   HttpServletResponse response,
 									   SessionAPI session) throws Exception {

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
 		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_COMP_HIERARCHICAL_TABLES_READ,
				ISecurityAPI.FUNC_COMP_HIERARCHICAL_TABLES_EDIT });

		IInvesflowAPI invesFlowAPI = session.getAPI();
		ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();
		IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();
		
		IItemCollection itemcol = catalogAPI.getHierarchicalTables(null);
		request.setAttribute("CTHierarchicalTablesList",
				CollectionBean.getBeanList(itemcol));

		// Formateador para la lista de eventos
		if (FunctionHelper.userHasFunction(request, session.getClientContext(), ISecurityAPI.FUNC_COMP_HIERARCHICAL_TABLES_EDIT)) {
			setFormatter(request, "CTHierarchicalTablesListFormatter", "/formatters/cthierarchicaltableslistformatter.xml");
		} else {
			setFormatter(request, "CTHierarchicalTablesListFormatter", "/formatters/cthierarchicaltableslistreadonlyformatter.xml");
		}
   	 	
   	 	// Obtener las entidades para las descripciones en la vista
   	 	IItemCollection entitiesCollection = catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_CT_ENTITY, "where TIPO > 1");
   	 	List entitiesList = entitiesAPI.getEntitiesExtendedItemBean(entitiesCollection);
		
   	 	LinkedHashMap entitiesMap = new LinkedHashMap();
		Iterator it = entitiesList.iterator();
		while (it.hasNext()) {
			ItemBean obj = (ItemBean) it.next();
			entitiesMap.put(obj.getItem().getKeyInteger().toString(), obj);
		}
   	 	request.setAttribute("EntitiesMap", entitiesMap);

   	 	return mapping.findForward("success");
	}
 	
}