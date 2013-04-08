package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.bean.BeanFormatter;
import ieci.tdw.ispac.ispaclib.bean.CacheFormatterFactory;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SelectEntityToCloneAction extends BaseAction {

	public ActionForward executeAction(ActionMapping mapping,
									   ActionForm form,
									   HttpServletRequest request,
									   HttpServletResponse response,
									   SessionAPI session) throws Exception {
		
		IEntitiesAPI entitiesAPI = session.getAPI().getEntitiesAPI();
		
		String query = " WHERE TIPO = 1 AND ID NOT IN (" + SpacEntities.SPAC_EXPEDIENTES + ","
														 + SpacEntities.SPAC_DT_INTERVINIENTES + ","
														 + SpacEntities.SPAC_DT_DOCUMENTOS + ","
														 + SpacEntities.SPAC_DT_TRAMITES + ")";

        IItemCollection collection = entitiesAPI.queryEntities(SpacEntities.SPAC_CT_ENTIDADES, query);
        // Obtener los recursos para establecerlos como etiquetas
        List beans = entitiesAPI.getEntitiesExtendedItemBean(collection);
        request.setAttribute("ItemList", beans);
        
        // Establecer el formateador
        CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
        BeanFormatter formatter = factory.getFormatter(getISPACPath("/digester/selectentityformatter.xml"));
        request.setAttribute("Formatter", formatter);
        
   		// Y se mantiene la ordenación de la lista de entidades
   		String displayTagOrderParams = getDisplayTagOrderParams(request);	
		request.setAttribute("displayTagOrderParams", displayTagOrderParams);
		
        return mapping.findForward("success");
	}
	
}