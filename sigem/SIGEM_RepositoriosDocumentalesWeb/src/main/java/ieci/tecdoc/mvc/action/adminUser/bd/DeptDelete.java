/*
 * Created on 24-mar-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ieci.tecdoc.mvc.action.adminUser.bd;

import ieci.tecdoc.core.exception.IeciTdException;
import ieci.tecdoc.idoc.admin.api.ObjFactory;
import ieci.tecdoc.idoc.admin.api.user.Department;
import ieci.tecdoc.idoc.admin.api.user.UserAccess;
import ieci.tecdoc.idoc.admin.api.user.Users;
import ieci.tecdoc.idoc.admin.internal.Defs;
import ieci.tecdoc.mvc.action.BaseAction;
import ieci.tecdoc.mvc.error.MvcError;
import ieci.tecdoc.mvc.form.adminUser.bd.BasicForm;
import ieci.tecdoc.mvc.util.Constantes;
import ieci.tecdoc.mvc.util.SessionHelper;

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
 * @author antmaria
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DeptDelete extends BaseAction{
    private static Logger logger = Logger.getLogger(DeptDelete.class);
    public ActionForward executeLogic(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception{
    	
    	String entidad=SessionHelper.getEntidad(request);
    	
    	BasicForm deptForm = (BasicForm) form;
    	int id = deptForm.getId();

    	int userId = getUserId(request);
    	UserAccess userAccess = ObjFactory.createUserAccess(); 
    	boolean userCanDeleteDept = userAccess.userCanDeleteDept(userId, id, entidad);
        if (!userCanDeleteDept)
            throw new IeciTdException(String.valueOf(MvcError.EC_NOT_CAN_DELETE_DEPT), null);
    	
    	Department dep = ObjFactory.createDepartment(userId, Defs.NULL_ID);
        dep.load(id, entidad);
        Users usersList= new Users();
        usersList.loadByDept(id, entidad);
        if (usersList.count() > 0 )
        	throw new IeciTdException(String.valueOf(MvcError.EC_DEPT_HAS_USERS), null);
        
        dep.delete(entidad);
        
        if (logger.isDebugEnabled())
    	    logger.debug("Borrado departamento con id: " + id);

    	ActionMessages messages = new ActionMessages();
    	ActionMessage msg = new ActionMessage("message.department.delete");
    	messages.add(Globals.MESSAGES_KEY, msg);
    	
    	request.setAttribute("tipo", String.valueOf(Constantes.DEPARTAMENT) );
    	saveMessages(request, messages);
    	
    	return mapping.findForward("success");
    }

}
