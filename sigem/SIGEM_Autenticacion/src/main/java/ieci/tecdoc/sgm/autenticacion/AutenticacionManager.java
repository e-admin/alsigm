
package ieci.tecdoc.sgm.autenticacion;

import ieci.tecdoc.sgm.autenticacion.exception.AutenticacionExcepcion;
import ieci.tecdoc.sgm.autenticacion.exception.SeguridadCodigosError;
import ieci.tecdoc.sgm.autenticacion.exception.SeguridadExcepcion;
import ieci.tecdoc.sgm.autenticacion.util.ConectorCertificado;
import ieci.tecdoc.sgm.autenticacion.util.SesionInfo;
import ieci.tecdoc.sgm.autenticacion.util.SesionInfoImpl;
import ieci.tecdoc.sgm.autenticacion.util.Solicitante;
import ieci.tecdoc.sgm.autenticacion.util.TipoAutenticacionCodigos;
import ieci.tecdoc.sgm.autenticacion.util.TipoSolicitante;
import ieci.tecdoc.sgm.autenticacion.util.hook.cert.CertificadoAutenticacionX509Info;
import ieci.tecdoc.sgm.autenticacion.util.utilities.TipoDocumento;
import ieci.tecdoc.sgm.autenticacion.util.utilities.Validador;
import ieci.tecdoc.sgm.base.miscelanea.Goodies;
import ieci.tecdoc.sgm.catalogo_tramites.CatalogoTramites;
import ieci.tecdoc.sgm.catalogo_tramites.util.Conector;
import ieci.tecdoc.sgm.catalogo_tramites.util.ConectorAutenticacionManager;
import ieci.tecdoc.sgm.catalogo_tramites.util.Conectores;
import ieci.tecdoc.sgm.catalogo_tramites.util.TipoConectorImpl;
import ieci.tecdoc.sgm.core.config.impl.spring.MultiEntityContextHolder;

import java.io.ByteArrayInputStream;
import java.lang.reflect.InvocationTargetException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

/**
 * Fachada del sistema de autenticación. En su interfaz expone todas las funcionalidades
 * necesarias en los procesos de autenticación.
 */
public class AutenticacionManager  {
	private static final Logger logger = Logger.getLogger(AutenticacionManager.class);

   /**
    * Obtiene el tipo de autenticación soportado para un identificador
    * determinado . El tipo puede ser ninguno, usuario web de Nivel 2,
    * certificado digital o ambos (usuario web y certificado digital).
    *
    * @param authId Identificador de autenticación.
    * @return El tipo de autenticación.
    * @throws AutenticacionExcepcion Si se produce algún error.
 * @throws SeguridadExcepcion
    */
   public static int getAuthenticationType(String authId, String entidad) throws AutenticacionExcepcion, SeguridadExcepcion {
      int authType = TipoAutenticacionCodigos.NONE;
      Conectores hooks = null;
      Conector hook;
      int count;

      try {
         hooks = ConectorAutenticacionManager.getHooksForProcedure(authId, entidad);
         count = hooks.count();
         for (int i = 0; i < count; i++) {
            hook = hooks.get(i);
            if (hook.getType() == TipoConectorImpl.CERTIFICATE_AUTH) {
                 authType = authType | TipoAutenticacionCodigos.X509_CERTIFICATE;
            } else if (hook.getType() == TipoConectorImpl.WEB_USER_AUTH) {
                 authType = authType | TipoAutenticacionCodigos.WEB_USER;
            }
         }
      } catch (Exception e) {
    	  logger.error("Error al obtener los tipos de autenticacion [getAuthenticationType][Exception][Enidad:"+entidad+"]", e.fillInStackTrace());
    	  throw new SeguridadExcepcion(SeguridadCodigosError.EC_GET_AUTH_INFO);
      }

      return authType;
   }

