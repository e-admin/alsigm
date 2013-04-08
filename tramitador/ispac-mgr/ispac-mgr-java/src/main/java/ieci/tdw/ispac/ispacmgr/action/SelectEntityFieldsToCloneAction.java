package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.util.ListCollection;
import ieci.tdw.ispac.ispaclib.bean.BeanFormatter;
import ieci.tdw.ispac.ispaclib.bean.CacheFormatterFactory;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.entity.EntityDAO;
import ieci.tdw.ispac.ispaclib.entity.def.EntityDef;
import ieci.tdw.ispac.ispaclib.entity.def.EntityField;
import ieci.tdw.ispac.ispacmgr.action.form.EntityForm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SelectEntityFieldsToCloneAction extends BaseAction {

	public ActionForward executeAction(ActionMapping mapping,
									   ActionForm form,
									   HttpServletRequest request,
									   HttpServletResponse response,
									   SessionAPI session) throws Exception {
		
		ClientContext cct = session.getClientContext();
		
		IEntitiesAPI entitiesAPI = session.getAPI().getEntitiesAPI();

        // Formulario asociado a la acción
        EntityForm defaultForm = (EntityForm) form;
        		
		// Obtener la entidad
		int entityId = Integer.parseInt((String) request.getParameter("entityId"));
		IItem entity = entitiesAPI.getCatalogEntity(entityId);
		
		// Obtener los campos de la definición de la entidad
		EntityDef entityDef = EntityDef.parseEntityDef(entity.getString("DEFINICION"));
		List fieldList = CollectionBean.getBeanList(new ListCollection(entityDef.getFields()));
		
		// Obtener las etiquetas de los campos
		Map entityLabels = getEntityLabelsMap(entitiesAPI, entityId, cct.getAppLanguage());
		
    	ItemBean bean;
    	List beans = new ArrayList();
        for (int i = 0; i < fieldList.size(); i++) {
            
        	bean = (ItemBean) fieldList.get(i);
            if (setEtiqueta(entityId, bean, ((EntityField)bean.getItem()), entityLabels)) {
            	beans.add(bean);
            }
        }
		
        request.setAttribute("ItemList", beans);
        
        defaultForm.setEntity(String.valueOf(entityId));
        
        // Marcar los campos seleccionados
		Map entityFieldsMap = (Map) request.getSession().getAttribute("EntityFieldsMap");
		if ((entityFieldsMap != null) &&
			(entityFieldsMap.containsKey(defaultForm.getEntity()))) {
				
			String[] selectedIdFields = (String[]) entityFieldsMap.get(defaultForm.getEntity());
			defaultForm.setMultibox(selectedIdFields);
		}
		else {
			// Cuando no se han modificado los campos de la entidad a clonar
			// aparecerán todos seleccionados
			request.setAttribute("SelectedAll", Boolean.TRUE);
		}
		
		// Campos obligatorios que se muestran seleccionados y deshabilitados
        request.setAttribute("MandatoryFieldIdsMap", entityDef.mandatoryValidationIdsMap());
        
        // Establecer el formateador
        CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
        BeanFormatter formatter = factory.getFormatter(getISPACPath("/digester/selectentityfieldsformatter.xml"));
        request.setAttribute("Formatter", formatter);
        
   		// Y se mantiene la ordenación de la lista de entidades
   		String displayTagOrderParams = getDisplayTagOrderParams(request);	
		request.setAttribute("displayTagOrderParams", displayTagOrderParams);
		
        return mapping.findForward("success");
	}
	
    /**
     * Obtiene un mapa con los recusos de la entidad 
     * @param session
     * @param entityId
     * @param language
     * @return
     * @throws ISPACException
     */
    private Map getEntityLabelsMap(IEntitiesAPI entitiesAPI, 
    							   int entityId,
    							   String language)  throws ISPACException {

		StringBuffer query = new StringBuffer();
		query.append(" WHERE ID_ENT=")
			 .append(entityId)
			 .append(" AND IDIOMA='")
			 .append(language)
			 .append("'");
		
		IItemCollection collection = entitiesAPI.queryEntities(SpacEntities.SPAC_CT_ENTITIES_RESOURCES, query.toString());
		return collection.toMap("CLAVE");    	
    }
    
    private boolean setEtiqueta(int entityId,
    							ItemBean itemBeanField, 
    						 	EntityField field, 
    						 	Map resourcesEntity) throws ISPACException {
    	
        if (resourcesEntity != null) {
        	
        	String physicalName = itemBeanField.getString("PHYSICALNAME");
        	if (!physicalName.equals(ICatalogAPI.ID_FIELD_NAME) &&
        		!physicalName.equals(ICatalogAPI.NUMEXP_FIELD_NAME)) {
        		
        		if (!((entityId == SpacEntities.SPAC_EXPEDIENTES) &&
        			 (physicalName.equals("IDPROCESO") ||physicalName.equals("REFERENCIA_INTERNA")))) {

        			EntityDAO entResource = ((EntityDAO)resourcesEntity.get(physicalName.toUpperCase()));
					if (entResource != null) {
						
						itemBeanField.setProperty("ETIQUETA", entResource.get("VALOR"));
						return true;
					}
        		}
        	}
        }
        
        return false;
    }
	
}