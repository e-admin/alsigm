/*
 * Created on 12-may-2005
 *
 */
package ieci.tecdoc.mvc.action.archive;

import ieci.tecdoc.core.exception.IeciTdException;
import ieci.tecdoc.idoc.admin.api.ObjFactory;
import ieci.tecdoc.idoc.admin.api.archive.Archive;
import ieci.tecdoc.idoc.admin.api.archive.ArchivePerms;
import ieci.tecdoc.idoc.admin.internal.Defs;
import ieci.tecdoc.mvc.action.BaseAction;
import ieci.tecdoc.mvc.dto.archive.Permission;
import ieci.tecdoc.mvc.dto.common.AccessDTO;
import ieci.tecdoc.mvc.dto.common.UserAccessDTO;
import ieci.tecdoc.mvc.error.MvcError;
import ieci.tecdoc.mvc.form.archive.AddPermsForm;
import ieci.tecdoc.mvc.service.ServiceArchive;
import ieci.tecdoc.mvc.service.adminUser.ServiceCommon;
import ieci.tecdoc.mvc.util.SessionHelper;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Antonio María
 *  
 */
public class ArchiveAddPerms extends BaseAction {

    /*
     * (non-Javadoc)
     * 
     * @see ieci.tecdoc.mvc.action.BaseAction#executeLogic(org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm,
     *      javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    private static Logger logger = Logger.getLogger(ArchiveAddPerms.class);

    protected ActionForward executeLogic(ActionMapping mapping,
            ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

    	String entidad=SessionHelper.getEntidad(request);
    	
        AddPermsForm addPermsForm = (AddPermsForm) form;

        String submitted = request.getParameter("submitted");
        HttpSession session = request.getSession();        
        AccessDTO accessDTO = null;
        if (submitted == null) {
            
            boolean isLdap = isLdapMethod(entidad);
            ServiceArchive serviceArchive = new ServiceArchive();
            
            String archiveId = session.getAttribute("archiveId").toString();
            int userId = getUserId(request);
            int archId = Integer.parseInt(archiveId);
            
            try {
                accessDTO = serviceArchive.getAccessDTO(request, isLdap, entidad);
                Archive archive = ObjFactory.createArchive(0, Defs.NULL_ID, isLdap);
                archive.load(archId, entidad);
                
                int acsId = archive.getAcsId();

                int permissionArray[] = null;
                int destType = accessDTO.getType();
                int destId = accessDTO.getId();
                if (destType == Defs.DESTINATION_USER) {
                    UserAccessDTO userAccessDTO = (UserAccessDTO) accessDTO;
                    if (userAccessDTO.getIsSystemSuperUser() || userAccessDTO.getIdocProfileSuperuser()) {
                        permissionArray = userAccessDTO.getPermission().getPermissionArray();
                        // NO tngo claro si tb ||
                        // userAccessDTO.getIdocProfileManager()
                    } else {
                        ArchivePerms archivePerms = ObjFactory.createArchivePerms();
                        int archivePerm = archivePerms.loadPerms(destType, destId, archId, entidad);
                        permissionArray = ServiceCommon.getPermmissionArray(archivePerm);
                    }
                } else if (destType == Defs.DESTINATION_GROUP || destType == Defs.DESTINATION_DEPT) {
                    ArchivePerms archivePerms = ObjFactory.createArchivePerms();
                    int archivePerm = archivePerms.loadPerms(destType, destId, archId, entidad);
                    permissionArray = ServiceCommon.getPermmissionArray(archivePerm);
                }

                String destName = accessDTO.getName();
                List permissionChoices = serviceArchive.getPermissionChoices();

                Permission permission = new Permission();
                permission.setPermissionArray(permissionArray);
                permission.setPermissionChoices(permissionChoices);

                String archiveName = archive.getName();

                addPermsForm.setArchiveName(archiveName);
                addPermsForm.setAcsId(acsId);
                addPermsForm.setArchiveId(archId);
                addPermsForm.setPermission(permission);
                addPermsForm.setDestName(destName);
                addPermsForm.setShowForm(true);
                addPermsForm.setAccessDTO(accessDTO);
                
                session.setAttribute("permmissionArrayOld", permissionArray);
            }// fin try
            catch (IeciTdException e) {
                if (e.getErrorCode().equals( String.valueOf(MvcError.EC_OBJ_LDAP_NOT_EXITS))) {
                    String message = e.getMessage();
                    postGlobalMessage(String.valueOf(MvcError.EC_OBJ_LDAP_NOT_EXITS), request);
                    addPermsForm.setShowForm(false);
                    return mapping.findForward("success");
                } else
                    throw e;

            } // fin catch 
        }// fin submitted == null
        else
        {
            // salvar modificaciones
            int permissionArrayOld [] = ( int[]) session.getAttribute("permmissionArrayOld");
            int permissionArrayNew [] = addPermsForm.getPermission().getPermissionArray();
            session.setAttribute("permmissionArrayOld", permissionArrayNew);
            int permissionToAdd [] = ServiceCommon.difference(permissionArrayNew, permissionArrayOld);
            int permissionToDelete[] = ServiceCommon.difference(permissionArrayOld, permissionArrayNew);
            
            ServiceArchive serviceArchive = new ServiceArchive();
            List permissionChoices = serviceArchive.getPermissionChoices();
            addPermsForm.getPermission().setPermissionChoices(permissionChoices); // Se ha borrado tras el reset

            
            accessDTO = addPermsForm.getAccessDTO();
            int destType = accessDTO.getType();
            int destId = accessDTO.getId();
            int acsId = addPermsForm.getAcsId();
            int archiveId = addPermsForm.getArchiveId();
            
            int permsToAdd = ServiceCommon.getPermission(permissionToAdd);
            int permsToDelete = ServiceCommon.getPermission(permissionToDelete);
            ArchivePerms archivePerms = ObjFactory.createArchivePerms();
            if (permsToAdd != 0)
                archivePerms.addPerms(destType, destId, acsId, permsToAdd, entidad );
            if (permsToDelete != 0)
                archivePerms.deletePerms(destType, destId, archiveId, permsToDelete, entidad );
            //accessDTO.getPermission().setPermissionArray(permissionArrayNew);
        }
        request.setAttribute("accessDTO", accessDTO);
        return mapping.findForward("success");
    }// fin execute

}