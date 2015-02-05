/*
 * Created on 18-may-2005
 *
 */
package ieci.tecdoc.mvc.action.archive;

import ieci.tecdoc.mvc.action.BaseAction;
import ieci.tecdoc.mvc.form.archive.DirForm;
import ieci.tecdoc.mvc.service.ServiceArchive;
import ieci.tecdoc.mvc.util.SessionHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Antonio María
 *
 */
public class DirProperties extends BaseAction{

    /* (non-Javadoc)
     * @see ieci.tecdoc.mvc.action.BaseAction#executeLogic(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    ServiceArchive serviceArchive;    
    protected ActionForward executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
    	String entidad=SessionHelper.getEntidad(request);
    	
    	DirForm dirForm = (DirForm) form;
        String dirId = request.getParameter("id");
        
        String submitted = request.getParameter("submitted");
        boolean isLdap = isLdapMethod(entidad);
        if (submitted == null)
        {
            serviceArchive = new ServiceArchive();
            int id = Integer.parseInt(dirId);
            dirForm.setId(id);
            int userId = getUserId(request);
            serviceArchive.loadDir(dirForm, isLdap, userId, false, entidad);
        }
        else
        {
            serviceArchive.saveDir(dirForm,false, entidad);
        }
        return mapping.findForward("success");
    }

}
