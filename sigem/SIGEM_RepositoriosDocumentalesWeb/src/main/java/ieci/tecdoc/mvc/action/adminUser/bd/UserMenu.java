/*
 * Created on 05-abr-2005
 *
 */
package ieci.tecdoc.mvc.action.adminUser.bd;

import ieci.tecdoc.idoc.admin.api.ObjFactory;
import ieci.tecdoc.idoc.admin.api.user.User;
import ieci.tecdoc.idoc.admin.api.user.UserAccess;
import ieci.tecdoc.mvc.action.BaseAction;
import ieci.tecdoc.mvc.error.MvcError;
import ieci.tecdoc.mvc.service.adminUser.ServiceCommon;
import ieci.tecdoc.mvc.util.Constantes;
import ieci.tecdoc.mvc.util.SessionHelper;
import ieci.tecdoc.util.adminUser.UserBeanLittle;

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
public class UserMenu extends BaseAction{

    /* (non-Javadoc)
     * @see ieci.tecdoc.mvc.action.BaseAction#executeLogic(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    private static Logger logger = Logger.getLogger(UserMenu.class);
    
    protected ActionForward executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
    	String entidad=SessionHelper.getEntidad(request);
    	
    	String objId = request.getParameter("id");
        String objChangeStatus = request.getParameter("changeStatus");
        int id = Integer.parseInt(objId);
        UserBeanLittle u = new UserBeanLittle();
        
        if (logger.isDebugEnabled())
        logger.debug("Cargando usuario: " + id);
        
        User user = ObjFactory.createUser();
        user.load(id, entidad);
        
        int mgrDeptId = ServiceCommon.getManagerId(user.getDeptId(), Constantes.DEPARTAMENT, entidad);
        int estado = user.getState();
        
        if (objChangeStatus != null ) // Hay que cambiar el estado
        {
            int userId = getUserId(request);
            UserAccess userAccess = ObjFactory.createUserAccess();
            boolean userCanEditUser = userAccess.userCanEditUser(userId, id, entidad);
            if (userCanEditUser) {
	            if (estado > 0)
	                user.setState(0);
	            else
	                user.setState(1);
	            user.store(entidad);
            }else
            {
                ActionMessages messages =  new ActionMessages();
                ActionMessage message  =  new ActionMessage(String.valueOf(MvcError.EC_NOT_CAN_BLOCK_USER));
                messages.add(ActionMessages.GLOBAL_MESSAGE, message);
                saveMessages(request, messages);
                
            }
	        
        }
        estado = user.getState();
        boolean bloqueado = false;
        if (estado > 0)
            bloqueado = true;
        u.setId(id);
        u.setBloqueado(bloqueado);
        u.setNombre(user.getName());
	    
        if (logger.isDebugEnabled())
            logger.debug(u);
        
	    request.setAttribute("userBean", u);
        request.setAttribute("mgrDeptId", String.valueOf(mgrDeptId));
	    
        return mapping.findForward("success");
    }

}