   /**
    * Obtiene el tipo de autenticación soportado para un identificador
    * determinado . El tipo puede ser ninguno, usuario web de Nivel 2,
    * certificado digital o ambos (usuario web y certificado digital).
    *
    * @param authId Identificador de autenticación.
    * @return El tipo de autenticación.
    * @throws AutenticacionExcepcion Si se produce algún error.
 * @throws SeguridadExcepcion
    */
   public static String getAuthenticationHook(String authId, int type, String entidad) throws AutenticacionExcepcion, SeguridadExcepcion {
      Conectores hooks = null;
      Conector hook;
      int count;

      try {
         hooks = ConectorAutenticacionManager.getHooksForProcedure(authId, entidad);
         count = hooks.count();
         for (int i = 0; i < count; i++) {
            hook = hooks.get(i);
            if (hook.getType() == type) {
                 return hook.getId();
            }
         }
      } catch (Exception e) {
    	  logger.error("Error al obtener los conectores de autenticacion [getAuthenticationHook][Exception][Enidad:"+entidad+"]", e.fillInStackTrace());
    	  throw new SeguridadExcepcion(SeguridadCodigosError.EC_GET_AUTH_INFO);
      }

      return "";
   }

   /**
    * Verifica si se ha realizado una autenticación de usuario previa en la sesión y si
    * esta es válida para el identificador del trámite que se pasa como parámetro.
    *
    * @param actSessionId Identificador de sesión. Si no existe debe ser nulo.
    * @param procId Identificador del trámite.
    * @return El identificador de sesión asignado si existe y es válida, nulo en caso
    * 		  contrario.
    * @throws AutenticacionExcepcion Si se produce algún error.
 * @throws SeguridadExcepcion
    */
   public static SesionInfo getLogin(String actSessionId, String procId, String entidad) throws AutenticacionExcepcion, SeguridadExcepcion {
     Conectores hooks = null;
     Conector hook;
     String actHookId = null;
     int i, count;

     SesionInfo sessionInfo = null;

     logger.debug("GetLogin <-- ActSessionId: " + actSessionId + " ProcedureId: " + procId);

     try {
    	 if (actSessionId != null) {
    		 sessionInfo = SesionManager.get(actSessionId, entidad);
             actHookId = sessionInfo.getHookId();
             if (actHookId != null) {
            	 hooks = ConectorAutenticacionManager.getHooksForProcedure(procId, entidad);
            	 count = hooks.count();
            	 for (i = 0; i < count; i++) {
                     hook = hooks.get(i);
                     if (hook.getId().compareTo(actHookId) == 0) {
                    	 //sessionInfo.setSessionId(Goodies.getUniqueId());
                    	 //SesionManager.add(sessionInfo, entidad);
                    	 if (sessionInfo.getSender().getCertificateIssuer() != null){
                    		 logger.debug("GetLogin --> OldSessionId: " + actSessionId + " NewSessionId: " + sessionInfo.getSessionId() + " ProcedureId: " + procId + " LoginType: X509_CERTIFICATE");
                    	 }else{
                    		 logger.debug("GetLogin --> OldSessionId: " + actSessionId + " NewSessionId: " + sessionInfo.getSessionId() + " ProcedureId: " + procId + " LoginType: WEB_USER");
                    	 }
                    	 break;
                     }
            	 }
             }else{
            	 logger.error("Error al con el conectores de autenticacion [getLogin][conector==null][Enidad:"+entidad+"]");
                 throw new SeguridadExcepcion(String.valueOf(SeguridadCodigosError.EC_GET_HOOK_INFO));
             }
    	 }
     } catch (AutenticacionExcepcion exc) {
    	 logger.error("Error al autenticar usuario [getLogin][AutenticacionExcepcion][Enidad:"+entidad+"]", exc.fillInStackTrace());
    	 throw exc;
     } catch (Exception e) {
    	 logger.error("Error al autenticar usuario [getLogin][Excepcion]", e.fillInStackTrace());
    	 throw new SeguridadExcepcion(SeguridadCodigosError.EC_BAD_LOGIN);
     }

     return sessionInfo;
  }

