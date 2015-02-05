package ieci.tdw.ispac.ispaccatalog.action;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaccatalog.helpers.ActiveScreen;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.bean.BeanFormatter;
import ieci.tdw.ispac.ispaclib.bean.CacheFormatterFactory;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class ShowCTTPDocsListAction extends BaseAction
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
				ISecurityAPI.FUNC_INV_DOCTYPES_READ,
				ISecurityAPI.FUNC_INV_DOCTYPES_EDIT });

		// Establece la pantalla inicial para la navegación
		setActiveScreen(request, ActiveScreen.CT_DOCTYPES);

		IInvesflowAPI invesFlowAPI = session.getAPI();
		ICatalogAPI catalogAPI= invesFlowAPI.getCatalogAPI();

		String criterio = request.getParameter("property(criterio)");
		String sql = "";
		if (StringUtils.isNotBlank(criterio)) {
			sql += "WHERE NOMBRE LIKE '%" + DBUtil.replaceQuotes(request.getParameter("property(criterio)")) + "%'";
		}
		sql += " ORDER BY NOMBRE";
				
        IItemCollection itemcol=catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_CT_TYPEDOC, sql);
        List cttpdlist=CollectionBean.getBeanList(itemcol);

   	 	CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
		BeanFormatter formatter = factory.getFormatter(getISPACPath("/formatters/cttpdocslistformatter.xml"));
		request.setAttribute("CTTPDocsListFormatter", formatter);

   	 	request.setAttribute("CTTPDocsList", cttpdlist);

   	 	return mapping.findForward("success");
	}
}