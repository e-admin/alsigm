package com.ieci.tecdoc.isicres.usecase.security;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.exception.SecurityException;
import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.common.invesicres.ScrOfic;
import com.ieci.tecdoc.idoc.decoder.dbinfo.LDAPDef;
import com.ieci.tecdoc.idoc.utils.Base64Util;
import com.ieci.tecdoc.isicres.session.security.SecuritySession;
import com.ieci.tecdoc.isicres.usecase.UseCaseConf;

/**
 * @author LMVICENTE
 * @creationDate 29-abr-2004 16:25:33
 * @version
 * @since
 */
public class SecurityUseCase {

    /*******************************************************************************************************************
     * Attributes
     ******************************************************************************************************************/
	private static Logger _logger = Logger.getLogger(SecurityUseCase.class);

    /*******************************************************************************************************************
     * Constructors
     ******************************************************************************************************************/

    public SecurityUseCase() {
        super();
    }

    /*******************************************************************************************************************
     * Public methods
     ******************************************************************************************************************/

    public void login(UseCaseConf useCaseConf, String login, String password) throws 
            ValidationException,
            SecurityException,
            Exception {
    	
    	// SLuna-20081216-I
    	
    	/*String sessionID = SecuritySession.login(login, password, useCaseConf
				.getUserDn(), useCaseConf.getUseLdap(), useCaseConf
				.getUsingOSAuth(), useCaseConf.getEntidadId());*/
    	
    	String sessionID = null;

		// Discriminamos si se entra desde SIGEM o no, para decodificar el
		// login. Si se entra desde SIGEM se decodifica, ya que previamente se
		// ha codificado. Si se trata de SICRES, no se decodifica, ya que no se
		// codifica previamente
    	if (!useCaseConf.getEntidadId().equals("ISicres")
				&& login.indexOf("##CODE##") != -1) {

			String decodedLogin = login.substring(0, login.indexOf("##CODE##"));
			
			decodedLogin = Base64Util.decodeToString(decodedLogin);
			sessionID = SecuritySession.login(decodedLogin, password,
					useCaseConf.getUserDn(), useCaseConf.getUseLdap(),
					useCaseConf.getUsingOSAuth(), useCaseConf.getLocale(), useCaseConf.getEntidadId());

		} else {

			sessionID = SecuritySession.login(login, password, useCaseConf
					.getUserDn(), useCaseConf.getUseLdap(), useCaseConf
					.getUsingOSAuth(), useCaseConf.getLocale(), useCaseConf.getEntidadId());

		}
    	
    	// SLuna-20081216-F

        useCaseConf.setSessionID(sessionID);
    }
    
    public void changePassword(UseCaseConf useCaseConf, String login, String oldPassword, String newPassword) throws 
            ValidationException,
            SecurityException {
    	
    	
        SecuritySession.changePassword(login, oldPassword, newPassword, useCaseConf.getEntidadId());
    }

    public void logout(UseCaseConf useCaseConf) throws SecurityException {
        SecuritySession.logout(useCaseConf.getSessionID(), useCaseConf.getEntidadId());
    }

    public ScrOfic getCurrentUserOfic(UseCaseConf useCaseConf) throws SecurityException,
            ValidationException {
        return SecuritySession.getCurrentUserOfic(useCaseConf.getSessionID(), useCaseConf.getLocale(), useCaseConf.getEntidadId());
    }
    
    public String getCurrentUserOficName(UseCaseConf useCaseConf)
			throws SecurityException, ValidationException {
		return SecuritySession.getCurrentUserOficName(useCaseConf
				.getSessionID(), useCaseConf.getLocale(), useCaseConf
				.getEntidadId());
	}


    public LDAPDef getLDAPInfo(UseCaseConf useCaseConf) throws Exception {
		return SecuritySession.ldapInfo(useCaseConf.getEntidadId());
	}

    /*******************************************************************************************************************
     * Protected methods
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Private methods
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Inner classes
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Test brench
     ******************************************************************************************************************/

}