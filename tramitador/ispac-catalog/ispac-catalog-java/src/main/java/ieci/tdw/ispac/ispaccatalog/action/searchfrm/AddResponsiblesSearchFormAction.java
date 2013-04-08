/*
 * Created on Nov 12, 2004 by IECISA
 *
 */
package ieci.tdw.ispac.ispaccatalog.action.searchfrm;

import java.util.ArrayList;
import java.util.List;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaccatalog.action.BaseAction;
import ieci.tdw.ispac.ispaccatalog.action.form.SelectForm;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AddResponsiblesSearchFormAction extends BaseAction
{
    public ActionForward executeAction(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response,
            SessionAPI session) throws Exception
    {
    	
 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_COMP_SEARCH_FORMS_EDIT });

        SelectForm selectForm = (SelectForm) form;
        if(request.getParameterValues("seleccion")!=null){
			selectForm.setUids(request.getParameterValues("seleccion"));
		}
        String[] uids = selectForm.getUids();
        String id = request.getParameter("id");
        
        IInvesflowAPI invesflowapi = session.getAPI();
    	ICatalogAPI catalogAPI = invesflowapi.getCatalogAPI();
    	 List listaResponsables=new ArrayList();
         if(uids.length>0){
         	//Buscamos los responsables actualmente asociados al formulario de búsqueda
         	listaResponsables=catalogAPI.getSearchFormOrganization(Integer.parseInt(id));
         
         }
         int i, j;
         for (i = 0; i < uids.length; i++) {
         	boolean encontrado=false;
         	for(j=0; j<listaResponsables.size() && !encontrado;j++){
         		if(StringUtils.equalsIgnoreCase(uids[i], ((ItemBean)listaResponsables.get(j)).getString("UID"))){
         			encontrado=true;
         		}
         	}
         	if(!encontrado){
	        	IItem item = catalogAPI.createCTEntity(ICatalogAPI.ENTITY_CT_SEARCHFORM_ORG);
	        	item.set("ID_SEARCHFRM", id);
	        	item.set("UID_USR", uids[i]);
	        	item.store(session.getClientContext());
        	}
    }

        return mapping.findForward("closeFrame");
    }
}