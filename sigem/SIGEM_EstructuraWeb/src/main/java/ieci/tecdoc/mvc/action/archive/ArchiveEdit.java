/*
 * Created on 05-may-2005
 *
 */
package ieci.tecdoc.mvc.action.archive;

import ieci.tecdoc.core.exception.IeciTdException;
import ieci.tecdoc.idoc.admin.api.ObjFactory;
import ieci.tecdoc.idoc.admin.api.archive.Archive;
import ieci.tecdoc.idoc.admin.api.archive.ArchiveAccess;
import ieci.tecdoc.idoc.admin.internal.Defs;
import ieci.tecdoc.mvc.action.BaseAction;
import ieci.tecdoc.mvc.dto.archive.Field;
import ieci.tecdoc.mvc.dto.archive.Fields;
import ieci.tecdoc.mvc.dto.archive.Folder;
import ieci.tecdoc.mvc.dto.archive.General;
import ieci.tecdoc.mvc.dto.archive.Index;
import ieci.tecdoc.mvc.dto.archive.Indexs;
import ieci.tecdoc.mvc.error.MvcError;
import ieci.tecdoc.mvc.form.archive.ArchiveForm;
import ieci.tecdoc.mvc.service.ServiceArchive;
import ieci.tecdoc.mvc.util.Constantes;
import ieci.tecdoc.mvc.util.SessionHelper;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

/**
 * @author Antonio María
 *
 */
public class ArchiveEdit extends BaseAction{

    /* (non-Javadoc)
     * @see ieci.tecdoc.mvc.action.BaseAction#executeLogic(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    ServiceArchive serviceArchive = null;
    private static Logger logger = Logger.getLogger(ArchiveEdit.class);
    protected ActionForward executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
    	String entidad=SessionHelper.getEntidad(request);
    	
    	ArchiveForm archiveForm = (ArchiveForm) form;
        
        String action = request.getParameter("action");
        String tabSelected = request.getParameter("tabSelected");
        if (tabSelected == null )
            tabSelected = "1";
        request.setAttribute("tabSelected", tabSelected );
            
        if (action == null )
        {
	        String objId = request.getParameter("id");
	        int id = Integer.parseInt(objId);
	        boolean isLdap = isLdapMethod(entidad);
	        boolean hasFtsConfig = hasFtsConfig();
	        
	        int userId = getUserId(request);
	        
	        ArchiveAccess archiveAccess = ObjFactory.createArchiveAccess();
            boolean userCanEditArch = archiveAccess.userCanEditArch(userId, id, entidad);
            if (!userCanEditArch){
                ActionMessages mensajes = new ActionMessages();
                ActionMessage msg = new ActionMessage( String.valueOf(MvcError.EC_NOT_ENOUGH_PERMISSIONS));
                mensajes.add(ActionMessages.GLOBAL_MESSAGE, msg);
                saveMessages(request,mensajes);
            }
            else
            {
    	        Archive archive = ObjFactory.createArchive(Defs.NULL_ID,Defs.NULL_ID, isLdap);
    	        archive.load(id, entidad);
    	        serviceArchive = new ServiceArchive(archive, isLdap);
    	        
    	        General general = serviceArchive.getGeneral(Constantes.EDITION, entidad);
    	        Fields fields = serviceArchive.getFields(Constantes.EDITION);
    	        Indexs indexs = serviceArchive.getIndexs(Constantes.EDITION);
    	        Folder folder = serviceArchive.getFolder();
    	        
    	        archiveForm.setHasFtsConfig(hasFtsConfig);
    	        archiveForm.setFields(fields);
    	        archiveForm.setGeneral(general);
    	        archiveForm.setIndexs(indexs);
    	        archiveForm.setFolder(folder);                
            }
	        

	        
        }
        else if (action.equals("addField"))
        {
            List fldsList = archiveForm.getFields().getFldsList();
            Field newField = serviceArchive.getNewField();
            fldsList.add(newField);
        }
        else if (action.equals("addIndex"))
        {
            List indexsList = archiveForm.getIndexs().getIndexsList();
            Index newIndex = serviceArchive.getNewIndex();
            indexsList.add(newIndex);
        }
        else if (action.equals("submitted"))
        {
            try {
            serviceArchive.updateArchive(entidad);
            
            }catch (IeciTdException e){
                ActionMessages mensajes = new ActionMessages();
                
                ActionMessage msg = new ActionMessage(e.getErrorCode());
                mensajes.add(ActionMessages.GLOBAL_MESSAGE, msg);
                saveMessages(request,mensajes);
            }
        }
        else if (action.equals("updateField"))
        {
            String index = request.getParameter("updateField");
            int idx = Integer.parseInt(index);
            serviceArchive.updateField(idx);
            
        }
        else if (action.equals("deleteField"))
        {
            String index = request.getParameter("deleteField");
            int idx = Integer.parseInt(index);
            serviceArchive.deleteField(idx);
        }
        else if (action.equals("updateIndex"))
        {
            String index = request.getParameter("updateIndex");
            int idx = Integer.parseInt(index);
            serviceArchive.updateIndex(idx);
        }
        else if (action.equals("deleteIndex"))
        {
            String index = request.getParameter("deleteIndex");
            int idx = Integer.parseInt(index);
            serviceArchive.deleteIndex(idx);
        }
        return mapping.findForward("success");
    }

}
