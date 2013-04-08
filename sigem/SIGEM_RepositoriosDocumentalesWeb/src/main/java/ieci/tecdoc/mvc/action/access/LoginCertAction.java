package ieci.tecdoc.mvc.action.access;

import ieci.tecdoc.core.exception.IeciTdException;
import ieci.tecdoc.idoc.admin.api.user.UserDefs;
import ieci.tecdoc.mvc.action.BaseAction;
import ieci.tecdoc.mvc.dto.access.LoginDTO;
import ieci.tecdoc.mvc.dto.access.UserConnectedDTO;
import ieci.tecdoc.mvc.error.MvcError;
import ieci.tecdoc.mvc.form.access.LoginForm;
import ieci.tecdoc.mvc.service.ServiceLogin;
import ieci.tecdoc.mvc.service.adminUser.ServiceCertificate;
import ieci.tecdoc.mvc.util.Constantes;
import ieci.tecdoc.mvc.util.MvcDefs;
import ieci.tecdoc.mvc.util.SessionHelper;
import ieci.tecdoc.sbo.acs.base.AcsProfile;
import ieci.tecdoc.sbo.uas.std.UasError;

import java.util.HashMap;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
/**
 * @author antmaria
 *
 */
public class LoginCertAction extends BaseAction {
	
    private static final Logger logger = Logger.getLogger(LoginCertAction.class);

	public ActionForward executeLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String entidad=SessionHelper.getEntidad(request);
		
		LoginForm       loginForm    	=	(LoginForm)form;
		LoginDTO        loginDTO     	=	new LoginDTO();
		HttpSession     session      	=	request.getSession();
		int             loginMethod  	=	-1;
		int             cntsTriesNum 	=	1;
		
		//loginMethod  =  ((Integer)session.getServletContext().getAttribute(MvcDefs.TOKEN_LOGIN_METHOD)).intValue();
        HashMap hash=(HashMap)session.getServletContext().getAttribute(MvcDefs.TOKEN_LOGIN_METHOD);
        loginMethod=((Integer)hash.get(entidad)).intValue();
		
	    ServiceLogin serviceLogin = ServiceLogin.getInstance();
	    
	    UserConnectedDTO user = null;
	    
	    user = (UserConnectedDTO) session.getAttribute(Constantes.TOKEN_USER_CONNECTED);
	    ServiceCertificate serviceCertificate = ServiceCertificate.getInstance();
	    String idCert =  serviceCertificate.decodeCert(request);
	    
	    if (user == null ) 
	    {
		    try{
		        user = serviceLogin.doLoginCert(idCert, loginMethod, entidad);
			}catch (IeciTdException ex){
		        String errorCode = ex.getErrorCode();
		        if (errorCode.equals(UasError.EC_MUST_CHANGE_PWD) ){ // Debe cambiar la contraseña
		            request.setAttribute(UasError.EC_MUST_CHANGE_PWD,new Boolean (true));
		            session.removeAttribute(MvcDefs.TOKEN_CNTS_NUM_TRIES);
		        }
		        else
		            session.setAttribute(MvcDefs.TOKEN_CNTS_NUM_TRIES, new Integer(cntsTriesNum));
		        throw ex;
		    }
	        session.setAttribute(Constantes.TOKEN_USER_CONNECTED, user);
	    }
	    else
	    {
	        Boolean addCertificate = (Boolean) session.getAttribute("addCertificate");
		    if (addCertificate.booleanValue()){  // Añadir certificado al usuario
		        serviceLogin.addCertificate(user.getId(),idCert, user.getUserName(), entidad);
		        session.removeAttribute("addCertificate");
		    }
	    }
        
        if (  user.getHasAccessUser()|| user.getHasAccessVol() || user.getHasAccessSys())
            return mapping.findForward("success");
        else{
            throw new IeciTdException(String.valueOf(MvcError.EC_NOT_CAN_ADMIN), "EC_NOT_CAN_ADMIN");
        }
		
	}
	
	public Integer getProfileNumber( String profile)
	{
	    StringTokenizer st = new StringTokenizer(profile, "_");
	    st.nextToken();
	    String profileType = st.nextToken();
	    Integer profileNumber = null;
	    
	    if (profileType.equals("SUPERUSER"))
	        profileNumber = new Integer(UserDefs.PROFILE_SUPERUSER);
	    else if (profileType.equals("MANAGER"))
	        profileNumber = new Integer(UserDefs.PROFILE_MANAGER);
	    else if (profileType.equals("STANDARD"))
	        profileNumber = new Integer(UserDefs.PROFILE_STANDARD);
	    else if (profileType.equals("NONE") )
	        profileNumber = new Integer(UserDefs.PROFILE_NONE);
	    
	    return profileNumber;
	    
	}
	
	
	public boolean isSysXSuperuser (String profile)
	   {
	      if (AcsProfile.SYS_XSUPERUSER.equals(profile))
	         return true;
	      else
	         return false;
	   }
	public boolean isSysSuperuser(String profile)
	   {
	      if (AcsProfile.SYS_SUPERUSER.equals(profile))
	         return true;
	      else
	         return false;
	   }
}