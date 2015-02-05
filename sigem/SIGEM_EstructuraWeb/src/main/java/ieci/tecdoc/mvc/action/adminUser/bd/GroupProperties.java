/*
 * Created on 24-mar-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ieci.tecdoc.mvc.action.adminUser.bd;

import ieci.tecdoc.idoc.admin.api.ObjFactory;
import ieci.tecdoc.idoc.admin.api.user.Group;
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
 * @author antmaria
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GroupProperties extends BaseAction {
    
    private static Logger logger = Logger.getLogger(GroupProperties.class);    
    
    int userId;
	public ActionForward executeLogic(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception{

		String entidad=SessionHelper.getEntidad(request);
		
	    userId = getUserId(request);
        BasicForm groupForm = (BasicForm) form;
        String objId = request.getParameter("id");
        
        int id = -1 ;
        if (objId != null )
            id = Integer.parseInt(objId);
        
        cargarGrupo(groupForm, id, entidad);

        return mapping.findForward("success");        
    }

    /**
     * @param groupForm
     * @param id
     */
    private void cargarGrupo(BasicForm groupForm, int id, String entidad) throws Exception {
        
        Group group = ObjFactory.createGroup(userId); 
        
        if (logger.isDebugEnabled())
            logger.debug("Cargando Grupo: " + id);
        
        group.load(id, entidad);
        
        SimpleDateFormat formatter;
        String patron = "d-M-yyyy H.m.s";
        formatter = new SimpleDateFormat(patron);
        
        String nombre = group.getName();
        
        
        int managerId = group.getManagerId();
        
        String managerName = "SYSSUPERUSER";
        if (managerId != 0)
            managerName = ServiceCommon.getUserNameById(managerId, false, entidad);

        String description = "";
        if ( group.getDescription() != null )
            description  = group.getDescription();
        
        Date creationDate = group.getCreationDate();
        String fechaCreacion = formatter.format(creationDate );
        
        int creatorId = group.getCreatorId();
        String creatorName = "SYSSUPERUSER";
        if (creatorId != 0)
            creatorName = ServiceCommon.getUserNameById(creatorId, false, entidad);
        
        Date updateDate = group.getUpdateDate();
        String fechaModificacion ="";
        if (updateDate != null)
            fechaModificacion = formatter.format(updateDate);
        
        int updaterId = group.getUpdaterId();
        String updaterName ="";
         
        if (updaterId > 0 && updaterId < Defs.NULL_ID)
            updaterName = ServiceCommon.getUserNameById(updaterId, false, entidad);
        else if (updaterId == 0 )
            updaterName = "SYSSUPERUSER";
        
        groupForm.setNombre(nombre);
        groupForm.setId(id);
        groupForm.setManagerName(managerName);
        groupForm.setDescripcion(description);
        
        groupForm.setCreationDate(fechaCreacion);
        groupForm.setCreatorName(creatorName);
        groupForm.setUpdateDate(fechaModificacion);
        groupForm.setUpdaterName(updaterName);
    }
}
