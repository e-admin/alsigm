package ieci.tdw.ispac.ispaccatalog.action;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.bean.BeanFormatter;
import ieci.tdw.ispac.ispaclib.bean.CacheFormatterFactory;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class ShowCTFrmSearchListAction extends BaseAction
{

 	public ActionForward executeAction(
 			ActionMapping mapping,
 			ActionForm form,
 			HttpServletRequest request,
 			HttpServletResponse response,
 			SessionAPI session)
 			throws Exception
	{

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
 		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_COMP_SEARCH_FORMS_READ,
				ISecurityAPI.FUNC_COMP_SEARCH_FORMS_EDIT });

 		IInvesflowAPI invesFlowAPI = session.getAPI();
		ICatalogAPI catalogAPI= invesFlowAPI.getCatalogAPI();

        IItemCollection itemcol=catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_CT_SEARCHFORM,
                (request.getParameter("property(criterio)")!=null?"WHERE DESCRIPCION LIKE '%" + DBUtil.replaceQuotes(request.getParameter("property(criterio)")) + "%'":null));
        List ctfbulist=CollectionBean.getBeanList(itemcol);

   	 	CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
		BeanFormatter formatter = factory.getFormatter(getISPACPath("/formatters/ctfrmsearchlistformatter.xml"));
		request.setAttribute("CTFrmSearchListFormatter", formatter);

   	 	request.setAttribute("CTFrmSearchList",ctfbulist);


   	 /* 		Obtener el fichero de entrada
   	    	 	Document doc=XMLDocUtil.newDocument(in);
   	    	 	String textofrmbusqu=XMLDocUtil.toString(doc);
   	 */

   	 	return mapping.findForward("success");
	}
}