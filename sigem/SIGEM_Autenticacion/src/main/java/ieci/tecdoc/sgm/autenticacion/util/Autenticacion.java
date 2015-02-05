/*
 * Created on 11-jul-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ieci.tecdoc.sgm.autenticacion.util;

/**
 * @author IECI S.A.
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

import java.security.cert.X509Certificate;
public interface Autenticacion  {
	
	
	public TipoAutenticacion getAuthenticationType(String authenticationId) throws Exception;

	   /**
	    * Permite al usuario acceder al sistema si posee las credenciales adecuadas.
	    * En este caso debe poseer un certificado emitido por una CA de las que
	    * se encuentran en la lista que se pasa como parámetro. Además su 
	    * certificado no debe estar revocado.
	    * 
	    * @param certificate Certificado.
	    * @param caList Lista cd CAs autorizadas.
	    * @return Un identificador de sesión.
	    * @throws java.lang.Exception Si se produce algún error.
	    */
   public void login(X509Certificate certificate, String sessionId) throws Exception;  
		
	   
  /**
   * Permite al usuario acceder al sistema si posee las credenciales adecuadas.
   * En este caso debe poseer un usuario y password que se validará contra un 
   * aplicativo externo.
   *  
   * @param user login de usuario.
   * @param password contraseña de usuario.
   * @param senderId
   * @param sessionId
   * @return Un identificador de sesión.
   * @throws Exception Si se produce algún error.
   */
   public void login(String user, String password, String senderId, String sessionId) throws Exception;

   /**
    * Permite al usuario cancelar su sesión de acceso a la aplicación
    * @param sessionId Identificador de sesión
    * @throws Exception
    */
   public void logout(String sessionId) throws Exception;
   
   /**
    * Obtiene los datos de acceso del usuario
    * @param sessioId Identificador de sesión
    * @return
    */
   public String getUserLogin(String sessioId);
}

