package ieci.tdw.ispac.ispaccatalog.action;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.util.ListCollection;
import ieci.tdw.ispac.ispaccatalog.action.form.SelectObjForm;
import ieci.tdw.ispac.ispaclib.bean.BeanFormatter;
import ieci.tdw.ispac.ispaclib.bean.CacheFormatterFactory;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.entity.EntityDAO;
import ieci.tdw.ispac.ispaclib.db.InternalDataType;
import ieci.tdw.ispac.ispaclib.entity.def.EntityDef;
import ieci.tdw.ispac.ispaclib.entity.def.EntityField;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SelectFieldsEntityAction extends BaseAction
{

	public ActionForward executeAction(ActionMapping mapping,
									   ActionForm form,
									   HttpServletRequest request,
									   HttpServletResponse response,
									   SessionAPI session) throws Exception {
		
		ClientContext cct = session.getClientContext();

        IInvesflowAPI invesFlowAPI = session.getAPI();
        IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();
		
        SelectObjForm substform=(SelectObjForm) form;

//	    String codetable = substform.getCodetable();
//	    String codefield = substform.getCodefield();
//	    String valuefield = substform.getValuefield();
	    String decorator = substform.getDecorator();
//	    String searchvalue = substform.getSearchvalue();
	    String caption = substform.getCaption();
	    String field = substform.getField();
//	    String sqlquery = substform.getSqlquery();

	    String queryString = substform.getQuerystring();
	    if (queryString == null)
	    {
	    	queryString = request.getQueryString();
	    	substform.setQuerystring(queryString);
	    }

	    if (caption==null || caption.length()==0)
	        caption="catalog.selectobj.caption.default";

        request.setAttribute("CaptionKey", caption);

        /*
         * Nombre de la variable de sesión que mantiene los parámetros
         * del tag de búsqueda.
         */
        //String parameters=substform.getParameters();

        String query="WHERE ID="+substform.getProperty(field);

//        if (sqlquery != null) {
//        	query = sqlquery;
//        }

//        if (searchvalue != null) {
//        	if (query.length()>0) query = query + " AND ";
//        	else query = " WHERE ";
//            query += valuefield + " LIKE '%" + DBUtil.replaceQuotes(searchvalue) + "%'";
//        } 
        
        String appLanguage = cct.getAppLanguage();
        
        // Obtener la entidad
        List fieldList = null;
        IItemCollection collection = entitiesAPI.queryEntities("SPAC_CT_ENTIDADES","ID", "",query);
        if (collection.next()) {
        	
        	for (Iterator iter = collection.iterator(); iter.hasNext();) {
        		
				IItem entity = (IItem) iter.next();
				String xmlDefinition = entity.getString("DEFINICION");
				EntityDef entityDef = EntityDef.parseEntityDef(xmlDefinition);
				
				fieldList = CollectionBean.getBeanList(new ListCollection(entityDef.getFields(InternalDataType.DATE)));
				
				IItemCollection resourcesCollection = entitiesAPI.getEntityResources(Integer.parseInt(substform.getProperty(field)), appLanguage);
				Map entitiesLabelMap = resourcesCollection.toMap("CLAVE");
				
				ItemBean bean;
				for (int i = 0; i < fieldList.size(); i++) {
					
		            bean = (ItemBean) fieldList.get(i);
		            bean.setProperty("TYPEDESCR", getMessage(request, cct.getLocale(), "field.type." + ((EntityField)bean.getItem()).getType().getId()));
		            
		            // En edicion
		            setEtiqueta(bean, ((EntityField)bean.getItem()), entitiesLabelMap);
		            bean.setProperty("ENTITYID:FIELDID", substform.getProperty(field)+":"+String.valueOf(((EntityField)bean.getItem()).getId()));
		        }
			}
        }
        
        request.setAttribute("ItemList", fieldList);
        
//        //si la entidad es spac_ct_entidades: sacar recursos y meterlos como etiqueta
//        if ("SPAC_CT_ENTIDADES".equals(codetable)){
//        	beans = entitiesAPI.getEntitiesExtendedItemBean(collection);
//        	request.setAttribute("ItemList", beans);
//        }

        // Obtiene el decorador
        CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
        BeanFormatter formatter = factory.getFormatter(getISPACPath(decorator));
        request.setAttribute("Formatter", formatter);
        
        request.setAttribute("NO_SEARCH", Boolean.TRUE);
		
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