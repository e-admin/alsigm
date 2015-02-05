/*
 * Created on 22-mar-2005
 *
 */
package ieci.tecdoc.mvc.action.adminUser.bd;

import ieci.tecdoc.idoc.admin.api.ObjFactory;
import ieci.tecdoc.idoc.admin.api.user.Department;
import ieci.tecdoc.idoc.admin.internal.Defs;
import ieci.tecdoc.mvc.action.BaseAction;
import ieci.tecdoc.mvc.form.adminUser.bd.BasicForm;
import ieci.tecdoc.mvc.service.adminUser.ServiceCommon;
import ieci.tecdoc.mvc.util.SessionHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

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
public class DeptProperties extends BaseAction{
    
    private static Logger logger = Logger.getLogger(DeptProperties.class);
    int userId;
    public ActionForward executeLogic(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception{

    	String entidad=SessionHelper.getEntidad(request);
    	
        BasicForm deptForm = (BasicForm) form;
        String objId = request.getParameter("id");
        userId = getUserId(request);
        int idDept = -1 ;
        if (objId != null )
            idDept = Integer.parseInt(objId);
        
        cargarDepartamento(deptForm, idDept, entidad);

        return mapping.findForward("success");
        
    }

        
    public void cargarDepartamento(BasicForm deptForm, int id, String entidad) throws Exception
    {
        Department dep = ObjFactory.createDepartment(userId,Defs.NULL_ID);
        
        if (logger.isDebugEnabled())
            logger.debug("Cargando Departamento: " + id);
        
        dep.load(id, entidad);
        // General
        SimpleDateFormat formatter;
        String patron = "d-M-yyyy H.m.s";
        formatter = new SimpleDateFormat(patron);
        
        String nombreDepto = dep.getName();
        int managerId = dep.getManagerId();
        
        String managerName = "SYSSUPERUSER";
        if (managerId != 0)
            managerName = ServiceCommon.getUserNameById(managerId, false, entidad);
        
        String description = "";
        if ( dep.getDescription() != null )
            description  = dep.getDescription();
        
        Date creationDate = dep.getCreationDate();
        String fechaCreacion = formatter.format(creationDate );
        
        
        int creatorId = dep.getCreatorId();
        String creatorName = "SYSSUPERUSER";
        if (creatorId != 0)
            creatorName = ServiceCommon.getUserNameById(creatorId, false, entidad);
        
        Date updateDate = dep.getUpdateDate();
        String fechaModificacion ="";
        if (updateDate != null)
            fechaModificacion = formatter.format(updateDate);
        
        int updaterId = dep.getUpdaterId();
        String updaterName ="";
         
        if (updaterId > 0 && updaterId < Defs.NULL_ID)
            updaterName = ServiceCommon.getUserNameById(updaterId, false, entidad);
        else if (updaterId == 0 )
            updaterName = "SYSSUPERUSER";
        
        deptForm.setNombre(nombreDepto);
        deptForm.setId(id);
        deptForm.setManagerName(managerName);
        deptForm.setDescripcion(description);
        
        deptForm.setCreationDate(fechaCreacion);
        deptForm.setCreatorName(creatorName);
        deptForm.setUpdateDate(fechaModificacion);
        deptForm.setUpdaterName(updaterName);

    }
}
