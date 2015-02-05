package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.ispacmgr.action.form.SearchForm;
import ieci.tdw.ispac.ispacmgr.common.constants.ActionsConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ShowSearchAction extends Action {
	
    public ActionForward execute(ActionMapping mapping, 
            					 ActionForm form, 
            					 HttpServletRequest request, 
            					 HttpServletResponse response) throws Exception {
    	
		// Formulario de búsqueda
		SearchForm searchForm = (SearchForm) form;
		
		// Establecer en sesión el formulario de búsqueda con los parámetros de la nueva búsqueda
		request.getSession().setAttribute(ActionsConstants.FORM_SEARCH_RESULTS, searchForm);
        
        return mapping.findForward("success");
    }
    
}
