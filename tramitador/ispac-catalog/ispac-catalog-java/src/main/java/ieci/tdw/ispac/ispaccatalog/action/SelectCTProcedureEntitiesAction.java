package ieci.tdw.ispac.ispaccatalog.action;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaccatalog.action.form.SelectObjForm;
import ieci.tdw.ispac.ispaclib.bean.BeanFormatter;
import ieci.tdw.ispac.ispaclib.bean.CacheFormatterFactory;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SelectCTProcedureEntitiesAction extends BaseAction{

	public ActionForward executeAction( ActionMapping mapping,
										ActionForm form,
										HttpServletRequest request,
										HttpServletResponse response,
										SessionAPI session) throws Exception
	{
        SelectObjForm substform=(SelectObjForm) form;

	    String decorator = substform.getDecorator();
	    String caption = substform.getCaption();

	    String queryString = substform.getQuerystring();
	    if (queryString == null)
	    {
	    	queryString = request.getQueryString();
	    	substform.setQuerystring(queryString);
	    }

	    if (caption==null || caption.length()==0)
	        caption="catalog.selectobj.caption.default";

        request.setAttribute("CaptionKey", caption);

        IInvesflowAPI invesFlowAPI = session.getAPI();
        IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();

        int pcdId = Integer.parseInt(request.getParameter("pcdId"));
        IItemCollection collection = invesFlowAPI.getCatalogAPI().getProcedureEntities(pcdId);
        
        //si la entidad es spac_ct_entidades: sacar recursos y meterlos como etiqueta
    	List beans = entitiesAPI.getEntitiesExtendedItemBean("CTENTITY:NOMBRE", collection);
    	request.setAttribute("ItemList", beans);
    	request.setAttribute("NO_SEARCH", Boolean.TRUE);

        // Obtiene el decorador
        CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
        BeanFormatter formatter = factory.getFormatter(getISPACPath(decorator));
        request.setAttribute("Formatter", formatter);
		
        return mapping.findForward("success");
	}
	
	
};