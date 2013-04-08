/*
 * Created on 05-abr-2005
 *
 */
package ieci.tecdoc.mvc.action.adminUser.bd;

import ieci.tecdoc.core.exception.IeciTdException;
import ieci.tecdoc.idoc.admin.api.ObjFactory;
import ieci.tecdoc.idoc.admin.api.exception.DeptErrorCodes;
import ieci.tecdoc.idoc.admin.api.exception.GroupErrorCodes;
import ieci.tecdoc.idoc.admin.api.exception.UserErrorCodes;
import ieci.tecdoc.idoc.admin.api.user.Department;
import ieci.tecdoc.idoc.admin.api.user.Group;
import ieci.tecdoc.idoc.admin.api.user.User;
import ieci.tecdoc.idoc.admin.internal.Defs;
import ieci.tecdoc.mvc.action.BaseAction;
import ieci.tecdoc.mvc.form.adminUser.common.SearchForm;
import ieci.tecdoc.mvc.util.Constantes;
import ieci.tecdoc.mvc.util.SessionHelper;

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
public class Search extends BaseAction{
    
    private static Logger logger = Logger.getLogger(Search.class);

    /* (non-Javadoc)
     * @see ieci.tecdoc.mvc.action.BaseAction#executeLogic(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    
    
    
    protected ActionForward executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
    	String entidad=SessionHelper.getEntidad(request);
    	
    	SearchForm searchForm = (SearchForm) form;
        byte tipoBusqueda = searchForm.getTipoBusqueda();
        String valor = searchForm.getValor();
        String submitted = request.getParameter("submitted");
        int id = -1;
        byte tipo = -1;
        boolean enc = false;
        ActionMessages messages          =  new ActionMessages();
        int idMgr = Defs.NULL_ID;
        if (submitted != null)
        {
            if (logger.isDebugEnabled())
            {
                logger.debug("Buscando el: " + tipoBusqueda + " de nombre: " + valor);
            }
	        if (tipoBusqueda == Constantes.PERSON)
	        {
	            User user = ObjFactory.createUser();
	            try {
	                user.load(valor, entidad);
	                tipo = Constantes.PERSON;
	                id = user.getId();
	                enc = true;
	                //int deptId = user.getDeptId();
	                
	                //idMgr = ServiceCommon.getManagerId(deptId, Constantes.DEPARTAMENT);
                    
	            } catch (IeciTdException e) {
	                if (logger.isDebugEnabled())
	                    logger.debug("Usuario no Existe");
	                long errorCode = Long.parseLong(e.getErrorCode());
	                if (errorCode == UserErrorCodes.EC_USER_NOT_EXITS)
	                {
	                    ActionMessage message  =  new ActionMessage(e.getErrorCode());
	                    messages.add(ActionMessages.GLOBAL_MESSAGE, message);
	                    saveMessages(request, messages);
	                }
	                else
	                    throw e;
	            }
	        }
	        else if (tipoBusqueda == Constantes.DEPARTAMENT)
	        {
	            ObjFactory o;
	            Department dept = ObjFactory.createDepartment();
	            try {
	                dept.load(valor, entidad);
	                tipo = Constantes.DEPARTAMENT;
	                id = dept.getId();
	                enc = true;
	                //idMgr = ServiceCommon.getManagerId(id, Constantes.DEPARTAMENT);
	            } catch (IeciTdException e) {
	                if (logger.isDebugEnabled())
	                    logger.debug("El departamento no Existe");
	                long errorCode = Long.parseLong(e.getErrorCode());               
	                if (errorCode == DeptErrorCodes.EC_DEPT_NOT_EXITS) 
	                {
	                    ActionMessage message  =  new ActionMessage(e.getErrorCode());
	                    messages.add(ActionMessages.GLOBAL_MESSAGE, message);
	                    saveMessages(request, messages);
	                }
	                else
	                    throw e;
	            }            
	        }
	        else{ // Busqueda por grupos
	            Group group = ObjFactory.createGroup();
	            try {
	                group.load(valor, entidad);
	                tipo = Constantes.GROUP;
	                id = group.getId();
	                enc = true;
	                //idMgr = ServiceCommon.getManagerId(id, Constantes.GROUP);
	            } catch (IeciTdException e) {
	                if (logger.isDebugEnabled())
	                    logger.debug("El grupo no Existe");
	                long errorCode = Long.parseLong(e.getErrorCode());
	                if (errorCode == GroupErrorCodes.EC_GROUP_NOT_EXITS)
	                {
	                    ActionMessage message  =  new ActionMessage(e.getErrorCode());
	                    messages.add(ActionMessages.GLOBAL_MESSAGE, message);
	                    saveMessages(request, messages);
	                }
	                else
	                    throw e;
	            }                
	            
	        }
        }
        if (enc)
        {
            
            request.setAttribute("tipo", new Byte(tipo));
            request.setAttribute("id", new Integer(id));
            request.setAttribute("enc", new Boolean(enc));
            //request.setAttribute("idMgr", String.valueOf(idMgr));
            if (logger.isDebugEnabled())
                logger.debug("Objecto encontrado");
        }
        return mapping.findForward("success");
    }

}
