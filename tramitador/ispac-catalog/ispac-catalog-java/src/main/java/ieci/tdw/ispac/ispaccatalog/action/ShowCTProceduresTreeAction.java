package ieci.tdw.ispac.ispaccatalog.action;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.IProcedureAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.util.ListCollection;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.bean.BeanFormatter;
import ieci.tdw.ispac.ispaclib.bean.CacheFormatterFactory;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.bean.TreeItemBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class ShowCTProceduresTreeAction extends BaseAction
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
 		
 		//Parámetro con el id de procedimiento a desplegar en árbol
 		String idProc = request.getParameter("idProc");

		IInvesflowAPI invesFlowAPI = session.getAPI();
		ICatalogAPI catalogAPI= invesFlowAPI.getCatalogAPI();
		IProcedureAPI procedureAPI = invesFlowAPI.getProcedureAPI();

        //Seleccionamos la última versión de cada procedimiento.
		/*
		String whereClause =  " WHERE ID IN ( "
							+ " SELECT MAX(ID) FROM SPAC_P_PROCEDIMIENTOS "
							+ " GROUP BY ID_GROUP "
							+ " ) ";

        IItemCollection itemcol=catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_CT_PROCEDURE, whereClause);
        
        //Obtenemos el árbol de ItemBeans
        TreeItemBean tree = CollectionBean.getBeanTree(itemcol);
        */
		
		Map tableentitymap = new HashMap();
		tableentitymap.put("SPAC_CT_PROCEDIMIENTO","SPAC_CT_PROCEDIMIENTOS");
		tableentitymap.put("SPAC_P_PROCEDIMIENTO","SPAC_P_PROCEDIMIENTOS");

        //Seleccionamos la última versión de cada procedimiento.
		String whereClause =  " WHERE SPAC_P_PROCEDIMIENTO.ID = SPAC_CT_PROCEDIMIENTO.ID AND SPAC_CT_PROCEDIMIENTO.ID IN ( "
							+ " SELECT MAX(ID) FROM SPAC_P_PROCEDIMIENTOS "
							+ " GROUP BY ID_GROUP "
							+ " ) ORDER BY SPAC_CT_PROCEDIMIENTO.NOMBRE";

        IItemCollection itemcol = catalogAPI.queryCTEntities(tableentitymap, whereClause);
        Map itemcolmapId = itemcol.toMap("SPAC_CT_PROCEDIMIENTO:ID");
        List items = new ArrayList();
        
        Iterator it = itemcolmapId.entrySet().iterator();
        while (it.hasNext()) {
        	
        	Entry e = (Entry) it.next();
        	IItem item = (IItem) e.getValue();
        	
        	if (item.getInt("SPAC_CT_PROCEDIMIENTO:ID_PADRE") != -1) {
        		
        		
        		IItem itemPadre = (IItem) itemcolmapId.get(item.get("SPAC_CT_PROCEDIMIENTO:ID_PADRE"));
        		if (itemPadre != null) {
        			
        			// Referenciar los elementos del árbol mediante los identificadores de grupo
        			item.set("SPAC_CT_PROCEDIMIENTO:ID_PADRE", itemPadre.getInt("SPAC_P_PROCEDIMIENTO:ID_GROUP"));
        			
        		} else {
        			itemPadre = procedureAPI.getProcedureById(item.getInt("SPAC_CT_PROCEDIMIENTO:ID_PADRE"));
        			if (itemPadre != null) {

            			// Referenciar los elementos del árbol mediante los identificadores de grupo
            			item.set("SPAC_CT_PROCEDIMIENTO:ID_PADRE", itemPadre.getInt("PPROCEDIMIENTOS:ID_GROUP"));

        			}
        		}
        	}
        	
        	items.add(item);
        }

        //Obtenemos el árbol de ItemBeans
        TreeItemBean tree = CollectionBean.getBeanTree(new ListCollection(items), "SPAC_P_PROCEDIMIENTO:ID_GROUP", "SPAC_CT_PROCEDIMIENTO:ID_PADRE");

   	 	//Obtenemos el formateador
        CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
		BeanFormatter formatter = factory.getFormatter(getISPACPath("/formatters/ctprocedurestreeformatter.xml"));

		request.setAttribute("CTProceduresListFormatter", formatter);
   	 	request.setAttribute("CTProceduresList",tree);
   	 	if(idProc != null){
   	 		request.setAttribute("idProc", idProc);
   	 	}

   	 	return mapping.findForward("success");
	}
}