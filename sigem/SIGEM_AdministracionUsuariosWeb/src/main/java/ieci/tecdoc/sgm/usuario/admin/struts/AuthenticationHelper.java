package ieci.tecdoc.sgm.usuario.admin.struts;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class AuthenticationHelper {

	private static final Logger logger = Logger.getLogger(AuthenticationHelper.class);
	
	private static final String PROPERTIES_FILE_NAME = "Authentication.properties";
	private static final String KEY_ADMIN_USER		= "ieci.tecdoc.sgm.autenticacion.admin.user";
	private static final String KEY_ADMIN_PASS		= "ieci.tecdoc.sgm.autenticacion.admin.pass";
	private static final String KEY_AUTHENTICATED_TOKEN = "ieci.tecdoc.sgm.autenticacion.admin.authtoken"; 
	
	private static Properties propiedades;
	
	static{
		propiedades = new Properties();
		try {
			propiedades.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(PROPERTIES_FILE_NAME));
		} catch (IOException e) {
			logger.fatal("Error inicilizando propiedades de autenticación.", e);
		}
	}

	public static boolean isUserAuthenticated(HttpServletRequest request){
		String cToken = (String)request.getSession().getAttribute(KEY_AUTHENTICATED_TOKEN);
		if((cToken != null) && (!"".equals(cToken))){
			return true;
		}
		return false;
	}	

	public static void authenticateUser(HttpServletRequest request){
		request.getSession().setAttribute(KEY_AUTHENTICATED_TOKEN, KEY_AUTHENTICATED_TOKEN);
	}	

	public static boolean validateUserPassword(String pcUser, String pcPass){
		String cAdminUser = propiedades.getProperty(KEY_ADMIN_USER);
		if( (pcUser != null) && (!"".equals(pcUser))){
			if(pcUser.equals(cAdminUser)){
				if(validatePass(pcPass)){
					return true;
				}
			}
		}
		return false;
	}	
	
	private static boolean validatePass(String pcPass){
		String cAdminPass = propiedades.getProperty(KEY_ADMIN_PASS);
		if(pcPass.equals(cAdminPass)){
			return true;
		}
		return false;
	}
	
}
