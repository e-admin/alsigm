package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.search.vo.SearchResultVO;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacmgr.common.constants.ActionsConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SelectSubstituteAction extends SelectValidationTableAction {
	
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
		
		// Entidad de sustituto
		String entityName = request.getParameter("entity");

		// Valor de sustituto
		String value = request.getParameter("value");
		if (StringUtils.isNotBlank(value)) {
		
			sqlQuery = composeSqlQuery(request,session, ICatalogAPI.VALOR_FIELD_NAME.toUpperCase() + " = '" + value + "'");

			collection = entitiesAPI.queryEntities(entityName, sqlQuery);
			if (collection.next()) {
			
				IItem substitute = collection.value();
                
				// Si se encuentra un solo registro con el valor especificado
				// se establece directamente
				if (!collection.next()) {

					// Salva en la petición el bean del sustituto
					request.setAttribute("Substitute", new ItemBean(substitute));
					
					// Comprobar si se ha especificado un action que establece el sustituto
					String setAction = request.getParameter("setAction");
					if (StringUtils.isNotBlank(setAction)) {
						
			            return new ActionForward("setSubstitute", setAction + "?" + request.getQueryString(), true);
					}

					return mapping.findForward("setSubstitute");
				}
				else {
					// Se muestran todos los registros encontrados
					collection.reset();
				}
			}
			else {
				// El valor buscado no existe
				// luego se cargan todos los registros para seleccionar uno
				collection = null;
			}
		}
		
		//Carga del valor de un campo validado contra una tabla que ejerece el rol de padre en una estructura jerarquica cuando se esteblece el valor
		//el campo validado contra la tabla que ejerce el rol de hija
		if (StringUtils.isNotEmpty(request.getParameter(ActionsConstants.HIERARCHICAL_DESCENDANTID))){
			sqlQuery = composeSqlQuery(request, session);
			collection = entitiesAPI.queryEntities(entityName, sqlQuery);
			if (collection.next()) {
				IItem substitute = collection.value();
				// Si se encuentra un solo registro con el valor especificado se establece directamente
				if (!collection.next()) {
					// Salva en la petición el bean del sustituto
					request.setAttribute("Substitute", new ItemBean(substitute));
					// Comprobar si se ha especificado un action que establece el sustituto
					String setAction = request.getParameter("setAction");
					if (StringUtils.isNotBlank(setAction)) {
			            return new ActionForward("setSubstitute", setAction + "?" + request.getQueryString(), true);
					}
					return mapping.findForward("setSubstitute");
				}
			}
		}
		
		
		if (collection == null) {
			
			// Mostrar el formulario de búsqueda
			if (showSearchForm(request)) {
				
				// Obtener los parámetros de búsqueda
				processSearchParameters(request, form);
				
				// Componer la consulta con los parámetros de búsqueda
				sqlQuery = composeSearchSqlQuery(request, session, ICatalogAPI.SUSTITUTO_FIELD_NAME.toUpperCase());
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
		}
		
		// Generar la lista para la vista
		generateViewList(request, collection, maxResultados, totalResultados, "SubstituteList");
		
		// Obtener formateador
		processFormatter(request, "/digester/substituteformatter.xml");
				
		return mapping.findForward("success");
	}

}