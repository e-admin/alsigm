
package ieci.tecdoc.sgm.autenticacion.util;

import java.security.cert.X509Certificate;

public class AutenticacionImpl implements Autenticacion {

  public TipoAutenticacion getAuthenticationType(String authenticationId) 
  {
  	TipoAutenticacion authenticationType = null;
    return authenticationType;
  }

  public void login(X509Certificate certificate, String sessionId) 
  {
  }

  public void login(String user, String password, String senderId, String sessionId) 
  {
  }

  public void logout(String sessionId) 
  {
  }

  public String getUserLogin(String sessioId) 
  {
  return null;
  }
}