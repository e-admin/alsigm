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

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class ShowCTProceduresListAction extends BaseAction
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
				ISecurityAPI.FUNC_INV_PROCEDURES_READ,
				ISecurityAPI.FUNC_INV_PROCEDURES_EDIT });

		String sFiltro = request.getParameter("property(criterio)");
        
		IInvesflowAPI invesFlowAPI = session.getAPI();
		ICatalogAPI catalogAPI= invesFlowAPI.getCatalogAPI();

		HashMap entidades=new HashMap();
		entidades.put("PPROC", new Integer(ICatalogAPI.ENTITY_P_PROCEDURE));
		entidades.put("CTPROC", new Integer(ICatalogAPI.ENTITY_CT_PROCEDURE));
		
		//Selecionamos la última versión de cada procedimiento.
		String whereClause =  " WHERE CTPROC.ID = PPROC.ID "
							+ " AND CTPROC.ID IN ( "
							+ " SELECT MAX(ID) FROM SPAC_P_PROCEDIMIENTOS "
							+ " GROUP BY ID_GROUP "
							+ " ) ";
	
		if((sFiltro!=null)&&(!"".equals(sFiltro))){
			whereClause +=  " AND CTPROC.NOMBRE LIKE '%" + DBUtil.replaceQuotes(sFiltro) + "%'";
		}
        //Asginado identificador de indentidad incorrecto
        IItemCollection itemcol=catalogAPI.queryCTEntities(entidades, whereClause );
        List ctproclist=CollectionBean.getBeanList(itemcol);

        CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
		BeanFormatter formatter = factory.getFormatter(getISPACPath("/formatters/ctprocedureslistformatter.xml"));
		request.setAttribute("CTProceduresListFormatter", formatter);

   	 	request.setAttribute("CTProceduresList",ctproclist);

   	 	return mapping.findForward("success");
	}
}