package ieci.tecdoc.sgm.autenticacion.ws.client;

import java.rmi.RemoteException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;

import ieci.tecdoc.sgm.core.base64.Base64;
import ieci.tecdoc.sgm.core.services.ServiciosUtils;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.dto.IRetornoServicio;
import ieci.tecdoc.sgm.core.services.sesion.ServicioSesionUsuario;
import ieci.tecdoc.sgm.core.services.sesion.SesionUsuarioException;

public class ServicioSesionUsuarioRemoteClient implements ServicioSesionUsuario{

	private SesionUsuarioWebService service;

	   /**
	    * Elimina una sesión de usuario del sistema. El usuario estará "deslogado" de todas las
	    * aplicaciones de tramitación de SIGEM.
	    * @param psIdSesion Identificador de sesión.
	    * @throws SesionUsuarioException En caso de producirse algún error.
	    */
	public void borrarSesion(String psIdSesion, Entidad poEntidad) throws SesionUsuarioException {
		try{
			RetornoServicio oRetorno = service.borrarSesion(getIdentificadorSesionWS(psIdSesion), getEntidadWS(poEntidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return;
			}else{
				throw getSesionUsuarioException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new SesionUsuarioException(SesionUsuarioException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	   /**
	    * Crea una nueva sesión de usuario en el sistema. Esta sesión de usuario es compartida
	    * por las aplicaciones de tramitación de SIGEM.
	    * @param poSessionUsuario Datos de la sesión de usuario.
	    * @throws SesionUsuarioException En caso de producirse algún error.
	    */
	public void crearSesion(ieci.tecdoc.sgm.core.services.sesion.SesionUsuario poSessionUsuario, Entidad poEntidad) throws SesionUsuarioException {
		try{
			RetornoServicio oRetorno = service.crearSesion(getSesionUsuarioWS(poSessionUsuario), getEntidadWS(poEntidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return;
			}else{
				throw getSesionUsuarioException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new SesionUsuarioException(SesionUsuarioException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	   /**
	    * Obtiene el identificador del método de autenticación utilizado para iniciar la sesión
	    * en el sistema.
	    * @param sessionId Identificador de sesión.
	    * @return Identificador del método de autenticación utilizado al crear la sesión.
	    * @throws SesionUsuarioException En caso de producirse algún error.
	    */
	public String getIdMetodoAutenticacion(String sessionId, Entidad poEntidad) throws SesionUsuarioException {
		try{
			ieci.tecdoc.sgm.autenticacion.ws.client.MetodoAutenticacion oResult =
					service.getIdMetodoAutenticacion(getIdentificadorSesionWS(sessionId), getEntidadWS(poEntidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oResult)){
				return oResult.getId();
			}else{
				throw getSesionUsuarioException((IRetornoServicio)oResult);
			}
		} catch (RemoteException e) {
			throw new SesionUsuarioException(SesionUsuarioException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	   /**
	    * Obtiene la información personal del usuario que inició la sesión en el sistema.
	    * @param sessionId Identificador de sesión.
	    * @return InfoUsuario Información personal del usuario.
	    * @throws SesionUsuarioException En caso de producirse algún error.
	    */
	public ieci.tecdoc.sgm.core.services.sesion.InfoUsuario getInfoUsuario(String sessionId, Entidad poEntidad) throws SesionUsuarioException {
		try{
			InfoUsuario oInfo = service.getInfoUsuario(getIdentificadorSesionWS(sessionId), getEntidadWS(poEntidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oInfo)){
				return getInfoUsuarioServicio(oInfo);
			}else{
				throw getSesionUsuarioException((IRetornoServicio)oInfo);
			}
		} catch (RemoteException e) {
			throw new SesionUsuarioException(SesionUsuarioException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}


	   /**
	    * Obtiene la información del método de autenticación utilizado para iniciar la sesión.
	    * @param sessionId Identificador de la sesión.
	    * @return MetodoAutenticacion Objeto con la información sobre el método de autenticación.
	    * @throws SesionUsuarioException En caso de producirse algún error.
	    */
	public ieci.tecdoc.sgm.core.services.sesion.MetodoAutenticacion getMetodoAutenticacion(String sessionId, Entidad poEntidad) throws SesionUsuarioException {
		try{
			MetodoAutenticacion oMetodo =
				service.getMetodoAutenticacion(getIdentificadorSesionWS(sessionId), getEntidadWS(poEntidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oMetodo)){
				return getMetodoAutenticacionService(oMetodo);
			}else{
				throw getSesionUsuarioException((IRetornoServicio)oMetodo);
			}
		} catch (RemoteException e) {
			throw new SesionUsuarioException(SesionUsuarioException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	   /**
	    * Elimina del sistema todas las sesiones de usuario que hayan sobrepasado el tiempo
	    * máximo de existencia definido por configuración.
	    * @throws SesionUsuarioException En caso de producirse algún error.
	    */
	public void limpiarSesiones(Entidad poEntidad) throws SesionUsuarioException {
		try{
			RetornoServicio oRetorno = service.limpiarSesiones(getEntidadWS(poEntidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return;
			}else{
				throw getSesionUsuarioException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new SesionUsuarioException(SesionUsuarioException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	   /**
	    * Elimina del sistema todas las sesiones de usuario que hayan sobrepasado el tiempo
	    * máximo que llega como parámetro.
	    * @param timeout Intervalo de tiempo hasta el momento actual máximo para mantener sesiones.
	    * @throws SesionUsuarioException En caso de producirse algún error.
	    */
	public void limpiarSesiones(int timeout, Entidad poEntidad) throws SesionUsuarioException {
		try{
			RetornoServicio oRetorno = service.limpiarSesionesTimeOut(getLimpiarSesionesWS(timeout), getEntidadWS(poEntidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return;
			}else{
				throw getSesionUsuarioException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new SesionUsuarioException(SesionUsuarioException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
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
    * @throws SesionUsuarioException
    */
	public String login(String actSessionId, String nombre, String apellidos, String email, String senderId, Entidad poEntidad) throws SesionUsuarioException {
		try{
			LoginExternalUser oUser = new LoginExternalUser();
			oUser.setActSessionId(actSessionId);
			oUser.setNombre(nombre);
			oUser.setApellidos(apellidos);
			oUser.setEmail(email);
			oUser.setSenderId(senderId);
			IdentificadorSesion oId = service.loginExternalUser(oUser, getEntidadWS(poEntidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oId)){
				return oId.getSessionId();
			}else{
				throw getSesionUsuarioException((IRetornoServicio)oId);
			}
		} catch (RemoteException e) {
			throw new SesionUsuarioException(SesionUsuarioException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
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
	public String login(String actSessionId, String authId, X509Certificate certificate, Entidad poEntidad) throws SesionUsuarioException {
		try{
			LoginCertificadoAutoridad oLogin = new LoginCertificadoAutoridad();
			oLogin.setAuthId(authId);
			oLogin.setB64Certificate(Base64.encodeBytes(certificate.getEncoded()));
			oLogin.setProcedureId(actSessionId);
			IdentificadorSesion oId = service.loginCertificateAuth(oLogin, getEntidadWS(poEntidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oId)){
				return oId.getSessionId();
			}else{
				throw getSesionUsuarioException((IRetornoServicio)oId);
			}
		} catch (RemoteException e) {
			throw new SesionUsuarioException(SesionUsuarioException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		} catch (CertificateEncodingException e) {
			throw new SesionUsuarioException(SesionUsuarioException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);		}
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
	public String login(String procedureId, X509Certificate certificate, Entidad poEntidad) throws SesionUsuarioException {
		try{
			LoginCertificado oLogin = new LoginCertificado();
			oLogin.setProcedureId(procedureId);
			oLogin.setB64Certificate(Base64.encodeBytes(certificate.getEncoded()));
			IdentificadorSesion oId = service.loginCertificate(oLogin, getEntidadWS(poEntidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oLogin)){
				return oId.getSessionId();
			}else{
				throw getSesionUsuarioException((IRetornoServicio)oId);
			}
		} catch (RemoteException e) {
			throw new SesionUsuarioException(SesionUsuarioException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}catch (CertificateEncodingException e) {
			throw new SesionUsuarioException(SesionUsuarioException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);		}
	}

	   /**
	    * Desconecta a un usuario del sistema.
	    *
	    * @param sessionId Identificador de sesión.
	    * @throws SesionUsuarioException Si se produce algún error.
	    */
	public void logout(String sessionId, Entidad poEntidad) throws SesionUsuarioException {
		try{
			IdentificadorSesion oId = new IdentificadorSesion();
			oId.setSessionId(sessionId);
			RetornoServicio oRetorno = service.logout(oId, getEntidadWS(poEntidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oRetorno)){
				return;
			}else{
				throw getSesionUsuarioException((IRetornoServicio)oRetorno);
			}
		} catch (RemoteException e) {
			throw new SesionUsuarioException(SesionUsuarioException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}


	   /**
	    * Obtiene todos los datos de la sesión de usuario.
	    * @param sessionId Identificador de la sesión de usuario.
	    * @return SesionUsuario Datos de la sesión de usuario.
	    * @throws SesionUsuarioException En caso de producirse algún error.
	    */
	public ieci.tecdoc.sgm.core.services.sesion.SesionUsuario obtenerSesion(String sessionId, Entidad poEntidad) throws SesionUsuarioException {
		try{
			IdentificadorSesion oId = new IdentificadorSesion();
			oId.setSessionId(sessionId);
			SesionUsuario oSesion = service.obtenerSesion(oId, getEntidadWS(poEntidad));
			if(ServiciosUtils.isReturnOK((IRetornoServicio)oSesion)){
				return getSesionUsuarioServicio(oSesion);
			}else{
				throw getSesionUsuarioException((IRetornoServicio)oSesion);
			}
		} catch (RemoteException e) {
			throw new SesionUsuarioException(SesionUsuarioException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}
	}

	public SesionUsuarioWebService getService() {
		return service;
	}

	public void setService(SesionUsuarioWebService service) {
		this.service = service;
	}

	private SesionUsuarioException getSesionUsuarioException(IRetornoServicio oReturn){
		return new SesionUsuarioException(Long.valueOf(oReturn.getErrorCode()).longValue());
	}

	private IdentificadorSesion getIdentificadorSesionWS(String pcId){
		IdentificadorSesion oId = new IdentificadorSesion();
		oId.setSessionId(pcId);
		return oId;
	}

	private SesionUsuario getSesionUsuarioWS(ieci.tecdoc.sgm.core.services.sesion.SesionUsuario poUsuario){
		if(poUsuario == null){
			return null;
		}
		SesionUsuario oUsuario = new SesionUsuario();
		oUsuario.setHookId(poUsuario.getHookId());
		oUsuario.setLoginDate(poUsuario.getLoginDate());
		oUsuario.setSessionId(poUsuario.getSessionId());
		oUsuario.setSender(getInfoUsuarioWS(poUsuario.getSender()));
		return oUsuario;
	}

	private ieci.tecdoc.sgm.core.services.sesion.SesionUsuario getSesionUsuarioServicio(SesionUsuario poUsuario){
		if(poUsuario == null){
			return null;
		}
		ieci.tecdoc.sgm.core.services.sesion.SesionUsuario oUsuario = new ieci.tecdoc.sgm.core.services.sesion.SesionUsuario();
		oUsuario.setHookId(poUsuario.getHookId());
		oUsuario.setLoginDate(poUsuario.getLoginDate());
		oUsuario.setSessionId(poUsuario.getSessionId());
		oUsuario.setSender(getInfoUsuarioServicio(poUsuario.getSender()));
		return oUsuario;
	}

	private InfoUsuario getInfoUsuarioWS(ieci.tecdoc.sgm.core.services.sesion.InfoUsuario poUser){
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

	private ieci.tecdoc.sgm.core.services.sesion.InfoUsuario getInfoUsuarioServicio(InfoUsuario poUsuario){
		if(poUsuario == null){
			return null;
		}
		ieci.tecdoc.sgm.core.services.sesion.InfoUsuario oUser = new ieci.tecdoc.sgm.core.services.sesion.InfoUsuario();
		oUser.setCertificateIssuer(poUsuario.getCertificateIssuer());
		oUser.setCertificateSN(poUsuario.getCertificateSN());
		oUser.setCIF(poUsuario.getCIF());
		oUser.setEmail(poUsuario.getEmail());
		oUser.setId(poUsuario.getId());
		oUser.setInQuality(poUsuario.getInQuality());
		oUser.setName(poUsuario.getName());
		oUser.setSocialName(poUsuario.getSocialName());
		oUser.setFirstName(poUsuario.getFirstName());
		oUser.setSurName(poUsuario.getSurName());
		oUser.setSurName2(poUsuario.getSurName2());
		return oUser;
	}

	private ieci.tecdoc.sgm.core.services.sesion.MetodoAutenticacion getMetodoAutenticacionService(MetodoAutenticacion poMetodo){
		if(poMetodo == null){
			return null;
		}
		ieci.tecdoc.sgm.core.services.sesion.MetodoAutenticacion oMetodo = new ieci.tecdoc.sgm.core.services.sesion.MetodoAutenticacion();
		oMetodo.setDescription(poMetodo.getDescription());
		oMetodo.setId(poMetodo.getId());
		oMetodo.setType(poMetodo.getType());
		return oMetodo;
	}

	private LimpiarSesiones getLimpiarSesionesWS(int ptimeout){
		LimpiarSesiones oSesiones = new LimpiarSesiones();
		oSesiones.setTimeout(String.valueOf(ptimeout));
		return oSesiones;
	}

	private ieci.tecdoc.sgm.autenticacion.ws.client.Entidad getEntidadWS(Entidad poEntidad){
		if(poEntidad == null){
			return null;
		}
		ieci.tecdoc.sgm.autenticacion.ws.client.Entidad oEntidad = new ieci.tecdoc.sgm.autenticacion.ws.client.Entidad();
		oEntidad.setIdentificador(poEntidad.getIdentificador());
		oEntidad.setNombre(poEntidad.getNombre());
		return oEntidad;
	}
}