   /**
    * Permite al usuario acceder al sistema si posee las credenciales adecuadas.
    * En este caso debe poseer un certificado emitido por una CA de las que
    * se encuentran en la lista de la política asociada al procedimiento que se pasa como parámetro.
    *
    * @param procedureId Identificador del procedimiento.
    * @param certificate Certificado presentado (credencial).
    * @return Un identificador de sesión.
    * @throws AutenticacionExcepcion Si se produce algún error.
 * @throws SeguridadExcepcion
    */
   public static String login(String procedureId, X509Certificate certificate, String entidad) throws AutenticacionExcepcion, SeguridadExcepcion {
      String sessionId = null;
      Conectores hooks = null;
      Conector hook;
      int i, count;
      SesionInfo sessionInfo = null;

      logger.debug("Login <-- ProcedureId: " + procedureId + " Certificate: "  + certificate.toString());

      try {
    	  hooks = ConectorAutenticacionManager.getHooksForProcedure(procedureId, entidad);
    	  count = hooks.count();

    	  for (i = 0; i < count; i++) {
    		  hook = hooks.get(i);
    		  if (hook.getType() == TipoConectorImpl.CERTIFICATE_AUTH) {
	              sessionInfo = new SesionInfoImpl();
	              //sessionId = login(hook.getId(), hook.getClassImpl(), hook.getInfo(), authHook.getAdditionalInfo(), certificate, sessionInfo);
	              sessionId = login(hook.getId(), "", certificate, sessionInfo, entidad);
	              if (sessionId != null)
	            	  break;
    		  }
    	  }

    	  if (sessionId == null) {
    		  logger.error("Error al autenticar usuario con certificado [login][sesionId == null][Enidad:"+entidad+"]");
    		  throw new SeguridadExcepcion(SeguridadCodigosError.EC_INVALID_CREDENTIALS);
    	  }
      } catch (AutenticacionExcepcion exc) {
    	  logger.error("Error al autenticar usuario con certificado [login][AutenticacionExcepcion][Enidad:"+entidad+"]", exc.fillInStackTrace());
    	  throw exc;
      } catch (Exception e) {
    	  logger.error("Error al autenticar usuario con certificado [login][Excepcion][Enidad:"+entidad+"]", e.fillInStackTrace());
    	  throw new SeguridadExcepcion(SeguridadCodigosError.EC_BAD_LOGIN);
      }

      return sessionId;
   }

   /**
    * Permite al usuario acceder al sistema si posee las credenciales adecuadas.
    * En este caso debe poseer un certificado emitido por una CA de las que
    * se encuentran en la lista que se pasa como parámetro. Además su
    * certificado no debe estar revocado.
    *
    * @param actSessionId Identificador de sesión. Si no existe debe ser nulo.
    * @param authId Identificador de autenticación.
    * @param certificate Certificado presentado (credencial).
    * @return Un identificador de sesión.
    * @throws AutenticacionExcepcion Si se produce algún error.
 * @throws SeguridadExcepcion
    */
   	public static String login(String actSessionId, String authId, X509Certificate certificate, String entidad) throws AutenticacionExcepcion, SeguridadExcepcion {
      String sessionId = null;
      Conectores hooks = null;
      Conector hook;
      String actHookId = null;
      int i, count;
      boolean found = false;
      SesionInfo sessionInfo = null;

      logger.debug("Login <-- SessionId: " + actSessionId + " Certificate: "  + certificate.toString());

      try {
    	  hooks = ConectorAutenticacionManager.getHooksForProcedure(authId, entidad);
    	  count = hooks.count();
    	  if (actSessionId != null) {
    		  actHookId = SesionManager.getHookId(actSessionId, entidad);
    		  for (i = 0; i < count; i++) {
    			  hook = hooks.get(i);
    			  if (hook.getType() == TipoConectorImpl.CERTIFICATE_AUTH) {
    				  if (hook.getId().compareTo(actHookId) == 0) {
    					  found = true;
    					  sessionId = actSessionId;
    					  break;
    				  }
    			  }
    		  }
    	  }

    	  if (!found) {
    		  for (i = 0; i < count; i++) {
    			  hook = hooks.get(i);
    			  if (hook.getType() == TipoConectorImpl.CERTIFICATE_AUTH) {
    				  sessionInfo = new SesionInfoImpl();

    				  sessionId = login(hook.getId(), "ieci.tecdoc.sgm.autenticacion.util.hook.FNMTCertificate", certificate, sessionInfo, entidad);
    				  if (sessionId != null)
    					  break;
    			  }
    		  }
    	  }

    	  if (sessionId == null) {
    		  logger.error("Error al autenticar usuario con certificado [login][sesionId = null][Enidad:"+entidad+"]");
    		  throw new SeguridadExcepcion(SeguridadCodigosError.EC_INVALID_CREDENTIALS);
    	  }

      } catch (AutenticacionExcepcion exc) {
    	  logger.error("Error al autenticar usuario con certificado [login][AutenticacionExcepcion][Enidad:"+entidad+"]", exc.fillInStackTrace());
    	  throw exc;
      } catch (SeguridadExcepcion se){
    	  logger.error("Error al autenticar usuario con certificado [login][SeguridadExcepcion][Enidad:"+entidad+"]", se.fillInStackTrace());
    	  throw se;
      } catch (Exception e) {
    	  logger.error("Error al autenticar usuario con certificado [login][Excepcion][Enidad:"+entidad+"]", e.fillInStackTrace());
    	  throw new SeguridadExcepcion(SeguridadCodigosError.EC_BAD_LOGIN);
      }

      return sessionId;
   }


