/*uanin
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package ieci.tdw.ispac.ispaccatalog.action.entities;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.impl.CatalogAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaccatalog.action.BaseAction;
import ieci.tdw.ispac.ispaccatalog.bean.PEntityBean;
import ieci.tdw.ispac.ispaclib.bean.BeanFormatter;
import ieci.tdw.ispac.ispaclib.bean.CacheFormatterFactory;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.context.IClientContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author alberto
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class SelectEntitiesAction extends BaseAction {

    public ActionForward executeAction(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response,
            SessionAPI session) throws Exception {

        IClientContext cct = session.getClientContext();
        IInvesflowAPI invesFlowAPI = session.getAPI();
		ICatalogAPI catalogAPI= invesFlowAPI.getCatalogAPI();

		String pcdId = request.getParameter("pcdId");

		//Eliminar entidad
		String delId = request.getParameter("delId");

		if (delId != null) {
		    IItem item = catalogAPI.getCTEntity(ICatalogAPI.ENTITY_P_ENTITY);
		    item.setKey(Integer.parseInt(delId));
		    item.delete(cct);
		}

		//Ordenar entidades
		String modOrder=request.getParameter("modOrder");
		String entityId=request.getParameter("pentId");
		if (modOrder != null)
		{
		    if (modOrder.equalsIgnoreCase("INC"))
		        catalogAPI.incOrderPEntity(Integer.parseInt(pcdId),Integer.parseInt(entityId));
		    else
		        catalogAPI.decOrderPEntity(Integer.parseInt(pcdId),Integer.parseInt(entityId));
		}


		HashMap entidades=new HashMap();
		entidades.put("CTENTITY",new Integer(ICatalogAPI.ENTITY_CT_ENTITY));
		entidades.put("PENTITIES", new Integer(ICatalogAPI.ENTITY_P_ENTITY));

		String sqlquery="WHERE CTENTITY.ID =PENTITIES.ID_ENT  AND PENTITIES.ID_PCD = " + pcdId +
						" ORDER BY PENTITIES.ORDEN";

        IItemCollection itemcol=catalogAPI.queryCTEntities(entidades,sqlquery);


        Map appmap=catalogAPI.queryCTEntities(CatalogAPI.ENTITY_CT_APP, null).toMap();
		Object[] beanargs = new Object[] { appmap  };

		List itemslist=CollectionBean.getBeanList(PEntityBean.class, itemcol, beanargs);
        //List itemslist=CollectionBean.getBeanList(itemcol);

   	 	CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
		BeanFormatter formatter = factory.getFormatter(getISPACPath("/formatters/entities/entitiesformatter.xml"));
		request.setAttribute("ItemsListFormatter", formatter);
   	 	request.setAttribute("ItemsList", itemslist);

   	 	request.setAttribute("pcdId", pcdId);

   	 	return mapping.findForward("success");
    }

}
