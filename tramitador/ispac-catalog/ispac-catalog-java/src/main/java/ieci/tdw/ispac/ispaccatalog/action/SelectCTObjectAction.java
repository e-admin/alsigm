package ieci.tdw.ispac.ispaccatalog.action;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaccatalog.action.form.SelectObjForm;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SelectCTObjectAction extends BaseAction
{

	public ActionForward executeAction( ActionMapping mapping,
										ActionForm form,
										HttpServletRequest request,
										HttpServletResponse response,
										SessionAPI session) throws Exception
	{
        SelectObjForm substform = (SelectObjForm) form;

	    String codetable = substform.getCodetable();
	    String codefield = substform.getCodefield();
	    String valuefield = substform.getValuefield();
	    String decorator = substform.getDecorator();
	    String searchvalue = substform.getSearchvalue();
	    String caption = substform.getCaption();
	    //String field = substform.getField();
	    String sqlquery = substform.getSqlquery();
	    String orderBy= substform.getOrderBy();
	    // Establecer el título
	    if (StringUtils.isBlank(caption)) {
	        caption = "catalog.selectobj.caption.default";
	    }
        request.setAttribute("CaptionKey", caption);

        // Establecer los parámetros
	    String queryString = substform.getQuerystring();
	    if (queryString == null) {
	    	queryString = request.getQueryString();
	    	substform.setQuerystring(queryString);
	    }
	    
	    else{
	    	
	    	substform.setQuerystring(URLDecoder.decode(substform.getQuerystring(), "ISO-8859-1"));
	    }


        // Indicar si hay que mostrar el buscador
        String noSearch = request.getParameter("noSearch");
        if (noSearch!=null) {
        	request.setAttribute("NO_SEARCH", Boolean.TRUE);
        }

        // Indicar si hay que mostrar el campo de texto
        String textValue = request.getParameter("textValue");
        if (StringUtils.isNotBlank(textValue)) {
        	
        	request.setAttribute("TEXT_VALUE", Boolean.TRUE);
        	
        	String textValueAction = request.getParameter("textValueAction");
        	if (StringUtils.isBlank(textValueAction)) {
        		textValueAction = "/selectObject.do";
        	}
        	request.setAttribute("TEXT_VALUE_ACTION", textValueAction);
        }

        // Establecer la lista de opciones
	    if (StringUtils.isNotBlank(codetable)) {
	    	
	        String query="";

	        if (sqlquery != null) {
	        	query = sqlquery;
	        }

	        if (searchvalue != null) {
	        	if (query.length()>0) {
	        		query = query + " AND ";
	        	} else { 
	        		query = " WHERE ";
	        	}
	            query += DBUtil.getToUpperSQL(valuefield) + " LIKE '%" + DBUtil.replaceQuotes(searchvalue.toUpperCase()) + "%'";
	        } 
	        if(StringUtils.isNotBlank(orderBy)){
	        	query+=" ORDER BY "+orderBy;
	        }

	        IInvesflowAPI invesFlowAPI = session.getAPI();
	        IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();
	   
	        IItemCollection collection = entitiesAPI.queryEntities(codetable,codefield, "",query);
	        List beans = null;

	        //si la entidad es spac_ct_entidades: sacar recursos y meterlos como etiqueta
	        if ("SPAC_CT_ENTIDADES".equals(codetable)){
	        	beans = entitiesAPI.getEntitiesExtendedItemBean(collection);
	        	request.setAttribute("NO_SEARCH", Boolean.TRUE);
	        }else{
	        	beans = CollectionBean.getBeanList(collection);
	        }
        	
	        for(int i=0; i<beans.size(); i++)
	        {
	        	ItemBean item=(ItemBean) beans.get(i);
	        	item.setProperty("ORDEN_TO_SHOW", (i+1)+"");
	        	beans.set(i, item);
	        	
	        }
	        request.setAttribute("ItemList", beans);

	        // Obtiene el decorador
	        setFormatter(request, "Formatter", decorator);
	    }

        return mapping.findForward("success");
	}
};