   /**
    * Permite al usuario acceder al sistema si posee las credenciales adecuadas.
    * En este caso debe poseer un certificado emitido por una CA de las que
    * se encuentran en la lista que se pasa como parámetro. Además su
    * certificado no debe estar revocado.
    *
    * @param actSessionId Identificador de sesión. Si no existe debe ser nulo.
    * @param authId Identificador de autenticación.
    * @param certificate Certificado presentado (credencial).
    * @param validador ELIMINAR ESTE PARAMETRO AL UNIR LA PAPLICACION CON SPRING
    * @return Un identificador de sesión.
    * @throws AutenticacionExcepcion Si se produce algún error.
 * @throws SeguridadExcepcion
    */
   	public static String login(String actSessionId, String authId, X509Certificate certificate, String validador, String entidad) throws AutenticacionExcepcion, SeguridadExcepcion {
      String sessionId = null;
      Conectores hooks = null;
      Conector hook;
      String actHookId = null;
      int i, count;
      boolean found = false;
      SesionInfo sessionInfo = null;

      logger.debug("Login <-- SessionId: " + actSessionId + " Certificate: "  + certificate.toString());

      try {
    	  hooks = ConectorAutenticacionManager.getHooksForProcedure(authId, entidad);
    	  count = hooks.count();
    	  if (actSessionId != null) {
    		  actHookId = SesionManager.getHookId(actSessionId, entidad);
    		  for (i = 0; i < count; i++) {
    			  hook = hooks.get(i);
    			  if (hook.getType() == TipoConectorImpl.CERTIFICATE_AUTH) {
    				  if (hook.getId().compareTo(actHookId) == 0) {
    					  found = true;
    					  sessionId = actSessionId;
    					  break;
    				  }
    			  }
    		  }
    	  }

    	  if (!found) {
    		  for (i = 0; i < count; i++) {
    			  hook = hooks.get(i);
    			  if (hook.getType() == TipoConectorImpl.CERTIFICATE_AUTH) {
    				  sessionInfo = new SesionInfoImpl();

    				  sessionId = login(hook.getId(), "ieci.tecdoc.sgm.autenticacion.util.hook.FNMTCertificate", certificate, sessionInfo, entidad);
    				  if (sessionId != null)
    					  break;
    			  }
    		  }
    	  }

    	  if (sessionId == null) {
    		  logger.error("Error al autenticar usuario con certificado [login][sesionId = null][Enidad:"+entidad+"]");
    		  throw new SeguridadExcepcion(SeguridadCodigosError.EC_INVALID_CREDENTIALS);
    	  }

      } catch (AutenticacionExcepcion exc) {
    	  logger.error("Error al autenticar usuario con certificado [login][AutenticacionExcepcion][Enidad:"+entidad+"]", exc.fillInStackTrace());
    	  throw exc;
      } catch (SeguridadExcepcion se){
    	  logger.error("Error al autenticar usuario con certificado [login][SeguridadExcepcion][Enidad:"+entidad+"]", se.fillInStackTrace());
    	  throw se;
      } catch (Exception e) {
    	  logger.error("Error al autenticar usuario con certificado [login][Excepcion][Enidad:"+entidad+"]", e.fillInStackTrace());
    	  throw new SeguridadExcepcion(SeguridadCodigosError.EC_BAD_LOGIN);
      }

      return sessionId;
   }


