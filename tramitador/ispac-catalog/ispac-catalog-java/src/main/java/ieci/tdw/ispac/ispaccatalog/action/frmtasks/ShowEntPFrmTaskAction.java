package ieci.tdw.ispac.ispaccatalog.action.frmtasks;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaccatalog.action.BaseAction;
import ieci.tdw.ispac.ispaccatalog.action.form.BatchForm;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.bean.BeanFormatter;
import ieci.tdw.ispac.ispaclib.bean.CacheFormatterFactory;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ShowEntPFrmTaskAction extends BaseAction {

    public ActionForward executeAction(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response,
            SessionAPI session) throws Exception {

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_INV_PROCEDURES_READ,
				ISecurityAPI.FUNC_INV_PROCEDURES_EDIT });

		IInvesflowAPI invesFlowAPI = session.getAPI();
		ICatalogAPI catalogAPI= invesFlowAPI.getCatalogAPI();

		BatchForm bform=(BatchForm)form;
		String itemId = request.getParameter("TaskId");
		if (itemId == null) itemId = bform.getItemId();

		IItemCollection itemcol=catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_P_FRMTASK, "WHERE ID_TRAMITE = " + itemId);

		StringBuffer valuesbuf = new StringBuffer();
		Iterator it = itemcol.iterator();
		while (it.hasNext())
		{
		    IItem item=(IItem)it.next();
		    valuesbuf.append(item.getString("ID_ENT") + ", ");
		}

		String values=valuesbuf.toString();

		String sqlquery="WHERE ";
		if (values.length() != 0)
		{
		    values = values.substring(0, values.length()-2);
		    sqlquery += "PENT.ID_ENT NOT IN (" + values + ") AND ";
		}

		IItemCollection stgcol=catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_P_TASK, "WHERE ID = " + itemId);
		sqlquery += " PENT.ID_PCD=" + stgcol.value().getString("ID_PCD");

		if (bform.getTxtFiltro()!=null && bform.getTxtFiltro().length()>0)
		    sqlquery+=" AND LOWER(CTENT.NOMBRE) LIKE LOWER('%" + bform.getTxtFiltro() + "%')";

		HashMap entidades=new HashMap();
		entidades.put("CTENT", new Integer(ICatalogAPI.ENTITY_CT_ENTITY));
		entidades.put("PENT", new Integer(ICatalogAPI.ENTITY_P_ENTITY));

		sqlquery += " AND PENT.ID_ENT = CTENT.ID";

		if (bform.getTxtFiltro()!=null && bform.getTxtFiltro().length()>0)
		    sqlquery+=" AND LOWER(CTENT.NOMBRE) LIKE LOWER('%" + bform.getTxtFiltro() + "%')";

        IItemCollection entcol=catalogAPI.queryCTEntities(entidades,sqlquery);

		List itemslist=CollectionBean.getBeanList(entcol);
		request.setAttribute("ItemsList",itemslist);

		CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
		BeanFormatter formatter = factory.getFormatter(getISPACPath("/formatters/frmtasks/entityfrmapptaskformatter.xml"));
		request.setAttribute("ItemsListFormatter", formatter);

		request.setAttribute("TaskId", String.valueOf(itemId));

		return mapping.findForward("success");
    }

}
