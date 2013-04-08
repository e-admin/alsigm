package ieci.tdw.ispac.ispaccatalog.action;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.entity.EntityDAO;
import ieci.tdw.ispac.ispaclib.entity.def.EntityDef;
import ieci.tdw.ispac.ispaclib.entity.def.EntityField;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SetCTEntityFieldAction extends BaseAction {

	public ActionForward executeAction( ActionMapping mapping,
										ActionForm form,
										HttpServletRequest request,
										HttpServletResponse response,
										SessionAPI session) throws Exception {
		
		ClientContext cct = session.getClientContext();
		
        IInvesflowAPI invesFlowAPI = session.getAPI();
        IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();
		
//        String codetable=substform.getCodetable();
//        String codefield=substform.getCodefield();

//        /*
//         * Nombre de la variable de sesión que mantiene los parámetros
//         * del tag de búsqueda.
//         */
//        String parameters=substform.getParameters();
//
//        // Obtiene los parámetros del tag de búsqueda y los salva en el request
//		String sParameters = session.getClientContext().getSsVariable( parameters);
//		if (sParameters != null)
//		{
//			request.setAttribute(parameters, ActionFrameTag.toMap( sParameters));
//		}

		// Código del sustituto
		String selectcode = request.getParameter("selectcode");
		
		if (selectcode==null)
		    return mapping.findForward("success");

		String entityId = selectcode.split(":")[0];
		String fieldId = selectcode.split(":")[1];
		//String fieldId = substform.getProperty(substform.getField());

		String appLanguage = cct.getAppLanguage();

        String query = "WHERE ID=" + entityId;
//      obtener la entidad
        IItemCollection collection = entitiesAPI.queryEntities("SPAC_CT_ENTIDADES", "ID", "", query);
        if (collection.next()) {
        	
        	for (Iterator iter = collection.iterator(); iter.hasNext();) {
        		
				IItem entity = (IItem) iter.next();
				String xmlDefinition = entity.getString("DEFINICION");
				EntityDef entityDef = EntityDef.parseEntityDef(xmlDefinition);
//				List fieldList = CollectionBean.getBeanList(new ListCollection(entityDef.getFields()));
				
				IItemCollection resourcesCollection = entitiesAPI.getEntityResources(Integer.parseInt(entityId), appLanguage);
				Map entitiesLabelMap = resourcesCollection.toMap("CLAVE");
				
				EntityField field = entityDef.findField(Integer.parseInt(fieldId));
				ItemBean itemBean = new ItemBean(field);
				itemBean.setProperty("TYPEDESCR", getMessage(request, cct.getLocale(), 
						"field.type." + ((EntityField)itemBean.getItem()).getType().getId()));

				// En edicion
	            setEtiqueta(itemBean, ((EntityField)itemBean.getItem()), entitiesLabelMap);
	    		
	            // Salva en la petición el bean
	    		request.setAttribute("Substitute", itemBean);
			}
        }

		return mapping.findForward("success");
	}
	
	private void setEtiqueta(ItemBean itemBeanField, EntityField field, Map resourcesEntity)
	throws ISPACException
	{
        if (resourcesEntity != null) {
        	
            EntityDAO entResource = ((EntityDAO)resourcesEntity.get(itemBeanField.getString("PHYSICALNAME").toUpperCase()));
			if (entResource != null) {
				
				itemBeanField.setProperty("ETIQUETA", entResource.get("VALOR"));
				return;
			}
        }
        
        String label = field.getLogicalName();
		if (label == null) {
			label = field.getPhysicalName();
		}
		itemBeanField.setProperty("ETIQUETA", label);
    }
	
}