package com.ieci.tecdoc.common;

import com.ieci.tecdoc.common.exception.SecurityException;
import com.ieci.tecdoc.common.exception.ValidationException;

/**
 * Interfaz de Authenticacion.
 * 
 * Las clases que implementen la authenticacion de la aplicacion deben seguir esta interfaz
 * 
 * @author lmvicente 
 * @version  
 * @since 
 * @creationDate 24-mar-2004
 */

public interface AuthenticationPolicy {

	/********************************
	 * Attributes           
	 ********************************/

	/********************************
	 * Constructors
	 ********************************/

	/********************************
	 * Public methods
	 ********************************/

	/**
	 * Login y password ya llegan validados, serán distintos de null
	 * o password length == 0.
	 * Si existen problemas se debera elevar una exception SecurityException.
	 * Sino se puede validar al usuario de elevará una SecurityException.
	 * Si la validacion de los parametros no es correcta se eleva ValidationException.
	 */
	public AuthenticationUser validate(String login, String password, String entidad) throws SecurityException, ValidationException;
	/**
	 * LDAP con SSO
	 * Si existen problemas se debera elevar una exception SecurityException.
	 * Sino se puede validar al usuario de elevará una SecurityException.
	 * Si la validacion de los parametros no es correcta se eleva ValidationException.
	 */
	public AuthenticationUser validate(String userDn, String entidad) throws SecurityException, ValidationException;
	
	/**
	 * Login y password ya llegan validados, serán distintos de null
	 * o password length == 0.
	 * Este usuario hay que validarlo por si no existe.	
	 *  
	 * @param login
	 * @param newPassword
	 * @param oldPassword
	 * @throws SecurityException
	 * @throws ValidationException
	 */
	public void changePassword(String login, String newPassword, String oldPassword, String entidad) throws SecurityException, ValidationException;
	
	/********************************
	 * Protected methods
	 ********************************/

	/********************************
	 * Private methods
	 ********************************/

	/********************************
	 * Inner classes
	 ********************************/

	/********************************
	 * Test brench
	 ********************************/
	
}
