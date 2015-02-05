/*
 * Created on 02-nov-2005
 *
 */
package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.ispacmgr.action.form.FormsForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class FormsAction extends Action {
    public ActionForward execute(ActionMapping mapping, 
            ActionForm form, 
            HttpServletRequest request, 
            HttpServletResponse response) throws Exception {

        FormsForm formsForm = (FormsForm)form;
        // recogemos el valor deldesplegable de búsqueda
        String urlForm = formsForm.getUrlForm();
        //int pcdId = formsForm.getPcdId();
        request.setAttribute("formSelect",urlForm);
        
        return mapping.findForward("success");
    
    }
}
