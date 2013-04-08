package ieci.tecdoc.sgm.autenticacion.ws.server;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.xml.soap.SOAPException;

import ieci.tecdoc.sgm.core.base64.Base64;
import ieci.tecdoc.sgm.core.exception.SigemException;
import ieci.tecdoc.sgm.core.services.ConstantesServicios;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.ServiciosUtils;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;
import ieci.tecdoc.sgm.core.services.sesion.InfoUsuario;
import ieci.tecdoc.sgm.core.services.sesion.LimpiarSesiones;
import ieci.tecdoc.sgm.core.services.sesion.MetodoAutenticacion;
import ieci.tecdoc.sgm.core.services.sesion.ServicioSesionUsuario;
import ieci.tecdoc.sgm.core.services.sesion.SesionUsuario;
import ieci.tecdoc.sgm.core.services.sesion.SesionUsuarioException;
import ieci.tecdoc.sgm.core.ws.axis.UtilAxis;

import org.apache.log4j.Logger;

public class SesionUsuarioWebService {

	private static final Logger logger = Logger.getLogger(SesionUsuarioWebService.class);

	private static final String SERVICE_NAME = ConstantesServicios.SERVICE_USER_SESSION;

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
    * @throws SesionUsuarioException
    */
   public IdentificadorSesion loginExternalUser(LoginExternalUser poLogin, Entidad entidad){
	   try {
		   IdentificadorSesion oIdSesion = getIdentificadorSesion(getServicioSesionUsuario()
		   				.login(	poLogin.getActSessionId(),
		   						poLogin.getNombre(),
		   						poLogin.getApellidos(),
		   						poLogin.getEmail(),
		   						poLogin.getSenderId(),
		   						getEntidadServicio(entidad)));
		   return (IdentificadorSesion)ServiciosUtils.completeReturnOK((RetornoServicio) oIdSesion);
		} catch (SesionUsuarioException e) {
			logger.error("Error en proceso de login de usuario externo.", e);
			return (IdentificadorSesion)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new IdentificadorSesion()),
								e.getErrorCode());
		}  catch (SigemException e) {
			logger.error("Error en proceso de login de usuario externo.", e);
			return (IdentificadorSesion)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new IdentificadorSesion()),
								e.getErrorCode());
		}  catch (Throwable e){
			logger.error("Error en proceso de login de usuario externo.", e);
			return (IdentificadorSesion)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new IdentificadorSesion()));
		}
   }

   /**
    * Permite al usuario acceder al sistema si posee las credenciales adecuadas.
    * En este caso debe poseer un certificado emitido por una CA de las que
    * se encuentran en la lista de la política asociada al procedimiento que se pasa como parámetro.
    *
    * @param procedureId Identificador del procedimiento.
    * @param certificate Certificado presentado (credencial).
    * @return Un identificador de sesión.
    * @throws SesionUsuarioException Si se produce algún error.
    */
   public IdentificadorSesion loginCertificate(LoginCertificado poLogin, Entidad poEntidad){
	   try {
		   InputStream is = new ByteArrayInputStream(Base64.decode(poLogin.getB64certificate()));
		   CertificateFactory cf = CertificateFactory.getInstance("X.509");
		   X509Certificate oCertificado = (X509Certificate)cf.generateCertificate(is);
		   IdentificadorSesion oIdSesion = getIdentificadorSesion(getServicioSesionUsuario()
		   				.login(poLogin.getProcedureId(), oCertificado, getEntidadServicio(poEntidad)));
		   is.close();
		   return (IdentificadorSesion)ServiciosUtils.completeReturnOK((RetornoServicio) oIdSesion);
	   } catch (SesionUsuarioException e) {
			logger.error("Error en proceso de login de usuario.", e);
			return (IdentificadorSesion)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new IdentificadorSesion()),
								e.getErrorCode());
	   }  catch (SigemException e) {
			logger.error("Error en proceso de login de usuario.", e);
			return (IdentificadorSesion)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new IdentificadorSesion()),
								e.getErrorCode());
	   }  catch (Throwable e){
			logger.error("Error en proceso de login de usuario.", e);
			return (IdentificadorSesion)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new IdentificadorSesion()));
	   }
   }

   /**
    * Permite al usuario acceder al sistema si posee las credenciales adecuadas.
    * En este caso debe poseer un certificado Además su
    * certificado no debe estar revocado.
    *
    * @param actSessionId Identificador de sesión. Si no existe debe ser nulo.
    * @param authId Identificador de autenticación.
    * @param certificate Certificado presentado (credencial).
    * @return Un identificador de sesión.
    * @throws SesionUsuarioException Si se produce algún error.
    */
   public IdentificadorSesion loginCertificateAuth(LoginCertificadoAutoridad poLogin, Entidad poEntidad){
	   try {
		   InputStream is = new ByteArrayInputStream(Base64.decode(poLogin.getB64certificate()));
		   CertificateFactory cf = CertificateFactory.getInstance("X.509");
		   X509Certificate oCertificado = (X509Certificate)cf.generateCertificate(is);
		   IdentificadorSesion oIdSesion = getIdentificadorSesion(getServicioSesionUsuario()
		   				.login(poLogin.getProcedureId(), poLogin.getAuthId(), oCertificado, getEntidadServicio(poEntidad) ));
		   is.close();
		   return (IdentificadorSesion)ServiciosUtils.completeReturnOK((RetornoServicio) oIdSesion);
	   } catch (SesionUsuarioException e) {
			logger.error("Error en proceso de login de usuario.", e);
			return (IdentificadorSesion)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new IdentificadorSesion()),
								e.getErrorCode());
	   }  catch (SigemException e) {
			logger.error("Error en proceso de login de usuario.", e);
			return (IdentificadorSesion)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new IdentificadorSesion()),
								e.getErrorCode());
	   }  catch (Throwable e){
			logger.error("Error en proceso de login de usuario.", e);
			return (IdentificadorSesion)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new IdentificadorSesion()));
	   }
   }

   /**
    * Desconecta a un usuario del sistema.
    *
    * @param sessionId Identificador de sesión.
    * @throws SesionUsuarioException Si se produce algún error.
    */
   public RetornoServicio logout(IdentificadorSesion sessionId, Entidad poEntidad){
		try {
			getServicioSesionUsuario().logout(sessionId.getSessionId(), getEntidadServicio(poEntidad));
			return ServiciosUtils.createReturnOK();
		} catch (SesionUsuarioException e) {
			logger.error("Error en proceso de logout de usuario.", e);
			return ServiciosUtils.createReturnError(e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error en proceso de logout de usuario.", e);
			return ServiciosUtils.createReturnError(e.getErrorCode());
		} catch (Throwable e){
			logger.error("Error en proceso de logout de usuario.", e);
			return ServiciosUtils.createReturnError();
		}
   }

   /**
    * Crea una nueva sesión de usuario en el sistema. Esta sesión de usuario es compartida
    * por las aplicaciones de tramitación de SIGEM.
    * @param poSessionUsuario Datos de la sesión de usuario.
    * @throws SesionUsuarioException En caso de producirse algún error.
    */
   public RetornoServicio crearSesion(ieci.tecdoc.sgm.autenticacion.ws.server.SesionUsuario poSessionUsuario, Entidad poEntidad){
		try {
			getServicioSesionUsuario().crearSesion(getSesionServicio(poSessionUsuario), getEntidadServicio(poEntidad));
			return ServiciosUtils.createReturnOK();
		} catch (SesionUsuarioException e) {
			logger.error("Error en proceso de logout de usuario.", e);
			return ServiciosUtils.createReturnError(e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error en proceso de logout de usuario.", e);
			return ServiciosUtils.createReturnError(e.getErrorCode());
		} catch (Throwable e){
			logger.error("Error en proceso de logout de usuario.", e);
			return ServiciosUtils.createReturnError();
		}
   }

   /**
    * Elimina una sesión de usuario del sistema. El usuario estará "deslogado" de todas las
    * aplicaciones de tramitación de SIGEM.
    * @param psIdSesion Identificador de sesión.
    * @throws SesionUsuarioException En caso de producirse algún error.
    */
   public RetornoServicio borrarSesion(IdentificadorSesion poIdSesion, Entidad poEntidad){
		try {
			getServicioSesionUsuario().borrarSesion(poIdSesion.getSessionId(), getEntidadServicio(poEntidad));
			return ServiciosUtils.createReturnOK();
		} catch (SesionUsuarioException e) {
			logger.error("Error en borrado de sesión.", e);
			return ServiciosUtils.createReturnError(e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error en borrado de sesión.", e);
			return ServiciosUtils.createReturnError(e.getErrorCode());
		} catch (Throwable e){
			logger.error("Error en borrado de sesión.", e);
			return ServiciosUtils.createReturnError();
		}
   }

   /**
    * Elimina del sistema todas las sesiones de usuario que hayan sobrepasado el tiempo
    * máximo de existencia definido por configuración.
    * @throws SesionUsuarioException En caso de producirse algún error.
    */
   public RetornoServicio limpiarSesiones(Entidad poEntidad){
		try {
			getServicioSesionUsuario().limpiarSesiones(getEntidadServicio(poEntidad));
			return ServiciosUtils.createReturnOK();
		} catch (SesionUsuarioException e) {
			logger.error("Error en borrado de sesiones.", e);
			return ServiciosUtils.createReturnError(e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error en borrado de sesiones.", e);
			return ServiciosUtils.createReturnError(e.getErrorCode());
		} catch (Throwable e){
			logger.error("Error en borrado de sesiones.", e);
			return ServiciosUtils.createReturnError();
		}
   }

   /**
    * Elimina del sistema todas las sesiones de usuario que hayan sobrepasado el tiempo
    * máximo que llega como parámetro.
    * @param timeout Intervalo de tiempo hasta el momento actual máximo para mantener sesiones.
    * @throws SesionUsuarioException En caso de producirse algún error.
    */
   public RetornoServicio limpiarSesionesTimeOut(LimpiarSesiones timeout, Entidad poEntidad){
		try {
			getServicioSesionUsuario().limpiarSesiones(Integer.valueOf(timeout.getTimeout()).intValue(), getEntidadServicio(poEntidad));
			return ServiciosUtils.createReturnOK();
		} catch (SesionUsuarioException e) {
			logger.error("Error en borrado de sesiones.", e);
			return ServiciosUtils.createReturnError(e.getErrorCode());
		} catch (SigemException e) {
			logger.error("Error en borrado de sesiones.", e);
			return ServiciosUtils.createReturnError(e.getErrorCode());
		} catch (Throwable e){
			logger.error("Error en borrado de sesiones.", e);
			return ServiciosUtils.createReturnError();
		}
   }

   /**
    * Obtiene todos los datos de la sesión de usuario.
    * @param sessionId Identificador de la sesión de usuario.
    * @return SesionUsuario Datos de la sesión de usuario.
    * @throws SesionUsuarioException En caso de producirse algún error.
    */
   public ieci.tecdoc.sgm.autenticacion.ws.server.SesionUsuario obtenerSesion(IdentificadorSesion poIdSesion, Entidad poEntidad){
	   try {
		   ieci.tecdoc.sgm.autenticacion.ws.server.SesionUsuario oSesion =
			   getSesionUsuarioWS(getServicioSesionUsuario().obtenerSesion(poIdSesion.getSessionId(), getEntidadServicio(poEntidad)));

		   return (ieci.tecdoc.sgm.autenticacion.ws.server.SesionUsuario)
		   				ServiciosUtils.completeReturnOK((RetornoServicio) oSesion);
	   } catch (SesionUsuarioException e) {
			logger.error("Error obteniendo sesión de usuario.", e);
			return (ieci.tecdoc.sgm.autenticacion.ws.server.SesionUsuario)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new ieci.tecdoc.sgm.autenticacion.ws.server.SesionUsuario()),
								e.getErrorCode());
	   }  catch (SigemException e) {
			logger.error("Error obteniendo sesión de usuario.", e);
			return (ieci.tecdoc.sgm.autenticacion.ws.server.SesionUsuario)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new ieci.tecdoc.sgm.autenticacion.ws.server.SesionUsuario()),
								e.getErrorCode());
	   }  catch (Throwable e){
			logger.error("Error inexperado obteniendo sesión de usuario.", e);
			return (ieci.tecdoc.sgm.autenticacion.ws.server.SesionUsuario)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new ieci.tecdoc.sgm.autenticacion.ws.server.SesionUsuario()));
	   }
   }

   /**
    * Obtiene la información personal del usuario que inició la sesión en el sistema.
    * @param sessionId Identificador de sesión.
    * @return InfoUsuario Información personal del usuario.
    * @throws SesionUsuarioException En caso de producirse algún error.
    */
   public ieci.tecdoc.sgm.autenticacion.ws.server.InfoUsuario getInfoUsuario(IdentificadorSesion poIdSesion, Entidad poEntidad){
	   try {
		   ieci.tecdoc.sgm.autenticacion.ws.server.InfoUsuario oInfo =
			   		getInfoUsuarioWS(getServicioSesionUsuario().getInfoUsuario(poIdSesion.getSessionId(), getEntidadServicio(poEntidad)));
		   return (ieci.tecdoc.sgm.autenticacion.ws.server.InfoUsuario)
				ServiciosUtils.completeReturnOK((RetornoServicio) oInfo);
	   } catch (SesionUsuarioException e) {
			logger.error("Error en proceso de login de usuario externo.", e);
			return (ieci.tecdoc.sgm.autenticacion.ws.server.InfoUsuario)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new ieci.tecdoc.sgm.autenticacion.ws.server.InfoUsuario()),
								e.getErrorCode());
	   }  catch (SigemException e) {
			logger.error("Error en proceso de login de usuario externo.", e);
			return (ieci.tecdoc.sgm.autenticacion.ws.server.InfoUsuario)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new ieci.tecdoc.sgm.autenticacion.ws.server.InfoUsuario()),
								e.getErrorCode());
	   }  catch (Throwable e){
			logger.error("Error en proceso de login de usuario externo.", e);
			return (ieci.tecdoc.sgm.autenticacion.ws.server.InfoUsuario)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new ieci.tecdoc.sgm.autenticacion.ws.server.InfoUsuario()));
	   }
   }

   /**
    * Obtiene el identificador del método de autenticación utilizado para iniciar la sesión
    * en el sistema.
    * @param sessionId Identificador de sesión.
    * @return Identificador del método de autenticación utilizado al crear la sesión.
    * @throws SesionUsuarioException En caso de producirse algún error.
    */
   public ieci.tecdoc.sgm.autenticacion.ws.server.MetodoAutenticacion getIdMetodoAutenticacion(IdentificadorSesion poIdSesion, Entidad poEntidad){
	   try {
		   ieci.tecdoc.sgm.autenticacion.ws.server.MetodoAutenticacion oMetodo = new ieci.tecdoc.sgm.autenticacion.ws.server.MetodoAutenticacion();
		   oMetodo.setId(getServicioSesionUsuario().getIdMetodoAutenticacion(poIdSesion.getSessionId(), getEntidadServicio(poEntidad)));
		   return (ieci.tecdoc.sgm.autenticacion.ws.server.MetodoAutenticacion)
				ServiciosUtils.completeReturnOK((RetornoServicio) oMetodo);
	   } catch (SesionUsuarioException e) {
			logger.error("Error obteniendo id método de autenticación.", e);
			return (ieci.tecdoc.sgm.autenticacion.ws.server.MetodoAutenticacion)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new ieci.tecdoc.sgm.autenticacion.ws.server.MetodoAutenticacion()),
								e.getErrorCode());
	   }  catch (SigemException e) {
			logger.error("Error obteniendo id método de autenticación.", e);
			return (ieci.tecdoc.sgm.autenticacion.ws.server.MetodoAutenticacion)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new ieci.tecdoc.sgm.autenticacion.ws.server.MetodoAutenticacion()),
								e.getErrorCode());
	   }  catch (Throwable e){
			logger.error("Error obteniendo id método de autenticación.", e);
			return (ieci.tecdoc.sgm.autenticacion.ws.server.MetodoAutenticacion)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new ieci.tecdoc.sgm.autenticacion.ws.server.MetodoAutenticacion()));
	   }
   }

   /**
    * Obtiene la información del método de autenticación utilizado para iniciar la sesión.
    * @param sessionId Identificador de la sesión.
    * @return MetodoAutenticacion Objeto con la información sobre el método de autenticación.
    * @throws SesionUsuarioException En caso de producirse algún error.
    */
   public ieci.tecdoc.sgm.autenticacion.ws.server.MetodoAutenticacion getMetodoAutenticacion(IdentificadorSesion poIdSesion, Entidad poEntidad){
	   try {
		   ieci.tecdoc.sgm.autenticacion.ws.server.MetodoAutenticacion oMetodo =
			   getMetodoAutenticacionWS(getServicioSesionUsuario().getMetodoAutenticacion(poIdSesion.getSessionId(), getEntidadServicio(poEntidad)));
		   return (ieci.tecdoc.sgm.autenticacion.ws.server.MetodoAutenticacion)
				ServiciosUtils.completeReturnOK((RetornoServicio) oMetodo);
	   } catch (SesionUsuarioException e) {
			logger.error("Error obteniendo id método de autenticación.", e);
			return (ieci.tecdoc.sgm.autenticacion.ws.server.MetodoAutenticacion)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new ieci.tecdoc.sgm.autenticacion.ws.server.MetodoAutenticacion()),
								e.getErrorCode());
	   }  catch (SigemException e) {
			logger.error("Error obteniendo id método de autenticación.", e);
			return (ieci.tecdoc.sgm.autenticacion.ws.server.MetodoAutenticacion)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new ieci.tecdoc.sgm.autenticacion.ws.server.MetodoAutenticacion()),
								e.getErrorCode());
	   }  catch (Throwable e){
			logger.error("Error obteniendo id método de autenticación.", e);
			return (ieci.tecdoc.sgm.autenticacion.ws.server.MetodoAutenticacion)
					ServiciosUtils.completeReturnError(
								(RetornoServicio)(new ieci.tecdoc.sgm.autenticacion.ws.server.MetodoAutenticacion()));
	   }
   }

	private ServicioSesionUsuario getServicioSesionUsuario() throws SOAPException, SigemException{
		String cImpl = UtilAxis.getImplementacion(org.apache.axis.MessageContext.getCurrentContext());
		if((cImpl == null) ||("".equals(cImpl))){
			return LocalizadorServicios.getServicioSesionUsuario();
		}else{
			StringBuffer sbImpl = new StringBuffer(SERVICE_NAME).append(".").append(cImpl);
			return LocalizadorServicios.getServicioSesionUsuario(sbImpl.toString());
		}
	}

	private IdentificadorSesion getIdentificadorSesion(String psId){
		IdentificadorSesion oId = new IdentificadorSesion();
		oId.setSessionId(psId);
		return oId;
	}

	private SesionUsuario getSesionServicio(ieci.tecdoc.sgm.autenticacion.ws.server.SesionUsuario poSesion){
		if(poSesion == null){
			return null;
		}
		SesionUsuario oSesion = new SesionUsuario();
		oSesion.setHookId(poSesion.getHookId());
		oSesion.setLoginDate(poSesion.getLoginDate());
		oSesion.setSessionId(poSesion.getSessionId());
		oSesion.setSender(getInfoUsuarioServicio(poSesion.getSender()));
		return oSesion;
	}

	private InfoUsuario getInfoUsuarioServicio(ieci.tecdoc.sgm.autenticacion.ws.server.InfoUsuario poUser){
		if(poUser == null){
			return null;
		}
		InfoUsuario oUser = new InfoUsuario();
		oUser.setCertificateIssuer(poUser.getCertificateIssuer());
		oUser.setCertificateSN(poUser.getCertificateSN());
		oUser.setCIF(poUser.getCIF());
		oUser.setEmail(poUser.getEmail());
		oUser.setId(poUser.getId());
		oUser.setInQuality(poUser.getInQuality());
		oUser.setName(poUser.getName());
		oUser.setSocialName(poUser.getSocialName());
		oUser.setFirstName(poUser.getFirstName());
		oUser.setSurName(poUser.getSurName());
		oUser.setSurName2(poUser.getSurName2());
		return oUser;
	}

	private ieci.tecdoc.sgm.autenticacion.ws.server.SesionUsuario getSesionUsuarioWS(SesionUsuario poSesion){
		if(poSesion == null){
			return null;
		}
		ieci.tecdoc.sgm.autenticacion.ws.server.SesionUsuario oSesion = new ieci.tecdoc.sgm.autenticacion.ws.server.SesionUsuario();
		oSesion.setHookId(poSesion.getHookId());
		oSesion.setLoginDate(poSesion.getLoginDate());
		oSesion.setSessionId(poSesion.getSessionId());
		oSesion.setSender(getInfoUsuarioWS(poSesion.getSender()));
		return oSesion;
	}

	private ieci.tecdoc.sgm.autenticacion.ws.server.InfoUsuario getInfoUsuarioWS(InfoUsuario poUser){
		if(poUser == null){
			return null;
		}
		ieci.tecdoc.sgm.autenticacion.ws.server.InfoUsuario oUser = new ieci.tecdoc.sgm.autenticacion.ws.server.InfoUsuario();
		oUser.setCertificateIssuer(poUser.getCertificateIssuer());
		oUser.setCertificateSN(poUser.getCertificateSN());
		oUser.setCIF(poUser.getCIF());
		oUser.setEmail(poUser.getEmail());
		oUser.setId(poUser.getId());
		oUser.setInQuality(poUser.getInQuality());
		oUser.setName(poUser.getName());
		oUser.setSocialName(poUser.getSocialName());
		oUser.setFirstName(poUser.getFirstName());
		oUser.setSurName(poUser.getSurName());
		oUser.setSurName2(poUser.getSurName2());
		return oUser;
	}

	private ieci.tecdoc.sgm.autenticacion.ws.server.MetodoAutenticacion getMetodoAutenticacionWS(MetodoAutenticacion poMetodo){
		if(poMetodo ==  null){
			return null;
		}
		ieci.tecdoc.sgm.autenticacion.ws.server.MetodoAutenticacion oMetodo = new ieci.tecdoc.sgm.autenticacion.ws.server.MetodoAutenticacion();
		oMetodo.setDescription(poMetodo.getDescription());
		oMetodo.setId(poMetodo.getId());
		oMetodo.setType(poMetodo.getType());
		return oMetodo;
	}

	private ieci.tecdoc.sgm.core.services.dto.Entidad getEntidadServicio(Entidad poEntidad){
		if(poEntidad == null){
			return null;
		}
		ieci.tecdoc.sgm.core.services.dto.Entidad oEntidad = new ieci.tecdoc.sgm.core.services.dto.Entidad();
		oEntidad.setIdentificador(poEntidad.getIdentificador());
		oEntidad.setNombre(poEntidad.getNombre());
		return oEntidad;
	}
}
