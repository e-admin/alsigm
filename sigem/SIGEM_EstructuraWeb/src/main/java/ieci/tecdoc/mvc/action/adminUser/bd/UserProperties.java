/*
 * Created on 28-mar-2005
 *
 */
package ieci.tecdoc.mvc.action.adminUser.bd;

import ieci.tecdoc.idoc.admin.api.user.Department;
import ieci.tecdoc.idoc.admin.api.user.User;
import ieci.tecdoc.idoc.admin.internal.Defs;
import ieci.tecdoc.idoc.admin.internal.DepartmentImpl;
import ieci.tecdoc.idoc.admin.internal.UserImpl;
import ieci.tecdoc.mvc.action.BaseAction;
import ieci.tecdoc.mvc.form.adminUser.bd.BasicForm;
import ieci.tecdoc.mvc.util.SessionHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Antonio María
 *
 */
public class UserProperties extends BaseAction{
    
	public ActionForward executeLogic(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception{

		String entidad=SessionHelper.getEntidad(request);
		
	    BasicForm userForm = (BasicForm) form;

	    String idObj = request.getParameter("id");
	    if (idObj != null ) {

	        cargarUsuario(userForm, Integer.parseInt(idObj), entidad);
	    }
	    
	    return mapping.findForward("success");
	}

	public void cargarUsuario (BasicForm form, int id, String entidad) throws Exception 
	{
        User user = new UserImpl();
        user.load(id, entidad);
                
        SimpleDateFormat formatter;
        String patron = "d-M-yyyy H.m.s";
        formatter = new SimpleDateFormat(patron);
        
        String nombreUsuario = user.getName();

        int idDepto = user.getDeptId();
        String nombreDepto = getDeptNameById(idDepto, entidad);

        String description = "";
        if ( user.getDescription() != null )
            description  = user.getDescription();
        
        Date creationDate = user.getCreationDate();
        String fechaCreacion = formatter.format(creationDate );
        
        int creatorId = user.getCreatorId();
        String creatorName = "SYSSUPERUSER";
        if (creatorId != 0)
            creatorName = getUserNameById(creatorId, entidad);
        
        Date updateDate = user.getUpdateDate();
        String fechaModificacion ="";
        if (updateDate != null)
            fechaModificacion = formatter.format(updateDate);
        
        int updaterId = user.getUpdaterId();
        String updaterName ="";
        if (updaterId > 0 && updaterId < Defs.NULL_ID)
            updaterName = getUserNameById(updaterId, entidad);
        else if (updaterId == 0 )
            updaterName = "SYSSUPERUSER";
        
        int estado = user.getState();

        form.setNombre(nombreUsuario);
        form.setId(id);
        form.setBelongToDept(nombreDepto);
        form.setDescripcion(description);
        form.setCreationDate(fechaCreacion);
        form.setCreatorName(creatorName);
        form.setUpdateDate(fechaModificacion);
        form.setUpdaterName(updaterName);
        if (estado != 0 )
            form.setBloqueado(true);
	}

	public String getUserNameById(int id, String entidad) throws Exception
    {
        User u = new UserImpl();
        u.load(id, entidad);
        String name = u.getName();
        return name;
    }

    public String getDeptNameById(int id, String entidad) throws Exception
    {
        Department dep = new DepartmentImpl();
        dep.load(id, entidad);
        String name = dep.getName();
        return name;
        
    }    

}
