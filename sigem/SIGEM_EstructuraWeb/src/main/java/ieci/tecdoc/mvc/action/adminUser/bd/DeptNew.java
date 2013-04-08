/*
 * Created on 30-mar-2005
 *
 */
package ieci.tecdoc.mvc.action.adminUser.bd;

import ieci.tecdoc.core.exception.IeciTdException;
import ieci.tecdoc.idoc.admin.api.ObjFactory;
import ieci.tecdoc.idoc.admin.api.user.Department;
import ieci.tecdoc.idoc.admin.api.user.UserAccess;
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
public class DeptNew extends BaseAction {

    /* (non-Javadoc)
     * @see ieci.tecdoc.mvc.action.BaseAction#executeLogic(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    private static Logger logger = Logger.getLogger(DeptNew.class);
    int userId;
    protected ActionForward executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
    	String entidad=SessionHelper.getEntidad(request);
    	
        boolean isCancelled = isCancelled(request);
        String submitted = request.getParameter("submitted");
        BasicForm deptForm = (BasicForm) form;
        String objId = request.getParameter("idPadre"); // Id del departamento padre
        userId = getUserId(request);
        
        int idDept = 0;
        idDept = Integer.parseInt(objId);

        UserAccess userAccess = ObjFactory.createUserAccess();
        boolean userCanCreateDept = userAccess.userCanCreateDept(userId, idDept, entidad);
        if (!userCanCreateDept)
            throw new IeciTdException(String.valueOf(MvcError.EC_NOT_CAN_CREATE_DEPT), null);
        
        if (!isCancelled)
        {
	        if (submitted != null ){
		        if (logger.isDebugEnabled()) 
		            logger.debug("Creando departamento colgando de: " + idDept);
		        guardarDatos(deptForm, entidad);
		        request.setAttribute("tipo","Departamento");
		        return mapping.findForward("success");
	        }
	        else{
	            deptForm.setId(idDept);
	            // Recuperar la lista de administradores
	            
	            List adminUsers = ServiceCommon.getAdminUsers(idDept, Constantes.DEPARTAMENT, entidad);
	            ListDTO listDTO = new ListDTO();
	            listDTO.setList(adminUsers);
	            request.setAttribute("adminUsersDTO", listDTO );
	            return mapping.findForward("ok");
	        }
        }else
            return mapping.findForward("cancel");
        
    }
    
    /**
     * @param dept
     * @param deptForm
     */
    private void guardarDatos(BasicForm form, String entidad) throws Exception{
       
        int id=form.getId(); // Aqui se ha guardado el id del dpto padre
        Department dept = ObjFactory.createDepartment(userId, id);
        
        dept.setName(form.getNombre());
        dept.setDescription(form.getDescripcion());
        
        String managerId = form.getManagerId();
        dept.setManagerId(Integer.parseInt(managerId));

        dept.store(entidad);
        
        if (logger.isDebugEnabled())
        {
            logger.debug("Departamento " + dept.getName() + " creado");
        }
    }

}
