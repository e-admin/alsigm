/*
 * Created on 02-jun-2005
 *
 */
package ieci.tecdoc.mvc.action.archive;

import ieci.tecdoc.idoc.admin.api.ObjFactory;
import ieci.tecdoc.idoc.admin.api.archive.Archive;
import ieci.tecdoc.idoc.admin.api.archive.ArchiveAccess;
import ieci.tecdoc.mvc.action.BaseAction;
import ieci.tecdoc.mvc.dto.archive.Field;
import ieci.tecdoc.mvc.dto.archive.Fields;
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
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

/**
 * @author Antonio María
 *
 */
public class ArchiveNew extends BaseAction{

    /* (non-Javadoc)
     * @see ieci.tecdoc.mvc.action.BaseAction#executeLogic(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    ServiceArchive serviceArchive = null;
    private static Logger logger = Logger.getLogger(ArchiveNew.class);
    
    protected ActionForward executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
    	String entidad=SessionHelper.getEntidad(request);
    	ArchiveForm archiveForm = (ArchiveForm) form;
        
        ActionForward forward = null;
        String submitted = request.getParameter("submitted");
        String action = request.getParameter("action");
        
        
        
        if (submitted == null)
        {
            archiveForm.executeReset();
            
            
            
            int userId = getUserId(request);
            boolean isLdap = isLdapMethod(entidad);
	        boolean hasFtsConfig = hasFtsConfig();
	        String parentIdObj = request.getParameter("parentId");
	        int parentId = Integer.parseInt(parentIdObj);
	        
	        ArchiveAccess archiveAccess = ObjFactory.createArchiveAccess();
            boolean userCanCreateArch = archiveAccess.userCanCreateArch(userId, parentId, entidad);
            
            if (!userCanCreateArch){
                ActionMessages mensajes = new ActionMessages();
                ActionMessage msg = new ActionMessage( String.valueOf(MvcError.EC_NOT_ENOUGH_PERMISSIONS));
                mensajes.add(ActionMessages.GLOBAL_MESSAGE, msg);
                saveMessages(request,mensajes);
            }
            else
            {
                Archive archive = ObjFactory.createArchive(userId,parentId, isLdap);
    	        serviceArchive = new ServiceArchive(archive, isLdap);
    	        
    	        General general = serviceArchive.getGeneral(Constantes.NEW, entidad);
    	        archiveForm.setGeneral(general);
    	        archiveForm.setHasFtsConfig(hasFtsConfig);    
            }
	        forward = mapping.findForward("input1");
        }
        else if (submitted.equals("input1")) // Paso 2 - Campos
        {
            Fields fields = serviceArchive.getFields(Constantes.NEW);
            archiveForm.setFields(fields );
            
            forward = mapping.findForward("input2");
        }
        else if (submitted.equals("input2")){
            if (action.equals("addField"))
            {
                List fldsList = archiveForm.getFields().getFldsList();
                Field newField = serviceArchive.getNewField();
                fldsList.add(newField);
                forward = mapping.findForward("input2");
            }
            else if (action.equals("deleteField"))
            {
                String index = request.getParameter("deleteField");
                int idx = Integer.parseInt(index);
                serviceArchive.deleteField(idx);
                forward = mapping.findForward("input2");
            }
            else{ // Paso 3 -Indices
                Indexs indexs = serviceArchive.getIndexs(Constantes.NEW);
                archiveForm.setIndexs(indexs);
                
                forward = mapping.findForward("input3");
            }
                
        }else if (submitted.equals("input3")){
            
            if (action.equals("addIndex"))
            {
                List indexsList = archiveForm.getIndexs().getIndexsList();
                Index newIndex = serviceArchive.getNewIndex();
                indexsList.add(newIndex);
                forward = mapping.findForward("input3");
            }
            else if (action.equals("deleteIndex"))
            {
                String index = request.getParameter("deleteIndex");
                int idx = Integer.parseInt(index);
                serviceArchive.deleteIndex(idx);
                forward = mapping.findForward("input3");
            }
            else { // Paso 4 - Almacenamiento
                forward = mapping.findForward("input4");
            }
        }else if (submitted.equals("input4")){ // Crear archivador
            serviceArchive.createArchive(entidad);
            forward = mapping.findForward("success");

            ActionMessages messages = new ActionMessages();
        	ActionMessage msg = new ActionMessage("message.archive.new");
        	messages.add(Globals.MESSAGES_KEY, msg);
        	
        	saveMessages(request, messages);

            
        }
            
            
        return forward;
        
    } // fin execute logic

}

    

