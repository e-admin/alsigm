package ieci.tecdoc.mvc.action.access;

import ieci.tecdoc.core.exception.IeciTdException;
import ieci.tecdoc.idoc.admin.api.user.UserDefs;
import ieci.tecdoc.mvc.action.BaseAction;
import ieci.tecdoc.mvc.dto.access.LoginDTO;
import ieci.tecdoc.mvc.dto.access.UserConnectedDTO;
import ieci.tecdoc.mvc.error.MvcError;
import ieci.tecdoc.mvc.form.access.LoginForm;
import ieci.tecdoc.mvc.service.ServiceLogin;
import ieci.tecdoc.mvc.util.Constantes;
import ieci.tecdoc.mvc.util.MvcDefs;
import ieci.tecdoc.mvc.util.SessionHelper;
import ieci.tecdoc.mvc.util.text.TransformUtils;
import ieci.tecdoc.sbo.acs.base.AcsProfile;
import ieci.tecdoc.sbo.uas.std.UasError;

import java.util.HashMap;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
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
public class LoginAction extends BaseAction {
	
    private static final Logger logger = Logger.getLogger(LoginAction.class);
	/**
	 * 
	 */
	public ActionForward executeLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String entidad=SessionHelper.getEntidad(request);
		
		LoginForm       loginForm    	=	(LoginForm)form;
		LoginDTO        loginDTO     	=	new LoginDTO();
		HttpSession     session      	=	request.getSession();
		int             loginMethod  	=	-1;
		int             cntsTriesNum 	=	1;
		
		
		BeanUtils.copyProperties(loginDTO, loginForm);
		String UserName = loginDTO.getUser();
		String UserPass = TransformUtils.decrypt(loginDTO.getPwd());
		
		
		//loginMethod  =  ((Integer)session.getServletContext().getAttribute(MvcDefs.TOKEN_LOGIN_METHOD)).intValue();
        HashMap hash=(HashMap)session.getServletContext().getAttribute(MvcDefs.TOKEN_LOGIN_METHOD);
        loginMethod=((Integer)hash.get(entidad)).intValue();
		if (logger.isDebugEnabled())
		{
			logger.debug("UserName: " + UserName);
			logger.debug("LoginMethod: " + loginMethod);
		}
		
		if(session.getAttribute(MvcDefs.TOKEN_CNTS_NUM_TRIES) != null)
	    {
	      	cntsTriesNum = ((Integer)session.getAttribute(MvcDefs.TOKEN_CNTS_NUM_TRIES)).intValue() + 1;
	    }
	                
	    if(logger.isDebugEnabled())
	    {
	      	logger.debug("User cntsTriesNum :"+cntsTriesNum);
	    }
	    
	    ServiceLogin serviceLogin = ServiceLogin.getInstance();
	    
