package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.Properties;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.item.GenericItem;
import ieci.tdw.ispac.ispacmgr.action.form.EntityBatchForm;

import java.sql.Types;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SetMultiValueAction extends BaseAction {

	public ActionForward executeAction(
	ActionMapping mapping,
	ActionForm form,
	HttpServletRequest request,
	HttpServletResponse response,
	SessionAPI session) throws Exception {
		
	    EntityBatchForm _form = (EntityBatchForm) form;
	    // Identificador de la entidad
	    String entity = _form.getProperty("entity");
	    
		//Lista de valores seleccionados
		String[] values = _form.getMultibox();

		IInvesflowAPI invesFlowAPI = session.getAPI();
		IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();
		
		
		if (entity != null && !entity.equals(""))
		    setValueListEntity(request, values, entitiesAPI, Integer.parseInt(entity));
		else
		    setValueList(request, values);
		    

//		//Nombre de la variable de sesión que mantiene los parámetros del tag de búsqueda.
//		String parameters = _form.getProperty("parameters");
//		String sParameters = session.getClientContext().getSsVariable( parameters);
//		// Obtiene los parámetros del tag de búsqueda y los salva en el request
//		if (sParameters != null)
//			request.setAttribute(parameters, ActionFrameTag.toMap( sParameters));

		return mapping.findForward("success");
	}
	
	
	private void setValueListEntity(HttpServletRequest request, String[] values, IEntitiesAPI entitiesAPI, int entityId) throws ISPACException{
		//Componemos la lista de Identificadores
		String listIds = "";
		for (int i = 0; i < values.length; i++)
		    listIds += values[i]+", ";
		if (listIds.length()!= 0) 
		    listIds = listIds.substring(0,listIds.length()-2);

	    if (!listIds.equals("")){
		    //TODO El campo (ID) sobre el que se debe hacer la consulta sacarlo de un parámetro, no puede estar FIJO
		    String sQuery = "WHERE ID IN (" + listIds + ") ";
		    IItemCollection collection = entitiesAPI.queryEntities(entityId, sQuery);
		    if (collection != null)
		        request.setAttribute("ValueList", CollectionBean.getBeanList(collection));
		}
		else
		    request.setAttribute("ValueList", new java.util.LinkedList());

	    
	}
	
	private void setValueList(HttpServletRequest request, String[] values) throws ISPACException{
	    List valueList = new LinkedList();
	    
		for (int i = 0; i < values.length; i++){
		    IItem item = new GenericItem(getMultiValueProperties(), "ID");
		    item.set("ID", values[i]);
		    item.set("VALOR", values[i]);
		    valueList.add(new ItemBean(item));
		}
	    request.setAttribute("ValueList", valueList);
	    
	}
	
	private Properties getMultiValueProperties(){
		int ordinal=0;
		Properties properties = new Properties();	
		properties.add(new Property(ordinal++,"ID",Types.VARCHAR));
		properties.add(new Property(ordinal++,"VALOR",Types.VARCHAR));
		return properties;
	}
	
}