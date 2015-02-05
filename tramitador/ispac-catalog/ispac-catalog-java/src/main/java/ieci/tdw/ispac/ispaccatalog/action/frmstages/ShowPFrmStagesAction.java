package ieci.tdw.ispac.ispaccatalog.action.frmstages;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.impl.CatalogAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaccatalog.action.BaseAction;
import ieci.tdw.ispac.ispaccatalog.action.form.BatchForm;
import ieci.tdw.ispac.ispaccatalog.bean.PFrmStageBean;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.bean.BeanFormatter;
import ieci.tdw.ispac.ispaclib.bean.CacheFormatterFactory;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ShowPFrmStagesAction extends BaseAction {

    public ActionForward executeAction(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response,
            SessionAPI session) throws Exception {

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_INV_PROCEDURES_READ,
				ISecurityAPI.FUNC_INV_PROCEDURES_EDIT });

        IInvesflowAPI invesFlowAPI = session.getAPI();
		ICatalogAPI catalogAPI= invesFlowAPI.getCatalogAPI();

		HashMap entidades=new HashMap();
		entidades.put("CTENT", new Integer(ICatalogAPI.ENTITY_CT_ENTITY));
		entidades.put("PFRMSTAGE", new Integer(ICatalogAPI.ENTITY_P_FRMSTAGE));

		BatchForm bform=(BatchForm)form;
		String itemId=request.getParameter("StageId");
		if (itemId == null)
		    itemId = bform.getItemId();

		String sqlquery="WHERE PFRMSTAGE.ID_ENT = CTENT.ID AND PFRMSTAGE.ID_FASE = " + itemId;

        IItemCollection itemcol=catalogAPI.queryCTEntities(entidades,sqlquery);

        Map appmap=catalogAPI.queryCTEntities(CatalogAPI.ENTITY_CT_APP, null).toMap();
		Object[] beanargs = new Object[]{appmap};

		List itemslist=CollectionBean.getBeanList(PFrmStageBean.class, itemcol, beanargs);

		request.setAttribute("ItemsList",itemslist);

		CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
		BeanFormatter formatter = factory.getFormatter(getISPACPath("/formatters/frmstages/pfrmstagesformatter.xml"));
		request.setAttribute("ItemsListFormatter", formatter);

		request.setAttribute("StageId", itemId);

   	 	return mapping.findForward("success");
    }

}
