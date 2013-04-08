package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.bean.BeanFormatter;
import ieci.tdw.ispac.ispaclib.bean.CacheFormatterFactory;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacmgr.action.form.EntityForm;
import ieci.tdw.ispac.ispacmgr.common.constants.ActionsConstants;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public abstract class SelectValidationTableAction extends BaseAction {

	protected int MAX_TBL_SEARCH_VALUES_DEFAULT = 50;

	public abstract ActionForward executeAction(ActionMapping mapping, 
												ActionForm form,
												HttpServletRequest request,
												HttpServletResponse response,
												SessionAPI session) throws Exception;
	
	protected String composeSqlQuery(HttpServletRequest request,SessionAPI session,String conditions) throws ISPACException {
		String sqlQuery = null;

		//Para mantener compatibilidad con los proyectos en los que se hayan hecho uso de tablas jerarquicas y que no quieran realizar las actualizaciones
		//se tratara por separado cuando reciba el id (versiones anteriores) o el nombre (version actualizada) de la jerarquia
		
		if (StringUtils.isEmpty(request.getParameter(ActionsConstants.HIERARCHICAL_NAME)) && StringUtils.isEmpty(request.getParameter(ActionsConstants.HIERARCHICAL_ID))) {
			sqlQuery = "WHERE ";
		}
		else {

			//Si se recibe como parametro el nombre de la jerarquia (version 5.7 y superiores)...
			if (StringUtils.isNotEmpty(request.getParameter(ActionsConstants.HIERARCHICAL_NAME))){
				//int hierarchyId = Integer.parseInt(request.getParameter(ActionsConstants.HIERARCHICAL_ID));
				String hierarchicalName = request.getParameter(ActionsConstants.HIERARCHICAL_NAME);
				ICatalogAPI catalogAPI = session.getAPI().getCatalogAPI();
				IItemCollection itemCol = catalogAPI.getHierarchicalTables("WHERE NOMBRE = '"+hierarchicalName+"'");
				if (!itemCol.next()){
					throw new ISPACException("Tabla jerarquica con nombre '"+hierarchicalName+"' no encontrada en SPAC_CT_JERARQUIAS");
				}
				int hierarchicalId = itemCol.value().getKeyInt(); 
				
				if (StringUtils.isNotEmpty(request.getParameter(ActionsConstants.HIERARCHICAL_DESCENDANTID))){
					int descendantId = Integer.parseInt(request.getParameter(ActionsConstants.HIERARCHICAL_DESCENDANTID));
					sqlQuery = "WHERE ID IN (SELECT ID_PADRE FROM " + ICatalogAPI.HIERARCHICAL_TABLE_NAME + hierarchicalId  + " WHERE ID_HIJA = " + descendantId + ")";
					return sqlQuery;
				}else{
					int filterId = Integer.parseInt(request.getParameter(ActionsConstants.FILTER_ID));
					sqlQuery = "WHERE ID IN (SELECT ID_HIJA FROM " + ICatalogAPI.HIERARCHICAL_TABLE_NAME + hierarchicalId + " WHERE ID_PADRE = " + filterId + ") ";
					//Comprobacion para mostrar los valores de la entidad hija q no se han asociado con ningun valor de la entidad padre
					String loadNotAssociated = request.getParameter(ActionsConstants.LOAD_NOT_ASSOCIATED);
					if (StringUtils.isEmpty(loadNotAssociated) || StringUtils.equalsIgnoreCase(loadNotAssociated, "true")){
						sqlQuery += " OR ID NOT IN (SELECT ID_HIJA FROM " + ICatalogAPI.HIERARCHICAL_TABLE_NAME + hierarchicalId + ") AND ";
					}else{
						sqlQuery += " AND ";
					}
				}				
			}else 
				if (StringUtils.isNotEmpty(request.getParameter(ActionsConstants.HIERARCHICAL_ID))){
					int hierarchyId = Integer.parseInt(request.getParameter(ActionsConstants.HIERARCHICAL_ID));
					int filterId = Integer.parseInt(request.getParameter(ActionsConstants.FILTER_ID));
					sqlQuery = "WHERE ID IN (SELECT ID_HIJA FROM SPAC_CT_JERARQUIA_" + StringUtils.leftPad(""+hierarchyId, 3, '0') + " WHERE ID_PADRE = " + filterId + ") ";
					
					//Comprobacion para mostrar los valores de la entidad hija q no se han asociado con ningun valor de la entidad padre
					String loadNotAssociated = request.getParameter(ActionsConstants.LOAD_NOT_ASSOCIATED);
					if (StringUtils.isEmpty(loadNotAssociated) || StringUtils.equalsIgnoreCase(loadNotAssociated, "true")){
						sqlQuery += " OR ID NOT IN (SELECT ID_HIJA FROM SPAC_CT_JERARQUIA_" + StringUtils.leftPad(""+hierarchyId, 3, '0') + ") AND ";
					}else{
						sqlQuery += " AND ";
					}					
			}			
			
			

			
			
			
		}
		if (StringUtils.isNotEmpty(conditions)){
			sqlQuery += conditions +  " AND ";
		}
		sqlQuery+= " VIGENTE = 1 ";
		return sqlQuery;
	}
	
	protected String composeSqlQuery(HttpServletRequest request,SessionAPI session) throws ISPACException {

		return this.composeSqlQuery(request, session,null);
	}
			
			
	protected boolean showSearchForm(HttpServletRequest request) {
		
		String showSearch = request.getParameter("showSearch");
		if ((StringUtils.isBlank(showSearch)) ||
			(showSearch.equals("true"))) {
		
			return true;
		}
		
		return false;
	}
	
	protected void processSearchParameters(HttpServletRequest request,
										   ActionForm form) throws ISPACException {
		
		EntityForm defaultForm = (EntityForm) form;
		
		String operadorS = defaultForm.getProperty("operador");
		String criterio = defaultForm.getProperty("criterio");
		
		if ((StringUtils.isEmpty(criterio)) &&
			(request.getParameter("criterio") != null) && 
			(request.getParameter("buscar") == null)) {
			
			operadorS = request.getParameter("operador");
			criterio = request.getParameter("criterio");
		}
		else if ((StringUtils.isEmpty(criterio)) &&
				 (request.getParameter("buscar") != null)) {
			
			criterio = "";
		}
		
		if (StringUtils.isBlank(operadorS)) {
			
			operadorS = "0";
		}
		
		// Guardar los parámetros de búsqueda
		defaultForm.setProperty("operador", operadorS);
		request.setAttribute("operador", operadorS);
		defaultForm.setProperty("criterio", criterio);
		request.setAttribute("criterio", criterio);
	}
	

	
	protected void generateViewList(HttpServletRequest request, 
									IItemCollection collection,
									int maxResultados,
									int totalResultados,
									String attributeName) throws ISPACException {
		
		// Generar la lista para la vista
		List lista = CollectionBean.getBeanList(collection);
		
		if (maxResultados > 0 && totalResultados>0) {
		
			if (maxResultados < totalResultados) {
				
				request.setAttribute("maxResultados", String.valueOf(maxResultados)); 
				request.setAttribute("numTotalRegistros",String.valueOf(totalResultados));
			}
			
			
		}
		
		
		//A todos los elementos se añade un campo ficticio orden_to_show para que se muestre el orden de manera incremental sin ningun salto
		//intermedio.
		
		  for(int i=0; i<lista.size(); i++)
	        {
	        	ItemBean item=(ItemBean) lista.get(i);
	        	item.setProperty("ORDEN_TO_SHOW", (i+1)+"");
	        	lista.set(i, item);
	        	
	        }
		request.setAttribute(attributeName, lista);
	}
	
	protected void processFormatter(HttpServletRequest request, 
									String defaultXmlFormatter) throws ISPACException {
		
		BeanFormatter formatter = null;
		CacheFormatterFactory factory = CacheFormatterFactory.getInstance();

		String sFormatter = request.getParameter("xmlFormatter");
		if (StringUtils.isNotBlank(sFormatter)) {
			
			formatter = factory.getFormatter(getISPACPath(sFormatter));
		}
		else {
			formatter = factory.getFormatter(getISPACPath(defaultXmlFormatter));
		}
		
		request.setAttribute("Formatter", formatter);
	}
	
	protected String composeSearchSqlQuery(HttpServletRequest request, SessionAPI session, String property) throws ISPACException {
		
		// Se controla en la query la vigencia del campo (VIGENTE = 1)
		String sqlQuery = null;
		
		String criterio = (String) request.getAttribute("criterio");
				 
		if (StringUtils.isNotEmpty(criterio)) {
			 
			int operador = Integer.parseInt((String) request.getAttribute("operador"));
			
			switch (operador) {
			
				case 1: 
					sqlQuery = composeSqlQuery(request,session,DBUtil.getToUpperSQL(property)+" LIKE '%" + DBUtil.replaceQuotes(criterio).toUpperCase() + "%'");
					break;
					
				case 2: 
					sqlQuery = composeSqlQuery(request,session,DBUtil.getToUpperSQL(property)+" LIKE '" + DBUtil.replaceQuotes(criterio).toUpperCase() + "%'");
					break;
					
				case 3: 
					sqlQuery = composeSqlQuery(request,session,DBUtil.getToUpperSQL(property)+" LIKE '%" + DBUtil.replaceQuotes(criterio).toUpperCase() + "'");
					break;
					
				default: 
					sqlQuery = composeSqlQuery(request, session, null);
			}
		}
		else {
			sqlQuery = composeSqlQuery(request, session, null);
		}
		
		return sqlQuery;
	}
	
}