   /**
    * Permite al usuario acceder al sistema si posee las credenciales adecuadas.
    * En este caso debe poseer un certificado emitido por una CA de las que
    * se encuentran en la lista que se pasa como parámetro. Además su
    * certificado no debe estar revocado.
    *
    * @param actSessionId Identificador de sesión. Si no existe debe ser nulo.
    * @param X509CertString Cadena de texto con el certificado.
    * @return Un identificador de sesión.
    * @throws AutenticacionExcepcion Si se produce algún error.
 * @throws SeguridadExcepcion
    */
   public static String loginValidador(String actSessionId, String authId, String X509CertString, String validador, String entidad) throws AutenticacionExcepcion, SeguridadExcepcion {
   		X509Certificate cert = null;
   		try {
			CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
			cert = (X509Certificate) certFactory.generateCertificate(new ByteArrayInputStream(X509CertString.getBytes()));
		} catch (CertificateException e) {
			logger.error("Error al obtener certificado de usuario [loginValidator][CertificateExcepcion][Enidad:"+entidad+"]", e.fillInStackTrace());
            throw new AutenticacionExcepcion(SeguridadCodigosError.EC_INVALID_CREDENTIALS);
		}
   		return login(actSessionId, authId, cert, validador);
   }

   /**
    * Permite al usuario acceder al sistema si posee las credenciales adecuadas.
    * En este caso, la información debe ser previamente recuperada de la validación de
    * los datos de usuario contra un aplicativo externo.
    *
    * @param actSessionId Identificador de sesión actual.
    * @param user Login de usuario.
    * @param email Correo del usuario.
    * @param senderId Identificador del remitente (NIF).
    * @return Un identificador de sesión.
    * @throws AutenticacionExcepcion Si se produce algún error.
 * @throws SeguridadExcepcion
    */
   public static String login(String actSessionId, /*String authId,*/ String nombre, String apellidos, String email, String senderId, String entidad) throws AutenticacionExcepcion, SeguridadExcepcion {
      String sessionId = null;
      Conectores hooks = null;
      Conector hook;
      String actHookId = null;
      int i, count;
      boolean found = false;
      SesionInfo sessionInfo = null;

      logger.debug("Login <-- SessionId: " + actSessionId + " User: " + nombre + " " + apellidos + " SenderId: " + senderId);

      try {
    	  hooks = CatalogoTramites.getHooks(entidad);
    	  count = hooks.count();

    	  if (actSessionId != null) {
    		  actHookId = SesionManager.getHookId(actSessionId, entidad);
    		  for (i = 0; i < count; i++) {
    			  hook = hooks.get(i);
    			  if (hook.getType() == TipoConectorImpl.WEB_USER_AUTH) {
    				  if (hook.getId().compareTo(actHookId) == 0) {
    					  found = true;
    					  sessionId = actSessionId;
    					  break;
    				  }
    			  }
    		  }
    	  }

    	  if (!found) {
    		  for (i = 0; i < count; i++) {
    			  hook = hooks.get(i);
    			  if (hook.getType() == TipoConectorImpl.WEB_USER_AUTH) {
    				  sessionInfo = new SesionInfoImpl();
    				  sessionId = loginWeb(hook.getId(), ""/*hook.getClassImpl()*/, nombre, apellidos, email, senderId, sessionInfo, entidad);
    				  if (sessionId != null)
    					  break;
    			  }
    		  }
    	  }
    	  if (sessionId == null) {
    		  logger.error("Error al autenticar usuario con usuario/contraseña [login][sesionId = null]");
    		  throw new SeguridadExcepcion(SeguridadCodigosError.EC_INVALID_CREDENTIALS);
    	  }

          logger.debug("Login <-- OldSessionId: " + actSessionId + "NewSessionId: " + sessionId + " User: " + nombre + " " + apellidos + " SenderId: " + senderId);
      } catch (AutenticacionExcepcion exc) {
    	  logger.error("Error al autenticar usuario con usuario/contraseña [login][AutenticacionExcepcion][Enidad:"+entidad+"]", exc.fillInStackTrace());
    	  throw exc;
      } catch (Exception e) {
    	  logger.error("Error al autenticar usuario con usuario/contraseña [login][Excepcion][Enidad:"+entidad+"]", e.fillInStackTrace());
    	  throw new SeguridadExcepcion(SeguridadCodigosError.EC_BAD_LOGIN);
      }
      return sessionId;
   }


