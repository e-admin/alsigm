package ieci.tdw.ispac.ispaccatalog.action;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ITemplateAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaccatalog.action.form.EntityForm;
import ieci.tdw.ispac.ispaccatalog.breadcrumbs.BreadCrumbsContainer;
import ieci.tdw.ispac.ispaccatalog.breadcrumbs.BreadCrumbsManager;
import ieci.tdw.ispac.ispaclib.bean.BeanFormatter;
import ieci.tdw.ispac.ispaclib.bean.CacheFormatterFactory;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class ShowTemplatesListAction extends BaseAction
{

 	public ActionForward executeAction(
	ActionMapping mapping,
	ActionForm form,
	HttpServletRequest request,
	HttpServletResponse response,
	SessionAPI session)
	throws Exception
	{
		IInvesflowAPI invesFlowAPI = session.getAPI();
		ICatalogAPI catalogAPI= invesFlowAPI.getCatalogAPI();

		EntityForm entityForm = (EntityForm) form;
		
		String stype = request.getParameter("type");
		
 		if (stype == null)
 		{
 			stype = entityForm.getProperty("ID_TPDOC");
 		} 		

		entityForm.setEntity( Integer.toString(ICatalogAPI.ENTITY_CT_TEMPLATE));

        IItemCollection collection;
        String sWhere;

        sWhere = "WHERE ID_TPDOC = " + stype;
        
        String filter = request.getParameter("property(criterio)");

        if (filter != null)
        {
        	sWhere += " AND NOMBRE LIKE '%" + DBUtil.replaceQuotes(filter) + "%'";
        }

        collection = catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_CT_TEMPLATE, sWhere);
        List list = CollectionBean.getBeanList(collection);
        ITemplateAPI templateAPI = invesFlowAPI.getTemplateAPI();
        
        //calcular si son especificas de procedimiento o no
        for (Iterator iter = list.iterator(); iter.hasNext();) {
			 ItemBean element = (ItemBean) iter.next();
			 int id = element.getItem().getInt("ID");
			 if (!templateAPI.isProcedureTemplate(id))
				 element.setProperty("NOMBRE", element.getProperty("NOMBRE")+ getResources(request).getMessage("generic.template"));
			 element.setProperty("PROCEDURE_TEMPLATE", new Boolean(templateAPI.isProcedureTemplate(id)));	 
		}
        
       	CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
		BeanFormatter formatter = factory.getFormatter(getISPACPath("/formatters/templateslistformatter.xml"));
		request.setAttribute("TemplatesListFormatter", formatter);

   	 	request.setAttribute("TemplatesList", list);

   	 	// Generamos la ruta de navegación hasta la pantalla actual.
		BreadCrumbsContainer bcm = BreadCrumbsManager.getInstance(catalogAPI).getBreadCrumbs(request);
 		request.setAttribute("BreadCrumbs", bcm.getList());

   	 	return mapping.findForward("success");
	}
}