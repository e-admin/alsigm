/*
 * Created on 22-mar-2005
 *
 */
package ieci.tecdoc.mvc.action.adminUser.bd;

import ieci.tecdoc.core.exception.IeciTdException;
import ieci.tecdoc.idoc.admin.api.ObjFactory;
import ieci.tecdoc.idoc.admin.api.user.Department;
import ieci.tecdoc.idoc.admin.api.user.Permission;
import ieci.tecdoc.idoc.admin.api.user.Permissions;
import ieci.tecdoc.idoc.admin.api.user.UserAccess;
import ieci.tecdoc.idoc.admin.api.user.UserDefs;
import ieci.tecdoc.idoc.admin.api.user.UserProfile;
import ieci.tecdoc.idoc.admin.api.user.UserProfiles;
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

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Antonio María
 *
 */
public class DeptEdit extends BaseAction{

    int userId;
    public ActionForward executeLogic(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception{

    	String entidad=SessionHelper.getEntidad(request);
    	
        BasicForm deptForm = (BasicForm) form;
        String objId = request.getParameter("id");
        if (isCancelled(request))
            return mapping.findForward("cancel");
        userId = getUserId(request);
        
        int idDept = -1;
        if (objId != null)
            idDept = Integer.parseInt(objId);
        
        
        List adminUsers = ServiceCommon.getAdminUsers(idDept, Constantes.DEPARTAMENT, entidad);
        ListDTO listDTO = new ListDTO();
        listDTO.setList(adminUsers);
        request.setAttribute("adminUsersDTO", listDTO );
        
        String submited = request.getParameter("submitted");
        if (submited == null )
        {
            cargarDepartamento(deptForm, idDept, entidad);
        }
        else
        {
            UserAccess userAccess = ObjFactory.createUserAccess();
            boolean userCanEditDept = userAccess.userCanEditDept(userId, idDept, entidad);
            if (!userCanEditDept)
                throw new IeciTdException(String.valueOf(MvcError.EC_NOT_CAN_EDIT_DEPT), null);
            guardarDatos(deptForm, entidad);
        }
        return mapping.findForward("success");
    }

    public void guardarDatos(BasicForm form, String entidad) throws Exception {
        int				userID=0;
        UserProfiles    profiles=null;
        UserProfile     profile=null;
        
        Department dep = ObjFactory.createDepartment(userId,Defs.NULL_ID);
        
        int idDepto = form.getId();
        dep.load(idDepto, entidad);
        
        String idManager = form.getManagerId();
        dep.setManagerId(Integer.parseInt(idManager));
        
        
        dep.setName(form.getNombre());
        dep.setDescription(form.getDescripcion());
        
        Permissions perms = dep.getPermissions();
        Permission perm = perms.getProductPermission(UserDefs.PRODUCT_IDOC); // Permisos sobre InvesDoc
        int permisos = 0;
    
        if (form.isIdocConsulta())
            permisos += UserDefs.PERMISSION_QUERY;
        if (form.isIdocModificacion())
            permisos += UserDefs.PERMISSION_UPDATE;
        if (form.isIdocCreacion())
            permisos += UserDefs.PERMISSION_CREATION;
        if (form.isIdocBorrado())
            permisos += UserDefs.PERMISSION_DELETION;
        if (form.isIdocImpresion())
            permisos += UserDefs.PERMISSION_PRINTING;
        perm.setPermission( permisos );
        dep.store(entidad);
        
    }    
    public void cargarDepartamento(BasicForm deptForm, int id, String entidad) throws Exception
    {
        
        Department dep = ObjFactory.createDepartment(userId,Defs.NULL_ID);
        dep.load(id, entidad);
        
        deptForm.setId(id);
        // General
        deptForm.setNombre(dep.getName());
        String description = dep.getDescription();
        if (description != null)
            deptForm.setDescripcion(description);
        int idManager = dep.getManagerId();
        deptForm.setManagerId(String.valueOf(idManager));
        
        // Permisos Genericos 
        Permissions perms =dep.getPermissions();
        Permission perm = perms.getProductPermission(UserDefs.PRODUCT_IDOC); // Permisos sobre InvesDoc
        int permisos = perm.getPermission();
        
        if ( (permisos & UserDefs.PERMISSION_QUERY) == UserDefs.PERMISSION_QUERY ) {
            deptForm.setIdocConsulta(true);
        }
        if ( (permisos & UserDefs.PERMISSION_UPDATE) == UserDefs.PERMISSION_UPDATE) {
            deptForm.setIdocModificacion(true);
        }
        if ( (permisos & UserDefs.PERMISSION_CREATION) == UserDefs.PERMISSION_CREATION) {
            deptForm.setIdocCreacion(true);
        }
        if ( (permisos & UserDefs.PERMISSION_DELETION) == UserDefs.PERMISSION_DELETION) {
            deptForm.setIdocBorrado(true);
        }
        if ( (permisos & UserDefs.PERMISSION_PRINTING)== UserDefs.PERMISSION_PRINTING){
            deptForm.setIdocImpresion(true);
	    }

    }    

}