   /**
    * Desconecta a un usuario del sistema.
    *
    * @param sessionId Identificador de sesión.
    * @throws AutenticacionExcepcion Si se produce algún error.
    */
   public static void logout(String sessionId, String entidad) throws AutenticacionExcepcion {
      boolean result = false;
      logger.debug("Logout <-- SessionId: " + sessionId);
      try {
         SesionManager.delete(sessionId, entidad);
         result = true;
      } catch (AutenticacionExcepcion exc) {
    	  logger.error("Error al desconectar al usuario [logout][AutenticacionExcepcion][Enidad:"+entidad+"]", exc.fillInStackTrace());
    	  throw exc;
      } catch (Exception e) {
    	  logger.error("Error al desconectar al usuario [logout][Excepcion][Enidad:"+entidad+"]", e.fillInStackTrace());
    	  throw new AutenticacionExcepcion(SeguridadCodigosError.EC_BAD_LOGOUT);
      } finally {
         logger.debug("Logout --> SessionId: " + sessionId + " Result: " + ((result)?"Logout":"Not Logout"));
      }

   }

   /**$
    * Permite acceder al usuario al sistema mediante un certificado válido
    * @param hookId Identificador del conector de autenticación
    * @param classImpl Clase que implementa la autenticación
    * @param certificate Certificado de usuario
    * @param sessionInfo Información de la session de usuario
    * @return Identificador de la sesión si todo es correcto, null en caso contrario
    * @throws Exception
    */
   private static String login(String hookId, String classImpl, X509Certificate certificate,
                               SesionInfo sessionInfo, String entidad) throws Exception {
      String sessionId = null;
      CertificadoAutenticacionX509Info cert;
      Solicitante sender;

      logger.debug("Login <-- SessionInfo: " + sessionInfo + " HookId: " + hookId + " Certificate:" + certificate.toString());

      try {
    	  MultiEntityContextHolder.setEntity(entidad);
    	 cert = ConectorCertificado.verifyCertificate(classImpl, certificate);
         if (cert.isValid()) {
            sessionId = Goodies.getUniqueId();
            sender = new Solicitante();
            sender.setId(cert.getNIF());
            sender.setEmail(cert.getEmail());
            sender.setName(cert.getName());
            sender.setFirstName(cert.getFirstName());
            sender.setSurName(cert.getSurName());
            sender.setSurName2(cert.getSurName2());
            sender.setCertificateIssuer(cert.getIssuer());
            sender.setCertificateSN(cert.getSerialNumber());
            if (!cert.isLegalEntity())
               sender.setInQuality(String.valueOf(TipoSolicitante.INDIVIDUAL));
            else
               sender.setInQuality(String.valueOf(TipoSolicitante.LEGAL_REPRESENTATIVE));
            sender.setSocialName(cert.getSocialName());
            sender.setCIF(cert.getCIF());
            //########## FALTA ESTE PARAMETRO ##########
//          sender.setAdditionalInfo(cert.getAdditionalInfo());
            sessionInfo.setHookId(hookId);
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String fecha = sdf.format(new Date());
            sessionInfo.setLoginDate(fecha);
            sessionInfo.setSessionId(sessionId);
            sessionInfo.setSender(sender);

            sessionInfo.setIdEntidad(entidad);

            SesionManager.add(sessionInfo, entidad);

            logger.debug("Login --> SessionInfo: " + sessionInfo + " LoginType: X509_CERTIFICATE CertStatus: " + cert.getStatus() + " CAResponse: " + cert.getCaResponse());
         }
      } catch (AutenticacionExcepcion exc) {
    	  logger.error("Error al validar el certificado [login][AutenticacionExcepcion][Entidad:"+entidad+"]", exc.fillInStackTrace());
    	  throw exc;
      } catch (InvocationTargetException e) {
    	  logger.error("Error al validar el certificado [login][Excepcion][Entidad:"+entidad+"]", e.fillInStackTrace());
    	  throw new SeguridadExcepcion(e.getCause().getMessage(), e);
      } catch (Exception e) {
    	  logger.error("Error al validar el certificado [login][Excepcion][Entidad:"+entidad+"]", e.fillInStackTrace());
    	  throw new SeguridadExcepcion(e.getMessage(), e);
      }

      return sessionId;
   }

