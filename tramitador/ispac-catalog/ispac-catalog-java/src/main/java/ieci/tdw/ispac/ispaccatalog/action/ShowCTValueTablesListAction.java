package ieci.tdw.ispac.ispaccatalog.action;

import ieci.tdw.ispac.api.EntityType;
import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.entity.EntityDAO;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class ShowCTValueTablesListAction extends BaseAction {

 	public ActionForward executeAction(ActionMapping mapping, 
 									   ActionForm form,
 									   HttpServletRequest request, 
 									   HttpServletResponse response,
 									   SessionAPI session) throws Exception {
 		
 		ClientContext cct = session.getClientContext();

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
 		FunctionHelper.checkFunctions(request, cct, new int[] {
				ISecurityAPI.FUNC_COMP_VALIDATION_TABLES_READ,
				ISecurityAPI.FUNC_COMP_VALIDATION_TABLES_EDIT });

		IInvesflowAPI invesFlowAPI = session.getAPI();
		ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();

		// TODO Gestionar las SYSTEM_VALIDATION_TABLE en el listado y mostrar los valores pero no modificar
		/*
		StringBuffer condicion = new StringBuffer()
		condicion.append("where TIPO>=")
				 .append(EntityType.VALIDATION_TABLE_SIMPLE_ENTITY_TYPE.getId());
		*/

		String criterio = request.getParameter("property(criterio)");
	
		IItemCollection itemcol = catalogAPI.getValidationTablesByName(criterio);
        List ctentlist = CollectionBean.getBeanList(itemcol);
        if (!ctentlist.isEmpty()) {
        
	        // Obtener los nombres de las tablas de validación
	        StringBuffer querySelectEntitiesNames = new StringBuffer();
	        for (Iterator iter = ctentlist.iterator(); iter.hasNext();) {
	        	
				ItemBean itemBean = (ItemBean) iter.next();
				String nombre = (String)itemBean.getProperty("NOMBRE");
				
				if (querySelectEntitiesNames.length() > 0)
					querySelectEntitiesNames.append(" OR ");
				
				querySelectEntitiesNames.append("( CLAVE = '")
			    						.append(nombre)
			    						.append("' AND ID_ENT = ")
			    						.append(itemBean.getItem().getKeyInteger())
			    						.append(" )");
			}
	        
			StringBuffer query = new StringBuffer();
			query.append(" WHERE ")
				 .append(" IDIOMA='")
				 .append(cct.getAppLanguage())
				 .append("' AND ( ")
				 .append(querySelectEntitiesNames)
				 .append(" )");
			
			IItemCollection col = session.getAPI().getEntitiesAPI().queryEntities(SpacEntities.SPAC_CT_ENTITIES_RESOURCES, query.toString());
			Map entitiesLabelMap = col.toMap("ID_ENT");
	
			// Establecer el nombre de la tabla de validación
			for (Iterator iter = ctentlist.iterator(); iter.hasNext();) {
				
				ItemBean element = (ItemBean) iter.next();
				EntityDAO entResource = ( (EntityDAO) entitiesLabelMap.get(element.getItem().getKey()));
				if (entResource != null) {
					element.setProperty("ETIQUETA", entResource.get("VALOR"));
				}
				
				if (EntityType.VALIDATION_TABLE_SIMPLE_ENTITY_TYPE.getId()==Integer.parseInt(element.getString("TIPO"))) {
					element.setProperty("NOMBRE_TIPO", "form.entity.tablaValidacionSimple");
				}
				else if (EntityType.VALIDATION_TABLE_SUSTITUTE_ENTITY_TYPE.getId()==Integer.parseInt(element.getString("TIPO"))){
					element.setProperty("NOMBRE_TIPO", "form.entity.tablaValidacionSustituto");
				}
			}
        }
        
		// Formateador para la lista de eventos
		if (FunctionHelper.userHasFunction(request, cct, ISecurityAPI.FUNC_COMP_VALIDATION_TABLES_EDIT)) {
			setFormatter(request, "CTValueTablesListFormatter", "/formatters/ctvaluetableslistformatter.xml");
		} else {
			setFormatter(request, "CTValueTablesListFormatter", "/formatters/ctvaluetableslistreadonlyformatter.xml");
		}
		
		// Lista de tablas de validación
   	 	request.setAttribute("CTValueTablesList", ctentlist);

   	 	return mapping.findForward("success");
	}
 	
}