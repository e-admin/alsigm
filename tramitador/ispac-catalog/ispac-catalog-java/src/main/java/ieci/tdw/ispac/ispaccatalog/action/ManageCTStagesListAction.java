package ieci.tdw.ispac.ispaccatalog.action;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaccatalog.action.form.EntityForm;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.context.ClientContext;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ManageCTStagesListAction extends BaseDispatchAction {
	
	public ActionForward moveup(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response,
            SessionAPI session) throws ISPACException {
		
		return moverElementos(mapping,form,request,session,true);
	}
	
	private ActionForward moverElementos(ActionMapping mapping,ActionForm form,HttpServletRequest request,SessionAPI session, boolean subir) throws ISPACException {
		
		ClientContext cct = session.getClientContext();
		
 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_INV_STAGES_EDIT });

		EntityForm entityForm = (EntityForm) form;
		String[] stagesIds = entityForm.getMultibox();
		int initPos = -1;
		int lastPos = -1;
		
		if(stagesIds == null)
			throw new ISPACInfo("exception.info.list.noSelectedElements");
		
		for (int i = 0; i < stagesIds.length; i++) {
			String pos = stagesIds[i];
			if (initPos<0) initPos = Integer.parseInt(pos);
			if (i==stagesIds.length-1) lastPos = Integer.parseInt(pos); 
		}
		
		//		comprobar seleccion consecutiva
		int nextPos = -1;
		for (int i = 0; i < stagesIds.length; i++) {
			int pos = Integer.parseInt(stagesIds[i]);
			if (nextPos<0)
				nextPos = pos;
			else{
				nextPos++;
			}
			if (pos!=nextPos) throw new ISPACInfo("exception.info.list.consecutiveSelection");
		}
		
		IInvesflowAPI invesFlowAPI = session.getAPI();
		ICatalogAPI catalogAPI= invesFlowAPI.getCatalogAPI();
		
		// Ejecución en un contexto transaccional
		boolean bCommit = false;
		
		try {
	        // Abrir transacción
	        cct.beginTX();
		
			// Bloquear las fases para actualizar los órdenes
			//IItemCollection itemcol=catalogAPI.queryCTEntitiesForUpdate(ICatalogAPI.ENTITY_CT_STAGE," ORDER BY ORDEN ");
	        IItemCollection itemcol=catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_CT_STAGE," ORDER BY ORDEN ");
	
	        List originalPars = new ArrayList();
	        for (Iterator iter = itemcol.iterator(); iter.hasNext();) {
				IItem element = (IItem) iter.next();
				String id = element.getString("ID");
				String orden = element.getString("ORDEN");
				originalPars.add(new ParIdPos(id,Integer.parseInt(orden)));
			}
	        
	        List elementsToUpdate = moveElements(originalPars, initPos, lastPos, subir==true?-1:1);
	        for (Iterator iter = elementsToUpdate.iterator(); iter.hasNext();) {
				ParIdPos par = (ParIdPos) iter.next();
				IItem item=catalogAPI.getCTEntity(ICatalogAPI.ENTITY_CT_STAGE, Integer.parseInt(par.id));
				item.set("ORDEN",par.pos);
				item.store(session.getClientContext());			
			}
	        
			// Si todo ha sido correcto se hace commit de la transacción
	        bCommit = true;
			
			ActionForward ret = mapping.findForward("listado_tramites");
			return ret;
		}
		finally {
			cct.endTX(bCommit);
		}
	}
	
	public ActionForward movedown(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response,
            SessionAPI session) throws ISPACException {
		return moverElementos(mapping,form,request,session,false);
	}
	
}