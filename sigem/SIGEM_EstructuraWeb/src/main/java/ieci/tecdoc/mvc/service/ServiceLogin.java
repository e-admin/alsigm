/*
 * Created on 13-jul-2005
 *
 */
package ieci.tecdoc.mvc.service;

import ieci.tecdoc.core.db.DBSessionManager;
import ieci.tecdoc.core.db.DbConnection;
import ieci.tecdoc.core.db.DbSelectFns;
import ieci.tecdoc.core.exception.IeciTdException;
import ieci.tecdoc.idoc.admin.api.user.Login;
import ieci.tecdoc.idoc.admin.api.user.UserDefs;
import ieci.tecdoc.idoc.admin.internal.Defs;
import ieci.tecdoc.mvc.dto.access.ChangePwdDTO;
import ieci.tecdoc.mvc.dto.access.UserConnectedDTO;
import ieci.tecdoc.mvc.service.adminUser.ServiceCertificate;
import ieci.tecdoc.sbo.acs.base.AcsAccessTokenProduct;
import ieci.tecdoc.sbo.acs.base.AcsAccessTokenProducts;
import ieci.tecdoc.sbo.acs.base.AcsProfile;
import ieci.tecdoc.sbo.idoc.api.AcsAccessObject;
import ieci.tecdoc.sbo.idoc.login.LoginManager;
import ieci.tecdoc.sbo.idoc.login.LoginMethod;
import ieci.tecdoc.sbo.uas.ldap.UasError;
import ieci.tecdoc.sbo.uas.std.UasMdoAuth;
import ieci.tecdoc.sbo.util.idoccrypto.IdocCryptoUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * @author Antonio María
 *
 */
public class ServiceLogin {
    private ServiceLogin(){}
    
    public static ServiceLogin getInstance(){
        return new ServiceLogin();
    }
    
    public void changePwd(ChangePwdDTO changePwdDTO, String entidad) throws Exception{
        
        String UserName = changePwdDTO.getUser();
        String UserPass = changePwdDTO.getPwd();
        String newPass = changePwdDTO.getNewPwd();
        String confNewPass = changePwdDTO.getConfNewPwd();
        int cntsTriesNum = changePwdDTO.getCntsTriesNum();
        try {
            DbConnection.open(DBSessionManager.getSession(entidad));
            UasMdoAuth.changePassword(UserName,UserPass,cntsTriesNum,newPass, confNewPass, entidad);
            
        } catch (Exception e1) {
            throw e1;
        }
        finally { DbConnection.close(); }
        
    }
    
    
    public UserConnectedDTO doLoginCert(String idCert, int loginMethod, String entidad) throws Exception
	{
        
	AcsAccessObject acs = null;
	String userName = null;
	AcsAccessTokenProducts token = null;
	UserConnectedDTO userDTO = null;
	try {
	    DbConnection.open(DBSessionManager.getSession(entidad));
	    String userId = DbSelectFns.selectShortText("IUSERUSERCERT","ID","WHERE CERT='"+idCert+"'"); // Salta Exception si no esta dado de alta
	    switch (loginMethod){
	    	case LoginMethod.STANDARD:
			    userName = DbSelectFns.selectShortText("IUSERUSERHDR","NAME","WHERE ID='"+userId+"'");
			    String pwdEnc = DbSelectFns.selectShortText("IUSERUSERHDR","PASSWORD","WHERE ID='"+userId+"'");    
			    String passwdToGenKey = "IUSER"+userId;
				String pwd = IdocCryptoUtil.decryptPassword(pwdEnc,passwdToGenKey);
				token = LoginManager.doAdmLoginStd(userName, pwd, 1, entidad);
	    	break;
	    	case LoginMethod.LDAP:
	    	    userName = DbSelectFns.selectShortText("IUSERUSERCERT","LDAPDN","WHERE ID='"+userId+"'");
	    		token = LoginManager.doAdmSsoLoginLdap(userName, entidad);
	    	break;
	    	case LoginMethod.SSO_LDAP:
	    	    userName = DbSelectFns.selectShortText("IUSERUSERCERT","LDAPDN","WHERE ID='"+userId+"'");
	    		token = LoginManager.doAdmSsoLoginLdap(userName, entidad);
	    	break;
	    	
	    }
		userDTO = getUserConnectedDTO(token, userName);
	    
	}catch (IeciTdException ex){
	    throw new IeciTdException(UasError.EC_NO_USER_IN_IDOC_SYSTEM, UasError.EM_NO_USER_IN_IDOC_SYSTEM);
	}
	finally {
	    DbConnection.close();    
	}
	return userDTO;
	}
    
    
    private UserConnectedDTO getUserConnectedDTO(AcsAccessTokenProducts token, String userName){
        
        UserConnectedDTO user = null;
        
        AcsAccessTokenProduct tokenProduct = null;
        int n = - 1;
        if (userName.equals(Defs.SYSSUPERUSER_NAME))
        {
            user = new UserConnectedDTO(Defs.SYSSUPERUSER_ID, Defs.SYSSUPERUSER_NAME);
	        user.setIsSystemXSuperUser(true);
        }
        else
        {
            n =  token.count();
            int idUser = token.get(0).getAcsAccessToken().getUserId(); // Supongo que de todos los accessToken devuelven el mismo idUser
            user = new UserConnectedDTO(idUser, userName);
            String prof = token.get(0).getAcsAccessToken().getProfile();
            user.setIsSystemXSuperUser(isSysXSuperuser(prof));
            user.setIsSystemSuperUser(isSysSuperuser(prof));            
        }
        
        Map profiles = new HashMap();
        
        boolean hasAccessUser = false;
        boolean hasAccessVol = false;
        boolean hasAccessSys = false;
        
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
	                case UserDefs.PRODUCT_SYSTEM: // Para Ldap devuelve Product_system en vez de product_idoc
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
        
        return user;        
    }
    

