/*
 * Created on 12-jul-2005
 *
 */
package ieci.tecdoc.mvc.action.access;

import ieci.tecdoc.core.exception.IeciTdException;
import ieci.tecdoc.mvc.action.BaseAction;
import ieci.tecdoc.mvc.dto.access.ChangePwdDTO;
import ieci.tecdoc.mvc.dto.access.UserConnectedDTO;
import ieci.tecdoc.mvc.form.access.ChangePwdForm;
import ieci.tecdoc.mvc.service.ServiceLogin;
import ieci.tecdoc.mvc.util.Constantes;
import ieci.tecdoc.mvc.util.MvcDefs;
import ieci.tecdoc.mvc.util.SessionHelper;
import ieci.tecdoc.mvc.util.encoders.Base64;
import ieci.tecdoc.mvc.util.text.TransformUtils;
import ieci.tecdoc.sbo.uas.base.UasBaseError;
import ieci.tecdoc.sbo.uas.std.UasError;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

/**
 * @author Antonio María
 *
 */
public class ChangePwdAction extends BaseAction{

    /* (non-Javadoc)
     * @see ieci.tecdoc.mvc.action.BaseAction#executeLogic(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    private static final Logger logger  = Logger.getLogger(ChangePwdAction.class);
    protected ActionForward executeLogic(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

    	String entidad=SessionHelper.getEntidad(request);
    	
        int             cntsTriesNum  = 1;
        ChangePwdForm   changePwdForm = (ChangePwdForm)form;
        ChangePwdDTO    changePwdDTO  = new ChangePwdDTO();
        HttpSession     session       = request.getSession(false);
        String user = request.getParameter("user");
        
        BeanUtils.copyProperties(changePwdDTO, changePwdForm);
        
        if(session.getAttribute(MvcDefs.TOKEN_CNTS_NUM_TRIES) != null)
           cntsTriesNum = ((Integer)session.getAttribute(MvcDefs.TOKEN_CNTS_NUM_TRIES)).intValue() + 1;
        
        changePwdDTO.setCntsTriesNum(cntsTriesNum);
        changePwdDTO.setUser(user);
        changePwdDTO.setPwd(TransformUtils.reverse(new String(Base64.decode(changePwdDTO.getPwd()))));
        changePwdDTO.setNewPwd(TransformUtils.reverse(new String(Base64.decode(changePwdDTO.getNewPwd()))));
        changePwdDTO.setConfNewPwd(TransformUtils.reverse(new String(Base64.decode(changePwdDTO.getConfNewPwd()))));
        
        try {
            ServiceLogin serviceLogin = ServiceLogin.getInstance();
            serviceLogin.changePwd(changePwdDTO, entidad);
            UserConnectedDTO userDTO = serviceLogin.doLogin(changePwdDTO.getUser(), changePwdDTO.getNewPwd(),changePwdDTO.getCntsTriesNum(), entidad);
            
            session.setAttribute(Constantes.TOKEN_USER_CONNECTED, userDTO);
            
        }catch (IeciTdException ex){
            
            session.setAttribute(MvcDefs.TOKEN_CNTS_NUM_TRIES, new Integer(cntsTriesNum));
            String errorCode = ex.getErrorCode();
            
            if(errorCode.equals(UasBaseError.EC_TOO_MANY_LOGIN_ATTEMPTS) || errorCode.equals(UasError.EC_USER_LOCKED))
            {
               
               logger.error(ex.getMessage(), ex);
               
               ActionMessages messages = new ActionMessages();
               ActionMessage message   = null;
               message = new ActionMessage(errorCode);
                  
               messages.add(ActionMessages.GLOBAL_MESSAGE, message);
               session.setAttribute(Globals.MESSAGE_KEY, messages);
               
               String bundle = Constantes.MESSAGES_GENERAL_ERRORS;
               request.setAttribute("bundle", bundle);
               
               return (mapping.findForward(MvcDefs.TOKEN_FORWARD_INVALIDATED));
               
            }
            else
            {
                request.setAttribute(UasError.EC_MUST_CHANGE_PWD,new Boolean (true));
                throw ex;
            }
               
        }
        return mapping.findForward("success");
        
    }

}
