package ieci.tdw.ispac.ispaccatalog.action;

import java.util.Iterator;
import java.util.List;

import ieci.tdw.ispac.api.ISPACEntities;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.ispaccatalog.action.procedure.ManageVistaCuadroProcedimientoAction;
import ieci.tdw.ispac.ispaccatalog.action.procedure.tree.ElementoCuadro;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.bean.TreeView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SelectCTObjectToTreeElementAction extends SelectCTObjectAction {
	
	public ActionForward executeAction(ActionMapping mapping,
									   ActionForm form,
									   HttpServletRequest request,
									   HttpServletResponse response,
									   SessionAPI session) throws Exception {
		
		// Funcionalidad para seleccionar un objeto
		super.executeAction(mapping, form, request, response, session);
		
		String parentElementId = null;
		int parentEntityId = ISPACEntities.ENTITY_NULLREGKEYID;
		
		// Elemento del árbol para el que se selecciona el objeto
		TreeView tree = (TreeView) request.getSession().getAttribute(ManageVistaCuadroProcedimientoAction.CUADRO_PROCEDIMIENTO);
		if (tree != null) {
	  
			ElementoCuadro elementoSeleccionado = (ElementoCuadro)tree.getSelectedNode().getModelItem();
			parentElementId = elementoSeleccionado.getRegId();
			parentEntityId = elementoSeleccionado.getEntityId();
		}
		
		// Lista de selección
		List itemList = (List) request.getAttribute("ItemList");
		if (itemList != null) {
		
	        // Establecer el elemento seleccionado en el árbol
	        for (Iterator iter = itemList.iterator(); iter.hasNext();) {
	        	
				ItemBean itemBean = (ItemBean) iter.next();
				itemBean.setProperty("PARENT_ELEMENT_ID", parentElementId);
				itemBean.setProperty("PARENT_ENTITY_ID", new Integer(parentEntityId));
			}
		}
	
		return mapping.findForward("success");
	}

}