    public UserConnectedDTO doLogin(String userName, String userPass,int cntsTriesNum, String entidad) throws Exception
    {
        UserConnectedDTO user = null;
        AcsAccessTokenProducts token = null;
        int n = - 1; 
        if (userName.equals(Defs.SYSSUPERUSER_NAME))
	    {
	        ieci.tecdoc.sbo.idoc.api.Login login = new ieci.tecdoc.sbo.idoc.api.Login();
	        AcsAccessObject acsAccessObject= login.doLoginStd(Defs.SYSSUPERUSER_NAME, userPass,cntsTriesNum, entidad);
	    }
        else
            token = Login.doAdmLogin(userName, userPass,cntsTriesNum, entidad);
            
        user = getUserConnectedDTO(token,userName);
        return user;
    }
    
    /*    
    public UserConnectedDTO doLogin(String userName, String userPass,int cntsTriesNum) throws Exception
    {
        UserConnectedDTO user = null;
        AcsAccessTokenProducts token = null;
        int n = - 1; 
        if (userName.equals(Defs.SYSSUPERUSER_NAME))
	    {
	        ieci.tecdoc.sbo.idoc.api.Login login = new ieci.tecdoc.sbo.idoc.api.Login();
	        AcsAccessObject acsAccessObject= login.doLoginStd(Defs.SYSSUPERUSER_NAME, userPass,cntsTriesNum);
	        
	        user = new UserConnectedDTO(Defs.SYSSUPERUSER_ID, Defs.SYSSUPERUSER_NAME);
	        user.setIsSystemXSuperUser(true);
	    }
        else
        {
            token = Login.doAdmLogin(userName, userPass,cntsTriesNum);
            n =  token.count();
            
            AcsAccessTokenProduct tokenProduct = null;
	        int idUser = token.get(0).getAcsAccessToken().getUserId(); // Supongo que de todos los accessToken devuelven el mismo idUser
	        user = new UserConnectedDTO(idUser, userName);
	        String prof = token.get(0).getAcsAccessToken().getProfile();
	        user.setIsSystemXSuperUser(isSysXSuperuser(prof));
	        user.setIsSystemSuperUser(isSysSuperuser(prof));
        }
        
        Map profiles = new HashMap();
        
        boolean hasAccessUser = false;
        boolean hasAccessVol = false;
        boolean hasAccessSys = false;
        
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
	                case UserDefs.PRODUCT_SYSTEM: // Para Ldap devuelve Product_system en vez de product_idoc
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
        
        return user;
    }
    
*/    
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
	
	
	private boolean isSysXSuperuser (String profile)
	   {
	      if (AcsProfile.SYS_XSUPERUSER.equals(profile))
	         return true;
	      else
	         return false;
	   }
	private boolean isSysSuperuser(String profile)
	   {
	      if (AcsProfile.SYS_SUPERUSER.equals(profile))
	         return true;
	      else
	         return false;
	   }

    /**
     * @param id
     * @param idCert
     */
    public void addCertificate(int id, String idCert, String userAttName, String entidad) throws Exception {
        ServiceCertificate service = ServiceCertificate.getInstance();
        service.updateIdCert(id,idCert,userAttName, entidad);
    }
}
