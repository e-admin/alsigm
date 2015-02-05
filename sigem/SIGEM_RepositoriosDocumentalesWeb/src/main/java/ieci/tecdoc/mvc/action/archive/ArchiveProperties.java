/*
 * Created on 05-may-2005
 *
 */
package ieci.tecdoc.mvc.action.archive;

import ieci.tecdoc.idoc.admin.api.ObjFactory;
import ieci.tecdoc.idoc.admin.api.archive.Archive;
import ieci.tecdoc.idoc.admin.internal.Defs;
import ieci.tecdoc.mvc.action.BaseAction;
import ieci.tecdoc.mvc.dto.archive.Advanced;
import ieci.tecdoc.mvc.dto.archive.Fields;
import ieci.tecdoc.mvc.dto.archive.General;
import ieci.tecdoc.mvc.form.archive.ArchiveForm;
import ieci.tecdoc.mvc.service.ServiceArchive;
import ieci.tecdoc.mvc.util.Constantes;
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
public class ArchiveProperties extends BaseAction{

    /* (non-Javadoc)
     * @see ieci.tecdoc.mvc.action.BaseAction#executeLogic(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    protected ActionForward executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
    	String entidad=SessionHelper.getEntidad(request);
        ArchiveForm archiveForm = (ArchiveForm) form;
        
        String objId = request.getParameter("id");
        int id = Integer.parseInt(objId);
        
        boolean isLdap = isLdapMethod(entidad);
        Archive archive = ObjFactory.createArchive(Defs.NULL_ID,Defs.NULL_ID, isLdap);
        
        archive.load(id, entidad);
        ServiceArchive serviceArchive = new ServiceArchive(archive, isLdap);

        General general = serviceArchive.getGeneral(Constantes.PROPERTIES, entidad);
        Fields fields = serviceArchive.getFields(Constantes.PROPERTIES);
        Advanced advanced = serviceArchive.getAdvanced(entidad);
        archiveForm.setGeneral(general);
        archiveForm.setFields(fields);
        archiveForm.setAdvanced(advanced);
         
        
        return mapping.findForward("success");
    }

}