   /**
    * Permite al usuario acceder mediante login en la Web
    * @param hookId Identificador del conector
    * @param classImpl
    * @param user Nombre del usuario
    * @param email Email del usuario
    * @param senderId Identificador de usuario
    * @param sessionInfo Información de la sesión de usuario
    * @return Identificador de la sesión si es correcto, null en caso contrario
    * @throws Exception
    */
   private static String loginWeb(String hookId, String classImpl, String nombre, String apellidos,
           String email, String senderId, SesionInfo sessionInfo, String entidad) throws Exception {
      String sessionId = null;
      Solicitante sender;

      logger.debug("LoginWeb <-- User: " + nombre + " " + apellidos + " Email: " + email + " SenderId: " + senderId + " HookId: " + hookId + " SessionInfo: " + sessionInfo);

      try {
    	  sessionId = Goodies.getUniqueId();

    	  sender = new Solicitante();
    	  sender.setId(senderId);
    	  sender.setEmail(email);

    	  String userName = nombre; //user.substring(0,user.indexOf(" "));
    	  String userSurname = apellidos; //user.substring(user.indexOf(" ")+1);
    	  if (userSurname != null) {
    		  userName = userName + " " + userSurname;
    	  }
    	  //sender.setName(userName + " " + userSurname);
    	  //sender.setSurName(userSurname);

    	  // Persona juridica
    	  if (Validador.validateDocumentType(senderId) == TipoDocumento.DOC_CIF) {

    		  sender.setCIF(senderId);
    		  sender.setInQuality(String.valueOf(TipoSolicitante.LEGAL_REPRESENTATIVE));
    		  sender.setSocialName(userName.trim());
    	  }
    	  // Persona fisica
    	  else {
    		  sender.setId(senderId);
    		  sender.setInQuality(String.valueOf(TipoSolicitante.INDIVIDUAL));
    		  sender.setName(userName.trim());
    	  }

    	  sessionInfo.setHookId(hookId);
    	  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	  String fecha = sdf.format(new Date());
    	  sessionInfo.setLoginDate(fecha);
    	  sessionInfo.setSessionId(sessionId);
    	  sessionInfo.setSender(sender);
    	  SesionManager.add(sessionInfo, entidad);

    	  logger.debug("LoginWeb --> User: " + nombre + " " + apellidos + " SenderId: " + senderId + " LoginType: WEB_USER SessionInfo: " + sessionInfo);
      } catch (AutenticacionExcepcion exc) {
    	  logger.error("Error al validar al usuario [loginWeb][AutenticacionExcepcion][Enidad:"+entidad+"]", exc.fillInStackTrace());
    	  throw exc;
      } catch (Exception e) {
    	  logger.error("Error al validar al usuario [loginWeb][Excepcion][Enidad:"+entidad+"]", e.fillInStackTrace());
    	  throw new SeguridadExcepcion(SeguridadCodigosError.EC_BAD_LOGIN);
      }

      return sessionId;
   }

}

