package ieci.tdw.ispac.ispaccatalog.action.signprocess;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaccatalog.action.BaseDispatchAction;
import ieci.tdw.ispac.ispaccatalog.action.form.EntityForm;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.utils.OrderUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class OrderSignerAction extends BaseDispatchAction{
	
	public ActionForward subir(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, SessionAPI session) throws Exception {
		return moverElementos(mapping,form,request, session,true);
	}
	
	public ActionForward bajar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, SessionAPI session) throws Exception {
		return moverElementos(mapping,form,request, session,false);
	}
	
	
	private ActionForward moverElementos(ActionMapping mapping,ActionForm form, HttpServletRequest request, SessionAPI session, boolean subir) throws ISPACException {
	
		ClientContext cct = session.getClientContext();
		
 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, cct, new int[] {
				ISecurityAPI.FUNC_INV_SIGN_CIRCUITS_EDIT });

		EntityForm defaultForm = (EntityForm) form;
		int regId=0;
		if(StringUtils.isNotBlank(request.getParameter("regId"))){
			regId = Integer.parseInt(request.getParameter("regId"));
		}
		if(regId==0) {
			regId=Integer.parseInt(defaultForm.getKey());
		}
		String[] seleccion = defaultForm.getMultibox();

		// Ejecución en un contexto transaccional
		boolean bCommit = false;
		
		try {
	        // Abrir transacción
	        cct.beginTX();
	        
	        //Obtengo la lista de firmantes 
	        ICatalogAPI catalogAPI= cct.getAPI().getCatalogAPI();
	        IItemCollection itemcol = catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_SIGNPROCESS_DETAIL, "WHERE ID_CIRCUITO = " + regId + " ORDER BY ID_PASO");

	        
	       
	        List listPars = new ArrayList();
	        List checkChange=new ArrayList();
	       if(seleccion!=null){
	    	   OrderUtil.ordenar(subir, "ID_PASO", itemcol, seleccion, listPars, checkChange);
	       }
	        int i=0;
	        
	     
	        for (i=0; i<listPars.size();i++) {
	        	IItem item=(IItem) listPars.get(i);
	         //se puede utilizar la variable i xq se ha usuado el recorrido del iterador para inicializar esta estructura.
	        	if( checkChange.get(i).toString().equals("true") ){
			
				   item.store(session.getClientContext());	
	        	}
	        	
	        	
			}
	        
			// Si todo ha sido correcto se hace commit de la transacción
	        bCommit = true;
			
	    	return mapping.findForward("success_store");
		}
		finally {
			cct.endTX(bCommit);
		}
	}
	
}