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
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ShowCTEntitiesListAction extends BaseAction {

 	public ActionForward executeAction(ActionMapping mapping, 
 									   ActionForm form,
 									   HttpServletRequest request, 
 									   HttpServletResponse response,
 									   SessionAPI session) throws Exception {
 		
 		ClientContext cct = session.getClientContext();

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
 		FunctionHelper.checkFunctions(request, cct, new int[] {
				ISecurityAPI.FUNC_COMP_ENTITIES_READ,
				ISecurityAPI.FUNC_COMP_ENTITIES_EDIT });

		IInvesflowAPI invesFlowAPI = session.getAPI();
		ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();

		StringBuffer condicion = new StringBuffer();
		condicion.append("WHERE TIPO = ")
				 .append(EntityType.PROCESS_ENTITY_TYPE.getId());
		
		String criterio = request.getParameter("property(criterio)");
		if (StringUtils.isNotBlank(criterio)) {
			
			condicion.append(" AND NOMBRE LIKE '%")
					 .append(DBUtil.replaceQuotes(criterio))
					 .append("%'");
		}
		
		condicion.append(" ORDER BY ID ");
		
		// Obtener las entidades
		IItemCollection itemcol = catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_CT_ENTITY, condicion.toString());
        List ctentlist = CollectionBean.getBeanList(itemcol);
        if (!ctentlist.isEmpty()) {
        
	        // Obtener los nombres de las entidades
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
			Map entitiesLabelMap = col.toMapStringKey("ID_ENT");
	
			// Establecer el recurso para el nombre de la entidad
			for (Iterator iter = ctentlist.iterator(); iter.hasNext();) {
				
				ItemBean element = (ItemBean) iter.next();
				EntityDAO entResource = ((EntityDAO)entitiesLabelMap.get(String.valueOf(element.getItem().getKeyInt())));
				if (entResource != null) {
					element.setProperty("ETIQUETA", entResource.get("VALOR"));
				}
			}
        }

		// Formateador
		if (FunctionHelper.userHasFunction(request, cct, ISecurityAPI.FUNC_INV_SIGN_CIRCUITS_EDIT)) {
			setFormatter(request, "CTEntitiesListFormatter", "/formatters/ctentitieslistformatter.xml");
		} else {
			setFormatter(request, "CTEntitiesListFormatter", "/formatters/ctentitieslistreadonlyformatter.xml");
		}
        
		// Lista de entidades
   	 	request.setAttribute("CTEntitiesList", ctentlist);

   	 	return mapping.findForward("success");
	}
 	
}