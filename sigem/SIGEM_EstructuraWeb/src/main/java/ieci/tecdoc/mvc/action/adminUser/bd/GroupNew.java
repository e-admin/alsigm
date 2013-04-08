/*
 * Created on 31-mar-2005
 *
 */
package ieci.tecdoc.mvc.action.adminUser.bd;

import ieci.tecdoc.core.exception.IeciTdException;
import ieci.tecdoc.idoc.admin.api.ObjFactory;
import ieci.tecdoc.idoc.admin.api.user.Group;
import ieci.tecdoc.idoc.admin.api.user.Permission;
import ieci.tecdoc.idoc.admin.api.user.Permissions;
import ieci.tecdoc.idoc.admin.api.user.UserAccess;
import ieci.tecdoc.idoc.admin.api.user.UserDefs;
import ieci.tecdoc.idoc.admin.internal.Defs;
import ieci.tecdoc.mvc.action.BaseAction;
import ieci.tecdoc.mvc.dto.common.ListDTO;
import ieci.tecdoc.mvc.error.MvcError;
import ieci.tecdoc.mvc.form.adminUser.bd.BasicForm;
import ieci.tecdoc.mvc.service.adminUser.ServiceCommon;
import ieci.tecdoc.mvc.util.Constantes;
import ieci.tecdoc.mvc.util.SessionHelper;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Antonio María
 *
 */
public class GroupNew extends BaseAction {

    /* (non-Javadoc)
     * @see ieci.tecdoc.mvc.action.BaseAction#executeLogic(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    private static Logger logger = Logger.getLogger(GroupNew.class);
    int userId;
    protected ActionForward executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
    	String entidad=SessionHelper.getEntidad(request);
    	
        userId = getUserId(request);
        boolean isCancelled = isCancelled(request);

        UserAccess userAccess = ObjFactory.createUserAccess(); 
    	boolean userCanCreateGroup = userAccess.userCanCreateGroup(userId, entidad);
        if (!userCanCreateGroup)
            throw new IeciTdException(String.valueOf(MvcError.EC_NOT_CAN_CREATE_GROUP), null);
        
        String submitted = request.getParameter("submitted");
        BasicForm groupForm = (BasicForm) form;

        if (!isCancelled){
	        if (submitted != null ){
		        guardarDatos( groupForm, entidad);
		        request.setAttribute("tipo","Grupo");
		        return mapping.findForward("success");
	        }
	        else{
	            // Recuperar la lista de administradores
	            List adminUsers = ServiceCommon.getAdminUsers(Defs.NULL_ID, Constantes.GROUP, entidad);
	            ListDTO listDTO = new ListDTO();
	            listDTO.setList(adminUsers);
	            request.setAttribute("adminUsersDTO", listDTO );
	            return mapping.findForward("ok");
	        }
	            
        }
        else{
            return mapping.findForward("cancel");            
        }
    }
    /**
     * @param group
     * @param groupForm
     */
    private void guardarDatos( BasicForm form, String entidad) throws Exception{
        
        Group group = ObjFactory.createGroup(userId);
        // General
        group.setName(form.getNombre());
        group.setDescription(form.getDescripcion());
        group.store(entidad);
        
        if (logger.isDebugEnabled())
        {
            logger.debug("Departamento " + group.getName() + " creado");
        }
    }

}
