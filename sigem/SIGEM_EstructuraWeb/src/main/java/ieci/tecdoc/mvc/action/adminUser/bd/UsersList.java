/*
 * Created on 21-mar-2005
 *
 */
package ieci.tecdoc.mvc.action.adminUser.bd;

import ieci.tecdoc.core.exception.IeciTdException;
import ieci.tecdoc.idoc.admin.api.ObjFactory;
import ieci.tecdoc.idoc.admin.api.user.User;
import ieci.tecdoc.idoc.admin.api.user.UserAccess;
import ieci.tecdoc.idoc.admin.api.user.Users;
import ieci.tecdoc.mvc.action.BaseAction;
import ieci.tecdoc.mvc.error.MvcError;
import ieci.tecdoc.mvc.form.adminUser.bd.UsersListForm;
import ieci.tecdoc.mvc.service.adminUser.ServiceCommon;
import ieci.tecdoc.mvc.util.Constantes;
import ieci.tecdoc.mvc.util.SessionHelper;
import ieci.tecdoc.util.adminUser.UserBeanLittle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
public class UsersList extends BaseAction {
    
    private static Logger logger = Logger.getLogger(UsersList.class);
    private int userId;
    public ActionForward executeLogic(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception
    {
    	
    	String entidad=SessionHelper.getEntidad(request);
    	
        if (logger.isDebugEnabled())
            logger.debug("Users List Action");
        
        String objId = request.getParameter("id");
        String objTipo = request.getParameter("tipo");
        String action = request.getParameter("action");
        
        UsersListForm usersListForm = (UsersListForm) form;
        
        String objIdUser = request.getParameter("idUser");
        userId = getUserId(request);
        if (objIdUser != null )
            changeStatus(objIdUser, entidad);
        
        int id = Integer.parseInt(objId);
        int tipo = Integer.parseInt(objTipo);
        
        Map basicProperties = ServiceCommon.getBasicProperties(id, tipo, entidad);
        
        
        UserAccess userAccess = ObjFactory.createUserAccess();
        
        int userId = getUserId(request);
        
        
        Users usersList= new Users();
        if (tipo == Constantes.DEPARTAMENT){
            boolean userCanViewDept = userAccess.userCanViewDept(userId, entidad);
            if (!userCanViewDept)
                throw new IeciTdException(String.valueOf(MvcError.EC_NOT_CAN_VIEW_DEPT), null);
        	usersList.loadByDept(id, entidad);
        }
        else{
            boolean userCanViewGroup = userAccess.userCanViewGroup(userId, entidad);
            if (!userCanViewGroup)
                throw new IeciTdException(String.valueOf(MvcError.EC_NOT_CAN_VIEW_GROUP), null);
            usersList.loadByGroup(id, entidad);
        }
        
        usersListForm.setNombre(basicProperties.get("name").toString());
        usersListForm.setManagerId(((Integer)basicProperties.get("managerId")).intValue() );
        
        usersListForm.setId(id);
        
        int n = usersList.count();
        
        List listaDeUsuarios = new ArrayList();

        for (int i = 0; i < n ; i++)
        {
            User user = usersList.getUser(i);
            String name = user.getName();
            int idUser = user.getId();
            int status = user.getState();
            
            int deptId = user.getDeptId();
            
            
            if (logger.isDebugEnabled())
            {
                logger.debug("id: " + idUser + " Name: " + name + " Status: " + status);
                
            }
            UserBeanLittle userBeanLittle = new UserBeanLittle();
            userBeanLittle.setNombre(name);
            if (status > 0)
                userBeanLittle.setBloqueado(true);
            
            userBeanLittle.setId(idUser);
            userBeanLittle.setDeptId(deptId);
            
            if (tipo == Constantes.GROUP){
                int idMgr = ServiceCommon.getManagerId(deptId, Constantes.DEPARTAMENT, entidad);
                userBeanLittle.setMgrDeptId(String.valueOf(idMgr));
            }
            
            listaDeUsuarios.add(userBeanLittle);
            
        }
        
        usersListForm.setUsersList(listaDeUsuarios);
        if (action != null )
            return mapping.findForward("addPerms");
        else
            return mapping.findForward("success");
    }
    
    /**
     * @param objIdUser
     */
    private void changeStatus(String objIdUser, String entidad) throws Exception{
        
        UserAccess userAccess = ObjFactory.createUserAccess();
        int idUsuario = Integer.parseInt(objIdUser);
        
        boolean userCanEditUser = userAccess.userCanEditUser(userId, idUsuario, entidad);
        if (!userCanEditUser)
            throw new IeciTdException(String.valueOf(MvcError.EC_NOT_CAN_BLOCK_USER), null);
        
        User user = ObjFactory .createUser();
        user.load(idUsuario, entidad);
        
        int status = user.getState();
        if (status > 0)
            user.setState(0);
        else
            user.setState(1);
        user.store(entidad);
        
    }
}