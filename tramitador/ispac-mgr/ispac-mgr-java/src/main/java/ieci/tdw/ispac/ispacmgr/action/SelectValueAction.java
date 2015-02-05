package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.search.vo.SearchResultVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SelectValueAction extends SelectValidationTableAction
{
	public ActionForward executeAction(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response,
			SessionAPI session) throws Exception {

		IInvesflowAPI invesFlowAPI = session.getAPI();
		IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();

		int maxResultados = 0;
		int totalResultados=0;
		String sqlQuery = null;
		IItemCollection collection = null;

		// Entidad de valor
		String entityName = request.getParameter("entity");

		// Mostrar el formulario de búsqueda
		if (showSearchForm(request)) {
			
			// Obtener los parámetros de búsqueda
			processSearchParameters(request, form);
			
			// Componer la consulta con los parámetros de búsqueda
			sqlQuery = composeSearchSqlQuery(request, session, ICatalogAPI.VALOR_FIELD_NAME.toUpperCase());
			
			SearchResultVO searchResultVO = entitiesAPI.getLimitedQueryEntities(entityName, sqlQuery, ICatalogAPI.ORDEN_FIELD_NAME.toUpperCase());
			collection=searchResultVO.getResultados();
			maxResultados=searchResultVO.getNumMaxRegistros();
			totalResultados=searchResultVO.getNumTotalRegistros();
			
			
		}
		else {
			// Componer la consulta
			sqlQuery = composeSqlQuery(request, session, null);
			
			collection = entitiesAPI.queryEntities(entityName, sqlQuery, ICatalogAPI.ORDEN_FIELD_NAME.toUpperCase(), 0);
		}
		
		// Generar la lista para la vista
		generateViewList(request, collection, maxResultados, totalResultados,"ValueList");

		// Obtener formateador
		processFormatter(request, "/digester/valueformatter.xml");

		return mapping.findForward("success");
	}
	
}