	    UserConnectedDTO user = null;
	    try{
	        user = serviceLogin.doLogin(UserName, UserPass, cntsTriesNum, entidad);
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
	    /*
	    
	    UserConnectedDTO user = null; 
	    AcsAccessTokenProducts token = null;
	    boolean hasAccessUser = false;
        boolean hasAccessVol = false;
        boolean hasAccessSys = false;
        int n = 0 ;
        try {
		    if (UserName.equals(Defs.SYSSUPERUSER_NAME))
		    {
		        ieci.tecdoc.sbo.idoc.api.Login login = new ieci.tecdoc.sbo.idoc.api.Login();
		        AcsAccessObject acsAccessObject= login.doLoginStd(Defs.SYSSUPERUSER_NAME,UserPass,cntsTriesNum);
		        user = new UserConnectedDTO(Defs.SYSSUPERUSER_ID, Defs.SYSSUPERUSER_NAME);
		        user.setIsSystemXSuperUser(true);
		    }
		    else {
		        token = Login.doAdmLogin(UserName, UserPass,cntsTriesNum);
			    
		        AcsAccessTokenProduct tokenProduct = null;
		        n = token.count();
		        
		        int idUser = token.get(0).getAcsAccessToken().getUserId(); // Supongo que de todos los accessToken devuelven el mismo idUser
		        
		        user = new UserConnectedDTO(idUser, UserName);
		        
		        String prof = token.get(0).getAcsAccessToken().getProfile();
		        
		        user.setIsSystemXSuperUser(isSysXSuperuser(prof));
		        user.setIsSystemSuperUser(isSysSuperuser(prof));    
		    }
        }catch (IeciTdException ex){
            String errorCode = ex.getErrorCode();
            if (errorCode.equals(UasError.EC_MUST_CHANGE_PWD)){ // Debe cambiar la contraseña
                request.setAttribute(UasError.EC_MUST_CHANGE_PWD,new Boolean (true));
                session.removeAttribute(MvcDefs.TOKEN_CNTS_NUM_TRIES);
            }
            else
                session.setAttribute(MvcDefs.TOKEN_CNTS_NUM_TRIES, new Integer(cntsTriesNum));
            throw ex;
        }
        //Product product;
        Map profiles = new HashMap();
        if (!user.getIsSystemSuperUser() && !user.getIsSystemXSuperUser())
        {
	        String profile;
	        Integer profileObj = null;
	        for (int i = 0; i < n ;  i ++){
	            int idProduct = token.get(i).getProductId();
	            switch (idProduct)
	            {
	                case UserDefs.PRODUCT_USER:
	                    hasAccessUser = token.get(i).hasAccess();
	                	user.setHasAccessUser(hasAccessUser);
	                	profile = token.get(i).getAcsAccessToken().getProfile();
	                	profileObj = getProfileNumber(profile);
	                	profiles.put(String.valueOf(UserDefs.PRODUCT_USER), profileObj );
	                    break; 
	                case UserDefs.PRODUCT_VOLUME:
	                    hasAccessVol = token.get(i).hasAccess();
	                	user.setHasAccessVol(hasAccessVol);
	                	profile = token.get(i).getAcsAccessToken().getProfile();
	                	profileObj = getProfileNumber(profile);
	                	profiles.put(String.valueOf(UserDefs.PRODUCT_VOLUME), profileObj );
	                    break;
	                case UserDefs.PRODUCT_IDOC:
	                    hasAccessSys = token.get(i).hasAccess();
	                	user.setHasAccessSys(hasAccessSys);
	                	profile = token.get(i).getAcsAccessToken().getProfile();
	                	profileObj = getProfileNumber(profile);
	                	profiles.put(String.valueOf(UserDefs.PRODUCT_IDOC), profileObj );
	                    break; 
	            }
	        }
        }
        else // Es SYS_SUPERUSER || XSYS_SUPERUSER
        {
            user.setHasAccessSys(true);
            user.setHasAccessUser(true);
            user.setHasAccessVol(true);
            profiles.put(String.valueOf(UserDefs.PRODUCT_IDOC), new Integer(UserDefs.PROFILE_SUPERUSER) );
            profiles.put(String.valueOf(UserDefs.PRODUCT_VOLUME), new Integer(UserDefs.PROFILE_SUPERUSER) ) ;
            profiles.put(String.valueOf(UserDefs.PRODUCT_USER), new Integer(UserDefs.PROFILE_SUPERUSER) );
        }
        user.setProfiles(profiles);
        */
	    
        session.setAttribute(Constantes.TOKEN_USER_CONNECTED, user);
        
        // logger.debug("profiles: " + user.getProfiles().toString());
        
        if (  user.getHasAccessUser()|| user.getHasAccessVol() || user.getHasAccessSys())
            return mapping.findForward("success");
        else{
            throw new IeciTdException(String.valueOf(MvcError.EC_NOT_CAN_ADMIN), "EC_NOT_CAN_ADMIN");
            //return mapping.findForward("error");
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