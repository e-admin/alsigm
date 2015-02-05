package ieci.tecdoc.isicres.rpadmin.struts.acciones.login;


import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.AuthenticationUser;
import com.ieci.tecdoc.common.exception.SecurityException;
import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.common.keys.ServerKeys;
import com.ieci.tecdoc.common.utils.ISicresGenPerms;
import com.ieci.tecdoc.idoc.authentication.InvesDocAuthenticationPolicy;
import com.ieci.tecdoc.isicres.desktopweb.utils.RequestUtils;
import com.ieci.tecdoc.isicres.session.security.SecuritySession;
import com.ieci.tecdoc.isicres.usecase.UseCaseConf;
import com.ieci.tecdoc.isicres.usecase.security.SecurityUseCase;
import com.ieci.tecdoc.utils.cache.CacheBag;
import com.ieci.tecdoc.utils.cache.CacheFactory;

public class LoginInvesdoc{

	private static final Logger logger = Logger.getLogger(LoginInvesdoc.class);
	public static final String PASSWORDCTRL_KEY="PasswordCtrl";
	public static final String NAMECTRL_KEY="NameCtrl";


	public void login(HttpServletRequest request, UseCaseConf useCaseConf) throws ValidationException, SecurityException, Exception{
		SecurityUseCase securityUseCase = new SecurityUseCase();
		//obtenemos el useCaseConf para el proceso de autenticacaion
		String name=LoginAction.getName(request);
		String passwordCrypt=RequestUtils.parseRequestParameterAsString(request, PASSWORDCTRL_KEY);

		// Validacion del usuario.
		securityUseCase.login(useCaseConf, name, passwordCrypt);
		CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(useCaseConf.getSessionID());
		ISicresGenPerms genPerms = (ISicresGenPerms) cacheBag.get(ServerKeys.GENPERMS_USER);

		boolean superUser = SecuritySession.isSuperuser(useCaseConf
				.getSessionID());

		//comprobamos si es superusuario || ó si tiene algún permiso de administración
		if(superUser || LoginAction.tienePermisosAdministracion(genPerms)){
			// Una vez validado el usuario se introduce en la sesiÃ³n diversos parametros.
			LoginAction.saveSessionData(request, useCaseConf, superUser,genPerms);

		}else{
			logger.error("Se ha producido un error en el login: El usuario no tiene permisos como Administrador");
			throw new SecurityException(SecurityException.ERROR_USER_APLICATION);
		}
	}

	/**
	 * Método que logea al usuario SYSSUPERUSER (sin comprobar la política de autenticación de la aplicación)
	 * @param request - Request
	 * @param useCaseConf - Datos de configuración del usuario logeado
	 *
	 * @throws ValidationException
	 * @throws SecurityException
	 * @throws Exception
	 */
	public void loginSysSuperUser(HttpServletRequest request, UseCaseConf useCaseConf) throws ValidationException,
			SecurityException, Exception {

		//obtenemos el useCaseConf para el proceso de autenticacaion
		String name=LoginAction.getName(request);
		String passwordCrypt=RequestUtils.parseRequestParameterAsString(request, PASSWORDCTRL_KEY);

		// Validacion del usuario.
		//securityUseCase.login(useCaseConf, name, passwordCrypt);

		//Autenticamos el usuario en el sistema INVESDOC
		AuthenticationUser user = new InvesDocAuthenticationPolicy().validate(name, passwordCrypt, useCaseConf.getEntidadId());

		//comprobamos que es el usuario SYSSUPERUSER (ID = 0)
		if(user.getId() == 0){
			//Obtenemos los datos del usuario
			String sessionID = SecuritySession.completarDatosLogin(user, useCaseConf.getEntidadId(), useCaseConf.getLocale());
			useCaseConf.setSessionID(sessionID);

			//Obtenemos los permisos del usuario
			CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(useCaseConf.getSessionID());
			ISicresGenPerms genPerms = (ISicresGenPerms) cacheBag.get(ServerKeys.GENPERMS_USER);

			// Una vez validado el usuario se introduce en la session diversos parametros.
			LoginAction.saveSessionData(request, useCaseConf, true,genPerms);
		}else{
			logger.error("Se ha producido un error en el login: El usuario indicado no tiene permisos como Administrador");
			throw new SecurityException(SecurityException.ERROR_USER_APLICATION);
		}
	}


}
