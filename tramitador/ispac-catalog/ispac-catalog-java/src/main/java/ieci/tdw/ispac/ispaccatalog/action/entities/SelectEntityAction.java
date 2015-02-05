package ieci.tdw.ispac.ispaccatalog.action.entities;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaccatalog.action.BaseAction;
import ieci.tdw.ispac.ispaccatalog.action.form.SelectObjForm;
import ieci.tdw.ispac.ispaclib.bean.BeanFormatter;
import ieci.tdw.ispac.ispaclib.bean.CacheFormatterFactory;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SelectEntityAction extends BaseAction {
	
	public ActionForward executeAction(ActionMapping mapping,
									   ActionForm form,
									   HttpServletRequest request,
									   HttpServletResponse response,
									   SessionAPI session) throws Exception {
		
        SelectObjForm substform=(SelectObjForm) form;

	    String codetable = substform.getCodetable();
	    String codefield = substform.getCodefield();
	    String valuefield = substform.getValuefield();
	    String decorator = substform.getDecorator();
	    String searchvalue = substform.getSearchvalue();
	    String caption = substform.getCaption();
	    //String field = substform.getField();

	    String queryString = substform.getQuerystring();
	    if (queryString == null) {
	    	queryString = request.getQueryString();
	    	substform.setQuerystring(queryString);
	    }

	    //MessageResources messages = this.getResources( request);
	    //String message = messages.getMessage(caption);
	    if (caption==null || caption.length()==0)
	        caption="catalog.selectobj.caption.default";

        request.setAttribute("CaptionKey", caption);

		/*
		 * Nombre de la variable de sesión que mantiene los parámetros
		 * del tag de búsqueda.
		 */
		String parameters = request.getParameter("parameters");
    	
//        // Obtiene los parametros del tag y los salva en la request
//        String sParameters = session.getClientContext().getSsVariable(parameters);
//        if (sParameters != null) {
//            request.setAttribute("parameters", ActionFrameTag.toMap(sParameters));
//        }

        // Obtiene los parametros del tag y los salva en la request
        Map sParameters = (Map)request.getSession().getAttribute(parameters);
        if (sParameters != null) {
            request.setAttribute("parameters", sParameters);
        }

        IInvesflowAPI invesFlowAPI = session.getAPI();
        IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();

        // Obtener la entidades
        String query = "WHERE TIPO = 1";
        if (searchvalue!=null)
            query += " AND " + valuefield + " LIKE '%" + DBUtil.replaceQuotes(searchvalue) + "%'";
        query += " ORDER BY NOMBRE";

        IItemCollection collection = entitiesAPI.queryEntities(codetable,codefield, "",query);
        request.setAttribute("ItemList", CollectionBean.getBeanList(collection));

        // Obtiene el decorador
        CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
        BeanFormatter formatter = factory.getFormatter(getISPACPath(decorator));
        request.setAttribute("Formatter", formatter);

        return mapping.findForward("success");
	}
}