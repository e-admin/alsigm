package ieci.tdw.ispac.ispacweb.security;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.context.ClientContext;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class UserCredentialsHelper {

	protected static final String ATTR_USER_CREDENTIALS = "ATTR_USER_CREDENTIALS";

	/**
	 * Obtiene las credenciales del usuario.
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param ctx
	 *            Contexto de cliente.
	 * @return Credenciales del usuario
	 * @throws ISPACException
	 */
	public static UserCredentials getUserCredentials(
			HttpServletRequest request, ClientContext ctx)
			throws ISPACException {

		UserCredentials credentials = (UserCredentials) request.getSession()
				.getAttribute(ATTR_USER_CREDENTIALS);
		
		if (credentials != null) {
			return credentials;
		} else {
			return saveUserCredentials(request, ctx);
		}
	}

	/**
	 * Almacena las credenciales del usuario.
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param ctx
	 *            Contexto de cliente.
	 * @return Credenciales del usuario
	 * @throws ISPACException
	 */
	public static UserCredentials saveUserCredentials(
			HttpServletRequest request, ClientContext ctx)
			throws ISPACException {

		UserCredentials credentials = new UserCredentials(ctx);
		request.getSession().setAttribute(ATTR_USER_CREDENTIALS, credentials);
		
		return credentials;
	}

}
